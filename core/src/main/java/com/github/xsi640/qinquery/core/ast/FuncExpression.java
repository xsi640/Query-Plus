package com.github.xsi640.qinquery.core.ast;

import java.util.List;

/**
 * @author SuYang
 */
public final class FuncExpression implements Expression {
    private final Expression func;
    private final List<Expression> parameters;

    public FuncExpression(Expression func, List<Expression> parameters) {
        if (func == null || parameters == null || parameters.isEmpty()) {
            throw new IllegalArgumentException("not null");
        }
        this.func = func;
        this.parameters = parameters;
    }

    public Expression getFunc() {
        return func;
    }

    public List<Expression> getParameters() {
        return parameters;
    }

    @Override
    public <R, C> R accept(Visitor<R, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public int priority() {
        return func.priority();
    }
}
