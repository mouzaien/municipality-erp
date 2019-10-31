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
@Table(name="WRK_LETTER_TO")
public class WrkLetterTo {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "F1")
	private Integer f1;
	@Column(name = "F2")
	private Integer f2;
	@OneToMany(mappedBy="wrkLetterTo")
	private Set<ArcRecords> records;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getF1() {
		return f1;
	}
	public void setF1(Integer f1) {
		this.f1 = f1;
	}
	public Integer getF2() {
		return f2;
	}
	public void setF2(Integer f2) {
		this.f2 = f2;
	}
	public Set<ArcRecords> getRecords() {
		return records;
	}
	public void setRecords(Set<ArcRecords> records) {
		this.records = records;
	}
	
	
}
