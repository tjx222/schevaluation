/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.dao;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.evl.bo.EvlTimeline;

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