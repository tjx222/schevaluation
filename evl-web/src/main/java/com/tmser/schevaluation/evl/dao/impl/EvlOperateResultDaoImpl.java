/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tmser.schevaluation.common.dao.AbstractDAO;
import com.tmser.schevaluation.evl.bo.EvlOperateResult;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.dao.EvlOperateResultDao;
import com.tmser.schevaluation.evl.statics.EvlMemberStatus;

/**
 * 评教结果表 Dao 实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlOperateResult.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
@Repository
public class EvlOperateResultDaoImpl extends AbstractDAO<EvlOperateResult, String> implements EvlOperateResultDao {

	@Override
	public List<EvlOperateResult> findAnalyzeTeacherResultScore(final EvlOperateResult tch) {
		String sql = "SELECT o.teacherId, AVG(o.resultScore) resultScore from EvlOperateResult o where o.classId=:classId"
				+ " and o.subjeId=:subjeId and o.questionnairesId=:questionId"
				+ " and o.indicatorId=:indicatorId and o.teacherId is not null and"
				+ " EXISTS (select 1 from EvlQuestionMember m WHERE "
				+ "m.`code`=o.code and m.classId = :classId AND m.`status`=:status and m.questionId =:questionId) group by o.teacherId";
		Map<String,Object> params = new HashMap<>();
		params.put("status", EvlMemberStatus.tijiaowenjuan.getValue());
		params.put("classId", tch.getClassId());
		params.put("questionId", tch.getQuestionnairesId());
		params.put("subjeId", tch.getSubjeId());
		params.put("indicatorId", tch.getIndicatorId());

		return super.queryByNamedSql(sql, params, new RowMapper<EvlOperateResult>(){
			@Override
			public EvlOperateResult mapRow(ResultSet rs, int rowNum) throws SQLException {
				if(rs == null || rs.getInt(1)==0){
					return null;
				}
				EvlOperateResult or = new EvlOperateResult();
				or.setTeacherId(rs.getInt(1));
				or.setResultScore(rs.getDouble(2));
				or.setOrgId(tch.getOrgId());
				or.setQuestionnairesId(tch.getQuestionnairesId());
				or.setSubjeId(tch.getSubjeId());
				or.setGradeId(tch.getGradeId());
				or.setClassId(tch.getClassId());
				or.setIndicatorId(tch.getIndicatorId());
				return or;
			}
		});
	}
	
	@Override
	public List<EvlOperateResult> findAnalyzeIndicatoResultScore(final EvlOperateResult eq) {
		String sql = "SELECT o.indicatorId, AVG(o.resultScore) resultScore from EvlOperateResult o where "
				+ " o.questionnairesId=:questionId and o.code is not null and "
				+ " EXISTS (select 1 from EvlQuestionMember m WHERE "
				+ "m.`code`= o.code and m.classId = o.classId AND m.`status`=:status and m.questionId =:questionId) group by o.indicatorId";
		Map<String,Object> params = new HashMap<>();
		params.put("status", EvlMemberStatus.tijiaowenjuan.getValue());
		params.put("questionId", eq.getQuestionnairesId());

		return super.queryByNamedSql(sql, params, new RowMapper<EvlOperateResult>(){
			@Override
			public EvlOperateResult mapRow(ResultSet rs, int rowNum) throws SQLException {
				if(rs == null){
					return null;
				}
				EvlOperateResult or = new EvlOperateResult();
				or.setIndicatorId(rs.getString(1));
				or.setResultScore(rs.getDouble(2));
				or.setOrgId(eq.getOrgId());
				or.setQuestionnairesId(eq.getQuestionnairesId());
				return or;
			}
		});
	}
	
	
	@Override
	public List<EvlOperateResult> findAnalyzeResultScore(final EvlOperateResult eq) {
		String sql = 
				"select o."+eq.group()+" id , round(sum(o.resultScore)/count(DISTINCT o.code,o.classId,o.subjeId),2) as avgScore from EvlOperateResult o" 
				+" where o.questionnairesId=:questionId and o.orgId=:orgId and o.teacherId is not null and "
				+" EXISTS (select 1 from EvlQuestionMember m where o.code=m.code and m.status=:status and m.questionId=:questionId)"
				+" GROUP BY o."+eq.group()+" ORDER BY avgScore desc";

		Map<String,Object> params = new HashMap<>();
		params.put("status", EvlMemberStatus.tijiaowenjuan.getValue());
		params.put("questionId", eq.getQuestionnairesId());
		params.put("orgId", eq.getOrgId());

		return super.queryByNamedSql(sql, params, new RowMapper<EvlOperateResult>(){
			@Override
			public EvlOperateResult mapRow(ResultSet rs, int rowNum) throws SQLException {
				if(rs == null || rs.getInt(1)==0){
					return null;
				}
				EvlOperateResult or = new EvlOperateResult();
				or.setStandby2(rs.getInt(1));
				or.setResultScore(rs.getDouble(2));
				or.setOrgId(eq.getOrgId());
				or.setQuestionnairesId(eq.getQuestionnairesId());
				return or;
			}
		});
	}
	@Override
	public List<EvlOperateResult> findAnalyzeIndicatorList(EvlQuestionnaires eq, String code) {
		EvlOperateResult eor = new EvlOperateResult();
		eor.setOrgId(eq.getOrgId());
		eor.setQuestionnairesId(eq.getQuestionnairesId());
		eor.setUserType(eq.getType());
		eor.setIndicatorLevel(eq.getIndicatorType());
		eor.setCode(code);
		eor.addCustomCulomn("orgId,questionnairesId,indicatorId,resultScore");
		List<EvlOperateResult> resultList = this.listAll(eor);

		
		Map<String,EvlOperateResult> resultMap = new HashMap<String,EvlOperateResult>();
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		for (EvlOperateResult eat1 : resultList) {
			String key = "c"+eat1.getClassId()+"s"+eat1.getSubjeId()+"i"+eat1.getIndicatorId();
			EvlOperateResult result = resultMap.get(key);
			if(result == null){
				countMap.put(key, 1);
				resultMap.put(key, eat1);
			}else{
				eat1.setResultScore(result.getResultScore()+eat1.getResultScore());
				countMap.put(key, countMap.get(key)+1);
				resultMap.put(key, eat1);
			}
		}
		for (String ope : resultMap.keySet()) {
			EvlOperateResult analyTea = resultMap.get(ope);
			Integer num = countMap.get(ope);
			analyTea.setResultScore(Double.valueOf(new DecimalFormat("0.00").format(analyTea.getResultScore() / num)));
		}
		
		Map<String,EvlOperateResult> resultMap2 = new HashMap<String,EvlOperateResult>();
		Map<String,Integer> countMap2 = new HashMap<String,Integer>();
		for (EvlOperateResult eat1 : resultList) {
			String key = "i"+eat1.getIndicatorId();
			EvlOperateResult result = resultMap2.get(key);
			if(result == null){
				countMap2.put(key, 1);
				resultMap2.put(key, eat1);
			}else{
				eat1.setResultScore(result.getResultScore()+eat1.getResultScore());
				countMap2.put(key, countMap2.get(key)+1);
				resultMap2.put(key, eat1);
			}
		}
		for (String ope : resultMap2.keySet()) {
			EvlOperateResult analyTea = resultMap2.get(ope);
			Integer num = countMap2.get(ope);
			analyTea.setResultScore(Double.valueOf(new DecimalFormat("0.00").format(analyTea.getResultScore() / num)));
		}
		return resultList;
	}
	@Override
	public void batchDelete(Integer questionnairesId) {
		EvlOperateResult resultModel = new EvlOperateResult();
		resultModel.setQuestionnairesId(questionnairesId);
		Object[] args = {questionnairesId,-1};
		String sql = "delete from EvlOperateResult where questionnairesId=? and resultLevel=?";
		this.update(sql, args);
	}
	@Override
	public boolean checkIndicatorCount(Integer questionId, String userCode,int count) {
		String sql ="select subjeId from EvlOperateResult where questionnairesId=? and code=? and resultLevel>0 GROUP BY subjeId HAVING COUNT(indicatorId) < ?";
		List<Map<String, Object>> result = this.query(sql, new Object[]{questionId,userCode,count});
		if(result.size()>0){
			return false;
		}
		return true;
	}

	@Override
	public void deleteRepeatResult(Integer questionId, String userCode) {
		String sql = "SELECT max(r.id) mid "
				+ " from EvlOperateResult r where r.questionnairesId=? and r.code=? "
				+ " GROUP BY r.classId, r.teacherId, r.subjeId,r.indicatorId HAVING count(r.id) >1";
		List<Map<String,Object>> mids = this.query(sql, new Object[]{questionId,userCode});
		if(mids != null){
			for(Map<String,Object> mid : mids){
				this.delete(String.valueOf(mid.get("mid")));
			}
		}
	}
}
