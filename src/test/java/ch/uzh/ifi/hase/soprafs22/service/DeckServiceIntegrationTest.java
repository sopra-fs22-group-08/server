package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.DeckRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;

/**
 * DeckServiceIntegrationTest
 */
@WebAppConfiguration
@SpringBootTest
public class DeckServiceIntegrationTest {

    @Qualifier("deckRepository")
    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private DeckService deckService;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setup() {
        // define testuser
        testUser = new User();
        testUser.setId(1l);
        testUser.setFirstName("test");
        testUser.setLastName("name");
        testUser.setUsername("testUsername");
        testUser.setEmail("firstname@email.com");
        testUser.setPassword("testPassword");

        // save user in repository through the userService
        userService.createUser(testUser);
    }

    @AfterEach
    public void teardown() {
        // flush all the repositories
        deckRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createDeck_validUser_success() {
        assertNull(deckRepository.findDeckByDeckname("testdeck"));

        Deck deckRequest = new Deck();
        deckRequest.setId(1L);
        deckRequest.setUser(testUser);
        deckRequest.setDeckname("testdeck");

        Deck createdDeck = deckService.createDeck(testUser.getId(), deckRequest);

        String baseMsg = "does not match!";
        assertEquals(deckRequest.getId(), createdDeck.getId(), "Deck ID" + baseMsg);
        assertEquals(deckRequest.getUser(), createdDeck.getUser(), "Saved User" + baseMsg);
        assertEquals(deckRequest.getDeckname(), createdDeck.getDeckname(), "Deckname" + baseMsg);
    }
}