/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.tmser.schevaluation.manage.org.service.OrganizationService;
import com.tmser.schevaluation.uc.bo.LoginLog;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.bo.UserSpace;
import com.tmser.schevaluation.uc.exception.RequestNotExistsException;
import com.tmser.schevaluation.uc.exception.UserSpaceNotExistsException;
import com.tmser.schevaluation.uc.service.LoginLogService;
import com.tmser.schevaluation.uc.service.SchoolYearService;
import com.tmser.schevaluation.uc.service.UserService;
import com.tmser.schevaluation.uc.service.UserSpaceService;
import com.tmser.schevaluation.uc.utils.SessionKey;
import com.tmser.schevaluation.utils.SpringContextHolder;

/**
 * <pre>
 * 登录session 工具类
 * </pre>
 *
 * @author tmser
 * @version $Id: LoginSessionHelper.java, v 1.0 2015年9月9日 下午1:27:24 tmser Exp $
 */
public abstract class LoginSessionHelper {

  /**
   * 根据用户id,设置session
   * 
   * @param userid
   */
  public static final void setSession(Integer userid) {
    setSession(userid, null);
  }

  private static UserSpace getCandidate(List<UserSpace> ls, Integer cpid) {
    UserSpace cus = null;
    if (cpid != null) {
      for (UserSpace us : ls) {
        if (us.getId().equals(cpid)) {
          cus = us;
          break;
        }
      }
    }

    if (cus == null) {
      for (UserSpace us : ls) {
        cus = us;
        if (cus.getIsDefault() != null && cus.getIsDefault())
          break;
      }
    }
    return cus;
  }

  /**
   * 根据用户id，和当前用户空间id 设置session
   * 
   * @param userid
   * @param spaceid
   */
  public static final void setSession(Integer userid, Integer spaceid) {
    if (SecurityUtils.getSubject() == null) {
      throw new RequestNotExistsException("login user changed");
    }
    Session session = SecurityUtils.getSubject().getSession(false);// 在shiro
                                                                   // 使用容器session
                                                                   // 管理时可用

    if (session != null && userid != null && (session
        .getAttribute(SessionKey.CURRENT_SCHOOLYEAR) == null
        || !userid.equals(
            ((User) session.getAttribute(SessionKey.CURRENT_USER)).getId()))) {
      UserService userService = SpringContextHolder.getBean(UserService.class);
      UserSpaceService userSpaceService = SpringContextHolder
          .getBean(UserSpaceService.class);
      LoginLogService loginLogService = SpringContextHolder
          .getBean(LoginLogService.class);
      SchoolYearService schoolYearService = SpringContextHolder
          .getBean(SchoolYearService.class);
      OrganizationService organizationService = SpringContextHolder
          .getBean(OrganizationService.class);// CURRENT_ORGANIZATION
      User u = userService.findOne(userid);
      session.setAttribute(SessionKey.CURRENT_USER, u);
      UserSpace cus = (UserSpace) session
          .getAttribute(SessionKey.CURRENT_SPACE);
      UserSpace model = new UserSpace();
      model.setUserId(userid);
      model.setEnable(1);
      model.addOrder("sort");
      List<UserSpace> lus = userSpaceService.findAll(model);
      if (lus == null || lus.size() == 0) {
        throw new UserSpaceNotExistsException("用户空间不存在");
      }

      if (cus == null) {
        if (lus.size() == 1) {
          cus = lus.get(0);
        } else {
          cus = getCandidate(lus, spaceid);
        }
      }

      if (loginLogService != null)
        loginLogService.addHistroy(cus, LoginLog.T_LOGIN);

      User um = new User();
      um.setId(u.getId());
      um.setLastLogin(new Date());
      userService.update(um);

      session.setAttribute(SessionKey.CURRENT_SCHOOLYEAR,
          schoolYearService.getCurrentSchoolYear());
      session.setAttribute(SessionKey.USER_SPACE_LIST, lus);
      session.setAttribute(SessionKey.CURRENT_SPACE, cus);
      session.setAttribute(SessionKey.CURRENT_ORGANIZATION,
          organizationService.findOne(cus.getOrgId()));
    }
  }
}
