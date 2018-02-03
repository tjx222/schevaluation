/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.schevaluation.common.page.PageList;
import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.common.vo.Result;
import com.tmser.schevaluation.common.web.controller.AbstractController;
import com.tmser.schevaluation.evl.bizservice.EvlStudentsBizService;
import com.tmser.schevaluation.evl.bo.ClassStudent;
import com.tmser.schevaluation.evl.bo.EvlSchoolYear;
import com.tmser.schevaluation.evl.bo.EvlStudentAccount;
import com.tmser.schevaluation.evl.service.ClassStudentService;
import com.tmser.schevaluation.evl.service.EvlMetaService;
import com.tmser.schevaluation.evl.service.EvlQuestionnairesService;
import com.tmser.schevaluation.evl.service.EvlSchoolYearService;
import com.tmser.schevaluation.evl.service.EvlStudentAccountService;
import com.tmser.schevaluation.evl.utils.EvlHelper;
import com.tmser.schevaluation.evl.vo.GradeAndClassVo;
import com.tmser.schevaluation.manage.org.bo.Organization;
import com.tmser.schevaluation.manage.org.service.OrganizationService;
import com.tmser.schevaluation.manage.resources.service.ResourcesService;
import com.tmser.schevaluation.schconfig.clss.bo.SchClass;
import com.tmser.schevaluation.schconfig.clss.service.SchClassService;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.utils.SessionKey;
import com.tmser.schevaluation.utils.StringUtils;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author yc
 * @version $Id: EvlOperateController1.java, v 1.0 2016年5月20日 上午11:25:39 dell
 *          Exp $
 */
@Controller
@RequestMapping("/jy/evl/manage")
public class EvlManageStudentsController extends AbstractController {
	@Autowired
	EvlQuestionnairesService questionnaireService;
	@Autowired
	private EvlMetaService evlMetaService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private EvlStudentAccountService studentAccountService;
	@Autowired
	private ClassStudentService classStudentService;
	@Autowired
	private SchClassService schClassService;
	@Autowired
	private EvlStudentsBizService studentsBizService;
	@Autowired
	private EvlSchoolYearService evlSchoolYearService;
	/**
	 * 学生管理首页
	 * 
	 * @return
	 */
	@RequestMapping("/students")
	public String manageStudents(Model m, String params, String studentId,
			Integer questionnaireId) {
		// 全部年级和班级
		Integer phaseId = EvlHelper.getCurrentPhaseId();// 学段ID
		User user = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Organization org = organizationService.findOne(user.getOrgId());
		List<GradeAndClassVo> gradeVos = new ArrayList<>();
		List<GradeAndClassVo> njList = new ArrayList<>();
		if(org!=null){
			njList=evlMetaService.findGradeByPhaseIdAndSystemId(phaseId, org.getSchoolings());
		}else{
			njList=evlMetaService.findGradeByPhaseIdAndSystemId(phaseId);
		}
		for (GradeAndClassVo gradeVo : njList) {
			List<SchClass> classList = studentsBizService.getSchByGradeIdAndOrgId(
					gradeVo.getId(), user.getOrgId(), 1);
			List<SchClass> appendNameClassName = studentsBizService
					.getSchByGradeIdAndOrgId(gradeVo.getId(), user.getOrgId(),
							1);
			for (SchClass schClass2 : appendNameClassName) {
				schClass2.setName(gradeVo.getName() + schClass2.getName());
			}
			gradeVo.setClasses(classList);
			gradeVos.add(gradeVo);
		}
		m.addAttribute("gradeVos", gradeVos);
		m.addAttribute("orgId", user.getOrgId());
		// 学生账号是否升级过
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		EvlSchoolYear model=new EvlSchoolYear();
		model.setOrgId(user.getOrgId());
		EvlSchoolYear evlSchoolYear=evlSchoolYearService.findOne(model);
		if(evlSchoolYear!=null){
			if(evlSchoolYear.getSchoolYear()<schoolYear){
				m.addAttribute("isStudentUpdate", false);
			}else{
				String failGrades=evlSchoolYear.getFailGrades();
				List<GradeAndClassVo> failGradeVos=new ArrayList<>();
				if(StringUtils.isNotEmpty(failGrades)){
					for(String gradeIdStr:failGrades.split(",")){
						for (GradeAndClassVo gradeVo : njList) {
							if(gradeVo.getId()==Integer.parseInt(gradeIdStr)){
								failGradeVos.add(gradeVo);
							}
						}
					}
				}
				m.addAttribute("failGradeVos",failGradeVos);
				m.addAttribute("evlSchoolYear",evlSchoolYear);
				m.addAttribute("isStudentUpdate", true);
			}
		}else{
			//未操作过
			m.addAttribute("isStudentUpdate", false);
		}
		m.addAttribute("schoolYear", evlSchoolYear);
		return viewName("/manageStudents");
	}
	
	@RequestMapping("/batchUpdateStudents")
	public Result batchUpdateStudents(Model m,@RequestParam("notUpdateGrades") String notUpdateGrades,@RequestParam("updateGrades") String[] updateGrades) {
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Result result=new Result();
		result.setCode(200);
		result.setMsg("恭喜您，升班成功！");
			//升级年级
			for(String gradeStrs:updateGrades){
				String[] twoGrades=gradeStrs.split("-");
				if(twoGrades.length==2){
					Integer gradeId=Integer.parseInt(twoGrades[0]);
					Integer toGradeId=Integer.parseInt(twoGrades[1]);
					boolean tag=studentsBizService.batchUpdateStudents(gradeId,toGradeId,user.getOrgId());
					if(!tag){
						//升级失败
						notUpdateGrades+=","+gradeId;
					};
				}
			}
		EvlSchoolYear model=new EvlSchoolYear();
		model.setOrgId(user.getOrgId());
		EvlSchoolYear evlSchoolYear=evlSchoolYearService.findOne(model);
		if(evlSchoolYear!=null){
			//更新学年
			Integer oldSchoolYear=evlSchoolYear.getSchoolYear();
			evlSchoolYear.setSchoolYear(oldSchoolYear+1);
			evlSchoolYear.setFailGrades(notUpdateGrades);
			evlSchoolYear.setLastupDttm(new Date());
			evlSchoolYear.setLastupId(user.getId());
			evlSchoolYearService.update(evlSchoolYear);
		}else{
			//第一次执行，记录学年初始信息
			evlSchoolYear=new EvlSchoolYear();
			evlSchoolYear.setOrgId(user.getOrgId());
			evlSchoolYear.setFailGrades(notUpdateGrades);
			evlSchoolYear.setCrtId(user.getId());
			evlSchoolYear.setCrtDttm(new Date());
			evlSchoolYear.setSchoolYear(EvlHelper.getCurrentSchoolYear());
			evlSchoolYearService.save(evlSchoolYear);
		}
		return result;
	}

	/**
	 * 根据学年加载班级
	 * 
	 * @param schoolYear
	 * @return
	 */
	@RequestMapping("/loadStudentsBySchoolYear")
	@ResponseBody
	public Result loadStudentsBySchoolYear(Integer schoolYear) {
		User user = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Result result = new Result();
		result.setCode(200);
		// 历史学年学生数据
		ClassStudent classModel=new ClassStudent();
		classModel.setSchoolYear(schoolYear);
		classModel.setOrgId(user.getOrgId());
		classModel.addGroup("classId");
		List<ClassStudent> classList = classStudentService.findAll(classModel);
		List<ClassStudent> returnList = new ArrayList<ClassStudent>();
		for (ClassStudent classInfo : classList) {
			Integer classId = classInfo.getClassId();
			SchClass classmodel = schClassService.findOne(classId);
			if (classmodel != null) {
				classInfo.setFlago(classmodel.getName());
				returnList.add(classInfo);
			}
		}
		result.setData(returnList);
		return result;
	}

	/**
	 * 根据学年班级加载数据
	 * 
	 * @param schoolYear
	 * @return
	 */
	@RequestMapping("/loadStudentsBySchoolYearAndClassId")
	@ResponseBody
	public Result loadStudentsBySchoolYearAndClassId(Integer schoolYear,
			Integer classId) {
		User user = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Result result = new Result();
		result.setCode(200);
		// 历史学年学生数据
		ClassStudent classInfo = new ClassStudent();
		classInfo.setOrgId(user.getOrgId());
		classInfo.setSchoolYear(schoolYear);
		classInfo.setClassId(classId);
		List<ClassStudent> classList = classStudentService.findAll(classInfo);
		result.setData(classList);
		return result;
	}
	

	/**
	 * 获取学生账号列表
	 * 
	 * @param m
	 * @param model
	 * @return
	 */

	@RequestMapping("/studentsPageList")
	public String getStudentsList(Model m, EvlStudentAccount model,ClassStudent classModel) {
		PageList<EvlStudentAccount> page = studentAccountService.findByPage(model,classModel);
		m.addAttribute("page", page);
		m.addAttribute("classModel", classModel);
		return viewName("/studentsPageList");
	}

	/**
	 * 新增或更新学生信息
	 * 
	 * @param m
	 * @param model
	 * @return
	 */

	@RequestMapping("/saveOrUpdate")
	public Result saveOrUpdate(Model m, String editId,EvlStudentAccount model,ClassStudent classModel) {
		//将学号转为大写
		if(editId!=null){
			model.setId(editId);
		}
		if(StringUtils.isNotEmpty(model.getCode())){
			model.setCode(model.getCode().toUpperCase().trim());
		}
		Result result = new Result();
		try {
			EvlStudentAccount temp = studentAccountService.isExistStudent(model,model.getOrgId());
			if (temp != null) {// 已存在，返回提醒
				result.setCode(500);
				result.setMsg("学生学号重复，未能正常添加该学生！");
			}else{
				result.setCode(200);
				if (model.getId() != null) {
					studentAccountService.update(model);
				}else{
					model=studentAccountService.save(model);
					classModel.setStudentId(model.getId());
					classModel.setSchoolYear(EvlHelper.getCurrentSchoolYear());
					classModel.setOrgId(model.getOrgId());
					classModel.setCrtDttm(new Date());
					classStudentService.save(classModel);
				}
			}
		} catch (Exception e) {
			logger.error("saveOrUpdate student failed!",e);
			result.setCode(500);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 删除用户{id:"1,2,3,"}
	 * 
	 * @param m
	 * @param id
	 * @return
	 */
	@RequestMapping("/delStudent")
	@ResponseBody
	public Result delStudent(Model m, String id) {
		Result result = new Result();
		try {
			result.setCode(200);
			if (StringUtils.isNotEmpty(id)) {
				String[] idArr = id.split(",");
				for (String studentId : idArr) {
					ClassStudent classStudent=new ClassStudent();
					classStudent.setStudentId(studentId);
					classStudent.setSchoolYear(EvlHelper.getCurrentSchoolYear());
					classStudentService.delete(classStudent);
					studentAccountService.delete(studentId);
				}
			}
		} catch (Exception e) {
			logger.error("delStudent student failed!",e);
			result.setCode(-1);
		}
		return result;
	}

	/**
	 * 查询单个学生信息
	 * 
	 * @param m
	 * @param model
	 * @return
	 */
	@RequestMapping("/getStudentInfo")
	public EvlStudentAccount getStudentInfo(Model m, EvlStudentAccount model) {
		return studentAccountService.findOne(model);
	}

	/**
	 * 下载批量导入学生账号模板
	 * 
	 * @param phaseId
	 * @param orgId
	 * @return
	 */
	@RequestMapping("/downLoadRegisterTemplate")
	@ResponseBody
	public ResponseEntity<byte[]> downloadWithPath(	@RequestParam(value = "fileName", required = false) String fileName,@RequestHeader("User-Agent") String userAgent) throws IOException {
		try {
			String root =ClassUtils.getDefaultClassLoader().getResource("").getPath();
			if(root != null  &&  !"".equals(root)){
				String basePath = new File(URLDecoder.decode(root,"utf-8")).getAbsolutePath();
				fileName=basePath+File.separator+"config"+File.separator+"ftl"+File.separator+fileName;
			}
			return studentAccountService.getImportTemplateFile(fileName);
		} catch (Exception e) {
			logger.error("下载批量注册模板出错", e);
		}
		return null;
	}

	/**
	 * 解析文件
	 */
	@RequestMapping(value = "/analyzeAndSaveFile", method = RequestMethod.POST)
	@ResponseBody
	public Result analyzeScoreFile(
			@RequestParam(value = "resid", required = false) String resid,
			Integer gradeId, Integer classId) throws IOException {
		Result result = new Result();
		result.setCode(200);
		if (resid != null) {
			try {
				List<String> tipList=new ArrayList<>();
				if(classId==null){
					tipList = this.studentAccountService.analyzeScoreFile(new File(resourcesService.download(resid)), gradeId);
				}else{
					tipList = this.studentAccountService.analyzeScoreFile(new File(resourcesService.download(resid)), gradeId, classId);
				}
				result.setData(tipList);
			} catch (Exception e) {
				e.printStackTrace();
				result.setCode(-1);
				result.setMsg("文件解析失败,请重新填写");
			}
		}
		return result;
	}

	/**
	 * 导出用户
	 */
	@RequestMapping("/exportStudentAccounts")
	public void exportStudentAccounts(HttpServletResponse response,	EvlStudentAccount model,ClassStudent classModel) {
		if (classModel.getSchoolYear() == null && classModel.getGradeId() == null
				&& classModel.getClassId() == null) {
			// 导出当前学年全部用户
			User user = (User) WebThreadLocalUtils
					.getSessionAttrbitue(SessionKey.CURRENT_USER);
			model.setOrgId(user.getOrgId());
			// 数据结合
			studentsBizService.exportStudentAccounts(response, user);
		} else {
			studentsBizService.exportStudentAccounts(response, model,classModel);
		}

	}
}
