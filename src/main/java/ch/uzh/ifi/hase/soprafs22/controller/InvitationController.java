package ch.uzh.ifi.hase.soprafs22.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.hase.soprafs22.entity.Invitation;
import ch.uzh.ifi.hase.soprafs22.rest.dto.InvitationGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.InvitationPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.InvitationService;

@RestController
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @GetMapping("/users/{userId}/invitations")
    @ResponseStatus(HttpStatus.OK)
    public List<InvitationGetDTO> getInvitationsByUserId(@PathVariable("userId") long userId) {
        // fetch all cards of deck in the internal representation
        List<Invitation> invitationsToBeReturned = invitationService.getInvitationsByUserId(userId);
        List<InvitationGetDTO> invitationGetDTOS = new ArrayList<>();
        // convert internal representation deck to API deck
        for (Invitation invitation : invitationsToBeReturned) {
            invitationGetDTOS.add(DTOMapper.INSTANCE.convertEntityToInvitationGetDTO(invitation));
        }
        return invitationGetDTOS;
    }

    // TODO: unnecessary receiverId in invitationPostDTO can be changed
    @PostMapping("/users/{receiverId}/invitation")
    @ResponseStatus(HttpStatus.CREATED)
    public Invitation createInvitation(
            @PathVariable("receiverId") Long receiverId,
            @RequestBody InvitationPostDTO invitationPostDTO) {
        // convert API invitation to internal representation
        Invitation invitationRequest = DTOMapper.INSTANCE.convertInvitationPostDTOToEntity(invitationPostDTO);
        Invitation invitation = invitationService.createInvitation(receiverId, invitationRequest);
        return invitation;
    }

    @DeleteMapping("/invitations/{invitationId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInvitation(@PathVariable(value = "invitationId") long invitationId) {
        invitationService.deleteInvitationById(invitationId);
    }

}