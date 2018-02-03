/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.web.json;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.tmser.schevaluation.common.bo.QueryObject;

/**
 * <pre>
 *  实体分页数据转 json 时，根据设置过滤掉单个实体的page 属性
 * </pre>
 *
 * @author tmser
 * @version $Id: PageJsonFilter.java, v 1.0 2015年4月18日 下午5:59:36 tmser Exp $
 */
public class PageJsonFilter implements PropertyPreFilter{

    private Class<?>    clazz;
    private  Set<String> includes = new HashSet<String>();
	private  Set<String> excludes = new HashSet<String>();

    
    /**
	 * Setter method for property <tt>includes</tt>.
	 * @param includes value to be assigned to property includes
	 */
	public void setIncludes(Set<String> includes) {
		this.includes = includes;
	}

	/**
	 * Setter method for property <tt>excludes</tt>.
	 * @param excludes value to be assigned to property excludes
	 */
	public void setExcludes(Set<String> excludes) {
		this.excludes = excludes;
	}

    /**
	 * Setter method for property <tt>clazz</tt>.
	 * @param clazz value to be assigned to property clazz
	 */
    public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
        return clazz;
    }

    public Set<String> getIncludes() {
        return includes;
    }

    public Set<String> getExcludes() {
        return excludes;
    }

    @Override
	public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (source == null) {
            return true;
        }
        
        if("page".equals(name) && QueryObject.class.isInstance(source)){
        	QueryObject qo = (QueryObject) source;
        	if(!qo.needParseToJson()){
        		return false;
        	}
        }
        
        if (clazz != null && !clazz.isInstance(source)) {
            return true;
        }

        if (this.excludes.contains(name)) {
            return false;
        }

        if (includes.size() == 0 || includes.contains(name)) {
            return true;
        }

        return false;
    }
}
