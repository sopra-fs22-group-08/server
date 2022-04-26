package ch.uzh.ifi.hase.soprafs22.rest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DuelGetDTO
 */
public class DuelGetDTO {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long deckId;

    @Getter
    @Setter
    private Long playerOneId;

    @Getter
    @Setter
    private Long playerTwoId;

    @Getter
    @Setter
    private Long playerOneScore;

    @Getter
    @Setter
    private Long playerTwoScore;
}