package com.tmser.schevaluation.common.utils;

import net.sf.ehcache.Cache;

import org.springframework.cache.CacheManager;

import com.tmser.schevaluation.utils.SpringContextHolder;


/**
 * 清理缓存
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: CacheUtils.java, v 1.0 2017年5月4日 下午7:09:51 ljh Exp $
 */
public class CacheUtils {
	
	public static final String META_CACHE="META_CACHE";
	
	private static CacheManager cacheManager = null;
	
	private static synchronized CacheManager getCacheManager(){
		if(cacheManager==null){
			cacheManager =  SpringContextHolder.getBean(CacheManager.class);
		}
		return cacheManager;
	}
	public static void clear(String cacheName){
		org.springframework.cache.Cache cache = getCacheManager().getCache(cacheName);
		((Cache)cache.getNativeCache()).clearStatistics();
	    cache.clear();
	}
}
