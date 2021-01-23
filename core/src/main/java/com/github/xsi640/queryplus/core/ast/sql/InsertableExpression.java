package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.FieldExpression;

/**
 * @author SuYang
 */
public interface InsertableExpression extends UpdatableExpression {
    UpdatableExpression set(String field, FieldExpression expression);
}
