/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.schevaluation.common.bo.QueryObject;
import com.tmser.schevaluation.utils.StringUtils;

/**
 * 时限表 Entity
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlTimeline.java, v 1.0 2016-05-09 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = EvlTimeline.TABLE_NAME)
public class EvlTimeline extends QueryObject {
  public static final String TABLE_NAME = "evl_timeline";
  public static final int pjsx = 1;// 评教时限

  @Id
  @Column(name = "id")
  private Integer id;

  /**
   * 评审类型
   **/
  @Column(name = "type")
  private Integer type;

  /**
   * 开始时间
   **/
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "begin_time")
  private Date beginTime;

  /**
   * 结束时间
   **/
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "end_time")
  private Date endTime;

  /**
   * 问卷表id
   **/
  @Column(name = "questionnaires_id")
  private Integer questionnairesId;

  @Column(name = "org_id")
  private Integer orgId;

  @Column(name = "last_id")
  private Integer lastId;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "last_dttm")
  private Date lastDttm;

  @Column(name = "crt_id")
  private Integer crtId;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "crt_dttm")
  private Date crtDttm;

  @Transient
  private String beginTimeStr;

  @Transient
  private String endTimeStr;

  public String getBeginTimeStr() {
    if (beginTime != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      beginTimeStr = sdf.format(beginTime);
    }
    return beginTimeStr;
  }

  public void setBeginTimeStr(String beginTimeStr) {
    if (StringUtils.isNotBlank(beginTimeStr)) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        setBeginTime(sdf.parse(beginTimeStr));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    this.beginTimeStr = beginTimeStr;
  }

  public String getEndTimeStr() {
    if (endTime != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      endTimeStr = sdf.format(endTime);
    }
    return endTimeStr;
  }

  public void setEndTimeStr(String endTimeStr) {
    if (StringUtils.isNotBlank(endTimeStr)) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        setEndTime(sdf.parse(endTimeStr));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    this.endTimeStr = endTimeStr;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Date getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Integer getQuestionnairesId() {
    return questionnairesId;
  }

  public void setQuestionnairesId(Integer questionnairesId) {
    this.questionnairesId = questionnairesId;
  }

  public Integer getOrgId() {
    return orgId;
  }

  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  public Integer getCrtId() {
    return crtId;
  }

  public void setCrtId(Integer crtId) {
    this.crtId = crtId;
  }

  public Date getCrtDttm() {
    return crtDttm;
  }

  public void setCrtDttm(Date crtDttm) {
    this.crtDttm = crtDttm;
  }

  public Integer getLastId() {
    return lastId;
  }

  public void setLastId(Integer lastId) {
    this.lastId = lastId;
  }

  public Date getLastDttm() {
    return lastDttm;
  }

  public void setLastDttm(Date lastDttm) {
    this.lastDttm = lastDttm;
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof EvlTimeline))
      return false;
    EvlTimeline castOther = (EvlTimeline) other;
    return new EqualsBuilder().append(id, castOther.id).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(id).toHashCode();
  }

}
