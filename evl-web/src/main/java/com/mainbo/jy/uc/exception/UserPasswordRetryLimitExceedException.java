/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.exception;

/**
 * <pre>
 * uc 模块基础异常
 * </pre>
 *
 * @author tmser
 * @version $Id: UcException.java, v 1.0 2015年1月23日 下午8:09:18 tmser Exp $
 */
@SuppressWarnings("serial")
public class UserPasswordRetryLimitExceedException extends UcException {
    public UserPasswordRetryLimitExceedException(int retryLimitCount) {
        super("user.password.retry.limit.exceed", new Object[]{retryLimitCount});
    }
}
