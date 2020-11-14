package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WRK_SECTION")
public class WrkSection implements Serializable {
	/**
	 * 
	 */

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;

	@Column(name = "SEC_NAME")
	private String sectionName;

	@Column(name = "DEPT_ID")
	private Integer deptId;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "DEFAULT_STRUCT")
	private Integer defaultStruct;

	@Column(name = "LETTER_FROM")
	private Integer letterFrom;

	// @OneToMany(fetch= FetchType.LAZY)
	// private Set<ArcUsers> users;

	// @Formula("(select u.DEPT_NAME from WRK_DEPT u where u.ID = DEPT_ID )")
	// private String secName;
	//
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getDefaultStruct() {
		return defaultStruct;
	}

	public void setDefaultStruct(Integer defaultStruct) {
		this.defaultStruct = defaultStruct;
	}

	public Integer getLetterFrom() {
		return letterFrom;
	}

	public void setLetterFrom(Integer letterFrom) {
		this.letterFrom = letterFrom;
	}
	// public Set<ArcUsers> getUsers() {
	// return users;
	// }
	// public void setUsers(Set<ArcUsers> users) {
	// this.users = users;
	// }

}
