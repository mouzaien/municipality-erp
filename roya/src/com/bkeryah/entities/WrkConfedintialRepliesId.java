package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WrkConfedintialRepliesId implements Serializable{

	@Column(name = "ID")
	private Integer id;
	@Column(name = "STEP_ID")
	private Integer stepId;
	@Column(name = "GRANTED")
	private Integer grantedUser;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStepId() {
		return stepId;
	}
	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}
	public Integer getGrantedUser() {
		return grantedUser;
	}
	public void setGrantedUser(Integer grantedUser) {
		this.grantedUser = grantedUser;
	}
	
	
	
}
