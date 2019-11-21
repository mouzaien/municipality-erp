package com.bkeryah.managedBean.investment;

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

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.investment.ContractDirect;
import com.bkeryah.entities.investment.ContractMainCategory;
import com.bkeryah.entities.investment.ContractStatus;
import com.bkeryah.entities.investment.ContractSubcategory;
import com.bkeryah.entities.investment.IntroContract;
import com.bkeryah.entities.investment.Investor;
import com.bkeryah.service.IDataAccessService;

import utilities.ContractOperationEnum;
import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

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
	private Integer introductionId;
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

	private String selecteStartDate;
	private Date selecteStartDate_G;
	private String selecteEndDate;
	private Date selecteEndDate_G;

	private boolean higriMode;

	@PostConstruct
	public void init() {
		loadContracts();
		// loadContractOperations();
		// Collections.sort(contractsDirectList);

		contractMainCategoryList = dataAccessService.loadAllContractMainCategory();
		contractSubcategoryList = dataAccessService.loadAllContractSubcategory();
		contractStatusList = dataAccessService.loadAllContractStatus();
		System.out.println("contractStatusList ----- >>>> " + contractStatusList.size());

	}

	private void loadContractOperations() {
		for (ContractDirect cont : contractsDirectList) {
			cont.getActionsList().add(ContractOperationEnum.DELETE);
			cont.getActionsList().add(ContractOperationEnum.BILL);
			try {
				Date endDate = Utils.convertHDateToGDate(cont.getEndDate());
				if(endDate == null)
					return ;
				if (endDate.before(new Date())) {
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
			// contractDirect.setContractDate(new Date());

			if (contractDirect.getOperationId() == ContractOperationEnum.RENEW.getAction()) {
				contractDirect.setStatus(2);
			} else {
				contractDirect.setStatus(1);
			}
			contractDirect.setContractDate(new Date());
			dataAccessService.saveContractDirect(contractDirect);
			if (contractsDirectList == null)
				contractsDirectList = new ArrayList<>();
			contractDirect = (ContractDirect) dataAccessService.findEntityById(ContractDirect.class,
					contractDirect.getId());
			contractsDirectList.add(contractDirect);
			contractDirect = new ContractDirect();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("add contractDirect: id: " + contractDirect.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("add contractDirect: id: " + contractDirect.getId());
		}
	}

	public void sortDates() {

		try {
			if (!higriMode) {
				selecteStartDate = Utils.grigDatesConvert(selecteStartDate_G);
				selecteEndDate = Utils.grigDatesConvert(selecteEndDate_G);
				contractDirect.setBillStartDate(Utils.grigDatesConvert(contractDirect.getBillGStartDate()));
				contractDirect.setBillEndDate(Utils.grigDatesConvert(contractDirect.getBillGEndDate()));
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

	public String printContractReport() {
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		reportName = getReportName();
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

	public void saveBill() {
		// double sum = 0.0;
		// PayLicBills newBill = new PayLicBills();
		// newBill.setBillOwnerName(contractDirect.getInvestor().getName());
		// newBill.setLicenceNumber(contractDirect.getId());
		// newBill.setLicenceType(MyConstants.CONTRACT_DIRECT_TYPE);
		// newBill.setBillStatus(0);
		// for (PayBillDetails billDetail : selectedDetailList) {
		// sum = sum + billDetail.getAmount();
		// }
		// newBill.setPayAmount(sum);
		// setNewBillNmber(dataAccessService.saveBill(newBill,
		// selectedDetailList).toString());
		// newBuilding.setLicNewBillNumber(newBillNmber);
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
			contractDirect.setIntroduction(intro.getDescription());
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
		try {
			selecteStartDate_G = Utils.convertHDateToGDate(contractDirect.getStartDate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		try {
			selecteEndDate_G = Utils.convertHDateToGDate(contractDirect.getEndDate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
