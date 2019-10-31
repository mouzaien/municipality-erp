package com.bkeryah.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "WHS_STOCK_ENTRY_MASTER")
public class StockEntryMaster {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer stockEntryMasterId;
	@Column(name = "STOCK_FIN_ENTRY_NO")
	private Integer stockFinEntryNo;
	@Column(name = "STOCK_FIN_ENTRY_HDATE")
	private String stockFinEntryHdate;
	@Column(name = "STOCK_FIN_ENTRY_GDATE")
	private Date stockFinEntryGdate;
	@Column(name = "STOCK_BUY_NO")
	private Integer stockBuyNo;
	@Column(name = "STOCK_BUY_DATE")
	private String stockBuyDate;
	@Column(name = "STOCK_NOTICE_NO")
	private Integer stockNoticeNo;
	@Column(name = "STOCK_NOTICE_DATE")
	private String stockNoticeDate;
	@Column(name = "STOCK_SUPPLIER_ID")
	private Integer stockSupplierId;
	@Column(name = "STOCK_FIN_YEARID")
	private Integer stockFinYearid;
	@Column(name = "STOCK_EMPNO_DONOR")
	private Integer stockEmpnoDonor;
	@Column(name = "STOCK_DEPTNO_SRC")
	private Integer stockDeptnoSrc;
	@Column(name = "STOCK_MOVETYPE_NO")
	private Integer stockMovetypeNo;
	@Column(name = "STOCK_DOCUTYPE_NO")
	private Integer stockDocutypeNo;
	@Column(name = "STOCK_NO")
	private Integer stockNo;
	@Column(name = "userid")
	private Integer userId;
	@Column(name = "stockEntryStatus")
	private String stockEntryStatus;
	@Column(name = "SERIALNUMBER")
	private Integer serialNumber;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "stockEntryMaster", cascade = CascadeType.ALL)
	private List<StockInDetails> stockInDetailsList;

	@Transient
	private String supplierName;
	@Transient
	private ArcUsers arcUser;
	@Transient
	private Integer recordId;

	public StockEntryMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StockEntryMaster(Integer stockEntryMasterId, Integer stockFinEntryNo, String stockFinEntryHdate,
			Date stockFinEntryGdate, Integer stockBuyNo, String stockBuyDate, Integer stockNoticeNo,
			String stockNoticeDate, Integer stockSupplierId, Integer stockFinYearid, Integer stockEmpnoDonor,
			Integer stockDeptnoSrc, Integer stockMovetypeNo, Integer stockDocutypeNo, Integer stockNo) {
		super();
		this.stockEntryMasterId = stockEntryMasterId;
		this.stockFinEntryNo = stockFinEntryNo;
		this.stockFinEntryHdate = stockFinEntryHdate;
		this.stockFinEntryGdate = stockFinEntryGdate;
		this.stockBuyNo = stockBuyNo;
		this.stockBuyDate = stockBuyDate;
		this.stockNoticeNo = stockNoticeNo;
		this.stockNoticeDate = stockNoticeDate;
		this.stockSupplierId = stockSupplierId;
		this.stockFinYearid = stockFinYearid;
		this.stockEmpnoDonor = stockEmpnoDonor;
		this.stockDeptnoSrc = stockDeptnoSrc;
		this.stockMovetypeNo = stockMovetypeNo;
		this.stockDocutypeNo = stockDocutypeNo;
		this.stockNo = stockNo;
	}

	public String getStockEntryStatus() {
		return stockEntryStatus;
	}

	public void setStockEntryStatus(String stockEntryStatus) {
		this.stockEntryStatus = stockEntryStatus;
	}

	public ArcUsers getArcUser() {
		return arcUser;
	}

	public void setArcUser(ArcUsers arcUser) {
		this.arcUser = arcUser;
	}

	public Integer getStockEntryMasterId() {
		return stockEntryMasterId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setStockEntryMasterId(Integer stockEntryMasterId) {
		this.stockEntryMasterId = stockEntryMasterId;
	}

	public Integer getStockFinEntryNo() {
		return stockFinEntryNo;
	}

	public void setStockFinEntryNo(Integer stockFinEntryNo) {
		this.stockFinEntryNo = stockFinEntryNo;
	}

	public String getStockFinEntryHdate() {
		return stockFinEntryHdate;
	}

	public void setStockFinEntryHdate(String stockFinEntryHdate) {
		this.stockFinEntryHdate = stockFinEntryHdate;
	}

	public Date getStockFinEntryGdate() {
		return stockFinEntryGdate;
	}

	public void setStockFinEntryGdate(Date stockFinEntryGdate) {
		this.stockFinEntryGdate = stockFinEntryGdate;
	}

	public List<StockInDetails> getStockInDetailsList() {
		return stockInDetailsList;
	}

	public void setStockInDetailsList(List<StockInDetails> stockInDetailsList) {
		this.stockInDetailsList = stockInDetailsList;
	}

	public Integer getStockBuyNo() {
		return stockBuyNo;
	}

	public void setStockBuyNo(Integer stockBuyNo) {
		this.stockBuyNo = stockBuyNo;
	}

	public String getStockBuyDate() {
		return stockBuyDate;
	}

	public void setStockBuyDate(String stockBuyDate) {
		this.stockBuyDate = stockBuyDate;
	}

	public Integer getStockNoticeNo() {
		return stockNoticeNo;
	}

	public void setStockNoticeNo(Integer stockNoticeNo) {
		this.stockNoticeNo = stockNoticeNo;
	}

	public String getStockNoticeDate() {
		return stockNoticeDate;
	}

	public void setStockNoticeDate(String stockNoticeDate) {
		this.stockNoticeDate = stockNoticeDate;
	}

	public Integer getStockSupplierId() {
		return stockSupplierId;
	}

	public void setStockSupplierId(Integer stockSupplierId) {
		this.stockSupplierId = stockSupplierId;
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

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

}