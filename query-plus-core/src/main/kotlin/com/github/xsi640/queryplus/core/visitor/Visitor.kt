package com.github.xsi640.queryplus.core.visitor

import com.github.xsi640.queryplus.core.ast.*

interface Visitor<C, out R> {
    fun onLiteral(context: C, expr: LiteralExpression): R
    fun onParameter(context: C, expr: ParameterExpression): R
    fun onOperator(context: C, expr: OperatorExpression): R
    fun onEmpty(context: C, expr: EmptyExpression): R
    fun onQuery(context: C, expr: QueryExpression): R
}