package ch.uzh.ifi.hase.soprafs22.controller;

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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.rest.dto.MultipleChoiceCardPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.MCCardService;

/**
 * CardControllerTest
 */
@WebMvcTest(CardController.class)
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MCCardService mcCardService;

    Deck deck;

    @Test
    public void givenCards_whenGetCardsByDeckId_thenReturnJsonArray() throws Exception {
        List<String> options = new ArrayList<String>();
        options.add("test1");
        options.add("test2");
        options.add("test3");
        options.add("test4");

        MultipleChoiceCard card = new MultipleChoiceCard();
        card.setId(1l);
        card.setDeck(deck);
        card.setQuestion("which?");
        card.setAnswer("test1");
        card.setOptions(options);

        List<MultipleChoiceCard> cards = Collections.singletonList(card);

        given(mcCardService.getCardsByDeckId(Mockito.any()))
            .willReturn(cards);

        MockHttpServletRequestBuilder getRequest = get("/decks/1/cards")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(card.getId().intValue())))
                .andExpect(jsonPath("$[0].answer", is(card.getAnswer())))
                .andExpect(jsonPath("$[0].question", is(card.getQuestion())))
                .andExpect(jsonPath("$[0].options", is(card.getOptions())));
    }

    @Test
    public void createCard_validInputs_success() throws Exception {
        List<String> options = new ArrayList<String>();
        options.add("test1");
        options.add("test2");
        options.add("test3");
        options.add("test4");

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

        MockHttpServletRequestBuilder postRequest = post("/decks/1/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mChoiceCardPostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(card.getId().intValue())))
                .andExpect(jsonPath("$.answer", is(card.getAnswer())))
                .andExpect(jsonPath("$.question", is(card.getQuestion())))
                .andExpect(jsonPath("$.options", is(card.getOptions())));
    }
}