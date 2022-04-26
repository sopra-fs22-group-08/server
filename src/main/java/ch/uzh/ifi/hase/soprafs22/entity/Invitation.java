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

@Entity
@Table(name="INVITATION")
public class Invitation implements Serializable{
    //foreign key USER_ID
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="USER_ID", nullable = false)
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
    @Column(nullable=false)
    private Long duelId;

    @Getter
    @Setter
    @Column(nullable = false)
    private Long senderId;

    @Getter
    @Setter
    @Column(nullable = false)
    private Long receiverId;
}
