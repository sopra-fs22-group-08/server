package ch.uzh.ifi.hase.soprafs22.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.rest.dto.MultipleChoiceCardGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.MultipleChoiceCardPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.MCCardService;

@RestController
public class CardController {
    private final MCCardService mccardService;

    public CardController(MCCardService mccardService) {
        this.mccardService = mccardService;
    }

    /**
     * @brief creates a single Card based on given ID of user
     *        POST REQUEST: Status Code OK 200. IF fail Status Code -> 404 -> Not
     *        Found
     */
    @PostMapping("/decks/{deckId}/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public MultipleChoiceCard createCard(@PathVariable("deckId") Long deckId,
            @RequestBody MultipleChoiceCardPostDTO multipleChoiceCardPostDTO) {
        // convert API deck to internal representation
        MultipleChoiceCard mcCardRequest = DTOMapper.INSTANCE
                .convertMultipleChoiceCardPostDTOtoEntity(multipleChoiceCardPostDTO);

        // create deck and save to deck repository
        MultipleChoiceCard mcCreatedCard = this.mccardService.createMCCard(deckId, mcCardRequest);

        return mcCreatedCard;
    }

    @GetMapping("/decks/{deckId}/cards")
    @ResponseStatus(HttpStatus.OK)
    public List<MultipleChoiceCardGetDTO> getCardsByDeckId(@PathVariable("deckId") Long deckId) {

        // fetch all cards of deck in the internal representation
        List<MultipleChoiceCard> multipleChoiceCards = this.mccardService.getCardsByDeckId(deckId);
        List<MultipleChoiceCardGetDTO> multipleChoiceCardGetDTOs = new ArrayList<>();

        for (MultipleChoiceCard mccard : multipleChoiceCards) {
            multipleChoiceCardGetDTOs.add(DTOMapper.INSTANCE.convertEntityToMultipleChoiceCardGetDTO(mccard));
        }

        return multipleChoiceCardGetDTOs;
    }
}