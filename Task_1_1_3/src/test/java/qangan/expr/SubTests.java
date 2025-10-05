package qangan.expr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Substraction tests.
 */
public class SubTests {
    @Test
    void testToString() {
        Sub sub = new Sub(new Number(8), new Number(3));
        assertEquals("(8-3)", sub.toString());
    }

    @Test
    void testEval() {
        Expression sub = new Sub(new Number(8), new Number(3));
        assertEquals(5.0, sub.eval(""));
    }

}
