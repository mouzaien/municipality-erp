package com.bkeryah.penalties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "REQ_FINES_DETAILS")
public class ReqFinesDetails {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;
	@Column(name = "FINE_NO")
	private Integer fineNo;
	@Column(name = "FINE_CODE")
	private Integer fineCode;
	@Column(name = "FINE_VALUE")
	private Integer fineValue;
	@Column(name = "FINE_DESC_2")
	private String fineDesc2;
	@Column(name = "FINE_COUNT")
	private Integer fineCount;
	@Column(name = "FINE_COUNT_NO")
	private Integer fineCountNo;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FINE_CODE", referencedColumnName = "ID", insertable = false, updatable = false)
	ReqFinesSetup reqFinesSetup;
	@ManyToOne
	@JoinColumn(name = "FINE_NO", referencedColumnName = "FINE_NO", insertable = false, updatable = false)
	private ReqFinesMaster reqFinesMaster;
	@Transient
	private double fineTotalValue;

	public ReqFinesSetup getReqFinesSetup() {
		return reqFinesSetup;
	}

	public void setReqFinesSetup(ReqFinesSetup reqFinesSetup) {
		this.reqFinesSetup = reqFinesSetup;
	}

	public Integer getFineNo() {
		return fineNo;
	}

	public void setFineNo(Integer fineNo) {
		this.fineNo = fineNo;
	}

	public Integer getFineCode() {
		return fineCode;
	}

	public void setFineCode(Integer fineCode) {
		this.fineCode = fineCode;
	}

	public Integer getFineValue() {
		return fineValue;
	}

	public void setFineValue(Integer fineValue) {
		this.fineValue = fineValue;
	}

	public String getFineDesc2() {
		return fineDesc2;
	}

	public void setFineDesc2(String fineDesc2) {
		this.fineDesc2 = fineDesc2;
	}

	public Integer getFineCount() {
		return fineCount;
	}

	public void setFineCount(Integer fineCount) {
		this.fineCount = fineCount;
	}

	public Integer getFineCountNo() {
		return fineCountNo;
	}

	public void setFineCountNo(Integer fineCountNo) {
		this.fineCountNo = fineCountNo;
	}

	public ReqFinesMaster getReqFinesMaster() {
		return reqFinesMaster;
	}

	public void setReqFinesMaster(ReqFinesMaster reqFinesMaster) {
		this.reqFinesMaster = reqFinesMaster;
	}

	public double getFineTotalValue() {
		if (fineValue != null && fineCount != null)
			return fineValue * fineCount;
		return 0;
	}

	public void setFineTotalValue(double fineTotalValue) {
		this.fineTotalValue = fineTotalValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
