package com.bkeryah.managedBean.fuel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;

import com.bkeryah.bean.StoreRequestModel;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.ArticleSubGroup;
import com.bkeryah.fuel.entities.Car;
import com.bkeryah.fuel.entities.CarModel;
import com.bkeryah.fuel.entities.VehicleType;
import com.bkeryah.service.IDataAccessService;

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
	private static final Logger logger = LogManager.getLogger(AskingVehicleBean.class);

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
			loadCarData(car);
			Utils.updateUIComponent("includeform:exchange_dlgId");
			Utils.openDialog("exchange_dlg");
		}

	}

	private void loadCarData(Car car) {
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
		List<StoreRequestModel> list = dataAccessService.getArticleHistory(car.getArtId(), new Integer(23));
		articleStore.getHistoryList().addAll(list);
	}

	// PRINT report about car movements
	// public String printArticleEnquiry() {
	// String reportName = "/reports/car_movement_card.jrxml";
	// Map<String, Object> parameters = new HashMap<String, Object>();
	// List<StoreRequestModel> strReqModelList = new
	// ArrayList<StoreRequestModel>();
	// Utils.printPdfReportFromListDataSource(reportName, parameters,
	// strReqModelList);
	// return "";
	// }

	// PRINT report about car movements
	public String printArticleMovements() {
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		Integer availablebQty = 0;
		List<Car> printedCarsList = new ArrayList<>();
		List<StoreRequestModel> strReqModelList = new ArrayList<StoreRequestModel>();
		if (car.getCarCode() != null) {
			reportName = "/reports/car_movement_card.jrxml";
			printedCarsList.add(car);
		} else {
			reportName = "/reports/all_cars_movement.jrxml";
			printedCarsList.addAll(carsList);
		}
		for (Car car : printedCarsList) {
			loadCarData(car);
			parameters.put("item_Id", selectedItemId);// "259306";
			String str = " رقم الهيكل" + car.getChassisNumber() + "||رقم اللوحة " + car.getMatricule() + "|| رقم الباب"
					+ car.getNumDoor();
			parameters.put("desc", str);
			parameters.put("type", loadModel(car.getCarModel()));
			parameters.put("carColor", car.getCarColor());

			strReqModelList =  new ArrayList<>();
			List<StoreRequestModel> artHistory = articleStore.getHistoryList();
			for (StoreRequestModel requestdets : artHistory) {
				StoreRequestModel requestModel = new StoreRequestModel();
				requestModel.setTransactionCode(requestdets.getTransactionCode());

				requestModel.setArticleId(articleStore.getArticleId());
				requestModel.setArticleCode(articleStore.getArticleCode());
				requestModel.setArticleName(articleStore.getArticleName());
				requestModel.setArticleUnite(articleStore.getArticleUnite());
				if (requestdets.getSerialNumber() == null || requestdets.getSerialNumber().isEmpty())
					requestModel.setSpecialNumber(null);
				else
					requestModel.setSpecialNumber(Integer.parseInt(requestdets.getSerialNumber()));
				requestModel.setTransactionDate(requestdets.getTransactionDate());
				requestModel.setTransactionGDate(requestdets.getTransactionGDate());
				requestModel.setTransactionCode(requestdets.getTransactionCode());
				requestModel.setSupplierName(requestdets.getSupplierName());
				requestModel.setRequesterName(requestdets.getRequesterName());
				// if(requestModel.getTransactionCode()=)
				switch (requestModel.getTransactionCode()) {
				case 2://// memoRecript
					requestModel.setQtyInput(requestdets.getQtyOutput());
					availablebQty += requestdets.getQtyOutput();
					requestModel.setQtyAvailable(availablebQty);
					strReqModelList.add(requestModel);
					break;
				case 3:// requestChange
				case 6:// rage3RequestChange
					requestModel.setQtyOutput(requestdets.getQtyOutput());
					availablebQty = availablebQty - requestModel.getQtyOutput();
					requestModel.setQtyAvailable(availablebQty);
					strReqModelList.add(requestModel);
					break;
				case 5:// rage3
					requestModel.setSupplierName(requestdets.getRequesterName());
					requestModel.setQtyInput(requestdets.getQtyOutput());
					availablebQty = requestdets.getQtyOutput();
					requestModel.setQtyAvailable(availablebQty);
					strReqModelList.add(requestModel);
					break;
				case 7:// mona2la
						// requestModel.setSupplierName(requestdets.getRequesterName());
						// requestModel.setQtyInput(requestdets.getQtyOutput());
						// availablebQty = requestdets.getQtyOutput();
					requestModel.setQtyAvailable(availablebQty);
					strReqModelList.add(requestModel);
					break;
				case 1:// inventory
					availablebQty = requestdets.getQtyOutput();
					requestModel.setQtyAvailable(availablebQty);
					strReqModelList.add(requestModel);
					break;

				}
				Collections.sort(strReqModelList, new Comparator<StoreRequestModel>() {
					@Override
					public int compare(StoreRequestModel m1, StoreRequestModel m2) {
						return m1.getTransactionGDate().compareTo(m2.getTransactionGDate());
					}
				});
			}
			car.setCarLastMovement(strReqModelList.get(strReqModelList.size() - 1).getRequesterName());

		}
		if (car.getCarCode() != null) {
			Utils.printPdfReportFromListDataSource(reportName, parameters, strReqModelList);
		}else {
			Collections.sort(printedCarsList, new Comparator<Car>() {
				@Override
				public int compare(Car c1, Car c2) {
					return c1.getNumDoor().compareTo(c2.getNumDoor());
				}
			});
			Utils.printPdfReportFromListDataSource(reportName, parameters, printedCarsList);
		}
		car =  new Car();
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
