package ch.uzh.ifi.hase.soprafs22.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.xml.bind.v2.TODO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

//No GetDTO used because no sensitive information can be leaked
@Entity
@Table(name="DECK")
public class Deck implements Serializable {

    //TODO: Figure out how to access User when deckId is given

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
