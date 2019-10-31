package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "SYS012")
public class Sys012 {
	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME_AR")
	private String nameAr;
	@Column(name = "NAME_GR")
	private String nameGr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameAr() {
		return nameAr;
	}

	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}

	public String getNameGr() {
		return nameGr;
	}

	public void setNameGr(String nameGr) {
		this.nameGr = nameGr;
	}
}