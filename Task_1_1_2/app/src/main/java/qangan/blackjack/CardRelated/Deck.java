package qangan.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Deck class description.
 */
public class Deck {
    private final List<Card> cards;

    /*
     * Deck class constructor.
     */
    public Deck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    /*
     * Deck shuffle.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /*
     * Draw card.
     */
    public Card dealCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }
    
    /*
     * Remaining cards.
     */
    public int remainingCards() {
        return cards.size();
    }
}

