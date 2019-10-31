package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "Technical_Users")
public class TechnicalUsers  implements Serializable {
	
	
	@Id
	//@GenericGenerator(name = "generator", strategy = "increment")
	//@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer technicalId;
	@Column(name = "ROLE_ID", nullable = false)
	private Integer roleId;
	@Column(name = "DESCRIPTION")
	private String description;
	@Formula("(select w.EMPNAME from ARC_USERS w where w.USER_ID = ID)")
	private String technicalName;
	
	public TechnicalUsers() {
		super();
		// TODO Auto-generated constructor stub
	}


	public TechnicalUsers(Integer technicalId, Integer roleId, String description) {
		super();
		this.technicalId = technicalId;
		this.roleId = roleId;
		this.description = description;
	}


	public Integer getTechnicalId() {
		return technicalId;
	}


	public void setTechnicalId(Integer technicalId) {
		this.technicalId = technicalId;
	}


	public Integer getRoleId() {
		return roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getTechnicalName() {
		return technicalName;
	}


	public void setTechnicalName(String technicalName) {
		this.technicalName = technicalName;
	}
	
	
}
