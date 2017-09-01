package com.mainbo.jy.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析HTML字符串工具类
 * @author yc
 * @version 2015-11-16
 */
public class ParseHtmlStrUtils {

	private static final String regEx_script = "<script[^>]*?>[＼＼s＼＼S]*?<＼＼/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[＼＼s＼＼S]*?<＼＼/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "＼＼s*|＼t|＼r|＼n";//定义空格回车换行符
      
    /**
     * @param htmlStr
     * @return
     *  删除HTML标签
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签
  
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签
  
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
  
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }
    /**
     * 清除空格标签 &nbsp 
     * @param htmlStr
     * @return
     */
    public static String getTextFromHtml(String htmlStr){
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        return htmlStr;
    }
    /**
     * 以endTag截取
     * @param htmlStr
     * @param endTag
     * @param i 
     * @return
     */
    public static String getTextFromHtml(String htmlStr,String endTag, int length){
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        if( htmlStr.indexOf(endTag)>length){
        	htmlStr = htmlStr.substring(0, htmlStr.indexOf(endTag)+1);
        }else{
        	htmlStr = htmlStr.substring(0, length>htmlStr.length()?htmlStr.length():length);
        }
        return htmlStr;
    }
    
}