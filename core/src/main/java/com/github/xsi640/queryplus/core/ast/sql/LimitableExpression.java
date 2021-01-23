package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.ParamExpression;

/**
 * @author SuYang
 */
public interface LimitableExpression extends SelectableExpression {
    SelectableExpression limit(Limit limit);

    SelectableExpression limit(ParamExpression start, ParamExpression count);
}
