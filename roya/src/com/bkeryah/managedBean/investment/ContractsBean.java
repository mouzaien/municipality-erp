package com.bkeryah.managedBean.investment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.*;
import org.primefaces.context.RequestContext;

import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.investment.AnnoucementDetails;
import com.bkeryah.entities.investment.Contract;
import com.bkeryah.entities.investment.ContractMainCategory;
import com.bkeryah.entities.investment.ContractStatus;
import com.bkeryah.entities.investment.ContractSubcategory;
import com.bkeryah.entities.investment.IntroContract;
import com.bkeryah.entities.investment.Investor;
import com.bkeryah.entities.investment.Tender;
import com.bkeryah.service.IDataAccessService;

import utilities.ContractOperationEnum;
import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ContractsBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<Contract> contractsList;
	private List<Contract> filteredContractsList;
	private Contract contract = new Contract();
	private List<AnnoucementDetails> annoucementDetailsList = new ArrayList<>();
	private boolean addMode;
	private List<Tender> tendersList;
	private List<String> selectedClausesList;
	private Integer annoucementDetailsId;
	private Integer introductionId;
	private boolean addBill;
	private Integer numBill;
	private PayLicBills bill;
	private List<PayLicBills> contractBillsList;

	private List<ContractMainCategory> contractMainCategoryList = new ArrayList<ContractMainCategory>();
	private List<ContractSubcategory> contractSubcategoryList = new ArrayList<ContractSubcategory>();
	private List<ContractStatus> contractStatusList = new ArrayList<ContractStatus>();
	private static final Logger logger = LogManager.getLogger(ContractsBean.class);

	private boolean higriMode = true;

	private String selecteStartDate;
	private Date selecteStartDate_G;
	private String selecteEndDate;
	private Date selecteEndDate_G;

	@PostConstruct
	public void init() {
		loadContracts();
		loadContractOperations();
		Collections.sort(contractsList);

		contractMainCategoryList = dataAccessService.loadAllContractMainCategory();
		contractSubcategoryList = dataAccessService.loadAllContractSubcategory();
		contractStatusList = dataAccessService.loadAllContractStatus();
		System.out.println("contractStatusList ----- >>>> " + contractStatusList.size());

	}

	private void loadContractOperations() {
		for (Contract cont : contractsList) {
			cont.getActionsList().add(ContractOperationEnum.DELETE);
			cont.getActionsList().add(ContractOperationEnum.BILL);
			try {
				if (Utils.convertHDateToGDate(cont.getEndDate()).before(new Date()))
					cont.getActionsList().add(ContractOperationEnum.RENEW);
				else if (cont.getStatus() != ContractOperationEnum.CANCEL.getAction())
					cont.getActionsList().add(ContractOperationEnum.CANCEL);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void loadContracts() {
		contractsList = dataAccessService.loadAllContracts();
	}

	public void loadContract() {
		addMode = false;
	}

	public void addContract() {
		introductionId = null;
		contract = new Contract();
		loadAnnDetails();
		// contract.setTender(new Tender());

		// if(investorsList == null || investorsList.isEmpty())
		// investorsList = dataAccessService.loadAllInvestors();
		addMode = true;
	}

	private void loadAnnDetails() {
		if (annoucementDetailsList == null || annoucementDetailsList.isEmpty()) {
			Integer[] status = { 1 };
			annoucementDetailsList = dataAccessService.loadAnnouncementDetailsHavingTenders(status);
		}
	}

	public void save() {
		try {

			// insertDate();
			sortDates();
			// String startDate =
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
			// .get("startDate");
			// contract.setStartDate(startDate);
			// String endDate =
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
			// .get("notifDate");
			// contract.setEndDate(endDate);
			// contract.setContractYears(
			// HijriCalendarUtil.findDatesDifferenceInYears(contract.getStartDate(),
			// contract.getEndDate()));
			// contract.setContractDate(new Date());
			if (contract.getOperationId() == ContractOperationEnum.RENEW.getAction()) {
				contract.setStatus(2);
			} else {
				contract.setStatus(1);
			}
			dataAccessService.saveContract(contract, selectedClausesList);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			if (contractsList == null)
				contractsList = new ArrayList<>();
			contract = (Contract) dataAccessService.findEntityById(Contract.class, contract.getId());
			contractsList.add(contract);
			contract = new Contract();
			logger.info("add contract: id: " + contract.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("add contract: id: " + contract.getId());
		}
	}

	public void sortDates() {

		try {
			if (higriMode) {
				selecteStartDate_G = Utils.convertHDateToGDate(selecteStartDate);
				selecteEndDate_G = Utils.convertHDateToGDate(selecteEndDate);
			} else {
				selecteStartDate = Utils.grigDatesConvert(selecteStartDate_G);
				selecteEndDate = Utils.grigDatesConvert(selecteEndDate_G);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contract.setContractDate(selecteStartDate_G);
		contract.setStartDate(selecteStartDate);
		contract.setEndDate(selecteEndDate);
	}

	public void insertDate() throws ParseException {
		// System.out.println("Selected Date is ------>>>> " +
		// selecteStartDate);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//
		// contract.setContractDate(selecteStartDate);
		// System.out.println("Selected Date datatype ------>>>> " +
		// selecteStartDate.getClass().getName());
		// String girgStartDate = sdf.format(selecteStartDate);
		// System.out.println(
		// "Selected Date datatype after convert to String ------>>>> " +
		// girgStartDate.getClass().getName());
		// System.out.println("Selected Date value after convert to String
		// ------>>>> " + girgStartDate);
		// contract.setStartDate(HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(girgStartDate));
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
		// contract.setEndDate(HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(girgEndDate));
	}

	public void update() {
		try {
			sortDates();
			// insertDate();
			contract.setStatus(3);
			dataAccessService.updateObject(contract);
			contract.getActionsList().remove(ContractOperationEnum.CANCEL);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("update contract: id: " + contract.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("update contract: id: " + contract.getId());
		}
	}

	public void updateBill() {
		try {
			bill.setLicenceNumber(contract.getId());
			bill.setLicenceType(MyConstants.CONTRACT_TYPE);
			dataAccessService.updateBillContract(bill);
			bill = new PayLicBills();
			numBill = null;
			contractBillsList = new ArrayList<>();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("update contract: id: " + contract.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("update contract: id: " + contract.getId());
		}
	}

	private void saveBill() {
		// double sum = 0.0;
		// PayLicBills newBill = new PayLicBills();
		// newBill.setBillOwnerName(contract.getTender().getInvestor().getName());
		// newBill.setLicenceNumber(contract.getId());
		// for (PayBillDetails billDetail : selectedDetailList) {
		// sum = sum + billDetail.getAmount();
		// }
		// newBill.setPayAmount(sum);
		// setNewBillNmber(dataAccessService.saveBill(newBill,
		// selectedDetailList).toString());
		// newBuilding.setLicNewBillNumber(newBillNmber);
		// RequestContext context = RequestContext.getCurrentInstance();
		// context.execute("PF('PrintBillDlgVar').show();");

	}

	public void loadBill() {
		if ((numBill != null) && (numBill != 0))
			bill = dataAccessService.getBillbyBillNumber(numBill);
	}

	public void deleteContract() {
		try {
			dataAccessService.deleteObject(contract);
			contractsList.remove(contract);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("delete contract: id: " + contract.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("delete contract: id: " + contract.getId());
		}
	}

	public String printContractReport() {
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		reportName = "/reports/contract.jasper";
		parameters.put("contractId", contract.getId());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void doAction() {
		if (contract.getOperationId() == 0)
			return;
		if (contract.getOperationId() == ContractOperationEnum.RENEW.getAction()) {
			annoucementDetailsId = contract.getTender().getAnnouncementDetailsId();
			annoucementDetailsList.add((AnnoucementDetails) dataAccessService.findEntityById(AnnoucementDetails.class,
					annoucementDetailsId));
			RequestContext.getCurrentInstance().execute("PF('addDialog').show();");
			addMode = true;
		} else if (contract.getOperationId() == ContractOperationEnum.CANCEL.getAction()) {
			RequestContext.getCurrentInstance().execute("PF('cancelDialog').show();");
		} else if (contract.getOperationId() == ContractOperationEnum.BILL.getAction()) {
			addBill = true;
			numBill = null;
			bill = new PayLicBills();
			setBillValues();
			RequestContext.getCurrentInstance().execute("PF('cancelDialog').show();");
		} else if (contract.getOperationId() == ContractOperationEnum.DELETE.getAction())
			deleteContract();
	}

	private void setBillValues() {
		contractBillsList = dataAccessService.loadContractBills(contract.getId(), MyConstants.CONTRACT_TYPE);
	}

	public void calculateProcessPeriod() {
		String startDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("startDate");
		String endDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("notifDate");
		if ((startDate != null) && (endDate != null))
			contract.setProcessPeriod((int) (HijriCalendarUtil.findDatesDifferenceInDays(startDate, endDate) * 0.05));
	}

	public void loadTenders() {
		if (annoucementDetailsId != null) {
			tendersList = dataAccessService.loadTendersByAnnouncementDetailsId(annoucementDetailsId);
		}
	}

	public void loadAssignedTender() {
		if ((annoucementDetailsId != null) && (annoucementDetailsId != 0)) {
			Tender tender = dataAccessService.loadAssignedTenderByAnnouncementDetailsId(annoucementDetailsId);
			contract.setTenderId(tender.getId());
			contract.setTender(tender);
			contract.setAnnualRent(tender.getTenderPrice());
			Investor investor = tender.getInvestor();
			contract.setInvRepresentFunct(Utils.loadMessagesFromFile("investor"));
			contract.setInvRepresentIdDate(investor.getHigriCreateDate());
			contract.setInvRepresentNatId(investor.getTradeRecord());
			contract.setInvRepresentIdPlace(investor.getRegion());
			contract.setInvRepresentName(investor.getName());
		}
	}

	public void loadTender() {
		if (contract.getTenderId() != null) {
			Tender tender = (Tender) dataAccessService.findEntityById(Tender.class, contract.getTenderId());
			contract.setAnnualRent(tender.getTenderPrice());
		}
	}

	public void loadIntroduction() {
		if (introductionId != null) {
			IntroContract intro = (IntroContract) dataAccessService.findEntityById(IntroContract.class, introductionId);
			contract.setIntroduction(intro.getDescription());
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<Contract> getContractsList() {
		return contractsList;
	}

	public void setContractsList(List<Contract> contractsList) {
		this.contractsList = contractsList;
	}

	public List<Contract> getFilteredContractsList() {
		return filteredContractsList;
	}

	public void setFilteredContractsList(List<Contract> filteredContractsList) {
		this.filteredContractsList = filteredContractsList;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public List<AnnoucementDetails> getAnnoucementDetailsList() {
		return annoucementDetailsList;
	}

	public void setAnnoucementDetailsList(List<AnnoucementDetails> annoucementDetailsList) {
		this.annoucementDetailsList = annoucementDetailsList;
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

	public Integer getAnnoucementDetailsId() {
		return annoucementDetailsId;
	}

	public void setAnnoucementDetailsId(Integer annoucementDetailsId) {
		this.annoucementDetailsId = annoucementDetailsId;
	}

	public List<Tender> getTendersList() {
		return tendersList;
	}

	public void setTendersList(List<Tender> tendersList) {
		this.tendersList = tendersList;
	}

	public Integer getIntroductionId() {
		return introductionId;
	}

	public void setIntroductionId(Integer introductionId) {
		this.introductionId = introductionId;
	}

	public boolean isAddBill() {
		return addBill;
	}

	public void setAddBill(boolean addBill) {
		this.addBill = addBill;
	}

	public Integer isNumBill() {
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

	public Integer getNumBill() {
		return numBill;
	}

	// public Date getSelecteStartDate() {
	// try {
	// if (contract.getStartDate() != null)
	// setSelecteStartDate(Utils.convertHDateToGDate(contract.getStartDate()));
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return selecteStartDate;
	// }
	//
	// public void setSelecteStartDate(Date selecteStartDate) {
	// this.selecteStartDate = selecteStartDate;
	// }
	//
	// public Date getSelecteEndDate() {
	// try {
	// if (contract.getEndDate() != null)
	// setSelecteEndDate(Utils.convertHDateToGDate(contract.getEndDate()));
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
	//
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
		selecteStartDate = contract.getStartDate();
		return selecteStartDate;
	}

	public void setSelecteStartDate(String selecteStartDate) {
		this.selecteStartDate = selecteStartDate;
	}

	public Date getSelecteStartDate_G() {
		try {
			selecteStartDate_G = Utils.convertHDateToGDate(contract.getStartDate());
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
		selecteEndDate = contract.getEndDate();
		return selecteEndDate;
	}

	public void setSelecteEndDate(String selecteEndDate) {
		this.selecteEndDate = selecteEndDate;
	}

	public Date getSelecteEndDate_G() {
		try {
			selecteEndDate_G = Utils.convertHDateToGDate(contract.getEndDate());
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

}
