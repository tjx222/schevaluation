/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.monitor.task.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mainbo.jy.common.vo.Result;
import com.mainbo.jy.common.web.controller.AbstractController;
import com.mainbo.jy.task.Task;
import com.mainbo.jy.utils.SpringContextHolder;
import com.mainbo.jy.utils.StringUtils;

/**
 * <pre>
 * 任务监控管理器
 * </pre>
 *
 * @author tmser
 * @version $Id: TaskManagerController.java, v 1.0 2016年9月7日 下午4:07:52 tmser Exp
 *          $
 */
@Controller
@RequestMapping("/jy/ws/monitor/task")
public class TaskManagerController extends AbstractController {

  /**
   * 定时器管理
   * 
   * @param m
   * @return
   */
  @RequestMapping("/index")
  public String index(Model m) {
    List<Task> tasks = SpringContextHolder.getBeansForType(Task.class);
    m.addAttribute("tasks", tasks);
    return ("monitor/task/index");
  }

  @RequestMapping("/excute/{code}/")
  @ResponseBody
  public Result excute(@PathVariable("code") String code) {
    Result jrs = new Result();
    jrs.setMsg("执行成功");
    List<Task> tasks = SpringContextHolder.getBeansForType(Task.class);
    try {
      if (StringUtils.isNotBlank(code)) {
        for (Task task : tasks) {
          if (code.equals(task.code())) {
            logger.debug("execute task code:{}", code);
            task.execute();
          }
        }
      }
    } catch (Exception e) {
      logger.error("execute task failed", e);
      jrs.setCode(Result.FAILED);
      jrs.setMsg("执行失败");
    }
    return jrs;
  }
}
