/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.dao;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.evl.bo.EvlTimeline;

 /**
 * 时限表 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlTimeline.java, v 1.0 2016-05-09 Generate Tools Exp $
 */
public interface EvlTimelineDao extends BaseDAO<EvlTimeline, Integer>{

	void updateValue(EvlTimeline time);

}