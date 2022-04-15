package ch.uzh.ifi.hase.soprafs22.rest.dto;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

public class DeckGetDTO {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String deckname;

    @Getter
    @Setter
    private Timestamp creationdate;
}
