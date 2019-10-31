package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;

import java.util.Date;

import javax.persistence.Column;

@Entity
@Table(name = "HRS_GOV_JOB_WRKS")
public class HrsGovJobWrks {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "SERIE_ID")
	private String serieId;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "CATCOD")
	private Integer catcod;
	@Column(name = "CBY")
	private Integer cby;
	@Column(name = "CIN")
	private Date cin;
	@Column(name = "EVCAT")
	private Integer evcat;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSerieId() {
		return serieId;
	}

	public void setSerieId(String serieId) {
		this.serieId = serieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCatcod() {
		return catcod;
	}

	public void setCatcod(Integer catcod) {
		this.catcod = catcod;
	}

	public Integer getCby() {
		return cby;
	}

	public void setCby(Integer cby) {
		this.cby = cby;
	}

	public Date getCin() {
		return cin;
	}

	public void setCin(Date cin) {
		this.cin = cin;
	}

	public Integer getEvcat() {
		return evcat;
	}

	public void setEvcat(Integer evcat) {
		this.evcat = evcat;
	}

}
