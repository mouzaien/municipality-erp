package com.bkeryah.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WHS_ITEMS_APPLICATION")
public class ActualDisbursement {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "RCTNO")
	private Integer id;
	@Column(name = "RCTDATE")
	private String date;
	@Column(name = "SPLNMB")
	private String specialNumber;
	@Column(name = "REQDLV")
	private String department;
	@Column(name = "EMPNO")
	private String recipientEmployee;
	@Column(name = "STRNO")
	private Integer storeId;
	@Column(name = "DOCU_TYP_NO")
	private Integer docuTypNo;
	@Column(name = "MOVE_TYP_NO")
	private Integer moveTypNo;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "YEARID")
	private int yearId;
	@Column(name = "CREATEDBY")
	private String createdby;
	@Column(name = "CREATEDDATE")
	private String createdDate;
	@Column(name = "INV_R_NO")
	private Integer generalrequestNumber;

	@Transient
	private String deptName;
	@Transient
	private String UserName;
	@Transient
	private String storeName;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "actualDisbursement")
	List<ActualDisbursementDetails> actualDisbursementDetails;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "YEARID", insertable = false, updatable = false)
	private FinFinancialYear year;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSpecialNumber() {
		return specialNumber;
	}

	public void setSpecialNumber(String specialNumber) {
		this.specialNumber = specialNumber;
	}

	public String getRecipientEmployee() {
		return recipientEmployee;
	}

	public void setRecipientEmployee(String recipientEmployee) {
		this.recipientEmployee = recipientEmployee;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getDocuTypNo() {
		return docuTypNo;
	}

	public void setDocuTypNo(Integer docuTypNo) {
		this.docuTypNo = docuTypNo;
	}

	public Integer getMoveTypNo() {
		return moveTypNo;
	}

	public void setMoveTypNo(Integer moveTypNo) {
		this.moveTypNo = moveTypNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}


	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getGeneralrequestNumber() {
		return generalrequestNumber;
	}

	public void setGeneralrequestNumber(Integer generalrequestNumber) {
		this.generalrequestNumber = generalrequestNumber;
	}

	public List<ActualDisbursementDetails> getActualDisbursementDetails() {
		return actualDisbursementDetails;
	}

	public void setActualDisbursementDetails(List<ActualDisbursementDetails> actualDisbursementDetails) {
		this.actualDisbursementDetails = actualDisbursementDetails;
	}

	public String getDepartment() {
		return department;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getUserName() {
		return UserName;
	}

	public FinFinancialYear getYear() {
		return year;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public void setYear(FinFinancialYear year) {
		this.year = year;
	}

}
