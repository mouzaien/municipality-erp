package com.bkeryah.fuel.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bkeryah.entities.investment.ContractDirect;

@Entity
@Table(name = "car")
public class Car implements Comparable<Car> {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;
	@Column(name = "fuel_type")
	private Integer fuelTypeId;
	@Column(name = "VEHICLE_TYPE_ID")
	private Integer vehicleTypeId;
	@Column(name = "car_model")
	private Integer carModel;
	@Column(name = "year_model")
	private Integer yearModel;
	@Column(name = "matricule")
	private String matricule;
	@Column(name = "NUM_DOOR")
	private Integer numDoor;
	@ManyToOne
	@JoinColumn(name = "car_model", referencedColumnName = "ID", insertable = false, updatable = false)
	private CarModel model;
	@ManyToOne
	@JoinColumn(name = "fuel_type", referencedColumnName = "ID", insertable = false, updatable = false)
	private FuelType fuelType;
	@ManyToOne
	@JoinColumn(name = "VEHICLE_TYPE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private VehicleType vehicleType;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
	private Set<UserCars> userCarsSet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCarModel() {
		return carModel;
	}

	public void setCarModel(Integer carModel) {
		this.carModel = carModel;
	}

	public Integer getYearModel() {
		return yearModel;
	}

	public void setYearModel(Integer yearModel) {
		this.yearModel = yearModel;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public CarModel getModel() {
		return model;
	}

	public void setModel(CarModel model) {
		this.model = model;
	}

	public Integer getFuelTypeId() {
		return fuelTypeId;
	}

	public void setFuelTypeId(Integer fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}

	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Integer vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public FuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Set<UserCars> getUserCarsSet() {
		return userCarsSet;
	}

	public void setUserCarsSet(Set<UserCars> userCarsSet) {
		this.userCarsSet = userCarsSet;
	}

	public Integer getNumDoor() {
		return numDoor;
	}

	public void setNumDoor(Integer numDoor) {
		this.numDoor = numDoor;
	}

	@Override
	public int compareTo(Car obj) {
		if(this.id < obj.id)
			return -1;
		else if(this.id > obj.id)
			return 1;
		else
			return 0;
	}
}
