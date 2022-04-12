package ch.uzh.ifi.hase.soprafs22.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DUEL")
public class Duel {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private long playerOneId;
    // private User playerOne;

    @Getter
    @Setter
    @Column(nullable = false)
    private long playerTwoId;
    // private User playerTwo;

    @Getter
    @Setter
    @Column(nullable = false)
    private long playerOneScore;

    @Getter
    @Setter
    @Column(nullable = false)
    private long playerTwoScore;
}