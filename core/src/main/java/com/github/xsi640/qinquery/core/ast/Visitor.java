package com.github.xsi640.qinquery.core.ast;

/**
 * @author SuYang
 */
public interface Visitor<R, C> {
    R visit(ValueExpression<?> expr, C context);
}
