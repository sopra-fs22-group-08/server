package ch.uzh.ifi.hase.soprafs22.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

public class InvitationPostDTO {
    @Getter
    @Setter
    private Long senderId;

    @Getter
    @Setter
    private Long receiverId;

    @Getter
    @Setter
    private Long duelId;

    @Getter
    @Setter
    private Long deckId;

    @Getter
    @Setter
    private String deckname;

    @Getter
    @Setter
    private String senderUsername;

    @Getter
    @Setter
    private String receiverUsername;

}
