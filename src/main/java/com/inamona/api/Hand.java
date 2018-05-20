package com.inamona.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

/**
 * Models a hand dealt as part of a {@link Game}.
 *
 * @author christopher
 * @since 5/13/18
 */
@Getter
@Entity
@Table(name = "hands")
@NamedQueries(
    @NamedQuery(
        name = "com.inamona.api.Hand.findAll",
        query = "from Hand h"
    )
)
@EqualsAndHashCode
public class Hand {
    /**
     * The number of {@link Card}s in a Hand.
     */
    public static final int HAND_SIZE = 5;

    /**
     * The ID of the Hand within the {@link Game}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long handId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnore
    private Game game;

    /**
     * The {@link LocalDateTime} at which the Hand was dealt.
     */
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dealtAt;

//    /**
//     * The {@link Card}s in the Hand.
//     */
//    @ManyToOne()
//    private final List<Card> cards = Lists.newArrayList();

    /**
     * Sets this Hand's parent {@link Game}.
     * @param game the parent {@link Game}.
     */
    public void setGame(final Game game) {
        this.game = game;
    }

    public URI selfUri(final UriInfo uriInfo) {
        return uriInfo.getAbsolutePathBuilder().path(String.valueOf(this.handId)).build();
    }
}
