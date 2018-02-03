/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service;

import java.util.List;
import java.util.Map;

import com.tmser.schevaluation.evl.vo.GradeAndClassVo;
import com.tmser.schevaluation.evl.vo.SubjectVo;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: EvlMetaService.java, v 1.0 2016年3月18日 上午9:09:21 tmser Exp $
 */
public interface EvlMetaService {
	/**
	 * 获取学段对应的学科Map
	 * @param phaseId
	 * @return
	 */
	public Map<String,String> getSubjectMap(Integer phaseId);
	/**
	 * 获取学段对应的年级Map
	 * @param phaseId
	 * @return
	 */
	public Map<String,String> getGradeMap(Integer phaseId);
	/**
	 * 通过学段ID获得此学段下的学科列表{无定制}
	 * @param phaseId
	 * @return
	 */
	public List<SubjectVo> findSubjectByPhaseId(Integer phaseId,Integer orgId);

	/**
	 * 通过学段ID和包含的学科ID集合获得此学段下的学科列表
	 * @param phaseId
	 * @param tXdXk
	 * @param includeIds
	 * @return
	 */
	List<SubjectVo> findSubjectByPhaseId(Integer phaseId,  String[] includeIds);
	
	/**
	 * 获得当前机构下对应学制的年级
	 * @param phaseId
	 * @param schooling
	 * @return
	 */
	List<GradeAndClassVo> findGradeByPhaseIdAndSystemId(Integer phaseId, Integer schooling);
	
	/**
	 * 获得当前机构下对应学制的年级
	 * @param phaseId
	 * @return
	 */
	List<GradeAndClassVo> findGradeByPhaseIdAndSystemId(Integer phaseId);
	/**
	 * 获得当前机构下对应学制的学科
	 * @param phaseId
	 * @param type
	 * @param systemId
	 * @return
	 */
	List<SubjectVo> findSubjectByPhaseIdAndSystemId(Integer phaseId, Integer type, Integer systemId);
}
