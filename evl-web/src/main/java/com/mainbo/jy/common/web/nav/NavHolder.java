/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.common.web.nav;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: NavsHolder.java, v 1.0 2015年2月14日 下午5:07:55 tmser Exp $
 */
public class NavHolder implements Serializable {
	private static final long serialVersionUID = 2625257676412095439L;
	
	public static final String NAVHOLDER_BEAN_NAME = "_NAVHOLDER_BEAN_NAME_";
	
	private Map<String,Nav> navsContainer = new HashMap<String,Nav>();
	
	
    public Map<String, Nav> getNavsContainer() {
		return navsContainer;
	}

	public void setNavsContainer(Map<String, Nav> navsContainer) {
		this.navsContainer = navsContainer;
	}

	public Nav getNav(String navid){
    	return navsContainer.get(navid);
    }
	
	public void setNav(Nav nav){
    	navsContainer.put(nav.getId(),nav);
    }
    
    public void setNavs(List<Nav> navs) {
    	for(Nav nav : navs)
    		navsContainer.put(nav.getId(),nav);
    }
    
    public  Map<String, Nav> getNavMap(){
    	return navsContainer;
    }
    
    public int size(){
    	return navsContainer != null ? navsContainer.size() : 0;
    }
}
