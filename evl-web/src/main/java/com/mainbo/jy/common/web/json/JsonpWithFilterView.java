/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.common.web.json;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * <pre>
 * jsonp 视图解析器
 * </pre>
 *
 * @author tmser
 * @version $Id: FastJsonJsonWithFilterView.java, v 1.0 2015年4月18日 下午6:57:29
 *          tmser Exp $
 */
public class JsonpWithFilterView extends FastJsonJsonWithFilterView {

  public static final String DEFAULT_CONTENT_TYPE = "application/javascript";

  public JsonpWithFilterView() {
    setContentType(DEFAULT_CONTENT_TYPE);
    setExposePathVariables(false);
  }

  @Override
  protected void renderMergedOutputModel(Map<String, Object> model,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    Object value = filterModel(model);
    String text = JSON.toJSONString(value, getSerializeFilter(), getFeatures());
    byte[] bytes = text.getBytes(getCharset());

    OutputStream stream = getUpdateContentLength() ? createTemporaryOutputStream()
        : response.getOutputStream();

    if ("GET".equals(request.getMethod().toUpperCase())) {
      Map<String, String[]> params = request.getParameterMap();
      if (params.containsKey("callback")) {
        stream.write(new String(params.get("callback")[0] + "(").getBytes());
        stream.write(bytes);
        stream.write(new String(");").getBytes());
      } else {
        stream.write(bytes);
      }
    } else {
      stream.write(bytes);
    }

    if (getUpdateContentLength()) {
      writeToResponse(response, (ByteArrayOutputStream) stream);
    }
  }

}
