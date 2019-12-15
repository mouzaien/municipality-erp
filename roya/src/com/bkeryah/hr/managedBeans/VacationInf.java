package com.bkeryah.hr.managedBeans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.RowEditEvent;
import com.bkeryah.entities.HrEmployeeVacation;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.VacationsType;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class VacationInf{
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<HrsMasterFile> employers;
	private List<HrEmployeeVacation> vacations;
	private List<HrEmployeeVacation> filteredVacations;
	private HrEmployeeVacation selectVacation;
	private HrsMasterFile employer;
	private Integer employerId;
	private Integer vacationstypeid;
	private List<VacationsType> vacationtype;

	@PostConstruct
	public void init() {
		employers = dataAccessService.loadHrsMasterEmployers();
		vacationtype = dataAccessService.loadVacationsType();
	}

	public void addRowVacation(ActionEvent actionEvent) {
		HrEmployeeVacation vacation = new HrEmployeeVacation();
		vacations.add(vacation);
	}

	public String printIreportFileAction() {

		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (selectVacation.getVacationType() == MyConstants.NORMAL_VACATION) {
			reportName = "/reports/normal_vacation.jasper";
			parameters.put("seqid", selectVacation.getId());
			parameters.put("compName", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
			Utils.printPdfReport(reportName, parameters);
		}
		return "";
	}

	public String printIreportFileActionFull() {
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		reportName = "/reports/vacation_sum_subreport.jasper";
		parameters.put("compName", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		parameters.put("pempno", getEmployerId());
		parameters.put("vactype", getVacationstypeid());
		parameters.put("now", HijriCalendarUtil.findCurrentHijriWithTimeStamp());
//		parameters.put("SUBREPORT_DIR", FacesContext.getCurrentInstance().getExternalContext()
//				.getRealPath("/reports/vacation_sum.jasper"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public boolean testField(HrEmployeeVacation objVct) {
		if ((objVct.getVacationPeriod().equals(null)) || (objVct.getHigriVacationStart().length() == 0)
				|| (objVct.getHigriVacationEnd().length() == 0) || (objVct.getHDatevacationFrom().length() == 0)
				|| (objVct.getHDateVacationTo().length() == 0) || (objVct.getExcuseDateHigri().length() == 0)
				|| (objVct.getExcuseNumber().length() == 0)) {

			return false;
		} else
			return true;
	}

	public void onRowEdit(RowEditEvent event) {
		((HrEmployeeVacation) event.getObject()).setEmployeeNumber(employerId);
		HrEmployeeVacation hremvc = new HrEmployeeVacation();
		hremvc = ((HrEmployeeVacation) event.getObject());
		boolean isfieldfull = testField(hremvc);
		if (isfieldfull) {
			if (((HrEmployeeVacation) event.getObject()).getId() == null) {
				dataAccessService.saveObject((HrEmployeeVacation) event.getObject());
				MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE_VACATION);
			} else {
				dataAccessService.updateObject((HrEmployeeVacation) event.getObject());
				MsgEntry.addInfoMessage(MsgEntry.SUCCESS_UPDATE_VACATION);
			}
		} else {
			MsgEntry.addErrorMessage(MsgEntry.COMPLETEFIELD);
		}
	}

	public void onRowCancel(RowEditEvent event) {

//		if (((HrEmployeeVacation) event.getObject()).getId() == null) {
			vacations.remove((HrEmployeeVacation) event.getObject());
			((HrEmployeeVacation) event.getObject()).setVacationStatus("N");
			dataAccessService.updateObject(((HrEmployeeVacation) event.getObject()));
			MsgEntry.addInfoMessage(MsgEntry.SUCCESS_DELETE_VACATION);
//		} else {
//			MsgEntry.addErrorMessage(MsgEntry.ERRORE_DELETE_VACATION);
//		}
		;
	}

	public void loadVacationInfo() {
		vacations = dataAccessService.loadVacationInfo(getEmployerId(), getVacationstypeid());
	}

	public List<HrsMasterFile> getEmployers() {
		return employers;
	}

	public void setEmployers(List<HrsMasterFile> employers) {
		this.employers = employers;
	}

	public HrsMasterFile getEmployer() {
		return employer;
	}

	public void setEmployer(HrsMasterFile employer) {
		this.employer = employer;
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

	public List<HrEmployeeVacation> getVacations() {
		return vacations;
	}

	public void setVacations(List<HrEmployeeVacation> vacations) {
		this.vacations = vacations;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public List<HrEmployeeVacation> getFilteredVacations() {
		return filteredVacations;
	}

	public void setFilteredVacations(List<HrEmployeeVacation> filteredVacations) {
		this.filteredVacations = filteredVacations;
	}

	public HrEmployeeVacation getSelectVacation() {
		return selectVacation;
	}

	public void setSelectVacation(HrEmployeeVacation selectVacation) {
		this.selectVacation = selectVacation;
	}

}
