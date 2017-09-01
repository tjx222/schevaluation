/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

import com.alibaba.druid.support.json.JSONUtils;
import com.mainbo.jy.manage.meta.bo.MetaRelationship;
import com.mainbo.jy.manage.meta.bo.SysDic;
import com.mainbo.jy.manage.meta.dao.MetaRelationshipDao;
import com.mainbo.jy.manage.meta.dao.SysDicDao;
import com.mainbo.jy.utils.StringUtils;

/**
 * <pre>
 * 默认基础数据类型服务
 * </pre>
 *
 * @author tmser
 * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:46:02 tmser Exp $
 */
public abstract class DefaultMetaProvider implements MetaProvider {

	@Autowired
	private MetaRelationshipDao metaRelationshipDao;

	@Autowired
	private SysDicDao sysDicDao;
	@Resource(name = "cacheManger")
	protected CacheManager cacheManager;

	protected Cache metaCache;

	@PostConstruct
	public void init() {
		metaCache = cacheManager.getCache(MetaConstants.META_CACHE_NAME);
	}

	protected Cache getCache() {
		return metaCache;
	}
	
	@Override
	public void evictCache(Integer metashipId){
		Cache cache = null;
		String key = getCacheName(metashipId);
		if (key != null && (cache = getCache()) != null) {
			cache.evict(key);
		}
	}
	
	protected abstract String getCacheName(Integer metashipId);

	/**
	 * @param metaId
	 * @return
	 * @see com.mainbo.jy.manage.meta.service.MetaProvider#getMeta(java.lang.Integer)
	 */
	@Override
	public Meta getMeta(Integer metaId) {
		return sysDicDao.get(metaId);
	}

	@SuppressWarnings("unchecked")
	protected List<Meta> listMetaByType(int type) {
		Cache cache = null;
		if ((cache = getCache()) != null) {
			ValueWrapper cacheElement = cache.get(MetaConstants.DIC_META_CACHE_NAME + type);
			if (cacheElement != null) {
				return (List<Meta>) cacheElement.get();
			}
		}

		SysDic model = new SysDic();
		model.setParentId(type);
		model.addOrder("dicOrderby asc");
		List<Meta> metaList = new ArrayList<>();
		metaList.addAll(sysDicDao.listAll(model));
		if ((cache = getCache()) != null) {
			cache.put(MetaConstants.DIC_META_CACHE_NAME + type, metaList);
		}

		return metaList;
	}

	@SuppressWarnings("unchecked")
	protected List<Meta> listGradeMeta(int type) {
		Cache cache = null;
		if ((cache = getCache()) != null) {
			ValueWrapper cacheElement = cache.get(MetaConstants.META_GRADE_META_CACHE_NAME + type);
			if (cacheElement != null) {
				return (List<Meta>) cacheElement.get();
			}
		}

		SysDic model = new SysDic();
		model.addAlias("s");
		model.setDicLevel(3);
		model.addOrder("s.dicOrderby asc");
		model.buildCondition(" and s.cascadeDicIds like :type").put("type", type + "_%");
		List<Meta> metaList = new ArrayList<>();
		metaList.addAll(sysDicDao.listAll(model));
		if ((cache = getCache()) != null) {
			cache.put(MetaConstants.META_GRADE_META_CACHE_NAME + type, metaList);
		}

		return metaList;
	}

	/**
	 * @param metaId
	 * @return
	 * @see com.mainbo.jy.manage.meta.service.MetaProvider#getMeta(java.lang.Integer)
	 */
	@Override
	public MetaRelationship getMetaRelationshipByPhaseId(Integer phaseId) {
		MetaRelationship model = new MetaRelationship();
		model.setType(getMetaRelationType());
		model.setEid(phaseId);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("scope", MetaRelationship.SYS);
		model.addCustomCondition(" and scope =:scope",params);
		return metaRelationshipDao.getOne(model);
	}
	/**
	 * @param metaId
	 * @return
	 * @see com.mainbo.jy.manage.meta.service.MetaProvider#getMeta(java.lang.Integer)
	 */
	@Override
	public MetaRelationship getMetaRelationshipByPhaseIdAndType(Integer phaseId,Integer orgId,Integer areaId,String type) {
		MetaRelationship model = new MetaRelationship();
		model.setType(getMetaRelationType());
		model.setEid(phaseId);
		MetaRelationship sysSubject = getMetaRelationshipByPhaseId(phaseId);
		MetaRelationship areaSubject = getAreaSubject(phaseId,areaId);
		MetaRelationship orgSubject = getOrgSubject(phaseId,orgId);
		if(MetaRelationship.ORG.equals(type)){
			Map<String,String> dataMap  = MetaRelationshipOrgAreaSys(sysSubject,areaSubject,orgSubject);
			model.setIds(dataMap.get("ids"));
			model.setDescs(dataMap.get("names"));
		}else if(MetaRelationship.AREA.equals(type)){
			Map<String,String> dataMap  = MetaRelationshipAreaSys(sysSubject,areaSubject);
			model.setIds(dataMap.get("ids"));
			model.setDescs(dataMap.get("names"));
		}else{
			return sysSubject;
		}
		return model;
	}
	private MetaRelationship getAreaSubject(Integer phaseId,Integer areaId){
		MetaRelationship model = new MetaRelationship();
		model.setType(MetaRelationship.T_XD_XK);
		model.setEid(phaseId);
		model.setAreaId(areaId);
		model.setScope(MetaRelationship.AREA);
		return metaRelationshipDao.getOne(model);
	}
	private MetaRelationship getOrgSubject(Integer phaseId,Integer orgId){
		MetaRelationship model = new MetaRelationship();
		model.setType(MetaRelationship.T_XD_XK);
		model.setEid(phaseId);
		model.setOrgId(orgId);
		model.setScope(MetaRelationship.ORG);
		return metaRelationshipDao.getOne(model);
	}
	private Map<String,String> MetaRelationshipOrgAreaSys(MetaRelationship sysSubject,MetaRelationship areaSubject,MetaRelationship orgSubject){
		Set<String> orgSet = new LinkedHashSet<String>();
		Set<String> orgNameSet = new LinkedHashSet<String>();
		Set<String> orgExcludeSet = new HashSet<String>();
		Set<String> orgNameExcludeSet = new HashSet<String>();
		
		Set<String> areaSet = new LinkedHashSet<String>();
		Set<String> areaNameSet = new LinkedHashSet<String>();
		Set<String> areaExcludeSet = new HashSet<String>();
		Set<String> areaNameExcludeSet = new HashSet<String>();
		
		Set<String> sysSet = new LinkedHashSet<String>();
		Set<String> sysNameSet = new LinkedHashSet<String>();
		
		if(orgSubject!=null&&StringUtils.isNotEmpty(orgSubject.getIds())){
			String[] orgIds = orgSubject.getIds().split(",");
			String[] orgNames = orgSubject.getDescs().split(",");
			for (int i= 0;i<orgIds.length;i++) {
				orgSet.add(orgIds[i]);
				orgNameSet.add(orgNames[i]);
			}
			if(!StringUtils.isEmpty(orgSubject.getExcludeIds())){
				String[] orgExcludeIds = orgSubject.getExcludeIds().split(",");
				String[] orgExcludeNames = orgSubject.getExcludeNames().split(",");
				for (int i= 0;i<orgExcludeIds.length;i++) {
					orgExcludeSet.add(orgExcludeIds[i]);
					orgNameExcludeSet.add(orgExcludeNames[i]);
				}
			}
		}
		if(areaSubject!=null&&StringUtils.isNotEmpty(areaSubject.getIds())){
			String[] areaIds = areaSubject.getIds().split(",");
			String[] areaNames = areaSubject.getDescs().split(",");
			for (int i= 0;i<areaIds.length;i++) {
				areaSet.add(areaIds[i]);
				areaNameSet.add(areaNames[i]);
			}
			if(!StringUtils.isEmpty(areaSubject.getExcludeIds())){
				String[] orgExcludeIds = areaSubject.getExcludeIds().split(",");
				String[] orgExcludeNames = areaSubject.getExcludeNames().split(",");
				for (int i= 0;i<orgExcludeIds.length;i++) {
					areaExcludeSet.add(orgExcludeIds[i]);
					areaNameExcludeSet.add(orgExcludeNames[i]);
				}
			}
		}
		if(sysSubject!=null&&StringUtils.isNotEmpty(sysSubject.getIds())){
			String[] sysIds = sysSubject.getIds().split(",");
			String[] sysNames = sysSubject.getDescs().split(",");
			for (int i= 0;i<sysIds.length;i++) {
				sysSet.add(sysIds[i]);
				sysNameSet.add(sysNames[i]);
			}
		}
		
		/**
		 * ids
		 */
		Set<String> allSet = new LinkedHashSet<String>();
		allSet.addAll(sysSet);
		allSet.addAll(areaSet);
		if(areaExcludeSet.size()>0){
			allSet.removeAll(areaExcludeSet);
		}
		allSet.addAll(orgSet);
		if(orgExcludeSet.size()>0){
			allSet.removeAll(orgExcludeSet);
		}
		String allIds = StringUtils.join(allSet.toArray(), ",");
		/**
		 * names
		 */
		Set<String> allNameSet = new LinkedHashSet<String>();
		allNameSet.addAll(orgNameSet);
		allNameSet.addAll(areaNameSet);
		allNameSet.addAll(sysNameSet);
		if(areaExcludeSet.size()>0){
			allNameSet.removeAll(areaNameExcludeSet);
		}
		if(orgExcludeSet.size()>0){
			allNameSet.removeAll(orgNameExcludeSet);
		}
		String allNames = StringUtils.join(allNameSet.toArray(), ",");
		Map<String,String> dataMap = new HashMap<String, String>();
		dataMap.put("ids", allIds);
		dataMap.put("names", allNames);
		return dataMap;
	}
	private Map<String,String> MetaRelationshipAreaSys(MetaRelationship sysSubject,MetaRelationship areaSubject){
		
		Set<String> areaSet = new LinkedHashSet<String>();
		Set<String> areaNameSet = new LinkedHashSet<String>();
		Set<String> areaExcludeSet = new HashSet<String>();
		Set<String> areaNameExcludeSet = new HashSet<String>();
		Set<String> sysSet = new LinkedHashSet<String>();
		Set<String> sysNameSet = new LinkedHashSet<String>();
		if(areaSubject!=null&&StringUtils.isNotEmpty(areaSubject.getIds())){
			String[] areaIds = areaSubject.getIds().split(",");
			String[] areaNames = areaSubject.getDescs().split(",");
			for (int i= 0;i<areaIds.length;i++) {
				areaSet.add(areaIds[i]);
				areaNameSet.add(areaNames[i]);
			}
			if(!StringUtils.isEmpty(areaSubject.getExcludeIds())){
				String[] orgExcludeIds = areaSubject.getExcludeIds().split(",");
				String[] orgExcludeNames = areaSubject.getExcludeNames().split(",");
				for (int i= 0;i<orgExcludeIds.length;i++) {
					areaExcludeSet.add(orgExcludeIds[i]);
					areaNameExcludeSet.add(orgExcludeNames[i]);
				}
			}
		}
		if(sysSubject!=null&&StringUtils.isNotEmpty(sysSubject.getIds())){
			String[] sysIds = sysSubject.getIds().split(",");
			String[] sysNames = sysSubject.getDescs().split(",");
			for (int i= 0;i<sysIds.length;i++) {
				sysSet.add(sysIds[i]);
				sysNameSet.add(sysNames[i]);
			}
		}
		
		/**
		 * ids
		 */
		Set<String> allSet = new LinkedHashSet<String>();
		allSet.addAll(sysSet);
		allSet.addAll(areaSet);
		if(areaExcludeSet.size()>0){
			allSet.removeAll(areaExcludeSet);
		}
		String allIds = StringUtils.join(allSet.toArray(), ",");
		/**
		 * names
		 */
		Set<String> allNameSet = new LinkedHashSet<String>();
		allNameSet.addAll(areaNameSet);
		allNameSet.addAll(sysNameSet);
		if(areaExcludeSet.size()>0){
			allNameSet.removeAll(areaNameExcludeSet);
		}
		String allNames = StringUtils.join(allNameSet.toArray(), ",");
		Map<String,String> dataMap = new HashMap<String, String>();
		dataMap.put("ids", allIds);
		dataMap.put("names", allNames);
		return dataMap;
	}
	
	
	
	/**
	 * 获取所有基础数据
	 * 
	 * @return
	 */
	@Override
	public List<MetaRelationship> listAll() {
		MetaRelationship model = new MetaRelationship();
		model.setType(getMetaRelationType());
		model.addOrder("sort asc");
		return metaRelationshipDao.listAll(model);
	}

	/**
	 * 按id获取原数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public MetaRelationship getMetaRelationship(Integer metashipId) {
		return metaRelationshipDao.get(metashipId);
	}

	protected abstract Integer getMetaRelationType();


	/**
	 * 学段原数据服务
	 * 
	 * <pre>
	 * 提供当前平台支持的所有学段相关服务
	 * </pre>
	 *
	 * @author tmser
	 * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:47:28 tmser Exp $
	 */
	public static class DefaultPhaseMetaProvider extends DefaultMetaProvider implements PhaseMetaProvider {

		/**
		 * @return
		 * @see com.mainbo.jy.manage.meta.service.impl.DefaultMetaProvider#getMetaRelationType()
		 */
		@Override
		protected Integer getMetaRelationType() {
			return MetaRelationship.T_XD;
		}

		/**
		 * @return
		 * @see com.mainbo.jy.manage.meta.MetaProvider.PhaseMetaProvider#listAllPhaseMeta()
		 */
		@Override
		public List<Meta> listAllPhaseMeta() {
			return listMetaByType(MetaConstants.PHASE_METADATA_ID);
		}
		
		@Override
		protected String getCacheName(Integer metashipId){
			return null;
		}

	}

	/**
	 * 学段学科原数据服务
	 * 
	 * <pre>
	 * 提供当前平台支持的所有学段学科相关服务
	 * </pre>
	 *
	 * @author tmser
	 * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:47:28 3020mt Exp $
	 */
	public static class DefaultPhaseSubjectMetaProvider extends DefaultMetaProvider implements PhaseSubjectMetaProvider {

		/**
		 * @param metaId
		 * @return
		 * @see com.mainbo.jy.manage.meta.service.MetaProvider.PhaseSubjectMetaProvider#listAllSubject(java.lang.Integer)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<Meta> listAllSubject(Integer metashipId) {
			Cache cache = null;
			if ((cache = getCache()) != null) {
				ValueWrapper cacheElement = cache.get(MetaConstants.META_SUBJECT_CACHE_NAME + metashipId);
				if (cacheElement != null) {
					return (List<Meta>) cacheElement.get();
				}
			}

			MetaRelationship mr = super.getMetaRelationship(metashipId);
			if (mr != null) {
				List<Meta> metaList = new ArrayList<Meta>();
				String[] metaIds = StringUtils.split(mr.getIds(), StringUtils.COMMA);
				for (String metaId : metaIds) {
					Meta meta = super.getMeta(Integer.valueOf(metaId));
					if (meta != null)
						metaList.add(meta);
				}

				if (cache != null) {
					cache.put(MetaConstants.META_SUBJECT_CACHE_NAME + metashipId, metaList);
				}

				return metaList;
			}
			return null;
		}
		
		private List<Meta> listOrgSysSubject(MetaRelationship mr) {
			List<Meta> metaList = new ArrayList<Meta>();
			String[] metaIds = StringUtils.split(mr.getIds(), StringUtils.COMMA);
			for (String metaId : metaIds) {
				Meta meta = super.getMeta(Integer.valueOf(metaId));
				if (meta != null)
					metaList.add(meta);
			}
			return metaList;
		}
		
		@Override
		protected String getCacheName(Integer metashipId){
			return MetaConstants.META_SUBJECT_CACHE_NAME + metashipId;
		}

		/**
		 * @return
		 * @see com.mainbo.jy.manage.meta.service.impl.DefaultMetaProvider#getMetaRelationType()
		 */
		@Override
		protected Integer getMetaRelationType() {
			return MetaRelationship.T_XD_XK;
		}

		/**
		 * @param phaseId
		 * @return
		 * @see com.mainbo.jy.manage.meta.MetaProvider.PhaseSubjectMetaProvider#listAllSubjectByPhaseId(java.lang.Integer)
		 */
		@Override
		public List<Meta> listAllSubjectByPhaseId(Integer phaseId,Integer orgId,Integer areaId,String type) {
			MetaRelationship mr = getMetaRelationshipByPhaseIdAndType(phaseId,orgId,areaId,type);
			if(MetaRelationship.SYS.equals(type)){
				return mr == null ? null : listAllSubject(mr.getId());
			}else{
				return mr == null ? null : listOrgSysSubject(mr);
			}
		}

		/**
		 * @return
		 * @see com.mainbo.jy.manage.meta.MetaProvider.PhaseSubjectMetaProvider#listAllSubjectMeta()
		 */
		@Override
		public List<Meta> listAllSubjectMeta() {
			return listMetaByType(MetaConstants.SUBJECT_METADATA_ID);
		}

	}

	/**
	 * 学段年级原数据服务
	 * 
	 * <pre>
	 * 提供当前平台支持的所有年级学科相关服务
	 * </pre>
	 *
	 * @author tmser
	 * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:47:28 3020mt Exp $
	 */
	public static class DefaultPhaseGradeMetaProvider extends DefaultMetaProvider implements PhaseGradeMetaProvider {

		/**
		 * @param metaId
		 * @return
		 * @see com.mainbo.jy.manage.meta.service.MetaProvider.PhaseGradeMetaProvider#listAllGrade(java.lang.Integer)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<Meta> listAllGrade(Integer metashipId) {
			Cache cache = null;
			if ((cache = getCache()) != null) {
				ValueWrapper cacheElement = cache.get(MetaConstants.META_GRADE_CACHE_NAME + metashipId);
				if (cacheElement != null) {
					return (List<Meta>) cacheElement.get();
				}
			}

			MetaRelationship mr = super.getMetaRelationship(metashipId);
			if (mr != null) {
				List<Meta> metaList = new ArrayList<Meta>();
				String[] metaIds = StringUtils.split(mr.getIds(), StringUtils.COMMA);
				for (String metaId : metaIds) {
					Meta meta = super.getMeta(Integer.valueOf(metaId));
					if (meta != null)
						metaList.add(meta);
				}

				if (cache != null) {
					cache.put(MetaConstants.META_GRADE_CACHE_NAME + metashipId, metaList);
				}

				return metaList;
			}
			return null;
		}
		
		@Override
		protected String getCacheName(Integer metashipId){
			return MetaConstants.META_GRADE_CACHE_NAME + metashipId;
		}

		/**
		 * @return
		 * @see com.mainbo.jy.manage.meta.service.impl.DefaultMetaProvider#getMetaRelationType()
		 */
		@Override
		protected Integer getMetaRelationType() {
			return MetaRelationship.T_XD_NJ;
		}

		/**
		 * @param phaseId
		 * @return
		 * @see com.mainbo.jy.manage.meta.MetaProvider.PhaseGradeMetaProvider#listAllGradeByPhaseId(java.lang.Integer)
		 */
		@Override
		public List<Meta> listAllGradeByPhaseId(Integer phaseId) {
			MetaRelationship mr = getMetaRelationshipByPhaseId(phaseId);
			return mr == null ? null : listAllGrade(mr.getId());
		}

		/**
		 * @return
		 * @see com.mainbo.jy.manage.meta.MetaProvider.PhaseGradeMetaProvider#listAllGradeMeta()
		 */
		@Override
		public List<Meta> listAllGradeMeta() {
			return listGradeMeta(MetaConstants.PHASE_METADATA_ID);
		}
		
		

	}

	/**
	 * 学校类型原数据服务
	 * 
	 * <pre>
	 * 提供当前平台支持的所有学校类型相关服务
	 * </pre>
	 *
	 * @author tmser
	 * @version $Id: MetaProvider.java, v 1.0 2016年9月30日 上午9:47:28 3020mt Exp $
	 */
	public static class DefaultOrgTypeMetaProvider extends DefaultMetaProvider implements OrgTypeMetaProvider {

		/**
		 * 根据学校类型及学段id获取，包含的年级列表
		 * 
		 * @param metaId
		 *            学校类型id
		 * @param phaseId
		 *            学段id
		 * @return
		 */
		@Override
		public List<Meta> listAllGrade(Integer metaId, Integer phaseId) {
			return listPhaseGradeMap(metaId).get(phaseId);
		}

		/**
		 * 获取根据学校类型id获取学段年级关系
		 * 
		 * @param metaId
		 *            学校类型id
		 * @return Map<phaseId, grade list>
		 */
		@Override
		@SuppressWarnings("unchecked")
		public Map<Integer, List<Meta>> listPhaseGradeMap(Integer metashipId) {
			Cache cache = null;
			if ((cache = getCache()) != null) {
				ValueWrapper cacheElement = cache.get(MetaConstants.META_ORGTYPE_CACHE_NAME + metashipId);
				if (cacheElement != null) {
					return (Map<Integer, List<Meta>>) cacheElement.get();
				}
			}

			MetaRelationship mr = super.getMetaRelationship(metashipId);

			if (mr != null) {
				Map<Integer, List<Meta>> map = new HashMap<Integer, List<Meta>>();
				if (StringUtils.isNotBlank(mr.getIds())) {
					List<Object> rs = (List<Object>) JSONUtils.parse(mr.getIds());
					for (Object obj : rs) {
						if (obj instanceof Map) {
							Map<String, List<Integer>> phaseInfos = (Map<String, List<Integer>>) obj;
							for (String k : phaseInfos.keySet()) {
								List<Meta> metaList = new ArrayList<Meta>();
								for (Integer metaId : phaseInfos.get(k)) {
									Meta meta = super.getMeta(metaId);
									if (meta != null)
										metaList.add(meta);
								}

								map.put(Integer.valueOf(k), metaList);
							}
						}
					}
				}

				if (cache != null) {
					cache.put(MetaConstants.META_ORGTYPE_CACHE_NAME + metashipId, map);
				}

				return map;
			}

			return Collections.emptyMap();
		}
		
		@Override
		protected String getCacheName(Integer metashipId){
			return MetaConstants.META_ORGTYPE_CACHE_NAME + metashipId;
		}

		/**
		 * @return
		 * @see com.mainbo.jy.manage.meta.service.impl.DefaultMetaProvider#getMetaRelationType()
		 */
		@Override
		protected Integer getMetaRelationType() {
			return MetaRelationship.T_ORG_TYPE;
		}

		/**
		 * @param metashipId
		 * @return
		 * @see com.mainbo.jy.manage.meta.MetaProvider.OrgTypeMetaProvider#listAllPhase(java.lang.Integer)
		 */
		@Override
		public List<Meta> listAllPhaseMeta(Integer metashipId) {
			Map<Integer, List<Meta>> ms = listPhaseGradeMap(metashipId);
			List<Meta> metaList = new ArrayList<Meta>();
			for (Integer key : ms.keySet()) {
				MetaRelationship mr = getMetaRelationship(key);
				metaList.add(getMeta(mr.getEid()));
			}
			return metaList;
		}

		/**
		 * @param metashipId
		 * @return
		 * @see com.mainbo.jy.manage.meta.MetaProvider.OrgTypeMetaProvider#listAllPhaseMeta(java.lang.Integer)
		 */
		@Override
		public List<MetaRelationship> listAllPhase(Integer metashipId) {
			Map<Integer, List<Meta>> ms = listPhaseGradeMap(metashipId);
			List<MetaRelationship> metaList = new ArrayList<MetaRelationship>();
			for (Integer key : ms.keySet()) {
				MetaRelationship mr = getMetaRelationship(key);
				metaList.add(mr);
			}
			return metaList;
		}

		/**
		 * @param metashipId
		 * @return
		 * @see com.mainbo.jy.manage.meta.MetaProvider.OrgTypeMetaProvider#listPhaseGradeCountMap(java.lang.Integer)
		 */
		@Override
		public Map<Integer, Integer> listPhaseGradeCountMap(
				Integer metashipId) {
			
			Map<Integer, List<Meta>> phaseGrades  = listPhaseGradeMap(metashipId);
			Map<Integer, Integer> gradeCountMap = new HashMap<Integer, Integer>();
			for(Integer phase : phaseGrades.keySet()){
				if(getMetaRelationship(phase).getEid() == 142){
					gradeCountMap.put(phase, 3);
				}else{
					gradeCountMap.put(phase, phaseGrades.get(phase).size());
				}
			}
			
			return gradeCountMap;
		}

		/**
		 * 获取根据学校类型id获取学段年级关系
		 * 
		 * @param metaId
		 *            学校类型id
		 * @return Map<phaseId, grade list>
		 */
		@Override
		@SuppressWarnings("unchecked")
		public Map<Integer, List<Meta>> listXzPhaseGradeMap(Integer metashipId) {
			Cache cache = null;
			if ((cache = getCache()) != null) {
				ValueWrapper cacheElement = cache.get(MetaConstants.META_XZ_ORGTYPE_CACHE_NAME + metashipId);
				if (cacheElement != null) {
					return (Map<Integer, List<Meta>>) cacheElement.get();
				}
			}
			MetaRelationship mr = super.getMetaRelationship(metashipId);
			if (mr != null) {
				Map<Integer, List<Meta>> map = new HashMap<Integer, List<Meta>>();
				if (StringUtils.isNotBlank(mr.getExcludeIds())) {
					List<Object> rs = (List<Object>) JSONUtils.parse(mr.getExcludeIds());
					for (Object obj : rs) {
						if (obj instanceof Map) {
							Map<String, List<Integer>> phaseInfos = (Map<String, List<Integer>>) obj;
							for (String k : phaseInfos.keySet()) {
								List<Meta> metaList = new ArrayList<Meta>();
								for (Integer metaId : phaseInfos.get(k)) {
									Meta meta = super.getMeta(metaId);
									if (meta != null)
										metaList.add(meta);
								}

								map.put(Integer.valueOf(k), metaList);
							}
						}
					}
				}

				if (cache != null) {
					cache.put(MetaConstants.META_XZ_ORGTYPE_CACHE_NAME + metashipId, map);
				}

				return map;
			}
			return Collections.emptyMap();
		}
		
		/**
		 * 行政年级
		 * 根据学校类型及学段id获取，包含的年级列表
		 * 
		 * @param metaId
		 *            学校类型id
		 * @param phaseId
		 *            学段id
		 * @return
		 */
		@Override
		public List<Meta> listAllXzGrade(Integer metaId, Integer phaseId) {
			return listXzPhaseGradeMap(metaId).get(phaseId);
		}
	}

}
