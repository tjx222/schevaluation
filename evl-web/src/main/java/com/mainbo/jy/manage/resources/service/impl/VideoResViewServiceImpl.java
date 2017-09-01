/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.resources.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.mainbo.jy.common.utils.WebThreadLocalUtils;
import com.mainbo.jy.manage.resources.bo.Resources;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CompositeResViewServiceImpl.java, v 1.0 2015年12月8日 上午9:30:08 tmser Exp $
 */
public class VideoResViewServiceImpl extends AbstractResViewServiceImpl{
	static final Set<String> SUPPORTSEXTS = new HashSet<String>();
	
	static{
		SUPPORTSEXTS.add("flv");
		SUPPORTSEXTS.add("mp4");
		SUPPORTSEXTS.add("m3u8");
		SUPPORTSEXTS.add("f4v");
	}
	
	public VideoResViewServiceImpl(){
		super.setSupportExts(SUPPORTSEXTS);
		super.setViewName("/resview/video_res_view");
	}
	
	/**
	 * @param res
	 * @return
	 * @see com.mainbo.jy.manage.resources.service.ResViewService#choseView(com.mainbo.jy.manage.resources.bo.Resources)
	 */
	@Override
	public void doView(Resources res) {
		HttpServletRequest request = WebThreadLocalUtils.getRequest();
		request.setAttribute("resid", res.getId());
		request.setAttribute("ext", res.getExt());
	}

}
