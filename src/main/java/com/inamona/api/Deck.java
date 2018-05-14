package com.inamona.api;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

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
    private Deque<Card> cards;

    /**
     * New instance.
     */
    public Deck() {
        this.cards = Arrays.stream(Suite.values()).parallel().collect(new ArrayDeque<Card>(), ArrayDeque::add, ArrayDeque::addAll);
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
        return new Hand(theHand);
    }

    /**
     * Shuffles the {@link Card}s in the Deck.
     */
    public void shuffle() {

    }
}
