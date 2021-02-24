package com.github.xsi640.queryplus.core.ast

import com.github.xsi640.queryplus.core.ast.enums.Operator
import com.github.xsi640.queryplus.core.visitor.Visitor

class OperatorExpression(
    val left: Expression,
    val operator: String,
    val right: List<Expression>?
) : Expression {
    override fun <C, R> visit(context: C, visitor: Visitor<C, R>): R {
        return visitor.onOperator(context, this)
    }

    override fun priority(): Int {
        val op = Operator.of(this.operator)
        if (op != null)
            return op.priority
        return 200
    }
}