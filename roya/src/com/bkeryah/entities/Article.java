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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ARTICLE")
public class Article {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private int id;
	@Column(name = "group_id")
	private Integer groupId;
	@Column(name = "sub_group_id")
	private Integer subGroupId;
	@Column(name = "item_Unite_Id")
	private int itemUniteId;
	
	@Column(name = "name")
	private String name;
	@Column(name = "CODE")
	private String code;
	@Column(name = "strno")
	private Integer strNo;
	@Column(name = "art_type")
	private Integer artType = 1;

	// @JoinColumn(insertable=false,updatable=false,name="group_id")
	// @ManyToOne
	// private ArticleGroup articleGroup;

	
	@JoinColumn(insertable = false, updatable = false, name = "Item_Unite_Id")
	@ManyToOne
	private ItemUnite itemUnite;
	@JoinColumn(insertable = false, updatable = false, name = "sub_group_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private ArticleSubGroup articleSubGroup;// = new ArticleSubGroup();
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private Set<ExchangeRequestDetails> exchangeRequestDetailsList;
	
	
	@JoinColumn(insertable = false, updatable = false, name = "STRNO")
	@ManyToOne
	private WhsWarehouses whsWarehouses;

	

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSubGroupId() {
		return subGroupId;
	}

	// public ArticleGroup getArticleGroup() {
	// return articleGroup;
	// }

	public ArticleSubGroup getArticleSubGroup() {
		return articleSubGroup;
	}

	public WhsWarehouses getWhsWarehouses() {
		return whsWarehouses;
	}

	public void setWhsWarehouses(WhsWarehouses whsWarehouses) {
		this.whsWarehouses = whsWarehouses;
	}

	public void setSubGroupId(Integer subGroupId) {
		this.subGroupId = subGroupId;
	}

	// public void setArticleGroup(ArticleGroup articleGroup) {
	// this.articleGroup = articleGroup;
	// }

	public void setArticleSubGroup(ArticleSubGroup articleSubGroup) {
		this.articleSubGroup = articleSubGroup;
	}

	public int getItemUniteId() {
		return itemUniteId;
	}

	public ItemUnite getItemUnite() {
		return itemUnite;
	}

	public void setItemUniteId(int itemUniteId) {
		this.itemUniteId = itemUniteId;
	}

	public void setItemUnite(ItemUnite itemUnite) {
		this.itemUnite = itemUnite;
	}

	public Set<ExchangeRequestDetails> getExchangeRequestDetailsList() {
		return exchangeRequestDetailsList;
	}

	public void setExchangeRequestDetailsList(Set<ExchangeRequestDetails> exchangeRequestDetailsList) {
		this.exchangeRequestDetailsList = exchangeRequestDetailsList;
	}

	public Integer getStrNo() {
		return strNo;
	}

	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}

	public Integer getArtType() {
		return artType;
	}

	public void setArtType(Integer artType) {
		this.artType = artType;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

}
