/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tmser.schevaluation.common.web.controller.AbstractController;
import com.tmser.schevaluation.uc.service.UCAccountSynService;
import com.tmser.schevaluation.uc.vo.UCAccountInfo;

/**
 * <pre>
 *   优课账号同步接口
 * </pre>
 *
 * @author daweiwbs
 * @version $Id: AccountSynchronize.java, v 1.0 2015年8月14日 上午11:34:12 daweiwbs Exp $
 */
@Controller
@RequestMapping("/jy/ws/uc")
public class UCAccountSynchronize extends AbstractController{

	@Autowired
	private UCAccountSynService ucAccountSynService;
	
	/**
	 * 补充优课用户的用户空间等信息，然后跳转到首页
	 * @param orgId
	 * @param userSpaceAdd
	 * @return
	 */
	@RequestMapping(value="/complementUserInfo", method=RequestMethod.POST)
	public String complementUserInfo(UCAccountInfo ucAccountInfo){
		//完善优课账号信息
		ucAccountSynService.complementUCUserInfo(ucAccountInfo);
		return "redirect:/";
	}
}
