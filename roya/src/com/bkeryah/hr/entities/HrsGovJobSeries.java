package com.bkeryah.hr.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_GOV_JOB_SERIES")
public class HrsGovJobSeries {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "JOB_G")
	private Integer jobG;
	@ManyToOne
	@JoinColumn(name = "JOB_G", referencedColumnName = "ID", insertable = false, updatable = false)
	HrsGovJobG jobGroup;

	@Column(name = "JOB_SPEC")
	private Integer jobSpec;
	@ManyToOne
	@JoinColumn(name = "JOB_SPEC", referencedColumnName = "ID", insertable = false, updatable = false)
	HrsGovJobSpec jobSpecific;

	@Column(name = "JOB_CATSERIES")
	private Integer jobCatseries;
	@ManyToOne
	@JoinColumn(name = "JOB_CATSERIES", referencedColumnName = "ID", insertable = false, updatable = false)
	HrsGovJobCatgseries jobCategories;

	@Column(name = "CODE")
	private String code;
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

	@Column(name = "JOB_TYPE")
	private Integer type;
	@ManyToOne
	@JoinColumn(name = "JOB_TYPE", referencedColumnName = "ID", insertable = false, updatable = false)
	HrsGovJobType jobType;

	public HrsGovJobType getJobType() {
		return jobType;
	}

	public void setJobType(HrsGovJobType jobType) {
		this.jobType = jobType;
	}

	public HrsGovJobSeries() {
	}

	public HrsGovJobSeries(HrsGovJobSeries src) {
		this.id = src.id;
		this.code = src.code;
		this.title = src.title;
		this.catcod = src.catcod;
		this.cby = src.cby;
		this.cin = src.cin;
		this.jobG = src.jobG;
		this.evcat = src.evcat;
		this.jobCategories = src.jobCategories;
		this.jobCatseries = src.jobCatseries;
		this.jobGroup = src.jobGroup;
		this.jobSpecific = src.jobSpecific;
		this.jobSpec = src.jobSpec;
		this.serieId = src.serieId;
		this.jobType = src.jobType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getJobG() {
		return jobG;
	}

	public void setJobG(Integer jobG) {
		this.jobG = jobG;
	}

	public Integer getJobSpec() {
		return jobSpec;
	}

	public void setJobSpec(Integer jobSpec) {
		this.jobSpec = jobSpec;
	}

	public Integer getJobCatseries() {
		return jobCatseries;
	}

	public void setJobCatseries(Integer jobCatseries) {
		this.jobCatseries = jobCatseries;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	@Override
	public String toString() {
		return title;
	}

	@Override
	public boolean equals(Object obj) {
		return ((HrsGovJobSeries) obj).getId().equals(this.id);
	}

	public HrsGovJobG getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(HrsGovJobG jobGroup) {
		this.jobGroup = jobGroup;
	}

	public HrsGovJobSpec getJobSpecific() {
		return jobSpecific;
	}

	public void setJobSpecific(HrsGovJobSpec jobSpecific) {
		this.jobSpecific = jobSpecific;
	}

	public HrsGovJobCatgseries getJobCategories() {
		return jobCategories;
	}

	public void setJobCategories(HrsGovJobCatgseries jobCategories) {
		this.jobCategories = jobCategories;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
