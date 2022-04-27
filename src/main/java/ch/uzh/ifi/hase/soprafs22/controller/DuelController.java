package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/duels/{duelId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInvitation(@PathVariable(value = "duelId") long duelId){
        duelService.deleteDuelById(duelId);
    }

    //TODO: Problem with Enumeration
    @PutMapping("/duels/{duelId}/players/{playerId}/status")
    public void updateDuel(@PathVariable (name = "duelId") long duelId,
                                         @PathVariable(name = "playerId") long playerId,
                                         @RequestParam("playerStatus") PlayerStatus playerStatus){
        duelService.updatePlayerStatus(duelId, playerId, playerStatus);
    }
}