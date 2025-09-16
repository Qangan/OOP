package qangan.blackjack;

/**
 * Card class description.
 */
public class Card {
    private final Suit suit;
    private final Rank rank;
    
    /**
     * Class constructor.
     *
     * @param suit - Suit
     * @param rank - Rank
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Rank getter.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Value getter.
     */
    public int getValue() {
        return rank.getValue();
    }
    
    /**
     * Suit getter.
     */
    public Suit getSuit() {
        return suit;
    }

    /*
     * String representation of card.
     */
    @Override
    public String toString() {
        return rank + " " + suit;
    }
}

