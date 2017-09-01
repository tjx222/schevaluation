/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mainbo.jy.evl.bo.EvlLevelWeight;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.bo.EvlSuggestionType;
import com.mainbo.jy.evl.vo.EvlIndicatorVo;
import com.mainbo.jy.evl.vo.SubjectVo;

/**
 * <pre>
 * Excle操作的工具
 * </pre>
 * 
 * @author zpp
 * @version $Id: ExcleUtils.java, v 1.0 2015年10月16日 下午5:15:40 zpp Exp $
 */
public abstract class ExcleUtils {

  private final static Logger logger = LoggerFactory
      .getLogger(ExcleUtils.class);

  private static final String CLASS_PATH_PREFIX = "classpath:";
  /**
   * 模板存放路径
   */
  private static final String TEMPLATEPATH = "config/ftl/";

  /**
   * 从Excle文件中读取信息并封装成List<Map>返回，其中list中每一个map代表一行数据，
   * map中的key代表列值（1,2,3,4...）,value存放对应列的值。
   * 
   * @param file
   */
  public static final List<Map<Integer, String>> findDatasFromFile(
      MultipartFile file) {
    List<Map<Integer, String>> returnlist = new ArrayList<Map<Integer, String>>();
    if (file.isEmpty()) {
      logger.info("文件是空的！");
    } else {
      String fileName = file.getOriginalFilename();
      String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
      if ("xls".equals(prefix) || "xlsx".equals(prefix)) {
        Workbook workbook = null;
        try {
          workbook = new HSSFWorkbook(file.getInputStream());
        } catch (Exception e) {
          logger.error("--文件解析出错--", e);
        }
        if (workbook != null) {
          Sheet sheet = workbook.getSheetAt(0);
          Row row = null;
          int m = 0;
          int lastRowNum = sheet.getLastRowNum();
          if (lastRowNum > 0) {
            for (int i = 0; i <= lastRowNum; i++) {
              row = sheet.getRow(i);
              if (row != null) {
                short lastCellNum = row.getLastCellNum();
                Map<Integer, String> map = new HashMap<Integer, String>();
                for (int j = 0; j < lastCellNum; j++) {
                  if (row.getCell(j) != null) {
                    Cell cell = row.getCell(j);
                    cell.setCellType(Cell.CELL_TYPE_STRING);// 字符串类型
                    String value = cell.getStringCellValue();
                    if (org.apache.commons.lang3.StringUtils.isEmpty(value)) {
                      m++;
                    } else {
                      value = value.trim();
                    }
                    map.put(j + 1, value);
                  } else {
                    map.put(j + 1, null);
                    m++;
                  }
                }
                if (m == lastCellNum) {// 这一行全为空
                  break;
                }
                returnlist.add(map);
                m = 0;
              }
            }
          }
        }
      } else {
        logger.info("文件的后缀名错误，后缀名必须是xls或者xlsx的文件！");
      }

    }
    return returnlist;
  }

  /**
   * 将数据写入Excle并导出下载，data参数要求比较特殊。说明如下：
   * 
   * @param response
   * @param map
   *          :初始化excle表格参数，key分别是{"filename":"文件名称","title":"第一行表格说明",
   *          "cellcount":"列的数量","sheetname":"sheet的名称"}
   * @param data
   *          ：数个数据集合，list中的每一个map作为一行数据，每列的key:value是{1: '',2:'',3:'',4:''...}
   */
  public static final void exportExcle(HttpServletResponse response,
      Map<String, String> map, List<Map<Integer, String>> data) {
    if (!CollectionUtils.isEmpty(map)) {
      String filename = map.get("filename");
      String cellcount = map.get("cellcount");
      String sheetname = map.get("sheetname");
      HSSFWorkbook wb = new HSSFWorkbook(); // --->创建了一个excel文件
      HSSFSheet sheet = wb.createSheet(sheetname); // --->创建了一个工作簿
      HSSFCellStyle style = creatStyle(wb, (short) 14, true);
      HSSFCellStyle style2 = creatStyle(wb, (short) 11, false);
      sheet.setDefaultColumnWidth(10);
      // 四个参数分别是：起始行，起始列，结束行，结束列
      int columns = Integer.parseInt(cellcount);
      sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns - 1));
      if (!CollectionUtils.isEmpty(data)) {
        for (int i = 0; i < data.size(); i++) {
          Map<Integer, String> mapt = data.get(i);
          if (i == 0) {
            HSSFRow rowTitle = sheet.createRow(i + 1);
            rowTitle.setHeightInPoints(28);
            for (int j = 0; j < columns; j++) {
              HSSFCell cellTitle = rowTitle.createCell(j);
              cellTitle.setCellStyle(style);
              cellTitle.setCellValue(mapt.get(j + 1));
            }
          } else {
            HSSFRow rowdata = sheet.createRow(i + 1);
            rowdata.setHeightInPoints(20);
            for (int j = 0; j < columns; j++) {
              HSSFCell celldata = rowdata.createCell(j);
              celldata.setCellStyle(style2);
              celldata.setCellValue(mapt.get(j + 1));
            }
          }
        }
      }
      OutputStream out;
      try {
        out = response.getOutputStream();
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename="
            + new String(filename.getBytes("gb2312"), "ISO-8859-1") + ".xls");
        wb.write(out);
        wb.close();
      } catch (IOException e) {
        logger.error("--Excle导出出错--", e);
      }
    }
  }

  /**
   * @param wb
   *          :工作簿
   * @param fontsize
   *          :字体大小
   * @param isbold
   *          :是否加粗
   * @return
   */
  private static HSSFCellStyle creatStyle(HSSFWorkbook wb, short fontsize,
      boolean isbold) {
    HSSFCellStyle style = wb.createCellStyle(); // 样式对象
    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
    style.setWrapText(true);// 能否换行
    style.setBorderTop((short) 1);
    style.setBorderRight((short) 1);
    style.setBorderBottom((short) 1);
    style.setBorderLeft((short) 1);
    // 设置标题字体格式
    Font font = wb.createFont();
    // 设置字体样式
    font.setFontHeightInPoints(fontsize); // --->设置字体大小
    font.setFontName("宋体"); // ---》设置字体，是什么类型例如：宋体
    font.setBold(isbold); // --->设置是否是加粗
    style.setFont(font); // --->将字体格式加入到style1中
    return style;
  }

  /**
   * 多个sheet文件数据下载
   * 
   * @param response
   * @param file
   *          fileName-生成文件名称，type-1、单行列头，2、双行列头
   * @param sheetList
   *          sheet集合-包含sheetBase（基本信息集）:title（标题）、sheetname（sheet名称）、
   *          cellcount（列数）,headMap（列头集）,dataList（数据集）
   */
  @SuppressWarnings("unchecked")
  public static final void exportSheetsExcel(HttpServletResponse response,
      Map<String, String> file, List<Map<String, ?>> sheetList) {
    String fileName = file.get("fileName");
    if (!"".equals(fileName)) {
      HSSFWorkbook wb = new HSSFWorkbook(); // --->创建了一个excel文件
      for (Map<String, ?> sheetMap : sheetList) {
        Map<String, String> sheetBase = (Map<String, String>) sheetMap
            .get("sheetBase");
        HSSFSheet sheet = wb.createSheet(sheetBase.get("sheetname")); // --->创建了一个工作簿
        HSSFCellStyle style2 = creatStyle(wb, (short) 9, false);
        HSSFCellStyle style = creatStyle(wb, (short) 9, true);
        sheet.setDefaultColumnWidth(10);// 默认8
        HSSFRow row0 = sheet.createRow(0); // --->创建一行
        // 四个参数分别是：起始行，起始列，结束行，结束列
        int columns = Integer.parseInt(sheetBase.get("cellcount"));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns - 1));
        row0.setHeightInPoints(20);
        HSSFCell cell0 = row0.createCell(0); // --->创建一个单元格
        cell0.setCellStyle(style);
        cell0.setCellValue(sheetBase.get("title"));
        // 设置表头(两行)
        HSSFRow rowTitle1 = sheet.createRow(1);
        rowTitle1.setHeightInPoints(20);
        rowTitle1.setRowStyle(style);
        HSSFRow rowTitle2 = sheet.createRow(2);
        rowTitle2.setHeightInPoints(20);
        int type = Integer.parseInt(file.get("type"));
        switch (type) {
        case 1:// 单行列头
          Map<String, String> headMap1 = (Map<String, String>) sheetMap
              .get("headMap");
          for (int i = 0; i < headMap1.size(); i++) {
            String value = headMap1.get(i + "");
            if (!"".equals(value)) {
              CellRangeAddress cra = new CellRangeAddress(1, 1, i, i);
              sheet.addMergedRegion(cra);
              createSheetCell(rowTitle1, style, i, value);
            }
          }
          break;
        case 2:// 双行列头
          int index = 0;
          int colNum = 0;
          Map<String, List<String>> headMap2 = (Map<String, List<String>>) sheetMap
              .get("headMap");
          for (String key : headMap2.keySet()) {
            List<String> headStrs = headMap2.get(key);
            if (CollectionUtils.isEmpty(headStrs)) {
              CellRangeAddress cra = new CellRangeAddress(1, 2, index, index);
              sheet.addMergedRegion(cra);
              createSheetCell(rowTitle1, style, index, key);
            } else {
              colNum = index + headStrs.size() - 1;
              CellRangeAddress cra = new CellRangeAddress(1, 1, index, colNum);
              // 在sheet里增加合并单元格
              sheet.addMergedRegion(cra);
              HSSFCell cellTitle1 = rowTitle1.createCell(index);
              for (int i = 0; i < headStrs.size(); i++) {
                rowTitle1.createCell(index + i).setCellStyle(style);
                createSheetCell(rowTitle2, style, index + i, headStrs.get(i));
              }
              cellTitle1.setCellStyle(style);
              cellTitle1.setCellValue(key);
            }
            index = colNum + 1;
          }
          break;
        default:
          logger.error("--未知导出Excle列头类型--");
          break;
        }
        // 填充表格数据
        loadDataList(sheet, style2, type + 1, columns,
            (List<Map<Integer, String>>) sheetMap.get("dataList"));
      }
      responseOutFile(response, wb, fileName);// 文件下载
    }
  }

  /**
   * 多个sheet文件数据下载
   * 
   * @param response
   * @param file
   *          fileName-生成文件名称，type-1、单行列头，2、双行列头
   * @param sheetList
   *          sheet集合-包含sheetBase（基本信息集）:title（标题）、sheetname（sheet名称）、
   *          cellcount（列数）,headMap（列头集）,dataList（数据集）
   */
  @SuppressWarnings("unchecked")
  public static final void exportSheetsExcel2(HttpServletResponse response,
      Map<String, String> file, List<Map<String, ?>> sheetList) {
    String fileName = file.get("fileName");
    if (!"".equals(fileName)) {
      HSSFWorkbook wb = new HSSFWorkbook(); // --->创建了一个excel文件
      for (Map<String, ?> sheetMap : sheetList) {
        Map<String, String> sheetBase = (Map<String, String>) sheetMap
            .get("sheetBase");
        HSSFSheet sheet = wb.createSheet(sheetBase.get("sheetname")); // --->创建了一个工作簿
        HSSFCellStyle style = creatStyle(wb, (short) 11, false);
        HSSFCellStyle style2 = creatStyle(wb, (short) 11, true);// 第一行加粗
        sheet.setDefaultColumnWidth(20);// 默认8
        int columns = Integer.parseInt(sheetBase.get("cellcount"));
        sheet.setColumnWidth(columns - 1, 20000);
        // 填充表格数据
        List<Map<Integer, String>> temp = (List<Map<Integer, String>>) sheetMap
            .get("dataList");
        Map<Integer, String> firstRow = temp.get(0);
        List<Map<Integer, String>> firstRowlist = new ArrayList<Map<Integer, String>>();
        firstRowlist.add(firstRow);// 处理第一行
        List<Map<Integer, String>> otherList = (List<Map<Integer, String>>) sheetMap
            .get("dataList");
        otherList.remove(0);
        loadDataList(sheet, style2, 0, columns, firstRowlist);
        loadDataList(sheet, style, 1, columns, otherList);
      }
      responseOutFile(response, wb, fileName);// 文件下载
    }
  }

  /**
   * 生成单元格
   * 
   * @param rowTitle1
   * @param style
   * @param index
   * @param value
   */
  private static void createSheetCell(HSSFRow rowTitle, HSSFCellStyle style,
      int index, String value) {
    HSSFCell cellTitle = rowTitle.createCell(index);
    cellTitle.setCellStyle(style);
    cellTitle.setCellValue(value);
  }

  /**
   * 生成单元格(无样式)
   * 
   * @param row
   * @param index
   * @param value
   */
  private static void createSheetCell(HSSFRow row, int index, String value) {
    HSSFCell cell = row.createCell(index);
    cell.setCellValue(value);
  }

  /**
   * 装载数据
   * 
   * @param sheet
   * @param style
   * @param startRow
   * @param columns
   * @param dataList
   */
  private static void loadDataList(HSSFSheet sheet, HSSFCellStyle style,
      int startRow, int columns, List<Map<Integer, String>> dataList) {
    if (!CollectionUtils.isEmpty(dataList)) {
      for (int i = 0; i < dataList.size(); i++) {
        Map<Integer, String> mapt = dataList.get(i);
        HSSFRow rowdata = sheet.createRow(i + startRow);
        rowdata.setHeightInPoints(20);
        for (int j = 0; j < columns; j++) {
          HSSFCell celldata = rowdata.createCell(j);
          celldata.setCellStyle(style);
          celldata.setCellValue(mapt.get(j + 1));
        }
      }
    }
  }

  /**
   * 文件下载
   * 
   * @param response
   * @param wb
   * @param fileName
   */
  private static void responseOutFile(HttpServletResponse response,
      HSSFWorkbook wb, String fileName) {
    OutputStream out;
    try {
      out = response.getOutputStream();
      response.reset();
      response.setContentType("application/x-msdownload");
      response.setHeader("Content-Disposition", "attachment; filename="
          + new String(fileName.getBytes("utf-8"), "ISO-8859-1") + ".xls");
      wb.write(out);
      wb.close();
    } catch (IOException e) {
      logger.error("--Excle导出出错--", e);
    }
  }

  /**
   * 下载问卷模板
   * 
   * @param response
   * @param root
   */
  public static void download_questionnaires(HttpServletResponse response,
      Map<String, Object> root) {
    String fileName = getPath("") + TEMPLATEPATH
        + root.get("template").toString();
    File templateFile = new File(fileName);
    if (templateFile.exists()) {
      InputStream inStream = null;
      HSSFWorkbook workbook = null;
      try {
        inStream = new FileInputStream(templateFile);
        workbook = new HSSFWorkbook(inStream);// 获取模板工作簿
        switch (Integer.parseInt(root.get("type").toString())) {
        case 0:
          download_indicator_all(workbook, root);
          break;
        case 1:
          download_indicator(workbook, root, true);
          break;
        case 2:
          download_indicator(workbook, root, false);
          break;
        default:
          break;
        }
        responseOutFile(response, workbook, root.get("title").toString());
      } catch (IOException e) {
        logger.error("excel文件模板操作异常", e);
      } finally {
        if (workbook != null) {
          try {
            workbook.close();
          } catch (IOException e) {
            logger.error("工作簿关闭异常", e);
          }
        }
        if (inStream != null) {
          try {
            inStream.close();
          } catch (IOException e) {
            logger.error("模板文件流关闭异常", e);
          }
        }
      }
    }
  }

  /**
   * 生成整体评教模板
   * 
   * @param sheet
   * @param root
   */
  @SuppressWarnings("unchecked")
  private static void download_indicator_all(HSSFWorkbook workbook,
      Map<String, Object> root) {
    HSSFSheet sheet = workbook.getSheetAt(0);
    List<SubjectVo> subjectList = (List<SubjectVo>) root.get("subjectList");
    List<EvlIndicatorVo> allIndicator = (List<EvlIndicatorVo>) root
        .get("indicatorVo");
    List<EvlSuggestionType> suggesttypeList = (List<EvlSuggestionType>) root
        .get("suggesttypeList");
    List<EvlLevelWeight> levelList = (List<EvlLevelWeight>) root
        .get("levelList");
    EvlQuestionnaires evlQuestionnaires = (EvlQuestionnaires) root
        .get("question");
    // 表头
    createSheetCell(sheet.getRow(0), 0, root.get("title").toString());
    createSheetCell(sheet.getRow(1), 0, evlQuestionnaires.getDirections());
    CellRangeAddress cra1 = new CellRangeAddress(1, 1, 0, 2);
    sheet.addMergedRegion(cra1);
    CellRangeAddress cra0 = new CellRangeAddress(0, 0, 0, 2);
    sheet.addMergedRegion(cra0);
    // 内容
    HSSFRow row3 = sheet.getRow(3);
    short rowHeight = row3.getHeight();
    HSSFCellStyle style0 = row3.getCell(0).getCellStyle();
    int downMerged1 = 3;
    boolean isHas = true;
    for (int i = 0; i < allIndicator.size(); i++) {
      EvlIndicatorVo oneIndicator = allIndicator.get(i);
      HSSFRow row = null;
      List<EvlIndicatorVo> secondIndicatorVo = oneIndicator
          .getChildIndicators();
      for (int j = 0; j < secondIndicatorVo.size(); j++) {
        EvlIndicatorVo secondIndicator = secondIndicatorVo.get(j);
        if (i == 0 && j == 0) {
          row = row3;
        } else {
          row = sheet.createRow(downMerged1 + j);
          row.setHeight(rowHeight);
        }
        if (j == 0) {
          createSheetCell(row, style0, 0, oneIndicator.getIndicator()
              .getTitle());
        } else {
          createSheetCell(row, style0, 0, "");
        }
        createSheetCell(row, style0, 1, secondIndicator.getIndicator()
            .getTitle());
        createSheetCell(row, style0, 2, "");
      }
      if (secondIndicatorVo.size() > 1) {
        CellRangeAddress cra4 = new CellRangeAddress(downMerged1, downMerged1
            + secondIndicatorVo.size() - 1, 0, 0);
        sheet.addMergedRegion(cra4);
        downMerged1 += secondIndicatorVo.size();
        isHas = false;
      } else {
        row = sheet.createRow(downMerged1);
        row.setHeight(rowHeight);
        createSheetCell(row, style0, 0, oneIndicator.getIndicator().getTitle());
        createSheetCell(row, style0, 1, "");
        createSheetCell(row, style0, 2, "");
        downMerged1++;
      }
    }
    if (isHas) {
      for (int i = 0; i < allIndicator.size(); i++) {
        CellRangeAddress cra4 = new CellRangeAddress(3 + i, 3 + i, 0, 1);
        sheet.addMergedRegion(cra4);
      }
    }
    String levels = "";
    for (int i = 0; i < levelList.size(); i++) {
      levels += levelList.get(i).getLevelName() + "   ";
    }
    String content = "";
    for (int i = 0; i < subjectList.size(); i++) {
      content += subjectList.get(i).getName() + ":" + levels + "\r\n";
    }
    row3.getCell(2).setCellValue(new HSSFRichTextString(content));
    CellRangeAddress cra5 = new CellRangeAddress(3, (downMerged1 == 3 ? 3
        : downMerged1 - 1), 2, 2);
    sheet.addMergedRegion(cra5);

    HSSFCellStyle leftStyle = getCellStyleString(workbook);
    HSSFRow suggestRow = sheet.createRow(downMerged1);
    createSheetCell(suggestRow, leftStyle, 0, "对学校的意见或建议：");
    suggestRow.setHeight(sheet.getRow(0).getHeight());
    CellRangeAddress cra6 = new CellRangeAddress(downMerged1, downMerged1, 0, 2);
    sheet.addMergedRegion(cra6);
    for (int i = 0; i < suggesttypeList.size(); i++) {
      int footIndex = downMerged1 + 1 + i;
      HSSFRow suggestType = sheet.createRow(footIndex);
      createSheetCell(suggestType, leftStyle, 0, suggesttypeList.get(i)
          .getName());
      suggestType.setHeight(rowHeight);
      CellRangeAddress cra7 = new CellRangeAddress(footIndex, footIndex, 1, 2);
      sheet.addMergedRegion(cra7);
    }
  }

  /**
   * 生成一级,二级评教模板
   * 
   * @param sheet
   * @param root
   * @param flag
   */
  @SuppressWarnings("unchecked")
  private static void download_indicator(HSSFWorkbook workbook,
      Map<String, Object> root, boolean flag) {
    HSSFSheet sheet = workbook.getSheetAt(0);
    List<SubjectVo> subjectList = (List<SubjectVo>) root.get("subjectList");
    List<EvlIndicatorVo> allIndicator = (List<EvlIndicatorVo>) root
        .get("indicatorVo");
    List<EvlSuggestionType> suggesttypeList = (List<EvlSuggestionType>) root
        .get("suggesttypeList");
    EvlQuestionnaires evlQuestionnaires = (EvlQuestionnaires) root
        .get("question");
    // 表头
    createSheetCell(sheet.getRow(0), 0, root.get("title").toString());
    createSheetCell(sheet.getRow(1), 0, evlQuestionnaires.getDirections());
    HSSFRow row2 = sheet.getRow(2);
    HSSFRow row3 = sheet.getRow(3);
    HSSFCellStyle headStyle = row3.getCell(2).getCellStyle();
    int headWidth = 1500;
    for (int i = 0; i < subjectList.size(); i++) {
      String subjectTitle = subjectList.get(i).getName();
      createSheetCell(row3, headStyle, 2 + i, subjectTitle);
      if (i > 0) {
        createSheetCell(row2, headStyle, 2 + i, "");
      }
      sheet
          .setColumnWidth(2 + i, headWidth + (subjectTitle.length() - 1) * 500);
    }
    int subjectCount = 1;
    if (subjectList.size() > 0) {
      subjectCount = subjectList.size();
    }
    CellRangeAddress cra2 = new CellRangeAddress(2, 2, 2, 1 + subjectCount);
    sheet.addMergedRegion(cra2);
    CellRangeAddress cra1 = new CellRangeAddress(1, 1, 0, 1 + subjectCount);
    sheet.addMergedRegion(cra1);
    CellRangeAddress cra0 = new CellRangeAddress(0, 0, 0, 1 + subjectCount);
    sheet.addMergedRegion(cra0);
    // 内容
    HSSFRow row4 = sheet.getRow(4);
    short rowHeight = row4.getHeight();
    HSSFCellStyle style0 = row4.getCell(0).getCellStyle();
    int downMerged1 = 4;
    boolean isHas = true;
    for (int i = 0; i < allIndicator.size(); i++) {
      EvlIndicatorVo oneIndicator = allIndicator.get(i);
      HSSFRow row = null;
      List<EvlIndicatorVo> secondIndicatorVo = oneIndicator
          .getChildIndicators();
      for (int j = 0; j < secondIndicatorVo.size(); j++) {
        EvlIndicatorVo secondIndicator = secondIndicatorVo.get(j);
        if (i == 0 && j == 0) {
          row = row4;
        } else {
          row = sheet.createRow(downMerged1 + j);
          row.setHeight(rowHeight);
        }
        if (j == 0) {
          createSheetCell(row, style0, 0, oneIndicator.getIndicator()
              .getTitle());
        } else {
          createSheetCell(row, style0, 0, "");
        }
        createSheetCell(row, style0, 1, secondIndicator.getIndicator()
            .getTitle());
        for (int k = 0; k < subjectList.size(); k++) {
          createSheetCell(row, style0, 2 + k, "");
        }
      }
      if (secondIndicatorVo.size() > 1) {
        CellRangeAddress cra4 = new CellRangeAddress(downMerged1, downMerged1
            + secondIndicatorVo.size() - 1, 0, 0);
        sheet.addMergedRegion(cra4);
        if (flag) {
          for (int k = 0; k < subjectList.size(); k++) {
            CellRangeAddress cra5 = new CellRangeAddress(downMerged1,
                downMerged1 + secondIndicatorVo.size() - 1, 2 + k, 2 + k);
            sheet.addMergedRegion(cra5);
          }
        }
        downMerged1 += secondIndicatorVo.size();
        isHas = false;
      } else {
        row = sheet.createRow(downMerged1);
        row.setHeight(rowHeight);
        createSheetCell(row, style0, 0, oneIndicator.getIndicator().getTitle());
        createSheetCell(row, style0, 1, "");
        for (int k = 0; k < subjectList.size(); k++) {
          createSheetCell(row, style0, 2 + k, "");
        }
        downMerged1++;
      }
    }
    if (isHas) {
      for (int i = 0; i < allIndicator.size(); i++) {
        CellRangeAddress cra4 = new CellRangeAddress(4 + i, 4 + i, 0, 1);
        sheet.addMergedRegion(cra4);
      }
    }
    HSSFCellStyle leftStyle = getCellStyleString(workbook);
    HSSFRow suggestRow = sheet.createRow(downMerged1);
    createSheetCell(suggestRow, leftStyle, 0, "对学校的意见或建议：");
    suggestRow.setHeight(sheet.getRow(0).getHeight());

    CellRangeAddress cra6 = new CellRangeAddress(downMerged1, downMerged1, 0,
        1 + subjectList.size());
    sheet.addMergedRegion(cra6);
    for (int i = 0; i < suggesttypeList.size(); i++) {
      int footIndex = downMerged1 + 1 + i;
      HSSFRow suggestType = sheet.createRow(footIndex);
      createSheetCell(suggestType, leftStyle, 0, suggesttypeList.get(i)
          .getName());
      suggestType.setHeight(rowHeight);
      CellRangeAddress cra7 = new CellRangeAddress(footIndex, footIndex, 1,
          1 + subjectList.size());
      sheet.addMergedRegion(cra7);
    }
  }

  /**
   * 单元格字符串样式
   * 
   * @param workbook
   * @return
   */
  private static HSSFCellStyle getCellStyleString(HSSFWorkbook workbook) {
    HSSFCellStyle cellStyleString = workbook.createCellStyle();
    cellStyleString.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    cellStyleString.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    return cellStyleString;
  }

  /**
   * 资源类加载url
   * 
   * @param resource
   * @return
   */
  private static URL getResource(String resource) {
    ClassLoader classLoader = null;// 类加载器
    classLoader = Thread.currentThread().getContextClassLoader();
    return classLoader.getResource(resource);
  }

  /**
   * 获取类根路径
   * 
   * @param resource
   * @return
   */
  private static String getPath(String resource) {
    if (resource != null) {
      if (resource.startsWith(CLASS_PATH_PREFIX)) {
        resource = getPath("") + resource.replaceAll(CLASS_PATH_PREFIX, "");
      }
    }
    URL url = getResource(resource);
    if (url == null)
      return null;
    return url.getPath().replaceAll("%20", " ");
  }

  /**
   * 获取poi excel 中cell 内容
   * 
   * @param cell
   * @return
   */
  public static String getCellValue(Cell cell) {
    if (cell == null) {
      return "";
    }
    String cellValue = "";
    DecimalFormat df = new DecimalFormat("#");
    switch (cell.getCellType()) {
    case Cell.CELL_TYPE_STRING:
      cellValue = cell.getRichStringCellValue().getString().trim();
      break;
    case Cell.CELL_TYPE_NUMERIC:
      cellValue = df.format(cell.getNumericCellValue()).toString().trim();
      break;
    case Cell.CELL_TYPE_BOOLEAN:
      cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
      break;
    case Cell.CELL_TYPE_FORMULA:
      cellValue = cell.getCellFormula();
      break;
    default:
      cellValue = "";
    }
    return cellValue;
  }
}
