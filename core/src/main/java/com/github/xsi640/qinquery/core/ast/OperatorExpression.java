package com.github.xsi640.qinquery.core.ast;

/**
 * @author SuYang
 */
public final class OperatorExpression implements Expression {

    private final Operator operator;

    public OperatorExpression(Operator operator) {
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public <R, C> R accept(Visitor<R, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public int priority() {
        return operator.priority;
    }
}
