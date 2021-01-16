package com.github.xsi640.qinquery.core.ast;

import java.util.List;

/**
 * @author SuYang
 */
public interface OperationExpression<T> extends Expression<T> {
    Expression<?> arg(int index);

    List<Expression<?>> args();

    Operator operator();
}
