package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.AbstractExpression;

/**
 * @author SuYang
 */
public interface QueryableExpression extends AbstractExpression {
    void count();

    void forUpdate();
}
