package com.github.xsi640.queryplus.core.ast

import com.github.xsi640.queryplus.core.visitor.Visitor
import java.lang.IllegalArgumentException

class LiteralExpression(
    val value: String
) : Expression {
    init {
        if (value.isEmpty()) {
            throw IllegalArgumentException("The LiteralExpression value can't empty.")
        }
    }

    override fun <C, R> visit(context: C, visitor: Visitor<C, R>): R {
        return visitor.onLiteral(context, this)
    }

    override fun priority(): Int {
        return 200
    }
}