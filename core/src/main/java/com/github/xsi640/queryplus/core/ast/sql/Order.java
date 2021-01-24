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
public class Order {
    private OrderMode mode;
    private FieldExpression field;

    public static Order of(OrderMode mode, FieldExpression field) {
        Order order = new Order();
        order.setMode(mode);
        order.setField(field);
        return order;
    }
}
