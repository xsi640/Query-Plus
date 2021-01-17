package com.github.xsi640.queryplus.core.visitor;

import com.github.xsi640.qinquery.core.ast.*;
import com.github.xsi640.queryplus.core.ast.FuncExpression;
import com.github.xsi640.queryplus.core.ast.OperatorExpression;
import com.github.xsi640.queryplus.core.ast.ParamExpression;
import com.github.xsi640.queryplus.core.ast.ValueExpression;

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
