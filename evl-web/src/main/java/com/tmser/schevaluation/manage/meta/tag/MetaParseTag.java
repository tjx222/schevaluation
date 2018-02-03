/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.meta.tag;

import java.io.IOException;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.tmser.schevaluation.manage.meta.Meta;
import com.tmser.schevaluation.manage.meta.MetaUtils;

/**
 * <pre>
 *  
 * </pre>
 *
 * @author tmser
 * @version $Id: MetaParseTag.java, v 1.0 2015年3月21日 上午10:13:34 tmser Exp $
 */
public class MetaParseTag extends RequestContextAwareTag{

	private static final long serialVersionUID = 4365476918146653847L;
	
	private Integer key;
	
	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.tags.RequestContextAwareTag#doStartTagInternal()
	 */
	@Override
	protected int doStartTagInternal(){
		out();
		return  EVAL_BODY_INCLUDE;
	}

	protected void out(){
		Meta dic = MetaUtils.getMeta(getKey());
		try {
			if(dic != null )
				pageContext.getOut().write(dic.getName());
		} catch (IOException e) {
			logger.error("can't out the dic name", e);
		}
	}
	
}
