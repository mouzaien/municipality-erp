package com.bkeryah.hr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_VAC_SOLD")
public class HrsVacSold {

	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "EMPNO")
	private Integer employeeNumber;
	@Formula("(SELECT A.FName FROM Arc_Users A where A.EMPNO = EMPNO)")
	private String empName;
	@Column(name = "VAC_CURR_YEAR")
	private Integer currentYear;
	@Column(name = "VAC_TYPE")
	private Integer vacType;
	@Column(name = "VAC_OLD_SOLD")
	private Integer oldSold;
	@Column(name = "VAC_CURR_YEAR_SOLD")
	private Integer currentYearSold;
	@Column(name = "VAC_CURR_YEAR_TAKEN")
	private Integer currentYearTaken;

	@Transient
	private Integer avalibelSold;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Integer getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(Integer currentYear) {
		this.currentYear = currentYear;
	}

	public Integer getVacType() {
		return vacType;
	}

	public void setVacType(Integer vacType) {
		this.vacType = vacType;
	}

	public Integer getOldSold() {
		return oldSold;
	}

	public void setOldSold(Integer oldSold) {
		this.oldSold = oldSold;
	}

	public Integer getCurrentYearSold() {
		return currentYearSold;
	}

	public void setCurrentYearSold(Integer currentYearSold) {
		this.currentYearSold = currentYearSold;
	}

	public Integer getCurrentYearTaken() {
		return currentYearTaken;
	}

	public void setCurrentYearTaken(Integer currentYearTaken) {
		this.currentYearTaken = currentYearTaken;
	}

	public Integer getAvalibelSold() {
		avalibelSold = (this.oldSold + this.currentYearSold) - this.currentYearTaken;
		return avalibelSold;
	}

	public void setAvalibelSold(Integer avalibelSold) {
		avalibelSold = (this.oldSold + this.currentYearSold) - this.currentYearTaken;
		// avalibelSold = (getOldSold() + getCurrentYearSold()) -
		// getCurrentYearTaken();
		this.avalibelSold = avalibelSold;

	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
}
