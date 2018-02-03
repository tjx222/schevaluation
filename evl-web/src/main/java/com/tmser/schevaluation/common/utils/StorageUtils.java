package com.tmser.schevaluation.common.utils;

import com.tmser.schevaluation.utils.DateUtils;
import com.tmser.schevaluation.utils.Identities;


/**
 * 存储工具类
 * @author csj
 * @version $Id: StorageUtils.java, v 1.0 2015年2月10日 下午2:54:54 csj Exp $
 */
public class StorageUtils {

	/**
	 * 缺省  总目录名 
	 */
	public static final String DEFAULT_MULU = "jy";
	/**
	 * 教案  总目录名 
	 */
	public static final String LESSION_PLAN_MULU = "plan";
	/**
	 * 反思  总目录名 
	 */
	public static final String REFLECTION_MULU = "reflection";
	/**
	 * 课件  总目录名 
	 */
	public static final String COURSEWARE_MULU = "courseware";
	/**
	 * 习题  总目录名 
	 */
	public static final String EXAMPLE_MULU = "example";
	/**
	 * 素材  总目录名 
	 */
	public static final String MATERIAL_MULU = "material";
	
	
	/**
	 * 撰写教案 文件格式 
	 */
	public static final String PLAN_FILE_FORMAT = "doc";
	
	
	/**
	 * 撰写教案-存储相对路径
	 * @return
	 */
	public static String getLessionPlanForder() {
		StringBuilder forder = new StringBuilder("/");
		forder.append(LESSION_PLAN_MULU+"/");
		forder.append(DateUtils.getYear() + "/" + DateUtils.getMonth() + "/" + DateUtils.getDay() + "/");
		String newName = Identities.uuid2();
		newName = newName + "." + PLAN_FILE_FORMAT;
		forder.append(newName);
		return forder.toString();
	}
	/**
	 * 撰写教案-存储相对路径
	 * @param mulu
	 * @return
	 */
	public static String getForder(String type,String fileName) {
		String aim = DEFAULT_MULU;
		if(type!=null && !type.equals("")) {
			if(type.equals(LESSION_PLAN_MULU))
				aim = LESSION_PLAN_MULU;
			else if(type.equals(REFLECTION_MULU))
				aim = REFLECTION_MULU;
			else if(type.equals(COURSEWARE_MULU))
				aim = COURSEWARE_MULU;
			else if(type.equals(EXAMPLE_MULU))
				aim = EXAMPLE_MULU;
			else if(type.equals(MATERIAL_MULU))
				aim = MATERIAL_MULU;
		}
		StringBuilder forder = new StringBuilder("/");
		forder.append(aim+"/");
		forder.append(DateUtils.getYear() + "/" + DateUtils.getMonth() + "/" + DateUtils.getDay() + "/");
		String newName = Identities.uuid2();
		if(fileName.split("\\.").length > 1)
			newName = newName + fileName.substring(fileName.lastIndexOf("."), fileName.length());
		forder.append(newName);
		return forder.toString();
	}
}
