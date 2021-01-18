package com.github.xsi640.queryplus.core.visitor;

import com.github.xsi640.queryplus.core.ast.*;

/**
 * @author SuYang
 */
public interface Visitor<C> {
    void onFunc(FuncExpression expr, C context);

    void onValue(ValueExpression expr, C context);

    void onUnary(UnaryExpression expr, C context);

    void onBinary(BinaryExpression expr, C context);
}
