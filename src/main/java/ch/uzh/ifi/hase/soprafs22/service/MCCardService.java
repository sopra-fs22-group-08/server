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

    public MultipleChoiceCard updateCard(Long cardId, MultipleChoiceCard cardRequest) {
        String baseErrorMessage = "You cannot update with empty";
        // get card out of the card repository
        MultipleChoiceCard currentCard = cardRepository.findCardById(cardId);

        // allow updating to same content but not empty ones
        if (cardRequest.getAnswer() != null) {
            currentCard.setAnswer(cardRequest.getAnswer());
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, baseErrorMessage + "answer!");
        }
        if (cardRequest.getQuestion() != null) {
            currentCard.setQuestion(cardRequest.getQuestion());
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, baseErrorMessage + "question!");
        }
        if (cardRequest.getOptions() != null) {
            currentCard.setOptions(cardRequest.getOptions());
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, baseErrorMessage + "options!");
        }
        cardRepository.save(currentCard);
        cardRepository.flush();

        return currentCard;
    }

    public MultipleChoiceCard deleteCard(Long cardId) {
        try {
            MultipleChoiceCard currentCard = cardRepository.findCardById(cardId);
            cardRepository.deleteById(cardId);
            return currentCard;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Card with ID " + cardId + " does NOT exist");
        }
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