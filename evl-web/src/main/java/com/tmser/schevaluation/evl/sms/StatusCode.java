/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.sms;



/**
 * <pre>
 *	ihuyi.com 短信服务返回错误代码集合
 * </pre>
 *
 * @author yc
 * @version $Id: StatusCode.java, v 1.0 2016年6月12日 上午10:07:35 dell Exp $
 */
public enum StatusCode {
	code_0(0,"提交失败"),
	code_2(2,"提交成功"),
	code_400(400,"非法ip访问"),
	code_401(401,"帐号不能为空"),
	code_402(402,"密码不能为空"),
	code_403(403,"手机号码不能为空"),
	code_4030(4030,"手机号码已被列入黑名单"),
	code_404(404,"短信内容不能为空"),
	code_405(405,"用户名或密码不正确"),
	code_4050(4050,"账号被冻结"),
	code_4051(4051,"剩余条数不足"),
	code_4052(4052,"访问ip与备案ip不符"),
	code_406(406,"手机格式不正确"),
	code_407(407,"短信内容含有敏感字符"),
	code_4070(4070,"签名格式不正确"),
	code_4071(4071,"没有提交备案模板"),
	code_4072(4072,"短信内容与模板不匹配"),
	code_4073(4073,"短信内容超出长度限制"),
	code_408(408,"您的帐户疑被恶意利用，已被自动冻结，如有疑问请与客服联系。");

	
	private String msg;
    public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	private int code;

    private StatusCode(int code,String msg) {
        this.msg = msg;
        this.code = code;
    }
    public static String getMsg(int code) {
        for (StatusCode c : StatusCode.values()) {
            if (c.getCode() == code) {
                return c.getMsg();
            }
        }
        return null;
    }
}
