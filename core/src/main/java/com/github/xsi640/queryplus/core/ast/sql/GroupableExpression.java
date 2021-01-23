package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.FieldExpression;

import java.util.List;

/**
 * @author SuYang
 */
public interface GroupableExpression extends OrderableExpression {
    List<FieldExpression> groups();

    GroupableExpression group(FieldExpression expression);
}
