/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.exception;

/**
 * <pre>
 *  用户被禁用
 * </pre>
 *
 * @author tmser
 * @version $Id: UcException.java, v 1.0 2015年1月23日 下午8:09:18 tmser Exp $
 */
@SuppressWarnings("serial")
public class RequestNotExistsException extends UcException {
    public RequestNotExistsException(String reason) {
        super("user.request.not.exists", new Object[]{reason});
    }
}
