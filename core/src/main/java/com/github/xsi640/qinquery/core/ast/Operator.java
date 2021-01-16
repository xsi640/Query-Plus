package com.github.xsi640.qinquery.core.ast;

/**
 * @author SuYang
 */
public enum Operator {
    // for number
    PLUS(50),
    SUB(50),
    MUL(51),
    DIV(51),
    MOD(51),
    NEG(52),
    // for string & number & date
    LT(40),
    GT(40),
    LE(40),
    GE(40),
    EQ(40),
    NE(40),
    // for boolean
    AND(31),
    OR(30),
    NOT(32);

    int priority;

    Operator(int priority) {
        this.priority = priority;
    }
}
