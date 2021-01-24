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
public class Limit {
    private ParamExpression start;
    private ParamExpression count;

    public static Limit of(ParamExpression start, ParamExpression count) {
        Limit limit = new Limit();
        limit.setStart(start);
        limit.setCount(count);
        return limit;
    }
}
