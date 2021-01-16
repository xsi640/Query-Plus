package com.github.xsi640.qinquery.core;

import com.github.xsi640.qinquery.core.ast.Expression;
import com.github.xsi640.qinquery.core.ast.Visitor;

/**
 * @author SuYang
 */
public abstract class BaseVisitor<R, C> implements Visitor<R, C> {
    @Override
    public R visit(Expression expr, C context) {
        return expr.accept(this, context);
    }
}
