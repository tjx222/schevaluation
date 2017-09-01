package com.mainbo.jy.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * web request,session,gloab 存储工具类
 * 简单封装 spring线程容器方法
 * @author jxtan
 * @date 2014年11月6日
 */
public final class WebThreadLocalUtils {
	
	/**
	 * spring web attr threadlocal holder
	 * 
	 */
  private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();

	public static void setRequest(HttpServletRequest value)
	{
		request.set(value);
     }
	
   public static void clear()
   {
	    request.remove();
   }
	  /**
	 * 获取Request 属性
	 * @param name
	 * @return
	 */
	public static Object getAttrbitue(String name){
		return request.get().getAttribute(name);
	}
	
	  /**
	 * 获取param 属性
	 * @param name
	 * @return
	 */
	public static String getParameter(String name){
		return request.get().getParameter(name);
	}
	
	/**
	 * 设置Request 属性
	 * @param name
	 * @param value
	 */
	public static void setAttrbitue(String name,Object value){
		request.get().setAttribute(name,value);
	}
	
	
	/**
	 * 删除Request 属性
	 * @param name
	 */
	public static void removeAttrbitue(String name){
		request.get().removeAttribute(name);
	}
	
	
	/**
	 * 获取Session 属性
	 * @param name
	 * @return
	 */
	public static Object getSessionAttrbitue(String name){
		return request.get().getSession().getAttribute(name);
	}
	
	
	/**
	 * 设置Session 属性
	 * @param name
	 * @param value
	 */
	public static void setSessionAttrbitue(String name,Object value){
		request.get().getSession().setAttribute(name,value);
	}
	
	/**
	 * 删除Session 属性
	 * @param name
	 */
	public static void removeSessionAttrbitue(String name){
		request.get().getSession().removeAttribute(name);
	}
	
	/**
	 * 获取当前request
	 * @return
	 */
	public static final HttpServletRequest getRequest(){
		return request.get();
	}
	
}
