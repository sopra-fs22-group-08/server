package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import ch.uzh.ifi.hase.soprafs22.constant.Visibility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

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
    final String baseMsg = "does not match!";

    @BeforeEach
    public void setup() {
        // define testuser
        testUser = new User();
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
        deckRequest.setUser(testUser);
        deckRequest.setDeckname("testdeck");
        deckRequest.setVisibility(Visibility.PUBLIC);

        Deck createdDeck = deckService.createDeck(testUser.getId(), deckRequest);

        assertEquals(deckRequest.getId(), createdDeck.getId(), "Deck ID" + baseMsg);
        assertEquals(deckRequest.getUser(), createdDeck.getUser(), "Saved User" + baseMsg);
        assertEquals(deckRequest.getDeckname(), createdDeck.getDeckname(), "Deckname" + baseMsg);
        assertEquals(deckRequest.getVisibility(), createdDeck.getVisibility(), "Visibility" + baseMsg);
    }

    @Test
    public void updateDeck_success() {
        Deck deckRequest = new Deck();
        deckRequest.setUser(testUser);
        deckRequest.setDeckname("testdeck");
        deckRequest.setVisibility(Visibility.PUBLIC);
        deckService.createDeck(testUser.getId(), deckRequest);

        Deck inputDeck = new Deck();
        inputDeck.setDeckname("newtestdeck");
        inputDeck.setVisibility(Visibility.PRIVATE);

        Deck updatedDeck = deckService.updateDeck(deckRequest, inputDeck);

        assertEquals(deckRequest.getUser(), updatedDeck.getUser(), "Saved User " + baseMsg);
        assertEquals(deckRequest.getDeckname(), updatedDeck.getDeckname(), "Deckname " + baseMsg);
        assertEquals(deckRequest.getVisibility(), updatedDeck.getVisibility(), "Visibility " + baseMsg);
    }

    @Test
    public void getDeckById_success() {
        Deck deckRequest = new Deck();
        deckRequest.setUser(testUser);
        deckRequest.setDeckname("testdeck");
        deckRequest.setVisibility(Visibility.PUBLIC);

        deckService.createDeck(testUser.getId(), deckRequest);

        Deck returnedDeck = deckService.getDeckById(deckRequest.getId());

        assertEquals(deckRequest.getId(), returnedDeck.getId(), "Deck ID" + baseMsg);
    }

    @Test
    public void getDecksByUserId_success() {
        Deck deckRequest = new Deck();
        deckRequest.setUser(testUser);
        deckRequest.setDeckname("testdeck");
        deckRequest.setVisibility(Visibility.PUBLIC);

        deckService.createDeck(testUser.getId(), deckRequest);

        List<Deck> returnedDeck = deckService.getDecksByUserId(testUser.getId());

        assertEquals(deckRequest.getId(), returnedDeck.get(0).getId(), "Deck ID" + baseMsg);
    }

    @Test
    public void deleteDeckById_success() {
        Deck deckRequest = new Deck();
        deckRequest.setUser(testUser);
        deckRequest.setDeckname("testdeck");
        deckRequest.setVisibility(Visibility.PUBLIC);
        Deck createdDeck = deckService.createDeck(testUser.getId(), deckRequest);

        deckService.deleteDeckById(createdDeck.getId());

        assertThrows(ResponseStatusException.class, () -> deckService.getDeckById(createdDeck.getId()));
    }

    @Test
    public void getDecksByFuzzyFind_success() {
        Deck deckRequest = new Deck();
        deckRequest.setUser(testUser);
        deckRequest.setDeckname("testdeck");
        deckRequest.setVisibility(Visibility.PUBLIC);
        Deck createdDeck = deckService.createDeck(testUser.getId(), deckRequest);

        List<Deck> foundDeck = deckService.getDecksByFuzzyFind("est");

        assertEquals(createdDeck.getDeckname(), foundDeck.get(0).getDeckname(), "Deckname " + baseMsg);
    }
}