package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.FieldExpression;

/**
 * @author SuYang
 */
public interface SqlAbstractExpression {
    void clear();

    JoinableExpression from(FieldExpression from, String alias);

    JoinableExpression from(FieldExpression from);
}
