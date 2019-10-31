package com.bkeryah.model;

import java.io.InputStream;

public class StockEntryModel {
	private Integer num;
	private Integer stockEntryNo;
	private String strName;
	private String suppName;
	private String entryHDate;
	private Integer entryByNo;
	private String entryByDAte;
	private Integer entryNoticeNo;
	private String entryNoticeDate;
	private Integer qty;
	private float total;
	private String reqName;
	private InputStream REQ_SIGN;
	private String REQ_SIGN_DATE;
	private String storeMgr;
	private InputStream STORE_MGR_SIGN;
	private String STORE_MGR_SIGN_DATE;
	private String storeDeanName;
	private InputStream STORE_DEAN_SIGN;
	private String STORE_DEAN_SIGN_DATE;

	private String artCode;
	private String artName;
	private String unitName;
	private float unitPrice;
	private float totalPrice;
	private String notes;
	private String amountInLetters;
	private Integer index;

	public Integer getStockEntryNo() {
		return stockEntryNo;
	}

	public void setStockEntryNo(Integer stockEntryNo) {
		this.stockEntryNo = stockEntryNo;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getSuppName() {
		return suppName;
	}

	public void setSuppName(String suppName) {
		this.suppName = suppName;
	}

	public String getEntryHDate() {
		return entryHDate;
	}

	public void setEntryHDate(String entryHDate) {
		this.entryHDate = entryHDate;
	}

	public Integer getEntryByNo() {
		return entryByNo;
	}

	public void setEntryByNo(Integer entryByNo) {
		this.entryByNo = entryByNo;
	}

	public String getEntryByDAte() {
		return entryByDAte;
	}

	public void setEntryByDAte(String entryByDAte) {
		this.entryByDAte = entryByDAte;
	}

	public Integer getEntryNoticeNo() {
		return entryNoticeNo;
	}

	public void setEntryNoticeNo(Integer entryNoticeNo) {
		this.entryNoticeNo = entryNoticeNo;
	}

	public String getEntryNoticeDate() {
		return entryNoticeDate;
	}

	public void setEntryNoticeDate(String entryNoticeDate) {
		this.entryNoticeDate = entryNoticeDate;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getReqName() {
		return reqName;
	}

	public void setReqName(String reqName) {
		this.reqName = reqName;
	}

	public InputStream getREQ_SIGN() {
		return REQ_SIGN;
	}

	public void setREQ_SIGN(InputStream rEQ_SIGN) {
		REQ_SIGN = rEQ_SIGN;
	}

	public String getREQ_SIGN_DATE() {
		return REQ_SIGN_DATE;
	}

	public void setREQ_SIGN_DATE(String rEQ_SIGN_DATE) {
		REQ_SIGN_DATE = rEQ_SIGN_DATE;
	}

	public String getStoreMgr() {
		return storeMgr;
	}

	public void setStoreMgr(String storeMgr) {
		this.storeMgr = storeMgr;
	}

	public InputStream getSTORE_MGR_SIGN() {
		return STORE_MGR_SIGN;
	}

	public void setSTORE_MGR_SIGN(InputStream sTORE_MGR_SIGN) {
		STORE_MGR_SIGN = sTORE_MGR_SIGN;
	}

	public String getSTORE_MGR_SIGN_DATE() {
		return STORE_MGR_SIGN_DATE;
	}

	public void setSTORE_MGR_SIGN_DATE(String sTORE_MGR_SIGN_DATE) {
		STORE_MGR_SIGN_DATE = sTORE_MGR_SIGN_DATE;
	}

	public String getStoreDeanName() {
		return storeDeanName;
	}

	public void setStoreDeanName(String storeDeanName) {
		this.storeDeanName = storeDeanName;
	}

	public InputStream getSTORE_DEAN_SIGN() {
		return STORE_DEAN_SIGN;
	}

	public void setSTORE_DEAN_SIGN(InputStream sTORE_DEAN_SIGN) {
		STORE_DEAN_SIGN = sTORE_DEAN_SIGN;
	}

	public String getSTORE_DEAN_SIGN_DATE() {
		return STORE_DEAN_SIGN_DATE;
	}

	public void setSTORE_DEAN_SIGN_DATE(String sTORE_DEAN_SIGN_DATE) {
		STORE_DEAN_SIGN_DATE = sTORE_DEAN_SIGN_DATE;
	}

	public String getArtCode() {
		return artCode;
	}

	public void setArtCode(String artCode) {
		this.artCode = artCode;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getIndex() {
		return index;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getAmountInLetters() {
		return amountInLetters;
	}

	public void setAmountInLetters(String amountInLetters) {
		this.amountInLetters = amountInLetters;
	}
}
