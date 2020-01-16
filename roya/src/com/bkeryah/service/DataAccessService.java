package com.bkeryah.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bkeryah.bean.ArcApplicationTypeClass;
import com.bkeryah.bean.ArcAttachmentClass;
import com.bkeryah.bean.ArcRecordClass;
import com.bkeryah.bean.AttachmentFileClass;
import com.bkeryah.bean.CommentClass;
import com.bkeryah.bean.EmpVac;
import com.bkeryah.bean.InventoryModel;
import com.bkeryah.bean.Main_menu_class;
import com.bkeryah.bean.ReferralSettingClass;
import com.bkeryah.bean.SpecialAddressClass;
import com.bkeryah.bean.StoreRequestModel;
import com.bkeryah.bean.Sub_menu_class;
import com.bkeryah.bean.SysTitleClass;
import com.bkeryah.bean.TrdModelClass;
import com.bkeryah.bean.UserClass;
import com.bkeryah.bean.UserContactClass;
import com.bkeryah.bean.UserFolderClass;
import com.bkeryah.bean.UserMailObj;
import com.bkeryah.bean.WrkArchiveRecipentClass;
import com.bkeryah.bean.WrkChargingClass;
import com.bkeryah.bean.WrkCommentsClass;
import com.bkeryah.bean.WrkDeptClass;
import com.bkeryah.bean.WrkJobClass;
import com.bkeryah.bean.WrkLetterFromClass;
import com.bkeryah.bean.WrkLetterToClass;
import com.bkeryah.bean.WrkPurposeClass;
import com.bkeryah.bean.WrkRolesClass;
import com.bkeryah.bean.WrkSectionClass;
import com.bkeryah.dao.DataAccess;
import com.bkeryah.dao.DataBaseConnectionClass;
import com.bkeryah.dao.ICommonDao;
import com.bkeryah.entities.*;
import com.bkeryah.entities.investment.AnncementNews;
import com.bkeryah.entities.investment.AnnoucementDetails;
import com.bkeryah.entities.investment.Announcement;
import com.bkeryah.entities.investment.BuildingType;
import com.bkeryah.entities.investment.Clause;
import com.bkeryah.entities.investment.Contract;
import com.bkeryah.entities.investment.ContractCancelReason;
import com.bkeryah.entities.investment.ContractDirect;
import com.bkeryah.entities.investment.ContractDirectType;
import com.bkeryah.entities.investment.ContractMainCategory;
import com.bkeryah.entities.investment.ContractStatus;
import com.bkeryah.entities.investment.ContractSubcategory;
import com.bkeryah.entities.investment.ContractType;
import com.bkeryah.entities.investment.IntroContract;
import com.bkeryah.entities.investment.InvNewspaper;
import com.bkeryah.entities.investment.Investor;
import com.bkeryah.entities.investment.InvestorIdentityType;
import com.bkeryah.entities.investment.InvestorStatus;
import com.bkeryah.entities.investment.InvestorType;
import com.bkeryah.entities.investment.RealEstate;
import com.bkeryah.entities.investment.SiteType;
import com.bkeryah.entities.investment.Tender;
import com.bkeryah.entities.licences.BldLicAttch;
import com.bkeryah.entities.licences.BldLicBuildingUsage;
import com.bkeryah.entities.licences.BldLicHangover;
import com.bkeryah.entities.licences.BldLicMasterTbl;
import com.bkeryah.entities.licences.BldLicNew;
import com.bkeryah.entities.licences.BldLicPcs;
import com.bkeryah.entities.licences.BldLicWall;
import com.bkeryah.entities.licences.BldPaperTypes;
import com.bkeryah.entities.licences.LicAgents;
import com.bkeryah.entities.licences.LicenceTypeEnum;
import com.bkeryah.fng.entities.AutorizationSettings;
import com.bkeryah.fng.entities.FngEmpAbsent;
import com.bkeryah.fng.entities.FngStatusAbsence;
import com.bkeryah.fng.entities.FngTimeTable;
import com.bkeryah.fng.entities.FngTypeAbsence;
import com.bkeryah.fng.entities.FngUserTempShift;
import com.bkeryah.fng.entities.TstFinger;
import com.bkeryah.fng.entities.TstFingerId;
import com.bkeryah.fuel.entities.Car;
import com.bkeryah.fuel.entities.CarBrand;
import com.bkeryah.fuel.entities.CarModel;
import com.bkeryah.fuel.entities.FuelSupply;
import com.bkeryah.fuel.entities.FuelTransaction;
import com.bkeryah.fuel.entities.FuelType;
import com.bkeryah.fuel.entities.UserCars;
import com.bkeryah.fuel.entities.VehicleType;
import com.bkeryah.hr.entities.CompensatoryVacStock;
import com.bkeryah.hr.entities.EmpMoveType;
import com.bkeryah.hr.entities.HrsAppreciationScale;
import com.bkeryah.hr.entities.HrsCompactBaseFloor;
import com.bkeryah.hr.entities.HrsCompactCatFloor;
import com.bkeryah.hr.entities.HrsCompactEmpCaracter;
import com.bkeryah.hr.entities.HrsCompactFloors;
import com.bkeryah.hr.entities.HrsCompactGoals;
import com.bkeryah.hr.entities.HrsCompactPerformance;
import com.bkeryah.hr.entities.HrsCompactRating;
import com.bkeryah.hr.entities.HrsEmpOvertime;
import com.bkeryah.hr.entities.HrsFloors;
import com.bkeryah.hr.entities.HrsGeneralAppreciation;
import com.bkeryah.hr.entities.HrsGovJobSeries;
import com.bkeryah.hr.entities.HrsGovJobType;
import com.bkeryah.hr.entities.HrsGovJobWrks;
import com.bkeryah.hr.entities.HrsJobCreation;
import com.bkeryah.hr.entities.HrsLoan;
import com.bkeryah.hr.entities.HrsLoanDetails;
import com.bkeryah.hr.entities.HrsLoanType;
import com.bkeryah.hr.entities.HrsSalary;
import com.bkeryah.hr.entities.HrsSalaryScaleOrder;
import com.bkeryah.hr.entities.HrsSumVacation;
import com.bkeryah.hr.entities.HrsVacSold;
import com.bkeryah.hr.entities.HrsYearsPrime;
import com.bkeryah.hr.entities.Sys012;
import com.bkeryah.hr.entities.Sys018;
import com.bkeryah.hr.entities.Sys037;
import com.bkeryah.hr.entities.Sys038;
import com.bkeryah.hr.entities.Sys051;
import com.bkeryah.hr.entities.Sys059;
import com.bkeryah.hr.entities.Sys112;
import com.bkeryah.hr.entities.VacCompensatoryDays;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.licences.models.BldLicNewModel;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.model.AbsentModel;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.model.DashbordModel;
import com.bkeryah.model.LoanModel;
import com.bkeryah.model.MemoReceiptModel;
import com.bkeryah.model.RetirementModel;
import com.bkeryah.model.User;
import com.bkeryah.model.VacationModel;
import com.bkeryah.penalties.LicTrdMasterFile;
import com.bkeryah.penalties.NotifFinesDetails;
import com.bkeryah.penalties.NotifFinesMaster;
import com.bkeryah.penalties.ReqFinesDetails;
import com.bkeryah.penalties.ReqFinesMaster;
import com.bkeryah.penalties.ReqFinesSetup;
import com.bkeryah.penalties.WrkFinesEntity;
import com.bkeryah.support.entities.RequestStatus;
import com.bkeryah.support.entities.RequestStep;
import com.bkeryah.support.entities.UserRequest;
import com.bkeryah.testssss.EmployeeForDropDown;

import customeExceptions.VacationAndInitException;
import oracle.jdbc.OracleTypes;
import utilities.FtpTransferFile;
import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

/**
 * @author amer
 *
 */

// @Transactional(readOnly = true)
public class DataAccessService implements IDataAccessService {

	private DataAccess dataAccessDAO;
	
	private ICommonDao commonDao;
	// private HijriCalendarUtil hijri = new HijriCalendarUtil();

	protected static final Logger logger = Logger.getLogger(DataAccessService.class);

	public DataAccess getDataAccessDAO() {
		return dataAccessDAO;
	}

	public DataAccessService() {

	}

	public void setDataAccessDAO(DataAccess dataAccessDAO) {
		this.dataAccessDAO = dataAccessDAO;
	}

	@Override
	public List<UserClass> findAllUsers() {
		return dataAccessDAO.findAllUsers();
	}

	// @Override
	// public UserClass login(String username, String passWord) {
	// return dataAccessDAO.login(username, passWord);
	// }

	@Override
	public ArcUsers loadUser(String username, String passWord) {
		return commonDao.loadUser(username, passWord);
	}

	@Override
	public String findSystemProperty(String propertyName) {
		return dataAccessDAO.findSystemProperty(propertyName);
	}

	// @Override
	// public UserClass findCurrentUser() {
	// return dataAccessDAO.findCurrentUser();
	// }

	@Override
	public List<UserFolderClass> findAllUserFolders(int vuser_id) {
		return dataAccessDAO.findAllUserFolders(vuser_id);
	}

	@Override
	public List<UserContactClass> findInboxSenderList(int vuser_id) {
		return dataAccessDAO.findInboxSenderList(vuser_id);
	}

	@Override
	public String findAllowedFilesTypes(String fileTypes) {
		return dataAccessDAO.findAllowedFilesTypes(fileTypes);
	}

	@Override
	public void wrkTransferApplication(String wrkId, String arcRecordId, int fromEmp, int toEmp, String comment,
			int purp) {
		dataAccessDAO.wrkTransferApplication(wrkId, arcRecordId, fromEmp, toEmp, comment, purp);
	}

	@Override
	public void sendRecordSingleCopy(String ArcRecordId, String WrkApplicationId, int FromUserId, int ToUserId,
			String LongComment, int purposeId, String AttachedExplain, String Title, int stepId) {
		dataAccessDAO.sendRecordSingleCopy(ArcRecordId, WrkApplicationId, FromUserId, ToUserId, LongComment, purposeId,
				AttachedExplain, Title, stepId);
	}

	@Override
	public void wrkSaveComment(int UserId, String WrkAppId, String AppNo, String AppDate, int AppPapers, String AppTo,
			String AppSubject, String AppAttach, String AppLongComment, String FirstCopy, String SecondCopy,
			int FontSize, int CommentType, int CommentWroteBy, String commentWroteIn) {
		dataAccessDAO.wrkSaveComment(UserId, WrkAppId, AppNo, AppDate, AppPapers, AppTo, AppSubject, AppAttach,
				AppLongComment, FirstCopy, SecondCopy, FontSize, CommentType, CommentWroteBy, commentWroteIn);
	}

	@Override
	public String getEmployeeManagerId(String EmployeeId) {
		return dataAccessDAO.getEmployeeManagerId(EmployeeId);
	}

	@Override
	public String CreateNewWrkComment(int UserId, String PAppSubject, int ToId, int AppPurpose, String AppTo,
			String AppLongComment, String AppSecondCopy, int AppCommentType, String AppFirstCopy, int AppPapers) {
		return dataAccessDAO.CreateNewWrkComment(UserId, PAppSubject, ToId, AppPurpose, AppTo, AppLongComment,
				AppSecondCopy, AppCommentType, AppFirstCopy, AppPapers);
	}

	@Override
	public String transferAttachment(InputStream inputstream, String fileName, int vuser_id, String arcRecordIdOutPut) {
		return dataAccessDAO.transferAttachment(inputstream, fileName, vuser_id, arcRecordIdOutPut);
	}

	@Override
	public List<UserContactClass> findCommentSignEmployeesList(int vuser_id, String wrkId) {
		return dataAccessDAO.findCommentSignEmployeesList(vuser_id, wrkId);
	}

	@Override
	public boolean CheckSignturePassword(int vuser_id, String confirmPassword) {
		return dataAccessDAO.CheckSignturePassword(vuser_id, confirmPassword);
	}

	@Override
	public void SignWrkComment(int UserId, String WrkApplicationId, int WrkApplicationStepId, String WrkCommentAppId,
			String ConfirmPassword, String Signtype, int SentToUserId) {
		dataAccessDAO.SignWrkComment(UserId, WrkApplicationId, WrkApplicationStepId, WrkCommentAppId, ConfirmPassword,
				Signtype, SentToUserId);

	}

	@Override
	public void sendCommentCopy(int vuser_id, int ToId, String WrkCommentAppId, String ArcRecordId) {
		dataAccessDAO.sendCommentCopy(vuser_id, ToId, WrkCommentAppId, ArcRecordId);
	}

	@Override
	@Transactional
	public List<WrkCommentType> findAllCommentTypes() {
		return commonDao.findAllCommentTypes();
	}

	@Override
	public List<WrkPurposeClass> findAllWrkPurpose(Integer puserid) {
		return dataAccessDAO.findAllWrkPurpose(puserid);
	}

	@Override
	public List<ArcAttachmentClass> findAttachmentFilesByArcId(String ArcId) {
		return dataAccessDAO.findAttachmentFilesByArcId(ArcId);
	}

	@Override
	public List<UserContactClass> findAllManagers(int vuser_id) {
		return dataAccessDAO.findAllManagers(vuser_id);
	}

	@Override
	public List<UserContactClass> findAllEmployeesInDept(int vuser_id) {
		return dataAccessDAO.findAllEmployeesInDept(vuser_id);
	}

	@Override
	public Employer getReturnVacData(int vuser_id) {
		return dataAccessDAO.getReturnVacData(vuser_id);
	}

	@Override
	public List<UserMailObj> findEmployeeInbox(int vuser_id) {
		return dataAccessDAO.findEmployeeInbox(null, null, vuser_id);
	}

	@Override
	public List<UserMailObj> findEmployeeOutbox(int vuser_id, String inDate, int partOfYear) {
		return dataAccessDAO.findEmployeeOutbox(vuser_id, inDate, partOfYear);
	}

	@Override
	public int EmployeeArchivedArch(int vuser_id) {
		return dataAccessDAO.EmployeeArchivedArch(vuser_id);
	}

	@Override
	public List<WrkCommentsClass> findCommentsByWrkId(String wrkId) {
		return dataAccessDAO.findCommentsByWrkId(wrkId);
	}

	@Override
	public CommentClass getWrkCommentInfoByWrkId(String wrkId) {
		return dataAccessDAO.getWrkCommentInfoByWrkId(wrkId);
	}

	@Override
	public String getEmployeeRealName(int markedBy) {
		return dataAccessDAO.getEmployeeRealName(markedBy);
	}

	@Override
	public List<UserMailObj> SearchEmployeeInbox(int EmployeeUserId, int searchType, String searchKey,
			String IncludeComment) {
		return dataAccessDAO.SearchEmployeeInbox(EmployeeUserId, searchType, searchKey, IncludeComment);
	}

	@Override
	public void addRecordToFavourit(String wrkId, int stepId, int vuser_id) {
		dataAccessDAO.addRecordToFavourit(wrkId, stepId, vuser_id);
	}

	@Override
	public List<UserMailObj> findFavouritInbox(int vuser_id) {
		return dataAccessDAO.findFavouritInbox(vuser_id);
	}

	@Override
	public List<UserMailObj> findEmployeeInboxUnread(int vuser_id) {
		return dataAccessDAO.findEmployeeInboxUnread(vuser_id);
	}

	@Override
	public List<UserMailObj> findEmployeeInboxRead(int vuser_id) {
		return dataAccessDAO.findEmployeeInboxRead(vuser_id);
	}

	@Override
	public boolean WrkAppHasComment(String wrkId) {
		return dataAccessDAO.WrkAppHasComment(wrkId);
	}

	@Override
	public void markComment(String wrkId, int vuser_id, String appId) {
		dataAccessDAO.markComment(wrkId, vuser_id, appId);
	}

	@Override
	public boolean EmployeeHasSign(int vuser_id) {
		return dataAccessDAO.EmployeeHasSign(vuser_id);
	}

	@Override
	public String retrieveRecord(int vuser_id, String appId, String wrkId) {
		return dataAccessDAO.retrieveRecord(vuser_id, appId, wrkId);
	}

	@Override
	public String SendNewRecord(String ArcRecTitle, int ArcIsImportant, int UserId, int ToId, String ArcComment,
			int ArcPurp, int WrkCommIsSecret, String CreateNewIncomeNo) {
		return dataAccessDAO.SendNewRecord(ArcRecTitle, ArcIsImportant, UserId, ToId, ArcComment, ArcPurp,
				WrkCommIsSecret, CreateNewIncomeNo);
	}

	@Override
	public String SendNewRecordcopy(String ArcRecTitle, int ArcIsImportant, int UserId, int ToId, String ArcComment,
			int ArcPurp, int WrkCommIsSecret, String CreateNewIncomeNo, String OriginalArcRecordId) {
		return dataAccessDAO.SendNewRecordcopy(ArcRecTitle, ArcIsImportant, UserId, ToId, ArcComment, ArcPurp,
				WrkCommIsSecret, CreateNewIncomeNo, OriginalArcRecordId);
	}

	@Override
	public void addExistedAttachToRecord(String attachaId, String x) {
		dataAccessDAO.addExistedAttachToRecord(attachaId, x);
	}

	@Override
	public List<String> findAllWrkCommentAppTo(String x) {
		return dataAccessDAO.findAllWrkCommentAppTo(x);
	}

	@Override
	public List<UserMailObj> findEmployeeInboxBySenderId(int vuser_id, int selectedSender) {
		return dataAccessDAO.findEmployeeInboxBySenderId(vuser_id, selectedSender);
	}

	@Override
	public List<UserMailObj> findEmployeeInboxDaily(int vuser_id) {
		return dataAccessDAO.findEmployeeInboxDaily(vuser_id);
	}

	@Override
	public List<UserMailObj> findEmployeeOutboxArchive(int vuser_id) {
		return dataAccessDAO.findEmployeeOutboxArchive(vuser_id);
	}

	@Override
	public List<UserMailObj> searchEmployeeOutbox(int EmployeeUserId, String SearchKey, String IncludeArchiveYN) {
		return dataAccessDAO.searchEmployeeOutbox(EmployeeUserId, SearchKey, IncludeArchiveYN);
	}

	@Override
	public String MoveRecordToFolder(String wrkId, int stepId, int vuser_id, int FolderId) {
		return dataAccessDAO.MoveRecordToFolder(wrkId, stepId, vuser_id, FolderId);
	}

	@Override
	public List<UserMailObj> findEmployeeFolderRecords(int vuser_id, int selectedUserFolder) {
		return dataAccessDAO.findEmployeeFolderRecords(vuser_id, selectedUserFolder);
	}

	@Override
	public void MakeItRead(String vStepId, String vWrkId) {
		dataAccessDAO.MakeItRead(vStepId, vWrkId);
	}

	@Override
	public String convertTextWithArNumric(String txt) {
		return dataAccessDAO.convertTextWithArNumric(txt);
	}

	@Override
	public List<ArcApplicationTypeClass> findAllApplicationTypes() {
		return dataAccessDAO.findAllApplicationTypes();
	}

	@Override
	public List<WrkLetterFromClass> findAllWrkLetterFroms() {
		return dataAccessDAO.findAllWrkLetterFroms();
	}

	@Override
	public List<WrkLetterToClass> findAllAWrkLetterTos() {
		return dataAccessDAO.findAllAWrkLetterTos();
	}

	@Override
	public List<WrkArchiveRecipentClass> findAllWrkArchiveRecipents() {
		return dataAccessDAO.findAllWrkArchiveRecipents();
	}

	@Override
	public String CreateNewIncomeRecord(int UserId, String ArcTitle, int ArcPurp, int ArcTyp, int LetterTo,
			int LetterFrom, int ArcSendTo, String LetterFromNo, String LetterFromDate, String RefrencedLetterNo,
			String MobileNumber, String LongComment, int IsArcRecordImportant) {
		return dataAccessDAO.CreateNewIncomeRecord(UserId, ArcTitle, ArcPurp, ArcTyp, LetterTo, LetterFrom, ArcSendTo,
				LetterFromNo, LetterFromDate, RefrencedLetterNo, MobileNumber, LongComment, IsArcRecordImportant);
	}

	@Override
	public Map<String, String> findrecordsMap() {
		return dataAccessDAO.findrecordsMap();
	}

	@Override
	public String addNewExportedRecord(String IncomeNumber, int UserId) {
		return dataAccessDAO.addNewExportedRecord(IncomeNumber, UserId);
	}

	@Override
	public ArcRecordClass getRecordbyIcomeNUmber(String IncomeNumber) {
		return dataAccessDAO.getRecordbyIcomeNUmber(IncomeNumber);
	}

	@Override
	public void addNewUserFolder(String FolderName, int UserId) {
		dataAccessDAO.addNewUserFolder(FolderName, UserId);
	}

	@Override
	public String deleteUserFolder(int FolderId) {
		return dataAccessDAO.deleteUserFolder(FolderId);
	}

	@Override
	public String updateUserPassword(int UserId, String NewPassword) {
		return dataAccessDAO.updateUserPassword(UserId, NewPassword);
	}

	@Override
	public String updateUserSignPassword(int UserId, String NewPassword) {
		return dataAccessDAO.updateUserSignPassword(UserId, NewPassword);
	}

	@Override
	public List<Main_menu_class> getMenuByUser(int userId) {
		return dataAccessDAO.getMenuByUser(userId);
	}

	@Override
	public List<Main_menu_class> findAllMainMenu() {
		return dataAccessDAO.findAllMainMenu();
	}

	@Override
	public List<Sub_menu_class> findExistedSubMenuByMainMenu(int userId, int MainMenuId) {
		return dataAccessDAO.findExistedSubMenuByMainMenu(userId, MainMenuId);
	}

	@Override
	public void removeMenufromUser(int Userid, int Menuid) {
		dataAccessDAO.removeMenufromUser(Userid, Menuid);
	}

	@Override
	public void removeSubMenufromUser(int Userid, int SubMenuid) {
		dataAccessDAO.removeSubMenufromUser(Userid, SubMenuid);
	}

	@Override
	public List<Main_menu_class> findAllAvaliableMainMenu(int Userid) {
		return dataAccessDAO.findAllAvaliableMainMenu(Userid);
	}

	@Override
	public List<Sub_menu_class> findAvaliableSubMenuByMainMenu(int userId, int MainMenuId) {
		return dataAccessDAO.findAvaliableSubMenuByMainMenu(userId, MainMenuId);
	}

	@Override
	public void addMenuToUser(int Userid, int Menuid) {
		dataAccessDAO.addMenuToUser(Userid, Menuid);
	}

	@Override
	public void addSubMenuToUser(int Userid, int SubMenuid) {
		dataAccessDAO.addSubMenuToUser(Userid, SubMenuid);
	}

	@Override
	public List<ReferralSettingClass> findAllReferralSetting() {
		return dataAccessDAO.findAllReferralSetting();
	}

	@Override
	public List<SpecialAddressClass> findSpeicalAddressByUser(int pUserId) {
		return dataAccessDAO.findSpeicalAddressByUser(pUserId);
	}

	@Override
	public String addNewSpecialAddress(int pUserId, int pSpecialAddressId) {
		return dataAccessDAO.addNewSpecialAddress(pUserId, pSpecialAddressId);
	}

	@Override
	public void addNewApplicationType(String NewApplicationTypeName) {
		dataAccessDAO.addNewApplicationType(NewApplicationTypeName);
	}

	@Override
	public void addNewPurpose(String PurposeName) {
		dataAccessDAO.addNewPurpose(PurposeName);
		;
	}

	@Override
	public void addNewWrkLetterTo(String NewLetterToName) {
		dataAccessDAO.addNewWrkLetterTo(NewLetterToName);
	}

	@Override
	public String addNewReferralSetting(int pManagerId, String ManagerTitle) {
		return dataAccessDAO.addNewReferralSetting(pManagerId, ManagerTitle);
	}

	@Override
	public List<WrkDeptClass> findAllDepartment() {
		return dataAccessDAO.findAllDepartment();
	}

	@Override
	public List<WrkRolesClass> findAllRoles() {
		return dataAccessDAO.findAllRoles();
	}

	@Override
	public List<SysTitleClass> findAllSysTitles() {
		return dataAccessDAO.findAllSysTitles();
	}

	@Override
	public List<WrkSectionClass> findAllSectionsByDept(int deptId) {
		return dataAccessDAO.findAllSectionsByDept(deptId);
	}

	@Override
	public List<WrkJobClass> findAllJobsBySection(int SectionId) {
		return dataAccessDAO.findAllJobsBySection(SectionId);
	}

	@Override
	public int createNewUser(String LoginName, String Password, String FirstName, String LastName, int titleId,
			int JobId, int DepartmentId, int ManagerId, int WrkRoleId, int SectionId, String MobileNumber) {
		return dataAccessDAO.createNewUser(LoginName, Password, FirstName, LastName, titleId, JobId, DepartmentId,
				ManagerId, WrkRoleId, SectionId, MobileNumber);
	}

	@Override
	public String updateUserInfo(int UserId, int ManagerId, int RoleId, int DeptId, int SectionId, int JobId) {
		return dataAccessDAO.updateUserInfo(UserId, ManagerId, RoleId, DeptId, SectionId, JobId);
	}

	@Override
	public String updateUserNameInfo(int UserId, String FirstName, String LastName, String NameAr, String NameEn) {
		return dataAccessDAO.updateUserNameInfo(UserId, FirstName, LastName, NameAr, NameEn);
	}

	@Override
	public String findDepartmentNameById(int departmentId) {
		return dataAccessDAO.findDepartmentNameById(departmentId);
	}

	@Override
	public int addNewDept(String DepartmentNameAR, String DepartmentNameEN) {
		return dataAccessDAO.addNewDept(DepartmentNameAR, DepartmentNameEN);
	}

	@Override
	public int addNewsection(String sectionName, int DeptId) {
		return dataAccessDAO.addNewsection(sectionName, DeptId);
	}

	@Override
	public Map<String, String> findUsersRolesMap() {
		return dataAccessDAO.findUsersRolesMap();
	}

	@Override
	public Map<String, String> findUsersMap() {
		return dataAccessDAO.findUsersMap();
	}

	@Override
	public List<WrkChargingClass> findAllChargingByStatus(int Status) {
		return dataAccessDAO.findAllChargingByStatus(Status);
	}

	@Override
	public String addNewCharging(int WhoIsCharged, int WhoIsNotHere, String UserNameAfter, String StartDate,
			String EndDate, int privilageAfter) {
		return dataAccessDAO.addNewCharging(WhoIsCharged, WhoIsNotHere, UserNameAfter, StartDate, EndDate,
				privilageAfter);
	}

	@Override
	public String findEmployeeName(int EmployeeId) {
		return dataAccessDAO.findEmployeeName(EmployeeId);
	}

	@Override
	public String updateUserSign(int pUserId, InputStream pSign) {
		return dataAccessDAO.updateUserSign(pUserId, pSign);
	}

	@Override
	public String updateUserMark(int pUserId, InputStream pMark) {
		return dataAccessDAO.updateUserMark(pUserId, pMark);
	}

	@Override
	public String updateUserRefSign(int pUserId, InputStream pRefSign) {
		return dataAccessDAO.updateUserRefSign(pUserId, pRefSign);
	}

	@Override
	public List<AttachmentFileClass> getAllFilesbyUser(int UserId) {
		return dataAccessDAO.getAllFilesbyUser(UserId);
	}

	@Override
	public void deleteAttachmentFile(String fileAttachId) {
		dataAccessDAO.deleteAttachmentFile(fileAttachId);
	}

	@Override
	public String encrypt(String string) {
		return dataAccessDAO.encrypt(string);
	}

	@Override
	public List<AttachmentFileClass> SearchAttachmentFiles(int UserId, String Search_key) {
		return dataAccessDAO.SearchAttachmentFiles(UserId, Search_key);
	}

	@Override
	public void addNewAttachment(String FileName, InputStream in, int AddedBy, int FolderId) {
		dataAccessDAO.addNewAttachment(FileName, in, AddedBy, FolderId);
	}

	@Override
	public List<ArcRecordClass> SearchRecordsByCommentdata(String SrchKey) {
		return dataAccessDAO.SearchRecordsByCommentdata(SrchKey);
	}

	@Override
	public List<ArcRecordClass> SearchRecordsByLetterFrom(int SrchKey) {
		return dataAccessDAO.SearchRecordsByLetterFrom(SrchKey);
	}

	@Override
	public List<ArcRecordClass> SearchRecordsByLetterTo(int SrchKey) {
		return dataAccessDAO.SearchRecordsByLetterTo(SrchKey);
	}

	@Override
	public List<ArcRecordClass> getRecords(String IncomeNoPar) {
		return dataAccessDAO.getRecords(IncomeNoPar);
	}

	@Override
	public int findArcRecordParam(String ArcId) {
		return dataAccessDAO.findArcRecordParam(ArcId);
	}

	@Override
	public void SendRecordFromArchieve(String RecordId, int EmployeeId, String SendingType) {
		dataAccessDAO.SendRecordFromArchieve(RecordId, EmployeeId, SendingType);
	}

	@Override
	public List<Employer> loadAllEmployers() {
		return dataAccessDAO.loadAllEmployers();
	}

	@Override
	public List<Sub_menu_class> findSubMenus(int UserId, int mainMenuId) {
		return dataAccessDAO.findSubMenus(UserId, mainMenuId);
	}

	@Override
	public Map<String, String> loadDirections() {
		return dataAccessDAO.loadDirections();
	}

	@Override
	public String checkUmmAlQuraDate(String value) {
		return dataAccessDAO.checkUmmAlQuraDate(value);
	}

	public EmpVac infoVacEmp(int puserid) {

		return dataAccessDAO.infoVacEmp(puserid);

	}

	@Override
	public EmpVac infVacEmp1(int vuser_id, int vacprd, EmpVac vacObject) {

		////
		EmpVac newObject = dataAccessDAO.infVacEmp1(vuser_id, vacprd);

		// vacObject.setCalc116(newObject.getCalc116());
		// vacObject.setAllvacprd(newObject.getAllvacprd());
		// vacObject.setMxvac116(newObject.getMxvac116());
		// vacObject.setVACSUM360(newObject.getVACSUM360());
		// vacObject.setLSTVACDT(newObject.getLSTVACDT());
		// vacObject.setLSTVACWRK(newObject.getLSTVACWRK());
		// vacObject.setLSTVACPRD(newObject.getLSTVACPRD());
		// vacObject.setVACARESULT(newObject.getVACARESULT());
		return vacObject;
	}

	@Override
	public EmpVac infVacEmp2(int vuser_id, int vacsum360, int vacprd, int calc, int pbascal, int vacaresult,
			EmpVac vacObject) {

		EmpVac newObject = dataAccessDAO.infVacEmp2(vuser_id, vacsum360, vacprd, calc, pbascal, vacaresult);

		// vacObject.setVCALC_new(newObject.getVCALC_new());
		// vacObject.setPROCTYP(newObject.getPROCTYP());
		// vacObject.setVACVAL(newObject.getVACVAL());
		// vacObject.setVACWRKFROM(newObject.getVACWRKFROM());
		// vacObject.setVACWRKto(newObject.getVACWRKto());
		// vacObject.setMesag(newObject.getMesag());
		// vacObject.setMesag1(newObject.getMesag1());
		// vacObject.setMesag2(newObject.getMesag2());
		// vacObject.setVACAPRD_new(newObject.getVACAPRD_new());

		return vacObject;
	}

	// @Override
	// public EmpVac validate_dates_vac116(int vuser_id, String vACASTRT, int
	// vacprd, HrEmployeeVacation vacObject) {
	//
	// EmpVac newObject = dataAccessDAO.validate_dates_vac116(vuser_id,
	// vACASTRT, vacprd);
	//
	// vacObject.setVACAEND(newObject.getVACAEND());
	// vacObject.setMesag(newObject.getMesag());
	// vacObject.setMesag1(newObject.getMesag1());
	// vacObject.setMesag2(newObject.getMesag2());
	//
	// return newObject;
	// }

	@Override
	public boolean instrVac1166(int vuser_id, int vactype, int vacprd, String vacastrt, String vacaend, int proctyp,
			int salary, int vacval, String vacwrkfrom, String vacwrKto) {
		return dataAccessDAO.instrVac1166(vuser_id, vactype, vacprd, vacastrt, vacaend, proctyp, salary, vacval,
				vacwrkfrom, vacwrKto);
	}

	@Override
	public EmpVac CALC_VAC122_emp122(int vuser_id, EmpVac empvacobj) {
		EmpVac newObject = dataAccessDAO.CALC_VAC122_emp122(vuser_id);

		empvacobj.setVac122(newObject.getVac122());
		empvacobj.setVacmx122(newObject.getVacmx122());
		return newObject;

	}

	@Override
	public EmpVac checkdate122(String vacastrt, int vacprd, EmpVac empvacobj) {
		EmpVac newObject = dataAccessDAO.checkdate122(vacastrt, vacprd);

		empvacobj.setVACAEND(newObject.getVACAEND());
		empvacobj.setMesag(newObject.getMesag());
		empvacobj.setMesag1(newObject.getMesag1());
		empvacobj.setMesag2(newObject.getMesag2());

		return empvacobj;
	}

	@Override
	public boolean prc_insrt_vacEtrari(int vuser_id, int vactype, int vacprd, String vacastrt, String vacaend) {
		return dataAccessDAO.prc_insrt_vacEtrari(vuser_id, vactype, vacprd, vacastrt, vacaend);

	}

	@Override
	public EmpVac prc_accept_vac(int PWRKID, int vuser_id, String d_MGRtxt, String d_PRStxt) {
		return dataAccessDAO.prc_accept_vac(PWRKID, vuser_id, d_MGRtxt, d_PRStxt);
	}

	@Override
	public EmpVac prc_accept_vacEtrati(int pWRKID, int vuser_id, String d_MGRtxt, String d_PRStxt) {
		return dataAccessDAO.prc_accept_vacEtrati(pWRKID, vuser_id, d_MGRtxt, d_PRStxt);
	}

	@Override
	public TrdModelClass findTrdApplicationData(int trdApplicationId) {
		return dataAccessDAO.findTrdApplicationData(trdApplicationId);
	}

	@Override
	@Transactional
	public TrdModelClass findTrdApplicationDataByArcrecord(Integer arcRecordId) {
		return dataAccessDAO.findTrdApplicationData(commonDao.findLicTrdByArcrecordId(arcRecordId));
	}

	// @Override
	// @Transactional
	// public void saveAuthorization(Integer fromId, Integer toId, String
	// recTitle, Integer recordType, Integer letterFrom, Integer letterto,
	// String userComment, Integer structId,
	// boolean withIncomeNumber, String letterFromNo, String letterFromDate,
	// Long mobileNumber, Integer recordIsImportant, String nationalId, Integer
	// purpose,
	// boolean isPictureAtached, String applicStatus, Integer
	// applicationUserDeptJob, Integer empNo,String absFrom, String absTo,String
	// absType,String jabsDate, String reason) throws ParseException{
	// int recordId = createNewArcRecord(fromId, toId, recTitle, recordType,
	// letterFrom, letterto, structId, withIncomeNumber, letterFromNo,
	// letterFromDate, mobileNumber, recordIsImportant, nationalId);
	// createNewWrkApplication(recordId, fromId, toId, userComment, purpose,
	// isPictureAtached, applicStatus, applicationUserDeptJob);
	// createHrsEmpAbsent(fromId, empNo, absFrom, absTo, absType, jabsDate,
	// reason);
	// }

	// @Override
	// @Transactional
	// public Integer createNewArcRecord(Integer fromId, Integer toId, String
	// recTitle, Integer recordType,
	// Integer letterFrom, Integer letterto, Integer structId,
	// boolean withIncomeNumber, String letterFromNo, String letterFromDate,
	// Long mobileNumber,
	// Integer recordIsImportant, String nationalId) {
	// if (fromId < 1 || toId < 1 || recTitle == null) {
	// return 0;
	// }
	// String CurrentHijriDate = hijri.findCurrentHijriDate();
	// ArcRecords arcRecords = new ArcRecords();
	// arcRecords.setRecId("");
	// arcRecords.setRecGDate(new Date());
	// arcRecords.setRecHDate(CurrentHijriDate);
	// arcRecords.setCreatedIn(new Date());
	// arcRecords.setCreatedBy(fromId);
	// arcRecords.setRecTitle(recTitle);
	// arcRecords.setStructId(structId);
	// arcRecords.setApplicationType(recordType);
	// if (withIncomeNumber) {
	// arcRecords.setIncomeNo(commonDao.createInocmeNUmber().toString());
	// arcRecords.setIncomeHDate(CurrentHijriDate);
	// } else {
	// arcRecords.setIncomeNo("");
	// arcRecords.setIncomeHDate("");
	// }
	// if ((letterFrom!=null) && (letterFrom <= 0))
	// letterFrom = commonDao.findUserSection(fromId);
	// arcRecords.setLetterFrom(letterFrom);
	// if ((letterto!=null) && (letterto <= 0))
	// letterto = commonDao.findUserSection(toId);
	// arcRecords.setLetterTo(letterto);
	// arcRecords.setLetterFromNo(letterFromNo);
	// arcRecords.setLetterFromDate(letterFromDate);
	// arcRecords.setMobileNumber(mobileNumber);
	// arcRecords.setRecordIsImportant(recordIsImportant);
	// arcRecords.setIncomeYear(Integer.parseInt(hijri.findCurrentYear()));
	// arcRecords.setNationalId(nationalId);
	// return commonDao.save(arcRecords);
	// }

	// @Override
	// @Transactional
	// public Integer createNewWrkApplication(int recordId, int fromId, int
	// toId, String userComment, int purpose,
	// boolean isPictureAtached, String applicStatus, int
	// applicationUserDeptJob) {
	//
	// String CurrentHijriDate = hijri.findCurrentHijriDate();
	// String CurrentTime = hijri.findCurrentTime();
	// WrkApplication wrkApp = new WrkApplication();
	// wrkApp.setStepId(findStepId(recordId));
	// wrkApp.setArcRecordId(recordId);
	// wrkApp.setFromUserId(fromId);
	// wrkApp.setToUserId(toId);
	// wrkApp.setApplicationUsercomment(userComment.getBytes());
	// wrkApp.setRefAttach((isPictureAtached) ? -1 : null);
	// wrkApp.setApplicationCreateDate(new Date());
	// wrkApp.setApplicationCreateTime(CurrentTime);
	// // check if F or N
	// wrkApp.setApplicationStatus(applicStatus);
	// wrkApp.setHijriDate(CurrentHijriDate);
	// wrkApp.setApplicationIsRead(0);
	// wrkApp.setApplicationPurpose(purpose);
	// wrkApp.setApplicationId(commonDao.createWrkId());
	// wrkApp.setApplicationIsReply(0);
	// wrkApp.setApplicationIsVisible(1);
	// wrkApp.setApplicationUserDeptJob(applicationUserDeptJob);
	// return commonDao.save(wrkApp);
	// }

	// @Override
	// @Transactional
	// public Integer createHrsEmpAbsent(int userId, int empNo,String absFrom,
	// String absTo,String absType,String jabsDate, String reason) throws
	// ParseException{
	// HrsUserAbsent hrsEmpAbsent = new HrsUserAbsent(userId, empNo,
	// hijri.ConvertgeorgianDatetoHijriDate(jabsDate), absFrom, absTo, absType,
	// jabsDate,reason);
	// return commonDao.save(hrsEmpAbsent);
	// }

	@Override
	@Transactional
	public void saveAuthorizationOrMission(HrsUserAbsent userAbsent, String title, int applicationType,
			boolean isPictureAtached) throws ParseException {
		try {
			ArcUsers user = Utils.findCurrentUser();
			Integer fromId = user.getUserId();
			Integer toId = user.getMgrId();
			Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());

			if (fromId < 1 || toId < 1 || title == null) {
				return;
			}

			ArcRecords arcRecords = new ArcRecords();
			arcRecords.setApplicationType(applicationType);
			arcRecords.setRecTitle(title);
			int recordId = createNewArcRecord(arcRecords, false, toId);

			WrkApplication application = new WrkApplication();
			application.setToUserId(toId);
			// application.setId(new WrkApplicationId(commonDao.createWrkId(),
			// findStepId(recordId)));
			application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
			application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
			// application.setApplicationUserDeptJob(applicationUserDeptJob);
			String userComment = title;
			createNewWrkApplication(recordId, application, userComment, isPictureAtached, applicationUserDeptJob);
			int absentId = createHrsEmpAbsent(userAbsent);
			saveHrsSigns(recordId, absentId, false, null, Utils.findCurrentUser().getUserId(), applicationType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("saveAuthorizationOrMission userAbsent" + userAbsent.getId() + "   " + e.toString());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void acceptAuthorizationOrMission(HrsUserAbsent authorization, WrkApplicationId wrkApplicationId,
			int modelType) {
		WrkApplication app = (WrkApplication) commonDao.findWrkApplicationById(wrkApplicationId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(wrkApplicationId);

		application.setId(new WrkApplicationId(wrkApplicationId.getApplicationId(), wrkApplicationId.getStepId() + 1));

		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(modelType, app.getId().getApplicationId(), app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		createNewWrkApplication(application.getArcRecordId(), application, null, false, null);

		saveHrsSigns(application.getArcRecordId(), authorization.getId(), true, null,
				Utils.findCurrentUser().getUserId(), modelType);
		boolean visible = (getDestinationModel(modelType, acceptCount).getSigned() == 1);
		if (visible) {
			authorization.setAccept(MyConstants.YES);
			updateHrsEmpAbsent(authorization);
			updateArcRecordsIncomeNo(app.getArcRecordId());
		}
	}

	@Override
	@Transactional
	public Integer saveAttachment(InputStream inputStream, String fileName, int OwnerId, double fileSize,
			int fileNode) {
		ArcAttach attach = new ArcAttach();
		attach.setAttachName(Utils.saveAttachment(inputStream));
		attach.setAttachDate(new Date());
		if (fileNode == 0)
			fileNode = 1;
		attach.setAtachNode(fileNode);
		attach.setAttachSize(fileSize);
		attach.setAttachType(1);
		attach.setAttachCategory("FILE");
		return save(attach);
	}

	@Override
	@Transactional
	public void saveTraining(HrsEmployeeTraining employeeTraining, String title, int applicationType,
			boolean isPictureAtached) {
		ArcUsers user = Utils.findCurrentUser();
		Integer fromId = user.getUserId();
		Integer toId = user.getMgrId();
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());

		if (fromId < 1 || toId < 1 || title == null) {
			return;
		}

		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setApplicationType(applicationType);
		arcRecords.setRecTitle(title);
		int recordId = createNewArcRecord(arcRecords, false, toId);

		WrkApplication application = new WrkApplication();
		application.setToUserId(toId);
		// application.setId(new WrkApplicationId(commonDao.createWrkId(),
		// findStepId(recordId)));
		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		// application.setApplicationUserDeptJob(applicationUserDeptJob);
		String userComment = title;
		createNewWrkApplication(recordId, application, userComment, isPictureAtached, applicationUserDeptJob);
		int trainingId = createEmployeeTraining(employeeTraining);
		saveHrsSigns(recordId, trainingId, false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.TRAINING.getValue());
	}

	private int createEmployeeTraining(HrsEmployeeTraining employeeTraining) {
		employeeTraining.setCreatedBy(Utils.findCurrentUser().getUserId());
		employeeTraining.setCreatedIn(new Date());
		return save(employeeTraining);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void acceptTraining(WrkApplicationId wrkApplicationId, HrsEmployeeTraining employeeTraining) {
		WrkApplication app = (WrkApplication) commonDao.findWrkApplicationById(wrkApplicationId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(wrkApplicationId);
		application.setId(wrkApplicationId);
		application.getId().setStepId(application.getId().getStepId() + 1);
		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(MailTypeEnum.TRAINING.getValue(), app.getId().getApplicationId(),
				app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		createNewWrkApplication(application.getArcRecordId(), application, null, false, null);

		boolean visible = (getDestinationModel(MailTypeEnum.TRAINING.getValue(), acceptCount).getSigned() == 1);
		saveHrsSigns(application.getArcRecordId(), employeeTraining.getId(), visible, null,
				Utils.findCurrentUser().getUserId(), MailTypeEnum.TRAINING.getValue());
		if (visible) {
			employeeTraining.setTrainingStatus(MyConstants.YES);
			commonDao.update(employeeTraining);
			updateArcRecordsIncomeNo(app.getArcRecordId());
		}
	}

	// add new WRK_COMMENT
	@Override
	@Transactional
	public Integer saveLetter(String incomeNo, int appPapers, String appTo, String appSubject, String appAttach,
			String longComment, String firstCopy, String secondCopy, int wroteBy, int commentType) {
		// HijriCalendarUtil hutil = new HijriCalendarUtil();
		WrkComment wrkComment = new WrkComment();
		wrkComment.setAppNo(incomeNo);
		wrkComment.setAppPapers(appPapers);
		wrkComment.setAppTo(appTo);
		wrkComment.setAppSubject(appSubject);
		wrkComment.setAppAttach(appAttach);
		wrkComment.setLongComment(longComment);
		wrkComment.setFirstCopy(firstCopy);
		wrkComment.setSecondCopy(secondCopy);
		wrkComment.setWroteBy(wroteBy);
		wrkComment.setWroteIn(HijriCalendarUtil.findCurrentHijriDate());
		wrkComment.setCommentType(commentType);
		return save(wrkComment);
	}

	@Override
	@Transactional
	public Integer findStepId(int recordId) {
		return commonDao.loadApplicationStepId(recordId);
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	///////////////////////////////////////////////////////////////////////////////////////////

	// add WRK comment
	@Override
	@Transactional
	public void addComment(WrkComment comment, WrkApplication application, List<Integer> attachmnetIds) {
		HijriCalendarUtil hutil = new HijriCalendarUtil();

		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setRecHDate(HijriCalendarUtil.findCurrentHijriDate());
		arcRecord.setRecGDate(new Date());
		arcRecord.setCreatedBy(Utils.findCurrentUser().getUserId());
		arcRecord.setFilesNo(attachmnetIds.size());
		arcRecord.setLetterFrom(commonDao.findUserSection(Utils.findCurrentUser().getUserId()));
		arcRecord.setStructId(1);
		arcRecord.setRecTitle(comment.getAppSubject());
		arcRecord.setCreatedIn(new Date());
		// arcRecord.setIncomeNo(commonDao.createInocmeNUmber());
		arcRecord.setIncomeHDate(HijriCalendarUtil.findCurrentHijriDate());
		arcRecord.setIncomeYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
		arcRecord.setRecordIsImportant(0);
		ArcUsers toUserId = (ArcUsers) commonDao.findEntityById(ArcUsers.class, Utils.findCurrentUser().getMgrId());
		arcRecord.setLetterTo(toUserId.getDeptId());

		arcRecord.setApplicationType(143);
		arcRecord.setId(createArcRecordsId());
		int recordId = save(arcRecord);

		// WrkApplication application = new WrkApplication();
		// application.setApplicationPurpose(purposeId);
		application.setApplicationCreateDate(new Date());
		// application.setId(new WrkApplicationId(commonDao.createWrkId(), 1));
		// application.setApplicationId(commonDao.createWrkId());
		application.setArcRecordId(recordId);
		// application.setStepId(1);
		application.setFromUserId(Utils.findCurrentUser().getUserId());
		application.setApplicationCreateDate(new Date());
		application.setApplicationCreateTime(HijriCalendarUtil.findCurrentTime());
		application.setApplicationIsRead(0);
		application.setApplicationIsReply(0);
		application.setApplicationIsVisible(1);
		application.setHijriDate(HijriCalendarUtil.findCurrentHijriDate());
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		// application.setApplicationCreateTime(calendar.getTime().toString());
		if (application.getToUserId() == null) {
			application.setToUserId(Utils.findCurrentUser().getMgrId());
		}

		int applicationId = commonDao.saveWrkApp(application, recordId).getApplicationId();
		System.out.println("setwrkappid  "+applicationId);
		comment.setWRKAPPID(applicationId);
		comment.setAppHdate(HijriCalendarUtil.findCurrentHijriDate());
		comment.setWroteBy(Utils.findCurrentUser().getUserId());
		comment.setMarkedBy(Utils.findCurrentUser().getUserId());
		comment.setMarkedIn(HijriCalendarUtil.findCurrentHijriDate());
		// comment.setFontSize(14);
		comment.setSignType("S");
		comment.setAppNo(arcRecord.getIncomeNo());   //?????
		comment.setWroteIn(hutil.findCurrentHijriDate());
		// comment.setCommentType(1);
		Integer commentId = save(comment);

		saveAttachs(attachmnetIds, recordId);

	}

	@Override
	@Transactional
	public void addPersonalComment(WrkComment comment, WrkApplication application, List<Integer> attachmnetIds) {
		HijriCalendarUtil hutil = new HijriCalendarUtil();

		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setRecHDate(HijriCalendarUtil.findCurrentHijriDate());
		arcRecord.setRecGDate(new Date());
		arcRecord.setCreatedBy(Utils.findCurrentUser().getUserId());
		arcRecord.setFilesNo(attachmnetIds.size());
		arcRecord.setLetterFrom(commonDao.findUserSection(Utils.findCurrentUser().getUserId()));
		arcRecord.setStructId(1);
		arcRecord.setRecTitle(comment.getAppSubject());
		arcRecord.setCreatedIn(new Date());
		arcRecord.setIncomeNo(commonDao.createIncomeNo());
		arcRecord.setIncomeHDate(HijriCalendarUtil.findCurrentHijriDate());
		arcRecord.setIncomeYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
		arcRecord.setRecordIsImportant(0);

		// arcRecord.setLetterTo(commonDao.findUserSection(Utils.findCurrentUser().getMgrId()));

		arcRecord.setApplicationType(MailTypeEnum.PERSONAL_COMMENT.getValue());

		int recordId = save(arcRecord);

		// WrkApplication application = new WrkApplication();
		// application.setApplicationPurpose(purposeId);
		application.setApplicationCreateDate(new Date());
		application.setId(new WrkApplicationId(commonDao.createWrkId(), 1));
		// application.setApplicationId(commonDao.createWrkId());
		application.setArcRecordId(recordId);
		// application.setStepId(1);
		application.setFromUserId(Utils.findCurrentUser().getUserId());
		application.setApplicationCreateDate(new Date());
		application.setApplicationCreateTime(HijriCalendarUtil.findCurrentTime());
		application.setApplicationIsRead(0);
		application.setApplicationIsReply(0);
		application.setApplicationIsVisible(1);
		application.setHijriDate(HijriCalendarUtil.findCurrentHijriDate());
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		// application.setApplicationCreateTime(calendar.getTime().toString());
		application.setToUserId(Utils.findCurrentUser().getUserId());
		int applicationId = commonDao.saveWrkApp(application, recordId).getApplicationId();

		comment.setWRKAPPID(applicationId);
		comment.setAppHdate(HijriCalendarUtil.findCurrentHijriDate());
		comment.setWroteBy(Utils.findCurrentUser().getUserId());
		// comment.setMarkedBy(Utils.findCurrentUser().getUserId());
		comment.setMarkedIn(HijriCalendarUtil.findCurrentHijriDate());
		// comment.setFontSize(14);
		comment.setSignType("S");
		comment.setSignedBy(Utils.findCurrentUser().getUserId());
		comment.setSignedIn(HijriCalendarUtil.findCurrentHijriDate());
		comment.setAppNo(arcRecord.getIncomeNo());
		comment.setWroteIn(hutil.findCurrentHijriDate());
		// comment.setCommentType(1);
		Integer commentId = save(comment);

		//
		// WrkApplicationId wrkApplicationId = new
		// WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId),
		// this.selectedInbox.StepId);
		// WrkApplication wrk =
		// commonDao.findWrkApplicationById(wrkApplicationId);
		//
		// signComment(wrk, "S", selectedUserInSignComment);

		saveAttachs(attachmnetIds, recordId);

	}

	@Override
	public List<WrkPurpose> getAllPurposes() {
		List<WrkPurpose> allPurposes = commonDao.getAllPurposes();
		if (!allPurposes.isEmpty() && allPurposes != null)
			return allPurposes;
		else
			return new ArrayList<WrkPurpose>();

	}

	@Override
	public List<Sys037> getAllContractorsCategories() {
		List<Sys037> allContractorsCategories = commonDao.getAllContractorsCategories();
		if (!allContractorsCategories.isEmpty() && allContractorsCategories != null)
			return allContractorsCategories;
		else
			return new ArrayList<Sys037>();

	}

	@Override
	public List<WrkCommentType> getCommentTypes() {
		if (!commonDao.getCommentTypes().isEmpty() && commonDao.getCommentTypes() != null)
			return commonDao.getCommentTypes();
		else
			return new ArrayList<WrkCommentType>();
	}

	@Override
	@Transactional
	public Integer AddNewInternalMemo(WrkApplication wrkApplication, ArcRecords arcRecord,
			List<Integer> attachmnetIds) {
		// HijriCalendarUtil hijri = new HijriCalendarUtil();
		Date sysDate = new Date();
		String CurrentHijriDate = HijriCalendarUtil.findCurrentHijriDate();
		String CurrentTime = HijriCalendarUtil.findCurrentTime();

		Integer archId = saveNewArcrecord(arcRecord, sysDate, CurrentHijriDate);

		wrkApplication.setArcRecordId(archId);
		wrkApplication.setFromUserId(Utils.findCurrentUser().getUserId());
		wrkApplication.setApplicationCreateDate(sysDate);
		wrkApplication.setApplicationCreateTime(CurrentTime);
		wrkApplication.setApplicationStatus("F");
		wrkApplication.setHijriDate(CurrentHijriDate);
		wrkApplication.setApplicationIsRead(0);
		wrkApplication.setApplicationIsReply(0);
		wrkApplication.setApplicationIsVisible(1);
		int wrkAppId = commonDao.saveWrkApp(wrkApplication, 0).getApplicationId();

		// secret Explain
		if (wrkApplication.isVisibleFlag() == true) {
			WrkConfedintialRepliesId wcr2 = new WrkConfedintialRepliesId();
			// save WrkConfedintial toId
			wcr2.setGrantedUser(wrkApplication.getToUserId());
			wcr2.setId(wrkApplication.getId().getApplicationId());
			wcr2.setStepId(wrkApplication.getId().getStepId());
			WrkConfedintialReplies w2 = new WrkConfedintialReplies();
			w2.setId(wcr2);
			saveObject(w2);
			// save WrkConfedintial fromId
			WrkConfedintialRepliesId wrkConfedintial = new WrkConfedintialRepliesId();
			wrkConfedintial.setGrantedUser(Utils.findCurrentUser().getUserId());
			wrkConfedintial.setId(wrkApplication.getId().getApplicationId());
			wrkConfedintial.setStepId(wrkApplication.getId().getStepId());
			WrkConfedintialReplies wrkConfedintialReplies = new WrkConfedintialReplies();
			wrkConfedintialReplies.setId(wrkConfedintial);
			saveObject(wrkConfedintialReplies);
		}

		saveAttachs(attachmnetIds, archId);
		return archId;

	}

	private Integer saveNewArcrecord(ArcRecords arcRecord, Date sysDate, String CurrentHijriDate) {
		Integer archId = arcRecord.getId();
		if (arcRecord.getId() == null) {
			if (arcRecord.isImportantFlag() == true) {
				arcRecord.setRecordIsImportant(1);

			} else {
				arcRecord.setRecordIsImportant(0);
			}

			if (arcRecord.isOutcomingNumFlag()) {
//				arcRecord.setOutcomingNo(commonDao.createOutcomeNo());
				arcRecord.setIncomeHDate(CurrentHijriDate);
			} else {

				arcRecord.setIncomeNo("");
			}

			arcRecord.setLetterFrom(commonDao.findUserSection(Utils.findCurrentUser().getUserId()));
			arcRecord.setCreatedIn(sysDate);
			arcRecord.setRecHDate(CurrentHijriDate);
			arcRecord.setCreatedBy(Utils.findCurrentUser().getUserId());
			arcRecord.setIncomeYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
			arcRecord.setRecGDate(sysDate);
			arcRecord.setApplicationType(MailTypeEnum.MODAKARA.getValue());
			// TODO activate
			archId = save(arcRecord);

		}

		return archId;
	}

	// public ArcUsers logIn(String username, String password) {
	// return commonDao.login(username, password);
	// }

	// @Override
	// public ArcUsers loadUser(String username) {
	// return commonDao.loadUser(username);
	// }

	@Override
	@Transactional
	public HrsUserAbsent loadAuthorizationOrMissionById(int idAuthorization) {
		return (HrsUserAbsent) commonDao.findEntityById(HrsUserAbsent.class, idAuthorization);
	}

	@Override
	public EmpVac loadEtrariVac(int app_id) {
		return dataAccessDAO.loadEtrariVac(app_id);
	}

	@Override
	public EmpVac loadNormalVac(int app_id) {
		return dataAccessDAO.loadNormalVac(app_id);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveHrsSigns(int arcRecordId, int docId, boolean visible, String note, int userId, Integer modelId) {
		String CurrentHijriDate = HijriCalendarUtil.findCurrentHijriDate();
		String CurrentTime = HijriCalendarUtil.findCurrentTime();
		HrsSigns hrsSigns = new HrsSigns();
		hrsSigns.setUserId(userId);
		hrsSigns.setJobName(Utils.findCurrentUser().getFirstName());
		hrsSigns.setDate(CurrentHijriDate);
		hrsSigns.setSignTime(CurrentTime);
		hrsSigns.setArcRecordId(arcRecordId);
		hrsSigns.setDocId(docId);
		hrsSigns.setVisible(visible);
		hrsSigns.setSignNote(note);
		hrsSigns.setStepId(commonDao.getHrsSignNextStep(arcRecordId));
		hrsSigns.setModelId(modelId);
		save(hrsSigns);
	}

	@Override
	@Transactional
	public Integer AddCopyInternalMemo(WrkApplication wrkApplication, ArcRecords arcRecord,
			List<Integer> attachmnetIds) {
		// HijriCalendarUtil hijri = new HijriCalendarUtil();
		String CurrentHijriDate = HijriCalendarUtil.findCurrentHijriDate();
		String CurrentTime = HijriCalendarUtil.findCurrentTime();

		if (arcRecord.isImportantFlag() == true) {
			arcRecord.setRecordIsImportant(1);

		} else {
			arcRecord.setRecordIsImportant(0);
		}

		arcRecord.setLetterFrom(commonDao.findUserSection(Utils.findCurrentUser().getUserId()));
		arcRecord.setCreatedIn(new Date());
		arcRecord.setRecHDate(CurrentHijriDate);
		arcRecord.setIncomeYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
		arcRecord.setRecGDate(new Date());
		arcRecord.setApplicationType(0);

		arcRecord.setCreatedBy(Utils.findCurrentUser().getUserId());
		// TODO activate
		// arcRecord.setLetterTo(commonDao.findUserSection(wrkApplication.getToUserId()));
		int archId = save(arcRecord);

		wrkApplication.setArcRecordId(archId);
		// wrkApplication.setId(new WrkApplicationId(commonDao.createWrkId(),
		// 1));
		// wrkApplication.setStepId(1);
		wrkApplication.setFromUserId(Utils.findCurrentUser().getUserId());
		wrkApplication.setApplicationCreateDate(new Date());
		wrkApplication.setApplicationCreateTime(CurrentTime);
		wrkApplication.setApplicationStatus("F");
		wrkApplication.setHijriDate(CurrentHijriDate);
		wrkApplication.setApplicationIsRead(0);
		// wrkApplication.setApplicationId(commonDao.createWrkId());
		wrkApplication.setApplicationIsReply(0);
		wrkApplication.setApplicationIsVisible(1);
		int wrkAppId = commonDao.saveWrkApp(wrkApplication, 0).getApplicationId();

		saveAttachs(attachmnetIds, archId);
		return archId;

	}

	@Override
	@Transactional
	public List<ArcUsers> getAllEmployees() {

		return commonDao.getAllEmployees();
	}

	@Override
	public List<User> getAllEmployeesInDept(Integer deptId) {
		return commonDao.getAllEmployeesInDept(deptId);
	}

	@Override
	public List<HrsMasterFile> getAllEmployeesByDept(Integer deptId) {
		return commonDao.getAllEmployeesByDeptId(deptId);
	}

	@Override
	public List<ArcUsers> getAllManagers() {
		return commonDao.getAllManagers();
	}

	@Override
	public List<Integer> addAttachments(List<ArcAttach> attachs) {
		return commonDao.addAttachments(attachs);
	}

	@Override
	@Transactional
	public Integer getUserDeptJob(int managerId) {
		return commonDao.getUserDeptJob(managerId);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateHrsEmpAbsent(HrsUserAbsent userAbsent) {
		commonDao.update(userAbsent);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateStockEntry(StockEntryMaster stockEntryMaster) {
		commonDao.update(stockEntryMaster);
	}

	@Override
	@Transactional
	public Integer createNewArcRecord(ArcRecords arcRecords, boolean withIncomeNumber, Integer toId) {
		ArcUsers user = Utils.findCurrentUser();
		Integer fromId = user.getUserId();
		String CurrentHijriDate = HijriCalendarUtil.findCurrentHijriDate();
		arcRecords.setRecGDate(new Date());
		arcRecords.setRecHDate(CurrentHijriDate);
		arcRecords.setCreatedIn(new Date());
		arcRecords.setRecId("");
		arcRecords.setRecordIsImportant(0);
		arcRecords.setCreatedBy(fromId);
		arcRecords.setRecGDate(new Date());
		arcRecords.setRecHDate(CurrentHijriDate);
		arcRecords.setCreatedIn(new Date());
		arcRecords.setLetterFrom(user.getDeptId());
		if (withIncomeNumber) {
			arcRecords.setIncomeNo(commonDao.createIncomeNo());
			arcRecords.setIncomeHDate(CurrentHijriDate);
		} else {
			arcRecords.setIncomeNo("");
			arcRecords.setIncomeHDate("");
		}
		Integer letterFrom = arcRecords.getLetterFrom();
		if ((letterFrom != null) && (letterFrom <= 0))
			letterFrom = commonDao.findUserSection(fromId);
		arcRecords.setLetterFrom(letterFrom);
		Integer letterto = arcRecords.getLetterTo();
		if ((letterto != null) && (letterto <= 0))
			letterto = commonDao.findUserSection(toId);
		arcRecords.setLetterTo(letterto);
		arcRecords.setIncomeYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
		Integer x = save(arcRecords);
		return x;
	}

	@Override
	// @Transactional(readOnly = false)
	public WrkApplicationId createNewWrkApplication(int recordId, WrkApplication application, String userComment,
			boolean isPictureAtached, Integer applicationUserDeptJob) {
		String CurrentHijriDate = HijriCalendarUtil.findCurrentHijriDate();
		String CurrentTime = HijriCalendarUtil.findCurrentTime();
		ArcUsers user = Utils.findCurrentUser();
		application.setArcRecordId(recordId);
		if (userComment != null)
			application.setApplicationUsercomment(userComment);
		application.setRefAttach((isPictureAtached) ? -1 : null);
		application.setApplicationCreateDate(new Date());
		application.setApplicationCreateTime(CurrentTime);
		application.setHijriDate(CurrentHijriDate);
		application.setApplicationIsRead(0);
		application.setApplicationIsReply(0);
		application.setApplicationIsVisible(1);
		application.setFromUserId(user.getUserId());
		WrkApplicationId wrkApplicationId = commonDao.saveWrkApp(application, recordId);
		return wrkApplicationId;

	}

	@Override
	@Transactional
	public Integer createHrsEmpAbsent(HrsUserAbsent userAbsent) {
		return save(userAbsent);
	}

	@Override
	@Transactional
	public HrsUserAbsent loadAuthorizationOrMissionByRecordId(int arcRecordId) {
		return (HrsUserAbsent) commonDao.loadHrsUserAbsentByRecordId(arcRecordId);
	}

	@Override
	public List<WrkRefrentionalSetting> getAllManagersFromWFS() {
		return commonDao.getAllManagersFromWFS();
	}

	@Override
	@Transactional
	public WrkApplication getWrkApplicationRecordById(int arcRecordId) {

		return commonDao.getWrkApplicationRecordById(arcRecordId);
	}

	@Override
	@Transactional
	public ArcRecords getArchRecordById(int archRecordId) {
		// TODO Auto-generated method stub
		return commonDao.getArchRecordById(archRecordId);
	}

	@Transactional
	@Override
	public WrkComment getWrkCommentByAppId(int appId) {

		return commonDao.getWrkCommentByAppId(appId);
	}

	@Override
	@Transactional
	public List<ArcAttach> getAttachmentByArchRecordId(int archRecordId) {
		// TODO Auto-generated method stub
		return commonDao.getAttachmentByArchRecordId(archRecordId);
	}

	@Override
	@Transactional
	public Integer newEmpInitaion(EmployeeInitiation empInitiation, Integer vacationId) {
		ArcUsers user = Utils.findCurrentUser();
		Integer fromId = user.getUserId();
		Integer toId = user.getMgrId();
		Integer empInitId = save(empInitiation);

		// commonDao.updateVacationAfterInit(vacationId);
		HrEmployeeVacation currentVacation = commonDao.getVacationById(vacationId);
		currentVacation.setHaveInitiation(-1);
		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setApplicationType(MailTypeEnum.LEAVESUCCESSOR.getValue());
		arcRecord.setRecTitle(Utils.loadMessagesFromFile("LeavSuccerDemand") + user.getEmployeeName());
		Integer arcRecordId = createNewArcRecord(arcRecord, false, toId);
		WrkApplication application = new WrkApplication();
		// application.setId(new WrkApplicationId(commonDao.createWrkId(),
		// findStepId(arcRecordId)));
		application.setToUserId(toId);

		createNewWrkApplication(arcRecordId, application,
				Utils.loadMessagesFromFile("LeavSuccerDemand" + user.getEmployeeName()), false, null);
		commonDao.update(currentVacation);
		saveHrsSigns(arcRecordId, empInitId, false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.LEAVESUCCESSOR.getValue());

		return empInitId;
	}

	@Override
	@Transactional
	public EmployeeInitiation getInitiationById(Integer initiationId) {
		return commonDao.getInitiationById(initiationId);
	}

	public Employer loadEmployerFromAuthorizationOrMission(int arcRecordId) {
		Employer employer = new Employer();
		int autorizationId = commonDao.getDocIdHrsSignByRecId(arcRecordId);
		Integer employerNumber = commonDao.getEmployerNumberHrsAbsent(autorizationId);
		if (employerNumber != null && employerNumber > 0) {

			ArcUsers user = commonDao.loadUserByEmpNO(employerNumber);
			employer.setName(user.getEmployeeName());
			employer.setDepartment(user.getUserDept().getDeptName());
			// HrsEmpHistorical empl =
			// commonDao.getHRsEmpHistoricalByEmpNo(employerNumber);
			// employer.setJob(empl.getGovJob4().getJobName());

		}
		return employer;

	}

	@Override
	@Transactional
	public HrEmployeeVacation getVacationById(Integer vacationId) {
		// TODO Auto-generated method stub
		return commonDao.getVacationById(vacationId);
	}

	@Override
	public Integer getLastVacationForCurrentId(Integer employeeNum) {
		return commonDao.getLastVacationForCurrentId(employeeNum);

	}

	@Override
	@Transactional
	public List<MainMenu> findUserMenus(int puserId) {
		return commonDao.findUserMenus(puserId);
	}

	@Override
	@Transactional
	public List<SubMenu> _findSubMenus(int puserId, int mainMenuId) {
		return commonDao._findSubMenus(puserId, mainMenuId);
	}

	//
	// @Override
	// public int saveInitaionAftrWorl(EmployeeInitiation employeeInitiation)
	// throws VacationAndInitException {
	//
	// return commonDao.saveInitaionAftrWorl(employeeInitiation);
	@Override
	public Object findEntityById(Class entityClass, int EntityId) {
		return commonDao.findEntityById(entityClass, EntityId);
	}

	@Override
	@Transactional
	public Employer loadEmployerByUser(ArcUsers arcUsers) {
		Employer employer = new Employer();
		employer.setName(arcUsers.getEmployeeName());
		employer.setDepartment(arcUsers.getUserDept().getDeptName());
		employer.setEmpNB(arcUsers.getEmployeeNumber());
		employer.setId(arcUsers.getUserId());
		if (arcUsers.getEmployeeNumber() != null) {
			HrsEmpHistorical empl = commonDao.getHRsEmpHistoricalByEmpNo(arcUsers.getEmployeeNumber());
			// employer.setJob(empl.getGovJob4().getJobName());
			if ((empl != null) && (empl.getGovJob4() != null))
				employer.setJob(empl.getGovJob4().getJobName());

			employer.setRank(empl.getRankNumber());
			employer.setBasicSalary(empl.getBasicSalary());
			employer.setJobNumber((empl.getJobNumber()));
			employer.setCategoryId(empl.getCATegoryId());

			int orderId = commonDao.findMaxOrderIdRank();
			HrsSalaryScale hrsSalaryScale = commonDao.findHrsSalaryScaleById(HrsSalaryScale.class,
					new HrsSalaryScaleId(orderId, empl.getRankNumber()));
			employer.setRankName(hrsSalaryScale.getRankName());
			HrsMasterFile hrsMasterFile = (HrsMasterFile) commonDao.findEntityById(HrsMasterFile.class,
					arcUsers.getEmployeeNumber());
			employer.setBirthDateGerige(hrsMasterFile.getBirthDateGrig());
			employer.setFirstApplicationDate(hrsMasterFile.getFirstApplicationDate());
		}

		return employer;
	}

	@Override
	@Transactional
	public void saveVacation(Employer employer, HrEmployeeVacation employeeVacation, String title, int applicationType,
			boolean isPictureAtached, List<Integer> attachmnetIds) throws ParseException {
		ArcUsers user = Utils.findCurrentUser();

		Integer fromId = user.getUserId();
		Integer toId = user.getMgrId();
		//SysProperties paramRecord = (SysProperties) findEntityById(SysProperties.class, 203);
		//Integer toId = Integer.parseInt(paramRecord.getValue());
		// Integer toId = Integer.parseInt(paramRecord.getValue());
		if ((applicationType == MailTypeEnum.VACATION.getValue()) && (employer.getId() != fromId))
			toId = (loadUserById(employer.getId())).getMgrId();
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());

		if (fromId < 1 || toId < 1 || title == null) {
			return;
		}
		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setApplicationType(applicationType);
		arcRecords.setRecTitle(title);
		if (attachmnetIds != null)
			arcRecords.setFilesNo(attachmnetIds.size());
		int recordId = createNewArcRecord(arcRecords, false, toId);

		WrkApplication application = new WrkApplication();
		application.setToUserId(toId);
		application.setApplicationPurpose(MyConstants.VAC_PURPS_SEND_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		String userComment = title;
		createNewWrkApplication(recordId, application, userComment, isPictureAtached, applicationUserDeptJob);
		int vacationId = createHrsVacation(employeeVacation, employer);
		saveHrsSigns(recordId, vacationId, false, null, employer.getId(), applicationType);
		if (attachmnetIds != null) {
			saveAttachs(attachmnetIds, recordId);
		}
	}

	@Override
	@Transactional
	public void SaveHrsGovWorks(HrsGovJobWrks hrsGovJobWrks) {
		try {

			saveObject(hrsGovJobWrks);

		} catch (Exception e) {

		}

	}

	@Override
	@Transactional(readOnly = false)
	public void redirectRecord(WrkApplicationId oldWrkApplicationId, WrkApplication newWrkApplication,
			List<UploadedFile> files, boolean isSecret) {
		// WrkApplicationId wrkId = new WrkApplicationId(WrkId, StepId);
		commonDao.updateWrkApplicationVisible(oldWrkApplicationId);
		WrkApplication App = (WrkApplication) commonDao.findWrkApplicationById(oldWrkApplicationId);
		WrkApplication application = new WrkApplication(App);
		List<WrkApplication> allRecordWrkApplications = commonDao
				.findAllWrkApplicationById(oldWrkApplicationId.getApplicationId());
		for (WrkApplication wrkApplication : allRecordWrkApplications) {
			wrkApplication.setApplicationIsVisible(0);
			wrkApplication.setApplicationIsRead(1);
			commonDao.update(wrkApplication);
		}
		application.setArcRecordId(application.getArcRecordId());
		application.setId(
				new WrkApplicationId(oldWrkApplicationId.getApplicationId(), oldWrkApplicationId.getStepId() + 1));
		application.setToUserId(newWrkApplication.getToUserId());
		application.setApplicationPurpose(newWrkApplication.getApplicationPurpose());
		application.setApplicationUsercomment(newWrkApplication.getUserComment());

		createNewWrkApplication(application.getArcRecordId(), application, null, false, null);

		if (isSecret == true) {
			WrkConfedintialRepliesId wrkConfToId = new WrkConfedintialRepliesId();
			// save WrkConfedintial toId
			wrkConfToId.setGrantedUser(application.getToUserId());
			wrkConfToId.setId(application.getId().getApplicationId());
			wrkConfToId.setStepId(application.getId().getStepId());
			WrkConfedintialReplies wrkConfeplies = new WrkConfedintialReplies();
			wrkConfeplies.setId(wrkConfToId);
			saveObject(wrkConfeplies);
			// save WrkConfedintial fromId
			WrkConfedintialRepliesId wrkConfedintial = new WrkConfedintialRepliesId();
			wrkConfedintial.setGrantedUser(Utils.findCurrentUser().getUserId());
			wrkConfedintial.setId(application.getId().getApplicationId());
			wrkConfedintial.setStepId(application.getId().getStepId());
			WrkConfedintialReplies wrkConfedintialReplies = new WrkConfedintialReplies();
			wrkConfedintialReplies.setId(wrkConfedintial);
			saveObject(wrkConfedintialReplies);
		}
		if (files != null) {
			for (UploadedFile file : files) {
				try {
					Integer attId = saveAttachment(file.getInputstream(), file.getFileName(),
							Utils.findCurrentUser().getUserId(), file.getSize(), 1);
					ArcRecAttId arcRecAttId = new ArcRecAttId();
					arcRecAttId.setAttachId(attId);
					arcRecAttId.setRecordId(application.getArcRecordId());
					ArcRecAtt arcRecAtt = new ArcRecAtt();
					arcRecAtt.setId(arcRecAttId);
					saveObject(arcRecAtt);

				} catch (Exception e) {

				}

			}
		}

	}

	@Override
	@Transactional
	public void redirectRecordCopy(ArcRecords oldRecord, WrkApplication newWrkApplication) {

	}

	// }
	@Override
	// @Transactional()
	public Integer saveInitaionAftrWorl(EmployeeInitiation employeeInitiation) throws VacationAndInitException {
		// TODO Auto-generated method stub
		try {
			return (Integer) commonDao.saveInitaionAftrWorl(employeeInitiation);
		} catch (VacationAndInitException e) {

			throw new VacationAndInitException("ERRRRORRRRRRRR");
		}

	}

	@Override
	public Integer newEmpInitaion(EmployeeInitiation eI) {
		// TODO Auto-generated method stub
		return null;
	}

	private int createHrsVacation(HrEmployeeVacation employeeVacation, Employer employer) {
		ArcUsers user = Utils.findCurrentUser();
		employeeVacation.setCreatedIn(new Date());
		employeeVacation.setCreatedById(user.getUserId());
		employeeVacation.setBasicSalary(employer.getBasicSalary());
		employeeVacation.setEmployeeJob(employer.getJob());
		employeeVacation.setEmployeeJobNum(employer.getJobNumber());
		employeeVacation.setEmployeeRank(employer.getRank());
		employeeVacation.setEmployeeNumber(employer.getEmpNB());

		return save(employeeVacation);
	}

	@Override
	@Transactional
	public ArcUsers findUserFromHrsSigns(int arcRecordId) {
		return commonDao.loadUserByEmpNO(commonDao.getEmpNoHrsVction(commonDao.getDocIdHrsSignByRecId(arcRecordId)));
	}

	@Override
	@Transactional
	public HrEmployeeVacation findVacationByArcRecordId(int arcRecordId) {
		return (HrEmployeeVacation) commonDao.findEntityById(HrEmployeeVacation.class,
				commonDao.getDocIdHrsSignByRecId(arcRecordId));
	}

	@Override
	@Transactional
	public HrsEmployeeTraining findTrainingByArcRecordId(int arcRecordId) {
		return (HrsEmployeeTraining) commonDao.findEntityById(HrsEmployeeTraining.class,
				commonDao.getDocIdHrsSignByRecId(arcRecordId));
	}

	@Override
	@Transactional
	public int findForcedVacationScore(int empNB) {
		return commonDao.findForcedVacationScore(empNB);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void acceptVacation(WrkApplicationId wrkApplicationId, HrEmployeeVacation employeeVacation,
			int vacationType) {
		WrkApplication app = (WrkApplication) commonDao.findWrkApplicationById(wrkApplicationId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(wrkApplicationId);
		application.setId(wrkApplicationId);
		application.getId().setStepId(application.getId().getStepId() + 1);
		application.setApplicationPurpose(getVacNormalPurpId(application.getId().getStepId()));
		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(vacationType, app.getId().getApplicationId(), app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		createNewWrkApplication(application.getArcRecordId(), application, null, false, null);

		boolean visible = (getDestinationModel(vacationType, acceptCount).getSigned() == 1);
		saveHrsSigns(application.getArcRecordId(), employeeVacation.getId(), visible, null,
				Utils.findCurrentUser().getUserId(), vacationType);
		if (visible) {
			employeeVacation.setVacationStatus(MyConstants.YES);

			updateArcRecordsIncomeNo(app.getArcRecordId());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				employeeVacation
						.setExcuseDateHigri(HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(sdf.format(new Date())));
			} catch (ParseException e) {
				logger.error(
						"acceptVacation wrkID" + " " + application.getId().getApplicationId() + " " + e.getMessage());
			}
			employeeVacation.setExcuseNumber(commonDao.createIncomeNo());
			String period = MessageFormat.format(Utils.loadMessagesFromFile("wrk.period"),
					employeeVacation.getHigriVacationStart(), employeeVacation.getHigriVacationEnd());
			commonDao.update(employeeVacation);
			if (employeeVacation.getVacationType() != MyConstants.SICK_VACATION) {
				String title = Utils.loadMessagesFromFile("copy.decision") + app.getApplicationUsercomment() + period;
				List<User> toIds = getListCopyReceivers(employeeVacation.getEmployeeNumber());
				String reportName = "";
				if (vacationType == MailTypeEnum.VACNEEDED.getValue()) {
					reportName = MyConstants.REPORT_FORCED_VACATION;
				} else if (vacationType == MailTypeEnum.VACATION.getValue()) {
					reportName = MyConstants.REPORT_NORMAL_VACATION;
				}
				createCopyHrsDocument(employeeVacation.getId(), title, toIds, reportName);

			}
		}

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void acceptFineRebound(Integer recordId, FineReboundMaster fineReboundMaster, int fineReboundType) {
		WrkApplication app = getWrkApplicationRecordById(recordId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		application.setId(app.getId());
		application.getId().setStepId(application.getId().getStepId() + 1);
		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(fineReboundType, app.getId().getApplicationId(), app.getArcRecordId(),
				2);
		application.setToUserId(userTo);
		createNewWrkApplication(application.getArcRecordId(), application, null, false, null);
		boolean visible = (getDestinationModel(fineReboundType, acceptCount).getSigned() == 1);
		saveHrsSigns(application.getArcRecordId(), fineReboundMaster.getFineId(), visible, null,
				Utils.findCurrentUser().getUserId(), fineReboundType);
		if (visible) {
			fineReboundMaster.setFineSignedBy(1);
			fineReboundMaster.setFineSignedIn(HijriCalendarUtil.findCurrentHijriDate());
			updateArcRecordsIncomeNo(app.getArcRecordId());
			commonDao.update(fineReboundMaster);
		}
	}

	@Override
	@Transactional
	public void acceptStockEntry(StockEntryMaster stockEntryMaster, Integer recordId, int modelType, String usercomment,
			Integer purpose) {
		WrkApplication app = getWrkApplicationRecordById(recordId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		application.setId(app.getId());
		application.setApplicationPurpose(purpose);
		application.getId().setStepId(application.getId().getStepId() + 1);
		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(modelType, app.getId().getApplicationId(), app.getArcRecordId(), 2,
				stockEntryMaster.getStockNo());
		application.setToUserId(userTo);
		createNewWrkApplication(application.getArcRecordId(), application, usercomment, false, null);

		saveHrsSigns(application.getArcRecordId(), stockEntryMaster.getStockEntryMasterId(), true, null,
				Utils.findCurrentUser().getUserId(), modelType);
		boolean visible = (getDestinationModel(modelType, acceptCount).getSigned() == 1);
		if (visible) {
			stockEntryMaster.setStockEntryStatus(MyConstants.YES);
			updateStockEntry(stockEntryMaster);
			//updateArcRecordsIncomeNo(app.getArcRecordId());
		}
	}

	/**
	 * Accept the health licence
	 * 
	 * @param arcRecordId
	 * @param docType
	 * @param healthCertificate
	 */
	@Override
	@Transactional
	public void acceptHealthLicence(Integer arcRecordId, Integer docType, HealthMasterLicence healthCertificate) {
		Integer stepNum = commonDao.getHrsSignNextStep(arcRecordId);
		WrkApplication application = getWrkApplicationRecordById(arcRecordId);
		HrsScenarioDocument scenarioDocument = getScenario(docType, stepNum);
		Integer firstUser = commonDao.getUserIdFromWorkAppByIdAndStepId(arcRecordId, 1);
		Integer userToId = getNextScenarioUserId(docType, application.getId().getApplicationId(), arcRecordId, 2);

		WrkApplicationId applicationId = new WrkApplicationId();
		WrkApplication app = new WrkApplication(application);
		applicationId.setApplicationId(application.getId().getApplicationId());
		applicationId.setStepId(application.getId().getStepId() + 1);
		app.setId(applicationId);
		if (stepNum == 2) {
			app.setToUserId(firstUser);
		} else {
			app.setToUserId(userToId);
		}

		createNewWrkApplication(arcRecordId, app, "", false, null);
		updateWrkApplicationVisible(application.getId());
		Integer docId = commonDao.getDocIdHrsSignByRecId(arcRecordId);
		if (scenarioDocument.getSigned() == 1) {
			saveHrsSigns(arcRecordId, docId, true, "", Utils.findCurrentUser().getUserId(), docType);
			updateArcRecordsIncomeNo(app.getArcRecordId());
			updateHealthLicenceSignature(healthCertificate);
		} else {
			saveHrsSigns(arcRecordId, docId, false, "", Utils.findCurrentUser().getUserId(), docType);
		}

	}

	/**
	 * Update the user that create this licence and the date
	 * 
	 * @param healthCertificate
	 */
	private void updateHealthLicenceSignature(HealthMasterLicence healthCertificate) {
		healthCertificate.setSignedBy(Utils.findCurrentUser().getUserId());
		healthCertificate.setIssueDate(HijriCalendarUtil.findCurrentHijriDate());
		commonDao.update(healthCertificate);
	}

	private List<User> getListCopyReceivers(int empNB) {
		List<User> toIds = new ArrayList<>();
		User employer = commonDao.getUserByEmpNO(empNB);
		toIds.add(employer);
		if (commonDao.checKIsManager(employer.getUserId())) {
			User user = new User();
			user.setWrkRoleId(2);
			user.setDeptId(MyConstants.DEV_DEPT);
			user.setUserId(Integer.parseInt(commonDao.getPropertiesValue(MyConstants.ADMIN_DEV_HEAD)));
			toIds.add(user);
		} else {
			// toIds.add(employer);
		}
		return toIds;
	}

	private void createCopyHrsDocument(int vacationId, String title, List<User> toIds, String fileName) {
		int attachId = 0;
		int applicationId = commonDao.createWrkId();
		for (User toUser : toIds) {
			if (toUser.getWrkRoleId().equals(2) && toUser.getDeptId().equals(MyConstants.DEV_DEPT))
				attachId = saveAttachment(vacationId, fileName);
			else
				attachId = 0;
			saveRecordCopy(applicationId, attachId, title, toUser.getUserId(), fileName);
			++applicationId;
		}
	}

	private void copyOfHrDocumentAndSendReport(int docId, String title, int ToId, String fileName) {
		int attachId = saveAttachment(docId, fileName);
		sendRecordCopy(attachId, title, ToId);
	}

	private void saveRecordCopy(int applicationId, int attachId, String title, int toId, String fileName) {
		ArcUsers user = Utils.findCurrentUser();
		Integer fromId = user.getUserId();
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());
		if (fromId < 1 || toId < 1 || title == null) {
			return;
		}
		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setStructId(user.getStructId());
		arcRecords.setApplicationType(MyConstants.COPY_TYPE);
		arcRecords.setRecTitle(title);
		int recordId = createNewArcRecord(arcRecords, false, toId);
		WrkApplication application = new WrkApplication();
		application.setToUserId(toId);
		application.setId(new WrkApplicationId(applicationId, findStepId(recordId)));
		application.setApplicationPurpose(MyConstants.VAC_PURPS_ISDAR_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		String userComment = title;
		createNewWrkApplication(recordId, application, userComment, true, applicationUserDeptJob);
		if (attachId > 0)
			saveArcRetAtt(recordId, attachId);
	}

	private void saveArcRetAtt(int recordId, int attachId) {
		ArcRecAtt arcRecAtt = new ArcRecAtt();
		ArcRecAttId id = new ArcRecAttId(recordId, attachId);
		arcRecAtt.setId(id);
		arcRecAtt.setIsAdded(1);
		saveObject(arcRecAtt);
	}

	private int saveAttachment(int documentId, String fileName) {
		ArcAttach attach = new ArcAttach();
		String name = "309";
		attach.setAttachName(name);
		attach.setAttachOwner(Utils.findCurrentUser().getUserId());
		attach.setAttachDate(new Date());
		attach.setAttachSize(0d);
		attach.setAttachType(1);
		attach.setAttachCategory("DATA");
		String url = "http://" + findSystemProperty(MyConstants.APP_NAME_NEW)
				+ "/reports/rwservlet?repsrv&report=d:\\archiving\\reports\\" + fileName + ".rdf" + "&PVACSEQ="
				+ documentId;
		attach.setText1(url);
		return save(attach);
	}

	@Override
	@Transactional
	public Integer getUserTo(int modelId, int stepId) {
		Integer userTo = null;
		HrsScenarioDocument scenario = commonDao.getDestinationModel(modelId, stepId);
		userTo = Integer.parseInt(
				((SysProperties) commonDao.findEntityById(SysProperties.class, scenario.getToId())).getValue());
		return userTo;
	}

	@Override
	@Transactional(readOnly = false)
	public HrsScenarioDocument getScenario(int modelId, int stepId) {

		HrsScenarioDocument scenario = commonDao.getDestinationModel(modelId, stepId);
		return scenario;
	}

	// private String getHRResponsibleByTypeVacation(int operationType) {
	//
	// switch (operationType) {
	// case MyConstants.FORCED_VACATION:
	// return MyConstants.HR_FORCED_VACATION_RESPONSIBLE;
	// case MyConstants.NORMAL_VACATION:
	// return MyConstants.HR_NORMAL_VACATION_RESPONSIBLE;
	// }
	// return null;
	// }
	//
	// private String getHRSignerByTypeVacation(int operationType) {
	//
	// switch (operationType) {
	// case MyConstants.FORCED_VACATION:
	// return MyConstants.HR_FORCED_VACATION_SIGNER;
	// case MyConstants.NORMAL_VACATION:
	// return MyConstants.HR_NORMAL_VACATION_SIGNER;
	// }
	// return null;
	// }

	@Override
	@Transactional(readOnly = false)
	public void updateWrkApplication(WrkApplication wrkApplication) {
		commonDao.update(wrkApplication);
	}

	@Override
	@Transactional
	public Integer saveCommentOrUpdate(WrkComment comment, boolean activeUpdate) {
		WrkComment wrkComment = (WrkComment) commonDao.findEntityById(WrkComment.class, comment.getWRKAPPID());
		if (wrkComment != null && wrkComment.getAppNo() != null)
			saveLetterHistory(Integer.parseInt(wrkComment.getAppNo()),
					Utils.convertToEnglishDigits(wrkComment.getLongComment()));
		if (wrkComment == null) {
			comment.setWroteIn(HijriCalendarUtil.findCurrentHijriDate());
			comment.setWroteBy(Utils.findCurrentUser().getUserId());
			comment.setMarkedBy(Utils.findCurrentUser().getUserId());
			comment.setMarkedIn(HijriCalendarUtil.findCurrentHijriDate());
			comment.setAppHdate(HijriCalendarUtil.findCurrentHijriDate());
			save(comment);
		} else {

			wrkComment.setAppTo(comment.getAppTo());
			wrkComment.setAppAttach(comment.getAppAttach());
			wrkComment.setAppPapers(comment.getAppPapers());
			wrkComment.setAppSubject(comment.getAppSubject());
			wrkComment.setFirstCopy(comment.getFirstCopy());
			wrkComment.setSecondCopy(comment.getSecondCopy());
			wrkComment.setCommentType(comment.getCommentType());
			wrkComment.setLongComment(comment.getLongComment());
			if (activeUpdate) {
				wrkComment.setSignedBy(null);
				wrkComment.setSignedIn("");
				wrkComment.setSignType(null);
			} else {
				wrkComment.setSignedBy(comment.getSignedBy());
				wrkComment.setSignedIn(comment.getSignedIn());
			}
			wrkComment.setFontSize(comment.getFontSize());
			commonDao.update(wrkComment);
		}
		return 0;
	}

	@Override
	@Transactional
	public Map<String, Integer> getEmployeeInDeptInfo() {
		return commonDao.getEmployeeInDeptInfo();
	}

	@Override
	@Transactional
	public Map<String, Integer> getEmployeeInfo() {

		return commonDao.getEmployeeInfo();
	}

	@Override
	public List<PayBank> getAllBanks() {
		// TODO Auto-generated method stub
		return commonDao.getAllBanks();
	}

	@Override
	@Transactional
	public void addBankAccountRequest(List<ArcAttach> attachs, int appType, HrLetterRequest request) {
		Integer hrEmployeeIdInteger = getHrResponsibleId();
		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setRecTitle(" Ã˜Â·Ã™â€žÃ˜Â¨ Ã˜ÂªÃ˜Â­Ã™Ë†Ã™Å Ã™â€ž Ã˜Â±Ã˜Â§Ã˜ÂªÃ˜Â¨ Ã˜Â§Ã™â€žÃ™â€° Ã˜Â­Ã˜Â³Ã˜Â§Ã˜Â¨ Ã˜Â¨Ã™â€ Ã™Æ’ " + " Ã™â€¦Ã™â€šÃ˜Â¯Ã™â€¦ Ã™â€¦Ã™â€  "
				+ Utils.findCurrentUser().getEmployeeName());
		arcRecord.setApplicationType(appType);
		Integer recordId = createNewArcRecord(arcRecord, false, hrEmployeeIdInteger);
		// Integer cc = getNextScenarioUserId(appType, WrkId, recordId,
		// Utils.findCurrentUser().getUserId());

		WrkApplication application = new WrkApplication();
		// application.setId(new WrkApplicationId(commonDao.createWrkId(),
		// findStepId(recordId)));
		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		application.setToUserId(hrEmployeeIdInteger);

		WrkApplicationId applicationId = createNewWrkApplication(recordId, application,
				" Ã˜Â·Ã™â€žÃ˜Â¨ Ã˜ÂªÃ˜Â­Ã™Ë†Ã™Å Ã™â€ž Ã˜Â±Ã˜Â§Ã˜ÂªÃ˜Â¨ Ã˜Â§Ã™â€žÃ™â€° Ã˜Â­Ã˜Â³Ã˜Â§Ã˜Â¨ Ã˜Â¨Ã™â€ Ã™Æ’ " + "  Ã™â€¦Ã™â€šÃ˜Â¯Ã™â€¦ Ã™â€¦Ã™â€  "
						+ Utils.findCurrentUser().getEmployeeName(),
				true, null);

		List<Integer> attachmentIds = addAttachments(attachs);
		for (Integer id : attachmentIds) {
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(id);
			arcRecAttId.setRecordId(recordId);
			arcRecAtt.setId(arcRecAttId);

			saveObject(arcRecAtt);

		}
		Integer requestId = (Integer) saveObject(request);
		saveHrsSigns(recordId, requestId, false, null, Utils.findCurrentUser().getUserId(), appType);

	}

	public Integer getHrResponsibleId() {
		String hrEmployeeId = commonDao.getPropertiesValue(MyConstants.HR_SALARY_RESPONSIBLE);
		Integer hrEmployeeIdInteger = ((hrEmployeeId != null) ? Integer.parseInt(hrEmployeeId.trim()) : null);
		return hrEmployeeIdInteger;
	}

	@Override
	public Integer getResponsibleId(String JobDescribtion) {
		String hrEmployeeId = commonDao.getPropertiesValue(JobDescribtion);
		Integer hrEmployeeIdInteger = ((hrEmployeeId != null) ? Integer.parseInt(hrEmployeeId.trim()) : null);
		return hrEmployeeIdInteger;
	}

	@Override
	public void updateObject(Object object) {
		commonDao.update(object);

	}

	@Override
	public void updateObject(Object object1, Object object2) {
		commonDao.update(object1, object2);

	}

	@Override
	public void updateObject(Object object1, Object object2, Object object3) {
		commonDao.update(object1, object2, object3);

	}

	@Override
	@Transactional
	public void requsetHrLetter(String purpose, String destination, int appType, Integer docType,
			HrLetterRequest request) {
		Integer hrEmployeeIdInteger = getHrResponsibleId();
		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setRecTitle(
				Utils.loadMessagesFromFile("letter.request") + " " + Utils.findCurrentUser().getEmployeeName());
		arcRecord.setApplicationType(appType);
		Integer recordId = createNewArcRecord(arcRecord, false, hrEmployeeIdInteger);

		WrkApplication application = new WrkApplication();
		// application.setId(new WrkApplicationId(commonDao.createWrkId(),
		// findStepId(recordId)));
		application.setToUserId(hrEmployeeIdInteger);
		createNewWrkApplication(recordId, application,

				Utils.loadMessagesFromFile("letter.request") + Utils.findCurrentUser().getEmployeeName(), true, null);
		Integer requestId = (Integer) saveObject(request);
		saveHrsSigns(recordId, requestId, false, null, Utils.findCurrentUser().getUserId(), docType);

	}

	@Override
	@Transactional
	public WrkComment signComment(WrkApplication wrkApplication, String signType, Integer recieverUserId,List<String> commentCopyReciever) {
		System.out.println("00000000"+wrkApplication.getId().getApplicationId());
		System.out.println("11111111"+wrkApplication.getId().getStepId());
		ArcRecords arcRecords = (ArcRecords) commonDao.findEntityById(ArcRecords.class,
				wrkApplication.getArcRecordId());
		arcRecords.setIncomeHDate(HijriCalendarUtil.findCurrentHijriDate());
		if (arcRecords.getIncomeNo() == null)
			arcRecords.setIncomeNo(commonDao.createIncomeNo());
		
		commonDao.update(arcRecords);

		
//		sendWrkApplication(wrkId, application, secretFlag, WrkCopyEmpRecievers,
//				WrkCopyMngRecievers);
	//	redirectWrkAppCopies(wrkId, WrkCopyEmpRecievers, WrkCopyMngRecievers);
		
		WrkComment wrkComment = (WrkComment) commonDao.findEntityById(WrkComment.class,
				wrkApplication.getId().getApplicationId());
		if ((wrkComment.getMarkedBy() != null) && (wrkComment.getMarkedBy() <= 0)) {
			wrkComment.setMarkedBy(Utils.findCurrentUser().getUserId());
			wrkComment.setMarkedIn(HijriCalendarUtil.findCurrentHijriDate());
		}
		wrkComment.setSignedBy(Utils.findCurrentUser().getUserId());
		wrkComment.setSignedIn(HijriCalendarUtil.findCurrentHijriDate());
		wrkComment.setSignType(signType);
		wrkComment.setAppNo(arcRecords.getIncomeNo());
		commonDao.update(wrkComment);

		for (WrkApplication wrkApp : commonDao.findAllWrkApplicationById(wrkApplication.getId().getApplicationId())) {
			wrkApp.setApplicationIsVisible(0);
			wrkApp.setApplicationIsRead(1);
			updateObject(wrkApp);
		}

		WrkApplicationId wrkApplicationId = new WrkApplicationId(wrkApplication.getId().getApplicationId(),
				wrkApplication.getId().getStepId() + 1);
		WrkApplication newWrkApp = new WrkApplication();
		newWrkApp.setId(wrkApplicationId);
		newWrkApp.setFromUserId(Utils.findCurrentUser().getUserId());
		if (recieverUserId == 0)
			recieverUserId = wrkComment.getMarkedBy();
		newWrkApp.setToUserId(recieverUserId);
		newWrkApp.setApplicationUsercomment("تم توقيع الخطاب بتاريخ " + HijriCalendarUtil.findCurrentHijriDate()
		+ " بواسطة  " + Utils.findCurrentUser().getFirstName() + " يرجي تصدير المعاملة  ");
		newWrkApp.setApplicationCreateDate(new Date());
		newWrkApp.setApplicationCreateTime(HijriCalendarUtil.findCurrentTime());
		newWrkApp.setHijriDate(HijriCalendarUtil.findCurrentHijriDate());
		newWrkApp.setApplicationIsRead(0);
		newWrkApp.setApplicationPurpose(27);
		newWrkApp.setApplicationIsVisible(1);
		newWrkApp.setApplicationIsReply(1);
		newWrkApp.setArcRecordId(wrkApplication.getArcRecordId());
		List<Integer> commentCopyRec=new ArrayList<Integer>();
		for (String userid : commentCopyReciever) {
			commentCopyRec.add(Integer.parseInt(userid));
		}
	
		if (commentCopyReciever != null) {
		//	for (String userid : commentCopyReciever) {
				redirectWrkAppCopies(wrkApplication, commentCopyRec,null);
		//		sendCommentCopy(Utils.findCurrentUser().getUserId(), Integer.parseInt(userid),
			//			wrkComment.getWRKAPPID().toString(), wrkApplication.getId().getApplicationId().toString());

	//		}
		}
		//redirectWrkAppCopies(newWrkApp, commentCopyReciever, null);
		saveObject(newWrkApp);
		return wrkComment;

		// }

	}

	@Override
	@Transactional
	public void loadNormalVacation(int empNB, String firstApplicationDate, String gregBirthDate, Integer userid,
			HrEmployeeVacation employeeVacation) {
		// int x = commonDao.loadVacationSumLastYear(empNB);
		employeeVacation.setVacationType(MyConstants.NORMAL_VACATION);
		// employeeVacation.setTotalVacationPeriod(getTotalVacationPeriod(empNB,
		// firstApplicationDate));
		employeeVacation.setTotalVacationPeriod(getTotalVacationSold((empNB)));
		employeeVacation.setUsedTotalVacationPeriod(getUsedTotalVacationPeriod(empNB));
		// employeeVacation.setVacationPeriodThisYear(getVacationPeriodThisYear());
		employeeVacation.setRemainTotalVacationPeriod(
				(int) employeeVacation.getTotalVacationPeriod() - (employeeVacation.getUsedTotalVacationPeriod()));
		// + employeeVacation.getVacationPeriodThisYear()));

		employeeVacation.setUsedNormalYearlyPeriod(commonDao.getUsedNormalYearlyPeriod(empNB));
		int maxYearlyPeriod = 144;
		// getMaxYearlyNormalYearlyPeriod(firstApplicationDate, gregBirthDate);
		employeeVacation.setRemainNormalYearlyPeriod(getRemainNormalYearlyPeriod(getMaxYearlyNormalYearlyPeriod(firstApplicationDate, gregBirthDate),
				employeeVacation.getUsedNormalYearlyPeriod(), employeeVacation.getRemainTotalVacationPeriod()));
		// employeeVacation
		// .setRemainNormalYearlyPeriod(getRemainNormalYearlyPeriod(employeeVacation.getVacationPeriodThisYear(),
		// employeeVacation.getUsedNormalYearlyPeriod(),
		// employeeVacation.getRemainTotalVacationPeriod()));

		HrEmployeeVacation lastNormalVacation = commonDao.loadLastNormalVacation(empNB);
		if (lastNormalVacation == null)
			lastNormalVacation = new HrEmployeeVacation();
		employeeVacation.setLastVacationDate(lastNormalVacation.getHigriVacationStart());
		employeeVacation.setLastVacationPeriod(lastNormalVacation.getVacationPeriod());
		employeeVacation.setLastVacationFrom(lastNormalVacation.getHDatevacationFrom());
		employeeVacation.setLastVacationTo(lastNormalVacation.getHDateVacationTo());
		employeeVacation.setLastVacationStart(lastNormalVacation.getHigriVacationStart());
		employeeVacation.setLastVacationEnd(lastNormalVacation.getHigriVacationEnd());
		employeeVacation.setPeriodLessFive(checkPeriodVacation(empNB, userid));
		// employeeVacation.setVacationCalc((HrsVacationCalc)
		// commonDao.findEntityById(HrsVacationCalc.class, empNB));
	}

	// private int getRemainNormalYearlyPeriod(int vacPeriodThisYear, int
	// usedNormalYearlyPeriod,
	// int remainTotalVacationPeriod) {
	// int remainPeriod = 0;
	//// remainPeriod = maxYearlyPeriod - usedNormalYearlyPeriod;
	//// remainPeriod = 120 - usedNormalYearlyPeriod;
	//
	// if (usedNormalYearlyPeriod < 120) {
	// remainPeriod = 120 - usedNormalYearlyPeriod;
	// if (usedNormalYearlyPeriod < remainTotalVacationPeriod +
	// vacPeriodThisYear){
	// if (remainPeriod > remainTotalVacationPeriod + vacPeriodThisYear) {
	// remainPeriod = remainTotalVacationPeriod + vacPeriodThisYear;
	//// return remainPeriod;
	// } else {
	//// return remainPeriod;
	// }
	// } else {
	// remainPeriod= 0;
	// }
	// // }
	// // else {return 0;}
	//
	// }
	// return remainPeriod;
	// }
	private int getRemainNormalYearlyPeriod(int maxYearlyPeriod, int usedNormalYearlyPeriod,
			int remainTotalVacationPeriod) {
		// int remainPeriod = 0;
		int x = Math.min(maxYearlyPeriod - usedNormalYearlyPeriod, remainTotalVacationPeriod);
		// remainPeriod = maxYearlyPeriod - usedNormalYearlyPeriod;
		// if(remainPeriod>remainTotalVacationPeriod)
		// remainPeriod = remainTotalVacationPeriod;
		// return remainPeriod;
		return x;
	}

	private int getMaxYearlyNormalYearlyPeriod(String firstApplicationDate, String gregBirthDate) {
		int period = 0;
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date birthDate = sdf.parse(gregBirthDate);
			// int years = Utils.getDiffYears(birthDate, now);
			final double DAY_MILLIS = 1000.0 * 24.0 * 60.0 * 60.0;
			double days = (int) ((now.getTime() - birthDate.getTime()) / (DAY_MILLIS));

			int age = (int) (days / 354);

			Date firsApplication = sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(firstApplicationDate));

			days = (int) ((now.getTime() - firsApplication.getTime()) / (DAY_MILLIS));
			int srvs = (int) (days / 354);

			if ((int) age >= 50 || srvs >= 25)
				period = 144;
			else
				period = 108;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return period;
	}

	@Override
	@Transactional
	public int getTotalVacationPeriod(int empNB, String firstApplicationDate) {
		int totalVacation = 0;
		String terminateDate = commonDao.getExecutionDateTermnate(empNB);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date now = new Date();
		// String finishDate = sdf.format(now);
		 String finishDate = "21/09/2017";
		try {
			if (terminateDate != null)
				finishDate = HijriCalendarUtil.ConvertHijriTogeorgianDate(terminateDate);
			// String endDate =
			// "30"+"/"+"12"+"/"+(Integer.parseInt(finishDate.substring(6)) - 1
			// );
			String endHijriDate = HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(finishDate);
			// endHijriDate = "30" + "/" + "12" + "/" +
			// (Integer.parseInt(endHijriDate.substring(6)) - 1);
			// Date start =
			// sdf.parse((HijriCalendarUtil.ConvertHijriTogeorgianDate(firstApplicationDate)));
			// Date end = sdf.parse(finishDate);
			// final double DAY_MILLIS = 1000.0 * 24.0 * 60.0 * 60.0;
			if (Utils.reverseDateToNumber(firstApplicationDate) >= 14260515) {
				totalVacation = (int) (Utils.calculateDiffDaysHijriDate(firstApplicationDate, endHijriDate) * 0.1);// (int)
																													// (((end.getTime()
																													// 0.1);
			} else if (Utils.reverseDateToNumber(endHijriDate) < 14260515) {
				// totalVacation = (int) ((((end.getTime() - start.getTime()) /
				// (DAY_MILLIS))) * (2.5 / 30));
				totalVacation = (int) (Utils.calculateDiffDaysHijriDate(firstApplicationDate, endHijriDate) / 30 * 2.5);
			} else {
				// double nbTenDaysBefore =
				// ((((sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate("15/05/1426"))
				// .getTime() - start.getTime()) / (DAY_MILLIS))) * (2.5 / 30));
				double nbTenDaysBefore = Utils.calculateDiffDaysHijriDate(firstApplicationDate, "15/05/1426") / 30
						* 2.5;
				// double nbTenDaysAfter = (((sdf.parse(finishDate).getTime()
				// -
				// (sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate("15/05/1426")).getTime()))
				// / (DAY_MILLIS)) * 0.1);

				double nbTenDaysAfter = Utils.calculateDiffDaysHijriDate("15/05/1426", endHijriDate) * 0.1;

				totalVacation = (int) (nbTenDaysBefore + nbTenDaysAfter);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer new_vac_period = commonDao
				.getNewVacPeriod(Integer.parseInt(utilities.HijriCalendarUtil.findCurrentYear()));

		return totalVacation + new_vac_period;
		// return totalVacation + getVacationPeriodThisYear();
	}

	// private int getVacationPeriodThisYear() {
	// int totalVacation = 0;
	// // String terminateDate = commonDao.getExecutionDateTermnate(empNB);
	// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	// Date now = new Date();
	// String finishDate = sdf.format(now);
	// try {
	// // if (terminateDate != null)
	// // finishDate =
	// // HijriCalendarUtil.ConvertHijriTogeorgianDate(terminateDate);
	// String endHijriDate =
	// HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(finishDate);
	// String firstDateThisYear = "01" + "/" + "01" + "/" +
	// (Integer.parseInt(endHijriDate.substring(6)));
	//
	// // Date start =
	// //
	// sdf.parse((HijriCalendarUtil.ConvertHijriTogeorgianDate(firstApplicationDate)));
	// // Date end = sdf.parse(finishDate);
	// final double DAY_MILLIS = 1000.0 * 24.0 * 60.0 * 60.0;
	// // if (Utils.reverseDateToNumber(firstApplicationDate) >= 14260515)
	// // {
	// totalVacation = (int)
	// (Utils.calculateDiffDaysHijriDate(firstDateThisYear, endHijriDate) *
	// 0.1);// (int)
	// // (((end.getTime()
	// // -
	// // start.getTime())
	// // /
	// // (DAY_MILLIS))
	// // *
	// // 0.1);
	// }
	// // else if (Utils.reverseDateToNumber(endHijriDate) < 14260515) {
	// // // totalVacation = (int) ((((end.getTime() - start.getTime()) /
	// // // (DAY_MILLIS))) * (2.5 / 30));
	// // totalVacation = (int)
	// // (Utils.calculateDiffDaysHijriDate(firstApplicationDate, endHijriDate)
	// // / 30 * 2.5);
	// // } else {
	// // // double nbTenDaysBefore =
	// // //
	// //
	// ((((sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate("15/05/1426"))
	// // // .getTime() - start.getTime()) / (DAY_MILLIS))) * (2.5 / 30));
	// // double nbTenDaysBefore =
	// // Utils.calculateDiffDaysHijriDate(firstApplicationDate, "15/05/1426")
	// // / 30
	// // * 2.5;
	// // // double nbTenDaysAfter = (((sdf.parse(finishDate).getTime()
	// // // -
	// // //
	// //
	// (sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate("15/05/1426")).getTime()))
	// // // / (DAY_MILLIS)) * 0.1);
	// //
	// // double nbTenDaysAfter =
	// // Utils.calculateDiffDaysHijriDate("15/05/1426", endHijriDate) * 0.1;
	//
	// // totalVacation = (int) (nbTenDaysBefore );
	// // }
	// // }
	// catch (ParseException e) {
	// e.printStackTrace();
	// }
	// return totalVacation;
	// }

	private int getUsedTotalVacationPeriod(int empNB) {
		return commonDao.getUsedTotalVacationPeriod(empNB);
	}

	@Override
	@Transactional
	public String getLastServiceDate(Integer empNB) {
		return commonDao.getLastServiceDate(empNB);
	}

	@Override
	public HrsEmpHistorical getEmployeeLastHistoryRecord(Integer id) {

		return commonDao.getHRsEmpHistoricalByEmpNo(id);
	}

	@Override
	@Transactional
	public void updateWrkApplicationVisible(WrkApplicationId wrkApplicationId) {
		commonDao.updateWrkApplicationVisible(wrkApplicationId);

	}

	@Override
	public void creatCopyOfTransaction(int applicationId, int attachId, String title, int toId, String fileName) {
		saveRecordCopy(applicationId, attachId, title, toId, fileName);

	}

	@Override
	@Transactional
	public Integer getUserIdFromWorkAppByIdAndStepId(Integer recordId, Integer stepId) {
		return commonDao.getUserIdFromWorkAppByIdAndStepId(recordId, stepId);
	}

	@Override
	@Transactional
	public List<HrsTrainingPlace> loadTrainingPlaces() {
		return commonDao.loadTrainingPlaces();
	}

	public void addNewHrMedicalRequest(HrMedicalRequest medicalRequest, Integer createdForId, Integer createdById) {

		medicalRequest.setCreatedBy(createdById);
		medicalRequest.setCreatedIn(new Date());

		medicalRequest.setDemandForUserId(createdForId);
		Integer medicalRepotrtId = (Integer) saveObject(medicalRequest);

		ArcUsers user = Utils.findCurrentUser();
		Integer fromId = user.getUserId();
		Integer toId = user.getMgrId();
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());

		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setApplicationType(MailTypeEnum.MEDICALREPORT.getValue());
		arcRecord.setRecTitle(
				MessageFormat.format(Utils.loadMessagesFromFile("employee.report.medical"), user.getEmployeeName()));
		int recordId = createNewArcRecord(arcRecord, false, toId);

		WrkApplication application = new WrkApplication();
		application.setToUserId(toId);
		// application.setId(new WrkApplicationId(commonDao.createWrkId(),
		// findStepId(recordId)));

		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);

		// application.setApplicationUserDeptJob(applicationUserDeptJob);
		String userComment = MessageFormat.format(Utils.loadMessagesFromFile("employee.report.medical"),
				user.getEmployeeName());
		createNewWrkApplication(recordId, application, userComment, false, applicationUserDeptJob);

		saveHrsSigns(recordId, medicalRepotrtId, false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.MEDICALREPORT.getValue());

	}

	@Override
	@Transactional
	public HrMedicalRequest getMedicalRequestInfoByArchRecordId(Integer archRecordId) {

		Integer DocId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		return (HrMedicalRequest) commonDao.findEntityById(HrMedicalRequest.class, DocId);
	}

	@Override
	@Transactional
	public EmployeeInitiation getEmployeeInitiationByArchrecorId(Integer archRecordId) {

		Integer DocId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		return (EmployeeInitiation) commonDao.findEntityById(EmployeeInitiation.class, DocId);
	}

	@Override
	@Transactional
	public HrLetterRequest getHrRequsetByArchrecorId(Integer archRecordId) {

		Integer DocId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		return (HrLetterRequest) commonDao.findEntityById(HrLetterRequest.class, DocId);
	}

	@Override
	@Transactional
	public void acceptActionforHrLetter(Integer archRecordId, Integer docType, HrLetterRequest letterRequest) {
		Integer stepNum = commonDao.getHrsSignNextStep(archRecordId);
		WrkApplication application = getWrkApplicationRecordById(archRecordId);
		HrsScenarioDocument scenarioDocument = getScenario(docType, stepNum);
		Integer userToId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 2);
		Integer fromId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 1);
		Integer ownerOfDemand = getUserIdFromWorkAppByIdAndStepId(archRecordId, 1);
		// Integer userTo = Integer.parseInt(
		// ((SysProperties) commonDao.findEntityById(SysProperties.class,
		// scenarioDocument.getToId())).getValue());

		WrkApplicationId applicationId = new WrkApplicationId();
		WrkApplication app = new WrkApplication(application);
		applicationId.setApplicationId(application.getId().getApplicationId());
		applicationId.setStepId(application.getId().getStepId() + 1);
		app.setId(applicationId);

		app.setToUserId(userToId);
		createNewWrkApplication(archRecordId, app, "", false, null);

		updateWrkApplicationVisible(application.getId());

		Integer docId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		if (scenarioDocument.getSigned() == 1) {
			letterRequest.setAcceptStatus(MyConstants.YES);
			updateObject(letterRequest);
			saveHrsSigns(archRecordId, docId, true, "", Utils.findCurrentUser().getUserId(), docType);
			// copyOfHrDocumentAndSendReport(docId, " Ã˜ÂªÃ™â€¦Ã˜Âª
			// Ã˜Â§Ã™â€žÃ™â€¦Ã™Ë†Ã˜Â§Ã™ï¿½Ã™â€šÃ˜Â©
			// Ã˜Â¹Ã™â€žÃ™â€° Ã˜Â·Ã™â€žÃ˜Â¨Ã™Æ’
			// Ã˜Â§Ã™â€žÃ˜Â®Ã˜Â§Ã˜Âµ Ã˜Â¨Ã˜ÂªÃ˜Â¹Ã˜Â±Ã˜Â¨Ã™ï¿½
			// Ã˜Â§Ã™â€žÃ˜Â±Ã˜Â§Ã˜ÂªÃ˜Â¨ ", ownerOfDemand,
			// MyConstants.REPORT_SALAR);

			updateArcRecordsIncomeNo(app.getArcRecordId());

		} else {
			saveHrsSigns(archRecordId, docId, false, "", Utils.findCurrentUser().getUserId(), docType);
		}

	}

	@Override
	@Transactional
	public void acceptActionforHrmedicalReport(Integer archRecordId, Integer docType, HrMedicalRequest medicalRequest) {
		Integer stepNum = commonDao.getHrsSignNextStep(archRecordId);
		WrkApplication application = getWrkApplicationRecordById(archRecordId);
		HrsScenarioDocument scenarioDocument = getScenario(docType, stepNum);
		Integer userToId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 2);

		// Integer fromId = getNextScenarioUserId(docType,
		// application.getId().getApplicationId(), archRecordId, 1);
		Integer ownerOfDemand = getUserIdFromWorkAppByIdAndStepId(archRecordId, 1);

		// Integer userTo = Integer.parseInt(
		// ((SysProperties) commonDao.findEntityById(SysProperties.class,
		// scenarioDocument.getToId())).getValue());

		WrkApplicationId applicationId = new WrkApplicationId();
		WrkApplication app = new WrkApplication(application);
		applicationId.setApplicationId(application.getId().getApplicationId());
		applicationId.setStepId(application.getId().getStepId() + 1);
		app.setId(applicationId);

		app.setToUserId(userToId);
		createNewWrkApplication(archRecordId, app, "", false, null);

		updateWrkApplicationVisible(application.getId());

		Integer docId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		if (scenarioDocument.getSigned() == 1) {
			medicalRequest.setAcceptStatus(MyConstants.YES);
			updateObject(medicalRequest);
			saveHrsSigns(archRecordId, docId, true, "", Utils.findCurrentUser().getUserId(), docType);
			updateArcRecordsIncomeNo(app.getArcRecordId());
			// copyOfHrDocumentAndSendReport(docId, " Ã˜ÂªÃ™â€¦Ã˜Âª
			// Ã˜Â§Ã™â€žÃ™â€¦Ã™Ë†Ã˜Â§Ã™ï¿½Ã™â€šÃ˜Â©
			// Ã˜Â¹Ã™â€žÃ™â€° Ã˜Â·Ã™â€žÃ˜Â¨Ã™Æ’
			// Ã˜Â§Ã™â€žÃ˜Â®Ã˜Â§Ã˜Âµ Ã˜Â¨Ã˜Â§Ã™â€žÃ˜ÂªÃ™â€šÃ˜Â±Ã™Å Ã˜Â±
			// Ã˜Â§Ã™â€žÃ˜Â·Ã˜Â¨Ã™â€° ", ownerOfDemand,
			// MyConstants.REPORT_MEDICAL);

		} else {
			saveHrsSigns(archRecordId, docId, false, "", Utils.findCurrentUser().getUserId(), docType);
		}

	}

	@Override
	@Transactional
	public void acceptActionForEmployeeInitiation(Integer archRecordId, Integer docType, Integer vacationId,
			EmployeeInitiation employeeInitiation) {
		Integer stepNum = commonDao.getHrsSignNextStep(archRecordId);
		WrkApplication application = getWrkApplicationRecordById(archRecordId);
		HrsScenarioDocument scenarioDocument = getScenario(docType, stepNum);
		Integer userToId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 2);
		Integer fromId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 1);
		Integer ownerOfDemand = getUserIdFromWorkAppByIdAndStepId(archRecordId, 1);
		// Integer userTo = Integer.parseInt(
		// ((SysProperties) commonDao.findEntityById(SysProperties.class,
		// scenarioDocument.getToId())).getValue());

		WrkApplicationId applicationId = new WrkApplicationId();
		WrkApplication app = new WrkApplication(application);
		applicationId.setApplicationId(application.getId().getApplicationId());
		applicationId.setStepId(application.getId().getStepId() + 1);
		app.setId(applicationId);

		app.setToUserId(userToId);
		createNewWrkApplication(archRecordId, app, "", false, null);

		updateWrkApplicationVisible(application.getId());

		Integer docId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		if (scenarioDocument.getSigned() == 1) {
			saveHrsSigns(archRecordId, docId, true, "", Utils.findCurrentUser().getUserId(), docType);
			commonDao.updateVacationAfterInit(vacationId);
			updateObject(employeeInitiation);
			// sendRecordCopy(0, "Ã˜ÂªÃ™â€¦ Ã™â€šÃ˜Â¨Ã™Ë†Ã™â€ž Ã˜Â·Ã™â€žÃ˜Â¨Ã™Æ’
			// Ã˜Â¨Ã™â€¦Ã˜Â¨Ã˜Â§Ã˜Â´Ã˜Â±Ã˜Â©
			// Ã˜Â§Ã™â€žÃ˜Â¹Ã™â€¦Ã™â€ž ", ownerOfDemand);
			updateArcRecordsIncomeNo(app.getArcRecordId());
			// copyOfHrDocumentAndSendReport(docId, " Ã˜ÂªÃ™â€¦Ã˜Âª
			// Ã˜Â§Ã™â€žÃ™â€¦Ã™Ë†Ã˜Â§Ã™ï¿½Ã™â€šÃ˜Â©
			// Ã˜Â¹Ã™â€žÃ™â€° Ã˜Â·Ã™â€žÃ˜Â¨Ã™Æ’
			// Ã˜Â§Ã™â€žÃ˜Â®Ã˜Â§Ã˜Âµ Ã˜Â¨Ã˜Â§Ã™â€žÃ™â€¦Ã˜Â¨Ã˜Â§Ã˜Â´Ã˜Â±Ã˜Â© ",
			// ownerOfDemand,
			// MyConstants.REPORT_MEDICAL);
			// sendRecordCopy(0, "Ã˜ÂªÃ™â€¦ Ã™â€šÃ˜Â¨Ã™Ë†Ã™â€ž Ã˜Â·Ã™â€žÃ˜Â¨Ã™Æ’
			// Ã˜Â¨Ã™â€¦Ã˜Â¨Ã˜Â§Ã˜Â´Ã˜Â±Ã˜Â©
			// Ã˜Â§Ã™â€žÃ˜Â¹Ã™â€¦Ã™â€ž ", ownerOfDemand);
		} else {
			saveHrsSigns(archRecordId, docId, false, "", Utils.findCurrentUser().getUserId(), docType);
		}

	}

	private void sendRecordCopy(int attachId, String title, int toId) {
		ArcUsers user = Utils.findCurrentUser();
		Integer fromId = user.getUserId();
		// int applicationUserDeptJob =
		// getUserDeptJob(user.getManager().getUserId());
		if (fromId < 1 || toId < 1 || title == null) {
			return;
		}
		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setStructId(user.getStructId());
		arcRecords.setApplicationType(MyConstants.COPY_TYPE);
		arcRecords.setRecTitle(title);
		int recordId = createNewArcRecord(arcRecords, false, toId);
		int wrkAppId = commonDao.createWrkId();
		WrkApplication application = new WrkApplication();
		application.setToUserId(toId);

		// application.setId(new WrkApplicationId(wrkAppId,
		// findStepId(recordId)));
		application.setApplicationPurpose(MyConstants.EXPLAIN_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		// application.setApplicationUserDeptJob(applicationUserDeptJob);

		createNewWrkApplication(recordId, application, title, true, wrkAppId);
		if (attachId != 0) {
			saveArcRetAtt(recordId, attachId);
		}

	}

	@Override
	@Transactional
	public void acceptActionFortransferSalary(Integer archRecordId, Integer docType, HrLetterRequest hrLetterRequest) {
		Integer stepNum = commonDao.getHrsSignNextStep(archRecordId);
		WrkApplication application = getWrkApplicationRecordById(archRecordId);
		HrsScenarioDocument scenarioDocument = getScenario(docType, stepNum);
		Integer userToId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 2);
		Integer fromId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 1);
		Integer ownerOfDemand = getUserIdFromWorkAppByIdAndStepId(archRecordId, 1);
		// Integer userTo = Integer.parseInt(
		// ((SysProperties) commonDao.findEntityById(SysProperties.class,
		// scenarioDocument.getToId())).getValue());

		WrkApplicationId applicationId = new WrkApplicationId();
		WrkApplication app = new WrkApplication(application);
		applicationId.setApplicationId(application.getId().getApplicationId());
		applicationId.setStepId(application.getId().getStepId() + 1);
		app.setId(applicationId);

		app.setToUserId(userToId);
		createNewWrkApplication(archRecordId, app, "", false, null);

		updateWrkApplicationVisible(application.getId());

		Integer docId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		if (scenarioDocument.getSigned() == 1) {
			Integer fromUserId = getUserIdFromWorkAppByIdAndStepId(archRecordId, 1);
			ArcUsers fromUser = (ArcUsers) findEntityById(ArcUsers.class, fromUserId);
			HrsMasterFile currentInfoForRequest = (HrsMasterFile) findEntityById(HrsMasterFile.class,
					fromUser.getEmployeeNumber());

			currentInfoForRequest.setBankId(hrLetterRequest.getBankId());
			currentInfoForRequest.setAccountNumber(hrLetterRequest.getIbanNumber());
			hrLetterRequest.setAcceptStatus(MyConstants.YES);
			updateObject(hrLetterRequest);
			updateObject(currentInfoForRequest);
			saveHrsSigns(archRecordId, docId, true, "", Utils.findCurrentUser().getUserId(), docType);
			copyOfHrDocumentAndSendReport(docId,
					" Ã˜ÂªÃ™â€¦Ã˜Âª Ã˜Â§Ã™â€žÃ™â€¦Ã™Ë†Ã˜Â§Ã™ï¿½Ã™â€šÃ˜Â© Ã˜Â¹Ã™â€žÃ™â€° Ã˜Â·Ã™â€žÃ˜Â¨Ã™Æ’ Ã˜Â§Ã™â€žÃ˜Â®Ã˜Â§Ã˜Âµ Ã˜Â¨Ã˜ÂªÃ˜Â­Ã™Ë†Ã™Å Ã™â€ž Ã˜Â§Ã™â€žÃ˜Â±Ã˜Â§Ã˜ÂªÃ˜Â¨  ", ownerOfDemand,
					MyConstants.REPORT_SALARY);
			updateArcRecordsIncomeNo(app.getArcRecordId());

		} else {
			saveHrsSigns(archRecordId, docId, false, "", Utils.findCurrentUser().getUserId(), docType);
		}

	}

	@Override
	@Transactional
	public HrsTrainingMandate loadTrainingMandate(int rank) {
		return commonDao.loadTrainingMandate(rank);
	}

	@Override
	@Transactional
	public Integer getWrkCountSigned(Integer arcRecordId) {
		Integer acceptCount = commonDao.getHrsSignNextStep(arcRecordId);
		return acceptCount;
	}

	@Override
	@Transactional
	public HrsScenarioDocument getDestinationModel(int modelId, int stepId) {
		return commonDao.getDestinationModel(modelId, stepId);
	}

	@Override
	public List<Integer> getAuthorizedEmployeesForModel(int modelId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public ArcUsers findUserFromHrsTrain(int arcRecordId) {
		return commonDao.loadUserByEmpNO(commonDao.getEmpNoTraining(commonDao.getDocIdHrsSignByRecId(arcRecordId)));
	}

	@Override
	public String getPropertiesValueById(int id) {
		return commonDao.getPropertiesValueById(id);
	}

	@Override
	public WrkApplication findWrkApplicationById(WrkApplicationId wrkApplicationId) {

		try {

			return commonDao.findWrkApplicationById(wrkApplicationId);
		} catch (Exception e) {
			logger.error("findWrkApplicationById   wrkApplicationId  :" + wrkApplicationId.getApplicationId());
		}
		return null;
	}

	@Override
	@Transactional
	public int getNextScenarioUserId(int modelTypeId, int WrkId, int arcRecordId, int modelStepUserId,
			Integer... storeId) {
		try {
			Integer countSigned = getWrkCountSigned(arcRecordId);

			HrsScenarioDocument currentModel = getDestinationModel(modelTypeId, countSigned.intValue());
			if (currentModel == null)
				return 0;
			String propertiesValueById = null;

			if (modelStepUserId == 1)
				propertiesValueById = getPropertiesValueById(currentModel.getFromId());
			else
				propertiesValueById = getPropertiesValueById(currentModel.getToId());

			boolean isFunction = false;
			int fromOfModelStep = 0;
			try {
				fromOfModelStep = Integer.parseInt(propertiesValueById);
			} catch (Exception e) {
				isFunction = true;
			}
			if (isFunction) {
				if (storeId.length > 0 && !storeId[0].equals(0) && propertiesValueById.equals("STORE_DEAN")) {// store
																												// model
					propertiesValueById = getPropertiesValueById(currentModel.getFromId());
					switch (storeId[0]) {
					// 217,216,215,214 ids in sysproperties
					case MyConstants.ELECT_STORE_DEAN:
						propertiesValueById = getPropertiesValueById(217);
						// "ELECT_STORE_DEAN"
						break;
					case MyConstants.LIBR_STORE_DEAN: // "":
						propertiesValueById = getPropertiesValueById(216);
						break;
					case MyConstants.CARS_STORE_DEAN:// "":
						propertiesValueById = getPropertiesValueById(215);
						break;
					case MyConstants.RETURN_STORE_DEAN:// "":
						propertiesValueById = getPropertiesValueById(214);
						break;
					case MyConstants.MAINTENANCE_STORE_DEAN:// "":
						propertiesValueById = getPropertiesValueById(218);
						break;
					case MyConstants.PARKSBEAUTY_STORE_DEAN:// "":
						propertiesValueById = getPropertiesValueById(219);
						break;
					case MyConstants.CLEAN_STORE_DEAN:// "":
						propertiesValueById = getPropertiesValueById(220);
						break;
					case MyConstants.ELECTRIC_STORE_DEAN:// "":
						propertiesValueById = getPropertiesValueById(221);
						break;
					case MyConstants.PRINT_STORE_DEAN:// "":
						propertiesValueById = getPropertiesValueById(222);
						break;
					default:
						propertiesValueById = getPropertiesValueById(146);
						break;
					}
					return Integer.parseInt(propertiesValueById);
				}

				if (MailTypeEnum.getValue(modelTypeId) == MailTypeEnum.HEALTH_LICENCE) {
					WrkId = getWrkIdByArc(getArcRecordParent(arcRecordId));
				}
				// ArcUsers modelUserId = getFunctionOwnerWrkApp(WrkId,
				// propertiesValueById, arcRecordId);
				Integer modelUserId = getFunctionOwnerWrkApp(WrkId, propertiesValueById, arcRecordId);
				Integer autorizedUseId = modelUserId;
				return autorizedUseId;

			} else {
				return fromOfModelStep;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Integer getFunctionOwnerWrkApp(int WrkId, String function, int arc_records) {
		WrkApplicationId wrkApplicationId = new WrkApplicationId(WrkId, 1);

		// WrkApplication app = (WrkApplication)
		// findWrkApplicationById(wrkApplicationId);
		Integer fromId = commonDao.getWrkAppFromID(arc_records);
		ArcUsers fromUser = (ArcUsers) findEntityById(ArcUsers.class, fromId);
		WrkApplication wrkApp = commonDao.getWrkApplication(WrkId);
		// ArcUsers manager = fromUser.getManager();
		Integer manager = fromUser.getMgrId();
		switch (function) {
		case "GET_MANAGER":
			return manager;
		case "CURRENT_USER":
			return fromId;
		case "GET_REQUEST_OWNER_MANAGER":// Ã˜Â±Ã˜Â¦Ã™Å Ã˜Â³
											// Ã˜Â§Ã™â€žÃ˜Â¬Ã™â€¡Ã˜Â©
											// Ã˜Â§Ã™â€žÃ˜Â·Ã˜Â§Ã™â€žÃ˜Â¨Ã˜Â©
			return manager;
		case "GET_REQUEST_OWNER":
			return fromUser.getUserId();
		case "EMPLOYER":
			return wrkApp.getToUserId();

		default:

		}
		return fromUser.getUserId();
	}

	@Override
	public String loadTrainingPlaceName(Integer placeId) {
		return ((HrsTrainingPlace) commonDao.findEntityById(HrsTrainingPlace.class, placeId)).getPlace();
	}

	@Override
	public String printDocument(String reportName, int documentId, String urlParam) {

		String url = "http://" + findSystemProperty(MyConstants.APP_NAME_NEW)
				+ "/reports/rwservlet?&userid=reports/reports@ORCL&destype=cache&desformat=pdf&report=d:\\archiving\\reports\\"
				+ reportName

				+ ".rdf&" + "&" + urlParam + "=" + documentId;
		// "http://" + findSystemProperty(MyConstants.APP_NAME_NEW)
		// +
		// "/reports/rwservlet?repsrv&destype=cache&desformat=pdf&report=d:\\archiving\\reports\\"
		// + reportName
		//
		// + ".rdf&" + "&" + urlParam + "=" + documentId;

		return url;

	}

	@Override
	public String printDocumentByNameAndParams(String reportName, String urlParam) {

		// String url = "http://" + findSystemProperty(MyConstants.APP_NAME_NEW)
		// +
		// "/reports/rwservlet?repsrv&destype=cache&desformat=pdf&report=d:\\archiving\\reports\\"
		// + reportName

		String url = "http://" + findSystemProperty(MyConstants.APP_NAME_NEW)
				+ "/reports/rwservlet?&userid=reports/reports@ORCL&destype=cache&desformat=pdf&report=d:\\archiving\\reports\\"
				+ reportName

				+ ".rdf&" + urlParam;

		return url;

	}

	@Override
	public String printNationalIdDocument(String nationalId, String name, String requestType) {

		String url = "http://" + findSystemProperty(MyConstants.APP_NAME_NEW)
				+ "/reports/rwservlet?&userid=reports/reports@ORCL&destype=cache&desformat=pdf&report=d:\\archiving\\reports\\r247.rdf&"
				+ "&P2=" + nationalId + "&P3=" + requestType + "&P4=" + Utils.findCurrentUser().getUserId();

		// String url = "http://" + findSystemProperty(MyConstants.APP_NAME_NEW)
		// +
		// "/reports/rwservlet?repsrv&report=d:\\archiving\\reports\\r247.rdf&"
		// + "&P2=" + nationalId + "&P3="
		// + requestType + "&P4=" + Utils.findCurrentUser().getUserId();

		return url;

	}

	@Override
	@Transactional
	public boolean refuseModel(WrkApplication application, WrkApplicationId wrkApplicationId, Object t,
			String wrkAppComment, int applicationPurpose) {
		updateWrkApplicationVisible(wrkApplicationId);
		application.setApplicationPurpose(applicationPurpose);
		createNewWrkApplication(application.getArcRecordId(), application, wrkAppComment, false, null);
		updateObject(t);
		return true;

	}

	@Override
	@Transactional
	public void acceptAction(Integer archRecordId, Integer docType, Object updatedObject) {
		Integer stepNum = commonDao.getHrsSignNextStep(archRecordId);
		WrkApplication application = getWrkApplicationRecordById(archRecordId);
		HrsScenarioDocument scenarioDocument = getScenario(docType, stepNum);
		Integer userToId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 2);
		Integer fromId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 1);
		// Integer userTo = Integer.parseInt(
		// ((SysProperties) commonDao.findEntityById(SysProperties.class,
		// scenarioDocument.getToId())).getValue());

		WrkApplicationId applicationId = new WrkApplicationId();
		WrkApplication app = new WrkApplication(application);
		applicationId.setApplicationId(application.getId().getApplicationId());
		applicationId.setStepId(application.getId().getStepId() + 1);
		app.setId(applicationId);

		app.setToUserId(userToId);
		createNewWrkApplication(archRecordId, app, "", false, null);

		updateWrkApplicationVisible(application.getId());

		Integer docId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		if (scenarioDocument.getSigned() == 1) {
			if (updatedObject instanceof HrMedicalRequest)

			{
				((HrMedicalRequest) updatedObject).setAcceptStatus(MyConstants.YES);

			}
			if (updatedObject instanceof HrLetterRequest)

			{
				((HrLetterRequest) updatedObject).setAcceptStatus(MyConstants.YES);
			}
			if (updatedObject instanceof EmployeeInitiation)

			{

				commonDao.updateVacationAfterInit(((EmployeeInitiation) updatedObject).getVacationId());
				((EmployeeInitiation) updatedObject).setAcceptStatus(MyConstants.YES);

			}

			saveHrsSigns(archRecordId, docId, true, "", Utils.findCurrentUser().getUserId(), docType);

			updateObject(updatedObject);
		} else {
			saveHrsSigns(archRecordId, docId, false, "", Utils.findCurrentUser().getUserId(), docType);
		}

	}

	@Override
	@Transactional
	public synchronized Integer createOutcomeNo() {
		return commonDao.createOutcomeNo();
	}

	@Override
	@Transactional
	public void updateArcRecordsIncomeNo(ArcRecords arcRecords) {
		arcRecords.setIncomeNo(createIncomeNo());
		updateObject(arcRecords);
	}

	private boolean updateArcRecordsIncomeNo(int arcRecordsId) {
		try {
			ArcRecords arcRecords = (ArcRecords) findEntityById(ArcRecords.class, arcRecordsId);
			if (arcRecords != null) {
				updateArcRecordsIncomeNo(arcRecords);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void injectDaos(Object commonDao, Object dataAccessDAO, Object sessionFactory) {
		this.commonDao = (ICommonDao) commonDao;
		this.dataAccessDAO = (DataAccess) dataAccessDAO;
		this.commonDao.injectFactory(sessionFactory);
	}

	@Override
	public EmployeeInitiation getLastEmployeeInitiation() {

		return commonDao.getLastEmployeeInitiation();
	}

	@Override
	@Transactional
	public List<ReqFinesMaster> findLstFinesMasterByLicId(String fLicenceNo) {
		// TODO Auto-generated method stub
		return commonDao.findLstFinesMasterByLicId(fLicenceNo);
	}

	@Override
	@Transactional
	public List<ReqFinesMaster> findLstFinesMasterByLicIdWithState(String fLicenceNo, Integer State) {
		// TODO Auto-generated method stub
		return commonDao.findLstFinesMasterByLicIdWithState(fLicenceNo, State);
	}

	@Override
	@Transactional
	public LicTrdMasterFile findLicByLicId(String fLicenceNo) {
		// TODO Auto-generated method stub
		return commonDao.findLicByLicId(fLicenceNo);
	}

	@Override
	@Transactional
	public ReqFinesDetails findFinesDetailsByFno(Integer fNo) {
		// TODO Auto-generated method stub
		return commonDao.findFinesDetailsByFno(fNo);
	}

	@Override
	@Transactional
	public List<ReqFinesDetails> findLstFinesDetailsByFineNO(Integer fNo) {
		// TODO Auto-generated method stub
		return commonDao.findLstFinesDetailsByFineNO(fNo);
	}

	@Override
	@Transactional
	public ReqFinesMaster findFinesMasterByLicId(String fLicenceNo) {
		// TODO Auto-generated method stub
		return commonDao.findFinesMasterByLicId(fLicenceNo);
	}

	@Override
	@Transactional
	public Long getOutBoxmailsNumbers(Integer userId) {
		// TODO Auto-generated method stub
		return commonDao.getOutBoxmailsNumbers(userId);
	}

	@Override
	@Transactional
	public int getAuthorizationsNumber(int userId, String month, String year) {
		return commonDao.getAuthorizationsNumber(userId, month, year);

	}

	@Override
	@Transactional
	public Long getInboxNumbers(Integer userId) {
		// TODO Auto-generated method stub
		return commonDao.getInboxNumbers(userId);
	}

	@Override
	@Transactional
	public Long getUnReadMails(Integer userId) {
		// TODO Auto-generated method stub
		return commonDao.getUnReadMails(userId);
	}

	@Override
	@Transactional
	public Long getUnReadMailsForToday(Integer userId) {
		// TODO Auto-generated method stub
		return commonDao.getUnReadMailsForToday(userId);
	}

	@Override
	@Transactional
	public Long getExcuseVacationCount(Integer empNumber) {

		return commonDao.getExcuseVacationCount(empNumber);
	}

	@Override
	@Transactional
	public Long getNormalVacationCount(Integer empNumber) {

		return commonDao.getNormalVacationCount(empNumber);
	}

	@Override
	public String getHashedPassword(String userName, String password) throws Exception {
		return commonDao.getHashedPassword(userName, password);

	}

	@Override
	@Transactional
	public void updateUserPasswordForSystem(String userName, Integer userId, String newPassword) throws Exception {
		String hashPass = commonDao.getHashedPassword(userName, newPassword);
		commonDao.updateUserPasswordForSystem(userId, hashPass);
	}

	@Override
	@Transactional
	public void updateUserPasswordForSign(String userName, Integer userId, String newPassword) throws Exception {
		String hashPass = commonDao.getHashedPassword(userName, newPassword);
		commonDao.updateUserPasswordForSign(userId, hashPass);
	}

	@Override
	public boolean signedIsAutorized(int userId, int privlegeId) throws SQLException {
		return dataAccessDAO.signedIsAutorized(userId, privlegeId);
	}

	@Override
	@Transactional
	public ArcUsers getUserNameById(Integer userId) throws Exception {
		return commonDao.getUserNameById(userId);
	}

	@Override
	@Transactional
	public List<WrkRoles> getallRoles() throws Exception {
		return commonDao.getallRoles();
	}

	@Override
	@Transactional
	public void saveBill(PayLicBills payLicBill) {
		payLicBill.setBillDate(HijriCalendarUtil.findCurrentHijriDate());
		int billId = commonDao.findMaxBillId() + 1;
		payLicBill.setBillNumber(billId);
		save(payLicBill);
		savePayBillDetails(billId, payLicBill.getPayBillDetails());
	}

	private void savePayBillDetails(int billId, Set<PayBillDetails> payBillDetailsSet) {
		for (PayBillDetails item : payBillDetailsSet) {
			item.setBillNumber(billId);
			item.setBillGYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
			item.setBillHYear(Calendar.getInstance().get(Calendar.YEAR));
			item.setCreatedBy(Utils.findCurrentUser().getUserId());
			item.setCreatedIn(new Date());
			save(item);
		}
	}

	@Override
	@Transactional
	public List<PayMaster> loadAllPayMasters() {
		return commonDao.loadAllPayMasters();
	}

	@Override
	@Transactional
	public List<Charging> getallCharging() {
		return commonDao.getallCharging();
	}

	@Override
	@Transactional
	public void deletePayBillDetails(PayBillDetails selectedItem) {
		commonDao.deletePayBillDetails(selectedItem);
	}

	@Override
	@Transactional
	public List<NationalIdPlaces> getallNationalIdPlaces() {
		// TODO Auto-generated method stub
		return commonDao.getallNationalIdPlaces();
	}

	@Override
	@Transactional
	public List<NationalIdType> getAllNationalIdTypes() {
		// TODO Auto-generated method stub

		return commonDao.getAllNationalIdTypes();
	}

	@Override
	@Transactional
	public List<Nationality> getallNationalities() {
		// TODO Auto-generated method stub
		return commonDao.getallNationalities();
	}

	@Override
	@Transactional
	public void addArcPeople(ArcPeople arcPeople) throws Exception {
		commonDao.saveObjectReturnLong(arcPeople);

	}

	@Override
	@Transactional
	public void addProjectExtract(ProjectExtract projectExtract) {
		saveObject(projectExtract);

	}

	@Override
	@Transactional
	public List<Project> getallProjects() {
		return commonDao.getallProjects();

	}

	@Override
	@Transactional
	public List<ProjectContract> getallProjectContract(Integer projectId) {
		// TODO Auto-generated method stub
		return commonDao.getallProjectContract(projectId);
	}

	@Override
	@Transactional
	public Map<Integer, Map<Integer, DocumentScenario>> findAllScenarios() {
		Map<Integer, Map<Integer, DocumentScenario>> allscenarios = new HashMap<Integer, Map<Integer, DocumentScenario>>();
		List arcApplicationTypes = commonDao.findAll(ArcApplicationType.class);
		for (Object object : arcApplicationTypes) {
			ArcApplicationType aat = (ArcApplicationType) object;
			List<DocumentScenario> scenarioList = findScenariosByModelId(aat.getId());
			Map<Integer, DocumentScenario> modelScenario = new HashMap<Integer, DocumentScenario>();
			for (DocumentScenario documentScenario : scenarioList) {
				modelScenario.put(documentScenario.getStepId(), documentScenario);
			}
			allscenarios.put(aat.getId(), modelScenario);
		}
		return allscenarios;
	}

	@Override
	@Transactional
	public List<DocumentScenario> findScenariosByModelId(int pmodelId) {
		return commonDao.findScenariosByModelId(pmodelId);
	}

	@Override
	@Transactional
	public List<PayLicBills> loadAllBills() {
		return commonDao.loadAllBills();
	}

	@Override
	@Transactional
	public List<PayBillDetails> loadBillDetails(Integer billNumber) {
		return commonDao.loadBillDetails(billNumber);
	}

	@Override
	@Transactional
	public PayLicBills changeBillNumber(PayLicBills payLicBill) {
		if (payLicBill.getLicenceNumber() != null) {
			payLicBill.setLicenceNumber(null);
			commonDao.update(payLicBill);
		}
		PayLicBills myBill = new PayLicBills(payLicBill);
		myBill.setBillDate(HijriCalendarUtil.findCurrentHijriDate());
		int billId = commonDao.findMaxBillId() + 1;
		myBill.setBillNumber(billId);
		save(myBill);
		Set<PayBillDetails> myBillDetails = new HashSet<>();
		for (PayBillDetails myDetails : payLicBill.getPayBillDetails()) {
			myBillDetails.add(new PayBillDetails(myDetails));
		}
		savePayBillDetails(billId, myBillDetails);
		myBill.setPayBillDetails(myBillDetails);
		return myBill;
	}

	@Override
	@Transactional
	public ProjectExtract getProjectExtractByArchRecordId(Integer archRecordId) {

		Integer DocId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		return (ProjectExtract) commonDao.findEntityById(ProjectExtract.class, DocId);
	}

	@Override
	@Transactional
	public HealthMasterLicence getHealthCertificateRecordId(Integer arcRecordId) {
		Integer docId = commonDao.getDocIdHrsSignByRecId(arcRecordId);
		return (HealthMasterLicence) commonDao.findEntityById(HealthMasterLicence.class, docId);
	}

	@Override
	@Transactional
	public void addNewProjectExtract(ProjectExtract projectExtract, Integer createdForId) {

		Integer projectExtractId = (Integer) saveObject(projectExtract);
		// ProjectExtract projectExtract2=
		// (ProjectExtract)findEntityById(ProjectExtract.class,
		// projectExtractId);
		ArcUsers user = Utils.findCurrentUser();
		Integer fromId = user.getUserId();
		Integer toId = user.getMgrId();
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());
		ProjectContract contract = (ProjectContract) findEntityById(ProjectContract.class,
				projectExtract.getContractId());
		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setApplicationType(MailTypeEnum.PROJECTeXTRACT.getValue());

		arcRecord.setRecTitle(Utils.loadMessagesFromFile("payment.number") + " " + projectExtract.getNumber()
		+ "من  مشروع " + contract.getProject().getName() + " مقدم من  " + user.getEmployeeName());

		int recordId = createNewArcRecord(arcRecord, false, toId);

		WrkApplication application = new WrkApplication();
		application.setToUserId(toId);
		// application.setId(new WrkApplicationId(commonDao.createWrkId(),
		// findStepId(recordId)));

		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);

		// application.setApplicationUserDeptJob(applicationUserDeptJob);payment
		String userComment = Utils.loadMessagesFromFile("payment") + " " + Utils.loadMessagesFromFile("prepared.by")
				+ " " + user.getEmployeeName();
		createNewWrkApplication(recordId, application, userComment, false, applicationUserDeptJob);

		saveHrsSigns(recordId, projectExtractId, false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.PROJECTeXTRACT.getValue());

	}

	@Override
	@Transactional
	public Integer addExchangeRequest(ExchangeRequest exchangeRequest, Integer createdForId,
			List<ExchangeRequestDetails> exchangeRequestDetailslIST) {

		Integer exchangeRequestId = (Integer) saveObject(exchangeRequest);

		for (ExchangeRequestDetails exc : exchangeRequestDetailslIST) {
			exc.setGeneralRequestNumber(exchangeRequestId);
			Integer excId = addNewExchangeRequestDetailsRecors(exc);

		}
		ArcUsers user = Utils.findCurrentUser();
		Integer toId = user.getMgrId();
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());

		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setApplicationType(MailTypeEnum.EXCHANGEREQUEST.getValue());
		String empName = user.getEmployeeName();
		String subject = Utils.loadMessagesFromFile("subject.exchange");
		arcRecord.setRecTitle(empName == null ? subject + empName : subject + user.getFirstName());
		int recordId = createNewArcRecord(arcRecord, false, toId);

		WrkApplication application = new WrkApplication();
		application.setToUserId(toId);
		// application.setId(new WrkApplicationId(commonDao.createWrkId(),
		// findStepId(recordId)));

		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);

		String items = "";
		for (ExchangeRequestDetails detail : exchangeRequestDetailslIST) {
			items = items + " " + detail.getTenderItemName() + " " + Utils.loadMessagesFromFile("count.exchange") + " "
					+ detail.getCount() + "\n";
		}
		String userComment = Utils.loadMessagesFromFile("subject.exchange") + " " + empName + " "
				+ Utils.loadMessagesFromFile("forms.exchange") + ": " + items;
		// String userComment = "subject.exchange" + empName + "forms.exchange"
		// + items;

		createNewWrkApplication(recordId, application, userComment, false, applicationUserDeptJob);

		saveHrsSigns(recordId, exchangeRequestId, false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.EXCHANGEREQUEST.getValue());
		return exchangeRequestId;
	}

	@Override
	public String loadNameByNationalId(long nationalId) {
		ArcPeople people = commonDao.findArcPeopleById(nationalId);
		return getValueIfNotNull(people.getFirstName()) + " " + getValueIfNotNull(people.getSeconedName()) + " "
				+ getValueIfNotNull(people.getThirdName()) + " " + getValueIfNotNull(people.getFourthName());
	}

	private String getValueIfNotNull(String name) {
		return (name == null) ? "" : name;
	}

	@Transactional
	public boolean checkScenarioFromEqualTo(Integer archRecordId, Integer docType) {

		Integer nextStepNum = commonDao.getHrsSignNextStep(archRecordId);
		HrsScenarioDocument scenarioDocument = getScenario(docType, nextStepNum);
		WrkApplication application = getWrkApplicationRecordById(archRecordId);
		int userToId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 2);
		int c = Utils.findCurrentUser().getUserId();
		if (userToId == c) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void acceptActionforProjectExtract(Integer archRecordId, Integer docType, String wrkAppComment,
			Integer appPurpose) {
		acceptForExtract(archRecordId, docType, wrkAppComment, appPurpose);
		// if (checkScenarioFromEqualTo(archRecordId, docType)) {
		// acceptForExtract(archRecordId, docType, wrkAppComment, appPurpose);
		// acceptForExtract(archRecordId, docType, wrkAppComment, appPurpose);
		// acceptForExtract(archRecordId, docType, wrkAppComment, appPurpose);
		// } else {
		// acceptForExtract(archRecordId, docType, wrkAppComment, appPurpose);
		// }

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void acceptForExtract(Integer archRecordId, Integer docType, String wrkAppComment, Integer appPurpose) {
		Integer stepNum = commonDao.getHrsSignNextStep(archRecordId);
		WrkApplication application = getWrkApplicationRecordById(archRecordId);
		HrsScenarioDocument scenarioDocument = getScenario(docType, stepNum);
		Integer userToId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 2);

		WrkApplicationId applicationId = new WrkApplicationId();
		WrkApplication app = new WrkApplication(application);
		applicationId.setApplicationId(application.getId().getApplicationId());
		applicationId.setStepId(application.getId().getStepId() + 1);
		app.setId(applicationId);

		app.setToUserId(userToId);
		app.setApplicationPurpose(appPurpose);
		createNewWrkApplication(archRecordId, app, wrkAppComment, false, null);

		updateWrkApplicationVisible(application.getId());

		Integer docId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		if (scenarioDocument.getSigned() == 1) {
			saveHrsSigns(archRecordId, docId, true, "", Utils.findCurrentUser().getUserId(), docType);
			updateArcRecordsIncomeNo(app.getArcRecordId());
		} else {
			saveHrsSigns(archRecordId, docId, false, "", Utils.findCurrentUser().getUserId(), docType);
		}

	}

	@Transactional
	@Override
	public List<TenderItems> getAllTenderItems() {

		return commonDao.getAllTenderItems();
	}

	public void addlistOfExchangeRequestDetails(List<ExchangeRequestDetails> requestDetails) {

		for (ExchangeRequestDetails exchangeRequestDetails : requestDetails) {

		}
	}

	@Override
	@Transactional
	public Integer addNewExchangeRequest(ExchangeRequest exchangeRequest) {
		return save(exchangeRequest);

	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Integer addNewExchangeRequestDetailsRecors(ExchangeRequestDetails exchangeRequestDetails) {
		return save(exchangeRequestDetails);

	}

	@Override
	@Transactional
	public Integer getStepNumberFromHrSign(Integer archRecordId) {
		return commonDao.getHrsSignNextStep(archRecordId);
	}

	@Override
	public Integer save(Object myObject) {
		 if (myObject instanceof ArcRecords)
		 ((ArcRecords) myObject).setId(createArcRecordsId());
		return commonDao.save(myObject);
	}

	@Override
	public Object saveObject(Object myObject) {
		int failCount = 0;
		while (failCount < 30)
			try {
				return commonDao.saveObject(myObject);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(
						"save action primary key  :" + myObject.getClass().getSimpleName() + "  " + e.getMessage());
				failCount++;
			}
		return null;
	}

	@Override
	@Transactional
	public void acceptActionforExchangeRequest(Integer archRecordId, Integer docType, ExchangeRequest request,
			String wrkAppComment, Integer applicationPurpose, boolean updateRequestDetails,
			List<ExchangeRequestDetails> deletedExchangeRequestDetails) {

		if (request.getStatus() != null && request.getStatus().equals(MyConstants.RESERVED))
			updateObject(request);
		Integer stepNum = commonDao.getHrsSignNextStep(archRecordId);
		WrkApplication application = getWrkApplicationRecordById(archRecordId);
		HrsScenarioDocument scenarioDocument = getScenario(docType, stepNum);
		Integer userToId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 2,
				request.getStockNo());

		// Integer fromId = getNextScenarioUserId(docType,
		// application.getId().getApplicationId(), archRecordId, 1);
		Integer ownerOfDemand = getUserIdFromWorkAppByIdAndStepId(archRecordId, 1);

		// Integer userTo = Integer.parseInt(
		// ((SysProperties) commonDao.findEntityById(SysProperties.class,
		// scenarioDocument.getToId())).getValue());

		WrkApplicationId applicationId = new WrkApplicationId();
		WrkApplication app = new WrkApplication(application);
		applicationId.setApplicationId(application.getId().getApplicationId());
		applicationId.setStepId(application.getId().getStepId() + 1);
		app.setId(applicationId);

		app.setToUserId(userToId);
		app.setApplicationPurpose(applicationPurpose);

		createNewWrkApplication(archRecordId, app, wrkAppComment, false, null);

		updateWrkApplicationVisible(application.getId());

		Integer docId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		boolean visible = false;
		if (updateRequestDetails) {
			for (ExchangeRequestDetails record : request.getExchangeRequestDetailsList()) {
				updateObject(record);
			}
			if (deletedExchangeRequestDetails != null) {
				for (ExchangeRequestDetails record : deletedExchangeRequestDetails) {
					deleteObject(record);
				}
			}
		} else if (scenarioDocument.getSigned() == 1) {
			request.setStatus(MyConstants.YES);
			updateObject(request);
			for (ExchangeRequestDetails record : request.getExchangeRequestDetailsList()) {
				if ((record.getGuranteeActualyCount() != null) && (record.getGuranteeActualyCount() > 0)
						|| record.getExchangeAtcualyCount() > 0) {
					record.setConfirmWS(MyConstants.YES);
				}
				updateObject(record);
			}
			visible = true;
			// saveHrsSigns(archRecordId, docId, true, "",
			// Utils.findCurrentUser().getUserId());
			// updateArcRecordsIncomeNo(app.getArcRecordId());
			copyOfHrDocumentAndSendReport(docId, Utils.loadMessagesFromFile("exch.request.response"), ownerOfDemand,
					"");

		}
		// else {
		// saveHrsSigns(archRecordId, docId, false, "",
		// Utils.findCurrentUser().getUserId());
		// }
		// }
		saveHrsSigns(archRecordId, docId, visible, "", Utils.findCurrentUser().getUserId(), docType);
	}

	@Override
	@Transactional
	public ExchangeRequest getExchangeRequestByArchRecordId(Integer archRecordId) {

		Integer DocId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		return (ExchangeRequest) commonDao.findEntityById(ExchangeRequest.class, DocId);
	}

	@Override
	@Transactional
	public ActualDisbursement getActualExchangeRequestByArchRecordId(Integer archRecordId) {

		Integer DocId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		return (ActualDisbursement) commonDao.findEntityById(ActualDisbursement.class, DocId);
	}

	public byte[] loadArcPeoplePic(Long nationalId) {
		ArcPeoplePic arcPeoplePic = commonDao.findArcPeoplePicById(nationalId);
		return (arcPeoplePic != null) ? arcPeoplePic.getPicture() : null;
	}

	@Override
	@Transactional
	public int findLicApplicationIdByRecord(int arcRecordId) {
		// TODO Auto-generated method stub
		return commonDao.findLicApplicationIdByRecord(arcRecordId);
	}

	@Override
	@Transactional
	public Integer addActualyExchangRequest(ActualDisbursement actualDisbursement, Integer createdForId,
			List<ActualDisbursementDetails> actualDisbursementDetails, Integer oldArcRecordId) {
		ArcUsers user = Utils.findCurrentUser();
		actualDisbursement.setCreatedby(user.getFirstName());
		actualDisbursement.setCreatedDate(HijriCalendarUtil.findCurrentHijriDate());
		actualDisbursement.setDocuTypNo(6);

		Integer actualExchangeId = (Integer) saveObject(actualDisbursement);

		for (ActualDisbursementDetails exc : actualDisbursementDetails) {
			exc.setActualDisbursementId(actualExchangeId);

			Integer actualDisbursementDetailsRecordId = (Integer) saveObject(exc);

		}
		// ArcUsers user = Utils.findCurrentUser();
		Integer fromId = user.getUserId();
		// Integer toId = user.getManager().getUserId();
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());

		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setApplicationType(MailTypeEnum.ACTUALEXCHANGE.getValue());
		arcRecord.setRecTitle("  Ã˜Â·Ã™â€žÃ˜Â¨ Ã˜Â§Ã™â€¦Ã˜Â± Ã˜ÂµÃ˜Â±Ã™ï¿½ Ã™ï¿½Ã˜Â¹Ã™â€žÃ™â€° Ã˜Â¨Ã™â€ Ã˜Â§Ã˜Â¡ Ã˜Â¹Ã™â€žÃ™â€° Ã˜Â·Ã™â€žÃ˜Â¨ Ã˜ÂµÃ˜Â±Ã™ï¿½ Ã™â€¦Ã™Ë†Ã˜Â§Ã˜Â¯ Ã˜Â±Ã™â€šÃ™â€¦     "
				+ actualDisbursement.getGeneralrequestNumber());
		int recordId = createNewArcRecord(arcRecord, false, 280);

		WrkApplication application = new WrkApplication();
		application.setToUserId(280);
		// application.setId(new WrkApplicationId(commonDao.createWrkId(),
		// findStepId(recordId)));

		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);

		// application.setApplicationUserDeptJob(applicationUserDeptJob);
		String userComment = "  Ã˜Â·Ã™â€žÃ˜Â¨ Ã˜Â§Ã™â€¦Ã˜Â± Ã˜ÂµÃ˜Â±Ã™ï¿½ Ã™ï¿½Ã˜Â¹Ã™â€žÃ™â€° Ã˜Â¨Ã™â€ Ã˜Â§Ã˜Â¡ Ã˜Â¹Ã™â€žÃ™â€° Ã˜Â·Ã™â€žÃ˜Â¨ Ã˜ÂµÃ˜Â±Ã™ï¿½ Ã™â€¦Ã™Ë†Ã˜Â§Ã˜Â¯ Ã˜Â±Ã™â€šÃ™â€¦     "
				+ actualDisbursement.getGeneralrequestNumber() + " Ã™â€¦Ã™â€  Ã˜Â§Ã˜Â¯Ã˜Â§Ã˜Â±Ã˜Â© "
				+ actualDisbursement.getDepartment();
		createNewWrkApplication(recordId, application, userComment, false, applicationUserDeptJob);

		saveHrsSigns(recordId, actualExchangeId, false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.ACTUALEXCHANGE.getValue());
		ArcRecordsLink arcRecordLink = new ArcRecordsLink();
		arcRecordLink.setArcRecordParentId(oldArcRecordId);
		arcRecordLink.setArcRrecordChildId(recordId);
		arcRecordLink.setModelType(MailTypeEnum.ACTUALEXCHANGE.getValue());
		save(arcRecordLink);

		return actualExchangeId;
	}

	@Transactional
	@Override
	public List<WrkApplication> getWrkApplicationListByRecordId(Integer archRecordId) {

		return commonDao.getWrkApplicationListByRecordId(archRecordId);
	}

	@Transactional
	@Override
	public Integer getArchRecParentFromLinkByRecordId(Integer childArchRecordId) {
		return commonDao.getArchRecParentFromLinkByRecordId(childArchRecordId);
	}

	@Override
	public int getNextArcId() {
		return commonDao.getNextArcId();
	}

	@Override
	public Integer getWrkIdByArc(int arcRecordId) {
		return commonDao.getWrkIdByArc(arcRecordId);
	}

	@Override
	@Transactional
	public void acceptActionforActualExchange(Integer archRecordId, Integer docType,
			List<ActualDisbursementDetails> ActualDisbursementDetails, Integer originalOwnerId, String wrkAppComment,
			Integer applicationPurpose) {
		Integer stepNum = commonDao.getHrsSignNextStep(archRecordId);
		WrkApplication application = getWrkApplicationRecordById(archRecordId);
		HrsScenarioDocument scenarioDocument = getScenario(docType, stepNum);
		Integer userToId = getNextScenarioUserId(docType, application.getId().getApplicationId(), archRecordId, 2);

		// Integer fromId = getNextScenarioUserId(docType,
		// application.getId().getApplicationId(), archRecordId, 1);
		// Integer ownerOfDemand =
		// getUserIdFromWorkAppByIdAndStepId(archRecordId, 1);

		// Integer userTo = Integer.parseInt(
		// ((SysProperties) commonDao.findEntityById(SysProperties.class,
		// scenarioDocument.getToId())).getValue());

		WrkApplicationId applicationId = new WrkApplicationId();
		WrkApplication app = new WrkApplication(application);
		applicationId.setApplicationId(application.getId().getApplicationId());
		applicationId.setStepId(application.getId().getStepId() + 1);
		app.setId(applicationId);

		app.setToUserId(userToId);
		app.setApplicationPurpose(applicationPurpose);
		createNewWrkApplication(archRecordId, app, wrkAppComment, false, null);

		updateWrkApplicationVisible(application.getId());

		Integer docId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		// medicalRequest.setAcceptStatus("y");
		for (ActualDisbursementDetails record : ActualDisbursementDetails) {

			updateObject(record);
		}
		if (scenarioDocument.getSigned() == 1) {
			saveHrsSigns(archRecordId, docId, true, "", Utils.findCurrentUser().getUserId(), docType);
			updateArcRecordsIncomeNo(app.getArcRecordId());
			copyOfHrDocumentAndSendReport(docId,
					"  Ã˜ÂªÃ™â€¦Ã˜Âª Ã˜Â§Ã™â€žÃ™â€¦Ã™Ë†Ã˜Â§Ã™ï¿½Ã™â€šÃ˜Â© Ã˜Â¹Ã™â€žÃ™â€° Ã˜Â·Ã™â€žÃ˜Â¨Ã™Æ’ Ã˜Â¨Ã˜Â´Ã™Æ’Ã™â€ž Ã™â€ Ã™â€¡Ã˜Â§Ã˜Â¦Ã™â€°  Ã™â€¦Ã™â€  Ã˜Â§Ã˜Â¯Ã˜Â§Ã˜Â±Ã˜Â© Ã˜Â§Ã™â€žÃ™â€¦Ã˜Â³Ã˜ÂªÃ™Ë†Ã˜Â¯Ã˜Â¹Ã˜Â§Ã˜Âª Ã™Ë†Ã™Å Ã˜Â±Ã˜Â¬Ã™â€°  Ã˜Â§Ã™â€žÃ˜Â°Ã™â€¡Ã˜Â§Ã˜Â¨ Ã˜Â§Ã™â€žÃ™â€°  Ã˜Â§Ã™â€žÃ™â€¦Ã˜Â³Ã˜ÂªÃ™Ë†Ã˜Â¯Ã˜Â¹ Ã™â€žÃ™â€žÃ˜Â§Ã˜Â³Ã˜ÂªÃ™â€žÃ˜Â§Ã™â€¦  ",
					originalOwnerId, "");

		} else {
			saveHrsSigns(archRecordId, docId, false, "", Utils.findCurrentUser().getUserId(), docType);
		}

	}

	@Override
	@Transactional
	public List<ArcRecordsLink> getChildsObjectsByParentRecordId(Integer parentArchRecordId) {

		return commonDao.getChildsObjectsByParentRecordId(parentArchRecordId);
	}

	@Override
	@Transactional
	public void saveProcurement(Procurement procurement, Integer oldArcRecordId) {
		ArcUsers user = Utils.findCurrentUser();
		Integer fromId = user.getUserId();
		Integer toId = user.getMgrId();

		procurement.setCreatedBy(fromId);
		procurement.setCreatedHDate(HijriCalendarUtil.findCurrentHijriDate());
		procurement.setCreateGDate(new Date());
		Integer procId = (Integer) saveObject(procurement);
		for (ProcurementDetails details : procurement.getProcurementDetailsList()) {
			details.setProcurementId(procId);
			save(details);
		}

		// save ArcRecords
		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setApplicationType(MailTypeEnum.PROCUREMENT_MATERIALS.getValue());
		String title = MessageFormat.format(Utils.loadMessagesFromFile("procurement.title"), user.getEmployeeName(),
				HijriCalendarUtil.findCurrentHijriDate());
		arcRecord.setRecTitle(title);
		int recordId = createNewArcRecord(arcRecord, false, toId);
		// Save WrkApplication
		WrkApplication application = new WrkApplication();
		application.setToUserId(toId);
		// application.setId(new WrkApplicationId(commonDao.createWrkId(),
		// findStepId(recordId)));

		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());
		createNewWrkApplication(recordId, application, title, false, applicationUserDeptJob);
		// Save HrsSigns
		saveHrsSigns(recordId, procId, false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.PROCUREMENT_MATERIALS.getValue());

		ArcRecordsLink arcRecordLink = new ArcRecordsLink();
		arcRecordLink.setArcRecordParentId(oldArcRecordId);
		arcRecordLink.setArcRrecordChildId(recordId);
		arcRecordLink.setModelType(MailTypeEnum.PROCUREMENT_MATERIALS.getValue());
		save(arcRecordLink);
	}

	@Override
	public Integer getArcRecordParent(int arcRecordId) {
		return commonDao.getArcRecordParent(arcRecordId);
	}

	@Override
	public List<FinFinancialYear> getAllFinYears() {

		return commonDao.getAllFinYears();

	}

	@Override
	public List<WhsWarehouses> getAllStores() {
		return commonDao.getAllStores();
	}

	@Override
	public Procurement loadProcurementByArcRecord(Integer recordId) {
		Integer DocId = commonDao.getDocIdHrsSignByRecId(recordId);
		return (Procurement) commonDao.findEntityById(Procurement.class, DocId);
	}

	@Override
	@Transactional
	public void acceptProcurement(Procurement procurement, Integer recordId, String wrkAppComment,
			int applicationPurpose) {
		WrkApplication app = getWrkApplicationRecordById(recordId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		application.setId(app.getId());
		application.getId().setStepId(app.getId().getStepId() + 1);

		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(MailTypeEnum.PROCUREMENT_MATERIALS.getValue(),
				app.getId().getApplicationId(), app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		application.setApplicationPurpose(applicationPurpose);
		createNewWrkApplication(application.getArcRecordId(), application, wrkAppComment, false, null);

		boolean visible = (getDestinationModel(MailTypeEnum.PROCUREMENT_MATERIALS.getValue(), acceptCount)
				.getSigned() == 1);
		saveHrsSigns(application.getArcRecordId(), procurement.getId(), visible, null,
				Utils.findCurrentUser().getUserId(), MailTypeEnum.PROCUREMENT_MATERIALS.getValue());
		if (visible) {
			procurement.setAcceptedYN(MyConstants.YES);
			updateArcRecordsIncomeNo(application.getArcRecordId());
			commonDao.update(procurement);

			int exchangeReqArcRec = getArcRecordParent(app.getArcRecordId());

			int ownerOfDemand = getWrkApplicationListByRecordId(exchangeReqArcRec).get(0).getFromUserId();
			String title = Utils.loadMessagesFromFile("copy.accept.procurement");
			// Integer ownerOfDemand =
			// getNextScenarioUserId(MailTypeEnum.EXCHANGEREQUEST.getValue(),
			// app.getId().getApplicationId(), exchangeReqArcRec, 2);
			updateArcRecordsIncomeNo(application.getArcRecordId());
			copyOfHrDocumentAndSendReport(procurement.getId(), title, ownerOfDemand, "");

		}
	}

	@Override
	@Transactional
	public int saveNewIncome(ArcRecords arcRecords, WrkApplication wrkApplication, List<AttachmentModel> attachList,
			List<Integer> copyList, boolean isForSave) throws Exception {
		int result = -1;
		List<Integer> attachIds = new ArrayList<>();
		try {
			if (attachList.size() > 0)
				attachIds = addAttachs(attachList);
			ArcUsers au = commonDao.findUserById(wrkApplication.getToUserId());
			Integer newRecordId = arcRecords.getId();
			newRecordId = saveNewArcRecords(arcRecords, au);
			wrkApplication.setApplicationIsVisible(0);
			wrkApplication.setApplicationIsRead(1);
	//		if (!isForSave) {

	//			List<WrkApplication> allRecordWrkApplications = commonDao
	//					.findAllWrkApplicationById(wrkApplication.getId().getApplicationId());
	//			for (WrkApplication wrkApp : allRecordWrkApplications) {
	//				wrkApp.setApplicationIsVisible(0);
	//				wrkApp.setApplicationIsRead(1);
	//				commonDao.update(wrkApp);
	//			}
				// updateWrkApplication(wrkApplication);

			//	wrkApplication.getId().setStepId(wrkApplication.getId().getStepId() + 1);

	//		}
	//		Integer newRecordId = arcRecords.getId();
	//		if (isForSave) {
	//			newRecordId = saveNewArcRecords(arcRecords, au);
	//		}
			result = Integer.parseInt(arcRecords.getIncomeNo());

			wrkApplication.setApplicationCreateDate(new Date());
			wrkApplication.setArcRecordId(newRecordId);
			wrkApplication.setFromUserId(Utils.findCurrentUser().getUserId());
			wrkApplication.setApplicationCreateDate(new Date());
			wrkApplication.setApplicationCreateTime(HijriCalendarUtil.findCurrentTime());
			wrkApplication.setApplicationIsRead(0);
			wrkApplication.setApplicationIsReply(0);
			wrkApplication.setApplicationIsVisible(1);
			wrkApplication.setHijriDate(HijriCalendarUtil.findCurrentHijriDate());

			commonDao.saveWrkApp(wrkApplication, newRecordId);

			saveAttachs(attachIds, newRecordId);

			sendCopies(arcRecords, copyList, attachIds);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private int saveNewArcRecords(ArcRecords arcRecords, ArcUsers au) {
		arcRecords.setApplicationType(143);
		arcRecords.setCreatedBy(Utils.findCurrentUser().getUserId());
		arcRecords.setCreatedIn(new Date());
		arcRecords.setIncomeHDate(HijriCalendarUtil.findCurrentHijriDate());
		arcRecords.setIncomeNo(commonDao.createIncomeNo());
		arcRecords.setIncomeYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
		arcRecords.setRecGDate(new Date());
		arcRecords.setRecordIsImportant(0);
		arcRecords.setLetterTo(au.getDeptId());
		arcRecords.setRecHDate(HijriCalendarUtil.findCurrentHijriDate());
		int newRecordId = save(arcRecords);
		return newRecordId;
	}

	private void saveAttachs(List<Integer> attachIds, int newRecordId) {
		for (Integer id : attachIds) {
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(id);
			arcRecAttId.setRecordId(newRecordId);
			arcRecAtt.setId(arcRecAttId);
			saveObject(arcRecAtt);

		}
	}

	private void sendCopies(ArcRecords arcRecords, List<Integer> copyList, List<Integer> attachIds) {
		for (Object id : copyList) {
			WrkApplication application = new WrkApplication();
			ArcRecords recordCopy = new ArcRecords();

			recordCopy.setRecTitle(Utils.loadMessagesFromFile("copy.from") + " " + arcRecords.getRecTitle());
			recordCopy.setLetterFrom(commonDao.findUserSection(Utils.findCurrentUser().getUserId()));

			recordCopy.setCreatedIn(new Date());
			recordCopy.setRecHDate(HijriCalendarUtil.findCurrentHijriDate());
			recordCopy.setIncomeYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
			recordCopy.setRecGDate(new Date());
			recordCopy.setCreatedBy(Utils.findCurrentUser().getUserId());
			recordCopy.setApplicationType(0);
			// TODO activate
			// arcRecord.setLetterTo(commonDao.findUserSection(wrkApplication.getToUserId()));
			int archId = save(recordCopy);
			saveAttachs(attachIds, archId);
			application.setToUserId(Integer.parseInt((String) id));
			application.setApplicationCreateDate(new Date());
			// application.setId(new
			// WrkApplicationId(commonDao.createWrkId(), 1));
			application.setArcRecordId(archId);
			application.setFromUserId(Utils.findCurrentUser().getUserId());
			application.setApplicationCreateDate(new Date());
			application.setApplicationCreateTime(HijriCalendarUtil.findCurrentTime());
			application.setApplicationIsRead(0);
			application.setApplicationIsReply(0);
			application.setApplicationIsVisible(1);
			application.setHijriDate(HijriCalendarUtil.findCurrentHijriDate());
			commonDao.saveWrkApp(application, archId);
		}
	}

	private List<Integer> addAttachs(List<AttachmentModel> attachList) throws Exception {
		List<ArcAttach> myList = new ArrayList<>();
		myList = Utils.SaveAttachementsToFtpServer(attachList);

		return addAttachments(myList);
	}

	@Override
	@Transactional
	public boolean isIncomeExist(int incomeNumber) {
		return commonDao.isIncomeExist(incomeNumber);
	}

	@Override
	public String printProjectExtractDocument(Integer projectExtactId) {
		String url = "http://" + findSystemProperty(MyConstants.APP_NAME_NEW)
				+ "/reports/rwservlet?repsrv&report=d:\\archiving\\reports\\r07a.rdf&" + "P1=" + projectExtactId;

		return url;

	}

	@Transactional
	public void saveMemo(WrkApplication wrkApplication, ArcRecords arcRecord, List<ArcAttach> attachs,
			List<String> toCopyList, Boolean isForSave) {

		List<Integer> attachmentIds = addAttachments(attachs);

//		if (!isForSave) {
			if (wrkApplication.getId() != null) {

				List<WrkApplication> allRecordWrkApplications = commonDao
						.findAllWrkApplicationById(wrkApplication.getId().getApplicationId());

				if (allRecordWrkApplications != null && allRecordWrkApplications.size() > 0)
					for (WrkApplication wrkApp : allRecordWrkApplications) {
						wrkApp.setApplicationIsVisible(0);
						wrkApp.setApplicationIsRead(1);
						commonDao.update(wrkApp);
					}
				wrkApplication.getId().setStepId(wrkApplication.getId().getStepId() + 1);
			}

//		}
		AddNewInternalMemo(wrkApplication, arcRecord, attachmentIds);

		for (String id : toCopyList) {
			saveInternalCopy(wrkApplication, arcRecord, attachmentIds, new Integer(id));
		}
	}

	/**
	 * @param wrkApplication
	 * @param arcRecord
	 * @param attachmentIds
	 * @param emp
	 */
	public void saveInternalCopy(WrkApplication wrkApplication, ArcRecords arcRecord, List<Integer> attachmentIds,
			int id) {

		ArcRecords arcRecordCopy = new ArcRecords();

		if (arcRecord.getRecordIsImportant() == 1)
			arcRecordCopy.setImportantFlag(true);
		else {
			arcRecordCopy.setImportantFlag(false);
		}
		if (arcRecord.getIncomeNo() != null)
			arcRecordCopy.setOutcomingNumFlag(true);
		else {
			arcRecordCopy.setOutcomingNumFlag(false);
		}
		arcRecordCopy.setRecTitle(" صورة  من  " + arcRecord.getRecTitle());

		WrkApplication wrkApplicationCopy = new WrkApplication(wrkApplication);

		wrkApplicationCopy.setToUserId(id);
		// arcRecordCopy.setApplicationType(MailTypeEnum.MODAKARA.getValue());
		AddCopyInternalMemo(wrkApplicationCopy, arcRecordCopy, attachmentIds);

		if (wrkApplicationCopy.isVisibleFlag() == true) {
			WrkConfedintialRepliesId wcr2 = new WrkConfedintialRepliesId();
			// save WrkConfedintial toId
			wcr2.setGrantedUser(wrkApplicationCopy.getToUserId());
			wcr2.setId(wrkApplicationCopy.getId().getApplicationId());
			wcr2.setStepId(wrkApplicationCopy.getId().getStepId());
			WrkConfedintialReplies w2 = new WrkConfedintialReplies();
			w2.setId(wcr2);
			saveObject(w2);
			// save WrkConfedintial fromId
			WrkConfedintialRepliesId wrkConfedintial = new WrkConfedintialRepliesId();
			wrkConfedintial.setGrantedUser(Utils.findCurrentUser().getUserId());
			wrkConfedintial.setId(wrkApplicationCopy.getId().getApplicationId());
			wrkConfedintial.setStepId(wrkApplicationCopy.getId().getStepId());
			WrkConfedintialReplies wrkConfedintialReplies = new WrkConfedintialReplies();
			wrkConfedintialReplies.setId(wrkConfedintial);
			saveObject(wrkConfedintialReplies);
		}
	}

	@Override
	@Transactional
	public void sendInternalMemoryForCopyInCharging(Charging charging) {
		WrkApplication wrkApplication = new WrkApplication();
		ArcRecords arcRecord = new ArcRecords();
		wrkApplication.setApplicationUsercomment(" " + "  ØªÙ… ØªÙƒÙ„ÙŠÙ�    " + charging.getEmployeInChargingNameBefore()
				+ "  Ø¨Ø¯Ù„Ø§ Ù…Ù†  " + charging.getEmployeInChargingNameAfter() + "  Ù�ÙŠ Ø§Ù„Ù�ØªØ±Ø© Ù…Ù†   "
				+ charging.getChargingStratDate() + " Ø¥Ù„Ù‰ " + charging.getChargingEndDate()

		);
		wrkApplication.setApplicationPurpose(12);
		wrkApplication.setToUserId(charging.getEmployeInChargingId());
		arcRecord.setRecTitle(" " + "  ØªÙ… ØªÙƒÙ„ÙŠÙ�      " + charging.getEmployeInChargingNameBefore() + "  Ø¨Ø¯Ù„Ø§ Ù…Ù†  "
				+ charging.getEmployeInChargingNameAfter() + "  Ù�ÙŠ Ø§Ù„Ù�ØªØ±Ø© Ù…Ù†   " + charging.getChargingStratDate()
				+ " Ø¥Ù„Ù‰ " + charging.getChargingEndDate()

		);
		List<Integer> attachmentIds = new ArrayList<>();
		Integer arcRecId = AddNewInternalMemo(wrkApplication, arcRecord, attachmentIds);
		List<Integer> copyIds = new ArrayList<>();

		copyIds.add(getResponsibleId(MyConstants.MOTABA3A_ID));
		copyIds.add(getResponsibleId(MyConstants.TA6WEER_ID));
		copyIds.add(getResponsibleId(MyConstants.PRESIDENT_OFFICER));
		copyIds.add(getResponsibleId(MyConstants.HR_USER));
		for (Integer id : copyIds) {

			ArcRecords arcRecordCopy = new ArcRecords();

			if (arcRecord.getRecordIsImportant() == 1)
				arcRecordCopy.setImportantFlag(true);
			else {
				arcRecordCopy.setImportantFlag(false);
			}
			if (arcRecord.getIncomeNo() != null)
				arcRecordCopy.setOutcomingNumFlag(true);
			else {
				arcRecordCopy.setOutcomingNumFlag(false);
			}
			arcRecordCopy.setRecTitle(" " + "  ØªÙ… ØªÙƒÙ„ÙŠÙ�    " + charging.getEmployeInChargingNameBefore() + "  Ø¨Ø¯Ù„Ø§ Ù…Ù†  "
					+ charging.getEmployeInChargingNameAfter() + "  Ù�ÙŠ Ø§Ù„Ù�ØªØ±Ø© Ù…Ù†   " + charging.getChargingStratDate()
					+ " Ø¥Ù„Ù‰ " + charging.getChargingEndDate()

			);

			WrkApplication wrkApplicationCopy = new WrkApplication(wrkApplication);
			wrkApplicationCopy
					.setApplicationUsercomment(" " + "  ØªÙ… ØªÙƒÙ„ÙŠÙ�    " + charging.getEmployeInChargingNameBefore()
							+ "  Ø¨Ø¯Ù„Ø§ Ù…Ù†  " + charging.getEmployeInChargingNameAfter() + "  Ù�ÙŠ Ø§Ù„Ù�ØªØ±Ø© Ù…Ù†   "
							+ charging.getChargingStratDate() + " Ø¥Ù„Ù‰ " + charging.getChargingEndDate()

			);
			wrkApplicationCopy.setToUserId(id);

			AddCopyInternalMemo(wrkApplicationCopy, arcRecordCopy, attachmentIds);

		}
		wrkApplication = new WrkApplication();
		arcRecord = new ArcRecords();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bkeryah.service.IDataAccessService#sendRecordToArchive(com.bkeryah.
	 * bean.UserMailClass)
	 */
	@Override
	@Transactional
	public void sendRecordToArchive(UserMailObj selectedInbox) {
		Integer archiveId = getResponsibleId(MyConstants.ARCHIVE_USER);
		if (archiveId != null && archiveId > 0)
			archiveRecord(selectedInbox, archiveId);
	}

	/**
	 * Archive one record
	 * 
	 * @param selectedInbox
	 */
	private void archiveRecord(UserMailObj selectedInbox, Integer userId) {
		WrkApplicationId wrkId = new WrkApplicationId(Integer.parseInt(selectedInbox.WrkId), selectedInbox.StepId);
		WrkApplication application = new WrkApplication();
		application.setArcRecordId(Integer.parseInt(selectedInbox.AppId));
		application.setToUserId(userId);
		application.setApplicationPurpose(0);
		application.setApplicationUsercomment(Utils.loadMessagesFromFile("archiving.record"));
		redirectRecord(wrkId, application, null, false);
	}

	@Override
	@Transactional
	public void sendRecordListToArchive(List<UserMailObj> checkedMailsList) {
		Integer archiveId = getResponsibleId(MyConstants.ARCHIVE_USER);
		for (UserMailObj record : checkedMailsList) {
			archiveRecord(record, archiveId);
		}
	}

	@Override
	@Transactional
	public boolean redirectWrkAppCopies(WrkApplication application, List<Integer> WrkCopyEmpRecievers,
			List<Integer> WrkCopyMngRecievers) {
		
		Integer newRecId = null;
		try {
			ArcRecords applicationRecord = (ArcRecords) findEntityById(ArcRecords.class, application.getArcRecordId());

			ArcRecords newAppRecord = new ArcRecords();
			newAppRecord.setRecTitle(Utils.loadMessagesFromFile("copy.from") + " " + applicationRecord.getRecTitle());
			newAppRecord.setCreatedBy(Utils.findCurrentUser().getUserId());
			newAppRecord.setRecHDate(HijriCalendarUtil.findCurrentHijriDate());
			newAppRecord.setRecGDate(new Date());
			newAppRecord.setCreatedIn(new Date());
			newAppRecord.setApplicationType(0);
			newAppRecord.setIncomeYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
			newAppRecord.setLetterFrom(commonDao.findUserSection(Utils.findCurrentUser().getUserId()));

			newRecId = save(newAppRecord);
			List<ArcAttach> attachmnets = commonDao.getAttachmentByArchRecordId(applicationRecord.getId());
			for (ArcAttach att : attachmnets) {
				ArcRecAtt arcRecAtt = new ArcRecAtt();
				ArcRecAttId arcRecAttId = new ArcRecAttId();
				arcRecAttId.setAttachId(att.getId());
				arcRecAttId.setRecordId(newRecId);
				arcRecAtt.setId(arcRecAttId);

				saveObject(arcRecAtt);
			}
			if (WrkCopyEmpRecievers.size() > 0 | (WrkCopyMngRecievers!=null&&WrkCopyMngRecievers.size() > 0)) {
				ArcRecordsLink arcRecordLink = new ArcRecordsLink();
				arcRecordLink.setArcRecordParentId(newRecId);
				arcRecordLink.setArcRrecordChildId(application.getArcRecordId());
				arcRecordLink.setStepId(application.getId().getStepId());
				arcRecordLink.setModelType(MailTypeEnum.COPY.getValue());
				save(arcRecordLink);
			}
			if (WrkCopyEmpRecievers.size() > 0)
				for (Object empid : WrkCopyEmpRecievers) {
					int EmployeeId = Integer.parseInt(String.valueOf(empid));
					if (EmployeeId != 336) {// support1 not needed
						WrkApplication newApplication = new WrkApplication(application);
						newApplication.setToUserId(EmployeeId);
						newApplication.setArcRecordId(newRecId);
						createNewWrkApplication(newRecId, newApplication, null, false, null);
					}

				}
			if (WrkCopyMngRecievers!=null&&WrkCopyMngRecievers.size() > 0)
				for (Object mgrid : WrkCopyMngRecievers) {
					WrkApplication newApplication = new WrkApplication(application);
					int ManagerId = Integer.parseInt(String.valueOf(mgrid));
					newApplication.setToUserId(ManagerId);
					// application.setId(new
					// WrkApplicationId(commonDao.createWrkId(), 1));
					newApplication.setArcRecordId(newRecId);
					createNewWrkApplication(newRecId, newApplication, null, false, null);
				}

		} catch (Exception e) {
			logger.error(" sendWrkApp newRecId :" + newRecId + "  wrkAppId :" + application.getId().getApplicationId());
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public int findEmployeeInboxCount(Integer userId) {
		return commonDao.findEmployeeInboxCount(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void sendWrkApplication(WrkApplicationId wrkId, WrkApplication application, boolean isSecret,
			List<Integer> WrkCopyEmpRecievers, List<Integer> WrkCopyMngRecievers) {
		try {
			redirectRecord(wrkId, application, null, isSecret);
			redirectWrkAppCopies(application, WrkCopyEmpRecievers, WrkCopyMngRecievers);
		} catch (Exception e) {
			logger.error(" wrkAppId :" + application.getId().getApplicationId());
		}
	}

	@Override
	@Transactional
	public void saveFineReboundMaster(FineReboundMaster fineReboundMaster, List<AttachmentModel> attachList)
			throws Exception {

		List<Integer> attachIds = addAttachs(attachList);

		ArcUsers user = Utils.findCurrentUser();
		String title = MessageFormat.format(Utils.loadMessagesFromFile("fine.rebound.title"),
				fineReboundMaster.getFineAplOwnerName(), "" + fineReboundMaster.getFineAplOwnerId());
		Integer fromId = user.getUserId();
		HrsScenarioDocument currentModel = getDestinationModel(MailTypeEnum.FINE_REBOUND.getValue(), 1);
		Integer toId = Integer.parseInt(getPropertiesValueById(currentModel.getToId()));

		// Integer toId =
		// getNextScenarioUserId(MailTypeEnum.FINE_REBOUND.getValue(),
		// app.getId().getApplicationId(), app.getArcRecordId(), 1);
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());

		if (fromId < 1 || toId < 1 || title == null) {
			return;
		}

		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setApplicationType(MailTypeEnum.FINE_REBOUND.getValue());
		arcRecords.setRecTitle(title);
		int recordId = createNewArcRecord(arcRecords, false, toId);

		WrkApplication application = new WrkApplication();
		application.setToUserId(toId);
		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		String userComment = title;
		createNewWrkApplication(recordId, application, userComment, false, applicationUserDeptJob);
		int vacationId = createFineReboundMaster(fineReboundMaster);
		saveHrsSigns(recordId, vacationId, false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.FINE_REBOUND.getValue());
		saveAttachs(attachIds, recordId);
	}

	private int createFineReboundMaster(FineReboundMaster fineReboundMaster) {
		fineReboundMaster.setFineCreatedBy(Utils.findCurrentUser().getUserId());
		fineReboundMaster.setFineCreatedIn(HijriCalendarUtil.findCurrentHijriDate());
		return commonDao.save(fineReboundMaster);
	}

	@Override
	public FineReboundMaster getFineReboundMasterByArcRecordId(Integer recordId) {
		Integer docId = commonDao.getDocIdHrsSignByRecId(recordId);
		return (FineReboundMaster) commonDao.findEntityById(FineReboundMaster.class, docId);
	}

	@Override
	public Integer getHrsSignNextStep(Integer recordId) {
		return commonDao.getHrsSignNextStep(recordId);
	}

	@Override
	@Transactional
	public void addFolderForUser(WrkInboxFolder folderInfo) {
		commonDao.addFolderForUser(folderInfo);

	}

	@Override
	@Transactional
	public void addMailsToFolder(List<WrkUserFolderMail> mails) {
		commonDao.addMailsToFolder(mails);

	}

	@Override
	@Transactional
	public List<WrkInboxFolder> getAllfoldersForUser(int userId) {
		// TODO Auto-generated method stub
		return commonDao.getAllfoldersForUser(userId);
	}

	@Override
	@Transactional
	public List<WrkUserFolderMail> getAllaMailsInFolder(int folderId) {
		return commonDao.getAllaMailsInFolder(folderId);
	}

	@Override
	public int getFolderNameForWrkApp(int stepId, int wrkId) {

		return commonDao.getFolderNameForWrkApp(stepId, wrkId);
	}

	@Override
	public WrkUserFolderMail getFolderMailInfoByMailIdStepNum(int stepId, int wrkId) {
		// TODO Auto-generated method stub
		return commonDao.getFolderMailInfoByMailIdStepNum(stepId, wrkId);
	}

	@Override
	public void deleteMailFomFolder(WrkUserFolderMail folderMail) {
		commonDao.deleteMailFomFolder(folderMail);

	}

	@Override
	@Transactional
	public List<ArcRecords> searchArcRecords(String subject, String letter, Integer recordSender, Integer incomeNB,
			String letterFromNo, List<Integer> selectedStructIdList, boolean employer) {
		return commonDao.searchArcRecords(subject, letter, recordSender, incomeNB, letterFromNo, selectedStructIdList,
				employer);
	}

	@Override
	@Transactional
	public List<WrkSection> loadArcDocumentStructList() {
		return commonDao.loadArcDocumentStructList();
	}

	public synchronized int createArcRecordsId() {
		return commonDao.createArcRecordsId();
	}

	public List<EmployeeForDropDown> getSpecialEmployeeList() {
		return commonDao.getSpecialEmployeeList();
	}

	private void chargingEmp(int chargingEmpIn) {
		dataAccessDAO.chargingEmp(chargingEmpIn);

	}

	@Override
	public void finishChargingEmp(int chargingEmpIn) {
		dataAccessDAO.finishChargingEmp(chargingEmpIn);

	}

	@Override
	@Transactional
	public void addChargingProcess(Charging charging) {
		charging.setEmployeInChargingNameAfter(charging.getEmployeInChargingNameAfter() + " Ø§Ù„Ù…ÙƒÙ„Ù�");
		save(charging);
	//	sendInternalMemoryForCopyInCharging(charging);
		chargingEmp(charging.getEmployeInChargingId());

	}

	@Override
	@Transactional
	public ArcUsers getUserByLoginName(String loginName) {
		return commonDao.getUserByLoginName(loginName);
	}

	@Override
	public void addNewArticleGroup(ArticleGroup articleGroup) {
		commonDao.addNewArticleGroup(articleGroup);

	}

	@Override
	public List<ArticleGroup> getAllArticleGroups() {
		// TODO Auto-generated method stub
		return commonDao.getAllArticleGroups();
	}

	@Override
	public void addNewArticleSubGroup(ArticleSubGroup articleSubGroup) {
		commonDao.addNewArticleSubGroup(articleSubGroup);

	}

	@Override
	public List<ArticleSubGroup> getAllArticleSubGroups() {
		// TODO Auto-generated method stub
		return commonDao.getAllArticleSubGroups();
	}

	@Override
	public List<Article> getAllArticles(Integer strNo) {
		// TODO Auto-generated method stub
		return commonDao.getAllArticles(strNo);
	}

	@Override
	public void addNewArticle(Article article) {
		commonDao.addNewArticle(article);

	}

	@Override
	public List<ItemUnite> getAllUnites() {
		// TODO Auto-generated method stub
		return commonDao.getAllUnites();
	}
	/*
	 * @Override public List<Integer> getAttachmentIdsByRecordId(int arcRecordID) {
	 * // TODO Auto-generated method stub return null; }
	 */

	@Override
	public List<WrkDept> findAllDepartments() {
		List wrkdep = commonDao.findAll(WrkDept.class);
		return wrkdep;
	}

	@Override
	public ArcUsers loadUserById(Integer employerId) {
		return (ArcUsers) commonDao.findEntityById(ArcUsers.class, employerId);
	}

	@Override
	public Integer redirectCopy(int stepId, int origWrkId, String newComm, int toId, int toIdCopy, Integer newArcRecord,
			String toUserId) {
		Integer NewArcRecordId = 0;
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{?=call FUN_REDIRECT_COPY(?,?,?,?,?,?)}";
		try {
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(2, stepId);
			callableStatement.setInt(3, origWrkId);
			callableStatement.setString(4, newComm);
			callableStatement.setInt(5, toId);
			callableStatement.setInt(6, toIdCopy);
			callableStatement.setString(7, toUserId);
			callableStatement.registerOutParameter(1, OracleTypes.NUMBER);

			callableStatement.execute();

			NewArcRecordId = ((BigDecimal) callableStatement.getObject(1)).intValue();

			callableStatement.close();

		} catch (Exception e) {
			logger.error("redirectCopy wrkId " + origWrkId + "   " + e.toString());

		} finally {
			try {
				connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return NewArcRecordId;
	}

	@Override
	@Transactional
	public void retrieveRecordFromArchive(int recordId, int userId) {
		WrkApplication wrkapp = commonDao.findRecordLastStep(recordId);
		WrkApplicationId wrkApplicationId = new WrkApplicationId(wrkapp.getId().getApplicationId(),
				wrkapp.getId().getStepId() + 1);
		WrkApplication newWrkapp = new WrkApplication();
		newWrkapp.setId(wrkApplicationId);
		newWrkapp.setFromUserId(wrkapp.getToUserId());
		newWrkapp.setToUserId(userId);
		// newWrkapp.setApplicationUsercommentStr("Ã˜ÂªÃ™â€¦Ã˜Âª
		// Ã˜Â§Ã™â€žÃ˜Â¥Ã˜Â­Ã˜Â§Ã™â€žÃ˜Â©
		// Ã˜Â¨Ã™Ë†Ã˜Â§Ã˜Â³Ã˜Â·Ã˜Â© " +
		// Utils.findCurrentUser().getFirstName() + " "
		// + Utils.findCurrentUser().getLastName() + "
		// Ã™â€žÃ™â€žÃ˜Â§Ã˜Â³Ã˜ÂªÃ˜Â±Ã˜Â¬Ã˜Â§Ã˜Â¹ Ã™â€¦Ã™â€ 
		// Ã˜Â£Ã˜Â±Ã˜Â´Ã™Å Ã™ï¿½ Ã˜Â§Ã™â€žÃ˜Â­Ã™ï¿½Ã˜Â¸
		// ");
		newWrkapp.setApplicationPurpose(19);
		newWrkapp.setArcRecordId(wrkapp.getArcRecordId());
		newWrkapp.setApplicationIsVisible(1);
		newWrkapp.setApplicationCreateDate(new Date());
		newWrkapp.setApplicationCreateTime(HijriCalendarUtil.findCurrentTime());
		newWrkapp.setApplicationIsRead(0);
		newWrkapp.setApplicationIsReply(0);
		newWrkapp.setApplicationIsVisible(1);
		newWrkapp.setHijriDate(HijriCalendarUtil.findCurrentHijriDate());

		List<WrkApplication> allRecordWrkApplications = commonDao
				.findAllWrkApplicationById(wrkapp.getId().getApplicationId());
		for (WrkApplication wrkApplication : allRecordWrkApplications) {
			wrkApplication.setApplicationIsVisible(0);
			wrkApplication.setApplicationIsRead(1);
			commonDao.update(wrkApplication);
		}
		commonDao.saveObject(newWrkapp);

	}

	@Override
	@Transactional
	public WrkApplication createNewWrkAppForRefuse(WrkApplicationId wrkID, String reason) {
		WrkApplication oldWrkApp = findWrkApplicationById(wrkID);
		int userTo = getUserIdFromWorkAppByIdAndStepId(oldWrkApp.getArcRecordId(), 1);
		/**** begin create new application **/
		WrkApplication newApplication = new WrkApplication(oldWrkApp);
		WrkApplicationId id = new WrkApplicationId(wrkID.getApplicationId(), oldWrkApp.getId().getStepId() + 1);
		newApplication.setId(id);
		newApplication.setToUserId(userTo);
		newApplication.setApplicationUsercomment(reason);
		return newApplication;
	}

	@Override
	@Transactional
	public void refuseExchangeRequest(WrkApplicationId wrkId, Integer recordId, ExchangeRequest request,
			String wrkAppComment, int applicationPurpose) {

		WrkApplication newApplication = createNewWrkAppForRefuse(wrkId, wrkAppComment);
		refuseModel(newApplication, wrkId, request,
				Utils.loadMessagesFromFile("reject.request.for") + " " + wrkAppComment, applicationPurpose);

	}

	@Override
	@Transactional
	public List<ArcUsers> loadTechnicalUsers() {
		return commonDao.loadTechnicalUsers();
	}

	public List<ArticleSubGroup> getAllArticleSubGroupsByGroupId(int groupId) {
		return commonDao.getAllArticleSubGroupsByGroupId(groupId);
	}

	@Override
	public InventoryMaster getInventoryMasterById(int Id) {
		return (InventoryMaster) findEntityById(InventoryMaster.class, Id);
	}

	@Override
	@Transactional
	public Integer addInventories(List<InventoryRecord> inventoryList) {

		for (InventoryRecord invo : inventoryList) {
			// exc.setGeneralRequestNumber(exchangeRequestId);
			Integer invoId = addNewInventoryDetailsRecors(invo);

		}
		return null;
	}

	@Override
	@Transactional
	public Integer addNewInventoryDetailsRecors(InventoryRecord inventoryRecord) {
		return save(inventoryRecord);

	}

	@Override
	@Transactional
	public List<InventoryModel> ListInventories(int strNo, Integer inventoryId) {
		return dataAccessDAO.ListInventories(strNo, inventoryId);
	}

	@Override
	@Transactional
	public List<StoreRequestModel> getTransactionsQty(int articleId, int strNo) {
		return dataAccessDAO.getTransactionsQty(articleId, strNo);
	}

	@Override
	@Transactional
	public List<InventoryRecord> getInventoryrecordByarticleId(int articleId, Integer inventoryId) {
		return commonDao.getAllInventoryByArticleId(articleId, inventoryId);
	}

	@Override
	public Integer saveLetterHistory(int letterId, String letterBody) {
		WrkCommentHistory wch = new WrkCommentHistory();
		wch.setCommNumber(letterId);
		wch.setEditDate(new Date());
		wch.setLongComment(letterBody);
		wch.setUserId(Utils.findCurrentUser().getUserId());
		return save(wch);
	}

	@Override
	@Transactional
	public void deleteObject(Object object) {
		commonDao.deleteObject(object);
	}

	@Override
	@Transactional
	public List<HrsMasterFile> loadHrsMasterEmployers() {
		// TODO Auto-generated method stub
		return commonDao.loadHrsMasterEmployers();
	}

	@Override
	@Transactional

	public List<HrEmployeeVacation> loadVacationInfo(Integer employerId, Integer vacationid) {

		return commonDao.loadVacationInfo(employerId, vacationid);
	}

	@Override
	public List<VacationsType> loadVacationsType() {
		// TODO Auto-generated method stub
		return commonDao.loadVacationsType();
	}

	@Override
	@Transactional
	public void redirectExchangeRequest(WrkApplicationId oldWrkAppId, int wrkReceiver, String notes) {
		WrkApplication newWrkApplication = new WrkApplication();
		newWrkApplication.setToUserId(wrkReceiver);
		newWrkApplication.setApplicationUsercomment(notes);
		redirectRecord(oldWrkAppId, newWrkApplication, null, false);
	}

	@Override
	@Transactional
	public List<ArcAttach> getAttachmentByIncomeNumber(int incomeNumber) {
		List<ArcAttach> result = new ArrayList<>();
		try {
			Set<ArcAttach> attList = new HashSet<ArcAttach>();
			List<Integer> atachtsIds = commonDao.findAllOldIncomes(incomeNumber);
			for (Integer incomNo : atachtsIds) {
				ArcRecords arcRecord = commonDao.findRecordByIncomeNumber(incomNo);
				List<ArcAttach> oldAttachts = commonDao.getAttachmentByArchRecordId(arcRecord.getId());
				if (oldAttachts.size() > 0)
					attList.addAll(oldAttachts);
			}

			result.addAll(attList);

			// ArcAttach letterAttach = new ArcAttach();
			// letterAttach.setAttachName("309");
			// letterAttach.setAttachDate(new Date());
			// letterAttach.setAttachOwner(Utils.findCurrentUser().getUserId());
			// WrkComment wrkComment =
			// commonDao.findWrkCommentByAppNo(incomeNumber + "");
			// try {
			// letterAttach.setText1(MyConstants.FTP_SERVER_FOLDER_LETTERS +
			// wrkComment.getPdfComment());
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// letterAttach.setAttachCategory("DATA");
			// // int letterAttachId = commonDao.save(letterAttach);
			// result.add(letterAttach);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	@Override
	public String createLetterLinkfromIncomeNo(int incomeNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArcRecords findRecordByIncomeNumber(int incomeNumber) {

		return commonDao.findRecordByIncomeNumber(incomeNumber);
	}

	@Override
	@Transactional
	public Integer getPropertiesValue(String propName) {
		String propValue = commonDao.getPropertiesValue(propName);
		Integer propValueNumber = ((propValue != null) ? Integer.parseInt(propValue.trim()) : null);
		return propValueNumber;
	}

	@Override
	public List<TechnicalUsers> loadNewTechnicalUsers() {
		return commonDao.loadNewTechnicalUsers();
	}

	@Override
	@Transactional
	public Integer addNewTechnicalResponse(TechnicalResponse technicalResponse) {
		return save(technicalResponse);
	}

	@Override
	@Transactional
	public List<TechnicalResponse> getTechnicalResponsesByRecordId(Integer archRecordId) {
		return commonDao.getTechnicalResponsesByRecordId(archRecordId);
	}

	@Override
	@Transactional
	public List<ArcPeople> loadnameEmployerbynationid(Long nationId) {
		return commonDao.loadnameEmployerbynationid(nationId);
	}

	@Override
	@Transactional
	public List<SysCategoryEmployer> loadCategoryEmployer() {
		List sysCategoryType = commonDao.findAll(SysCategoryEmployer.class);
		return sysCategoryType;
	}

	@Override
	@Transactional
	public List<SysReligion> loadReligionEmployer() {
		List sysReligionType = commonDao.findAll(SysReligion.class);
		return sysReligionType;
	}

	@Override
	@Transactional
	public List<SysQualification> loadQualifiedemployer() {
		List sysQualifiedType = commonDao.findAll(SysQualification.class);
		return sysQualifiedType;
	}

	@Override
	@Transactional
	public List<SysBirthCountry> loadBirthCountry() {
		List syscountry = commonDao.findAll(SysBirthCountry.class);
		return syscountry;
	}

	@Override
	@Transactional
	public List<SysGraduatePlace> loadGraduatEmploye() {
		List gradute = commonDao.findAll(SysGraduatePlace.class);
		return gradute;
	}

	@Override
	@Transactional
	public List<SysNationality> loadNationalEmploye() {
		List nationality = commonDao.findAll(SysNationality.class);
		return nationality;
	}

	@Override
	@Transactional
	public List<SysSpecialization> loadSpecialEmploye() {
		List special = commonDao.findAll(SysSpecialization.class);
		return special;
	}

	@Override
	@Transactional
	public List<PayBank> loadbank() {
		List bank = commonDao.findAll(PayBank.class);
		return bank;
	}

	@Override
	@Transactional
	public List<HrsJobCreation> loadHrJob(Integer catid, Integer status, Integer rank) {
		return commonDao.loadHrJob(catid, status, rank);
	}

	@Override
	public WrkComment findWrkCommentByAppNo(String appNo) {
		return commonDao.findWrkCommentByAppNo(appNo);
	}

	@Override
	public boolean recordIslinked(int currentIncome) {
		return commonDao.recordIslinked(currentIncome);
	}

	@Override
	@Transactional
	public Integer saveBill(PayLicBills bill, List<PayBillDetails> billDetails) {
		if (bill.getBillNumber() == null) {
			int billNumber = commonDao.findMaxBillId() + 1;
			bill.setBillNumber(billNumber);
			bill.setBillStatus(0);
			bill.setBillDate(HijriCalendarUtil.findCurrentHijriDate());
			save(bill);
			for (PayBillDetails billDetail : billDetails) {
				if (billDetail.getAmount() > 0) {
					billDetail.setBillNumber(billNumber);
					if (billDetail.getPayMaster() == null) {
						billDetail.setPayMaster(1438);
						billDetail.setPayDetails(1438);
					}
					billDetail.setCreatedIn(new Date());
					billDetail.setCreatedBy(Utils.findCurrentUser().getUserId());
					billDetail.setBillHYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
					billDetail.setBillGYear(Calendar.getInstance().get(Calendar.YEAR));
					save(billDetail);
				}
			}
		}
		return bill.getBillNumber();
	}

	@Override
	@Transactional
	public void saveGeneralAppreciation(HrsGeneralAppreciation generalAppreciation,
			List<HrsCompactEmpCaracter> listEmpCaracters, String title, Integer EmpId, List<Integer> attachmnetIds) {

		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setApplicationType(MailTypeEnum.GENERAL_APPRECIATION.getValue());
		arcRecords.setRecTitle(title);
		arcRecords.setFilesNo(attachmnetIds.size());
		int recordId = createNewArcRecord(arcRecords, false, EmpId);

		WrkApplication application = new WrkApplication();
		application.setToUserId(Integer.parseInt(findSystemProperty("HR_ID")));
		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		String userComment = title;
		ArcUsers user = Utils.findCurrentUser();
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());
		createNewWrkApplication(recordId, application, userComment, false, applicationUserDeptJob);
		commonDao.update(generalAppreciation);
		saveAttachs(attachmnetIds, recordId);
		for (HrsCompactEmpCaracter compactEmpCaracter : listEmpCaracters) {

			compactEmpCaracter.setHrsCompactPerformid(generalAppreciation.getHrsCompactPerformanceid());
			savePerfermanceCaracter(compactEmpCaracter);
		}

		saveHrsSigns(recordId, generalAppreciation.getId(), false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.GENERAL_APPRECIATION.getValue());
	}

	@Override
	@Transactional
	public void saveMasterHrsCompactPerformance(HrsCompactPerformance hrsCompactPerformance,
			List<HrsCompactFloors> lstFloorModel, List<HrsCompactGoals> lstHrsCompactGoals, String title,
			Integer countCatNumber) {

		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setApplicationType(MailTypeEnum.COMPACT_PERFORMANCE.getValue());
		arcRecords.setRecTitle(title);
		int recordId = createNewArcRecord(arcRecords, false, hrsCompactPerformance.getEmpid());

		WrkApplication application = new WrkApplication();
		application.setToUserId(hrsCompactPerformance.getEmpid());
		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		String userComment = title.concat(hrsCompactPerformance.getEmpName());
		ArcUsers user = Utils.findCurrentUser();
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());
		createNewWrkApplication(recordId, application, userComment, false, applicationUserDeptJob);

		List<HrsCompactFloors> lstfloorsDetails = new ArrayList<>();
		List<HrsCompactBaseFloor> lstfloorsBase = new ArrayList<>();
		HrsCompactBaseFloor baseFloor = new HrsCompactBaseFloor();
		HrsCompactFloors compactFloor = new HrsCompactFloors();

		int idBaseInc = 1;
		// int countCatNumber = getAllCatFloors().size();

		if (hrsCompactPerformance.getId() == null) {
			int idbaseFloor = commonDao.findMaxHrsFloorsBase();
			int idfloor = commonDao.findMaxHrsFloors();
			hrsCompactPerformance.setStatus(0);
			int idMaster = save(hrsCompactPerformance);

			// List Goals
			for (HrsCompactGoals hrsCompactGoals : lstHrsCompactGoals) {
				hrsCompactGoals.setHrsCompactPerformid(idMaster);
				save(hrsCompactGoals);
			}
			// save first base floor
			int catId = lstFloorModel.get(0).getCatFloorId();
			baseFloor.setId(idbaseFloor);
			baseFloor.setHrsCompactPerformanceId(idMaster);
			baseFloor.setRelativeImportance((lstFloorModel.get(0).getRelativeImportance()) == null ? 0
					: lstFloorModel.get(0).getRelativeImportance());
			lstfloorsBase.add(baseFloor);
			// save first floor details
			for (int x = 0; x < lstFloorModel.size(); x++) {
				if (lstFloorModel.get(x).getCatFloorId().equals(catId)) {
					compactFloor = new HrsCompactFloors();
					compactFloor.setCatFloorId(catId);
					compactFloor.setHrsCompactBasefloorid(idbaseFloor);
					compactFloor.setFloorsid(lstFloorModel.get(x).getFloorsid());
					compactFloor.setHrsCompactPerformanceid(idMaster);
					compactFloor.setEvaluation(
							(lstFloorModel.get(x).getEvaluation()) == null ? 0 : lstFloorModel.get(x).getEvaluation());
					lstfloorsDetails.add(compactFloor);

					compactFloor = null;
				}

			}
			idbaseFloor++;

			while (idBaseInc < countCatNumber) {
				for (int i = 1; i < lstFloorModel.size(); i++) {

					if (!lstFloorModel.get(i).getCatFloorId().equals(catId) && idBaseInc < countCatNumber) {
						baseFloor = new HrsCompactBaseFloor();
						baseFloor.setHrsCompactPerformanceId(idMaster);
						catId = lstFloorModel.get(i).getCatFloorId();
						// baseFloor.setId(catId);
						baseFloor.setRelativeImportance((lstFloorModel.get(i).getRelativeImportance()) == null ? 0
								: lstFloorModel.get(i).getRelativeImportance());
						baseFloor.setId(idbaseFloor);
						lstfloorsBase.add(baseFloor);
						// fill list goal
						for (int j = 0; j < lstFloorModel.size(); j++) {
							while (j < lstFloorModel.size() && lstFloorModel.get(j).getCatFloorId().equals(catId)) {
								compactFloor = new HrsCompactFloors();
								compactFloor.setCatFloorId(catId);
								compactFloor.setHrsCompactBasefloorid(idbaseFloor);
								compactFloor.setFloorsid(lstFloorModel.get(j).getFloorsid());
								compactFloor.setHrsCompactPerformanceid(idMaster);
								compactFloor.setEvaluation((lstFloorModel.get(j).getEvaluation()) == null ? 0
										: lstFloorModel.get(j).getEvaluation());
								lstfloorsDetails.add(compactFloor);
								idfloor++;
								j++;
								// compactFloor = null;
							}

						}
						baseFloor = null;
						idBaseInc++;
						idbaseFloor++;

					}

				}
			}

			// Save list BaseFloors
			for (HrsCompactBaseFloor hrsCompactBaseFloor : lstfloorsBase) {
				save(hrsCompactBaseFloor);
			}
			// Save List Goal
			for (HrsCompactFloors hrsCompactFloor : lstfloorsDetails) {
				save(hrsCompactFloor);
			}
			saveHrsSigns(recordId, idMaster, false, null, Utils.findCurrentUser().getUserId(),
					MailTypeEnum.COMPACT_PERFORMANCE.getValue());

		}
	}

	@Override
	@Transactional

	public HrsJobCreation loadSelectedJob(Integer createId, Integer status) {
		return commonDao.loadSelecredJob(createId, status);
	}

	@Override
	@Transactional
	public ArcRecordsLink getArcLinkByRecordId(Integer archRecordId) {
		return commonDao.getArcLinkByRecordId(archRecordId);
	}

	@Override
	public List<WrkCommentsClass> findCommentsByArcId(Integer wrkId,Integer stepId) {
		return dataAccessDAO.findCommentsByArcId(wrkId,stepId);
	}

	@Override
	@Transactional
	public ArcUsers loadUserByEmpNO(Integer employerId) {
		return commonDao.loadUserByEmpNO(employerId);
	}

	@Override
	@Transactional
	public HrsEmpHistorical getHRsEmpHistoricalByEmpNo(Integer employerId) {
		return commonDao.getHRsEmpHistoricalByEmpNo(employerId);
	}

	@Override
	@Transactional
	public HrsSalaryScale findHrsSalaryScaleById(Integer rankNumber) {
		int orderId = commonDao.findMaxOrderIdRank();
		HrsSalaryScale hrsSalaryScale = commonDao.findHrsSalaryScaleById(HrsSalaryScale.class,
				new HrsSalaryScaleId(orderId, rankNumber));
		return hrsSalaryScale;
	}

	@Override
	@Transactional
	public Integer loadScoreSickVacation(Integer empNo) {
		return commonDao.loadScoreSickVacation(empNo);
	}

	@Override
	@Transactional
	public List<HrsSalaryScaleDgrs> loadScaleDgree(Integer rankCode) {
		// TODO Auto-generated method stub
		return commonDao.loadScaleDgree(rankCode);
	}

	@Override
	@Transactional
	public HrsSalaryScaleDgrs loadSelectedDegree(Integer dgreeId) {
		// TODO Auto-generated method stub
		return commonDao.loadSelectedDegree(dgreeId);
	}

	@Override
	@Transactional
	public HrsSalaryScaleDgrs loadSelectedDegree(Integer classCode, Integer rankCode) {
		// TODO Auto-generated method stub
		return commonDao.loadSelectedDegree(classCode, rankCode);
	}

	@Override
	@Transactional
	public Integer saveNewEmployee(HrsMasterFile hrmasfile, HrsEmpHistorical empHistorical, HrsJobCreation selectJob) {
		Integer isfound = 0;

		if (hrmasfile.getCactegoryId() == 1 || hrmasfile.getCactegoryId() == 2 || hrmasfile.getCactegoryId() == 4) {
			isfound = commonDao.foundCountEmp(hrmasfile.getFirstApplicationDate(), hrmasfile.getCactegoryId());
		}

		Integer employerNumber = 0;
		if (isfound == 0) {
			if (hrmasfile.getCactegoryId() == 1 || hrmasfile.getCactegoryId() == 2 || hrmasfile.getCactegoryId() == 4) {
				employerNumber = Integer.parseInt(
						hrmasfile.getFirstApplicationDate().substring(7, 10) + hrmasfile.getCactegoryId() + "001");
			} else {
				employerNumber = commonDao.getMaxEmpNoWorkers();
			}
		} else {

			employerNumber = commonDao.getMaxEmployerNumber(hrmasfile.getFirstApplicationDate(),
					hrmasfile.getCactegoryId());
		}
		;
		hrmasfile.setEmployeNumber(employerNumber);
		hrmasfile.setCreatedBy(Utils.findCurrentUser().getUserId());
		hrmasfile.setEmployerStatus(1);
		// date

		empHistorical.setId(new HrsEmpHistoricalId(employerNumber, 1));
		empHistorical.setCreatedBy(Utils.findCurrentUser().getUserId());

		return null;
	}

	@Override
	@Transactional
	public Integer saveEmployer(HrsMasterFile hrmasfile, HrsEmpHistorical empHistorical, HrsJobCreation selectJob) {
		Integer isfound = 0;
		if (hrmasfile.getCactegoryId() == 1 || hrmasfile.getCactegoryId() == 2 || hrmasfile.getCactegoryId() == 4) {
			isfound = commonDao.foundCountEmp(hrmasfile.getFirstApplicationDate(), hrmasfile.getCactegoryId());
		}

		Integer employerNumber = 0;
		if (isfound == 0) {
			if (hrmasfile.getCactegoryId() == 1 || hrmasfile.getCactegoryId() == 2 || hrmasfile.getCactegoryId() == 4) {
				employerNumber = Integer.parseInt(
						hrmasfile.getFirstApplicationDate().substring(7, 10) + hrmasfile.getCactegoryId() + "001");
			} else {
				employerNumber = commonDao.getMaxEmpNoWorkers();
			}
		} else {

			employerNumber = commonDao.getMaxEmployerNumber(hrmasfile.getFirstApplicationDate(),
					hrmasfile.getCactegoryId());
		}
		;

		hrmasfile.setEmployeNumber(employerNumber);
		hrmasfile.setCreatedBy(Utils.findCurrentUser().getUserId());
		hrmasfile.setEmployerStatus(1);
		hrmasfile.setCreatedDate(new Date());
		empHistorical.setId(new HrsEmpHistoricalId(employerNumber, 1));
		empHistorical.setCreatedBy(Utils.findCurrentUser().getUserId());
		empHistorical.setCreateDate(new Date());
		empHistorical.setRecordType(101);
		HrsJobHistorical jobHistorical = new HrsJobHistorical();
		int serial = commonDao.getMaxSerialJobHistorical(empHistorical.getJobcode(), empHistorical.getJobNumber());
		jobHistorical.setEmployerNumber(employerNumber);
		jobHistorical.setSerial(serial);
		jobHistorical.setJobCode(empHistorical.getJobcode());
		jobHistorical.setJobNumber(empHistorical.getJobNumber());
		jobHistorical.setRankCode(empHistorical.getRankNumber());
		jobHistorical.setClassCode(empHistorical.getClassNumber());
		jobHistorical.setBasicSalary(empHistorical.getBasicSalary());
		jobHistorical.setTransportSalary(empHistorical.getTransferSalary());
		jobHistorical.setMandatoryIn(empHistorical.getMandateInner());
		jobHistorical.setJobStatus(3);
		jobHistorical.setJobAction(6);
		jobHistorical.setJobYear(selectJob.getJobYear());
		jobHistorical.setCreatedBy(Utils.findCurrentUser().getUserId());
		jobHistorical.setCreateDate(new Date());
		commonDao.saveObject(hrmasfile);
		commonDao.saveObject(empHistorical);
		if (hrmasfile.getCactegoryId() == 1 || hrmasfile.getCactegoryId() == 2 || hrmasfile.getCactegoryId() == 4) {
			commonDao.update(selectJob);
			commonDao.save(jobHistorical);
		}

		return hrmasfile.getEmployeNumber();
	}

	@Override
	@Transactional
	public HrEmployeeVacation loadLastVacation(Integer empNB, int sickVacation) {
		return commonDao.loadLastVacation(empNB, sickVacation);
	}

	@Override
	@Transactional
	public HrEmployeeVacation loadVacationByArcRecord(int recordId) {
		Integer DocId = commonDao.getDocIdHrsSignByRecId(recordId);
		return (HrEmployeeVacation) commonDao.findEntityById(HrEmployeeVacation.class, DocId);
	}

	@Override
	@Transactional
	public StockEntryMaster getStockEntryMasterByArchRecordId(Integer archRecordId) {
		Integer DocId = commonDao.getDocIdHrsSignByRecId(archRecordId);
		return (StockEntryMaster) commonDao.findEntityById(StockEntryMaster.class, DocId);
	}

	public void acceptLicTrdModel(WrkApplicationId wrkId, TrdModelClass selectedTrdApplication) {
		WrkApplication app = (WrkApplication) commonDao.findWrkApplicationById(wrkId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(wrkId);
		application.setId(wrkId);
		application.getId().setStepId(application.getId().getStepId() + 1);

		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(MailTypeEnum.LIC.getValue(), app.getId().getApplicationId(),
				app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		createNewWrkApplication(application.getArcRecordId(), application, null, false, null);

		boolean visible = (getDestinationModel(MailTypeEnum.LIC.getValue(), acceptCount).getSigned() == 1);
		saveHrsSigns(application.getArcRecordId(), selectedTrdApplication.getLicApplicationId(), visible, null,
				Utils.findCurrentUser().getUserId(), MailTypeEnum.LIC.getValue());
		if (visible) {
			// employeeVacation.setVacationStatus(MyConstants.YES);
			updateArcRecordsIncomeNo(app.getArcRecordId());
			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			// try {
			// employeeVacation
			// .setExcuseDateHigri(HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(sdf.format(new
			// Date())));
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }
			// employeeVacation.setExcuseNumber(commonDao.createInocmeNUmber());
			//
			// commonDao.update(employeeVacation);
		}
	}

	@Override
	public Charging getChargingemployer(Integer userId) {
		// TODO Auto-generated method stub
		return commonDao.getChargingemployer(userId);
	}

	@Override
	@Transactional
	public List<ExchangeRequest> searchExchangeRequests(String beginDate, String finishDate, Integer strNo) {
		return dataAccessDAO.searchExchangeRequests(beginDate, finishDate, strNo);
	}

	@Override
	@Transactional
	public int nationalIsFound(Long nationalNumber, Integer cat) {
		// TODO Auto-generated method stub
		return commonDao.nationalIsFound(nationalNumber, cat);
	}

	@Override
	@Transactional
	public List<HrsMasterFile> findAllEmplyersWorks() {
		// TODO Auto-generated method stub
		List emps = commonDao.findAll(HrsMasterFile.class);
		;
		return emps;
	}

	@Override
	@Transactional
	public HrsMasterFile loadEmployerData(Integer empno) {
		// TODO Auto-generated method stub
		return commonDao.loadEmployerData(empno);
	}

	@Override
	@Transactional
	public HrsEmpHistorical loadEmployerDataHistorcial(Integer empno) {
		// TODO Auto-generated method stub
		return commonDao.loadEmployerDataHistorcial(empno);
	}

	@Override
	@Transactional
	public List<StoreRequestModel> getArticleHistory(Integer articleId) {
		return dataAccessDAO.getArticleHistory(articleId);
	}

	@Override
	@Transactional
	public List<UserRoles> loadAllRoles() {
		return commonDao.loadAllRoles();
	}

	@Override
	@Transactional
	public List<SysProperties> loadAllSysProperties() {
		return commonDao.loadAllSysProperties();
	}

	@Override
	@Transactional
	public void updateUserRoles(ArcUsers selectedUser, List<String> selectedRoles) {
		Set<UserRoles> roles = selectedUser.getRoles();
		if (roles != null) {
			for (UserRoles role : roles) {
				if (!selectedRoles.contains(role.getId()))
					commonDao.deleteUserRole(selectedUser.getUserId(), role.getId());
			}
		}
		for (String roleId : selectedRoles) {
			if (!roles.contains(roleId)) {
				RolePriv role = new RolePriv(selectedUser.getUserId(), new Integer(roleId));
				commonDao.save(role);
			}
		}
	}

	@Override
	@Transactional
	public List<HrScenario> loadDistinctScenarios() {
		return commonDao.loadDistinctScenarios();
	}

	@Override
	@Transactional
	public List<FinEntity> loadSupliers() {
		return commonDao.loadSupliers();
	}

	@Override
	@Transactional
	public Integer getCreatedId(String jobcode, Integer jobNumber) {
		return commonDao.getCreatedId(jobcode, jobNumber);
	}

	@Override
	@Transactional
	public List<UserRoles> loadUserRoles(Integer userId) {
		return commonDao.loadUserRoles(userId);
	}

	@Override
	@Transactional
	public void updateScenario(HrScenario scenario, List<HrsScenarioDocument> removedSteps) {
		if (scenario.getStepsCount() != scenario.getDocuments().size()) {
			scenario.setStepsCount(scenario.getDocuments().size());
			commonDao.update(scenario);
		}
		if ((removedSteps != null) && (!removedSteps.isEmpty())) {
			for (HrsScenarioDocument doc : removedSteps)
				commonDao.deleteObject(doc);
		}
		for (HrsScenarioDocument scen : scenario.getDocuments()) {
			if (scen.getId() == 0) {
				scen.setModelId(scenario.getModelId());
				commonDao.save(scen);
			} else
				commonDao.update(scen);
		}
	}

	@Override
	@Transactional
	public List<ArcApplicationType> loadNewScenarioModels() {
		return commonDao.loadNewScenarioModels();
	}

	@Override
	@Transactional
	public List<SysProperties> loadScenarioSysProperties() {
		return commonDao.loadScenarioSysProperties();
	}

	@Override
	@Transactional
	public void saveScenario(HrScenario scenario) {
		scenario.setStepsCount(scenario.getDocuments().size());
		save(scenario);
		for (HrsScenarioDocument doc : scenario.getDocuments()) {
			doc.setModelId(scenario.getModelId());
			save(doc);
		}
	}

	@Override
	@Transactional
	public void deleteScenario(HrScenario scenario) {
		for (HrsScenarioDocument doc : scenario.getDocuments()) {
			commonDao.deleteObject(doc);
		}
		commonDao.deleteObject(scenario);
	}

	@Override
	@Transactional
	public List<BldPaperTypes> loadAllPaperTypes() {
		return commonDao.loadAllPaperTypes();
	}

	@Override
	@Transactional
	public ArcPeople loadArcPeople(long nationalId) {
		return commonDao.findArcPeopleById(nationalId);
	}

	@Override
	@Transactional
	public Integer saveNewBuilding(BldLicNew newBuilding, String title, List<AttachmentModel> attachList)
			throws ParseException {
		List<ArcAttach> attachs = Utils.SaveAttachementsToFtpServer(attachList);
		for (ArcAttach xx : attachs) {
			xx.setAttachOwner(Utils.findCurrentUser().getUserId());
		}
		List<Integer> attachIds = addAttachments(attachs);
		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setApplicationType(MyConstants.NEW_BUILDING_TYPE);
		arcRecords.setRecTitle(title);
		arcRecords.setFilesNo(attachIds.size());
		int recordId = createNewArcRecord(arcRecords, false, null);
		saveAttachs(attachIds, recordId);
		WrkApplication application = new WrkApplication();
		Integer userTo = Integer
				.parseInt(getDestinationModel(MailTypeEnum.NEW_BUILDING_LICENCE.getValue(), 1).getToUser().getValue());
		application.setToUserId(userTo);
		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		String userComment = title;
		createNewWrkApplication(recordId, application, userComment, false, null);
		int licId = saveNewBuildingModel(newBuilding);
		saveBldLicPieces(licId, newBuilding.getPieces());
		saveBUildingMasterTbl(licId, recordId, LicenceTypeEnum.NEW_BUILDING.getValue(),
				newBuilding.getLicNewAplOwnerId());
		saveHrsSigns(recordId, licId, false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.NEW_BUILDING_LICENCE.getValue());
		return licId;
	}

	private void saveBldLicPieces(int licId, List<BldLicPcs> pieces) {
		if (pieces != null) {
			for (BldLicPcs piece : pieces) {
				if ((piece.getId().getPieceId() != null) && (!piece.getId().getPieceId().trim().equals(""))) {
					piece.getId().setLicId(licId);
					saveObject(piece);
				}
			}
		}
	}

	private void deleteLastPieces(Integer licNewId) {
		commonDao.deleteNewBuildingPieces(licNewId);
	}

	private void saveBUildingMasterTbl(int licId, int recordId, int type, String nationalId) {
		BldLicMasterTbl licence = new BldLicMasterTbl();
		licence.setBldReqId(licId);
		licence.setBldReqRecId(recordId);
		licence.setBldReqTyp(type);
		licence.setBldReqNatNo(nationalId);
		save(licence);
	}

	private int saveNewBuildingModel(BldLicNew newBuilding) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		newBuilding.setLicNewHDate(HijriCalendarUtil.findCurrentHijriDate());
		newBuilding.setLicNewGDate(new Date());
		newBuilding.setLicNewWroteBy(Utils.findCurrentUser().getUserId());
		newBuilding.setLicNewWroteIn(sdf.format(new Date()));
		return commonDao.saveNewBuilding(newBuilding);
	}

	@Override
	@Transactional
	public void saveAttachBuilding(BldLicAttch attachBuilding, String title, List<AttachmentModel> attachList)
			throws ParseException {
		List<ArcAttach> attachs = Utils.SaveAttachementsToFtpServer(attachList);
		for (ArcAttach xx : attachs) {
			xx.setAttachOwner(Utils.findCurrentUser().getUserId());
		}
		List<Integer> attachIds = addAttachments(attachs);
		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setApplicationType(MyConstants.ATTACH_BUILDING_TYPE);
		arcRecords.setRecTitle(title);
		arcRecords.setFilesNo(attachIds.size());
		int recordId = createNewArcRecord(arcRecords, false, null);
		WrkApplication application = new WrkApplication();
		Integer userTo = Integer.parseInt(
				getDestinationModel(MailTypeEnum.ATTACH_BUILDING_LICENCE.getValue(), 1).getToUser().getValue());
		application.setToUserId(userTo);
		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		String userComment = title;
		createNewWrkApplication(recordId, application, userComment, false, null);
		int licId = saveAttachBuildingModel(attachBuilding);
		saveBldLicPieces(licId, attachBuilding.getPieces());
		saveBUildingMasterTbl(licId, recordId, LicenceTypeEnum.BUILDING_EXTENSION.getValue(),
				attachBuilding.getLicAttAplOwner());
		saveHrsSigns(recordId, licId, false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.ATTACH_BUILDING_LICENCE.getValue());

		saveAttachs(attachIds, recordId);
	}

	@Override
	@Transactional
	public List<ArcUsers> getAllActiveEmployeesInDept(Integer departmentId) {
		return commonDao.getAllActiveEmployeesInDept(departmentId);
	}

	@Override
	public BldLicNew getNewBuildingLicenceByArcRecordId(int recordId) {
		Integer docId = commonDao.getDocIdHrsSignByRecId(recordId);
		return (BldLicNew) commonDao.findEntityById(BldLicNew.class, docId);
	}

	@Override
	public List<BldLicBuildingUsage> loadAllBuildingUsages() {
		return commonDao.loadAllBuildingUsages();
	}

	@Override
	public List<LicAgents> loadAllEngineeringOffices() {
		return commonDao.loadAllEngineeringOffices();
	}

	@Override
	public BldLicAttch getAttachBuildingLicenceByArcRecordId(int recordId) {
		Integer docId = commonDao.getDocIdHrsSignByRecId(recordId);
		return (BldLicAttch) commonDao.findEntityById(BldLicAttch.class, docId);
	}

	private int saveAttachBuildingModel(BldLicAttch attachBuilding) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		attachBuilding.setLicAttHDate(HijriCalendarUtil.findCurrentHijriDate());
		attachBuilding.setLicAttGDate(new Date());
		attachBuilding.setLicAttWroteBy(Utils.findCurrentUser().getUserId());
		attachBuilding.setLicAttWroteIn(sdf.format(new Date()));
		return commonDao.saveAttBuilding(attachBuilding);
	}

	@Transactional
	private int saveNewBuildingWallModel(BldLicWall newBuilding) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		newBuilding.setLicWallHDate(HijriCalendarUtil.findCurrentHijriDate());
		newBuilding.setLicWallGDate(new Date());
		newBuilding.setLicWallWroteBy(Utils.findCurrentUser().getUserId());
		newBuilding.setLicWallWroteIn(sdf.format(new Date()));
		return commonDao.saveNewBuildingWall(newBuilding);
	}

	@Override
	@Transactional
	public void acceptNewBuilding(BldLicNew newBuilding, List<AttachmentModel> attachList) {
		List<ArcAttach> attachs = Utils.SaveAttachementsToFtpServer(attachList);
		for (ArcAttach xx : attachs) {
			xx.setAttachOwner(Utils.findCurrentUser().getUserId());
		}
		List<Integer> attachIds = addAttachments(attachs);
		updateObject(newBuilding);
	}

	@Override
	public BldLicWall getNewBuildingWallLicenceByArcRecordId(int recordId) {
		Integer docId = commonDao.getDocIdHrsSignByRecId(recordId);
		return (BldLicWall) commonDao.findEntityById(BldLicWall.class, docId);
	}

	@Override
	@Transactional
	public void acceptNewBuildingWall(int recordId, BldLicWall newBuilding, List<AttachmentModel> attachList) {
		List<ArcAttach> attachs = Utils.SaveAttachementsToFtpServer(attachList);
		for (ArcAttach xx : attachs) {
			xx.setAttachOwner(Utils.findCurrentUser().getUserId());
		}
		List<Integer> attachIds = addAttachments(attachs);
		saveAttachs(attachIds, recordId);
		WrkApplication app = getWrkApplicationRecordById(recordId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		application.setId(app.getId());
		application.getId().setStepId(application.getId().getStepId() + 1);
		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(MyConstants.NEW_BUILDING_WALL, app.getId().getApplicationId(),
				app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		createNewWrkApplication(application.getArcRecordId(), application, null, false, null);
		boolean visible = (getDestinationModel(MyConstants.NEW_BUILDING_WALL, acceptCount).getSigned() == 1);
		saveHrsSigns(application.getArcRecordId(), newBuilding.getLicWallId(), visible, null,
				Utils.findCurrentUser().getUserId(), MyConstants.NEW_BUILDING_WALL);
		// updateBldLicPieces(newBuilding);
		if (visible) {
			newBuilding.setLicWallSignedBy(Utils.findCurrentUser().getUserId());
			newBuilding.setLicWallSignedIn(HijriCalendarUtil.findCurrentHijriDate());
			updateArcRecordsIncomeNo(app.getArcRecordId());

		}
		commonDao.update(newBuilding);
		// updateObject(newBuilding);
	}

	@Override
	@Transactional
	public void acceptAttachBuilding(Integer recordId, BldLicAttch attachBld, List<AttachmentModel> attachList,
			boolean updateObject) {
		List<ArcAttach> attachs = Utils.SaveAttachementsToFtpServer(attachList);
		for (ArcAttach xx : attachs) {
			xx.setAttachOwner(Utils.findCurrentUser().getUserId());
		}
		List<Integer> attachIds = addAttachments(attachs);
		WrkApplication app = getWrkApplicationRecordById(recordId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		application.setId(app.getId());
		application.getId().setStepId(application.getId().getStepId() + 1);
		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(MyConstants.ATTACH_BUILDING_TYPE, app.getId().getApplicationId(),
				app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		createNewWrkApplication(application.getArcRecordId(), application, null, false, null);
		boolean visible = (getDestinationModel(MyConstants.ATTACH_BUILDING_TYPE, acceptCount).getSigned() == 1);
		saveHrsSigns(application.getArcRecordId(), attachBld.getLicAttId(), visible, null,
				Utils.findCurrentUser().getUserId(), MyConstants.ATTACH_BUILDING_TYPE);
		for (Integer id : attachIds) {
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(id);
			arcRecAttId.setRecordId(recordId);
			arcRecAtt.setId(arcRecAttId);
			saveObject(arcRecAtt);

		}
		if (updateObject) {
			if (visible) {
				attachBld.setLicAttSignedBy(Utils.findCurrentUser().getUserId());
				attachBld.setLicAttSignedIn(HijriCalendarUtil.findCurrentHijriDate());
				updateArcRecordsIncomeNo(app.getArcRecordId());

			}
			commonDao.update(attachBld);
		}
	}

	@Override
	@Transactional
	public void acceptNewBuilding(Integer recordId, BldLicNew newBuilding, List<AttachmentModel> attachList,
			boolean updateObject) {
		List<ArcAttach> attachs = Utils.SaveAttachementsToFtpServer(attachList);
		for (ArcAttach xx : attachs) {
			xx.setAttachOwner(Utils.findCurrentUser().getUserId());
		}
		List<Integer> attachIds = addAttachments(attachs);
		WrkApplication app = getWrkApplicationRecordById(recordId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		application.setId(app.getId());
		application.getId().setStepId(application.getId().getStepId() + 1);
		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(MyConstants.NEW_BUILDING_TYPE, app.getId().getApplicationId(),
				app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		createNewWrkApplication(application.getArcRecordId(), application, null, false, null);
		boolean visible = (getDestinationModel(MyConstants.NEW_BUILDING_TYPE, acceptCount).getSigned() == 1);
		saveHrsSigns(application.getArcRecordId(), newBuilding.getLicNewId(), visible, null,
				Utils.findCurrentUser().getUserId(), MyConstants.NEW_BUILDING_TYPE);
		// updateBldLicPieces(newBuilding);
		for (Integer id : attachIds) {
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(id);
			arcRecAttId.setRecordId(recordId);
			arcRecAtt.setId(arcRecAttId);
			saveObject(arcRecAtt);

		}
		if (updateObject) {
			if (visible) {
				newBuilding.setLicNewSignedBy(Utils.findCurrentUser().getUserId());
				newBuilding.setLicNewSignedIn(HijriCalendarUtil.findCurrentHijriDate());
				updateArcRecordsIncomeNo(app.getArcRecordId());

			}
			commonDao.update(newBuilding);
		}
	}

	@Override
	@Transactional
	public void saveNewBuildingWall(BldLicWall newBuilding, String title, List<AttachmentModel> attachList)
			throws ParseException {
		List<ArcAttach> attachs = Utils.SaveAttachementsToFtpServer(attachList);
		for (ArcAttach xx : attachs) {
			xx.setAttachOwner(Utils.findCurrentUser().getUserId());
		}
		List<Integer> attachIds = addAttachments(attachs);
		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setApplicationType(MyConstants.NEW_BUILDING_WALL);
		arcRecords.setRecTitle(title);
		arcRecords.setFilesNo(attachIds.size());
		int recordId = createNewArcRecord(arcRecords, false, null);
		int licId = saveNewBuildingWallModel(newBuilding);
		WrkApplication application = new WrkApplication();
		// application.setToUserId(Utils.findCurrentUser().getMgrId());
		application.setToUserId(52);
		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);

		String userComment = title;
		createNewWrkApplication(recordId, application, userComment, false, null);
		saveAttachs(attachIds, recordId);
		saveHrsSigns(recordId, licId, false, null, Utils.findCurrentUser().getUserId(), MyConstants.NEW_BUILDING_WALL);
		// saveBUildingMasterTbl(licId, recordId,
		// LicenceTypeEnum.FENCING.getValue(),
		// newBuilding.getLicWallAplOwner());
	}

	@Override
	@Transactional
	public List<LicTrdMasterFile> getTrdMasterFileList() {
		return dataAccessDAO.getTrdMasterFileList();
	}

	@Override
	@Transactional
	public Map<String, Integer> getAllNamePenalities() {

		Map<String, Integer> penalitiesList = new HashMap<>();

		List<LicTrdMasterFile> lst = getTrdMasterFileList();
		;
		for (LicTrdMasterFile licTrdMasterFile : lst) {
			penalitiesList.put(licTrdMasterFile.getLicNo() + "|" + licTrdMasterFile.getTrdName(),
					licTrdMasterFile.getId());
		}
		return penalitiesList;
	}

	@Override
	@Transactional
	public ReqFinesMaster checkLicLst(String licNo) {
		return dataAccessDAO.checkLicLst(licNo);
	}

	@Override
	@Transactional
	public PayLicBills loadBillByLicNo(Integer licWallId) {
		return commonDao.loadBillByLicNo(licWallId);
	}

	@Override
	public String printDocument(String repName, String parameter, String urlParam) {
		String url = "http://" + findSystemProperty(MyConstants.APP_NAME_NEW)
				+ "/reports/rwservlet?&userid=reports/reports@ORCL&destype=cache&desformat=pdf&report=d:\\archiving\\reports\\"
				+ repName + ".rdf&" + "&" + urlParam + "=" + parameter;

		return url;
	}

	@Override
	@Transactional
	public LicTrdMasterFile loadLicwnceByNumber(String licenceNumber) {
		return commonDao.loadlicenceByNumber(licenceNumber);
	}

	@Override
	@Transactional
	public List<User> getAllUsers() {
		return commonDao.getAllUsers();
	}

	@Override
	@Transactional
	public List<ReqFinesMaster> loadAllPenalities(boolean notification) {
		return commonDao.loadAllPenalities(notification);
	}

	@Override
	@Transactional
	public List<ReqFinesSetup> getCodesFines() {
		return commonDao.getCodesFines();
	}

	@Override
	@Transactional
	public int nationalIsFoundarcpeople(Long identifierNumber) {
		return commonDao.nationalIsFoundarcpeople(identifierNumber);
	}

	@Override
	@Transactional
	public ArcPeople loadnamebynationid(Long identifierNumber) {
		return commonDao.loadnamebynationid(identifierNumber);
	}

	@Override
	@Transactional
	public Integer billstatus(Integer newBill) {
		return dataAccessDAO.billstatus(newBill);
	}

	@Override
	@Transactional
	public Integer getCountBill(Integer newBill) {
		return commonDao.getCountBill(newBill);
	}

	@Override
	@Transactional
	public PayLicBills getBillbyBillNumber(Integer bill) {
		return commonDao.getBillbyBillNumber(bill);
	}

	@Override
	@Transactional
	public Integer getMaxBillNumber() {
		return commonDao.getMaxBillNumber();

	}

	@Override
	@Transactional
	public List<PayBillDetails> getBillDetail(Integer bill) {
		return commonDao.getBillDetail(bill);
	}

	@Override
	@Transactional
	public Integer replaceOldBillToNew(Integer Bill, int userId) {
		List<PayBillDetails> billDetail = getBillDetail(Bill);
		PayLicBills payBill = getBillbyBillNumber(Bill);
		Integer newBill = getMaxBillNumber() + 1;
		PayLicBills newPayBill = payBill;
		newPayBill.setBillNumber(newBill);
		newPayBill.setBillDate(new Date().toString());
		if (payBill.getLicenceNumber() != null && payBill.getBillNumber() != null) {
			payBill.setLicenceNumber(null);
			commonDao.update(payBill);
		}
		for (PayBillDetails billDet : billDetail) {
			if (billDet.getAmount() > 0) {
				billDet.setBillNumber(newBill);
				billDet.setCreatedIn(new Date());
				billDet.setCreatedBy(Utils.findCurrentUser().getUserId());
				billDet.setBillHYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
				billDet.setBillGYear(Calendar.getInstance().get(Calendar.YEAR));
				save(billDetail);
			}
		}
		return newBill;
	}

	@Override
	@Transactional
	public Integer getNumberLicencePenality(Integer fineCode, Integer fineNo) {
		return commonDao.getNumberLicencePenality(fineCode, fineNo);
	}

	@Override
	public List<LicTrdMasterFile> getTrdMasterFileListByPeriodType(Integer typeLicence, Integer period) {
		return dataAccessDAO.getTrdMasterFileListByPeriodType(typeLicence, period);
	}

	@Transactional
	public List<ArcPeopleModel> loadArcPeoples() {
		return commonDao.loadAllPeoples();
	}

	@Override
	@Transactional
	public List<ArcAttachmentClass> findAttachmentFilesByLicenceId(Integer licId) {
		LicTrdArchive licTrdArchive = (LicTrdArchive) findEntityById(LicTrdArchive.class, licId);
		if (licTrdArchive == null)
			return null;
		Integer arcRecordId = licTrdArchive.getArcId();
		return dataAccessDAO.findAttachmentFilesByArcId("" + arcRecordId);
	}

	@Override
	@Transactional
	public Integer getCountBillByFineNo(Integer fineNo) {
		return commonDao.getCountBillByFineNo(fineNo);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveLicencePenalty(ReqFinesMaster reqFinesMaster, List<ReqFinesDetails> reqFinesDetailsList,
			boolean withBill) {
		Integer fineNo = save(reqFinesMaster);
		reqFinesMaster.setfFineCase((long) 0);
		for (ReqFinesDetails reqFinesDetails : reqFinesDetailsList) {
			reqFinesDetails.setFineNo(fineNo);
			save(reqFinesDetails);
		}
		if (!withBill)
			return fineNo;
		reqFinesMaster.setReqFinesDetailsList(reqFinesDetailsList);
		double totalValue = reqFinesMaster.getTotalValue();

		PayLicBills newBill = new PayLicBills();
		newBill.setLicenceNumber(fineNo);
		String LicenceNo = reqFinesMaster.getfLicenceNo();
		if (LicenceNo != null)
			newBill.setLicenceNumber(fineNo);
		newBill.setBillOwnerName(reqFinesMaster.getfName());
		newBill.setLicenceType("F");
		newBill.setBillPayType("S");
		newBill.setPayAmount(totalValue);
		PayBillDetails det = new PayBillDetails();
		det.setAmount(totalValue);
		List<PayBillDetails> billDetailList = new ArrayList<>();
		billDetailList.add(det);
		return saveBill(newBill, billDetailList);
	}

	@Override
	@Transactional
	public List<ReqFinesMaster> findListFinesMasterByNationalId(String nationalId) {
		return commonDao.findListFinesMasterByNationalId(nationalId);
	}

	@Override
	@Transactional
	public List<BldLicHangover> loadAllRubbishCertificate() {
		return commonDao.loadAllRubbishCertificate();
	}

	@Override
	@Transactional
	public BldLicHangover loadRubbishCertificate(Integer rubbishCertificateId) {
		return (BldLicHangover) commonDao.findEntityById(BldLicHangover.class, rubbishCertificateId);
	}

	@Override
	@Transactional
	public void saveRubbishRemoveCertificate(BldLicHangover bldLicHangover, String title, PayLicBills newBill,
			List<PayBillDetails> billDetailList, List<AttachmentModel> attachList) {
		List<ArcAttach> attachs = Utils.SaveAttachementsToFtpServer(attachList);
		for (ArcAttach xx : attachs) {
			xx.setAttachOwner(Utils.findCurrentUser().getUserId());
		}
		List<Integer> attachIds = addAttachments(attachs);
		// int billNumber = commonDao.findMaxBillId() + 1;
		// String billNo = saveBill(newBill, billDetailList);
		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setApplicationType(MyConstants.NEW_BUILDING_TYPE);
		arcRecords.setRecTitle(title);
		arcRecords.setFilesNo(attachIds.size());
		int recordId = createNewArcRecord(arcRecords, false, null);
		saveAttachs(attachIds, recordId);
		int billNumber = commonDao.findMaxBillId() + 1;
		bldLicHangover.setLicHangoverBillNumber("" + billNumber);
		int licId = saveRubbishCertificateModel(bldLicHangover);
		saveBldLicPieces(licId, bldLicHangover.getPieces());
		saveBUildingMasterTbl(licId, recordId, LicenceTypeEnum.REMOVE_WASTE.getValue(),
				bldLicHangover.getLicHangoverAplOwner());
		newBill.setLicenceNumber(licId);
		saveBill(newBill, billDetailList);
	}

	private int saveRubbishCertificateModel(BldLicHangover bldLicHangover) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bldLicHangover.setLicHangoverBldLicDate(HijriCalendarUtil.findCurrentHijriDate());
		bldLicHangover.setLicHangoverGDate(new Date());
		bldLicHangover.setLicHangoverWroteBy(Utils.findCurrentUser().getUserId());
		bldLicHangover.setLicHangoverWroteIn(sdf.format(new Date()));
		return commonDao.saveRubbishCertificate(bldLicHangover);
	}

	@Override
	@Transactional
	public Integer getRecIdMasterTBLByLicenceId(Integer licId) {
		return commonDao.getRecIdMasterTBLByLicenceId(licId);
	}

	@Transactional
	public ArcPeopleModel loadArcPeopleModel(long natioId) {
		return commonDao.loadArcPeopleModel(natioId);
	}

	@Override
	@Transactional
	public long addArcPeople(ArcPeopleModel arcPeople) throws Exception {
		long nationalID = commonDao.saveObjectReturnLong(arcPeople);
		return nationalID;
	}

	@Override
	@Transactional
	public List<BldLicNewModel> loadLicNewList(long nationalId) {
		return dataAccessDAO.loadLicNewList(nationalId);
	}

	@Override
	@Transactional
	public WrkComment loadLinkedArcRecord(String wrkIncomeNo) {
		WrkComment comment = null;
		Integer oldIncomeNo = commonDao.loadOldIncomeNoFromLinking(wrkIncomeNo);
		if (oldIncomeNo != null)
			comment = commonDao.findWrkCommentByAppNo(oldIncomeNo.toString());
		return comment;
	}

	@Override
	@Transactional
	public List<HrsMasterFile> loadActiveEmpsHasSalary() {
		return commonDao.loadEmpsActive();
	}

	@Override
	@Transactional
	public void updateRubbishRemoveCertificate(BldLicHangover bldLicHangover, List<PayBillDetails> billDetailList,
			List<AttachmentModel> attachList) {
		List<ArcAttach> attachs = Utils.SaveAttachementsToFtpServer(attachList);
		for (ArcAttach xx : attachs) {
			xx.setAttachOwner(Utils.findCurrentUser().getUserId());
		}
		List<Integer> attachIds = addAttachments(attachs);
		Integer recordId = commonDao.getRecIdMasterTBLByLicenceId(bldLicHangover.getLicHangoverId());
		ArcRecords arcRecords = (ArcRecords) findEntityById(ArcRecords.class, recordId);
		arcRecords.setFilesNo(arcRecords.getFilesNo() + attachIds.size());
		commonDao.update(arcRecords);
		for (Integer id : attachIds) {
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(id);
			arcRecAttId.setRecordId(recordId);
			arcRecAtt.setId(arcRecAttId);
			saveObject(arcRecAtt);

		}
		int billNumber = commonDao.findMaxBillId() + 1;
		bldLicHangover.setLicHangoverBillNumber("" + billNumber);
		int licId = saveRubbishCertificateModel(bldLicHangover);
		saveBldLicPieces(licId, bldLicHangover.getPieces());
		// newBill.setLicenceNumber(licId);
		// saveBill(newBill, billDetailList);
	}

	@Override
	@Transactional
	public List<HrsGovJobSeries> getHrsJobs() {
		return commonDao.getHrsJobs();
	}

	@Override
	@Transactional
	public void calcSalary(int catid, int pmonth, int pyear) {
		dataAccessDAO.calcSalary(catid, pmonth, pyear);
		;
	}

	@Override
	@Transactional
	public List<HrsCompactFloors> getCatFloors() {
		List<HrsCompactCatFloor> catFloors = commonDao.getfCatFloors();
		List<HrsCompactFloors> hrsCompFloors = new ArrayList<HrsCompactFloors>();
		for (HrsCompactCatFloor hrsCompactCatFloor : catFloors) {
			Set<HrsFloors> floors = hrsCompactCatFloor.getFloors();

			for (HrsFloors hrsFloors : floors) {
				HrsCompactFloors floor = new HrsCompactFloors();
				floor.setCatFloorId(hrsCompactCatFloor.getId());
				floor.setFloorsid(hrsFloors.getId());
				floor.setFloorDescription(hrsFloors.getDescription());
				floor.setCatFloorDesc(hrsCompactCatFloor.getCatName());
				hrsCompFloors.add(floor);
			}

		}

		return hrsCompFloors;
	}

	@Override
	@Transactional
	public List<HrsFloors> getFloors() {
		return commonDao.getFloors();
	}

	@Override
	@Transactional
	public List<Sys037> loadAllJobCategories() {
		return commonDao.loadAllJobCategories();
	}

	@Override
	@Transactional
	public List<HrsCompactCatFloor> getAllCatFloors() {
		return commonDao.getAllCatFloors();
	}

	@Override
	@Transactional
	public List<HrsSalaryScale> loadAllJobRanks() {
		return dataAccessDAO.loadAllJobRanks();
	}

	@Override
	@Transactional
	public List<HrsGovJob4> loadJobsByCategory(Integer categoryId, Integer rank) {
		return commonDao.loadJobsByCategory(categoryId, rank);
	}

	@Override
	@Transactional
	public List<Sys112> loadAllDepartments() {
		return commonDao.loadAllDepartments();
	}

	@Override
	@Transactional
	public List<HrsJobCreation> loadAllJobs() {
		return commonDao.loadAllJobs();
	}

	@Override
	@Transactional
	public List<Sys038> loadAllJobStatus() {
		return commonDao.loadAllJobStatus();
	}

	public void addJobToHistory(HrsJobCreation job, HrsJobCreation oldJob, Integer actionNB) {
		HrsJobHistorical history = new HrsJobHistorical();
		Integer serial = 1;
		if (oldJob != null) {
			HrsJobHistorical lastHistory = commonDao.loadLastHistoricalJob(oldJob.getJobNumber(), oldJob.getJobCode());
			serial = lastHistory.getSerial() + 1;
			history.setRefSeq(lastHistory.getJobSequnce());
		}
		history.setSerial(serial);
		history.setRankCode(job.getRankCode());
		history.setJobNumber(job.getJobNumber());
		history.setJobYear(job.getJobYear());
		history.setJobCode(job.getJobCode());
		history.setCreatedBy(Utils.findCurrentUser().getUserId());
		history.setCreateDate(new Date());
		history.setJobStatus(job.getJobstatus());
		history.setExecuteNumber(job.getExcutionNumber());
		history.setJobAction(actionNB);
		commonDao.save(history);
	}

	@Override
	@Transactional
	public void saveJob(HrsJobCreation job) {
		commonDao.save(job);
		// addJobToHistory(job, null, 1);
	}

	@Override
	@Transactional
	public void updateJob(HrsJobCreation job, HrsJobCreation oldJob, Integer action) {
		commonDao.update(job);
		// addJobToHistory(job, oldJob, action);
	}

	@Override
	@Transactional
	public List<HrsEmpHistorical> calculatePrimes() {
		return dataAccessDAO.calculatePrimes();
	}

	@Override
	@Transactional
	public HrsYearsPrime findYearPrime(Integer year) {
		return commonDao.findYearPrime(year);
	}

	@Override
	@Transactional
	public void savePrimes(List<HrsEmpHistorical> primesList, Integer year) {
		commonDao.save(new HrsYearsPrime("" + year, new Date()));
		commonDao.disableLastFlags();
		for (HrsEmpHistorical prime : primesList) {
			prime.setFlag(1);
			prime.setCreateDate(new Date());
			prime.setCreatedBy(Utils.findCurrentUser().getUserId());
			prime.setJobYear("" + year);
			prime.getId().setStepId(commonDao.loadSerialEmpHistorical(prime.getId().getEmpno()));
			prime.setRecordType(107);
			prime.setOldMandateInner(prime.getOldMandateInner());
			prime.setOldMandateOuter(prime.getOldMandateOuter());
			commonDao.saveObject(prime);
		}
	}

	@Override
	@Transactional
	public List<HrsGovJobType> getAllHrsGovJobTypes() {
		return commonDao.getAllHrsGovJobTypes();
	}

	@Override
	public HrsJobHistorical loadEmployerLastJob(Integer empNo) {
		return commonDao.loadEmployerLastJob(empNo);
	}

	// @Override
	// @Transactional
	// public List<HrsMasterFile> loadEmpHasSalary() {
	// // TODO Auto-generated method stub
	// return commonDao.loadEmpsActive();
	// }
	@Override
	@Transactional

	public void savePerfermanceCaracter(HrsCompactEmpCaracter hrsCompactEmpCaracter) {
		save(hrsCompactEmpCaracter);

	}

	@Override
	public List<Sys012> loadMonthsHijri() {
		// TODO Auto-generated method stub
		return commonDao.loadMonthsHijri();
	}

	@Override
	@Transactional
	public void loadEmployerJobData(HrsCompactPerformance compactPerformance) {
		ArcUsers employer = loadUserById(compactPerformance.getEmpid());
		compactPerformance
				.setMgrName(((ArcUsers) findEntityById(ArcUsers.class, employer.getMgrId())).getEmployeeName());
		compactPerformance.setEmpName(employer.getEmployeeName());
		try {
			compactPerformance.setEmployer(getUserNameById(compactPerformance.getEmpid()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		HrsJobHistorical history = loadEmployerLastJob(employer.getEmployeeNumber());
		if (history != null) {
			compactPerformance.setJobNo(history.getJobNumber());
			compactPerformance.setDirectorid(employer.getMgrId());
			compactPerformance.setJobDescription(history.getGovJob4().getJobName());
		}
	}

	@Override
	@Transactional
	public HrsCompactPerformance getHrsCompactPerformByArcID(Integer archRecordId) {

		Integer DocId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		return (HrsCompactPerformance) commonDao.findEntityById(HrsCompactPerformance.class, DocId);
	}

	@Override
	@Transactional
	public HrsCompactRating getHrsCompactRatingById(Integer ratingId) {
		return (HrsCompactRating) commonDao.findEntityById(HrsCompactRating.class, ratingId);
	}

	@Override
	@Transactional
	public HrsGeneralAppreciation getHrsGeneralAppreciationByArcID(Integer archRecordId) {

		Integer DocId = commonDao.getDocIdHrsSignByRecId(archRecordId);

		return (HrsGeneralAppreciation) commonDao.findEntityById(HrsGeneralAppreciation.class, DocId);
	}

	@Override
	@Transactional
	public List<HrsCompactGoals> findHrsCompactPerformGoals(Integer performId) {
		return commonDao.findHrsCompactPerformGoals(performId);
	}

	@Override
	@Transactional
	public List<HrsCompactCatFloor> findHrsCompactPerformFloors(Integer performId) {
		return commonDao.findHrsCompactPerformFloors(performId);
	}

	@Override
	@Transactional
	public List<HrsCompactFloors> getHrsCompactFloors(Integer hrsPerfomId, Integer jobType) {
		return dataAccessDAO.getHrsCompactFloors(hrsPerfomId, jobType);
	}

	@Override
	@Transactional
	public HrsCompactPerformance getHrsCompactPerformByPerformanceID(Integer performanceId) {

		return (HrsCompactPerformance) commonDao.findEntityById(HrsCompactPerformance.class, performanceId);
	}

	@Override
	@Transactional
	public HrsGeneralAppreciation getHrsGeneralAppreciationByPerformanceID(Integer performanceId) {
		return (HrsGeneralAppreciation) commonDao.findHrsGeneralAppreciationByPerformId(performanceId);
	}

	@Override
	@Transactional
	public Employer loadEmployerByUser(Integer empNo) {
		ArcUsers arcUsers = commonDao.loadUserByEmpNO(empNo);
		Employer employer = new Employer();
		HrsEmpHistorical empl = commonDao.getHRsEmpHistoricalByEmpNo(empNo);
		List<Integer> category = new ArrayList<>();
		category.add(1);
		category.add(2);
		category.add(4);
		// category.add(9);
		if (arcUsers != null && empl != null)
			for (Integer x : category) {
				if (empl.getCATegoryId() == x) {
					employer.setName(arcUsers.getEmployeeName());
					employer.setDepartment(arcUsers.getUserDept().getDeptName());
					employer.setEmpNB(arcUsers.getEmployeeNumber());
					employer.setId(arcUsers.getUserId());
				} else {
					employer.setBasicSalary(empl.getBasicSalary());
					employer.setCategoryId(empl.getCATegoryId());
				}
			}
		;

		if (arcUsers != null && empl != null) {
			if (arcUsers.getEmployeeNumber() != null) {

				// employer.setJob(empl.getGovJob4().getJobName());
				employer.setSalaryStatus(empl.getFlag());
				if (empl != null) {
					if (empl.getGovJob4() != null)
						employer.setJob(empl.getGovJob4().getJobName());

					employer.setRank(empl.getRankNumber());
					employer.setBasicSalary(empl.getBasicSalary());
					employer.setJobNumber((empl.getJobNumber()));
					employer.setCategoryId(empl.getCATegoryId());
					employer.setTrans(empl.getTransferSalary());
					int orderId = commonDao.findMaxOrderIdRank();

					HrsSalaryScale hrsSalaryScale = commonDao.findHrsSalaryScaleById(HrsSalaryScale.class,
							new HrsSalaryScaleId(orderId, empl.getRankNumber()));
					employer.setRankName(hrsSalaryScale.getRankName());
					HrsMasterFile hrsMasterFile = (HrsMasterFile) commonDao.findEntityById(HrsMasterFile.class,
							arcUsers.getEmployeeNumber());
					employer.setBirthDateGerige(hrsMasterFile.getBirthDateGrig());
					employer.setFirstApplicationDate(hrsMasterFile.getFirstApplicationDate());
				}
			}
		}
		return employer;
	}

	@Override
	@Transactional
	public List<HrsCompactPerformance> loadNotEvaluatedPerformances() {
		return commonDao.loadNotEvaluatedPerformances();
	}

	@Override
	@Transactional
	public void acceptModel(HrsCompactPerformance compactPerformance, List<HrsCompactFloors> hrsCommpactFloors,
			List<HrsCompactGoals> lstGoals, int modelType, String wrkAppComment, Integer applicationPurpose,
			Integer recordId) {

		Integer performanceId = compactPerformance.getId();
		// Integer recordId =
		// commonDao.getRecordIdFromHrsSignByDocId(performanceId, modelType);
		WrkApplication app = getWrkApplicationRecordById(recordId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		application.setId(app.getId());
		application.getId().setStepId(application.getId().getStepId() + 1);
		Integer userTo = getNextScenarioUserId(modelType, app.getId().getApplicationId(), app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		application.setApplicationPurpose(applicationPurpose);
		createNewWrkApplication(application.getArcRecordId(), application, wrkAppComment, false, null);
		// TODO enable after adding scenario
		Integer acceptCount = commonDao.getHrsSignNextStep(recordId);
		boolean visible = (getDestinationModel(modelType, acceptCount).getSigned() == 1);
		saveHrsSigns(application.getArcRecordId(), performanceId, visible, null, Utils.findCurrentUser().getUserId(),
				modelType);
		if (visible) {
			if (modelType == MailTypeEnum.COMPACT_PERFORMANCE.getValue())
				compactPerformance.setStatus(3);
			else
				compactPerformance.setStatus(5);
			commonDao.update(compactPerformance);
			List<Integer> copySenders = Arrays.asList(compactPerformance.getEmpid(),
					compactPerformance.getDirectorid());
			redirectWrkAppCopies(application, copySenders, new ArrayList<Integer>());
		}
	}

	private void updatePerformanceAndAppreciation(HrsCompactPerformance compactPerformance, Double goalResult,
			Double floorResult) {
		// compactPerformance.setStatus(1);
		commonDao.update(compactPerformance);
		HrsGeneralAppreciation appreciation = new HrsGeneralAppreciation();
		appreciation.setHrsCompactPerformanceid(compactPerformance.getId());
		appreciation.setResultGoals(goalResult);
		appreciation.setResultFloors(floorResult);
		appreciation.setResult((goalResult + floorResult) / 2);
		appreciation.setHrsCompactRatingId(commonDao.getRatingByValue(appreciation.getResult().intValue()));
		if (goalResult != 0)
			commonDao.save(appreciation);
	}

	private double updateFloors(List<HrsCompactFloors> hrsCommpactFloors) {
		Integer sumEval = 0;
		Integer relativeImp = 0;
		Integer catFloordId = 0;
		for (HrsCompactFloors floor : hrsCommpactFloors) {
			if (floor.getEvaluation() != null)
				sumEval += floor.getEvaluation();
			if (catFloordId != floor.getCatFloorId()) {
				catFloordId = floor.getCatFloorId();
				relativeImp = floor.getRelativeImportance();
			}
			updateBaseFloors(floor.getHrsCompactBasefloorid(), relativeImp);
			commonDao.update(floor);
		}
		return sumEval / hrsCommpactFloors.size();
	}

	private double updateGoals(List<HrsCompactGoals> lstGoals) {
		Integer sumEval = 0;
		for (HrsCompactGoals goal : lstGoals) {
			if (goal.getEvaluation() != null)
				sumEval += goal.getEvaluation();
			commonDao.update(goal);
		}
		return sumEval / lstGoals.size();
	}

	private void updateBaseFloors(Integer floorID, Integer relativeImp) {
		HrsCompactBaseFloor baseFloor = (HrsCompactBaseFloor) findEntityById(HrsCompactBaseFloor.class, floorID);

		baseFloor.setRelativeImportance(relativeImp);
		commonDao.update(baseFloor);
	}

	@Override
	public List<HrsCompactEmpCaracter> getCompactEmpCaractersListByPerformId(Integer performId) {
		// TODO Auto-generated method stub
		return commonDao.findAllHrsCompactEmpCaracByPerformId(performId);
	}

	@Override
	@Transactional
	public List<HrsCompactBaseFloor> loadBaseFloors(Integer performanceId) {
		return commonDao.loadBaseFloors(performanceId);
	}

	@Override
	@Transactional

	public List<HrsSalary> loadListSalary(int catid, Integer pmonth, int pyear) {
		return commonDao.loadListSalary(catid, pmonth, pyear);
	}

	@Override
	@Transactional
	public List<ArcUsers> getemployersinusrs() {
		return commonDao.getemployersinusrs();
	}

	@Override
	@Transactional

	public List<HrsLoan> loadLoans(Integer empNB) {
		return commonDao.loadLoans(empNB);
	}

	@Override
	@Transactional
	public List<HrsMasterFile> getAllLoanEmployees() {
		return commonDao.getAllLoanEmployees();
	}

	@Override
	@Transactional
	public List<HrsLoanDetails> loadLoanDetails(Integer loanId) {
		return commonDao.loadLoanDetails(loanId);
	}

	// @Override
	@Transactional
	public void acceptHrsCompactPerformance(HrsCompactPerformance hrsCompactPerformance, Integer recordId,
			String userComment) {
		WrkApplication app = getWrkApplicationRecordById(recordId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		application.setId(app.getId());
		application.getId().setStepId(application.getId().getStepId() + 1);
		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(MailTypeEnum.COMPACT_PERFORMANCE.getValue(),
				app.getId().getApplicationId(), app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		createNewWrkApplication(recordId, application, null, false, null);
		boolean visible = (getDestinationModel(MailTypeEnum.COMPACT_PERFORMANCE.getValue(), acceptCount)
				.getSigned() == 1);
		saveHrsSigns(recordId, hrsCompactPerformance.getId(), visible, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.COMPACT_PERFORMANCE.getValue());

	}

	@Override
	@Transactional
	public void returnWrk(HrsCompactPerformance hrsCompactPerformance, Integer recordId,
			List<HrsCompactFloors> hrsCommpactFloors, List<HrsCompactGoals> lstGoals, String wrkAppComment,
			Integer applicationPurpose) {
		WrkApplication app = getWrkApplicationRecordById(recordId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		Double goalResult = updateGoals(lstGoals);
		Double floorResult = updateFloors(hrsCommpactFloors);
		updatePerformanceAndAppreciation(hrsCompactPerformance, goalResult, floorResult);
		application.setId(app.getId());
		application.getId().setStepId(application.getId().getStepId() + 1);
		application.setToUserId(application.getFromUserId());
		application.setUserComment(wrkAppComment);
		createNewWrkApplication(recordId, application, wrkAppComment, false, null);
	}

	@Override
	@Transactional
	public HrsScenarioDocument getNextUser(Integer arcRecordId, Integer modelTypeId) {
		Integer countSigned = getWrkCountSigned(arcRecordId);
		HrsScenarioDocument currentModel = getDestinationModel(modelTypeId, countSigned.intValue());
		return currentModel;
	}

	@Override
	@Transactional
	public void saveHrsCompactPerformance(HrsCompactPerformance hrsCompactPerformance,
			List<HrsCompactFloors> lstFloorModel, List<HrsCompactGoals> lstHrsCompactGoals, String title) {
		ArcRecords arcRecords = new ArcRecords();
		arcRecords.setApplicationType(MailTypeEnum.PERFORMANCE_EVALUATION.getValue());
		arcRecords.setRecTitle(title);
		int recordId = createNewArcRecord(arcRecords, false, hrsCompactPerformance.getEmpid());
		Double goalResult = updateGoals(lstHrsCompactGoals);
		Double floorResult = updateFloors(lstFloorModel);
		updatePerformanceAndAppreciation(hrsCompactPerformance, goalResult, floorResult);
		WrkApplication application = new WrkApplication();
		application.setToUserId(Integer.parseInt(findSystemProperty("HR_ID")));
		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);
		String userComment = title.concat(hrsCompactPerformance.getEmployer().getEmployeeName());
		;
		ArcUsers user = Utils.findCurrentUser();
		Integer applicationUserDeptJob = getUserDeptJob(user.getMgrId());
		createNewWrkApplication(recordId, application, userComment, false, applicationUserDeptJob);
		saveHrsSigns(recordId, hrsCompactPerformance.getId(), false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.PERFORMANCE_EVALUATION.getValue());
		hrsCompactPerformance.setStatus(4);
		commonDao.update(hrsCompactPerformance);

	}

	@Override
	@Transactional
	public void refuseCharterModel(HrsCompactPerformance compactPerformance, List<HrsCompactFloors> hrsCommpactFloors,
			List<HrsCompactGoals> lstGoals, int modelType, String wrkAppComment, Integer applicationPurpose,
			Integer recordId) {
		WrkApplication app = getWrkApplicationRecordById(recordId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		application.setId(app.getId());
		application.getId().setStepId(application.getId().getStepId() + 1);
		Integer userTo = application.getFromUserId();
		application.setToUserId(userTo);
		application.setApplicationPurpose(applicationPurpose);
		createNewWrkApplication(application.getArcRecordId(), application, wrkAppComment, false, null);

		if (modelType == MailTypeEnum.COMPACT_PERFORMANCE.getValue())
			compactPerformance.setStatus(4);
		else
			compactPerformance.setStatus(6);
		commonDao.update(compactPerformance);
		List<Integer> copySenders = Arrays.asList(compactPerformance.getEmpid(), compactPerformance.getDirectorid());
		redirectWrkAppCopies(application, copySenders, new ArrayList<Integer>());
	}

	@Override
	@Transactional
	public void refuseAppreciation(HrsGeneralAppreciation generalAppreciation, String wrkAppComment,
			Integer applicationPurpose, Integer recordId) {
		WrkApplication app = getWrkApplicationRecordById(recordId);
		HrsCompactPerformance compactPerformance = getHrsCompactPerformByPerformanceID(
				generalAppreciation.getHrsCompactPerformanceid());
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		application.setId(app.getId());
		application.getId().setStepId(application.getId().getStepId() + 1);
		Integer userTo = application.getFromUserId();
		application.setToUserId(userTo);
		application.setApplicationPurpose(applicationPurpose);
		createNewWrkApplication(application.getArcRecordId(), application, wrkAppComment, false, null);
		compactPerformance.setStatus(7);
		commonDao.update(compactPerformance);
		List<Integer> copySenders = Arrays.asList(userTo, compactPerformance.getDirectorid());
		redirectWrkAppCopies(application, copySenders, new ArrayList<Integer>());
	}

	// private void calcVacation(HrsVacationCalc calcVac , Integer days){
	//
	// if (calcVac.getOLDCOUNTER() <36 && calcVac.getOLDVACTIONSCORE() >
	// calcVac.getOLDCOUNTER())
	// {
	// if (days <=(36-calcVac.getOLDCOUNTER())){
	// calcVac.setOLDCOUNTER(calcVac.getOLDCOUNTER()+days);
	// calcVac.setOLDVACTIONSCORE(calcVac.getOLDVACTIONSCORE()-days);
	//
	// }
	// else{
	// calcVac.setOLDCOUNTER((36-calcVac.getOLDCOUNTER()));
	// calcVac.setOLDVACTIONSCORE(calcVac.getOLDVACTIONSCORE()-days);
	// calcVac.setTWOMONTHCOUNTER(calcVac.getTWOMONTHCOUNTER()+ (days
	// -(36-calcVac.getOLDCOUNTER())) );
	// calcVac.setSCORETWOMONTH(calcVac.getSCORETWOMONTH()-(days
	// -(36-calcVac.getOLDCOUNTER())));
	// }
	// }
	// else{
	//
	//
	// }
	//
	// }

	@SuppressWarnings("unchecked")
	@Transactional
	public List<HrsSalaryScale> loadAllSalayScales(Integer orderId) {
		List lst = commonDao.loadAllSalayScales(orderId);
		return lst;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<HrsSalaryScaleOrder> loadAllSalayScaleOrders() {
		List lst = commonDao.findAll(HrsSalaryScaleOrder.class);
		return lst;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<HrsSalaryScaleDgrs> loadAllSalayScaleDgrs(Integer orderId, Integer rankCode) {
		List lst = commonDao.loadAllSalayScaleDgrs(orderId, rankCode);
		return lst;

	}

	@Override
	@Transactional
	public void acceptModelAppreciation(HrsGeneralAppreciation generalAppreciation, String wrkAppComment,
			Integer applicationPurpose, Integer arcRecordId) {
		HrsCompactPerformance compactPerformance = getHrsCompactPerformByPerformanceID(
				generalAppreciation.getHrsCompactPerformanceid());
		WrkApplication app = getWrkApplicationRecordById(arcRecordId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(app.getId());
		application.setId(app.getId());
		application.getId().setStepId(application.getId().getStepId() + 1);
		Integer userTo = getNextScenarioUserId(MailTypeEnum.GENERAL_APPRECIATION.getValue(),
				app.getId().getApplicationId(), app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		application.setApplicationPurpose(applicationPurpose);
		createNewWrkApplication(application.getArcRecordId(), application, wrkAppComment, false, null);
		// TODO enable after adding scenario
		Integer acceptCount = commonDao.getHrsSignNextStep(arcRecordId);
		boolean visible = (getDestinationModel(MailTypeEnum.GENERAL_APPRECIATION.getValue(), acceptCount)
				.getSigned() == 1);
		saveHrsSigns(application.getArcRecordId(), generalAppreciation.getId(), visible, null,
				Utils.findCurrentUser().getUserId(), MailTypeEnum.GENERAL_APPRECIATION.getValue());

		compactPerformance.setStatus(6);
		commonDao.update(compactPerformance);

		List<Integer> copySenders = Arrays.asList(compactPerformance.getEmpid(), compactPerformance.getDirectorid());
		redirectWrkAppCopies(application, copySenders, new ArrayList<Integer>());

	}

	@Override
	@Transactional
	public List<HrsLoanType> loadAllLoanTypes() {
		return commonDao.loadAllLoanTypes();
	}

	@Override
	@Transactional
	public List<Sys012> loadAllMonths() {
		return commonDao.loadAllMonths();
	}

	@Override
	@Transactional
	public void saveLoan(HrsLoan loan) {
		int strt = loan.getLoanStrtMonth();
		Integer loanId = commonDao.save(loan);
	}

	@Override
	@Transactional
	public void updateLoanDetails(List<HrsLoanDetails> loanDetailsList) {
		for (HrsLoanDetails det : loanDetailsList)
			commonDao.update(det);
	}

	@Override
	@Transactional
	public List<HrsAppreciationScale> findAppreciationScalesByFloorId(Integer floorId) {
		// TODO Auto-generated method stub
		return commonDao.findAppreciationScalesByFloorId(floorId);
	}

	@Override
	@Transactional
	public List<ArcUsers> loadAllUsers() {
		return commonDao.findAllUsers();
	}

	@Override
	public List<SysTitle> loadAllTitles() {
		return commonDao.loadAllTitles();
	}

	@Override
	@Transactional
	public List<WrkSection> loadSectionsByDeptId(Integer deptId) {
		return commonDao.loadSectionsByDeptId(deptId);
	}

	@Override
	@Transactional
	public List<WrkJobs> loadJobsBySectionId(Integer wrkSectionId) {
		return commonDao.loadJobsBySectionId(wrkSectionId);
	}

	@Override
	@Transactional
	public List<FngTimeTable> loadTimeShift() {
		// TODO Auto-generated method stub
		return commonDao.loadTimeShift();
	}

	@Transactional
	public List<TstFinger> loadAllFingerByusersIdAndDate(String usersIdList, String DateStart, String DateFinish,
			Boolean allEmp, boolean higriMode) {
		return dataAccessDAO.getVacationFng(usersIdList, DateStart, DateFinish, allEmp, higriMode);
	}

	@Override
	@Transactional
	public List<TstFinger> loadAllDetailFingerByusersIdAndDate(String usersIdList, String DateStart, String DateFinish,
			boolean higriMode) {
		return dataAccessDAO.getFngDetail(usersIdList, DateStart, DateFinish, higriMode);
	}

	@Override
	@Transactional
	public ArcRecords getArcRecord(Integer arcRecordId) {
		// TODO Auto-generated method stub
		return commonDao.getArcRecord(arcRecordId);
	}

	@Override
	@Transactional
	public void saveNewUser(ArcUsers user) {
		user.setIsActive(1);
		user.setLoginName(user.getLoginName().toUpperCase());
		user.setPassword(commonDao.getHashedPassword(user.getLoginName(), user.getPassword()));
		Integer userId = commonDao.save(user);
		user.getWrkProfile().setUserId(userId);
		// user.getWrkProfileSign().setUserId(userId);
		commonDao.save(user.getWrkProfile());
		// commonDao.save(user.getWrkProfileSign());
	}

	@Override
	@Transactional
	public WrkProfile loadProfileUser(Integer userId) {
		return commonDao.loadProfileUser(userId);
	}

	@Override
	@Transactional
	public WrkProfileSign loadProfileSignUser(Integer userId) {
		return (WrkProfileSign) commonDao.findEntityById(WrkProfileSign.class, userId);
	}

	@Override
	@Transactional
	public void updateSignatures(WrkProfile wrkProfile, WrkProfileSign wrkProfileSign) {
		commonDao.update(wrkProfile);
		commonDao.update(wrkProfileSign);

	}

	@Override
	@Transactional
	public List<ArcPeople> loadAllArcPeoples() {
		return commonDao.loadAllArcPeoples();
	}

	@Override
	@Transactional
	public void saveOvertime(HrsEmpOvertime overtime) {
		overtime.setCreatedBy(Utils.findCurrentUser().getUserId());
		overtime.setCreateDate(new Date());
		commonDao.save(overtime);
	}

	@Override
	@Transactional
	public void updateOvertime(HrsEmpOvertime overtime) {
		overtime.setCreatedBy(Utils.findCurrentUser().getUserId());
		overtime.setCreateDate(new Date());
		commonDao.update(overtime);
	}

	@Override
	@Transactional
	public List<HrsEmpOvertime> retriveOverTime(Integer catid, Integer frommonth, Integer tomonth, Integer pyear) {
		// TODO Auto-generated method stub
		return commonDao.retriveOverTime(catid, frommonth, tomonth, pyear);
	}

	@Override
	@Transactional
	public List<ArcUsers> employeersTree() {
		return dataAccessDAO.employeersTree();
	}

	@Override
	@Transactional
	public List<HrsEmpOvertime> loadEmployerOvertimes(Integer empNB) {
		return commonDao.loadEmployerOvertimes(empNB);
	}

	@Override
	@Transactional
	public List<HrsEmpHistorical> loadPromotionJobs(Integer employerNB) {
		return commonDao.loadPromotionJobs(employerNB);
	}

	@Override
	@Transactional
	public List<EmpMoveType> loadMoveType() {
		return commonDao.loadEmpMoveType();
	}

	@Override
	@Transactional
	public List<InvestorType> loadAllInvestorType() {
		// TODO Auto-generated method stub
		return commonDao.loadInvestorType();
	}

	@Override
	@Transactional
	public List<InvestorIdentityType> loadAllInvestorIdentityType() {
		// TODO Auto-generated method stub
		return commonDao.loadInvestorIdentityType();
	}

	@Override
	@Transactional
	public List<InvestorStatus> loadAllInvestorStatus() {
		// TODO Auto-generated method stub
		return commonDao.loadInvestorStatus();
	}

	@Override
	@Transactional
	public List<BuildingType> loadAllBuildingType() {
		return commonDao.loadBuildingType();
	}

	@Override
	public List<ContractMainCategory> loadAllContractMainCategory() {
		return commonDao.loadContractMainCategory();
	}

	@Override
	public List<ContractSubcategory> loadAllContractSubcategory() {
		return commonDao.loadContractSubcategory();
	}

	@Override
	public List<ContractStatus> loadAllContractStatus() {
		return commonDao.loadContractStatus();
	}

	@Override
	@Transactional
	public HrsEmpHistorical loadJobCreationData(Integer jobId, Integer salaryBasic) {
		HrsJobCreation job = commonDao.loadSelecredJob(jobId, MyConstants.JOBEMPTY);
		int orderId = commonDao.loadMaxID();
		HrsSalaryScaleDgrs salary = commonDao.loadSalaryScale(job.getRankCode(), salaryBasic, orderId);
		HrsEmpHistorical empHist = new HrsEmpHistorical();
		empHist.setJobcode(job.getJobCode());
		empHist.setJobNumber(job.getJobNumber());
		empHist.setRankNumber(job.getRankCode());
		empHist.setClassNumber(salary.getClassCode());
		empHist.setBasicSalary(salary.getFirstSalary());
		empHist.setTransferSalary(salary.getTransferSalary());
		empHist.setMandateInner(salary.getMandatorIn());
		empHist.setMandateOuter(salary.getMandatorOut());
		return empHist;
	}

	@Override
	public void savePromotion(HrsEmpHistorical empHistorical, HrsEmpHistorical newJob) {
		// empHistorical.setJobcode(newJob.getJobcode());
		// empHistorical.setIncomNo(newJob.getIncomNo());
		// empHistorical.setIncomDate(Utils.convertDateToString(newJob.getIncomDateGrig()));
		// empHistorical.setIncomYear(newJob.getIncomYear());
		// empHistorical.setExecuteNo(newJob.getExecuteNo());
		// empHistorical.setExecuteDate(Utils.convertDateToString(newJob.getExecuteDateGrig()));
		// empHistorical.setEXeCuteSouRCe(newJob.getEXeCuteSouRCe());
		// empHistorical.setJobNumber(newJob.getJobNumber());
		// empHistorical.setRankNumber(newJob.getRankNumber());
		// empHistorical.setClassNumber(newJob.getClassNumber());
		// empHistorical.setBasicSalary(newJob.getBasicSalary());
		// empHistorical.setTransferSalary(newJob.getTransferSalary());
		// empHistorical.setMandateInner(newJob.getMandateInner());
		// empHistorical.setMandateOuter(newJob.getMandateOuter());
		newJob.setJobcode(newJob.getJobcode());
		newJob.setCATegoryId(empHistorical.getCATegoryId());
		newJob.setIncomNo(newJob.getIncomNo());
		// newJob.setIncomDate(Utils.convertDateToString(newJob.getIncomDateGrig()));
		newJob.setIncomDate(newJob.getIncomDate());
		newJob.setIncomYear(newJob.getIncomYear());
		newJob.setExecuteNo(newJob.getExecuteNo());
		// newJob.setExecuteDate(Utils.convertDateToString(newJob.getExecuteDateGrig()));
		newJob.setExecuteDate(newJob.getExecuteDate());
		newJob.setEXeCuteSouRCe(newJob.getEXeCuteSouRCe());
		newJob.setJobNumber(newJob.getJobNumber());
		newJob.setRankNumber(newJob.getRankNumber());
		newJob.setClassNumber(newJob.getClassNumber());
		newJob.setBasicSalary(newJob.getBasicSalary());
		newJob.setRecordType(newJob.getRecordType());
		newJob.setTransferSalary(newJob.getTransferSalary());
		newJob.setMandateInner(newJob.getMandateInner());
		newJob.setMandateOuter(newJob.getMandateOuter());
		empHistorical.setFlag(0);
		// updateObject(empHistorical);
		newJob.setOldJobCode(empHistorical.getJobcode());

		newJob.setFlag(1);
		// saveObject(newJob);
		// commonDao.update(empHistorical);
		// commonDao.save(newJob);
		HrsEmpHistoricalId empHistId = new HrsEmpHistoricalId();
		empHistId.setEmpno(empHistorical.getId().getEmpno());
		empHistId.setStepId(empHistorical.getId().getStepId() + 1);
		newJob.setId(empHistId);
		commonDao.addNewPromotion(empHistorical, newJob);

	}

	@Override
	@Transactional
	public int checkPeriodVacation(Integer empNB, Integer userid) {
		return commonDao.checkPeriodVacation(empNB, userid);

	}

	@Override
	@Transactional
	public List<FngTypeAbsence> loadAllTypeAbsence() {
		List typeAbsList = commonDao.findAll(FngTypeAbsence.class);
		return typeAbsList;
	}

	@Override
	@Transactional
	public List<FngStatusAbsence> loadAllStatusAbsence() {
		List statusAbsList = commonDao.findAll(FngStatusAbsence.class);
		return statusAbsList;
	}

	@Override
	@Transactional
	public List<FngEmpAbsent> loadAllFngEmpAbsent() {
		List fngEmpAbsentList = commonDao.findAll(FngEmpAbsent.class);
		return fngEmpAbsentList;
	}

	@Override
	@Transactional
	public void saveAbsence(FngEmpAbsent empAbsent) {
		commonDao.save(empAbsent);

	}

	@Override
	@Transactional
	public HrsEmpHistorical loadEmpHistorical(Integer employerNumber, String jobCode, Integer jobNumber,
			Integer rankNumber) {
		return commonDao.loadEmpHistorical(employerNumber, jobCode, jobNumber, rankNumber);
	}

	@Override
	@Transactional
	public HrsGovJob4 loadHrsGovJob(String jobCode) {
		return commonDao.loadHrsGovJob(jobCode);
	}

	@Override
	@Transactional
	public List<HrsEmpTerminate> loadAllEmpTerminates() {
		return commonDao.loadAllEmpTerminates();
	}

	@Override
	public List<Sys059> loadAllTerminateReasons() {
		return commonDao.loadAllTerminateReasons();
	}

	@Override
	public List<Sys018> loadAllPayStatus() {
		return commonDao.loadAllPayStatus();
	}

	@Override
	public List<Sys051> loadAllPayTypes() {
		return commonDao.loadAllPayTypes();
	}

	@Override
	@Transactional
	public void saveLeavingEmployer(HrsEmpTerminate empTerminate, HrsEmpHistorical empHistorical, HrsJobHistorical jobHistorical) {
		HrsEmpHistorical hrsNewEmpHistorical = new HrsEmpHistorical();
		HrsJobCreation creation = commonDao.loadJobCreation(empHistorical.getJobNumber(), empHistorical.getJobcode());
		creation.setJobstatus(1);
//		HrsJobHistorical hrsNewJobHistorical = new HrsJobHistorical();
		HrsEmpHistoricalId empHistoricalId = new HrsEmpHistoricalId();
		empTerminate.setCreatedBy(Utils.findCurrentUser().getUserId());
		empTerminate.setCreatedIn(new Date());
//		hrsNewEmpHistorical = empHistorical;
//		hrsNewJobHistorical = jobHistorical;
		empHistorical.setFlag(0);
		empHistoricalId.setEmpno(empHistorical.getId().getEmpno());
		empHistoricalId.setStepId((empHistorical.getId().getStepId()+1));
		hrsNewEmpHistorical.setId(empHistoricalId);
		
//		hrsNewEmpHistorical.setCATegoryId(empHistorical.getCATegoryId());
		hrsNewEmpHistorical.setIncomNo(empTerminate.getIncomeNumber());
		hrsNewEmpHistorical.setIncomDate(empTerminate.getIncomeDateHijri());
		hrsNewEmpHistorical.setExecuteNo(empTerminate.getExecutedNumber().toString());
		hrsNewEmpHistorical.setExecuteDate(empTerminate.getExecutedDate());
		hrsNewEmpHistorical.setRecordType(237);
		hrsNewEmpHistorical.setFlag(1);
		hrsNewEmpHistorical.setCATegoryId(empHistorical.getCATegoryId());
		hrsNewEmpHistorical.setRankNumber(empHistorical.getRankNumber());
		hrsNewEmpHistorical.setClassNumber(empHistorical.getClassNumber());
		hrsNewEmpHistorical.setBasicSalary(empHistorical.getBasicSalary());
		hrsNewEmpHistorical.setTransferSalary(empHistorical.getTransferSalary());
		hrsNewEmpHistorical.setMandateInner(empHistorical.getMandateInner());
		hrsNewEmpHistorical.setMandateOuter(empHistorical.getMandateOuter());
		hrsNewEmpHistorical.setJobNumber(empHistorical.getJobNumber());
		hrsNewEmpHistorical.setJobcode(empHistorical.getJobcode());
//		hrsNewEmpHistorical.setRankNumber(empHistorical.getRankNumber());
//		hrsNewEmpHistorical.setClassNumber(empHistorical.getClassNumber());
		jobHistorical.setEmployerNumber(null);
		jobHistorical.setSerial(jobHistorical.getSerial()+1);
		jobHistorical.setClassCode(null);
		jobHistorical.setIncomeNumber(empTerminate.getIncomeNumber());
		jobHistorical.setExecuteNumber(empTerminate.getExecutedNumber().toString());
		jobHistorical.setExecutedate(empTerminate.getExecutedDate());
		jobHistorical.setJobStatus(1);
		jobHistorical.setJobAction(9);
		
		commonDao.saveObject(empTerminate);
		commonDao.saveObject(hrsNewEmpHistorical);
		commonDao.saveObject(jobHistorical);

		commonDao.update(empHistorical);
		commonDao.update(creation);
	}

	@Override
	@Transactional
	public void exportSalaryFile(Integer categoryId, Integer year, Integer month, Integer type) {

		List<String> fileList = dataAccessDAO.getEmpSalariesFile(categoryId, month, year);
		String fileName = "xx.txt";
		if (type.equals(1))
			fileName = MyConstants.SALARFILE;
		else
			fileName = MyConstants.SALAROVERTIME;

		createFileInServer(fileList, fileName);
	}

	private void createFileInServer(List<String> fileList, final String fileName) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int lineNb = 0;
			for (String line : fileList) {
				lineNb++;
				if (lineNb == fileList.size())
					baos.write(line.getBytes());
				else
					baos.write((line + "\r\n").getBytes());
			}
			byte[] bytes = baos.toByteArray();
			InputStream in = new ByteArrayInputStream(bytes);
			FtpTransferFile.uploadSalariesFile(in, fileName);
		} catch (IOException e) {
			logger.error("createFileInServer" + e.getStackTrace());
		}
	}

	@Override
	public List<HrsUserAbsent> getAllAbsent(String lstUser, String startDate, String endDate) {
		return dataAccessDAO.getAllAbsent(lstUser, startDate, endDate);
	}

	@Override
	public void loadLetterReportParameters(Map<String, Object> parameters, String wrkId) {
		dataAccessDAO.loadLetterReportParameters(parameters, wrkId);
	}

	@Override
	@Transactional
	public User getNameFromUserById(Integer userId) {
		return commonDao.getNameFromUserById(userId);
	}

	@Override
	@Transactional
	public List<ArcRecords> loadRecordsInDepartment(Integer deptId, Integer mode) {
		return commonDao.loadRecordsInDepartment(deptId, mode);
	}

	@Override
	@Transactional
	public void saveArcRecordsList(List<DeptArcRecords> recordsList) {
		for (DeptArcRecords rec : recordsList) {
			commonDao.save(rec);
		}
	}

	@Override
	public List<UserMailObj> findEmployeeInboxNew(Integer start, Integer size, int vuser_id) {
		return dataAccessDAO.findEmployeeInbox(start, size, vuser_id);
	}

	@Override
	@Transactional
	public List<TradIssueType> loadTrdType() {
		// TODO Auto-generated method stub
		return commonDao.loadTrdType();
	}

	@Override
	@Transactional
	public List<BillIssueDetail> loadBillIssue() {
		return commonDao.loadBillIssue();
	}

	@Override
	@Transactional

	public List<PayBillDetails> calctradingMarket(Integer tradType, Integer activityType, Double advArea,
			Double addAdvArea, Double marketArea, Integer issueYears) {
		List<PayBillDetails> result = new ArrayList<>();
		final double PREVIEW_FEES = 20;
		double sum = 0.0;
		double advcost = 0.0;
		double rubish = 0.0;
		if (tradType == 0) {
			MsgEntry.addErrorMessage("Ø£Ø®ØªØ± Ù†ÙˆØ¹ Ø§Ù„Ø¥ØµØ¯Ø§Ø± ");
			return null;
		}
		if ((tradType == 1 || tradType == 2)
				&& (marketArea == 0 || advArea == 0 || issueYears == 0 || activityType == 0)) {
			MsgEntry.addErrorMessage("Ø£Ø¯Ø®Ù„ Ø¨ÙŠØ§Ù†Ø§Øª  Ø§Ù„Ù†Ø´Ø§Ø·  / Ù…Ø³Ø§Ø­Ø© Ø§Ù„Ù„ÙˆØ­Ø© / Ø§Ù„Ù…Ø­Ù„  / Ø³Ù†ÙˆØ§Øª Ø§Ù„Ø¥ØµØ¯Ø§Ø± ");
			return null;
		}

		if (tradType == 3) {
			result.add(new PayBillDetails(1422103, 1422103, 100.0, "Ø±Ø³ÙˆÙ… "));
			return result;
		}
		if (tradType == 4 || tradType == 5 || tradType == 6) {
			result.add(new PayBillDetails(1422103, 1422103, 20.0, "Ø±Ø³ÙˆÙ… "));
			result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "ÙƒØ´Ù�ÙŠØ©"));
			return result;
		}

		List<BillIssueCash> billCash = new ArrayList<>();

		billCash = commonDao.loadBillCash(MyConstants.issue_Category, activityType, advArea);
		sum = billCash.get(0).getCatrgory3() * marketArea;
		switch (MyConstants.issue_Category) {
		case 1:

			advcost = (advArea * 150) + (addAdvArea * 300);
			break;
		case 2:

			advcost = (advArea * 150) + (addAdvArea * 300);
			break;
		case 3:

			advcost = (advArea * 150) + (addAdvArea * 300);
			break;
		case 4:

			advcost = (advArea * 150) + (addAdvArea * 250);
			break;
		case 5:

			advcost = (advArea * 125) + (addAdvArea * 250);
			break;
		default:
			break;
		}

		List<BillIssueRubish> billCashRubish = new ArrayList<>();

		billCashRubish = commonDao.loadBillCashRubish(MyConstants.issue_Category, activityType);
		rubish = billCashRubish.get(0).getCatrgory3() * marketArea;

		result.add(new PayBillDetails(1422103, 1422103, roundNum(sum * issueYears), "Ø±Ø³ÙˆÙ… "));
		result.add(new PayBillDetails(11452512, 11452512, roundNum(advcost * issueYears), "Ù„ÙˆØ­Ø§Øª"));
		result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "ÙƒØ´Ù�ÙŠØ©"));
		// result.add(new PayBillDetails(1421908, 1421908, roundNum(rubish *
		// issueYears), "Ù†Ù�Ø§ÙŠØ§Øª "));

		return result;
	}

	public static double roundNum(double num) {
		BigDecimal bd = new BigDecimal(num).setScale(2, RoundingMode.HALF_EVEN);
		num = bd.doubleValue();
		// return Math.round((num * 100.0) / 100.0d);
		return num;
	}

	@Override
	@Transactional
	public List<PayBillDetails> calculatePetrolStationFees(Integer issueType, Double advArea, Double addAdvArea,
			Integer issueYears, Double area, boolean b) {
		double sum = 0.0;
		double advcost = 0.0;
		double rubish = 0.0;
		final double PREVIEW_FEES = 20;
		List<PayBillDetails> result = new ArrayList<>();

		if (issueType == 3) {
			result.add(new PayBillDetails(1422103, 1422103, 100.0, "Ø±Ø³ÙˆÙ…"));
			return result;
		}
		if (issueType == 0) {
			MsgEntry.addErrorMessage("Ø£Ø®ØªØ± Ù†ÙˆØ¹ Ø§Ù„Ø¥ØµØ¯Ø§Ø±   ");
			return null;
		}
		if ((issueType == 2) && (advArea == 0 || issueYears == 0 || area == 0)) {
			MsgEntry.addErrorMessage("Ø£Ø¯Ø®Ù„ Ù…Ø³Ø§Ø­Ø© Ø§Ù„Ù…Ø­Ø·Ø© - Ø³Ù†ÙˆØ§Øª Ø§Ù„Ø¥ØµØ¯Ø§Ø± - Ù…Ø³Ø§Ø­Ø© Ø§Ù„Ù„ÙˆØ­Ø©");
			return null;
		}
		List<BillIssueRubish> billCashRubish = new ArrayList<>();
		billCashRubish = commonDao.loadBillCashRubish(MyConstants.issue_Category, 12);

		switch (issueType) {
		case 1:
			List<BillIssueCash> billCashpetrol = new ArrayList<>();

			billCashpetrol = commonDao.loadBillCash(MyConstants.issue_Category, 12, advArea);
			sum = billCashpetrol.get(0).getCatrgory3();

			rubish = billCashRubish.get(0).getCatrgory3() * issueYears;

			result.add(new PayBillDetails(1422106, 1422106, sum, "Ø±Ø³ÙˆÙ…"));
			// result.add(new PayBillDetails(1422106, 1422106, rubish,
			// "Ù†Ù�Ø§ÙŠØ§Øª"));
			// result.add(new PayBillDetails(11452512, 11452512,
			// roundNum(advcost * issueYears), "Ù„ÙˆØ­Ø§Øª"));
			result.add(new PayBillDetails(1422109, 1422109, PREVIEW_FEES, "ÙƒØ´Ù�ÙŠØ©"));
			break;
		case 2:
			rubish = billCashRubish.get(0).getCatrgory3() * issueYears;
			// result.add(new PayBillDetails(1422106, 1422106, rubish,
			// "Ù†Ù�Ø§ÙŠØ§Øª"));

			result = calctradingMarket(issueType, 1, advArea, addAdvArea, area, issueYears);
			break;
		case 3:
			result.add(new PayBillDetails(1422103, 1422103, 100.0, "Ø±Ø³ÙˆÙ…"));
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	@Transactional
	public Double rubishValue(Integer activityType) {
		Double rubish = 0.0;
		List<BillIssueRubish> billCashRubish = new ArrayList<>();

		billCashRubish = commonDao.loadBillCashRubish(MyConstants.issue_Category, activityType);

		// rubish = billCashRubish.get(0).getCatrgory3() * marketArea;
		rubish = billCashRubish.get(0).getCatrgory3();

		return rubish;

	}

	@Override
	@Transactional
	public List<BillIssueDig> loadStreetDig() {
		return commonDao.loadStreetDig();
	}

	@Override
	@Transactional
	public List<BillIssueDigDetail> getTypeDigByCat(Integer streetGeneral) {
		return commonDao.getTypeDigByCat(streetGeneral);
	}

	@Override
	public List<RecDepts> getAllRecDepts() {
		// TODO Auto-generated method stub
		return commonDao.getAllRecDepts();
	}

	@Override
	@Transactional
	public List<PayBillDetails> calculateStreetDig(Double digline, Integer daysNumber, Integer streetType,
			Integer extendDays, Boolean isExtend) {
		List<BillIssueDigCash> billCashDig = new ArrayList<>();
		Double sum = 0.0;
		billCashDig = commonDao.loadBillCashStreetDig(MyConstants.issue_Category, streetType);
		if (isExtend == false) {
			sum = billCashDig.get(0).getCategory3() * digline * daysNumber;
		} else {
			sum = billCashDig.get(0).getCategory3() * extendDays * digline * daysNumber;
		}
		List<PayBillDetails> result = new ArrayList<>();

		result.add(new PayBillDetails(1422106, 1422106, sum, " Ø±Ø³ÙˆÙ… Ø­Ù�Ø± Ø§Ù„Ø´ÙˆØ§Ø±Ø¹"));
		;
		return result;
	}

	@Override
	public DashbordModel loadDasbordParams(Integer userId) {
		DashbordModel dashbordModel = null;
		try {
			dashbordModel = dataAccessDAO.loadDasbordParams(userId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return dashbordModel;
	}

	@Transactional
	public Map<Integer, User> loadUsers() {
		Map<Integer, User> usersMap = null;
		List<User> resultList = commonDao.getAllUsers();
		if ((resultList != null) && (!resultList.isEmpty())) {
			usersMap = new HashMap<>();
			for (User user : resultList)
				usersMap.put(user.getUserId(), user);
		}
		return usersMap;
	}

	@Override
	@Transactional
	public Map<Integer, MasterFile> loadMasterFiles() {
		Map<Integer, MasterFile> employersMap = null;
		List<MasterFile> resultList = commonDao.getAllMasterFiles();
		if ((resultList != null) && (!resultList.isEmpty())) {
			employersMap = new HashMap<>();
			for (MasterFile emp : resultList)
				employersMap.put(emp.getEmployeNumber(), emp);
		}
		return employersMap;
	}

	@Override
	public List<ArcRecords> loadAllFromDepartmentRecords(Integer deptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TstFinger getFingerByUserId(TstFingerId userId) {
		return commonDao.getFingerDigByUserId(userId);
	}

	@Override
	@Transactional
	public void loadNormalVacationNew(int empNB, String firstApplicationDate, String gregBirthDate, Integer userid,
			int currentDebit, HrEmployeeVacation employeeVacation) {
		// HrsSumVacation hrsSumVacation = commonDao.listsumbyEmp(empNB);
		// /* Ø±ØµÙŠØ¯ Ø§Ù„Ø§Ø¬Ø§Ø²Ø§Øª Ù…Ù†Ø° Ø¨Ø¯Ø¡ Ø§Ù„ØªØ¹ÙŠÙ† */
		// employeeVacation
		// .setTotalVacationPeriod(getTotalVacationPeriod(empNB,
		// firstApplicationDate));
		employeeVacation.setTotalVacationPeriod(getTotalVacationSold((empNB)));
		// employeeVacation.setTotalVacationPeriod(hrsSumVacation.getVACOLDYEAR37());
		/* Ø§Ø¬Ù…Ø§Ù„Ù‰ Ù…Ø§ØªÙ…ØªØ¹ Ø¨Ù‡ Ø®Ù„Ø§Ù„ Ø§Ù„Ø¹Ø§Ù… */ employeeVacation
				.setUsedNormalYearlyPeriod(commonDao.getUsedNormalYearlyPeriod(empNB));
		/* Ø¬Ù…Ù„Ù‡ Ø§Ù„Ø§Ø¬Ø§Ø²Øª Ø§Ù„ØªØ§Ø±ÙŠØ®ÙŠÙ‡ */employeeVacation.setUsedTotalVacationPeriod(
				getUsedTotalVacationPeriod(empNB)/* Ø§Ø¬Ø§Ø²Ø§Øª Ù„ØªØ§Ø±ÙŠØ® Ø§Ù„ÙŠÙˆÙ… */);
		/* Ø¬Ù…Ù„Ù‡ Ø§Ù„Ø±ØµÙŠØ¯ Ø§Ù„Ù…ØªØ§Ø­ */employeeVacation
				.setRemainTotalVacationPeriod((int) employeeVacation.getTotalVacationPeriod() - (employeeVacation
						.getUsedNormalYearlyPeriod()/*
													 * + hrsSumVacation.
													 * getVACSUMYEARSOLD()
													 */));
		// /*Ø§Ù„Ø±ØµÙŠØ¯ Ø§Ù„Ø­Ø§Ù„Ù‰ Ù„Ù„Ø³Ù†Ù‡ */ currentDebit
		// int x = commonDao.loadVacationSumLastYear(empNB);

		// employeeVacation.setVacationPeriodThisYear(getVacationPeriodThisYear());
		// + employeeVacation.getVacationPeriodThisYear()));
		int maxYearlyPeriod = 144;
		// getMaxYearlyNormalYearlyPeriod(firstApplicationDate, gregBirthDate);
		/* Ø§Ù„Ø±ØµÙŠØ¯ Ø§Ù„Ù…Ø³Ù…ÙˆØ­ Ø¨Ù‡ Ø§Ù‚Ù„ Ù…Ù† 120 */  
		employeeVacation.setRemainNormalYearlyPeriod(getRemainNormalYearlyPeriod(getMaxYearlyNormalYearlyPeriod(firstApplicationDate, gregBirthDate),
						employeeVacation.getUsedNormalYearlyPeriod(), employeeVacation.getRemainTotalVacationPeriod()));
		// employeeVacation
		employeeVacation.setVacationType(MyConstants.NORMAL_VACATION);
		// .setRemainNormalYearlyPeriod(getRemainNormalYearlyPeriod(employeeVacation.getVacationPeriodThisYear(),
		// employeeVacation.getUsedNormalYearlyPeriod(),
		// employeeVacation.getRemainTotalVacationPeriod()));
		HrEmployeeVacation lastNormalVacation = commonDao.loadLastNormalVacation(empNB);
		if (lastNormalVacation == null)
			lastNormalVacation = new HrEmployeeVacation();
		employeeVacation.setLastVacationDate(lastNormalVacation.getHigriVacationStart());
		employeeVacation.setLastVacationPeriod(lastNormalVacation.getVacationPeriod());
		employeeVacation.setLastVacationFrom(lastNormalVacation.getHDatevacationFrom());
		employeeVacation.setLastVacationTo(lastNormalVacation.getHDateVacationTo());
		employeeVacation.setLastVacationStart(lastNormalVacation.getHigriVacationStart());
		employeeVacation.setLastVacationEnd(lastNormalVacation.getHigriVacationEnd());
		employeeVacation.setPeriodLessFive(checkPeriodVacation(empNB, userid));
		// employeeVacation.setVacationCalc((HrsVacationCalc)
		// commonDao.findEntityById(HrsVacationCalc.class, empNB));
	}

	public HrsSumVacation listsumbyEmp(int empno) {
		return commonDao.listsumbyEmp(empno);
	}

	@Override
	public DepartmentArcRecords getAttachNBByRecId(Integer recId) {
		return commonDao.getAttachNBByRecId(recId);
	}

	@Override
	public String findReciverNameById(Integer deptId) {
		return commonDao.findReciverNameById(deptId);
	}

	@Override
	public List<VacationModel> getUsersVacations(String lstUser, String startDate, Boolean allEmp) {
		return dataAccessDAO.getUsersVacations(lstUser, startDate, allEmp);
	}

	@Override
	@Transactional
	public List<DeptArcRecords> loadDeptRecords(Integer currDeptId, Integer status) {
		return dataAccessDAO.loadDeptRecords(currDeptId, status);
	}

	@Override
	public void saveArcRecordsList(List<ArcRecords> recordsList, Integer deptId) {

	}

	@Override
	@Transactional
	public User getUserById(Integer userId) {
		return (User) commonDao.findEntityById(User.class, userId);
	}

	@Override
	@Transactional
	public User getUserByEmpNO(int employerNumber) {
		return commonDao.getUserByEmpNO(employerNumber);
	}

	private Integer getVacNormalPurpId(Integer stepID) {
		switch (stepID) {
		case 2:
			return MyConstants.VAC_PURPS_MGR_TYPE;
		case 3:
			return MyConstants.VAC_PURPS_VERIF_TYPE;
		case 4:
			return MyConstants.VAC_PURPS_ISDAR_TYPE;
		case 5:
			return MyConstants.VAC_PURPS_ISDAR_TYPE;
		default:
			return MyConstants.VAC_PURPS_MGR_TYPE;
		}
	}

	@Override
	@Transactional
	public List<InvNewspaper> loadNewspapers() {
		return commonDao.loadAllNewspapers();
	}

	@Override
	@Transactional
	public List<Investor> loadAllInvestors() {
		return commonDao.loadAllInvestors();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserRequest> loadUserRequests(Integer status, Integer userId, boolean isTecSupportRole) {
		List<UserRequest> userRequests = commonDao.loadUserRequests(status, userId, isTecSupportRole);
		return userRequests;
	}

	@Transactional
	public Map<Integer, User> loadReqsStatus() {
		Map<Integer, User> usersMap = null;
		List<User> resultList = commonDao.getAllUsers();
		if ((resultList != null) && (!resultList.isEmpty())) {
			usersMap = new HashMap<>();
			for (User user : resultList)
				usersMap.put(user.getUserId(), user);
		}
		return usersMap;
	}

	@Override
	public Map<Integer, RequestStatus> getUserReqStatus() {
		List<RequestStatus> userReqsStatus = commonDao.getUserReqStatus();
		Map<Integer, RequestStatus> reqStatMap = new HashMap<>();
		for (RequestStatus requestStatus : userReqsStatus) {
			reqStatMap.put(requestStatus.getId(), requestStatus);
		}
		return reqStatMap;
	}

	@Override
	@Transactional
	public List<RealEstate> loadAllRealEstates() {
		return commonDao.loadAllRealEstates();
	}

	@Override
	@Transactional
	public List<Contract> loadAllContracts() {
		return commonDao.loadAllContracts();
	}

	@Override
	@Transactional
	public List<Announcement> findAllAnnouncements() {
		List announcements = commonDao.findAll(Announcement.class);
		return announcements;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnoucementDetails> loadAnnoucementDetails(Integer AnnoucementId) {
		List announcements = commonDao.loadAnnoucementDetails(AnnoucementId);
		return announcements;
	}

	@Override
	@Transactional
	public List<SiteType> loadAllSiteTypes() {
		return commonDao.loadAllSiteTypes();
	}

	@Override
	@Transactional
	public List<ContractType> loadAllContractTypes() {
		return commonDao.loadAllContractTypes();
	}

	@Override
	@Transactional
	public List<Tender> loadTendersByAnnouncementDetailsId(Integer annDetailsId) {
		return commonDao.loadTendersByAnnouncementDetailsId(annDetailsId);
	}

	@Override
	@Transactional
	public List<HrsMasterFile> getAllEmployeesActive() {
		// TODO Auto-generated method stub
		return commonDao.getAllEmployeesActive();
	}

	@Override
	@Transactional
	public List<TechnicalUsers> loadAllTechnicalUsers() {
		return commonDao.loadAllTechnicalUsers();
	}

	@Override
	@Transactional
	public List<Clause> loadAllClauses() {
		return commonDao.loadAllClauses();
	}

	@Override
	@Transactional
	public void saveContract(Contract contract, List<String> selectedClausesList) {
		Integer contId = commonDao.save(contract);
		AnnoucementDetails annDetails = (AnnoucementDetails) commonDao.findEntityById(AnnoucementDetails.class,
				contract.getTender().getAnnouncementDetailsId());
		annDetails.setStatus(1);
		commonDao.update(annDetails);
		// if(selectedClausesList != null){
		// for(String cls : selectedClausesList){
		// ContractClause conCla = new ContractClause();
		// conCla.setContractId(contId);
		// conCla.setClauseId(Integer.parseInt(cls));
		// commonDao.save(conCla);
		// }
		// }
	}

	@Override
	@Transactional
	public WrkComment findWrkCommentByCopyArcId(Integer copyArcId) {
		ArcRecordsLink link = commonDao.getArcLinkByRecordId(copyArcId);
		if (link == null)
			return null;
		Integer parentArcId = link.getArcRrecordChildId();
		Integer wrkAppId = commonDao.getWrkApplicationRecordById(parentArcId).getId().getApplicationId();
		WrkComment comm = commonDao.getWrkCommentByAppId(wrkAppId);
		return comm;
	}

	@Override
	@Transactional
	public FngTimeTable loadTimeShiftByUserId(Integer userId) {
		return commonDao.loadTimeShiftByUserId(userId);
	}

	@Override
	@Transactional
	public String getAmountInLetters(String amount) {
		return commonDao.getAmountInLetters(amount);
	}

	// @Override
	// @Transactional(propagation = Propagation.REQUIRED)
	// public boolean saveAnnouncement(Announcement announcement) {
	// try {
	// commonDao.deleteAnncementNews(announcement.getId());
	// save(announcement);
	// return true;
	// } catch (Exception e) {
	// logger.error("saveAnnouncement :" + e.getMessage());
	// return false;
	// }
	// }

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveAnnouncement(Announcement announcement) {
		Integer annId = save(announcement);
		for (AnnoucementDetails det : announcement.getAnnDetailsList()) {
			det.setAnnoucementId(annId);
			save(det);
		}
		for (AnncementNews news : announcement.getAnncementNews()) {
			news.setAnnncementId(annId);
			save(news);
		}
	}

	@Transactional
	public Tender loadSelectedTender(Integer realEstateId, Integer investorId) {
		return commonDao.loadSelectedTender(realEstateId, investorId);
	}

	@Override
	@Transactional
	public List<Investor> loadInvestorsByRealEstateId(Integer realEstateId) {
		return commonDao.loadInvestorsByRealEstateId(realEstateId);
	}

	@Override
	@Transactional
	public List<AnnoucementDetails> loadAnnouncementDetailsByStatus(Integer[] status) {
		return commonDao.loadAnnouncementDetailsByStatus(status);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<Integer, AbsentModel> getHrsUserAbsents(Integer currentYear) {
		Map<Integer, AbsentModel> userAbsents = new HashMap<Integer, AbsentModel>();
		try {
			List<AbsentModel> absents = commonDao.getHrsUserAbsents(currentYear);
			for (AbsentModel absentModel : absents) {

				userAbsents.put(absentModel.getEmpno(), absentModel);
			}
		} catch (Exception e) {
			logger.error("getHrsUserAbsents :" + e.getMessage());
		}
		return userAbsents;
	}

	@Override
	@Transactional
	public List<Car> loadAllCars() {
		return commonDao.loadAllCars();
	}

	@Override
	@Transactional
	public List<CarBrand> loadAllCarBrands() {
		return commonDao.loadAllCarBrands();
	}

	@Override
	@Transactional
	public List<CarModel> loadCarModelByBrandId(Integer carBrand) {
		return commonDao.loadCarModelByBrandId(carBrand);
	}

	@Override
	@Transactional
	public List<VehicleType> loadAllVehicleTypes() {
		return commonDao.loadAllVehicleTypes();
	}

	@Override
	@Transactional
	public List<FuelType> loadAllFuelTypes() {
		return commonDao.loadAllFuelTypes();
	}

	@Override
	@Transactional
	public List<FuelTransaction> loadAllFuelTransactions() {
		return commonDao.loadAllFuelTransactions();
	}

	public void correctUserFngs(Integer userId, String startDate, String endDate) {
		dataAccessDAO.correctUserFngs(userId, startDate, endDate);
	}

	@Override
	@Transactional
	public List<UserCars> loadUserCarsByUserId(Integer employerId) {
		return commonDao.loadUserCarsByUserId(employerId);
	}

	@Override
	@Transactional
	public List<UserCars> loadAllAllocatedUserCars() {
		return commonDao.loadAllAllocatedUserCars();
	}

	@Override
	@Transactional
	public List<Car> loadNotAllocatedCarsByModelId(Integer carModelId) {
		return commonDao.loadNotAllocatedCarsByModelId(carModelId);
	}

	@Override
	@Transactional
	public List<FuelTransaction> loadFuelTransactions(Date startDate, Date endDate) {
		return commonDao.loadFuelTransactions(startDate, endDate);
	}

	@Override
	@Transactional
	public UserCars findCarByNumDoor(Integer numDoor) {
		return commonDao.findCarByNumDoor(numDoor);
	}

	@Override
	@Transactional
	public FuelTransaction findLastFuelTransaction(Integer userCarId) {
		return commonDao.findLastFuelTransaction(userCarId);
	}

	@Override
	@Transactional
	public List<ContractDirect> loadAllContractDirects(Integer contractTypeId) {
		return commonDao.loadAllContractDirects(contractTypeId);
	}

	@Override
	@Transactional
	public void saveContractDirect(ContractDirect contractDirect) {
		commonDao.save(contractDirect);
	}

	@Override
	@Transactional
	public List<FuelSupply> loadAllFuelSupplies() {
		return commonDao.loadAllFuelSupplies();
	}

	@Override
	@Transactional
	public FuelSupply loadLastFuelSupply(Integer fuelTypeId) {
		return commonDao.loadLastFuelSupply(fuelTypeId);
	}

	@Override
	@Transactional
	public List<IntroContract> loadAllIntroContracts() {
		return commonDao.loadAllIntroContracts();
	}

	@Override
	@Transactional
	public void selectTender(List<Tender> tendersList) {
		for (Tender tender : tendersList) {
			if (tender.isChecked())
				tender.setStatus(1);
			else
				tender.setStatus(0);
			commonDao.update(tender);
		}
		// AnnoucementDetails annDetails = (AnnoucementDetails)
		// commonDao.findEntityById(AnnoucementDetails.class,
		// tendersList.get(0).getAnnouncementDetailsId());
		// annDetails.setStatus(1);
		// commonDao.update(annDetails);
	}

	@Override
	@Transactional
	public List<AnnoucementDetails> loadAnnouncementDetailsHavingTenders(Integer[] status) {
		return commonDao.loadAnnouncementDetailsHavingTenders(status);
	}

	@Override
	@Transactional
	public Tender loadAssignedTenderByAnnouncementDetailsId(Integer annoucementDetailsId) {
		return commonDao.loadAssignedTenderByAnnouncementDetailsId(annoucementDetailsId);
	}

	@Override
	@Transactional
	public List<ContractCancelReason> loadAllContractCancelReasons() {
		return commonDao.loadAllContractCancelReasons();
	}

	@Override
	@Transactional
	public List<ArcPeople> loadArcPeopleFields() {
		return commonDao.loadArcPeopleFields();
	}

	@Override
	@Transactional
	public List<NotifFinesMaster> loadAllNotifPenalities(Integer status) {
		return commonDao.loadAllNotifPenalities(status);
	}

	public void sendListRecordTo(List<UserMailObj> checkedMailsList, Integer userid) {
		for (UserMailObj record : checkedMailsList) {
			archiveRecord(record, userid);
		}
	}

	/*
	 * @Override
	 * 
	 * @Transactional public void saveContractBills(PayLicBills payLicBill,
	 * ContractBills contractBills, Integer billNumber) {
	 * payLicBill.setBillDate(HijriCalendarUtil.findCurrentHijriDate()); if
	 * (billNumber == 0) { billNumber = commonDao.findMaxBillId() + 1; }
	 * payLicBill.setBillNumber(billNumber); contractBills.setBillId(billNumber);
	 * save(payLicBill); save(contractBills); savePayBillDetails(billNumber,
	 * payLicBill.getPayBillDetails()); }
	 */

	@Override
	@Transactional
	public List<PayLicBills> loadContractBills(Integer contractId, String contractType) {
		return commonDao.loadContractBills(contractId, contractType);
	}

	@Override
	@Transactional
	public List<ContractDirectType> loadAllContractDirectTypes() {
		return commonDao.loadAllContractDirectTypes();
	}

	@Override
	@Transactional
	public void updateBillContract(PayLicBills bill) {
		commonDao.update(bill);

	}

	@Override
	@Transactional
	public List<LicTrdMasterFile> loadLicences() {
		return commonDao.loadLicences();
	}

	@Override
	@Transactional
	public List<ReqFinesSetup> loadFineSetupBySection(Integer sectionId) {
		return commonDao.loadFineSetupBySection(sectionId);
	}

	@Override
	@Transactional
	public void acceptPenalityAndBill(ReqFinesMaster reqFinesMaster) {
		reqFinesMaster.setType(0);
		reqFinesMaster.setfFineCase((long) 1);
		commonDao.update(reqFinesMaster);
		List<ReqFinesDetails> reqFinesDetailsList = reqFinesMaster.getReqFinesDetailsList();
		for (ReqFinesDetails reqFinesDetails : reqFinesDetailsList) {
			commonDao.update(reqFinesDetails);
		}
		double totalValue = reqFinesMaster.getTotalValue();
		PayLicBills newBill = new PayLicBills();
		newBill.setLicenceNumber(reqFinesMaster.getFineNo());
		String LicenceNo = reqFinesMaster.getfLicenceNo();
		if (LicenceNo != null)
			newBill.setLicenceNumber(reqFinesMaster.getFineNo());
		newBill.setBillOwnerName(reqFinesMaster.getfName());
		newBill.setLicenceType("F");
		newBill.setBillPayType("S");
		newBill.setPayAmount(totalValue);
		PayBillDetails det = new PayBillDetails();
		det.setAmount(totalValue);
		List<PayBillDetails> billDetailList = new ArrayList<>();
		billDetailList.add(det);
		saveBill(newBill, billDetailList);
	}

	@Override
	@Transactional
	public void updateNotifFineMaster(NotifFinesMaster notifFinesMaster) {
		commonDao.update(notifFinesMaster);
		for (NotifFinesDetails det : notifFinesMaster.getNotifFinesDetailsList()) {
			commonDao.update(det);
		}

	}

	@Override
	@Transactional
	public Map<Object, Number> loadArcRecordsNBForCurrentYear(Integer userId, Integer deptId) {
		return dataAccessDAO.loadArcRecordsNBForCurrentYear(userId, deptId);
	}

	@Override
	@Transactional
	public void saveEmplyerVacStock(VacCompensatoryDays vacCompensatoryDays,
			CompensatoryVacStock compensatoryVacStock) {
		save(vacCompensatoryDays);
		updateObject(compensatoryVacStock);

	}

	@Override
	@Transactional
	public void saveEmplyerVacStock(VacCompensatoryDays vacCompensatoryDays,
			List<CompensatoryVacStock> compensatoryVacStock) {
		for (CompensatoryVacStock cvs : compensatoryVacStock) {
			updateObject(cvs);
		}
		save(vacCompensatoryDays);
	}

	@Override
	@Transactional
	public List<CompensatoryVacStock> getAllUsersCompVacStock() {
		return commonDao.getAllUsersCompVacStock();
	}

	@Override
	@Transactional
	public List<HrsVacSold> getAllHrsVacSold() {
		return commonDao.getAllHrsVacSold();
	}

	@Override
	public WhsWarehouses loadStoreIdById(Integer storeId) {
		return (WhsWarehouses) commonDao.findEntityById(WhsWarehouses.class, storeId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean addUserRequest(UserRequest reuest, RequestStep reqSteps) {
		// return commonDao.addUserRequest(reuest, reqSteps);
		// List<RequestStep> steps=new ArrayList<RequestStep>();
		// steps.add(reqSteps);
		// reuest.setRequestSteps( steps);
		Integer requestId = (Integer) save(reuest);
		reqSteps.setRequestId(requestId);
		commonDao.saveObject(reqSteps);
		return true;
	}

	@Override
	@Transactional
	public int getTotalVacationSold(int empNB) {
		Integer totalVacation = 0;
		int currYear = Integer.parseInt(utilities.HijriCalendarUtil.findCurrentYear());
	//	totalVacation = commonDao.getNewVacPeriod(currYear);
		totalVacation = commonDao.getInitUserVacSold(currYear, empNB);
	//	totalVacation = sold_curr_year + init_sold;
		return totalVacation;
	}

	@Override
	@Transactional
	public List<DocumentsType> getAllDocumentsType() {
		return commonDao.getAllDocumentsType();
	}

	@Override
	@Transactional
	public synchronized String createIncomeNo() {
		return commonDao.createIncomeNo();
	}

	@Override
	@Transactional
	public void addtransAttachs(Integer arcRecordId, Integer arcCopyRecordId, List<AttachmentModel> attachList)
			throws Exception {
		List<Integer> attachIds = addAttachs(attachList);
		for (Integer id : attachIds) {
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(id);
			arcRecAttId.setRecordId(arcRecordId);
			arcRecAtt.setId(arcRecAttId);
			saveObject(arcRecAtt);
		}
		if (arcCopyRecordId != null)
			for (Integer idCopy : attachIds) {
				ArcRecAtt arcRecAtt = new ArcRecAtt();
				ArcRecAttId arcRecAttId = new ArcRecAttId();
				arcRecAttId.setAttachId(idCopy);
				arcRecAttId.setRecordId(arcCopyRecordId);
				arcRecAtt.setId(arcRecAttId);
				saveObject(arcRecAtt);

			}
	}

	@Override
	@Transactional
	public void wrkAppTransfert(WrkApplication application, WrkApplicationId wrkId, boolean secretFlag,
			List<Integer> WrkCopyEmpRecievers, List<Integer> WrkCopyMngRecievers, List<AttachmentModel> attachList)
			throws Exception {
		try {
			wrkTransferApplication(wrkId.getApplicationId().toString(), application.getArcRecordId().toString(),
					application.getFromUserId(), application.getToUserId(), application.getUserComment(),
					application.getApplicationPurpose());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("wrkSendAction wrkAppId :" + application.getId().getApplicationId());

		}

		List<Integer> attachIds = addAttachs(attachList);
		attchFilesToTrans(application, attachIds);
	}

	private void attchFilesToTrans(WrkApplication application, List<Integer> attachIds) {
		for (Integer id : attachIds) {
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(id);
			arcRecAttId.setRecordId(application.getArcRecordId());
			arcRecAtt.setId(arcRecAttId);
			saveObject(arcRecAtt);
		}
		for (Integer idCopy : attachIds) {
			ArcRecords recordCopy = new ArcRecords();
			recordCopy.setRecTitle(Utils.loadMessagesFromFile("copy.from") + " " + "arcRecords.getRecTitle()");
			recordCopy.setLetterFrom(commonDao.findUserSection(Utils.findCurrentUser().getUserId()));

			recordCopy.setCreatedIn(new Date());
			recordCopy.setRecHDate(HijriCalendarUtil.findCurrentHijriDate());
			recordCopy.setIncomeYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
			recordCopy.setRecGDate(new Date());
			recordCopy.setCreatedBy(Utils.findCurrentUser().getUserId());
			recordCopy.setApplicationType(0);

			// TODO activate
			int archId = save(recordCopy);
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(idCopy);
			arcRecAttId.setRecordId(archId);
			arcRecAtt.setId(arcRecAttId);
			saveObject(arcRecAtt);

		}
	}

	@Override
	@Transactional
	public List<WrkFinesEntity> getAllWrkFinesEntity() {
		return commonDao.getAllWrkFinesEntity();
	}

	@Override
	public ArcRecords findRecordByOutComeNo(int outcomeNumber) {

		return commonDao.findRecordByOutComeNo(outcomeNumber);
	}

	// @Override
	@Transactional
	public Integer sendWrkApp(WrkApplication wrkApplication, ArcRecords arcRecord, List<Integer> attachmnetIds) {
		String CurrentHijriDate = HijriCalendarUtil.findCurrentHijriDate();
		String CurrentTime = HijriCalendarUtil.findCurrentTime();
		Date sysDate = new Date();

		int archId = arcRecord.getId();

		wrkApplication.setArcRecordId(archId);
		wrkApplication.setFromUserId(Utils.findCurrentUser().getUserId());
		wrkApplication.setApplicationCreateDate(sysDate);
		wrkApplication.setApplicationCreateTime(CurrentTime);
		wrkApplication.setApplicationStatus("F");
		wrkApplication.setHijriDate(CurrentHijriDate);
		wrkApplication.setApplicationIsRead(0);
		wrkApplication.setApplicationIsReply(0);
		wrkApplication.setApplicationIsVisible(1);

		int wrkAppId = commonDao.saveWrkApp(wrkApplication, 0).getApplicationId();

		saveAttachs(attachmnetIds, archId);
		return archId;

	}

	@Override
	@Transactional
	public List<FinEntity> getAllfinEntitys() {
		return commonDao.getAllfinEntitys();

	}

	@Override
	@Transactional
	public List<Project> getAllProjects() {
		return commonDao.getAllProjects();
	}

	@Override
	@Transactional
	public List<ProjectContract> getallProjectContract() {
		return commonDao.getallProjectContract();

	}

	@Override
	@Transactional
	public AutorizationSettings loadAutorization() {

		return commonDao.getAutorizations();
	}

	@Override
	@Transactional
	public void loadNormalVacationNew(int empNB, String firstApplicationDate, String gregBirthDate, Integer userid,
			int currentDebit, HrEmployeeVacation employeeVacation, HrsVacSold hrsVacSoldList) {
		// HrsSumVacation hrsSumVacation = commonDao.listsumbyEmp(empNB);
		// /* Ø±ØµÙŠØ¯ Ø§Ù„Ø§Ø¬Ø§Ø²Ø§Øª Ù…Ù†Ø° Ø¨Ø¯Ø¡ Ø§Ù„ØªØ¹ÙŠÙ† */
		// employeeVacation
		// .setTotalVacationPeriod(getTotalVacationPeriod(empNB,
		// firstApplicationDate));
		// employeeVacation.setTotalVacationPeriod(getTotalVacationSold((empNB)));
		employeeVacation.setTotalVacationPeriod((hrsVacSoldList.getOldSold()) + (hrsVacSoldList.getCurrentYearSold()));
		// employeeVacation.setTotalVacationPeriod(hrsSumVacation.getVACOLDYEAR37());
		/* Ø§Ø¬Ù…Ø§Ù„Ù‰ Ù…Ø§ØªÙ…ØªØ¹ Ø¨Ù‡ Ø®Ù„Ø§Ù„ Ø§Ù„Ø¹Ø§Ù… */
		// employeeVacation.setUsedNormalYearlyPeriod(commonDao.getUsedNormalYearlyPeriod(empNB));
		employeeVacation.setUsedNormalYearlyPeriod(hrsVacSoldList.getCurrentYearTaken());
		/* Ø¬Ù…Ù„Ù‡ Ø§Ù„Ø§Ø¬Ø§Ø²Øª Ø§Ù„ØªØ§Ø±ÙŠØ®ÙŠÙ‡ */
		// employeeVacation.setUsedTotalVacationPeriod(
		// getUsedTotalVacationPeriod(empNB)/* Ø§Ø¬Ø§Ø²Ø§Øª Ù„ØªØ§Ø±ÙŠØ® Ø§Ù„ÙŠÙˆÙ… */);

		// /* Ø¬Ù…Ù„Ù‡ Ø§Ù„Ø±ØµÙŠØ¯ Ø§Ù„Ù…ØªØ§Ø­ */employeeVacation
		// .setRemainTotalVacationPeriod((int)
		// employeeVacation.getTotalVacationPeriod() - (employeeVacation
		// .getUsedNormalYearlyPeriod()/*
		// * + hrsSumVacation.
		// * getVACSUMYEARSOLD()
		// */));
		/* Ø¬Ù…Ù„Ù‡ Ø§Ù„Ø±ØµÙŠØ¯ Ø§Ù„Ù…ØªØ§Ø­ */
		employeeVacation.setRemainTotalVacationPeriod(hrsVacSoldList.getAvalibelSold());
		// /*Ø§Ù„Ø±ØµÙŠØ¯ Ø§Ù„Ø­Ø§Ù„Ù‰ Ù„Ù„Ø³Ù†Ù‡ */ currentDebit
		// int x = commonDao.loadVacationSumLastYear(empNB);

		// employeeVacation.setVacationPeriodThisYear(getVacationPeriodThisYear());
		// + employeeVacation.getVacationPeriodThisYear()));
		int maxYearlyPeriod = 144;
		// getMaxYearlyNormalYearlyPeriod(firstApplicationDate, gregBirthDate);
		/* Ø§Ù„Ø±ØµÙŠØ¯ Ø§Ù„Ù…Ø³Ù…ÙˆØ­ Ø¨Ù‡ Ø§Ù‚Ù„ Ù…Ù† 120 */ employeeVacation
				.setRemainNormalYearlyPeriod(getRemainNormalYearlyPeriod(getMaxYearlyNormalYearlyPeriod(firstApplicationDate, gregBirthDate),
						employeeVacation.getUsedNormalYearlyPeriod(), employeeVacation.getRemainTotalVacationPeriod()));
		// employeeVacation
		employeeVacation.setVacationType(MyConstants.NORMAL_VACATION);
		// .setRemainNormalYearlyPeriod(getRemainNormalYearlyPeriod(employeeVacation.getVacationPeriodThisYear(),
		// employeeVacation.getUsedNormalYearlyPeriod(),
		// employeeVacation.getRemainTotalVacationPeriod()));
		HrEmployeeVacation lastNormalVacation = commonDao.loadLastNormalVacation(empNB);
		if (lastNormalVacation == null)
			lastNormalVacation = new HrEmployeeVacation();
		employeeVacation.setLastVacationDate(lastNormalVacation.getHigriVacationStart());
		employeeVacation.setLastVacationPeriod(lastNormalVacation.getVacationPeriod());
		employeeVacation.setLastVacationFrom(lastNormalVacation.getHDatevacationFrom());
		employeeVacation.setLastVacationTo(lastNormalVacation.getHDateVacationTo());
		employeeVacation.setLastVacationStart(lastNormalVacation.getHigriVacationStart());
		employeeVacation.setLastVacationEnd(lastNormalVacation.getHigriVacationEnd());
		employeeVacation.setPeriodLessFive(checkPeriodVacation(empNB, userid));
		// employeeVacation.setVacationCalc((HrsVacationCalc)
		// commonDao.findEntityById(HrsVacationCalc.class, empNB));
	}

	@Override
	@Transactional
	public HrsVacSold loadHrsVacSoldById(Integer id) {

		return commonDao.getHrsVacSoldById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void acceptVacation(WrkApplicationId wrkApplicationId, HrEmployeeVacation employeeVacation, int vacationType,
			HrsVacSold hrsVacSold) {
		WrkApplication app = (WrkApplication) commonDao.findWrkApplicationById(wrkApplicationId);
		WrkApplication application = new WrkApplication(app);
		commonDao.updateWrkApplicationVisible(wrkApplicationId);
		application.setId(wrkApplicationId);
		application.getId().setStepId(application.getId().getStepId() + 1);
		application.setApplicationPurpose(getVacNormalPurpId(application.getId().getStepId()));
		Integer acceptCount = commonDao.getHrsSignNextStep(application.getArcRecordId());
		Integer userTo = getNextScenarioUserId(vacationType, app.getId().getApplicationId(), app.getArcRecordId(), 2);
		application.setToUserId(userTo);
		createNewWrkApplication(application.getArcRecordId(), application, null, false, null);

		boolean visible = (getDestinationModel(vacationType, acceptCount).getSigned() == 1);
		saveHrsSigns(application.getArcRecordId(), employeeVacation.getId(), visible, null,
				Utils.findCurrentUser().getUserId(), vacationType);
		if (visible) {
			employeeVacation.setVacationStatus(MyConstants.YES);

			//updateArcRecordsIncomeNo(app.getArcRecordId());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				employeeVacation
						.setExcuseDateHigri(HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(sdf.format(new Date())));

				hrsVacSold.setCurrentYearTaken(
						(hrsVacSold.getCurrentYearTaken()) + (employeeVacation.getVacationPeriod()));
				commonDao.update(hrsVacSold);
			} catch (ParseException e) {
				logger.error(
						"acceptVacation wrkID" + " " + application.getId().getApplicationId() + " " + e.getMessage());
			}
			//employeeVacation.setExcuseNumber(commonDao.createOutcomeNo().toString());
			String period = MessageFormat.format(Utils.loadMessagesFromFile("wrk.period"),
					employeeVacation.getHigriVacationStart(), employeeVacation.getHigriVacationEnd());
			commonDao.update(employeeVacation);
			if (employeeVacation.getVacationType() != MyConstants.SICK_VACATION) {
				String title = Utils.loadMessagesFromFile("copy.decision") + app.getApplicationUsercomment() + period;
				List<User> toIds = getListCopyReceivers(employeeVacation.getEmployeeNumber());
				String reportName = "";
				if (vacationType == MailTypeEnum.VACNEEDED.getValue()) {
					reportName = MyConstants.REPORT_FORCED_VACATION;
				} else if (vacationType == MailTypeEnum.VACATION.getValue()) {
					reportName = MyConstants.REPORT_NORMAL_VACATION;
				}
				createCopyHrsDocument(employeeVacation.getId(), title, toIds, reportName);

			}
		}

	}

	@Override
	@Transactional
	public List<HrsUserAbsent> getAuthorizationsHoursNumber(int userId, String month, String year) {
		return commonDao.getAuthorizationsHoursNumber(userId, month, year);

	}

	@Override
	@Transactional
	public List<HrsUserAbsent> getAllEmpApsent(int userId) {
		return commonDao.getAllEmpApsent(userId);

	}

	@Override
	@Transactional
	public Article getArticleById(Integer id) {
		return commonDao.getArticleById(id);
	}

	@Override
	@Transactional
	public ArcUsers findUserById(int userId) {
		return commonDao.findUserById(userId);
	}

	@Override
	@Transactional
	public List<CompensatoryVacStock> getUserCompensatoryVacStockByEmpNo(Integer empNO, Integer compVacType) {
		return commonDao.getUserCompensatoryVacStockByEmpNo(empNO, compVacType);
	}

	@Override
	@Transactional
	public List<User> getAllEmployeesInSelectedDepts(List<String> deptIds) {
		// TODO Auto-generated method stub
		return commonDao.getAllEmployeesInSelectedDepts(deptIds);
	}

	@Override
	@Transactional
	public void deleteFngUserTempShiftById(Integer userId, Date workDate) {
		// TODO Auto-generated method stub
		commonDao.deleteFngUserTempShiftById(userId, workDate);
	}

	@Override
	@Transactional
	public FngTimeTable getTimeShiftById(Integer timeShiftId) {
		// TODO Auto-generated method stub
		return commonDao.getTimeShiftById(timeShiftId);
	}

	@Override
	@Transactional
	public List<FngUserTempShift> getEmployeeShiftsById(Integer userId, Date workDate) {
		// TODO Auto-generated method stub
		return commonDao.getEmployeeShiftsById(userId, workDate);
	}

	@Override
	@Transactional
	public WrkDept getDeptById(Integer deptId) {
		return (WrkDept) commonDao.findEntityById(User.class, deptId);
	}

	@Override
	@Transactional
	public List<StoreRequestModel> ListStoreRequestModel(int strNo) {
		return dataAccessDAO.ListStoreRequestModel(strNo);
	}

	@Override
	@Transactional
	public String convertNumberToLiteralWords(Float number) {

		return dataAccessDAO.convertNumberToLiteralWords(number);
	}

	@Override
	@Transactional
	public List<StockEntryMaster> searchMemoReceipts(String beginDate, String finishDate, Integer strNo) {
		return dataAccessDAO.searchMemoReceipts(beginDate, finishDate, strNo);
	}

	@Override
	@Transactional
	public MemoReceiptModel getMemoReceiptDetails(Integer memo_receipt_id) {
		return dataAccessDAO.getMemoReceiptDetails(memo_receipt_id);
	}

	@Override
	@Transactional
	public List<InventoryMaster> getInventoriesByStrNo(Integer strNo) {
		return commonDao.getInventoriesByStrNo(strNo);
	}
	@Override
	public List<Article> getAllArticles() {
		// TODO Auto-generated method stub
		return commonDao.getAllArticles();
	}

//	@Override
//	@Transactional
//	public HrsJobHistorical gethrsjobHistorical(Integer employerNB) {
//		return commonDao.loadLastHistoricalJob(jobNumber, jobCode);
//	}
	@Override
	@Transactional
	public HrsJobHistorical	loadLastHistoricalJob(Integer jobNumber, String jobCode) {
	return commonDao.loadLastHistoricalJob(jobNumber, jobCode);
}

	@Override
	@Transactional
	public HrsJobCreation	loadJobCreation(Integer jobNumber, String jobCode) {
	return commonDao.loadJobCreation(jobNumber, jobCode);
}
	@Override
	@Transactional
	public void saveOperation(DeputationTraining dep_tr) {
		 commonDao.addOperation(dep_tr);
	}
	@Override
	@Transactional
	public void exportDepTrainFile(Integer emp_no,Integer month,Integer year) {
		
		List<String> fileList = commonDao.loadDepTrain(emp_no, month, year);
		
		String fileName = "xx.txt";
		
			fileName = MyConstants.deputation_training;
		

		createFileInServer(fileList, fileName);
	}
	@Override
	@Transactional
	public List<DeputationTraining> loadStatus(Integer emp_number) {
		return commonDao.loadStatus(emp_number);
		
	}
	@Override
	@Transactional
	public List<DeputationTraining> loadStatus(Integer emp_number,Integer month,Integer year) {
		return commonDao.loadStatus(emp_number,month,year);
		
	}

	@Override
	@Transactional
	public void saveReward(RewardInfo rewardinfo) {
		commonDao.saveReward(rewardinfo);
	}

	@Override
	@Transactional
	public List<RewardInfo> loadRewards(Integer emp_number) {
		return commonDao.loadRewards(emp_number);
		
	}
	@Override
	@Transactional
	public void exportRewardFile(Integer emp_no,Integer month,Integer year) {

		List<String> fileList = commonDao.loadReward(emp_no, month, year);
		String fileName = "xx.txt";
		
			fileName = MyConstants.reward;
		

		createFileInServer(fileList, fileName);
	}
	@Override
	@Transactional
	public List<RewardInfo> loadRewards(Integer emp_number, Integer month, Integer year) {
		return commonDao.loadRewards(emp_number, month, year);

	}

	@Override
	@Transactional
	public Integer getIdFromWorkAppByAppId(Integer appId) {
		return commonDao.getIdFromWorkAppByAppId(appId);
	}

	@Override
	@Transactional
	public List<WrkLetterFrom> loadAllWrkLetterFrom() {
		return commonDao.loadAllWrkLetterFrom();
	}

	@Override
	@Transactional
	public List<WrkLetterTo> loadAllWrkLetterTo() {
		return commonDao.loadAllWrkLetterTo();
	}

	@Override
	@Transactional
	public List<WrkPurpose> loadAllPurposes() {
		return commonDao.loadAllPurposes();
	}

	@Override
	@Transactional
	public List<WrkCommentType> loadAllCommentTypes() {
		return commonDao.loadAllCommentTypes();
	}

	@Override
	@Transactional
	public List<VacationsType> loadAllVacationTypes() {
		return commonDao.loadAllVacationTypes();
	}

	@Override
	@Transactional
	public List<SysCategoryEmployer> loadAllCategoryEmployers() {
		return commonDao.loadAllCategoryEmployers();
	}

	@Override
	@Transactional
	public List<FngStatusAbsence> loadAllAbsenceStatus() {
		return commonDao.loadAllAbsenceStatus();
	}

	@Override
	@Transactional
	public List<FngTypeAbsence> loadAllAbsenceTypes() {
		return commonDao.loadAllAbsenceTypes();
	}
	@Override
	@Transactional
	public List<WrkDept> findDepartmentById(Integer deptId){
		return commonDao.findDepartmentById(deptId);
	}
	public List<LoanModel> loadUsersLoan(Integer year,Integer month,Integer type){
		
		return commonDao.loadUsersLoan(year,month,type);
	}
	public List<RetirementModel> loadRetirement(){
		return commonDao.loadRetirement();
	}
	
}
