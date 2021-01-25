package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.ast.sql.*;
import com.github.xsi640.queryplus.core.visitor.Visitor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SuYang
 */
public class SqlExpression implements SqlComboExpression {

    @Getter
    private List<Table> tables = null;
    @Getter
    private List<Join> joins = null;
    @Getter
    private LogicExpression where = null;
    @Getter
    private List<FieldExpression> groups = null;
    @Getter
    private List<Order> orders = null;
    @Getter
    private Limit limit = null;
    @Getter
    private List<Select> selects = null;
    @Getter
    private boolean distinct = false;
    @Getter
    private boolean count = false;
    @Getter
    private boolean update = false;
    @Getter
    private boolean forUpdate = false;
    @Getter
    private Map<String, ParamExpression> updateMap = null;
    @Getter
    private Map<String, ParamExpression> insertMap = null;
    @Getter
    private boolean insert = false;
    @Getter
    private boolean delete = false;
    @Getter
    private List<String> deletes = null;

    @Override
    public QueryableExpression distinct() {
        this.distinct = true;
        return this;
    }

    @Override
    public ModifiableExpression where(LogicExpression expression) {
        this.where = expression;
        return this;
    }

    @Override
    public List<FieldExpression> groups() {
        return this.groups;
    }

    @Override
    public GroupableExpression group(FieldExpression expression) {
        if (this.groups == null)
            this.groups = new ArrayList<>();
        this.groups.add(expression);
        return this;
    }

    @Override
    public List<Join> joins() {
        return this.joins;
    }

    @Override
    public JoinableExpression join(JoinMode mode, String schema, FieldExpression table, String alias, LogicExpression on) {
        if (this.joins == null)
            this.joins = new ArrayList<>();
        this.joins.add(Join.of(mode, schema, table, alias, on));
        return this;
    }

    @Override
    public JoinableExpression leftJoin(FieldExpression table, String alias, LogicExpression on) {
        return this.join(JoinMode.LEFT, null, table, alias, on);
    }

    @Override
    public JoinableExpression rightJoin(FieldExpression table, String alias, LogicExpression on) {
        return this.join(JoinMode.RIGHT, null, table, alias, on);
    }

    @Override
    public JoinableExpression innerJoin(FieldExpression table, String alias, LogicExpression on) {
        return this.join(JoinMode.INNER, null, table, alias, on);
    }

    @Override
    public JoinableExpression leftJoin(FieldExpression table, LogicExpression on) {
        return this.leftJoin(table, null, on);
    }

    @Override
    public JoinableExpression rightJoin(FieldExpression table, LogicExpression on) {
        return this.rightJoin(table, null, on);
    }

    @Override
    public JoinableExpression innerJoin(FieldExpression table, LogicExpression on) {
        return this.innerJoin(table, null, on);
    }

    @Override
    public SelectableExpression limit(Limit limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public SelectableExpression limit(ParamExpression start, ParamExpression count) {
        this.limit = Limit.of(start, count);
        return this;
    }

    @Override
    public UpdatableExpression executeUpdate() {
        this.update = true;
        this.delete = false;
        this.insert = false;
        return this;
    }

    @Override
    public InsertableExpression executeInsert() {
        this.insert = true;
        this.update = false;
        this.delete = false;
        return this;
    }

    @Override
    public void delete(List<String> fields) {
        this.delete = true;
        this.insert = false;
        this.update = false;
        this.deletes = fields;
    }

    @Override
    public void delete() {
        this.delete = true;
        this.update = false;
        this.insert = false;
    }

    @Override
    public List<Order> orders() {
        return this.orders;
    }

    @Override
    public OrderableExpression order(Order order) {
        if (this.orders == null)
            this.orders = new ArrayList<>();
        this.orders.add(order);
        return this;
    }

    @Override
    public OrderableExpression order(OrderMode mode, FieldExpression expression) {
        return this.order(Order.of(mode, expression));
    }

    @Override
    public OrderableExpression asc(FieldExpression expression) {
        return this.order(OrderMode.ASC, expression);
    }

    @Override
    public OrderableExpression desc(FieldExpression expression) {
        return this.order(OrderMode.DESC, expression);
    }

    @Override
    public void count() {
        this.count = true;
    }

    @Override
    public void forUpdate() {
        this.forUpdate = true;
    }

    @Override
    public Limit limit() {
        return this.limit;
    }

    @Override
    public List<Select> selects() {
        return this.selects;
    }

    @Override
    public SelectableExpression select(Select select) {
        if (this.selects == null) {
            this.selects = new ArrayList<>();
        }
        this.selects.add(select);
        return this;
    }

    @Override
    public SelectableExpression select(ParamExpression expression) {
        return this.select(Select.of(expression, null));
    }

    @Override
    public SelectableExpression select(ParamExpression expression, String alias) {
        return this.select(Select.of(expression, alias));
    }

    @Override
    public void clear() {
        this.tables = null;
        this.joins = null;
        this.where = null;
        this.groups = null;
        this.orders = null;
        this.limit = null;
        this.selects = null;
        this.distinct = false;
        this.count = false;
        this.update = false;
        this.forUpdate = false;
        this.updateMap = null;
        this.insert = false;
        this.delete = false;
        this.deletes = null;
    }

    @Override
    public JoinableExpression from(String schema, FieldExpression from, String alias) {
        if (tables == null) {
            tables = new ArrayList<>();
        }
        tables.add(Table.of(schema, from, alias));
        return this;
    }

    @Override
    public JoinableExpression from(FieldExpression from, String alias) {
        return this.from(null, from, alias);
    }

    @Override
    public JoinableExpression from(FieldExpression from) {
        return this.from(null, from, null);
    }

    @Override
    public UpdatableExpression update(String field, ParamExpression expression) {
        if (this.updateMap == null) {
            this.updateMap = new HashMap<>();
        }
        this.updateMap.put(field, expression);
        return this;
    }

    @Override
    public UpdatableExpression insert(String field, FieldExpression expression) {
        if (this.insertMap == null) {
            this.insertMap = new HashMap<>();
        }
        this.insertMap.put(field, expression);
        return this;
    }

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {
        visitor.onSql(this, context);
    }

    @Override
    public int priority() {
        return 200;
    }
}
