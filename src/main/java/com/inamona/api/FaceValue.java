package com.inamona.api;

import lombok.AllArgsConstructor;

/**
 * @author christopher
 * @since 5/13/18
 */
@AllArgsConstructor
public enum FaceValue {
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13),
    ACE("A", 14);

    /**
     * The symbolic representation of the Card.
     */
    private final String symbol;

    /**
     * The numeric value of the Card.
     */
    private final int value;
}