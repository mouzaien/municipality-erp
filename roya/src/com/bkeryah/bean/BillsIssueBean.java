package com.bkeryah.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.bkeryah.entities.PayBillDetails;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.TradeLicense;
import com.bkeryah.service.IDataAccessService;

import utilities.CalcFeesUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class BillsIssueBean {

	int tradeLicenseId;
	String newBillNmber = "";
	/**************** BUILDING ******************/
	public Integer buildingIssueType;
	public Integer buildingLicenseType;
	public Integer buildingType;
	public Double buildingArea;
	public Double buildingWallLength;
	public List<PayBillDetails> buildingResult;
	/**************** TRADE MARKET ******************/
	public Integer tradeMarketIssueType;
	public Integer tradeMarketMainActivity;
	public Double tradeMarketArea;
	public Double tradeMarketAdvArea;
	public Double tradeMarketAddAdvArea;
	public Integer tradeMarketIssueYears;
	public List<PayBillDetails> tradeMarketResult;
	/******************** PETROLSTATION **********************/
	public Integer petrolStationIssueType;
	public Double petrolStationArea;
	public Double petrolStationAdvArea;
	public Double petrolStationAddAdvArea;
	public Integer petrolStationIssueYears;
	public List<PayBillDetails> petrolStationResult;
	/******************** ATM **********************/
	public Integer ATMIssueType;
	public Double ATMAdvArea;
	public Double ATMAddAdvArea;
	public Integer ATMIssueYears;
	public List<PayBillDetails> ATMResult;
	/******************* TELECOMETOWER ***********************/
	public Integer telecomTowerIssueType;
	public Integer telecomTowerIssueYears;
	public List<PayBillDetails> telecomTowerResult;
	/******************* BuildingShelter ***********************/
	public Integer buildingShelterIssueType;
	public Integer buildingShelterClass;
	public Double buildingShelterAdvArea;
	public Double buildingShelterAddAdvArea;
	public Integer buildingShelterIssueYears;
	public Integer buildingShelterUnitCount;
	public List<PayBillDetails> buildingShelterResult;
	/******************** MobileCarts **********************/
	public Integer mobileCartsIssueType;
	public Double mobileCartsAddAdvArea;
	public Integer mobileCartsIssueYears;
	public List<PayBillDetails> mobileCartsResult;
	/*************************************************/
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	List<PayBillDetails> selectedDetailList = new ArrayList<>();
	PayLicBills newBill = new PayLicBills();
	/*******************************************************/
	List<PayBillDetails> selectedHealthCertificateList = new ArrayList<>();
	public Integer healthCertificateIssueType;

	@PostConstruct
	public void init() {

	}

	public String getNewBillNmber() {
		return newBillNmber;
	}

	public void setNewBillNmber(String newBillNmber) {
		this.newBillNmber = newBillNmber;
	}

	public Integer getBuildingIssueType() {
		return buildingIssueType;
	}

	public void setBuildingIssueType(Integer buildingIssueType) {
		this.buildingIssueType = buildingIssueType;
	}

	public Integer getBuildingLicenseType() {
		return buildingLicenseType;
	}

	public void setBuildingLicenseType(Integer buildingLicenseType) {
		this.buildingLicenseType = buildingLicenseType;
	}

	public Integer getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(Integer buildingType) {
		this.buildingType = buildingType;
	}

	public Double getBuildingArea() {
		return buildingArea;
	}

	public void setBuildingArea(Double buildingArea) {
		this.buildingArea = buildingArea;
	}

	public Double getBuildingWallLength() {
		return buildingWallLength;
	}

	public void setBuildingWallLength(Double buildingWallLength) {
		this.buildingWallLength = buildingWallLength;
	}

	public List<PayBillDetails> getBuildingResult() {
		return buildingResult;
	}

	public void setBuildingResult(List<PayBillDetails> buildingResult) {
		this.buildingResult = buildingResult;
	}

	public Integer getTradeMarketIssueType() {
		return tradeMarketIssueType;
	}

	public void setTradeMarketIssueType(Integer tradeMarketIssueType) {
		this.tradeMarketIssueType = tradeMarketIssueType;
	}

	public Integer getTradeMarketMainActivity() {
		return tradeMarketMainActivity;
	}

	public void setTradeMarketMainActivity(Integer tradeMarketMainActivity) {
		this.tradeMarketMainActivity = tradeMarketMainActivity;
	}

	public Double getTradeMarketArea() {
		return tradeMarketArea;
	}

	public void setTradeMarketArea(Double tradeMarketArea) {
		this.tradeMarketArea = tradeMarketArea;
	}

	public Double getTradeMarketAdvArea() {
		return tradeMarketAdvArea;
	}

	public void setTradeMarketAdvArea(Double tradeMarketAdvArea) {
		this.tradeMarketAdvArea = tradeMarketAdvArea;
	}

	public Double getTradeMarketAddAdvArea() {
		return tradeMarketAddAdvArea;
	}

	public void setTradeMarketAddAdvArea(Double tradeMarketAddAdvArea) {
		this.tradeMarketAddAdvArea = tradeMarketAddAdvArea;
	}

	public Integer getTradeMarketIssueYears() {
		return tradeMarketIssueYears;
	}

	public void setTradeMarketIssueYears(Integer tradeMarketIssueYears) {
		this.tradeMarketIssueYears = tradeMarketIssueYears;
	}

	public List<PayBillDetails> getTradeMarketResult() {
		return tradeMarketResult;
	}

	public void setTradeMarketResult(List<PayBillDetails> tradeMarketResult) {
		this.tradeMarketResult = tradeMarketResult;
	}

	public Integer getPetrolStationIssueType() {
		return petrolStationIssueType;
	}

	public void setPetrolStationIssueType(Integer petrolStationIssueType) {
		this.petrolStationIssueType = petrolStationIssueType;
	}

	public Double getPetrolStationArea() {
		return petrolStationArea;
	}

	public void setPetrolStationArea(Double petrolStationArea) {
		this.petrolStationArea = petrolStationArea;
	}

	public Double getPetrolStationAdvArea() {
		return petrolStationAdvArea;
	}

	public void setPetrolStationAdvArea(Double petrolStationAdvArea) {
		this.petrolStationAdvArea = petrolStationAdvArea;
	}

	public Double getPetrolStationAddAdvArea() {
		return petrolStationAddAdvArea;
	}

	public void setPetrolStationAddAdvArea(Double petrolStationAddAdvArea) {
		this.petrolStationAddAdvArea = petrolStationAddAdvArea;
	}

	public Integer getPetrolStationIssueYears() {
		return petrolStationIssueYears;
	}

	public void setPetrolStationIssueYears(Integer petrolStationIssueYears) {
		this.petrolStationIssueYears = petrolStationIssueYears;
	}

	public List<PayBillDetails> getPetrolStationResult() {
		return petrolStationResult;
	}

	public void setPetrolStationResult(List<PayBillDetails> petrolStationResult) {
		this.petrolStationResult = petrolStationResult;
	}

	public Integer getATMIssueType() {
		return ATMIssueType;
	}

	public void setATMIssueType(Integer aTMIssueType) {
		ATMIssueType = aTMIssueType;
	}

	public Double getATMAdvArea() {
		return ATMAdvArea;
	}

	public void setATMAdvArea(Double aTMAdvArea) {
		ATMAdvArea = aTMAdvArea;
	}

	public Double getATMAddAdvArea() {
		return ATMAddAdvArea;
	}

	public void setATMAddAdvArea(Double aTMAddAdvArea) {
		ATMAddAdvArea = aTMAddAdvArea;
	}

	public Integer getATMIssueYears() {
		return ATMIssueYears;
	}

	public void setATMIssueYears(Integer aTMIssueYears) {
		ATMIssueYears = aTMIssueYears;
	}

	public List<PayBillDetails> getATMResult() {
		return ATMResult;
	}

	public void setATMResult(List<PayBillDetails> aTMResult) {
		ATMResult = aTMResult;
	}

	public Integer getTelecomTowerIssueType() {
		return telecomTowerIssueType;
	}

	public void setTelecomTowerIssueType(Integer telecomTowerIssueType) {
		this.telecomTowerIssueType = telecomTowerIssueType;
	}

	public Integer getTelecomTowerIssueYears() {
		return telecomTowerIssueYears;
	}

	public void setTelecomTowerIssueYears(Integer telecomTowerIssueYears) {
		this.telecomTowerIssueYears = telecomTowerIssueYears;
	}

	public List<PayBillDetails> getTelecomTowerResult() {
		return telecomTowerResult;
	}

	public void setTelecomTowerResult(List<PayBillDetails> telecomTowerResult) {
		this.telecomTowerResult = telecomTowerResult;
	}

	public Integer getBuildingShelterIssueType() {
		return buildingShelterIssueType;
	}

	public void setBuildingShelterIssueType(Integer buildingShelterIssueType) {
		this.buildingShelterIssueType = buildingShelterIssueType;
	}

	public Integer getBuildingShelterClass() {
		return buildingShelterClass;
	}

	public void setBuildingShelterClass(Integer buildingShelterClass) {
		this.buildingShelterClass = buildingShelterClass;
	}

	public Double getBuildingShelterAdvArea() {
		return buildingShelterAdvArea;
	}

	public void setBuildingShelterAdvArea(Double buildingShelterAdvArea) {
		this.buildingShelterAdvArea = buildingShelterAdvArea;
	}

	public Double getBuildingShelterAddAdvArea() {
		return buildingShelterAddAdvArea;
	}

	public void setBuildingShelterAddAdvArea(Double buildingShelterAddAdvArea) {
		this.buildingShelterAddAdvArea = buildingShelterAddAdvArea;
	}

	public Integer getBuildingShelterIssueYears() {
		return buildingShelterIssueYears;
	}

	public void setBuildingShelterIssueYears(Integer buildingShelterIssueYears) {
		this.buildingShelterIssueYears = buildingShelterIssueYears;
	}

	public Integer getBuildingShelterUnitCount() {
		return buildingShelterUnitCount;
	}

	public void setBuildingShelterUnitCount(Integer buildingShelterUnitCount) {
		this.buildingShelterUnitCount = buildingShelterUnitCount;
	}

	public List<PayBillDetails> getBuildingShelterResult() {
		return buildingShelterResult;
	}

	public void setBuildingShelterResult(List<PayBillDetails> buildingShelterResult) {
		this.buildingShelterResult = buildingShelterResult;
	}

	public Integer getMobileCartsIssueType() {
		return mobileCartsIssueType;
	}

	public void setMobileCartsIssueType(Integer mobileCartsIssueType) {
		this.mobileCartsIssueType = mobileCartsIssueType;
	}

	public Double getMobileCartsAddAdvArea() {
		return mobileCartsAddAdvArea;
	}

	public void setMobileCartsAddAdvArea(Double mobileCartsAddAdvArea) {
		this.mobileCartsAddAdvArea = mobileCartsAddAdvArea;
	}

	public Integer getMobileCartsIssueYears() {
		return mobileCartsIssueYears;
	}

	public void setMobileCartsIssueYears(Integer mobileCartsIssueYears) {
		this.mobileCartsIssueYears = mobileCartsIssueYears;
	}

	public List<PayBillDetails> getMobileCartsResult() {
		return mobileCartsResult;
	}

	public void setMobileCartsResult(List<PayBillDetails> mobileCartsResult) {
		this.mobileCartsResult = mobileCartsResult;
	}

	public List<PayBillDetails> getSelectedDetailList() {
		return selectedDetailList;
	}

	public void setSelectedDetailList(List<PayBillDetails> selectedDetailList) {
		this.selectedDetailList = selectedDetailList;
	}

	public PayLicBills getNewBill() {
		return newBill;
	}

	public void setNewBill(PayLicBills newBill) {
		this.newBill = newBill;
	}

	public List<PayBillDetails> getSelectedHealthCertificateList() {
		return selectedHealthCertificateList;
	}

	public void setSelectedHealthCertificateList(List<PayBillDetails> selectedHealthCertificateList) {
		this.selectedHealthCertificateList = selectedHealthCertificateList;
	}

	public Integer getHealthCertificateIssueType() {
		return healthCertificateIssueType;
	}

	public void setHealthCertificateIssueType(Integer healthCertificateIssueType) {
		this.healthCertificateIssueType = healthCertificateIssueType;
	}

	public void calculateBuildingFees(ActionEvent ae) {

		buildingResult = CalcFeesUtil.calculateBuildingFees(buildingLicenseType, buildingIssueType, buildingType,
				buildingArea, buildingWallLength);

		buildingLicenseType = null;
		buildingIssueType = null;
		buildingType = null;
		buildingArea = null;
		buildingWallLength = null;

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	
	
	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	
	
	public int getTradeLicenseId() {
		return tradeLicenseId;
	}

	public void setTradeLicenseId(int tradeLicenseId) {
		this.tradeLicenseId = tradeLicenseId;
	}

	public void calculateTradeMarketFees(ActionEvent ae) {
		tradeMarketResult = CalcFeesUtil.calculateTradeingMarketFees(tradeMarketIssueType, tradeMarketAdvArea,
				tradeMarketAddAdvArea, tradeMarketArea, tradeMarketMainActivity, tradeMarketIssueYears);

		tradeMarketIssueType = null;
		tradeMarketAdvArea = null;
		tradeMarketAddAdvArea = null;
		tradeMarketArea = null;
		tradeMarketMainActivity = null;
		tradeMarketIssueYears = null;

	}

	public void calculateBPetrolStationFees(ActionEvent ae) {
		petrolStationResult = CalcFeesUtil.calculatePetrolStationFees(petrolStationIssueType, petrolStationAdvArea,
				petrolStationAddAdvArea, petrolStationIssueYears, petrolStationArea, false);
	}

	public void calculateATMFees(ActionEvent ae) {
		ATMResult = CalcFeesUtil.calculateATMFees(ATMIssueType, ATMAdvArea, ATMAddAdvArea, ATMIssueYears);
	}

	public void calculateTelecomTowerFees(ActionEvent ae) {
		telecomTowerResult = CalcFeesUtil.calculateTelecomTowerFees(telecomTowerIssueType, telecomTowerIssueYears);
	}

	public void calculateBuildingSheltersFees(ActionEvent ae) {
		buildingShelterResult = CalcFeesUtil.calculateBuildingShelterFees(buildingShelterIssueType,
				buildingShelterAdvArea, buildingShelterAddAdvArea, buildingShelterUnitCount, buildingShelterIssueYears,
				buildingShelterClass);
	}

	public void calculateHealthCertificateFees(ActionEvent ae) {
		selectedHealthCertificateList = CalcFeesUtil.calculateHealthCertificate(healthCertificateIssueType);
	}

	public void calculateMobileCartsFees(ActionEvent ae) {
		mobileCartsResult = CalcFeesUtil.calculateMobileCartsFees(mobileCartsIssueType, mobileCartsAddAdvArea,
				mobileCartsIssueYears);
	}

	public void openBillDlg1(ActionEvent ae) {
		RequestContext context = RequestContext.getCurrentInstance();
		newBill.setBillOwnerName("");

		selectedDetailList = buildingResult;
		if (selectedDetailList == null || selectedDetailList.size() > 0) {
			context.execute("PF('AddBillDlgVar').show();");
		} else {
			MsgEntry.addErrorMessage("تأكد من البيانات أولا");
		}

	}

	public void openBillDlg2(ActionEvent ae) {
		newBill.setBillOwnerName("");
		RequestContext context = RequestContext.getCurrentInstance();
		selectedDetailList = tradeMarketResult;
		if (selectedDetailList.size() > 0) {
			context.execute("PF('AddBillDlgVar').show();");
		} else {
			MsgEntry.addErrorMessage("تأكد من البيانات أولا");
		}

	}

	public void openBillDlg3(ActionEvent ae) {
		newBill.setBillOwnerName("");
		RequestContext context = RequestContext.getCurrentInstance();
		selectedDetailList = petrolStationResult;
		if (selectedDetailList.size() > 0) {
			context.execute("PF('AddBillDlgVar').show();");
		} else {
			MsgEntry.addErrorMessage("تأكد من البيانات أولا");
		}

	}

	public void openBillDlg4(ActionEvent ae) {
		newBill.setBillOwnerName("");
		RequestContext context = RequestContext.getCurrentInstance();
		selectedDetailList = ATMResult;
		if (selectedDetailList.size() > 0) {
			context.execute("PF('AddBillDlgVar').show();");
		} else {
			MsgEntry.addErrorMessage("تأكد من البيانات أولا");
		}

	}

	public void openBillDlg5(ActionEvent ae) {
		newBill.setBillOwnerName("");
		RequestContext context = RequestContext.getCurrentInstance();
		selectedDetailList = telecomTowerResult;
		if (selectedDetailList.size() > 0) {
			context.execute("PF('AddBillDlgVar').show();");
		} else {
			MsgEntry.addErrorMessage("تأكد من البيانات أولا");
		}

	}

	public void openBillDlg6(ActionEvent ae) {
		newBill.setBillOwnerName("");
		RequestContext context = RequestContext.getCurrentInstance();
		selectedDetailList = buildingShelterResult;
		if (selectedDetailList.size() > 0) {
			context.execute("PF('AddBillDlgVar').show();");
		} else {
			MsgEntry.addErrorMessage("تأكد من البيانات أولا");
		}

	}

	public void openBillDlg7(ActionEvent ae) {
		newBill.setBillOwnerName("");
		RequestContext context = RequestContext.getCurrentInstance();
		selectedDetailList = mobileCartsResult;
		if (selectedDetailList.size() > 0) {
			context.execute("PF('AddBillDlgVar').show();");
		} else {
			MsgEntry.addErrorMessage("تأكد من البيانات أولا");
		}

	}

	public void openBillDlg8(ActionEvent ae) {
		newBill.setBillOwnerName("");
		RequestContext context = RequestContext.getCurrentInstance();
		selectedDetailList = selectedHealthCertificateList;
		if (selectedDetailList.size() > 0) {
			context.execute("PF('AddBillDlgVar').show();");
		} else {
			MsgEntry.addErrorMessage("تأكد من البيانات أولا");
		}

	}

	public void saveBill(ActionEvent ae) {
		double sum = 0.0;
		for (PayBillDetails billDetail : selectedDetailList) {
			sum = sum + billDetail.getAmount();
		}
		newBill.setPayAmount(sum);
		newBillNmber = dataAccessService.saveBill(newBill, selectedDetailList).toString();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('AddBillDlgVar').hide();");

		context.execute("PF('PrintBillDlgVar').show();");

	}

	public String printBarcodeReport() {

		String reportName = "/reports/bill.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SUBREPORT_DIR", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/bill_detail.jasper"));
		parameters.put("p1", newBillNmber);
		parameters.put("name_customer", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void findTradeLicenseInfo(ActionEvent ae){
		TradeLicense tradeLicense = (TradeLicense) dataAccessService.findEntityById(TradeLicense.class,tradeLicenseId);
		tradeMarketIssueType = 1;
		tradeMarketAdvArea = tradeLicense.getAdvArea().doubleValue();
		tradeMarketAddAdvArea = 0.0;
		tradeMarketArea = tradeLicense.getArea().doubleValue();
		tradeMarketMainActivity = 1;
		tradeMarketIssueYears = tradeLicense.getYears();
		tradeMarketResult = CalcFeesUtil.calculateTradeingMarketFees(tradeMarketIssueType, tradeMarketAdvArea,
				tradeMarketAddAdvArea, tradeMarketArea, tradeMarketMainActivity, tradeMarketIssueYears);
		tradeMarketResult.add(new PayBillDetails(1438, 1438, tradeLicense.getFine() ,"غرامات"));
	}
	
	
	
	
	
}
