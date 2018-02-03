/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.shiro;


import javax.annotation.Resource;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmser.schevaluation.uc.service.LoginService;

/**
 * <pre>
 *	自定义认证及权限收集器
 * </pre>
 *
 * @author tmser
 * @version $Id: UserRealm.java, v 1.0 2015年1月23日 下午6:23:54 tmser Exp $
 */
public abstract class AbstractUserRealm extends AuthorizingRealm {
    private static final String OR_OPERATOR = " or ";
    private static final String AND_OPERATOR = " and ";
    private static final String NOT_OPERATOR = "not ";
    private static final Logger log = LoggerFactory.getLogger(AbstractUserRealm.class);
    
    @Resource
    private LoginService loginService;
    
	/**
	 * @param principals
	 * @return
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Integer uid = (Integer) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(loginService.findStringRoles(uid));
        authorizationInfo.setStringPermissions(loginService.findStringPermissions(uid));

        return authorizationInfo;
	}

    /**
     * 支持or and not 关键词  不支持and or混用
     *
     * @param principals
     * @param permission
     * @return
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
    	if (permission == null || permission.trim().length() == 0) {
            throw new IllegalArgumentException("permission string cannot be null or empty. Make sure permission strings are properly formatted.");
        }
    	log.trace("valdate permission := {}",permission );
    	String p = permission.toLowerCase();
        if (p.contains(OR_OPERATOR)) {
            String[] permissions = permission.split(OR_OPERATOR);
            for (String orPermission : permissions) {
                if (isPermittedWithNotOperator(principals, orPermission)) {
                    return true;
                }
            }
            return false;
        } else if (p.contains(AND_OPERATOR)) {
            String[] permissions = permission.split(AND_OPERATOR);
            for (String orPermission : permissions) {
                if (!isPermittedWithNotOperator(principals, orPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            return isPermittedWithNotOperator(principals, permission);
        }
    }

    private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
        if (permission.toLowerCase().trim().startsWith(NOT_OPERATOR)) {
            return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
        } else {
            return super.isPermitted(principals, permission);
        }
    }


}
