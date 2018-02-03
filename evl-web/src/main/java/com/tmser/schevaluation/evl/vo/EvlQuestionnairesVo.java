/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.vo;

import java.io.Serializable;

import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.bo.EvlTimeline;

/**
 * evl问卷扩展实体类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author ljh
 * @version $Id: EvlQuestionnairesVo.java, v 1.0 2016-5-9 Generate Tools Exp $
 */
@SuppressWarnings("serial")
public class EvlQuestionnairesVo implements Serializable{

	/**
	 * 问卷实体类
	 */
	private EvlQuestionnaires evlQuestionnaires;

	/**
	 * 问卷时间实体类
	 */
	private EvlTimeline evlTimeline;
	
	private Integer shouldReviewCount;//当前问卷应该评审
	private Integer alreadyReviewCount;//当前问卷已经评审

	public Integer getShouldReviewCount() {
		return shouldReviewCount;
	}

	public void setShouldReviewCount(Integer shouldReviewCount) {
		this.shouldReviewCount = shouldReviewCount;
	}

	public Integer getAlreadyReviewCount() {
		return alreadyReviewCount;
	}

	public void setAlreadyReviewCount(Integer alreadyReviewCount) {
		this.alreadyReviewCount = alreadyReviewCount;
	}

	public EvlQuestionnaires getEvlQuestionnaires() {
		return evlQuestionnaires;
	}

	public void setEvlQuestionnaires(EvlQuestionnaires evlQuestionnaires) {
		this.evlQuestionnaires = evlQuestionnaires;
	}

	public EvlTimeline getEvlTimeline() {
		return evlTimeline;
	}

	public void setEvlTimeline(EvlTimeline evlTimeline) {
		this.evlTimeline = evlTimeline;
	}

}
