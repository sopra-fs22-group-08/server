package ch.uzh.ifi.hase.soprafs22.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.hase.soprafs22.entity.Invitation;

@Repository("invitationRepository")
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findInvitationByUserId(Long userId);
}