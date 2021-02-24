package com.github.xsi640.queryplus.core.dialect

import org.omg.CORBA.Object

abstract class Segment

class StatmentSegment(val segment: String) : Segment()

class ParameterSegment(
    val name: String?,
    val isList: Boolean = false,
    val value: Object,
    val converter: ((Any) -> (Any))?
)