package com.github.xsi640.qinquery.core.ast;

import com.github.xsi640.qinquery.core.visitor.Visitor;

import java.util.List;

/**
 * @author SuYang
 */
public abstract class ParamExpression extends AbstractExpression {
    public Expression func(SymbolExpression symbol, List<ParamExpression> parameters) {
        return new FuncExpression(symbol, this, parameters);
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onParam(this, context);
    }

    @Override
    public int priority() {
        return 200;
    }
}
