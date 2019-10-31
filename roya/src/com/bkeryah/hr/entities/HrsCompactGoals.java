package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "HRS_COMPACT_GOALS")
public class HrsCompactGoals {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "CALC_UNIT")
	private String calcUnit;
	@Column(name = "RELATIVE_IMPORTANCE")
	private Integer relativeImportance;
	@Column(name = "TARGET_OUTPUT")
	private Integer targetOutput = 0;
	@Column(name = "HRS_COMPACT_PERFORMID")
	private Integer hrsCompactPerformid;
	// @Column(name = "HRS_COMPACT_BASEFLOORID")
	// private Integer hrsCompactBasefloorid;
	@Column(name = "REAL_RESULT")
	private Integer realResult = 0;
	@Column(name = "EVALUATION")
	private Integer evaluation;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCalcUnit() {
		return calcUnit;
	}

	public void setCalcUnit(String calcUnit) {
		this.calcUnit = calcUnit;
	}

	public Integer getRelativeImportance() {
		return relativeImportance;
	}

	public void setRelativeImportance(Integer relativeImportance) {
		this.relativeImportance = relativeImportance;
	}

	public Integer getTargetOutput() {
		return targetOutput;
	}

	public void setTargetOutput(Integer targetOutput) {
		this.targetOutput = targetOutput;
	}

	public Integer getHrsCompactPerformid() {
		return hrsCompactPerformid;
	}

	public void setHrsCompactPerformid(Integer hrsCompactPerformid) {
		this.hrsCompactPerformid = hrsCompactPerformid;
	}

	// public Integer getHrsCompactBasefloorid() {
	// return hrsCompactBasefloorid;
	// }
	//
	// public void setHrsCompactBasefloorid(Integer hrsCompactBasefloorid) {
	// this.hrsCompactBasefloorid = hrsCompactBasefloorid;
	// }

	public Integer getRealResult() {
		return realResult;
	}

	public void setRealResult(Integer realResult) {
		this.realResult = realResult;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

}
