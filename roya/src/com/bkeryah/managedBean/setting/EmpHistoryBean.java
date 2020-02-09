package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.HrsEmpHistorical;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

@ManagedBean(name = "empHistoryBean")
@ViewScoped
public class EmpHistoryBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<HrsEmpHistorical> empHistoryList = new ArrayList<HrsEmpHistorical>();
	private HrsEmpHistorical empHistory;
	private String empNo;
	private List<User> usrsList;

	@PostConstruct
	public void init() {
		usrsList = dataAccessService.getAllUsers();
	}

	public void onRowEdit(RowEditEvent event) {
		HrsEmpHistorical selectedItem = (HrsEmpHistorical) event.getObject();
		FacesMessage msg = new FacesMessage("تم حفظ التعديل");
		dataAccessService.updateObject(selectedItem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("تم إلغاء التعديل");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void loadAllEmpsHis(AjaxBehaviorEvent E) {
		try {
			System.out.println("empNo >>>>>> " + empNo);
			if ("-1".equalsIgnoreCase(empNo)) {
				empHistoryList = dataAccessService.findAllHrsEmpHistorical();
			} else {
				if (empNo != null && !empNo.isEmpty() && empNo.length() < 10) {
					empHistoryList = dataAccessService.findEmpHistoricalByEmpNo(Integer.parseInt(empNo));
				} else {
					empHistoryList = new ArrayList<>();
				}
			}
		} catch (Exception e) {

		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<HrsEmpHistorical> getEmpHistoryList() {
		return empHistoryList;
	}

	public void setEmpHistoryList(List<HrsEmpHistorical> empHistoryList) {
		this.empHistoryList = empHistoryList;
	}

	public HrsEmpHistorical getEmpHistory() {
		return empHistory;
	}

	public void setEmpHistory(HrsEmpHistorical empHistory) {
		this.empHistory = empHistory;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public List<User> getUsrsList() {
		return usrsList;
	}

	public void setUsrsList(List<User> usrsList) {
		this.usrsList = usrsList;
	}

}
