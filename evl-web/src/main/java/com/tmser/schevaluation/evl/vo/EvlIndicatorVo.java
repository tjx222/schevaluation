/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tmser.schevaluation.evl.bo.EvlIndicator;
import com.tmser.schevaluation.evl.bo.EvlOperateResult;
/**
 * evl指标视图数据
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: EvlIndicatorVo.java, v 1.0 2016-5-9 Generate Tools Exp $
 */
@SuppressWarnings("serial")
public class EvlIndicatorVo implements Serializable{
	//当前问卷
	EvlIndicator indicator=new EvlIndicator();
	//子指标
	List<EvlIndicatorVo> childIndicators=new ArrayList<>();
	//评教结果
	List<EvlOperateResult> resultList = new ArrayList<>();
	//自定义类型
	//当前问卷
	EvlIndicator twoindicator=new EvlIndicator();
	
	public EvlIndicator getTwoindicator() {
		return twoindicator;
	}
	public void setTwoindicator(EvlIndicator twoindicator) {
		this.twoindicator = twoindicator;
	}
	public List<EvlOperateResult> getResultList() {
		return resultList;
	}
	public void setResultList(List<EvlOperateResult> resultList) {
		this.resultList = resultList;
	}
	public EvlIndicator getIndicator() {
		return indicator;
	}
	public void setIndicator(EvlIndicator indicator) {
		this.indicator = indicator;
	}
	public List<EvlIndicatorVo> getChildIndicators() {
		return childIndicators;
	}
	public void setChildIndicators(List<EvlIndicatorVo> childIndicators) {
		this.childIndicators = childIndicators;
	}
}