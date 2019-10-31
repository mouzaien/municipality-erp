package com.bkeryah.entities.investment;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bkeryah.model.User;

@Entity
@Table(name = "INV_CLAUSES")              
public class Clause {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NUM_CLAUSE")
	private Integer numClause;
	@Column(name = "NAME")
	private String name;
	@Column(name = "DESCRIPTION")
	private String description;
//	@ManyToMany(mappedBy="clausesList")
//	private List<Contract> contractsList;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNumClause() {
		return numClause;
	}
	public void setNumClause(Integer numClause) {
		this.numClause = numClause;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public List<Contract> getContractsList() {
//		return contractsList;
//	}
//	public void setContractsList(List<Contract> contractsList) {
//		this.contractsList = contractsList;
//	}

	@Override
	public String toString() {
		return numClause + "-" + name;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Clause) obj).getId().equals(this.id);
	}
}