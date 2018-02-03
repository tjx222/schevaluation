/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.monitor.jvm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.schevaluation.common.web.controller.AbstractController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: JvmMonitorController.java, v 1.0 2015年9月30日 下午4:48:07 tmser Exp $
 */
@Controller
@RequestMapping("/jy/ws/monitor/jvm")
public class JvmMonitorController extends AbstractController{
    @RequestMapping()
    public String index() {
        return "/monitor/jvm/index";
    }
}
