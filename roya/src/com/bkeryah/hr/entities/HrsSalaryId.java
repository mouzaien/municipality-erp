package com.bkeryah.hr.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.GenericGenerator;


@SuppressWarnings("serial")
@Embeddable
public class HrsSalaryId implements Serializable {
	@Column(name = "EMPNO")
	private Integer employerNumber;
	@Column(name = "SAL_MONTH")
	private Integer salMonth;
	@Column(name = "SAL_YEAR")
	private Integer salYear;
	
	public Integer getEmployerNumber() {
		return employerNumber;
	}
	public void setEmployerNumber(Integer employerNumber) {
		this.employerNumber = employerNumber;
	}
	
	public Integer getSalMonth() {
		return salMonth;
	}
	public void setSalMonth(Integer salMonth) {
		this.salMonth = salMonth;
	}
	public Integer getSalYear() {
		return salYear;
	}
	public void setSalYear(Integer salYear) {
		this.salYear = salYear;
	}
//	public HrsSalaryId(Integer employerNumber, Integer salMonth, Integer salYear) {
//		super();
//		this.employerNumber = employerNumber;
//		this.salMonth = salMonth;
//		this.salYear = salYear;
//	}
	
}
