package com.github.xsi640.queryplus.core.ast;

/**
 * @author SuYang
 */
public enum UnaryOperator {
    NOT(100);

    int priority;

    UnaryOperator(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
