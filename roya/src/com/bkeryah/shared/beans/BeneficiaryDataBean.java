package com.bkeryah.shared.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.bkeryah.entities.ArcPeople;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class BeneficiaryDataBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ArcPeople peopleInfo = new ArcPeople();
//	private List<NationalIdType> nationalityTypeList = new ArrayList<>();
//	private List<Nationality> nationalityList = new ArrayList<>();
	private String paperTypId;
	private String nationalityId;
	private List<ArcPeople> peoplesList;
    private List<ArcPeople> filteredPeoples;
    private boolean addMode;
//	private List<NationalIdPlaces> nationalIdPlacesList = new ArrayList<>();

//	@PostConstruct
//	public void init() {
//		nationalityTypeList = dataAccessService.getAllNationalIdTypes();
//		nationalityList = dataAccessService.getallNationalities();
//		nationalIdPlacesList = dataAccessService.getallNationalIdPlaces();
//
//	}
	
	public void addPeople(){
		addMode = true;
		peopleInfo = new ArcPeople();
    }
	
	public void loadPeople(ArcPeople userItem){
		addMode = false;
		peopleInfo = dataAccessService.loadArcPeople(userItem.getNationalId());
		paperTypId = ""+peopleInfo.getPaperType();
//		nationalityId = ""+peopleInfo.getNationalityId();
    }

	public void loadPeoples(){
		dataAccessService = null;
		peoplesList = dataAccessService.loadArcPeopleFields();
	}
	
	public void activeFields() {
		addMode = true;
	}

	public void save() {
//		peopleInfo.setPaperType(Integer.parseInt(paperTypId.trim()));
//		peopleInfo.setNationalityId(Integer.parseInt(nationalityId.trim()));
		try {
			
			peopleInfo.setPaperType((Integer)Integer.parseInt(paperTypId.trim()));
			
			dataAccessService.addArcPeople(peopleInfo);
			nationalityId = "";
			paperTypId = "";
			peopleInfo = new ArcPeople();
			RequestContext.getCurrentInstance().execute("PF('addDialog').hide();");
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}
	
	public void update() {
//		peopleInfo.setPaperType(Integer.parseInt(paperTypId.trim()));
//		peopleInfo.setNationalityId(Integer.parseInt(nationalityId.trim()));
		try {
			dataAccessService.updateObject(peopleInfo);
			nationalityId = "";
			paperTypId = "";
			peopleInfo = new ArcPeople();
			RequestContext.getCurrentInstance().execute("PF('addDialog').hide();");
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public ArcPeople getPeopleInfo() {
		return peopleInfo;
	}

	public void setPeopleInfo(ArcPeople peopleInfo) {
		this.peopleInfo = peopleInfo;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public String getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getPaperTypId() {
		return paperTypId;
	}

	public void setPaperTypId(String paperTypId) {
		this.paperTypId = paperTypId;
	}

	public List<ArcPeople> getPeoplesList() {
		return peoplesList;
	}

	public void setPeoplesList(List<ArcPeople> peoplesList) {
		this.peoplesList = peoplesList;
	}

	public List<ArcPeople> getFilteredPeoples() {
		return filteredPeoples;
	}

	public void setFilteredPeoples(List<ArcPeople> filteredPeoples) {
		this.filteredPeoples = filteredPeoples;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}
}
