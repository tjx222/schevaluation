/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.schevaluation.common.web.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.tmser.schevaluation.common.service.BaseService;
import com.tmser.schevaluation.utils.SpringContextHolder;


/**
 * <pre>
 *	id 为整形数据获取标签
 * </pre>
 *
 * @author tmser
 * @version $Id: NavTag.java, v 1.0 2015年2月14日 上午10:44:49 tmser Exp $
 */
public class IntegerKeyDataTag extends RequestContextAwareTag{
	
	protected static final long serialVersionUID = -9104260518603844927L;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private Integer key;
	

    public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}
	
	/**
	 * 数据存储的名称
	 */
	protected String var;
	
	/**
	 *  服务类
	 */
	protected String className = null;
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.tags.RequestContextAwareTag#doStartTagInternal()
	 */
	@Override
	protected int doStartTagInternal(){
		Object baseService = null;
		try {
			baseService = SpringContextHolder.getBean(ClassUtils.forName(getClassName(),ClassUtils.getDefaultClassLoader()));
		} catch (Exception e) {
			logger.error("didn't find the bean", e);
		}
		if(baseService == null || !(baseService instanceof BaseService<?, ?>)){
			return SKIP_BODY;
		}
		out(baseService);
		return  EVAL_BODY_INCLUDE;
	}
	
	@SuppressWarnings("unchecked")
	protected void out(Object baseService){
		 pageContext.setAttribute(var, ((BaseService<?, Integer>)baseService).findOne(getKey()));
	}
	
    /**
     * Release any acquired resources.
     */
	@Override
	public void doFinally(){
		super.doFinally();
		className = null;
		var = null;
		key = null;
    }
}
