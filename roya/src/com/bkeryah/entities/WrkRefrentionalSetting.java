package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WRK_REFERRAL_SETTING")
public class WrkRefrentionalSetting {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;

	@Column(name = "NAME")
	private String DeptName;

	@Column(name = "REAL_NAME")
	private String managerName;

	@Column(name = "F1")
	private Integer f1;

	@Column(name = "F2")
	private Integer f2;

	@Column(name = "TITLE")
	private String DeptTitle;

	@Column(name = "MGR_ID")
	private Integer managerId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Integer getF1() {
		return f1;
	}

	public void setF1(int f1) {
		this.f1 = f1;
	}

	public Integer getF2() {
		return f2;
	}

	public void setF2(Integer f2) {
		this.f2 = f2;
	}

	public String getDeptTitle() {
		return DeptTitle;
	}

	public void setDeptTitle(String deptTitle) {
		DeptTitle = deptTitle;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public Integer getManagerId() {
		return managerId;
	}

}
