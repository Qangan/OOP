package qangan.blackjack;

/** Player class description. */
public class Player {
    /** Player hand. */
    private Hand hand;

    /** Player constructor. */
    public Player() {
        this.hand = new Hand();
    }

    /** Draw card. */
    public void receive(Deck deck) {
        hand.addCard(deck);
    }

    /** Blackjack checker. */
    public boolean hasBlackjack() {
        return hand.isBlackjack();
    }

    /** Hand score getter. */
    public int getScore() {
        return hand.getScore();
    }

    /** Hand getter. */
    public Hand getHand() {
        return hand;
    }

    /** Prints hand. */
    public String showHand() {
        return hand.toString() + " => " + hand.getScore();
    }
}
