package com.github.xsi640.queryplus.core.ast.sql;

import java.util.List;

/**
 * @author SuYang
 */
public interface ModifiableExpression extends GroupableExpression {
    UpdatableExpression executeUpdate();

    InsertableExpression executeInsert();

    void delete(List<String> fields);

    void delete();
}
