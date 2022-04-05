package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername_success() {
        // given
        User user = new User();
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setUsername("firstname@lastname");
        user.setEmail("firstname@email.com");
        user.setPassword("12345");
        user.setStatus(UserStatus.ONLINE);
        user.setToken("1");

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByUsername(user.getUsername());

        // then
        assertNotNull(found.getId());
        assertEquals(found.getFirstName(), user.getFirstName());
        assertEquals(found.getLastName(), user.getLastName());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getStatus(), user.getStatus());
    }

    @Test
    public void findByFirstAndLastname_success() {
        // given
        User user = new User();
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setUsername("firstname@lastname");
        user.setEmail("firstname@email.com");
        user.setPassword("12345");
        user.setStatus(UserStatus.ONLINE);
        user.setToken("1");

        User user2 = new User();
        user2.setFirstName("Firstname");
        user2.setLastName("Lastname2");
        user2.setUsername("firstname@lastname2");
        user2.setEmail("firstname2@email.com");
        user2.setPassword("12345");
        user2.setStatus(UserStatus.ONLINE);
        user2.setToken("2");

        entityManager.persist(user);
        entityManager.flush();

        entityManager.persist(user2);
        entityManager.flush();

        // when
        User found = userRepository.findByFirstNameAndLastName(user.getFirstName(), user.getLastName());

        // then
        assertNotNull(found.getId());
        assertEquals(found.getFirstName(), user.getFirstName());
        assertEquals(found.getLastName(), user.getLastName());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getStatus(), user.getStatus());
    }
}