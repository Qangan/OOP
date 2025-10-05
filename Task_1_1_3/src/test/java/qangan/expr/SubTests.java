package qangan.expr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
