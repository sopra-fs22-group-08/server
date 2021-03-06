package ch.uzh.ifi.hase.soprafs22.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import ch.uzh.ifi.hase.soprafs22.constant.Visibility;

import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.repository.CardRepository;
import ch.uzh.ifi.hase.soprafs22.repository.DeckRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;

@Service
@Transactional
public class DeckService {
    // TODO: add logger a la UserService
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository, UserRepository userRepository, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public Deck createDeck(Long userID, Deck deckRequest) {
        String trimmedDeckname = deckRequest.getDeckname().trim();
        if (trimmedDeckname.length() != 0) {
            // create deck and save to deck repository
            Deck deckToBeReturned = this.userRepository.findById(userID).map(user -> {
                deckRequest.setUser(user);
                return deckRepository.save(deckRequest);
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User with ID " + userID + " has not been found"));
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "Deck must not have an empty or space filled name!");
        }
        return deckRequest;
    }

    public Deck getDeckById(Long id) {
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

    public List<Deck> getDecks() {
        return this.deckRepository.findAll();
    }

    /**
     * @brief updates the Deck with the new Deckname and new Visibility
     */
    public Deck updateDeck(Deck currentDeck, Deck deckInput) {
        String trimmedDeckname = deckInput.getDeckname().trim();
        // check if deckname is empty or only whitespaces
        if (deckInput.getDeckname() == null || trimmedDeckname.length() == 0) {
            String baseErrorMessage = "You cannot choose an empty Deckname!";
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage));
        }
        // set changes
        currentDeck.setVisibility(deckInput.getVisibility());
        currentDeck.setDeckname(trimmedDeckname);
        // save changes
        deckRepository.save(currentDeck);
        deckRepository.flush();
        return currentDeck;
    }

    public void deleteDeckById(long deckId) {
        List<MultipleChoiceCard> cardsToBeDeleted = cardRepository.findCardByDeckId(deckId);
        try {
            for (MultipleChoiceCard card : cardsToBeDeleted) {
                cardRepository.deleteById(card.getId());
            }
            deckRepository.deleteById(deckId);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Duel with ID " + deckId + " does NOT exist");
        }
    }

    public List<Deck> getDecksByVisibility(Visibility visibility) {
        List<Deck> publicDecksToBeReturned = this.deckRepository.findDeckByVisibility(visibility);
        return publicDecksToBeReturned;
    }

    public List<Deck> getDecksByFuzzyFind(String deckname) {
        List<Deck> decksFromRepo = deckRepository.findDeckByDecknameContainingIgnoreCase(deckname);
        if (decksFromRepo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No decks with that NAME found!");
        }
        List<Deck> decksToBeReturned = new ArrayList<>();
        for (Deck in : decksFromRepo) {
            if (in.getVisibility() == Visibility.PUBLIC) {
                decksToBeReturned.add(in);
            }
        }
        if (decksToBeReturned.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No PUBLIC Decks with that NAME found!");
        }
        return decksToBeReturned;
    }
}