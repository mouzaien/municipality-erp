package com.bkeryah.fuel.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "fuel_transaction")
public class FuelTransaction {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "user_car_id")
	private Integer userCarId;
	@Column(name = "delivery_qty")
	private Integer deliveryQty;
	@Column(name = "created_by")
	private Integer createdBy;
	@Column(name = "CREATED_IN")
	private Date createdIn;
	@Column(name = "START_DATE")
	private Date startDate;
	@Column(name = "END_DATE")
	private Date endDate;
	@ManyToOne
	@JoinColumn(name = "user_car_id", referencedColumnName = "ID", insertable = false, updatable = false)
	private UserCars userCar;
	@Transient
	private String startDateStr;
	@Transient
	private String endDateStr;
	@Transient
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserCarId() {
		return userCarId;
	}

	public void setUserCarId(Integer userCarId) {
		this.userCarId = userCarId;
	}

	public Integer getDeliveryQty() {
		return deliveryQty;
	}

	public void setDeliveryQty(Integer deliveryQty) {
		this.deliveryQty = deliveryQty;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public UserCars getUserCar() {
		return userCar;
	}

	public void setUserCar(UserCars userCar) {
		this.userCar = userCar;
	}

	public Date getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStartDateStr() {
		if(startDate != null)
			startDateStr = sdf.format(startDate);
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		if(endDate != null)
			endDateStr = sdf.format(endDate);
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
}
