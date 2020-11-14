package com.bkeryah.managedBean.investment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.OrderBy;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.bkeryah.entities.PayBillDetails;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.PayMaster;
import com.bkeryah.entities.investment.ContractDirect;
import com.bkeryah.entities.investment.ContractInstallments;
import com.bkeryah.entities.investment.ContractMainCategory;
import com.bkeryah.entities.investment.ContractStatus;
import com.bkeryah.entities.investment.ContractSubcategory;
import com.bkeryah.entities.investment.ContractsFees;
import com.bkeryah.entities.investment.IntroContract;
import com.bkeryah.entities.investment.Investor;
import com.bkeryah.service.IDataAccessService;

import utilities.ContractOperationEnum;
import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;
import java.util.Calendar;

@ManagedBean
@ViewScoped
public class ContractDirectBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<ContractDirect> contractsDirectList;
	private List<ContractDirect> filteredContractsDirectList;
	private ContractDirect contractDirect = new ContractDirect();
	private boolean addMode;
	private List<String> selectedClausesList;
	private Integer introductionId = -1;
	private Integer operationId;
	private Integer contractTypeId;
	private boolean addBill;
	private Integer numBill;
	private PayLicBills bill;
	private List<PayLicBills> contractBillsList;

	private List<ContractMainCategory> contractMainCategoryList = new ArrayList<ContractMainCategory>();
	private List<ContractSubcategory> contractSubcategoryList = new ArrayList<ContractSubcategory>();
	private List<ContractStatus> contractStatusList = new ArrayList<ContractStatus>();
	private static final Logger logger = Logger.getLogger(ContractDirectBean.class);

	private String selecteStartDate = new String();
	private Date selecteStartDate_G;
	private String selecteEndDate = new String();
	private Date selecteEndDate_G;

	private boolean higriMode = true;
	private List<IntroContract> introContractList;

	private String introName;
	private String introDescription;
	private IntroContract introdaction = new IntroContract();

	private Integer newcontractNum;
	private Integer yearsNum = new Integer(1);
	private Integer installmentNum = 1;
	private List<ContractInstallments> ContractInstallmentsList = new ArrayList<>();
	private boolean tableHigriMode;
	private Integer status = new Integer(-1);
	private ContractMainCategory mainCategory = new ContractMainCategory();
	private ContractSubcategory subCategory = new ContractSubcategory();
	private Integer contNum = -1;
	private String investorName;
	private String fromStartDate;
	private String toStartDate;
	private String fromEndDate;
	private String toEndDate;
	private boolean allowFiltre;
	private Integer totalInstallmentCount = 1;
	private Integer contractType; // سنوي 1 او شهري 2 او 3يومي
	private String durationLabel = new String();
	private Integer oldInstallment = new Integer(0);
	private Integer discountExceptionel = new Integer(0);// خصم إستثنائي مثلا 5%
															// جائحة كورونا
	private Integer discountDuration = new Integer(0);
	private List<ContractsFees> contractsFeesList = new ArrayList<ContractsFees>();

	private double totalDiscount = 0.000;
	private List<Investor> investorsList;
	private List<ContractDirect> filteredContractsNumList = new ArrayList<ContractDirect>();
	private Integer investorID = -1;
	private String contractIntro;
	private Integer billBandNumber = 1438;
	private List<PayMaster> itemsList;

	@PostConstruct
	public void init() {
		loadContracts();
		// loadContractOperations();
		// Collections.sort(contractsDirectList);

		contractMainCategoryList = dataAccessService.loadAllContractMainCategory();
		// contractSubcategoryList =
		// dataAccessService.loadAllContractSubcategory();
		contractStatusList = dataAccessService.loadAllContractStatus();
		System.out.println("contractStatusList ----- >>>> " + contractStatusList.size());

		introContractList = dataAccessService.loadAllIntroContracts();
		investorsList = dataAccessService.loadAllInvestors();
		filteredContractsNumList = dataAccessService.loadAllContractDirects(contractTypeId);

	}

	private void loadContractOperations() {
		for (ContractDirect cont : contractsDirectList) {
			cont.getActionsList().add(ContractOperationEnum.DELETE);
//			cont.getActionsList().add(ContractOperationEnum.BILL);
			try {
				if (Utils.convertHDateToGDate(cont.getEndDate()).before(new Date())) {
					cont.getActionsList().add(ContractOperationEnum.RENEW);
					cont.setFinished(1);
				} else {
					cont.setFinished(0);
					if (cont.getStatus() != ContractOperationEnum.CANCEL.getAction())
						cont.getActionsList().add(ContractOperationEnum.CANCEL);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void loadContractsByFinishingStatus() {

		contractsDirectList = dataAccessService.loadendedContractDirectListByStatus(status);
		loadContractOperations();
	}

	public void loadContractDirectListByAllFilters() {
		contractsDirectList.clear();
		fromStartDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:fromStartDate");
		toStartDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:toStartDate");
		fromEndDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:fromEndDate");
		toEndDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:toEndDate");
		contractsDirectList = dataAccessService.loadContractDirectListByAllFilters(contNum, investorID, status,
				fromStartDate, toStartDate, fromEndDate, toEndDate);
		loadContractOperations();
	}

	// public void loadContractsByFinishingStatus() {
	// List<ContractDirect> finishedCntrctsList = new
	// ArrayList<ContractDirect>();
	// List<ContractDirect> validCntrctsList = new ArrayList<ContractDirect>();
	// loadContracts();
	// try {
	// for (ContractDirect contract : contractsDirectList) {
	// if
	// (Utils.convertGregStringToDate(Utils.convertHijriDateToGregorian(contract.getEndDate()))
	// .before(new Date())) {
	// finishedCntrctsList.add(contract);
	// } else {
	// validCntrctsList.add(contract);
	// }
	// }
	// if (status == 1) {
	// contractsDirectList = finishedCntrctsList;
	// loadContractOperations();
	// } else if (status == 0) {
	// contractsDirectList = validCntrctsList;
	// loadContractOperations();
	// } else {
	// loadContracts();
	// }
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	public void loadContracts() {
		contractsDirectList = dataAccessService.loadAllContractDirects(contractTypeId);
		loadContractOperations();
	}

	public void loadContract() {
		addMode = false;
	}

	public void addContract() {
		contractDirect = new ContractDirect();
		addMode = true;
		introductionId = null;
		Utils.openDialog("addDialog");
	}

	public void updateDurationLabel() {

		if (contractType != null) {
			switch (contractType) {
			case 1:
				durationLabel = "سنة";
				break;
			case 2:
				durationLabel = "شهر";
				break;
			case 3:
				durationLabel = "يوم";
				break;

			default:
				break;
			}
		}
		calculateInstallments();
	}

	public void calculateInstallments() {
		if (yearsNum != null || yearsNum > 0) {
			switch (contractType) {
			case 1:
				totalInstallmentCount = yearsNum;
				break;
			case 2:
				totalInstallmentCount = yearsNum;
				break;
			case 3:
				totalInstallmentCount = 1;
				break;
			}
		}
	}

	public void loadContractFeeslistbycontractId(ContractDirect selectedContract) {
		contractsFeesList = new ArrayList<ContractsFees>();
		contractDirect = selectedContract;
		contractsFeesList = dataAccessService.loadContractFeeslistbycontractId(selectedContract.getId());
	}

	public void save() {
		try {

			// insertDate();
			sortDates();
			// String startDate =
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
			// .get("startDate");
			// contractDirect.setStartDate(startDate);
			// String endDate =
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
			// .get("notifDate");
			// contractDirect.setEndDate(endDate);
			// contractDirect.setContractYears(HijriCalendarUtil.findDatesDifferenceInYears(contractDirect.getStartDate(),
			// contractDirect.getEndDate()));
			contractDirect.setContractDate(new Date());

			if (contractDirect.getOperationId() == ContractOperationEnum.RENEW.getAction()) {
				contractDirect.setStatus(2);
			} else {
				contractDirect.setStatus(1);
			}

			if (newcontractNum != null) {
				contractDirect.setContractNum(newcontractNum);
			}
			contractDirect.setContractType(contractType);
			contractDirect.setContractYears(yearsNum);
			contractDirect.setIntroduction(contractIntro);

			createContractsFeesList();
			Integer cotractId = dataAccessService.saveContractDirect(contractDirect, contractsFeesList, billBandNumber);

			// create fees
			// add bills for contract
			// saveBill(cotractId);
			contractsDirectList = dataAccessService.loadAllContractDirects(contractTypeId);
			contractDirect = new ContractDirect();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("add contractDirect: id: " + contractDirect.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("add contractDirect: id: " + contractDirect.getId());
		}

	}

	public void saveFeesUpdate() {
		calculateDiscount();
		Integer unBillingFeesNum = 0;
		List<ContractsFees> contFeesList = new ArrayList<ContractsFees>();
		double oldDiscount = 0.00; // إجمالي الخصم في الفواتير المدفوعة
		for (ContractsFees contFees : contractsFeesList) {
			if (contFees.isCheckfees() == true) {
				contFees.setStatus(2);
				calculateFeesDiscount(contFees);
				contractDirect.setTotalBillValue(contractDirect.getTotalBillValue() + contractDirect.getAnnualRent()
						- contFees.getDiscountAmount());
			} else if (contFees.getStatus() == 1) {
				unBillingFeesNum++;
			} else {
				oldDiscount = oldDiscount + contFees.getDiscountAmount();
			}
			contFeesList.add(contFees);
		}
		if ((totalDiscount - oldDiscount) >= (unBillingFeesNum * contractDirect.getAnnualRent())) {
			MsgEntry.addErrorMessage("قيمة مبلغ الخصم الباقية أكبر من قيمة الأقساط المتبقية");
		}

		dataAccessService.updatecontractFeesList(contFeesList);
		dataAccessService.insertInvestBill(contractDirect, contractDirect.getTotalBillValue(), billBandNumber);
	}

	public List<ContractsFees> createContractsFeesList() {
		ContractsFees fees = new ContractsFees();
		String dueHDate = contractDirect.getStartDate();
		calculateDiscount();
		try {
			for (int i = 1; i <= totalInstallmentCount; i++) {
				fees.setDueHDate(calculateDueDates(i, dueHDate));
				fees.setDueGDate(Utils.convertHDateToGDate(fees.getDueHDate()));
				fees.setOldFees(oldInstallment);
				fees.setDiscountduration(discountDuration);
				fees.setDiscountExceptionel(discountExceptionel);
				calculateFeesDiscount(fees);
				if (i == 1 || i <= installmentNum) {
					fees.setStatus(2);
					contractDirect.setTotalBillValue(contractDirect.getTotalBillValue() + contractDirect.getAnnualRent()
							- fees.getDiscountAmount());
				} else {
					fees.setStatus(1);
				}

				contractsFeesList.add(fees);
			}
			return contractsFeesList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contractsFeesList;
	}

	private void calculateFeesDiscount(ContractsFees fees) {
		if (totalDiscount > contractDirect.getAnnualRent()) {
			// تقسيم الخصم على الأقساط المفوترة
			fees.setDiscountAmount(contractDirect.getAnnualRent());
			totalDiscount = totalDiscount - contractDirect.getAnnualRent();

		} else {
			fees.setDiscountAmount(totalDiscount);
			totalDiscount = 0;
		}
	}

	public double calculateDiscount() {

		if (discountDuration > 0 && contractDirect.getAnnualRent() != null && discountExceptionel >= 0) {

			switch (contractType) {
			case 1:
				totalDiscount = ((contractDirect.getAnnualRent() / 365) * discountDuration) + discountExceptionel;
				break;
			case 2:
				totalDiscount = ((contractDirect.getAnnualRent() / 30) * discountDuration) + discountExceptionel;
				break;
			case 3:
				totalDiscount = ((contractDirect.getAnnualRent() / yearsNum) * discountDuration) + discountExceptionel;
				break;
			}

		}
		if (addMode && totalDiscount != 0 && totalDiscount > (installmentNum * contractDirect.getAnnualRent())) {
			discountDuration = 0;
			MsgEntry.addErrorMessage("إجمالي الخصم اكبر من قيمة الاقساط المفوترة ... يرجى تخفيف مدة الخصم");
		}
		return 0;
	}

	public String calculateDueDates(Integer feesNum, String dueHDate) {
		String hDate = new String();
		Date dueGDate = new Date();
		Date gDate = new Date();
		Calendar calendar;
		try {
			dueGDate = Utils.convertHDateToGDate(dueHDate);
			calendar = Utils.getCalendar(dueGDate);
			if (feesNum != null && !contractDirect.getContractType().equals(3)) {
				if (contractDirect.getContractType().equals(1)) {
					calendar.add(Calendar.YEAR, feesNum);
				} else {
					calendar.add(Calendar.MONTH, feesNum);
				}
				gDate = calendar.getTime();
				hDate = Utils.grigDatesConvert(gDate);
			} else {
				hDate = dueHDate;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hDate;
	}

	public void saveIntrodaction() {
		try {
			if (introName != null && introDescription != null) {
				introdaction.setName(introName);
				introdaction.setDescription(introDescription);
				dataAccessService.save(introdaction);
				introContractList = dataAccessService.loadAllIntroContracts();
				introName = new String();
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void loadSuCategoryByMainCategoryId() {
		contractSubcategoryList = dataAccessService
				.loadSuCategoryByMainCategoryId(contractDirect.getContractMaincatgId());

	}

	public void savemainCategory() {

		try {

			dataAccessService.save(mainCategory);
			contractMainCategoryList = dataAccessService.loadAllContractMainCategory();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));

		} catch (

		Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void saveSubCategory() {

		try {

			dataAccessService.save(subCategory);
			// contractSubcategoryList =
			// dataAccessService.loadAllContractSubcategory();
			loadSuCategoryByMainCategoryId();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));

		} catch (

		Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void sortDates() {

		try {
			if (!higriMode) {
				selecteStartDate = Utils.grigDatesConvert(selecteStartDate_G);
				selecteEndDate = Utils.grigDatesConvert(selecteEndDate_G);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contractDirect.setStartDate(selecteStartDate);
		contractDirect.setEndDate(selecteEndDate);

	}

	public void insertDate() throws ParseException {
		// System.out.println("Selected Date is ------>>>> " +
		// selecteStartDate);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//
		// contractDirect.setContractDate(selecteStartDate);
		// System.out.println("Selected Date datatype ------>>>> " +
		// selecteStartDate.getClass().getName());
		// String girgStartDate = sdf.format(selecteStartDate);
		// System.out.println(
		// "Selected Date datatype after convert to String ------>>>> " +
		// girgStartDate.getClass().getName());
		// System.out.println("Selected Date value after convert to String
		// ------>>>> " + girgStartDate);
		// contractDirect.setStartDate(HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(girgStartDate));
		//
		// System.out.println("Selected Date is ------>>>> " + selecteEndDate);
		// System.out.println("Selected Date datatype ------>>>> " +
		// selecteStartDate.getClass().getName());
		// String girgEndDate = sdf.format(selecteEndDate);
		//
		// System.out.println(
		// "Selected Date datatype after convert to String ------>>>> " +
		// girgEndDate.getClass().getName());
		// System.out.println("Selected Date value after convert to String
		// ------>>>> " + girgEndDate);
		// contractDirect.setEndDate(HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(girgEndDate));
	}

	public void update() {
		try {

			// insertDate();
			sortDates();
			contractDirect.setStatus(3);
			dataAccessService.updateObject(contractDirect);
			contractDirect.getActionsList().remove(ContractOperationEnum.CANCEL);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("update contractDirect: id: " + contractDirect.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("update contractDirect: id: " + contractDirect.getId());
		}
	}

	public void updateContractDirectNum() {

		newcontractNum = dataAccessService.findMaxContractDirectNum() + 1;
		loadIntroduction();

	}

	public void deleteContract() {
		try {
			dataAccessService.deleteObject(contractDirect);
			contractsDirectList.remove(contractDirect);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("delete contractDirect: id: " + contractDirect.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("delete contractDirect: id: " + contractDirect.getId());
		}
	}

	public String printAllContractsAction() {
		List<ContractDirect> contractsList = new ArrayList<ContractDirect>();
		ContractDirect contract = new ContractDirect();
		String reportName = "/reports/contract_direct_repolrt.jrxml";
		int num = 1;
		for (ContractDirect contObj : contractsDirectList) {
			contract = new ContractDirect();
			contract.setNum(num);
			contract.setInvestor(contObj.getInvestor());
			contract.setContractNum(contObj.getContractNum());
			contract.setStartDate(contObj.getStartDate());
			contract.setEndDate(contObj.getEndDate());
			contract.setInvestorName(contObj.getInvestorName());
			contract.setStatus(contObj.getStatus());
			contractsList.add(contract);

			num++;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		Utils.printPdfReportFromListDataSource(reportName, parameters, contractsList);
		// Utils.printPdfReport(reportName, parameters);

		return "";
	}

	public String printContractReport() {
		String reportName = "/reports/standard_contract.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		// reportName = getReportName();
		parameters.put("contractId", contractDirect.getId());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	private String getReportName() {
		String reportName;
		switch (contractDirect.getRealEstate().getSiteTypeId()) {
		case "12":
		case "53":
			reportName = "/reports/contract_store.jasper";
			break;
		default:
			reportName = "/reports/direct_contract.jasper";
			break;
		}
		return reportName;
	}

	public void doAction() {
		if (contractDirect.getOperationId() == 0)
			return;
		if (contractDirect.getOperationId() == ContractOperationEnum.RENEW.getAction()) {
			RequestContext.getCurrentInstance().execute("PF('addDialog').show();");
			addMode = true;
		} else if (contractDirect.getOperationId() == ContractOperationEnum.CANCEL.getAction()) {
			RequestContext.getCurrentInstance().execute("PF('cancelDialog').show();");
		} else if (contractDirect.getOperationId() == ContractOperationEnum.BILL.getAction()) {
			addBill = true;
			numBill = null;
			bill = new PayLicBills();
			setBillValues();
			RequestContext.getCurrentInstance().execute("PF('cancelDialog').show();");
		} else if (contractDirect.getOperationId() == ContractOperationEnum.DELETE.getAction())
			deleteContract();
	}

	private void setBillValues() {
		contractBillsList = dataAccessService.loadContractBills(contractDirect.getId(),
				MyConstants.CONTRACT_DIRECT_TYPE);
		ContractInstallmentsList = dataAccessService.loadContractInstallments(contractDirect.getId());
	}

	public void loadBill() {
		if ((numBill != null) && (numBill != 0))
			bill = dataAccessService.getBillbyBillNumber(numBill);
	}

	public void updateBill() {
		try {
			bill.setLicenceNumber(contractDirect.getId());
			bill.setLicenceType(MyConstants.CONTRACT_DIRECT_TYPE);
			dataAccessService.updateBillContract(bill);
			bill = new PayLicBills();
			numBill = null;
			contractBillsList = new ArrayList<>();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("update contract direct: id: " + contractDirect.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("update contract direct: id: " + contractDirect.getId());
		}
	}

	public void saveBill(Integer cotractId) {
		// double sum = 0.0;
		PayLicBills newBill = new PayLicBills();
		Investor inv = (Investor) dataAccessService.findEntityById(Investor.class, contractDirect.getInvestorId());
		newBill.setBillOwnerName(inv.getName());
		newBill.setLicenceNumber(cotractId);
		newBill.setLicenceType(MyConstants.CONTRACT_DIRECT_TYPE);
		newBill.setBillDate(HijriCalendarUtil.findCurrentHijriDate());
		newBill.setBillStatus(0);

		if (installmentNum == null || installmentNum == 0) {
			installmentNum = 1;
		}

		// Integer nYears =
		// HijriCalendarUtil.findDatesDifferenceInYears(contractDirect.getStartDate(),contractDirect.getEndDate());
		double sum = installmentNum * contractDirect.getAnnualRent();
		// if (sum < 1) {
		// sum = 1;
		// }
		newBill.setPayAmount(sum);
		newBill.setAplOwner(contractDirect.getInvRepresentNatId().toString()); /// رقم
																				/// السجل
		PayBillDetails det = new PayBillDetails();
		det.setCreatedBy(Utils.findCurrentUser().getUserId());
		det.setAmount(sum);
		det.setBillGYear(Calendar.getInstance().get(Calendar.YEAR));
		det.setPayMaster(1438);
		det.setPayDetails(1438);
		det.setCreatedIn(new Date());
		det.setBillHYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
		try {
			Integer billId = dataAccessService.save(newBill);
			det.setBillNumber(billId);
			dataAccessService.save(det);

		} catch (Exception e) {
			e.printStackTrace();
		}
		///////////////// ContractInstallments الاقساط//////////////////////////
		ContractInstallments conIns = new ContractInstallments();
		conIns.setContractId(cotractId);
		conIns.setContractNum(contractDirect.getContractNum());
		conIns.setContractAmount(contractDirect.getAnnualRent());
		for (int i = 0; i < installmentNum; i++) {
			conIns.setStatus(1);
			dataAccessService.save(conIns);
		}
		if (yearsNum > installmentNum)
			for (int i = 0; i < yearsNum - installmentNum; i++) {
				conIns.setStatus(0);
				dataAccessService.save(conIns);
			}

	}

	public void calculateProcessPeriod() {
		String startDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("startDate");
		String endDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("notifDate");
		if ((startDate != null) && (endDate != null))
			contractDirect
					.setProcessPeriod((int) (HijriCalendarUtil.findDatesDifferenceInDays(startDate, endDate) * 0.05));
	}

	public void loadIntroduction() {
		if (introductionId != null) {
			IntroContract intro = (IntroContract) dataAccessService.findEntityById(IntroContract.class, introductionId);
			setContractIntro(intro.getDescription());
		}
	}

	public void loadInvestor() {
		if (contractDirect.getInvestorId() != null) {
			Investor investor = (Investor) dataAccessService.findEntityById(Investor.class,
					contractDirect.getInvestorId());
			contractDirect.setInvRepresentFunct(Utils.loadMessagesFromFile("investor"));
			contractDirect.setInvRepresentIdDate(investor.getHigriCreateDate());
			contractDirect.setInvRepresentNatId(investor.getTradeRecord());
			contractDirect.setInvRepresentIdPlace(investor.getRegion());
			contractDirect.setInvRepresentName(investor.getName());
			contractDirect.setInvestor(investor);
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	// public List<Investor> getInvestorsList() {
	// return investorsList;
	// }
	//
	// public void setInvestorsList(List<Investor> investorsList) {
	// this.investorsList = investorsList;
	// }

	public String printBillReport(Integer contractId) {
		try {
			String reportName = "/reports/bill.jasper";
			PayLicBills payLicBill = dataAccessService.loadBillByLicNo(contractId);
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

	public List<String> getSelectedClausesList() {
		return selectedClausesList;
	}

	public void setSelectedClausesList(List<String> selectedClausesList) {
		this.selectedClausesList = selectedClausesList;
	}

	public List<ContractDirect> getContractsDirectList() {
		return contractsDirectList;
	}

	public void setContractsDirectList(List<ContractDirect> contractsDirectList) {
		this.contractsDirectList = contractsDirectList;
	}

	public List<ContractDirect> getFilteredContractsDirectList() {
		return filteredContractsDirectList;
	}

	public void setFilteredContractsDirectList(List<ContractDirect> filteredContractsDirectList) {
		this.filteredContractsDirectList = filteredContractsDirectList;
	}

	public ContractDirect getContractDirect() {
		return contractDirect;
	}

	public void setContractDirect(ContractDirect contractDirect) {
		this.contractDirect = contractDirect;
	}

	public Integer getIntroductionId() {
		return introductionId;
	}

	public void setIntroductionId(Integer introductionId) {
		this.introductionId = introductionId;
	}

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public Integer getContractTypeId() {
		return contractTypeId;
	}

	public void setContractTypeId(Integer contractTypeId) {
		this.contractTypeId = contractTypeId;
	}

	public boolean isAddBill() {
		return addBill;
	}

	public void setAddBill(boolean addBill) {
		this.addBill = addBill;
	}

	public Integer getNumBill() {
		return numBill;
	}

	public void setNumBill(Integer numBill) {
		this.numBill = numBill;
	}

	public PayLicBills getBill() {
		return bill;
	}

	public void setBill(PayLicBills bill) {
		this.bill = bill;
	}

	public List<PayLicBills> getContractBillsList() {
		return contractBillsList;
	}

	public void setContractBillsList(List<PayLicBills> contractBillsList) {
		this.contractBillsList = contractBillsList;
	}

	public List<ContractMainCategory> getContractMainCategoryList() {
		return contractMainCategoryList;
	}

	public void setContractMainCategoryList(List<ContractMainCategory> contractMainCategoryList) {
		this.contractMainCategoryList = contractMainCategoryList;
	}

	public List<ContractSubcategory> getContractSubcategoryList() {
		return contractSubcategoryList;
	}

	public void setContractSubcategoryList(List<ContractSubcategory> contractSubcategoryList) {
		this.contractSubcategoryList = contractSubcategoryList;
	}

	public List<ContractStatus> getContractStatusList() {
		return contractStatusList;
	}

	public void setContractStatusList(List<ContractStatus> contractStatusList) {
		this.contractStatusList = contractStatusList;
	}

	public String getSelecteStartDate() {
		selecteStartDate = contractDirect.getStartDate();
		return selecteStartDate;
	}

	public void setSelecteStartDate(String selecteStartDate) {
		this.selecteStartDate = selecteStartDate;
	}

	public Date getSelecteStartDate_G() {
		if (contractDirect != null && contractDirect.getStartDate() != null) {
			try {
				selecteStartDate_G = Utils.convertHDateToGDate(contractDirect.getStartDate());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return selecteStartDate_G;
	}

	public void setSelecteStartDate_G(Date selecteStartDate_G) {

		this.selecteStartDate_G = selecteStartDate_G;
	}

	public String getSelecteEndDate() {
		selecteEndDate = contractDirect.getEndDate();
		return selecteEndDate;
	}

	public void setSelecteEndDate(String selecteEndDate) {
		this.selecteEndDate = selecteEndDate;
	}

	public Date getSelecteEndDate_G() {
		if (contractDirect.getEndDate() != null) {
			try {
				selecteEndDate_G = Utils.convertHDateToGDate(contractDirect.getEndDate());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return selecteEndDate_G;
	}

	public void setSelecteEndDate_G(Date selecteEndDate_G) {
		this.selecteEndDate_G = selecteEndDate_G;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public static Logger getLogger() {
		return logger;
	}

	// public Date getSelecteStartDate() {
	// try {
	// if (contractDirect.getStartDate() != null)
	// setSelecteStartDate(Utils.convertHDateToGDate(contractDirect.getStartDate()));
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return selecteStartDate;
	// }

	// public void setSelecteStartDate(Date selecteStartDate) {
	// this.selecteStartDate = selecteStartDate;
	// }
	//
	// public Date getSelecteEndDate() {
	// try {
	// if (contractDirect.getEndDate() != null)
	// setSelecteEndDate(Utils.convertHDateToGDate(contractDirect.getEndDate()));
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return selecteEndDate;
	// }
	//
	// public void setSelecteEndDate(Date selecteEndDate) {
	// this.selecteEndDate = selecteEndDate;
	// }

	public List<IntroContract> getIntroContractList() {
		if ((introContractList == null) || (introContractList.isEmpty())) {
			introContractList = dataAccessService.loadAllIntroContracts();
			loadIntroduction();
		}
		return introContractList;
	}

	public void setIntroContractList(List<IntroContract> introContractList) {
		this.introContractList = introContractList;
	}

	public String getIntroName() {
		return introName;
	}

	public void setIntroName(String introName) {
		this.introName = introName;
	}

	public IntroContract getIntrodaction() {
		return introdaction;
	}

	public void setIntrodaction(IntroContract introdaction) {
		this.introdaction = introdaction;
	}

	public String getIntroDescription() {
		return introDescription;
	}

	public void setIntroDescription(String introDescription) {
		this.introDescription = introDescription;
	}

	public Integer getNewcontractNum() {
		return newcontractNum;
	}

	public void setNewcontractNum(Integer newcontractNum) {
		this.newcontractNum = newcontractNum;
	}

	public Integer getYearsNum() {
		return yearsNum;
	}

	public void setYearsNum(Integer yearsNum) {
		this.yearsNum = yearsNum;
	}

	public Integer getInstallmentNum() {
		return installmentNum;
	}

	public void setInstallmentNum(Integer installmentNum) {
		this.installmentNum = installmentNum;
	}

	public List<ContractInstallments> getContractInstallmentsList() {
		return ContractInstallmentsList;
	}

	public void setContractInstallmentsList(List<ContractInstallments> contractInstallmentsList) {
		ContractInstallmentsList = contractInstallmentsList;
	}

	public boolean isTableHigriMode() {
		return tableHigriMode;
	}

	public void setTableHigriMode(boolean tableHigriMode) {
		this.tableHigriMode = tableHigriMode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ContractMainCategory getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(ContractMainCategory mainCategory) {
		this.mainCategory = mainCategory;
	}

	public ContractSubcategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(ContractSubcategory subCategory) {
		this.subCategory = subCategory;
	}

	public Integer getContNum() {
		return contNum;
	}

	public void setContNum(Integer contNum) {
		this.contNum = contNum;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
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

	public String getFromEndDate() {
		return fromEndDate;
	}

	public void setFromEndDate(String fromEndDate) {
		this.fromEndDate = fromEndDate;
	}

	public String getToEndDate() {
		return toEndDate;
	}

	public void setToEndDate(String toEndDate) {
		this.toEndDate = toEndDate;
	}

	public boolean isAllowFiltre() {
		return allowFiltre;
	}

	public void setAllowFiltre(boolean allowFiltre) {
		this.allowFiltre = allowFiltre;
		// Utils.updateUIComponent("includeform:filters_panel");
	}

	public Integer getTotalInstallmentCount() {
		return totalInstallmentCount;
	}

	public void setTotalInstallmentCount(Integer totalInstallmentCount) {
		this.totalInstallmentCount = totalInstallmentCount;
	}

	public Integer getContractType() {
		contractType = contractDirect.getContractType();
		return contractType;
	}

	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}

	public String getDurationLabel() {
		return durationLabel;
	}

	public void setDurationLabel(String durationLabel) {
		this.durationLabel = durationLabel;
	}

	public Integer getOldInstallment() {
		return oldInstallment;
	}

	public void setOldInstallment(Integer oldInstallment) {
		this.oldInstallment = oldInstallment;
	}

	public Integer getDiscountExceptionel() {
		return discountExceptionel;
	}

	public void setDiscountExceptionel(Integer discountExceptionel) {
		this.discountExceptionel = discountExceptionel;
	}

	public Integer getDiscountDuration() {
		return discountDuration;
	}

	public void setDiscountDuration(Integer discountDuration) {
		this.discountDuration = discountDuration;
	}

	public List<ContractsFees> getContractsFeesList() {
		return contractsFeesList;
	}

	public void setContractsFeesList(List<ContractsFees> contractsFeesList) {
		this.contractsFeesList = contractsFeesList;
	}

	public double getTotalDiscount() {
		calculateDiscount();
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public List<Investor> getInvestorsList() {
		return investorsList;
	}

	public void setInvestorsList(List<Investor> investorsList) {
		this.investorsList = investorsList;
	}

	public List<ContractDirect> getFilteredContractsNumList() {
		return filteredContractsNumList;
	}

	public void setFilteredContractsNumList(List<ContractDirect> filteredContractsNumList) {
		this.filteredContractsNumList = filteredContractsNumList;
	}

	public Integer getInvestorID() {
		return investorID;
	}

	public void setInvestorID(Integer investorID) {
		this.investorID = investorID;
	}

	public String getContractIntro() {
		return contractIntro;
	}

	public void setContractIntro(String contractIntro) {
		this.contractIntro = contractIntro;
	}

	public Integer getBillBandNumber() {
		return billBandNumber;
	}

	public void setBillBandNumber(Integer billBandNumber) {
		this.billBandNumber = billBandNumber;
	}

	public List<PayMaster> getItemsList() {
		if ((itemsList == null) || (itemsList.isEmpty()))
			itemsList = dataAccessService.loadAllPayMasters();
		return itemsList;
	}

	public void setItemsList(List<PayMaster> itemsList) {
		this.itemsList = itemsList;
	}

}
