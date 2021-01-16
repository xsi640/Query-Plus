package com.github.xsi640.qinquery.core.ast;

import com.github.xsi640.qinquery.core.visitor.Visitor;

/**
 * @author SuYang
 */
public final class OperatorExpression extends SymbolExpression {

    private final Operator operator;

    public OperatorExpression(Operator operator) {
        super(operator.name());
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onOperator(this, context);
    }

    @Override
    public int priority() {
        return this.operator.priority;
    }
}
