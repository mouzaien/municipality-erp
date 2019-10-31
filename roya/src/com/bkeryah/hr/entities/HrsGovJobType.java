package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.Set;

import javax.persistence.Column;

@Entity
@Table(name = "HRS_GOV_JOB_TYPE")
public class HrsGovJobType {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "DESCRIPTION")
	private String description;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<HrsGovJobSeries> jobsType;

	public Set<HrsGovJobSeries> getJobsType() {
		return jobsType;
	}

	public void setJobsType(Set<HrsGovJobSeries> jobsType) {
		this.jobsType = jobsType;
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

}
