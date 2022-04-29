package ch.uzh.ifi.hase.soprafs22.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.repository.CardRepository;
import ch.uzh.ifi.hase.soprafs22.repository.DeckRepository;

@Service
@Transactional
public class MCCardService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    @Autowired
    public MCCardService(CardRepository cardRepository, DeckRepository deckRepository) {
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
    }

    /**
     * @brief adds new Card to Deck with given ID or returns: 404 Not Found
     */
    public MultipleChoiceCard createMCCard(Long deckId, MultipleChoiceCard cardRequest) {
        MultipleChoiceCard card = this.deckRepository.findById(deckId).map(deck -> {
            cardRequest.setDeck(deck);
            return cardRepository.save(cardRequest);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found Deck with id = " + deckId));
        return card;
    }

    public List<MultipleChoiceCard> getCardsByDeckId(Long deckId) {
        // check for existence of deck
        if (!deckRepository.existsById(deckId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found with this id: " + deckId);
        }
        // fetch all cards of deck in the internal representation
        List<MultipleChoiceCard> multipleChoiceCards = this.cardRepository.findCardByDeckId(deckId);
        return multipleChoiceCards;
    }
}