package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "HRS_GENERAL_IMPORTANCE")
public class HrsGeneralImportance {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "TARGET")
	private Integer target;
	@Column(name = "HRS_GEN_APPRECIATION_ID")
	private Integer hrsGenAppreciationId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public Integer getHrsGenAppreciationId() {
		return hrsGenAppreciationId;
	}

	public void setHrsGenAppreciationId(Integer hrsGenAppreciationId) {
		this.hrsGenAppreciationId = hrsGenAppreciationId;
	}

}
