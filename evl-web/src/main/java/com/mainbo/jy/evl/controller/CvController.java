/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.mainbo.jy.evl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mainbo.jy.common.web.controller.AbstractController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CvController.java, v 1.0 2017年11月23日 下午3:42:18 tmser Exp $
 */
@Controller
@RequestMapping("/o")
public class CvController extends AbstractController {

  public static class M {
    private String msg;

    /**
     * Getter method for property <tt>msg</tt>.
     *
     * @return msg String
     */
    public String getMsg() {
      return msg;
    }

    /**
     * Setter method for property <tt>msg</tt>.
     *
     * @param msg
     *          String value to be assigned to property msg
     */
    public void setMsg(String msg) {
      this.msg = msg;
    }

  }

  @RequestMapping("/str")
  @ResponseBody
  public String str(String msg) {
    return "string msg test" + msg;
  }

  @RequestMapping("/rep")
  public String rep() {
    return "rep";
  }

  @RequestMapping("/srep")
  @ResponseBody
  public String srep(@RequestBody String msg) {
    System.out.println("msg : " + msg);
    return "rep";
  }

}
