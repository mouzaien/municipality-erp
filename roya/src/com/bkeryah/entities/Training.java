package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="training")
public class Training {
	@Id
	@Column(name="CLASS_NAME")
	private String className;
	@Column(name="COURSE_NAME")
	private String courseName;
	@Column(name="OFFERIN_LANGUAGE")
	private String offerInLang;
	@Column(name="CLASS_START_DATE")
	private String classStDate;
	@Column(name="CLASS_END_DATE")
	private String classEndDate;
	@Column(name="DURATION")
	private Integer duration;
	@Column(name="DURATION_UNIT")
	private String durationUnit;
	@Column(name="CENTER_AR_NAME")
	private String centArName;
	@Column(name="LOCATION_AR_NAME")
	private String locArName;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getOfferInLang() {
		return offerInLang;
	}
	public void setOfferInLang(String offerInLang) {
		this.offerInLang = offerInLang;
	}
	public String getClassStDate() {
		return classStDate;
	}
	public void setClassStDate(String classStDate) {
		this.classStDate = classStDate;
	}
	public String getClassEndDate() {
		return classEndDate;
	}
	public void setClassEndDate(String classEndDate) {
		this.classEndDate = classEndDate;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getDurationUnit() {
		return durationUnit;
	}
	public void setDurationUnit(String durationUnit) {
		this.durationUnit = durationUnit;
	}
	public String getCentArName() {
		return centArName;
	}
	public void setCentArName(String centArName) {
		this.centArName = centArName;
	}
	public String getLocArName() {
		return locArName;
	}
	public void setLocArName(String locArName) {
		this.locArName = locArName;
	}
	

}
