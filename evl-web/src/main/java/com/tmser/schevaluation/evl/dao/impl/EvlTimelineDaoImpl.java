/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.dao.impl;

import org.springframework.stereotype.Repository;

import com.tmser.schevaluation.common.dao.AbstractDAO;
import com.tmser.schevaluation.evl.bo.EvlTimeline;
import com.tmser.schevaluation.evl.dao.EvlTimelineDao;

/**
 * 时限表 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlTimeline.java, v 1.0 2016-05-09 Generate Tools Exp $
 */
@Repository
public class EvlTimelineDaoImpl extends AbstractDAO<EvlTimeline,Integer> implements EvlTimelineDao {

	@Override
	public void updateValue(EvlTimeline time) {
		this.updateWithNullValue(time);
	}
}
