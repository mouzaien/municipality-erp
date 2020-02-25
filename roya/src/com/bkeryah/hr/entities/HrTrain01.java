package com.bkeryah.hr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_TRAIN_CAT_01")
public class HrTrain01 {
	@Id
	@Column(name ="COURSE_CODE")
	private String courseCode;	
	
	@Column(name ="MAIN_CATEGORY")
	private String mainCat;										
	
	@Column(name ="SUB_CATEGORY1")
	private String subCat1;												
	
	@Column(name ="SUB_CATEGORY2")
	private String subCat2;									
	
	@Column(name ="COURSE_TYPE")
	private String CourseType;										
	
	@Column(name ="COURSE_NAME")
	private String courseName;												
	
	@Column(name ="COURSE_DESCRIPTION")
	private String courseDesc;										
	
	@Column(name ="COURSE_OBJECTIVES")
	private String courseObj;												
	
	@Column(name ="SUCCESS_CRITERION")
	private String succesCrit;	
	
	@Column(name ="AUDIENCE")
	private String audience;	
	
	@Column(name ="DURATION")
	private Integer duration;	
	
	@Column(name ="DURATION_UNIT")
	private String durationUnt;

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getMainCat() {
		return mainCat;
	}

	public void setMainCat(String mainCat) {
		this.mainCat = mainCat;
	}

	public String getSubCat1() {
		return subCat1;
	}

	public void setSubCat1(String subCat1) {
		this.subCat1 = subCat1;
	}

	public String getSubCat2() {
		return subCat2;
	}

	public void setSubCat2(String subCat2) {
		this.subCat2 = subCat2;
	}

	public String getCourseType() {
		return CourseType;
	}

	public void setCourseType(String courseType) {
		CourseType = courseType;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDesc() {
		return courseDesc;
	}

	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}

	public String getCourseObj() {
		return courseObj;
	}

	public void setCourseObj(String courseObj) {
		this.courseObj = courseObj;
	}

	public String getSuccesCrit() {
		return succesCrit;
	}

	public void setSuccesCrit(String succesCrit) {
		this.succesCrit = succesCrit;
	}

	public String getAudience() {
		return audience;
	}

	public void setAudience(String audience) {
		this.audience = audience;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getDurationUnt() {
		return durationUnt;
	}

	public void setDurationUnt(String durationUnt) {
		this.durationUnt = durationUnt;
	}	
	

}
