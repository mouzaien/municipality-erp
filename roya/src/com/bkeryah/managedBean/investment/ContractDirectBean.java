package com.bkeryah.managedBean.investment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.PayBillDetails;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.PayMaster;
import com.bkeryah.entities.WrkSection;
import com.bkeryah.entities.investment.ContractComponents;
import com.bkeryah.entities.investment.ContractDirect;
import com.bkeryah.entities.investment.ContractInstallments;
import com.bkeryah.entities.investment.ContractMainCategory;
import com.bkeryah.entities.investment.ContractMessage;
import com.bkeryah.entities.investment.ContractStatus;
import com.bkeryah.entities.investment.ContractSubcategory;
import com.bkeryah.entities.investment.ContractType;
import com.bkeryah.entities.investment.ContractsFees;
import com.bkeryah.entities.investment.IntroContract;
import com.bkeryah.entities.investment.Investor;
import com.bkeryah.entities.investment.RealEstate;
import com.bkeryah.service.IDataAccessService;

import service.ISmsService;
import service.SmsService;
import sms.sender.ResponseTypeEnum;
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

	private Integer newcontractNum = 0;
	private Integer yearsNum;
	private Integer installmentNum = 0; // الأقساط المفوترة
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
	private Integer contractType = 1; // سنوي 1 او شهري 2 او 3يومي
	private String durationLabel = new String();
	// private Integer oldInstallment = new Integer(0);
	private Integer discountExceptionel = new Integer(0);// خصم إستثنائي مثلا 5%
															// جائحة كورونا
	private Integer discountDuration = new Integer(0);
	private List<ContractsFees> contractsFeesList = new ArrayList<ContractsFees>();
	private List<ContractsFees> selectedcontractsFeesList = new ArrayList<ContractsFees>();

	private double totalDiscount = 0.000;
	private List<Investor> investorsList;
	private List<ContractDirect> filteredContractsNumList = new ArrayList<ContractDirect>();
	private Integer investorID = -1;
	private String contractIntro;
	private Integer billBandNumber;// = 1438;
	private List<PayMaster> itemsList;
	private Integer contractStatus;
	private List<ContractsFees> feesList = new ArrayList<ContractsFees>();
	private List<WrkSection> sectionsList = new ArrayList<>();
	private Integer sectionId;
	private Integer contractMaincatgId;
	private Integer contractSubcatgId;
	private Integer activityIdFilter;
	private String componentsFilter;
	private List<ContractComponents> allContractComponentList = new ArrayList<>();
	private List<ContractType> contractTypesList = new ArrayList<>();
	private Integer payStatus;
	private Integer contractStatusFilter;
	private Integer reminderDiscountDuration = 0;
	private Integer oldDiscountDuration = new Integer(0);
	private Integer checkedFees = new Integer(0);
	private double reminderDiscountAmount = 0;
	private boolean renewBill;
	private List<RealEstate> unusedRealEstatesList;
	private boolean assigned;
	private boolean ended;
	private String oldStartdate;
	private String oldenddate;
	private String bayan;
	private boolean allowDelete;
	private String messageTxt = new String();
	ISmsService smsService = new SmsService();
	private List<ContractMessage> contractMsgList = new ArrayList<ContractMessage>();
	private List<ContractMessage> filterContractMsgList;
	private String phoneNo = new String();
	public static final String HOST_NAME = "smtp.gmail.com";
	public static final int PORT = 465;
	public String emailFrom = new String();
	public String emailTo = new String();
	public String subject = new String();
	public String emailMessage = new String();
	private String tradRecod = new String();

	@PostConstruct
	public void init() {
		loadContracts();
		// loadContractOperations();
		// Collections.sort(contractsDirectList);
		ArcUsers sessionUsr = Utils.findCurrentUser();
		// sessionUsr.getDeptId()
		sectionsList = dataAccessService.getwrksectionByDepId(11);
		allContractComponentList = dataAccessService.loadAllContractComponents();
		contractTypesList = dataAccessService.loadAllContractTypes();
		contractMainCategoryList = dataAccessService.loadAllContractMainCategory();

		// contractSubcategoryList =
		// dataAccessService.loadAllContractSubcategory();
		contractStatusList = dataAccessService.loadAllContractStatus();

		introContractList = dataAccessService.loadAllIntroContracts();
		investorsList = dataAccessService.loadAllInvestors();
		filteredContractsNumList = dataAccessService.loadAllContractDirects(contractTypeId);
		// compaerFeesAndBillDates();

	}

	private void loadContractOperations() {
		for (ContractDirect cont : contractsDirectList) {
			// cont.getActionsList().add(ContractOperationEnum.DELETE);
			// cont.getActionsList().add(ContractOperationEnum.BILL);
			try {
				if (cont.getEndDate() != null && cont.getEndDate() != null
						&& Utils.convertHDateToGDate(cont.getEndDate()).before(new Date())) {
					// cont.getActionsList().add(ContractOperationEnum.RENEW);
					cont.setFinished(1);
				} else {
					cont.setFinished(0);
					// if (cont.getCont() !=
					// ContractOperationEnum.CANCEL.getAction()) {
					if (cont.getContractStatId() != ContractOperationEnum.CANCEL.getAction()) {
						cont.getActionsList().add(ContractOperationEnum.SMS);
						cont.getActionsList().add(ContractOperationEnum.RENEW);
						cont.getActionsList().add(ContractOperationEnum.GIVE_UP);
						cont.getActionsList().add(ContractOperationEnum.CANCEL);
						cont.getActionsList().add(ContractOperationEnum.DELETE);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		compaerFeesAndBillDates();
	}

	public void loadContractsByFinishingStatus() {

		contractsDirectList = dataAccessService.loadendedContractDirectListByStatus(status);
		loadContractOperations();
	}

	public void getContractComponentsByActivityId() {
		if (activityIdFilter != null && activityIdFilter > 0)
			allContractComponentList = dataAccessService.getContractComponentsListByActivityId(activityIdFilter);

		loadContractDirectListByAllFilters();

	}

	public void loadContractDirectListByAllFilters() {
		contractsDirectList.clear();
		// filteredContractsDirectList = null;
		fromStartDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:fromStartDate");
		toStartDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:toStartDate");
		fromEndDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:fromEndDate");
		toEndDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("includeform:toEndDate");
		contractsDirectList = dataAccessService.loadContractDirectListByAllFilters(contNum, investorID, status,
				fromStartDate, toStartDate, fromEndDate, toEndDate, sectionId, contractMaincatgId, contractSubcatgId,
				activityIdFilter, componentsFilter, contractStatusFilter);
		for (ContractDirect contObj : contractsDirectList) {
			Investor inv = (Investor) dataAccessService.findEntityById(Investor.class, contObj.getInvestorId());
			contObj.setInvestorName(inv.getName());
		}
		loadContractOperations();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('ContractsTable').clearFilters()");
		filteredContractsDirectList = contractsDirectList;
		Utils.updateUIComponent("includeform:allusersettingdt");
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
		contractDirect.setContractStatId(1);
		Utils.openDialog("addDialog");
	}

	public void extendContract(ContractDirect contract) {
		contractDirect = contract;
		if (contractDirect.getOperationId() == ContractOperationEnum.RENEW.getAction()) {
			// تجديد /تمديد
			contractDirect.setStartDate(contractDirect.getEndDate());
			contractDirect.setSelecteStartDate_G(contractDirect.getSelecteEndDate_G());
			contractDirect.setEndDate(null);
			contractDirect.setSelecteEndDate_G(null);
			Utils.openDialog("addDialog");
		} else if (contractDirect.getOperationId() == ContractOperationEnum.CANCEL.getAction()) {
			// إلغاء او انهاء
			Utils.openDialog("cancel_dlg");
		} else if (contractDirect.getOperationId() == ContractOperationEnum.GIVE_UP.getAction()) {
			// متنازل عنه
			assigned = true;
			Utils.openDialog("addDialog");
		} else if (contractDirect.getOperationId() == ContractOperationEnum.SMS.getAction()) {
			// تنبية اشعارات رسائل الجوال
			messageTxt = "تم إصدار فاتورة رقم " + contractDirect.getSmsBillNo() + " للعقد رقم  "
					+ contractDirect.getContractNum() + " بتاريخ إستحقاق " + contractDirect.getSmsDueHDate();
			Utils.openDialog("sendSMS_dlg");
		} else if (contractDirect.getOperationId() == ContractOperationEnum.DELETE.getAction()) {
			// حذف العقد
			Utils.openDialog("delete_dlg");
		}
		// switch (contractDirect.getOperationId()) {
		// case 2:// تجديد /تمديد
		//
		// contractDirect.setStartDate(contractDirect.getEndDate());
		// contractDirect.setSelecteStartDate_G(contractDirect.getSelecteEndDate_G());
		// contractDirect.setEndDate(null);
		// contractDirect.setSelecteEndDate_G(null);
		// Utils.openDialog("addDialog");
		// break;
		// case 3: // إلغاء او انهاء
		// contractDirect.setStatus(ContractOperationEnum.CANCEL.getAction());
		// Utils.openDialog("cancel_dlg");
		// break;
		// case 5: // متنازل عنه
		// assigned = true;
		// Utils.openDialog("addDialog");
		// break;
		// }
		contractDirect.setStatus(ContractOperationEnum.CANCEL.getAction());

		if (contractDirect.getOperationId() > 0) {
			contractDirect.setContractStatId(contractDirect.getOperationId());
		}
	}

	public void updateBandNum() {

		System.out.println("Bayan :" + bayan);
		System.out.println("BandNum :" + billBandNumber);
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
		// getYearsNum();
		if (yearsNum != null && yearsNum > 0 && contractType != null) {
			switch (contractType) {
			case 1:
				totalInstallmentCount = yearsNum;
				Utils.updateUIComponent(":includeform:total_installment");
				break;
			case 2:
				totalInstallmentCount = yearsNum;
				Utils.updateUIComponent(":includeform:total_installment");
				break;
			case 3:
				totalInstallmentCount = 1;
				Utils.updateUIComponent(":includeform:total_installment");
				break;
			}
		}
	}

	public void loadContractFeeslistbycontractId(ContractDirect selectedContract) {
		contractsFeesList = new ArrayList<ContractsFees>();
		contractDirect = selectedContract;
		contractsFeesList = dataAccessService.loadContractFeeslistbycontractId(selectedContract.getId());
		oldDiscountDuration = 0;
		updateFeesRenewableState(-1);
		for (ContractsFees fees : contractsFeesList) {
			fees.setOldFactId(fees.getFactId());
			if (fees.getDiscountduration() != null)
				oldDiscountDuration += fees.getDiscountduration();
		}
		calculateTotalFeesDuration();
		renewBill = false;
	}

	public String save() {
		try {

			if ((billBandNumber == null || billBandNumber <= 0) && installmentNum > 0) {
				MsgEntry.addErrorMessage("يجب إدخال اسم البند");
				return "";
			} else if (contractDirect.getSelecteEndDate_G().compareTo(contractDirect.getSelecteStartDate_G()) < 0) {
				MsgEntry.addErrorMessage("تاريخ البداية يجب ان يكون قبل تاريخ النهاية");
				return "";

			} else {
				// insertDate();
				// sortDates();
				sortNewDates();
				/////////////
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
				contractDirect.setContractStatId(contractStatus);
				contractDirect.setContractDate(new Date());
				// Integer usrId =
				// dataAccessService.getPropertiesValue("PRESIDENT_ID");
				contractDirect.setPresidentId(dataAccessService.getPropertiesValue("PRESIDENT_ID"));

				if (newcontractNum != null && newcontractNum > 0) {
					contractDirect.setContractNum(newcontractNum);
				}
				contractDirect.setContractType(contractType);
				contractDirect.setContractYears(yearsNum);
				if (contractDirect.getOperationId() == 0)
					contractDirect.setIntroduction(contractIntro);
				ArcUsers sessionUsr = Utils.findCurrentUser();
				contractDirect.setUsrDeptId(sessionUsr.getDeptId());
				contractDirect.setUsrSectionId(sessionUsr.getWrkSectionId());
				// createContractsFeesList();
				Integer cotractId;

				// create fees
				// add bills for contract
				// saveBill(cotractId);

				if (contractDirect.getContractStatId() != null) {
					// نمديد او تجديد
					if (contractDirect.getOperationId() == ContractOperationEnum.RENEW.getAction()) {
						ContractDirect newContractDirect = new ContractDirect();
						contractDirect.setContractStatId(ContractOperationEnum.RENEW.getAction());
						newContractDirect = contractDirect;
						newContractDirect.setContractNum(contractDirect.getContractNum());
						cotractId = dataAccessService.saveContractDirect(newContractDirect, contractsFeesList,
								billBandNumber, bayan);
					} else if (contractDirect.getOperationId() == ContractOperationEnum.GIVE_UP.getAction()) {
						contractDirect.setContractStatId(ContractOperationEnum.GIVE_UP.getAction());
						ContractDirect asignedContractDirect = new ContractDirect();
						asignedContractDirect = contractDirect;
						asignedContractDirect.setContractNum(contractDirect.getContractNum());
						cotractId = dataAccessService.saveContractDirect(asignedContractDirect, contractsFeesList,
								billBandNumber, bayan);
						assigned = false;
						// متنازل عنه
					} else {// جديد
						contractDirect.setContractStatId(1);
						contractDirect.setStatus(1);
						cotractId = dataAccessService.saveContractDirect(contractDirect, contractsFeesList,
								billBandNumber, bayan);
					}
					contractsDirectList = dataAccessService.loadAllContractDirects(contractTypeId);
					contractDirect = new ContractDirect();
					MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
					Utils.closeDialog("addDialog");
				} // end if
				loadContractOperations();
			} // end else
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("add contractDirect: id: " + contractDirect.getId());
		}
		return "";

	}

	public void cancelContractDirect() {
		if (contractDirect.getContractStatId() != null
				&& contractDirect.getOperationId() == ContractOperationEnum.CANCEL.getAction()) {
			contractDirect.setContractStatId(ContractOperationEnum.CANCEL.getAction());
			dataAccessService.updateObject(contractDirect);
			loadContractOperations();
		}
	}

	public void compaerFeesAndBillDates() {
		List<ContractsFees> feesLst = new ArrayList<ContractsFees>();

		// dataAccessService.getContractPayedStatus(contract.getId());
		try {
			for (ContractDirect contract : contractsDirectList) {
				feesLst = dataAccessService.getContractPayedStatus(contract.getId());
				if (feesLst.size() > 0) {
					contract.setPayStatus(0);// غير مسددة
					contract.setSmsDueHDate(feesLst.get(feesLst.size() - 1).getDueHDate());
					contract.setSmsDueGDate(feesLst.get(feesLst.size() - 1).getDueGDate());
					// if (feesLst.get(feesLst.size() - 1).getFactId() != null)
					contract.setSmsBillNo(feesLst.get(feesLst.size() - 1).getFactId());
				} else {
					contract.setPayStatus(1);// مسددة
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveFeesUpdate() {
		if (billBandNumber == null || billBandNumber <= 0) {
			MsgEntry.addErrorMessage("يجب إدخال اسم البند");
		} else {

			contractDirect.setTotalBillValue(0);
			calculateDiscount();
			Integer unBillingFeesNum = 0;
			Integer oldFeesCount = 0;
			List<ContractsFees> contFeesList = new ArrayList<ContractsFees>();
			Integer discountStatus;
			Integer discountRemainder = discountDuration;// لتوزيع قيمة الخصم
															// على
															// الاقساط
			int count = 0;
			double oldDiscount = 0.00; // إجمالي الخصم في الفواتير المدفوعة
			for (ContractsFees contFees : contractsFeesList) {
				oldFeesCount = contFees.getOldFees();
				if (contFees.isCheckfees() == true) {
					if ((contFees.getFactId() == null)) {
						contFees.setStatus(2);
						contFees.setOldFees(oldFeesCount + 1);
						oldFeesCount++;
						discountStatus = calculateFeesDiscount(contFees);

						discountRemainder = feesDiscountDuration(contFees, discountStatus, discountRemainder, count);
						count++;
					}
					contractDirect.setTotalBillValue(contractDirect.getTotalBillValue() + contractDirect.getAnnualRent()
							- contFees.getDiscountAmount());
				} else if (contFees.getStatus() == 1) {
					unBillingFeesNum++;
				} else {
					oldDiscount = oldDiscount + contFees.getDiscountAmount();
				}
				contFeesList.add(contFees);
				// reminderDiscountDuration += discountRemainder;
			}
			contractsFeesList = contFeesList;
			if (reminderDiscountAmount > (unBillingFeesNum * contractDirect.getAnnualRent())) {
				MsgEntry.addErrorMessage("قيمة مبلغ الخصم الباقية أكبر من قيمة الأقساط المتبقية");

			} else {
				if (installmentNum < 0 || count > 0) {
					// Integer factId =
					// dataAccessService.insertInvestBill(contractDirect,
					// contractDirect.getTotalBillValue(),
					// billBandNumber);
					try {
						dataAccessService.updatecontractFeesList(contractsFeesList, contractDirect, billBandNumber,
								bayan);
						MsgEntry.addInfoMessage("تمت إصدار فاتورة ");
					} catch (Exception ex) {
						ex.printStackTrace();
						MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
						logger.error("add contractDirect: id: " + contractDirect.getId());
					}
				}
			}

			discountDuration = 0;
			reminderDiscountAmount = 0;
			reminderDiscountDuration = 0;
		}
	}

	// public void updateFessBill(){
	//
	// gf
	// }

	public Integer calculateTotalFeesDuration() {
		Integer total = 0;
		if (contractsFeesList.size() > 0 && contractDirect.getProcessPeriod() != null) {
			for (ContractsFees contFees : contractsFeesList) {
				if (contFees.getDiscountduration() != null)
					total += contFees.getDiscountduration();

			}
			reminderDiscountDuration = contractDirect.getProcessPeriod() - total;
		}
		return total;
	}

	public void updateFeesDueDates() {
		loadContractFeeslistbycontractId(contractDirect);
		String dueHDate = contractDirect.getStartDate();
		String oldFeesHDueDate = new String();
		int i = 1;
		try {
			for (ContractsFees fees : contractsFeesList) {
				oldFeesHDueDate = setDueFeesDates(fees, dueHDate, oldFeesHDueDate, i);
				// }
				i++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// dataAccessService.updateContractFessList(contractsFeesList);
	}

	public List<ContractsFees> createContractsFeesList() {
		ContractsFees fees = new ContractsFees();
		String dueHDate = contractDirect.getStartDate();
		// calculateDiscount();
		Integer discountStatus;
		Integer discountRemainder = discountDuration;
		int count = 0;
		String oldFeesHDueDate = new String();
		try {
			contractsFeesList = new ArrayList<ContractsFees>();
			for (int i = 1; i <= totalInstallmentCount; i++) {
				fees = new ContractsFees();
				fees.setOldFees(contractDirect.getOldPayedFees());
				// fees payed
				if (i <= contractDirect.getOldPayedFees()) {
					fees.setStatus(3);

				} else if ((i - contractDirect.getOldPayedFees()) <= (installmentNum)) {
					// fee factured
					fees.setStatus(2);

					// count++;
				} else {
					// fees not factured
					fees.setStatus(1);
				}
				if (fees.getStatus().equals(3) || fees.getStatus().equals(2)) {
					discountStatus = calculateFeesDiscount(fees);
					discountRemainder = feesDiscountDuration(fees, discountStatus, discountRemainder, count);
					// fees.setOldFees(fees.getOldFees() + 1);
					// contractDirect.setTotalBillValue(contractDirect.getTotalBillValue()
					// + contractDirect.getAnnualRent()
					// - fees.getDiscountAmount());
					count++;
				}

				contractsFeesList.add(fees);
				oldFeesHDueDate = setDueFeesDates(fees, dueHDate, oldFeesHDueDate, i);

			}
			calculateTotalFeesDuration();
			fees = new ContractsFees();
			return contractsFeesList;
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return contractsFeesList;
	}

	private String setDueFeesDates(ContractsFees fees, String dueHDate, String oldFeesHDueDate, int i)
			throws Exception {
		if (higriMode) {
			if (contractDirect.getContractType() == 1 || contractDirect.getContractType() == 3)
				fees.setDueHDate(calculateDueDates(i, dueHDate));
			if (contractDirect.getContractType() == 2) {
				if (i == 1) {
					fees.setDueHDate(dueHDate);
				} else {
					fees.setDueHDate(calculateDueDates(i, oldFeesHDueDate));
				}
				oldFeesHDueDate = fees.getDueHDate();
			}
		} else {
			fees.setDueHDate(calculateDueDates(i, dueHDate));
		}

		fees.setDueGDate(Utils.convertHDateToGDate(fees.getDueHDate()));
		return oldFeesHDueDate;
	}

	private Integer feesDiscountDuration(ContractsFees fees, Integer discountStatus, Integer discountRemainder,
			int count) {
		// if (discountStatus == 1) {
		// if (contractType == 1 && (count <= 1 || discountDuration > 360)) {
		// fees.setDiscountExceptionel(360);
		// discountRemainder = discountDuration - 360;
		//
		// } else if (contractType == 2 && (count <= 1 || discountDuration >
		// 30)) {
		//
		// }
		// } else
		// fees.setDiscountduration(discountRemainder);

		if (contractType == 1 && (count < 1 || discountDuration > 360)) {
			if (discountStatus == 1) {
				fees.setDiscountduration(360);
				discountRemainder = discountDuration - 360;
			}
			if (discountStatus == 0) {
				fees.setDiscountduration(discountRemainder);
				fees.setDiscountExceptionel(discountExceptionel);
				discountRemainder = 0;
			}
		} else if (contractType == 2 && (count < 1 || discountDuration > 30)) {
			if (discountStatus == 1) {
				fees.setDiscountduration(30);
				discountRemainder = discountDuration - 30;
			}
			if (discountStatus == 0) {
				fees.setDiscountduration(discountRemainder);
				fees.setDiscountExceptionel(discountExceptionel);
				discountRemainder = 0;
			}
		} else {
			fees.setDiscountduration(discountRemainder);
			fees.setDiscountExceptionel(discountExceptionel);
			discountRemainder = 0;
		}

		return discountRemainder;

	}

	private Integer calculateFeesDiscount(ContractsFees fees) {
		if (totalDiscount > contractDirect.getAnnualRent()) {
			// تقسيم الخصم على الأقساط المفوترة
			fees.setDiscountAmount(contractDirect.getAnnualRent());
			totalDiscount = totalDiscount - contractDirect.getAnnualRent();
			return 1;

		} else {
			fees.setDiscountAmount(totalDiscount);
			totalDiscount = 0;
			return 0;
		}

	}

	// private double calculateReminderDiscountAmount() {
	// return reminderDiscountAmount;
	// }

	public void checkedFeesIncremint(ContractsFees contFees) {
		if (contFees.isCheckfees()) {
			checkedFees++;
		} else {
			checkedFees--;
		}
		if (contFees.getStatus().equals(2)) {
			if (contFees.isCheckfees() && contFees.getStatus().equals(2)) {
				// checkedFees++;
				renewBill = true;
			} else {
				// checkedFees--;
				renewBill = false;
			}
		}
		if (contFees.getFactId() != null && contFees.getStatus() == 2) {
			Integer billNum = Integer.parseInt(contFees.getFactId());
			updateFeesRenewableState(billNum);

		}
	}

	private void updateFeesRenewableState(Integer billNum) {
		List<ContractsFees> fessList = new ArrayList<ContractsFees>();

		for (ContractsFees fees : contractsFeesList) {
			// if (fees.isCheckfees()) {
			if (fees.getFactId() != null) {
				if (Integer.parseInt(fees.getFactId()) == billNum) {
					fees.setCanRenew(true);
					// renewBill = true;
				} else {
					// if (fees.getFactId() == null)
					// fees.setCanRenew(true);
					// else {
					fees.setCanRenew(false);
				}
			} else {
				if (checkedFees < 0 && fees.getFactId() == null) {
					// fees.setCanRenew(false);
				}
			}
			fessList.add(fees);
		}
		contractsFeesList = fessList;

		Utils.updateUIComponent("includeform:feesTabel");
	}

	public void generateRenewBill() {
		if (billBandNumber == null || billBandNumber <= 0) {
			MsgEntry.addErrorMessage("يجب إدخال اسم البند");
		} else {
			try {
				Double billValue = 0.0;
				PayLicBills oldBill = new PayLicBills();
				for (ContractsFees fees : contractsFeesList) {
					if (fees.isCheckfees() && fees.isCanRenew() && fees.getFactId() != null) {
						if (oldBill.getBillNumber() == null) {
							oldBill = dataAccessService.getBillById(Integer.parseInt(fees.getFactId()));
							break;
						}
						// billValue = billValue + oldBill.getPayAmount();
					}
				}
				contractDirect.setTotalBillValue(oldBill.getPayAmount());
				// contractDirect.setTotalBillValue(billValue);

				dataAccessService.updatecontractFeesBills(contractsFeesList, contractDirect, billBandNumber, bayan);
				MsgEntry.addInfoMessage("تمت إعادة الإصدار");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public double calculateDiscount() {
		if ((reminderDiscountDuration > 0 || discountDuration > 0 || discountExceptionel >= 0)
				&& (contractDirect.getAnnualRent() != null && contractDirect.getAnnualRent() > 0)
				&& (installmentNum > 0 || !addMode)) {

			switch (contractType) {
			case 1:
				totalDiscount = ((contractDirect.getAnnualRent() / 360) * discountDuration) + discountExceptionel;
				reminderDiscountAmount = ((contractDirect.getAnnualRent() / 360)
						* (reminderDiscountDuration - discountDuration));
				break;
			case 2:
				totalDiscount = ((contractDirect.getAnnualRent() / 30) * discountDuration) + discountExceptionel;
				reminderDiscountAmount = ((contractDirect.getAnnualRent() / 30)
						* (reminderDiscountDuration - discountDuration));

				break;
			case 3:
				totalDiscount = ((contractDirect.getAnnualRent() / yearsNum) * discountDuration) + discountExceptionel;
				reminderDiscountAmount = ((contractDirect.getAnnualRent() / yearsNum)
						* (reminderDiscountDuration - discountDuration));
				break;
			}

		}
		if (totalDiscount != 0 && ((addMode
				&& totalDiscount > (installmentNum + contractDirect.getOldPayedFees()) * contractDirect.getAnnualRent())
				|| (!addMode && totalDiscount > (checkedFees + contractDirect.getOldPayedFees())
						* contractDirect.getAnnualRent()))) {
			discountDuration = 0;
			MsgEntry.addErrorMessage(
					"إجمالي الخصم اكبر من قيمة الاقساط المفوترة ... يرجى تخفيف مدة الخصم او زيادة عدد الاقساط المفوترة");
		}
		return 0;
	}

	public String getInvName(Integer invId) {
		Investor inv = (Investor) dataAccessService.findEntityById(Investor.class, invId);
		return inv.getName();
	}

	public String calculateDueDates(Integer feesNum, String dueHDate) {
		String hDate = new String();
		Integer day = new Integer(0);
		Integer month = new Integer(0);
		Integer year = new Integer(0);
		Date dueGDate = new Date();
		Date gDate = new Date();
		Calendar calendar;
		try {
			if (higriMode) {
				day = HijriCalendarUtil.findDayOfHijriDate(dueHDate);
				month = HijriCalendarUtil.findMonthOfHijriDate(dueHDate);
				year = HijriCalendarUtil.findYearOfHijriDate(dueHDate);
				if (feesNum != null && contractDirect.getContractType() != null
						&& !contractDirect.getContractType().equals(3)) {
					if (contractDirect.getContractType().equals(1)) {
						hDate = HijriCalendarUtil.addYearsToHijriDate(dueHDate, feesNum - 1);

					} else {
						// hDate =
						// HijriCalendarUtil.addMonthsToHijriDate(dueHDate,
						// feesNum - 1);
						hDate = HijriCalendarUtil.addDaysToHijriDate(dueHDate,
								HijriCalendarUtil.findMonthLength(year, month));

					}
				} else {
					hDate = dueHDate;
				}
			} else {
				dueGDate = Utils.convertHDateToGDate(dueHDate);
				calendar = Utils.getCalendar(dueGDate);
				if (feesNum != null && contractDirect.getContractType() != null
						&& !contractDirect.getContractType().equals(3)) {
					if (contractDirect.getContractType().equals(1)) {
						calendar.add(Calendar.YEAR, feesNum - 1);
					} else {
						calendar.add(Calendar.MONTH, feesNum - 1);
					}
					gDate = calendar.getTime();
					hDate = Utils.grigDatesConvert(gDate);
				} else {
					hDate = dueHDate;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// try {
		// dueGDate = Utils.convertHDateToGDate(dueHDate);
		// calendar = Utils.getCalendar(dueGDate);
		// if (feesNum != null && contractDirect.getContractType() != null
		// && !contractDirect.getContractType().equals(3)) {
		// if (contractDirect.getContractType().equals(1)) {
		// calendar.add(Calendar.YEAR, feesNum - 1);
		// } else {
		// calendar.add(Calendar.MONTH, feesNum - 1);
		// }
		// gDate = calendar.getTime();
		// hDate = Utils.grigDatesConvert(gDate);
		// } else {
		// hDate = dueHDate;
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

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
		if (contractMaincatgId != null && contractMaincatgId > 0) {
			contractDirect.setContractMaincatgId(contractMaincatgId);
		}
		contractSubcategoryList = dataAccessService
				.loadSuCategoryByMainCategoryId(contractDirect.getContractMaincatgId());
		if (contractDirect.getStartDate() != null)
			oldStartdate = contractDirect.getStartDate();
		if (contractDirect.getEndDate() != null)
			oldenddate = contractDirect.getEndDate();

	}

	public void loadSuCategoryBfilter() {
		if (contractMaincatgId != null && contractMaincatgId > 0) {
			contractSubcategoryList = dataAccessService.loadSuCategoryByMainCategoryId(contractMaincatgId);

		}
		loadContractDirectListByAllFilters();
	}

	public void loadSuCategoryByMainCategoryId(ContractDirect selectesContract) {
		addMode = false;
		// loadContractFeeslistbycontractId(selectesContract);
		contractDirect = selectesContract;
		if (contractDirect.getContractYears() != null) {
			totalInstallmentCount = contractDirect.getContractYears();
		}
		contractSubcategoryList = dataAccessService
				.loadSuCategoryByMainCategoryId(selectesContract.getContractMaincatgId());
		contractsFeesList = dataAccessService.getContractFessListByContractId(selectesContract.getId());
		// calculateFeesDiscount(selectesContract);
		calculateTotalFeesDuration();
		calculateInstallments();
		if (contractDirect.getStartDate() != null)
			oldStartdate = contractDirect.getStartDate();
		if (contractDirect.getEndDate() != null)
			oldenddate = contractDirect.getEndDate();
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

	public Integer findStringDatesDifference(String firstdate, String secondDate) {

		int dateDiff = 0;
		try {
			Date date_1 = Utils.convertGregStringToDate(firstdate);
			Date date_2 = Utils.convertGregStringToDate(secondDate);
			dateDiff = date_1.compareTo(date_2);
			// 0 means that dates are equals
			// else means that dates are not equals
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateDiff;
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

	public void sortNewDates() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String hiri1 = params.get("includeform:hiri1");
		String gric1 = params.get("includeform:gric1");
		String hiri12 = params.get("includeform:hiri12");
		String gric12 = params.get("includeform:gric12");
		if (contractDirect.getSelecteStartDate_G() != null && gric1 == null)
			gric1 = Utils.convertDateToString(contractDirect.getSelecteStartDate_G());
		if (contractDirect.getSelecteEndDate_G() != null && gric12 == null)
			gric12 = Utils.convertDateToString(contractDirect.getSelecteEndDate_G());

		try {
			if (!higriMode && gric1 != null && gric12 != null) {

				// contractDirect.setStartDate(Utils.grigDatesConvert(contractDirect.getSelecteStartDate_G()));
				// contractDirect.setEndDate(Utils.grigDatesConvert(contractDirect.getSelecteEndDate_G()));
				contractDirect.setStartDate(Utils.grigDatesConvert(Utils.convertGregStringToDate(gric1)));
				contractDirect.setEndDate(Utils.grigDatesConvert(Utils.convertGregStringToDate(gric12)));
				contractDirect.setSelecteStartDate_G(Utils.convertGregStringToDate(gric1));
				contractDirect.setSelecteEndDate_G(Utils.convertGregStringToDate(gric12));
			} else {
				if (hiri1 != null && hiri12 != null) {
					contractDirect.setStartDate(hiri1);
					contractDirect.setEndDate(hiri12);
					contractDirect.setSelecteStartDate_G(Utils.convertHDateToGDate(hiri1));
					contractDirect.setSelecteEndDate_G(Utils.convertHDateToGDate(hiri12));

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			// Map<String, String> params =
			// FacesContext.getCurrentInstance().getExternalContext()
			// .getRequestParameterMap();
			// String year_Num = params.get("includeform:yearsnumber");
			// insertDate();
			// sortDates();
			sortNewDates();
			// contractDirect.setContractYears((yearsNum));
			contractDirect.setContractStatId(contractStatus);
			// contractDirect.setStatus(3);
			contractDirect.setContractType(contractType);
			contractDirect.setContractYears(yearsNum);

			// contractDirect.setContractType(contractType);

			int statDiff = findStringDatesDifference(contractDirect.getStartDate(), oldStartdate);
			int endDiff = findStringDatesDifference(contractDirect.getEndDate(), oldenddate);
			if (statDiff != 0 || endDiff != 0) {
				updateFeesDueDates();
				dataAccessService.updateContractDirect(contractDirect, contractsFeesList);
			} else {
				dataAccessService.updateObject(contractDirect);
			}
			// contractDirect.getActionsList().remove(ContractOperationEnum.CANCEL);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("update contractDirect: id: " + contractDirect.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("update contractDirect: id: " + contractDirect.getId());
		}
	}

	public void savecontractFess() {
		HashMap<String, ContractsFees> contrOldFees = new HashMap<String, ContractsFees>();
		List<ContractsFees> oldcontractsFeesList = new ArrayList<ContractsFees>();
		if (contractsFeesList != null) {

			for (ContractsFees contractsFees : contractsFeesList) {
				oldcontractsFeesList.add(contractsFees);
				if (contractsFees.getStatus().equals(3) || contractsFees.getStatus().equals(2))
					contrOldFees.put(contractsFees.getDueHDate(), contractsFees);
			}
			contractsFeesList.clear();
		}
		createContractsFeesList();

		for (ContractsFees contractsFees : contractsFeesList) {
			// test if fee is payed
			ContractsFees oldFee = contrOldFees.get(contractsFees.getDueHDate());
			if (oldFee != null) {
				if (oldFee.getStatus().equals(2)) {
					if (!contractsFees.getStatus().equals(3))
						contractsFees.setStatus(oldFee.getStatus());
					contractsFees.setFactId(oldFee.getFactId());
					contractsFees.setDiscountAmount(oldFee.getDiscountAmount());
					contractsFees.setDiscountduration(oldFee.getDiscountduration());
					contractsFees.setDiscountExceptionel(oldFee.getDiscountExceptionel());
					contractsFees.setOldFees(oldFee.getOldFees());

				}

			}
		}
		dataAccessService.saveContractDirectFess(contractDirect, contractsFeesList, billBandNumber, bayan,
				oldcontractsFeesList);
	}

	public void updateFeesObject(ContractsFees feeaItem) {
		dataAccessService.updateContrFee(feeaItem);

	}

	public void updateContractDirectNum() {
		newcontractNum = dataAccessService.findMaxContractDirectNum() + 1;
		loadIntroduction();
		// unusedRealEstatesList =
		// dataAccessService.loadAllUnusedRealEstatesList();

	}

	public void deleteContract() {
		try {
			// dataAccessService.deleteObject(contractDirect);
			if (contractDirect != null && contractDirect.getOperationId() == ContractOperationEnum.DELETE.getAction()) {
				dataAccessService.deleteContractDirect(contractDirect);
				contractsDirectList.remove(contractDirect);
				loadContracts();
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			MsgEntry.addErrorMessage("تأكد ان العقد لم تصدر له فواتير ");
			logger.error("delete contractDirect: id: " + contractDirect.getId());
		}
	}

	public void sendSmsMessage() {
		try {

			ContractMessage contMSGObj = new ContractMessage();
			contMSGObj.setContractId(contractDirect.getId());
			contMSGObj.setSendDateG(new Date());
			contMSGObj.setSendDateH(HijriCalendarUtil.findCurrentHijriDate());
			contMSGObj.setPhoneNo(contractDirect.getInvestor().getMobile());
			contMSGObj.setBillNo(Long.parseLong(contractDirect.getSmsBillNo()));
			contMSGObj.setTreadeRecord(contractDirect.getInvestor().getTradeRecord());
			contMSGObj.setMessageTxt(messageTxt);
			smsService = new SmsService();
			// messageTxt = contractDirect.getSmsBillNo();

			String message = URLEncoder.encode(messageTxt, "UTF-8");
			ResponseTypeEnum RESPONSE = smsService.sendMessage(contractDirect.getInvestor().getMobile(), message);
			// ResponseTypeEnum RESPONSE =
			// smsService.sendMessage(contractDirect.getInvestor().getMobile(),messageTxt);

			// FacesContext.getCurrentInstance().getExternalContext().redirect("bill.xhtml");
			// resetFields();
			System.out.println(messageTxt);

			if (RESPONSE.getError() != 1) {
				MsgEntry.addErrorMessage("لم يتم الأرسال ... ويرجى مراجعة مدير التقنية والتأكد من رصيد باقة الرسائل");
			} else {

				dataAccessService.save(contMSGObj);
				MsgEntry.addInfoMessage("تم الإرسال");
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("لم يتم الإرسال");
		}
	}

	public void openSMSDialog() {
		Utils.openDialog("sendNewSMS_dlg");
	}

	public void openEmailDialog() {
		Utils.openDialog("sendEmail_dlg");
	}

	public void sendNewSmsMessage() {
		try {
			smsService = new SmsService();
			ResponseTypeEnum RESPONSE = smsService.sendMessage(phoneNo, URLEncoder.encode(messageTxt, "UTF-8"));
			System.out.println(messageTxt);
			if (RESPONSE.getError() != 1) {
				MsgEntry.addErrorMessage("لم يتم الأرسال ... ويرجى مراجعة مدير التقنيةوالتأكد من رصيد باقة الرسائل");
			} else {
				MsgEntry.addInfoMessage("تم الإرسال");
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("لم يتم الإرسال");
		}
	}

	public void sendEmailMessage() {
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName(HOST_NAME);
			email.setSmtpPort(PORT);
			email.setSSLOnConnect(true);

			email.setSubject("Some subject");
			email.setFrom(emailFrom, "بلدية محافظة المذنب", String.valueOf(StandardCharsets.UTF_8));
			email.addTo(emailTo);
			email.setHtmlMsg(emailMessage);
			email.send();
			MsgEntry.addInfoMessage("تم الإرسال");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // public String loadContractMessagerList() {
		// contractMsgList = dataAccessService.getAllContractMessagesList();
		// FacesContext context = FacesContext.getCurrentInstance();
		// context.getExternalContext().getSessionMap().put("contractMsgList",
		// contractMsgList);
		// return "contract_message";
		// }
		//
		// public String contractMessagerInit() {
		// contractMsgList = dataAccessService.getAllContractMessagesList();
		// FacesContext context = FacesContext.getCurrentInstance();
		// contractMsgList = new ArrayList<>();
		// contractMsgList = (List<ContractMessage>)
		// context.getExternalContext().getSessionMap().get("contractMsgList");
		// return "";
		// }

	public String printAllContractsAction() {
		compaerFeesAndBillDates();
		List<ContractDirect> contractsList = new ArrayList<ContractDirect>();
		List<ContractDirect> printedContractsList = new ArrayList<ContractDirect>();
		if (filteredContractsDirectList != null) {
			if (filteredContractsDirectList.size() > 0)
				printedContractsList = filteredContractsDirectList;
		} else {
			printedContractsList = contractsDirectList;
		}
		ContractDirect contract = new ContractDirect();
		String reportName = "/reports/contract_direct_repolrt.jrxml";

		int num = 1;
		for (ContractDirect contObj : printedContractsList) {
			contract = new ContractDirect();
			contract.setNum(num);
			contract.setInvestor(contObj.getInvestor());
			contract.setContractNum(contObj.getContractNum());
			contract.setStartDate(contObj.getStartDate());
			contract.setEndDate(contObj.getEndDate());
			Investor inv = (Investor) dataAccessService.findEntityById(Investor.class, contObj.getInvestorId());
			contract.setInvestorName(inv.getName());
			contract.setStatus(contObj.getPayStatus());
			contract.setPayStatus(contObj.getPayStatus());
			contract.setInvPhoneNo(inv.getMobile());

			contractsList.add(contract);

			num++;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();

		Map<Integer, String> contStatusMap = new HashMap<Integer, String>();

		for (ContractStatus contStatus : contractStatusList) {
			contStatusMap.put(contStatus.getId(), contStatus.getName());
		}
		parameters.put("statusMap", contStatusMap);

		Utils.printPdfReportFromListDataSource(reportName, parameters, contractsList);
		// Utils.printPdfReport(reportName, parameters);

		return "";
	}

	public String printContractReport() {
		String reportName = "/reports/standard_contract.jrxml";
		String contStringDay = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (contractDirect.getStartDate() != null) {
			contStringDay = Utils.getDayForHigriDate(contractDirect.getStartDate());
		}
		// reportName = getReportName();
		parameters.put("contractId", contractDirect.getId());
		parameters.put("stringStartDay", contStringDay);
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	/**
	 * @param document
	 */
	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		// sheet.removeColumnBreak(8);
		sheet.setColumnHidden(5, true);
		sheet.setColumnHidden(7, true);

		sheet.setColumnHidden(8, true);
		sheet.setColumnHidden(9, true);
		sheet.setColumnHidden(10, true);
		sheet.setColumnHidden(11, true);
		sheet.setColumnHidden(12, true);
		sheet.setColumnHidden(13, true);
		//
		// Row row = sheet.createRow(rownum);
		// row.createCell(0).setCellValue(num);

		//
		// HSSFCellStyle cellStyle = wb.createCellStyle();
		// cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
		// cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		//
		// for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
		// HSSFCell cell = header.getCell(i);
		//
		// cell.setCellStyle(cellStyle);
		// }
	}

	/**
	 * export xls file from list
	 */
	public void exportXLS() {
		// Create a Workbook
		Workbook workbook = new HSSFWorkbook(); // new HSSFWorkbook() for
												// generating `.xls` file

		/*
		 * CreationHelper helps us create instances of various things like
		 * DataFormat, Hyperlink, RichTextString etc, in a format (HSSF, XSSF)
		 * independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("العقود");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		String[] columns = { "الرقم", "رقم العقد ", "تاريخ البداية", "تاريخ النهاية", "المستثمر", "حالة الفاتورة" };
		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create Other rows and cells with employees data
		int rowNum = 1;
		int num = 1;
		for (ContractDirect contObj : contractsDirectList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(num);
			row.createCell(1).setCellValue(contObj.getContractNum());

			if (!tableHigriMode) {
				Cell date1 = row.createCell(2);
				date1.setCellValue(contObj.getStartContDate());
				date1.setCellStyle(dateCellStyle);
				Cell date2 = row.createCell(3);
				date2.setCellValue(contObj.getEndContDate());
				date2.setCellStyle(dateCellStyle);
			} else {
				Cell date1 = row.createCell(2);
				date1.setCellValue(contObj.getStartDate());
				date1.setCellStyle(dateCellStyle);
				Cell date2 = row.createCell(3);
				date2.setCellValue(contObj.getEndDate());
				date2.setCellStyle(dateCellStyle);
			}

			Investor inv = (Investor) dataAccessService.findEntityById(Investor.class, contObj.getInvestorId());
			row.createCell(4).setCellValue(inv.getName());
			row.createCell(5).setCellValue(contObj.getPayStatusName());
			num++;
		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Write the output to a file

		try {
			String path = "D:/العقود.xls";
			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Closing the workbook

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
			// RequestContext.getCurrentInstance().execute("PF('cancelDialog').show();");
			RequestContext.getCurrentInstance().execute("PF('cancel_dlg').show();");
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

	public void loadContractbills(ContractDirect selectedItem) {
		contractDirect = selectedItem;
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
			// installmentNum = 1;
			installmentNum = 0;
		}
		if (installmentNum > 0) {
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
			///////////////// ContractInstallments
			///////////////// الاقساط//////////////////////////
			ContractInstallments conIns = new ContractInstallments();
			conIns.setContractId(cotractId);
			conIns.setContractNum(contractDirect.getContractNum());
			conIns.setContractAmount(contractDirect.getAnnualRent());
			for (int i = 0; i < installmentNum; i++) {
				conIns.setStatus(1);
				dataAccessService.save(conIns);
			}
			try {
				if (yearsNum > installmentNum)
					for (int i = 0; i < yearsNum - installmentNum; i++) {
						conIns.setStatus(0);
						dataAccessService.save(conIns);
					}
			} catch (Exception ex) {
				ex.printStackTrace();
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
				logger.error("update contract direct: id: " + contractDirect.getId());
			}
		}
	}

	public String selestedFeesValue() {
		for (ContractsFees selectedFees : selectedcontractsFeesList)
			System.out.println("" + selectedFees.getId());
		System.out.println("=====================");
		allowDelete = true;
		return "";
	}

	public void deleteFeesList() {
		// for (ContractsFees selectedFees : selectedcontractsFeesList)
		dataAccessService.deleteFeesList(selectedcontractsFeesList);
		contractsFeesList = dataAccessService.loadContractFeeslistbycontractId(contractDirect.getId());
		MsgEntry.addErrorMessage("تم حذف الأقساط المختارة");

	}

	public void recreateFeesList() {

		Map<String, ContractsFees> feesMap = new HashMap<String, ContractsFees>();

		for (ContractsFees selectedFees : selectedcontractsFeesList)
			System.out.println("" + selectedFees.getId());
		System.out.println("=====================");
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
			if (intro != null) {
				setContractIntro(intro.getDescription());
			}
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

	public String printBillReport(Integer billNumber) {
		// public String printBillReport(Integer contractId) {
		try {
			String reportName = "/reports/bill.jasper";
			// PayLicBills payLicBill =
			// dataAccessService.loadBillByLicNo(contractId);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("p1", billNumber);
			// parameters.put("p1", payLicBill.getBillNumber());
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
		if (contractDirect.getContractYears() != null)
			yearsNum = contractDirect.getContractYears();
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
		if (contractDirect.getContractType() != null)
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

	// public Integer getOldInstallment() {
	// return oldInstallment;
	// }
	//
	// public void setOldInstallment(Integer oldInstallment) {
	// this.oldInstallment = oldInstallment;
	// }

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
		// calculateDiscount();
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

	public Integer getContractStatus() {
		if (contractDirect.getContractStatId() != null)
			contractStatus = contractDirect.getContractStatId();
		return contractStatus;

	}

	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}

	public List<ContractsFees> getFeesList() {
		return feesList;
	}

	public void setFeesList(List<ContractsFees> feesList) {
		this.feesList = feesList;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public List<WrkSection> getSectionsList() {
		return sectionsList;
	}

	public void setSectionsList(List<WrkSection> sectionsList) {
		this.sectionsList = sectionsList;
	}

	public Integer getContractMaincatgId() {
		return contractMaincatgId;
	}

	public void setContractMaincatgId(Integer contractMaincatgId) {
		this.contractMaincatgId = contractMaincatgId;
	}

	public Integer getContractSubcatgId() {
		return contractSubcatgId;
	}

	public void setContractSubcatgId(Integer contractSubcatgId) {
		this.contractSubcatgId = contractSubcatgId;
	}

	public Integer getActivityIdFilter() {
		return activityIdFilter;
	}

	public void setActivityIdFilter(Integer activityIdFilter) {
		this.activityIdFilter = activityIdFilter;
	}

	public String getComponentsFilter() {
		return componentsFilter;
	}

	public void setComponentsFilter(String componentsFilter) {
		this.componentsFilter = componentsFilter;
	}

	public List<ContractComponents> getAllContractComponentList() {
		return allContractComponentList;
	}

	public void setAllContractComponentList(List<ContractComponents> allContractComponentList) {
		this.allContractComponentList = allContractComponentList;
	}

	public List<ContractType> getContractTypesList() {
		return contractTypesList;
	}

	public void setContractTypesList(List<ContractType> contractTypesList) {
		this.contractTypesList = contractTypesList;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getContractStatusFilter() {
		return contractStatusFilter;
	}

	public void setContractStatusFilter(Integer contractStatusFilter) {
		this.contractStatusFilter = contractStatusFilter;
	}

	public Integer getReminderDiscountDuration() {
		// calculateTotalFeesDuration();
		return reminderDiscountDuration;
	}

	public void setReminderDiscountDuration(Integer reminderDiscountDuration) {
		// calculateTotalFeesDuration();
		this.reminderDiscountDuration = reminderDiscountDuration;
	}

	public Integer getOldDiscountDuration() {
		return oldDiscountDuration;
	}

	public void setOldDiscountDuration(Integer oldDiscountDuration) {
		this.oldDiscountDuration = oldDiscountDuration;
	}

	public boolean isRenewBill() {
		return renewBill;
	}

	public void setRenewBill(boolean renewBill) {
		this.renewBill = renewBill;
	}

	public List<RealEstate> getUnusedRealEstatesList() {
		unusedRealEstatesList = dataAccessService.loadAllUnusedRealEstatesList();
		return unusedRealEstatesList;
	}

	public void setUnusedRealEstatesList(List<RealEstate> unusedRealEstatesList) {
		this.unusedRealEstatesList = unusedRealEstatesList;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	public String getBayan() {
		return bayan;
	}

	public void setBayan(String bayan) {
		this.bayan = bayan;
	}

	public List<ContractsFees> getSelectedcontractsFeesList() {
		return selectedcontractsFeesList;
	}

	public void setSelectedcontractsFeesList(List<ContractsFees> selectedcontractsFeesList) {
		this.selectedcontractsFeesList = selectedcontractsFeesList;
	}

	public boolean isAllowDelete() {
		return allowDelete;
	}

	public void setAllowDelete(boolean allowDelete) {
		this.allowDelete = allowDelete;
	}

	public String getMessageTxt() {
		return messageTxt;
	}

	public void setMessageTxt(String messageTxt) {
		this.messageTxt = messageTxt;
	}

	public List<ContractMessage> getContractMsgList() {
		return contractMsgList;
	}

	public void setContractMsgList(List<ContractMessage> contractMsgList) {
		this.contractMsgList = contractMsgList;
	}

	public List<ContractMessage> getFilterContractMsgList() {
		return filterContractMsgList;
	}

	public void setFilterContractMsgList(List<ContractMessage> filterContractMsgList) {
		this.filterContractMsgList = filterContractMsgList;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	public String getTradRecod() {
		return tradRecod;
	}

	public void setTradRecod(String tradRecod) {
		this.tradRecod = tradRecod;
	}

}