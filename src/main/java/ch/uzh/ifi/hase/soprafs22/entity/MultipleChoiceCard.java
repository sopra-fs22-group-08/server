package ch.uzh.ifi.hase.soprafs22.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="MC_CARD")
public class MultipleChoiceCard extends Card implements Serializable {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long cardId;

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
    private List<String> options = new ArrayList<>();
}
