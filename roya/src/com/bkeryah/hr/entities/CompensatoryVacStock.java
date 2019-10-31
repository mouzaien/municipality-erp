package com.bkeryah.hr.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "COMPENSATORY_VAC")
public class CompensatoryVacStock {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "EMPNO")
	private Integer empno;
	@Formula("(SELECT A.FName FROM Arc_Users A where A.EMPNO = EMPNO)")
	private String empName;
	@Column(name = "QUANTITY")
	private Integer qty;
	@Column(name = "TAKEN")
	private Integer taken;
	@Column(name = "REMAIN")
	private Integer remain;
	@Column(name = "COMP_YEAR")
	private Integer year;
	@Column(name = "VAC_TYPE") // 27
	private Integer VAC_TYPE;
	@Column(name = "COMP_TYPE") // 4..5
	private Integer comp_type;
	@Column(name = "COMP_GREG_DATE")
	private Date comp_greg_date;
	@Column(name = "COMP_HIGRI_DATE")
	private String comp_higri_date;
	@Transient
	private String comp;

	public Integer getVAC_TYPE() {
		return VAC_TYPE;
	}

	public void setVAC_TYPE(Integer vAC_TYPE) {
		VAC_TYPE = vAC_TYPE;
	}

	public Integer getComp_type() {
		return comp_type;
	}

	public void setComp_type(Integer comp_type) {
		this.comp_type = comp_type;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmpno() {
		return empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getComp_greg_date() {
		return comp_greg_date;
	}

	public void setComp_greg_date(Date comp_greg_date) {
		this.comp_greg_date = comp_greg_date;
	}

	public String getComp_higri_date() {
		return comp_higri_date;
	}

	public void setComp_higri_date(String comp_higri_date) {
		this.comp_higri_date = comp_higri_date;
	}

	public Integer getTaken() {
		return taken;
	}

	public void setTaken(Integer taken) {
		this.taken = taken;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		remain = qty - taken;
		this.remain = remain;
	}

	public String getComp() {
		if (comp_type == 4) {
			comp = "عيد الفطر";
		} else {
			comp = "عيد الأضحى";

		}
		return comp;
	}

	public void setComp(String comp) {
		this.comp = comp;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
}
