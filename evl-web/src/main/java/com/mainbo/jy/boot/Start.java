/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.mainbo.jy.boot;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.mainbo.jy.common.web.listener.logback.LogbackConfigListener;

/**
 * <pre>
 * 零配置启动类
 * </pre>
 *
 * @author tmser
 * @version $Id: Start.java, v 1.0 2017年11月13日 下午5:37:00 tmser Exp $
 */
public class Start extends AbstractAnnotationConfigDispatcherServletInitializer {

  /**
   * @return
   * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses()
   */
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] { RootConfig.class };
  }

  /**
   * @return
   * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getServletConfigClasses()
   */
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] { WebConfig.class };
  }

  /**
   * @return
   * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer#getServletMappings()
   */
  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    servletContext.setInitParameter("logbackConfigLocation", "classpath:config/init/logback.xml");
    servletContext.setInitParameter("webAppRootKey", "evl.root");
    servletContext.addListener(new LogbackConfigListener());

    // FilterRegistration.Dynamic reqCf =
    // servletContext.addFilter("requestContextFilter", new
    // RequestContextFilter());
    // reqCf.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class),
    // false, "/*");
    // reqCf.setAsyncSupported(true);

    super.onStartup(servletContext);
  }

}
