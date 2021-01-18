package com.github.xsi640.queryplus.core;

import com.github.xsi640.queryplus.core.visitor.StringVisitor;
import com.github.xsi640.queryplus.core.ast.*;

/**
 * @author SuYang
 */
public class Application {
    public static void main(String[] args) {
        Expression expr = new FuncExpression("sum", new FuncExpression("abs", new ValueExpression(22)),
                new BinaryExpression(BinaryOperator.PLUS, new ValueExpression(1), new ValueExpression(1)));

        StringBuilder builder = new StringBuilder();
        StringVisitor<?> visitor = new StringVisitor<>(builder);
        expr.accept(visitor, null);
        System.out.println(builder.toString());
    }
}
