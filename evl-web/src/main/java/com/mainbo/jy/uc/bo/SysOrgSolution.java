/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.QueryObject;

 /**
 * 学校方案表试题映射 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SysOrgSolution.java, v 1.0 2015-09-21 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SysOrgSolution.TABLE_NAME)
public class SysOrgSolution extends QueryObject {
	public static final String TABLE_NAME="sys_org_solution";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *学校id
	 **/
	@Column(name="org_id")
	private Integer orgId;

	@Column(name="solution_id")
	private Integer solutionId;
	
	
	/**
	 * 关系是否删除
	 **/
	@Column(name="is_delete")
	private Boolean isDelete;


	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setSolutionId(Integer solutionId){
		this.solutionId = solutionId;
	}

	public Integer getSolutionId(){
		return this.solutionId;
	}

    
	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SysOrgSolution))
				return false;
			SysOrgSolution castOther = (SysOrgSolution) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


