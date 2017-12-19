/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.mainbo.jy.boot;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.mainbo.jy.common.web.listener.RequestContextFilter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SpringSessionInitializer.java, v 1.0 2017年12月19日 下午2:34:05
 *          tmser Exp $
 */
public class SpringSessionInitializer extends AbstractHttpSessionApplicationInitializer {

  /**
   * @param servletContext
   * @see org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer#afterSessionRepositoryFilter(javax.servlet.ServletContext)
   */
  @Override
  protected void afterSessionRepositoryFilter(ServletContext servletContext) {
    registerFilter(servletContext, true, "requestContextFilter", new RequestContextFilter(), null);

    Map<String, String> encodingParams = new HashMap<String, String>();
    encodingParams.put("encoding", "UTF-8");
    encodingParams.put("forceEncoding", "false");
    registerFilter(servletContext, true, "encodingFilter", new CharacterEncodingFilter(), encodingParams);
  }

  private void registerFilter(ServletContext servletContext, boolean insertBeforeOtherFilters, String filterName,
      Filter filter, Map<String, String> params) {
    Dynamic registration = servletContext.addFilter(filterName, filter);
    if (registration == null) {
      throw new IllegalStateException("Duplicate Filter registration for '" + filterName
          + "'. Check to ensure the Filter is only configured once.");
    }
    registration.setAsyncSupported(isAsyncSessionSupported());
    EnumSet<DispatcherType> dispatcherTypes = getSessionDispatcherTypes();
    registration.addMappingForUrlPatterns(dispatcherTypes, !insertBeforeOtherFilters, "/*");
    if (params != null) {
      registration.setInitParameters(params);
    }
  }

}
