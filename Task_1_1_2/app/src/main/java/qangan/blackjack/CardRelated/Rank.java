package qangan.blackjack;

/*
 * Rank enum.
 */
public enum Rank {
    ACE("Туз", 11),
    TWO("Двойка", 2),
    THREE("Тройка", 3),
    FOUR("Четверка", 4),
    FIVE("Пятерка", 5),
    SIX("Шестерка", 6),
    SEVEN("Семерка", 7),
    EIGHT("Восьмерка", 8),
    NINE("Девятка", 9),
    TEN("Десятка", 10),
    JACK("Валет", 10),
    QUEEN("Дама", 10),
    KING("Король", 10);

    private final String label;
    private final int value;

    Rank(String label, int value) {
        this.label = label;
        this.value = value;
    }

    /*
     * Label getter.
     */
    public String getLabel() {
        return label;
    }

    /*
     * Value getter.
     */
    public int getValue() {
        return value;
    }

    /*
     * String representation.
     */
    @Override
    public String toString() {
        return label;
    }
}
