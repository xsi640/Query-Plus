package com.github.xsi640.queryplus.core.visitor;

import com.github.xsi640.qinquery.core.ast.*;
import com.github.xsi640.queryplus.core.ast.*;

/**
 * @author SuYang
 */
public interface Visitor<C> {
    void onParam(ParamExpression expr, C context);

    void onFunc(FuncExpression expr, C context);

    void onValue(ValueExpression expr, C context);

    void onOperator(OperatorExpression expor, C context);

    void onSymbol(SymbolExpression expr, C context);
}
