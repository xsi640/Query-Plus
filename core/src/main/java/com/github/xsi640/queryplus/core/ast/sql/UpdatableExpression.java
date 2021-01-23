package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.AbstractExpression;
import com.github.xsi640.queryplus.core.ast.ParamExpression;

/**
 * @author SuYang
 */
public interface UpdatableExpression extends AbstractExpression {
    UpdatableExpression set(String field, ParamExpression expression);
}
