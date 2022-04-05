package ch.uzh.ifi.hase.soprafs22.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MultipleChoiceCardPostDTO {
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
