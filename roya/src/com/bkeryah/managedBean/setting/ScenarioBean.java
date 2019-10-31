package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.ArcApplicationType;
import com.bkeryah.entities.HrScenario;
import com.bkeryah.entities.HrsScenarioDocument;
import com.bkeryah.entities.SysProperties;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ScenarioBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<HrScenario>scenarioList;
	private List<HrScenario>filteredScenarioList;
	private List<HrsScenarioDocument> removedSteps;
	private HrScenario scenario;
	private boolean addMode;
	private List<ArcApplicationType> modelList;
	private List<SysProperties> propertiesList;
	protected static final Logger logger = Logger.getLogger(ScenarioBean.class);

	@PostConstruct
	public void init() {
		loadDistinctScenarios();;
		propertiesList = dataAccessService.loadScenarioSysProperties();
	}
	
	private void loadDistinctScenarios(){
		scenarioList = dataAccessService.loadDistinctScenarios();
	}
	
	public void loadSelectedScenario(HrScenario selectedScenario) {
		scenario = (HrScenario) dataAccessService.findEntityById(HrScenario.class, selectedScenario.getModelId());
		scenario.setScenarioLabel(selectedScenario.getScenarioLabel());
		removedSteps = new ArrayList<>();
		addMode = false;
		Collections.sort(scenario.getDocuments());
	}
	
	public void addScenario(){
		modelList = dataAccessService.loadNewScenarioModels();
		scenario = new HrScenario();
		scenario.setDocuments(new ArrayList<HrsScenarioDocument>());
		addMode = true;
	}
	
	public void addStep(){
		HrsScenarioDocument stepScen = new HrsScenarioDocument();
		stepScen.setStepId(scenario.getDocuments().size()+1);
		if(!scenario.getDocuments().isEmpty())
			stepScen.setFromId(scenario.getDocuments().get(scenario.getDocuments().size()-1).getToId());
		stepScen.setToId(propertiesList.get(0).getId());
		scenario.getDocuments().add(stepScen);
	}
	
	public void removeStep(HrsScenarioDocument scenarioDocItem) {
		int index = scenario.getDocuments().indexOf(scenarioDocItem);
		scenario.getDocuments().remove(scenarioDocItem);
		removedSteps.add(scenarioDocItem);
		for(int i=index;i<scenario.getDocuments().size();i++){
			scenario.getDocuments().get(i).setStepId(i+1);
		}
	}
	
	public void removeScenario(HrScenario scenarioItem) {
		try {
			scenario = (HrScenario) dataAccessService.findEntityById(HrScenario.class, scenarioItem.getModelId());
			dataAccessService.deleteScenario(scenario);
			scenarioList.remove(scenarioItem);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("removeScenario	" + e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		
	}
	
	public void saveScenarioAction(){
		if(addMode){
			dataAccessService.saveScenario(scenario);
		}else
			dataAccessService.updateScenario(scenario, removedSteps);
		loadDistinctScenarios();
	}
	
	public void onRowEdit(RowEditEvent event) {
//		Integer articleIdParam =  ((HrsScenarioDocument)event.getObject()).get;
    }
     
    public void onRowCancel(RowEditEvent event) {
    	
    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<HrScenario> getScenarioList() {
		return scenarioList;
	}

	public void setScenarioList(List<HrScenario> scenarioList) {
		this.scenarioList = scenarioList;
	}

	public List<HrScenario> getFilteredScenarioList() {
		return filteredScenarioList;
	}

	public void setFilteredScenarioList(List<HrScenario> filteredScenarioList) {
		this.filteredScenarioList = filteredScenarioList;
	}

	public HrScenario getScenario() {
		return scenario;
	}

	public void setScenario(HrScenario scenario) {
		this.scenario = scenario;
	}

	public List<HrsScenarioDocument> getRemovedSteps() {
		return removedSteps;
	}

	public void setRemovedSteps(List<HrsScenarioDocument> removedSteps) {
		this.removedSteps = removedSteps;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public List<ArcApplicationType> getModelList() {
		return modelList;
	}

	public void setModelList(List<ArcApplicationType> modelList) {
		this.modelList = modelList;
	}

	public List<SysProperties> getPropertiesList() {
		return propertiesList;
	}

	public void setPropertiesList(List<SysProperties> propertiesList) {
		this.propertiesList = propertiesList;
	}

}
