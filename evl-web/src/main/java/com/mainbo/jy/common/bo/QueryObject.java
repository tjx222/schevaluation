
package com.mainbo.jy.common.bo;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.mainbo.jy.common.page.Page;

/**
 * 基础BO,需要使用ORM 的bo 必须继承改抽象类。
 * 模型查询支持类
 * 
 * @author tjx
 * @version 2.0 2016-2-28
 */
@SuppressWarnings("serial")
public abstract class QueryObject implements Serializable, PageAble, OrderAble {

  public static enum JOINTYPE {
    INNER,
    LEFT,
    RIGHT,
    FULL;
  }

  /**
   * join
   */
  @Transient
  private StringBuilder join;

  /**
   * 别名
   */
  @Transient
  private String alias;

  /**
   * 扩展属性
   */
  @Transient
  private String flags;

  /**
   * 扩展属性
   */
  @Transient
  private String flago;

  /**
   * 分页参数
   */
  @Transient
  private Page page = new Page();

  /**
   * 自定义排序
   */
  @Transient
  private String tspOrder;

  /**
   * 自定义查询字段
   */
  @Transient
  private String customCulomn;

  /**
   * 自定义unit 对象
   */
  @Transient
  private QueryObject unionObject;

  /**
   * 自定义unit 对象
   */
  @Transient
  private boolean isUnionAll;

  /**
   * 自定义unit 对象
   */
  @Transient
  private boolean forUpdate = false;

  /**
   * 是否允许转换page to json
   */
  @Transient
  private Boolean needParseToJson = false;

  /**
   * 自定义查询条件 更新时若使用了customCondition ，则不更据id 更新 sql语句格式是 named 参数化形式
   * 不用传递复杂的条件，特别是非标准sql 支持的语句
   */
  @Transient
  private NamedConditon customCondition;

  /**
   * 查询结果中，不能映射到bo 的字段将字段映射到otherColumnMap
   */
  @Transient
  private Map<String, Object> otherColumnResult;

  /**
   * 自定义查询字段
   */
  @Transient
  private String group;

  /**
   * set need parse to json
   * 
   * @param needParseToJson
   *          need parse to json?
   */
  public void parsePage(boolean needParseToJson) {
    this.needParseToJson = needParseToJson;
  }

  /**
   * 
   * @param currentPage
   *          cp
   */
  public void currentPage(int currentPage) {
    this.needParseToJson = true;
    page.setCurrentPage(currentPage);
  }

  /**
   * 设置每页显示结果数，优先使用用户设置的结果条数
   * 
   * @param pageSize
   *          page size
   */
  public void pageSize(int pageSize) {
    this.needParseToJson = true;
    if (!page.customPageSize() || page.getPageSize() < 1 || page.getPageSize() > 100) {
      page.setPageSize(pageSize);
    }
  }

  @Override
  public boolean needParseToJson() {
    return needParseToJson;
  }

  /**
   * add join
   * 
   * @param join
   *          join type
   * @param tablename
   *          join table name
   * @return this
   */
  public QueryObject addJoin(JOINTYPE join, String tablename) {
    this.join = new StringBuilder(" ").append(join.name()).append(" join ").append(tablename);
    return this;
  }

  /**
   * get join sql
   */
  public String join() {
    return this.join != null ? this.join.toString() : "";
  }

  /**
   * get union sql
   */
  public QueryObject union() {
    return this.unionObject;
  }

  /**
   * add union
   * 
   * @param unionObject
   *          union objcet
   * @param isUnionAll
   *          is union all
   */
  public void addUnion(QueryObject unionObject, boolean isUnionAll) {
    if (unionObject == this) {
      throw new IllegalStateException("union object can't be it's self!");
    }
    this.unionObject = unionObject;
    this.isUnionAll = isUnionAll;
  }

  /**
   * add union
   */
  public void addUnion(QueryObject unionObject) {
    this.unionObject = unionObject;
    this.isUnionAll = true;
  }

  /**
   * is union all
   */
  public boolean unionAll() {
    return this.isUnionAll;
  }

  /**
   * join 时 on 的条件，必须参数化输入，参数设置到
   */
  public void on(String onConditions) {
    if (this.join == null) {
      throw new NullPointerException("must be set addJoin first!");
    }
    this.join.append(" on ").append(onConditions);
  }

  /**
   * 分页参数
   */
  @Override
  public Page getPage() {
    return page;
  }

  /**
   * add page
   */
  public void addPage(Page page) {
    this.needParseToJson = true;
    this.page = page;
  }

  /**
   * get custom culomn
   */
  public String customCulomn() {
    return customCulomn;
  }

  /**
   * add customColumn
   */
  public void addCustomCulomn(String customCulomn) {
    this.customCulomn = customCulomn;
  }

  /**
   * get custom condition
   */
  public NamedConditon customCondition() {
    return customCondition;
  }

  /**
   * add custom condition
   */
  public void addCustomCondition(String customCondition, Map<String, Object> paramMap) {
    this.customCondition = new NamedConditon(customCondition, paramMap);
  }

  /**
   * 无参数条件查询
   */
  public void addCustomCondition(String customCondition) {
    this.customCondition = new NamedConditon(customCondition, null);
  }

  /**
   * build custom conditon
   */
  public NamedConditon buildCondition(String customCondition) {
    if (this.customCondition == null) {
      this.customCondition = new NamedConditon(customCondition, new HashMap<String, Object>());
    } else {
      this.customCondition.appendCondition(customCondition);
    }
    return this.customCondition;
  }

  /**
   * 自定义排序
   */
  @Override
  public String order() {
    return tspOrder;
  }

  /**
   * 自定义排序
   */
  public void addOrder(String order) {
    this.tspOrder = order;
  }

  /**
   * 自定义排序
   */
  public void setOrder(String order) {
    this.tspOrder = order;
  }

  /**
   * 自定义排序
   */
  public String group() {
    return group;
  }

  /**
   * 自定义排序
   */
  public void addGroup(String group) {
    this.group = group;
  }

  /**
   * get alias
   */
  public String alias() {
    if (this.alias == null) {
      return "";
    }
    return this.alias;
  }

  /**
   * add alias
   */
  public void addAlias(String alias) {
    this.alias = alias;
  }

  /**
   * set for update
   */
  public QueryObject forUpdate() {
    this.forUpdate = true;
    return this;
  }

  /**
   * get is for update
   */
  public boolean needForUpdate() {
    return this.forUpdate;
  }

  /**
   *
   */
  public String getFlags() {
    return flags;
  }

  /**
   * 扩展属性s
   */
  public void setFlags(String flags) {
    this.flags = flags;
  }

  /**
   * 扩展属性o
   */
  public String getFlago() {
    return flago;
  }

  /**
   * 扩展属性o
   */
  public void setFlago(String flago) {
    this.flago = flago;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   * Getter method for property <tt>otherColumnResult</tt>.
   *
   */
  public Map<String, Object> getOtherColumnResult() {
    return otherColumnResult;
  }

  /**
   * Setter method for property <tt>otherColumnResult</tt>.
   */
  public void setOtherColumnResult(Map<String, Object> otherColumnResult) {
    this.otherColumnResult = otherColumnResult;
  }

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public abstract int hashCode();

  public static class NamedConditon {
    private String conditon;
    private final Map<String, Object> paramMap;

    public NamedConditon(String conditon, Map<String, Object> paramMap) {
      this.conditon = conditon;
      if (paramMap == null) {
        this.paramMap = Collections.emptyMap();
      } else {
        this.paramMap = paramMap;
      }
    }

    /**
     * Getter method for property <tt>conditon</tt>.
     *
     * @return conditon String
     */
    public String getConditon() {
      return conditon;
    }

    void appendCondition(String sql) {
      if (this.conditon != null && sql != null) {
        this.conditon = this.conditon + sql;
      }
    }

    /**
     * Getter method for property <tt>paramMap</tt>.
     *
     * @return paramMap Map<String,Object>
     */
    public Map<String, Object> getParamMap() {
      return paramMap;
    }

    /**
     * add condition
     * 
     * @param name
     *          name
     * @param value
     *          value
     * @return this
     */
    public NamedConditon put(String name, Object value) {
      this.paramMap.put(name, value);
      return this;
    }

  }
}
