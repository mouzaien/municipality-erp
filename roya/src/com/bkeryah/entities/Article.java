package com.bkeryah.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ARTICLE")
public class Article {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private int id;
	
	@Column(name = "sub_group_id")
	private Integer subGroupId;

	@Column(name = "GROUP_ID")
	private Integer groupId;

	@Column(name = "Item_Unite_Id")
	private int itemUniteId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CODE")
	private String code;

	@Column(name = "STRNO")
	private int strNo;

	@Column(name = "art_type")
	private Integer artType;

	@JoinColumn(insertable = false, updatable = false, name = "Item_Unite_Id")
	@ManyToOne(fetch = FetchType.EAGER)
	private ItemUnite itemUnite;

	@JoinColumn(insertable = false, updatable = false, name = "GROUP_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private ArticleGroup articleGroup;

	@JoinColumn(insertable = false, updatable = false, name = "sub_group_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private ArticleSubGroup articleSubGroup;// = new ArticleSubGroup();

	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private Set<ExchangeRequestDetails> exchangeRequestDetailsList;

	@JoinColumn(insertable = false, updatable = false, name = "STRNO")
	@ManyToOne(fetch = FetchType.LAZY)
	private WhsWarehouses whsWarehouses;

	@Transient
	private String unitName;
	
	@Transient
	private Integer qty;
	
	@Transient
	private Integer exchMasterId;
	
	@Transient
	private String exchMasterDate;
	
	@Formula("(select n.STRNAME from WHS_WAREHOUSES n where  n.STRNO = STRNO)")
	private String strName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getSubGroupId() {
		return subGroupId;
	}

	public void setSubGroupId(Integer subGroupId) {
		this.subGroupId = subGroupId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public int getItemUniteId() {
		return itemUniteId;
	}

	public void setItemUniteId(int itemUniteId) {
		this.itemUniteId = itemUniteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStrNo() {
		return strNo;
	}

	public void setStrNo(int strNo) {
		this.strNo = strNo;
	}

	public Integer getArtType() {
		return artType;
	}

	public void setArtType(Integer artType) {
		this.artType = artType;
	}

	public ItemUnite getItemUnite() {
		return itemUnite;
	}

	public void setItemUnite(ItemUnite itemUnite) {
		this.itemUnite = itemUnite;
	}

	public ArticleGroup getArticleGroup() {
		return articleGroup;
	}

	public void setArticleGroup(ArticleGroup articleGroup) {
		this.articleGroup = articleGroup;
	}

	public ArticleSubGroup getArticleSubGroup() {
		return articleSubGroup;
	}

	public void setArticleSubGroup(ArticleSubGroup articleSubGroup) {
		this.articleSubGroup = articleSubGroup;
	}

	public Set<ExchangeRequestDetails> getExchangeRequestDetailsList() {
		return exchangeRequestDetailsList;
	}

	public void setExchangeRequestDetailsList(Set<ExchangeRequestDetails> exchangeRequestDetailsList) {
		this.exchangeRequestDetailsList = exchangeRequestDetailsList;
	}

	public WhsWarehouses getWhsWarehouses() {
		return whsWarehouses;
	}

	public void setWhsWarehouses(WhsWarehouses whsWarehouses) {
		this.whsWarehouses = whsWarehouses;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getExchMasterId() {
		return exchMasterId;
	}

	public void setExchMasterId(Integer exchMasterId) {
		this.exchMasterId = exchMasterId;
	}

	public String getExchMasterDate() {
		return exchMasterDate;
	}

	public void setExchMasterDate(String exchMasterDate) {
		this.exchMasterDate = exchMasterDate;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}


}
