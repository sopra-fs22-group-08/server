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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "user")
    private List<Deck> decks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Invitation> invitations = new ArrayList<>();

    // primary key
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String firstName;

    @Setter
    @Getter
    @Column(nullable = false)
    private String lastName;

    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String username;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String token;

    @Setter
    @Getter
    @Column(nullable = false)
    private UserStatus status;

    @Setter
    @Getter
    @Column(nullable = false)
    private String email;

    @Getter
    @Setter
    @CreationTimestamp
    private Timestamp creationdate;
}