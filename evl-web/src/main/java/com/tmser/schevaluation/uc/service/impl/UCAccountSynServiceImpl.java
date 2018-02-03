/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.manage.meta.MetaUtils;
import com.tmser.schevaluation.manage.meta.bo.MetaRelationship;
import com.tmser.schevaluation.manage.org.bo.Organization;
import com.tmser.schevaluation.manage.org.service.OrganizationService;
import com.tmser.schevaluation.uc.LoginSessionHelper;
import com.tmser.schevaluation.uc.SysRole;
import com.tmser.schevaluation.uc.bo.Login;
import com.tmser.schevaluation.uc.bo.Role;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.bo.UserSpace;
import com.tmser.schevaluation.uc.service.LoginService;
import com.tmser.schevaluation.uc.service.PasswordService;
import com.tmser.schevaluation.uc.service.RoleService;
import com.tmser.schevaluation.uc.service.SchoolYearService;
import com.tmser.schevaluation.uc.service.UCAccountSynService;
import com.tmser.schevaluation.uc.service.UserService;
import com.tmser.schevaluation.uc.service.UserSpaceService;
import com.tmser.schevaluation.uc.shiro.Constants;
import com.tmser.schevaluation.uc.utils.SessionKey;
import com.tmser.schevaluation.uc.vo.UCAccountInfo;
import com.tmser.schevaluation.utils.SecurityCode;
import com.tmser.schevaluation.utils.SecurityCode.SecurityCodeLevel;

/**
 * <pre>
 * uc账号同步Service
 * </pre>
 *
 * @author wangdawei
 * @version $Id: UCAccountSynServiceImpl.java, v 1.0 2015年8月19日 下午3:19:55
 *          wangdawei Exp $
 */
@Service
@Transactional
public class UCAccountSynServiceImpl implements UCAccountSynService {

  @Autowired
  private LoginService loginService;
  @Autowired
  private UserService userService;
  @Autowired
  private PasswordService passwordService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private SchoolYearService schoolYearService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private OrganizationService orgService;

  /**
   * 优课账号同步
   * 
   * @param ucAccountInfo
   * @see com.tmser.schevaluation.uc.service.UCAccountSynService#ucAccountSynchronize(com.tmser.schevaluation.uc.vo.UCAccountInfo)
   */
  @Override
  public void ucAccountSynchronize(UCAccountInfo ucAccountInfo) {
    Login login = loginService.findByCellPhone(ucAccountInfo.getAccount());
    if (login == null) {// 不存在则新增
      // 新增登陆信息
      String loginName = getUniqueLoginName();
      String salt = SecurityCode.getSecurityCode(8, SecurityCodeLevel.Hard,
          false);
      String newPassword = passwordService.encryptPassword(loginName,
          ucAccountInfo.getPassword(), salt);
      login = new Login();
      login.setLoginname(loginName);
      login.setPassword(newPassword);
      login.setCellphone(ucAccountInfo.getAccount());
      login.setSalt(salt);
      login.setIsAdmin(false);
      login.setEnable(1);
      login.setDeleted(false);
      login = loginService.save(login);
      // 新增用户信息
      User user = new User();
      user.setId(login.getId());
      user.setNickname(ucAccountInfo.getName());
      user.setName(ucAccountInfo.getName());
      user.setCellphone(ucAccountInfo.getAccount());
      user.setCellphoneValid(true);
      user.setCellphoneView(true);
      user.setAppId(1);
      if (ucAccountInfo.getSex() != null) {
        user.setSex(ucAccountInfo.getSex());
      }
      if (ucAccountInfo.getEmail() != null) {
        user.setMail(ucAccountInfo.getEmail());
      }
      user.setUserType(User.SCHOOL_USER);
      user.setMailValid(false);
      user.setMailView(false);
      user.setIsFamousTeacher(0);
      user.setCrtDttm(new Date());
      user.setLastupDttm(new Date());
      userService.save(user);
    } else {// 存在则更新密码
      String newPass = passwordService.encryptPassword(login.getLoginname(),
          ucAccountInfo.getPassword(), login.getSalt());
      login.setPassword(newPass);
      loginService.update(login);
    }
  }

  /**
   * 生成唯一的非存在的用户名
   * 
   * @return
   */
  private String getUniqueLoginName() {
    String loginName = "gd_"
        + SecurityCode.getSecurityCode(8, SecurityCodeLevel.Simple, false);
    Login login = loginService.findOneByLoginName(loginName);
    if (login != null) {
      loginName = getUniqueLoginName();
    }
    return loginName;
  }

  /**
   * 完善优课账号信息
   * 
   * @param ucAccountInfo
   * @see com.tmser.schevaluation.uc.service.UCAccountSynService#complementUCUserInfo(com.tmser.schevaluation.uc.vo.UCAccountInfo)
   */
  @Override
  public void complementUCUserInfo(UCAccountInfo ucAccountInfo) {
    // 更新user
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    Organization org = orgService.findOne(ucAccountInfo.getOrgId());
    Integer schoolYear = schoolYearService.getCurrentSchoolYear();// 学年
    User model = new User();
    model.setId(user.getId());
    model.setOrgId(ucAccountInfo.getOrgId());
    model.setOrgName(org.getName());
    userService.update(model);
    // 创建用户空间
    Map<Integer, String> spaceMap = new HashMap<Integer, String>();
    List<UserSpace> userSpaceArray = ucAccountInfo.getUserSpaceList();
    for (UserSpace userSpace : userSpaceArray) {
      if (userSpace != null && userSpace.getSysRoleId() != null) {
        // 根据 角色+年级+科目 去重
        if (spaceMap.get(userSpace.getSysRoleId()) != null
            && (userSpace.getSysRoleId() + "_" + userSpace.getGradeId() + "_"
                + userSpace.getSubjectId())
                    .equals(spaceMap.get(userSpace.getSysRoleId()))) {
          continue;
        }
        userSpace.setUsername(user.getName());
        userSpace.setUserId(user.getId());
        userSpace.setOrgId(ucAccountInfo.getOrgId());
        MetaRelationship mr = MetaUtils.getPhaseMetaProvider()
            .getMetaRelationshipByPhaseId(userSpace.getPhaseId());
        userSpace.setPhaseType(mr.getEid());
        Integer usePosition = null;
        if (org.getType().intValue() == (Organization.SCHOOL)) {
          usePosition = 2;
        } else {
          usePosition = 1;
        }
        List<Role> roleList = roleService
            .findRoleListByUseOrgId(ucAccountInfo.getOrgId(), usePosition); // 根据学校获取所有角色，没有就获取系统默认的学校角色集合
        for (Role r : roleList) {
          if (r.getSysRoleId().intValue() == userSpace.getSysRoleId()
              .intValue()) {
            userSpace.setRoleId(r.getId()); // 加入角色id
            break;
          }
        }
        if (userSpace.getSysRoleId().equals(SysRole.JYZR.getId())
            || userSpace.getSysRoleId().equals(SysRole.JYY.getId())) { // 教研主任或教研员
          userSpace.setGradeId(0);
          if (userSpace.getSysRoleId().equals(SysRole.JYZR.getId())) {
            userSpace.setSubjectId(0);
          }
        } else if (userSpace.getSysRoleId().equals(SysRole.XZ.getId())
            || userSpace.getSysRoleId().equals(SysRole.FXZ.getId())
            || userSpace.getSysRoleId().equals(SysRole.ZR.getId())) {
          userSpace.setGradeId(0);
          userSpace.setSubjectId(0);
        } else if (userSpace.getSysRoleId().equals(SysRole.XKZZ.getId())) {// 学科组长
          userSpace.setGradeId(0);
        } else if (userSpace.getSysRoleId().equals(SysRole.NJZZ.getId())) {// 年级组长
          userSpace.setSubjectId(0);
        }

        userSpace.setSpaceHomeUrl(Constants.EVL_INDEX);
        userSpace.setSort(1);
        userSpace.setIsDefault(false);
        userSpace.setEnable(1);
        if (org.getType() == Organization.SCHOOL) {
          userSpace.setSchoolYear(schoolYear);
        }
        userSpaceService.save(userSpace);
        spaceMap.put(userSpace.getSysRoleId(), userSpace.getSysRoleId() + "_"
            + userSpace.getGradeId() + "_" + userSpace.getSubjectId());
      }
    }
    WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_USER, null);// 更新session
    LoginSessionHelper.setSession(user.getId());
  }

}
