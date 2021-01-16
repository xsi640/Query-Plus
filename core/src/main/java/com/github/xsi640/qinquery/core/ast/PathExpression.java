package com.github.xsi640.qinquery.core.ast;

/**
 * @author SuYang
 */
public interface PathExpression<T> extends Expression<T> {
    PathExpression<?> root();
}
