package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_EMP_CAT")
public class HREmpCat {
	
	@Id
	@Column(name ="EMP_CAT_CODE")
	private String empCatCode;
		
	@Column(name ="EMP_CAT_EN_NAME")
	private String empCatEnName;
		
	@Column(name ="EMP_CAT_AR_NAME")
	private String empCatArName;

	public String getEmpCatCode() {
		return empCatCode;
	}

	public void setEmpCatCode(String empCatCode) {
		this.empCatCode = empCatCode;
	}

	public String getEmpCatEnName() {
		return empCatEnName;
	}

	public void setEmpCatEnName(String empCatEnName) {
		this.empCatEnName = empCatEnName;
	}

	public String getEmpCatArName() {
		return empCatArName;
	}

	public void setEmpCatArName(String empCatArName) {
		this.empCatArName = empCatArName;
	}

}
