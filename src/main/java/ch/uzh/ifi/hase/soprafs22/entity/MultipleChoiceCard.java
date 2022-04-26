package ch.uzh.ifi.hase.soprafs22.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
// BUG: Tried abstract class CARD but abstract class attributes get saved as null - don't know how to fix
@Entity
@Table(name="MC_CARD")
public class MultipleChoiceCard implements Serializable {
    @Getter
    @Setter
    @Column(name="question", nullable = false)
    private String question;

    @Getter
    @Setter
    @Column(name="answer", nullable = false)
    private String answer;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name= "DECK_ID", nullable = false)
    @JsonIgnore
    private Deck deck;


    //Store 4 options to answer for this card
    @Getter
    @Setter
    @ElementCollection
    private List<String> options;
}