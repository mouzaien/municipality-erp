package com.bkeryah.hr.entities;

import java.util.Date;
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
@Table(name = "HRS_GOV_JOB_G")
public class HrsGovJobG {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "CBY")
	private Integer cby;
	@Column(name = "CIN")
	private Date cin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@Override
	public String toString() {
		return title;
	}

	@Override
	public boolean equals(Object obj) {
		return ((HrsGovJobG) obj).getId().equals(this.id);
	}

	@OneToMany(fetch = FetchType.LAZY)
	private Set<HrsGovJobSeries> jobs;

	public Set<HrsGovJobSeries> getJobs() {
		return jobs;
	}

	public void setJobs(Set<HrsGovJobSeries> jobs) {
		this.jobs = jobs;
	}

}
