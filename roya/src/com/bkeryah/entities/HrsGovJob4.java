package com.bkeryah.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bkeryah.hr.entities.HrsJobCreation;

@Entity
@Table(name="HRS_GOV_JOB_4")
public class HrsGovJob4 {

	@Id
	@Column(name = "ID")
	private String jobCode;
	
	@Column(name = "TITLE")
	private String jobName;
	
	@Column(name = "REF1")
	private String referenceGov1;
	
	@Column(name = "REF2")
	private String referenceGov2;
	
	@Column(name = "REF3")
	private String referenceGov3;
	
	
	@Column(name = "CBY")
	private Integer createdBy;
	@ManyToOne(fetch =FetchType.LAZY)
	@JoinColumn(name="CBY" ,referencedColumnName="USER_ID" ,insertable = false,updatable = false)
	private ArcUsers arcUser ;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="gov4")
	private Set<HrsJobCreation> jobSet ;
	
	
	
	@Column(name = "CIN")
	private Date createDate;
	
	@Column(name = "CATCOD")
	private Integer CATegoryId;
	@Column(name = "	EVCAT")
	private Integer evaluateCategory;
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getReferenceGov1() {
		return referenceGov1;
	}
	public void setReferenceGov1(String referenceGov1) {
		this.referenceGov1 = referenceGov1;
	}
	public String getReferenceGov2() {
		return referenceGov2;
	}
	public void setReferenceGov2(String referenceGov2) {
		this.referenceGov2 = referenceGov2;
	}
	public String getReferenceGov3() {
		return referenceGov3;
	}
	public void setReferenceGov3(String referenceGov3) {
		this.referenceGov3 = referenceGov3;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public ArcUsers getArcUser() {
		return arcUser;
	}
	public void setArcUser(ArcUsers arcUser) {
		this.arcUser = arcUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getCATegoryId() {
		return CATegoryId;
	}
	public void setCATegoryId(Integer cATegoryId) {
		CATegoryId = cATegoryId;
	}
	public Integer getEvaluateCategory() {
		return evaluateCategory;
	}
	public void setEvaluateCategory(Integer evaluateCategory) {
		this.evaluateCategory = evaluateCategory;
	}
	
	
	
	
}
