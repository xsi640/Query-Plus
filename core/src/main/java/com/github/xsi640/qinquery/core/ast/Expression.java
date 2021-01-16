package com.github.xsi640.qinquery.core.ast;

import java.io.Serializable;

/**
 * @author SuYang
 */
public interface Expression<T> extends Serializable {
    <R, C> R accept(Visitor<R, C> visitor, C context);

    Class<? extends T> type();
}
