package com.bkeryah.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="HRS_SCENARIO")
public class HrScenario {

	@Id
	@Column(name = "MODELID")
	private int modelId;
	@Column(name = "STEPS")
	private Integer stepsCount;
	@OneToMany(mappedBy = "scenario", fetch=FetchType.EAGER)
	private List<HrsScenarioDocument> documents;
	@Transient
	private String scenarioLabel;
	
	public HrScenario() {
		
	}

	public HrScenario(int modelId, String scenarioLabel, Integer stepsCount) {
		super();
		this.modelId = modelId;
		this.scenarioLabel = scenarioLabel;
		this.stepsCount = stepsCount;
	}

	public String getScenarioLabel() {
		return scenarioLabel;
	}

	public void setScenarioLabel(String scenarioLabel) {
		this.scenarioLabel = scenarioLabel;
	}

	public Integer getStepsCount() {
		return stepsCount;
	}

	public void setStepsCount(Integer stepsCount) {
		this.stepsCount = stepsCount;
	}

	public List<HrsScenarioDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<HrsScenarioDocument> documents) {
		this.documents = documents;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
}
