/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.orm.SqlMapping;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.common.utils.WebThreadLocalUtils;
import com.mainbo.jy.uc.bo.Role;
import com.mainbo.jy.uc.bo.SysOrgSolution;
import com.mainbo.jy.uc.bo.User;
import com.mainbo.jy.uc.dao.RoleDao;
import com.mainbo.jy.uc.dao.SysOrgSolutionDao;
import com.mainbo.jy.uc.service.RoleService;
import com.mainbo.jy.uc.utils.SessionKey;
/**
 * 用户角色 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Role.java, v 1.0 2015-02-05 Generate Tools Exp $
 */
@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role, Integer> implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private SysOrgSolutionDao sosDao;
	/**
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Role, Integer> getDAO() {
		return roleDao;
	}

	/**
	 * @param userid
	 * @return
	 * @see com.mainbo.jy.uc.service.RoleService#findRoleByUserid(java.lang.Integer)
	 */
	@Override
	public List<Role> findRoleByUserid(Integer userid,Integer sysRoleId) {
		if(userid != null){
			return roleDao.listRoleByUserid(userid,sysRoleId);
		}
		return null;
	}

	/**
	 * 通过机构和应用方向获取角色集合
	 * @param orgId
	 * @param usePosition
	 * @return
	 */
	@Override
	public List<Role> findRoleListByUseOrgId(Integer orgId, Integer usePosition) {
		SysOrgSolution sos = new SysOrgSolution();
		Role rl = new Role();
		if(orgId != null){
			sos.setOrgId(orgId);
			SysOrgSolution orgSolution = sosDao.getOne(sos);
			if(orgSolution != null){
				rl.setSolutionId(orgSolution.getSolutionId());
			}else{
				rl.setSolutionId(0);
			}
		}else{
			rl.setSolutionId(0);
		}
		rl.setIsDel(false);
		rl.setUsePosition(usePosition);
		return roleDao.listAll(rl);
	}
	
	@Override
	public List<Role> findRoleByRoleName(String roleName, Integer usePosition) {
		SysOrgSolution sos = new SysOrgSolution();
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Role rl = new Role();
		rl.setRoleName(SqlMapping.LIKE_PRFIX +roleName+ SqlMapping.LIKE_PRFIX);
		if(user != null && user.getOrgId()!= null){
			sos.setOrgId(user.getOrgId());
			SysOrgSolution orgSolution = sosDao.getOne(sos);
			if(orgSolution != null){
				rl.setSolutionId(orgSolution.getSolutionId());
			}else{
				rl.setSolutionId(0);
			}
		}else{
			rl.setSolutionId(0);
		}
		rl.setIsDel(false);
		rl.setUsePosition(usePosition);
		return  roleDao.listAll(rl);
	}

	@Override
	public List<Role> findRoleBySysRoleId(Integer sysRoleId, Integer usePosition) {
		SysOrgSolution sos = new SysOrgSolution();
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Role rl = new Role();
		rl.setSysRoleId(sysRoleId);
		if(user != null && user.getOrgId()!= null){
			sos.setOrgId(user.getOrgId());
			SysOrgSolution orgSolution = sosDao.getOne(sos);
			if(orgSolution != null){
				rl.setSolutionId(orgSolution.getSolutionId());
			}else{
				rl.setSolutionId(0);
			}
		}else{
			rl.setSolutionId(0);
		}
		rl.setIsDel(false);
		rl.setUsePosition(usePosition);
		return  roleDao.listAll(rl);
	}
}
