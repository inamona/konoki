package com.inamona.api;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

import static com.inamona.api.Hand.HAND_SIZE;

/**
 * Models a standard 52-card deck of playing cards.
 *
 * @author christopher
 * @since 5/13/18
 */
public class Deck {
    /**
     * The {@link Card}s in the Deck.
     */
    private Stack<Card> cards;

    /**
     * New instance.
     */
    public Deck() {
        final Stack<Card> cards = new Stack<>();
        for (final Suite suite : Suite.values()) {
            for (final FaceValue faceValue : FaceValue.values()) {
                cards.add(new Card(suite, faceValue));
            }
        }
        this.cards = cards;
    }

    /**
     * Deals a {@link Hand} of {@link Card}s from the Deck.
     * @return the {@link Hand}.
     */
    public Hand dealHand() {
        final List<Card> theHand = Lists.newArrayList();
        while (theHand.size() < HAND_SIZE) {
            theHand.add(this.cards.pop());
        }
        return null;
    }

    /**
     * Shuffles the {@link Card}s in the Deck.
     */
    public void shuffle() {

    }

    /**
     * Detemines the number of {@link Card}s left in the Deck.
     * @return the number of {@link Card}s left in the Deck.
     */
    public int remainingCards() {
        return this.cards.size();
    }
}
