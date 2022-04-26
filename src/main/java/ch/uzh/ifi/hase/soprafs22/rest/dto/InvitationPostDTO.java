package ch.uzh.ifi.hase.soprafs22.rest.dto;

import lombok.Getter;
import lombok.Setter;

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

}
