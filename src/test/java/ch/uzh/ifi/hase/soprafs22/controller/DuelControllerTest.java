package ch.uzh.ifi.hase.soprafs22.controller;

import static ch.uzh.ifi.hase.soprafs22.helpers.Utilities.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import ch.uzh.ifi.hase.soprafs22.entity.Duel;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DuelPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.DuelService;

/**
 * DuelController
 */
@WebMvcTest(DuelController.class)
public class DuelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DuelService duelService;

    private Duel testDuel;

    @BeforeEach
    public void setup() {
        testDuel = new Duel();
        testDuel.setId(1l);
        testDuel.setDeckId(1l);
        testDuel.setPlayerOneId(1l);
        testDuel.setPlayerTwoId(2l);
    }

    @Test
    public void createDuel_duelCreated() throws Exception {

        DuelPostDTO duelPostDTO = new DuelPostDTO();
        duelPostDTO.setDeckId(1l);
        duelPostDTO.setPlayerOneId(1l);
        duelPostDTO.setPlayerTwoId(2l);

        given(duelService.createDuel(Mockito.any())).willReturn(testDuel);

        MockHttpServletRequestBuilder postRequest = post("/duels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(duelPostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(testDuel.getId().intValue())))
                .andExpect(jsonPath("$.deckId", is(testDuel.getDeckId().intValue())))
                .andExpect(jsonPath("$.playerOneId", is(testDuel.getPlayerOneId().intValue())))
                .andExpect(jsonPath("$.playerTwoId", is(testDuel.getPlayerTwoId().intValue())));

    }

    @Test
    public void getAllDuels_isOk() throws Exception {

        List<Duel> duels = Collections.singletonList(testDuel);
        given(duelService.getDuels()).willReturn(duels);

        MockHttpServletRequestBuilder getRequest = get("/duels")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(testDuel.getId().intValue())));
    }

    @Test
    public void getDuelsById_isOk() throws Exception {

        given(duelService.getDuelById(Mockito.anyLong()))
            .willReturn(testDuel);

        MockHttpServletRequestBuilder getRequest = get("/duels/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testDuel));

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testDuel.getId().intValue())));
    }

    // @Test
    // public void deleteInvitation_isOk(){}
    //
    // @Test
    // public void updateDuelStatus_isOk(){}
    //
    // @Test
    // public void updateDuelScore_isOk(){}

}