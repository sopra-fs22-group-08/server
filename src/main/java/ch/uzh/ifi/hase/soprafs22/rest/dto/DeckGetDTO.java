package ch.uzh.ifi.hase.soprafs22.rest.dto;

import java.sql.Timestamp;

import ch.uzh.ifi.hase.soprafs22.constant.Visibility;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

public class DeckGetDTO {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String deckname;

    @Getter
    @Setter
    @CreationTimestamp
    private Timestamp creationdate;

    @Getter
    @Setter
    private Visibility visibility;
}