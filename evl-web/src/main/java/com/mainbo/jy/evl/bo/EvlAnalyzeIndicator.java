package com.mainbo.jy.evl.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.BaseObject;

/**
 * 评价分析指标表实体类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeIndicator.java, v 1.0 2016年5月10日 下午5:05:33
 *          gengqianfeng Exp $
 */
@Entity
@Table(name = EvlAnalyzeIndicator.TABLE_NAME)
public class EvlAnalyzeIndicator extends BaseObject {

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	private static final long serialVersionUID = 1921520074550223270L;

	/**
	 * 静态常量表名
	 */
	public static final String TABLE_NAME = "evl_analyze_indicator";

	/**
	 * 主键id
	 */
	@Id
	@Column(name = "id")
	private Integer id;

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
	 * 指标id
	 */
	@Column(name = "indicator_id")
	private String indicatorId;

	/**
	 * 结果平均分
	 */
	@Column(name = "result_score")
	private Double resultScore;

	/**
	 * 得分率
	 */
	@Column(name = "score_average")
	private Double scoreAverage;

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

	public Integer getQuestionnairesId() {
		return questionnairesId;
	}

	public void setQuestionnairesId(Integer questionnairesId) {
		this.questionnairesId = questionnairesId;
	}

	public String getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public Double getResultScore() {
		return resultScore;
	}

	public void setResultScore(Double resultScore) {
		this.resultScore = resultScore;
	}

	public Double getScoreAverage() {
		return scoreAverage;
	}

	public void setScoreAverage(Double scoreAverage) {
		this.scoreAverage = scoreAverage;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof EvlAnalyzeIndicator))
			return false;
		EvlAnalyzeIndicator castOther = (EvlAnalyzeIndicator) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

}
