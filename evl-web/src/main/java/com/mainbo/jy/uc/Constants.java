package com.mainbo.jy.uc;

/**
 * 系统常量
 * @author jxtan
 * @date 2014年10月26日
 */
public abstract class Constants {
	
    /**
     * 操作名称
     */
	public static final String OP_NAME = "op";


    /**
     * 消息key
     */
    public static final String MESSAGE = "message";

    /**
     * 错误key
     */
    public static final String ERROR = "error";

    /**
     * 上个页面地址
     */
    public static final String BACK_URL = "BackURL";

    public static final String IGNORE_BACK_URL = "ignoreBackURL";

    /**
     * 当前请求的地址 带参数
     */
    public static final String CURRENT_URL = "currentURL";

    /**
     * 当前请求的地址 不带参数
     */
    public static final String NO_QUERYSTRING_CURRENT_URL = "noQueryStringCurrentURL";
    

    public static final String CONTEXT_PATH = "ctx";

	/**
	 * 默认登录用户session key 名称
	 */
	
	public static final String DEFAULT_LOGIN_NAME = "__LOGIN_NAME";
	
	/**
	 * 登录用户来源
	 */
	public static final String LOGIN_FROM_WHERE = "LOGIN_FROM_WHERE";
	
	/**
	 * 本地登录
	 */
	public static final String LOGIN_FROM_LOCAL = "local";
	
	/**
	 * 默认用户
	 */
	public static final String SYSUSER_NAME = "SYSUSER";
	
	
	public static final Integer LESSONPLAN_DEVICE_ID = 1; //备课资源存储设备id
	public static final String LESSONPLAN_DEVICE_NAME = "备课资源存储设备";  //备课资源存储设备名称
}