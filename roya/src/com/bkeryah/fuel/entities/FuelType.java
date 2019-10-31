package com.bkeryah.fuel.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "fuel_type")
public class FuelType {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fuelType")
	private Set<Car> carSet;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fuelType")
	private Set<FuelSupply> fuelSupplySet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Car> getCarSet() {
		return carSet;
	}

	public void setCarSet(Set<Car> carSet) {
		this.carSet = carSet;
	}

	public Set<FuelSupply> getFuelSupplySet() {
		return fuelSupplySet;
	}

	public void setFuelSupplySet(Set<FuelSupply> fuelSupplySet) {
		this.fuelSupplySet = fuelSupplySet;
	}

}
