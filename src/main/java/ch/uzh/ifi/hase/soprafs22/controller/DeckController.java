package ch.uzh.ifi.hase.soprafs22.controller;
import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.repository.DeckRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DeckPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class DeckController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeckRepository deckRepository;

    /**
     * @brief retrieves Decks based on given ID of a Deck
     *        GET REQUEST: Status Code OK 200. IF fail Status Code -> 404 -> Not
     *        Found
     */
    @GetMapping("/decks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Deck getDecksById(@PathVariable("id") long id){
        //get deck by id from repository
        Deck deck = deckRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Deck with this ID exists:" + id));
        return deck;
    }

    /**
     * @brief retrieves all Decks based on given ID of User
     *        GET REQUEST: Status Code OK 200. IF fail Status Code -> 404 -> Not
     *        Found
     */
    @GetMapping("/users/{userId}/decks")
    @ResponseStatus(HttpStatus.OK)
    public List<Deck> getDecksByUserId(@PathVariable("userId") long userId){
        //check for existence of user
        if(!userRepository.existsById(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not find with this id: "+ userId);
        }
        //fetch all decks of user in the internal representation
        List<Deck> decks = this.deckRepository.findDeckByUserId(userId);
        return decks;
    }

    /**
     * @brief creates a single Deck based on given ID of user
     *        POST REQUEST: Status Code OK 200. IF fail Status Code -> 404 -> Not
     *        Found
     */
    @PostMapping("/users/{userId}/decks")
    @ResponseStatus(HttpStatus.CREATED)
    public Deck createDeck(@PathVariable("userId") Long userId, @RequestBody DeckPostDTO deckPostDTO){
        //convert API deck to internal representation
        Deck deckRequest = DTOMapper.INSTANCE.convertDeckPostDTOtoEntity(deckPostDTO);
        //create deck and save to deck repository
        Deck deck = this.userRepository.findById(userId).map(user -> {
            deckRequest.setUser(user);
            return deckRepository.save(deckRequest);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found User with id = " + userId));

        return deck;
    }



}