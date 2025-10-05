package qangan.expr;

import java.util.Map;

/**
 * Division class.
 */
public class Div extends Expression {
    /**
     * Left operand
     */
    protected final Expression left;
    /**
     * Right operand
     */
    protected final Expression right;

    /**
     * Division constructor.
     */
    public Div(Expression left, Expression right) {
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
        return "(" + left.toString() + "/" + right.toString() + ")";
    }

    /**
     * Returns derivative of expression with variable.
     */
    @Override
    public Expression derivative(String variable) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))
                ),
                new Mul(right, right)
        );
    }

    /**
     * Returns value of the expression given.
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        int rightValue = right.eval(variables);

        return left.eval(variables) / rightValue;
    }

    /**
     * Checks if two Add expressions are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Div other){
            return this.left.equals(other.left) && this.right.equals(other.right);
        } else {
            return false;
        }
    }

}