package com.github.xsi640.queryplus.core.ast

import com.github.xsi640.queryplus.core.ast.enums.*
import com.github.xsi640.queryplus.core.visitor.Visitor

interface Expression {
    fun <C, R> visit(context: C, visitor: Visitor<C, R>): R

    fun <R> visit(visitor: Visitor<Unit, R>): R {
        return this.visit(Unit, visitor)
    }

    fun priority(): Int

    fun condition(whens: List<Expression>): Expression {
        return OperatorExpression(this, FuncOperator.CONDITION.name, whens)
    }

    fun condition(vararg whens: Expression): Expression {
        return this.condition(listOf(*whens))
    }

    fun operate(opr: Operator, parameters: List<Expression>): Expression {
        return OperatorExpression(this, opr.value, parameters)
    }

    fun operate(opr: Operator, vararg parameters: Expression): Expression {
        return OperatorExpression(this, opr.value, listOf(*parameters))
    }

    fun and(right: Expression): Expression {
        return this.operate(LogicOperator.AND, listOf(right))
    }

    fun or(right: Expression): Expression {
        return this.operate(LogicOperator.OR, listOf(right))
    }

    operator fun not(): Expression {
        return this.operate(LogicOperator.NOT, EmptyExpression)
    }

    fun lt(right: Expression): Expression {
        return this.operate(CompareOperator.LT, right)
    }

    fun gt(right: Expression): Expression {
        return this.operate(CompareOperator.GT, right)
    }

    fun lte(right: Expression): Expression {
        return this.operate(CompareOperator.LTE, right)
    }

    fun gte(right: Expression): Expression {
        return this.operate(CompareOperator.GTE, right)
    }

    fun eq(right: Expression): Expression {
        return this.operate(CompareOperator.EQ, right)
    }

    fun neq(right: Expression): Expression {
        return this.operate(CompareOperator.NEQ, right)
    }

    fun neq2(right: Expression): Expression {
        return this.operate(CompareOperator.NEQ2, right)
    }

    fun plus(right: Expression): Expression {
        return this.operate(MathOperator.PLUS, right)
    }

    fun sub(right: Expression): Expression {
        return this.operate(MathOperator.SUB, right)
    }

    fun mul(right: Expression): Expression {
        return this.operate(MathOperator.MUL, right)
    }

    fun div(right: Expression): Expression {
        return this.operate(MathOperator.DIV, right)
    }

    fun mod(right: Expression): Expression {
        return this.operate(MathOperator.MOD, right)
    }

    fun cast(dataType: String): Expression {
        return this.operate(FuncOperator.CAST, LiteralExpression(dataType))
    }

    fun startsWith(right: Expression): Expression {
        return this.operate(FuncOperator.startsWith, right)
    }

    fun startsWithIgnoreCase(right: Expression): Expression {
        return this.operate(FuncOperator.startsWithIgnoreCase, right)
    }

    fun endsWith(right: Expression): Expression {
        return this.operate(FuncOperator.endsWith, right)
    }

    fun endsWithIgnoreCase(right: Expression): Expression {
        return this.operate(FuncOperator.endsWithIgnoreCase, right)
    }

    fun contains(right: Expression): Expression {
        return this.operate(FuncOperator.contains, right)
    }

    fun containsIgnoreCase(right: Expression): Expression {
        return this.operate(FuncOperator.containsIgnoreCase, right)
    }

    fun sqlIn(exprs: List<Expression>): Expression {
        return this.operate(FuncOperator.IN, exprs)
    }

    fun sqlIn(vararg exprs: Expression): Expression {
        return this.operate(FuncOperator.IN, listOf(*exprs))
    }
}

object EmptyExpression : Expression {
    override fun <C, R> visit(context: C, visitor: Visitor<C, R>): R {
        return visitor.onEmpty(context, this)
    }

    override fun priority(): Int {
        return 200
    }

}