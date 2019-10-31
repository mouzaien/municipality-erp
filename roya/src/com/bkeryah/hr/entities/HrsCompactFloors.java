package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "HRS_COMPACT_FLOORS")
public class HrsCompactFloors {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "TARGET_OUTPUT")
	private Integer targetOutput;
	@Column(name = "EVALUATION")
	private Integer evaluation;
	@Column(name = "HRS_COMPACT_PERFORMANCEID")
	private Integer hrsCompactPerformanceid;
	@Column(name = "FLOORSID")
	private Integer floorsid;
	@Column(name = "HRS_COMPACT_BASEFLOORID")
	private Integer hrsCompactBasefloorid;
	@Transient
	private String floorDescription;
	@Transient
	private String catFloorDesc;

	@Transient
	private Integer catFloorId;

	@Transient
	private Integer relativeImportance;

	public Integer getRelativeImportance() {
		return relativeImportance;
	}

	public void setRelativeImportance(Integer relativeImportance) {
		this.relativeImportance = relativeImportance;
	}

	public Integer getCatFloorId() {
		return catFloorId;
	}

	public void setCatFloorId(Integer catFloorId) {
		this.catFloorId = catFloorId;
	}

	public String getFloorDescription() {
		return floorDescription;
	}

	public void setFloorDescription(String floorDescription) {
		this.floorDescription = floorDescription;
	}

	public String getCatFloorDesc() {
		return catFloorDesc;
	}

	public void setCatFloorDesc(String catFloorDesc) {
		this.catFloorDesc = catFloorDesc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTargetOutput() {
		return targetOutput;
	}

	public void setTargetOutput(Integer targetOutput) {
		this.targetOutput = targetOutput;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	public Integer getHrsCompactPerformanceid() {
		return hrsCompactPerformanceid;
	}

	public void setHrsCompactPerformanceid(Integer hrsCompactPerformanceid) {
		this.hrsCompactPerformanceid = hrsCompactPerformanceid;
	}

	public Integer getFloorsid() {
		return floorsid;
	}

	public void setFloorsid(Integer floorsid) {
		this.floorsid = floorsid;
	}

	public Integer getHrsCompactBasefloorid() {
		return hrsCompactBasefloorid;
	}

	public void setHrsCompactBasefloorid(Integer hrsCompactBasefloorid) {
		this.hrsCompactBasefloorid = hrsCompactBasefloorid;
	}

}
