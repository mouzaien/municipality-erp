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
@Table(name = "WRK_LETTER_FROM")
public class WrkLetterFrom {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;
	@Column(name = "NAME")
	private String letterFromName;
//	@OneToMany(mappedBy = "wrkLetterFrom")
//	private Set<ArcRecords> records;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLetterFromName() {
		return letterFromName;
	}
	public void setLetterFromName(String letterFromName) {
		this.letterFromName = letterFromName;
	}
//	public Set<ArcRecords> getRecords() {
//		return records;
//	}
//	public void setRecords(Set<ArcRecords> records) {
//		this.records = records;
//	}
		
	
}
