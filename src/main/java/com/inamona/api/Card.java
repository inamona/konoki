package com.inamona.api;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Models a standard playing card.
 *
 * @author christopher
 * @since 5/13/18
 */
@AllArgsConstructor
public class Card {
    /**
     * The {@link Suite} of the Card.
     */
    private final Suite suite;

    /**
     * The {@link FaceValue} of the Card.
     */
    private final FaceValue faceValue;

    /**
     * Factory method to produce all the {@link Card}s in a given {@link Suite}.
     * @param suite the {@link Suite} from which to construct the list of Cards.
     * @return the list of all Cards in the given {@link Suite}.
     */
    public static List<Card> allCardsOfSuite(final Suite suite) {
        return Arrays.stream(FaceValue.values()).parallel().map(faceValue-> new Card(suite, faceValue)).collect(toList());
    }
}
