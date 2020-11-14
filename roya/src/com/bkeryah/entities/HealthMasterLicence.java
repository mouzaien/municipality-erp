package com.bkeryah.entities;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LIC_MASTER_HELTH")
public class HealthMasterLicence {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "APPL_ID", nullable = true)
	private Integer applicationId;
	@Column(name = "APLL_TYPE", nullable = true)
	private Integer applicationType;
	@Column(name = "APPL_DATE", nullable = true)
	private String applicationHDate;
	@Column(name = "APLL_OWNER", nullable = true)
	private String applicationOwner;
	@Column(name = "ST_ID", nullable = true)
	private Integer medicalCenterId;
	@Column(name = "RESULT", nullable = true)
	private Integer checkResult;
	@Column(name = "RSL_BGN_DATE", nullable = true)
	private String resultStartDate;
	@Column(name = "RSL_END_DATE", nullable = true)
	private String resultEndDate;
	@Column(name = "NOTE", nullable = true)
	private String notes;
	@Column(name = "JOB_ID", nullable = true)
	private Integer jobId;
	@Column(name = "DEPEND1", nullable = true)
	private Integer markedBy;
	@Column(name = "DEPEND3", nullable = true)
	private Integer signedBy;
	@Column(name = "C_BY", nullable = true)
	private Integer createdBy;
	@Column(name = "TEFOD_DATE", nullable = true)
	private String tefodDate;
	@Column(name = "HOMMA_DATE", nullable = true)
	private String hommaDate;
	@Column(name = "LIC_ID", nullable = true)
	private String previousRequestApplicationId;
	@Column(name = "IS_PRINTED", nullable = true)
	private Integer isPrinted;
	@Column(name = "PRINTED_DATE", nullable = true)
	private String printedIn;
	@Column(name = "PRINTED_By", nullable = true)
	private String printedBy;
	@Column(name = "TRD_NAME", nullable = true)
	private String trdLicenceName;
	@Column(name = "LIC_ADDRESS", nullable = true)
	private String trdLicenceAddress;
	@Column(name = "K_NAME", nullable = true)
	private String kafeelName;
	@Column(name = "DATE_DEPEND", nullable = true)
	private String issueDate;
	@Column(name = "LIC_NO", nullable = true)
	private Integer licenceId;
	@Column(name = "K_ADRS", nullable = true)
	private String kafeelAddress;
	@Column(name = "K_NATNO", nullable = true)
	private String kafeelNationalId;
	@Column(name = "ID", nullable = true)
	private Integer id;
	@Column(name = "V_TYP", nullable = true)
	private Integer V_Typ;
	@Column(name = "JH_ID", nullable = true)
	private Integer JH_ID;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "id.healthMasterLicence", cascade = CascadeType.ALL)
	private HealthArchiveLicence healthArchiveLicence;
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "APLL_OWNER", referencedColumnName = "NAT_NO", insertable = false, updatable = false)
	private ArcPeople arcPeople;
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "JOB_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private HealthLicenceJob job;
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "ST_ID", referencedColumnName = "ST_ID", insertable = false, updatable = false)
	private HealthLicenceCenter healthCenter;
	@Column(name = "K_PHONE_NUMBER", nullable = true)
	private String kafeelPhoneNumber;
	@Column(name = "APPL_OWNER_NAME", nullable = true)
	private String appOwnerName;
	@Column(name = "GENDER", nullable = true)
	private Integer gender;
	@Column(name = "K_APLL_NUMBER", nullable = true)
	private String kafeelApllNumber;
	@Column(name = "STATUS_Y_N", nullable = true)
	private String status;
	@Column(name = "OWNER_IMAGE", nullable = true)
	private byte[] ownerImage;
	@Transient
	private Integer apllOwnerSource;

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public Integer getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(Integer applicationType) {
		this.applicationType = applicationType;
	}

	public String getApplicationHDate() {
		return applicationHDate;
	}

	public void setApplicationHDate(String applicationHDate) {
		this.applicationHDate = applicationHDate;
	}

	public String getApplicationOwner() {
		return applicationOwner;
	}

	public void setApplicationOwner(String applicationOwner) {
		this.applicationOwner = applicationOwner;
	}

	public Integer getMedicalCenterId() {
		return medicalCenterId;
	}

	public void setMedicalCenterId(Integer medicalCenterId) {
		this.medicalCenterId = medicalCenterId;
	}

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}

	public String getResultStartDate() {
		return resultStartDate;
	}

	public void setResultStartDate(String resultStartDate) {
		this.resultStartDate = resultStartDate;
	}

	public String getResultEndDate() {
		return resultEndDate;
	}

	public void setResultEndDate(String resultEndDate) {
		this.resultEndDate = resultEndDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public Integer getMarkedBy() {
		return markedBy;
	}

	public void setMarkedBy(Integer markedBy) {
		this.markedBy = markedBy;
	}

	public Integer getSignedBy() {
		return signedBy;
	}

	public void setSignedBy(Integer signedBy) {
		this.signedBy = signedBy;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getTefodDate() {
		return tefodDate;
	}

	public void setTefodDate(String tefodDate) {
		this.tefodDate = tefodDate;
	}

	public String getHommaDate() {
		return hommaDate;
	}

	public void setHommaDate(String hommaDate) {
		this.hommaDate = hommaDate;
	}

	public String getPreviousRequestApplicationId() {
		return previousRequestApplicationId;
	}

	public void setPreviousRequestApplicationId(String previousRequestApplicationId) {
		this.previousRequestApplicationId = previousRequestApplicationId;
	}

	public Integer getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(Integer isPrinted) {
		this.isPrinted = isPrinted;
	}

	public String getPrintedIn() {
		return printedIn;
	}

	public void setPrintedIn(String printedIn) {
		this.printedIn = printedIn;
	}

	public String getPrintedBy() {
		return printedBy;
	}

	public void setPrintedBy(String printedBy) {
		this.printedBy = printedBy;
	}

	public String getTrdLicenceName() {
		return trdLicenceName;
	}

	public void setTrdLicenceName(String trdLicenceName) {
		this.trdLicenceName = trdLicenceName;
	}

	public String getTrdLicenceAddress() {
		return trdLicenceAddress;
	}

	public void setTrdLicenceAddress(String trdLicenceAddress) {
		this.trdLicenceAddress = trdLicenceAddress;
	}

	public String getKafeelName() {
		return kafeelName;
	}

	public void setKafeelName(String kafeelName) {
		this.kafeelName = kafeelName;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public Integer getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(Integer licenceId) {
		this.licenceId = licenceId;
	}

	public String getKafeelAddress() {
		return kafeelAddress;
	}

	public void setKafeelAddress(String kafeelAddress) {
		this.kafeelAddress = kafeelAddress;
	}

	public String getKafeelNationalId() {
		return kafeelNationalId;
	}

	public void setKafeelNationalId(String kafeelNationalId) {
		this.kafeelNationalId = kafeelNationalId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getV_Typ() {
		return V_Typ;
	}

	public void setV_Typ(Integer v_Typ) {
		V_Typ = v_Typ;
	}

	public Integer getJH_ID() {
		return JH_ID;
	}

	public void setJH_ID(Integer jH_ID) {
		JH_ID = jH_ID;
	}

	public HealthArchiveLicence getHealthArchiveLicence() {
		return healthArchiveLicence;
	}

	public void setHealthArchiveLicence(HealthArchiveLicence healthArchiveLicence) {
		this.healthArchiveLicence = healthArchiveLicence;
	}

	public ArcPeople getArcPeople() {
		return arcPeople;
	}

	public void setArcPeople(ArcPeople arcPeople) {
		this.arcPeople = arcPeople;
	}

	public HealthLicenceJob getJob() {
		return job;
	}

	public void setJob(HealthLicenceJob job) {
		this.job = job;
	}

	// public HealthLicenceCenter getHealthCenter() {
	// return healthCenter;
	// }
	//
	// public void setHealthCenter(HealthLicenceCenter healthCenter) {
	// this.healthCenter = healthCenter;
	// }

	public String getKafeelPhoneNumber() {
		return kafeelPhoneNumber;
	}

	public void setKafeelPhoneNumber(String kafeelPhoneNumber) {
		this.kafeelPhoneNumber = kafeelPhoneNumber;
	}

	public String getAppOwnerName() {
		return appOwnerName;
	}

	public void setAppOwnerName(String appOwnerName) {
		this.appOwnerName = appOwnerName;
	}

	public Integer getApllOwnerSource() {
		return apllOwnerSource;
	}

	public void setApllOwnerSource(Integer apllOwnerSource) {
		this.apllOwnerSource = apllOwnerSource;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getKafeelApllNumber() {
		return kafeelApllNumber;
	}

	public void setKafeelApllNumber(String kafeelApllNumber) {
		this.kafeelApllNumber = kafeelApllNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public byte[] getOwnerImage() {
		return ownerImage;
	}

	public void setOwnerImage(byte[] ownerImage) {
		this.ownerImage = ownerImage;
	}

}
