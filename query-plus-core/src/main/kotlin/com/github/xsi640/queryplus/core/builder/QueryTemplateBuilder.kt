package com.github.xsi640.queryplus.core.builder

import com.github.xsi640.queryplus.core.dialect.Segment

interface QueryTemplateBuilderFactory {
    fun create(): QueryTemplateBuilder
}

interface QueryTemplateBuilder {
    fun append(csq: CharSequence): QueryTemplateBuilder

    fun append(c: Char): QueryTemplateBuilder

    fun appendParameter(
        name: String?,
        isList: Boolean = false,
        value: Any,
        converter: ((Any) -> (Any))? = null
    ): QueryTemplateBuilder

    fun build(): List<Segment>
}