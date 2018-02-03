/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service;

import java.util.List;

import com.tmser.schevaluation.evl.bo.EvlTimeline;
import com.tmser.schevaluation.common.service.BaseService;

/**
 * 时限表 服务类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlTimeline.java, v 1.0 2016-05-09 Generate Tools Exp $
 */

public interface EvlTimelineService extends BaseService<EvlTimeline, Integer> {

	/**
	 * 根据机构id获取所有限制时间
	 * 
	 * @return
	 */
	public List<EvlTimeline> findAllTimelineList(EvlTimeline etl);

	/**
	 * 更新time{非过滤null}
	 * @param time
	 */
	public void updateValue(EvlTimeline time);
	
}
