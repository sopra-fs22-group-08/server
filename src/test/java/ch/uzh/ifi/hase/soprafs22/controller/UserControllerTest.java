package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ch.uzh.ifi.hase.soprafs22.helpers.Utilities.asJsonString;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        // given
        User user = new User();
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setUsername("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);

        List<User> allUsers = Collections.singletonList(user);

        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(userService.getUsers()).willReturn(allUsers);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(user.getLastName())))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].status", is(user.getStatus().toString())));
    }

    /*
     * @brief test GET with valid User 1
     */
    @Test
    public void getUserOne_isOk() throws Exception {
        User user = new User();
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setUsername("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);

        given(userService.getUserbyID(1)).willReturn(user);

        MockHttpServletRequestBuilder getRequest = get("/users/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andExpect(status().isOk());
    }

    /*
     * @brief test GET with invalid User 1
     */
    @Test
    public void getUserOne_notFound() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/users/1")
                .contentType(MediaType.APPLICATION_JSON);
        given(userService.getUserbyID(1))
                .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mockMvc.perform(getRequest).andExpect(status().isNotFound());
    }

    /*
     * @brief test POST with valid User
     */
    @Test
    public void createUser_validInput_userCreated() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.ONLINE);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setFirstName("Test");
        userPostDTO.setLastName("User");
        userPostDTO.setUsername("testUsername");

        given(userService.createUser(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())));
    }

    /*
     * @brief test POST with invalid User
     */
    @Test
    public void createUser_invalidInput_userConflicted() throws Exception {
        // simulate second user

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Test");
        user2.setLastName("User");
        user2.setUsername("testUsername");
        user2.setToken("2");
        user2.setStatus(UserStatus.ONLINE);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setFirstName("Test");
        userPostDTO.setLastName("User");
        userPostDTO.setUsername("testUsername");

        given(userService.createUser(Mockito.any()))
                .willThrow(new ResponseStatusException(HttpStatus.CONFLICT));

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest2 = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        mockMvc.perform(postRequest2)
                .andExpect(status().isConflict());
    }

    /*
     * @brief test PUT with valid User
     */
    @Test
    public void updateUser_validInput_noContent() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUsername("updatedUser");
        user.setBirthday("1999-01-01");
        user.setToken("1");
        user.setStatus(UserStatus.ONLINE);

        given(userService.createUser(Mockito.any())).willReturn(user);

        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setUsername("updatedUser");
        userPutDTO.setBirthday("1999-01-01");

        MockHttpServletRequestBuilder putRequest = put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutDTO));

        given(userService.updateUser(Mockito.any(), Mockito.any()))
                .willReturn(user);

        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());
    }
}