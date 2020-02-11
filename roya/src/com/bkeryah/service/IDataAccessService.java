package com.bkeryah.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.UploadedFile;
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
import com.bkeryah.entities.*;
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
import com.bkeryah.entities.licences.BldLicNew;
import com.bkeryah.entities.licences.BldLicWall;
import com.bkeryah.entities.licences.BldPaperTypes;
import com.bkeryah.entities.licences.LicAgents;
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
import com.bkeryah.model.AbsentModel;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.model.DashbordModel;
import com.bkeryah.model.LoanModel;
import com.bkeryah.model.MemoReceiptModel;
import com.bkeryah.model.RetirementModel;
import com.bkeryah.model.User;
import com.bkeryah.model.VacationModel;
import com.bkeryah.penalties.LicTrdMasterFile;
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

public interface IDataAccessService {
	public List<WrkDept> findDepartmentById(Integer deptId);

	public List<UserClass> findAllUsers();

	// public UserClass login(String username, String passWord);

	public String findSystemProperty(String propertyName);

	// public UserClass findCurrentUser();

	public List<UserFolderClass> findAllUserFolders(int vuser_id);

	public List<UserContactClass> findInboxSenderList(int vuser_id);

	public String findAllowedFilesTypes(String fileTypes);

	public void wrkTransferApplication(String wrkId, String appId, int vuser_id, int wrkReciever, String wrkCommTxt,
			int wrkPurp);

	public void sendRecordSingleCopy(String appId, String wrkId, int vuser_id, int employeeId, String string,
			int wrkPurp, String wrkCommTxt, String appTitle, int stepId);

	public void wrkSaveComment(int UserId, String WrkAppId, String AppNo, String AppDate, int AppPapers, String AppTo,
			String AppSubject, String AppAttach, String AppLongComment, String FirstCopy, String SecondCopy,
			int FontSize, int CommentType, int CommentWroteBy, String commentWroteIn);

	public String getEmployeeManagerId(String valueOf);

	public String CreateNewWrkComment(int currEmp, String newWrkCommTopic, int currMng, int newWrkCommPurpose,
			String newWrkCommDirectedTo, String newWrkCommMainContent, String newWrkCommLowerCopy, int newWrkCommTyp,
			String newWrkCommHiegherCopy, int newWrkPapers);

	public String transferAttachment(InputStream inputstream, String fileName, int vuser_id, String arcRecordIdOutPut);

	public List<UserContactClass> findCommentSignEmployeesList(int vuser_id, String wrkId);

	public boolean CheckSignturePassword(int vuser_id, String confirmPassword);

	public void SignWrkComment(int vuser_id, String wrkId, int stepId, String appId, String confirmPassword,
			String commentSignType, int selectedUserInSignComment);

	public void sendCommentCopy(int vuser_id, int parseInt, String appId, String appId2);

	public List<WrkCommentType> findAllCommentTypes();

	/**
	 * load wrk purpose list
	 * 
	 * @param puserid
	 * @return
	 */
	public List<WrkPurposeClass> findAllWrkPurpose(Integer puserid);

	public List<ArcAttachmentClass> findAttachmentFilesByArcId(String appId);

	public List<UserContactClass> findAllManagers(int vuser_id);

	public List<UserContactClass> findAllEmployeesInDept(int vuser_id);

	public List<UserMailObj> findEmployeeInbox(int vuser_id);

	public List<UserMailObj> findEmployeeOutbox(int vuser_id, String inDate, int partOfYear);

	public int EmployeeArchivedArch(int vuser_id);

	public List<WrkCommentsClass> findCommentsByWrkId(String wrkId);

	public CommentClass getWrkCommentInfoByWrkId(String wrkId);

	public String getEmployeeRealName(int markedBy);

	public List<UserMailObj> SearchEmployeeInbox(int vuser_id, int i, String searchKey, String searchIncluedComment);

	public void addRecordToFavourit(String wrkId, int stepId, int vuser_id);

	public List<UserMailObj> findFavouritInbox(int vuser_id);

	public List<UserMailObj> findEmployeeInboxUnread(int vuser_id);

	public List<UserMailObj> findEmployeeInboxRead(int vuser_id);

	public boolean WrkAppHasComment(String wrkId);

	public void markComment(String wrkId, int vuser_id, String appId);

	public boolean EmployeeHasSign(int vuser_id);

	public String retrieveRecord(int vuser_id, String appId, String wrkId);

	public String SendNewRecord(String newRecordTitle, int isImportantRecord, int vuser_id, int newRecordReciever,
			String newRecordComm, int newRecordPurp, int isSecret, String createIncomeNo);

	public String SendNewRecordcopy(String newRecordTitle, int isImportantRecord, int vuser_id, int employeeId,
			String newRecordComm, int newRecordPurp, int isSecret, String string, String newRecordId);

	public void addExistedAttachToRecord(String attachaId, String x);

	public List<String> findAllWrkCommentAppTo(String x);

	public List<UserMailObj> findEmployeeInboxBySenderId(int vuser_id, int selectedSender);

	public List<UserMailObj> findEmployeeInboxDaily(int vuser_id);

	public List<UserMailObj> findEmployeeOutboxArchive(int vuser_id);

	public List<UserMailObj> searchEmployeeOutbox(int vuser_id, String outboxSearchKey, String srchTyp);

	public String MoveRecordToFolder(String wrkId, int stepId, int vuser_id, int parseInt);

	public List<UserMailObj> findEmployeeFolderRecords(int vuser_id, int selectedUserFolder);

	public void MakeItRead(String valueOf, String wrkId);

	public String convertTextWithArNumric(String txt);

	/**
	 * 
	 * load application type
	 * 
	 * @return
	 */

	public List<ArcApplicationTypeClass> findAllApplicationTypes();

	/**
	 * load list of letter form
	 * 
	 * @return
	 */
	public List<WrkLetterFromClass> findAllWrkLetterFroms();

	/**
	 * load list letter to
	 * 
	 * @return
	 */
	public List<WrkLetterToClass> findAllAWrkLetterTos();

	/**
	 * load list of wrk archive reciept
	 * 
	 * @return
	 */
	public List<WrkArchiveRecipentClass> findAllWrkArchiveRecipents();

	public String CreateNewIncomeRecord(int UserId, String ArcTitle, int ArcPurp, int ArcTyp, int LetterTo,
			int LetterFrom, int ArcSendTo, String LetterFromNo, String LetterFromDate, String RefrencedLetterNo,
			String MobileNumber, String LongComment, int IsArcRecordImportant);

	public Map<String, String> findrecordsMap();

	public String addNewExportedRecord(String IncomeNumber, int UserId);

	public ArcRecordClass getRecordbyIcomeNUmber(String IncomeNumber);

	public void addNewUserFolder(String FolderName, int UserId);

	public String deleteUserFolder(int FolderId);

	public String updateUserPassword(int UserId, String NewPassword);

	public String updateUserSignPassword(int UserId, String NewPassword);

	public List<Main_menu_class> getMenuByUser(int userId);

	public List<Main_menu_class> findAllMainMenu();

	public List<Sub_menu_class> findExistedSubMenuByMainMenu(int userId, int MainMenuId);

	public void removeMenufromUser(int Userid, int Menuid);

	public void removeSubMenufromUser(int Userid, int SubMenuid);

	public List<Main_menu_class> findAllAvaliableMainMenu(int Userid);

	public List<Sub_menu_class> findAvaliableSubMenuByMainMenu(int userId, int MainMenuId);

	public void addMenuToUser(int Userid, int Menuid);

	public void addSubMenuToUser(int Userid, int SubMenuid);

	public List<ReferralSettingClass> findAllReferralSetting();

	public List<SpecialAddressClass> findSpeicalAddressByUser(int pUserId);

	public String addNewSpecialAddress(int pUserId, int pSpecialAddressId);

	public void addNewApplicationType(String NewApplicationTypeName);

	public void addNewPurpose(String PurposeName);

	public void addNewWrkLetterTo(String NewLetterToName);

	public String addNewReferralSetting(int pManagerId, String ManagerTitle);

	public List<WrkDeptClass> findAllDepartment();

	public List<WrkRolesClass> findAllRoles();

	public List<SysTitleClass> findAllSysTitles();

	public List<WrkSectionClass> findAllSectionsByDept(int deptId);

	public List<WrkJobClass> findAllJobsBySection(int SectionId);

	public int createNewUser(String LoginName, String Password, String FirstName, String LastName, int titleId,
			int JobId, int DepartmentId, int ManagerId, int WrkRoleId, int SectionId, String MobileNumber);

	public String updateUserInfo(int UserId, int ManagerId, int RoleId, int DeptId, int SectionId, int JobId);

	public String updateUserNameInfo(int UserId, String FirstName, String LastName, String NameAr, String NameEn);

	public String findDepartmentNameById(int departmentId);

	public int addNewDept(String DepartmentNameAR, String DepartmentNameEN);

	public int addNewsection(String sectionName, int DeptId);

	public Map<String, String> findUsersRolesMap();

	public Map<String, String> findUsersMap();

	public List<WrkChargingClass> findAllChargingByStatus(int Status);

	public String addNewCharging(int WhoIsCharged, int WhoIsNotHere, String UserNameAfter, String StartDate,
			String EndDate, int privilageAfter);

	public String findEmployeeName(int EmployeeId);

	public String updateUserSign(int pUserId, InputStream pSign);

	public String updateUserMark(int pUserId, InputStream pMark);

	public String updateUserRefSign(int pUserId, InputStream pRefSign);

	public List<AttachmentFileClass> getAllFilesbyUser(int UserId);

	public void deleteAttachmentFile(String fileAttachId);

	public String encrypt(String string);

	public List<AttachmentFileClass> SearchAttachmentFiles(int UserId, String Search_key);

	public void addNewAttachment(String FileName, InputStream in, int AddedBy, int FolderId);

	public List<ArcRecordClass> SearchRecordsByCommentdata(String SrchKey);

	public List<ArcRecordClass> SearchRecordsByLetterFrom(int SrchKey);

	public List<ArcRecordClass> SearchRecordsByLetterTo(int SrchKey);

	public List<ArcRecordClass> getRecords(String IncomeNoPar);

	public int findArcRecordParam(String ArcId);

	public void SendRecordFromArchieve(String RecordId, int EmployeeId, String SendingType);

	public List<com.bkeryah.hr.managedBeans.Employer> loadAllEmployers();

	public List<Sub_menu_class> findSubMenus(int UserId, int mainMenuId);

	public Map<String, String> loadDirections();

	public String checkUmmAlQuraDate(String value);

	public EmpVac infoVacEmp(int puserid);

	public EmpVac infVacEmp2(int vuser_id, int vacsum360, int vacprd, int i, int pbascal, int vacaresult,
			EmpVac vacObject);

	public EmpVac loadEtrariVac(int app_id);

	public EmpVac loadNormalVac(int app_id);

	// public EmpVac validate_dates_vac116(int vuser_id, String vACASTRT, int
	// vacprd, HrEmployeeVacation empvacobj);

	public boolean instrVac1166(int vuser_id, int vactype, int vacprd, String vacastrt, String vacaend, int proctyp,
			int salary, int vacval, String vacwrkfrom, String vacwrKto);

	public EmpVac CALC_VAC122_emp122(int vuser_id, EmpVac empvacobj);

	public EmpVac checkdate122(String vacastrt, int vacprd, EmpVac empvacobj);

	public boolean prc_insrt_vacEtrari(int vuser_id, int vactype, int vacprd, String vacastrt, String vacaend);

	public EmpVac prc_accept_vac(int PWRKID, int vuser_id, String d_MGRtxt, String d_PRStxt);

	public EmpVac prc_accept_vacEtrati(int pWRKID, int vuser_id, String d_MGRtxt, String d_PRStxt);

	public TrdModelClass findTrdApplicationData(int trdApplicationId);

	public Employer getReturnVacData(int vuser_id);

	EmpVac infVacEmp1(int vuser_id, int vacprd, EmpVac vacObject);

	public Integer saveAttachment(InputStream inputStream, String fileName, int OwnerId, double fileSize, int fileNode);

	public Integer saveLetter(String incomeNo, int appPapers, String appTo, String appSubject, String appAttach,
			String longComment, String firstCopy, String secondCopy, int wroteBy, int commentType);

	////////////////////////////////////////////////////////////////////////
	public void addComment(WrkComment comment, WrkApplication application, List<Integer> attachmnetIds);

	public List<WrkPurpose> getAllPurposes();

	public List<WrkCommentType> getCommentTypes();

	// public ArcUsers logIn(String username, String password);

	// get all emps in Dept
	public List<User> getAllEmployeesInDept(Integer deptId);

	//////// Balabel////////////
	public List<HrsMasterFile> getAllEmployeesByDept(Integer deptId);
	///////////////////////

	// get all emps
	public List<ArcUsers> getAllEmployees();

	public void saveAuthorizationOrMission(HrsUserAbsent userAbsent, String title, int applicationType,
			boolean isPictureAtached) throws ParseException;

	public ArcUsers loadUser(String username, String passWord);

	public HrsUserAbsent loadAuthorizationOrMissionByRecordId(int idAuthorization);

	/**
	 * save record into scenario signs
	 * 
	 * @param arcRecordId
	 * @param docId
	 * @param visible
	 * @param note
	 * @param userId
	 * @param modelId
	 */
	public void saveHrsSigns(int arcRecordId, int docId, boolean visible, String note, int userId, Integer modelId);

	/**
	 * accept autorization or mession button
	 * 
	 * @param authorization
	 * @param wrkApplicationId
	 * @param modelType
	 */
	public void acceptAuthorizationOrMission(HrsUserAbsent authorization, WrkApplicationId wrkApplicationId,
			int modelType);

	public void updateHrsEmpAbsent(HrsUserAbsent userAbsent);

	// void saveAuthorization(HrsUserAbsent userAbsent, boolean
	// isPictureAtached) throws ParseException;

	// get all managers
	public List<ArcUsers> getAllManagers();

	public List<Integer> addAttachments(List<ArcAttach> attachs);

	// original Add
	public Integer AddNewInternalMemo(WrkApplication wrkApplication, ArcRecords arcRecord, List<Integer> attachmnetIds);

	// Copy Adding
	public Integer AddCopyInternalMemo(WrkApplication wrkApplication, ArcRecords arcRecord,
			List<Integer> attachmnetIds);

	// get all managers from WrkRefrentionalSetting table

	public List<WrkRefrentionalSetting> getAllManagersFromWFS();

	Integer getUserDeptJob(int managerId);

	Integer createNewArcRecord(ArcRecords arcRecords, boolean withIncomeNumber, Integer toId);

	Integer createHrsEmpAbsent(HrsUserAbsent userAbsent);

	WrkApplicationId createNewWrkApplication(int recordId, WrkApplication application, String userComment,
			boolean isPictureAtached, Integer applicationUserDeptJob);

	HrsUserAbsent loadAuthorizationOrMissionById(int idAuthorization);

	public WrkApplication getWrkApplicationRecordById(int WrkApplicationId);

	public ArcRecords getArchRecordById(int archRecordId);

	public WrkComment getWrkCommentByAppId(int appId);

	// public HrEmployeeVacation getvacationById(int vacationId);
	public List<ArcAttach> getAttachmentByArchRecordId(int archRecordId);

	public Integer newEmpInitaion(EmployeeInitiation eI);

	public Integer newEmpInitaion(EmployeeInitiation empInitiation, Integer vacationId);

	public EmployeeInitiation getInitiationById(Integer initiationId);

	public HrEmployeeVacation getVacationById(Integer vacationId);

	public Integer getLastVacationForCurrentId(Integer employeeNum);

	/**
	 * Load employer from HRSUserAbsent
	 * 
	 * @param arcRecordId
	 * @return
	 */
	public Employer loadEmployerFromAuthorizationOrMission(int arcRecordId);

	public List<MainMenu> findUserMenus(int puserId);

	public List<SubMenu> _findSubMenus(int puserId, int mainMenuId);

	public Object findEntityById(Class entityClass, int EntityId);

	public Employer loadEmployerByUser(ArcUsers arcUsers);

	void saveVacation(Employer employer, HrEmployeeVacation employeeVacation, String title, int applicationType,
			boolean isPictureAtached, List<Integer> attachmnetIds) throws ParseException;

	public ArcUsers findUserFromHrsSigns(int arcRecordId);

	public void redirectRecord(WrkApplicationId oldWrkApplicationId, WrkApplication newWrkApplication,
			List<UploadedFile> files, boolean isSecret);

	public void redirectRecordCopy(ArcRecords oldRecord, WrkApplication newWrkApplication);

	// public int saveInitaionAftrWorl(EmployeeInitiation employeeInitiation)
	// throws VacationAndInitException;
	public Integer saveInitaionAftrWorl(EmployeeInitiation employeeInitiation) throws VacationAndInitException;

	HrEmployeeVacation findVacationByArcRecordId(int arcRecordId);

	public int findForcedVacationScore(int empNB);

	void acceptVacation(WrkApplicationId wrkApplicationId, HrEmployeeVacation employeeVacation, int vacationType);

	void acceptFineRebound(Integer recordId, FineReboundMaster fineReboundMaster, int fineReboundType);

	void updateWrkApplication(WrkApplication wrkApplication);

	public Map<String, Integer> getEmployeeInfo();

	public Map<String, Integer> getEmployeeInDeptInfo();

	public Integer saveCommentOrUpdate(WrkComment comment, boolean activeUpdate);

	public List<PayBank> getAllBanks();

	public WrkComment signComment(WrkApplication wrkApplication, String signType, Integer recieverUserId,List<String> commentCopyReciever);

	public void addBankAccountRequest(List<ArcAttach> attachs, int appType, HrLetterRequest request);

	/**
	 * send request hr letter
	 * 
	 * @param purpose
	 * @param destination
	 * @param appType
	 * @param docType
	 * @param request
	 */
	public void requsetHrLetter(String purpose, String destination, int appType, Integer docType,
			HrLetterRequest request);

	public void updateObject(Object object);

	public void updateObject(Object object1, Object object2);

	public void updateObject(Object object1, Object object2, Object object3);

	public HrsEmpHistorical getEmployeeLastHistoryRecord(Integer id);

	public void updateWrkApplicationVisible(WrkApplicationId wrkApplicationId);

	public Integer getResponsibleId(String JobDescribtion);

	public void loadNormalVacation(int empNB, String firstApplicationDate, String gregBirthDate, Integer userid,
			HrEmployeeVacation employeeVacation);

	public String getLastServiceDate(Integer empNB);

	public void creatCopyOfTransaction(int applicationId, int attachId, String title, int toId, String fileName);

	public Integer getUserIdFromWorkAppByIdAndStepId(Integer recordId, Integer stepId);

	public void addNewHrMedicalRequest(HrMedicalRequest medicalRequest, Integer createdForId, Integer createdById);

	public HrMedicalRequest getMedicalRequestInfoByArchRecordId(Integer archRecordId);

	public void acceptAction(Integer archRecordId, Integer docType, Object updatedObject);

	public List<HrsTrainingPlace> loadTrainingPlaces();

	public HrsTrainingMandate loadTrainingMandate(int rank);

	public void saveTraining(HrsEmployeeTraining employeeTraining, String title, int trainingType, boolean b);

	public HrsEmployeeTraining findTrainingByArcRecordId(int appId);

	public void acceptTraining(WrkApplicationId wrkApplicationId, HrsEmployeeTraining employeeTraining);

	public Integer getWrkCountSigned(Integer arcRecordId);

	HrsScenarioDocument getDestinationModel(int modelId, int stepId);

	public HrsScenarioDocument getScenario(int modelId, int stepId);

	public List<Integer> getAuthorizedEmployeesForModel(int modelId);

	ArcUsers findUserFromHrsTrain(int arcRecordId);

	String getPropertiesValueById(int id);

	WrkApplication findWrkApplicationById(WrkApplicationId wrkApplicationId);

	/**
	 * modelStepUserId is = 1 if you want to find fromId else toId
	 * 
	 * @param modelTypeId
	 * @param WrkId
	 * @param arcRecordId
	 * @param modelStepUserId
	 * @return
	 */
	int getNextScenarioUserId(int modelTypeId, int WrkId, int arcRecordId, int modelStepUserId, Integer... storeId);

	// ArcUsers getFunctionOwnerWrkApp(int WrkId, String function);

	public EmployeeInitiation getEmployeeInitiationByArchrecorId(Integer archRecordId);

	public void acceptActionForEmployeeInitiation(Integer archRecordId, Integer docType, Integer vacationId,
			EmployeeInitiation employeeInitiation);

	public String loadTrainingPlaceName(Integer placeId);

	public String printDocument(String reportName, int documentId, String urlParam);

	/**
	 * refuse model button
	 * 
	 * @param application
	 * @param wrkApplicationId
	 * @param t
	 * @param wrkAppComment
	 * @param applicationPurpose
	 * @return
	 */
	boolean refuseModel(WrkApplication application, WrkApplicationId wrkApplicationId, Object t, String wrkAppComment,
			int applicationPurpose);

	public HrLetterRequest getHrRequsetByArchrecorId(Integer archRecordId);

	public void acceptActionforHrLetter(Integer archRecordId, Integer docType, HrLetterRequest letterRequest);

	public Integer createOutcomeNo();

	public void acceptActionFortransferSalary(Integer archRecordId, Integer docType, HrLetterRequest hrLetterRequest);

	public void updateArcRecordsIncomeNo(ArcRecords arcRecords);

	public void acceptActionforHrmedicalReport(Integer archRecordId, Integer docType, HrMedicalRequest medicalRequest);

	public EmployeeInitiation getLastEmployeeInitiation();

	// public ArcUsers login(String upperCase, String passWord);

	public void injectDaos(Object commonDao, Object dataAccessDAO, Object sessionFactory);

	public Long getOutBoxmailsNumbers(Integer userId);

	public Long getInboxNumbers(Integer userId);

	public Long getUnReadMails(Integer userId);

	public Long getUnReadMailsForToday(Integer userId);

	public Long getNormalVacationCount(Integer empNumber);

	public Long getExcuseVacationCount(Integer empNumber);

	public int getAuthorizationsNumber(int userId, String month, String year);

	public String getHashedPassword(String userName, String password) throws Exception;

	public void updateUserPasswordForSystem(String userName, Integer userId, String newPassword) throws Exception;

	public void updateUserPasswordForSign(String userName, Integer userId, String newPassword) throws Exception;

	public ArcUsers getUserNameById(Integer userId) throws Exception;

	boolean signedIsAutorized(int userId, int privlegeId) throws SQLException;

	public List<WrkRoles> getallRoles() throws Exception;

	public List<Charging> getallCharging();

	public void saveBill(PayLicBills payLicBill);

	public List<PayMaster> loadAllPayMasters();

	public List<NationalIdType> getAllNationalIdTypes();

	public List<Nationality> getallNationalities();

	public List<NationalIdPlaces> getallNationalIdPlaces();

	public void addArcPeople(ArcPeople arcPeople) throws Exception;

	public void deletePayBillDetails(PayBillDetails selectedItem);

	public List<PayLicBills> loadAllBills();

	public List<PayBillDetails> loadBillDetails(Integer billNumber);

	public PayLicBills changeBillNumber(PayLicBills payLicBill);

	public List<Project> getallProjects();

	void addProjectExtract(ProjectExtract projectExtract);

	public List<ProjectContract> getallProjectContract(Integer projectId);

	public Map<Integer, Map<Integer, DocumentScenario>> findAllScenarios();

	public List<DocumentScenario> findScenariosByModelId(int pmodelId);

	public int findLicApplicationIdByRecord(int arcRecordId);

	void addNewProjectExtract(ProjectExtract projectExtract, Integer createdForId);

	ProjectExtract getProjectExtractByArchRecordId(Integer archRecordId);

	public String loadNameByNationalId(long nationalId);

	public String printNationalIdDocument(String nationalId, String name, String requestType);

	void acceptActionforProjectExtract(Integer archRecordId, Integer docType, String wrkAppComment, Integer appPurpose);

	List<TenderItems> getAllTenderItems();

	Integer addNewExchangeRequest(ExchangeRequest exchangeRequest);

	Integer addNewTechnicalResponse(TechnicalResponse technicalResponse);

	Integer addNewExchangeRequestDetailsRecors(ExchangeRequestDetails exchangeRequestDetails);

	public Integer save(Object myObject);

	void acceptActionforExchangeRequest(Integer archRecordId, Integer docType, ExchangeRequest request,
			String wrkAppComment, Integer applicationPurpose, boolean updateRequestDetails,
			List<ExchangeRequestDetails> deletedExchangeRequestDetails);

	ExchangeRequest getExchangeRequestByArchRecordId(Integer archRecordId);

	public StockEntryMaster getStockEntryMasterByArchRecordId(Integer archRecordId);

	public HealthMasterLicence getHealthCertificateRecordId(Integer arcRecordId);

	public byte[] loadArcPeoplePic(Long nationalId);

	public void acceptHealthLicence(Integer arcRecordId, Integer docType, HealthMasterLicence healthCertificate);

	Integer addExchangeRequest(ExchangeRequest exchangeRequest, Integer createdForId,
			List<ExchangeRequestDetails> exchangeRequestDetailslIST);

	public int saveNewIncome(ArcRecords arcRecords, WrkApplication wrkApplication, List<AttachmentModel> attachList,
			List<Integer> copyList, boolean isForSave) throws Exception;

	Integer getStepNumberFromHrSign(Integer archRecordId);

	int getNextArcId();

	Integer addActualyExchangRequest(ActualDisbursement actualDisbursement, Integer createdForId,
			List<ActualDisbursementDetails> actualDisbursementDetails, Integer oldArcRecordId);

	Integer addInventories(List<InventoryRecord> inventoryList);

	ActualDisbursement getActualExchangeRequestByArchRecordId(Integer archRecordId);

	List<WrkApplication> getWrkApplicationListByRecordId(Integer archRecordId);

	List<TechnicalResponse> getTechnicalResponsesByRecordId(Integer archRecordId);

	Integer getArchRecParentFromLinkByRecordId(Integer childArchRecordId);

	Integer getWrkIdByArc(int arcRecordId);

	public void saveProcurement(Procurement procurement, Integer oldArcRecordId);

	void acceptActionforActualExchange(Integer archRecordId, Integer docType,
			List<ActualDisbursementDetails> ActualDisbursementDetails, Integer originalOwnerId, String wrkAppComment,
			Integer applicationPurpose);

	public boolean isIncomeExist(int incomeNumber);

	Integer getArcRecordParent(int arcRecordId);

	List<ArcRecordsLink> getChildsObjectsByParentRecordId(Integer parentArchRecordId);

	List<FinFinancialYear> getAllFinYears();

	List<WhsWarehouses> getAllStores();

	public Procurement loadProcurementByArcRecord(Integer recordId);

	public void acceptProcurement(Procurement procurement, Integer recordId, String wrkAppComment,
			int applicationPurpose);

	Object saveObject(Object myObject);

	String printDocumentByNameAndParams(String reportName, String urlParam);

	String printProjectExtractDocument(Integer projectExtactId);

	void saveMemo(WrkApplication wrkApplication, ArcRecords arcRecord, List<ArcAttach> attachs, List<String> toCopyList,
			Boolean isForSave);

	void sendInternalMemoryForCopyInCharging(Charging charging);

	void addPersonalComment(WrkComment comment, WrkApplication application, List<Integer> attachmnetIds);

	public void sendRecordToArchive(UserMailObj selectedInbox);

	/**
	 * Archive a record list
	 * 
	 * @param checkedMailsList
	 */
	public void sendRecordListToArchive(List<UserMailObj> checkedMailsList);

	public int findEmployeeInboxCount(Integer userId);

	public boolean redirectWrkAppCopies(WrkApplication application, List<Integer> WrkCopyEmpRecievers,
			List<Integer> WrkCopyMngRecievers);

	public void sendWrkApplication(WrkApplicationId wrkId, WrkApplication application, boolean isSecret,
			List<Integer> WrkCopyEmpRecievers, List<Integer> WrkCopyMngRecievers);

	public void saveFineReboundMaster(FineReboundMaster fineReboundMaster, List<AttachmentModel> attachList)
			throws Exception;

	public FineReboundMaster getFineReboundMasterByArcRecordId(Integer recordId);

	public Integer getHrsSignNextStep(Integer recordId);

	public List<ArcRecords> searchArcRecords(String subject, String letter, Integer recordSender, Integer incomeNB,
			String letterFromNo, List<Integer> selectedStructIdList, boolean employer);

	public List<WrkInboxFolder> getAllfoldersForUser(int userId);

	public void addFolderForUser(WrkInboxFolder folderInfo);

	public void addMailsToFolder(List<WrkUserFolderMail> mails);

	public List<WrkUserFolderMail> getAllaMailsInFolder(int folderId);

	public int getFolderNameForWrkApp(int stepId, int wrkId);

	WrkUserFolderMail getFolderMailInfoByMailIdStepNum(int stepId, int wrkId);

	void deleteMailFomFolder(WrkUserFolderMail folderMail);

	public List<WrkSection> loadArcDocumentStructList();

	Integer findStepId(int recordId);

	public List<EmployeeForDropDown> getSpecialEmployeeList();

	void addChargingProcess(Charging charging);

	public void finishChargingEmp(int chargingEmpIn);

	ArcUsers getUserByLoginName(String loginName);

	Integer getFunctionOwnerWrkApp(int WrkId, String function, int arc_records);

	public List<WrkDept> findAllDepartments();

	public void addNewArticleGroup(ArticleGroup articleGroup);

	List<ArticleGroup> getAllArticleGroups();

	void addNewArticleSubGroup(ArticleSubGroup articleSubGroup);

	List<ArticleSubGroup> getAllArticleSubGroups();

	void addNewArticle(Article article);

	List<Article> getAllArticles(Integer strNo);

	List<ItemUnite> getAllUnites();

	// List<Integer> getAttachmentIdsByRecordId( int arcRecordID);
	public ArcUsers loadUserById(Integer employerId);

	Integer redirectCopy(int stepId, int origWrkId, String newComm, int toId, int toIdCopy, Integer newArcRecord,
			String toUserId);

	public void retrieveRecordFromArchive(int recordId, int userId);

	public List<ArticleSubGroup> getAllArticleSubGroupsByGroupId(int groupId);

	public InventoryMaster getInventoryMasterById(int Id);

	Integer addNewInventoryDetailsRecors(InventoryRecord inventoryRecord);

	public WrkApplication createNewWrkAppForRefuse(WrkApplicationId wrkID, String reason);

	public void refuseExchangeRequest(WrkApplicationId wrkId, Integer recordId, ExchangeRequest request,
			String wrkAppComment, int applicationPurpose);

	public List<ArcUsers> loadTechnicalUsers();

	public List<TechnicalUsers> loadNewTechnicalUsers();

	public List<InventoryModel> ListInventories(int strNo, Integer inventoryId);

	public List<InventoryRecord> getInventoryrecordByarticleId(int articleId, Integer inventoryId);

	public Integer saveLetterHistory(int letterId, String letterBody);

	public void redirectExchangeRequest(WrkApplicationId oldWrkAppId, int wrkReceiver, String notes);

	public void deleteObject(Object object);

	List<HrsMasterFile> loadHrsMasterEmployers();

	List<HrEmployeeVacation> loadVacationInfo(Integer employerId, Integer vacationid);

	public List<VacationsType> loadVacationsType();

	public List<ArcAttach> getAttachmentByIncomeNumber(int incomeNumber);

	public String createLetterLinkfromIncomeNo(int incomeNumber);

	public ArcRecords findRecordByIncomeNumber(int incomeNumber);

	public Integer getPropertiesValue(String propName);

	public List<ArcPeople> loadnameEmployerbynationid(Long nation);

	public List<SysCategoryEmployer> loadCategoryEmployer();

	public List<SysReligion> loadReligionEmployer();

	public List<SysQualification> loadQualifiedemployer();

	public List<SysBirthCountry> loadBirthCountry();

	public List<SysGraduatePlace> loadGraduatEmploye();

	public List<SysNationality> loadNationalEmploye();

	public List<SysSpecialization> loadSpecialEmploye();

	public List<PayBank> loadbank();

	public WrkComment findWrkCommentByAppNo(String appNo);

	public boolean recordIslinked(int currentIncome);

	public Integer saveBill(PayLicBills bill, List<PayBillDetails> billDetails);

	public void saveMasterHrsCompactPerformance(HrsCompactPerformance hrsCompactPerformance,
			List<HrsCompactFloors> hrsCommpactFloors, List<HrsCompactGoals> lstHrsCompactGoals, String title,
			Integer countCatNumber);

	public void saveGeneralAppreciation(HrsGeneralAppreciation generalAppreciation,
			List<HrsCompactEmpCaracter> listEmpCaracters, String title, Integer EmpId, List<Integer> attachmnetIds);

	public ArcRecordsLink getArcLinkByRecordId(Integer archRecordId);

	List<WrkCommentsClass> findCommentsByArcId(Integer wrkId,Integer stepId);

	List<HrsAppreciationScale> findAppreciationScalesByFloorId(Integer floorId);

	public ArcUsers loadUserByEmpNO(Integer employerId);

	public HrsEmpHistorical getHRsEmpHistoricalByEmpNo(Integer employerId);

	public HrsSalaryScale findHrsSalaryScaleById(Integer rankNumber);

	public Integer loadScoreSickVacation(Integer empNo);

	public List<HrsSalaryScaleDgrs> loadScaleDgree(Integer rankCode);

	public HrEmployeeVacation loadLastVacation(Integer empNB, int sickVacation);

	public HrsSalaryScaleDgrs loadSelectedDegree(Integer dgreeId);

	public HrsSalaryScaleDgrs loadSelectedDegree(Integer classCode, Integer rankCode);

	public HrEmployeeVacation loadVacationByArcRecord(int recordId);

	public Integer saveEmployer(HrsMasterFile hrmasfile, HrsEmpHistorical empHistoric, HrsJobCreation selectJob);

	public Integer saveNewEmployee(HrsMasterFile hrmasfile, HrsEmpHistorical empHistoric, HrsJobCreation selectJob);

	public void acceptLicTrdModel(WrkApplicationId wrkId, TrdModelClass selectedTrdApplication);

	public TrdModelClass findTrdApplicationDataByArcrecord(Integer arcRecordId);

	public Charging getChargingemployer(Integer userId);

	Integer getUserTo(int modelId, int stepId);

	void updateStockEntry(StockEntryMaster stockEntryMaster);

	void acceptStockEntry(StockEntryMaster stockEntryMaster, Integer recordId, int modelType, String usercomment,
			Integer purpose);

	List<StoreRequestModel> getTransactionsQty(int articleId, int srtNo);

	public List<ExchangeRequest> searchExchangeRequests(String beginDate, String finishDate, Integer strNo);

	public int nationalIsFound(Long nationalNumber, Integer integer);

	public List<StoreRequestModel> getArticleHistory(Integer articleId);

	List<UserRoles> loadAllRoles();

	List<FinEntity> loadSupliers();

	public void updateUserRoles(ArcUsers selectedUser, List<String> selectedRoles);

	List<SysProperties> loadAllSysProperties();

	List<UserRoles> loadUserRoles(Integer userId);

	public List<HrsMasterFile> findAllEmplyersWorks();

	public HrsMasterFile loadEmployerData(Integer empno);

	public HrsEmpHistorical loadEmployerDataHistorcial(Integer empno);

	public Integer getCreatedId(String jobcode, Integer jobNumber);

	List<HrsJobCreation> loadHrJob(Integer catid, Integer status, Integer rank);

	public HrsJobCreation loadSelectedJob(Integer createId, Integer status);

	public void updateScenario(HrScenario scenario, List<HrsScenarioDocument> removedSteps);

	public List<ArcApplicationType> loadNewScenarioModels();

	public List<HrScenario> loadDistinctScenarios();

	public List<SysProperties> loadScenarioSysProperties();

	public void saveScenario(HrScenario scenario);

	public void savePerfermanceCaracter(HrsCompactEmpCaracter hrsCompactEmpCaracter);

	public void deleteScenario(HrScenario scenarioItem);

	public List<BldPaperTypes> loadAllPaperTypes();

	public ArcPeople loadArcPeople(long nationalId);

	public Integer saveNewBuilding(BldLicNew newBuilding, String title, List<AttachmentModel> attachList)
			throws ParseException;

	public void saveNewBuildingWall(BldLicWall newBuilding, String title, List<AttachmentModel> attachList)
			throws ParseException;

	void saveAttachBuilding(BldLicAttch attachBuilding, String title, List<AttachmentModel> attachList)
			throws ParseException;

	public List<ArcUsers> getAllActiveEmployeesInDept(Integer departmentId);

	public BldLicNew getNewBuildingLicenceByArcRecordId(int recordId);

	public List<BldLicBuildingUsage> loadAllBuildingUsages();

	public List<LicAgents> loadAllEngineeringOffices();

	public void acceptNewBuilding(Integer recordId, BldLicNew newBuilding, List<AttachmentModel> attachList,
			boolean updateObject);

	public BldLicAttch getAttachBuildingLicenceByArcRecordId(int recordId);

	public BldLicWall getNewBuildingWallLicenceByArcRecordId(int recordId);

	public void acceptNewBuildingWall(int recordId, BldLicWall newBuilding, List<AttachmentModel> attachList);

	void acceptNewBuilding(BldLicNew newBuilding, List<AttachmentModel> attachList);

	public PayLicBills loadBillByLicNo(Integer licNewId);

	List<LicTrdMasterFile> getTrdMasterFileList();

	ReqFinesMaster checkLicLst(String licNo);

	void acceptAttachBuilding(Integer recordId, BldLicAttch attachBld, List<AttachmentModel> attachList,
			boolean updateObject);

	Map<String, Integer> getAllNamePenalities();

	List<ReqFinesMaster> findLstFinesMasterByLicId(String fLicenceNo);

	List<ReqFinesMaster> findLstFinesMasterByLicIdWithState(String fLicenceNo, Integer State);

	LicTrdMasterFile findLicByLicId(String fLicenceNo);

	ReqFinesMaster findFinesMasterByLicId(String fLicenceNo);

	ReqFinesDetails findFinesDetailsByFno(Integer fNo);

	public String printDocument(String repName, String parameter, String urlParam);

	public LicTrdMasterFile loadLicwnceByNumber(String licenceNumber);

	public List<User> getAllUsers();

	public List<ReqFinesMaster> loadAllPenalities(boolean notification);

	List<ReqFinesSetup> getCodesFines();

	List<ReqFinesDetails> findLstFinesDetailsByFineNO(Integer fNo);

	public int nationalIsFoundarcpeople(Long identifierNumber);

	public ArcPeople loadnamebynationid(Long identifierNumber);

	public Integer getNumberLicencePenality(Integer fineCode, Integer fineNo);

	List<ArcPeopleModel> loadArcPeoples();

	public List<LicTrdMasterFile> getTrdMasterFileListByPeriodType(Integer typeLicence, Integer period);

	public List<ArcAttachmentClass> findAttachmentFilesByLicenceId(Integer licId);

	public Integer billstatus(Integer integer);

	public Integer getCountBill(Integer Bill);

	public PayLicBills getBillbyBillNumber(Integer Bill);

	public List<PayBillDetails> getBillDetail(Integer Bill);

	public Integer replaceOldBillToNew(Integer Bill, int userId);

	public Integer getMaxBillNumber();

	Integer saveLicencePenalty(ReqFinesMaster reqFinesMaster, List<ReqFinesDetails> reqFinesDetailsList,
			boolean withBill);

	public Integer getCountBillByFineNo(Integer fineNo);

	public List<ReqFinesMaster> findListFinesMasterByNationalId(String nationalId);

	public List<BldLicHangover> loadAllRubbishCertificate();

	public BldLicHangover loadRubbishCertificate(Integer rubbishCertificateId);

	public void saveRubbishRemoveCertificate(BldLicHangover bldLicHangover, String title, PayLicBills newBill,
			List<PayBillDetails> billDetailList, List<AttachmentModel> attachList);

	Integer getRecIdMasterTBLByLicenceId(Integer licId);

	public ArcPeopleModel loadArcPeopleModel(long natioId);

	public long addArcPeople(ArcPeopleModel arcPeople) throws Exception;

	List<BldLicNewModel> loadLicNewList(long nationalId);

	public WrkComment loadLinkedArcRecord(String wrkIncomeNo);

	public List<HrsMasterFile> loadActiveEmpsHasSalary();

	public void updateRubbishRemoveCertificate(BldLicHangover bldLicHangover, List<PayBillDetails> billDetailList,
			List<AttachmentModel> attachList);

	List<HrsGovJobSeries> getHrsJobs();

	List<HrsGovJobType> getAllHrsGovJobTypes();

	public List<Sys037> loadAllJobCategories();

	public void SaveHrsGovWorks(HrsGovJobWrks hrsGovJobWrks);

	public List<HrsCompactFloors> getCatFloors();

	public List<HrsGovJob4> loadJobsByCategory(Integer categoryId, Integer rank);

	public List<HrsFloors> getFloors();

	public List<Sys112> loadAllDepartments();

	public List<HrsJobCreation> loadAllJobs();

	public List<Sys038> loadAllJobStatus();

	public void saveJob(HrsJobCreation job);

	public void updateJob(HrsJobCreation job, HrsJobCreation oldJob, Integer action);

	public List<HrsEmpHistorical> calculatePrimes();

	public HrsYearsPrime findYearPrime(Integer year);

	public void savePrimes(List<HrsEmpHistorical> primesList, Integer year);

	List<Sys037> getAllContractorsCategories();

	public List<HrsCompactEmpCaracter> getCompactEmpCaractersListByPerformId(Integer performId);

	List<HrsSalaryScale> loadAllJobRanks();

	public HrsJobHistorical loadEmployerLastJob(Integer empNo);

	public List<HrsCompactCatFloor> getAllCatFloors();

	// List<HrsMasterFile> loadEmpHasSalary();
	public void loadEmployerJobData(HrsCompactPerformance compactPerformance);

	public void calcSalary(int catid, int pmonth, int pyear);

	public List<Sys012> loadMonthsHijri();

	HrsCompactPerformance getHrsCompactPerformByArcID(Integer archRecordId);

	HrsCompactRating getHrsCompactRatingById(Integer ratingId);

	List<HrsCompactGoals> findHrsCompactPerformGoals(Integer performId);

	List<HrsCompactCatFloor> findHrsCompactPerformFloors(Integer performId);

	public HrsCompactPerformance getHrsCompactPerformByPerformanceID(Integer performanceId);

	public HrsGeneralAppreciation getHrsGeneralAppreciationByPerformanceID(Integer performanceId);

	Employer loadEmployerByUser(Integer empNo);

	List<HrsCompactFloors> getHrsCompactFloors(Integer hrsPerfomId, Integer jobType);

	public List<HrsCompactPerformance> loadNotEvaluatedPerformances();

	public void acceptModel(HrsCompactPerformance compactPerformance, List<HrsCompactFloors> hrsCommpactFloors,
			List<HrsCompactGoals> lstGoals, int modelType, String wrkAppComment, Integer applicationPurpose,
			Integer arcRecordId);

	public void acceptModelAppreciation(HrsGeneralAppreciation generalAppreciation, String wrkAppComment,
			Integer applicationPurpose, Integer arcRecordId);

	public HrsGeneralAppreciation getHrsGeneralAppreciationByArcID(Integer archRecordId);

	public List<HrsSalary> loadListSalary(int catid, Integer pmonth, int pyear);

	public List<HrsCompactBaseFloor> loadBaseFloors(Integer performanceId);

	public List<ArcUsers> getemployersinusrs();

	public List<HrsLoan> loadLoans(Integer empNB);

	public List<HrsMasterFile> getAllLoanEmployees();

	public List<HrsLoanDetails> loadLoanDetails(Integer loanId);

	void returnWrk(HrsCompactPerformance hrsCompactPerformance, Integer recordId,
			List<HrsCompactFloors> hrsCommpactFloors, List<HrsCompactGoals> lstGoals, String wrkAppComment,
			Integer applicationPurpose);

	HrsScenarioDocument getNextUser(Integer arcRecordId, Integer modelTypeId);

	void saveHrsCompactPerformance(HrsCompactPerformance hrsCompactPerformance, List<HrsCompactFloors> lstFloorModel,
			List<HrsCompactGoals> lstHrsCompactGoals, String title);

	void refuseCharterModel(HrsCompactPerformance compactPerformance, List<HrsCompactFloors> hrsCommpactFloors,
			List<HrsCompactGoals> lstGoals, int modelType, String wrkAppComment, Integer applicationPurpose,
			Integer recordId);

	List<HrsSalaryScale> loadAllSalayScales(Integer orderId);

	List<HrsSalaryScaleOrder> loadAllSalayScaleOrders();

	List<HrsSalaryScaleDgrs> loadAllSalayScaleDgrs(Integer orderId, Integer rankCode);

	public List<HrsLoanType> loadAllLoanTypes();

	public List<Sys012> loadAllMonths();

	public void saveLoan(HrsLoan loan);

	public void updateLoanDetails(List<HrsLoanDetails> loanDetailsList);

	void refuseAppreciation(HrsGeneralAppreciation generalAppreciation, String wrkAppComment,
			Integer applicationPurpose, Integer recordId);

	public List<ArcUsers> loadAllUsers();

	public List<SysTitle> loadAllTitles();

	public List<WrkSection> loadSectionsByDeptId(Integer deptId);

	public List<WrkJobs> loadJobsBySectionId(Integer wrkSectionId);

	public List<TstFinger> loadAllFingerByusersIdAndDate(String usersId, String DateStart, String DateFinish,
			Boolean allEmp, boolean higriMode);

	public TstFinger getFingerByUserId(TstFingerId userId);

	public List<FngTimeTable> loadTimeShift();

	public void saveNewUser(ArcUsers user);

	public WrkProfile loadProfileUser(Integer userId);

	public WrkProfileSign loadProfileSignUser(Integer userId);

	public void updateSignatures(WrkProfile wrkProfile, WrkProfileSign wrkProfileSign);

	public List<ArcPeople> loadAllArcPeoples();

	public ArcRecords getArcRecord(Integer arcRecordId);

	public List<HrsEmpOvertime> retriveOverTime(Integer catid, Integer frommonth, Integer tomonth, Integer pyear);

	public List<ArcUsers> employeersTree();

	public void saveOvertime(HrsEmpOvertime overtime);

	public List<HrsEmpOvertime> loadEmployerOvertimes(Integer empNB);

	public void updateOvertime(HrsEmpOvertime overtime);

	public List<HrsEmpHistorical> loadPromotionJobs(Integer employerNB);

	/////////////////////////// Mohamed Balable////////////////////////////////
	public List<EmpMoveType> loadMoveType();

	public List<InvestorType> loadAllInvestorType();

	public List<InvestorIdentityType> loadAllInvestorIdentityType();

	public List<InvestorStatus> loadAllInvestorStatus();

	public List<BuildingType> loadAllBuildingType();

	public List<ContractMainCategory> loadAllContractMainCategory();

	public List<ContractSubcategory> loadAllContractSubcategory();

	public List<ContractStatus> loadAllContractStatus();

	/////////////////////////////////////////////////////////////////////
	public HrsEmpHistorical loadJobCreationData(Integer jobId, Integer salaryBasic);

	public void savePromotion(HrsEmpHistorical empHistorical, HrsEmpHistorical newJob);

	public HrsEmpHistorical loadEmpHistorical(Integer employerNumber, String jobCode, Integer jobNumber,
			Integer rankNumber);

	public int checkPeriodVacation(Integer empNB, Integer userId);

	public List<FngTypeAbsence> loadAllTypeAbsence();

	public HrsGovJob4 loadHrsGovJob(String jobCode);

	public List<FngStatusAbsence> loadAllStatusAbsence();

	public List<FngEmpAbsent> loadAllFngEmpAbsent();

	public void saveAbsence(FngEmpAbsent empAbsent);

	public List<HrsEmpTerminate> loadAllEmpTerminates();

	public List<Sys059> loadAllTerminateReasons();

	public List<TstFinger> loadAllDetailFingerByusersIdAndDate(String usersIdList, String DateStart, String DateFinish,
			boolean higriMode);

	public List<Sys018> loadAllPayStatus();

	public List<Sys051> loadAllPayTypes();

	public void saveLeavingEmployer(HrsEmpTerminate empTerminate, HrsEmpHistorical empHistorical, HrsJobHistorical jobHistorical);

	public void exportSalaryFile(Integer categoryId, Integer year, Integer month, Integer type);

	public List<HrsUserAbsent> getAllAbsent(String lstUser, String startDate, String endDate);

	public void loadLetterReportParameters(Map<String, Object> parameters, String wrkId);

	public User getNameFromUserById(Integer userId);

	public List<ArcRecords> loadRecordsInDepartment(Integer deptId, Integer mode);

	public List<TradIssueType> loadTrdType();

	public void saveArcRecordsList(List<ArcRecords> recordsList, Integer deptId);

	List<UserMailObj> findEmployeeInboxNew(Integer start, Integer size, int vuser_id);

	public List<BillIssueDetail> loadBillIssue();

	public List<PayBillDetails> calctradingMarket(Integer tradType, Integer tradeMarketMainActivity,
			Double tradeMarketAdvArea, Double tradeMarketAddAdvArea, Double tradeMarketArea,
			Integer tradeMarketIssueYears);

	public List<PayBillDetails> calculatePetrolStationFees(Integer petrolStationIssueType, Double petrolStationAdvArea,
			Double petrolStationAddAdvArea, Integer petrolStationIssueYears, Double petrolStationArea, boolean b);

	public Double rubishValue(Integer activityType);

	public List<BillIssueDig> loadStreetDig();

	public List<BillIssueDigDetail> getTypeDigByCat(Integer streetGeneral);

	public DepartmentArcRecords getAttachNBByRecId(Integer recId);

	public List<PayBillDetails> calculateStreetDig(Double digline, Integer daysNumber, Integer streetType,
			Integer extendDay, Boolean isExtend);

	DashbordModel loadDasbordParams(Integer userId);

	public Map<Integer, User> loadUsers();

	public Map<Integer, MasterFile> loadMasterFiles();

	public List<ArcRecords> loadAllFromDepartmentRecords(Integer deptId);

	void loadNormalVacationNew(int empNB, String firstApplicationDate, String gregBirthDate, Integer userid,
			int currendDebit, HrEmployeeVacation employeeVacation);

	int getTotalVacationPeriod(int empNB, String firstApplicationDate);

	/**
	 * load departments of arc record
	 * 
	 * @return
	 */
	public List<RecDepts> getAllRecDepts();

	public String findReciverNameById(Integer deptId);

	public List<VacationModel> getUsersVacations(String lstUser, String startDate, Boolean allEmp);

	/**
	 * load records of deparments by deparment id and status
	 * 
	 * @param currDeptId
	 * @param status
	 * @return
	 */
	public List<DeptArcRecords> loadDeptRecords(Integer currDeptId, Integer status);

	/**
	 * save records printed of department
	 * 
	 * @param recordsList
	 */
	public void saveArcRecordsList(List<DeptArcRecords> recordsList);

	public User getUserById(Integer userId);

	public User getUserByEmpNO(int employerNumber);

	public List<UserRequest> loadUserRequests(Integer status, Integer userId, boolean isTecSupportRole);

	public List<InvNewspaper> loadNewspapers();

	public List<Investor> loadAllInvestors();

	public Map<Integer, RequestStatus> getUserReqStatus();

	List<Announcement> findAllAnnouncements();

	public List<RealEstate> loadAllRealEstates();

	public List<Contract> loadAllContracts();

	List<AnnoucementDetails> loadAnnoucementDetails(Integer AnnoucementId);

	public List<SiteType> loadAllSiteTypes();

	public List<ContractType> loadAllContractTypes();

	public List<Tender> loadTendersByAnnouncementDetailsId(Integer annDetailsId);

	public List<HrsMasterFile> getAllEmployeesActive();

	public List<TechnicalUsers> loadAllTechnicalUsers();

	public List<Clause> loadAllClauses();

	public void saveContract(Contract contract, List<String> selectedClausesList);

	public WrkComment findWrkCommentByCopyArcId(Integer copyArcId);

	public FngTimeTable loadTimeShiftByUserId(Integer userId);

	public String getAmountInLetters(String strSummTotal);

	void saveAnnouncement(Announcement announcement);

	public Tender loadSelectedTender(Integer realEstateId, Integer investorId);

	Map<Integer, AbsentModel> getHrsUserAbsents(Integer currentYear);

	public List<Investor> loadInvestorsByRealEstateId(Integer realEstateId);

	public List<AnnoucementDetails> loadAnnouncementDetailsByStatus(Integer[] status);

	public List<Car> loadAllCars();

	public List<CarBrand> loadAllCarBrands();

	public List<CarModel> loadCarModelByBrandId(Integer carBrand);

	public List<VehicleType> loadAllVehicleTypes();

	public List<FuelType> loadAllFuelTypes();

	public List<FuelTransaction> loadAllFuelTransactions();

	void correctUserFngs(Integer userId, String startDate, String endDate);

	public List<UserCars> loadUserCarsByUserId(Integer employerId);

	public List<UserCars> loadAllAllocatedUserCars();

	public List<Car> loadNotAllocatedCarsByModelId(Integer carModelId);

	public List<FuelTransaction> loadFuelTransactions(Date startDate, Date endDate);

	public UserCars findCarByNumDoor(Integer numDoor);

	public FuelTransaction findLastFuelTransaction(Integer userCarId);

	public List<ContractDirect> loadAllContractDirects(Integer contractTypeId);

	public void saveContractDirect(ContractDirect contractDirect);

	public List<FuelSupply> loadAllFuelSupplies();

	public FuelSupply loadLastFuelSupply(Integer fuelTypeId);

	public List<IntroContract> loadAllIntroContracts();

	public void selectTender(List<Tender> tendersList);

	void sendListRecordTo(List<UserMailObj> checkedMailsList, Integer userid);

	public List<AnnoucementDetails> loadAnnouncementDetailsHavingTenders(Integer[] status);

	public Tender loadAssignedTenderByAnnouncementDetailsId(Integer annoucementDetailsId);

	// void saveContractBills(PayLicBills payLicBill, ContractBills
	// contractBills,Integer billNumber);
	public List<ContractCancelReason> loadAllContractCancelReasons();

	public List<ArcPeople> loadArcPeopleFields();

	public List<NotifFinesMaster> loadAllNotifPenalities(Integer status);

	public List<ContractDirectType> loadAllContractDirectTypes();

	public List<PayLicBills> loadContractBills(Integer contractId, String contractType);

	public void updateBillContract(PayLicBills bill);

	public List<LicTrdMasterFile> loadLicences();

	public List<ReqFinesSetup> loadFineSetupBySection(Integer sectionId);

	public void acceptPenalityAndBill(ReqFinesMaster reqFinesMaster);

	public void updateNotifFineMaster(NotifFinesMaster notifFinesMaster);

	public Map<Object, Number> loadArcRecordsNBForCurrentYear(Integer userId, Integer deptId);

	public void saveEmplyerVacStock(VacCompensatoryDays vacCompensatoryDays, CompensatoryVacStock compensatoryVacStock);

	public void saveEmplyerVacStock(VacCompensatoryDays vacCompensatoryDays,
			List<CompensatoryVacStock> compensatoryVacStock);

	public List<CompensatoryVacStock> getAllUsersCompVacStock();

	//////////////////////// Balabel /////////////
	public List<HrsVacSold> getAllHrsVacSold();
	//////////////////////////////////////////////

	public WhsWarehouses loadStoreIdById(Integer storeId);

	boolean addUserRequest(UserRequest reuest, RequestStep reqSteps);

	public int getTotalVacationSold(int empNB);

	public List<DocumentsType> getAllDocumentsType();

	public String createIncomeNo();

	public void addtransAttachs(Integer arcRecordId, Integer arcCopyRecordId, List<AttachmentModel> attachList)
			throws Exception;

	public void wrkAppTransfert(WrkApplication application, WrkApplicationId wrkId, boolean secretFlag,
			List<Integer> WrkCopyEmpRecievers, List<Integer> WrkCopyMngRecievers, List<AttachmentModel> attachList)
			throws Exception;

	public List<WrkFinesEntity> getAllWrkFinesEntity();

	ArcRecords findRecordByOutComeNo(int outcomeNumber);

	/////////////// Balabel//////////////////
	public AutorizationSettings loadAutorization();

	public List<Project> getAllProjects();

	public List<ProjectContract> getallProjectContract();

	public List<FinEntity> getAllfinEntitys();

	////
	public HrsVacSold loadHrsVacSoldById(Integer id);

	void loadNormalVacationNew(int empNB, String firstApplicationDate, String gregBirthDate, Integer userid,
			int currendDebit, HrEmployeeVacation employeeVacation, HrsVacSold hrsVacSoldList);

	void acceptVacation(WrkApplicationId wrkApplicationId, HrEmployeeVacation employeeVacation, int vacationType,
			HrsVacSold hrsVacSold);

	public List<HrsUserAbsent> getAuthorizationsHoursNumber(int userId, String month, String year);

	public List<HrsUserAbsent> getAllEmpApsent(int userId);

	public Article getArticleById(Integer id);

	public ArcUsers findUserById(int userId);

	public List<CompensatoryVacStock> getUserCompensatoryVacStockByEmpNo(Integer empNO, Integer compVacType);

	// get all emps in Selected Depts (ARC_USER)
	public List<User> getAllEmployeesInSelectedDepts(List<String> deptIds);

	// delete FngUserTempShift by id and work date
	public void deleteFngUserTempShiftById(Integer userId, Date workDate);

	// to get time shift by timeshiftId
	public FngTimeTable getTimeShiftById(Integer timeShiftId);

	// to get time shifts for emplyee by userId and workDate
	public List<FngUserTempShift> getEmployeeShiftsById(Integer userId, Date workDate);

	public WrkDept getDeptById(Integer deptId);

	public List<StoreRequestModel> ListStoreRequestModel(int strNo);

	public MemoReceiptModel getMemoReceiptDetails(Integer memo_receipt_id);

	public String convertNumberToLiteralWords(Float number);

	public List<StockEntryMaster> searchMemoReceipts(String beginDate, String finishDate, Integer strNo);

	public List<InventoryMaster> getInventoriesByStrNo(Integer strNo);
	public List<Article> getAllArticles();
	public void addJobToHistory(HrsJobCreation job, HrsJobCreation oldJob, Integer actionNB) ;
	public HrsJobHistorical	loadLastHistoricalJob(Integer jobNumber, String jobCode);
//	public HrsJobHistorical gethrsjobHistorical(Integer employerNB);

	public HrsJobCreation loadJobCreation(Integer jobNumber, String jobCode);

	public void saveOperation(DeputationTraining dep_tr);

	public void exportDepTrainFile(Integer emp_no, Integer month, Integer year);

	List<DeputationTraining> loadStatus(Integer emp_number);

	List<DeputationTraining> loadStatus(Integer emp_number, Integer month, Integer year);

	void saveReward(RewardInfo rewardinfo);

	List<RewardInfo> loadRewards(Integer emp_number);

	void exportRewardFile(Integer emp_no, Integer month, Integer year);

	List<RewardInfo> loadRewards(Integer emp_number, Integer month, Integer year);
	
	public Integer getIdFromWorkAppByAppId(Integer appId);

	List<WrkLetterFrom> loadAllWrkLetterFrom();

	List<WrkLetterTo> loadAllWrkLetterTo();

	List<WrkPurpose> loadAllPurposes();

	List<FngTypeAbsence> loadAllAbsenceTypes();

	List<FngStatusAbsence> loadAllAbsenceStatus();

	List<SysCategoryEmployer> loadAllCategoryEmployers();

	List<VacationsType> loadAllVacationTypes();

	List<WrkCommentType> loadAllCommentTypes();

	public List<LoanModel> loadUsersLoan(Integer year, Integer month, Integer type);

	public List<RetirementModel> loadRetirement();

	public List<HRCountry> getAllCountry();

	public List<HRAbsence> getAllAbsence();

	public List<HRStudyType> getAllStudyType();

	public List<HRPhoneType> getAllPhoneType();

	public List<HREmpRanks> getAllEmpRanks();

	public List<HREmpCat> getAllEmpCat();

	public List<HRLawSentence> getAllLawSentence();

	public List<HRCourse> getAllCourseTypes();

	public List<HRSubjectStatus> getAllSubjectStatus();

	public List<HRBlood> getAllBloodTypes();

	public List<HRNationality> getAllNationalities();

	public List<HRMarStatus> getAllMarStatus();

	public List<HRPositionAction> getAllPositionAction();

	public List<HRTitles> getAllTitles();

	public List<HRQlfSpeciality> getAllQlfSpeciality();

	public List<HRQlfMajors> getAllQlfMajors();

	public List<HRQlfTypes> getAllQlfTypes();

	public List<HROrgTypes> getAllOrgTypes();

	public List<HRCity> getAllCities();

	public List<HRArea> getAllAreas();

	public List<HRContacts> getAllContacts();

	public List<HRReligion> getAllReligions();

	public List<HrsEmpHistorical> findAllHrsEmpHistorical();

	public List<HrsEmpHistorical> findEmpHistoricalByEmpNo(Integer empNO);
	
}
