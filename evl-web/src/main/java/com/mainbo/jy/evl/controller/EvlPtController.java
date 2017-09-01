package com.mainbo.jy.evl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainbo.jy.common.orm.SqlMapping;
import com.mainbo.jy.common.page.PageList;
import com.mainbo.jy.common.utils.WebThreadLocalUtils;
import com.mainbo.jy.common.web.controller.AbstractController;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.service.EvlQuestionnairesService;
import com.mainbo.jy.manage.meta.MetaUtils;
import com.mainbo.jy.manage.meta.bo.MetaRelationship;
import com.mainbo.jy.manage.org.bo.Organization;
import com.mainbo.jy.manage.org.service.OrganizationService;
import com.mainbo.jy.uc.bo.User;
import com.mainbo.jy.uc.bo.UserSpace;
import com.mainbo.jy.uc.service.UserSpaceService;
import com.mainbo.jy.uc.utils.SessionKey;

/**
 * 评教情况一览
 * 
 * <pre>
 *
 * </pre>
 *
 * @author wangyao
 * @version $Id: EvlPtController.java, v 1.0 2017年5月10日 上午10:49:27 wangyao Exp $
 */
@Controller
@RequestMapping("/jy/evl/pt")
public class EvlPtController extends AbstractController {

  @Autowired
  private EvlQuestionnairesService evlQuestionnairesService;
  @Autowired
  private OrganizationService organizationService;
  @Autowired
  private UserSpaceService userSpaceService;

  @RequestMapping("/view")
  public String viewQuestionRelation(Model m, Integer phaseId,
      EvlQuestionnaires quest) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    UserSpace us = (UserSpace) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    UserSpace space = new UserSpace();
    space.setUserId(user.getId());
    space.setEnable(1);
    space.setSchoolYear(quest.getSchoolYear());
    List<UserSpace> findAll = userSpaceService.findAll(space);
    Map<Integer, UserSpace> phaseIds = new HashMap<Integer, UserSpace>();
    for (UserSpace userSpace : findAll) {
      phaseIds.put(userSpace.getPhaseId(), userSpace);
    }
    List<MetaRelationship> phaseLists = new ArrayList<MetaRelationship>();
    List<MetaRelationship> phaseList = MetaUtils.getPhaseMetaProvider()
        .listAll();
    for (MetaRelationship metaRelationship : phaseList) {
      if (phaseIds.containsKey(0)) {// 教研主任，显示所有学段
        phaseLists.add(metaRelationship);
      } else {
        UserSpace userSpace = phaseIds.get(metaRelationship.getId());
        if (userSpace != null) {// 教研员，显示所对应的学段
          phaseLists.add(metaRelationship);
        }
      }
    }
    if (!CollectionUtils.isEmpty(phaseLists)) {
      MetaRelationship metaRelationship = phaseLists.get(0);
      if (phaseId == null && metaRelationship != null) {
        phaseId = metaRelationship.getId();
      }
    }
    if (us != null) {
      us.setPhaseId(phaseId);
    }
    Organization findOne = organizationService.findOne(user.getOrgId());
    if (findOne != null) {
      List<Organization> orgList = this.findOrgByAreaId(findOne.getAreaId(),
          phaseId);
      if (!CollectionUtils.isEmpty(orgList)) {
        List<Integer> orgIds = new ArrayList<Integer>();
        Map<Integer, Organization> nameMap = new HashMap<Integer, Organization>();
        for (Organization organization : orgList) {
          orgIds.add(organization.getId());
          nameMap.put(organization.getId(), organization);
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        EvlQuestionnaires model = new EvlQuestionnaires();
        model.setPhaseId(phaseId);
        StringBuilder sql = new StringBuilder();
        if (!CollectionUtils.isEmpty(orgIds)) {
          sql.append(" and orgId in (:orgIds)");
          paramMap.put("orgIds", orgIds);
        }
        model.addPage(quest.getPage());
        model.pageSize(15);
        model.addCustomCondition(sql.toString(), paramMap);
        model.addOrder("crtDttm desc");
        PageList<EvlQuestionnaires> findByPage = evlQuestionnairesService
            .findByPage(model);
        for (EvlQuestionnaires evlQuestionnaires : findByPage.getDatalist()) {
          Organization organization = nameMap.get(evlQuestionnaires.getOrgId());
          if (organization != null) {
            evlQuestionnaires.setFlago(organization.getName());
          }
        }
        m.addAttribute("dataPage", findByPage);
      }
    }
    m.addAttribute("phaseList", phaseLists);
    m.addAttribute("phaseId", phaseId);
    return viewName("/view");
  }

  private List<Organization> findOrgByAreaId(Integer areaId, Integer phaseId) {
    Organization org = new Organization();
    if (areaId != null) {
      org.setAreaIds(SqlMapping.LIKE_PRFIX + areaId + SqlMapping.LIKE_PRFIX);
    }
    if (phaseId != null) {
      org.setPhaseTypes(SqlMapping.LIKE_PRFIX + phaseId + SqlMapping.LIKE_PRFIX);
    }
    org.setEnable(Organization.ENABLE);
    org.setType(Organization.SCHOOL);
    org.addCustomCulomn("id,name,shortName,areaId");
    return organizationService.findAll(org);
  }
}
