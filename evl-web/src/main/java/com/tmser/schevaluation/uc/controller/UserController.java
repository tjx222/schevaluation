package com.tmser.schevaluation.uc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.common.vo.Result;
import com.tmser.schevaluation.common.web.controller.AbstractController;
import com.tmser.schevaluation.manage.meta.Meta;
import com.tmser.schevaluation.manage.meta.MetaUtils;
import com.tmser.schevaluation.manage.meta.bo.MetaRelationship;
import com.tmser.schevaluation.manage.org.bo.Area;
import com.tmser.schevaluation.manage.org.bo.Organization;
import com.tmser.schevaluation.manage.org.service.AreaService;
import com.tmser.schevaluation.manage.org.service.OrganizationService;
import com.tmser.schevaluation.uc.bo.Role;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.service.RoleService;
import com.tmser.schevaluation.uc.service.UserService;
import com.tmser.schevaluation.uc.utils.SessionKey;
import com.tmser.schevaluation.utils.StringUtils;

@Controller
@RequestMapping("/jy/uc")
public class UserController extends AbstractController {
	private final static Logger logger = LoggerFactory
			.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private RoleService roleService;
	

	//保存用户信息，移动端使用
	@RequestMapping("/saveuserinfomobile")
	@ResponseBody
	public Result saveUserInfoMobile(User user) {
		Result result = new Result();
		User oldUser = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		user.setId(oldUser.getId());
		user.setLastupId(oldUser.getId());
		user.setLastupDttm(new Date());
		userService.update(user);
		oldUser = userService.findOne(user.getId());
		logger.info("用户【{}-{}】 信息修改成功！",oldUser.getName(),oldUser.getId());
		WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_USER,oldUser);
		result.setCode(1);
		result.setMsg("信息修改成功");
		return result;
	}
	
	// 验证用户电话
	@RequestMapping("/verifyUseCell")
	@ResponseBody
	public Object[] verifyUseCell(String fieldId,
			@RequestParam("fieldValue") String cellphoneTo) {
		Object[] result = new Object[2];
		result[0] = fieldId;
		result[1] = true;
		User oldUser = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		if (!cellphoneTo.equals(oldUser.getCellphone())) {
			if (!StringUtils.isEmpty(cellphoneTo)) {
				User user = new User();
				user.setCellphone(cellphoneTo);
				List<User> uList = userService.findAll(user);
				if (uList.size() > 0) {
					result[1] = false;
				}
			}
		}
		return result;
	}

	// 验证用户邮箱
	@RequestMapping("/verifyUserMail")
	@ResponseBody
	public Object[] verifyUserMail(String fieldId,
			@RequestParam("fieldValue") String mailTo) {
		Object[] result = new Object[2];
		result[0] = fieldId;
		result[1] = true;
		User oldUser = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		if (!mailTo.equals(oldUser.getMail())) {
			if (!StringUtils.isEmpty(mailTo)) {
				User user = new User();
				user.setMail(mailTo);
				List<User> uList = userService.findAll(user);
				if (uList.size() > 0) {
					result[1] = false;
				}
			}
		}
		return result;
	}
	
	
	
	// 上传头像
	@RequestMapping("/modifyUserPhoto")
	@ResponseBody
	public Result uploadUserPhoto(String photoPath,Integer type,Model m) {
		Result result=new Result();
		try {
			userService.modifyPhoto(photoPath);
			result.setCode(1);
			m.addAttribute("type", type);
		} catch (Exception e) {
			logger.error("上传失败");
			result.setCode(0);
		}
		return result;

	}
	
	/**
	 * 跳转到角色完善页
	 * @return
	 */
	@RequestMapping("/addrole")
	public String toAddRole(Model model){
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Integer orgId = user.getOrgId();
		if(orgId != null){
			//获取机构
			Organization org = organizationService.findOne(orgId);
			Integer areaId = org.getAreaId();
			//获取区域
			Area area = areaService.findOne(areaId);
			//获取角色列表
			List<Role> roleList = roleService.findRoleListByUseOrgId(orgId, 1 == org.getType() ? 1:2);
			
			model.addAttribute("org", org);
			model.addAttribute("area", area);
			model.addAttribute("roleList", roleList);
			if(org.getType().intValue() != Organization.SCHOOL){
				//如果不是学校则查询所有学科
				List<Meta> subjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectMeta();
				List<MetaRelationship> phaseList = MetaUtils.getPhaseMetaProvider().listAll();
				model.addAttribute("subjectList", subjectList);
				model.addAttribute("phaseList", phaseList);
			} else {
				List<MetaRelationship> phaseList = MetaUtils.getOrgTypeMetaProvider().listAllPhase(org.getSchoolings());
				model.addAttribute("phaseList", phaseList);
				//如果是学校则根据学段查询学科，年级，放入map，key对应学段元数据的id
				Map<Integer, List<Meta>> phaseGradeMap = MetaUtils.getOrgTypeMetaProvider().listPhaseGradeMap(org.getSchoolings());
				//学段Id对应学科map
				Map<Integer,List<Meta>> phaseSubjectMap = new HashMap<Integer, List<Meta>>();
				for (Integer phaseId : phaseGradeMap.keySet()) {
					List<Meta> subjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectByPhaseId(Integer.valueOf(phaseId),org.getId(),org.getAreaId(),MetaRelationship.ORG);
					phaseSubjectMap.put(phaseId, subjectList);
				}
				model.addAttribute("phaseGradeMap", JSONObject.toJSON(phaseGradeMap));
				model.addAttribute("phaseSubjectMap", JSONObject.toJSON(phaseSubjectMap));
			}
		}
		return viewName("addrole");
	}
}
