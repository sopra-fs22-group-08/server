package ch.uzh.ifi.hase.soprafs22.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="DECK")
public class Deck implements Serializable {

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name= "USER_ID", nullable = false)
    private User user;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long deckId;

    @Getter
    @Setter
    @Column(nullable = false)
    private String deckname;

    @Getter
    @Setter
    @CreationTimestamp
    private Timestamp creationdate;

}
