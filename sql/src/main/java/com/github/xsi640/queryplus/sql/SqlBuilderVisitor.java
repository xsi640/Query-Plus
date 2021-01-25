package com.github.xsi640.queryplus.sql;

import com.github.xsi640.queryplus.core.ast.SqlExpression;

/**
 * @author SuYang
 */
public interface SqlBuilderVisitor<C> {
    void buildQuery(SqlExpression expr, C context);

    void buildUpdate(SqlExpression expr, C context);

    void buildInsert(SqlExpression expr, C context);

    void buildDelete(SqlExpression expr, C context);
}
