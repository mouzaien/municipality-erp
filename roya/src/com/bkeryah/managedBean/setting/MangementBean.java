package com.bkeryah.managedBean.setting;

/**
 * @author thapet
 *
 */
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.entities.WrkArchiveRcipent;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;
import utilities.MsgEntry;
import utilities.Utils;

import org.apache.log4j.Logger;

@ManagedBean
@ViewScoped
public class MangementBean {
	private static Logger logger = Logger.getLogger(MangementBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private User mangers = new User();
	private List<User> usersList = new ArrayList<User>();
	private WrkArchiveRcipent mang = new WrkArchiveRcipent();
	private List<WrkArchiveRcipent> wrkList = new ArrayList<WrkArchiveRcipent>();
	private List<WrkArchiveRcipent> filteredwrkList;
	private Integer id;
	private String title;

	@PostConstruct
	public void init() {

		usersList = dataAccessService.getAllUsers();
		wrkList = dataAccessService.findAllMang();

	}

	/// الاضافة
	public void saveMang() {
		try {
			if (mang != null) {
				mang = new WrkArchiveRcipent();
				mang.setTitle(title);
				mang.setId(id);
				dataAccessService.save(mang);
				wrkList = dataAccessService.findAllMang();
			}
			MsgEntry.addInfoMessage("تم الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage("خطأ:لم يتم الإضافة");
			e.printStackTrace();
		}

	}

	// التعديل
	public void updateMang() {
		try {
			if (mang != null) {
				dataAccessService.updateObject(mang);
				mang = new WrkArchiveRcipent();
				// logger.info("update User:id:" + mang.getId());
			}
			MsgEntry.addInfoMessage("تم التعديل");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
			e.printStackTrace();
		}

	}

	// الحذف
	public void deleteMang() {
		try {
			if (mang != null) {
				dataAccessService.deleteObject(mang);
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {
			MsgEntry.addErrorMessage("لم يتم الحذف");
			e.printStackTrace();
		}

	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		MangementBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public WrkArchiveRcipent getMang() {
		return mang;
	}

	public void setMang(WrkArchiveRcipent mang) {
		this.mang = mang;
	}

	public List<WrkArchiveRcipent> getWrkList() {
		return wrkList;
	}

	public void setWrkList(List<WrkArchiveRcipent> wrkList) {
		this.wrkList = wrkList;
	}

	public List<WrkArchiveRcipent> getFilteredwrkList() {
		return filteredwrkList;
	}

	public void setFilteredwrkList(List<WrkArchiveRcipent> filteredwrkList) {
		this.filteredwrkList = filteredwrkList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getMangers() {
		return mangers;
	}

	public void setMangers(User mangers) {
		this.mangers = mangers;
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

}