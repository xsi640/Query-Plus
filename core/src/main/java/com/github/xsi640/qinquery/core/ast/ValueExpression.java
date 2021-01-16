package com.github.xsi640.qinquery.core.ast;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author SuYang
 */
public final class ValueExpression implements Expression {

    private final Object value;

    public ValueExpression(Object value) {
        if (value instanceof Boolean ||
                value instanceof Date ||
                value instanceof Integer ||
                value instanceof Long ||
                value instanceof Float ||
                value instanceof Double ||
                value instanceof BigDecimal ||
                value instanceof BigInteger ||
                value instanceof String) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("not support value type.");
        }
    }

    public Object getValue() {
        return value;
    }

    @Override
    public <R, C> R accept(Visitor<R, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public int priority() {
        return 200;
    }
}
