package ch.uzh.ifi.hase.soprafs22.controller;

import static ch.uzh.ifi.hase.soprafs22.helpers.Utilities.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import ch.uzh.ifi.hase.soprafs22.constant.Visibility;
import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DeckPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.DeckService;

/**
 * DeckControllerTest
 */
@WebMvcTest(DeckController.class)
public class DeckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeckService deckService;

    // could also mock this, but works as is
    private User user;

    @Test
    public void givenDeck_whenGetDecks_thenReturnJsonArray() throws Exception {
        Deck deck = new Deck();
        deck.setId(1L);
        deck.setUser(user);
        deck.setDeckname("testdeck");

        List<Deck> allDecks = Collections.singletonList(deck);

        given(deckService.getDecks()).willReturn(allDecks);

        MockHttpServletRequestBuilder getRequest = get("/decks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(deck.getId().intValue())))
                .andExpect(jsonPath("$[0].deckname", is(deck.getDeckname())));
    }

    @Test
    public void givenDeck_whenGetDecksByUserId_thenReturnJsonArray() throws Exception {
        Deck deck = new Deck();
        deck.setId(1L);
        deck.setUser(user);
        deck.setDeckname("testdeck");

        List<Deck> allDecks = Collections.singletonList(deck);

        given(deckService.getDecksByUserId(1))
                .willReturn(allDecks);

        MockHttpServletRequestBuilder getRequest = get("/users/1/decks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(deck.getId().intValue())))
                .andExpect(jsonPath("$[0].deckname", is(deck.getDeckname())));
    }

    @Test
    public void givenDeck_whenGetDecksById_thenReturnDeck_success() throws Exception {
        Deck deck = new Deck();
        deck.setId(1L);
        deck.setUser(user);
        deck.setDeckname("testdeck");

        given(deckService.getDeckById(1L)).willReturn(deck);

        MockHttpServletRequestBuilder getRequest = get("/decks/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void createDeck_validInputs_success() throws Exception {
        Deck deck = new Deck();
        deck.setId(1L);
        deck.setUser(user);
        deck.setDeckname("testdeck");

        given(deckService.createDeck(Mockito.any(), Mockito.any()))
                .willReturn(deck);

        DeckPostDTO deckPostDTO = new DeckPostDTO();
        deckPostDTO.setDeckname("testdeck");

        MockHttpServletRequestBuilder postRequest = post("/users/1/decks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(deckPostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(deck.getId().intValue())))
                .andExpect(jsonPath("$.deckname", is(deck.getDeckname())));
    }

    @Test
    public void getDecksByVisibility_isOk() throws Exception {
        Deck deck = new Deck();
        deck.setId(1L);
        deck.setUser(user);
        deck.setDeckname("testdeck");
        deck.setVisibility(Visibility.PUBLIC);

        List<Deck> allDecks = Collections.singletonList(deck);

        given(deckService.getDecksByVisibility(Visibility.PUBLIC))
                .willReturn(allDecks);

        MockHttpServletRequestBuilder getRequest = get("/decks/visibility/PUBLIC")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(deck.getId().intValue())))
                .andExpect(jsonPath("$[0].deckname", is(deck.getDeckname())));

    }

    @Test
    public void getDecksByFuzzyFind_isOk() throws Exception {
        Deck deck = new Deck();
        deck.setId(1L);
        deck.setUser(user);
        deck.setDeckname("testdeck");
        deck.setVisibility(Visibility.PUBLIC);

        List<Deck> allDecks = Collections.singletonList(deck);

        given(deckService.getDecksByFuzzyFind("te"))
                .willReturn(allDecks);

        MockHttpServletRequestBuilder getRequest = get("/decks/search/te")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(deck.getId().intValue())))
                .andExpect(jsonPath("$[0].deckname", is(deck.getDeckname())));

    }
}