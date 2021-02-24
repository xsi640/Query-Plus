package com.github.xsi640.queryplus.core

import com.github.xsi640.queryplus.core.ast.Expression
import com.github.xsi640.queryplus.core.ast.LiteralExpression
import com.github.xsi640.queryplus.core.ast.enums.LikeMode
import com.github.xsi640.queryplus.core.builder.QueryTemplateBuilder
import com.github.xsi640.queryplus.core.visitor.Visitor
import java.math.BigDecimal
import java.time.Duration
import java.util.*

fun String.asLiteral(): LiteralExpression {
    return LiteralExpression(this)
}

fun String.escapeName(): String {
    return this
}

fun String.escapeTable(): String {
    val parts = this.split("\\.")
    if (parts.size > 1) {
        val builder = StringBuilder()
        builder.append(parts[0].escapeName())
        for (i in 1 until parts.size) {
            builder.append(".").append(parts[i].escapeName())
        }
    }
    return this.escapeName()
}

fun <T> QueryTemplateBuilder.buildSeparation(separate: String, iterable: Iterable<T>, run: (T) -> Unit) {
    val it = iterable.iterator()
    run(it.next())
    while (it.hasNext()) {
        this.append(separate)
        run(it.next())
    }
}

fun QueryTemplateBuilder.buildBrackets(
    hasBracket: Boolean,
    run: () -> Unit
) {
    if (hasBracket) {
        this.append('(')
        run()
        this.append(')')
    } else {
        run()
    }
}

fun QueryTemplateBuilder.buildObject(value: Any) {
    if (value is Int || value is Long) {
        this.append(value.toString())
    } else if (value is BigDecimal) {
        this.append(value.toPlainString())
    } else if (value is String) {
        this.append("'")
        this.append(value.escapeName())
        this.append("'")
    } else if (value is Boolean) {
        this.append(value.toString())
    } else if (value is Date) {
        this.append(value.time.toString())
    } else if (value is Duration) {
        this.append(value.toMillis().toString())
    } else {
        throw IllegalArgumentException()
    }
}

fun QueryTemplateBuilder.buildLikeOperate(
    ignoreCase: Boolean,
    run: () -> Unit
) {
    if (ignoreCase) {
        this.append("LOWER(")
        run()
        this.append(")")
    } else {
        run()
    }
}

fun QueryTemplateBuilder.buildLikeOperateString(
    s: String,
    mode: LikeMode,
    ignoreCase: Boolean
): String {
    val str = if (ignoreCase) {
        s.toLowerCase()
    } else {
        s
    }
    var result = ""
    if (mode == LikeMode.ENDS_WITH || mode == LikeMode.CONTAINS)
        result += "%$str"
    else if (mode == LikeMode.STARTS_WITH || mode == LikeMode.CONTAINS)
        result += "$str%"
    return result
}
