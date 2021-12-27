package com.bkeryah.managedBean.param;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;

import com.bkeryah.entities.Project;
import com.bkeryah.entities.SysCategoryEmployer;
import com.bkeryah.entities.UserRoles;
import com.bkeryah.entities.VacationsType;
import com.bkeryah.entities.WrkCommentType;
import com.bkeryah.entities.WrkLetterFrom;
import com.bkeryah.entities.WrkLetterTo;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.fng.entities.FngStatusAbsence;
import com.bkeryah.fng.entities.FngTypeAbsence;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class SettingsDataBean {
	private static Logger logger = LogManager.getLogger(SettingsDataBean.class);
	private boolean addMode;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private String operation;
	/**
	 * Letter from
	 */
	private List<WrkLetterFrom> letterFromList;
	private List<WrkLetterFrom> filteredLetterFromList;
	private WrkLetterFrom letterFrom;
	/**
	 * Letter to
	 */
	private List<WrkLetterTo> letterToList;
	private List<WrkLetterTo> filteredLetterToList;
	private WrkLetterTo letterTo;
	/**
	 * comment type
	 */
	private List<WrkCommentType> commentTypeList;
	private List<WrkCommentType> filteredCommentTypeList;
	private WrkCommentType commentType;
	/**
	 * Comment purpose
	 */
	private List<WrkPurpose> purposeList;
	private List<WrkPurpose> filteredPurposeList;
	private WrkPurpose purpose;
	/**
	 * category employer
	 */
	private List<SysCategoryEmployer> categoryEmployerList;
	private List<SysCategoryEmployer> filteredCategoryEmployerList;
	private SysCategoryEmployer categoryEmployer;
	/**
	 * vacation type
	 */
	private List<VacationsType> vacationTypeList;
	private List<VacationsType> filteredVacationTypeList;
	private VacationsType vacationType;
	/**
	 * absence status
	 */
	private List<FngStatusAbsence> absenceStatusList;
	private List<FngStatusAbsence> filteredAbsenceStatusList;
	private FngStatusAbsence absenceStatus;
	/**
	 * absence type
	 */
	private List<FngTypeAbsence> absenceTypeList;
	private List<FngTypeAbsence> filteredAbsenceTypeList;
	private FngTypeAbsence absenceType;
	/**
	 * user roles
	 */
	private List<UserRoles> rolesList;
	private List<UserRoles> filteredRolesList;
	private UserRoles role;
	
	public static final String LETTER_FROM = "LETTER_FROM";
	public static final String LETTER_TO = "LETTER_TO";
	public static final String COMMENT_TYPE = "COMMENT_TYPE";
	public static final String PURPOSE = "PURPOSE";
	public static final String CATEGORY_EMPLOYER = "CATEGORY_EMPLOYER";
	public static final String VACATION_TYPE = "VACATION_TYPE";
	public static final String ABSENCE_STATUS = "ABSENCE_STATUS";
	public static final String ABSENCE_TYPE = "ABSENCE_TYPE";
	public static final String ROLE_DESC = "ROLE_DESC";
	
	public void defineOperation(String operation) {
		this.operation = operation;
		addMode = false;
		switch (operation) {
		case LETTER_FROM:
			letterFromList = dataAccessService.loadAllWrkLetterFrom();
			break;
		case LETTER_TO:
			letterToList = dataAccessService.loadAllWrkLetterTo();
			break;
		case COMMENT_TYPE:
			commentTypeList = dataAccessService.loadAllCommentTypes();
			break;
		case PURPOSE:
			purposeList = dataAccessService.loadAllPurposes();
			break;
		case CATEGORY_EMPLOYER:
			categoryEmployerList = dataAccessService.loadAllCategoryEmployers();
			break;
		case VACATION_TYPE:
			vacationTypeList = dataAccessService.loadAllVacationTypes();
			break;
		case ABSENCE_TYPE:
			absenceTypeList = dataAccessService.loadAllAbsenceTypes();
			break;
		case ABSENCE_STATUS:
			absenceStatusList = dataAccessService.loadAllAbsenceStatus();
			break;
		case ROLE_DESC:
			rolesList = dataAccessService.loadAllRoles();
			break;
		default:
			
			break;
		}			
	}

	public void addLetterFrom() {
		letterFrom = new WrkLetterFrom();
		addMode = true;
	}
	
	public void addLetterTo() {
		letterTo = new WrkLetterTo();
		addMode = true;
	}
	
	public void addCommentType() {
		commentType = new WrkCommentType();
		addMode = true;
	}
	
	public void addPurpose() {
		purpose = new WrkPurpose();
		addMode = true;
	}
	
	public void addCategoryEmployer() {
		categoryEmployer = new SysCategoryEmployer();
		addMode = true;
	}
	
	public void addVacationType() {
		vacationType = new VacationsType();
		addMode = true;
	}
	
	public void addAbsenceType() {
		absenceType = new FngTypeAbsence();
		addMode = true;
	}
	
	public void addAbsenceStatus() {
		absenceStatus = new FngStatusAbsence();
		addMode = true;
	}
	
	public void addRole() {
		role = new UserRoles();
		addMode = true;
	}

	public void loadSelectedObject() {
		addMode = false;
	}

	public void save() {
		try {
			switch (operation) {
			case LETTER_FROM:
				dataAccessService.saveObject(letterFrom);
				letterFromList.add(letterFrom);
				letterFrom = new WrkLetterFrom();
				logger.info("add letterFrom: id: " + letterFrom.getId());
				break;
			case LETTER_TO:
				dataAccessService.saveObject(letterTo);
				letterToList.add(letterTo);
				letterTo = new WrkLetterTo();
				logger.info("add letterTo: id: " + letterTo.getId());
				break;
			case COMMENT_TYPE:
				dataAccessService.saveObject(commentType);
				commentTypeList.add(commentType);
				commentType = new WrkCommentType();
				logger.info("add commentType: id: " + commentType.getId());
				break;
			case PURPOSE:
				dataAccessService.saveObject(purpose);
				purposeList.add(purpose);
				purpose = new WrkPurpose();
				logger.info("add purpose: id: " + purpose.getId());
				break;
			case CATEGORY_EMPLOYER:
				dataAccessService.saveObject(categoryEmployer);
				categoryEmployerList.add(categoryEmployer);
				categoryEmployer = new SysCategoryEmployer();
				logger.info("add categoryEmployer: id: " + categoryEmployer.getId());
				break;
			case VACATION_TYPE:
				dataAccessService.saveObject(vacationType);
				vacationTypeList.add(vacationType);
				vacationType = new VacationsType();
				logger.info("add vacationType: id: " + vacationType.getId());
				break;
			case ABSENCE_TYPE:
				dataAccessService.saveObject(absenceType);
				absenceTypeList.add(absenceType);
				absenceType = new FngTypeAbsence();
				logger.info("add absenceType: id: " + absenceType.getId());
				break;
			case ABSENCE_STATUS:
				dataAccessService.saveObject(absenceStatus);
				absenceStatusList.add(absenceStatus);
				absenceStatus = new FngStatusAbsence();
				logger.info("add absenceStatus: id: " + absenceStatus.getId());
				break;
			case ROLE_DESC:
				dataAccessService.saveObject(role);
				rolesList.add(role);
				role = new UserRoles();
				logger.info("add role: id: " + role.getId());
				break;
			default:
				break;
			} 	
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			switch (operation) {
			case LETTER_FROM:
				dataAccessService.updateObject(letterFrom);
				letterFrom = new WrkLetterFrom();
				logger.info("Update letterFrom: id: " + letterFrom.getId());
				break;
			case LETTER_TO:
				dataAccessService.updateObject(letterTo);
				letterTo = new WrkLetterTo();
				logger.info("Update letterTo: id: " + letterTo.getId());
				break;
			case COMMENT_TYPE:
				dataAccessService.updateObject(commentType);
				commentType = new WrkCommentType();
				logger.info("Update commentType: id: " + commentType.getId());
				break;
			case PURPOSE:
				dataAccessService.updateObject(purpose);
				purpose = new WrkPurpose();
				logger.info("Update purpose: id: " + purpose.getId());
				break;
			case CATEGORY_EMPLOYER:
				dataAccessService.updateObject(categoryEmployer);
				categoryEmployer = new SysCategoryEmployer();
				logger.info("Update categoryEmployer: id: " + categoryEmployer.getId());
				break;
			case VACATION_TYPE:
				dataAccessService.updateObject(vacationType);
				vacationType = new VacationsType();
				logger.info("Update vacationType: id: " + vacationType.getId());
				break;
			case ABSENCE_TYPE:
				dataAccessService.updateObject(absenceType);
				absenceType = new FngTypeAbsence();
				logger.info("Update absenceType: id: " + absenceType.getId());
				break;
			case ABSENCE_STATUS:
				dataAccessService.updateObject(absenceStatus);
				absenceStatus = new FngStatusAbsence();
				logger.info("Update absenceStatus: id: " + absenceStatus.getId());
				break;
			case ROLE_DESC:
				dataAccessService.updateObject(role);
				role = new UserRoles();
				logger.info("Update role: id: " + role.getId());
				break;
			default:
				break;
			} 	
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		SettingsDataBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public List<WrkLetterFrom> getLetterFromList() {
		return letterFromList;
	}

	public void setLetterFromList(List<WrkLetterFrom> letterFromList) {
		this.letterFromList = letterFromList;
	}

	public List<WrkLetterFrom> getFilteredLetterFromList() {
		return filteredLetterFromList;
	}

	public void setFilteredLetterFromList(List<WrkLetterFrom> filteredLetterFromList) {
		this.filteredLetterFromList = filteredLetterFromList;
	}

	public WrkLetterFrom getLetterFrom() {
		return letterFrom;
	}

	public void setLetterFrom(WrkLetterFrom letterFrom) {
		this.letterFrom = letterFrom;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public List<WrkLetterTo> getLetterToList() {
		return letterToList;
	}

	public void setLetterToList(List<WrkLetterTo> letterToList) {
		this.letterToList = letterToList;
	}

	public List<WrkLetterTo> getFilteredLetterToList() {
		return filteredLetterToList;
	}

	public void setFilteredLetterToList(List<WrkLetterTo> filteredLetterToList) {
		this.filteredLetterToList = filteredLetterToList;
	}

	public WrkLetterTo getLetterTo() {
		return letterTo;
	}

	public void setLetterTo(WrkLetterTo letterTo) {
		this.letterTo = letterTo;
	}

	public List<WrkCommentType> getCommentTypeList() {
		return commentTypeList;
	}

	public void setCommentTypeList(List<WrkCommentType> commentTypeList) {
		this.commentTypeList = commentTypeList;
	}

	public List<WrkCommentType> getFilteredCommentTypeList() {
		return filteredCommentTypeList;
	}

	public void setFilteredCommentTypeList(List<WrkCommentType> filteredCommentTypeList) {
		this.filteredCommentTypeList = filteredCommentTypeList;
	}

	public WrkCommentType getCommentType() {
		return commentType;
	}

	public void setCommentType(WrkCommentType commentType) {
		this.commentType = commentType;
	}

	public List<WrkPurpose> getPurposeList() {
		return purposeList;
	}

	public void setPurposeList(List<WrkPurpose> purposeList) {
		this.purposeList = purposeList;
	}

	public List<WrkPurpose> getFilteredPurposeList() {
		return filteredPurposeList;
	}

	public void setFilteredPurposeList(List<WrkPurpose> filteredPurposeList) {
		this.filteredPurposeList = filteredPurposeList;
	}

	public WrkPurpose getPurpose() {
		return purpose;
	}

	public void setPurpose(WrkPurpose purpose) {
		this.purpose = purpose;
	}

	public List<SysCategoryEmployer> getCategoryEmployerList() {
		return categoryEmployerList;
	}

	public void setCategoryEmployerList(List<SysCategoryEmployer> categoryEmployerList) {
		this.categoryEmployerList = categoryEmployerList;
	}

	public List<SysCategoryEmployer> getFilteredCategoryEmployerList() {
		return filteredCategoryEmployerList;
	}

	public void setFilteredCategoryEmployerList(List<SysCategoryEmployer> filteredCategoryEmployerList) {
		this.filteredCategoryEmployerList = filteredCategoryEmployerList;
	}

	public SysCategoryEmployer getCategoryEmployer() {
		return categoryEmployer;
	}

	public void setCategoryEmployer(SysCategoryEmployer categoryEmployer) {
		this.categoryEmployer = categoryEmployer;
	}

	public List<VacationsType> getVacationTypeList() {
		return vacationTypeList;
	}

	public void setVacationTypeList(List<VacationsType> vacationTypeList) {
		this.vacationTypeList = vacationTypeList;
	}

	public List<VacationsType> getFilteredVacationTypeList() {
		return filteredVacationTypeList;
	}

	public void setFilteredVacationTypeList(List<VacationsType> filteredVacationTypeList) {
		this.filteredVacationTypeList = filteredVacationTypeList;
	}

	public VacationsType getVacationType() {
		return vacationType;
	}

	public void setVacationType(VacationsType vacationType) {
		this.vacationType = vacationType;
	}

	public List<FngStatusAbsence> getAbsenceStatusList() {
		return absenceStatusList;
	}

	public void setAbsenceStatusList(List<FngStatusAbsence> absenceStatusList) {
		this.absenceStatusList = absenceStatusList;
	}

	public FngStatusAbsence getAbsenceStatus() {
		return absenceStatus;
	}

	public void setAbsenceStatus(FngStatusAbsence absenceStatus) {
		this.absenceStatus = absenceStatus;
	}

	public List<FngTypeAbsence> getAbsenceTypeList() {
		return absenceTypeList;
	}

	public void setAbsenceTypeList(List<FngTypeAbsence> absenceTypeList) {
		this.absenceTypeList = absenceTypeList;
	}

	public List<FngTypeAbsence> getFilteredAbsenceTypeList() {
		return filteredAbsenceTypeList;
	}

	public void setFilteredAbsenceTypeList(List<FngTypeAbsence> filteredAbsenceTypeList) {
		this.filteredAbsenceTypeList = filteredAbsenceTypeList;
	}

	public FngTypeAbsence getAbsenceType() {
		return absenceType;
	}

	public void setAbsenceType(FngTypeAbsence absenceType) {
		this.absenceType = absenceType;
	}

	public List<FngStatusAbsence> getFilteredAbsenceStatusList() {
		return filteredAbsenceStatusList;
	}

	public void setFilteredAbsenceStatusList(List<FngStatusAbsence> filteredAbsenceStatusList) {
		this.filteredAbsenceStatusList = filteredAbsenceStatusList;
	}

	public List<UserRoles> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<UserRoles> rolesList) {
		this.rolesList = rolesList;
	}

	public List<UserRoles> getFilteredRolesList() {
		return filteredRolesList;
	}

	public void setFilteredRolesList(List<UserRoles> filteredRolesList) {
		this.filteredRolesList = filteredRolesList;
	}

	public UserRoles getRole() {
		return role;
	}

	public void setRole(UserRoles role) {
		this.role = role;
	}

}
