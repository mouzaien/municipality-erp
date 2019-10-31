package com.bkeryah.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;

import java.sql.Date;

import javax.persistence.Column;

@Entity
@Table(name = "WHS_STOCK_OUT_MASTER")
public class StockOutMaster {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer stockMasterOutId;
	@Column(name = "STOCK_FIN_OUT_NO")
	private Integer stockFinOutNo;
	@Column(name = "STOCK_FIN_OUT_HDATE")
	private String stockFinOutHdate;
	@Column(name = "STOCK_FIN_OUT_GDATE")
	private Date stockFinOutGdate;
	@Column(name = "STOCK_FIN_YEARID")
	private Integer stockFinYearid;
	@Column(name = "STOCK_EMPNO_DONOR")
	private Integer stockEmpnoDonor;
	@Column(name = "STOCK_DEPTNO_SRC")
	private Integer stockDeptnoSrc;
	@Column(name = "STOCK_EMPNO_RECEIVER")
	private Integer stockEmpnoReceiver;
	@Column(name = "STOCK_MOVETYPE_NO")
	private Integer stockMovetypeNo;
	@Column(name = "STOCK_DOCUTYPE_NO")
	private Integer stockDocutypeNo;
	@Column(name = "STOCK_NO")
	private Integer stockNo;

	public StockOutMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StockOutMaster(Integer stockMasterOutId, Integer stockFinOutNo, String stockFinOutHdate,
			Date stockFinOutGdate, Integer stockFinYearid, Integer stockEmpnoDonor, Integer stockDeptnoSrc,
			Integer stockEmpnoReceiver, Integer stockMovetypeNo, Integer stockDocutypeNo, Integer stockNo) {
		super();
		this.stockMasterOutId = stockMasterOutId;
		this.stockFinOutNo = stockFinOutNo;
		this.stockFinOutHdate = stockFinOutHdate;
		this.stockFinOutGdate = stockFinOutGdate;
		this.stockFinYearid = stockFinYearid;
		this.stockEmpnoDonor = stockEmpnoDonor;
		this.stockDeptnoSrc = stockDeptnoSrc;
		this.stockEmpnoReceiver = stockEmpnoReceiver;
		this.stockMovetypeNo = stockMovetypeNo;
		this.stockDocutypeNo = stockDocutypeNo;
		this.stockNo = stockNo;
	}

	public Integer getStockMasterOutId() {
		return stockMasterOutId;
	}

	public void setStockMasterOutId(Integer stockMasterOutId) {
		this.stockMasterOutId = stockMasterOutId;
	}

	public Integer getStockFinOutNo() {
		return stockFinOutNo;
	}

	public void setStockFinOutNo(Integer stockFinOutNo) {
		this.stockFinOutNo = stockFinOutNo;
	}

	public String getStockFinOutHdate() {
		return stockFinOutHdate;
	}

	public void setStockFinOutHdate(String stockFinOutHdate) {
		this.stockFinOutHdate = stockFinOutHdate;
	}

	public Date getStockFinOutGdate() {
		return stockFinOutGdate;
	}

	public void setStockFinOutGdate(Date stockFinOutGdate) {
		this.stockFinOutGdate = stockFinOutGdate;
	}

	public Integer getStockFinYearid() {
		return stockFinYearid;
	}

	public void setStockFinYearid(Integer stockFinYearid) {
		this.stockFinYearid = stockFinYearid;
	}

	public Integer getStockEmpnoDonor() {
		return stockEmpnoDonor;
	}

	public void setStockEmpnoDonor(Integer stockEmpnoDonor) {
		this.stockEmpnoDonor = stockEmpnoDonor;
	}

	public Integer getStockDeptnoSrc() {
		return stockDeptnoSrc;
	}

	public void setStockDeptnoSrc(Integer stockDeptnoSrc) {
		this.stockDeptnoSrc = stockDeptnoSrc;
	}

	public Integer getStockEmpnoReceiver() {
		return stockEmpnoReceiver;
	}

	public void setStockEmpnoReceiver(Integer stockEmpnoReceiver) {
		this.stockEmpnoReceiver = stockEmpnoReceiver;
	}

	public Integer getStockMovetypeNo() {
		return stockMovetypeNo;
	}

	public void setStockMovetypeNo(Integer stockMovetypeNo) {
		this.stockMovetypeNo = stockMovetypeNo;
	}

	public Integer getStockDocutypeNo() {
		return stockDocutypeNo;
	}

	public void setStockDocutypeNo(Integer stockDocutypeNo) {
		this.stockDocutypeNo = stockDocutypeNo;
	}

	public Integer getStockNo() {
		return stockNo;
	}

	public void setStockNo(Integer stockNo) {
		this.stockNo = stockNo;
	}

}