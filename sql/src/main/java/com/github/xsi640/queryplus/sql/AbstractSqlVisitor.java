package com.github.xsi640.queryplus.sql;

import com.github.xsi640.queryplus.core.ast.*;
import com.github.xsi640.queryplus.core.visitor.AbstractVisitor;
import com.github.xsi640.queryplus.exception.ExpressionArgumentException;
import com.github.xsi640.queryplus.sql.utils.SqlVisitorUtils;
import com.github.xsi640.queryplus.sqlbuilder.SqlTemplateBuilder;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * @author SuYang
 */
public class AbstractSqlVisitor<C> extends AbstractVisitor<C> {

    private C context;
    private SqlTemplateBuilder builder;

    public AbstractSqlVisitor(SqlTemplateBuilder builder, C context) {
        this.builder = builder;
        this.context = context;
    }

    @Override
    public void onFunc(FuncExpression expr, C context) {
        String funcName = expr.getFuncName();
        FuncMode mode = FuncMode.valueOf(funcName);
        switch (mode) {
            case IN:
                this.onInExpression(expr.getMark(), expr.getParameters(), context);
                return;
            case STARTS_WITH:
            case STARTS_WITH_IGNORE_CASE:
            case ENDS_WITH:
            case ENDS_WITH_IGNORE_CASE:
            case CONTAINS:
            case CONTAINS_IGNORE_CASE:
                this.onLikeExpression(expr.getMark(), mode, expr.getParameters().get(0), false, context);
                return;
            case CAST:
                ExpressionArgumentException.check(!(expr.getParameters().get(0) instanceof ValueExpression), "The Function CAST parameter[0] must be FieldExpression of dataType.");
                ValueExpression valueExpr = (ValueExpression) expr.getParameters().get(0);
                this.onCastExpression(expr.getMark(), String.valueOf(valueExpr.getValue()), context);
                return;
            case IS_NULL:
                this.onIsNullExpression(expr.getParameters().get(0), context);
                return;
        }
        builder.append(funcName);
        builder.append("(");
        SqlVisitorUtils.buildSeparation(builder, ", ", expr.getParameters(), e -> e.accept(this, context));
        builder.append(")");
    }

    @Override
    public void onValue(ValueExpression expr, C context) {
        SqlVisitorUtils.buildValue(builder, expr.getValue());
    }

    @Override
    public void onUnary(UnaryExpression expr, C context) {
        UnaryOperator opr = expr.getOperator();
        switch (opr) {
            case NOT:
                builder.append(" NOT ");
                break;
        }
        ParamExpression e = expr.getExpression();
        if (opr.getPriority() >= e.priority()) {
            builder.append("(");
            e.accept(this, context);
            builder.append(")");
        } else {
            e.accept(this, context);
        }
    }

    @Override
    public void onBinary(BinaryExpression expr, C context) {
        BinaryOperator op = expr.getOperator();
        Expression left = expr.getLeft();
        Expression right = expr.getRight();
        if (op.getPriority() > left.priority()) {
            builder.append("(");
            left.accept(this, context);
            builder.append(")");
        } else {
            left.accept(this, context);
        }
        switch (op) {
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
                break;
            case EQ:
                builder.append(" = ");
                break;
            case NE:
                builder.append(" != ");
                break;
            case AND:
                builder.append(" AND ");
                break;
            case OR:
                builder.append(" OR ");
                break;
            default:
                throw new IllegalStateException();
        }
        if (op.getPriority() >= right.priority()) {
            builder.append("(");
            right.accept(this, context);
            builder.append(")");
        } else {
            right.accept(this, context);
        }
    }

    @Override
    public void onField(FieldExpression expr, C context) {
        builder.append(expr.getField());
    }

    @Override
    public void onSql(SqlExpression expr, C context) {

    }

    @Override
    public void onSqlParameter(SqlParameterExpression expr, C context) {
        builder.append(expr.getName(), expr.getValue(), null);
    }

    private void onIsNullExpression(ParamExpression expression, C context) {
        expression.accept(this, context);
        builder.append(" IS NULL ");
    }

    private void onCastExpression(ParamExpression mark, String dataType, C context) {
        builder.append(" CAST(");
        mark.accept(this, context);
        builder.append(" AS ");
        builder.append(dataType);
        builder.append(")");
    }

    private void onLikeExpression(ParamExpression mark, FuncMode mode, ParamExpression parameter, boolean caseInsensitive, C context) {
        builder.append("(");
        if (caseInsensitive) {
            builder.append("LOWER(");
            mark.accept(this, context);
            builder.append(")");
        } else {
            mark.accept(this, context);
        }
        builder.append(" LIKE ");
        if (parameter instanceof ValueExpression) {
            ValueExpression expr = (ValueExpression) parameter;
            String s = SqlVisitorUtils.buildLikeOprandString(String.valueOf(expr.getValue()), caseInsensitive, mode);
            builder.append("\'");
            builder.append(s);
            builder.append("\'");
        } else if (parameter instanceof SqlParameterExpression) {
            SqlParameterExpression expr = (SqlParameterExpression) parameter;
            builder.append(expr.getName(), expr.getValue(), null);
        } else {
            builder.append("CONCAT(");
            if (mode == FuncMode.ENDS_WITH || mode == FuncMode.ENDS_WITH_IGNORE_CASE || mode == FuncMode.CONTAINS) {
                builder.append("'%', ");
            }
            if (caseInsensitive) {
                builder.append("LOWER(");
                parameter.accept(this, context);
                builder.append(")");
            } else {
                parameter.accept(this, context);
            }
            if (mode == FuncMode.STARTS_WITH || mode == FuncMode.STARTS_WITH_IGNORE_CASE || mode == FuncMode.CONTAINS) {
                builder.append(", '%'");
            }
            builder.append(")");
        }
        builder.append(")");
    }

    private void onInExpression(ParamExpression mark, List<ParamExpression> parameters, C context) {
        builder.append("(");
        mark.accept(this, context);
        builder.append(" IN (");
        SqlVisitorUtils.buildSeparation(this.builder, ", ", parameters, e -> e.accept(this, context));
        builder.append(")");
    }

}
