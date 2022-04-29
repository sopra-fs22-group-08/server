package ch.uzh.ifi.hase.soprafs22.entity;

import java.io.Serializable;

import javax.persistence.Column;
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

@Entity
@Table(name = "INVITATION")
public class Invitation implements Serializable {
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
    private Long duelId;

    @Getter
    @Setter
    @Column(nullable = false)
    private Long deckId;

    @Getter
    @Setter
    @Column(nullable = false)
    private String deckname;

    @Getter
    @Setter
    @Column(nullable = false)
    private Long senderId;

    @Getter
    @Setter
    @Column(nullable = false)
    private Long receiverId;

    @Getter
    @Setter
    @Column(nullable = false)
    private String senderUsername;

    @Getter
    @Setter
    @Column(nullable = false)
    private String receiverUsername;
}