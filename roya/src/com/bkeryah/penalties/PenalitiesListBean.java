package com.bkeryah.penalties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.bkeryah.entities.PayBillDetails;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PenalitiesListBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ReqFinesMaster reqFinesMaster;
	private List<ReqFinesMaster> penalitiesList;
	private List<ReqFinesMaster> filteredPenalities;
	private String urlBill;
	private String urlPenality;
	private Integer fineNo;
	private boolean notification;
	private boolean notifiPenality;

	@PostConstruct
	private void init() {
		Integer healthCareId = dataAccessService.getPropertiesValue("HEALTH_CONTROL_HEAD");
		if ((Utils.findCurrentUser().getUserId() == healthCareId)
				|| (Utils.findCurrentUser().getUserId() == MyConstants.SUPPORT_USER_ID))
			notification = true;
		loadPenalitiesByType();
	}

	public void loadPenalitiesByType() {
		penalitiesList = dataAccessService.loadAllPenalities(notifiPenality);
	}

	public void loadSelectedPenalty() {
		reqFinesMaster
				.setDeptName(dataAccessService.findDepartmentNameById(Integer.parseInt(reqFinesMaster.getfDeptNo())));
		reqFinesMaster.setSupervisorName(dataAccessService
				.loadUserById(Integer.parseInt(reqFinesMaster.getfSupervisorCode())).getEmployeeName());
		for (ReqFinesDetails detail : reqFinesMaster.getReqFinesDetailsList()) {
			detail.setFineCountNo(
					dataAccessService.getNumberLicencePenality(detail.getFineCode(), reqFinesMaster.getFineNo()));
		}
		fineNo = reqFinesMaster.getFineNo();
	}

	public void acceptPenality() {
		dataAccessService.acceptPenalityAndBill(reqFinesMaster);
		penalitiesList.remove(reqFinesMaster);
	}

	public String printPenalityReport() {
		try {
			String reportName = "/reports/penality.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fineNo", reqFinesMaster.getFineNo());
			parameters.put("supervisor", reqFinesMaster.getSupervisorNameView());
			parameters.put("LOGO_DIR", FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath(Utils.loadMessagesFromFile("report.logo")));
			Utils.printPdfReport(reportName, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "";
	}

	public String printReportPenalityAction() {
		try {
			String reportName = "/reports/penality_report.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fine_no", reqFinesMaster.getFineNo());
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
		try {
			String reportName = "/reports/bill.jasper";
			PayLicBills payLicBill = dataAccessService.loadBillByLicNo(reqFinesMaster.getFineNo());
			System.out.println(">>>>"+reqFinesMaster.getFineNo());
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("p1", payLicBill.getBillNumber());
			parameters.put("SUBREPORT_DIR",
					FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/bill_detail.jasper"));
			Utils.printPdfReport(reportName, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	public String addPenality(){
		return "penalty";
		
	}
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public ReqFinesMaster getReqFinesMaster() {
		return reqFinesMaster;
	}

	public void setReqFinesMaster(ReqFinesMaster reqFinesMaster) {
		this.reqFinesMaster = reqFinesMaster;
	}

	public List<ReqFinesMaster> getPenalitiesList() {
		return penalitiesList;
	}

	public void setPenalitiesList(List<ReqFinesMaster> penalitiesList) {
		this.penalitiesList = penalitiesList;
	}

	public List<ReqFinesMaster> getFilteredPenalities() {
		return filteredPenalities;
	}

	public void setFilteredPenalities(List<ReqFinesMaster> filteredPenalities) {
		this.filteredPenalities = filteredPenalities;
	}

	public String getUrlBill() {
		if (reqFinesMaster == null)
			return "";
		// urlBill = findUrlBill();
		Integer countBill = dataAccessService.getCountBillByFineNo(reqFinesMaster.getFineNo());
		if (countBill == 0) {
			Integer billNumber = saveBill();
		} else {
			PayLicBills payLicBill = dataAccessService.loadBillByLicNo(reqFinesMaster.getFineNo());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date billExpireDate;
			try {
				billExpireDate = sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(
						HijriCalendarUtil.addDaysToHijriDate(payLicBill.getBillDate(), 30)));
				if (new Date().after(billExpireDate)) {
					urlBill = dataAccessService.printDocument("pay003", payLicBill.getBillNumber(), "billno");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		return urlBill;

	}

	private void findUrlBill() {

	}

	private Integer saveBill() {
		double sum = 0.0;
		PayLicBills newBill = new PayLicBills();
		newBill.setBillOwnerName(reqFinesMaster.getfName());
		newBill.setLicenceNumber(reqFinesMaster.getFineNo());

		newBill.setLicenceType("F");
		newBill.setBillStatus(0);
		newBill.setBillPayType("C");

		for (ReqFinesDetails fine : reqFinesMaster.getReqFinesDetailsList()) {
			sum += fine.getFineTotalValue();
		}
		newBill.setPayAmount(sum);
		PayBillDetails det = new PayBillDetails();
		det.setPayMaster(11400);
		det.setPayDetails(11401);
		det.setAmount(sum);
		List<PayBillDetails> billDetailList = new ArrayList<>();
		billDetailList.add(det);
		Integer billNumber = dataAccessService.saveBill(newBill, billDetailList);
		urlBill = dataAccessService.printDocument("rq06", reqFinesMaster.getFineNo(), "P1");
		return billNumber;
	}

	public void setUrlBill(String urlBill) {
		this.urlBill = urlBill;
	}

	public String getUrlPenality() {
		if (reqFinesMaster == null)
			return "";
		return dataAccessService.printDocument("rq04", reqFinesMaster.getFineNo(), "P1");
	}

	public void setUrlPenality(String urlPenality) {
		this.urlPenality = urlPenality;
	}

	public Integer getFineNo() {
		return fineNo;
	}

	public void setFineNo(Integer fineNo) {
		this.fineNo = fineNo;
	}

	public boolean isNotification() {
		return notification;
	}

	public void setNotification(boolean notification) {
		this.notification = notification;
	}

	public boolean isNotifiPenality() {
		return notifiPenality;
	}

	public void setNotifiPenality(boolean notifiPenality) {
		this.notifiPenality = notifiPenality;
	}
}
