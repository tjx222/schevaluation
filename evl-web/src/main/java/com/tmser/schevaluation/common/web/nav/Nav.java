package com.tmser.schevaluation.common.web.nav;

import java.io.Serializable;
import java.util.List;
/**
 * 导航条
 *
 * @author tjx
 * @version 2.0
 * 2014-1-7
 */
public class Nav implements Serializable{
	private static final long serialVersionUID = 1707013880934068493L;
	
	public static final String RA_NAVMAP = "ra_navmap";
	public static final String NAV_ROOT = "ra-navs";
	
	private List<NavElem> elems;
	private String extend;
	private String rollback;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<NavElem> getElems() {
		return elems;
	}
	public void setElems(List<NavElem> elems) {
		this.elems =  elems;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public String getRollback() {
		return rollback;
	}
	public void setRollback(String rollback) {
		this.rollback = rollback;
	}
	
	
}
