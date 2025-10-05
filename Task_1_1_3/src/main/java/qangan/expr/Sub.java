package qangan.expr;

import java.util.Map;

/**
 * Substraction class.
 */
public class Sub extends Expression {
    /**
     * Left operand
     */
    protected final Expression left;
    /**
     * Right operand
     */
    protected final Expression right;

    /**
     * Substraction constructor.
     */
    public Sub(Expression left, Expression right) {
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
     * Returns string representation of expression.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }

    /**
     * Returns derivative of expression with variable.
     */
    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Returns value.
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) - right.eval(variables);
    }

    /**
     * Compares expressions.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Sub other) {
            return this.left.equals(other.left) && this.right.equals(other.right);
        } else {
            return false;
        }
    }
}