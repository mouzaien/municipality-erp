package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_SUBJECT_STATUSES")
public class HRSubjectStatus {

	
	@Id
	@Column(name ="SUBJECT_STATUS_CODE")
	private String subjectCode;
		
	@Column(name ="SUBJECT_STATUS_EN_NAME")
	private String subjectEnName;
		
	@Column(name ="SUBJECT_STATUS_AR_NAME")
	private String subjectArName;

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectEnName() {
		return subjectEnName;
	}

	public void setSubjectEnName(String subjectEnName) {
		this.subjectEnName = subjectEnName;
	}

	public String getSubjectArName() {
		return subjectArName;
	}

	public void setSubjectArName(String subjectArName) {
		this.subjectArName = subjectArName;
	}

}
