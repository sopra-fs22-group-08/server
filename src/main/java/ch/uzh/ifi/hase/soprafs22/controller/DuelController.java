package ch.uzh.ifi.hase.soprafs22.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.hase.soprafs22.entity.Duel;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DuelGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DuelPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.DuelService;

/**
 * DuelController
 */
@RestController
public class DuelController {

    @Autowired
    private DuelService duelService;

    @PostMapping("/duels")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DuelGetDTO createDuel(@RequestBody DuelPostDTO duelPostDTO) {
        /*
         * TODO: to instantiate the duel, we need to instantiate the users inside
         * the duel.
         * We could do it with passing the name and then finding the users
         * in the repository.
         */
        Duel duelToBeCreated = DTOMapper.INSTANCE.convertDuelPostDtoToEntity(duelPostDTO);
        Duel createdDuel = duelService.createDuel(duelToBeCreated);
        return DTOMapper.INSTANCE.convertEntityToDuelGetDTO(createdDuel);
    }
}