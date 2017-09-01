/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.utils.WebThreadLocalUtils;
import com.mainbo.jy.evl.service.EvlMetaService;
import com.mainbo.jy.evl.vo.GradeAndClassVo;
import com.mainbo.jy.evl.vo.SubjectVo;
import com.mainbo.jy.manage.meta.Meta;
import com.mainbo.jy.manage.meta.MetaUtils;
import com.mainbo.jy.manage.meta.bo.MetaRelationship;
import com.mainbo.jy.manage.org.bo.Organization;
import com.mainbo.jy.manage.org.service.OrganizationService;
import com.mainbo.jy.uc.utils.SessionKey;
/**
 * 元数据查询
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: KpiDataRecord.java, v 1.0 2016-2-22 Generate Tools Exp $
 */
@Service
@Transactional
public class EvlMetaServiceImpl  implements EvlMetaService {
	
	@Autowired
	private OrganizationService organizationService;

	@Override
	public List<SubjectVo> findSubjectByPhaseId(Integer phaseId,Integer orgId){
		Organization org = organizationService.findOne(orgId);
		List<SubjectVo> metaList = new ArrayList<SubjectVo>();
		List<Meta> subjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectByPhaseId(phaseId,org.getId(),org.getAreaId(),MetaRelationship.ORG);
		if(subjectList!=null){
			for(Meta meta:subjectList){
				SubjectVo metaVo = new SubjectVo();
				metaVo.setName(meta.getName());
				metaVo.setId(meta.getId());
				metaList.add(metaVo);
			}
		}
		return metaList;
	}
	
	@Override
	public List<SubjectVo> findSubjectByPhaseId(Integer phaseId, String[] includeIds) {
		Organization org  =(Organization)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORGANIZATION);
		List<SubjectVo> metaList = new ArrayList<SubjectVo>();
		List<Meta> subjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectByPhaseId(phaseId,org.getId(),org.getAreaId(),MetaRelationship.ORG);
		if(subjectList!=null){
			if(includeIds==null){
				for(Meta meta :subjectList){
					SubjectVo metaVo = new SubjectVo();
					metaVo.setName(meta.getName());
					metaVo.setId(meta.getId());
					metaList.add(metaVo);
				}
			}else{
				for(Meta meta:subjectList){
		            for(String id:includeIds){
		            	if((meta.getId()+"").equals(id)){
		            		SubjectVo metaVo = new SubjectVo();
			            	metaVo.setName(meta.getName());
			            	metaVo.setId(meta.getId());
			            	metaList.add(metaVo);
			            	break;
		            	}
		            }
				}
			}
		}
		return metaList;
	}

	@Override
	public List<GradeAndClassVo> findGradeByPhaseIdAndSystemId(Integer currentPhaseId, Integer schooling) {
		List<Meta> gradeList = MetaUtils.getOrgTypeMetaProvider().listAllXzGrade(schooling, currentPhaseId);
		List<GradeAndClassVo> metaList = new ArrayList<GradeAndClassVo>();
		if(gradeList != null){
			for(Meta meta:gradeList){
				GradeAndClassVo metaVo = new GradeAndClassVo();
				metaVo.setName(meta.getName());
				metaVo.setId(meta.getId());
				metaList.add(metaVo);
			}
		}
		return metaList;
	}
	
	@Override
	public List<GradeAndClassVo> findGradeByPhaseIdAndSystemId(Integer currentPhaseId) {
		List<Meta> gradeList =  MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(currentPhaseId);
		List<GradeAndClassVo> metaList = new ArrayList<GradeAndClassVo>();
		if(gradeList != null){
			for(Meta meta:gradeList){
				GradeAndClassVo metaVo = new GradeAndClassVo();
				metaVo.setName(meta.getName());
				metaVo.setId(meta.getId());
				metaList.add(metaVo);
			}
		}
		return metaList;
	}

	/**
	 * @param phaseId
	 * @param type
	 * @param systemId
	 * @return
	 * @see com.mainbo.jy.evl.service.EvlMetaService#findSubjectByPhaseIdAndSystemId(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<SubjectVo> findSubjectByPhaseIdAndSystemId(Integer phaseId,
			Integer type, Integer systemId) {
		
		return null;
	}

	/**
	 * @param phaseId
	 * @return
	 * @see com.mainbo.jy.evl.service.EvlMetaService#getSubjectMap(java.lang.Integer)
	 */
	@Override
	public Map<String, String> getSubjectMap(Integer phaseId) {
		List<Meta> subjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectMeta();
		Map<String, String> subjectMap=new HashMap<String, String>();
		for(Meta subject:subjectList){
			subjectMap.put(subject.getId()+"", subject.getName());
		}
		return subjectMap;
	}
	/**
	 * @param phaseId
	 * @return
	 * @see com.mainbo.jy.evl.service.EvlMetaService#getGradeMap(java.lang.Integer)
	 */
	@Override
	public Map<String, String> getGradeMap(Integer phaseId) {
		List<Meta> gradeList = MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(phaseId);	
		Map<String, String> gradeMap=new HashMap<String, String>();
		for(Meta grade:gradeList){
			gradeMap.put(grade.getId()+"", grade.getName());
		}
		return gradeMap;
	}
	
}
