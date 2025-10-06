package qangan.expr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Derivation tests.
 */
public class DerivativeTests {

    @Test
    void testDerivativeConstant() {
        Expression expr = Parser.parse("(5+7)");
        Expression deriv = expr.derivative("x");
        Expression expected = new Add(new Number(0), new Number(0));
        assertEquals(expected, deriv);
    }

    @Test
    void testDerivativeVariable() {
        Expression expr = Parser.parse("x");
        Expression expr2 = Parser.parse("(42*x)");
        Expression derivx = expr.derivative("x");
        Expression derivy = expr.derivative("y");
        Expression deriv = expr2.derivative("x");

        assertEquals(new Number(1), derivx);
        assertEquals(new Number(0), derivy);
        assertEquals(new Add(
                new Mul(new Number(0), new Variable("x")),
                new Mul(new Number(42), new Number(1))
        ), deriv);
    }

    @Test
    void testDerivativeSum() {
        Expression expr = Parser.parse("(x+3)");
        Expression deriv = expr.derivative("x");
        Expression expected = new Add(new Number(1), new Number(0));
        assertEquals(expected, deriv);
    }

    @Test
    void testDerivativeSubtraction() {
        Expression expr = Parser.parse("(x-4)");
        Expression deriv = expr.derivative("x");
        Expression expected = new Sub(new Number(1), new Number(0));
        assertEquals(expected, deriv);
    }

    @Test
    void testDerivativeMultiplication() {
        Expression expr = Parser.parse("(x*y)");
        Expression deriv = expr.derivative("x");
        Expression expected = new Add(
                new Mul(new Number(1), new Variable("y")),
                new Mul(new Variable("x"), new Number(0))
        );
        assertEquals(expected, deriv);
    }

    @Test
    void testDerivativeDivision() {
        Expression expr = Parser.parse("(u/v)");
        Expression deriv = expr.derivative("u");
        Expression expected = new Div(
                new Sub(
                        new Mul(new Number(1), new Variable("v")),
                        new Mul(new Variable("u"), new Number(0))
                ),
                new Mul(new Variable("v"), new Variable("v"))
        );
        assertEquals(expected, deriv);
    }
}