package com.bkeryah.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PROJECTS_CONTRACTS")
public class ProjectContract {

	@Id
	@Column(name = "CONTRACT_ID")
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private int id;
	@Column(name = "CONTRACT_OWNER")
	private String owner;
	@Column(name = "CONTRACT_DURATION")
	private Integer duration;
	@Column(name = "CONTRACT_DURATION_UNIT")
	private Integer durationUnit;
	@Column(name = "CONTRACT_SIGN_DATE")
	private String signDate;
	@Column(name = "CONTRACT_ASSIGN_DATE")
	private String assignDate;
	@Column(name = "CONTRACT_SITE_RECIEVE_DATE")
	private String siteRecieveDate;
	@Column(name = "CONTRACT_VALUE")
	private double value;
	@Column(name = "CONTRACT_YEAR" ,nullable=true)
	private Integer year;
	@Column(name = "PROJECT_ID")
	private int projectId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contract")
	private Set<ProjectExtract> extracts = new HashSet<ProjectExtract>();
	
	@ManyToOne()
	@JoinColumn(name = "PROJECT_ID",insertable=false,updatable=false)
	private Project project;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getDurationUnit() {
		return durationUnit;
	}
	public void setDurationUnit(Integer durationUnit) {
		this.durationUnit = durationUnit;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	public String getSiteRecieveDate() {
		return siteRecieveDate;
	}
	public void setSiteRecieveDate(String siteRecieveDate) {
		this.siteRecieveDate = siteRecieveDate;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Set<ProjectExtract> getExtracts() {
		return extracts;
	}
	public void setExtracts(Set<ProjectExtract> extracts) {
		this.extracts = extracts;
	}
	
	
	
	
}
