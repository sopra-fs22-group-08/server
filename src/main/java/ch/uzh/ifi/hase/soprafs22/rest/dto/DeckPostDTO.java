package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.Visibility;
import lombok.Getter;
import lombok.Setter;

public class DeckPostDTO {

    @Getter
    @Setter
    private String deckname;

    @Getter
    @Setter
    private Visibility visibility;
}
