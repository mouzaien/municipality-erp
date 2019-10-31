package com.bkeryah.testssss;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.service.IDataAccessService;

@ManagedBean
@ViewScoped
public class TestBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	List<ArcUsers> arcUsers = new ArrayList<>();

	@PostConstruct
	public void init() {
		arcUsers = dataAccessService.getAllEmployees();

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public List<ArcUsers> getArcUsers() {
		return arcUsers;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public void setArcUsers(List<ArcUsers> arcUsers) {
		this.arcUsers = arcUsers;
	}

}
