package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
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
@NoArgsConstructor
public class User implements Serializable, Principal {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "user")
    private List<Deck> decks = new ArrayList<>();

    // BUG: custom constructor for the principal
    public User(String name) {
        this.username = name;
    }

    //primary key
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

    /*
     * @brief override from Principal
     * @returns returns the username for the simp to checkt
     */
    @Override
    public String getName() {
        return this.username;
    }
}