package com.tmser.schevaluation.common.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author jxtan
 * @date 2014年11月6日
 */
public abstract class AbstractController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    protected MessageSource messageSource;
    
    private String viewPrefix;
    
    protected AbstractController() {
        setViewPrefix(defaultViewPrefix());
    }
    
    /**
     * 当前模块 视图的前缀
     * 默认
     * 1、获取当前类头上的@RequestMapping中的value作为前缀
     * 2、如果没有就使用当前模型小写的简单类名
     */
    public void setViewPrefix(String viewPrefix) {
        if (viewPrefix.startsWith("/jy")) {
            viewPrefix = viewPrefix.substring(3);
        }
        this.viewPrefix = viewPrefix;
    }

    public String getViewPrefix() {
        return viewPrefix;
    }

    /**
     * 获取视图名称：即prefixViewName + "/" + suffixName
     *
     * @return
     */
    public String viewName(String suffixName) {
        if (!suffixName.startsWith("/")) {
            suffixName = "/" + suffixName;
        }
        return getViewPrefix() + suffixName;
    }
    
	/**
	 * 获取默认国际化消息，默认为空
	 * @param key
	 * @return
	 */
	protected String getMessage(final String key)
	{
			return messageSource.getMessage(key, null,"",null);
	}
	
	
	
	/**
	 * 获取默认国际化消息，默认为空
	 * @param key
	 * @param args 参数列表
	 * @return
	 */
	protected String getMessage(final String key,final Object[] args)
	{
			return messageSource.getMessage(key, args,"",null);
	}
	
    protected String defaultViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }

        if (StringUtils.isEmpty(currentViewPrefix)) {
            currentViewPrefix = this.getClass().getSimpleName()
            		.replace("Controller", "").toLowerCase();
        }

        return currentViewPrefix;
    }
}
