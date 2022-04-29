package ch.uzh.ifi.hase.soprafs22.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.hase.soprafs22.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByFirstNameAndLastName(String firstName, String lastName);

    User findById(long id);

    User findByUsername(String username);

    User findByToken(String token);
}