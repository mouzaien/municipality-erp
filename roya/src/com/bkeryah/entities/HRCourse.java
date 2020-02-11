package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_COURSE_TYPE")
public class HRCourse {

	

	@Id
	@Column(name ="COURSE_TYPE_CODE")
	private String courseCode;
		
	@Column(name ="COURSE_TYPE_EN_NAME")
	private String courseEnName;
		
	@Column(name ="COURSE_TYPE_AR_NAME")
	private String courseArName;

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseEnName() {
		return courseEnName;
	}

	public void setCourseEnName(String courseEnName) {
		this.courseEnName = courseEnName;
	}

	public String getCourseArName() {
		return courseArName;
	}

	public void setCourseArName(String courseArName) {
		this.courseArName = courseArName;
	}
}
