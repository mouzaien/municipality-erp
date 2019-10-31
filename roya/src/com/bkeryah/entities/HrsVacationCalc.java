package com.bkeryah.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "HRS_VACATION_CALC")
public class HrsVacationCalc {

	@Id
	@Column(name = "EMPNO")
	private Integer employeeNumber;
	@Column(name = "year")
	private Integer year;
	@Column(name = "OLDVACTIONSCORE") // zero or one
	private Integer OLDVACTIONSCORE;
	@Column(name = "OLDCOUNTER") // zero or one
	private Integer OLDCOUNTER;
	@Column(name = "SCORETWOMONTH") // zero or one
	private Integer SCORETWOMONTH;
	@Column(name = "TWOMONTHCOUNTER") // zero or one
	private Integer TWOMONTHCOUNTER;
//	@OneToMany(mappedBy = "vacationCalc", fetch=FetchType.LAZY)
//	private List<HrEmployeeVacation> vacations;
	public Integer getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getOLDVACTIONSCORE() {
		return OLDVACTIONSCORE;
	}
	public void setOLDVACTIONSCORE(Integer oLDVACTIONSCORE) {
		OLDVACTIONSCORE = oLDVACTIONSCORE;
	}
	public Integer getOLDCOUNTER() {
		return OLDCOUNTER;
	}
	public void setOLDCOUNTER(Integer oLDCOUNTER) {
		OLDCOUNTER = oLDCOUNTER;
	}
	public Integer getSCORETWOMONTH() {
		return SCORETWOMONTH;
	}
	public void setSCORETWOMONTH(Integer sCORETWOMONTH) {
		SCORETWOMONTH = sCORETWOMONTH;
	}
	public Integer getTWOMONTHCOUNTER() {
		return TWOMONTHCOUNTER;
	}
	public void setTWOMONTHCOUNTER(Integer tWOMONTHCOUNTER) {
		TWOMONTHCOUNTER = tWOMONTHCOUNTER;
	}
//	public List<HrEmployeeVacation> getVacations() {
//		return vacations;
//	}
//	public void setVacations(List<HrEmployeeVacation> vacations) {
//		this.vacations = vacations;
//	}
}
