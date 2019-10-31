package com.bkeryah.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bkeryah.applicationStatus.ProjectStatus;

@Entity
@Table(name = "PROJECTS")
public class Project {

	@Id
	@Column(name = "PROJECT_ID")
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private int id;
	@Column(name = "PROJECT_NUMBER")
	private String number;
	@Column(name = "PROJECT_NAME")
	private String name;
	
	@Column(name = "STATUS")
	private ProjectStatus stauts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	private Set<ProjectContract> contracts = new HashSet<ProjectContract>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<ProjectContract> getContracts() {
		return contracts;
	}
	public void setContracts(Set<ProjectContract> contracts) {
		this.contracts = contracts;
	}
	public ProjectStatus getStauts() {
		return stauts;
	}
	public void setStauts(ProjectStatus stauts) {
		this.stauts = stauts;
	}


	
	
}
