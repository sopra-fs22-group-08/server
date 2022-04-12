package ch.uzh.ifi.hase.soprafs22.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//No GetDTO used because no sensitive information can be leaked
@Entity
@Table(name="DECK")
public class Deck implements Serializable {

    // TODO: Figure out how to access User when deckId is given
    @OneToMany(mappedBy = "deck")
    private List<MultipleChoiceCard> cards = new ArrayList<>();

    //foreign key USER_ID
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name= "USER_ID", nullable = false)
    @JsonIgnore
    private User user;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String deckname;

    @Getter
    @Setter
    @CreationTimestamp
    private Timestamp creationdate;

}