package qangan.expr;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DivTests {
    @Test
    void testToString() {
        Div div = new Div(new Number(6), new Number(2));
        assertEquals("(6/2)", div.toString());

        Div complex = new Div(new Variable("x"), new Div(new Number(1), new Number(2)));
        assertEquals("(x/(1/2))", complex.toString());
    }

    @Test
    void testEval() {
        Expression div = new Div(new Variable("x"), new Number(2));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(5, div.eval(vars));
    }

    @Test
    void testDivisionByZero() {
        Expression div = new Div(new Number(5), new Number(0));
        assertThrows(RuntimeException.class, () -> div.eval(""));
    }

}
