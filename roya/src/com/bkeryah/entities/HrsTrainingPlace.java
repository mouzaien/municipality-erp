package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_PLACES_TRAINING")
public class HrsTrainingPlace {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "PLACE_TRAIN")
	private String place;
	@Column(name = "TICKET_PRICE")
	private Double ticketMoney;
	@Column(name = "MANDOUT_DAYS")
	private Integer mandatePeriod;
	@Column(name = "CBY")
	private Integer createdBy;
	@Column(name = "CIN")
	private Date createdIn;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public Double getTicketMoney() {
		return ticketMoney;
	}
	public void setTicketMoney(Double ticketMoney) {
		this.ticketMoney = ticketMoney;
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
	public Integer getMandatePeriod() {
		return mandatePeriod;
	}
	public void setMandatePeriod(Integer mandatePeriod) {
		this.mandatePeriod = mandatePeriod;
	}

	
	
}