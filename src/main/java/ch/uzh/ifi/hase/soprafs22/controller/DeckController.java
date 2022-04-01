package ch.uzh.ifi.hase.soprafs22.controller;
import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * @brief retrieves all Decks based on given ID of User
     *        GET REQUEST: Status Code OK 200. IF fail Status Code -> 404 -> Not
     *        Found
     */
    @GetMapping("/users/{userId}/decks")
    @ResponseStatus(HttpStatus.OK)
    public List<Deck> getDecksByUserId(@PathVariable("userId") long userId){
        if(!userRepository.existsById(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not find with this id: "+ userId);
        }
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
    public Deck createDeck(@PathVariable("userId") Long userId, @RequestBody Deck deckRequest){
        Deck deck = this.userRepository.findById(userId).map(user -> {
            deckRequest.setUser(user);
            return deckRepository.save(deckRequest);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found User with id = " + userId));
        return deck;
    }

}
