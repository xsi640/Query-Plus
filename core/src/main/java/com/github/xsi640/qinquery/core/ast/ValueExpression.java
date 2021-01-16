package com.github.xsi640.qinquery.core.ast;

/**
 * @author SuYang
 */
public interface ValueExpression<T> extends Expression<T> {
    T value();
}
