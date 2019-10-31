package com.bkeryah.hr.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "USER_VAC_SOLD")
public class UserVacSold {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "init_date")
	private Date initDate;
	@Column(name = "init_sold")
	private Integer initSold;
	@Column(name = "year_sold")
	private Integer yearSold;
	@Column(name = "empno")
	private Integer empno;
	@Column(name = "init_year")
	private Integer initYear;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Integer getInitSold() {
		return initSold;
	}

	public void setInitSold(Integer initSold) {
		this.initSold = initSold;
	}

	public Integer getYearSold() {
		return yearSold;
	}

	public void setYearSold(Integer yearSold) {
		this.yearSold = yearSold;
	}

	public Integer getEmpno() {
		return empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

	public Integer getInitYear() {
		return initYear;
	}

	public void setInitYear(Integer initYear) {
		this.initYear = initYear;
	}
}
