package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.LogicExpression;

/**
 * @author SuYang
 */
public interface FilterableExpression extends ModifiableExpression {
    ModifiableExpression where(LogicExpression expression);
}
