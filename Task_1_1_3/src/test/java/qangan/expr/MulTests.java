package qangan.expr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Multiplication tests.
 */
public class MulTests {
    @Test
    void testToString() {
        Mul mul = new Mul(new Number(4), new Number(5));
        assertEquals("(4*5)", mul.toString());
    }

    @Test
    void testEval() {
        Expression mul = new Mul(new Number(4), new Number(5));
        assertEquals(20.0, mul.eval(""));
    }
}
