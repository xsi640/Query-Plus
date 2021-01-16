package com.github.xsi640.qinquery.core.ast;

/**
 * @author SuYang
 */
public final class SymbolExpression implements Expression {

    private final String symbol;

    public SymbolExpression(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException("the symbol not empty.");
        }
        this.symbol = symbol;
    }

    @Override
    public <R, C> R accept(Visitor<R, C> visitor, C context) {
        return null;
    }

    @Override
    public int priority() {
        return 0;
    }
}
