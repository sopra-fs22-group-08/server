package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * DuelPostDTO
 */
public class DuelPostDTO {

    @Getter
    @Setter
    private long playerOneId;
    // private User playerOne;

    @Getter
    @Setter
    private long playerTwoId;
    // private User playerTwo;
}