package com.bkeryah.support;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;

import com.bkeryah.entities.TechnicalUsers;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class TechnicalUsersBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<TechnicalUsers> technicalUsersList;
	private List<TechnicalUsers>filteredTechnicalUsersList;
	private TechnicalUsers technicalUser = new TechnicalUsers();
	protected static final Logger logger = LogManager.getLogger(TechnicalUsersBean.class);

	@PostConstruct
	public void init() {
		technicalUsersList = dataAccessService.loadAllTechnicalUsers();
	}
	
//	private void loadDistinctScenarios(){
//		scenarioList = dataAccessService.loadDistinctScenarios();
//	}
	
	public void loadSelectedTechnical(TechnicalUsers techItem) {
		technicalUser = techItem;
//		scenario = (HrScenario) dataAccessService.findEntityById(HrScenario.class, techItem.getModelId());
//		scenario.setScenarioLabel(techItem.getScenarioLabel());
//		removedSteps = new ArrayList<>();
//		addMode = false;
//		Collections.sort(scenario.getDocuments());
	}
	
	public void addTechnicalUser(){
		technicalUser = new TechnicalUsers();
	}
	
	public void removeTechnicalUser(TechnicalUsers techItem) {
		try {
			dataAccessService.deleteObject(techItem);
			technicalUsersList.remove(techItem);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("removeTechnicalUser techId	:" + techItem.getTechnicalId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("removeTechnicalUser	" + e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		
	}
	
	public void saveTechnicalUserAction(){
		try {
			dataAccessService.save(technicalUser);
			technicalUser = (TechnicalUsers) dataAccessService.findEntityById(TechnicalUsers.class, technicalUser.getTechnicalId());
			technicalUsersList.add(technicalUser);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("saveTechnicalUserAction techId	:" + technicalUser.getTechnicalId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("saveTechnicalUserAction	" + e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<TechnicalUsers> getTechnicalUsersList() {
		return technicalUsersList;
	}

	public void setTechnicalUsersList(List<TechnicalUsers> technicalUsersList) {
		this.technicalUsersList = technicalUsersList;
	}

	public List<TechnicalUsers> getFilteredTechnicalUsersList() {
		return filteredTechnicalUsersList;
	}

	public void setFilteredTechnicalUsersList(List<TechnicalUsers> filteredTechnicalUsersList) {
		this.filteredTechnicalUsersList = filteredTechnicalUsersList;
	}

	public TechnicalUsers getTechnicalUser() {
		return technicalUser;
	}

	public void setTechnicalUser(TechnicalUsers technicalUser) {
		this.technicalUser = technicalUser;
	}
}
