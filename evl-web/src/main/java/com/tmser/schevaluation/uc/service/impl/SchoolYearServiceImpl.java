/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tmser.schevaluation.uc.service.SchoolYearService;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolYearServiceImpl.java, v 1.0 2015年2月3日 下午5:32:38 tmser Exp
 *          $
 */
@Service
public class SchoolYearServiceImpl implements SchoolYearService {

  @Value("${schoolyear_split:07-15}")
  private String schoolYearSplit;

  public String getSchoolYearSplit() {
    return schoolYearSplit;
  }

  public void setSchoolYearSplit(String schoolYearSplit) {
    this.schoolYearSplit = schoolYearSplit;
  }

  /**
   * @return
   * @see com.tmser.schevaluation.uc.service.SchoolYearService#getCurrentSchoolYear()
   */
  @Override
  public Integer getCurrentSchoolYear() {
    int m = 6; // 默认月
    int d = 15;// 默认天
    try {
      String[] md = getSchoolYearSplit().trim().split("-");
      m = Integer.valueOf(md[0]) - 1;
      d = Integer.valueOf(md[1]);
    } catch (Exception e) {
      e.printStackTrace();
      // do nothing
    }
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    if (c.get(Calendar.MONTH) < m
        || (m == c.get(Calendar.MONTH) && c.get(Calendar.DAY_OF_MONTH) < d)) {
      year--;
    }
    return year;
  }

}
