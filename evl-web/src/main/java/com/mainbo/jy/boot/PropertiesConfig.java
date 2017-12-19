/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.mainbo.jy.boot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: RootConfig.java, v 1.0 2017年11月13日 下午5:46:24 tmser Exp $
 */
@Configuration
public class PropertiesConfig {
  @Bean
  public static PathMatchingResourcePatternResolver resourcePatternResolver() {
    return new PathMatchingResourcePatternResolver();
  }

  @Bean
  public static Properties config() throws IOException {
    ResourcePatternResolver resourcePatternResolver = resourcePatternResolver();
    PropertiesFactoryBean pfb = new PropertiesFactoryBean();
    List<Resource> resources = new ArrayList<>();
    resources.addAll(Arrays.asList(resourcePatternResolver.getResources("classpath*:/config/init/*.properties")));
    resources
        .addAll(Arrays.asList(resourcePatternResolver.getResources("file:${confDir}/${webAppRootKey}/*.properties")));

    pfb.setLocations(resources.toArray(new Resource[resources.size()]));
    pfb.afterPropertiesSet();
    return pfb.getObject();
  }

  @Bean
  public static PropertyPlaceholderConfigurer configBean() throws IOException {
    PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
    propertyPlaceholderConfigurer.setProperties(config());
    return propertyPlaceholderConfigurer;
  }
}
