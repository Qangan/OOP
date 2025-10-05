package qangan.expr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NumberTests {

    @Test
    void testToStringAndPrint() {
        Number num1 = new Number(3);
        assertEquals("3", num1.toString());
        Number num2 = new Number(2);
        assertEquals("2", num2.toString());
    }

    @Test
    void testEval() {
        Expression num = new Number(7);
        assertEquals(7, num.eval(""));
    }

    @Test
    void testEquals() {
        Number a = new Number(4);
        Number b = new Number(4);
        Number c = new Number(5);
        assertEquals(a.toString(), b.toString());
        assertNotEquals(a.toString(), c.toString());
    }

}
