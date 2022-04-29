package ch.uzh.ifi.hase.soprafs22.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.entity.Invitation;
import ch.uzh.ifi.hase.soprafs22.repository.DuelRepository;
import ch.uzh.ifi.hase.soprafs22.repository.InvitationRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;

@Service
@Transactional
public class InvitationService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final InvitationRepository invitationRepository;

    public InvitationService(DuelRepository duelRepository, UserRepository userRepository,
            InvitationRepository invitationRepository) {
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
    }

    public Invitation createInvitation(Long receiverId, Invitation invitationRequest) {
        // create deck and save to deck repository
        Invitation invitationToBeReturned = this.userRepository.findById(receiverId).map(receiver -> {
            invitationRequest.setUser(receiver);
            return invitationRepository.save(invitationRequest);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User with ID" + receiverId + " has not been found"));
        return invitationToBeReturned;
    }

    public List<Invitation> getInvitationsByUserId(long userId) {
        // check for existence of user
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not find with this id: " + userId);
        }
        // fetch all decks of user in the internal representation
        List<Invitation> invitationsToBeReturned = this.invitationRepository.findInvitationByUserId(userId);
        return invitationsToBeReturned;
    }

    public void deleteInvitationById(long invitationId) {
        try {
            invitationRepository.deleteById(invitationId);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Invitation with ID " + invitationId + " does NOT exist");
        }
    }
}