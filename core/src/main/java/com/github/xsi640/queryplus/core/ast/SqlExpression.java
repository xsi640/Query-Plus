package com.github.xsi640.queryplus.core.ast;

import com.github.xsi640.queryplus.core.ast.sql.*;
import com.github.xsi640.queryplus.core.visitor.Visitor;

import java.util.List;

/**
 * @author SuYang
 */
public class SqlExpression implements SqlComboExpression {

    private FieldExpression schema = null;
    private FieldExpression table = null;
    private String tableAlias = null;
    private List<Join> joins = null;
    private ParamExpression where = null;
    private List<FieldExpression> groups = null;
    private List<Order> orders = null;
    private Limit limit = null;
    private List<Select> selects = null;
    private boolean distinct = false;
    private boolean count = false;

    @Override
    public <C> void accept(Visitor<C> visitor, C context) {

    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public QueryableExpression distinct() {
        return null;
    }

    @Override
    public ModifiableExpression where(LogicExpression expression) {
        return null;
    }

    @Override
    public List<FieldExpression> groups() {
        return null;
    }

    @Override
    public GroupableExpression group(FieldExpression expression) {
        return null;
    }

    @Override
    public List<Join> joins() {
        return null;
    }

    @Override
    public JoinableExpression join(JoinMode mode, FieldExpression table, String alias, LogicExpression on) {
        return null;
    }

    @Override
    public JoinableExpression leftJoin(FieldExpression table, String alias, LogicExpression on) {
        return null;
    }

    @Override
    public JoinableExpression rightJoin(FieldExpression table, String alias, LogicExpression on) {
        return null;
    }

    @Override
    public JoinableExpression innerJoin(FieldExpression table, String alias, LogicExpression on) {
        return null;
    }

    @Override
    public JoinableExpression leftJoin(FieldExpression table, LogicExpression on) {
        return null;
    }

    @Override
    public JoinableExpression rightJoin(FieldExpression table, LogicExpression on) {
        return null;
    }

    @Override
    public JoinableExpression innerJoin(FieldExpression table, LogicExpression on) {
        return null;
    }

    @Override
    public SelectableExpression limit(Limit limit) {
        return null;
    }

    @Override
    public SelectableExpression limit(ParamExpression start, ParamExpression count) {
        return null;
    }

    @Override
    public UpdatableExpression update() {
        return null;
    }

    @Override
    public void delete(List<FieldExpression> fields) {

    }

    @Override
    public void delete() {

    }

    @Override
    public List<Order> orders() {
        return null;
    }

    @Override
    public OrderableExpression order(Order order) {
        return null;
    }

    @Override
    public OrderableExpression order(OrderMode mode, FieldExpression expression) {
        return null;
    }

    @Override
    public OrderableExpression asc(FieldExpression expression) {
        return null;
    }

    @Override
    public OrderableExpression desc(FieldExpression expression) {
        return null;
    }

    @Override
    public void count() {

    }

    @Override
    public void forUpdate() {

    }

    @Override
    public Limit limit() {
        return null;
    }

    @Override
    public List<Select> selects() {
        return null;
    }

    @Override
    public SelectableExpression select(Select select) {
        return null;
    }

    @Override
    public SelectableExpression select(ParamExpression expression) {
        return null;
    }

    @Override
    public SelectableExpression select(ParamExpression expression, String alias) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public JoinableExpression from(FieldExpression from, String alias) {
        return null;
    }

    @Override
    public JoinableExpression from(FieldExpression from) {
        return null;
    }

    @Override
    public UpdatableExpression set(String field, ParamExpression expression) {
        return null;
    }
}
