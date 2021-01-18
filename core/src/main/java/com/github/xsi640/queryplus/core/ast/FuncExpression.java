package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.visitor.Visitor;

import java.util.Arrays;
import java.util.List;

/**
 * @author SuYang
 */
public final class FuncExpression extends ParamExpression {
    private final String funcName;
    private final List<ParamExpression> parameters;

    public FuncExpression(String funcName, ParamExpression... parameters) {
        if (funcName == null || funcName.isEmpty() || parameters.length == 0) {
            throw new IllegalArgumentException("func not null");
        }
        this.funcName = funcName;
        this.parameters = Arrays.asList(parameters);
    }

    public String getFuncName() {
        return funcName;
    }

    public List<ParamExpression> getParameters() {
        return parameters;
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onFunc(this, context);
    }

    @Override
    public int priority() {
        return 20;
    }
}
