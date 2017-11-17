/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.mainbo.jy.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: RootConfig.java, v 1.0 2017年11月13日 下午5:46:24 tmser Exp $
 */
// @Configuration
// @ComponentScan(basePackages = "com.mainbo", excludeFilters = {
// @ComponentScan.Filter(type = FilterType.ANNOTATION, value =
// EnableWebMvc.class) })
public class RootConfig {

  @Bean
  public ThreadPoolTaskExecutor msgThreadPool() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setQueueCapacity(100);
    executor.setThreadNamePrefix("msg-");
    executor.setMaxPoolSize(25);
    executor.setKeepAliveSeconds(30);
    return executor;
  }

}
