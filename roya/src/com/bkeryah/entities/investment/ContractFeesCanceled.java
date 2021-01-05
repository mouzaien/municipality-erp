package com.bkeryah.entities.investment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CONTRACT_FEES_CANCELED")
public class ContractFeesCanceled {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CONRACT_FEES_ID")
	private Integer canceledFeesId; // رقم القسط الملغى
	@Column(name = "CONTRACT_ID")
	private Integer contractId;// رقم العقد
	@Column(name = "OLD_FACT_ID")
	private String factId;// رقم الفاتورة الملغاة
	@Column(name = "CANCEL_H_DATE")
	private String cancelHDate;// تاريخ الألغاء هجري
	@Column(name = "CANCEL_G_DATE")
	private Date cancelGDate;// تاريخ الإلغاء ميلادي
	@Column(name = "STATUS")
	private Integer status; // 1 ملغاة
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCanceledFeesId() {
		return canceledFeesId;
	}
	public void setCanceledFeesId(Integer canceledFeesId) {
		this.canceledFeesId = canceledFeesId;
	}
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public String getFactId() {
		return factId;
	}
	public void setFactId(String factId) {
		this.factId = factId;
	}
	public String getCancelHDate() {
		return cancelHDate;
	}
	public void setCancelHDate(String cancelHDate) {
		this.cancelHDate = cancelHDate;
	}
	public Date getCancelGDate() {
		return cancelGDate;
	}
	public void setCancelGDate(Date cancelGDate) {
		this.cancelGDate = cancelGDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
