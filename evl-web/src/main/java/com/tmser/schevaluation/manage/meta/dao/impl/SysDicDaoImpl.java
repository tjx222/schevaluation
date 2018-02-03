package com.tmser.schevaluation.manage.meta.dao.impl;

import org.springframework.stereotype.Repository;

import com.tmser.schevaluation.common.dao.AbstractDAO;
import com.tmser.schevaluation.common.dao.annotation.UseCache;
import com.tmser.schevaluation.manage.meta.bo.SysDic;
import com.tmser.schevaluation.manage.meta.dao.SysDicDao;

/**
 * 元数据表 Dao 实现类
 * 
 * @author zpp
 * @version 1.0 2015-01-21
 */
@Repository
@UseCache
public class SysDicDaoImpl extends AbstractDAO<SysDic, Integer> implements SysDicDao {

}
