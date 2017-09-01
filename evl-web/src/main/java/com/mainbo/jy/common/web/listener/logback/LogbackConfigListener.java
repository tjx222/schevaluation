/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.common.web.listener.logback;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: LogbackConfigListener.java, v 1.0 2015年2月28日 上午11:03:36 tmser Exp $
 */
public class LogbackConfigListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        WebLogbackConfigurer.shutdownLogging(event.getServletContext());
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        WebLogbackConfigurer.initLogging(event.getServletContext());
    }
}
