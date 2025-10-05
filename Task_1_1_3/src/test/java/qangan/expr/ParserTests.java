package qangan.expr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ParserTests {
    @Test
    void testParseNumber() {
        Expression expr = Parser.parse("42");
        assertInstanceOf(Number.class, expr);
        assertEquals(new Number(42), expr);
    }

    @Test
    void testParseNegativeDecimal() {
        Expression expr = Parser.parse("-3");
        assertInstanceOf(Number.class, expr);
        assertEquals(new Number(-3), expr);
    }

    @Test
    void testParseVariable() {
        Expression expr = Parser.parse("x");
        assertInstanceOf(Variable.class, expr);
        assertEquals(new Variable("x"), expr);
    }

    @Test
    void testParseVariableWithDigits() {
        Expression expr = Parser.parse("var1");
        assertInstanceOf(Variable.class, expr);
        assertEquals(new Variable("var1"), expr);
    }

    @Test
    void testParseExpressionWithMultipleVariables() {
        Expression expr = Parser.parse("(((var1*var2)/var3)-1)");
        Expression expected = new Sub(
                new Div(
                        new Mul(new Variable("var1"), new Variable("var2")),
                        new Variable("var3")
                ),
                new Number(1)
        );
        assertEquals(expected, expr);
    }

    @Test
    void testParseSimpleAdd() {
        Expression expr = Parser.parse("(3+4)");
        assertInstanceOf(Add.class, expr);
        assertEquals(new Add(new Number(3), new Number(4)), expr);
    }

    @Test
    void testParseSimpleSub() {
        Expression expr = Parser.parse("(10-5)");
        assertInstanceOf(Sub.class, expr);
        assertEquals(new Sub(new Number(10), new Number(5)), expr);
    }

    @Test
    void testParseSimpleMul() {
        Expression expr = Parser.parse("(6*7)");
        assertInstanceOf(Mul.class, expr);
        assertEquals(new Mul(new Number(6), new Number(7)), expr);
    }

    @Test
    void testParseSimpleDiv() {
        Expression expr = Parser.parse("(8/2)");
        assertInstanceOf(Div.class, expr);
        assertEquals(new Div(new Number(8), new Number(2)), expr);
    }

    @Test
    void testParseNestedMixed() {
        Expression expr = Parser.parse("((1+2)*(3-4))");
        assertInstanceOf(Mul.class, expr);
        Expression expected = new Mul(
                new Add(new Number(1), new Number(2)),
                new Sub(new Number(3), new Number(4))
        );
        assertEquals(expected, expr);
    }

    @Test
    void testParseWhitespace() {
        Expression expr = Parser.parse(" (  3 +   ( 4 *5 ) ) ");
        Expression expected = new Add(
                new Number(3),
                new Mul(new Number(4), new Number(5))
        );
        assertEquals(expected, expr);
    }
}