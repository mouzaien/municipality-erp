package com.bkeryah.entities;

import java.io.Serializable;
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
@Table(name = "WRK_JOBS")
public class WrkJobs implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;
	
	@Column(name = "JOB_NAME" ,nullable=false)
	private String jobName;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name = "SEC_ID")
	private int sectionId;
	
	@OneToMany(fetch= FetchType.LAZY)
	 private Set<ArcUsers> userSet ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public Set<ArcUsers> getUserSet() {
		return userSet;
	}
	public void setUserSet(Set<ArcUsers> userSet) {
		this.userSet = userSet;
	}
	
	
	
}
