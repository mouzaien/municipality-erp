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
@Table(name = "WRK_DEPT")
public class WrkDept2 implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;

	@Column(name = "DEPT_NAME")
	private String deptName;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "DEPT_NAME_E")
	private String deptNameE;

	@Column(name = "IS_ACTIVE_Y_N")
	private String deptIsActive;

	@Formula("(select s.SEC_NAME from WRK_SECTION s where s.DEPT_ID = ID)")
	private String deptSectionName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDeptNameE() {
		return deptNameE;
	}

	public void setDeptNameE(String deptNameE) {
		this.deptNameE = deptNameE;
	}

	public String getDeptIsActive() {
		return deptIsActive;
	}

	public void setDeptIsActive(String deptIsActive) {
		this.deptIsActive = deptIsActive;
	}

	public String getDeptSectionName() {
		return deptSectionName;
	}

	public void setDeptSectionName(String deptSectionName) {
		this.deptSectionName = deptSectionName;
	}

//	public void setDeptSection(String deptSection) {
//		this.setDeptSection = deptSection;
//	}

}
