package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class WrkApplicationId implements Serializable {

	@Column(name = "ID")
	private Integer applicationId;
	@Column(name = "STEP_ID")
	private Integer stepId;

	public WrkApplicationId(Integer applicationId, Integer stepId) {
		

		super();
		this.applicationId = applicationId;
		this.stepId = stepId;
	}
	
	

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public WrkApplicationId() {
	}

//	@Override
//	protected Object clone() throws CloneNotSupportedException {
//		// TODO Auto-generated method stub
//		return super.clone();
//	}
//	
	
}
