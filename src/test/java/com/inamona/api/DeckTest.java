package com.inamona.api;

import org.junit.Before;
import org.junit.Test;

import static com.inamona.api.Hand.HAND_SIZE;
import static org.junit.Assert.assertEquals;

/**
 * @author christopher
 * @since 5/13/18
 */
public class DeckTest {

    private Deck deck;

    @Before
    public void setup() {
        this.deck = new Deck();
    }

    @Test
    public void newDeck() {
        assertEquals(this.deck.remainingCards(), 52);
    }

    @Test
    public void dealHand() {
        this.deck.dealHand();
        assertEquals(this.deck.remainingCards(), 52 - HAND_SIZE);
    }
}