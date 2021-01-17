package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.visitor.Visitor;

import java.io.Serializable;

/**
 * @author SuYang
 */
public interface Expression extends Serializable {
    <C> void accept(Visitor<C> visitor, C context);

    int priority();
}
