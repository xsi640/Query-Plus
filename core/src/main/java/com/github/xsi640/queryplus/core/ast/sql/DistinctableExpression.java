package com.github.xsi640.queryplus.core.ast.sql;

/**
 * @author SuYang
 */
public interface DistinctableExpression extends QueryableExpression {
    QueryableExpression distinct();
}
