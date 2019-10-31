package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "HRS_COMPACT_CAT_FLOOR")
public class HrsCompactCatFloor {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CAT_NAME")
	private String catName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "hrsCompactCatFloor", cascade = CascadeType.ALL)
	private Set<HrsFloors> floors;

	
	public Set<HrsFloors> getFloors() {
		return floors;
	}

	public void setFloors(Set<HrsFloors> floors) {
		this.floors = floors;
	}

}
