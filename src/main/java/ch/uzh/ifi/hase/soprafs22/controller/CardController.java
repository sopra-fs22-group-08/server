package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.repository.CardRepository;
import ch.uzh.ifi.hase.soprafs22.repository.DeckRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.MultipleChoiceCardPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CardController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeckRepository deckRepository;

    /**
     * @brief creates a single Deck based on given ID of user
     *        POST REQUEST: Status Code OK 200. IF fail Status Code -> 404 -> Not
     *        Found
     */
    @PostMapping("/decks/{deckId}/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public MultipleChoiceCard createCard(@PathVariable("deckId") Long deckId, @RequestBody MultipleChoiceCardPostDTO multipleChoiceCardPostDTO){
        //convert API deck to internal representation
        MultipleChoiceCard cardRequest = DTOMapper.INSTANCE.convertMultipleChoiceCardPostDTOtoEntity(multipleChoiceCardPostDTO);
        //create deck and save to deck repository
        MultipleChoiceCard card = this.deckRepository.findById(deckId).map(deck -> {
            cardRequest.setDeck(deck);
            return cardRepository.save(cardRequest);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found User with id = " + deckId));

        return card;
    }


}
