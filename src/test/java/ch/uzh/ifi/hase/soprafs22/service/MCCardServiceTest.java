package ch.uzh.ifi.hase.soprafs22.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.repository.CardRepository;

/**
 * MCCardServiceTest
 */
public class MCCardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private MCCardService cardService;

    private MultipleChoiceCard card;

    // create card
    // update card
    // delete card
    // get card by id
    // get cards by deckid
}