package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.FieldExpression;
import com.github.xsi640.queryplus.core.ast.LogicExpression;

import java.util.List;

/**
 * @author SuYang
 */
public interface JoinableExpression extends FilterableExpression {
    List<Join> joins();

    JoinableExpression join(JoinMode mode, String schema, FieldExpression table, String alias, LogicExpression on);

    JoinableExpression leftJoin(FieldExpression table, String alias, LogicExpression on);

    JoinableExpression rightJoin(FieldExpression table, String alias, LogicExpression on);

    JoinableExpression innerJoin(FieldExpression table, String alias, LogicExpression on);

    JoinableExpression leftJoin(FieldExpression table, LogicExpression on);

    JoinableExpression rightJoin(FieldExpression table, LogicExpression on);

    JoinableExpression innerJoin(FieldExpression table, LogicExpression on);
}
