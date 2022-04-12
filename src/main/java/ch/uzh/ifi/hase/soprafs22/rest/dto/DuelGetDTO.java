package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * DuelGetDTO
 */
public class DuelGetDTO {
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long playerOneId;
    // private User playerOne;

    @Getter
    @Setter
    private long playerTwoId;
    // private User playerTwo;

    @Getter
    @Setter
    private long playerOneScore;

    @Getter
    @Setter
    private long playerTwoScore;
}