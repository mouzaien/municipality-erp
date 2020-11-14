package com.bkeryah.entities.investment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CONTRACR_INSTALLMENTS")
public class ContractInstallments  {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CONTRACT_NUM")
	private Integer contractNum;
	
	@Column(name = "CONTRACT_ID")
	private Integer contractId;
	
	@Column(name = "CONTRACT_AMOUNT_YEARLY")
	private double contractAmount;
	
	@Column(name = "STATUS")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getContractNum() {
		return contractNum;
	}

	public void setContractNum(Integer contractNum) {
		this.contractNum = contractNum;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}