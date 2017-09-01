/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.common.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mainbo.jy.common.bo.QueryObject;
import com.mainbo.jy.common.page.PageList;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: AbstractService.java, v 1.0 2015年1月31日 下午2:43:08 tmser Exp $
 */
public abstract class AbstractService<E extends QueryObject, K extends Serializable> implements BaseService<E, K> {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @param model
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#save(java.lang.Object)
	 */
	@Override
	public E save(E model) {
		return getDAO().insert(model);
	}

	/**
	 * @param model
	 * @see com.mainbo.jy.common.service.BaseService#update(java.lang.Object)
	 */
	@Override
	public int update(E model) {
		return getDAO().update(model);
	}

	/**
	 * @param id
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#findOne(java.io.Serializable)
	 */
	@Override
	public E findOne(K id) {
		if(null == id)
			return null;
		return getDAO().get(id);
	}
	
	@Override
	public E findOne(E model) {
		if(null == model)
			return null;
		return getDAO().getOne(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#listPage(java.lang.Object)
	 */
	@Override
	public PageList<E> findByPage(E model) {
		return getDAO().listPage(model);
	}

	/**
	 * @param model
	 * @param limit
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#list(java.lang.Object, int)
	 */
	@Override
	public List<E> find(E model, int limit) {
		return getDAO().list(model,limit);
	}

	/**
	 * @param model
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#listAll(java.lang.Object)
	 */
	@Override
	public List<E> findAll(E model) {
		return getDAO().listAll(model);
	}

	/**
	 * @param id
	 * @see com.mainbo.jy.common.service.BaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(K id) {
		getDAO().delete(id);
	}

	/**
	 * @param model
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#count(java.lang.Object)
	 */
	@Override
	public int count(E model) {
		return getDAO().count(model);
	}

	/**
	 * @param id
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#exists(java.io.Serializable)
	 */
	@Override
	public boolean exists(K id) {
		
		return findOne(id) != null;
	}
	
}
