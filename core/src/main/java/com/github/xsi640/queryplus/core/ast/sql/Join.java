package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.FieldExpression;
import com.github.xsi640.queryplus.core.ast.LogicExpression;
import lombok.Data;

/**
 * @author SuYang
 */
@Data
public final class Join {
    private JoinMode mode;
    private FieldExpression schema;
    private FieldExpression table;
    private String tableAlias;
    private LogicExpression on;
}
