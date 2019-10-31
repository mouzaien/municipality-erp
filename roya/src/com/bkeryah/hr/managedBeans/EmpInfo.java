package com.bkeryah.hr.managedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.service.IDataAccessService;

@ManagedBean
@ViewScoped
public class EmpInfo {
	
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	
	
	
	
	private Integer deptId;
	private String jobDescription;
	private Integer empId;
	private Integer jobNo;
	
	private Integer mgrName;
	

	private List<ArcUsers> employersList;
	
	
	
	
	public void loadEmployersByDept() {
		setEmployersList(dataAccessService.getAllActiveEmployeesInDept(getDeptId()));
	}
	
	
	
	
	
	
	
	
	
	








	public Integer getMgrName() {
		return mgrName;
	}

	public void setMgrName(Integer mgrName) {
		this.mgrName = mgrName;
	}


	public Integer getEmpId() {
		return empId;
	}

	public String getJobDescription() {
		return jobDescription;
	}


	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public Integer getJobNo() {
		return jobNo;
	}

	public void setJobNo(Integer jobNo) {
		this.jobNo = jobNo;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public List<ArcUsers> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<ArcUsers> employersList) {
		this.employersList = employersList;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

}
