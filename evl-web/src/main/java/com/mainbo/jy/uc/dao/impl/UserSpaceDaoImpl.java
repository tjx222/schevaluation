/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mainbo.jy.common.dao.AbstractDAO;
import com.mainbo.jy.common.dao.annotation.UseCache;
import com.mainbo.jy.uc.bo.UserSpace;
import com.mainbo.jy.uc.dao.UserSpaceDao;

/**
 * 用户空间信息 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserSpace.java, v 1.0 2015-02-03 Generate Tools Exp $
 */
@Repository
@UseCache
public class UserSpaceDaoImpl extends AbstractDAO<UserSpace,Integer> implements UserSpaceDao {
	
	private static final Logger logger = LoggerFactory.getLogger(UserSpaceDaoImpl.class);
	/**
	 * 通过用户id批量查询工作空间
	 * @param userIds
	 * @return
	 */
	@Override
	public List<UserSpace> findByUserIds(List<Integer> userIds) {
		String sql ="select * from UserSpace where userId in(:userIds)";
		Map<String,Object> map =new HashMap<>();
		map.put("userIds", userIds);
		return this.queryByNamedSql(sql, map, this.mapper);
	}

	/**
	 * 通过角色获取某机构的用户空间集合
	 * @param xz
	 * @param orgId
	 * @param subjectIdsStr
	 * @param gradeIdsStr
	 * @return
	 * @see com.mainbo.jy.uc.dao.UserSpaceDao#getUserSpaceByRole(com.mainbo.jy.common.SysRole, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserSpace> getUserSpaceByOrg_Subject_Grade(List<Integer> orgIdList,List<Integer> subjectIdList, List<Integer> gradeIdList) {
		String sql = "select distinct userId from UserSpace where orgId in(:orgIdList) and subjectId in(:subjectIdList) and gradeId in(:gradeIdList)";
		Map<String,Object> map =new HashMap<>();
		map.put("orgIdList", orgIdList);
		map.put("subjectIdList", subjectIdList);
		map.put("gradeIdList", gradeIdList);
		return this.queryByNamedSql(sql, map, getMapper());
		
	}

	/**
	 * @param solutionId
	 * @param orgId
	 * @see com.mainbo.jy.uc.dao.UserSpaceDao#updateUserSolution(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateUserSolution(Integer solutionId, Integer orgId,Integer schoolYear) {
		//update sys_user_menu m INNER JOIN sys_user_space s ON m.user_id =s.user_id set m.sys_role_id=(select r.id from sys_role r where  r.solution_id = 9 and r.sys_role_id=(select s.sys_role_id from sys_user_space s where s.user_id = m.user_id and s.role_id=m.sys_role_id LIMIT 0,1) LIMIT 0,1)
		//		 where s.school_year=2016 and s.org_id=65
		String sql = ("update UserMenu m inner join UserSpace s on m.userId =s.userId"
				+ " set m.sysRoleId=(select r.id from Role r where  r.solutionId = :solutionId and "
				+ " r.sysRoleId=(select s.sysRoleId from UserSpace s "
				+ "where s.userId = m.userId and s.roleId=m.sysRoleId LIMIT 0,1) LIMIT 0,1)"
				+ " where s.orgId=:orgId and s.schoolYear=:schoolYear");
		Map<String,Object> map =new HashMap<>();
		map.put("solutionId", solutionId);
		map.put("orgId", orgId);
		map.put("schoolYear", schoolYear);
		
	    int rs = super.updateWithNamedSql(sql.toString(),map);//更新菜单
	    
	    logger.info("update solution id=[{}] of school id=[{}] effect sys_user_menu [{}] lines ",solutionId,orgId,rs);
		
		sql = ("update UserSpace s "
				+ "set s.roleId=(select r.id from Role r where r.sysRoleId=s.sysRoleId and r.solutionId = :solutionId LIMIT 0,1)"
				+ "	where exists (select 1"
				+ "	from  Role r where r.sysRoleId=s.sysRoleId and r.solutionId =:solutionId and s.orgId=:orgId and s.schoolYear=:schoolYear) and s.schoolYear=:schoolYear and s.orgId=:orgId" );
		rs = updateWithNamedSql(sql.toString(),map);//更新身份
		
		logger.info("update solution id=[{}] of school id=[{}] effect sys_user_space [{}] lines ",solutionId,orgId,rs);
	}

}
