package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.FieldExpression;
import com.github.xsi640.queryplus.core.ast.LogicExpression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SuYang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Join {
    private JoinMode mode;
    private FieldExpression schema;
    private FieldExpression table;
    private String tableAlias;
    private LogicExpression on;

    public static Join of(JoinMode mode, FieldExpression schema, FieldExpression table, String alias, LogicExpression on) {
        Join join = new Join();
        join.setMode(mode);
        join.setSchema(schema);
        join.setTable(table);
        join.setTableAlias(alias);
        join.setOn(on);
        return join;
    }
}
