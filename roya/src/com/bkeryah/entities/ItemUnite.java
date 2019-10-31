package com.bkeryah.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TDR_ITEMUNIT")
public class ItemUnite {
	@Id
	@Column(name = "ITEMUNITID")
	private long id;
	@Column(name = "ITEMUNITNAME")
	private String name;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="itemUnite")
	private List<Article> articles;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
