package com.github.xsi640.queryplus.core.visitor

import com.github.xsi640.queryplus.core.ast.*
import com.github.xsi640.queryplus.core.ast.enums.CompareOperator
import com.github.xsi640.queryplus.core.ast.enums.LikeMode
import com.github.xsi640.queryplus.core.ast.enums.LogicOperator
import com.github.xsi640.queryplus.core.ast.enums.MathOperator
import com.github.xsi640.queryplus.core.buildBrackets
import com.github.xsi640.queryplus.core.buildLikeOperate
import com.github.xsi640.queryplus.core.buildLikeOperateString
import com.github.xsi640.queryplus.core.builder.QueryTemplateBuilder
import java.lang.IllegalArgumentException

abstract class ExpressionVisitor(
    val builder: QueryTemplateBuilder
) : AbstractVisitor<Unit, Unit>() {
    override fun onCompare(context: Unit, left: Expression, operator: CompareOperator, right: Expression) {
        builder.buildBrackets(operator.priority > left.priority()) {
            left.visit(this)
        }
        when (operator) {
            CompareOperator.LT -> builder.append(" < ")
            CompareOperator.GT -> builder.append(" > ")
            CompareOperator.LTE -> builder.append(" <= ")
            CompareOperator.GTE -> builder.append(" >= ")
            CompareOperator.EQ -> builder.append(" = ")
            CompareOperator.NEQ -> builder.append(" <> ")
            CompareOperator.NEQ2 -> builder.append(" != ")
        }
        builder.buildBrackets(operator.priority >= right.priority()) {
            right.visit(this)
        }
    }

    override fun onLogic(context: Unit, left: Expression, operator: LogicOperator, right: Expression?) {
        if (operator == LogicOperator.NOT) {
            builder.append("NOT ")
            builder.buildBrackets(operator.priority >= left.priority()) {
                left.visit(this)
            }
        } else if (right != null) {
            builder.buildBrackets(operator.priority > left.priority()) {
                right.visit(this)
            }
            when (operator) {
                LogicOperator.AND -> builder.append(" AND ")
                LogicOperator.OR -> builder.append(" OR ")
                LogicOperator.NOT -> builder.append(" NOT ")
            }
            builder.buildBrackets(operator.priority >= right.priority()) {
                right.visit(this)
            }
        }
    }

    override fun onMath(context: Unit, left: Expression, operator: MathOperator, parameters: List<Expression>) {
        builder.buildBrackets(operator.priority > left.priority()) {
            left.visit(this)
        }
        parameters.forEach { p ->
            when (operator) {
                MathOperator.PLUS -> builder.append(" + ")
                MathOperator.SUB -> builder.append(" - ")
                MathOperator.MUL -> builder.append(" * ")
                MathOperator.DIV -> builder.append(" / ")
                MathOperator.MOD -> builder.append(" % ")
            }
            builder.buildBrackets(operator.priority >= p.priority()) {
                p.visit(this)
            }
        }
    }

    override fun onLike(context: Unit, left: Expression, pattern: Expression, mode: LikeMode, ignoreCase: Boolean) {
        builder.buildLikeOperate(ignoreCase) {
            left.visit(this)
        }
        builder.append(" LIKE ")
        if (pattern is ParameterExpression) {
            builder.appendParameter(pattern.name, pattern.isList, pattern.value) {
                if (it !is String) {
                    throw IllegalArgumentException("The Parameter value must be String.")
                }
                builder.buildLikeOperateString(it, mode, ignoreCase)
            }
        } else {
            builder.append("CONCAT(")
            if (mode == LikeMode.ENDS_WITH || mode == LikeMode.CONTAINS)
                builder.append("'%', ")
            builder.buildLikeOperate(ignoreCase) {
                pattern.visit(this)
            }
            if (mode == LikeMode.STARTS_WITH || mode == LikeMode.CONTAINS)
                builder.append(", '%'")
            builder.append(')')
        }
    }

    override fun onSqlIn(context: Unit, left: Expression, parameters: List<Expression>) {
        left.visit(this)
        builder.append(" IN (")
        val it = parameters.iterator()
        while (it.hasNext()) {
            it.next().visit(this)
            if (it.hasNext())
                builder.append(", ")
        }
        builder.append(')')
    }

    override fun onCast(context: Unit, left: Expression, dataType: String) {
        builder.append("CAST(")
        left.visit(this)
        builder.append(" AS ")
        builder.append(dataType).append(')')
    }

    override fun onCase(context: Unit, left: Expression, parameters: List<Expression>) {
        builder.append("(CASE ")
        left.visit(this)
        val it = parameters.iterator()
        while (it.hasNext()) {
            val when0 = it.next()
            var then = if (it.hasNext()) {
                it.next()
            } else {
                EmptyExpression
            }
            if (then != EmptyExpression) {
                builder.append(" WHEN ")
                when0.visit(this)
                builder.append(" THEN ")
                then.visit(this)
            } else {
                builder.append(" ELSE ")
                when0.visit(this)
            }
        }
        builder.append(" END)")
    }

    override fun onFunc(context: Unit, left: Expression, funcName: String, parameters: List<Expression>?) {
        builder.append(funcName).append('(')
        left.visit(this)
        if (parameters != null) {
            val it = parameters.iterator()
            while (it.hasNext()) {
                it.next().visit(this)
                if (it.hasNext())
                    builder.append(", ")
            }
        }
        builder.append(')')
    }

    override fun onLiteral(context: Unit, expr: LiteralExpression) {
        builder.append(expr.value)
    }

    override fun onParameter(context: Unit, expr: ParameterExpression) {
        builder.appendParameter(expr.name, expr.isList, expr.value)
    }

    override fun onEmpty(context: Unit, expr: EmptyExpression) {

    }
}