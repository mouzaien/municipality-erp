package com.bkeryah.hr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS035")
public class Sys035 {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;

//	@Formula("(select n.RECTYPE from HRS_EMP_HISTORICAL n where  n.RECTYPE = ID)")
//	private String recordTypes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getRecordTypes() {
//		return recordTypes;
//	}
//
//	public void setRecordTypes(String recordTypes) {
//		this.recordTypes = recordTypes;
//	}
}
