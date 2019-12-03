package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WHS_WAREHOUSES")
public class WhsWarehouses implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "STRNO")
	private int storeNumber;
	@Column(name = "STRNAME")
	private String storeName;
	@Column(name = "STRTYPE")
	private String storeType;
	@Column(name = "STRSTS")
	private String storeStatus;
	@Column(name = "STRKEPR")
	private String storeManager;
	@Column(name = "INVYDATE")
	private String invyDate;
	@Column(name = "HEJRAD")
	private String hejriDate;
	@Column(name = "inventory_blocked")
	private Integer invIsBlocked;
	@Column(name = "STORE_BOSS_ID")
	private Integer storeBossId;

	public Integer getInvIsBlocked() {
		return invIsBlocked;
	}

	public void setInvIsBlocked(Integer invIsBlocked) {
		this.invIsBlocked = invIsBlocked;
	}

	@Column(name = "store_dean_id")
	private Integer storeUserId;

	public int getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(int storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

	public String getStoreManager() {
		return storeManager;
	}

	public void setStoreManager(String storeManager) {
		this.storeManager = storeManager;
	}

	public String getInvyDate() {
		return invyDate;
	}

	public void setInvyDate(String invyDate) {
		this.invyDate = invyDate;
	}

	public String getHejriDate() {
		return hejriDate;
	}

	public void setHejriDate(String hejriDate) {
		this.hejriDate = hejriDate;
	}

	public Integer getStoreUserId() {
		return storeUserId;
	}

	public void setStoreUserId(Integer storeUserId) {
		this.storeUserId = storeUserId;
	}

	public Integer getStoreBossId() {
		return storeBossId;
	}

	public void setStoreBossId(Integer storeBossId) {
		this.storeBossId = storeBossId;
	}
}
