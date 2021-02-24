package com.github.xsi640.queryplus.core.visitor

import com.github.xsi640.queryplus.core.ast.*
import com.github.xsi640.queryplus.core.ast.enums.UnionMode
import com.github.xsi640.queryplus.core.buildSeparation
import com.github.xsi640.queryplus.core.builder.QueryTemplateBuilder

open class QueryVisitor(builder: QueryTemplateBuilder) : ExpressionVisitor(builder) {
    override fun onQuery(context: Unit, expr: QueryExpression) {
        onSelect(expr.selects.value, expr.count, expr.distinct)
        onFrom(expr.tables.value)
        onJoins(expr.joins.value)
        onWhere(expr.where)
        onGroup(expr.groups.value)
        onHaving(expr.having)
        onOrders(expr.orders.value)
        onLimit(expr.limit)
        onUnions(expr.unions.value)
        onForUpdate(expr.forUpdate)
    }

    open fun onSelect(selects: MutableList<Select>, count: Boolean, distinct: Boolean) {
        builder.append("SELECT ")
        if (count) {
            builder.append("COUNT(")
        }
        if (distinct) {
            builder.append("DISTINCT ")
        }
        if (selects.isEmpty()) {
            builder.append(if (count && !distinct) "1" else "*")
        } else {
            builder.buildSeparation(", ", selects) {
                it.expression.visit(this)
                if (!it.alias.isNullOrEmpty()) {
                    builder.append(" AS ").append(it.alias)
                }
            }
        }
        if (count) {
            builder.append(")")
        }
    }

    open fun onFrom(tables: MutableList<BaseTable>) {
        if (tables.isNotEmpty()) {
            builder.append(" FROM ")
            onTableAndAlias(tables)
        }
    }

    open fun onTableAndAlias(tables: MutableList<BaseTable>) {
        val it = tables.iterator()
        while (it.hasNext()) {
            val table = it.next()
            if (table is Table) {
                onTableAndAlias(table.schema, table.table, table.alias)
            } else if (table is QueryTable) {
                builder.append('(')
                onQuery(Unit, table.query)
                builder.append(')')
                if (!table.alias.isNullOrEmpty()) {
                    builder.append(" AS ").append(table.alias)
                }
            }
            if (it.hasNext())
                builder.append(", ")
        }
    }

    open fun onTableAndAlias(schema: String?, table: String, alias: String?) {
        if (!schema.isNullOrEmpty()) {
            builder.append(schema).append(".")
        }
        builder.append(table)
        if (!alias.isNullOrEmpty()) {
            builder.append(" AS ").append(alias)
        }
    }

    open fun onJoins(joins: MutableList<Join>) {
        joins.forEach { j ->
            builder.append(" ").append(j.mode.name).append(" JOIN ")
            val table = j.table
            if (table is Table) {
                onTableAndAlias(table.schema, table.table, table.alias)
            } else if (table is QueryTable) {
                builder.append('(')
                onQuery(Unit, table.query)
                builder.append(')')
                if (!table.alias.isNullOrEmpty()) {
                    builder.append(" AS ").append(table.alias)
                }
            }
            builder.append(" ON ")
            j.on.visit(this)
        }
    }

    open fun onWhere(where: Expression?) {
        if (where != null) {
            builder.append(" WHERE ")
            where.visit(this)
        }
    }

    open fun onGroup(groups: MutableList<Expression>) {
        if (groups.isNotEmpty()) {
            builder.append(" GROUP BY ")
            builder.buildSeparation(", ", groups) { g ->
                g.visit(this)
            }
        }
    }

    open fun onHaving(having: Expression?) {
        if (having != null) {
            builder.append(" HAVING ")
            having.visit(this)
        }
    }

    open fun onOrders(orders: MutableList<Order>) {
        if (orders.isNotEmpty()) {
            builder.append(" ORDER BY ")
            builder.buildSeparation(", ", orders) { o ->
                o.expression.visit(this)
                builder.append(' ').append(o.mode.name)
            }
        }
    }

    open fun onLimit(limit: Limit?) {
        if (limit != null) {
            builder.append(" LIMIT ")
            limit.start.visit(this)
            builder.append(", ")
            limit.count.visit(this)
        }
    }

    open fun onUnions(unions: MutableList<Union>) {
        unions.forEach { u ->
            builder.append(" UNION ")
            if (u.mode == UnionMode.ALL)
                builder.append("ALL ")
            builder.append('(')
            onQuery(Unit, u.query)
            builder.append(')')
        }
    }

    open fun onForUpdate(forUpdate: Boolean) {
        if (forUpdate) {
            builder.append(" FOR UPDATE")
        }
    }
}