package com.github.xsi640.queryplus.mysql

import com.github.xsi640.queryplus.core.ast.QueryExpression
import com.github.xsi640.queryplus.core.builder.QueryTemplateBuilder
import com.github.xsi640.queryplus.core.builder.QueryTemplateBuilderFactory
import com.github.xsi640.queryplus.core.dialect.Segment
import com.github.xsi640.queryplus.core.dialect.SqlDialect
import com.github.xsi640.queryplus.core.visitor.QueryVisitor

class MySqlDialect(
    val factory: QueryTemplateBuilderFactory
) : SqlDialect {

    override fun buildQuery(query: QueryExpression): List<Segment> {
        val builder = factory.create()
        val mySqlVisitor = MySqlVisitor(builder)
        mySqlVisitor.onQuery(Unit, query)
        return builder.build()
    }

    inner class MySqlVisitor(builder: QueryTemplateBuilder) : QueryVisitor(builder) {
        override fun onTableAndAlias(schema: String?, table: String, alias: String?) {

        }
    }
}