/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.schevaluation.common.annotation.UseToken;
import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.common.vo.Result;
import com.tmser.schevaluation.common.web.controller.AbstractController;
import com.tmser.schevaluation.uc.Constants;
import com.tmser.schevaluation.uc.bo.Login;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.bo.UserSpace;
import com.tmser.schevaluation.uc.exception.UserPasswordRetryLimitExceedException;
import com.tmser.schevaluation.uc.service.LoginService;
import com.tmser.schevaluation.uc.service.PasswordService;
import com.tmser.schevaluation.uc.service.UserService;
import com.tmser.schevaluation.uc.utils.SessionKey;

/**
 * <pre>
 * 用户登录，找回密码
 * </pre>
 *
 * @author tmser
 * @version $Id: LoginController.java, v 1.0 2015年2月1日 下午10:55:37 tmser Exp $
 */
@Controller
@RequestMapping("/jy/uc")
public class LoginController extends AbstractController {

  @Resource
  private LoginService loginService;

  @Autowired
  private UserService userService;

  @Resource
  private PasswordService passwordService;

  @RequestMapping(value = { "/{login:login;?.*}" })
  public String loginForm(ModelMap model) {
    // 表示退出
    if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("logout"))) {
      model.addAttribute(Constants.MESSAGE, messageSource.getMessage("user.logout.success", null, null));
    }

    // 表示用户删除了 @see org.apache.shiro.web.filter.user.SysUserFilter
    if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("notfound"))) {
      model.addAttribute(Constants.ERROR, messageSource.getMessage("user.notfound", null, null));
    }

    // 表示用户被管理员强制退出
    if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("forcelogout"))) {
      model.addAttribute(Constants.ERROR, messageSource.getMessage("user.forcelogout", null, null));
    }

    // 表示用户输入的验证码错误
    if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("jcaptchaError"))) {
      model.addAttribute(Constants.ERROR, messageSource.getMessage("jcaptcha.validate.error", null, null));
    }

    // 表示用户锁定了 @see org.apache.shiro.web.filter.user.SysUserFilter
    if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("blocked"))) {
      // TODO 锁定原因
      model.addAttribute(Constants.ERROR, messageSource.getMessage("user.blocked", new Object[] { "" }, null));
    }

    if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("unknown"))) {
      model.addAttribute(Constants.ERROR, messageSource.getMessage("user.unknown.error", null, null));
    }

    // 登录失败了 提取错误消息
    Exception shiroLoginFailureEx = (Exception) WebThreadLocalUtils
        .getAttrbitue(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
    if (shiroLoginFailureEx != null
        && StringUtils.isBlank((String) WebThreadLocalUtils.getAttrbitue(Constants.ERROR))) {
      if (shiroLoginFailureEx != null) {
        if (shiroLoginFailureEx instanceof UnknownAccountException
            || shiroLoginFailureEx instanceof ExcessiveAttemptsException
            || shiroLoginFailureEx instanceof UserPasswordRetryLimitExceedException
            || shiroLoginFailureEx instanceof LockedAccountException) {
          model.addAttribute(Constants.ERROR, shiroLoginFailureEx.getMessage());
        } else if (shiroLoginFailureEx instanceof AuthenticationException) {
          model.addAttribute(Constants.ERROR, messageSource.getMessage("user.not.exists", null, null));
          logger.error("login failed", shiroLoginFailureEx);
        } else {
          model.addAttribute(Constants.ERROR, messageSource.getMessage("user.unknown.error", null, null));
          logger.error("login failed", shiroLoginFailureEx);
        }
      }
    }

    // 如果用户直接到登录页面 先退出一下
    // 原因：isAccessAllowed实现是subject.isAuthenticated()---->即如果用户验证通过 就允许访问
    // 这样会导致登录一直死循环
    Subject subject = SecurityUtils.getSubject();
    if (subject != null && subject.isAuthenticated()) {
      subject.logout();
    }

    // 如果同时存在错误消息 和 普通消息 只保留错误消息
    if (model.containsAttribute(Constants.ERROR)) {
      model.remove(Constants.MESSAGE);
    }
    return "index";
  }

  @RequestMapping(value = { "/switch", "/workspace" })
  public String switchWorkSpace(Integer spaceid) {
    return "forward:" + loginService.toWorkSpace(spaceid);
  }

  @RequestMapping("/modify")
  @UseToken
  public String modify(Integer type, Model m) {
    User us = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    m.addAttribute("user", us);
    m.addAttribute("type", type);
    m.addAttribute("login", loginService.findOne(us.getId()));
    return viewName("modify");
  }

  @RequestMapping("/savemodifymobile")
  @ResponseBody
  @UseToken
  public Result savePwdModify(Model m, Login login, String newpassword) {
    Result re = new Result();
    User us = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    Login logins = loginService.findOne(us.getId());
    newpassword = passwordService.encryptPassword(logins.getLoginname(), newpassword, logins.getSalt());
    login.setPassword(newpassword);
    login.setId(us.getId());
    loginService.update(login);
    m.addAttribute("msg", "修改成功");
    re.setCode(1);
    re.setMsg("修改密码成功");
    return re;
  }

  // 找回密码
  @RequestMapping("/findps/saveretrievepassword")
  public String saveRetrievePassword(Model m, Login login, String newpassword, Integer id) {
    Login logins = loginService.findOne(id);
    newpassword = passwordService.encryptPassword(logins.getLoginname(), newpassword, logins.getSalt());
    login.setPassword(newpassword);
    login.setId(id);
    loginService.update(login);
    return viewName("resetpassword_success");
  }

  @RequestMapping("/findps/retrievepassword")
  public String retrievePassword() {
    return viewName("retrievepassword");
  }

  @RequestMapping("/findps/resetpassword")
  public String resetPassword(Model m, String loginname, String name) {
    Login login = loginService.findLogin(loginname);
    if (login != null) {
      User user = userService.findOne(login.getId());
      if (!name.equals(user.getName())) {
        m.addAttribute("error", "您输入的用户名或教师姓名不正确，请重新输入！");
        return viewName("retrievepassword");
      } else {
        m.addAttribute("id", login.getId());
        return viewName("resetpassword");
      }
    } else {
      m.addAttribute("error", "您输入的用户名或教师姓名不正确，请重新输入！");
      return viewName("retrievepassword");
    }
  }

  // 验证用户密码
  @RequestMapping("/findps/verifyUsePassword")
  @ResponseBody
  public Object[] verifyUsePassword(String fieldId, @RequestParam("fieldValue") String password) {
    Object[] result = new Object[2];
    result[0] = fieldId;
    result[1] = true;
    User us = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    Login logins = loginService.findOne(us.getId());
    String passwords = passwordService.encryptPassword(logins.getLoginname(), password, logins.getSalt());
    if (!passwords.equals(logins.getPassword())) {
      result[1] = false;
    }
    return result;
  }

  @RequestMapping("/select")
  public String toSelect(Model m) {
    UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    us.setPhaseId(0);
    return "forward:/index";
  }

  /**
   * 验证Session是否失效
   * 
   * @param m
   * @param request
   */
  @RequestMapping("/isSessionOk")
  public void isSessionOk(Model m) {
    Object user = WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    if (user != null) {
      m.addAttribute("invalidated", false);
    } else {
      m.addAttribute("invalidated", true);
    }
  }

}
