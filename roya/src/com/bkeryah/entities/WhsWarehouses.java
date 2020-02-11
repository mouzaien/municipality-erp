package com.bkeryah.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "WHS_WAREHOUSES")
public class WhsWarehouses implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "STRNO")
	private Integer storeNumber;
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
	@Column(name = "STORE_DEAN_ID")
	private Integer storeDeanId;
	@Column(name = "STORE_TYPE")
	private Integer strType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "whsWarehouses")
	private List<Article> articles;

	public Integer getInvIsBlocked() {
		return invIsBlocked;
	}

	public void setInvIsBlocked(Integer invIsBlocked) {
		this.invIsBlocked = invIsBlocked;
	}

	public Integer getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(Integer storeNumber) {
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

	public Integer getStoreBossId() {
		return storeBossId;
	}

	public void setStoreBossId(Integer storeBossId) {
		this.storeBossId = storeBossId;
	}

	public Integer getStrType() {
		return strType;
	}

	public void setStrType(Integer strType) {
		this.strType = strType;
	}

	public Integer getStoreDeanId() {
		return storeDeanId;
	}

	public void setStoreDeanId(Integer storeDeanId) {
		this.storeDeanId = storeDeanId;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
