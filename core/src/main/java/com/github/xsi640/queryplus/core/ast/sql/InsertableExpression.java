package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.AbstractExpression;
import com.github.xsi640.queryplus.core.ast.FieldExpression;

/**
 * @author SuYang
 */
public interface InsertableExpression extends AbstractExpression {
    UpdatableExpression insert(String field, FieldExpression expression);
}
