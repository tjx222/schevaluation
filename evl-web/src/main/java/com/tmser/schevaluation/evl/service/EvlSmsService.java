/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service;

import com.tmser.schevaluation.common.service.BaseService;
import com.tmser.schevaluation.evl.bo.EvlSms;

/**
 * 短息发送状态记录 服务类
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: EvlSms.java, v 1.0 2017-05-26 ljh Exp $
 */

public interface EvlSmsService extends BaseService<EvlSms, Integer>{
	/**
	 * 获取当日某一手机号码数量
	 * @return 
	 */
	public int  getTodayCount(String phone);

	/**
	 * 保存手机号码发送记录
	 * @param phone
	 */
	void saveTodayCount(String phone);
}
