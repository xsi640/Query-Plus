package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.visitor.Visitor;
import lombok.Getter;

/**
 * @author SuYang
 */
public class FieldExpression extends ParamExpression {

    @Getter
    private final String field;

    public FieldExpression(String field) {
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException("field not null");
        }
        this.field = field;
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onField(this, context);
    }

    @Override
    public int priority() {
        return 200;
    }
}
