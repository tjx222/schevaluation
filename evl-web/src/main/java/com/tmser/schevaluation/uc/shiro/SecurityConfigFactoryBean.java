/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.shiro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SecurityConfigFactoryBean.java, v 1.0 2015年5月21日 上午11:01:45 tmser Exp $
 */
public class SecurityConfigFactoryBean implements FactoryBean<Section>, InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfigFactoryBean.class);

	private boolean singleton = true;

	private Section singletonInstance;
	
	private Resource location;
	
	private List<Resource> locations;

	@Override
	public final void afterPropertiesSet() throws IOException {
		if (this.singleton) {
			this.singletonInstance = createSection();
		}
	}

	@Override
	public final Section getObject() throws IOException {
		if (this.singleton) {
			return this.singletonInstance;
		}
		else {
			return createSection();
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Section.class;
	}

	@Override
	public final boolean isSingleton() {
		return this.singleton;
	}
	
	protected Section createSection() throws IOException{
		return loadSection();
	}
	
	public void setLocation(Resource location) {
		this.location = location;
		if(this.locations == null){
			this.locations = new ArrayList<Resource>();
			this.locations.add(location);
		}
	}
	
	/** 
	 * Getter method for property <tt>locations</tt>. 
	 * @return property value of locations 
	 */
	public List<Resource> getLocations() {
		return locations;
	}

	/**
	 * Setter method for property <tt>locations</tt>.
	 * @param locations value to be assigned to property locations
	 */
	public void setLocations(List<Resource> locations) {
		this.locations = locations;
	}

	/**
	 * Load properties into the given instance.
	 * @param props the Properties instance to load into
	 * @throws IOException in case of I/O errors
	 * @see #setLocations
	 */
	protected Section loadSection() throws IOException {
		if (this.locations != null) {
			Ini ini = new Ini();
			Section se = ini.addSection(Ini.DEFAULT_SECTION_NAME);
			for(Resource r : this.locations){
				if (logger.isInfoEnabled()) {
					logger.info("Loading ini file from {}", r);
				}
				Ini tmptIni = new Ini();
				try {
					if(r.exists()){
						tmptIni.load(r.getInputStream());
						se.putAll(tmptIni.getSection(Ini.DEFAULT_SECTION_NAME));
					}
						
				}catch (IOException ex) {
					logger.warn("Could not load properties from  {} : {}" ,location, ex.getMessage());
					throw ex;
				}
			}
			return se;
		}
		return null;
	}
}
