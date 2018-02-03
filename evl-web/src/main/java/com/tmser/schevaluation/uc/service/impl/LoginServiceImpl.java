/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.schevaluation.common.bo.BaseObject;
import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.uc.bo.Login;
import com.tmser.schevaluation.uc.bo.LoginLog;
import com.tmser.schevaluation.uc.bo.Permission;
import com.tmser.schevaluation.uc.bo.Role;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.bo.UserSpace;
import com.tmser.schevaluation.uc.dao.LoginDao;
import com.tmser.schevaluation.uc.exception.UcException;
import com.tmser.schevaluation.uc.exception.UserBlockedException;
import com.tmser.schevaluation.uc.exception.UserNotExistsException;
import com.tmser.schevaluation.uc.exception.UserPasswordNotMatchException;
import com.tmser.schevaluation.uc.listenner.LoginSuccessListenner;
import com.tmser.schevaluation.uc.log.LoginLogUtils;
import com.tmser.schevaluation.uc.service.LoginLogService;
import com.tmser.schevaluation.uc.service.LoginService;
import com.tmser.schevaluation.uc.service.PasswordService;
import com.tmser.schevaluation.uc.service.PermissionService;
import com.tmser.schevaluation.uc.service.RoleService;
import com.tmser.schevaluation.uc.service.UserService;
import com.tmser.schevaluation.uc.utils.SessionKey;
import com.tmser.schevaluation.utils.StringUtils;

/**
 * <pre>
 * 登录服务
 * </pre>
 *
 * @author tmser
 * @version $Id: LoginServiceImpl.java, v 1.0 2015年1月31日 下午1:56:04 tmser Exp $
 */
@Service
@Transactional
public class LoginServiceImpl extends AbstractService<Login, Integer>
    implements LoginService {
  private static final Logger logger = LoggerFactory
      .getLogger(LoginServiceImpl.class);
  @Resource
  private LoginDao loginDao;

  @Resource
  private PasswordService passwordService;

  @Autowired(required = false)
  private LoginSuccessListenner loginSuccessListenner;

  @Autowired(required = false)
  private LoginLogService loginLogService;

  @Resource
  private RoleService roleService;

  @Resource
  private PermissionService permissionService;

  @Resource
  private UserService userService;

  /**
   * 按用户名查找
   * 
   * @param username
   * @return
   * @see com.tmser.schevaluation.uc.service.LoginService#findByUsername(java.lang.String)
   */
  @Override
  public Login findByUsername(String username) {
    if (StringUtils.isEmpty(username)) {
      return null;
    }
    Login model = new Login();
    model
        .buildCondition("and (loginname = :username or logincode = :username )")
        .put("username", username);
    Login login = loginDao.getOne(model);
    if (login != null && StringUtils.isNotBlank(login.getLogincode())) {
      User u = userService.findOne(login.getId());
      if (u.getAppId() == 0) {
        login = null;
      }
    }
    return login;
  }

  /**
   * 按邮箱查找用户
   * 
   * @param email
   * @return
   * @see com.tmser.schevaluation.uc.service.LoginService#findByEmail(java.lang.String)
   */
  @Override
  public Login findByEmail(String email) {
    if (StringUtils.isEmpty(email)) {
      return null;
    }
    Login model = new Login();
    model.setMail(email);
    return loginDao.getOne(model);
  }

  /**
   * 按手机号查找
   * 
   * @param mobilePhoneNumber
   * @return
   * @see com.tmser.schevaluation.uc.service.LoginService#findByMobilePhoneNumber(java.lang.String)
   */
  @Override
  public Login findByCellPhone(String cellphone) {
    if (StringUtils.isEmpty(cellphone)) {
      return null;
    }
    Login model = new Login();
    model.setCellphone(cellphone);
    return loginDao.getOne(model);
  }

  /**
   * @param principal
   * @param password
   * @return
   * @throws UcException
   * @see com.tmser.schevaluation.uc.service.LoginService#login(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public Login login(String principal, String password) throws UcException {
    if (StringUtils.isEmpty(principal) || StringUtils.isEmpty(password)) {
      LoginLogUtils.log(principal, "loginError", "principal is empty");
      throw new UserNotExistsException();
    }
    // 密码如果不在指定范围内 肯定错误
    if (password.length() < Login.PASSWORD_MIN_LENGTH
        || password.length() > Login.PASSWORD_MAX_LENGTH) {
      LoginLogUtils.log(principal, "loginError",
          "password length error! password is between {} and {}",
          Login.PASSWORD_MIN_LENGTH, Login.PASSWORD_MAX_LENGTH);

      throw new UserPasswordNotMatchException();
    }

    Login login = null;

    if (maybeUsername(principal)) {
      login = findByUsername(principal);
    }

    if (login == null && maybeEmail(principal)) {
      login = findByEmail(principal);
    }

    if (login == null && maybeMobilePhoneNumber(principal)) {
      login = findByCellPhone(principal);
    }

    if (login == null || Boolean.TRUE.equals(login.getDeleted())) {
      LoginLogUtils.log(principal, "loginError", "user is not exists!");

      throw new UserNotExistsException();
    }

    passwordService.validate(login, password);

    if (BaseObject.ENABLE != login.getEnable()) {
      LoginLogUtils.log(principal, "loginError", "user is blocked!");
      throw new UserBlockedException("");// TODO 获取禁用理由
    }

    LoginLogUtils.log(principal, "loginSuccess", "");

    try {
      if (loginSuccessListenner != null) {
        loginSuccessListenner.doOnSuccess(login.getId());
      }
    } catch (Exception e) {
      logger.error("invoke login listenner failed!", e);
    }

    return login;
  }

  private boolean maybeUsername(String principal) {
    if (!principal.matches(Login.USERNAME_PATTERN)) {
      return false;
    }
    // 如果用户名不在指定范围内也是错误的
    if (principal.length() < Login.USERNAME_MIN_LENGTH
        || principal.length() > Login.USERNAME_MAX_LENGTH) {
      return false;
    }

    return true;
  }

  private boolean maybeEmail(String principal) {
    if (!principal.matches(Login.EMAIL_PATTERN)) {
      return false;
    }
    return true;
  }

  private boolean maybeMobilePhoneNumber(String principal) {
    if (!principal.matches(Login.MOBILE_PHONE_NUMBER_PATTERN)) {
      return false;
    }
    return true;
  }

  /**
   * @return
   * @see com.tmser.schevaluation.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<Login, Integer> getDAO() {
    return loginDao;
  }

  /**
   * @param spaceid
   * @return
   * @see com.tmser.schevaluation.uc.service.LoginService#toWorkSpace(java.lang.Integer)
   */
  @SuppressWarnings("unchecked")
  @Override
  public String toWorkSpace(Integer spaceid) {
    UserSpace us = (UserSpace) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SPACE);

    String url = null;

    if (us != null && (spaceid == null || us.getId().equals(spaceid))) {
      url = us.getSpaceHomeUrl();
    }

    if (url == null) {
      List<UserSpace> usl = (List<UserSpace>) WebThreadLocalUtils
          .getSessionAttrbitue(SessionKey.USER_SPACE_LIST);
      if (usl != null) {
        for (UserSpace u : usl) {
          if (u.getId().equals(spaceid)) {
            url = u.getSpaceHomeUrl();// 切换session
            WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_SPACE,
                u);
            if (loginLogService != null)
              loginLogService.addHistroy(u, LoginLog.T_CHANGE);

            break;
          }
        }
      }
    }
    return url == null ? "/" : url;
  }

  public void setLoginSuccessListenner(LoginSuccessListenner lsl) {
    this.loginSuccessListenner = lsl;
  }

  public void setLoginDao(LoginDao loginDao) {
    this.loginDao = loginDao;
  }

  public void setPasswordService(PasswordService passwordService) {
    this.passwordService = passwordService;
  }

  /**
   * @param uid
   * @return
   * @see com.tmser.schevaluation.uc.service.LoginService#findStringRoles(java.lang.Integer)
   */
  @Override
  public Set<String> findStringRoles(Integer uid) {
    Set<String> roleSet = new HashSet<>();
    if (uid != null) {
      List<Role> rs = roleService.findRoleByUserid(uid, null);
      for (Role r : rs) {
        if (r != null)
          roleSet.add(r.getRoleCode());
      }
    }
    return roleSet;
  }

  /**
   * @param uid
   * @return
   * @see com.tmser.schevaluation.uc.service.LoginService#findStringPermissions(java.lang.Integer)
   */
  @Override
  public Set<String> findStringPermissions(Integer uid) {
    Set<String> permissionSet = new HashSet<>();
    if (uid != null) {
      UserSpace us = (UserSpace) WebThreadLocalUtils
          .getSessionAttrbitue(SessionKey.CURRENT_SPACE);
      List<Role> rs = roleService.findRoleByUserid(uid, us.getSysRoleId());

      for (Role r : rs) {
        List<Permission> ps = permissionService
            .findPermissionByRoleid(r.getId());
        for (Permission p : ps) {
          if (p != null)
            permissionSet.add(p.getCode());
        }
      }

    }
    return permissionSet;
  }

  /**
   * @param loginname
   * @return
   * @see com.tmser.schevaluation.uc.service.LoginService#findLogin(java.lang.String)
   */
  @Override
  public Login findLogin(String principal) {
    Login login = null;

    if (maybeUsername(principal)) {
      login = findByUsername(principal);
    }

    if (login == null && maybeEmail(principal)) {
      login = findByEmail(principal);
    }

    if (login == null && maybeMobilePhoneNumber(principal)) {
      login = findByCellPhone(principal);
    }

    return login;
  }

  /**
   * 用户互通校验
   * 
   * @param username
   * @param password
   * @return
   * @see com.tmser.schevaluation.uc.service.LoginService#checkAccountLoginAccessProtocol(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public Map<String, Object> checkAccountLoginAccessProtocol(String username,
      String password) {
    Map<String, Object> map = new HashMap<String, Object>();
    Login model = new Login();
    model.setLoginname(username);
    model.setPassword(password);
    model = loginDao.getOne(model);
    if (model != null) {
      User user = userService.findOne(model.getId());
      if (user != null) {
        map.put("restStatus", "200");
        Map<String, Object> appUser = new HashMap<String, Object>();
        Map<String, String> appUserMap = new HashMap<String, String>();
        appUserMap.put("account", model.getLoginname());
        appUserMap.put("accountStatus", model.getEnable() == 1 ? "1" : "2");
        appUserMap.put("appAuthority", "");
        appUserMap.put("appKey", "");
        appUserMap.put("createHost", "");
        appUserMap.put("userType", "27");
        appUserMap.put("callphone", user.getCellphone());
        appUserMap.put("mail", user.getMail());
        appUserMap.put("nickname", user.getNickname());
        appUserMap.put("password", model.getPassword());
        appUserMap.put("username", user.getName());
        appUser.put("appUser", appUserMap);
        map.put("dataMap", appUser);
      } else {
        map.put("restStatus", "500");
        map.put("errorCode", "5X013");
        map.put("message", "没有匹配到有效的用户");
      }
    } else {
      map.put("restStatus", "500");
      map.put("errorCode", "5X013");
      map.put("message", "没有匹配到有效的用户");
    }
    return map;
  }

  /**
   * 通过账户名称获得账户对象
   * 
   * @param account
   * @return
   * @see com.tmser.schevaluation.uc.service.LoginService#getLoginByLoginname(java.lang.String)
   */
  @Override
  public Login getLoginByLoginname(String account) {
    Login model = new Login();
    model.setLoginname(account);
    return loginDao.getOne(model);
  }

  /**
   * 根据登录名查找
   * 
   * @param loginName
   * @return
   * @see com.tmser.schevaluation.uc.service.LoginService#findOneByLoginName(java.lang.String)
   */
  @Override
  public Login findOneByLoginName(String loginName) {
    Login login = new Login();
    login.setLoginname(loginName);
    List<Login> loginList = find(login, 1);
    if (loginList != null && loginList.size() > 0) {
      return loginList.get(0);
    }
    return null;
  }

  /**
   * @param loginname
   * @param psd
   * @return
   * @see com.tmser.schevaluation.uc.service.LoginService#newLogin(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public Login newLogin(String loginname, String psd) {
    Login l = new Login();
    l.setLoginname(loginname);
    l.setPassword(psd);
    l.setDeleted(false);
    l.setEnable(1);
    l.setIsAdmin(false);
    return l;
  }

}
