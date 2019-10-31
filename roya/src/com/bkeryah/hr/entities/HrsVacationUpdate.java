package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "HRS_VACATION_UPDATE")
public class HrsVacationUpdate {
	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "YEAR")
	private int year;
	@Column(name = "DAYS")
	private int days;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}


}