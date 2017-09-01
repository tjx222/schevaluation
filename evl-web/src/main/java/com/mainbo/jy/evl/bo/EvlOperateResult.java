package com.mainbo.jy.evl.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.BaseObject;

/**
 * 评价操作结果表
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlOperateResult.java, v 1.0 2016年5月10日 下午4:18:41 gengqianfeng
 *          Exp $
 */
@Entity
@Table(name = EvlOperateResult.TABLE_NAME)
public class EvlOperateResult extends BaseObject {

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	private static final long serialVersionUID = -2371607001366138374L;

	/**
	 * 静态常量表名
	 */
	public static final String TABLE_NAME = "evl_operate_result";

	/**
	 * 班级id
	 */
	@Column(name = "class_id")
	private Integer classId;

	/**
	 * 班级排序字段
	 */
	@Column(name = "class_sort")
	private Integer classSort;

	/**
	 * 学号
	 */
	@Column(name = "code")
	private String code;

	/**
	 * 主键id
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private String id;

	/**
	 * 评价指标id
	 */
	@Column(name = "indicator_id")
	private String indicatorId;

	/**
	 * 指标级别（0、总体评教）,1、一级指标评教，2、二级指标评教
	 */
	@Column(name = "indicator_level")
	private Integer indicatorLevel;

	/**
	 * 机构id
	 */
	@Column(name = "org_id")
	private Integer orgId;

	/**
	 * 问卷id
	 */
	@Column(name = "questionnaires_id")
	private Integer questionnairesId;

	/**
	 * 评价结果等级
	 */
	@Column(name = "result_level")
	private Integer resultLevel;

	/**
	 * 结果分数（由结果等级和权重获取）
	 */
	@Column(name = "result_score")
	private Double resultScore;

	/**
	 * 扩展字段1
	 */
	@Column(name = "standby1")
	private String standby1;

	/**
	 * 扩展字段2
	 */
	@Column(name = "standby2")
	private Integer standby2;

	/**
	 * 学生名称
	 */
	@Column(name = "student_name")
	private String studentName;

	/**
	 * 学科id
	 */
	@Column(name = "subje_id")
	private Integer subjeId;

	/**
	 * 年级id
	 */
	@Column(name = "grade_id")
	private Integer gradeId;

	/**
	 * 被评教教师id
	 */
	@Column(name = "teacher_id")
	private Integer teacherId;

	/**
	 * 学生id
	 */
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * 用户类型（0-学生，1-家长）
	 */
	@Column(name = "user_type")
	private Integer userType;

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getClassSort() {
		return classSort;
	}

	public void setClassSort(Integer classSort) {
		this.classSort = classSort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public Integer getIndicatorLevel() {
		return indicatorLevel;
	}

	public void setIndicatorLevel(Integer indicatorLevel) {
		this.indicatorLevel = indicatorLevel;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getQuestionnairesId() {
		return questionnairesId;
	}

	public void setQuestionnairesId(Integer questionnairesId) {
		this.questionnairesId = questionnairesId;
	}

	public Integer getResultLevel() {
		return resultLevel;
	}

	public void setResultLevel(Integer resultLevel) {
		this.resultLevel = resultLevel;
	}

	public Double getResultScore() {
		return resultScore;
	}

	public void setResultScore(Double resultScore) {
		this.resultScore = resultScore;
	}

	public String getStandby1() {
		return standby1;
	}

	public void setStandby1(String standby1) {
		this.standby1 = standby1;
	}

	public Integer getStandby2() {
		return standby2;
	}

	public void setStandby2(Integer standby2) {
		this.standby2 = standby2;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getSubjeId() {
		return subjeId;
	}

	public void setSubjeId(Integer subjeId) {
		this.subjeId = subjeId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof EvlOperateResult))
			return false;
		EvlOperateResult castOther = (EvlOperateResult) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

}
