package ch.uzh.ifi.hase.soprafs22.rest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DuelPostDTO
 */
public class DuelPostDTO {

    @Getter
    @Setter
    private Long playerOneId;

    @Getter
    @Setter
    private Long deckId;

    @Getter
    @Setter
    private Long playerTwoId;
}