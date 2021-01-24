package com.github.xsi640.queryplus.sql.utils;

import com.github.xsi640.queryplus.sql.FuncMode;
import com.github.xsi640.queryplus.sqlbuilder.SqlTemplateBuilder;
import com.github.xsi640.queryplus.utils.Action;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;

/**
 * @author SuYang
 */
public class SqlVisitorUtils {
    public static <T> void buildSeparation(SqlTemplateBuilder builder, String separate, Iterable<T> iterable, Action<T> func) {
        Iterator<T> it = iterable.iterator();
        func.run(it.next());
        while (it.hasNext()) {
            builder.append(separate);
            func.run(it.next());
        }
    }

    public static String buildLikeOprandString(String s, boolean caseInsensitive, FuncMode mode) {
        String str = s;
        if (caseInsensitive) {
            str = str.toLowerCase();
        }
        if (mode == FuncMode.ENDS_WITH || mode == FuncMode.ENDS_WITH_IGNORE_CASE || mode == FuncMode.CONTAINS) {
            str = "%" + str;
        }
        if (mode == FuncMode.STARTS_WITH || mode == FuncMode.STARTS_WITH_IGNORE_CASE || mode == FuncMode.CONTAINS) {
            str += "%";
        }
        return str;
    }

    public static void buildValue(SqlTemplateBuilder builder, Object value) {
        if (value instanceof Integer ||
                value instanceof Long) {
            builder.append(String.valueOf(value));
        } else if (value instanceof BigDecimal) {
            builder.append(((BigDecimal) value).toPlainString());
        } else if (value instanceof String) {
            builder.append("'" + value + "'");
        } else if (value instanceof Boolean) {
            builder.append(String.valueOf(value));
        } else if (value instanceof Date) {
            builder.append(String.valueOf(((Date) value).getTime()));
        } else if (value instanceof Duration) {
            builder.append(String.valueOf(((Duration) value).toMillis()));
        } else {
            throw new IllegalArgumentException("The value can't to sql string.");
        }
    }
}
