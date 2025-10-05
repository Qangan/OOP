package qangan.expr;

import java.util.Map;
import java.util.Objects;

/**
 * Variable class.
 */
public class Variable extends Expression {
    /**
     * Variable name.
     */
    protected final String name;

    /**
     * Variable constructor.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Prints variable.
     */
    @Override
    public void print() {
        System.out.print(this);
    }

    /**
     * Returns variable string representation.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns derivative.
     */
    @Override
    public Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * Returns value.
     */
    @Override
    public int eval(Map<String, Integer> variables) throws RuntimeException {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else {
            throw new RuntimeException("Variable " + name + " is not defined");
        }
    }

    /**
     * Compares variables.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Variable other) {
            return Objects.equals(this.name, other.name);
        } else {
            return false;
        }
    }
}