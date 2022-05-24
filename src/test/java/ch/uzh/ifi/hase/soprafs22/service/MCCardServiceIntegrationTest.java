package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.uzh.ifi.hase.soprafs22.constant.Visibility;
import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.CardRepository;
import ch.uzh.ifi.hase.soprafs22.repository.DeckRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;

/**
 * MCCardServiceIntegrationTest
 * 
 * @brief will be a full stack integration for the services
 */
@WebAppConfiguration
@SpringBootTest
public class MCCardServiceIntegrationTest {

    @Qualifier("multipleChoiceCardRepository")
    @Autowired
    private CardRepository cardRepository;

    @Qualifier("deckRepository")
    @Autowired
    private DeckRepository deckRepository;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService;

    @Autowired
    private MCCardService cardService;

    final String baseMsg = "does not match!";
    private User testUser;
    private Deck testDeck;

    @BeforeEach
    public void setup() {
        // NOTE: IDs are set automatically, otherwise conflicts

        // define testuser
        testUser = new User();
        testUser.setFirstName("test");
        testUser.setLastName("name");
        testUser.setUsername("testUsername");
        testUser.setEmail("firstname@email.com");
        testUser.setPassword("testPassword");

        // save user in repository through the userService
        userService.createUser(testUser);

        testDeck = new Deck();
        testDeck.setUser(testUser);
        testDeck.setDeckname("testOne");
        testDeck.setVisibility(Visibility.PUBLIC);

        deckService.createDeck(testUser.getId(), testDeck);
    }

    @AfterEach
    public void teardown() {
        // flush all the repositories
        cardRepository.deleteAll();
        deckRepository.deleteAll();
        userRepository.deleteAll();
    }

    private MultipleChoiceCard createCard() {
        var options = new ArrayList<String>() {
            {
                add("Correct");
                add("incorrect1");
                add("incorrect2");
                add("incorrect3");
            }
        };

        MultipleChoiceCard cardRequest = new MultipleChoiceCard();
        // cardRequest.setId(1L);
        cardRequest.setDeck(testDeck);
        cardRequest.setAnswer("Correct");
        cardRequest.setQuestion("Which is correct?");
        cardRequest.setOptions(options);

        return cardRequest;
    }

    @Test
    public void createCard_success() {

        MultipleChoiceCard cardRequest = createCard();
        MultipleChoiceCard createdCard = cardService.createMCCard(testDeck.getId(), cardRequest);

        assertEquals(cardRequest.getId(), createdCard.getId(), "CardId " + baseMsg);
        assertEquals(cardRequest.getDeck(), createdCard.getDeck(), "Deck " + baseMsg);
        assertEquals(cardRequest.getAnswer(), createdCard.getAnswer(), "Answer " + baseMsg);
        assertEquals(cardRequest.getQuestion(), createdCard.getQuestion(), "Question " +
                baseMsg);
        assertEquals(cardRequest.getOptions(), createdCard.getOptions(), "Options " + baseMsg);

    }

    // get card by id
    @Test
    public void getCardByCardId_success() {
        MultipleChoiceCard cardRequest = createCard();
        cardService.createMCCard(testDeck.getId(), cardRequest);
        MultipleChoiceCard returnedCard = cardService.getCardByCardId(cardRequest.getId());

        assertEquals(cardRequest.getId(), returnedCard.getId(), "CardId " + baseMsg);
        assertEquals(cardRequest.getAnswer(), returnedCard.getAnswer(), "Answer " + baseMsg);
        assertEquals(cardRequest.getQuestion(), returnedCard.getQuestion(), "Question " +
                baseMsg);
    }

    // TODO: get cards by deckid

}