package com.github.xsi640.queryplus.sqlbuilder;

/**
 * @author SuYang
 */
public interface Converter<In, Out> {
    Out convert(In in);
}
