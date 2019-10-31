package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name ="ARC_PEOPLE_PLACE")
public class NationalIdPlaces {

	@Id
	@Column(name = "ID" , nullable = false)
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private Integer placeId;
	
	@Column(name = "NAME" , nullable = false)
	private  String placeName; 
	
	@Column(name = "CNTRY_S_G_W" , nullable = false)
	//S for internal KSA places ,G for external places 
	private  String placeType;

	public Integer getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}
	
	
	
	
}
