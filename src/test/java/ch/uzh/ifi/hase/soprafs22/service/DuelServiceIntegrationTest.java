package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.uzh.ifi.hase.soprafs22.entity.Duel;
import ch.uzh.ifi.hase.soprafs22.entity.Invitation;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.DuelRepository;
import ch.uzh.ifi.hase.soprafs22.repository.InvitationRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;

/**
 * DuelServiceIntegrationTest
 */
@WebAppConfiguration
@SpringBootTest
public class DuelServiceIntegrationTest {

    @Autowired
    @Qualifier("duelRepository")
    private DuelRepository duelRepository;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DuelService duelService;

    final String baseMsg = "does not match!";
    private User testUserOne;
    private User testUserTwo;

    @BeforeEach
    public void setup() {
        // define testuser
        testUserOne = new User();
        testUserOne.setFirstName("testOne");
        testUserOne.setLastName("name");
        testUserOne.setUsername("testUsernameOne");
        testUserOne.setEmail("firstname@email.com");
        testUserOne.setPassword("testPassword");

        // save user in repository through the userService
        userService.createUser(testUserOne);

        // define testuser
        testUserTwo = new User();
        testUserTwo.setFirstName("testTwo");
        testUserTwo.setLastName("name");
        testUserTwo.setUsername("testUsernameTwo");
        testUserTwo.setEmail("firstname@email.com");
        testUserTwo.setPassword("testPassword");

        // save user in repository through the userService
        userService.createUser(testUserTwo);
    }

    @AfterEach
    public void teardown() {
        // flush all the repositories
        duelRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createDuel_success() {
        Duel testDuel = new Duel();
        testDuel.setPlayerOneId(testUserOne.getId());
        testDuel.setPlayerTwoId(testUserTwo.getId());
        testDuel.setDeckId(1L);

        Duel createdDuel = duelService.createDuel(testDuel);

        assertEquals(testDuel.getPlayerOneId(), createdDuel.getPlayerOneId(), "Player One ID " + baseMsg);
        assertEquals(testDuel.getPlayerTwoId(), createdDuel.getPlayerTwoId(), "Player Two ID " + baseMsg);
    }

    @Test
    public void delDuel_success() {
        Duel testDuel = new Duel();
        testDuel.setPlayerOneId(testUserOne.getId());
        testDuel.setPlayerTwoId(testUserTwo.getId());
        testDuel.setDeckId(1L);

        Duel createdDuel = duelService.createDuel(testDuel);

        boolean success = duelService.deleteDuelById(createdDuel.getId());

        assertTrue(success);
    }

    @Test
    public void updatePlayerScore_getDuelById_success() {
        Duel testDuel = new Duel();
        testDuel.setPlayerOneId(testUserOne.getId());
        testDuel.setPlayerTwoId(testUserTwo.getId());
        testDuel.setDeckId(1L);

        Duel createdDuel = duelService.createDuel(testDuel);

        assertEquals(0, createdDuel.getPlayerOneScore(), "Player One Score is not 0");

        duelService.updatePlayerScore(createdDuel.getId(), testUserOne.getId(), 5);

        Duel foundDuel = duelService.getDuelById(createdDuel.getId());

        assertEquals(5, foundDuel.getPlayerOneScore(), "Player One Score is not 5");

        assertEquals(testDuel.getPlayerOneId(), createdDuel.getPlayerOneId(), "Player One ID " + baseMsg);
        assertEquals(testDuel.getPlayerTwoId(), createdDuel.getPlayerTwoId(), "Player Two ID " + baseMsg);
    }
}