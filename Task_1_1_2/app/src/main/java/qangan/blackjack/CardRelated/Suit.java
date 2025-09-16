package qangan.blackjack;

/** Suit enum. */
public enum Suit {
    SPADES("Пики"),
    HEARTS("Черви"),
    DIAMONDS("Бубны"),
    CLUBS("Трефы");

    private final String label;

    Suit(String label) {
        this.label = label;
    }

    /** String representation. */
    @Override
    public String toString() {
        return label;
    }
}
