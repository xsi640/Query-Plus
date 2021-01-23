package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.ParamExpression;

import java.util.List;

/**
 * @author SuYang
 */
public interface SelectableExpression extends DistinctableExpression {
    Limit limit();

    List<Select> selects();

    SelectableExpression select(Select select);

    SelectableExpression select(ParamExpression expression);

    SelectableExpression select(ParamExpression expression, String alias);
}
