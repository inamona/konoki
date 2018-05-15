package com.inamona.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Models a hand dealt as part of a {@link Game}.
 *
 * @author christopher
 * @since 5/13/18
 */
@Entity
@AllArgsConstructor
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
    private final long handId;

    /**
     * The {@link LocalDateTime} at which the Hand was dealt.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private final Date dealtAt;

    /**
     * The {@link Card}s in the Hand.
     */
    private final List<Card> cards;
}
