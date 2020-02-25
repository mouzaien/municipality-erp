package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="EMP_TRAINING")
public class EmpTraining {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name="ID")
	private Integer id;
	@Column(name="EMPNO")
	private Integer empNo;
	@Column(name="ENROLLMENT_STATUS")
	private String ennStatus;
	@Column(name="CLASS_NAME")
	private String className;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEmpNo() {
		return empNo;
	}
	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}
	public String getEnnStatus() {
		return ennStatus;
	}
	public void setEnnStatus(String ennStatus) {
		this.ennStatus = ennStatus;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

}
