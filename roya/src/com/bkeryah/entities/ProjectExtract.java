package com.bkeryah.entities;

import javax.annotation.PreDestroy;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PROJECTS_EXTRACTS")
public class ProjectExtract {

	@Id
	@Column(name = "EXTRACT_ID")
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private int id;
	@Column(name = "EXTRACT_NUMBER")
	private String number;
	@Column(name = "EXTRACT_SPENT_TIME_PERCENTAGE")
	private String spentTimePercentage;
	@Column(name = "EXTRACT_FINISH_PERCENTAGE")
	private String finishPercentage;
	@Column(name = "EXTRACT_TO")
	private String to;
	@Column(name = "EXTRACT_CREATED_IN")
	private String createdIn;
	@Column(name = "EXTRACT_CREATED_BY")
	private Integer createdBy;
	@Column(name = "CONTRACT_ID")
	private int contractId;

	@Column(name = "EXTRACT_VALUE")
	private String value;
	@ManyToOne()
	@JoinColumn(name = "CONTRACT_ID", insertable = false, updatable = false)
	private ProjectContract contract;
	
	public ProjectExtract() {
//
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSpentTimePercentage() {
		return spentTimePercentage;
	}

	public void setSpentTimePercentage(String spentTimePercentage) {
		this.spentTimePercentage = spentTimePercentage;
	}

	public String getFinishPercentage() {
		return finishPercentage;
	}

	public void setFinishPercentage(String finishPercentage) {
		this.finishPercentage = finishPercentage;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(String createdIn) {
		this.createdIn = createdIn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	public ProjectContract getContract() {
		return contract;
	}

	public void setContract(ProjectContract contract) {
		this.contract = contract;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}

}
