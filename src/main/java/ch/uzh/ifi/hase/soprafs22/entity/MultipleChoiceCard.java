package ch.uzh.ifi.hase.soprafs22.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

// BUG: Tried abstract class CARD but abstract class attributes get saved as null - don't know how to fix
@Entity
@Table(name = "MC_CARD")
public class MultipleChoiceCard implements Serializable {
    @Getter
    @Setter
    @Column(name = "question", nullable = false)
    private String question;

    @Getter
    @Setter
    @Column(name = "answer", nullable = false)
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
    @JoinColumn(name = "DECK_ID", nullable = false)
    @JsonIgnore
    private Deck deck;

    // Store 4 options to answer for this card
    @Getter
    @Setter
    @ElementCollection
    private List<String> options = new ArrayList<>();
}