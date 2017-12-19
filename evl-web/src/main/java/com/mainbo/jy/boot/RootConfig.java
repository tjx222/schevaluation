/**
Mainbo.com Inc.
Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.mainbo.jy.boot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mainbo.jy.common.cache.ehcache.CompositeEhCacheManager;
import com.mainbo.jy.common.cache.ehcache.EhCacheManager;
import com.mainbo.jy.common.orm.MapperScannerConfig;
import com.mainbo.jy.manage.resources.service.ResViewService;
import com.mainbo.jy.manage.resources.service.impl.CompositeResViewServiceImpl;
import com.mainbo.jy.manage.resources.service.impl.ImageResViewServiceImpl;
import com.mainbo.jy.manage.resources.service.impl.VideoResViewServiceImpl;
import com.mainbo.jy.storage.service.StorageService;
import com.mainbo.jy.storage.service.impl.LocalStorageServiceImpl;
import com.mainbo.jy.uc.service.PasswordService;
import com.mainbo.jy.uc.service.impl.CompositePasswordService;
import com.mainbo.jy.uc.service.impl.NoSaltPasswordServiceImpl;
import com.mainbo.jy.uc.service.impl.PasswordServiceImpl;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: RootConfig.java, v 1.0 2017年11月13日 下午5:46:24 tmser Exp $
 */
@Configuration
@ComponentScan(basePackages = "com.mainbo", excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
@EnableCaching
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Import(DbConfig.class)
@ImportResource(locations = { "classpath:/config/spring/spring-nav-tch.xml",
    "classpath:/config/spring/spring-nav-evl.xml", "classpath:/config/spring/spring-shiro.xml" })
public class RootConfig extends CachingConfigurerSupport {

  @Value("${local_file_directory:/opt/evl/fileroot}")
  private String basePath;

  @Bean
  public static MapperScannerConfig mapperScannerConfig() {
    MapperScannerConfig msc = new MapperScannerConfig();
    msc.setBasePackage("com/mainbo/jy/**/bo");
    return msc;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource rs = new ReloadableResourceBundleMessageSource();
    rs.setBasenames("classpath:messages/messages");
    rs.setUseCodeAsDefaultMessage(false);
    rs.setDefaultEncoding("UTF-8");
    rs.setCacheSeconds(60);
    return rs;
  }

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

  @Bean
  public PasswordService passwordServiceImpl() {
    return new PasswordServiceImpl();
  }

  @Bean
  public PasswordService noSaltPasswordServiceImpl() {
    return new NoSaltPasswordServiceImpl();
  }

  @Bean
  @Primary
  public PasswordService passwordService() {
    CompositePasswordService ps = new CompositePasswordService();
    List<PasswordService> psList = new ArrayList<>();
    psList.add(noSaltPasswordServiceImpl());
    psList.add(passwordServiceImpl());
    ps.setPasswordServices(psList);
    ps.setEncodeServiceIndex(0);
    return ps;
  }

  @Bean
  public ResViewService viewService() {
    CompositeResViewServiceImpl crvs = new CompositeResViewServiceImpl();
    List<ResViewService> rvsList = new ArrayList<>();
    rvsList.add(new ImageResViewServiceImpl());
    rvsList.add(new VideoResViewServiceImpl());
    crvs.setViewServices(rvsList);
    return crvs;
  }

  @Bean("cacheManger")
  @Override
  public CacheManager cacheManager() {
    CompositeEhCacheManager cm = new CompositeEhCacheManager();

    List<CacheManager> managers = new ArrayList<>();
    EhCacheManager entityEhcm = new EhCacheManager();
    EhCacheManagerFactoryBean entityManager = new EhCacheManagerFactoryBean();
    entityManager.setConfigLocation(new ClassPathResource("/config/cache/ehcache-entity.xml"));
    entityManager.afterPropertiesSet();

    entityEhcm.setCacheManager(entityManager.getObject());
    entityEhcm.setTransactionAware(true);
    managers.add(entityEhcm);

    EhCacheManager busEhcm = new EhCacheManager();
    EhCacheManagerFactoryBean busManager = new EhCacheManagerFactoryBean();
    busManager.setConfigLocation(new ClassPathResource("/config/cache/ehcache-evl.xml"));
    busManager.afterPropertiesSet();
    busEhcm.setCacheManager(busManager.getObject());
    managers.add(busEhcm);

    cm.setCacheManagers(managers);

    cm.setFallbackToNoOpCache(true);
    return cm;
  }

  @Bean
  public StorageService storageService() {
    LocalStorageServiceImpl ss = new LocalStorageServiceImpl();
    ss.setBasePath(basePath);
    return ss;
  }

}
