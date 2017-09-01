package com.mainbo.jy.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

import com.mainbo.jy.utils.FileUtils;
import com.mainbo.jy.utils.StringUtils;

public class RequestUtils {
	  private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);

	  public static String getQueryParam(HttpServletRequest request, String name)
	  {
	    if (StringUtils.isEmpty(name)) {
	      return null;
	    }
	    if (request.getMethod().equalsIgnoreCase("POST")) {
	      return request.getParameter(name);
	    }
	    String s = request.getQueryString();
	    if (StringUtils.isEmpty(s)) {
	      return null;
	    }
	    try
	    {
	      s = URLDecoder.decode(s, "UTF-8");
	    }
	    catch (UnsupportedEncodingException e)
	    {
	      log.error("encoding UTF-8 not support?", e);
	    }
	    
	    String[] values = parseQueryString(s).get(name);
	    if ((values != null) && (values.length > 0)) {
	      return values[(values.length - 1)];
	    }
	    return null;
	  }
	  
	public static Map<String, Object> getQueryParams(HttpServletRequest request)
	  {
	    Map<String, String[]> map;
	    if (request.getMethod().equalsIgnoreCase("POST"))
	    {
	      map = request.getParameterMap();
	    }else
	    {
	      String s = request.getQueryString();
	      if (StringUtils.isEmpty(s)) {
	        return new HashMap<String, Object>();
	      }
	      
	      try
	      {
	        s = URLDecoder.decode(s, "UTF-8");
	      }
	      catch (UnsupportedEncodingException e)
	      {
	        log.error("encoding UTF-8 not support?", e);
	      }
	      map = parseQueryString(s);
	    }
	    
	    Map<String, Object> params = new HashMap<String, Object>(map.size());
	    for (Map.Entry<String, String[]> entry : map.entrySet())
	    {
	      int len = entry.getValue().length;
	      if (len == 1) {
	        params.put(entry.getKey(), entry.getValue()[0]);
	      } else if (len > 1) {
	        params.put(entry.getKey(), entry.getValue());
	      }
	    }
	    
	    return params;
	  }
	  
	  public static Map<String, String[]> parseQueryString(String s)
	  {
	    String[] valArray = null;
	    if (s == null) {
	      throw new IllegalArgumentException();
	    }
	    Map<String, String[]> ht = new HashMap<String, String[]>();
	    StringTokenizer st = new StringTokenizer(s, "&");
	    while (st.hasMoreTokens())
	    {
	      String pair = st.nextToken();
	      int pos = pair.indexOf('=');
	      if (pos != -1)
	      {
	        String key = pair.substring(0, pos);
	        String val = pair.substring(pos + 1, pair.length());
	        if (ht.containsKey(key))
	        {
	          String[] oldVals = ht.get(key);
	          valArray = new String[oldVals.length + 1];
	          for (int i = 0; i < oldVals.length; i++) {
	            valArray[i] = oldVals[i];
	          }
	          valArray[oldVals.length] = val;
	        }
	        else
	        {
	          valArray = new String[1];
	          valArray[0] = val;
	        }
	        ht.put(key, valArray);
	      }
	    }
	    return ht;
	  }
	  
	  public static Map<String, String> getRequestMap(HttpServletRequest request, String prefix)
	  {
	    return getRequestMap(request, prefix, false);
	  }
	  
	  public static Map<String, String> getRequestMapWithPrefix(HttpServletRequest request, String prefix)
	  {
	    return getRequestMap(request, prefix, true);
	  }
	  
	  private static Map<String, String> getRequestMap(HttpServletRequest request, String prefix, boolean nameWithPrefix)
	  {
	    Map<String, String> map = new HashMap<String, String>();
	    Enumeration<?> names = request.getParameterNames();
	    while (names.hasMoreElements())
	    {
	      String name = (String)names.nextElement();
	      if (name.startsWith(prefix))
	      {
	        String key = nameWithPrefix ? name : name.substring(prefix.length());
	        String value = org.apache.commons.lang3.StringUtils.join(request.getParameterValues(name), ',');
	        map.put(key, value);
	      }
	    }
	    return map;
	  }
	  
	  public static Map<String, Object> getRequestAttributeMap(HttpServletRequest request, String prefix, boolean nameWithPrefix)
	  {
	    Map<String, Object> map = new HashMap<String, Object>();
	    Enumeration<?> names = request.getAttributeNames();
	    while (names.hasMoreElements())
	    {
	      String name = (String)names.nextElement();
	      if (name.startsWith(prefix))
	      {
	        String key = nameWithPrefix ? name : name.substring(prefix.length());
	        map.put(key, request.getAttribute(name));
	      }
	    }
	    return map;
	  }
	  
	  public static String getIpAddr(HttpServletRequest request)
	  {
	    String ip = request.getHeader("X-Real-IP");
	    if ((!StringUtils.isEmpty(ip)) && (!"unknown".equalsIgnoreCase(ip))) {
	      return ip;
	    }
	    ip = request.getHeader("X-Forwarded-For");
	    if ((!StringUtils.isEmpty(ip)) && (!"unknown".equalsIgnoreCase(ip)))
	    {
	      int index = ip.indexOf(',');
	      if (index != -1) {
	        return ip.substring(0, index);
	      }
	      return ip;
	    }
	    return request.getRemoteAddr();
	  }
	  
	  public static String getLocation(HttpServletRequest request)
	  {
	    UrlPathHelper helper = new UrlPathHelper();
	    StringBuffer buff = request.getRequestURL();
	    String uri = request.getRequestURI();
	    String origUri = helper.getOriginatingRequestUri(request);
	    buff.replace(buff.length() - uri.length(), buff.length(), origUri);
	    String queryString = helper.getOriginatingQueryString(request);
	    if (queryString != null) {
	      buff.append("?").append(queryString);
	    }
	    return buff.toString();
	  }
	  
	  public static String getRequestedSessionId(HttpServletRequest request)
	  {
	    String sid = request.getRequestedSessionId();
	    String ctx = request.getContextPath();
	    if ((request.isRequestedSessionIdFromURL()) || (StringUtils.isBlank(ctx))) {
	      return sid;
	    }
	    return CookieUtils.getCookie(request, "JSESSIONID");
	  }
	  
	  public static void addError(HttpServletRequest request, String name, String message)
	  {
	    @SuppressWarnings("unchecked")
		Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
	    if (errors == null)
	    {
	      errors = new LinkedHashMap<String, String>();
	      request.setAttribute("errors", errors);
	    }
	    errors.put(name, message);
	  }
	  
	  public static String getLookupPath(HttpServletRequest request)
	    throws IllegalStateException
	  {
	    UrlPathHelper urlPathHelper = new UrlPathHelper();
	    String uri = urlPathHelper.getLookupPathForRequest(request);
	    
	    return uri;
	  }
	  
	  public static String getLookupPathNoExtension(HttpServletRequest request)
	    throws IllegalStateException
	  {
	    UrlPathHelper urlPathHelper = new UrlPathHelper();
	    String uri = urlPathHelper.getLookupPathForRequest(request);
	    
	    return FileUtils.getFileNameNoExtension(uri);
	  }
	  
	  public static String getLookupPathExtension(HttpServletRequest request)
	    throws IllegalStateException
	  {
	    UrlPathHelper urlPathHelper = new UrlPathHelper();
	    String uri = urlPathHelper.getLookupPathForRequest(request);
	    return FileUtils.getFileNameExtension(uri);
	  }
}