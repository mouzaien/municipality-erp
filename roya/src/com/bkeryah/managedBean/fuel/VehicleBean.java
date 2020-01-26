package com.bkeryah.managedBean.fuel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.bkeryah.entities.Article;
import com.bkeryah.fuel.entities.Car;
import com.bkeryah.fuel.entities.CarModel;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class VehicleBean{
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<Car> carsList;
	private List<Car> filteredCarsList;
	private List<Article> carsArticle;
	private List<CarModel> carModelsList;
	private Car car = new Car();
	private boolean addMode;
	private Integer carBrandId;
	private static final Logger logger = Logger.getLogger(VehicleBean.class);

	@PostConstruct
	public void init() {
		carsList = dataAccessService.loadAllCars();
		Collections.sort(carsList);
		carsArticle =dataAccessService.getAllArticles(13);
	
		//getAllArticles
	}
	
	public void addCar(){
		car = new Car();
		addMode = true;
	}
	
	public void loadSelectedCar() {
//		addMode = false;
		addMode = true;
		carBrandId = car.getModel().getBrandId();
		loadCarModels();
	}
	
	public void loadCarModels() {
		if(carBrandId != null){
			carModelsList = dataAccessService.loadCarModelByBrandId(carBrandId);
		}
	}
	
	public String printCarsReport() {
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
			System.out.println("car.getArtId() >>" +car.getArtId());
			Article art= dataAccessService.getArticleById(car.getArtId());
			car.setCarCode(art.getCode());
			if(car.getId() == null){
			dataAccessService.save(car);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			//carsList.add((Car) dataAccessService.findEntityById(Car.class, car.getId()));
			
			car = new Car();
			logger.info("add car: id: " + car.getId());
			}
			else
			{
				update();
				
			}
			carsList = dataAccessService.loadAllCars();
			System.out.println(carsList.size());
			Utils.updateUIComponent("includeform");
			Utils.closeDialog("car_dlg");
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("CAR_Details_Error"));
			logger.error("add car: id: " + car.getId());
		}
	}
	
	public void update(){
		try{
			dataAccessService.updateObject(car);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
//			carsList.add((Car) dataAccessService.findEntityById(Car.class, car.getId()));
			car = new Car();
			logger.info("add car: id: " + car.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("add car: id: " + car.getId());
		}
	}
	
	public void deleteCar(){
		try{
			dataAccessService.deleteObject(car);
			carsList.remove(car);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("delete car: id: " + car.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("delete car: id: " + car.getId());
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

	public List<Car> getCarsList() {
		return carsList;
	}

	public void setCarsList(List<Car> carsList) {
		this.carsList = carsList;
	}

	public List<Car> getFilteredCarsList() {
		return filteredCarsList;
	}

	public void setFilteredCarsList(List<Car> filteredCarsList) {
		this.filteredCarsList = filteredCarsList;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public List<CarModel> getCarModelsList() {
		return carModelsList;
	}

	public void setCarModelsList(List<CarModel> carModelsList) {
		this.carModelsList = carModelsList;
	}

	public Integer getCarBrandId() {
		return carBrandId;
	}

	public void setCarBrandId(Integer carBrandId) {
		this.carBrandId = carBrandId;
	}

	public List<Article> getCarsArticle() {
		return carsArticle;
	}

	public void setCarsArticle(List<Article> carsArticle) {
		this.carsArticle = carsArticle;
	}

}
