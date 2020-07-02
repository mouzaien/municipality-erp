package com.bkeryah.entities.licences;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "LIC_VISITS_TYPES")
public class LicVisitsTypes {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "VISIT_DESC")
	private String visitsDesc;
	@Column(name = "TYPE")
	private Integer type;
	@Column(name = "DAYS_NO")
	private Integer daysNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	// public void setName(String name) {
	// this.name = name;
	// }
	//
	// public String getVisitsDesc() {
	// return visitsDesc;
	// }

	// public void setvisitsDescisitsDesc(String visitsDesc) {
	// this.visitsDesc = visitsDesc;
	// }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getVisitsDesc() {
		return visitsDesc;
	}

	public void setVisitsDesc(String visitsDesc) {
		this.visitsDesc = visitsDesc;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDaysNo() {
		return daysNo;
	}

	public void setDaysNo(Integer daysNo) {
		this.daysNo = daysNo;
	}
}
