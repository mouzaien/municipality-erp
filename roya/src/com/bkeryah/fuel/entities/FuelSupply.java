package com.bkeryah.fuel.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FUEL_SUPPLY")
public class FuelSupply {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;
	@Column(name = "FUEL_TYPE_ID")
	private Integer fuelTypeId;
	@Column(name = "SUPPLY_QUANTITY")
	private Integer quantity;
	@Column(name = "ACTUAL_QUANTITY")
	private Integer actualQuantity;
	@Column(name = "SUPPLY_DATE")
	private Date supplyDate;
	@ManyToOne
	@JoinColumn(name = "FUEL_TYPE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private FuelType fuelType;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFuelTypeId() {
		return fuelTypeId;
	}
	public void setFuelTypeId(Integer fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getActualQuantity() {
		return actualQuantity;
	}
	public void setActualQuantity(Integer actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public Date getSupplyDate() {
		return supplyDate;
	}
	public void setSupplyDate(Date supplyDate) {
		this.supplyDate = supplyDate;
	}
	public FuelType getFuelType() {
		return fuelType;
	}
	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}
	
}
