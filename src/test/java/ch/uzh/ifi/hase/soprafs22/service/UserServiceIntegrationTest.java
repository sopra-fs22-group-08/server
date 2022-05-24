package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@SpringBootTest
public class UserServiceIntegrationTest {

    final String baseMsg = "does not match!";

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    public User setupCreateUser() {
        User testUser = new User();
        testUser.setFirstName("test");
        testUser.setLastName("name");
        testUser.setUsername("testUsername");
        testUser.setEmail("firstname@email.com");
        testUser.setPassword("testPassword");

        return testUser;
    }

    @Test
    public void createUser_validInputs_success() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = setupCreateUser();

        // when
        User createdUser = userService.createUser(testUser);

        // then
        assertEquals(testUser.getId(), createdUser.getId());
        assertEquals(testUser.getFirstName(), createdUser.getFirstName());
        assertEquals(testUser.getLastName(), createdUser.getLastName());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertNotNull(createdUser.getToken());
        assertEquals(UserStatus.ONLINE, createdUser.getStatus());
    }

    @Test
    public void createUser_duplicateUsername_throwsException() {
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setFirstName("test");
        testUser.setLastName("name");
        testUser.setUsername("testUsername");
        testUser.setPassword("testPassword");
        testUser.setEmail("testName@email.com");
        userService.createUser(testUser);

        // attempt to create second user with same username
        User testUser2 = new User();

        // change the name but forget about the username
        testUser.setFirstName("test");
        testUser.setLastName("name2");
        testUser2.setUsername("testUsername");
        testUser2.setPassword("testPassword");
        testUser2.setEmail("testName2@email.com");

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser2));
    }

    @Test
    public void updateUser_success() {

        // setup
        User testUser = setupCreateUser();
        userService.createUser(testUser);

        User updatedUser = new User();
        updatedUser.setFirstName("newFirstName");
        updatedUser.setLastName("newLastname");
        updatedUser.setUsername("newUsername");
        updatedUser.setStatus(UserStatus.OFFLINE);

        userService.updateUser(testUser, updatedUser);

        assertEquals(updatedUser.getUsername(), testUser.getUsername(), "Username " + baseMsg);
        assertEquals(updatedUser.getFirstName(), testUser.getFirstName(), "FirstName " + baseMsg);
        assertEquals(updatedUser.getLastName(), testUser.getLastName(), "LastName " + baseMsg);
        assertEquals(updatedUser.getStatus(), testUser.getStatus(), "Status " + baseMsg);
    }

    @Test
    public void checkLoginData_success() {
        // setup
        User testUser = setupCreateUser();
        userService.createUser(testUser);

        User updatedUser = userService.checkLoginData(testUser);

        assertEquals(updatedUser.getUsername(), testUser.getUsername(), "Username " + baseMsg);
        assertEquals(updatedUser.getFirstName(), testUser.getFirstName(), "FirstName " + baseMsg);
        assertEquals(updatedUser.getLastName(), testUser.getLastName(), "LastName " + baseMsg);
        assertEquals(updatedUser.getStatus(), testUser.getStatus(), "Status " + baseMsg);
    }

    @Test
    public void getUserById_success() {
        // setup
        User testUser = setupCreateUser();
        userService.createUser(testUser);

        User updatedUser = userService.getUserbyID(testUser.getId());

        assertEquals(updatedUser.getUsername(), testUser.getUsername(), "Username " + baseMsg);
        assertEquals(updatedUser.getFirstName(), testUser.getFirstName(), "FirstName " + baseMsg);
        assertEquals(updatedUser.getLastName(), testUser.getLastName(), "LastName " + baseMsg);
        assertEquals(updatedUser.getStatus(), testUser.getStatus(), "Status " + baseMsg);
    }

    @Test
    public void setUserOffline_success() {
        // setup
        User testUser = setupCreateUser();
        userService.createUser(testUser);

        User updatedUser = userService.setOffline(testUser);

        assertEquals(UserStatus.OFFLINE, updatedUser.getStatus(), "Status " + baseMsg);
    }

    @Test
    public void sendMail_success() {
        String testmail = "senseiberesu@gmail.com";
        boolean success = userService.sendVerificationMail(testmail);
        assertTrue(success);
    }
}