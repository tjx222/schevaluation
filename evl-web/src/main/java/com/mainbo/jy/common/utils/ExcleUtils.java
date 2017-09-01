/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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

/**
 * <pre>
 * Excle操作的工具
 * </pre>
 *
 * @author zpp
 * @version $Id: ExcleUtils.java, v 1.0 2015年10月16日 下午5:15:40 zpp Exp $
 */
public abstract class ExcleUtils {

	private final static Logger logger = LoggerFactory.getLogger(ExcleUtils.class);

	/**
	 * 从Excle文件中读取信息并封装成List<Map>返回，其中list中每一个map代表一行数据，
	 * map中的key代表列值（1,2,3,4...）,value存放对应列的值。
	 * @param file
	 */
	public static final List<Map<Integer, String>> findDatasFromFile(MultipartFile file) {
		List<Map<Integer, String>> returnlist = new ArrayList<Map<Integer,String>>();
		if(file.isEmpty()){
			logger.info("文件是空的！");
		}else{
			String fileName = file.getOriginalFilename();
			String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
			if("xls".equals(prefix) || "xlsx".equals(prefix)){
				Workbook workbook = null;
				try {
					workbook = new HSSFWorkbook(file.getInputStream());
				} catch (Exception e) {
					logger.error("--文件解析出错--", e);
				}
				if(workbook!=null){
					Sheet sheet = workbook.getSheetAt(0);
					Row row = null;
					int m = 0;
					int lastRowNum = sheet.getLastRowNum();
					if(lastRowNum>0){
						for(int i=0;i<=lastRowNum;i++){
							row = sheet.getRow(i);
							if(row!=null){
								short lastCellNum = row.getLastCellNum();
								Map<Integer, String> map = new HashMap<Integer,String>();
								for(int j=0;j<lastCellNum;j++){
									if(row.getCell(j)!=null){
										Cell cell = row.getCell(j);
										cell.setCellType(Cell.CELL_TYPE_STRING);//字符串类型
										String value = cell.getStringCellValue();
										if(org.apache.commons.lang3.StringUtils.isEmpty(value)){
											m++;
										}else{
											value = value.trim();
										}
										map.put(j+1, value);
									}else{
										map.put(j+1, null);
										m++;
									}
								}
								if(m == lastCellNum){//这一行全为空
									break;
								}
								returnlist.add(map);
								m = 0;
							}
						}
					}
				}
			}else{
				logger.info("文件的后缀名错误，后缀名必须是xls或者xlsx的文件！");
			}

		}
		return returnlist;
	}

	/**
	 * 将数据写入Excle并导出下载，data参数要求比较特殊。说明如下：
	 * @param response
	 * @param map :初始化excle表格参数，key分别是{"filename":"文件名称","title":"第一行表格说明","cellcount":"列的数量","sheetname":"sheet的名称"}
	 * @param data ：数个数据集合，list中的每一个map作为一行数据，每列的key:value是{1:'',2:'',3:'',4:''...}
	 */
	public static final void exportExcle(HttpServletResponse response,Map<String, String> map,List<Map<Integer, String>> data) {
		if(!CollectionUtils.isEmpty(map)){
			String filename = map.get("filename");
			String title = map.get("title");
			String cellcount = map.get("cellcount");
			String sheetname = map.get("sheetname");
			HSSFWorkbook wb = new HSSFWorkbook();  //--->创建了一个excel文件
			HSSFSheet sheet = wb.createSheet(sheetname); //--->创建了一个工作簿
			HSSFCellStyle style = creatStyle(wb,(short)14,true);
			HSSFCellStyle style2 = creatStyle(wb,(short)11,false);
			sheet.setDefaultColumnWidth(25);
			HSSFRow row0 = sheet.createRow(0);   //--->创建一行
			// 四个参数分别是：起始行，起始列，结束行，结束列
			int columns = Integer.parseInt(cellcount);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns-1));
			row0.setHeightInPoints(35);
			HSSFCell cell0 = row0.createCell(0);   //--->创建一个单元格
			cell0.setCellStyle(style);
			cell0.setCellValue(title);
			if(!CollectionUtils.isEmpty(data)){
				for(int i=0;i<data.size();i++){
					Map<Integer, String> mapt = data.get(i);
					if(i==0){
						HSSFRow rowTitle = sheet.createRow(i+1);
						rowTitle.setHeightInPoints(28);
						for(int j=0;j<columns;j++){
							HSSFCell cellTitle = rowTitle.createCell(j);
							cellTitle.setCellStyle(style);
							cellTitle.setCellValue(mapt.get(j+1));
						}
					}else{
						HSSFRow rowdata = sheet.createRow(i+1);
						rowdata.setHeightInPoints(20);
						for(int j=0;j<columns;j++){
							HSSFCell celldata = rowdata.createCell(j);
							celldata.setCellStyle(style2);
							celldata.setCellValue(mapt.get(j+1));
						}
					}
				}
			}
			OutputStream out;
			try {
				out = response.getOutputStream();
				response.reset();
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition","attachment; filename="+new String(filename.getBytes("gb2312"),"ISO-8859-1")+".xls");
				wb.write(out);
				wb.close();
			} catch (IOException e) {
				logger.error("--Excle导出出错--", e);
			}
		}
	}

	/**
	 * @param wb :工作簿
	 * @param fontsize :字体大小
	 * @param isbold :是否加粗
	 * @return
	 */
	private static HSSFCellStyle creatStyle(HSSFWorkbook wb,short fontsize, boolean isbold) {
		HSSFCellStyle style = wb.createCellStyle(); // 样式对象
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
		style.setWrapText(true);//能否换行
		style.setBorderTop((short)1);
		style.setBorderRight((short)1);
		style.setBorderBottom((short)1);
		style.setBorderLeft((short)1);
		//设置标题字体格式
		Font font = wb.createFont();
		//设置字体样式
		font.setFontHeightInPoints(fontsize);   //--->设置字体大小
		font.setFontName("宋体");   //---》设置字体，是什么类型例如：宋体
		font.setBold(isbold);     //--->设置是否是加粗
		style.setFont(font);     //--->将字体格式加入到style1中
		return style;
	}

}
