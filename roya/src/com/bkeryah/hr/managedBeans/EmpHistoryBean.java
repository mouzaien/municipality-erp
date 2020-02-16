package com.bkeryah.hr.managedBeans;

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
import com.bkeryah.entities.Nationality;
import com.bkeryah.hr.entities.Sys038;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

@ManagedBean(name = "empHistoryBean")
@ViewScoped
public class EmpHistoryBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<HrsEmpHistorical> empHistoryList = new ArrayList<HrsEmpHistorical>();
	private HrsEmpHistorical empHistory;
	private List<Nationality> sysList = new ArrayList<Nationality>();
	private Nationality sys;
	private List<Sys038> rankList = new ArrayList<Sys038>();
	private String empNo;
	private Integer RecordType;
	private List<User> usrsList;
	private String CATegoryName;

	@PostConstruct
	public void init() {
		usrsList = dataAccessService.getAllUsers();
		sysList = dataAccessService.getallNationalities();
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

	public List<Nationality> getSysList() {
		return sysList;
	}

	public void setSysList(List<Nationality> sysList) {
		this.sysList = sysList;
	}

	public Nationality getSys() {
		return sys;
	}

	public void setSys(Nationality sys) {
		this.sys = sys;
	}

	public String getCATegoryName() {
		return CATegoryName;
	}

	public void setCATegoryName(String cATegoryName) {
		CATegoryName = cATegoryName;
	}

	public List<Sys038> getRankList() {
		return rankList;
	}

	public void setRankList(List<Sys038> rankList) {
		this.rankList = rankList;
	}

	public Integer getRecordType() {
		return RecordType;
	}

	public void setRecordType(Integer recordType) {
		RecordType = recordType;
	}

}
