package qangan.blackjack;

import java.util.ArrayList;
import java.util.List;

/** Hand class implementation. */
public class Hand {
    private final List<Card> cards;
    private int score;
    private int aceCount;

    /** Hand constructor. */
    public Hand() {
        this.cards = new ArrayList<>();
        this.score = 0;
        this.aceCount = 0;
    }

    /** Draw card. */
    public void addCard(Deck deck) {
        Card card = deck.dealCard();
        if (card != null) {
            cards.add(card);
            this.score += card.getValue();
            if (card.getRank() == Rank.ACE) {
                this.aceCount += 1;
            }
            while (this.score > 21 && this.aceCount > 0) {
                this.score -= 10;
                this.aceCount -= 1;
            }
        }
    }

    /** Blackjack checker. */
    public boolean isBlackjack() {
        return getScore() == 21 && cards.size() == 2;
    }

    /** Card getter. */
    public Card getCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.get(index);
        }
        return null;
    }

    /** Cards count getter. */
    public int getCardCount() {
        return cards.size();
    }

    /** Score getter. */
    public int getScore() {
        return score;
    }

    /** Hand clear. */
    public void clear() {
        cards.clear();
        this.score = 0;
        this.aceCount = 0;
    }

    /** String representation. */
    @Override
    public String toString() {
        return cards.toString();
    }
}
