package ch.uzh.ifi.hase.soprafs22.rest.dto;

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
    private long deckId;

    @Getter
    @Setter
    private long playerOneId;

    @Getter
    @Setter
    private long playerTwoId;

    @Getter
    @Setter
    private long playerOneScore;

    @Getter
    @Setter
    private long playerTwoScore;
}