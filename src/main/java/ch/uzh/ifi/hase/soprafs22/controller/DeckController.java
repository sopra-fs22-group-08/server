package ch.uzh.ifi.hase.soprafs22.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DeckGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DeckPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.DeckService;

@RestController
public class DeckController {

    /**
     * @brief retrieves ALL "public" Decks from database.
     *        GET REQUEST: Status Code OK 200. IF fail Status Code -> 404 -> Not
     *        Found
     */
    @GetMapping("/decks")
    @ResponseStatus(HttpStatus.OK)
    public List<DeckGetDTO> getAllPublicDecks() {
        List<Deck> decks = deckService.getDecks();
        List<DeckGetDTO> deckGetDTOs = new ArrayList<>();

        for (Deck deck : decks) {
            deckGetDTOs.add(DTOMapper.INSTANCE.convertEntityToDeckGetDTO(deck));
        }
        return deckGetDTOs;

    }

    @Autowired
    private DeckService deckService;

    /**
     * @brief retrieves Decks based on given ID of a Deck
     *        GET REQUEST: Status Code OK 200. IF fail Status Code -> 404 -> Not
     *        Found
     */
    @GetMapping("/decks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Deck getDecksById(@PathVariable("id") long id) {
        // get deck by id from repository
        Deck deckToBeReturned = deckService.getDecksById(id);
        return deckToBeReturned;
    }

    /**
     * @brief retrieves all Decks based on given ID of User
     *        GET REQUEST: Status Code OK 200. IF fail Status Code -> 404 -> Not
     *        Found
     */
    @GetMapping("/users/{userId}/decks")
    @ResponseStatus(HttpStatus.OK)
    public List<DeckGetDTO> getDecksByUserId(@PathVariable("userId") long userId) {
        // fetch all cards of deck in the internal representation
        List<Deck> decksToBeReturned = deckService.getDecksByUserId(userId);
        List<DeckGetDTO> deckGetDTOS = new ArrayList<>();
        // convert internal representation deck to API deck
        for (Deck deck : decksToBeReturned) {
            deckGetDTOS.add(DTOMapper.INSTANCE.convertEntityToDeckGetDTO(deck));
        }
        return deckGetDTOS;
    }

    /**
     * @brief creates a single Deck based on given ID of user
     *        POST REQUEST: Status Code OK 200. IF fail Status Code -> 404 -> Not
     *        Found
     */
    @PostMapping("/users/{userId}/decks")
    @ResponseStatus(HttpStatus.CREATED)
    public Deck createDeck(@PathVariable("userId") Long userId, @RequestBody DeckPostDTO deckPostDTO) {
        // convert API deck to internal representation
        Deck deckRequest = DTOMapper.INSTANCE.convertDeckPostDTOtoEntity(deckPostDTO);
        Deck deck = deckService.createDeck(userId, deckRequest);
        return deck;
    }

}