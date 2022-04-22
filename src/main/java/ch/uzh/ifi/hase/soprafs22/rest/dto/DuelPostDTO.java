package ch.uzh.ifi.hase.soprafs22.rest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DuelPostDTO
 */
public class DuelPostDTO {

    @Getter
    @Setter
    private long playerOneId;

    @Getter
    @Setter
    private long deckId;

    @Getter
    @Setter
    private long playerTwoId;
}