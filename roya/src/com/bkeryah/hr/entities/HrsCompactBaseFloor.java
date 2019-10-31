package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "HRS_COMPACT_BASEFLOOR")
public class HrsCompactBaseFloor {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "RELATIVE_IMPORTANCE")
	private Integer relativeImportance;
	@Column(name = "HRS_COMPACT_PERFORMANCE_ID")
	private Integer hrsCompactPerformanceId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRelativeImportance() {
		return relativeImportance;
	}

	public void setRelativeImportance(Integer relativeImportance) {
		this.relativeImportance = relativeImportance;
	}

	public Integer getHrsCompactPerformanceId() {
		return hrsCompactPerformanceId;
	}

	public void setHrsCompactPerformanceId(Integer hrsCompactPerformanceId) {
		this.hrsCompactPerformanceId = hrsCompactPerformanceId;
	}
}
