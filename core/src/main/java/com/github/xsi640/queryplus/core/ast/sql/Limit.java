package com.github.xsi640.queryplus.core.ast.sql;

import com.github.xsi640.queryplus.core.ast.ParamExpression;
import lombok.Data;

/**
 * @author SuYang
 */
@Data
public class Limit {
    private ParamExpression start;
    private ParamExpression count;
}
