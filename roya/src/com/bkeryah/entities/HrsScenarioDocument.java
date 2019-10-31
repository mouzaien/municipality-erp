package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_SCENARIO_DOCUMENT")
public class HrsScenarioDocument implements Comparable<HrsScenarioDocument> {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;
	@Column(name = "MODELID", nullable = false)
	private int modelId;
	@Column(name = "STEP_ID")
	private int stepId;
	@Column(name = "FROM_ID")
	private int fromId;
	@Column(name = "TO_ID")
	private int toId;
	@Column(name = "SIGN_TRUE")
	private int signed;
	@ManyToOne
	@JoinColumn(name = "modelId", referencedColumnName = "MODELID", insertable = false, updatable = false)
	private HrScenario scenario;
	@OneToOne
	@JoinColumn(name="FROM_ID",referencedColumnName="ID",insertable=false,updatable=false)
	private SysProperties fromUser;
	@OneToOne
	@JoinColumn(name="TO_ID",referencedColumnName="ID",insertable=false,updatable=false)
	private SysProperties toUser;

	public HrsScenarioDocument() {
		super();
	}

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

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public int getSigned() {
		return signed;
	}

	public void setSigned(int signed) {
		this.signed = signed;
	}

	public HrScenario getScenario() {
		return scenario;
	}

	public void setScenario(HrScenario scenario) {
		this.scenario = scenario;
	}

	public SysProperties getFromUser() {
		return fromUser;
	}

	public void setFromUser(SysProperties fromUser) {
		this.fromUser = fromUser;
	}

	public SysProperties getToUser() {
		return toUser;
	}

	public void setToUser(SysProperties toUser) {
		this.toUser = toUser;
	}

	@Override
	public int compareTo(HrsScenarioDocument obj) {
		if(this.stepId > obj.getStepId())
			return 1;
		else if(this.stepId < obj.getStepId())
			return -1;
		else
			return 0;
	}

}
