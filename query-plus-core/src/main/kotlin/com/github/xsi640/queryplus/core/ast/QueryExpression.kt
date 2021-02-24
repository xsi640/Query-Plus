package com.github.xsi640.queryplus.core.ast

import com.github.xsi640.queryplus.core.ast.enums.JoinMode
import com.github.xsi640.queryplus.core.ast.enums.OrderMode
import com.github.xsi640.queryplus.core.ast.enums.UnionMode
import com.github.xsi640.queryplus.core.visitor.Visitor

abstract class BaseTable

class Table(
    val schema: String?,
    val table: String,
    val alias: String?
) : BaseTable()

class QueryTable(
    val query: QueryExpression,
    val alias: String?
) : BaseTable()

class Join(
    val mode: JoinMode,
    val table: BaseTable,
    val on: Expression
)

class Limit(
    val start: Expression,
    val count: Expression
)

class Order(
    val mode: OrderMode,
    val expression: Expression
)

class Select(
    val expression: Expression,
    val alias: String?
)

class Union(
    val mode: UnionMode,
    val query: QueryExpression
)

class QueryExpression : Expression {
    var tables = lazy { mutableListOf<BaseTable>() }
    var joins = lazy { mutableListOf<Join>() }
    var where: Expression? = null
    var groups = lazy { mutableListOf<Expression>() }
    var having: Expression? = null
    var orders = lazy { mutableListOf<Order>() }
    var limit: Limit? = null
    var selects = lazy { mutableListOf<Select>() }
    var distinct = false
    var count = false
    var forUpdate = false
    var unions = lazy { mutableListOf<Union>() }

    override fun <C, R> visit(context: C, visitor: Visitor<C, R>): R {
        return visitor.onQuery(context, this)
    }

    override fun priority(): Int {
        return 200
    }
}