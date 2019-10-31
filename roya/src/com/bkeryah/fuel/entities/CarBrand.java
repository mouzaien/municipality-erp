package com.bkeryah.fuel.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CAR_BRAND")
public class CarBrand {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
	private Set<CarModel> modelSet;

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

	public Set<CarModel> getModelSet() {
		return modelSet;
	}

	public void setModelSet(Set<CarModel> modelSet) {
		this.modelSet = modelSet;
	}

}
