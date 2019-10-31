package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_MEDICAL_CHECK")
public class HrMedicalRequest {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;

	@Column(name = "PLACE_MEDICAL")
	private String hospitalName;

	@Column(name = "DATE_MEDICAL")
	private String requestDate;

	@Column(name = "DEMAND_USER_ID")
	private Integer demandForUserId;

	@Column(name = "CBY")
	private Integer createdBy;

	@Column(name = "CIN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date CreatedIn;
	
	@Column(name = "accept_y_n")
	private String acceptStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public Integer getDemandForUserId() {
		return demandForUserId;
	}

	public void setDemandForUserId(Integer demandForUserId) {
		this.demandForUserId = demandForUserId;
	}

	public Date getCreatedIn() {
		return CreatedIn;
	}

	public void setCreatedIn(Date createdIn) {
		CreatedIn = createdIn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

}
