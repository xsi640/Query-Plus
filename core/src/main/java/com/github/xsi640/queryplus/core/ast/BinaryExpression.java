package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.visitor.Visitor;
import lombok.Getter;

/**
 * @author SuYang
 */
public class BinaryExpression extends ParamExpression {

    @Getter
    private final BinaryOperator operator;
    @Getter
    private final ParamExpression left;
    @Getter
    private final ParamExpression right;

    public BinaryExpression(BinaryOperator operator, ParamExpression left, ParamExpression right) {
        if (operator == null || left == null || right == null) {
            throw new IllegalArgumentException("binary all args not null");
        }
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onBinary(this, context);
    }

    @Override
    public int priority() {
        return operator.priority;
    }

    public static BinaryExpression of(BinaryOperator operator, ParamExpression left, ParamExpression right) {
        return new BinaryExpression(operator, left, right);
    }
}
