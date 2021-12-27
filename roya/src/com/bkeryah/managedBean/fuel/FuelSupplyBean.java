package com.bkeryah.managedBean.fuel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.*;

import com.bkeryah.fuel.entities.FuelSupply;
import com.bkeryah.fuel.entities.FuelTransaction;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class FuelSupplyBean{
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<FuelSupply> fuelSuppliesList;
	private List<FuelSupply> filteredFuelSuppliesList;
	private FuelSupply fuelSupply = new FuelSupply();
	private boolean addMode;
	private static final Logger logger = LogManager.getLogger(FuelSupplyBean.class);

	@PostConstruct
	public void init() {
		fuelSuppliesList = dataAccessService.loadAllFuelSupplies();
	}
	
	public void addFuelSupply(){
		fuelSupply = new FuelSupply();
		addMode = true;
	}
	
	public String printFuelSuppliesReport() {
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		reportName = "/reports/cars_list.jasper";
		parameters.put("LOGO_DIR",
				FacesContext.getCurrentInstance().getExternalContext().getRealPath(Utils.loadMessagesFromFile("report.logo")));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}
	
	public void save(){
		try{
//			if((fuelSuppliesList != null) && (!fuelSuppliesList.isEmpty()))
//				fuelSupply.setActualQuantity(fuelSupply.getQuantity() + fuelSuppliesList.get(0).getActualQuantity());
//			else
//				fuelSupply.setActualQuantity(fuelSupply.getQuantity());
			FuelSupply lastFuelSupply = dataAccessService.loadLastFuelSupply(fuelSupply.getFuelTypeId());
			if(lastFuelSupply.getId() != null)
				fuelSupply.setActualQuantity(fuelSupply.getQuantity() + lastFuelSupply.getActualQuantity());
			else
				fuelSupply.setActualQuantity(fuelSupply.getQuantity());
			
			dataAccessService.save(fuelSupply);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			fuelSuppliesList.add((FuelSupply) dataAccessService.findEntityById(FuelSupply.class, fuelSupply.getId()));
			fuelSupply = new FuelSupply();
			logger.info("add fuelSupply: id: " + fuelSupply.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("add fuelSupply: id: " + fuelSupply.getId());
		}
	}
	
	public void deleteFuelSupply(){
		try{
			dataAccessService.deleteObject(fuelSupply);
			fuelSuppliesList.remove(fuelSupply);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("delete fuelSupply: id: " + fuelSupply.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("delete fuelSupply: id: " + fuelSupply.getId());
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

	public List<FuelSupply> getFuelSuppliesList() {
		return fuelSuppliesList;
	}

	public void setFuelSuppliesList(List<FuelSupply> fuelSuppliesList) {
		this.fuelSuppliesList = fuelSuppliesList;
	}

	public List<FuelSupply> getFilteredFuelSuppliesList() {
		return filteredFuelSuppliesList;
	}

	public void setFilteredFuelSuppliesList(List<FuelSupply> filteredFuelSuppliesList) {
		this.filteredFuelSuppliesList = filteredFuelSuppliesList;
	}

	public FuelSupply getFuelSupply() {
		return fuelSupply;
	}

	public void setFuelSupply(FuelSupply fuelSupply) {
		this.fuelSupply = fuelSupply;
	}
}
