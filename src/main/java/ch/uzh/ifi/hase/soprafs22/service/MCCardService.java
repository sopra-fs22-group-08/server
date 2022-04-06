package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Card;
import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.repository.CardRepository;
import ch.uzh.ifi.hase.soprafs22.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
@Transactional
public class MCCardService {

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
    public MultipleChoiceCard createCard(Long deckId, MultipleChoiceCard cardRequest){
        MultipleChoiceCard card = this.deckRepository.findById(deckId).map(deck -> {
            cardRequest.setDeck(deck);
            return cardRepository.save(cardRequest);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found Deck with id = " + deckId));
        return card;
    }
}
