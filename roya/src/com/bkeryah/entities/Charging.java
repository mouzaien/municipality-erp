package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import utilities.Utils;

@Entity
@Table(name = "WRK_CHARGING")
public class Charging {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "CHARGING_ID")
	private Integer id;

	@Column(name = "WHO_IS_CHARGED")
	private Integer employeInChargingId;

	@Column(name = "WHO_IS_NOT_HERE")
	private Integer employeeOutChargingId;

	@Column(name = "USER_NAME_BEFORE")
	private String employeInChargingNameBefore;

	@Column(name = "USER_NAME_AFTER")
	private String employeInChargingNameAfter;

	@Column(name = "START_TAKLEEF")
	private String chargingStratDate;

	@Column(name = "END_TAKLEEF")
	private String chargingEndDate;

	@Column(name = "FLAG")
	private Integer chargingStatus;

	@Column(name = "PRIV_BEFORE")
	private Integer chargingEmpPrivilegeBefore;

	@Column(name = "PRIV_AFTER")
	private Integer chargingEmpPrivilegeAfter;

	@Column(name = "MGR_BEFORE")
	private Integer chargingEmpManagerIdBefore;

	@Column(name = "MGR_AFTER")
	private Integer chargingEmpManagerIdAfter;

	public Integer getEmployeInChargingId() {
		return employeInChargingId;
	}

	public void setEmployeInChargingId(Integer employeInChargingId) {
		this.employeInChargingId = employeInChargingId;
	}

	public Integer getEmployeeOutChargingId() {
		return employeeOutChargingId;
	}

	public void setEmployeeOutChargingId(Integer employeeOutChargingId) {
		this.employeeOutChargingId = employeeOutChargingId;
	}

	public String getEmployeInChargingNameBefore() {
		return employeInChargingNameBefore;
	}

	public void setEmployeInChargingNameBefore(String employeInChargingNameBefore) {
		this.employeInChargingNameBefore = employeInChargingNameBefore;
	}

	public String getEmployeInChargingNameAfter() {
		return employeInChargingNameAfter;
	}

	public void setEmployeInChargingNameAfter(String employeInChargingNameAfter) {
		this.employeInChargingNameAfter = employeInChargingNameAfter;
	}

	public String getChargingStratDate() {
		return chargingStratDate;
	}

	public void setChargingStratDate(String chargingStratDate) {
		this.chargingStratDate = chargingStratDate;
	}

	public String getChargingEndDate() {
		return chargingEndDate;
	}

	public void setChargingEndDate(String chargingEndDate) {
		this.chargingEndDate = chargingEndDate;
	}

	public Integer getChargingStatus() {
		return chargingStatus;
	}

	public void setChargingStatus(Integer chargingStatus) {
		this.chargingStatus = chargingStatus;
	}

	public Integer getChargingEmpPrivilegeBefore() {
		return chargingEmpPrivilegeBefore;
	}

	public void setChargingEmpPrivilegeBefore(Integer chargingEmpPrivilegeBefore) {
		this.chargingEmpPrivilegeBefore = chargingEmpPrivilegeBefore;
	}

	public Integer getChargingEmpPrivilegeAfter() {
		return chargingEmpPrivilegeAfter;
	}

	public void setChargingEmpPrivilegeAfter(Integer chargingEmpPrivilegeAfter) {
		this.chargingEmpPrivilegeAfter = chargingEmpPrivilegeAfter;
	}

	public Integer getChargingEmpManagerIdBefore() {
		return chargingEmpManagerIdBefore;
	}

	public void setChargingEmpManagerIdBefore(Integer chargingEmpManagerIdBefore) {
		this.chargingEmpManagerIdBefore = chargingEmpManagerIdBefore;
	}

	public Integer getChargingEmpManagerIdAfter() {
		return chargingEmpManagerIdAfter;
	}

	public void setChargingEmpManagerIdAfter(Integer chargingEmpManagerIdAfter) {
		this.chargingEmpManagerIdAfter = chargingEmpManagerIdAfter;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
