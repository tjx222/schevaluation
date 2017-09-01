/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.org.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.BaseObject;

/**
 * 学校关系表 Entity
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: OrganizationRelationship.java, v 1.0 2016-09-29 Generate Tools
 *          Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = OrganizationRelationship.TABLE_NAME)
public class OrganizationRelationship extends BaseObject {
	public static final String TABLE_NAME = "sys_org_relationship";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "org_id", nullable = false)
	private Integer orgId;

	@Column(name = "phase_id", nullable = false)
	private Integer phaseId;

	@Column(name = "schooling", nullable = false)
	private Integer schooling;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getPhaseId() {
		return this.phaseId;
	}

	public void setSchooling(Integer schooling) {
		this.schooling = schooling;
	}

	public Integer getSchooling() {
		return this.schooling;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof OrganizationRelationship))
			return false;
		OrganizationRelationship castOther = (OrganizationRelationship) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}
