package com.bkeryah.model;

public class AbsentModel {
	private Integer empno;
	private Integer days;
	private Integer hours;
	private Integer minutes;

	public Integer getEmpno() {
		return empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.equals(null))
			return false;
		if (this.getEmpno().equals(null))
			return false;
		return this.getEmpno().equals(((AbsentModel) obj).getEmpno());
	}
}
