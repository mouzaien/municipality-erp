package com.bkeryah.hr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="XXX_HR_TRAIN_CAT_03")
public class HrTrainCat03 {

	@Id
	@Column(name ="CENTER_AR_NAME")
	private String cenArName;	
	
	@Column(name ="CENTER_EN_NAME")
	private String CenEnName;										
	
	@Column(name ="LOCATION_AR_NAME")
	private String loc;

	public String getCenArName() {
		return cenArName;
	}

	public void setCenArName(String cenArName) {
		this.cenArName = cenArName;
	}

	public String getCenEnName() {
		return CenEnName;
	}

	public void setCenEnName(String cenEnName) {
		CenEnName = cenEnName;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}												
	
	
}
