package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="HRS_SCENARIO_DOCUMENT")
public class DocumentScenario {

	@Id
	@Column(name="ID")
	 int id;
	@Column(name="MODELID")
	 int modelId;
	@Column(name="STEP_ID")
	 int stepId;
	@Column(name="FROM_ID")
	 int fromId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name="FROM_ID")
	private SysProperties fromValue;
	@Column(name="TO_ID")
	private int toId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name="TO_ID")
	private SysProperties toValue;
	@Column(name="SIGN_TRUE")
	private int signTrue;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public int getStepId() {
		return stepId;
	}
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	public int getFromId() {
		return fromId;
	}
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}
	public SysProperties getFromValue() {
		return fromValue;
	}
	public void setFromValue(SysProperties fromValue) {
		this.fromValue = fromValue;
	}
	public int getToId() {
		return toId;
	}
	public void setToId(int toId) {
		this.toId = toId;
	}
	public SysProperties getToValue() {
		return toValue;
	}
	public void setToValue(SysProperties toValue) {
		this.toValue = toValue;
	}
	public int getSignTrue() {
		return signTrue;
	}
	public void setSignTrue(int signTrue) {
		this.signTrue = signTrue;
	}
	
	
	
	
	
	
	
}
