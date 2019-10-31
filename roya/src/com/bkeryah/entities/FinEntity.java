package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FIN_ENTITY")
public class FinEntity {

	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "RELATEDENTITYID")
	private Integer finEntityId;
	@Column(name = "RELATEDENTITYNAME")
	private String finEntityName;
	@Column(name = "TYPESOFBENIFICIARY")
	private String finEntityTypeUser;
	
	
	public FinEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public FinEntity(Integer finEntityId, String finEntityName, String finEntityTypeUser) {
		super();
		this.finEntityId = finEntityId;
		this.finEntityName = finEntityName;
		this.finEntityTypeUser = finEntityTypeUser;
	}


	public Integer getFinEntityId() {
		return finEntityId;
	}


	public void setFinEntityId(Integer finEntityId) {
		this.finEntityId = finEntityId;
	}


	public String getFinEntityName() {
		return finEntityName;
	}


	public void setFinEntityName(String finEntityName) {
		this.finEntityName = finEntityName;
	}


	public String getFinEntityTypeUser() {
		return finEntityTypeUser;
	}


	public void setFinEntityTypeUser(String finEntityTypeUser) {
		this.finEntityTypeUser = finEntityTypeUser;
	}
	
}
