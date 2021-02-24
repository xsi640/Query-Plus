package com.github.xsi640.queryplus.core.ast

import com.github.xsi640.queryplus.core.visitor.Visitor
import java.lang.IllegalArgumentException

class ParameterExpression(
    val name: String?,
    val isList: Boolean = false,
    val value: Any
) : Expression {

    init {
        if (isList && value !is Collection<*>) {
            throw IllegalArgumentException("The ParameterExpression valus is not Collection and isList is true.")
        }
    }

    override fun <C, R> visit(context: C, visitor: Visitor<C, R>): R {
        return visitor.onParameter(context, this)
    }

    override fun priority(): Int {
        return 200;
    }
}