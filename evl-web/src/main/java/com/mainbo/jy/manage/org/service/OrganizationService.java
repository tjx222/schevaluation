/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.org.service;

import java.util.List;

import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.manage.org.bo.Organization;

/**
 * 机构 服务类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: Organization.java, v 1.0 2015-03-19 tmser Exp $
 */

public interface OrganizationService extends BaseService<Organization, Integer>{

	/**
	 * 通过学校名称检索学校
	 * @param schoolName
	 * @return
	 */
	List<Organization> findOrgByName(String schoolName);

	/**
	 * 通过id连城的字符串（如：,1,22,333,）获取机构集合
	 * @param orgsJoinIds
	 * @return
	 */
	List<Organization> getOrgListByIdsStr(String orgsJoinIds);
	
	/**
	 * 
	 * @return
	 */
	Organization createOrganization(Organization org);

	/**
	 * 通过学校名称和区域ids检索学校
	 * @param schoolName
	 * @param areaIds
	 * @return
	 */
	public List<Organization> findOrgByNameAndAreaIds(String schoolName,String areaIds);

	void updateOrgType(Organization model);
}
