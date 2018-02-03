/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.web.interceptor;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptorAdapter;

import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;

/**
 * <pre>
 *  异步请求拦截器，在异步请求处理前将request 放入WebThreadLocalUtils
 * </pre>
 *
 * @author tmser
 * @version $Id: AvoidDuplicateSubmissionInterceptor.java, v 1.0 2015年4月6日 下午9:25:50 tmser Exp $
 */
public class RequestHolderInterceptor extends DeferredResultProcessingInterceptorAdapter{
	
	/**
	 * 异步线程清除 thread 变量
	 * @param request
	 * @param deferredResult
	 * @throws Exception
	 * @see org.springframework.web.context.request.async.DeferredResultProcessingInterceptorAdapter#preProcess(org.springframework.web.context.request.NativeWebRequest, org.springframework.web.context.request.async.DeferredResult)
	 */
	@Override
	public <T> void preProcess(NativeWebRequest request,
			DeferredResult<T> deferredResult) throws Exception {
		if(WebThreadLocalUtils.getRequest() != null){
			WebThreadLocalUtils.clear();
		}
	}
				
}
