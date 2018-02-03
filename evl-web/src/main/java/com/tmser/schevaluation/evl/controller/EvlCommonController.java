package com.tmser.schevaluation.evl.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.common.web.controller.AbstractController;
import com.tmser.schevaluation.evl.bizservice.EvlquestionBizService;
import com.tmser.schevaluation.evl.service.EvlQuestionnairesService;
import com.tmser.schevaluation.manage.meta.MetaUtils;
import com.tmser.schevaluation.manage.meta.bo.MetaRelationship;
import com.tmser.schevaluation.manage.org.bo.Organization;
import com.tmser.schevaluation.manage.org.service.OrganizationService;
import com.tmser.schevaluation.uc.SysRole;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.bo.UserSpace;
import com.tmser.schevaluation.uc.dao.RoleTypeDao;
import com.tmser.schevaluation.uc.utils.SessionKey;
import com.tmser.schevaluation.utils.StringUtils;

/**
 * 主页控制器
 * 
 * <pre>
 *
 * </pre>
 *
 * @author gengqianfeng
 * @version $Id: EvlCommonController.java, v 1.0 2016年5月10日 下午6:04:10
 *          gengqianfeng Exp $
 */
@Controller
@RequestMapping("/jy/evl")
public class EvlCommonController extends AbstractController {

  @Autowired
  private EvlquestionBizService evlquestionBizService;
  @Autowired
  private EvlQuestionnairesService evlquestionService;

  @Resource
  private OrganizationService organizationService;
  @Resource
  private RoleTypeDao roleTypeDao;

  /**
   * 首页
   * 
   * @return
   */
  @RequestMapping("/index")
  public String index(@RequestParam(required = false) Integer phaseId,
      Model model) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    if (User.AREA_USER.equals(user.getUserType())) {
      return "forward:/jy/evl/pt/view";
    } else if (User.SCHOOL_USER.equals(user.getUserType())) {
      UserSpace us = (UserSpace) WebThreadLocalUtils
          .getSessionAttrbitue(SessionKey.CURRENT_SPACE);
      SysRole role = SysRole.getRoleById(us.getSysRoleId());
      if (role != null && Integer.valueOf(0).equals(us.getPhaseId())) {
        if (phaseId == null) {
          Organization org = organizationService.findOne(us.getOrgId());
          if (org != null) {
            String id = org.getPhaseTypes();
            String[] ids = id != null ? id.split(",") : new String[] {};
            List<MetaRelationship> mlist = new ArrayList<MetaRelationship>(
                ids.length);
            for (String pid : ids) {
              try {
                if (!StringUtils.isBlank(pid))
                  mlist.add(MetaUtils.getMetaRelation(Integer.parseInt(pid)));
              } catch (NumberFormatException e) {
                logger.error("add phase failed ", e);
              }
            }
            if (mlist.size() > 1) {
              model.addAttribute("phaseList", mlist);
              return "/uc/select";
            } else if (mlist.size() == 1) {
              us.setPhaseId(mlist.get(0).getId());
              us.setPhaseType(mlist.get(0).getEid());
            }
          }
        } else {
          UserSpace newUs = new UserSpace();
          try {
            BeanUtils.copyProperties(newUs, us);
            newUs.setPhaseId(phaseId);
            newUs.setPhaseType((MetaUtils.getMetaRelation(phaseId)).getEid());
            newUs.setFlags("true");
          } catch (IllegalAccessException e) {
            logger.error("", e);
          } catch (InvocationTargetException e) {
            logger.error("", e);
          }
          WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_SPACE,
              newUs);
        }
      }

      // 根据用户权限显示菜单列表：evl默认校长和主任有此权限
      Map<Integer, Object> userMap = evlquestionBizService
          .getEvlPowerUserList();
      boolean hasPower = false;
      if (userMap != null) {
        if (userMap.get(user.getId()) != null) {
          hasPower = true;
        }
      }
      model.addAttribute("hasPower", hasPower);
      return viewName("/index");
    } else {
      model.addAttribute("questions", evlquestionService.getQuestions());
      return viewName("/stuindex");
    }
  }
}
