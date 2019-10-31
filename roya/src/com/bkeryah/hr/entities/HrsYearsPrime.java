package com.bkeryah.hr.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_YEARS_PRIME")
public class HrsYearsPrime {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "PRIME_YEAR")
	private String primeYear;
	@Column(name = "CAL_DATE")
	private Date calDate;

	public Integer getId() {
		return id;
	}
	
	public HrsYearsPrime() {
		
	}

	public HrsYearsPrime(String primeYear, Date calDate) {
		super();
		this.primeYear = primeYear;
		this.calDate = calDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrimeYear() {
		return primeYear;
	}

	public void setPrimeYear(String primeYear) {
		this.primeYear = primeYear;
	}

	public Date getCalDate() {
		return calDate;
	}

	public void setCalDate(Date calDate) {
		this.calDate = calDate;
	}

}
