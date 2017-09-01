/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.service.impl;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.common.utils.RequestUtils;
import com.mainbo.jy.common.utils.WebThreadLocalUtils;
import com.mainbo.jy.uc.bo.LoginLog;
import com.mainbo.jy.uc.bo.UserSpace;
import com.mainbo.jy.uc.dao.LoginLogDao;
import com.mainbo.jy.uc.service.LoginLogService;
/**
 * 登录日志 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LoginLog.java, v 1.0 2015-05-13 Generate Tools Exp $
 */
@Service
@Transactional
public class LoginLogServiceImpl extends AbstractService<LoginLog, Long> implements LoginLogService {

	@Autowired
	private LoginLogDao loginLogDao;
	
	/**
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<LoginLog, Long> getDAO() {
		return loginLogDao;
	}

    /**
     * 记录登录
     * @param userid
     * @param type
     * @return
     */
	@Override
	public void addHistroy(UserSpace cus,int type){
		if(cus == null ) return;
		try{
			ServletRequest request = WebThreadLocalUtils.getRequest();
	        LoginLog model = new LoginLog();
	        model.setUserId(cus.getUserId());
	        model.setSpaceId(cus.getId());
	        model.setIp(RequestUtils.getIpAddr((HttpServletRequest)request));
	        model.setGradeId(cus.getGradeId());
	        model.setSubjectId(cus.getSubjectId());
	        model.setType(type);
	        model.setSysRoleId(cus.getSysRoleId());
        	loginLogDao.insert(model);
        }catch(Exception e){
        	logger.info("save login history failed",e);
        }
 	}
}
