/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.monitor.ehcache.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mainbo.jy.common.vo.Result;
import com.mainbo.jy.common.web.controller.AbstractController;
import com.mainbo.jy.utils.DateUtils;
import com.mainbo.jy.utils.PrettyMemoryUtils;

/**
 * <pre>
 * 缓存监控
 * </pre>
 *
 * @author tmser
 * @version $Id: CacheManageController.java, v 1.0 2015年9月24日 上午9:59:54 tmser Exp $
 */
@Controller
@RequestMapping("/jy/ws/monitor/ehcache")
public class EhcacheMonitorController extends AbstractController {

	@Resource(name="cacheManger")
    private CacheManager cacheManager;

    @RequestMapping()
    public String index(Model model) {
        model.addAttribute("cacheManager", cacheManager);
        return "/monitor/ehcache/index";
    }

    @RequestMapping("/{cacheName}/details")
    public String details(
            @PathVariable("cacheName") String cacheName,
            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
            Model model) {

        model.addAttribute("cacheName", cacheName);
        Cache cache = (Cache)cacheManager.getCache(cacheName).getNativeCache();
        List<?> allKeys = cache.getKeys();
       
        List<String> showKeys = new ArrayList<String>();

        for(Object key : allKeys) {
            if(key.toString().contains(searchText)) {
                showKeys.add(key.toString());
            }
        }
        Map<String,Map<String,Object>> keydetails = new HashMap<String,Map<String,Object>>();
        for(String key : showKeys){
        	keydetails.put(key, keyDetail(cacheName,key));
        }
        model.addAttribute("keys", showKeys);
        model.addAttribute("keydetails", keydetails);
        return "/monitor/ehcache/details";
    }

    private Map<String, Object> keyDetail(String cacheName, String key) {
    	Cache cache = (Cache)cacheManager.getCache(cacheName).getNativeCache();
        Element element = cache.get(key);
        Map<String, Object> data = new HashMap<String,Object>();
        if(element != null){
        	String dataPattern = "yyyy-MM-dd HH:mm:ss";	
            
            data.put("size", PrettyMemoryUtils.prettyByteSize(element.getSerializedSize()));
            data.put("hitCount", element.getHitCount());

            Date latestOfCreationAndUpdateTime = new Date(element.getLatestOfCreationAndUpdateTime());
            data.put("latestOfCreationAndUpdateTime", DateUtils.formatDate(latestOfCreationAndUpdateTime, dataPattern));
            Date lastAccessTime = new Date(element.getLastAccessTime());
            data.put("lastAccessTime", DateUtils.formatDate(lastAccessTime, dataPattern));
            if(element.getExpirationTime() == Long.MAX_VALUE) {
                data.put("expirationTime", "不过期");
            } else {
                Date expirationTime = new Date(element.getExpirationTime());
                data.put("expirationTime", DateUtils.formatDate(expirationTime, dataPattern));
            }

            data.put("timeToIdle", element.getTimeToIdle());
            data.put("timeToLive", element.getTimeToLive());
            data.put("version", element.getVersion());
        }
        return data;
    }


    @RequestMapping("/{cacheName}/{key}/delete")
    @ResponseBody
    public Result delete(
            @PathVariable("cacheName") String cacheName,
            @PathVariable("key") String key
    ) {
    	Result rs = Result.newInstance();
    	org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);

        cache.evict(key);
        return rs;

    }

    @RequestMapping("/{cacheName}/clear")
    @ResponseBody
    public Result clear(
            @PathVariable("cacheName") String cacheName
    ) {
    	Result rs = Result.newInstance();
    	org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
    	((Cache)cache.getNativeCache()).clearStatistics();
        cache.clear();
        return rs;

    }
}