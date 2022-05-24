package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.constant.Visibility;
import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.CardRepository;
import ch.uzh.ifi.hase.soprafs22.repository.DeckRepository;

/**
 * MCCardServiceTest
 */
public class MCCardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private DeckRepository deckRepository;

    @InjectMocks
    private MCCardService cardService;

    private MultipleChoiceCard card;
    private Deck deck;
    private User user;
    private List<String> options;
    final String baseMsg = "does not match!";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        deck = new Deck();
        deck.setId(1L);
        deck.setUser(user);
        deck.setDeckname("testOne");
        deck.setVisibility(Visibility.PUBLIC);

        card = new MultipleChoiceCard();
        card.setId(1L);
        card.setDeck(deck);
        card.setAnswer("Correct");
        card.setQuestion("Which one is correct?");
        options = new ArrayList<String>() {
            {
                add("Correct");
                add("incorrect1");
                add("incorrect2");
                add("incorrect3");
            }
        };
        card.setOptions(options);

        // Mockito.when(deckRepository.findByDeckId(Mockito.anyLong()))
        // .thenReturn(deck);

        Mockito.when(cardRepository.save(Mockito.any()))
                .thenReturn(card);
    }

    // TODO: createCard
    // FIXME: cannot create cards as mocks, as there is some conflict in the mocked
    // deckRepository
    // @Test
    // public void createCard_success() {
    //
    // MultipleChoiceCard retCard = cardService.createMCCard(1L, card);
    //
    // // assertThrows(ResponseStatusException.class, () ->
    // cardService.createMCCard(1L, card));
    //
    // // Mockito.verify(cardRepository, Mockito.times(1)).save(Mockito.any());
    //
    // assertEquals(1L, retCard.getId(), "CardId " + baseMsg);
    // assertEquals(deck, card.getDeck(), "Deck " + baseMsg);
    // assertEquals("Correct", retCard.getAnswer(), "Answer " + baseMsg);
    // assertEquals("Which one is correct?", retCard.getQuestion(), "Question " +
    // baseMsg);
    // assertEquals(options, retCard.getOptions(), "Options " + baseMsg);
    // }

    @Test
    public void updateCard_success() {
        Mockito.when(cardRepository.findCardById(Mockito.anyLong()))
                .thenReturn(card);

        MultipleChoiceCard cardRequest = card;
        cardRequest.setAnswer("corrected answer");
        cardRequest.setQuestion("corrected question");
        cardRequest.setOptions(new ArrayList<String>() {
            {
                add("corrected answer");
                add("incorrect1");
                add("incorrect2");
                add("incorrect3");
            }
        });

        MultipleChoiceCard retCard = cardService.updateCard(1L, cardRequest);

        assertEquals(cardRequest.getId(), retCard.getId(), "CardId " + baseMsg);
        assertEquals(cardRequest.getDeck(), card.getDeck(), "Deck " + baseMsg);
        assertEquals(cardRequest.getAnswer(), retCard.getAnswer(), "Answer " + baseMsg);
        assertEquals(cardRequest.getQuestion(), retCard.getQuestion(), "Question " + baseMsg);
        assertEquals(cardRequest.getOptions(), retCard.getOptions(), "Options " + baseMsg);
    }

    @Test
    public void deleteCard_success() {
        Mockito.when(cardRepository.findCardById(Mockito.anyLong()))
                .thenReturn(card);
        MultipleChoiceCard delCard = cardService.deleteCard(1L);

        assertEquals(card, delCard, "Deleted Card " + baseMsg);
    }

    // TODO: get card by id
    // @Test
    // public void getCardById_success() {
    //     // Mockito.when(cardRepository.findCardById(Mockito.anyLong()))
    //     //         .thenReturn(card);
    //     MultipleChoiceCard retCard = cardService.getCardByCardId(card.getId());
    //
    //     assertThrows(ResponseStatusException.class, () -> cardService.getCardByCardId(card.getId()));
    // }

    // TODO: get cards by deckid
}