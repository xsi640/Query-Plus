package com.github.xsi640.qinquery.core.ast;

import com.github.xsi640.qinquery.core.visitor.Visitor;

/**
 * @author SuYang
 */
public class SymbolExpression extends AbstractExpression {

    private final String symbol;

    public SymbolExpression(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException("the symbol not empty.");
        }
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onSymbol(this, context);
    }

    @Override
    public int priority() {
        return 90;
    }
}
