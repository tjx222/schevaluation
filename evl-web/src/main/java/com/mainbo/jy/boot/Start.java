/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.mainbo.jy.boot;

/**
 * <pre>
 * 零配置启动类
 * </pre>
 *
 * @author tmser
 * @version $Id: Start.java, v 1.0 2017年11月13日 下午5:37:00 tmser Exp $
 */
public class Start {// extends
                    // AbstractAnnotationConfigDispatcherServletInitializer {

  /**
   * @return
   * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses()
   */
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] { RootConfig.class };
  }

  /**
   * @return
   * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getServletConfigClasses()
   */
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] { WebConfig.class };
  }

  /**
   * @return
   * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer#getServletMappings()
   */
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }

}
