package com.bkeryah.managedBean.fuel;

import java.text.SimpleDateFormat;
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
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.logging.log4j.*;
import org.primefaces.event.SelectEvent;

import com.bkeryah.fuel.entities.FuelSupply;
import com.bkeryah.fuel.entities.FuelTransaction;
import com.bkeryah.fuel.entities.UserCars;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class FuelBean{
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<FuelTransaction> fuelTransactionsList = new ArrayList<>();
	private List<FuelTransaction> filteredFuelTransactionsList;
	private List<UserCars> userCarsList;
	private FuelTransaction fuelTransaction = new FuelTransaction();
	private boolean addMode;
	private Integer employerId;
	private Date startDate;
	private Date endDate;
	private Integer numDoor;
	private boolean activeAddBtn;
	private FuelTransaction lastTransaction;
	private FuelSupply fuelSupply;
	private static final Logger logger = LogManager.getLogger(FuelBean.class);

	@PostConstruct
	public void init() {
//		fuelTransactionsList = dataAccessService.loadAllFuelTransactions();
	}
	
	public void searchFuelTransactions(){
		if((startDate != null) && (endDate != null))
			fuelTransactionsList = dataAccessService.loadFuelTransactions(startDate, endDate);
	}
	
	public String printFuelReport() {
		if((startDate == null) || (endDate == null))
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		reportName = "/reports/fuel_transactions.jasper";
		parameters.put("start_date", sdf.format(startDate));
		parameters.put("end_date", sdf.format(endDate));
		parameters.put("LOGO_DIR",
				FacesContext.getCurrentInstance().getExternalContext().getRealPath(Utils.loadMessagesFromFile("report.logo")));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}
	
	public String printBillReport() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		String reportName = "/reports/bill_fuel.jasper";
		parameters.put("transactionId", fuelTransaction.getId());
		parameters.put("LOGO_DIR",
				FacesContext.getCurrentInstance().getExternalContext().getRealPath(Utils.loadMessagesFromFile("report.logo")));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}
	
	public void addFuel(){
		fuelTransaction = new FuelTransaction();
		addMode = true;
		activeAddBtn = false;
		numDoor = null;
		lastTransaction = new FuelTransaction();
		fuelSupply = new FuelSupply();
	}
	
	public void loadSelectedCar() {
		addMode = false;
		activeAddBtn = false;
	}
	
	public void loadUserCars() {
		if(employerId != null){
			userCarsList = dataAccessService.loadUserCarsByUserId(employerId);
		}
	}
	
	public void loadCarDetails(AjaxBehaviorEvent event) {
		if(numDoor != null){
			lastTransaction = new FuelTransaction();
			activeAddBtn = false;
			UserCars userCar = (UserCars) dataAccessService.findCarByNumDoor(numDoor);
			fuelTransaction.setUserCar(userCar);
			fuelTransaction.setUserCarId(userCar.getId());
			if(userCar != null){
				lastTransaction = dataAccessService.findLastFuelTransaction(userCar.getId());
				activeAddBtn = true;
				fuelSupply = dataAccessService.loadLastFuelSupply(fuelTransaction.getUserCar().getCar().getFuelTypeId());
				if(fuelSupply.getActualQuantity() == null)
					fuelSupply.setActualQuantity(0);
			} 
		}
	}
	
	public void calculateEndDate(SelectEvent event){
		if(fuelTransaction.getStartDate() != null){
			 Calendar c = Calendar.getInstance();
			 c.setTime(fuelTransaction.getStartDate());
			 c.add(Calendar.DATE, 7);
			 fuelTransaction.setEndDate(c.getTime());
		}
	}
	
	public void save(){
		try{
			fuelTransaction.setCreatedBy(Utils.findCurrentUser().getUserId());
			fuelTransaction.setCreatedIn(new Date());
			dataAccessService.save(fuelTransaction);
			updateFuelQuatity();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			fuelTransactionsList = new ArrayList<>();
			fuelTransactionsList.add((FuelTransaction) dataAccessService.findEntityById(FuelTransaction.class, fuelTransaction.getId()));
//			fuelTransaction = new FuelTransaction();
			logger.info("add FuelTransaction: id: " + fuelTransaction.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("add FuelTransaction: id: " + fuelTransaction.getId());
		}
	}
	
	private void updateFuelQuatity() {
		fuelSupply.setActualQuantity(fuelSupply.getActualQuantity()-fuelTransaction.getDeliveryQty());
		dataAccessService.updateObject(fuelSupply);
	}

	public void deleteCar(){
		try{
			dataAccessService.deleteObject(fuelTransaction);
			fuelTransactionsList.remove(fuelTransaction);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("delete FuelTransaction: id: " + fuelTransaction.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("delete FuelTransaction: id: " + fuelTransaction.getId());
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<FuelTransaction> getFuelTransactionsList() {
		return fuelTransactionsList;
	}

	public void setFuelTransactionsList(List<FuelTransaction> fuelTransactionsList) {
		this.fuelTransactionsList = fuelTransactionsList;
	}

	public List<FuelTransaction> getFilteredFuelTransactionsList() {
		return filteredFuelTransactionsList;
	}

	public void setFilteredFuelTransactionsList(List<FuelTransaction> filteredFuelTransactionsList) {
		this.filteredFuelTransactionsList = filteredFuelTransactionsList;
	}

	public List<UserCars> getUserCarsList() {
		return userCarsList;
	}

	public void setUserCarsList(List<UserCars> userCarsList) {
		this.userCarsList = userCarsList;
	}

	public FuelTransaction getFuelTransaction() {
		return fuelTransaction;
	}

	public void setFuelTransaction(FuelTransaction fuelTransaction) {
		this.fuelTransaction = fuelTransaction;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getNumDoor() {
		return numDoor;
	}

	public void setNumDoor(Integer numDoor) {
		this.numDoor = numDoor;
	}

	public boolean isActiveAddBtn() {
		return activeAddBtn;
	}

	public void setActiveAddBtn(boolean activeAddBtn) {
		this.activeAddBtn = activeAddBtn;
	}

	public FuelTransaction getLastTransaction() {
		return lastTransaction;
	}

	public void setLastTransaction(FuelTransaction lastTransaction) {
		this.lastTransaction = lastTransaction;
	}

	public FuelSupply getFuelSupply() {
		return fuelSupply;
	}

	public void setFuelSupply(FuelSupply fuelSupply) {
		this.fuelSupply = fuelSupply;
	}
	
}
