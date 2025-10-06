package qangan.expr;

import java.util.HashMap;
import java.util.Map;

/**
 * Expressions class.
 */
public abstract class Expression {

    /**
     * Prints expression.
     */
    public abstract void print();

    /**
     * Returns string representation of expression.
     */
    public abstract String toString();

    /**
     * Returns derivative of expression with variable.
     */
    public abstract Expression derivative(String variable);

    /**
     * Returns value of expression.
     */
    protected abstract int eval(Map<String, Integer> variables);

    /**
     * Returns value of expression.
     */
    public int eval(String variable) {
        Map<String, Integer> variables = parseVarList(variable);
        return eval(variables);
    }

    /**
     * Compares expressions.
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Parses string of variable assignments into a map.
     */
    private Map<String, Integer> parseVarList(String varlist) {
        Map<String, Integer> variables = new HashMap<>();
        if (varlist.trim().isEmpty()) {
            return variables;
        }

        String[] pairs = varlist.split(";");
        for (String pair : pairs) {
            String[] parts = pair.trim().split("=");
            if (parts.length == 2) {
                String varName = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                variables.put(varName, value);
            }
        }
        return variables;
    }

}
