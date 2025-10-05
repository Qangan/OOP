package qangan.expr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddTests {
    @Test
    void testToString() {
        Add add = new Add(new Number(3), new Number(4));
        assertEquals("(3+4)", add.toString());
    }

    @Test
    void testEval() {
        Expression add = new Add(new Number(3), new Number(7));
        assertEquals(10, add.eval(""));
    }

}
