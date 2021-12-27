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

import com.bkeryah.fuel.entities.Car;
import com.bkeryah.fuel.entities.CarModel;
import com.bkeryah.fuel.entities.UserCars;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class UserCarsBean{
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<UserCars> userCarsList;
	private List<UserCars> filteredUserCarsList;
	private List<CarModel> carModelsList;
	private List<Car> carsList;
	private UserCars userCar = new UserCars();
	private Integer employerId;
	private Integer carBrandId;
	private Integer carModelId;
	
	private boolean addMode;
	
	private static final Logger logger = LogManager.getLogger(UserCarsBean.class);

	@PostConstruct
	public void init() {
		userCarsList = dataAccessService.loadAllAllocatedUserCars();
	}
	
	public void addUserCar(){
		userCar = new UserCars();
		userCar.setOwnerStatus(1);
		addMode = true;
	}
	
	public void loadSelectedCar() {
		addMode = false;
	}
	
	public void loadCarModels() {
		if(carBrandId != null){
			carModelsList = dataAccessService.loadCarModelByBrandId(carBrandId);
		}
	}
	
	public void loadCars(){
		if(carModelId != null){
			carsList = dataAccessService.loadNotAllocatedCarsByModelId(carModelId);
		}
	}
	
	public void loadCarDetails() {
		if(userCar.getCarId() != null){
			userCar.setCar((Car) dataAccessService.findEntityById(Car.class, userCar.getCarId()));
		}
	}
	
	public void save(){
		try{
			userCar.setCreatedBy(Utils.findCurrentUser().getUserId());
			dataAccessService.save(userCar);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			userCarsList.add((UserCars) dataAccessService.findEntityById(UserCars.class, userCar.getId()));
			userCar = new UserCars();
			logger.info("add UserCar: id: " + userCar.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("add UserCar: id: " + userCar.getId());
		}
	}
	
	public String printUserCarsReport() {
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		reportName = "/reports/user_cars.jasper";
		parameters.put("LOGO_DIR",
				FacesContext.getCurrentInstance().getExternalContext().getRealPath(Utils.loadMessagesFromFile("report.logo")));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}
	
	public void deleteCar(){
		try{
			userCar.setOwnerStatus(0);
			dataAccessService.updateObject(userCar);
			userCarsList.remove(userCar);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("delete UserCar: id: " + userCar.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("delete UserCar: id: " + userCar.getId());
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<UserCars> getUserCarsList() {
		return userCarsList;
	}

	public void setUserCarsList(List<UserCars> userCarsList) {
		this.userCarsList = userCarsList;
	}

	public List<UserCars> getFilteredUserCarsList() {
		return filteredUserCarsList;
	}

	public void setFilteredUserCarsList(List<UserCars> filteredUserCarsList) {
		this.filteredUserCarsList = filteredUserCarsList;
	}

	public UserCars getUserCar() {
		return userCar;
	}

	public void setUserCar(UserCars userCar) {
		this.userCar = userCar;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public List<CarModel> getCarModelsList() {
		return carModelsList;
	}

	public void setCarModelsList(List<CarModel> carModelsList) {
		this.carModelsList = carModelsList;
	}

	public List<Car> getCarsList() {
		return carsList;
	}

	public void setCarsList(List<Car> carsList) {
		this.carsList = carsList;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public Integer getCarBrandId() {
		return carBrandId;
	}

	public void setCarBrandId(Integer carBrandId) {
		this.carBrandId = carBrandId;
	}

	public Integer getCarModelId() {
		return carModelId;
	}

	public void setCarModelId(Integer carModelId) {
		this.carModelId = carModelId;
	}
	
}
