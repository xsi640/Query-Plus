package com.github.xsi640.queryplus.core.ast;

/**
 * @author SuYang
 */
public enum BinaryOperator {
    // for number
    PLUS(50),
    SUB(50),
    MUL(51),
    DIV(51),
    // for string & number & date
    LT(40),
    GT(40),
    LE(40),
    GE(40),
    EQ(40),
    NE(40),
    // for boolean
    AND(30),
    OR(30);

    int priority;

    BinaryOperator(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
