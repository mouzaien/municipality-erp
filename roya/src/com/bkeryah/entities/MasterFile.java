package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "HRS_Master_File")
public class MasterFile {

	@Id
	@Column(name = "EMPNO")
	private Integer employeNumber;
	@Column(name = "NATNO")
	private Long nationalNumber;
	@Column(name = "CATCOD")
	private Integer cactegoryId;
	@Column(name = "FST_NAME_AR")
	private String firstName;
	@Column(name = "SND_NAME_AR")
	private String secondName;
	@Column(name = "TRD_NAME_AR")
	private String thirdName;
	@Column(name = "FTH_NAME_AR")
	private String forthName;
	@Column(name = "FAPPLDAT")
	private String firstApplicationDate;
	@Column(name = "FSRVDT")
	private String firstServiceDate;
	@Column(name = "JOB_DESC")
	private String jobDescription;
	@Column(name = "BNKCOD")
	private Integer bankId;
	@Formula("(select b.Name from PAY_BANKS b where b.id = BNKCOD)")
	private String bankName;

	public Integer getEmployeNumber() {
		return employeNumber;
	}

	public void setEmployeNumber(Integer employeNumber) {
		this.employeNumber = employeNumber;
	}

	public Long getNationalNumber() {
		return nationalNumber;
	}

	public void setNationalNumber(Long nationalNumber) {
		this.nationalNumber = nationalNumber;
	}

	public Integer getCactegoryId() {
		return cactegoryId;
	}

	public void setCactegoryId(Integer cactegoryId) {
		this.cactegoryId = cactegoryId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getForthName() {
		return forthName;
	}

	public void setForthName(String forthName) {
		this.forthName = forthName;
	}

	public String getFirstApplicationDate() {
		return firstApplicationDate;
	}

	public void setFirstApplicationDate(String firstApplicationDate) {
		this.firstApplicationDate = firstApplicationDate;
	}

	public String getFirstServiceDate() {
		return firstServiceDate;
	}

	public void setFirstServiceDate(String firstServiceDate) {
		this.firstServiceDate = firstServiceDate;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
