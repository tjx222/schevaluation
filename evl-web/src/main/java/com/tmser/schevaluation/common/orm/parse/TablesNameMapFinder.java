package com.tmser.schevaluation.common.orm.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.MySQLGroupConcat;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.OracleHint;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeKeyExpression;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.WithinGroupExpression;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.JsonOperator;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.OrderByVisitor;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.update.Update;

public class TablesNameMapFinder
    implements SelectVisitor, FromItemVisitor, ExpressionVisitor, ItemsListVisitor, SelectItemVisitor, OrderByVisitor {

  public static final String DEFAULT_ALIAS = "__ALIAS";
  private Map<String, String> tables;
  /**
   * There are special names, that are not table names but are parsed as
   * tables. These names are collected here and are not included in the tables
   * - names anymore.
   */
  private List<String> otherItemNames;

  /**
   * Main entry for this Tool class. A list of found tables is returned.
   *
   * @param delete
   * @return
   */
  public Map<String, String> getTableList(Delete delete) {
    init();
    Table t = delete.getTable();
    String alias = t.getAlias() != null ? t.getAlias().getName() : DEFAULT_ALIAS;
    tables.put(alias, t.getBaseName());
    if (delete.getWhere() != null) {
      delete.getWhere().accept(this);
    }

    return tables;
  }

  /**
   * Main entry for this Tool class. A list of found tables is returned.
   *
   * @param insert
   * @return
   */
  public Map<String, String> getTableList(Insert insert) {
    init();
    Table t = insert.getTable();
    String alias = t.getAlias() != null ? t.getAlias().getName() : DEFAULT_ALIAS;
    tables.put(alias, t.getBaseName());
    if (insert.getSelect() != null) {
      insert.getSelect().getSelectBody().accept(this);
    }
    if (insert.getItemsList() != null) {
      insert.getItemsList().accept(this);
    }

    return tables;
  }

  /**
   * Main entry for this Tool class. A list of found tables is returned.
   *
   * @param replace
   * @return
   */
  public Map<String, String> getTableList(Replace replace) {
    init();
    Table t = replace.getTable();
    String alias = t.getAlias() != null ? t.getAlias().getName() : DEFAULT_ALIAS;
    tables.put(alias, t.getBaseName());
    if (replace.getExpressions() != null) {
      for (Expression expression : replace.getExpressions()) {
        expression.accept(this);
      }
    }
    if (replace.getItemsList() != null) {
      replace.getItemsList().accept(this);
    }

    return tables;
  }

  /**
   * Main entry for this Tool class. A list of found tables is returned.
   *
   * @param select
   * @return
   */
  public Map<String, String> getTableList(Select select) {
    init();
    if (select.getWithItemsList() != null) {
      for (WithItem withItem : select.getWithItemsList()) {
        withItem.accept(this);
      }
    }
    select.getSelectBody().accept(this);

    return tables;
  }

  /**
   * Main entry for this Tool class. A list of found tables is returned.
   *
   * @param update
   * @return
   */
  public Map<String, String> getTableList(Update update) {
    init();
    for (Table t : update.getTables()) {
      String alias = t.getAlias() != null ? t.getAlias().getName() : DEFAULT_ALIAS;
      tables.put(alias, t.getBaseName());
    }
    if (update.getExpressions() != null) {
      for (Expression expression : update.getExpressions()) {
        expression.accept(this);
      }
    }

    if (update.getFromItem() != null) {
      update.getFromItem().accept(this);
    }

    if (update.getJoins() != null) {
      for (Join join : update.getJoins()) {
        join.getRightItem().accept(this);
      }
    }

    if (update.getWhere() != null) {
      update.getWhere().accept(this);
    }

    return tables;
  }

  @Override
  public void visit(WithItem withItem) {
    otherItemNames.add(withItem.getName().toLowerCase());
    withItem.getSelectBody().accept(this);
  }

  @Override
  public void visit(PlainSelect plainSelect) {
    List<SelectItem> selectItemList = plainSelect.getSelectItems();
    for (SelectItem si : selectItemList) {
      if (si != null)
        si.accept(this);
    }
    if (plainSelect.getFromItem() != null)
      plainSelect.getFromItem().accept(this);

    if (plainSelect.getJoins() != null) {
      for (Join join : plainSelect.getJoins()) {
        if (join.getOnExpression() != null)
          join.getOnExpression().accept(this);
        if (join.getRightItem() != null)
          join.getRightItem().accept(this);
      }
    }
    if (plainSelect.getWhere() != null) {
      plainSelect.getWhere().accept(this);
    }

    if (plainSelect.getHaving() != null) {
      plainSelect.getHaving().accept(this);
    }

  }

  @Override
  public void visit(Table t) {
    String tableWholeName = t.getFullyQualifiedName();
    if (!otherItemNames.contains(tableWholeName.toLowerCase()) && !tables.containsValue(tableWholeName)) {
      String alias = t.getAlias() != null ? t.getAlias().getName() : DEFAULT_ALIAS;
      tables.put(alias, t.getBaseName());
    }
  }

  @Override
  public void visit(SubSelect subSelect) {
    if (subSelect.getSelectBody() != null)
      subSelect.getSelectBody().accept(this);
  }

  @Override
  public void visit(Addition addition) {
    visitBinaryExpression(addition);
  }

  @Override
  public void visit(AndExpression andExpression) {
    visitBinaryExpression(andExpression);
  }

  @Override
  public void visit(Between between) {
    if (between.getLeftExpression() != null)
      between.getLeftExpression().accept(this);
    if (between.getBetweenExpressionStart() != null)
      between.getBetweenExpressionStart().accept(this);
    if (between.getBetweenExpressionEnd() != null)
      between.getBetweenExpressionEnd().accept(this);
  }

  @Override
  public void visit(Column tableColumn) {
  }

  @Override
  public void visit(Division division) {
    visitBinaryExpression(division);
  }

  @Override
  public void visit(DoubleValue doubleValue) {
  }

  @Override
  public void visit(EqualsTo equalsTo) {
    visitBinaryExpression(equalsTo);
  }

  @Override
  public void visit(Function function) {
  }

  @Override
  public void visit(GreaterThan greaterThan) {
    visitBinaryExpression(greaterThan);
  }

  @Override
  public void visit(GreaterThanEquals greaterThanEquals) {
    visitBinaryExpression(greaterThanEquals);
  }

  @Override
  public void visit(InExpression inExpression) {
    if (inExpression.getLeftExpression() != null)
      inExpression.getLeftExpression().accept(this);
    if (inExpression.getRightItemsList() != null)
      inExpression.getRightItemsList().accept(this);
  }

  @Override
  public void visit(SignedExpression signedExpression) {
    if (signedExpression.getExpression() != null)
      signedExpression.getExpression().accept(this);
  }

  @Override
  public void visit(IsNullExpression isNullExpression) {
    if (isNullExpression.getLeftExpression() != null) {
      isNullExpression.getLeftExpression().accept(this);
    }
  }

  @Override
  public void visit(JdbcParameter jdbcParameter) {
  }

  @Override
  public void visit(LikeExpression likeExpression) {
    visitBinaryExpression(likeExpression);
  }

  @Override
  public void visit(ExistsExpression existsExpression) {
    existsExpression.getRightExpression().accept(this);
  }

  @Override
  public void visit(LongValue longValue) {
  }

  @Override
  public void visit(MinorThan minorThan) {
    visitBinaryExpression(minorThan);
  }

  @Override
  public void visit(MinorThanEquals minorThanEquals) {
    visitBinaryExpression(minorThanEquals);
  }

  @Override
  public void visit(Multiplication multiplication) {
    visitBinaryExpression(multiplication);
  }

  @Override
  public void visit(NotEqualsTo notEqualsTo) {
    visitBinaryExpression(notEqualsTo);
  }

  @Override
  public void visit(NullValue nullValue) {
  }

  @Override
  public void visit(OrExpression orExpression) {
    visitBinaryExpression(orExpression);
  }

  @Override
  public void visit(Parenthesis parenthesis) {
    parenthesis.getExpression().accept(this);
  }

  @Override
  public void visit(StringValue stringValue) {
  }

  @Override
  public void visit(Subtraction subtraction) {
    visitBinaryExpression(subtraction);
  }

  public void visitBinaryExpression(BinaryExpression binaryExpression) {
    if (binaryExpression.getLeftExpression() != null)
      binaryExpression.getLeftExpression().accept(this);
    if (binaryExpression.getLeftExpression() != null)
      binaryExpression.getRightExpression().accept(this);
  }

  @Override
  public void visit(ExpressionList expressionList) {
    for (Expression expression : expressionList.getExpressions()) {
      expression.accept(this);
    }

  }

  @Override
  public void visit(DateValue dateValue) {
  }

  @Override
  public void visit(TimestampValue timestampValue) {
  }

  @Override
  public void visit(TimeValue timeValue) {
  }

  @Override
  public void visit(CaseExpression caseExpression) {
    if (caseExpression.getElseExpression() != null) {
      caseExpression.getElseExpression().accept(this);
    }

    if (caseExpression.getSwitchExpression() != null) {
      caseExpression.getSwitchExpression().accept(this);
    }

    List<Expression> whenList = caseExpression.getWhenClauses();
    if (whenList != null) {
      for (Expression ex : whenList) {
        ex.accept(this);
      }
    }

  }

  @Override
  public void visit(WhenClause whenClause) {
    if (whenClause.getWhenExpression() != null) {
      whenClause.getWhenExpression().accept(this);
    }

    if (whenClause.getThenExpression() != null) {
      whenClause.getThenExpression().accept(this);
    }
  }

  @Override
  public void visit(AllComparisonExpression allComparisonExpression) {
    allComparisonExpression.getSubSelect().getSelectBody().accept(this);
  }

  @Override
  public void visit(AnyComparisonExpression anyComparisonExpression) {
    anyComparisonExpression.getSubSelect().getSelectBody().accept(this);
  }

  @Override
  public void visit(SubJoin subjoin) {
    if (subjoin.getLeft() != null)
      subjoin.getLeft().accept(this);
    if (subjoin.getJoin() != null && subjoin.getJoin().getRightItem() != null)
      subjoin.getJoin().getRightItem().accept(this);
  }

  @Override
  public void visit(Concat concat) {
    visitBinaryExpression(concat);
  }

  @Override
  public void visit(Matches matches) {
    visitBinaryExpression(matches);
  }

  @Override
  public void visit(BitwiseAnd bitwiseAnd) {
    visitBinaryExpression(bitwiseAnd);
  }

  @Override
  public void visit(BitwiseOr bitwiseOr) {
    visitBinaryExpression(bitwiseOr);
  }

  @Override
  public void visit(BitwiseXor bitwiseXor) {
    visitBinaryExpression(bitwiseXor);
  }

  @Override
  public void visit(CastExpression cast) {
    cast.getLeftExpression().accept(this);
  }

  @Override
  public void visit(Modulo modulo) {
    visitBinaryExpression(modulo);
  }

  @Override
  public void visit(AnalyticExpression analytic) {
  }

  @Override
  public void visit(SetOperationList list) {
    List<SelectBody> bds = list.getSelects();
    if (bds != null) {
      for (SelectBody sb : bds) {
        sb.accept(this);
      }
    }

    List<OrderByElement> obs = list.getOrderByElements();
    if (obs != null) {
      for (OrderByElement ob : obs) {
        ob.accept(this);
      }
    }

  }

  @Override
  public void visit(ExtractExpression eexpr) {
    eexpr.getExpression().accept(this);
  }

  @Override
  public void visit(LateralSubSelect lateralSubSelect) {
    SubSelect ss = lateralSubSelect.getSubSelect();
    if (ss != null && ss.getSelectBody() != null)
      ss.getSelectBody().accept(this);
  }

  @Override
  public void visit(MultiExpressionList multiExprList) {
    for (ExpressionList exprList : multiExprList.getExprList()) {
      exprList.accept(this);
    }
  }

  @Override
  public void visit(ValuesList valuesList) {
    if (valuesList.getMultiExpressionList() != null) {
      valuesList.getMultiExpressionList().accept(this);
    }
  }

  private void init() {
    otherItemNames = new ArrayList<String>();
    tables = new HashMap<String, String>();
  }

  @Override
  public void visit(IntervalExpression iexpr) {
  }

  @Override
  public void visit(JdbcNamedParameter jdbcNamedParameter) {
  }

  @Override
  public void visit(OracleHierarchicalExpression oexpr) {
  }

  @Override
  public void visit(RegExpMatchOperator rexpr) {
    visitBinaryExpression(rexpr);
  }

  @Override
  public void visit(RegExpMySQLOperator rexpr) {
    visitBinaryExpression(rexpr);
  }

  @Override
  public void visit(JsonExpression jsonExpr) {
  }

  @Override
  public void visit(AllColumns allColumns) {

  }

  @Override
  public void visit(AllTableColumns allTableColumns) {

  }

  @Override
  public void visit(SelectExpressionItem selectExpressionItem) {
    Expression ex = selectExpressionItem.getExpression();
    if (ex != null)
      ex.accept(this);
  }

  @Override
  public void visit(HexValue hexValue) {

  }

  @Override
  public void visit(WithinGroupExpression wgexpr) {
    if (wgexpr.getExprList() != null) {
      wgexpr.getExprList().accept(this);
    }

    List<OrderByElement> obs = wgexpr.getOrderByElements();
    if (obs != null) {
      for (OrderByElement ob : obs) {
        ob.accept(this);
      }
    }
  }

  @Override
  public void visit(JsonOperator jsonExpr) {

  }

  @Override
  public void visit(UserVariable var) {

  }

  @Override
  public void visit(NumericBind bind) {

  }

  /**
   * @param aexpr
   * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.KeepExpression)
   */
  @Override
  public void visit(KeepExpression aexpr) {
    List<OrderByElement> obs = aexpr.getOrderByElements();
    if (obs != null) {
      for (OrderByElement ob : obs) {
        ob.accept(this);
      }
    }

  }

  @Override
  public void visit(MySQLGroupConcat groupConcat) {
    groupConcat.getExpressionList().accept(this);
    List<OrderByElement> obs = groupConcat.getOrderByElements();
    if (obs != null) {
      for (OrderByElement ob : obs) {
        ob.accept(this);
      }
    }
  }

  @Override
  public void visit(RowConstructor rowConstructor) {
    if (rowConstructor.getExprList() != null) {
      rowConstructor.getExprList().accept(this);
    }
  }

  @Override
  public void visit(OracleHint hint) {

  }

  @Override
  public void visit(TimeKeyExpression timeKeyExpression) {

  }

  @Override
  public void visit(DateTimeLiteralExpression literal) {
  }

  @Override
  public void visit(NotExpression aThis) {
    aThis.getExpression().accept(this);
  }

  @Override
  public void visit(TableFunction tableFunction) {

  }

  @Override
  public void visit(OrderByElement orderBy) {
    orderBy.getExpression().accept(this);
  }

}
