package com.github.xsi640.queryplus.core.visitor;

import com.github.xsi640.qinquery.core.ast.*;
import com.github.xsi640.queryplus.core.ast.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * @author SuYang
 */
public class StringVisitor<C> extends AbstractVisitor<C> {

    private StringBuilder builder;

    public StringVisitor(StringBuilder builder) {
        this.builder = builder;
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    @Override
    public void onFunc(FuncExpression expr, C context) {
        SymbolExpression func = expr.getFunc();
        Expression mark = expr.getMark();
        List<ParamExpression> params = expr.getParameters();
        buildWithParenthesis(mark, func.priority() > mark.priority(), context);
        if (func.getSymbol().equals(Operator.PLUS.name())) {
            builder.append(" + ");
        } else {
            func.accept(this, context);
        }
        for (ParamExpression param : params) {
            buildWithParenthesis(param, func.priority() >= param.priority(), context);
        }
    }

    @Override
    public void onValue(ValueExpression expr, C context) {
        Object value = expr.getValue();
        if (value instanceof Integer ||
                value instanceof Long) {
            builder.append(value.toString());
        } else if (value instanceof BigDecimal) {
            builder.append(((BigDecimal) value).toPlainString());
        } else if (value instanceof String) {
            builder.append("'");
            builder.append(value.toString());
            builder.append("'");
        } else if (value instanceof Boolean) {
            builder.append(value.toString());
        } else if (value instanceof Date) {
            builder.append(((Date) value).getTime());
        } else if (value instanceof Duration) {
            builder.append(((Duration) value).toMillis());
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void onSymbol(SymbolExpression expr, C context) {
        builder.append(expr.getSymbol());
    }

    private void buildWithParenthesis(Expression expression, boolean hasParenthesis, C context) {
        if (hasParenthesis) {
            builder.append("(");
            expression.accept(this, context);
            builder.append(")");
        } else {
            expression.accept(this, context);
        }
    }
}
