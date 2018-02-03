/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.orm.SqlMapping;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.uc.bo.Role;
import com.tmser.schevaluation.uc.dao.RoleDao;
import com.tmser.schevaluation.uc.service.RoleService;

/**
 * 用户角色 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Role.java, v 1.0 2015-02-05 Generate Tools Exp $
 */
@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role, Integer>
    implements RoleService {

  @Autowired
  private RoleDao roleDao;

  /**
   * @return
   * @see com.tmser.schevaluation.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<Role, Integer> getDAO() {
    return roleDao;
  }

  /**
   * @param userid
   * @return
   * @see com.tmser.schevaluation.uc.service.RoleService#findRoleByUserid(java.lang.Integer)
   */
  @Override
  public List<Role> findRoleByUserid(Integer userid, Integer sysRoleId) {
    if (userid != null) {
      return roleDao.listRoleByUserid(userid, sysRoleId);
    }
    return null;
  }

  /**
   * 通过机构和应用方向获取角色集合
   * 
   * @param orgId
   * @param usePosition
   * @return
   */
  @Override
  public List<Role> findRoleListByUseOrgId(Integer orgId, Integer usePosition) {
    Role rl = new Role();
    rl.setSolutionId(0);
    rl.setIsDel(false);
    rl.setUsePosition(usePosition);
    return roleDao.listAll(rl);
  }

  @Override
  public List<Role> findRoleByRoleName(String roleName, Integer usePosition) {
    Role rl = new Role();
    rl.setRoleName(SqlMapping.LIKE_PRFIX + roleName + SqlMapping.LIKE_PRFIX);
    rl.setSolutionId(0);
    rl.setIsDel(false);
    rl.setUsePosition(usePosition);
    return roleDao.listAll(rl);
  }

  @Override
  public List<Role> findRoleBySysRoleId(Integer sysRoleId,
      Integer usePosition) {
    Role rl = new Role();
    rl.setSysRoleId(sysRoleId);
    rl.setSolutionId(0);
    rl.setIsDel(false);
    rl.setUsePosition(usePosition);
    return roleDao.listAll(rl);
  }
}
