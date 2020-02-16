package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_NATIONALITY")
public class HRNationality {

																								
	@Id
	@Column(name ="NATIONALITY_CODE")
	private String natCode;										
	
	@Column(name ="NATIONALITY_EN_NAME")
	private String natEnName;												
	
	@Column(name ="NATIONALITY_AR_NAME")
	private String natArName;									
	
	public String getNatCode() {													
		return natCode;																
	}

	public void setNatCode(String natCode) {						
		this.natCode = natCode;														
	}

	public String getNatEnName() {								
		return natEnName;										
	}

	public void setNatEnName(String natEnName) {
		this.natEnName = natEnName;
	}

	public String getNatArName() {
		return natArName;
	}

	public void setNatArName(String natArName) {
		this.natArName = natArName;
	}
}
