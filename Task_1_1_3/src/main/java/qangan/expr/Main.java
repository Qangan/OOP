package qangan.expr;

/**
 * Demonstration of code functionality.
 */
public class Main {
    /**
     * Demonstration of code functionality.
     */
    public static void main(String[] args) {
        String vars = "x = 4; y = 2; z = 2";
        System.out.println("x = 4; y = 2; z = 2");
        Expression[] expressions = {
            new Number(7),
            new Variable("x"),
            new Add(new Number(3), new Number(5)),
            new Sub(new Variable("x"), new Number(1)),
            new Mul(new Variable("x"), new Variable("y")),
            new Div(new Variable("z"), new Number(2)),
            new Mul(
                new Add(new Variable("x"), new Variable("y")),
                new Sub(new Variable("z"), new Number(3))
                )
        };

        for (Expression expr : expressions) {
            System.out.println("Current: " + expr);
            int result = expr.eval(vars);
            System.out.println("Result: " + result);
            Expression deriv = expr.derivative("x");
            System.out.println("df/dx: " + deriv.toString());
            System.out.println();
        }

        String[] exprStrs = {"(x+5)", "((x*2)+(y/3))", "(((x+y)*z)-(x/2))"};
        for (String s : exprStrs) {
            System.out.println("Current: " + s);
            Expression parsed = Parser.parse(s);
            System.out.println("Parsed: " + parsed.toString());
            System.out.println("Result: " + parsed.eval(vars));
            System.out.println("df/dx: " + parsed.derivative("x").toString());
            System.out.println();
        }
    }
}