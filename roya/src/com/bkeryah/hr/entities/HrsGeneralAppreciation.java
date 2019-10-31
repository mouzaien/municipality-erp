package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "HRS_GENERAL_APPRECIATION")
public class HrsGeneralAppreciation {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "RESULT")
	private Double result;
	@Column(name = "DATE_START")
	private String dateStart;
	@Column(name = "DATE_FINISH")
	private String dateFinish;
	@Column(name = "NOTE")
	private String note;
	@Column(name = "RESULT_GOALS")
	private Double resultGoals;
	@Column(name = "RESULT_FLOORS")
	private Double resultFloors;
	@Column(name = "HRS_COMPACT_PERFORMID")
	private Integer hrsCompactPerformanceid;
	@Column(name = "HRS_COMPACT_RATINGID")
	private Integer hrsCompactRatingId;
	
	@Column(name = "JUSTIFICATIONS")
	private String justifications;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(String dateFinish) {
		this.dateFinish = dateFinish;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getResultGoals() {
		return resultGoals;
	}

	public void setResultGoals(Double resultGoals) {
		this.resultGoals = resultGoals;
	}

	public Double getResultFloors() {
		return resultFloors;
	}

	public void setResultFloors(Double resultFloors) {
		this.resultFloors = resultFloors;
	}

	public Integer getHrsCompactPerformanceid() {
		return hrsCompactPerformanceid;
	}

	public void setHrsCompactPerformanceid(Integer hrsCompactPerformanceid) {
		this.hrsCompactPerformanceid = hrsCompactPerformanceid;
	}

	public String getJustifications() {
		return justifications;
	}

	public void setJustifications(String justifications) {
		this.justifications = justifications;
	}

	public Integer getHrsCompactRatingId() {
		return hrsCompactRatingId;
	}

	public void setHrsCompactRatingId(Integer hrsCompactRatingId) {
		this.hrsCompactRatingId = hrsCompactRatingId;
	}

	
	
	
}
