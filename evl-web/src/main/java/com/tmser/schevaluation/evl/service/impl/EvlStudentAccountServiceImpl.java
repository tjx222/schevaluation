/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.page.PageList;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.evl.bizservice.EvlStudentsBizService;
import com.tmser.schevaluation.evl.bo.ClassStudent;
import com.tmser.schevaluation.evl.bo.EvlStudentAccount;
import com.tmser.schevaluation.evl.dao.EvlStudentAccountDao;
import com.tmser.schevaluation.evl.service.ClassStudentService;
import com.tmser.schevaluation.evl.service.EvlMetaService;
import com.tmser.schevaluation.evl.service.EvlStudentAccountService;
import com.tmser.schevaluation.evl.utils.EvlHelper;
import com.tmser.schevaluation.evl.utils.ExcleUtils;
import com.tmser.schevaluation.schconfig.clss.bo.SchClass;
import com.tmser.schevaluation.schconfig.clss.service.SchClassService;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.service.SchoolYearService;
import com.tmser.schevaluation.uc.utils.SessionKey;
import com.tmser.schevaluation.utils.FileUtils;
import com.tmser.schevaluation.utils.Identities;
import com.tmser.schevaluation.utils.StringUtils;

/**
 * 学生账号表 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlStudentAccount.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
@Service
@Transactional
public class EvlStudentAccountServiceImpl
    extends AbstractService<EvlStudentAccount, String>
    implements EvlStudentAccountService {
  @Autowired
  private EvlStudentAccountDao evlStudentAccountDao;
  @Autowired
  private SchClassService schClassService;
  @Autowired
  private EvlMetaService evlMetaService;
  @Autowired
  private ClassStudentService classStudentService;
  @Autowired
  private SchoolYearService schoolYearService;
  @Autowired
  private EvlStudentsBizService studentsBizService;

  /**
   * @return
   * @see com.tmser.schevaluation.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<EvlStudentAccount, String> getDAO() {
    return evlStudentAccountDao;
  }

  /**
   * @param model
   * @param response
   * @throws IOException
   * @see com.tmser.schevaluation.evl.service.EvlStudentAccountService#getImportTemplateFile(com.tmser.schevaluation.evl.bo.EvlStudentAccount,
   *      javax.servlet.http.HttpServletResponse)
   */

  @Override
  public ResponseEntity<byte[]> getImportTemplateFile(String fileName)
      throws Exception {
    File f = new File(fileName);

    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    fileName = new String("批量导入学生用户模板.xls".getBytes("UTF-8"), "iso-8859-1");
    headers.setContentDispositionFormData("attachment", fileName);

    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(f), headers,
        HttpStatus.OK);
  }

  @Override
  public List<String> analyzeScoreFile(File file, Integer gradeId,
      Integer classId) throws Exception {
    List<String> tipList = new ArrayList<String>();
    if (!file.exists()) {
      logger.info("文件是空的！");
    } else {
      String fileName = file.getName();
      String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
      if ("xls".equals(prefix) || "xlsx".equals(prefix)) {
        Workbook workbook = null;
        FileInputStream fis = new FileInputStream(file);
        try {
          // Excel 97(-2007)
          workbook = new HSSFWorkbook(fis);
        } catch (Exception e) {
          try {
            fis = new FileInputStream(file);
            // Excel 2007 OOXML
            workbook = new XSSFWorkbook(fis);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        } finally {
          fis.close();
        }
        if (workbook != null) {
          int sheetCount = workbook.getNumberOfSheets();
          for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            List<String> errList = analyzeSheet(sheet, gradeId, classId);
            tipList.addAll(errList);
          }
        }
      } else {
        logger.info("文件的后缀名错误，后缀名必须是xls或者xlsx的文件！");
      }
    }
    return tipList;
  }

  @Override
  public List<String> analyzeScoreFile(File file, Integer gradeId)
      throws Exception {
    List<String> tipList = new ArrayList<String>();
    if (!file.exists()) {
      logger.info("文件是空的！");
    } else {
      String fileName = file.getName();
      String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
      if ("xls".equals(prefix) || "xlsx".equals(prefix)) {
        Workbook workbook = null;
        FileInputStream fis = new FileInputStream(file);
        try {
          // Excel 97(-2007)
          workbook = new HSSFWorkbook(fis);
        } catch (Exception e) {
          try {
            fis = new FileInputStream(file);
            // Excel 2007 OOXML
            workbook = new XSSFWorkbook(fis);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        } finally {
          fis.close();
        }
        if (workbook != null) {
          int sheetCount = workbook.getNumberOfSheets();
          for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            List<String> errList = analyzeSheet(sheet, gradeId);
            tipList.addAll(errList);
          }
        }
      } else {
        logger.info("文件的后缀名错误，后缀名必须是xls或者xlsx的文件！");
      }
    }
    return tipList;
  }

  /**
   * 分析sheet页
   * 
   * @param sheet
   * @throws Exception
   */
  public List<String> analyzeSheet(Sheet sheet, Integer gradeId,
      Integer classId) throws Exception {
    int lastRowNum = sheet.getLastRowNum();
    List<String> tipList = new ArrayList<String>();
    if (lastRowNum > 0) {
      // 从第二行开始读取
      for (int i = 2; i <= lastRowNum; i++) {
        Map<Integer, String> rememberMap = new HashMap<Integer, String>();
        Row row = sheet.getRow(i);
        if (row != null) {
          short lastCellNum = row.getLastCellNum();
          // 读取用户信息
          for (int j = 0; j < lastCellNum; j++) {
            if (row.getCell(j) != null) {// 空模板单元格跳过
              Cell cell = row.getCell(j);
              String value = ExcleUtils.getCellValue(cell);
              rememberMap.put(j, value);
            }
          }
        }
        List<String> errList = saveStudentAccount(rememberMap, gradeId, classId,
            i);
        if (!CollectionUtils.isEmpty(errList)) {
          tipList.addAll(errList);
        }
      }
    }
    return tipList;
  }

  /**
   * 分析sheet页
   * 导入年纪账号
   * 
   * @param sheet
   * @throws Exception
   */
  public List<String> analyzeSheet(Sheet sheet, Integer gradeId)
      throws Exception {
    int lastRowNum = sheet.getLastRowNum();
    List<String> tipList = new ArrayList<String>();
    if (lastRowNum > 0) {
      // 从第二行开始读取
      for (int i = 2; i <= lastRowNum; i++) {
        Map<Integer, String> rememberMap = new HashMap<Integer, String>();
        Row row = sheet.getRow(i);
        if (row != null) {
          short lastCellNum = row.getLastCellNum();
          // 读取用户信息
          for (int j = 0; j < lastCellNum; j++) {
            if (row.getCell(j) != null) {// 空模板单元格跳过
              Cell cell = row.getCell(j);
              String value = ExcleUtils.getCellValue(cell);
              rememberMap.put(j, value);
            }
          }
        }
        List<String> errList = saveStudentAccount(rememberMap, gradeId, i);
        if (!CollectionUtils.isEmpty(errList)) {
          tipList.addAll(errList);
        }
      }
    }
    return tipList;
  }

  /**
   * 保存解析分数
   * 
   * @param intemId
   * @param njMv
   * @param bjMv
   * @param xkMv
   * @param score
   */
  private List<String> saveStudentAccount(Map<Integer, String> map,
      Integer gradeId, Integer classId, Integer faultColumn) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    // {0=111, 1=思思, 2=111, 3=135110}
    EvlStudentAccount model = new EvlStudentAccount();
    List<String> tipList = new ArrayList<String>();
    if (map != null) {
      String code = map.get(0).toUpperCase();
      String name = map.get(1);
      String sex = map.get(2);
      if (StringUtils.isNotEmpty(sex) && sex.equals("男")) {
        sex = "1";
      } else {
        sex = "0";
      }
      String mobile = map.get(3);
      if (StringUtils.isEmpty(mobile) && StringUtils.isEmpty(name)
          && StringUtils.isEmpty(code)) {
      } else {
        if (StringUtils.isNotEmpty(code)) {// 判断重复数据
          EvlStudentAccount temp = this.isExistStudent(code, user.getOrgId());
          if (temp != null) {// 已存在，返回提醒
            tipList.add("第" + (faultColumn + 1) + "行学生学号重复，未能正常添加该学生！");
          } else if (code.length() > 15) {// 判断添加时的校验规则
            tipList.add("第" + (faultColumn + 1) + "行学生学号长度过长，未能正常添加该学生！");
          } else if (StringUtils.isEmpty(name)) {
            tipList.add("第" + (faultColumn + 1) + "行学生姓名为空，未能正常添加该学生！");
          } else if (name.length() > 15) {
            tipList.add("第" + (faultColumn + 1) + "行学生姓名长度过长，未能正常添加该学生！");
          } else if (StringUtils.isEmpty(mobile)) {
            tipList.add("第" + (faultColumn + 1) + "行学生手机号为空，未能正常添加该学生！");
          } else if (isPhoneNum(mobile) == false) {
            tipList.add("第" + (faultColumn + 1) + "行学生手机号格式不正确，未能正常添加该学生！");
          } else {
            model.setCode(code);
            model.setName(name);
            model.setSex(Integer.parseInt(sex));
            model.setCellphone(mobile);
            model.setOrgId(user.getOrgId());
            model = this.save(model);
            // 存储关联班级信息
            ClassStudent classStudent = new ClassStudent();
            classStudent.setClassId(classId);
            classStudent.setStudentId(model.getId());
            classStudent.setGradeId(gradeId);
            classStudent.setSchoolYear(EvlHelper.getCurrentSchoolYear());
            classStudent.setOrgId(user.getOrgId());
            classStudent.setCrtDttm(new Date());
            classStudentService.save(classStudent);
          }
        } else {
          tipList.add("第" + (faultColumn + 1) + "行学生学号为空，未能正常添加该学生！");
        }
      }
    }
    return tipList;
  }

  // 通过班级d获取name
  private String getClassNameById(Integer id) {
    SchClass class_ = schClassService.findOne(id);
    String name = "";
    if (class_ != null) {
      name = class_.getName();
    }
    return name;
  }

  /**
   * 导入学生或更新学生的班级关系
   * 
   * @param map
   * @param gradeId
   * @param faultColumn
   * @return
   */
  private List<String> saveStudentAccount(Map<Integer, String> map,
      Integer gradeId, Integer faultColumn) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    // {0=111, 1=思思, 2=111, 3=135110}
    EvlStudentAccount model = new EvlStudentAccount();
    List<String> tipList = new ArrayList<String>();
    if (map != null) {
      String code = map.get(0);
      String name = map.get(1);
      String className = map.get(2);
      if (!StringUtils.isEmpty(className)) {
        className = className.trim();
      }
      String sex = map.get(3);
      if (StringUtils.isNotEmpty(sex) && sex.equals("男")) {
        sex = "1";
      } else {
        sex = "0";
      }
      String mobile = map.get(4);
      if (StringUtils.isEmpty(mobile) && StringUtils.isEmpty(name)
          && StringUtils.isEmpty(code)) {
        tipList.add("第" + (faultColumn + 1) + "行学生信息缺失，未能正常添加该学生！");
      } else {
        if (StringUtils.isNotEmpty(code)) {// 判断重复数据
          code = code.toUpperCase();
          EvlStudentAccount temp = this.isExistStudent(code, user.getOrgId());
          if (temp != null) {// 已存账号
            // 存储关联班级信息
            ClassStudent classStudent = new ClassStudent();
            classStudent.setStudentId(temp.getId());
            classStudent.setSchoolYear(EvlHelper.getCurrentSchoolYear());
            classStudent.setOrgId(user.getOrgId());
            ClassStudent oldClassStudent = classStudentService
                .findOne(classStudent);
            if (oldClassStudent != null) {
              Map<String, String> gradeNamesMap = evlMetaService
                  .getGradeMap(EvlHelper.getCurrentPhaseId());
              String oldClassName = getClassNameById(
                  oldClassStudent.getClassId());
              tipList.add("第" + (faultColumn + 1) + "行学生学号重复，已存在于"
                  + gradeNamesMap.get(oldClassStudent.getGradeId() + "")
                  + oldClassName + "，未能正常添加该学生！");
            } else {
              Integer classId = getClassIdByName(gradeId, className,
                  user.getOrgId());
              if (classId != null) {
                classStudent.setClassId(classId);
                classStudent.setGradeId(gradeId);
                classStudentService.save(classStudent);
              } else {
                tipList
                    .add("第" + (faultColumn + 1) + "行学生未找到该年级对应班级，未能正常添加该学生！");
              }
            }
          } else if (code.length() > 15) {// 判断添加时的校验规则
            tipList.add("第" + (faultColumn + 1) + "行学生学号长度过长，未能正常添加该学生！");
          } else if (StringUtils.isEmpty(name)) {
            tipList.add("第" + (faultColumn + 1) + "行学生姓名为空，未能正常添加该学生！");
          } else if (name.length() > 15) {
            tipList.add("第" + (faultColumn + 1) + "行学生姓名长度过长，未能正常添加该学生！");
          } else if (StringUtils.isEmpty(mobile)) {
            tipList.add("第" + (faultColumn + 1) + "行学生手机号为空，未能正常添加该学生！");
          } else if (isPhoneNum(mobile) == false) {
            tipList.add("第" + (faultColumn + 1) + "行学生手机号格式不正确，未能正常添加该学生！");
          } else if (StringUtils.isEmpty(className)) {
            tipList.add("第" + (faultColumn + 1) + "行学生所属班级名称为空，未能正常添加该学生！");
          } else {
            Integer classId = getClassIdByName(gradeId, className,
                user.getOrgId());
            if (classId == null) {
              tipList.add("第" + (faultColumn + 1) + "行学生未找到该年级对应班级，未能正常添加该学生！");
            } else {
              model.setCode(code);
              model.setName(name);
              model.setSex(Integer.parseInt(sex));
              model.setCellphone(mobile);
              model.setOrgId(user.getOrgId());
              model = this.save(model);
              // 存储关联班级信息
              ClassStudent classStudent = new ClassStudent();
              classStudent.setClassId(classId);
              classStudent.setStudentId(model.getId());
              classStudent.setGradeId(gradeId);
              classStudent.setSchoolYear(EvlHelper.getCurrentSchoolYear());
              classStudent.setOrgId(user.getOrgId());
              classStudentService.save(classStudent);
            }
          }
        } else {
          tipList.add("第" + (faultColumn + 1) + "行学生学号为空，未能正常添加该学生！");
        }
      }
    }
    return tipList;
  }

  @Override
  public List<EvlStudentAccount> getAllAccountUserByOrgId(Integer orgId) {
    Integer schoolYear = (Integer) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    EvlStudentAccount model = new EvlStudentAccount();
    model.setOrgId(orgId);

    ClassStudent classModel = new ClassStudent();
    classModel.setOrgId(orgId);
    classModel.setSchoolYear(schoolYear);
    // 关联班级映射表进行查询
    List<EvlStudentAccount> accountList = this.findAll(model, classModel);
    return accountList;
  }

  @Override
  public List<SchClass> getSchByGradeIdAndOrgId(Integer gradeId, Integer orgId,
      Integer schoolYear) {
    return studentsBizService.getSchByGradeIdAndOrgId(gradeId, orgId, 1);
  }

  private Integer getClassIdByName(Integer gradeId, String name,
      Integer orgId) {
    SchClass classModel = new SchClass();
    classModel.setGradeId(gradeId);
    classModel.setName(name);
    classModel.setOrgId(orgId);
    classModel.setSchoolYear(schoolYearService.getCurrentSchoolYear());
    SchClass schClass = schClassService.findOne(classModel);
    if (schClass != null) {
      return schClass.getId();
    }
    return null;
  }

  public boolean isPhoneNum(String telePhoneNum) {
    String regExp = "^[1][0-9]{10}$";
    Pattern p = Pattern.compile(regExp);
    Matcher m = p.matcher(telePhoneNum);
    return m.find();
  }

  /**
   * 学生信息根据班级中间表进行查询，即多表查询（关联班级学生映射表）
   * 
   * @param model
   * @return
   * @see com.tmser.schevaluation.common.service.AbstractService#findAll(com.tmser.schevaluation.common.bo.QueryObject)
   */
  @Override
  public List<EvlStudentAccount> findAll(EvlStudentAccount model,
      ClassStudent classModel) {
    return evlStudentAccountDao.listAll(model, classModel);
  }

  /**
   * @param model
   * @param classModel
   * @return
   * @see com.tmser.schevaluation.evl.service.EvlStudentAccountService#findByPage(com.tmser.schevaluation.evl.bo.EvlStudentAccount,
   *      com.tmser.schevaluation.evl.bo.ClassStudent)
   */
  @Override
  public PageList<EvlStudentAccount> findByPage(EvlStudentAccount model,
      ClassStudent classModel) {
    return evlStudentAccountDao.listPage(model, classModel);
  }

  /**
   * @param account
   * @return
   * @see com.tmser.schevaluation.evl.service.EvlStudentAccountService#saveOrUpdate(com.tmser.schevaluation.evl.bo.EvlStudentAccount)
   */
  @Override
  public EvlStudentAccount save(EvlStudentAccount account) {
    if (StringUtils.isBlank(account.getId())) {
      account.setId(Identities.uuid2());
    }
    return evlStudentAccountDao.insert(account);
  }

  @Override
  public EvlStudentAccount isExistStudent(EvlStudentAccount studentAccount,
      Integer orgId) {
    EvlStudentAccount model = new EvlStudentAccount();
    model.setCode(studentAccount.getCode());
    model.setOrgId(orgId);
    if (studentAccount.getId() != null) {
      model
          .addCustomCondition(" and id<>" + "'" + studentAccount.getId() + "'");
    }
    EvlStudentAccount account = this.findOne(model);
    return account;
  }

  @Override
  public EvlStudentAccount isExistStudent(String code, Integer orgId) {
    EvlStudentAccount model = new EvlStudentAccount();
    model.setCode(code);
    model.setOrgId(orgId);
    EvlStudentAccount account = this.findOne(model);
    return account;
  }

  @Override
  public void batchSaveStudent(List<EvlStudentAccount> stus) {
    evlStudentAccountDao.batchInsert(stus);
  }
}
