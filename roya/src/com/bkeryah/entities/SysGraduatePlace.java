package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS104")
public class SysGraduatePlace implements Serializable{
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;
	@Column(name = "NAME")
	private String graduatedPlaceName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGraduatedPlaceName() {
		return graduatedPlaceName;
	}
	public void setGraduatedPlaceName(String graduatedPlaceName) {
		this.graduatedPlaceName = graduatedPlaceName;
	}
	
	
}
