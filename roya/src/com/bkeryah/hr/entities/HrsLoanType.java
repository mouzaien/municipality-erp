package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bkeryah.entities.ArcUsers;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.util.List;

import javax.persistence.Column;

@Entity
@Table(name = "HRS_LOAN_TYPE")
public class HrsLoanType {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "LOAN_TYPE_ID")
	private Integer loanTypeId;
	@Column(name = "LOAN_TYPE_NAME")
	private String loanTypeName;
	@Column(name = "IS_ACTIVE_Y_N")
	private String isActiveYN;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
	private List<HrsLoan> loans;

	public Integer getLoanTypeId() {
		return loanTypeId;
	}

	public void setLoanTypeId(Integer loanTypeId) {
		this.loanTypeId = loanTypeId;
	}

	public String getLoanTypeName() {
		return loanTypeName;
	}

	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}

	public String getIsActiveYN() {
		return isActiveYN;
	}

	public void setIsActiveYN(String isActiveYN) {
		this.isActiveYN = isActiveYN;
	}

	public List<HrsLoan> getLoans() {
		return loans;
	}

	public void setLoans(List<HrsLoan> loans) {
		this.loans = loans;
	}
}