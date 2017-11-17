/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.sms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.evl.sms.SmsService;

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
    Map<String, String> resultMap = new HashMap<>();
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
    List<Map<String, String>> resultMaps = new ArrayList<>();
    for (String phoneNumber : phoneNumbers) {
      resultMaps.add(sendSms(msg, phoneNumber));
    }
    return resultMaps;
  }

}
