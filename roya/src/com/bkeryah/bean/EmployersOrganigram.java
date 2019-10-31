package com.bkeryah.bean;

import java.util.List;

import com.bkeryah.hr.managedBeans.Employer;

public class EmployersOrganigram {
	
	private Employer manager;
	private List<Employer> employersList;
	
	public EmployersOrganigram(Employer manager, List<Employer> employersList) {
		super();
		this.manager = manager;
		this.employersList = employersList;
	}
	
	public Employer getManager() {
		return manager;
	}
	
	public void setManager(Employer manager) {
		this.manager = manager;
	}
	
	public List<Employer> getEmployersList() {
		return employersList;
	}
	
	public void setEmployersList(List<Employer> employersList) {
		this.employersList = employersList;
	}

}
