package com.tmser.schevaluation.evl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmser.schevaluation.evl.utils.freemarker.HtmlGenerator;
import com.tmser.schevaluation.evl.utils.freemarker.ResourceLoader;
import com.tmser.schevaluation.utils.Encodes;
import com.tmser.schevaluation.utils.StringUtils;
import com.tmser.schevaluation.utils.ZipUtils;

import freemarker.template.TemplateException;

public class FreemarkerUtils {

  private final static Logger logger = LoggerFactory
      .getLogger(FreemarkerUtils.class);

  /**
   * 模板存放路径
   */
  private static final String TEMPLATEPATH = "config/ftl/";
  /**
   * 图片编码对象
   */
  private static Map<String, String> imageBaseCode = new HashMap<String, String>();

  /**
   * 生成word下载文档
   * 
   * @param response
   *          返回下载文件流
   * @param template
   *          模板
   * @param root
   *          数据
   */
  public static void createWord(HttpServletResponse response, String template,
      Map<String, Object> root) {
    // 读取图片64编码文件
    String imageCodeKeys = root.get("imageCodeKeys").toString();

    String[] codeKeys = imageCodeKeys.split(",");
    for (int i = 0; i < codeKeys.length; i++) {
      String base64Code = imageBaseCode.get(codeKeys[i]);// 缓存读取图片编码文件

      imageBaseCode.remove(codeKeys[i]);

      root.put("base64Code_" + i, base64Code);
    }

    HtmlGenerator htmlGenerator = new HtmlGenerator();
    String outFile = "";
    try {
      outFile = htmlGenerator.generate(TEMPLATEPATH + template, root);
    } catch (IOException | TemplateException e) {
      logger.error("生成word文件编码异常", e);
    }

    loadResponseHead(response, root.get("title") + ".doc");// 加载下载头部信息

    OutputStream osWord = null;
    try {
      osWord = response.getOutputStream();
      osWord.write(outFile.getBytes("UTF-8"));
    } catch (IOException e) {
      logger.error("下载word文档流写入异常", e);
    } finally {
      try {
        osWord.flush();
        osWord.close();
      } catch (IOException e) {
        logger.error("下载word文档流关闭异常", e);
      }
    }
  }

  /**
   * 生成Excel下载文档
   * 
   * @param response
   *          返回下载文件流
   * @param template
   *          模板
   * @param root
   *          数据
   */
  public static void createExcel(HttpServletResponse response, String template,
      Map<String, Object> root) {
    String outFile = "";
    if (StringUtils.isNotEmpty(template)) {
      HtmlGenerator htmlGenerator = new HtmlGenerator();
      try {
        outFile = htmlGenerator.generate(TEMPLATEPATH + template, root);
      } catch (IOException | TemplateException e) {
        logger.error("生成Excel文件编码异常", e);
      }
    } else {
      logger.info("模板文件为空");
    }
    if (root != null && root.get("title") != null) {
      // 加载下载头部信息
      loadResponseHead(response, root.get("title") + ".xlsx");//

    } else {
      logger.info("下载文件标题为空");
    }
    OutputStream osWord = null;
    try {
      osWord = response.getOutputStream();
      osWord.write(outFile.getBytes("UTF-8"));
    } catch (IOException e) {
      logger.error("下载Excel文档流写入异常", e);
    } finally {
      try {
        osWord.flush();
        osWord.close();
      } catch (IOException e) {
        logger.error("下载Excel文档流关闭异常", e);
      }
    }
  }

  /**
   * 输出HTML文件
   * 
   * @param response
   *          返回文件下载流
   * @param template
   *          模板
   * @param root
   *          数据
   */
  public static void createHtml(HttpServletResponse response, String template,
      Map<String, Object> root) {
    // 通过一个文件输出流，就可以写到相应的文件中
    String outputFile = getOutFilePath(root.get("imagePath").toString(),
        root.get("title") + ".html");
    File zipFile = null;
    String targetName = null;
    try {
      HtmlGenerator htmlGenerator = new HtmlGenerator();
      String outFile = htmlGenerator.generate(TEMPLATEPATH + template, root);
      // 生成新文件
      File newfile = new File(outputFile);
      if (!newfile.getParentFile().exists()) {
        newfile.getParentFile().mkdir();
      }
      OutputStream osHtml = new FileOutputStream(outputFile);
      osHtml.write(outFile.getBytes("UTF-8"));
      osHtml.close();
      osHtml.flush();

      zipFile = newfile.getParentFile();
      targetName = zipFile.getParent() + "/" + zipFile.getName() + ".zip";
      // 文件夹压缩
      ZipUtils.compress(newfile.getParent(), targetName);
      // 装载下载文件流
      loadResponseHead(response, targetName);// 加载下载头部信息
      loadResponseStream(response, targetName);// 加载压缩下载文件
    } catch (IOException e) {
      logger.error("html文件写入异常", e);
    } catch (TemplateException e) {
      logger.error("获取html模板异常", e);
    } catch (Exception e) {
      logger.error("文件夹压缩异常", e);
    } finally {
      if (zipFile != null) {
        if (zipFile.isDirectory()) {
          File[] files = zipFile.listFiles();
          for (File file : files) {
            file.delete();
          }
        }
        zipFile.delete();// 删除原有文件夹
      }
    }

  }

  /**
   * 临时保存的文件路径
   * 
   * @param imagePath
   * @param fileName
   * @return
   */
  public static String getOutFilePath(String imagePath, String fileName) {
    String outputFile = "";
    if ("".equals(imagePath)) {
      outputFile = getTmpFilePath() + "/" + System.currentTimeMillis() + "/"
          + fileName;
    } else {
      outputFile = imagePath.substring(0, imagePath.lastIndexOf("/")) + "/"
          + fileName;
    }
    return outputFile;
  }

  /**
   * 临时保存文件夹路径
   * 
   * @return
   */
  public static String getTmpFilePath() {
    String outputFileClass = ResourceLoader.getPath("");
    return new File(outputFileClass).getParentFile().getParent()
        .replace("\\", "/")
        + "/tmp";
  }

  /**
   * 装载下载流头部信息
   * 
   * @param response
   * @param outFileName
   */
  public static void loadResponseHead(HttpServletResponse response,
      String outFileName) {
    response.reset();
    response.setContentType("application/x-msdownload");
    String fileName = outFileName.substring(outFileName.lastIndexOf("/") + 1);
    try {
      // 文件名编码转化
      response.setHeader("Content-Disposition", "attachment; filename="
          + new String(fileName.getBytes("gb2312"), "ISO-8859-1"));
    } catch (UnsupportedEncodingException e) {
      logger.error("文件名编码转化异常", e);
    }
  }

  /**
   * 加载下载文件流
   * 
   * @param response
   * @param outFileName
   */
  public static void loadResponseStream(HttpServletResponse response,
      String outFileName) {
    ServletOutputStream out = null;
    FileInputStream inputStream = null;
    File file = new File(outFileName);
    try {
      inputStream = new FileInputStream(file);
      out = response.getOutputStream();
      int b = 0;
      byte[] buffer = new byte[512];
      while (b != -1) {
        b = inputStream.read(buffer);
        out.write(buffer, 0, b == -1 ? 0 : b);
      }
    } catch (FileNotFoundException e) {
      logger.error("文件未找到异常", e);
    } catch (IOException e) {
      logger.error("文件读写异常", e);
    } finally {
      try {
        if (inputStream != null) {
          out.flush();
          out.close();
          inputStream.close();
        }
        file.delete();
      } catch (IOException e) {
        logger.error("关闭操作流异常", e);
      }
    }
  }

  /**
   * 图表图片保存路径
   * 
   * @param picBase64Info
   * @return
   */
  public static String getPicFilePath(String picBase64Info, String type) {
    picBase64Info = picBase64Info.replaceAll(" ", "+"); // "+" 变为了 " "
    if ("html".equals(type)) {
      return decodeBase64(picBase64Info, getTmpFilePath()); // 读取图片信息，返回图片保存路径
    } else if ("word".equals(type)) {
      String imageCodeKey = "";
      String[] arr = picBase64Info.split("base64,");
      if (arr.length > 0 && !"".equals(arr[1])) {
        imageCodeKey = UUID.randomUUID().toString();
        imageBaseCode.put(imageCodeKey, arr[1]);
      }
      return imageCodeKey;
    } else {
      return null;
    }

  }

  /**
   * 解析base64，返回图片所在路径
   * 
   * @param base64Info
   * @return
   */
  private static String decodeBase64(String base64Info, String filePath) {
    if (StringUtils.isEmpty(base64Info)) {
      return null;
    }
    String[] arr = base64Info.split("base64,");
    String picPath = filePath + "/" + System.currentTimeMillis() + "/"
        + UUID.randomUUID().toString() + ".png";
    File file = new File(picPath);
    if (!file.getParentFile().exists()) {
      file.getParentFile().mkdir();
    }
    try {
      byte[] buffer = Encodes.decodeBase64(arr[1]);
      OutputStream os = new FileOutputStream(picPath);
      os.write(buffer);
      os.close();
      os.flush();
    } catch (IOException e) {
      throw new RuntimeException();
    }

    return picPath;
  }
}
