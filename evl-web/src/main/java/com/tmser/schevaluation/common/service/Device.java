/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.service;

/**
 * <pre>
 *  移动端判定接口
 * </pre>
 *
 * @author tmser
 * @version $Id: Device.java, v 1.0 2016年1月14日 下午5:46:19 tmser Exp $
 */
public interface Device {
	

	 boolean isNormal();

	/**
	 * True if this device is a mobile device such as an Apple iPhone or an Nexus One Android.
	 * Could be used by a pre-handle interceptor to redirect the user to a dedicated mobile web site.
	 * Could be used to apply a different page layout or stylesheet when the device is a mobile device.
	 */
	 boolean isMobile();

	/**
	 * True if this device is a tablet device such as an Apple iPad or a Motorola Xoom.
	 * Could be used by a pre-handle interceptor to redirect the user to a dedicated tablet web site.
	 * Could be used to apply a different page layout or stylesheet when the device is a tablet device.
	 */
	 boolean isTablet();
	
}
