package com.bkeryah.entities.investment;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "INVESTOR")
public class Investor {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "TRADE_RECORD")
	private Long tradeRecord;
	@Column(name = "NAME")
	private String name;
	@Column(name = "REGION")
	private String region;
	@Column(name = "HIGRI_CREATE_DATE")
	private String higriCreateDate;
	@Column(name = "M_CREATE_DATE")
	private Date createDate;
	@Column(name = "ACTIVITY_TYPE")
	private String activityType;
	@Column(name = "PHONE")
	private String phone;
	@Column(name = "FAX")
	private String fax;
	@Column(name = "MOBILE")
	private String mobile;
	@Column(name = "POSTAL_CODE")
	private String postalCode;
	@Column(name = "CODE")
	private String code;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "INVESTOR_TYPE")
	private Integer investorType;
	@Column(name = "INVESTOR_ID_TYPE")
	private Integer investorIdType;
	@Column(name = "INVESTOR_STATUS")
	private Integer investorStatus;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Tender> tenderList;
	// @OneToMany(fetch= FetchType.LAZY)
	// private List<Contract> contractList ;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "investor")
	private Set<ContractDirect> contractDirectSet;

	@Transient
	private Date grigCreateDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getTradeRecord() {
		return tradeRecord;
	}

	public void setTradeRecord(Long tradeRecord) {
		this.tradeRecord = tradeRecord;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getHigriCreateDate() {
		return higriCreateDate;
	}

	public void setHigriCreateDate(String higriCreateDate) {
		this.higriCreateDate = higriCreateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public List<Tender> getTenderList() {
		return tenderList;
	}

	public void setTenderList(List<Tender> tenderList) {
		this.tenderList = tenderList;
	}

	public Set<ContractDirect> getContractDirectSet() {
		return contractDirectSet;
	}

	public void setContractDirectSet(Set<ContractDirect> contractDirectSet) {
		this.contractDirectSet = contractDirectSet;
	}

	public Integer getInvestorType() {
		return investorType;
	}

	public void setInvestorType(Integer investorType) {
		this.investorType = investorType;
	}

	public Integer getInvestorIdType() {
		return investorIdType;
	}

	public void setInvestorIdType(Integer investorIdType) {
		this.investorIdType = investorIdType;
	}

	public Integer getInvestorStatus() {
		return investorStatus;
	}

	public void setInvestorStatus(Integer investorStatus) {
		this.investorStatus = investorStatus;
	}

	public Date getGrigCreateDate() {
		return grigCreateDate;
	}

	public void setGrigCreateDate(Date grigCreateDate) {
		this.grigCreateDate = grigCreateDate;
	}

	// public List<Contract> getContractList() {
	// return contractList;
	// }
	//
	// public void setContractList(List<Contract> contractList) {
	// this.contractList = contractList;
	// }

}