package com.bkeryah.fuel.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bkeryah.model.User;

@Entity
@Table(name = "user_cars")
public class UserCars {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;
	@Column(name = "car_id")
	private Integer carId;
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "delivery_counter")
	private Integer deliveryCounter;
	@Column(name = "created_by")
	private Integer createdBy;
	@Column(name = "delivery_date")
	private Date deliveryDate;
	@Column(name = "OWNER_STATUS")
	private Integer ownerStatus;
	@ManyToOne
	@JoinColumn(name = "car_id", referencedColumnName = "ID", insertable = false, updatable = false)
	private Car car;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User user;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userCar")
	private Set<FuelTransaction> fuelTransactionsSet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDeliveryCounter() {
		return deliveryCounter;
	}

	public void setDeliveryCounter(Integer deliveryCounter) {
		this.deliveryCounter = deliveryCounter;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Set<FuelTransaction> getFuelTransactionsSet() {
		return fuelTransactionsSet;
	}

	public void setFuelTransactionsSet(Set<FuelTransaction> fuelTransactionsSet) {
		this.fuelTransactionsSet = fuelTransactionsSet;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getOwnerStatus() {
		return ownerStatus;
	}

	public void setOwnerStatus(Integer ownerStatus) {
		this.ownerStatus = ownerStatus;
	}

}
