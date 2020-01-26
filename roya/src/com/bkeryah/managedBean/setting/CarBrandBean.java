package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.fuel.entities.CarBrand;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class CarBrandBean {
	private static Logger logger = Logger.getLogger(CarBrandBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private CarBrand brand = new CarBrand();
	private List<CarBrand> carList = new ArrayList<CarBrand>();
	private String name;
	private List<CarBrand> filteredcarList;

	@PostConstruct
	public void init() {
		carList = dataAccessService.findAllBrand();
	}

	// حــــــــــــــذف
	public void deleteBrand() {
		try {
			if (brand != null) {
				dataAccessService.deleteObject(brand);
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("ملحوظة:يجب حذف النوع قبل إتمام العملية");
		}
	}

	// add الاضافة
	public void saveBrand() {
		try {
			if (brand != null) {
				brand = new CarBrand();
				brand.setName(name);
				dataAccessService.save(brand);
				carList = dataAccessService.findAllBrand();
			}
			MsgEntry.addInfoMessage("تم الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// Edit تعديل
	public void updateBrand() {
		try {
			if (brand != null) {
				dataAccessService.updateObject(brand);
				brand = new CarBrand();
				logger.info("Update User: id: " + brand.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
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

	public CarBrand getBrand() {
		return brand;
	}

	public void setBrand(CarBrand brand) {
		this.brand = brand;
	}

	public List<CarBrand> getCarList() {
		return carList;
	}

	public void setCarList(List<CarBrand> carList) {
		this.carList = carList;
	}

	public List<CarBrand> getFilteredcarList() {
		return filteredcarList;
	}

	public void setFilteredcarList(List<CarBrand> filteredcarList) {
		this.filteredcarList = filteredcarList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
