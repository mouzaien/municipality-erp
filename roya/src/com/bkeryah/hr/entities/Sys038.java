package com.bkeryah.hr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="SYS038")
public class Sys038 {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;
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
}
