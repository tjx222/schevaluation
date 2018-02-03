/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.evl.bo.EvlTimeline;
import com.tmser.schevaluation.evl.dao.EvlTimelineDao;
import com.tmser.schevaluation.evl.service.EvlTimelineService;

/**
 * 时限表 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlTimeline.java, v 1.0 2016-05-09 Generate Tools Exp $
 */
@Service
@Transactional
public class EvlTimelineServiceImpl extends AbstractService<EvlTimeline, Integer> implements EvlTimelineService {

	@Autowired
	private EvlTimelineDao evlTimelineDao;

	/**
	 * @return
	 * @see com.tmser.schevaluation.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<EvlTimeline, Integer> getDAO() {
		return evlTimelineDao;
	}

	@Override
	public List<EvlTimeline> findAllTimelineList(EvlTimeline etl) {
		EvlTimeline model = new EvlTimeline();
		model.setOrgId(etl.getOrgId());
		return this.findAll(model);
	}

	@Override
	public void updateValue(EvlTimeline time) {
		this.evlTimelineDao.updateValue(time);
	}

}
