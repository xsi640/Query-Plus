package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.visitor.Visitor;
import com.github.xsi640.queryplus.exception.ExpressionArgumentException;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author SuYang
 */
public final class ValueExpression implements ParamExpression {

    @Getter
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
            throw ExpressionArgumentException.of("not support value type.");
        }
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onValue(this, context);
    }

    @Override
    public int priority() {
        return 200;
    }

    public static ValueExpression of(Object value) {
        return new ValueExpression(value);
    }
}
