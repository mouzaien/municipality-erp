package com.bkeryah.hr.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.bkeryah.entities.HrsGovJob4;

@Entity
@Table(name = "HRS_JOB_CREATION")
public class HrsJobCreation {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer createId;
	@Column(name = "RANKCODE")
	private Integer rankCode;
	@Column(name = "JOBCOD")
	private String jobCode;
	@Column(name = "JOBNO")
	private Integer jobNumber;
	@Column(name = "CATCOD")
	private Integer categoryId;
	@Column(name = "JOBYEAR")
	private Integer jobYear;
	@Column(name = "INCOMNO")
	private Integer incomeNumber;
	@Column(name = "JOBSTS")
	private Integer jobstatus;
	@Column(name = "CBY")
	private Integer createdBy;
	@Column(name = "INCOMEYEAR")
	private Integer incomeYear;
	@Column(name = "EXCYEAR")
	private Integer excutionYear;
	@Column(name = "INCOMESRC")
	private Integer incomesource;
	@Column(name = "DARAR")
	private Integer insteadDamage;
	@Column(name = "PERC_NATURE_FIRST_SAL")
	private Integer naturalFirstBascal;
	@Column(name = "PERC_NATURE_BASCAL")
	private Integer naturalBascal;
	@Column(name = "JOBDATE")
	private String jobDate;
	@Column(name = "DEPTCOD")
	private String depatmentCode;
	@Column(name = "CIN")
	private Date createdInDate;
	@Column(name = "INCOMEDT")
	private String incomedate;
	@Column(name = "EXCSRC")
	private String excutionResource;
	@Column(name = "EXCNO")
	private String excutionNumber;
	@Column(name = "EXCDT")
	private String excutionDate;

	@Column(name = "GIRG_JOB_DATE")
	private Date girgJobDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JOBCOD", referencedColumnName = "ID", insertable = false, updatable = false)
	private HrsGovJob4 gov4;

	 
	

	public HrsJobCreation(HrsJobCreation job) {
		super();
		this.createId = job.getCreateId();
		this.rankCode = job.getRankCode();
		this.jobCode = job.getJobCode();
		this.jobNumber = job.getJobNumber();
		this.categoryId = job.getCategoryId();
		this.jobYear = job.getJobYear();
		this.incomeNumber = job.getIncomeNumber();
		this.jobstatus = job.getJobstatus();
		this.createdBy = job.getCreatedBy();
		this.incomeYear = job.getIncomeYear();
		this.excutionYear = job.getExcutionYear();
		this.incomesource = job.getIncomesource();
		this.insteadDamage = job.getInsteadDamage();
		this.naturalFirstBascal = job.getNaturalFirstBascal();
		this.naturalBascal = job.getNaturalBascal();
		this.jobDate = job.getJobDate();
		this.depatmentCode = job.getDepatmentCode();
		this.createdInDate = job.getCreatedInDate();
		this.incomedate = job.getIncomedate();
		this.excutionResource = job.getExcutionResource();
		this.excutionNumber = job.getExcutionNumber();
		this.excutionDate = job.getExcutionDate();
	}

	public HrsJobCreation() {

	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Integer getRankCode() {
		return rankCode;
	}

	public void setRankCode(Integer rankCode) {
		this.rankCode = rankCode;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public Integer getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(Integer jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getJobYear() {
		return jobYear;
	}

	public void setJobYear(Integer jobYear) {
		this.jobYear = jobYear;
	}

	public Integer getIncomeNumber() {
		return incomeNumber;
	}

	public void setIncomeNumber(Integer incomeNumber) {
		this.incomeNumber = incomeNumber;
	}

	public Integer getJobstatus() {
		return jobstatus;
	}

	public void setJobstatus(Integer jobstatus) {
		this.jobstatus = jobstatus;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getIncomeYear() {
		return incomeYear;
	}

	public void setIncomeYear(Integer incomeYear) {
		this.incomeYear = incomeYear;
	}

	public Integer getExcutionYear() {
		return excutionYear;
	}

	public void setExcutionYear(Integer excutionYear) {
		this.excutionYear = excutionYear;
	}

	public Integer getIncomesource() {
		return incomesource;
	}

	public void setIncomesource(Integer incomesource) {
		this.incomesource = incomesource;
	}

	public Integer getInsteadDamage() {
		return insteadDamage;
	}

	public void setInsteadDamage(Integer insteadDamage) {
		this.insteadDamage = insteadDamage;
	}

	public Integer getNaturalFirstBascal() {
		return naturalFirstBascal;
	}

	public void setNaturalFirstBascal(Integer naturalFirstBascal) {
		this.naturalFirstBascal = naturalFirstBascal;
	}

	public Integer getNaturalBascal() {
		return naturalBascal;
	}

	public void setNaturalBascal(Integer naturalBascal) {
		this.naturalBascal = naturalBascal;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}

	public String getDepatmentCode() {
		return depatmentCode;
	}

	public void setDepatmentCode(String depatmentCode) {
		this.depatmentCode = depatmentCode;
	}

	public Date getCreatedInDate() {
		return createdInDate;
	}

	public void setCreatedInDate(Date createdInDate) {
		this.createdInDate = createdInDate;
	}

	public String getIncomedate() {
		return incomedate;
	}

	public void setIncomedate(String incomedate) {
		this.incomedate = incomedate;
	}

	public String getExcutionResource() {
		return excutionResource;
	}

	public void setExcutionResource(String excutionResource) {
		this.excutionResource = excutionResource;
	}

	public String getExcutionNumber() {
		return excutionNumber;
	}

	public void setExcutionNumber(String excutionNumber) {
		this.excutionNumber = excutionNumber;
	}

	public String getExcutionDate() {
		return excutionDate;
	}

	public void setExcutionDate(String excutionDate) {
		this.excutionDate = excutionDate;
	}

	public HrsGovJob4 getGov4() {
		return gov4;
	}

	public void setGov4(HrsGovJob4 gov4) {
		this.gov4 = gov4;
	}

	public Date getGirgJobDate() {
		return girgJobDate;
	}

	public void setGirgJobDate(Date girgJobDate) {
		this.girgJobDate = girgJobDate;
	}

}
