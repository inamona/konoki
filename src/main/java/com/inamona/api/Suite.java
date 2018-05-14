package com.inamona.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Models the four suites in a standard 52-card deck of playing cards.
 *
 * @author christopher
 * @since 5/13/18
 */
@AllArgsConstructor
public enum Suite {
    SPADES("\u2660"), HEARTS("\u2665"), DIAMONDS("\u2666"), CLUBS("\u2663");

    /**
     * The symbolic representation of the Suite.
     */
    @Getter
    private final String symbol;
}
