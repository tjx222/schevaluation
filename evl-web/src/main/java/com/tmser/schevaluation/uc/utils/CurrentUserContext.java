package com.tmser.schevaluation.uc.utils;

import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.bo.UserSpace;

/**
 * 
 * <pre>
 * 当前用户消息消息上下文
 * </pre>
 *
 * @author wanzheng
 * @version $Id: CurrentContext.java, v 1.0 Jul 14, 2015 1:34:41 PM wanzheng Exp
 *          $
 */
public class CurrentUserContext {

  /**
   * 获取当前用户信息
   * 
   * @return
   */
  public static User getCurrentUser() {
    return (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
  }

  /**
   * 获取当前用户id
   * 
   * @return
   */
  public static Integer getCurrentUserId() {
    return getCurrentUser() == null ? -1 : getCurrentUser().getId();
  }

  /**
   * 获取当前用户空间信息
   * 
   * @return
   */
  public static UserSpace getCurrentSpace() {
    return (UserSpace) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SPACE);
  }

  /**
   * 获取当前用户空间id
   * 
   * @return
   */
  public static Integer getCurrentUserSpaceId() {
    return getCurrentSpace().getId();
  }

  /**
   * 获取当前用户空间id
   * 
   * @return
   */
  public static Integer getCurrentSchoolYear() {
    return (Integer) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
  }

}
