package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetDTO> getAllUsers() {
        // fetch all users in the internal representation
        List<User> users = userService.getUsers();
        List<UserGetDTO> userGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (User user : users) {
            userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOs;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // create user
        User createdUser = userService.createUser(userInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
    }

    /**
     * @brief retrieves a single User based on his ID
     *        GET REQUEST: Status Code OK 200. IF fail Status Code -> 404 -> Not
     *        Found
     */
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO getUser(@PathVariable("userId") long userId) {
        User user = userService.getUserbyID(userId);
        UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
        return userGetDTO;
    }

    /**
     * @brief updates the User -> PutMapping
     *        PUT REQUEST: Status Code 204 -> NO_CONTENT, Error: Status Code = 404
     *        -> NOT FOUND
     */
    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updateUser(@PathVariable("userId") long userId, @RequestBody UserPutDTO userPutDTO) {
        User currentUser = userService.getUserbyID(userId);
        User inputUser = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);
        userService.updateUser(currentUser, inputUser);
    }

    /**
     * @brief logs the user in
     * @return UserGetDTO
     *         PUT MAPPING: Http.status code = 200 OK. Error => HTTP Status Code:
     *         NOT_Found
     */
    @PutMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO checkLogin(@RequestBody UserPutDTO userPutDTO) {
        User userInput = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);
        User user = userService.checkLoginData(userInput);
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
    }

    /**
     * @brief logs the user out and sets it offline (User.Status = OFFLINE)
     * @return UserGetDTO
     *         PUT MAPPING: HTTP.Status code = 200 OK
     */
    @PutMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO setUserOffline(@RequestBody UserPutDTO userPutDTO) {
        User userInput = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);
        User logoutUser = userService.setOffline(userInput);
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(logoutUser);
    }

    /**
     * @brief sends verification email to user -> PutMapping
                PUT MAPPING: HTTP.Status code = 200 OK
     */
    @PutMapping("/users/{userId}/verification")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void sendVerification(@PathVariable("userId") long userId) {
        User toVerify = userService.getUserbyID(userId);
        userService.sendVerificationMail(toVerify.getEmail());
    }

}