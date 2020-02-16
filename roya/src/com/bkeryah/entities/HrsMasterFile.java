package com.bkeryah.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import utilities.Utils;

@Entity
@Table(name = "HRS_Master_File")
public class HrsMasterFile {

	@Id
	@Column(name = "EMPNO")
	private Integer employeNumber;
	@Column(name = "NATNO")
	private Long nationalNumber;
	@Column(name = "CATCOD")
	private Integer cactegoryId;
	@Column(name = "DPTCODE")
	private Integer dptCode;
	@Column(name = "FST_NAME_AR")
	private String firstName;
	@Column(name = "SND_NAME_AR")
	private String secondName;
	@Column(name = "TRD_NAME_AR")
	private String thirdName;
	@Column(name = "FTH_NAME_AR")
	private String forthName;
	@Column(name = "BDAY_H")
	private String birthDateHij;
	@Column(name = "BDAY_G")
	private String birthDateGrig;

	@Column(name = "RLGN")
	private Integer employeReligion;
	@Column(name = "FAPPLDAT")
	private String firstApplicationDate;
	@Column(name = "FSRVDT")
	private String firstServiceDate;
	@Column(name = "sex")
	private String sex;

	@Column(name = "CIN")
	private Date createdDate;
	@Column(name = "CBY")
	private Integer createdBy;
	@Column(name = "EMPSTS")
	private Integer employerStatus;

	@Column(name = "ACCNO")
	private String accountNumber;

	@Column(name = "INSURANCENO")
	private String insuranceNumber;

	@Column(name = "MSTATE")
	private String socialityState;
	@Column(name = "MOB_TEL")
	private String mobileNumber;

	@Column(name = "BPLACE")
	private Integer birthPlace;

	@Column(name = "CHILDS")
	private Integer childsNumber;

	@Column(name = "NATCOD")
	private Integer nationality;
	@Column(name = "GRADPLACE")
	private Integer graduteplace;
	@Column(name = "GRADMAGOR")
	private Integer specialization;
	@Column(name = "GRADDRGREE")
	private Integer qualification;

	@Column(name = "WORK_TEL")
	private String workPhone;
	@Column(name = "HOME_TEL")
	private String homePhone;
	@Column(name = "JOB_DESC")
	private String jobDescription;
	@Column(name = "JOB_NATURE_Y_N")
	private String jobNature;
	@Column(name = "TRANS_Y_N")
	private String transfer;
	@Column(name = "BNKCOD")
	private Integer bankId;
	@Column(name = "SAL_STP")
	private Integer salaryStop;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BNKCOD", referencedColumnName = "ID", insertable = false, updatable = false)
	private PayBank bank;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "employer")
	private HrsEmpTerminate empTerminate;

	@Transient
	private Date birthDate;
	@Transient
	private Date firstApplicationGrigDate;
	@Transient
	private Date firstServiceGrigDate;
	@Transient
	private String firstApplicationDateStringSort;
	@Transient
	private String firstServiceDateStringSort;

	@Transient
	private String fullName;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

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

	public Integer getDptCode() {
		return dptCode;
	}

	public void setDptCode(Integer dptCode) {
		this.dptCode = dptCode;
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

	public String getBirthDateHij() {
		return birthDateHij;
	}

	public void setBirthDateHij(String birthDateHij) {
		this.birthDateHij = birthDateHij;
	}

	public String getBirthDateGrig() {
		return birthDateGrig;
	}

	public void setBirthDateGrig(String birthDateGrig) {
		this.birthDateGrig = birthDateGrig;
	}

	public Integer getEmployerStatus() {
		return employerStatus;
	}

	public void setEmployerStatus(Integer employerStatus) {
		this.employerStatus = employerStatus;
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

	public PayBank getBank() {
		return bank;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public void setBank(PayBank bank) {
		this.bank = bank;
	}

	public Integer getEmployeReligion() {
		return employeReligion;
	}

	public void setEmployeReligion(Integer employeReligion) {
		this.employeReligion = employeReligion;
	}

	public Integer getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(Integer birthPlace) {
		this.birthPlace = birthPlace;
	}

	public Integer getNationality() {
		return nationality;
	}

	public void setNationality(Integer nationality) {
		this.nationality = nationality;
	}

	public Integer getGraduteplace() {
		return graduteplace;
	}

	public void setGraduteplace(Integer graduteplace) {
		this.graduteplace = graduteplace;
	}

	public Integer getSpecialization() {
		return specialization;
	}

	public void setSpecialization(Integer specialization) {
		this.specialization = specialization;
	}

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public Integer getChildsNumber() {
		return childsNumber;
	}

	public void setChildsNumber(Integer childsNumber) {
		this.childsNumber = childsNumber;
	}

	public String getSocialityState() {
		return socialityState;
	}

	public void setSocialityState(String socialityState) {
		this.socialityState = socialityState;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getJobNature() {
		return jobNature;
	}

	public void setJobNature(String jobNature) {
		this.jobNature = jobNature;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getSalaryStop() {
		return salaryStop;
	}

	public void setSalaryStop(Integer salaryStop) {
		this.salaryStop = salaryStop;
	}

	public HrsEmpTerminate getEmpTerminate() {
		return empTerminate;
	}

	public void setEmpTerminate(HrsEmpTerminate empTerminate) {
		this.empTerminate = empTerminate;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getFirstApplicationGrigDate() {
		return firstApplicationGrigDate;
	}

	public void setFirstApplicationGrigDate(Date firstApplicationGrigDate) {
		this.firstApplicationGrigDate = firstApplicationGrigDate;
	}

	public Date getFirstServiceGrigDate() {
		return firstServiceGrigDate;
	}

	public void setFirstServiceGrigDate(Date firstServiceGrigDate) {
		this.firstServiceGrigDate = firstServiceGrigDate;
	}

	public String getFirstApplicationDateStringSort() {
		return firstApplicationDateStringSort;
	}

	public void setFirstApplicationDateStringSort(String firstApplicationDateStringSort) {
		this.firstApplicationDateStringSort = firstApplicationDateStringSort;
	}

	public String getFirstServiceDateStringSort() {
		return firstServiceDateStringSort;
	}

	public void setFirstServiceDateStringSort(String firstServiceDateStringSort) {
		this.firstServiceDateStringSort = firstServiceDateStringSort;
	}

	public String getFullName() {
		String name = "";
		if (firstName != null) {
			name = name + firstName+" ";
		}
		if (secondName != null) {
			name = name + secondName+" ";
		}
		if (thirdName != null) {
			name = name + thirdName+" ";
		}

		if (forthName != null) {
			name = name + forthName;
		}
		return name;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
