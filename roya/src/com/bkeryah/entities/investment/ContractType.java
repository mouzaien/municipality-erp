package com.bkeryah.entities.investment;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "INV_CONT_TYPES")              
public class ContractType {

	@Id
	@Column(name = "CONT_TYPE_CODE")
	private String code;
	@Column(name = "CONT_TYPE_DESC")
	private String name;
	@OneToMany(fetch= FetchType.LAZY)
	private List<RealEstate> realEstateList ;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<RealEstate> getRealEstateList() {
		return realEstateList;
	}
	public void setRealEstateList(List<RealEstate> realEstateList) {
		this.realEstateList = realEstateList;
	}
	
}