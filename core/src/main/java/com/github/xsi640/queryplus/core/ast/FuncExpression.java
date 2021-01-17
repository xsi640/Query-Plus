package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.visitor.Visitor;

import java.util.Arrays;
import java.util.List;

/**
 * @author SuYang
 */
public final class FuncExpression extends ParamExpression {
    private final SymbolExpression func;
    private final ParamExpression mark;
    private final List<ParamExpression> parameters;

    public FuncExpression(SymbolExpression func, ParamExpression mark, ParamExpression... parameters) {
        if (func == null || mark == null) {
            throw new IllegalArgumentException("func and mark not null");
        }
        this.func = func;
        this.mark = mark;
        this.parameters = Arrays.asList(parameters);
    }

    public FuncExpression(SymbolExpression func, ParamExpression mark, List<ParamExpression> parameters) {
        if (func == null || mark == null) {
            throw new IllegalArgumentException("func and mark not null");
        }
        this.func = func;
        this.mark = mark;
        this.parameters = parameters;
    }

    public SymbolExpression getFunc() {
        return func;
    }

    public ParamExpression getMark() {
        return mark;
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
        return func.priority();
    }
}
