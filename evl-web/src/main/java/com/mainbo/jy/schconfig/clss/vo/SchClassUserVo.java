/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.schconfig.clss.vo;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.QueryObject;
import com.mainbo.jy.schconfig.clss.bo.SchClassUser;

/**
 * <pre>
 * 班级编辑vo
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClassUserVo.java, v 1.0 2016年3月21日 上午11:26:28 tmser Exp $
 */
public class SchClassUserVo extends QueryObject {

  /**
   * <pre>
   *
   * </pre>
   */
  private static final long serialVersionUID = 337405223045747864L;

  private Integer clsid;

  private List<SchClassUser> userList;

  private SchClassUser master;

  public Integer getClsid() {
    return clsid;
  }

  public void setClsid(Integer clsid) {
    this.clsid = clsid;
  }

  public List<SchClassUser> getUserList() {
    return userList;
  }

  public void setUserList(List<SchClassUser> userList) {
    this.userList = userList;
  }

  public SchClassUser getMaster() {
    return master;
  }

  public void setMaster(SchClassUser master) {
    this.master = master;
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof SchClassUserVo))
      return false;
    SchClassUserVo castOther = (SchClassUserVo) other;
    return new EqualsBuilder().append(clsid, castOther.clsid).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(clsid).toHashCode();
  }

}
