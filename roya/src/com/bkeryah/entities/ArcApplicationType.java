package com.bkeryah.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ARC_APPLICATION_TYPE")
public class ArcApplicationType {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;
	@Column(name = "TYPE")
	private String type;
	@OneToMany(mappedBy = "arcApplicationType")
	private Set<ArcRecords> records;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Set<ArcRecords> getRecords() {
		return records;
	}
	public void setRecords(Set<ArcRecords> records) {
		this.records = records;
	}

	
}
