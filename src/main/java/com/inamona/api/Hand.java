package com.inamona.api;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Models a hand dealt as part of a {@link Game}.
 *
 * @author christopher
 * @since 5/13/18
 */
public class Hand {
    /**
     * The number of {@link Card}s in a Hand.
     */
    public static final int HAND_SIZE = 5;

    /**
     * The ID of the Hand within the {@link Game}
     */
    private final UUID handId;

    /**
     * The {@link LocalDateTime} at which the Hand was dealt.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime dealtAt;

    /**
     * The {@link Card}s in the Hand.
     */
    private final List<Card> cards;

    public Hand(final List<Card> cards) {
        this.cards = cards;
        this.handId = UUID.randomUUID();
        this.dealtAt = LocalDateTime.now();
    }
}
