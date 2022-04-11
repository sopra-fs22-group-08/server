package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.repository.CardRepository;
import ch.uzh.ifi.hase.soprafs22.repository.DeckRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.MultipleChoiceCardGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.MultipleChoiceCardPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.MCCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CardController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeckRepository deckRepository;

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
    public MultipleChoiceCard createCard(@PathVariable("deckId") Long deckId, @RequestBody MultipleChoiceCardPostDTO multipleChoiceCardPostDTO){
        //convert API deck to internal representation
        MultipleChoiceCard mcCardRequest = DTOMapper.INSTANCE.convertMultipleChoiceCardPostDTOtoEntity(multipleChoiceCardPostDTO);

        //create deck and save to deck repository
        MultipleChoiceCard mcCreatedCard = this.mccardService.createMCCard(deckId, mcCardRequest);

        return mcCreatedCard;
    }

    @GetMapping("/decks/{deckId}/cards")
    @ResponseStatus(HttpStatus.OK)
    public List<MultipleChoiceCardGetDTO> getCardsByDeckId(@PathVariable("deckId") Long deckId){

        //fetch all cards of deck in the internal representation
        //List<MultipleChoiceCard> multipleChoiceCards = this.mccardService.getCards(deckId);
        List<MultipleChoiceCard> multipleChoiceCards = this.mccardService.getCards(deckId);
        List<MultipleChoiceCardGetDTO> multipleChoiceCardGetDTOs = new ArrayList<>();

        for (MultipleChoiceCard mccard: multipleChoiceCards) {
            multipleChoiceCardGetDTOs.add(DTOMapper.INSTANCE.convertEntityToMultipleChoiceCardGetDTO(mccard));
        }

        return multipleChoiceCardGetDTOs;
    }


}
