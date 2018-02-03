package com.tmser.schevaluation.evl.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tmser.schevaluation.evl.bo.EvlAnalyzeIndicator;
import com.tmser.schevaluation.evl.bo.EvlIndicator;
import com.tmser.schevaluation.evl.bo.EvlOperateResult;

/**
 * 评价分析指标扩展实体类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeIndicatorVo.java, v 1.0 2016年5月10日 下午5:47:05
 *          gengqianfeng Exp $
 */
@SuppressWarnings("serial")
public class EvlAnalyzeIndicatorVo implements Serializable{

	/**
	 * 指标实体对象
	 */
	private EvlIndicator evlIndicator;

	/**
	 * 子指标
	 */
	private List<EvlAnalyzeIndicatorVo> childIndicators = new ArrayList<EvlAnalyzeIndicatorVo>();

	/**
	 * 分析指标对象
	 */
	private EvlAnalyzeIndicator evlAnalyzeIndicator;

	/**
	 * 评价结果实体对象
	 */
	private List<EvlOperateResult> evlOperateResult = new ArrayList<EvlOperateResult>();

	public EvlIndicator getEvlIndicator() {
		return evlIndicator;
	}

	public void setEvlIndicator(EvlIndicator evlIndicator) {
		this.evlIndicator = evlIndicator;
	}

	public List<EvlAnalyzeIndicatorVo> getChildIndicators() {
		return childIndicators;
	}

	public void setChildIndicators(List<EvlAnalyzeIndicatorVo> childIndicators) {
		this.childIndicators = childIndicators;
	}

	public EvlAnalyzeIndicator getEvlAnalyzeIndicator() {
		return evlAnalyzeIndicator;
	}

	public void setEvlAnalyzeIndicator(EvlAnalyzeIndicator evlAnalyzeIndicator) {
		this.evlAnalyzeIndicator = evlAnalyzeIndicator;
	}

	public List<EvlOperateResult> getEvlOperateResult() {
		return evlOperateResult;
	}

	public void setEvlOperateResult(List<EvlOperateResult> evlOperateResult) {
		this.evlOperateResult = evlOperateResult;
	}

}
