package qangan.expr;

import java.util.Map;

/**
 * Number class.
 */
public class Number extends Expression {
    /**
     * Value of the constant.
     */
    protected final int value;

    /**
     * Number constructor.
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * Returns value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Prints number.
     */
    @Override
    public void print() {
        System.out.print(this);
    }

    /**
     * Returns string representation of number.
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Returns derivative.
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * Returns value of the expression given.
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }

    /**
     * Compares numbers.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Number other) {
            return (this.value == other.value);
        } else {
            return false;
        }
    }

}