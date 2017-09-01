/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.sms.impl;

import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mainbo.jy.evl.sms.SmsService;
import com.mainbo.jy.evl.sms.StatusCode;
import com.mainbo.jy.manage.config.ConfigUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author yc
 * @version $Id: SmsServiceImpl.java, v 1.0 2016年6月12日 上午10:35:24 yc Exp $
 */
@Service
@Transactional
public class SmsServiceImpl implements SmsService {
	
	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @param msg
	 * @param phoneNumber
	 * @return
	 * @see com.mainbo.jy.evl.sms.SmsService#sendSms(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Map<String, String> sendSms(String msg, String phoneNumber) {
		Map<String, String> resultMap=new HashMap<>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("account", ConfigUtils.readConfig("account",""));
		paramMap.put("password", ConfigUtils.readConfig("password","")); 
		paramMap.put("mobile", phoneNumber);
		paramMap.put("content", msg);
		try {
			Form form = Form.form();
			if (paramMap != null) {
				for (String key : paramMap.keySet()) {
					form.add(key, paramMap.get(key));
				}
			}
			int connectTimeout = 5000;
			int socketTimeout = 8000;
			String xmlString = Request.Post(Url)
					.bodyForm(form.build(), Charset.forName("utf-8"))
					.useExpectContinue().connectTimeout(connectTimeout)
					.socketTimeout(socketTimeout).execute().returnContent()
					.asString();

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
 			Document doc = builder.parse(is);
			Element rootElement = doc.getDocumentElement(); // 获取根节点
			NodeList childNodes = rootElement.getChildNodes(); // 根节点下所有子节点
			for (int i = 0; i < childNodes.getLength(); i++) { // 循环第一层子节点
				Node childNode = childNodes.item(i);
				NodeList childNodes_2 = childNode.getChildNodes();
				String key=childNode.getNodeName();
				for (int j = 0; j < childNodes_2.getLength(); j++) { // 循环第二层子节点
					Node childNode_2 = childNodes_2.item(j);
					resultMap.put(key, childNode_2.getNodeValue());
				}
			}

		} catch (Exception e) {
			logger.error("",e);
			resultMap.put("code",String.valueOf(StatusCode.code_0.getCode()));
			resultMap.put("msg","短信接口异常！");
			
		}
		return resultMap;
	}

	/**
	 * @param msg
	 * @param phoneNumbers
	 * @return
	 * @see com.mainbo.jy.evl.sms.SmsService#sendSms(java.lang.String,
	 *      java.lang.String[])
	 */
	@Override
	public List<Map<String, String>> sendSms(String msg, String... phoneNumbers) {
		List<Map<String, String>> resultMaps=new ArrayList<>();
		for(String phoneNumber:phoneNumbers){
			resultMaps.add(sendSms(msg, phoneNumber));
		}
		return resultMaps;
	}
	
	
}
