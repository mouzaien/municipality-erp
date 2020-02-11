package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name ="XXX_HR_POS_ACTION_TYPES")
public class HRPositionAction {

	
	@Id
	@Column(name ="POS_ACTION_CODE")
	private String posCode;
		
	@Column(name ="POS_ACTION_EN_NAME")
	private String posEnName;
		
	@Column(name ="POS_ACTION_AR_NAME")
	private String posArName;

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public String getPosEnName() {
		return posEnName;
	}

	public void setPosEnName(String posEnName) {
		this.posEnName = posEnName;
	}

	public String getPosArName() {
		return posArName;
	}

	public void setPosArName(String posArName) {
		this.posArName = posArName;
	}
}
