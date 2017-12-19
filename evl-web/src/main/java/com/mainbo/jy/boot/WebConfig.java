/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.mainbo.jy.boot;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.FieldRetrievingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mainbo.jy.common.web.json.FastJsonJsonWithFilterView;
import com.mainbo.jy.common.web.json.FastJsonWithFilterHttpMessageConverter;
import com.mainbo.jy.common.web.json.JsonpWithFilterView;
import com.mainbo.jy.common.web.json.PageJsonFilter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: WebConfig.java, v 1.0 2017年11月13日 下午5:46:34 tmser Exp $
 */
@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@ComponentScan(basePackages = "com.mainbo.jy.**.controller", useDefaultFilters = false, includeFilters = @Filter(Controller.class), lazyInit = true)
public class WebConfig extends WebMvcConfigurerAdapter {

  @Value("${mvc.asyncTimeout:20000}")
  private Long asyncTimeout;

  @Resource(name = "msgThreadPool")
  private ThreadPoolTaskExecutor msgThreadPool;

  @Bean
  public FieldRetrievingFactoryBean disableCircularReferenceDetect() {
    FieldRetrievingFactoryBean dcrf = new FieldRetrievingFactoryBean();
    dcrf.setStaticField("com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect");
    return dcrf;
  }

  /**
   * json 过滤器
   */
  @Bean
  public PageJsonFilter pageJsonFilter() {
    return new PageJsonFilter();
  }

  /**
   * {@inheritDoc}
   * <p>
   * This implementation is empty.
   */
  @Override
  public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    configurer.setDefaultTimeout(asyncTimeout);
    configurer.setTaskExecutor(msgThreadPool);
  }

  @Bean
  @Order(1)
  public InternalResourceViewResolver jspViewResolver() {
    InternalResourceViewResolver bean = new InternalResourceViewResolver();
    bean.setViewClass(org.springframework.web.servlet.view.JstlView.class);
    bean.setPrefix("/WEB-INF/views/");
    bean.setSuffix(".jsp");
    bean.setOrder(1);
    return bean;
  }

  @Bean
  @Order(0)
  public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
    ContentNegotiatingViewResolver bean = new ContentNegotiatingViewResolver();
    bean.setContentNegotiationManager(contentNegotiationManager());
    List<View> views = new ArrayList<View>();
    FastJsonJsonWithFilterView jsonView = new FastJsonJsonWithFilterView();
    jsonView.setFeatures(SerializerFeature.DisableCircularReferenceDetect);
    jsonView.setSerializeFilter(pageJsonFilter());
    views.add(jsonView);

    JsonpWithFilterView jsonpView = new JsonpWithFilterView();
    jsonpView.setFeatures(SerializerFeature.DisableCircularReferenceDetect);
    jsonpView.setSerializeFilter(pageJsonFilter());
    views.add(jsonpView);

    MarshallingView xmlView = new MarshallingView();
    XStreamMarshaller xsms = new XStreamMarshaller();
    xsms.setAutodetectAnnotations(true);
    xmlView.setMarshaller(xsms);
    xmlView.setContentType("application/xml");

    bean.setDefaultViews(views);
    bean.setOrder(0);
    return bean;
  }

  /**
   * 公共部分解析器
   * 
   * @return
   */
  @Bean(name = "multipartResolver")
  public CommonsMultipartResolver commonsMultipartResolver() {
    CommonsMultipartResolver common = new CommonsMultipartResolver();
    common.setMaxUploadSize(100 * 1024 * 1024);// 10M
    return common;
  }

  @Bean
  public ContentNegotiationManager contentNegotiationManager() {
    ContentNegotiationManagerFactoryBean configurer = new ContentNegotiationManagerFactoryBean();
    configurer.setFavorPathExtension(true);
    configurer.setFavorParameter(true);
    configurer.setIgnoreAcceptHeader(false);
    configurer.addMediaType("json", MediaType.valueOf("application/json"));
    configurer.addMediaType("jsonp", MediaType.valueOf("application/javascript"));
    configurer.addMediaType("xml", MediaType.valueOf("application/xml"));
    configurer.addMediaType("html", MediaType.valueOf("text/html"));
    configurer.setDefaultContentType(MediaType.valueOf("text/html"));
    return configurer.getObject();
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // Configure the list of HttpMessageConverters to use
    converters.add(new ByteArrayHttpMessageConverter());
    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    stringConverter.setWriteAcceptCharset(false);
    converters.add(stringConverter);

    FastJsonWithFilterHttpMessageConverter jsonConverter = new FastJsonWithFilterHttpMessageConverter();
    jsonConverter.setSerializeFilter(pageJsonFilter());
    jsonConverter.setFeatures(SerializerFeature.DisableCircularReferenceDetect);
    converters.add(jsonConverter);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/index").setViewName("index");
    registry.addViewController("/recorder").setViewName("recorder/index");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("/static/");
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  /**
   * Getter method for property <tt>asyncTimeout</tt>.
   *
   * @return asyncTimeout Long
   */
  public Long getAsyncTimeout() {
    return asyncTimeout;
  }

  /**
   * Setter method for property <tt>asyncTimeout</tt>.
   *
   * @param asyncTimeout
   *          Long value to be assigned to property asyncTimeout
   */
  public void setAsyncTimeout(Long asyncTimeout) {
    this.asyncTimeout = asyncTimeout;
  }

}
