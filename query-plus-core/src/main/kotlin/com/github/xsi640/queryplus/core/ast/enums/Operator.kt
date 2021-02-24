package com.github.xsi640.queryplus.core.ast.enums

interface Operator {
    val priority: Int
    val value: String

    companion object {
        fun of(operator: String): Operator? {
            val arrays = mutableListOf<Operator>()
            CompareOperator.values().forEach {
                if (it.name.equals(operator, true))
                    return it
            }
            FuncOperator.values().forEach {
                if (it.name.equals(operator, true))
                    return it
            }
            LogicOperator.values().forEach {
                if (it.name.equals(operator, true))
                    return it
            }
            MathOperator.values().forEach {
                if (it.name.equals(operator, true))
                    return it
            }
            return null
        }
    }
}

enum class CompareOperator(override val priority: Int) : Operator {
    LT(40),
    GT(40),
    LTE(40),
    GTE(40),
    EQ(40),
    NEQ(40),
    NEQ2(40);

    override val value: String
        get() = name
}

enum class FuncOperator(override val priority: Int) : Operator {
    IN(90),
    CAST(90),
    CONDITION(90),
    startsWith(90),
    startsWithIgnoreCase(90),
    endsWith(90),
    endsWithIgnoreCase(90),
    contains(90),
    containsIgnoreCase(90);

    override val value: String
        get() = name
}

enum class LogicOperator(override val priority: Int) : Operator {
    AND(30),
    OR(30),
    NOT(30);

    override val value: String
        get() = name
}

enum class MathOperator(override val priority: Int) : Operator {
    PLUS(50),
    SUB(50),
    MUL(51),
    DIV(51),
    MOD(51);

    override val value: String
        get() = name
}