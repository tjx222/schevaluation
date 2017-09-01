/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.common.service;

import java.io.Serializable;
import java.util.List;

import com.mainbo.jy.common.bo.QueryObject;
import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.page.PageList;

/**
 * <pre>
 * 通用的service 接口
 * </pre>
 *
 * @author tmser
 * @version $Id: BaseService.java, v 1.0 2015年1月31日 下午2:34:08 tmser Exp $
 */
public interface BaseService<E extends QueryObject, K extends Serializable> {
	
	/**
	 * 获取
	 * @return
	 */
	BaseDAO<E, K> getDAO();

	/**
	 * 插入
	 * 
	 * @param model
	 *            要插入的对象属性
	 */
	E save(E model);

	/**
	 * 更新
	 * 
	 * @param model
	 *            更新的属性
	 * @return 返回受影响结果条数
	 */
	int update(E model);

	/**
	 * 根据主键id 获取记录
	 * 
	 * @param id
	 *            id
	 * @return 获取的对象记录
	 */
	E findOne(K id);
	
	/**
	 * 根据模型 获取记录
	 * 
	 * @param model
	 *            model
	 * @return 获取的对象记录
	 */
	E findOne(E model);

	/**
	 * 根据模板对象分页查询
	 * 
	 * @param model
	 *            查询条件
	 * @return 结果集合
	 */
	PageList<E> findByPage(E model);

	/**
	 * 根据模板对象分页查询,并限制结果数
	 * 
	 * @param model  查询条件
	 * @param limit 结果条数
	 * @return 结果集合
	 */
	List<E> find(E model,int limit);
	
	/**
	 * 根据模板对象分页查询
	 * 
	 * @param model  查询条件
	 * @return 结果集合
	 */
	List<E> findAll(E model);
	
	/**
	 * 根据id 删除
	 * 
	 * @param id
	 *            对象id
	 */
	void delete(K id);
	
	/**
	 * 根据模式统计结果
	 * @param model
	 * @return
	 */
	int count(E model);
	
	/**
     * 实体是否存在
     *
     * @param id 主键
     * @return 存在 返回true，否则false
     */
    public boolean exists(K id);
}
