package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Deck> decks;

    @Getter
    @Setter
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String name;

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

    @Getter
    @Setter
    @Column(nullable = true, unique = false)
    private String birthday;

    @Setter
    @Getter
    @Column(nullable = false)
    private String email;

    @Getter
    @Setter
    @CreationTimestamp
    private Timestamp creationdate;
}