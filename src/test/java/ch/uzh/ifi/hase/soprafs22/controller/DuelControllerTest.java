package ch.uzh.ifi.hase.soprafs22.controller;

import static ch.uzh.ifi.hase.soprafs22.helpers.Utilities.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    public void createDuel_duelCreated() throws Exception {
        Duel duel = new Duel();
        duel.setId(1l);
        duel.setDeckId(1l);
        duel.setPlayerOneId(1l);
        duel.setPlayerTwoId(2l);

        DuelPostDTO duelPostDTO = new DuelPostDTO();
        duelPostDTO.setDeckId(1l);
        duelPostDTO.setPlayerOneId(1l);
        duelPostDTO.setPlayerTwoId(2l);

        given(duelService.createDuel(Mockito.any())).willReturn(duel);

        MockHttpServletRequestBuilder postRequest = post("/duels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(duelPostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(duel.getId().intValue())))
                .andExpect(jsonPath("$.deckId", is(duel.getDeckId().intValue())))
                .andExpect(jsonPath("$.playerOneId", is(duel.getPlayerOneId().intValue())))
                .andExpect(jsonPath("$.playerTwoId", is(duel.getPlayerTwoId().intValue())));

    }

    // @Test
    // public void deleteInvitation_isOk(){}
    //
    // @Test
    // public void getAllDuels_returnAsJsonArray(){}
    //
    // @Test
    // public void getDuelById_isOk(){}
    //
    // @Test
    // public void updateDuelStatus_isOk(){}
    //
    // @Test
    // public void updateDuelScore_isOk(){}

}