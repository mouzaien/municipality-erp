package com.bkeryah.hr.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="hrs_sum_vacation")
public class HrsSumVacation {
	
//	@GenericGenerator(name = "generator", strategy = "increment")
//	@GeneratedValue(generator = "generator")
	
	
//	@Column(name = "EMPNO")
//	private Integer employerNumber;
	@Id
	@Column(name = "EMPNO")
	private Integer employeeNumber;
//	@Column(name = "SAL_MONTH")
//	private Integer salMonth;
//	@Column(name = "SAL_YEAR")
//	private Integer salYear;
	@Column(name = "DAYS")
	private Integer days;
	@Column(name = "VACOLDYEAR37")
	private Integer VACOLDYEAR37;
	@Column(name = "VACOLDEYRAR")
	private Integer VACOLDEYRAR;
	@Column(name = "CURRENTYEAR")
	private Integer CURRENTYEAR;
	@Column(name = "VACSUMYEARSOLD")
	private Integer VACSUMYEARSOLD;
	
	
	
	public Integer getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Integer getVACOLDYEAR37() {
		return VACOLDYEAR37;
	}
	public void setVACOLDYEAR37(Integer vACOLDYEAR37) {
		VACOLDYEAR37 = vACOLDYEAR37;
	}
	public Integer getVACOLDEYRAR() {
		return VACOLDEYRAR;
	}
	public void setVACOLDEYRAR(Integer vACOLDEYRAR) {
		VACOLDEYRAR = vACOLDEYRAR;
	}
	public Integer getCURRENTYEAR() {
		return CURRENTYEAR;
	}
	public void setCURRENTYEAR(Integer cURRENTYEAR) {
		CURRENTYEAR = cURRENTYEAR;
	}
	public Integer getVACSUMYEARSOLD() {
		return VACSUMYEARSOLD;
	}
	public void setVACSUMYEARSOLD(Integer vACSUMYEARSOLD) {
		VACSUMYEARSOLD = vACSUMYEARSOLD;
	}
	
}
