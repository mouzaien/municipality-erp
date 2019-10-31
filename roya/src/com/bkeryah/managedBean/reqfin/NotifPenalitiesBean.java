package com.bkeryah.managedBean.reqfin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.bkeryah.entities.PayLicBills;
import com.bkeryah.model.User;
import com.bkeryah.penalties.NotifFinesDetails;
import com.bkeryah.penalties.NotifFinesMaster;
import com.bkeryah.penalties.ReqFinesDetails;
import com.bkeryah.penalties.ReqFinesMaster;
import com.bkeryah.penalties.ReqFinesSetup;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class NotifPenalitiesBean {

	protected static final Logger logger = Logger.getLogger(NotifPenalitiesBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private NotifFinesMaster notifFinesMaster;
	private List<NotifFinesMaster> notifPenalitiesList;
	private List<NotifFinesMaster> filteredNotifPenalities;
	private Integer fineNo;
	private String status;
	private NotifFinesDetails selectedDetails;

	@PostConstruct
	private void init() {
		loadNotifPenalities();
	}

	public void loadNotifPenalities() {
		notifPenalitiesList = dataAccessService
				.loadAllNotifPenalities((StringUtils.isEmpty(status)) ? null : Integer.parseInt(status));
	}

	public void loadSelectedNotification() {
		for (NotifFinesDetails det : notifFinesMaster.getNotifFinesDetailsList()) {
			List<ReqFinesSetup> fines = dataAccessService.loadFineSetupBySection(det.getFineSection().getId());
			det.getFineSection().setFinesList(fines);
//			det.getFineSection().getSelectedFine().setId(det.getFineSection().getSelectedFineId());
		}
	}

	public String printNotifReport() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		reportName = "/reports/notif_penality.jasper";
		parameters.put("notif_id", notifFinesMaster.getNotifId());
		try {
			parameters.put("visitDate",
					HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(sdf.format(notifFinesMaster.getNotifDate())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		parameters.put("now", HijriCalendarUtil.findCurrentHijriDate());
		Utils.printPdfReport(reportName, parameters);
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

	private void findUrlBill() {

	}

	public String save() {
		try {
			ReqFinesMaster fine = castNotificationToPenality();
			List<ReqFinesDetails> details = castNotifDetailsToPenalityDetails(
					notifFinesMaster.getNotifFinesDetailsList());
			Integer fineNo = dataAccessService.saveLicencePenalty(fine, details, false);
			notifFinesMaster.setStatus(1);
			notifFinesMaster.setFineId(fineNo);
			dataAccessService.updateNotifFineMaster(notifFinesMaster);
			// notifPenalitiesList.remove(notifFinesMaster);
			notifFinesMaster = new NotifFinesMaster();
			return "";
		} catch (Exception e) {
			logger.error("Penalty save " + e.getMessage());
		}
		return "";
	}

	public String cancel() {
		try {
			notifFinesMaster.setStatus(2);
			dataAccessService.updateObject(notifFinesMaster);
			notifPenalitiesList.remove(notifFinesMaster);
			notifFinesMaster = new NotifFinesMaster();
			return "";
		} catch (Exception e) {
			logger.error("Penalty save " + e.getMessage());
		}
		return "";
	}

	public String deleteSelDetails() {
		try {
			dataAccessService.deleteObject(selectedDetails);
			notifFinesMaster.getNotifFinesDetailsList().remove(selectedDetails);
		} catch (Exception e) {
			MsgEntry.addErrorMessage("خطا فى تنفيذ العملية");
		}
		return "";
	}

	private List<ReqFinesDetails> castNotifDetailsToPenalityDetails(List<NotifFinesDetails> notifFinesDetailsList) {
		List<ReqFinesDetails> details = new ArrayList<>();
		for (NotifFinesDetails notif : notifFinesDetailsList) {
			ReqFinesDetails det = new ReqFinesDetails();
			det.setFineCode(notif.getFineSection().getSelectedFine().getId());
			det.setFineCount(notif.getFineCount());
//			det.setFineNo(notif.getFineNo());
			det.setFineValue(notif.getFineValue());
//			notif.setFineCode(notif.getFineSection().getId());
			details.add(det);
		}
		return details;
	}

	private ReqFinesMaster castNotificationToPenality() {
		SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
		User supervisor = dataAccessService.getUserByEmpNO(notifFinesMaster.getSupervisorEmpNo());
		ReqFinesMaster fine = new ReqFinesMaster();
		fine.setfLicenceNo(notifFinesMaster.getLicenceNo());
		try {
			String notifDate = df.format(notifFinesMaster.getNotifDate());
			String hijriDate = HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(notifDate);
			fine.setEntryDate(hijriDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fine.setfDeptNo("" + supervisor.getDeptId());
		fine.setfIdNo(dataAccessService.findLicByLicId(notifFinesMaster.getLicenceNo()).getAplOwner());
		fine.setFineDate(HijriCalendarUtil.findCurrentHijriDate());
		fine.setfName(notifFinesMaster.getfName());
		fine.setfSupervisorCode("" + supervisor.getUserId());
		fine.setType(1);
		return fine;
	}

	public void savePenality() {
		double sum = 0.0;
		PayLicBills newBill = new PayLicBills();
		// newBill.setBillOwnerName(notifFinesMaster.getfName());
		// newBill.setLicenceNumber(notifFinesMaster.getFineNo());
		//
		// newBill.setLicenceType("F");
		// newBill.setBillStatus(0);
		// newBill.setBillPayType("C");
		//
		// for (NotifFinesDetails fine :
		// notifFinesMaster.getNotifFinesDetailsList()) {
		// sum += fine.getFineTotalValue();
		// }
		// newBill.setPayAmount(sum);
		// PayBillDetails det = new PayBillDetails();
		// det.setPayMaster(11400);
		// det.setPayDetails(11401);
		// det.setAmount(sum);
		// List<PayBillDetails> billDetailList = new ArrayList<>();
		// billDetailList.add(det);
		// Integer billNumber = dataAccessService.saveBill(newBill,
		// billDetailList);
	}

	// public void setUrlBill(String urlBill) {
	// this.urlBill = urlBill;
	// }

	// public String getUrlPenality() {
	// if (notifFinesMaster == null)
	// return "";
	// return dataAccessService.printDocument("rq04",
	// notifFinesMaster.getFineNo(), "P1");
	// }

	// public void setUrlPenality(String urlPenality) {
	// this.urlPenality = urlPenality;
	// }

	public Integer getFineNo() {
		return fineNo;
	}

	public void setFineNo(Integer fineNo) {
		this.fineNo = fineNo;
	}

	public NotifFinesMaster getNotifFinesMaster() {
		return notifFinesMaster;
	}

	public void setNotifFinesMaster(NotifFinesMaster notifFinesMaster) {
		this.notifFinesMaster = notifFinesMaster;
	}

	public List<NotifFinesMaster> getNotifPenalitiesList() {
		return notifPenalitiesList;
	}

	public void setNotifPenalitiesList(List<NotifFinesMaster> notifPenalitiesList) {
		this.notifPenalitiesList = notifPenalitiesList;
	}

	public List<NotifFinesMaster> getFilteredNotifPenalities() {
		return filteredNotifPenalities;
	}

	public void setFilteredNotifPenalities(List<NotifFinesMaster> filteredNotifPenalities) {
		this.filteredNotifPenalities = filteredNotifPenalities;
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
}
