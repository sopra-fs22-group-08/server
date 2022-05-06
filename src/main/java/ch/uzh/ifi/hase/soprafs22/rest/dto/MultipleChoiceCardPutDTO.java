package ch.uzh.ifi.hase.soprafs22.rest.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * MultipleChoiceCardPutDTO
 */
public class MultipleChoiceCardPutDTO {

    @Getter
    @Setter
    private String question;

    @Getter
    @Setter
    private String answer;

    @Getter
    @Setter
    private List<String> options;
}