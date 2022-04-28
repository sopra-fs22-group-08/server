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

import java.util.ArrayList;
import java.util.List;

/**
 * DuelController
 */
@RestController
public class DuelController {

    @Autowired
    private DuelService duelService;

    @PostMapping("/duels")
    @ResponseStatus(HttpStatus.CREATED)
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

    @GetMapping("/duels")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<DuelGetDTO> getAllDuels(){
        List<Duel> duels = duelService.getDuels();
        List<DuelGetDTO> duelGetDTOs = new ArrayList<>();

        for(Duel duel : duels){
            duelGetDTOs.add(DTOMapper.INSTANCE.convertEntityToDuelGetDTO(duel));
        }
        return duelGetDTOs;

    }

    @GetMapping("/duels/{duelId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DuelGetDTO getDuelById(@PathVariable(name = "duelId") long duelId){
        Duel duel = duelService.getDuelById(duelId);
        DuelGetDTO duelGetDTO = DTOMapper.INSTANCE.convertEntityToDuelGetDTO(duel);
        return duelGetDTO;

    }
    //TODO: Duel should be deleted when both players are finished
    @PutMapping("/duels/{duelId}/players/{playerId}/status/{playerStatus}")
    @ResponseStatus(HttpStatus.OK)
    public void updateDuelStatus(@PathVariable (name = "duelId") long duelId,
                                         @PathVariable(name = "playerId") long playerId,
                                         @PathVariable(name = "playerStatus") PlayerStatus playerStatus){
        duelService.updatePlayerStatus(duelId, playerId, playerStatus);
    }

    @PutMapping("/duels/{duelId}/players/{playerId}/score/{newScore}")
    @ResponseStatus(HttpStatus.OK)
    public void updateDuelScore(@PathVariable (name = "duelId") long duelId,
                                @PathVariable(name = "playerId") long playerId,
                                @PathVariable(name = "newScore") long newScore){
        duelService.updatePlayerScore(duelId, playerId, newScore);

    }


}