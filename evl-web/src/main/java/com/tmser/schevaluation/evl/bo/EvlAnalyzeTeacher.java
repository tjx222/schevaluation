package com.tmser.schevaluation.evl.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.schevaluation.common.bo.BaseObject;

/**
 * 评价分析教师表实体类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeTeacher.java, v 1.0 2016年5月10日 下午4:48:58 gengqianfeng
 *          Exp $
 */
@Entity
@Table(name = EvlAnalyzeTeacher.TABLE_NAME)
public class EvlAnalyzeTeacher extends BaseObject implements Cloneable {

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	private static final long serialVersionUID = 6390945721255590835L;

	// 分数段区间
	public static final String[] SCORESECTION = new String[] { "100-95", "94-90", "89-85", "84-80", "79-75", "74-70", "69-65", "64-60", "60以下" };
	// 教龄段区间
	public static final String[] SCHOOLAGE_SECTION = new String[] { "5年及以下", "6~10年", "11~15年", "16~20年", "21年以上" };

	/**
	 * 静态常量表名
	 */
	public static final String TABLE_NAME = "evl_analyze_teacher";

	/**
	 * 班级id
	 */
	@Column(name = "class_id")
	private String classId;

	/**
	 * 年级id
	 */
	@Column(name = "grade_id")
	private String gradeId;

	/**
	 * 主键id
	 */
	@Id
	@Column(name = "id")
	private Integer id;

	/**
	 * 指标id
	 */
	@Column(name = "indicator_id")
	private String indicatorId;

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
	 * 结果平均分
	 */
	@Column(name = "result_score")
	private Double resultScore;

	/**
	 * 教龄
	 */
	@Column(name = "school_age")
	private Integer schoolAge;

	/**
	 * 得分率
	 */
	@Column(name = "score_average")
	private Double scoreAverage;

	/**
	 * 性别
	 */
	@Column(name = "sex")
	private Integer sex;

	/**
	 * 排序（根据条件实时排序）
	 */
	@Column(name = "sort")
	private Integer sort;

	/**
	 * 学科id
	 */
	@Column(name = "subject_id")
	private String subjectId;

	/**
	 * 教师id
	 */
	@Column(name = "teacher_id")
	private Integer teacherId;

	/**
	 * 教师名称
	 */
	@Column(name = "teacher_name")
	private String teacherName;

	/**
	 * 教师职务（多个已中文顿号分隔）
	 */
	@Column(name = "teacher_role")
	private String teacherRole;
	
	@Transient
	private String teacherRoleName;

	public String getTeacherRoleName() {
		return teacherRoleName;
	}

	public void setTeacherRoleName(String teacherRoleName) {
		this.teacherRoleName = teacherRoleName;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
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

	public Double getResultScore() {
		return resultScore;
	}

	public void setResultScore(Double resultScore) {
		this.resultScore = resultScore;
	}

	public Integer getSchoolAge() {
		return schoolAge;
	}

	public void setSchoolAge(Integer schoolAge) {
		this.schoolAge = schoolAge;
	}

	public Double getScoreAverage() {
		return scoreAverage;
	}

	public void setScoreAverage(Double scoreAverage) {
		this.scoreAverage = scoreAverage;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherRole() {
		return teacherRole;
	}

	public void setTeacherRole(String teacherRole) {
		this.teacherRole = teacherRole;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof EvlAnalyzeTeacher))
			return false;
		EvlAnalyzeTeacher castOther = (EvlAnalyzeTeacher) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}
	@Override
	public EvlAnalyzeTeacher clone() {
		EvlAnalyzeTeacher evlAnalyzeTeacher = new EvlAnalyzeTeacher();
		try {
			evlAnalyzeTeacher = (EvlAnalyzeTeacher) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return evlAnalyzeTeacher;
	}
}
