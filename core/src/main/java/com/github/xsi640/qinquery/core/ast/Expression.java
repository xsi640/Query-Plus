package com.github.xsi640.qinquery.core.ast;

import java.io.Serializable;

/**
 * @author SuYang
 */
public interface Expression extends Serializable {
    <R, C> R accept(Visitor<R, C> visitor, C context);

    int priority();
}
