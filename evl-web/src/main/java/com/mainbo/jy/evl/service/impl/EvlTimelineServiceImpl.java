/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.evl.bo.EvlTimeline;
import com.mainbo.jy.evl.dao.EvlTimelineDao;
import com.mainbo.jy.evl.service.EvlTimelineService;

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
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
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
