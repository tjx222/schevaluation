/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.schevaluation.common.bo.BaseObject;

/**
 * <pre>
 * 记录学校学生账号升级情况和当前学年
 * </pre>
 *
 * @author yangchao
 * @version $Id: EvlSchoolYear.java, v 1.0 2016年8月1日 下午4:07:23 yangchao Exp $
 */
@Entity
@Table(name = EvlSchoolYear.TABLE_NAME)
public class EvlSchoolYear extends BaseObject {
  public static final String TABLE_NAME = "evl_schoolyear";

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "org_id")
  private Integer orgId;
  /**
   * 学年
   **/
  @Column(name = "school_year")
  private Integer schoolYear;

  // 未升级成功的年级IDs，逗号分隔间隔
  @Column(name = "fail_grades")
  private String failGrades;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getOrgId() {
    return orgId;
  }

  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  public Integer getSchoolYear() {
    return schoolYear;
  }

  public void setSchoolYear(Integer schoolYear) {
    this.schoolYear = schoolYear;
  }

  public String getFailGrades() {
    return failGrades;
  }

  public void setFailGrades(String failGrades) {
    this.failGrades = failGrades;
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof EvlSchoolYear))
      return false;
    EvlSchoolYear castOther = (EvlSchoolYear) other;
    return new EqualsBuilder().append(id, castOther.id)
        .append(orgId, castOther.orgId)
        .append(schoolYear, castOther.schoolYear)
        .append(failGrades, castOther.failGrades).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(id).append(orgId).append(schoolYear)
        .append(failGrades).toHashCode();
  }
}
