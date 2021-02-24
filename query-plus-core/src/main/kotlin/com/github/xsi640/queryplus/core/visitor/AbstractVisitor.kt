package com.github.xsi640.queryplus.core.visitor

import com.github.xsi640.queryplus.core.ast.*
import com.github.xsi640.queryplus.core.ast.enums.*

abstract class AbstractVisitor<C, out R> : Visitor<C, R> {

    protected abstract fun onCompare(context: C, left: Expression, operator: CompareOperator, right: Expression): R

    protected abstract fun onLogic(context: C, left: Expression, operator: LogicOperator, right: Expression?): R

    protected abstract fun onMath(
        context: C,
        left: Expression,
        operator: MathOperator,
        parameters: List<Expression>
    ): R

    protected abstract fun onLike(
        context: C, left: Expression, pattern: Expression, mode: LikeMode,
        ignoreCase: Boolean
    ): R

    protected abstract fun onSqlIn(context: C, left: Expression, parameters: List<Expression>): R

    protected abstract fun onCast(context: C, left: Expression, dataType: String): R

    protected abstract fun onCase(context: C, left: Expression, parameters: List<Expression>): R

    protected abstract fun onFunc(context: C, left: Expression, funcName: String, parameters: List<Expression>?): R

    override fun onOperator(context: C, expr: OperatorExpression): R {
        return when (val operator = Operator.of(expr.operator)) {
            is CompareOperator -> {
                onCompare(context, expr.left, operator, expr.right!![0])
            }
            is LogicOperator -> {
                onLogic(context, expr.left, operator, expr.right?.get(0))
            }
            is MathOperator -> {
                onMath(context, expr.left, operator, expr.right!!)
            }
            is FuncOperator -> {
                when (operator) {
                    FuncOperator.IN -> onSqlIn(context, expr.left, expr.right!!)
                    FuncOperator.CAST -> {
                        val literal = expr.right!![0] as LiteralExpression
                        return onCast(context, expr.left, literal.value)
                    }
                    FuncOperator.CONDITION -> onCase(context, expr.left, expr.right!!)
                    FuncOperator.startsWith -> onLike(
                        context,
                        expr.left,
                        expr.right!![0],
                        LikeMode.STARTS_WITH,
                        false
                    )
                    FuncOperator.startsWithIgnoreCase -> onLike(
                        context,
                        expr.left,
                        expr.right!![0],
                        LikeMode.STARTS_WITH,
                        true
                    )
                    FuncOperator.endsWith -> onLike(
                        context,
                        expr.left,
                        expr.right!![0],
                        LikeMode.ENDS_WITH,
                        false
                    )
                    FuncOperator.endsWithIgnoreCase -> onLike(
                        context,
                        expr.left,
                        expr.right!![0],
                        LikeMode.ENDS_WITH,
                        true
                    )
                    FuncOperator.contains -> onLike(
                        context,
                        expr.left,
                        expr.right!![0],
                        LikeMode.CONTAINS,
                        false
                    )
                    FuncOperator.containsIgnoreCase -> onLike(
                        context,
                        expr.left,
                        expr.right!![0],
                        LikeMode.CONTAINS,
                        true
                    )
                }
            }
            else -> {
                onFunc(context, expr.left, expr.operator, expr.right)
            }
        }
    }
}