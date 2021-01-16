package com.github.xsi640.qinquery.core.ast;

import java.io.Serializable;

/**
 * @author SuYang
 */
public interface Operator extends Serializable {
    String name();

    Class<?> type();
}
