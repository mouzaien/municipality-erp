package com.bkeryah.managedBean.param;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.FinEntity;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class SuppsParamBean {
	String TAG = this.getClass().getSimpleName();
	private static Logger logger = Logger.getLogger(FinEntity.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<FinEntity> suppsList;
	private List<FinEntity> filteredSupps;
	private FinEntity finEntity = new FinEntity();
	private boolean addMode;

	@PostConstruct
	public void init() {
		suppsList = dataAccessService.getAllfinEntitys();
	}

	// ==========================CRUD
	// Operations=================================
	public void addNewSupp() {
		addMode = true;
		finEntity = new FinEntity();
	}

	// todo
	public void loadSupps(FinEntity supp) {
		finEntity = (FinEntity) dataAccessService.findEntityById(FinEntity.class, supp.getFinEntityId());
		logger.info("finEntity is?= " + finEntity);
	}

	public void loadSelectedfinEntity() {
		addMode = false;
	}

	public void savefinEntity() {
		try {
			// System.out.println(" finEntity.getFinEntityName()" +
			// finEntity.getFinEntityName());
			// System.out.println(" finEntity.getFinEntityTypeUser()" +
			// finEntity.getFinEntityTypeUser());
			if (finEntity == null || (finEntity.getFinEntityName().isEmpty())
					|| finEntity.getFinEntityName().isEmpty()) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

			} else {
				dataAccessService.saveObject(finEntity);
				suppsList.add(finEntity);
				finEntity = new FinEntity();
				logger.info("add finEntity: id: " + finEntity.getFinEntityId());
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}
	}

	public void updatefinEntity() {
		try {
			if (finEntity != null) {
				dataAccessService.updateObject(finEntity);
				finEntity = new FinEntity();
				logger.info("Update User: id: " + finEntity.getFinEntityId());
			}
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}
	}

	// =========== ===========Getters and Setters ===================== \\

	public String getTAG() {
		return TAG;
	}

	public void setTAG(String tAG) {
		TAG = tAG;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		SuppsParamBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<FinEntity> getSuppsList() {
		return suppsList;
	}

	public void setSuppsList(List<FinEntity> suppsList) {
		this.suppsList = suppsList;
	}

	public List<FinEntity> getFilteredSupps() {
		return filteredSupps;
	}

	public void setFilteredSupps(List<FinEntity> filteredSupps) {
		this.filteredSupps = filteredSupps;
	}

	public FinEntity getFinEntity() {
		return finEntity;
	}

	public void setFinEntity(FinEntity finEntity) {
		this.finEntity = finEntity;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

}
