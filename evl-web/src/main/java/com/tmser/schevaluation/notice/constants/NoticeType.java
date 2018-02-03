package com.tmser.schevaluation.notice.constants;

/**
 * 
 * <pre>
 * 通知类型
 * </pre>
 *
 * @author wanzheng
 * @version $Id: NoticeType.java, v 1.0 Jun 1, 2015 1:58:11 PM wanzheng Exp $
 */
public enum NoticeType {
  PLAIN_SUMMARY(1, "plainSummary", "计划总结"), COMPANION(2, "companion", "同伴互助"), LECTURERECORDS(
      3, "lecturerecords", "听课记录"), LECTUREREPLY(31, "lecturereply", "听课记录"), LECTUREREPLYTOREPLY(
      32, "lecturereplytoreply", "听课记录"), ACTIVITY_ZL(4, "activity_zl", "集体备课"), ACTIVITY_FB(
      44, "activity_fb", "集体备课"), CHECK(100, "check", "查阅消息"), REVIEW(101,
      "review", "评论消息"), SCHOOL_ACTIVITY_YQ(5, "schoolActivity_yq", "校际教研"), SCHOOL_ACTIVITY_TZ(
      6, "schoolActivity_tz", "校际教研"), KPI(700, "kpi", "绩效考核"), KPI_SOLUTION_INIT(
      701, "kpi_solution_init", "录入方案"), KPI_REVIEW_INPUT_STATUS(702,
      "kpi_review_input_status", "填报情况"), KPI_REVIEW_STATUS(703,
      "kpi_review_status", "评审情况"), KPI_REVIEW_OPERACONFIRM(704,
      "kpi_review_operaconfirm", "结果认定"), KPI_RESULT_PUBLISH_STATUS(705,
      "kpi_result_publish_status", "结果公示"), EVL_JZPJ(706, "evl_jzpj", "家长评教"), EVL_XSPJ(
      707, "evl_xspj", "学生评教");
  // 通知标题前缀
  private String titlePrefix;
  // 通知类型值
  private Integer value;
  // 模板名称
  private String templateName;

  NoticeType(Integer value, String templateName, String titlePrefix) {
    this.value = value;
    this.templateName = templateName;
    this.titlePrefix = titlePrefix;
  }

  /**
   * 通过值获取类型
   * 
   * @return
   */
  public static NoticeType getByValue(Integer value) {
    NoticeType[] values = NoticeType.values();
    for (int i = 0; i < values.length; i++) {
      if (values[i].value.equals(value)) {
        return values[i];
      }
    }
    return null;
  }

  public String getTitlePrefix() {
    return titlePrefix;
  }

  public Integer getValue() {
    return value;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTitlePrefix(String titlePrefix) {
    this.titlePrefix = titlePrefix;
  }
}
