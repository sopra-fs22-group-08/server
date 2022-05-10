package ch.uzh.ifi.hase.soprafs22.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

/**
 * @brief User Service
 *        This class is the "worker" and responsible for all functionality
 *        related to
 *        the user
 *        (e.g., it creates, modifies, deletes, finds). The result will be
 *        passed back
 *        to the caller.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);

        checkIfUserExists(newUser);

        // saves the given entity but data is only persisted in the database once
        // flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    /**
     * @brief updates the old User with the new Username or the new Birthday
     *        added
     */
    public User updateUser(User currentUser, User userInput) {
        if (userInput.getUsername() == null) {
            String baseErrorMessage = "You cannot choose an empty Username!";
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage));
        }
        if (userInput.getUsername() != null) {
            User databaseUser = userRepository.findByUsername(userInput.getUsername());
            if (databaseUser != null && currentUser.getUsername() != databaseUser.getUsername()) {
                String baseErrorMessage = "You cannot choose this Username. It has already been taken!";
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage));
            } else {
                currentUser.setUsername(userInput.getUsername());
            }
        }
        if (userInput.getBirthday() != null) {
            currentUser.setBirthday(userInput.getBirthday());
        }
        userRepository.save(currentUser);
        userRepository.flush();
        return currentUser;
    }

    /**
     * @brief checks whether user exists
     * @throws org.springframework.web.server.ResponseStatusException
     * @returns current User
     */
    public User checkLoginData(User user) {
        String errorMessage;
        User currUser = userRepository.findByUsername(user.getUsername());
        // check name
        if (currUser == null) {
            errorMessage = "Username doesn't exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(errorMessage));
        } else {
            String testPassword = user.getPassword();
            String currPassword = currUser.getPassword();
            // check pw
            if (!currPassword.equals(testPassword)) {
                errorMessage = "Password is incorrect";
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, errorMessage);
                // set online
            } else {
                currUser.setStatus(UserStatus.ONLINE);
                userRepository.save(currUser);
                userRepository.flush();
                return currUser;
            }
        }
    }

    /**
     * @returns user if found, otherwise throws error
     */
    public User getUserbyID(long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            String baseErrorMessage = "The user with this id does not exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage));
        } else {
            return user;
        }
    }

    /**
     * @brief logs out the User and sets it to offline
     */
    public User setOffline(User user) {
        // long id = user.getId();
        // User logoutUser = userRepository.findById(id);
        String userName = user.getUsername();
        User logoutUser = userRepository.findByUsername(userName);
        logoutUser.setStatus(UserStatus.OFFLINE);
        userRepository.save(logoutUser);
        userRepository.flush();
        return logoutUser;
    }

    /**
     * This is a helper method that will check the uniqueness criteria of the
     * username and the name, the name as a combination of first and last name,
     * defined in the User entity. The method will do nothing if the input is unique
     * and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws org.springframework.web.server.ResponseStatusException
     * @see User
     */
    // TODO: Change Status Code to 409 CONFLICT
    private void checkIfUserExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
        User userByFirstNameAndLastName = userRepository.findByFirstNameAndLastName(userToBeCreated.getFirstName(),
                userToBeCreated.getLastName());
        String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";

        if (userByUsername != null && userByFirstNameAndLastName != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage, "username and the name", "are"));
        } else if (userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage, "username", "is"));
        } else if (userByFirstNameAndLastName != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage, "combination of first and last name", "is"));
        }
    }

    /**
     * This is a helper method that will send an account verification mail to the
     * defined email address of the newly created user.
     *
     * @param toAddress
     * @throws org.springframework.web.server.ResponseStatusException
     * @see User
     */
    public void sendVerificationMail(String toAddress) {
        Email from = new Email("noreply@no-brainer.ch");
        String subject = "Welcome to No Brainer";
        Email to = new Email(toAddress);
        Content content = new Content("text/plain", " - The last learning app you'll ever need!");
        Mail mail = new Mail(from, subject, to, content);

        String emailToken;
        try {
            // if there is an env file, it can be loaded
            Dotenv dotenv = Dotenv.load();
            emailToken = dotenv.get("EMAIL_TOKEN");
        } catch (DotenvException e) {
            // this is where we are with no .env file
            emailToken = System.getenv("EMAIL_TOKEN");
        }
        SendGrid sg = new SendGrid(emailToken);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            // System.out.println(response.getStatusCode());
            // System.out.println(response.getBody());
            // System.out.println(response.getHeaders());
        } catch (IOException ex) {
            String baseErrorMessage = "An error while sending the mail occurred";
            log.error(baseErrorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage));
        }
        System.out.println("Mail sent successfully to: " + toAddress.toString());
    }

}