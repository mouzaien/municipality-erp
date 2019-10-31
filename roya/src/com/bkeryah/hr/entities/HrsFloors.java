package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;

@Entity
@Table(name = "FLOORS")
public class HrsFloors {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "CATERIE_FLOURID")
	private Integer caterieFlourid;
	@ManyToOne
	@JoinColumn(name = "CATERIE_FLOURID", referencedColumnName = "ID", insertable = false, updatable = false)
	HrsCompactCatFloor hrsCompactCatFloor;

	public HrsCompactCatFloor getHrsCompactCatFloor() {
		return hrsCompactCatFloor;
	}

	public void setHrsCompactCatFloor(HrsCompactCatFloor hrsCompactCatFloor) {
		this.hrsCompactCatFloor = hrsCompactCatFloor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCaterieFlourid() {
		return caterieFlourid;
	}

	public void setCaterieFlourid(Integer caterieFlourid) {
		this.caterieFlourid = caterieFlourid;
	}

}
