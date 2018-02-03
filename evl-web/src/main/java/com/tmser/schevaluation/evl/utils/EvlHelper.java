/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.utils;

import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.evl.bo.EvlSchoolYear;
import com.tmser.schevaluation.evl.service.EvlSchoolYearService;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.bo.UserSpace;
import com.tmser.schevaluation.uc.utils.SessionKey;
import com.tmser.schevaluation.utils.SpringContextHolder;

/**
 * <pre>
 * EVl 互助工具类
 * </pre>
 *
 * @author tmser
 * @version $Id: KpiHelper.java, v 1.0 2016年3月17日 下午6:05:42 tmser Exp $
 */
public class EvlHelper {

  public static final Integer PRIMARY_SCHOOLS = 140;// 小学

  public static Integer PHASEID = null;// 小学

  public static final Integer getCurrentPhaseId() {
    Integer phaseId = EvlHelper.PRIMARY_SCHOOLS;// 默认为1 小学
    UserSpace space = (UserSpace) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    if (space != null && space.getPhaseId() != null) {
      phaseId = space.getPhaseId();
    }
    return phaseId;
  }

  public static Integer getCurrentSchoolYear() {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    EvlSchoolYear model = new EvlSchoolYear();
    model.setOrgId(user.getOrgId());
    EvlSchoolYearService evlSchoolYearService = SpringContextHolder
        .getBean(EvlSchoolYearService.class);
    EvlSchoolYear evlSchoolYear = evlSchoolYearService.findOne(model);
    if (evlSchoolYear != null) {
      return evlSchoolYear.getSchoolYear();
    } else {
      return getUserSchoolYear();
    }
  }

  public static final Integer getUserSchoolYear() {
    Integer schoolYear = (Integer) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    return schoolYear;
  }
}
