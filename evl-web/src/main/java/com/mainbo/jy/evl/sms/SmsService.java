/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.sms;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author yc
 * @version $Id: SmsService.java, v 1.0 2016年6月12日 上午10:29:43 dell Exp $
 */
public interface SmsService {
	/**
	 * 向单个手机号发送短信
	 */
	Map<String, String> sendSms(String msg,String phoneNumber);
	/**
	 * 向多个手机号发送短信
	 */
	List<Map<String, String>> sendSms(String msg,String...phoneNumbers);
}
