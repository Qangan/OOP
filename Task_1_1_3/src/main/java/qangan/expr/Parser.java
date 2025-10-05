package qangan.expr;

/**
 * Expression parser class.
 */
public class Parser {

    protected String expression;
    protected int position;

    /**
     * Parses a string expression.
     */
    public static Expression parse(String expr) {
        Parser parser = new Parser();
        parser.expression = expr.replaceAll("\\s+", ""); // Remove whitespace
        parser.position = 0;
        return parser.parseExpression();
    }

    /**
     * Parses expression recursively.
     */
    private Expression parseExpression() {

        char currentChar = expression.charAt(position);

        if (currentChar == '(') {
            return parse();
        } else if (Character.isDigit(currentChar) || currentChar == '-') {
            return parseNumber();
        } else if (Character.isLetter(currentChar)) {
            return parseVariable();
        } else {
            throw new RuntimeException("Unexpected char: " + currentChar + " at pos " + position);
        }
    }

    /**
     * Parses expression.
     */
    private Expression parse() {
        position++;

        Expression left = parseExpression();

        char operator = expression.charAt(position);
        position++;

        Expression right = parseExpression();

        position++;

        return switch (operator) {
            case '+' -> new Add(left, right);
            case '-' -> new Sub(left, right);
            case '*' -> new Mul(left, right);
            case '/' -> new Div(left, right);
            default -> throw new RuntimeException("Unknown operator: " + operator);
        };
    }

    /**
     * Parses number.
     */
    private Expression parseNumber() {
        int startPos = position;

        if (expression.charAt(position) == '-') {
            position++;
        }

        while (position < expression.length()
                && Character.isDigit(expression.charAt(position))) {
            position++;
        }

        String numberStr = expression.substring(startPos, position);
        try {
            int value = Integer.parseInt(numberStr);
            return new Number(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Not a number: " + numberStr);
        }
    }

    /**
     * Parses variable name.
     */
    private Expression parseVariable() {
        int startPos = position;

        while (position < expression.length()
                && (Character.isLetter(expression.charAt(position))
                        || Character.isDigit(expression.charAt(position)))) {
            position++;
        }

        String variableName = expression.substring(startPos, position);
        return new Variable(variableName);
    }
}