package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.visitor.Visitor;

/**
 * @author SuYang
 */
public class UnaryExpression extends ParamExpression {

    private final UnaryOperator operator;
    private final ParamExpression expression;

    private UnaryExpression(UnaryOperator operator, ParamExpression param) {
        if(operator ==null|| param==null) {
            throw new IllegalArgumentException("unary all args not null");
        }
        this.operator = operator;
        this.expression = param;
    }

    public UnaryOperator getOperator() {
        return operator;
    }

    public ParamExpression getExpression() {
        return expression;
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onUnary(this, context);
    }

    @Override
    public int priority() {
        return operator.priority;
    }
}
