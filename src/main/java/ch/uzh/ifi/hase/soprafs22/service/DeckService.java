package ch.uzh.ifi.hase.soprafs22.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.repository.DeckRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;

@Service
@Transactional
public class DeckService {
    // TODO: add logger a la UserService
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository, UserRepository userRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
    }

    public Deck createDeck(Long userID, Deck deckRequest) {
        // create deck and save to deck repository
        Deck deckToBeReturned = this.userRepository.findById(userID).map(user -> {
            deckRequest.setUser(user);
            return deckRepository.save(deckRequest);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User with ID" + userID + " has not been found"));
        return deckToBeReturned;
    }

    public Deck getDecksById(long id) {
        Deck deckToBeReturned = deckRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No deck was found with ID: " + id));
        return deckToBeReturned;
    }

    public List<Deck> getDecksByUserId(long userID) {
        // check for existence of user
        if (!userRepository.existsById(userID)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not find with this id: " + userID);
        }
        // fetch all decks of user in the internal representation
        List<Deck> decksToBeReturned = this.deckRepository.findDeckByUserId(userID);
        return decksToBeReturned;
    }
}