/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author edited by yalkhatieb
 */
@Entity
@Table(name = "ARC_USERS")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "login_name")
	private String username;

	@Column(name = "LNAME", nullable = false)
	private String lastName;

	@Column(name = "password")
	private String password;

	@Column(name = "F_ACTIVE")
	private Boolean enabled;

	@Column(name = "FNAME", nullable = false)
	private String firstName;

	@Column(name = "EMPNAME")
	private String employeeName;

	@Column(name = "TITLE_ID")
	private Integer titleId;

	@Column(name = "MGR_ID")
	private Integer mgrId;

	@Column(name = "MOBILE")
	private String mobileNumber;

	// @Column(name = "LOGIN_NAME", nullable = false)
	// private String loginName;

	@Column(name = "EMPNO", nullable = true)
	private Integer employeeNumber;

	@Column(name = "DEPT_ID")
	private Integer deptId;

	@Column(name = "JOB_ID")
	private Integer jobId;

	@Column(name = "WRK_ROLE_ID")
	private Integer wrkRoleId;

	@Column(name = "SEC_ID")
	private Integer wrkSectionId;

	@Formula("(select w.JOB_NAME from WRK_JOBS w where w.id = job_Id)")
	private String jobName;

	@Formula("(select d.DEPT_NAME from WRK_DEPT d where d.id = DEPT_Id)")
	private String deptName;

	@Column(name = "EMPNO_TMP")
	private Integer empnoTmp;

	// todo get The Rest Of Formulas

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "user") private
	 * Set<UserCars> userCarsSet;
	 */

	// public String getLoginName() {
	// return loginName;
	// }
	//
	// public void setLoginName(String loginName) {
	// this.loginName = loginName;
	// }

	public Integer getEmpnoTmp() {
		return empnoTmp;
	}

	public void setEmpnoTmp(Integer empnoTmp) {
		this.empnoTmp = empnoTmp;
	}

	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}

	public Integer getMgrId() {

		return mgrId;
	}

	public void setMgrId(Integer mgrId) {
		this.mgrId = mgrId;
	}

	public String getMobileNumber() {

		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Integer getWrkSectionId() {
		return wrkSectionId;
	}

	public void setWrkSectionId(Integer wrkSectionId) {
		this.wrkSectionId = wrkSectionId;
	}

	public Integer getWrkRoleId() {
		return wrkRoleId;
	}

	public void setWrkRoleId(Integer wrkRoleId) {
		this.wrkRoleId = wrkRoleId;
	}

	@Transient
	private String fullName;

	public String getFullName() {
		if (employeeName != null)
			fullName = employeeName;
		else if (firstName != null && lastName != null && !firstName.equals(lastName))
			fullName = firstName + " " + lastName;
		else
			fullName = firstName;
		return fullName;
	}

	@Override
	public String toString() {
		if (employeeName != null)
			fullName = employeeName;
		else if (firstName != null && lastName != null && !firstName.equals(lastName))
			fullName = firstName + " " + lastName;
		else
			fullName = firstName;
		return userId + "|" + fullName;
	}

	@Override
	public boolean equals(Object obj) {
		return ((User) obj).getUserId().equals(this.userId);
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public User(int id, String username, String password, Boolean enabled) {
		this.userId = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/*
	 * public Set<UserCars> getUserCarsSet() { return userCarsSet; }
	 * 
	 * public void setUserCarsSet(Set<UserCars> userCarsSet) { this.userCarsSet
	 * = userCarsSet; }
	 */

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
