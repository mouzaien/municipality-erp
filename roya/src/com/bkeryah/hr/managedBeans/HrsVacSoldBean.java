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

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.VacationsType;
import com.bkeryah.hr.entities.HrsVacSold;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class HrsVacSoldBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	private Integer deptId = -1;
	private List<HrsMasterFile> employers = new ArrayList<HrsMasterFile>();
	private List<Integer> years = new ArrayList<Integer>();
	private HrsVacSold hrsVacSold = new HrsVacSold();
	private List<HrsVacSold> hrsVacSoldList;
	private List<HrsVacSold> filterHrsVacSoldList;
	private Integer vacationstypeid;
	private List<VacationsType> vacationtype;

	@PostConstruct
	public void init() {
		fullYears();
		loadAllHrsSumVacation();
		vacationtype = dataAccessService.loadVacationsType();
		employers = dataAccessService.loadHrsMasterEmployers();
		hrsVacSoldList = dataAccessService.getAllHrsVacSold();
		// users = dataAccessService.getAllEmployeesInDept(deptId);
	}

	public void loadAllHrsSumVacation() {
		employers = dataAccessService.getAllEmployeesByDept(deptId);
	}

	public void loadEmployeBarDpt(AjaxBehaviorEvent ebv) {
		vacationtype = dataAccessService.loadVacationsType();
	}

	public void saveVacaiton() {
		try {
			Integer save = dataAccessService.save(hrsVacSold);
			MsgEntry.addInfoMessage("تم الحفظ");
			System.out.print("save number--->" + save);
		} catch (Exception e) {
			MsgEntry.addErrorMessage("إجازة الموظف في عام " + hrsVacSold.getCurrentYear() + " موجودة مسبقا ");
		}
		hrsVacSold = new HrsVacSold();
		hrsVacSoldList = dataAccessService.getAllHrsVacSold();
	}

	public void onRowEdit(RowEditEvent event) {
		HrsVacSold selectedItem = (HrsVacSold) event.getObject();
		dataAccessService.updateObject(selectedItem);
		FacesMessage msg = new FacesMessage("تم تغير أجازة الموظف رقم   ", selectedItem.getEmployeeNumber().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		HrsVacSold selectedItem = (HrsVacSold) event.getObject();
		FacesMessage msg = new FacesMessage(" تم إلغاء التعديل ", selectedItem.getEmployeeNumber().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void fullYears() {
		for (int i = 0; i <= 22; i++) {
			years.add(i, 1438 + i);
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<HrsMasterFile> getEmployers() {
		return employers;
	}

	public void setEmployers(List<HrsMasterFile> employers) {
		this.employers = employers;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public List<HrsVacSold> getHrsVacSoldList() {
		return hrsVacSoldList;
	}

	public void setHrsVacSoldList(List<HrsVacSold> hrsVacSoldList) {
		this.hrsVacSoldList = hrsVacSoldList;
	}

	public HrsVacSold getHrsVacSold() {
		return hrsVacSold;
	}

	public void setHrsVacSold(HrsVacSold hrsVacSold) {
		this.hrsVacSold = hrsVacSold;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public Integer getVacationstypeid() {
		return vacationstypeid;
	}

	public void setVacationstypeid(Integer vacationstypeid) {
		this.vacationstypeid = vacationstypeid;
	}

	public List<VacationsType> getVacationtype() {
		return vacationtype;
	}

	public void setVacationtype(List<VacationsType> vacationtype) {
		this.vacationtype = vacationtype;
	}

	public List<HrsVacSold> getFilterHrsVacSoldList() {
		return filterHrsVacSoldList;
	}

	public void setFilterHrsVacSoldList(List<HrsVacSold> filterHrsVacSoldList) {
		this.filterHrsVacSoldList = filterHrsVacSoldList;
	}
}
