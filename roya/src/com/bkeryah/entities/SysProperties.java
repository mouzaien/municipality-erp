package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_PROPERTIES")
public class SysProperties implements Serializable {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "PROP_NAME")
	private String name;
	
	@Column(name = "PROP_VALUE")
	private String value;
	
	@Column(name = "ARABIC_DESC")
	private String description;
	
	@Column(name = "CAT_ID")
	private Integer categoryId;
	
	@Column(name = "TYPE_PROPERTY")
	private Integer typeProperty;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getTypeProperty() {
		return typeProperty;
	}

	public void setTypeProperty(Integer typeProperty) {
		this.typeProperty = typeProperty;
	}
	
	
	
	
	
	
}
