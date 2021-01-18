package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.visitor.Visitor;

/**
 * @author SuYang
 */
public class BinaryExpression extends ParamExpression {

    private final BinaryOperator operator;
    private final ParamExpression left;
    private final ParamExpression right;

    public BinaryExpression(BinaryOperator operator, ParamExpression left, ParamExpression right) {
        if(operator == null || left == null || right == null){
            throw new IllegalArgumentException("binary all args not null");
        }
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public ParamExpression getLeft() {
        return left;
    }

    public ParamExpression getRight() {
        return right;
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onBinary(this, context);
    }

    @Override
    public int priority() {
        return operator.priority;
    }
}
