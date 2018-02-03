/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.web.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

/**
 * <pre>
 *  解决tomcat 警告
 *  The web application [] appears to have started a thread named
 *   [Abandoned connection cleanup thread] com.mysql.jdbc.AbandonedConnectionCleanupThread
 * </pre>
 *
 * @author tmser
 * @version $Id: ContextFinalizer.java, v 1.0 2016年6月3日 下午4:45:56 tmser $
 */
public class MysqlContextFinalizer implements ServletContextListener {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MysqlContextFinalizer.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    Enumeration<Driver> drivers = DriverManager.getDrivers();
    Driver d = null;
    while (drivers.hasMoreElements()) {
      try {
        d = drivers.nextElement();
        DriverManager.deregisterDriver(d);
        LOGGER.warn(String.format("Driver %s deregistered", d));
      } catch (SQLException ex) {
        LOGGER.warn(String.format("Error deregistering driver %s", d), ex);
      }
    }
    AbandonedConnectionCleanupThread.checkedShutdown();
  }

}