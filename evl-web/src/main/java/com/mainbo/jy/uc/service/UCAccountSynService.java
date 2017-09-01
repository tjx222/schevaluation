/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.service;

import com.mainbo.jy.uc.vo.UCAccountInfo;

/**
 * <pre>
 * uc账号同步Service
 * </pre>
 *
 * @author wangdawei
 * @version $Id: UCAccountSynService.java, v 1.0 2015年8月19日 下午3:17:53 wangdawei Exp $
 */
public interface UCAccountSynService {

	/**
	 * 优课账号同步
	 * @param ucAccountSynchronize
	 */
	void ucAccountSynchronize(UCAccountInfo ucAccountInfo);

	/**
	 * 完善优课账号信息
	 * @param ucAccountInfo
	 */
	void complementUCUserInfo(UCAccountInfo ucAccountInfo);

}
