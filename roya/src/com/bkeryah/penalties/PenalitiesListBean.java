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

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.LicActivityTypeRy;
import com.bkeryah.entities.PayBillDetails;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.investment.ContractDirect;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.ContractOperationEnum;
import utilities.HijriCalendarUtil;
import utilities.MyConstants;
import utilities.Utils;

/**
 * @author Amr Alkady
 *
 */
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
	private String fineStatus = "-1";
	private Integer fineTradeType;
	private Integer fineSadad;
	private String licNo;
	private String alpNo;
	private String fromStartDate;
	private String toStartDate;
	private boolean higriMode = true;
	private Date fromStartDateG;// = new Date();
	private Date toStartDateG;// = new Date();
	private List<LicActivityTypeRy> activityList = new ArrayList<LicActivityTypeRy>();

	@PostConstruct
	private void init() {
		Integer healthCareId = dataAccessService.getPropertiesValue("HEALTH_CONTROL_HEAD");
		if ((Utils.findCurrentUser().getUserId() == healthCareId)
				|| (Utils.findCurrentUser().getUserId() == MyConstants.SUPPORT_USER_ID))
			notification = true;

		// load all penalities by log in user dept Id
		penalitiesList = dataAccessService.loadAllPenalities(fineStatus, Utils.findCurrentUser().getDeptId());
		activityList = dataAccessService.getAllLicActivityTypeList();
		loadFineData();
	}

	public void loadPenalitiesByType() {
		fromStartDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:fromStDate");
		System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:fromStDate"));
		toStartDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:toStartDate");
		String dfrom = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:dfrom");
		String dto = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:dto");

		if (!higriMode) {
			try {
				fromStartDate = Utils.grigDatesConvert(fromStartDateG);
				toStartDate = Utils.grigDatesConvert(toStartDateG);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Integer deptId = Utils.findCurrentUser().getDeptId();
		penalitiesList = dataAccessService.loadListPenalities(fineStatus, fineTradeType, fineSadad, licNo, alpNo,
				fromStartDate, toStartDate, Integer.toString(deptId));

		loadFineData();

	}

	public void loadFineData() {
		for (ReqFinesMaster fine : penalitiesList) {
			if (fine.getfSupervisorCode() != null) {
				ArcUsers usr = dataAccessService.loadUserById(Integer.parseInt(fine.getfSupervisorCode().trim()));
				if (usr != null) {
					fine.setSupervisorNameView(usr.getEmployeeName());
					fine.setSupervisorName(usr.getEmployeeName());
				}
			}
			if (fine.getfLicenceNo() != null) {
				LicTrdMasterFile act = (LicTrdMasterFile) dataAccessService.findLicByLicId(fine.getfLicenceNo().trim());
				if (act != null)
					fine.setMahlId(act.getMhlId());
			}
			fine.setReqFinesDetailsList(dataAccessService.findLstFinesDetailsByFineNO(fine.getFineNo()));
			for (ReqFinesDetails detail : fine.getReqFinesDetailsList()) {
				detail.setFineCountNo(
						dataAccessService.getNumberLicencePenality(detail.getFineCode(), fine.getFineNo()));
			}
			fine.setFineAmount(
					fine.getReqFinesDetailsList().stream().mapToDouble(fdet -> fdet.getFineTotalValue()).sum());
		}
	}

	public void loadSelectedPenalty() {
		reqFinesMaster
				.setDeptName(dataAccessService.findDepartmentNameById(Integer.parseInt(reqFinesMaster.getfDeptNo())));
		// reqFinesMaster.setSupervisorName(dataAccessService
		// .loadUserById(Integer.parseInt(reqFinesMaster.getfSupervisorCode())).getEmployeeName());
		LicActivityTypeRy act = (LicActivityTypeRy) dataAccessService.findEntityById(LicActivityTypeRy.class,
				reqFinesMaster.getActivityType());
		reqFinesMaster.setActivityName(act.getName());
		fineNo = reqFinesMaster.getFineNo();
	}

	/**
	 * @param document
	 */
	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		sheet.setColumnHidden(9, true);
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

	public String printAllFines() {
		try {
			String reportName = "/reports/penalities.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			Utils.printPdfReportFromListDataSource(reportName, parameters, penalitiesList);
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
			parameters.put("fine_no", reqFinesMaster.getNotifyMasterId());
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
			System.out.println(">>>>" + reqFinesMaster.getFineNo());
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

	public String addPenality() {
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

	public String getFineStatus() {
		return fineStatus;
	}

	public void setFineStatus(String fineStatus) {
		this.fineStatus = fineStatus;
	}

	public Integer getFineTradeType() {
		return fineTradeType;
	}

	public void setFineTradeType(Integer fineTradeType) {
		this.fineTradeType = fineTradeType;
	}

	public Integer getFineSadad() {
		return fineSadad;
	}

	public void setFineSadad(Integer fineSadad) {
		this.fineSadad = fineSadad;
	}

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	public String getAlpNo() {
		return alpNo;
	}

	public void setAlpNo(String alpNo) {
		this.alpNo = alpNo;
	}

	public String getFromStartDate() {
		return fromStartDate;
	}

	public void setFromStartDate(String fromStartDate) {
		this.fromStartDate = fromStartDate;
	}

	public String getToStartDate() {
		return toStartDate;
	}

	public void setToStartDate(String toStartDate) {
		this.toStartDate = toStartDate;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public Date getFromStartDateG() {
		return fromStartDateG;
	}

	public void setFromStartDateG(Date fromStartDateG) {
		this.fromStartDateG = fromStartDateG;
	}

	public Date getToStartDateG() {
		return toStartDateG;
	}

	public void setToStartDateG(Date toStartDateG) {
		this.toStartDateG = toStartDateG;
	}

	public List<LicActivityTypeRy> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<LicActivityTypeRy> activityList) {
		this.activityList = activityList;
	}
}
