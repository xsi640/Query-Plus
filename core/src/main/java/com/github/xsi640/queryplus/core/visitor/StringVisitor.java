package com.github.xsi640.queryplus.core.visitor;

import com.github.xsi640.queryplus.core.ast.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author SuYang
 */
public class StringVisitor<C> extends AbstractVisitor<C> {

    private final StringBuilder builder;

    public StringVisitor(StringBuilder builder) {
        this.builder = builder;
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    @Override
    public void onFunc(FuncExpression expr, C context) {
        String fucName = expr.getFuncName();
        builder.append(fucName).append("(");
        List<ParamExpression> params = expr.getParameters();
        Iterator<ParamExpression> it = params.listIterator();
        while (it.hasNext()) {
            ParamExpression p = it.next();
            p.accept(this, context);
            if (it.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append(")");
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
    public void onUnary(UnaryExpression expr, C context) {
        UnaryOperator operator = expr.getOperator();
        if (operator == UnaryOperator.NOT) {
            builder.append(" NOT ");
        }
        ParamExpression exp = expr.getExpression();
        buildWithParenthesis(exp, operator.getPriority() >= exp.priority(), context);
    }

    @Override
    public void onBinary(BinaryExpression expr, C context) {
        BinaryOperator operator = expr.getOperator();
        ParamExpression left = expr.getLeft();
        ParamExpression right = expr.getRight();
        buildWithParenthesis(left, operator.getPriority() > left.priority(), context);
        switch (operator) {
            case PLUS:
                builder.append(" + ");
                break;
            case SUB:
                builder.append(" - ");
                break;
            case MUL:
                builder.append(" * ");
                break;
            case DIV:
                builder.append(" / ");
                break;
            case LT:
                builder.append(" < ");
                break;
            case GT:
                builder.append(" > ");
                break;
            case LE:
                builder.append(" <= ");
                break;
            case GE:
                builder.append(" >= ");
            case EQ:
                builder.append(" = ");
            case NE:
                builder.append(" != ");
                break;
            case AND:
                builder.append(" AND ");
                break;
            case OR:
                builder.append(" OR ");
                break;
        }
        buildWithParenthesis(right, operator.getPriority() >= right.priority(), context);
    }

    @Override
    public void onField(FieldExpression expr, C context) {
        builder.append(expr.getField());
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
