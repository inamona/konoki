package com.inamona.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Models a game.
 *
 * @author christopher
 * @since 5/13/18
 */
@Getter
@Entity
@NamedQueries(
    @NamedQuery(
        name = "com.inamona.api.Game.findAll",
        query = "from Game g"
    )
)
public class Game {
    /**
     * The ID of this game.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long gameId;

    /**
     * The {@link Date} at which the Game started.
     */
    @Column(name = "started_at", updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedAt;

    /**
     * The {@link Date} at which the Game ended.
     */
    @Column(name = "ended_at")
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endedAt;

//    /**
//     * The {@link Hand}s in the Game.
//     */
//    @OneToMany(targetEntity = Hand.class, cascade = CascadeType.REMOVE)
//    private final List<Hand> hands;
}