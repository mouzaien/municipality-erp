package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class HrsEmpHistoricalId implements Serializable {

	@Column(name = "EMPNO")
	private Integer empno;
	@Column(name = "SERIAL")
	private Integer stepId;
	
	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public Integer getStepId() {
		return stepId;
	}
	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}
	public HrsEmpHistoricalId(Integer empno, Integer stepId) {
		super();
		this.empno = empno;
		this.stepId = stepId;
	}
	public HrsEmpHistoricalId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
