package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="Hrs_Salary_Scale")
public class HrsSalaryScale {

	@EmbeddedId 
    private HrsSalaryScaleId id;
	
	@Column(name = "rankTitle")
	private String rankName;
	
	@Column(name = "BSCSAL")
	private Integer  basicSalary	;
	
	@Column(name = "HOURLYPRICE")
	private Integer  hourlyPrice	;
	
	@Column(name = "Trans")
	private Integer  transfer	;
	
	@Column(name = "MANDIN")
	private Integer  mandatorIn	;
	
	@Column(name = "MANDOUT")
	private Integer  mandatorOut	;
	
	@Column(name = "YEARLYINC")
	private Integer  yearlyInc	;
	
	@Column(name = "CBY")
	private Integer  createdBy	;
	
	@Column(name = "CIN")
	private Date  createdIn	;
	
	@Column(name = "CLSSCOUNT")
	private Integer  classCount	;

	public HrsSalaryScaleId getId() {
		return id;
	}

	public void setId(HrsSalaryScaleId id) {
		this.id = id;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public Integer getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(Integer basicSalary) {
		this.basicSalary = basicSalary;
	}

	public Integer getHourlyPrice() {
		return hourlyPrice;
	}

	public void setHourlyPrice(Integer hourlyPrice) {
		this.hourlyPrice = hourlyPrice;
	}

	public Integer getTransfer() {
		return transfer;
	}

	public void setTransfer(Integer transfer) {
		this.transfer = transfer;
	}

	public Integer getMandatorIn() {
		return mandatorIn;
	}

	public void setMandatorIn(Integer mandatorIn) {
		this.mandatorIn = mandatorIn;
	}

	public Integer getMandatorOut() {
		return mandatorOut;
	}

	public void setMandatorOut(Integer mandatorOut) {
		this.mandatorOut = mandatorOut;
	}

	public Integer getYearlyInc() {
		return yearlyInc;
	}

	public void setYearlyInc(Integer yearlyInc) {
		this.yearlyInc = yearlyInc;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}

	public Integer getClassCount() {
		return classCount;
	}

	public void setClassCount(Integer classCount) {
		this.classCount = classCount;
	}
	
	
	
	
	
	
	
	
	
}
