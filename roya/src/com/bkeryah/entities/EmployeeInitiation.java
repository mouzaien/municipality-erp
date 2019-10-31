package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_DIRECT_VACATION")
public class EmployeeInitiation {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "VACATION_ID")
	private int vacationId;
	@Column(name = "DATE_H")
	private String intaionHigriDate;
	@Column(name = "DAY_VACATION")
	private String intaionDay;
	
	
	@Column(name = "accept_y_n")
	private String acceptStatus;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="VACATION_ID" ,referencedColumnName="VACSEQ" ,insertable = false,updatable = false)
	private HrEmployeeVacation Vacation;


	public String getIntaionHigriDate() {
		return intaionHigriDate;
	}

	public void setIntaionHigriDate(String intaionHigriDate) {
		this.intaionHigriDate = intaionHigriDate;
	}

	public HrEmployeeVacation getVacation() {
		return Vacation;
	}

	public void setVacation(HrEmployeeVacation vacation) {
		Vacation = vacation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getVacationId() {
		return vacationId;
	}

	public void setVacationId(int vacationId) {
		this.vacationId = vacationId;
	}

	public String getIntaionDay() {
		return intaionDay;
	}

	public void setIntaionDay(String intaionDay) {
		this.intaionDay = intaionDay;
	}

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	


}
