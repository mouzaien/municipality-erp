package com.bkeryah.managedBean.reqfin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bkeryah.entities.Supervisor;
import com.bkeryah.model.User;
import com.bkeryah.penalties.LicTrdMasterFile;
import com.bkeryah.penalties.NotifFinesDetails;
import com.bkeryah.penalties.NotifFinesMastR;
import com.bkeryah.service.IDataAccessService;
import com.sun.faces.util.Util;

import utilities.HijriCalendarUtil;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class NotifPenalitiesBean {

	protected static final Logger logger = Logger.getLogger(NotifPenalitiesBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private NotifFinesMastR notifFinesMaster;
	private List<NotifFinesMastR> notifPenalitiesList;
	private List<NotifFinesMastR> filteredNotifPenalities;
	private Integer fineNo;
	private String status;
	private String supervisorName;
	private NotifFinesDetails selectedDetails;

	@PostConstruct
	private void init() {

		notifPenalitiesList = dataAccessService.getAllNotifList();
		// loadNotifPenalities();
	}
	//
	// public void loadNotifPenalities() {
	// notifPenalitiesList = dataAccessService
	// .loadAllNotifPenalities((StringUtils.isEmpty(status)) ? null :
	// Integer.parseInt(status));
	// }

	public void loadSelectedNotification(NotifFinesMastR mstr) {
		if (mstr != null) {
			notifFinesMaster = mstr;
			User sup = (User) dataAccessService.findEntityById(User.class, mstr.getSupervisorId());
			if (sup != null) {
				// supervisor Name
				supervisorName = sup.getEmployeeName();
			}
			Utils.openDialog("details_dlg");
		}
	}

	public String addPenalty(NotifFinesMastR mstr) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		httpSession.setAttribute("notifMstr", mstr);
		return "penalty";
	}

	public String printNotifReport() {
		try {
			String reportName = "/reports/penality_report.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fine_no", notifFinesMaster.getId());
			parameters.put("now", HijriCalendarUtil.findCurrentHijriDate());
			// parameters.put("SUBREPORT_DIR",
			// FacesContext.getCurrentInstance().getExternalContext()
			// .getRealPath("/reports/sub_penality_report.jasper"));
			Utils.printPdfReport(reportName, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	public String printBillReport() {
		// String reportName = "/reports/bill.jasper";
		// PayLicBills payLicBill =
		// dataAccessService.loadBillByLicNo(notifFinesMaster.getFineNo());
		// Map<String, Object> parameters = new HashMap<String, Object>();
		// parameters.put("p1", payLicBill.getBillNumber());
		// parameters.put("SUBREPORT_DIR",
		// FacesContext.getCurrentInstance().getExternalContext()
		// .getRealPath("/reports/bill_detail.jasper"));
		// Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public NotifFinesMastR getNotifFinesMaster() {
		return notifFinesMaster;
	}

	public void setNotifFinesMaster(NotifFinesMastR notifFinesMaster) {
		this.notifFinesMaster = notifFinesMaster;
	}

	public List<NotifFinesMastR> getNotifPenalitiesList() {
		return notifPenalitiesList;
	}

	public void setNotifPenalitiesList(List<NotifFinesMastR> notifPenalitiesList) {
		this.notifPenalitiesList = notifPenalitiesList;
	}

	public List<NotifFinesMastR> getFilteredNotifPenalities() {
		return filteredNotifPenalities;
	}

	public void setFilteredNotifPenalities(List<NotifFinesMastR> filteredNotifPenalities) {
		this.filteredNotifPenalities = filteredNotifPenalities;
	}

	public Integer getFineNo() {
		return fineNo;
	}

	public void setFineNo(Integer fineNo) {
		this.fineNo = fineNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public NotifFinesDetails getSelectedDetails() {
		return selectedDetails;
	}

	public void setSelectedDetails(NotifFinesDetails selectedDetails) {
		this.selectedDetails = selectedDetails;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

}
