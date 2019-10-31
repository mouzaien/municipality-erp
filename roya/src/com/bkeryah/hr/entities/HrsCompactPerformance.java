package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.bkeryah.entities.ArcPeople;
import com.bkeryah.entities.ArcUsers;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Column;

@Entity
@Table(name = "HRS_COMPACT_PERFORMANCE")
public class HrsCompactPerformance {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "EMPID")
	private Integer empid;
	@Column(name = "DIRECTORID")
	private Integer directorid;
	@Column(name = "DEPTID")
	private Integer deptid;
	@Column(name = "JOB_NO")
	private Integer jobNo;
	@Column(name = "JOB_DESCRIPTION")
	private String jobDescription;
	@Column(name = "PERIODE_TYPE")
	private Integer periodeType;
	@Column(name = "STATUS")
	private Integer status;
	@Transient
	private String empName;
	@Transient
	private String mgrName;
//	@ManyToOne
//	@JoinColumn(name="EMPID",referencedColumnName="USER_ID",insertable=false,updatable=false)
	@Transient
	private ArcUsers employer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getEmpid() {
		return empid;
	}

	public void setEmpid(Integer empid) {
		this.empid = empid;
	}

	public Integer getDirectorid() {
		return directorid;
	}

	public void setDirectorid(Integer directorid) {
		this.directorid = directorid;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public Integer getJobNo() {
		return jobNo;
	}

	public void setJobNo(Integer jobNo) {
		this.jobNo = jobNo;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public Integer getPeriodeType() {
		return periodeType;
	}

	public void setPeriodeType(Integer periodeType) {
		this.periodeType = periodeType;
	}

	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ArcUsers getEmployer() {
		return employer;
	}

	public void setEmployer(ArcUsers employer) {
		this.employer = employer;
	}

}
