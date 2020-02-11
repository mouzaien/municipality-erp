package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.fuel.entities.CarBrand;
import com.bkeryah.fuel.entities.CarModel;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class CarModelBean {
	private static Logger logger = Logger.getLogger(CarModelBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private CarModel model = new CarModel();
	private List<CarModel> carList = new ArrayList<CarModel>();
	private List<CarBrand> brandList = new ArrayList<CarBrand>();
	private String name;
	private Integer brandId;
	private List<CarModel> filteredcarList;

	@PostConstruct
	public void init() {
		carList = dataAccessService.findAllModel();
		brandList = dataAccessService.findAllBrand();
	}

	// حــــــــــــــذف
	public void deleteModel() {
		try {
			if (model != null) {
				dataAccessService.deleteObject(model);
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add الاضافة
	//INSERT
	public void saveModel() {
		try {
			if (model != null) {
				model.setName(name);
				model.setBrandId(brandId);
				dataAccessService.save(model);
				carList = dataAccessService.findAllModel();
				model = new CarModel();
				MsgEntry.addInfoMessage("تم الإضافة");

			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// Edit تعديل
	public void updateModel() {
		try {
			if (model != null) {
				dataAccessService.updateObject(model);
				model = new CarModel();
				logger.info("Update User: id: " + model.getId());
				MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");

			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public CarModel getModel() {
		return model;
	}

	public void setModel(CarModel model) {
		this.model = model;
	}

	public List<CarModel> getCarList() {
		return carList;
	}

	public void setCarList(List<CarModel> carList) {
		this.carList = carList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CarModel> getFilteredcarList() {
		return filteredcarList;
	}

	public void setFilteredcarList(List<CarModel> filteredcarList) {
		this.filteredcarList = filteredcarList;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public List<CarBrand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<CarBrand> brandList) {
		this.brandList = brandList;
	}
}
