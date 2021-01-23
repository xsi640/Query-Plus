package com.github.xsi640.queryplus.exception;

/**
 * @author SuYang
 */
public class ExpressionArgumentException extends QueryException {
    public ExpressionArgumentException(String message) {
        super(message);
    }

    public static ExpressionArgumentException of(String message) {
        return new ExpressionArgumentException(message);
    }

    public static void check(boolean flag, String message) {
        if (flag) {
            throw ExpressionArgumentException.of(message);
        }
    }
}
