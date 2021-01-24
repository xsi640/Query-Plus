package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.ParamExpression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SuYang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Select {
    private ParamExpression expression;
    private String alias;

    public static Select of(ParamExpression expression, String alias) {
        Select select = new Select();
        select.setExpression(expression);
        select.setAlias(alias);
        return select;
    }
}
