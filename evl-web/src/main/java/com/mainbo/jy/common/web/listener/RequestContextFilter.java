/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.common.web.listener;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mainbo.jy.common.utils.WebThreadLocalUtils;

/**
 * <pre>
 *  设置及清除全局request 线程变量
 * </pre>
 *
 * @author tmser
 * @version $Id: DefaultSessionListener.java, v 1.0 2015年2月12日 上午10:41:25 tmser
 *          Exp $
 */
public class RequestContextFilter extends OncePerRequestFilter {
  private final static Logger logger = LoggerFactory.getLogger(RequestContextFilter.class);

  /**
   * @param request
   * @param response
   * @param filterChain
   * @throws ServletException
   * @throws IOException
   * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      logger.info(request.getRequestURI());
      WebThreadLocalUtils.setRequest(request);
      filterChain.doFilter(request, response);
    } finally {
      WebThreadLocalUtils.clear();
    }

  }

}
