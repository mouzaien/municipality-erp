package com.bkeryah.managedBean.fuel;

import java.util.ArrayList;
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

import com.bkeryah.bean.StoreRequestModel;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.ArticleGroup;
import com.bkeryah.entities.ArticleSubGroup;
import com.bkeryah.fuel.entities.Car;
import com.bkeryah.fuel.entities.CarModel;
import com.bkeryah.fuel.entities.FuelType;
import com.bkeryah.fuel.entities.VehicleType;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean(name = "askingVehicleBean")
@ViewScoped
public class AskingVehicleBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<Car> carsList;
	private List<Car> filteredCarsList;
	private List<Article> carsArticle;
	private Car car;
	private String articleCode;
	private String articleName;
	private List<ArticleSubGroup> articleSubGroups;
	private StoreRequestModel articleStore;
	private Integer carArtId;
	private Integer subGroupId;
	private Integer selectedItemId;
	private static final Logger logger = Logger.getLogger(AskingVehicleBean.class);

	@PostConstruct
	public void init() {
		car = new Car();
		carsList = dataAccessService.loadAllCars();
		Collections.sort(carsList);
		// getAll Sub groups meaning All Type of Cars
		articleSubGroups = dataAccessService.getAllArticleSubGroupsByGroupId(9);
		// carsArticle = dataAccessService.getAllArticles(13);

	}

	// public String printCarsReport() {
	// String reportName = "";
	// Map<String, Object> parameters = new HashMap<String, Object>();
	// reportName = "/reports/cars_list.jasper";
	// parameters.put("LOGO_DIR",
	// FacesContext.getCurrentInstance().getExternalContext()
	// .getRealPath(Utils.loadMessagesFromFile("report.logo")));
	// Utils.printPdfReport(reportName, parameters);
	// return "";
	// }

	public void loadCarDetails() {
		if (subGroupId != null) {
			carsList = dataAccessService.loadCarDetailsBySubGroupId(subGroupId);
			// if(carsList.size()> 0)
			// {
			//
			// }
			// carsList = dataAccessService.loadCarDetailsByArtId(subGroupId);
		}
	}

	public String loadModel(Integer modelId) {
		if (modelId != null) {
			CarModel cm = (CarModel) dataAccessService.findEntityById(CarModel.class, modelId);
			return cm.getName();
		}
		return "";
	}

	public String loadBrandName(Integer fId) {
		if (fId != null) {
			CarModel cm = (CarModel) dataAccessService.findEntityById(CarModel.class, fId);
			return cm.getBrand().getName();
		}
		return "";
	}

	public String loadVehicleType(Integer vehicleTypeId) {
		if (vehicleTypeId != null) {

			VehicleType vt = (VehicleType) dataAccessService.findEntityById(VehicleType.class, vehicleTypeId);
			return vt.getName();
		}
		return "";
	}

	public void loadSelectedArticle() {
		if (car != null) {
			articleStore = new StoreRequestModel();
			selectedItemId = car.getArtId();
			this.articleCode = car.getCarCode();
			this.articleName = car.getArtName();
			// articleStore.setSpecialNumber(car.g);
			articleStore.setArticleId(car.getArtId());
			articleStore.setArticleCode(car.getCarCode());
			articleStore.setArticleName(car.getArtName());
			System.out.println("artId" + car.getArtId());
			// articleStore.setArticleUnite(articleItem.getArticleUnite());
			articleStore.setHistoryList(dataAccessService.getArticleHistory(car.getArtId(), new Integer(13)));
			Utils.updateUIComponent("includeform:exchange_dlgId");
			Utils.openDialog("exchange_dlg");
		}

	}

	// PRINT roport about car movements
	public String printArticleMovements() {
		String reportName = "/reports/car_artical_card.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		Integer availablebQty = 0;
		parameters.put("item_Id", selectedItemId);// "259306";
		String str = " رقم الهيكل" + car.getChassisNumber() + "||رقم اللوحة " + car.getMatricule() + "|| رقم الباب"
				+ car.getNumDoor();
		parameters.put("desc", str);
		parameters.put("type", loadModel(car.getCarModel()));
		parameters.put("carColor", car.getCarColor());

		List<StoreRequestModel> strReqModelList = new ArrayList<StoreRequestModel>();
		List<StoreRequestModel> artHistory = articleStore.getHistoryList();
		for (StoreRequestModel requestdets : artHistory) {
			StoreRequestModel requestModel = new StoreRequestModel();

			requestModel.setTransactionCode(requestdets.getTransactionCode());

			requestModel.setArticleId(articleStore.getArticleId());
			requestModel.setArticleCode(articleStore.getArticleCode());
			requestModel.setArticleName(articleStore.getArticleName());
			requestModel.setArticleUnite(articleStore.getArticleUnite());
			requestModel.setSpecialNumber(requestdets.getSpecialNumber());
			requestModel.setTransactionDate(requestdets.getTransactionDate());
			requestModel.setTransactionCode(requestdets.getTransactionCode());
			requestModel.setSupplierName(requestdets.getSupplierName());
			requestModel.setRequesterName(requestdets.getRequesterName());
			switch (requestModel.getTransactionCode()) {
			case 2://// memoRecript
				requestModel.setQtyInput(requestdets.getQtyOutput());
				availablebQty += requestdets.getQtyOutput();
				requestModel.setQtyAvailable(availablebQty);
				strReqModelList.add(requestModel);
				break;
			case 3:// requestChange
				requestModel.setQtyOutput(requestdets.getQtyOutput());
				availablebQty = availablebQty - requestModel.getQtyOutput();
				requestModel.setQtyAvailable(availablebQty);
				strReqModelList.add(requestModel);
				break;
			case 5:// rage3
				availablebQty = requestdets.getQtyOutput();
				requestModel.setQtyAvailable(availablebQty);
				strReqModelList.add(requestModel);
				break;
			}
		}
		Utils.printPdfReportFromListDataSource(reportName, parameters, strReqModelList);
		return "";
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
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

	public List<Article> getCarsArticle() {
		return carsArticle;
	}

	public void setCarsArticle(List<Article> carsArticle) {
		this.carsArticle = carsArticle;
	}

	public Integer getCarArtId() {
		return carArtId;
	}

	public void setCarArtId(Integer carArtId) {
		this.carArtId = carArtId;
	}

	public StoreRequestModel getArticleStore() {
		return articleStore;
	}

	public void setArticleStore(StoreRequestModel articleStore) {
		this.articleStore = articleStore;
	}

	public Integer getSelectedItemId() {
		return selectedItemId;
	}

	public void setSelectedItemId(Integer selectedItemId) {
		this.selectedItemId = selectedItemId;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public List<ArticleSubGroup> getArticleSubGroups() {
		return articleSubGroups;
	}

	public void setArticleSubGroups(List<ArticleSubGroup> articleSubGroups) {
		this.articleSubGroups = articleSubGroups;
	}

	public Integer getSubGroupId() {
		return subGroupId;
	}

	public void setSubGroupId(Integer subGroupId) {
		this.subGroupId = subGroupId;
	}

}
