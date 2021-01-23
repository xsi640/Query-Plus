package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.FieldExpression;

import java.util.List;

/**
 * @author SuYang
 */
public interface ModifiableExpression extends GroupableExpression {
    UpdatableExpression update();

    void delete(List<FieldExpression> fields);

    void delete();
}
