package com.bkeryah.entities;

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
@Table(name = "SYS_TITLE")
public class SysTitle {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;
	@Column(name = "TITLE")
	private String title;
//	@OneToMany(mappedBy="userTitle",fetch= FetchType.LAZY )
//	 private Set<ArcUsers> userSet ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
//	public Set<ArcUsers> getUserSet() {
//		return userSet;
//	}
//	public void setUserSet(Set<ArcUsers> userSet) {
//		this.userSet = userSet;
//	}
	

}
