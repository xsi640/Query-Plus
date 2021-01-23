package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.ParamExpression;
import lombok.Data;

/**
 * @author SuYang
 */
@Data
public class Select {
    private ParamExpression expression;
    private String alias;
}
