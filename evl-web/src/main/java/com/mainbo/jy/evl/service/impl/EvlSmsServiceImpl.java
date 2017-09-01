/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.evl.bo.EvlSms;
import com.mainbo.jy.evl.dao.EvlSmsDao;
import com.mainbo.jy.evl.service.EvlSmsService;
/**
 * 短息发送状态记录 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: EvlSmsServiceImpl.java, v 1.0 2017-05-26 ljh Exp $
 */
@Service
@Transactional
public class EvlSmsServiceImpl extends AbstractService<EvlSms, Integer> implements EvlSmsService {

	@Autowired
	private EvlSmsDao evlSmsDao;
	
	/**
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<EvlSms, Integer> getDAO() {
		return evlSmsDao;
	}

	@Override
	public int getTodayCount(String phone) {
		EvlSms model = new EvlSms();
		model.setPhone(phone);
		model.setTime(new Date());
		model.setEnable(EvlSms.ENABLE);
		model = this.findOne(model);
		if(model==null||model.getCount()==null){
			return 0;
		}else{
			return model.getCount();
		}
	}
	@Override
	public void saveTodayCount(String phone) {
		EvlSms model = new EvlSms();
		model.setPhone(phone);
		model.setTime(new Date());
		model.setEnable(EvlSms.ENABLE);
		EvlSms s = this.findOne(model);
		if(s==null){
			model.setCount(1);
			this.save(model);
		}else{
			Integer count = s.getCount()+1;
			s.setCount(count);
			this.update(s);
		}
	}
	
}
