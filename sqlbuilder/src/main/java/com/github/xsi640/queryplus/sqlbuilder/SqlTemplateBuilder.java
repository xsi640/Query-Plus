package com.github.xsi640.queryplus.sqlbuilder;

import java.util.List;

/**
 * @author SuYang
 */
public interface SqlTemplateBuilder {
    SqlTemplateBuilder append(CharSequence sequence);

    <In, Out> SqlTemplateBuilder append(String name, In value, Converter<In, Out> converter);

    <In, Out> SqlTemplateBuilder appendList(String name, List<In> list, Converter<List<In>, List<Out>> converter);
}
