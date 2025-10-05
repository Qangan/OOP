package qangan.expr;

import java.util.Map;

/**
 * Multiplication class.
 */
public class Mul extends Expression {
    /**
     * Left operand
     */
    protected final Expression left;
    /**
     * Right operand
     */
    protected final Expression right;

    /**
     * Multiplication constructor.
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Prints expression.
     */
    @Override
    public void print() {
        System.out.print(this);
    }

    /**
     * Returns string representation.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }

    /**
     * Returns derivative of expression with variable.
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }

    /**
     * Returns value of the expression given.
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) * right.eval(variables);
    }

    /**
     * Compares expressions.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Mul other) {
            return this.left.equals(other.left) && this.right.equals(other.right);
        } else {
            return false;
        }
    }
}