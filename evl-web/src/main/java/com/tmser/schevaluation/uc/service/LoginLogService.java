/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.service;

import com.tmser.schevaluation.common.service.BaseService;
import com.tmser.schevaluation.uc.bo.LoginLog;
import com.tmser.schevaluation.uc.bo.UserSpace;

/**
 * 登录日志 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LoginLog.java, v 1.0 2015-05-13 Generate Tools Exp $
 */

public interface LoginLogService extends BaseService<LoginLog, Long>{

    /**
     * 记录登录
     * @param userid
     * @param type
     * @return
     */
	void addHistroy(UserSpace cus,int type);
}
