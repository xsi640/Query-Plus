package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.FieldExpression;

import java.util.List;

/**
 * @author SuYang
 */
public interface OrderableExpression extends LimitableExpression {
    List<Order> orders();

    OrderableExpression order(Order order);

    OrderableExpression order(OrderMode mode, FieldExpression expression);

    OrderableExpression asc(FieldExpression expression);

    OrderableExpression desc(FieldExpression expression);
}
