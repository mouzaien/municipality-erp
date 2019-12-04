package com.bkeryah.entities;

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
//	@GenericGenerator(name = "generator", strategy = "increment")
//	@GeneratedValue(generator = "generator")
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private ArcUsers arcUser;
	@Column(name = "APP_TYPE")
	private Integer applicationType;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APP_TYPE", referencedColumnName = "ID", insertable = false, updatable = false)
	private ArcApplicationType arcApplicationType;
	@Column(name = "INCOME_NO")
	private String incomeNo;
	@Column(name = "INCOME_H_DATE")
	private String incomeHDate;
	@Column(name = "FILES_NO")
	private Integer filesNo;
	@Column(name = "LETTER_FROM")
	private Integer letterFrom;
	// @ManyToOne(fetch =FetchType.EAGER)
	// @JoinColumn(name="LETTER_FROM" ,referencedColumnName="ID", insertable =
	// false, updatable = false)
	// private WrkLetterFrom wrkLetterFrom ;
	@Column(name = "LETTER_TO")
	private Integer letterTo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LETTER_TO", referencedColumnName = "ID", insertable = false, updatable = false)
	private WrkLetterTo wrkLetterTo;
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
	private Integer attachsType;
	@Transient
	private Integer outcomingNo;
	@Transient
	private boolean importantFlag;
	@Transient
	private boolean outcomingNumFlag;
	// @OneToOne(fetch =FetchType.EAGER)
	// @JoinColumn(name="ID",referencedColumnName="ID",insertable=true,updatable=true)
	// @Cascade(value=CascadeType.ALL)
	// private ArcRecordsExtension arcRecordsExtension;
	// @Column(name = "NATIONAL_ID")
	// private String nationalId;
	// @Column(name = "LETTER_FROM_TYP")
	// private Integer letterFromType;
	// @OneToMany(mappedBy = "arcRecords")
	// Set<ArcRecAtt> arcAttachList;

	// @OneToOne(fetch = FetchType.LAZY, mappedBy = "id.arcRecord",
	// cascade=CascadeType.ALL)
	// private HealthArchiveLicence healthArchiveLicence;
	//
	@OneToOne
	@JoinColumn(name = "ID", referencedColumnName = "APP_ID", insertable = false, updatable = false)
	private WrkComment wrkComment;
	@Transient
	private boolean checked;
	@Transient
	private Integer attachNB;
	@Transient
	private String modified;
	@Transient
	private Integer reciverDeptId;
	@Transient
	private String reciverDeptName;

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

	public ArcUsers getArcUser() {
		return arcUser;
	}

	public void setArcUser(ArcUsers arcUser) {
		this.arcUser = arcUser;
	}

	public Integer getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(Integer applicationType) {
		this.applicationType = applicationType;
	}

	public ArcApplicationType getArcApplicationType() {
		return arcApplicationType;
	}

	public void setArcApplicationType(ArcApplicationType arcApplicationType) {
		this.arcApplicationType = arcApplicationType;
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

	// public WrkLetterFrom getWrkLetterFrom() {
	// return wrkLetterFrom;
	// }
	// public void setWrkLetterFrom(WrkLetterFrom wrkLetterFrom) {
	// this.wrkLetterFrom = wrkLetterFrom;
	// }
	public Integer getLetterTo() {
		return letterTo;
	}

	public void setLetterTo(Integer letterTo) {
		this.letterTo = letterTo;
	}

	public WrkLetterTo getWrkLetterTo() {
		return wrkLetterTo;
	}

	public void setWrkLetterTo(WrkLetterTo wrkLetterTo) {
		this.wrkLetterTo = wrkLetterTo;
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

	// public String getNationalId() {
	// return nationalId;
	// }
	// public void setNationalId(String nationalId) {
	// this.nationalId = nationalId;
	// }
	// public Integer getLetterFromType() {
	// return letterFromType;
	// }
	// public void setLetterFromType(Integer letterFromType) {
	// this.letterFromType = letterFromType;
	// }
	public boolean isImportantFlag() {
		return importantFlag;
	}

	public void setImportantFlag(boolean importantFlag) {
		this.importantFlag = importantFlag;
	}

	// public ArcRecordsExtension getArcRecordsExtension() {
	// return arcRecordsExtension;
	// }
	// public void setArcRecordsExtension(ArcRecordsExtension
	// arcRecordsExtension) {
	// this.arcRecordsExtension = arcRecordsExtension;
	// }
	// public HealthArchiveLicence getHealthArchiveLicence() {
	// return healthArchiveLicence;
	// }
	// public void setHealthArchiveLicence(HealthArchiveLicence
	// healthArchiveLicence) {
	// this.healthArchiveLicence = healthArchiveLicence;
	// }
	public WrkComment getWrkComment() {
		return wrkComment;
	}

	public void setWrkComment(WrkComment wrkComment) {
		this.wrkComment = wrkComment;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getAttachNB() {
		return attachNB;
	}

	public void setAttachNB(Integer attachNB) {
		this.attachNB = attachNB;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public Integer getReciverDeptId() {
		return reciverDeptId;
	}

	public void setReciverDeptId(Integer reciverDeptId) {
		this.reciverDeptId = reciverDeptId;
	}

	public String getReciverDeptName() {
		return reciverDeptName;
	}

	public void setReciverDeptName(String reciverDeptName) {
		this.reciverDeptName = reciverDeptName;
	}

	public Integer getAttachsType() {
		return attachsType;
	}

	public void setAttachsType(Integer attachsType) {
		this.attachsType = attachsType;
	}

	public Integer getOutcomingNo() {
		return outcomingNo;
	}

	public void setOutcomingNo(Integer outcomingNo) {
	this.outcomingNo = outcomingNo;
	}

	public boolean isOutcomingNumFlag() {
		return outcomingNumFlag;
	}

	public void setOutcomingNumFlag(boolean outcomingNumFlag) {
		this.outcomingNumFlag = outcomingNumFlag;
	}

}
