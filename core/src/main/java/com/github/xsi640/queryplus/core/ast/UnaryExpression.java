package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.visitor.Visitor;
import com.github.xsi640.queryplus.exception.ExpressionArgumentException;
import lombok.Getter;

/**
 * @author SuYang
 */
public class UnaryExpression implements ParamExpression, LogicExpression {

    @Getter
    private final UnaryOperator operator;
    @Getter
    private final ParamExpression expression;

    private UnaryExpression(UnaryOperator operator, ParamExpression param) {
        ExpressionArgumentException.check(operator == null, "The UnaryExpression operator can't null.");
        ExpressionArgumentException.check(param == null, "The UnaryExpression param can't null.");

        this.operator = operator;
        this.expression = param;
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onUnary(this, context);
    }

    @Override
    public int priority() {
        return operator.priority;
    }

    public UnaryExpression of(UnaryOperator operator, ParamExpression param) {
        return new UnaryExpression(operator, param);
    }
}
