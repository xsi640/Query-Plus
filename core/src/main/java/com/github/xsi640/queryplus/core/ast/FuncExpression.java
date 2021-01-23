package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.visitor.Visitor;
import com.github.xsi640.queryplus.exception.ExpressionArgumentException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author SuYang
 */
public final class FuncExpression implements ParamExpression, LogicExpression {
    @Getter
    private final String funcName;
    @Getter
    private final List<ParamExpression> parameters;

    public FuncExpression(String funcName, ParamExpression... parameters) {
        ExpressionArgumentException.check(funcName == null || funcName.isEmpty(), "FuncExpression funcName can't null.");
        ExpressionArgumentException.check(parameters == null || parameters.length == 0, "FuncExpression parameters can't null or empty.");

        this.funcName = funcName;
        this.parameters = Arrays.asList(parameters);
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onFunc(this, context);
    }

    @Override
    public int priority() {
        return 20;
    }

    public static FuncExpression of(String funcName, ParamExpression... parameters) {
        return new FuncExpression(funcName, parameters);
    }
}
