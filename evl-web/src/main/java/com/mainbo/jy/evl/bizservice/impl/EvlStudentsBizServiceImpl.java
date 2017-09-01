package com.mainbo.jy.evl.bizservice.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.evl.bizservice.EvlStudentsBizService;
import com.mainbo.jy.evl.bo.ClassStudent;
import com.mainbo.jy.evl.bo.EvlStudentAccount;
import com.mainbo.jy.evl.service.ClassStudentService;
import com.mainbo.jy.evl.service.EvlMetaService;
import com.mainbo.jy.evl.service.EvlStudentAccountService;
import com.mainbo.jy.evl.utils.EvlHelper;
import com.mainbo.jy.evl.utils.ExcleUtils;
import com.mainbo.jy.schconfig.clss.bo.SchClass;
import com.mainbo.jy.schconfig.clss.service.SchClassService;
import com.mainbo.jy.uc.bo.User;
import com.mainbo.jy.uc.service.SchoolYearService;
@Service
@Transactional
public class EvlStudentsBizServiceImpl implements EvlStudentsBizService {
	
	@Autowired
	EvlStudentAccountService studentAccountService;
	@Autowired
	SchClassService schClassService;
	@Autowired
	EvlMetaService evlMetaService;
	@Autowired
	ClassStudentService classStudentService;
	@Autowired
	SchoolYearService schoolYearService;
	
	/**
	 * 导出用户
	 * 
	 * @param response
	 * @param orgId
	 * @see com.mainbo.jy.back.yhgl.service.BackUserManageService#exportSchoolUser(javax.servlet.http.HttpServletResponse,
	 *      java.lang.Integer)
	 */
	@Override
	public void exportStudentAccounts(HttpServletResponse response, User user) {
		int colNum = 5;
		// 文件
		Map<String, String> file = new HashMap<String, String>();
		file.put("fileName", "导出的学生账号");
		file.put("type", "1");// 1表示单列头，2表示双列头
		Map<String, List<String>> headMap = new LinkedHashMap<>();
		// sheet对象集合
		List<Map<String, ?>> sheetList = new ArrayList<Map<String, ?>>();
		// 数据结合
		EvlStudentAccount model = new EvlStudentAccount();
		//Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		model.setOrgId(user.getOrgId());
		List<EvlStudentAccount> njList = studentAccountService.findAll(model);
		Map<String, String> gradeMap=evlMetaService.getGradeMap(EvlHelper.getCurrentPhaseId());
		for (EvlStudentAccount njMv : njList) {
			SchClass classmodel = schClassService.findOne(njMv.getClassId());
			String gradename="未知年级";
			String classname="未知班级";
			if(classmodel!=null){
				classname = classmodel.getName();
				if(classmodel.getGradeId()!=null){
					gradename = gradeMap.get(classmodel.getGradeId().toString());
				}
			}
			Map<String, String> sheetBase = new HashMap<String, String>();
			sheetBase.put("sheetname", gradename + "(" + classname + ")");
			sheetBase.put("cellcount", colNum + "");
			List<Map<Integer, String>> dataList = new ArrayList<Map<Integer, String>>();// 数据集合
			Map<Integer, String> titleMap = new HashMap<Integer, String>();
			titleMap.put(1, "学号");
			titleMap.put(2, "姓名");
			titleMap.put(3, "班级");
			titleMap.put(4, "性别");
			titleMap.put(5, "家长电话");
//			titleMap.put(6, "评教地址");
			dataList.add(titleMap);
			// 数据列表
			EvlStudentAccount groupOne = new EvlStudentAccount();
			groupOne.setOrgId(user.getOrgId());
			ClassStudent classModel=new ClassStudent();
			classModel.setGradeId(njMv.getGradeId());
			classModel.setClassId(njMv.getClassId());
			List<EvlStudentAccount> groupOneList = studentAccountService.findAll(groupOne,classModel);
			for (int i = 0; i < groupOneList.size(); i++) {
				Map<Integer, String> dataMap = new HashMap<Integer, String>();
				EvlStudentAccount users = groupOneList.get(i);
				dataMap.put(1, users.getCode());
				dataMap.put(2, users.getName());
				dataMap.put(3, gradename + classname);
				if (user.getSex() == 0) {
					dataMap.put(4, "女");
				} else {
					dataMap.put(4, "男");
				}
				dataMap.put(5, users.getCellphone());
//				dataMap.put(6, "www.mainbo.com");
				dataList.add(dataMap);
			}
			Map<String, Object> sheetMap = new HashMap<String, Object>();
			sheetMap.put("sheetBase", sheetBase);
			sheetMap.put("dataList", dataList);
			sheetMap.put("headMap", headMap);
			sheetList.add(sheetMap);
		}
		ExcleUtils.exportSheetsExcel2(response, file, sheetList);
	}
	/**
	 * @param response
	 * @param model
	 * @see com.mainbo.jy.evl.service.EvlStudentAccountService#exportStudentAccounts(javax.servlet.http.HttpServletResponse,
	 *      com.mainbo.jy.evl.bo.EvlStudentAccount)
	 */
	@Override
	public void exportStudentAccounts(HttpServletResponse response,	EvlStudentAccount model,ClassStudent classModel) {
		// TODO 导出制定筛选条件的用户
		int colNum = 5;
		// 文件
		Map<String, String> file = new HashMap<String, String>();
		file.put("fileName", "导出的学生账号");
		file.put("type", "1");// 1表示单列头，2表示双列头
		Map<String, List<String>> headMap = new LinkedHashMap<>();
		// sheet对象集合
		List<Map<String, ?>> sheetList = new ArrayList<Map<String, ?>>();
		// 数据查询
		
		List<EvlStudentAccount> accountsList = studentAccountService.findAll(model,classModel);
		String gradename = "";
		String classname = "";
		if (classModel.getGradeId() != null) {
			Map<String, String> gradeMap=evlMetaService.getGradeMap(EvlHelper.getCurrentPhaseId());
			gradename = gradeMap.get(classModel.getGradeId().toString());
		}
		if (classModel.getClassId() != null) {
			SchClass classmodel = schClassService.findOne(classModel.getClassId());
			if (classmodel != null) {
				classname = classmodel.getName();
			}
		}

		Map<String, String> sheetBase = new HashMap<String, String>();
		sheetBase.put("sheetname", gradename + "(" + classname + ")");
		sheetBase.put("cellcount", colNum + "");
		List<Map<Integer, String>> dataList = new ArrayList<Map<Integer, String>>();// 数据集合
		Map<Integer, String> titleMap = new HashMap<Integer, String>();
		titleMap.put(1, "学号");
		titleMap.put(2, "姓名");
		titleMap.put(3, "班级");
		titleMap.put(4, "性别");
		titleMap.put(5, "家长电话");
		//titleMap.put(6, "评教地址");
		dataList.add(titleMap);
		for (EvlStudentAccount account : accountsList) {
			Map<Integer, String> dataMap = new HashMap<Integer, String>();
			dataMap.put(1, account.getCode());
			dataMap.put(2, account.getName());
			dataMap.put(3,classname);
			if (account.getSex() == 0) {
				dataMap.put(4, "女");
			} else {
				dataMap.put(4, "男");
			}
			dataMap.put(5, account.getCellphone());
			//dataMap.put(6, "www.mainbo.com");
			dataList.add(dataMap);
		}
		Map<String, Object> sheetMap = new HashMap<String, Object>();
		sheetMap.put("sheetBase", sheetBase);
		sheetMap.put("dataList", dataList);
		sheetMap.put("headMap", headMap);
		sheetList.add(sheetMap);
		ExcleUtils.exportSheetsExcel2(response, file, sheetList);
	}
	/**
	 * @param gradeId
	 * @param toGradeId
	 * @see com.mainbo.jy.evl.bizservice.EvlStudentsBizService#batchUpdateStudents(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public boolean batchUpdateStudents(Integer gradeId, Integer toGradeId,Integer orgId) {
		// TODO Auto-generated method stub
		try{
			
			List<SchClass> classes = getSchByGradeIdAndOrgId(gradeId, orgId, 1);
			List<SchClass> toClasses = getSchByGradeIdAndOrgId(toGradeId, orgId, 1);
			if(classes.size()==toClasses.size()){
				for(int i=0;i<classes.size();i++){
					batchUpdateStudents(classes.get(i).getId(), toClasses.get(i).getId(),gradeId,toGradeId);	
				}
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	/**
	 * 对班级学生进行升级
	 * @param schClass
	 * @param schClass2
	 */
	private void batchUpdateStudents(Integer classId, Integer toClassId,Integer gradeId, Integer toGradeId) {
		Integer userSchoolYear=EvlHelper.getUserSchoolYear();
		ClassStudent model=new ClassStudent();
		model.setClassId(classId);
		model.setGradeId(gradeId);
		model.setSchoolYear(userSchoolYear-1);
		List<ClassStudent> classStudents=classStudentService.findAll(model);
		for(ClassStudent classStudent:classStudents){
			classStudent.setClassId(toClassId);
			classStudent.setGradeId(toGradeId);
			classStudent.setSchoolYear(userSchoolYear);
			classStudent.setId(null);
			classStudentService.save(classStudent);
		}
	}
	@Override
	public List<SchClass> getSchByGradeIdAndOrgId(Integer gradeId, Integer orgId, Integer status) {
		SchClass sch = new SchClass();
		sch.setGradeId(gradeId);
		sch.setOrgId(orgId);
		sch.setSchoolYear(schoolYearService.getCurrentSchoolYear());
		if(status==0 || status==1){
			sch.setEnable(status);
		}
		sch.addOrder(" sort ASC ");
		List<SchClass> listAll = schClassService.findAll(sch);
		return listAll;
	}
}
