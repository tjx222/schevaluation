package com.tmser.schevaluation.evl.utils;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @Desc：word操作工具类
 * @Author：ljh
 */
public class WordUtil {
	
/**
* @Desc：生成word文件
* @param dataMap word中需要展示的动态数据，用map集合来保存
* @param templateName word模板名称，例如：test.ftl
* @param filePath 文件生成的目标路径，例如：D:/wordFile/
* @param fileName 生成的文件名称，例如：test.doc
*/
public static void createWord(Map<?, ?> dataMap,String templateName,String filePath,String fileName,HttpServletResponse response){
        try {
	        //创建配置实例 
	        Configuration configuration = new Configuration();
        	//设置编码
            configuration.setDefaultEncoding("UTF-8");
            //ftl模板文件统一放至 com.mainbo.template 包下面
            configuration.setClassForTemplateLoading(WordUtil.class,"/com/mainbo/jy/evl/template/");
            //获取模板 
            Template template = configuration.getTemplate(templateName);
            //输出文件
            response.reset();
    		response.setContentType("application/x-msdownload");
    		response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes("gb2312"), "ISO-8859-1")+ ".doc");
    		ServletOutputStream out = response.getOutputStream();  
            //将模板和数据模型合并生成文件 
            Writer outWritter = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
            //生成文件
            template.process(dataMap, outWritter);
            //关闭流
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

 