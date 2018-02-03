/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tmser.schevaluation.evl.utils.freemarker;

import java.net.URL;

/**
 * 资源加载类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: ResourceLoader.java, v 1.0 2016年6月3日 上午10:15:04 gengqianfeng
 *          Exp $
 */
public class ResourceLoader {

	/**
	 * classpath
	 */
	public static String CLASS_PATH_PREFIX = "classpath:";

	/**
	 * classpath内加载资源文件
	 * 
	 * @param resource
	 * @return
	 */
	public static URL getResource(String resource) {
		ClassLoader classLoader = null;// 类加载器
		classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader.getResource(resource);
	}

	/**
	 * classpath内搜索路径
	 * 
	 * @param resource
	 * @return
	 */
	public static String getPath(String resource) {
		if (resource != null) {
			if (resource.startsWith(CLASS_PATH_PREFIX)) {
				resource = getPath("")
						+ resource.replaceAll(CLASS_PATH_PREFIX, "");
			}
		}

		URL url = getResource(resource);
		if (url == null)
			return null;
		return url.getPath().replaceAll("%20", " ");
	}

	/**
	 * getPath
	 * 
	 * @param resource
	 * @param clazz
	 * @return
	 */
	public static String getPath(String resource, Class<?> clazz) {
		URL url = getResource(resource, clazz);
		if (url == null)
			return null;
		return url.getPath().replaceAll("%20", " ");
	}

	/**
	 * 指定class中获取资源
	 * 
	 * @param resource
	 * @param clazz
	 * @return
	 */
	public static URL getResource(String resource, Class<?> clazz) {
		return clazz.getResource(resource);
	}

	/**
	 * If running under JDK 1.2 load the specified class using the
	 * <code>Thread</code> <code>contextClassLoader</code> if that fails try
	 * Class.forname. Under JDK 1.1 only Class.forName is used.
	 * 
	 */
	public static Class<?> loadClass(String clazz)
			throws ClassNotFoundException {
		return Class.forName(clazz);
	}

}
