package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BILL_ISSUE_DIG_CASH")
public class BillIssueDigCash implements Serializable {

	/**
	 * 
	 */
	//PAY_bill_details_seq.nextval
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
//	@SequenceGenerator(name = "G1", sequenceName = "PAY_bill_details_seq" ,allocationSize=1)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "ID_DIG_TYPE")
	private Integer idDigType;
	@Column(name = "cat1")
	private Double category1;
	@Column(name = "cat2")
	private Double category2;
	@Column(name = "cat3")
	private Double category3;
	@Column(name = "cat4")
	private Double category4;
	@Column(name = "cat5")
	private Double category5;
	@Column(name = "DAYS_MAX")
	private Integer daysMax;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdDigType() {
		return idDigType;
	}
	public void setIdDigType(Integer idDigType) {
		this.idDigType = idDigType;
	}
	
	public Integer getDaysMax() {
		return daysMax;
	}
	public void setDaysMax(Integer daysMax) {
		this.daysMax = daysMax;
	}
	public Double getCategory1() {
		return category1;
	}
	public void setCategory1(Double category1) {
		this.category1 = category1;
	}
	public Double getCategory2() {
		return category2;
	}
	public void setCategory2(Double category2) {
		this.category2 = category2;
	}
	public Double getCategory3() {
		return category3;
	}
	public void setCategory3(Double category3) {
		this.category3 = category3;
	}
	public Double getCategory4() {
		return category4;
	}
	public void setCategory4(Double category4) {
		this.category4 = category4;
	}
	public Double getCategory5() {
		return category5;
	}
	public void setCategory5(Double category5) {
		this.category5 = category5;
	}
	


}
