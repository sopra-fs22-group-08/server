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

import ch.uzh.ifi.hase.soprafs22.entity.Invitation;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.InvitationRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;

/**
 * InvitationServiceIntegrationTest
 */
@WebAppConfiguration
@SpringBootTest
public class InvitationServiceIntegrationTest {

    @Qualifier("invitationRepository")
    @Autowired
    private InvitationRepository invitationRepository;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private UserService userService;

    private User testUserOne;
    private User testUserTwo;
    final String baseMsg = "does not match!";

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
        invitationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createInvitation_success() {
        Invitation testInvitation = new Invitation();
        testInvitation.setSenderId(testUserOne.getId());
        testInvitation.setSenderUsername(testUserOne.getUsername());
        testInvitation.setReceiverId(testUserTwo.getId());
        testInvitation.setReceiverUsername(testUserTwo.getUsername());
        testInvitation.setDeckId(1L);
        testInvitation.setDeckname("testOne");
        testInvitation.setDuelId(1L);
        testInvitation.setUser(testUserOne);

        Invitation createdInvite = invitationService.createInvitation(testUserTwo.getId(), testInvitation);

        assertEquals(testInvitation.getUser(), createdInvite.getUser(), "User " + baseMsg);
    }

    @Test
    public void getInviteByUserId_success() {
        Invitation testInvitation = new Invitation();
        testInvitation.setSenderId(testUserOne.getId());
        testInvitation.setSenderUsername(testUserOne.getUsername());
        testInvitation.setReceiverId(testUserTwo.getId());
        testInvitation.setReceiverUsername(testUserTwo.getUsername());
        testInvitation.setDeckId(1L);
        testInvitation.setDeckname("testOne");
        testInvitation.setDuelId(1L);
        testInvitation.setUser(testUserOne);

        Invitation createdInvite = invitationService.createInvitation(testUserTwo.getId(), testInvitation);
        List<Invitation> foundInvites = invitationService.getInvitationsByUserId(testUserTwo.getId());

        assertEquals(createdInvite.getDuelId(), foundInvites.get(0).getDuelId(), "Duel ID " + baseMsg);
    }

    @Test
    public void delInvite_success() {
        Invitation testInvitation = new Invitation();
        testInvitation.setSenderId(testUserOne.getId());
        testInvitation.setSenderUsername(testUserOne.getUsername());
        testInvitation.setReceiverId(testUserTwo.getId());
        testInvitation.setReceiverUsername(testUserTwo.getUsername());
        testInvitation.setDeckId(1L);
        testInvitation.setDeckname("testOne");
        testInvitation.setDuelId(1L);
        testInvitation.setUser(testUserOne);
        invitationService.createInvitation(testUserTwo.getId(), testInvitation);

        boolean success = invitationService.deleteInvitationById(testInvitation.getId());

        assertTrue(success);
    }
}