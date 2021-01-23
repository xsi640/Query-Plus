package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.FieldExpression;
import lombok.Data;

/**
 * @author SuYang
 */
@Data
public class Order {
    private OrderMode mode;
    private FieldExpression field;
}
