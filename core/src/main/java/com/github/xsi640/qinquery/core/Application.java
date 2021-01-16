package com.github.xsi640.qinquery.core;

import com.github.xsi640.qinquery.core.ast.*;
import com.github.xsi640.qinquery.core.visitor.StringVisitor;

/**
 * @author SuYang
 */
public class Application {
    public static void main(String[] args) {
        Expression expr = new FuncExpression(new SymbolExpression("abs"),
                new FuncExpression(new OperatorExpression(Operator.PLUS), new ValueExpression(1), new ValueExpression(1)));

        StringBuilder builder = new StringBuilder();
        StringVisitor<?> visitor = new StringVisitor<>(builder);
        expr.accept(visitor, null);
        System.out.println(builder.toString());
    }
}
