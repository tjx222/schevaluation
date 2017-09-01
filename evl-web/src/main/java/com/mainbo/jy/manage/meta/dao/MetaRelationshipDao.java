/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.meta.dao;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.manage.meta.bo.MetaRelationship;

 /**
 * 元数据关系表 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: MetaRelationship.java, v 1.0 2015-03-11 tmser Exp $
 */
public interface MetaRelationshipDao extends BaseDAO<MetaRelationship, Integer>{
	/**
	 * 根据eid 和 type 查找相关的关系实体
	 * @param eid
	 * @param type
	 * @return
	 */
	MetaRelationship getByEidAndType(Integer eid,Integer type,Integer sort);
	
	/**
	 * 重新getOne,按照id倒叙排序(防止平台脏数据，动态添加，导致id不统一)
	 * @param ship
	 * @return
	 * @see com.mainbo.jy.common.dao.BaseDAO#getOne(java.lang.Object)
	 */
	@Override
	public MetaRelationship getOne(MetaRelationship ship);
}