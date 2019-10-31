package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_TRAINING_WORTH")
public class HrsEmployeeTraining {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "EMPNO")
	private Integer employeeNumber;
	@Column(name = "TRAINING_PLACE")
	private Integer place;
	@Column(name = "TRAINING_PERIOD")
	private Integer period;
	@Column(name = "START_DATE")
	private String startDate;
	@Column(name = "END_DATE")
	private String endDate;
	@Column(name = "TRAINING_REWARD")
	private Double rewardMoney;
	@Column(name = "TICKET")
	private Double ticketMoney;
	@Column(name = "MANDATEOUT")
	private Double mandateMoney;
	@Column(name = "CBY")
	private Integer createdBy;
	@Column(name = "CIN")
	private Date createdIn;
	@Column(name = "STATUS")
	private String trainingStatus;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public Integer getPlace() {
		return place;
	}
	public void setPlace(Integer place) {
		this.place = place;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Double getRewardMoney() {
		return rewardMoney;
	}
	public void setRewardMoney(Double rewardMoney) {
		this.rewardMoney = rewardMoney;
	}
	public Double getTicketMoney() {
		return ticketMoney;
	}
	public void setTicketMoney(Double ticketMoney) {
		this.ticketMoney = ticketMoney;
	}
	public Double getMandateMoney() {
		return mandateMoney;
	}
	public void setMandateMoney(Double mandateMoney) {
		this.mandateMoney = mandateMoney;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedIn() {
		return createdIn;
	}
	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}
	public String getTrainingStatus() {
		return trainingStatus;
	}
	public void setTrainingStatus(String trainingStatus) {
		this.trainingStatus = trainingStatus;
	}
	
	
}