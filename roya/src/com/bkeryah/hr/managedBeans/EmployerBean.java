//package com.bkeryah.hr.managedBeans;
//
//import java.io.Serializable;
//
//import javax.faces.bean.ViewScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//
//import com.bkeryah.service.IDataAccessService;
//
//import utilities.Utils;
//
//@Named
//@ViewScoped()
//public class EmployerBean implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	@Inject
//	private IDataAccessService dataAccessService;
//
//	public Employer employer = new Employer();
//
//	public Employer getEmployer() {
//		return employer;
//	}
//
//	public void setEmployer(Employer employer) {
//		this.employer = employer;
//	}
//
//	public String getReturnVacData() {
//
//		employer = dataAccessService.getReturnVacData(Utils.findCurrentUser().getVuser_id());
//
//
//
//
//
//		return null;
//	}
//
//	public void output() {
//		getReturnVacData();
//
//	}
//}
