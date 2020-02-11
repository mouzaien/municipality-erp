package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_MAR_STATUS")
public class HRMarStatus {

	
	@Id
	@Column(name ="MAR_STATUS_CODE")
	private String statusCode;
		
	@Column(name ="MAR_STATUS_EN_NAME")
	private String statusEnName;
		
	@Column(name ="MAR_STATUS_AR_NAME")
	private String statusArName;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusEnName() {
		return statusEnName;
	}

	public void setStatusEnName(String statusEnName) {
		this.statusEnName = statusEnName;
	}

	public String getStatusArName() {
		return statusArName;
	}

	public void setStatusArName(String statusArName) {
		this.statusArName = statusArName;
	}
}
