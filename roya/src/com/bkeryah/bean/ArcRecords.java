package com.bkeryah.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ARC_RECORDS")
public class ArcRecords {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "REC_ID")
	private String recId;
	@Temporal(TemporalType.DATE)
	@Column(name = "REC_G_DATE")
	private Date recGDate;
	@Column(name = "REC_H_DATE")
	private String recHDate;
	@Column(name = "REC_TITLE")
	private String recTitle;
	@Column(name = "STRUCT_ID")
	private Integer structId;
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_IN")
	private Date createdIn;
	@Column(name = "CREATED_BY")
	private Integer createdBy;
	@Column(name = "APP_TYPE")
	private Integer applicationType;
	@Column(name = "INCOME_NO")
	private String incomeNo;
	@Column(name = "INCOME_H_DATE")
	private String incomeHDate;
	@Column(name = "FILES_NO")
	private Integer filesNo;
	@Column(name = "LETTER_FROM")
	private Integer letterFrom;
	@Column(name = "LETTER_TO")
	private Integer letterTo;
	@Column(name = "LETTER_FROM_NO")
	private String letterFromNo;
	@Column(name = "LETTER_FROM_DATE")
	private String letterFromDate;
	@Column(name = "PPL_ID")
	private Long mobileNumber;
	@Column(name = "IS_IMPORTANT")
	private Integer recordIsImportant;
	@Column(name = "INCOME_YEAR")
	private Integer incomeYear;
	@Transient
	private boolean importantFlag;
	@Transient
	private boolean incomeNumFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public Date getRecGDate() {
		return recGDate;
	}

	public void setRecGDate(Date recGDate) {
		this.recGDate = recGDate;
	}

	public String getRecHDate() {
		return recHDate;
	}

	public void setRecHDate(String recHDate) {
		this.recHDate = recHDate;
	}

	public String getRecTitle() {
		return recTitle;
	}

	public void setRecTitle(String recTitle) {
		this.recTitle = recTitle;
	}

	public Integer getStructId() {
		return structId;
	}

	public void setStructId(Integer structId) {
		this.structId = structId;
	}

	public Date getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(Integer applicationType) {
		this.applicationType = applicationType;
	}

	public String getIncomeNo() {
		return incomeNo;
	}

	public void setIncomeNo(String incomeNo) {
		this.incomeNo = incomeNo;
	}

	public String getIncomeHDate() {
		return incomeHDate;
	}

	public void setIncomeHDate(String incomeHDate) {
		this.incomeHDate = incomeHDate;
	}

	public Integer getFilesNo() {
		return filesNo;
	}

	public void setFilesNo(Integer filesNo) {
		this.filesNo = filesNo;
	}

	public Integer getLetterFrom() {
		return letterFrom;
	}

	public void setLetterFrom(Integer letterFrom) {
		this.letterFrom = letterFrom;
	}

	public Integer getLetterTo() {
		return letterTo;
	}

	public void setLetterTo(Integer letterTo) {
		this.letterTo = letterTo;
	}

	public String getLetterFromNo() {
		return letterFromNo;
	}

	public void setLetterFromNo(String letterFromNo) {
		this.letterFromNo = letterFromNo;
	}

	public String getLetterFromDate() {
		return letterFromDate;
	}

	public void setLetterFromDate(String letterFromDate) {
		this.letterFromDate = letterFromDate;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Integer getRecordIsImportant() {
		return recordIsImportant;
	}

	public void setRecordIsImportant(Integer recordIsImportant) {
		this.recordIsImportant = recordIsImportant;
	}

	public Integer getIncomeYear() {
		return incomeYear;
	}

	public void setIncomeYear(Integer incomeYear) {
		this.incomeYear = incomeYear;
	}

	public boolean isIncomeNumFlag() {
		return incomeNumFlag;
	}

	public void setIncomeNumFlag(boolean incomeNumFlag) {
		this.incomeNumFlag = incomeNumFlag;
	}

	public boolean isImportantFlag() {
		return importantFlag;
	}

	public void setImportantFlag(boolean importantFlag) {
		this.importantFlag = importantFlag;
	}

}
