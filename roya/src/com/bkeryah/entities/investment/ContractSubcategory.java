package com.bkeryah.entities.investment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CONTRACT_SUBCATEGORY")
public class ContractSubcategory {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CODE")
	private Integer code;
	@Column(name = "NAME")
	private String name;
	@Column(name = "CONTRACT_MAIN_ID")
	private Integer contMainCategoryid;

	@Formula("(SELECT M.NAME FROM CONTRACT_MAIN_CATEGORY M WHERE M.ID =CONTRACT_MAIN_ID )")
	private String contMainCategoryName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getContMainCategoryid() {
		return contMainCategoryid;
	}

	public void setContMainCategoryid(Integer contMainCategoryid) {
		this.contMainCategoryid = contMainCategoryid;
	}

	public String getContMainCategoryName() {
		return contMainCategoryName;
	}

	public void setContMainCategoryName(String contMainCategoryName) {
		this.contMainCategoryName = contMainCategoryName;
	}

}
