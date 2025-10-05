package qangan.expr;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class VariableTests {
    @Test
    void testVariableWithLongName() {
        Variable var = new Variable("qwertyuiopasdfghjklzxcvbn");
        assertEquals("qwertyuiopasdfghjklzxcvbn", var.toString());
    }

    @Test
    void testVariableWithNumbersInName() {
        Variable var = new Variable("x1");
        assertEquals("x1", var.toString());
        Variable var2 = new Variable("var123");
        assertEquals("var123", var2.toString());
    }

    @Test
    void testEvalWithDefinedVariable() {
        Variable x = new Variable("x");
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        assertEquals(5, x.eval(vars));
    }

    @Test
    void testEvalWithUndefinedVariable() {
        Variable x = new Variable("x");
        Map<String, Integer> emptyVars = new HashMap<>();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            x.eval(emptyVars);
        });

        assertTrue(exception.getMessage().contains("Variable x is not defined"));
    }

    @Test
    void testEvalWithPartiallyDefinedVariables() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);

        assertEquals(5.0, x.eval(vars));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            y.eval(vars);
        });
        assertTrue(exception.getMessage().contains("Variable y is not defined"));
    }

}
