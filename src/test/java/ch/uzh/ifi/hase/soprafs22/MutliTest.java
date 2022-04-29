package ch.uzh.ifi.hase.soprafs22;

import static ch.uzh.ifi.hase.soprafs22.helpers.Utilities.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DeckPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.MultipleChoiceCardPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.DeckService;
import ch.uzh.ifi.hase.soprafs22.service.MCCardService;
import ch.uzh.ifi.hase.soprafs22.service.UserService;

/**
 * MultiTest
 */
@SpringBootTest
@AutoConfigureMockMvc
public class MutliTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private DeckService deckService;

    @MockBean
    private MCCardService mcCardService;

    /**
     * @brief This test is meant to be a complex (unit) test:
     *        Register User -> User creates Deck -> User creates Card -> User
     *        requests deck
     */
    @Test
    public void registerUser_createDeck_createCards_requestDeck() throws Exception {
        // create user
        // given
        User user = new User();
        user.setId(1l);
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setUsername("firstname_lastname");
        user.setEmail("firstname@lastname.com");
        user.setStatus(UserStatus.ONLINE);
        user.setToken("1");

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setFirstName("Test");
        userPostDTO.setLastName("User");
        userPostDTO.setUsername("testUsername");

        given(userService.createUser(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postUserRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postUserRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())));

        // now create a deck
        Deck deck = new Deck();
        deck.setId(1L);
        deck.setUser(user);
        deck.setDeckname("testdeck");

        given(deckService.createDeck(Mockito.any(), Mockito.any()))
                .willReturn(deck);

        DeckPostDTO deckPostDTO = new DeckPostDTO();
        deckPostDTO.setDeckname("testdeck");

        MockHttpServletRequestBuilder postDeckRequest = post("/users/1/decks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(deckPostDTO));

        mockMvc.perform(postDeckRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(deck.getId().intValue())))
                .andExpect(jsonPath("$.deckname", is(deck.getDeckname())));

        // now create cards
        List<String> options = new ArrayList<String>();
        options.add("test1");
        options.add("test2");
        options.add("test3");
        options.add("test4");
        // assertTrue(options.size() < 5);

        MultipleChoiceCard card = new MultipleChoiceCard();
        card.setId(1l);
        card.setDeck(deck);
        card.setQuestion("which?");
        card.setAnswer("test1");
        card.setOptions(options);

        given(mcCardService.createMCCard(Mockito.any(), Mockito.any()))
                .willReturn(card);

        MultipleChoiceCardPostDTO mChoiceCardPostDTO = new MultipleChoiceCardPostDTO();
        mChoiceCardPostDTO.setQuestion("which?");
        mChoiceCardPostDTO.setAnswer("test1");
        mChoiceCardPostDTO.setOptions(options);

        MockHttpServletRequestBuilder postCardRequest = post("/decks/1/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mChoiceCardPostDTO));

        mockMvc.perform(postCardRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(card.getId().intValue())))
                .andExpect(jsonPath("$.answer", is(card.getAnswer())))
                .andExpect(jsonPath("$.question", is(card.getQuestion())))
                .andExpect(jsonPath("$.options", is(card.getOptions())));

        // get deck beck as json array
        List<Deck> allDecks = Collections.singletonList(deck);

        given(deckService.getDecksByUserId(1))
                .willReturn(allDecks);

        MockHttpServletRequestBuilder getDeckRequest = get("/users/1/decks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getDeckRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(deck.getId().intValue())))
                .andExpect(jsonPath("$[0].deckname", is(deck.getDeckname())));
    }

}