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
@Table(name = "BILL_ISSUE_CASH")
public class BillIssueCash implements Serializable {

	/**
	 * 
	 */
	//PAY_bill_details_seq.nextval
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
//	@SequenceGenerator(name = "G1", sequenceName = "PAY_bill_details_seq" ,allocationSize=1)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "SLIDE_NAME")
	private String slideName;
	@Column(name = "CATEGORY")
	private Integer category;
	@Column(name = "ID_BILL_DETAILL")
	private Integer idBillDetail;
	@Column(name = "MIN_CASH")
	private Double minimumCash;
	@Column(name = "MAX_CASH")
	private Double maximumCash;
	@Column(name = "CAT1")
	private Double catrgory1;
	@Column(name = "CAT2")
	private Double catrgory2;
	@Column(name = "CAT3")
	private Double catrgory3;
	@Column(name = "CAT4")
	private Double catrgory4;
	@Column(name = "CAT5")
	private Double catrgory5;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSlideName() {
		return slideName;
	}
	public void setSlideName(String slideName) {
		this.slideName = slideName;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Integer getIdBillDetail() {
		return idBillDetail;
	}
	public void setIdBillDetail(Integer idBillDetail) {
		this.idBillDetail = idBillDetail;
	}

	
	public Double getMinimumCash() {
		return minimumCash;
	}
	public void setMinimumCash(Double minimumCash) {
		this.minimumCash = minimumCash;
	}
	public Double getMaximumCash() {
		return maximumCash;
	}
	public void setMaximumCash(Double maximumCash) {
		this.maximumCash = maximumCash;
	}
	public Double getCatrgory1() {
		return catrgory1;
	}
	public void setCatrgory1(Double catrgory1) {
		this.catrgory1 = catrgory1;
	}
	public Double getCatrgory2() {
		return catrgory2;
	}
	public void setCatrgory2(Double catrgory2) {
		this.catrgory2 = catrgory2;
	}
	public Double getCatrgory3() {
		return catrgory3;
	}
	public void setCatrgory3(Double catrgory3) {
		this.catrgory3 = catrgory3;
	}
	public Double getCatrgory4() {
		return catrgory4;
	}
	public void setCatrgory4(Double catrgory4) {
		this.catrgory4 = catrgory4;
	}
	public Double getCatrgory5() {
		return catrgory5;
	}
	public void setCatrgory5(Double catrgory5) {
		this.catrgory5 = catrgory5;
	}
	
	

}
