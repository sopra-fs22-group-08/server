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

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Invitation;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.InvitationPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.InvitationService;

/**
 * InvitationControllerTest
 */
@WebMvcTest(InvitationController.class)
public class InvitationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvitationService invitationService;

    @Test
    public void createInvitation_successCreated() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.ONLINE);
        //
        // Duel duel = new Duel();
        // duel.setId(1l);
        // duel.setDeckId(1l);
        // duel.setPlayerOneId(1l);
        // duel.setPlayerTwoId(2l);
        //
        // Deck deck = new Deck();
        // deck.setId(1L);
        // deck.setUser(user);
        // deck.setDeckname("testdeck");

        Invitation invite = new Invitation();
        invite.setId(1l);
        invite.setUser(user);
        invite.setDuelId(1l);
        invite.setDeckId(1l);
        invite.setDeckname("testdeck");
        invite.setReceiverId(2l);
        invite.setSenderId(1l);
        invite.setSenderUsername(user.getUsername());
        invite.setReceiverUsername("receiverUsername");

        given(invitationService.createInvitation(Mockito.any(), Mockito.any()))
                .willReturn(invite);

        InvitationPostDTO invitationPostDTO = new InvitationPostDTO();
        invitationPostDTO.setSenderId(1l);
        invitationPostDTO.setSenderUsername(user.getUsername());
        invitationPostDTO.setReceiverId(2l);
        invitationPostDTO.setSenderUsername("receiverUsername");
        invitationPostDTO.setDuelId(1l);
        invitationPostDTO.setDeckId(1l);
        invitationPostDTO.setDeckname("testdeck");

        MockHttpServletRequestBuilder postRequest = post("/users/2/invitation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invitationPostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(invite.getId().intValue())))
                .andExpect(jsonPath("$.senderId", is(invite.getSenderId().intValue())))
                .andExpect(jsonPath("$.receiverId", is(invite.getReceiverId().intValue())))
                .andExpect(jsonPath("$.deckId", is(invite.getDeckId().intValue())))
                .andExpect(jsonPath("$.deckname", is(invite.getDeckname())))
                .andExpect(jsonPath("$.duelId", is(invite.getDuelId().intValue())))
                .andExpect(jsonPath("$.senderUsername", is(invite.getSenderUsername())))
                .andExpect(jsonPath("$.receiverUsername", is(invite.getReceiverUsername())));
    }

}