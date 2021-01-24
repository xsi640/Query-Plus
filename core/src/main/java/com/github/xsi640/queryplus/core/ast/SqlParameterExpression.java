package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.visitor.Visitor;
import com.github.xsi640.queryplus.exception.ExpressionArgumentException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author SuYang
 */
public class SqlParameterExpression implements ParamExpression {

    @Getter
    private String name;
    @Getter
    private Object value;

    public SqlParameterExpression(String name, Object value, boolean isList) {
        ExpressionArgumentException.check(StringUtils.isBlank(name), "The SqlParameter name can't null or empty.");
        ExpressionArgumentException.check(value == null, "The SqlParameter value can't null.");

        this.name = name;
        this.value = value;
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onSqlParameter(this, context);
    }

    @Override
    public int priority() {
        return 200;
    }
}
