/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.schconfig.clss.dao.impl;

import org.springframework.stereotype.Repository;

import com.mainbo.jy.common.dao.AbstractDAO;
import com.mainbo.jy.schconfig.clss.bo.SchClassUser;
import com.mainbo.jy.schconfig.clss.dao.SchClassUserDao;

/**
 * 学校班级用户关联 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClassUser.java, v 1.0 2016-02-19 tmser Exp $
 */
@Repository
public class SchClassUserDaoImpl extends AbstractDAO<SchClassUser,Integer> implements SchClassUserDao {

	/**
	 * @param clssid
	 * @see com.mainbo.jy.schconfig.clss.dao.SchClassUserDao#deleteByClassId(java.lang.Integer)
	 */
	@Override
	public void deleteByClassId(Integer clssid) {
		if(clssid != null){
			String sql  = "delete from SchClassUser where classId = ?";
			super.update(sql, clssid);
		}
		
	}
}
