package com.bkeryah.hr.managedBeans;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.*;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.HrEmployeeVacation;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class EmployersBean {

	protected static final Logger logger = LogManager.getLogger(CommentBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	private List<HrsMasterFile> employers;
	private List<HrsMasterFile> filtredEmployers;

	public void init() {
		employers = dataAccessService.getAllEmployeesActive();
	}

	public String goToEmployeeDetails(Integer employeeNumber) {
		System.err.println("  employeeNumber " + employeeNumber);
		// FacesContext facesContext = FacesContext.getCurrentInstance();
		// HttpSession session = (HttpSession)
		// facesContext.getExternalContext().getSession(true);
		// session.setAttribute("employeeNumber", employeeNumber);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("employeeNumber", employeeNumber);

		return "recruitEmployers";
	}

	public void onRowEdit(RowEditEvent event) {
		HrsMasterFile selectedElm = (HrsMasterFile) event.getObject();
		dataAccessService.updateObject(selectedElm);
		MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE);

	}

	public void onRowCancel(RowEditEvent event) {
		MsgEntry.addInfoMessage(MsgEntry.SUCCESS_CANCEL_EMPLOYER);
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public static Logger getLogger() {
		return logger;
	}

	public List<HrsMasterFile> getEmployers() {
		return employers;
	}

	public void setEmployers(List<HrsMasterFile> employers) {
		this.employers = employers;
	}

	public List<HrsMasterFile> getFiltredEmployers() {
		return filtredEmployers;
	}

	public void setFiltredEmployers(List<HrsMasterFile> filtredEmployers) {
		this.filtredEmployers = filtredEmployers;
	}

}
