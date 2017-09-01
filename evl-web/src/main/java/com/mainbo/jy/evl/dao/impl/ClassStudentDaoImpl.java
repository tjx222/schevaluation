/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.mainbo.jy.common.dao.AbstractDAO;
import com.mainbo.jy.evl.bo.ClassStudent;
import com.mainbo.jy.evl.dao.ClassStudentDao;
import com.mainbo.jy.utils.StringUtils;

/**
 * 学生表 Dao 实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: ClassStudent.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
@Repository
public class ClassStudentDaoImpl extends AbstractDAO<ClassStudent, Integer>
    implements ClassStudentDao {

  /**
   * @param classStudent
   * @see com.mainbo.jy.evl.dao.ClassStudentDao#delete(com.mainbo.jy.evl.bo.ClassStudent)
   */
  @Override
  public void delete(ClassStudent classModel) {
    StringBuilder sqlBuffer = new StringBuilder();
    String param = "";
    sqlBuffer.append("delete from ClassStudent where 1=1");
    // 班级条件拼凑
    if (classModel != null) {
      if (classModel.getStudentId() != null) {
        sqlBuffer.append(" and studentId =? ");
        param += classModel.getStudentId().toString() + "-";
      }
      if (StringUtils.isNotEmpty(classModel.getClassName())) {
        sqlBuffer.append(" and className =? ");
        param += classModel.getClassName() + "-";
      }
      if (classModel.getClassId() != null) {
        sqlBuffer.append(" and classId =? ");
        param += classModel.getClassId().toString() + "-";
      }
      if (classModel.getGradeId() != null) {
        sqlBuffer.append(" and gradeId =? ");
        param += classModel.getGradeId().toString() + "-";
      }
      if (classModel.getSchoolYear() != null) {
        sqlBuffer.append(" and schoolYear =? ");
        param += classModel.getSchoolYear().toString() + "-";
      }
      if (!CollectionUtils.isEmpty(classModel.getClassIds())) {
        sqlBuffer.append(" and classId in (?) ");
        param += classModel.getSchoolYear().toString() + "-";
      }
      if (!CollectionUtils.isEmpty(classModel.getGradeIds())) {
        sqlBuffer.append(" gradeId in (?) ");
        param += classModel.getGradeIds().toString() + "-";
      }
    }
    Object[] paramArr = null;
    if (StringUtils.isNotBlank(param)) {
      param = param.substring(0, param.length() - 1);
      paramArr = param.split("-");
    }
    update(sqlBuffer.toString(), paramArr);
  }
}
