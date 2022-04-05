package ch.uzh.ifi.hase.soprafs22.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


public abstract class Card {


    @Getter
    @Setter
    @Column(name="question", nullable = false)
    private String question;

    @Getter
    @Setter
    @Column(name="question", nullable = false)
    private String answer;
}
