package com.github.xsi640.queryplus.core.dialect

import com.github.xsi640.queryplus.core.ast.QueryExpression

interface SqlDialect {
    fun buildQuery(query: QueryExpression): List<Segment>
}