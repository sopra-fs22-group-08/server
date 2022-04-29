package ch.uzh.ifi.hase.soprafs22.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

//No GetDTO used because no sensitive information can be leaked
@Entity
@Table(name = "DECK")
public class Deck implements Serializable {

    // TODO: Figure out how to access User when deckId is given
    @OneToMany(mappedBy = "deck")
    private List<MultipleChoiceCard> cards = new ArrayList<>();

    // foreign key USER_ID
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
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