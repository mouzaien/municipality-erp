package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.ItemUnite;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ItemUniteBean {
	private static Logger logger = Logger.getLogger(ItemUniteBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ItemUnite unite = new ItemUnite();
	private List<ItemUnite> uniteList = new ArrayList<ItemUnite>();
	private Integer id;
	private String name;
	private List<ItemUnite> filtereduniteList;

	@PostConstruct
	public void init() {
		uniteList = dataAccessService.findAllUnite();
	}

	// add الاضافة
	public void saveUnite() {
		try {
			if (unite != null) {
				unite.setName(name);
				dataAccessService.save(unite);
				uniteList = dataAccessService.findAllUnite();
			}
			MsgEntry.addInfoMessage("تمت الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage("خطأ: لم تتم الإضافة");
			e.printStackTrace();

		}

	}

	// Edit تعديل
	public void updateUnite() {
		try {
			if (unite != null) {
				dataAccessService.updateObject(unite);
				unite = new ItemUnite();
				// logger.info("Update User: id: " + unite.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// حــــــــــــــذف
	public void deleteUnite() {
		try {
			if (unite != null) {
				dataAccessService.deleteObject(unite);
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {
			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ItemUniteBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public ItemUnite getUnite() {
		return unite;
	}

	public void setUnite(ItemUnite unite) {
		this.unite = unite;
	}

	public List<ItemUnite> getUniteList() {
		return uniteList;
	}

	public void setUniteList(List<ItemUnite> uniteList) {
		this.uniteList = uniteList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ItemUnite> getFiltereduniteList() {
		return filtereduniteList;
	}

	public void setFiltereduniteList(List<ItemUnite> filtereduniteList) {
		this.filtereduniteList = filtereduniteList;
	}

}
