package com.bkeryah.hr.entities;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;


@Entity
@Table(name="HRS_SALARY")
public class HrsSalary {
//	@Id
//	@GenericGenerator(name = "generator", strategy = "increment")
//	@GeneratedValue(generator = "generator")
	@EmbeddedId
	private HrsSalaryId id;
	
//	@Column(name = "EMPNO")
//	private Integer employerNumber;
	@Column(name = "CATCOD")
	private Integer categoryCode;
//	@Column(name = "SAL_MONTH")
//	private Integer salMonth;
//	@Column(name = "SAL_YEAR")
//	private Integer salYear;
	@Column(name = "SAL_DESERVED")
	private Integer salDeserved;
	@Column(name = "SAL_TRANS")
	private Integer salTransmit;
	@Column(name = "SAL_WORKED_DAYS")
	private Integer salWorkedDays;
	@Column(name = "SAL_NATURAL")
	private Integer salNaturalFirstSalary;
	@Column(name = "SAL_NATURE_BASIC")
	private Integer salNaturalNow;
	@Column(name = "SAL_DARAR")
	private Integer salDarar;
	@Column(name = "SAL_OTHER_IN")
	private Integer salOtherIn;
	@Column(name = "SAL_RETIRE")
	private Integer salRetire;
	@Column(name = "SAL_INSURANCE")
	private Integer salInsurance;
	@Column(name = "SAL_BANK_1")
	private Integer salBankAqar;
	@Column(name = "SAL_BANK_2")
	private Integer salBankTaslef;
	@Column(name = "SAL_BANK_3")
	private Integer salBankzeraa;
	@Column(name = "SAL_BANK_4")
	private Integer salBank4;
	@Column(name = "SAL_BANK_5")
	private Integer salBank5;
	@Column(name = "SAL_DEDUCT")
	private Integer salDeduct;
	@Column(name = "SAL_JAZA_DAYS_NO")
	private Integer salJazaDaysNumber;
	@Column(name = "SAL_JAZA_DAYS")
	private Integer salJazaDays;
	@Column(name = "SAL_JAZA_HOURS_NO")
	private Integer salJazaHoursNumber;
	@Column(name = "SAL_JAZA_HOURS")
	private Integer salJazaHours;
	@Column(name = "SAL_JAZA_HOURS_DEDUCT")
	private Integer salJazaHourDeduct;
	@Column(name = "SAL_SANED")
	private Integer salSand;
	@Column(name = "SAL_OTHER_OUT")
	private Integer salOtherOut;
	@Column(name = "SAL_TOTAL_SUM")
	private Integer salTotalSum;
	@Formula("(select w.EMPNAME from arc_users w where w.EMPNO = EMPNO)")
	private String name;
//	public Integer getEmployerNumber() {
//		return employerNumber;
//	}
//	public void setEmployerNumber(Integer employerNumber) {
//		this.employerNumber = employerNumber;
//	}
	public Integer getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(Integer categoryCode) {
		this.categoryCode = categoryCode;
	}
//	public Integer getSalMonth() {
//		return salMonth;
//	}
//	public void setSalMonth(Integer salMonth) {
//		this.salMonth = salMonth;
//	}
//	public Integer getSalYear() {
//		return salYear;
//	}
//	public void setSalYear(Integer salYear) {
//		this.salYear = salYear;
//	}
	public Integer getSalDeserved() {
		return salDeserved;
	}
	public void setSalDeserved(Integer salDeserved) {
		this.salDeserved = salDeserved;
	}
	public Integer getSalTransmit() {
		return salTransmit;
	}
	public void setSalTransmit(Integer salTransmit) {
		this.salTransmit = salTransmit;
	}
	public Integer getSalWorkedDays() {
		return salWorkedDays;
	}
	public void setSalWorkedDays(Integer salWorkedDays) {
		this.salWorkedDays = salWorkedDays;
	}
	public Integer getSalNaturalFirstSalary() {
		return salNaturalFirstSalary;
	}
	public void setSalNaturalFirstSalary(Integer salNaturalFirstSalary) {
		this.salNaturalFirstSalary = salNaturalFirstSalary;
	}
	public Integer getSalNaturalNow() {
		return salNaturalNow;
	}
	public void setSalNaturalNow(Integer salNaturalNow) {
		this.salNaturalNow = salNaturalNow;
	}
	public Integer getSalDarar() {
		return salDarar;
	}
	public void setSalDarar(Integer salDarar) {
		this.salDarar = salDarar;
	}
	public Integer getSalOtherIn() {
		return salOtherIn;
	}
	public void setSalOtherIn(Integer salOtherIn) {
		this.salOtherIn = salOtherIn;
	}
	public Integer getSalRetire() {
		return salRetire;
	}
	public void setSalRetire(Integer salRetire) {
		this.salRetire = salRetire;
	}
	public Integer getSalInsurance() {
		return salInsurance;
	}
	public void setSalInsurance(Integer salInsurance) {
		this.salInsurance = salInsurance;
	}
	public Integer getSalBankAqar() {
		return salBankAqar;
	}
	public void setSalBankAqar(Integer salBankAqar) {
		this.salBankAqar = salBankAqar;
	}
	public Integer getSalBankTaslef() {
		return salBankTaslef;
	}
	public void setSalBankTaslef(Integer salBankTaslef) {
		this.salBankTaslef = salBankTaslef;
	}
	public Integer getSalBankzeraa() {
		return salBankzeraa;
	}
	public void setSalBankzeraa(Integer salBankzeraa) {
		this.salBankzeraa = salBankzeraa;
	}
	public Integer getSalBank4() {
		return salBank4;
	}
	public void setSalBank4(Integer salBank4) {
		this.salBank4 = salBank4;
	}
	public Integer getSalBank5() {
		return salBank5;
	}
	public void setSalBank5(Integer salBank5) {
		this.salBank5 = salBank5;
	}
	public Integer getSalDeduct() {
		return salDeduct;
	}
	public void setSalDeduct(Integer salDeduct) {
		this.salDeduct = salDeduct;
	}
	public Integer getSalJazaDaysNumber() {
		return salJazaDaysNumber;
	}
	public void setSalJazaDaysNumber(Integer salJazaDaysNumber) {
		this.salJazaDaysNumber = salJazaDaysNumber;
	}
	public Integer getSalJazaDays() {
		return salJazaDays;
	}
	public void setSalJazaDays(Integer salJazaDays) {
		this.salJazaDays = salJazaDays;
	}
	public Integer getSalJazaHoursNumber() {
		return salJazaHoursNumber;
	}
	public void setSalJazaHoursNumber(Integer salJazaHoursNumber) {
		this.salJazaHoursNumber = salJazaHoursNumber;
	}
	public Integer getSalJazaHours() {
		return salJazaHours;
	}
	public void setSalJazaHours(Integer salJazaHours) {
		this.salJazaHours = salJazaHours;
	}
	public Integer getSalJazaHourDeduct() {
		return salJazaHourDeduct;
	}
	public void setSalJazaHourDeduct(Integer salJazaHourDeduct) {
		this.salJazaHourDeduct = salJazaHourDeduct;
	}
	public Integer getSalSand() {
		return salSand;
	}
	public void setSalSand(Integer salSand) {
		this.salSand = salSand;
	}
	public Integer getSalOtherOut() {
		return salOtherOut;
	}
	public void setSalOtherOut(Integer salOtherOut) {
		this.salOtherOut = salOtherOut;
	}
	public Integer getSalTotalSum() {
		return salTotalSum;
	}
	public void setSalTotalSum(Integer salTotalSum) {
		this.salTotalSum = salTotalSum;
	}
	public HrsSalaryId getId() {
		return id;
	}
	public void setId(HrsSalaryId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
