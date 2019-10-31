package com.bkeryah.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import utilities.MyConstants;

@Entity
@Table(name = "TDR_PROCUREMENT")
public class Procurement {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CRAETED_H_DATE")
	private String createdHDate;
	@Column(name = "CREATE_G_DATE")
	private Date createGDate;
	@Column(name = "CREATED_BY")
	private Integer createdBy;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "ACCEPTED_Y_N")
	private String acceptedYN = MyConstants.NO;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "procurement")
	private List<ProcurementDetails> procurementDetailsList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreatedHDate() {
		return createdHDate;
	}

	public void setCreatedHDate(String createdHDate) {
		this.createdHDate = createdHDate;
	}

	public Date getCreateGDate() {
		return createGDate;
	}

	public void setCreateGDate(Date createGDate) {
		this.createGDate = createGDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAcceptedYN() {
		return acceptedYN;
	}

	public void setAcceptedYN(String acceptedYN) {
		this.acceptedYN = acceptedYN;
	}

	public List<ProcurementDetails> getProcurementDetailsList() {
		return procurementDetailsList;
	}

	public void setProcurementDetailsList(List<ProcurementDetails> procurementDetailsList) {
		this.procurementDetailsList = procurementDetailsList;
	}
}