package com.github.xsi640.queryplus.sql;

import com.github.xsi640.queryplus.core.ast.SqlExpression;
import com.github.xsi640.queryplus.core.ast.sql.Join;
import com.github.xsi640.queryplus.core.ast.sql.Table;
import com.github.xsi640.queryplus.sql.utils.SqlVisitorUtils;
import com.github.xsi640.queryplus.sqlbuilder.SqlTemplateBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author SuYang
 */
public class AbstractSqlBuilderVisitor<C> implements SqlBuilderVisitor<C> {

    private AbstractSqlVisitor<C> visitor;
    private SqlTemplateBuilder builder;

    public AbstractSqlBuilderVisitor(AbstractSqlVisitor<C> visitor) {
        this.visitor = visitor;
        this.builder = visitor.getBuilder();
    }

    @Override
    public void buildQuery(SqlExpression expr, C context) {
        buildQuery0(expr, context);
        buildFrom(expr, context);
        buildJoins(expr, context);
        buildWhere(expr, context);
        buildGroup(expr, context);
        buildOrderBy(expr, context);
        buildLimit(expr, context);
        buildForUpdate(expr, context);
    }

    @Override
    public void buildUpdate(SqlExpression expr, C context) {
        builder.append("UPDATE ");
        buildTableAndAlias(builder, expr.getTables());
        buildJoins(expr, context);
        buildUpdateMap(expr, context);
        buildWhere(expr, context);
    }

    @Override
    public void buildInsert(SqlExpression expr, C context) {
        builder.append("INSERT INTO");
        buildTableAndAlias(builder, expr.getTables());
        builder.append(" (");
        SqlVisitorUtils.buildSeparation(builder, ", ", expr.getInsertMap().keySet(), k -> builder.append(k));
        builder.append(") VALUES(");
        SqlVisitorUtils.buildSeparation(builder, ", ", expr.getInsertMap().keySet(), k -> expr.getInsertMap().get(k).accept(visitor, context));
        builder.append(")");
        if (!expr.getUpdateMap().isEmpty()) {
            buildUpdateMap(expr, context);
        }
    }

    @Override
    public void buildDelete(SqlExpression expr, C context) {
        this.buildDelete0(expr, context);
        this.buildFrom(expr, context);
        this.buildJoins(expr, context);
        this.buildWhere(expr, context);
    }

    private void buildForUpdate(SqlExpression expr, C context) {
        if (expr.isForUpdate()) {
            builder.append(" FOR UPDATE");
        }
    }

    private void buildLimit(SqlExpression expr, C context) {
        if (expr.getLimit() != null) {
            builder.append(" LIMIT ");
            expr.getLimit().getStart().accept(visitor, context);
            builder.append(", ");
            expr.getLimit().getCount().accept(visitor, context);
        }
    }

    private void buildOrderBy(SqlExpression expr, C context) {
        if (expr.getOrders() != null && !expr.getOrders().isEmpty()) {
            builder.append(" ORDER BY ");
            SqlVisitorUtils.buildSeparation(builder, ", ", expr.getOrders(), o -> {
                o.getField().accept(visitor, context);
                builder.append(" ").append(o.getMode().name());
            });
        }
    }

    private void buildGroup(SqlExpression expr, C context) {
        if (expr.getGroups() != null && !expr.getGroups().isEmpty()) {
            builder.append(" GROUP BY ");
            SqlVisitorUtils.buildSeparation(builder, ", ", expr.getGroups(), g -> {
                g.accept(visitor, context);
            });
        }
    }

    private void buildQuery0(SqlExpression expr, C context) {
        builder.append("SELECT ");
        if (expr.isCount()) {
            builder.append("COUNT(");
        }
        if (expr.isDistinct()) {
            builder.append("DISTINCT ");
        }
        if (expr.getSelects() == null || expr.getSelects().isEmpty()) {
            if (expr.isCount() && !expr.isDistinct()) {
                builder.append("1");
            } else {
                builder.append("*");
            }
        } else {
            SqlVisitorUtils.buildSeparation(builder, ", ", expr.getSelects(), s -> {
                s.getExpression().accept(visitor, context);
                if (!StringUtils.isBlank(s.getAlias())) {
                    builder.append(" AS ").append(s.getAlias());
                }
            });
        }
        if (expr.isCount()) {
            builder.append(")");
        }
    }

    private void buildUpdateMap(SqlExpression expr, C context) {
        builder.append(" SET ");
        SqlVisitorUtils.buildSeparation(builder, ", ", expr.getUpdateMap().keySet(), k -> {
            builder.append(k);
            builder.append(" = ");
            expr.getUpdateMap().get(k).accept(visitor, context);
        });
    }

    private void buildDelete0(SqlExpression expr, C context) {
        builder.append("DELETE ");
        Table table = expr.getTables().get(0);
        if (expr.getDeletes() == null || expr.getDeletes().isEmpty()) {
            if (StringUtils.isBlank(table.getAlias())) {
                builder.append(table.getTable().getField());
            } else {
                builder.append(table.getAlias());
            }
            if (expr.getJoins() != null && !expr.getJoins().isEmpty()) {
                builder.append(", ");
                SqlVisitorUtils.buildSeparation(builder, ", ", expr.getJoins(), j -> {
                    if (StringUtils.isBlank(j.getTableAlias())) {
                        builder.append(j.getTable().getField());
                    } else {
                        builder.append(j.getTableAlias());
                    }
                });
            }
        } else {
            SqlVisitorUtils.buildSeparation(builder, ", ", expr.getDeletes(), d -> builder.append(d));
        }
    }

    private void buildWhere(SqlExpression expr, C context) {
        if (expr.getWhere() != null) {
            builder.append(" WHERE ");
            expr.getWhere().accept(visitor, context);
        }
    }

    private void buildJoins(SqlExpression expr, C context) {
        for (Join join : expr.getJoins()) {
            builder.append(" ").append(join.getMode().name()).append(" JOIN ");
            buildTableAndAlias(this.builder, Collections.singletonList(Table.of(join.getSchema(), join.getTable(), join.getTableAlias())));
            builder.append(" ON ");
            join.getOn().accept(visitor, context);
        }
    }

    private void buildFrom(SqlExpression expr, C context) {
        builder.append(" FROM ");
        buildTableAndAlias(builder, expr.getTables());
    }

    private void buildTableAndAlias(SqlTemplateBuilder builder, List<Table> tables) {
        SqlVisitorUtils.buildSeparation(builder, ", ", tables, tb -> {
            if (!StringUtils.isBlank(tb.getSchema())) {
                builder.append(tb.getSchema()).append(".");
            }
            builder.append(tb.getTable().getField());
            if (!StringUtils.isBlank(tb.getAlias())) {
                builder.append(" AS ").append(tb.getAlias());
            }
        });
    }
}
