package com.bkeryah.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.Supervisor;
import com.bkeryah.entities.licences.LicVisits;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class SupervisorBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	List<ArcUsers> users;
	List<Supervisor> supervisors;
	private Supervisor supervisor = new Supervisor();
	private boolean active;

	@PostConstruct
	public void init() {

		users = dataAccessService.findEmployeesByDept(2);
		supervisors = dataAccessService.findAllSupervisorsByDept(2);

	}

	public void addSuperVisor() {
		if (active == true)
			supervisor.setStatus(1);
		else
			supervisor.setStatus(0);

		try {
			supervisor.setDeptId(Utils.findCurrentUser().getDeptId());
			dataAccessService.addSupervisor(supervisor);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			supervisors = dataAccessService.findAllSupervisorsByDept(Utils.findCurrentUser().getDeptId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}

	}

	public void onRowEdit(RowEditEvent event) {
		Supervisor supervisor = (Supervisor) event.getObject();
		try {
			dataAccessService.updateObject(supervisor);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}

	}

	public String deleteSuper(Supervisor supervisor) {
		if (supervisor != null) {
			try {
				dataAccessService.deleteObject(supervisor);
				supervisors = dataAccessService.findAllSupervisorsByDept(2);
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

			}

		}
		return "";
	}
	
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<ArcUsers> getUsers() {
		return users;
	}

	public void setUsers(List<ArcUsers> users) {
		this.users = users;
	}

	public List<Supervisor> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<Supervisor> supervisors) {
		this.supervisors = supervisors;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
