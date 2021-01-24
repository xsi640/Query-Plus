package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.FieldExpression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SuYang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    private String schema;
    private FieldExpression table;
    private String alias;

    public static Table of(String schema, FieldExpression table, String alias) {
        Table t = new Table();
        t.setSchema(schema);
        t.setTable(table);
        t.setAlias(alias);
        return t;
    }
}
