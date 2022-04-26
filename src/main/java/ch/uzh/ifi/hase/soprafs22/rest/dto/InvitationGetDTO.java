package ch.uzh.ifi.hase.soprafs22.rest.dto;

import lombok.Getter;
import lombok.Setter;

public class InvitationGetDTO {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long senderId;

    @Getter
    @Setter
    private Long receiverId;

    @Getter
    @Setter
    private Long duelId;
}
