package com.bkeryah.entities.investment;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

@Entity
@Table(name = "INV_TENDER")              
public class Tender {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "INVESTOR_ID")
	private Integer investorId;
	@Column(name = "ANNOUN_DETAILS_ID")
	private Integer announcementDetailsId;
	@Column(name = "TENDER_DATE")
	private String tenderDate;
	@Column(name = "TENDER_PRICE")
	private Double tenderPrice;
	/*
	 * 0: open, 1:assigned,2: closed after doing contract
	 * */
	@Column(name = "STATUS")
	private Integer status;
	@ManyToOne
	@JoinColumn(name = "INVESTOR_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private Investor investor;
	@ManyToOne
	@JoinColumn(name = "ANNOUN_DETAILS_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private AnnoucementDetails annoucementDetails;
	@OneToMany(fetch= FetchType.LAZY, mappedBy="tender")
	private List<Contract> contractList;
	@Transient
	private boolean checked;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInvestorId() {
		return investorId;
	}
	public void setInvestorId(Integer investorId) {
		this.investorId = investorId;
	}
	
	public String getTenderDate() {
		return tenderDate;
	}
	public void setTenderDate(String tenderDate) {
		this.tenderDate = tenderDate;
	}
	public Double getTenderPrice() {
		return tenderPrice;
	}
	public void setTenderPrice(Double tenderPrice) {
		this.tenderPrice = tenderPrice;
	}
	public Investor getInvestor() {
		return investor;
	}
	public void setInvestor(Investor investor) {
		this.investor = investor;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAnnouncementDetailsId() {
		return announcementDetailsId;
	}
	public void setAnnouncementDetailsId(Integer announcementDetailsId) {
		this.announcementDetailsId = announcementDetailsId;
	}
	public AnnoucementDetails getAnnoucementDetails() {
		return annoucementDetails;
	}
	public void setAnnoucementDetails(AnnoucementDetails annoucementDetails) {
		this.annoucementDetails = annoucementDetails;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<Contract> getContractList() {
		return contractList;
	}
	public void setContractList(List<Contract> contractList) {
		this.contractList = contractList;
	}
}