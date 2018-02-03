/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.schevaluation.common.bo.BaseObject;
import com.tmser.schevaluation.evl.utils.EvlHelper;

/**
 * 考核评教相关设置表 Entity
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlQuestionnaires.java, v 1.0 2016-05-09 Generate Tools Exp $
 */
@Entity
@Table(name = EvlQuestionnaires.TABLE_NAME)
public class EvlQuestionnaires extends BaseObject {
  /**
   * <pre>
   *
   * </pre>
   */
  private static final long serialVersionUID = 1L;
  public static final String TABLE_NAME = "evl_questionnaires";
  /*-------------------评教类型{1：学生评教 2：家长评教 3：教师自评 4：民主互评}---------------------*/
  public static final int type_xueshengpingjiao = 1;
  public static final int type_jiazhangpingjiao = 2;
  public static final int type_jiaoshiziping = 3;
  public static final int type_minzhuhuping = 4;
  /*-------------------人员类型{1：全体学生/家长 2：按年级选择 3：按班级选择}---------------------*/
  public static final int student_type_quantixusheng = 1;
  public static final int student_type_nianji = 2;
  public static final int student_type_banji = 3;
  /*-------------------考核时段{1：学期  2：学年}---------------------*/
  public static final int operation_type_xueqi = 1;
  public static final int operation_type_xuenian = 2;
  /*-------------------学期类型{{上学期：1，下学期：2}}---------------------*/
  public static final int xueqi_shangxueqi = 1;
  public static final int xueqi_xiaxueqi = 2;
  /*-------------------评价方式{0：整体评教 1：对各一级指标进行评价 2：对各二级指标进行评价}---------------------*/
  public static final int indicator_type_zhengti = 0;
  public static final int indicator_type_oneLevel = 1;
  public static final int indicator_type_twoLevel = 2;
  /*-------------------是否收集建议{1:收集 0:不收集}---------------------*/
  public static final int iscollect_shouji = 1;
  public static final int iscollect_bushouji = 0;

  /**
   * 方案id
   **/
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "questionnaires_id")
  private Integer questionnairesId;

  /**
   * 标题
   **/
  @Column(name = "title", length = 32)
  private String title;

  /**
   * 问卷总分
   **/
  @Column(name = "totle_score")
  private Double totleScore;

  /**
   * 说明
   **/
  @Column(name = "directions", length = 21845)
  private String directions;

  /**
   * 评教类型{1：学生评教 2：家长评教 3：教师自评 4：民主互评}
   **/
  @Column(name = "type")
  private Integer type;

  /**
   * 评教人员类型{1：全体学生/家长 2：按年级选择 3：按班级选择}
   **/
  @Column(name = "student_type")
  private Integer studentType;

  /**
   * 评价学科{逗号分隔}
   **/
  @Column(name = "subject_ids", length = 512)
  private String subjectIds;

  /**
   * 考核时段{1：学期 2：学年}
   **/
  @Column(name = "operation_type")
  private Integer operationType;

  /**
   * 学期类型{{上学期：1，下学期：2}}
   **/
  @Column(name = "xueqi")
  private Integer xueqi;

  /**
   * 评价方式{0：整体评教 1：对各一级指标进行评价2：对各二级指标进行评价}
   **/
  @Column(name = "indicator_type")
  private Integer indicatorType;

  /**
   * 评教状态
   **/
  @Column(name = "status")
  private Integer status;

  /**
   * 学年
   **/
  @Column(name = "school_year")
  private Integer schoolYear;

  /**
   * 是否收集建议{1:收集 0:不收集}
   **/
  @Column(name = "iscollect")
  private Integer iscollect;

  @Column(name = "org_id")
  private Integer orgId;

  @Column(name = "last_id")
  private Integer lastId;
  @Column(name = "phase_id")
  private Integer phaseId;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "last_dttm")
  private Date lastDttm;

  /**
   * 分析报告问卷结果内用户自定义百分比，例："50,15,15,20"--优秀，良好，合格，不合格
   */
  @Column(name = "back1", length = 32)
  private String back1;

  @Column(name = "back2")
  private Integer back2;

  @Transient
  private String url;

  /**
   * Getter method for property <tt>url</tt>.
   * 
   * @return property value of url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Setter method for property <tt>url</tt>.
   * 
   * @param url
   *          value to be assigned to property url
   */
  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getPhaseId() {
    if (phaseId == null) {
      return EvlHelper.PRIMARY_SCHOOLS;// 默认小学
    } else {
      return phaseId;
    }
  }

  public void setPhaseId(Integer phaseId) {
    this.phaseId = phaseId;
  }

  public void setQuestionnairesId(Integer questionnairesId) {
    this.questionnairesId = questionnairesId;
  }

  public Integer getQuestionnairesId() {
    return this.questionnairesId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTotleScore(Double totleScore) {
    this.totleScore = totleScore;
  }

  public Double getTotleScore() {
    return this.totleScore;
  }

  public Integer getTotleScoreInt() {
    Integer score = 0;
    if (this.totleScore != null) {
      score = this.totleScore.intValue();
    }
    return score;
  }

  public void setDirections(String directions) {
    this.directions = directions;
  }

  public String getDirections() {
    return this.directions;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getType() {
    return this.type;
  }

  public void setStudentType(Integer studentType) {
    this.studentType = studentType;
  }

  public Integer getStudentType() {
    return this.studentType;
  }

  public void setSubjectIds(String subjectIds) {
    this.subjectIds = subjectIds;
  }

  public String getSubjectIds() {
    return this.subjectIds;
  }

  public void setOperationType(Integer operationType) {
    this.operationType = operationType;
  }

  public Integer getOperationType() {
    return this.operationType;
  }

  public void setXueqi(Integer xueqi) {
    this.xueqi = xueqi;
  }

  public Integer getXueqi() {
    return this.xueqi;
  }

  public void setIndicatorType(Integer indicatorType) {
    this.indicatorType = indicatorType;
  }

  public Integer getIndicatorType() {
    return this.indicatorType;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setSchoolYear(Integer schoolYear) {
    this.schoolYear = schoolYear;
  }

  public Integer getSchoolYear() {
    return this.schoolYear;
  }

  public void setIscollect(Integer iscollect) {
    this.iscollect = iscollect;
  }

  public Integer getIscollect() {
    return this.iscollect;
  }

  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  public Integer getOrgId() {
    return this.orgId;
  }

  public void setLastId(Integer lastId) {
    this.lastId = lastId;
  }

  public Integer getLastId() {
    return this.lastId;
  }

  public void setLastDttm(Date lastDttm) {
    this.lastDttm = lastDttm;
  }

  public Date getLastDttm() {
    return this.lastDttm;
  }

  public void setBack1(String back1) {
    this.back1 = back1;
  }

  public String getBack1() {
    return this.back1;
  }

  public void setBack2(Integer back2) {
    this.back2 = back2;
  }

  public Integer getBack2() {
    return this.back2;
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof EvlQuestionnaires))
      return false;
    EvlQuestionnaires castOther = (EvlQuestionnaires) other;
    return new EqualsBuilder().append(questionnairesId,
        castOther.questionnairesId).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(questionnairesId).toHashCode();
  }
}
