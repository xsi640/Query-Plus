package com.github.xsi640.qinquery.core.visitor;

import com.github.xsi640.qinquery.core.ast.*;

/**
 * @author SuYang
 */
public abstract class AbstractVisitor<C> implements Visitor<C> {
    @Override
    public void onParam(ParamExpression expr, C context) {
        if (expr instanceof ValueExpression) {
            this.onValue((ValueExpression) expr, context);
        } else if (expr instanceof FuncExpression) {
            this.onFunc((FuncExpression) expr, context);
        }
    }

    @Override
    public void onOperator(OperatorExpression expor, C context) {
        onSymbol(expor, context);
    }
}
