package com.bkeryah.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.security.core.AuthenticationException;

import com.bkeryah.entities.ArcApplicationType;
import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.ArcDocumentStruct;
import com.bkeryah.entities.ArcPeople;
import com.bkeryah.entities.ArcPeopleModel;
import com.bkeryah.entities.ArcPeoplePic;
import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.ArcRecordsLink;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.ArticleGroup;
import com.bkeryah.entities.ArticleSubGroup;
import com.bkeryah.entities.BillIssueCash;
import com.bkeryah.entities.BillIssueDetail;
import com.bkeryah.entities.BillIssueDig;
import com.bkeryah.entities.BillIssueDigCash;
import com.bkeryah.entities.BillIssueDigDetail;
import com.bkeryah.entities.BillIssueRubish;
import com.bkeryah.entities.Charging;
import com.bkeryah.entities.DepartmentArcRecords;
import com.bkeryah.entities.DeptArcRecords;
import com.bkeryah.entities.DeputationTraining;
import com.bkeryah.entities.DocumentScenario;
import com.bkeryah.entities.DocumentsType;
import com.bkeryah.entities.EmployeeInitiation;
import com.bkeryah.entities.FinEntity;
import com.bkeryah.entities.FinFinancialYear;
import com.bkeryah.entities.HrEmployeeVacation;
import com.bkeryah.entities.HrScenario;
import com.bkeryah.entities.HrsEmpHistorical;
import com.bkeryah.entities.HrsEmpTerminate;
import com.bkeryah.entities.HrsGovJob4;
import com.bkeryah.entities.HrsJobHistorical;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.HrsSalaryScale;
import com.bkeryah.entities.HrsSalaryScaleDgrs;
import com.bkeryah.entities.HrsSalaryScaleId;
import com.bkeryah.entities.HrsScenarioDocument;
import com.bkeryah.entities.HrsTrainingMandate;
import com.bkeryah.entities.HrsTrainingPlace;
import com.bkeryah.entities.HrsUserAbsent;
import com.bkeryah.entities.InventoryMaster;
import com.bkeryah.entities.InventoryRecord;
import com.bkeryah.entities.ItemUnite;
import com.bkeryah.entities.MainMenu;
import com.bkeryah.entities.MasterFile;
import com.bkeryah.entities.NationalIdPlaces;
import com.bkeryah.entities.NationalIdType;
import com.bkeryah.entities.Nationality;
import com.bkeryah.entities.PayBank;
import com.bkeryah.entities.PayBillDetails;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.PayMaster;
import com.bkeryah.entities.Project;
import com.bkeryah.entities.ProjectContract;
import com.bkeryah.entities.RecDepts;
import com.bkeryah.entities.RewardInfo;
import com.bkeryah.entities.SubMenu;
import com.bkeryah.entities.SysCategoryEmployer;
import com.bkeryah.entities.SysProperties;
import com.bkeryah.entities.SysTitle;
import com.bkeryah.entities.TechnicalResponse;
import com.bkeryah.entities.TechnicalUsers;
import com.bkeryah.entities.TenderItems;
import com.bkeryah.entities.TradIssueType;
import com.bkeryah.entities.UserRoles;
import com.bkeryah.entities.VacationsType;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkComment;
import com.bkeryah.entities.WrkCommentType;
import com.bkeryah.entities.WrkDept;
import com.bkeryah.entities.WrkInboxFolder;
import com.bkeryah.entities.WrkJobs;
import com.bkeryah.entities.WrkLetterFrom;
import com.bkeryah.entities.WrkLetterTo;
import com.bkeryah.entities.WrkProfile;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.entities.WrkRefrentionalSetting;
import com.bkeryah.entities.WrkRoles;
import com.bkeryah.entities.WrkSection;
import com.bkeryah.entities.WrkUserFolderMail;
import com.bkeryah.entities.investment.AnnoucementDetails;
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
import com.bkeryah.hr.entities.HrsCompactGoals;
import com.bkeryah.hr.entities.HrsCompactPerformance;
import com.bkeryah.hr.entities.HrsEmpOvertime;
import com.bkeryah.hr.entities.HrsFloors;
import com.bkeryah.hr.entities.HrsGeneralAppreciation;
import com.bkeryah.hr.entities.HrsGovJobSeries;
import com.bkeryah.hr.entities.HrsGovJobType;
import com.bkeryah.hr.entities.HrsJobCreation;
import com.bkeryah.hr.entities.HrsLoan;
import com.bkeryah.hr.entities.HrsLoanDetails;
import com.bkeryah.hr.entities.HrsLoanType;
import com.bkeryah.hr.entities.HrsSalary;
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
import com.bkeryah.model.AbsentModel;
import com.bkeryah.model.LoanModel;
import com.bkeryah.model.RetirementModel;
import com.bkeryah.model.User;
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

/**
 * @author idarwiesh
 *
 */

public interface ICommonDao {
	// get all users in the system
	public List<ArcUsers> findAllUsers();

	// upload file to remote server
	public String addArcRecordAttachment(InputStream inputstream, String fileName, String arcRecordId, int userId);

	// create pope file from properties file
	public Properties createPopertiesFile(String filePath);

	// get user entity by user_id
	public ArcUsers findUserById(int userId);

	// findAttach by attach ID
	public ArcAttach findattachById(int attachId);

	// find record by ARC_RECORD (ID)
	public ArcRecords findRecordById(int recordId);

	// get all file types
	public List<Object> findAll(Class typeClass);

	// get Entity Object by ID
	public Object findEntityById(Class entityClass, int EntityId);

	// find attachment List by arc_record id
	public List<ArcAttach> findAttachmentList(int arcRecordId);

	// find wrk_application by arc_record id ....
	public List<WrkApplication> findAllWrkApplicationById(Integer wrkApplicationId);

	// Save Any Object Object
	public Integer save(Object myObject);

	public Integer saveInitaionAftrWorl(EmployeeInitiation employeeInitiation) throws VacationAndInitException;

	public void update(Object myObject);

	public void update(Object object1, Object object2);

	public void update(Object object1, Object object2, Object object3);

	public Object saveObject(Object myObject);

	// get New ARC_RECORD income number
	public Integer createOutcomeNo();

	// find user current section
	public Integer findUserSection(int userId);

	// find next ID for wrk_application record
	public int createWrkId();

	// check next record step in wrk application
	public int loadApplicationStepId(int recordId);

	// mark wrk_comment
	public int markLetter(int wrkAppId, int userId);

	// sign wrk_comment
	/**
	 * @param wrkAppId
	 *            app id letter id
	 * @param userId
	 *            user that sign letter
	 * @param signType
	 *            A,S,N >> A ammen reference Sign N sign with his manager
	 *            privalages S sign with his privialges
	 * @return
	 */
	public void saveAttachment(InputStream inputStream, String att_name);

	public int signLetter(int pwrkAppId, int puserId, String psignType);

	// public String readQuery(String QueryId);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// public ArcUsers login(String username, String password);

	public ArcUsers loadUser(final String username, final String password) throws AuthenticationException;

	public int getHrsSignNextStep(int arcRecord);

	public HrsUserAbsent loadHrsUserAbsentByRecordId(int arcRecordId);

	WrkApplication findWrkApplicationById(WrkApplicationId wrkApplicationId);

	public String getPropertiesValue(String name);

	// get all purposes
	public List<WrkPurpose> getAllPurposes();

	// get all types of comment
	public List<WrkCommentType> getCommentTypes();

	// get all emps in Dept (ARC_USER)
	public List<User> getAllEmployeesInDept(Integer deptId);

	// get all emps in Dept (HRS_MASTER_FILE)
	public List<HrsMasterFile> getAllEmployeesByDeptId(Integer deptId);

	// get all emps
	public List<ArcUsers> getAllEmployees();

	// get all managers
	public List<ArcUsers> getAllManagers();

	// save list of attachment
	public List<Integer> addAttachments(List<ArcAttach> attachs);

	// get all managers from WrkRefrentionalSetting table
	public List<WrkRefrentionalSetting> getAllManagersFromWFS();

	public ArcRecords getrecordById(Integer recordId);

	public WrkApplicationId saveWrkApp(WrkApplication app, Integer recordId);

	public Integer getUserDeptJob(int managerId);

	public WrkApplication getWrkApplicationRecordById(int wrkApplicationId);

	public ArcRecords getArchRecordById(int archRecordId);

	public WrkComment getWrkCommentByAppId(int WrkCommentId);

	public List<ArcAttach> getAttachmentByArchRecordId(int archRecordId);

	// public List<ArcAttach> getAttachmentById(List<Ar>);
	public EmployeeInitiation getInitiationById(Integer initiationId);

	public HrEmployeeVacation getVacationById(Integer vacationId);

	public Integer getLastVacationForCurrentId(Integer employeeNum);

	public int getDocIdHrsSignByRecId(int arcRecordId);

	public Integer getEmployerNumberHrsAbsent(int autorizationId);

	public HrsEmpHistorical getHRsEmpHistoricalByEmpNo(int employerNumber);

	public ArcUsers loadUserByEmpNO(int employerNumber);

	public List<MainMenu> findUserMenus(int puserId);

	public List<SubMenu> _findSubMenus(int puserId, int mainMenuId);

	HrsSalaryScale findHrsSalaryScaleById(Class entityClass, HrsSalaryScaleId EntityId);

	public int findMaxOrderIdRank();

	int getEmpNoHrsVction(int vacationId);

	public int findForcedVacationScore(int empNB);

	public void updateWrkApplicationVisible(WrkApplicationId wrkApplicationId);

	public Integer getCountAccept(Integer arcRecordId);

	void updateVacationAfterInit(Integer employeeVacationId);

	public Map<String, Integer> getEmployeeInfo();

	public Map<String, Integer> getEmployeeInDeptInfo();
	// Integer checkEmployerIsTerminated(Integer employerNumber);

	/**
	 * get execution date of terminate employee service
	 * 
	 * @param employerNumber
	 * @return
	 */
	public String getExecutionDateTermnate(Integer employerNumber);

	public List<PayBank> getAllBanks();

	// public HrsEmpHistorical getEmployeeLastHistoryRecord(Integer id);

	public int getUsedTotalVacationPeriod(int empNB);

	public int getUsedNormalYearlyPeriod(int empNB);

	public HrEmployeeVacation loadLastNormalVacation(int empNB);

	public String getLastServiceDate(Integer empNB);

	public boolean checKIsManager(int userId);

	public Integer getUserIdFromWorkAppByIdAndStepId(Integer recordId, Integer stepId);

	public List<HrsTrainingPlace> loadTrainingPlaces();

	public HrsTrainingMandate loadTrainingMandate(int rank);

	public HrsScenarioDocument getDestinationModel(int modelId, int stepId);

	public List<Integer> getAuthorizedEmployeesForModel(int modelId);

	int getEmpNoTraining(int trainId);

	String getPropertiesValueById(int id);

	public EmployeeInitiation getLastEmployeeInitiation();

	public void injectFactory(Object sessionFactory);

	public Long getOutBoxmailsNumbers(Integer userId);

	public Long getInboxNumbers(Integer userId);

	public Long getUnReadMails(Integer userId);

	public int getAuthorizationsNumber(int userId, String month, String year);

	public Long getUnReadMailsForToday(Integer userId);

	public Long getNormalVacationCount(Integer empNumber);

	public Long getExcuseVacationCount(Integer empNumber);

	public String getHashedPassword(String userName, String password);

	// public String updatePassword(String userName, String password, String
	// newPassword) throws Exception;
	public void updateUserPasswordForSystem(Integer userId, String hashedNewPassword) throws Exception;

	public void updateUserPasswordForSign(Integer userId, String hashedNewPassword) throws Exception;

	public ArcUsers getUserNameById(Integer userId) throws Exception;

	public List<WrkRoles> getallRoles() throws Exception;

	public List<PayMaster> loadAllPayMasters();

	public List<Charging> getallCharging();

	public List<NationalIdType> getAllNationalIdTypes();

	public void deletePayBillDetails(PayBillDetails selectedItem);

	public int findMaxBillId();

	public int findMaxHrsCompactPerformance();

	public int findMaxHrsFloorsBase();

	public int findMaxHrsFloors();

	public List<Nationality> getallNationalities();

	public List<NationalIdPlaces> getallNationalIdPlaces();

	public Long saveObjectReturnLong(Object myObject) throws Exception;

	public List<Project> getallProjects();

	public List<ProjectContract> getallProjectContract(Integer projectId);

	public List<DocumentScenario> findScenariosByModelId(int pmodelId);

	public List<PayLicBills> loadAllBills();

	public List<PayBillDetails> loadBillDetails(Integer billNumber);

	public int findLicApplicationIdByRecord(int arcRecordId);

	public ArcPeople findArcPeopleById(long nationalId);

	public List<TenderItems> getAllTenderItems();

	public ArcPeoplePic findArcPeoplePicById(Long nationalId);

	List<WrkApplication> getWrkApplicationListByRecordId(Integer archRecordId);

	List<TechnicalResponse> getTechnicalResponsesByRecordId(Integer archRecordId);

	Integer getArchRecParentFromLinkByRecordId(Integer childArchRecordId);

	int getNextArcId();

	public void refresh(Object myObject);

	Integer getWrkIdByArc(int arcRecordId);

	List<ArcRecordsLink> getChildsObjectsByParentRecordId(Integer parentArchRecordId);

	List<WhsWarehouses> getAllStores();

	List<FinFinancialYear> getAllFinYears();

	Integer getArcRecordParent(int arcRecordId);

	public boolean isIncomeExist(Integer incomeNumber);

	public int findEmployeeInboxCount(Integer userId);

	public List<WrkInboxFolder> getAllfoldersForUser(int userId);

	public void addFolderForUser(WrkInboxFolder folderInfo);

	public void addMailsToFolder(List<WrkUserFolderMail> mails);

	public List<WrkUserFolderMail> getAllaMailsInFolder(int folderId);

	public int getFolderNameForWrkApp(int stepId, int wrkId);

	public Object addMailToFolder(WrkUserFolderMail folderMail);

	WrkUserFolderMail getFolderMailInfoByMailIdStepNum(int stepId, int wrkId);

	void deleteMailFomFolder(WrkUserFolderMail folderMail);

	public List<ArcRecords> searchArcRecords(String subject, String letter, Integer recordSender, Integer incomeNB,
			String letterFromNo, List<Integer> selectedStructIdList, boolean employer);

	public List<WrkSection> loadArcDocumentStructList();

	public Connection findConnection();

	int createArcRecordsId();

	public List<EmployeeForDropDown> getSpecialEmployeeList();

	ArcUsers getUserByLoginName(String userName);

	int getWrkAppFromID(int arcRecord);

	public List<WrkDept> findAllDepartments();

	void addNewArticleGroup(ArticleGroup articleGroup);

	List<ArticleGroup> getAllArticleGroups();

	void addNewArticleSubGroup(ArticleSubGroup articleSubGroup);

	List<ArticleSubGroup> getAllArticleSubGroups();

	void addNewArticle(Article article);

	void addNewPromotion(HrsEmpHistorical oldEmpHistorical, HrsEmpHistorical newEmpHistorical2);

	List<Article> getAllArticles(Integer strNo);

	List<ItemUnite> getAllUnites();

	List<Integer> getAttachmentIdsByRecordId(int arcRecordID);

	public WrkApplication findRecordLastStep(int recordId);

	public List<ArticleSubGroup> getAllArticleSubGroupsByGroupId(int groupId);

	public List<InventoryRecord> getAllInventoryByArticleId(int articleId, Integer inventoryId);

	public List<ArcUsers> loadTechnicalUsers();

	public List<TechnicalUsers> loadNewTechnicalUsers();

	public void deleteObject(Object selectedItem);

	public ArcRecords findRecordByIncomeNumber(int incomeNumber);

	List<HrsMasterFile> loadHrsMasterEmployers();

	List<HrEmployeeVacation> loadVacationInfo(Integer employerId, Integer vacationid);

	public List<VacationsType> loadVacationsType();

	public List<ArcPeople> loadnameEmployerbynationid(Long nationId);

	public List<HrsJobCreation> loadHrJob(Integer catid, Integer status, Integer rank);

	public List<Integer> findAllOldIncomes(int incomeNumber);

	public WrkComment findWrkCommentByAppNo(String appNum);

	public boolean recordIslinked(int currentIncome);

	public ArcRecordsLink getArcLinkByRecordId(Integer archRecordId);

	public Integer loadScoreSickVacation(Integer empNo);

	public HrsJobCreation loadSelecredJob(Integer createId, Integer status);

	public List<HrsSalaryScaleDgrs> loadScaleDgree(Integer rankCode);

	public HrEmployeeVacation loadLastVacation(Integer empNB, int sickVacation);

	public Integer findLicTrdByArcrecordId(Integer arcRecordId);

	List<FinEntity> loadSupliers();

	List<UserRoles> loadAllRoles();

	List<SysProperties> loadAllSysProperties();

	public Integer getMaxEmployerNumber(String firstApplicationDate, Integer cactegoryId);

	public int deleteUserRole(Integer userId, Integer roleId);

	public Charging getChargingemployer(Integer userId);

	List<UserRoles> loadUserRoles(Integer userId);

	public int getMaxSerialJobHistorical(String jobcode, Integer jobNumber);

	public int nationalIsFound(Long nationalNumber, Integer cat);

	public HrsMasterFile loadEmployerData(Integer empno);

	public HrsEmpHistorical loadEmployerDataHistorcial(Integer empno);

	HrsSalaryScaleDgrs loadSelectedDegree(Integer dgreeId);

	HrsSalaryScaleDgrs loadSelectedDegree(Integer classCode, Integer rankCode);

	Integer foundCountEmp(String firstApplicationDate, Integer cactegoryId);

	public Integer getCreatedId(String jobcode, Integer jobNumber);

	List<ArcApplicationType> loadNewScenarioModels();

	List<SysProperties> loadScenarioSysProperties();

	List<HrScenario> loadDistinctScenarios();

	public List<BldPaperTypes> loadAllPaperTypes();

	public int saveNewBuilding(BldLicNew newBuilding);

	int saveNewBuildingWall(BldLicWall newBuilding);

	int saveAttBuilding(BldLicAttch attchBuilding);

	public List<ArcUsers> getAllActiveEmployeesInDept(Integer departmentId);

	public List<BldLicBuildingUsage> loadAllBuildingUsages();

	public List<LicAgents> loadAllEngineeringOffices();

	public void deleteNewBuildingPieces(Integer licNewId);

	public List<ReqFinesMaster> findLstFinesMasterByLicId(String fLicenceNo);

	public List<ReqFinesMaster> findLstFinesMasterByLicIdWithState(String fLicenceNo, Integer fFineCase);

	public LicTrdMasterFile loadlicenceByNumber(String licenceNumber);

	LicTrdMasterFile findLicByLicId(String fLicenceNo);

	List<User> getAllUsers();

	public List<ReqFinesMaster> loadAllPenalities(boolean notification);

	List<ReqFinesSetup> getCodesFines();

	ReqFinesMaster findFinesMasterByLicId(String fLicenceNo);

	ReqFinesDetails findFinesDetailsByFno(Integer fNo);

	List<ReqFinesDetails> findLstFinesDetailsByFineNO(Integer fNo);

	PayLicBills loadBillByLicNo(Integer licNewId);

	public Integer getNumberLicencePenality(Integer fineCode, Integer fineNo);

	public int nationalIsFoundarcpeople(Long identifierNumber);

	public ArcPeople loadnamebynationid(Long identifierNumber);

	List<ArcPeopleModel> loadAllPeoples();

	public Integer getCountBill(Integer newBill);

	public PayLicBills getBillbyBillNumber(Integer bill);

	public List<PayBillDetails> getBillDetail(Integer bill);

	public Integer getMaxBillNumber();

	Integer getMaxFineMAsterId();

	public Integer getRecordIdFromHrsSignByDocId(Integer docId, Integer modelId);

	public Integer getCountBillByFineNo(Integer fineNo);

	public List<ReqFinesMaster> findListFinesMasterByNationalId(String nationalId);

	public List<BldLicHangover> loadAllRubbishCertificate();

	public int saveRubbishCertificate(BldLicHangover bldLicHangover);

	Integer getRecIdMasterTBLByLicenceId(Integer licId);

	ArcPeopleModel loadArcPeopleModel(long nationalId);

	public Integer loadOldIncomeNoFromLinking(String wrkIncomeNo);

	public List<HrsMasterFile> loadEmpsActive();

	List<HrsGovJobSeries> getHrsJobs();

	List<HrsGovJobType> getAllHrsGovJobTypes();

	public List<Sys037> loadAllJobCategories();

	public List<HrsCompactCatFloor> getfCatFloors();

	public List<Sys112> loadAllDepartments();

	public List<HrsFloors> getFloors();

	public List<HrsJobCreation> loadAllJobs();

	public List<Sys038> loadAllJobStatus();

	public HrsJobHistorical loadLastHistoricalJob(Integer jobNumber, String jobCode);

	public HrsYearsPrime findYearPrime(Integer year);

	public void disableLastFlags();

	public Integer loadSerialEmpHistorical(Integer empNo);

	public List<HrsGovJob4> loadJobsByCategory(Integer categoryId, Integer rank);

	public List<Sys037> getAllContractorsCategories();

	public HrsJobHistorical loadEmployerLastJob(Integer empNo);

	public List<HrsCompactCatFloor> getAllCatFloors();

	public List<HrsMasterFile> loadEmpSalary();

	public List<Sys012> loadMonthsHijri();

	List<HrsCompactGoals> findHrsCompactPerformGoals(int performId);

	List<HrsCompactCatFloor> findHrsCompactPerformFloors(int performId);

	HrsGeneralAppreciation findHrsGeneralAppreciationByPerformId(int performId);

	public List<HrsCompactPerformance> loadNotEvaluatedPerformances();

	public Integer getRatingByValue(int result);

	List<HrsCompactEmpCaracter> findAllHrsCompactEmpCaracByPerformId(int performId);

	public List<HrsSalary> loadListSalary(int catid, Integer pmonth, int pyear);

	public List<HrsCompactBaseFloor> loadBaseFloors(Integer performanceId);

	public List<ArcUsers> getemployersinusrs();

	public List<HrsLoan> loadLoans(Integer empNB);

	public List<HrsMasterFile> getAllLoanEmployees();

	public List<HrsLoanDetails> loadLoanDetails(Integer loanId);

	WrkApplication getWrkApplication(Integer wrkId);

	List<HrsSalaryScale> loadAllSalayScales(Integer orderId);

	List<HrsSalaryScaleDgrs> loadAllSalayScaleDgrs(Integer orderId, Integer classCode);

	public List<HrsLoanType> loadAllLoanTypes();

	public List<Sys012> loadAllMonths();

	public List<HrsAppreciationScale> findAppreciationScalesByFloorId(Integer floorId);

	public List<SysTitle> loadAllTitles();

	public List<WrkSection> loadSectionsByDeptId(Integer deptId);

	public List<WrkJobs> loadJobsBySectionId(Integer wrkSectionId);
	// public List<TstFinger> loadAllFingerByusersIdAndDate(List<Integer>
	// usersIdList, String DateStart, String DateFinish);

	public List<FngTimeTable> loadTimeShift();

	public ArcRecords getArcRecord(Integer arcRecordId);

	public WrkProfile loadProfileUser(Integer userId);

	public List<ArcPeople> loadAllArcPeoples();

	public List<HrsEmpOvertime> loadEmployerOvertimes(Integer empNB);

	public List<HrsEmpOvertime> retriveOverTime(Integer catid, Integer frommonth, Integer tomonth, Integer pyear);

	public List<HrsEmpHistorical> loadPromotionJobs(Integer employerNB);

	/////////////////////////// Mohamed Balable//////////////////////////
	public List<EmpMoveType> loadEmpMoveType();

	public List<InvestorType> loadInvestorType();

	public List<InvestorIdentityType> loadInvestorIdentityType();

	public List<InvestorStatus> loadInvestorStatus();

	public List<BuildingType> loadBuildingType();

	public List<ContractMainCategory> loadContractMainCategory();

	public List<ContractSubcategory> loadContractSubcategory();

	public List<ContractStatus> loadContractStatus();

	//////////////////////////////////////////////////////
	public HrsSalaryScaleDgrs loadSalaryScale(Integer rankCode, Integer basicSalary, int id);

	public HrsEmpHistorical loadEmpHistorical(Integer employerNumber, String jobCode, Integer jobNumber,
			Integer rankNumber);

	public int checkPeriodVacation(Integer empNB, Integer userid);

	public HrsGovJob4 loadHrsGovJob(String jobCode);

	public List<HrsEmpTerminate> loadAllEmpTerminates();

	public Integer getMaxEmpNoWorkers();

	public List<Sys059> loadAllTerminateReasons();

	public List<Sys018> loadAllPayStatus();

	public List<Sys051> loadAllPayTypes();

	public int loadMaxID();

	public User getNameFromUserById(Integer userId);

	public List<ArcRecords> loadRecordsInDepartment(Integer deptId, Integer mode);

	public List<TradIssueType> loadTrdType();

	List<String> loadSalaryData(Integer year, Integer month, Integer type);

	public List<BillIssueDetail> loadBillIssue();

	public List<BillIssueCash> loadBillCash(int issueCategory, Integer activityType, Double advArea);

	public List<BillIssueRubish> loadBillCashRubish(int issueCategory, Integer activityType);

	public List<BillIssueDig> loadStreetDig();

	public List<BillIssueDigDetail> getTypeDigByCat(Integer streetGeneral);

	public List<BillIssueDigCash> loadBillCashStreetDig(int issueCategory, Integer streetType);

	public List<MasterFile> getAllMasterFiles();

	public TstFinger getFingerDigByUserId(TstFingerId userId);

	public int loadVacationSumLastYear(int empNB);

	public HrsSumVacation listsumbyEmp(int empno);

	/**
	 * load departments of records
	 * 
	 * @return
	 */
	public List<RecDepts> getAllRecDepts();

	public DepartmentArcRecords getAttachNBByRecId(int recId);

	public String findReciverNameById(int reciverId);

	public List<DeptArcRecords> loadDeptRecords(Integer currDeptId, Integer status);
	// public Set<User> loadUsers();

	public User getUserByEmpNO(int employerNumber);

	public List<InvNewspaper> loadAllNewspapers();

	List<RequestStatus> getUserReqStatus();

	public List<Investor> loadAllInvestors();

	List<UserRequest> loadUserRequests(Integer status, Integer userId, boolean isTecSupportRole);

	public List<RealEstate> loadAllRealEstates();

	public List<Contract> loadAllContracts();

	public List<SiteType> loadAllSiteTypes();

	List<AnnoucementDetails> loadAnnoucementDetails(Integer AnnoucementId);

	public List<ContractType> loadAllContractTypes();

	public List<Tender> loadTendersByAnnouncementDetailsId(Integer annDetailsId);

	public List<HrsMasterFile> getAllEmployeesActive();

	public List<TechnicalUsers> loadAllTechnicalUsers();

	public List<Clause> loadAllClauses();

	public FngTimeTable loadTimeShiftByUserId(Integer userId);

	public String getAmountInLetters(String amount);

	void deleteAnncementNews(Integer announcementId);

	public Tender loadSelectedTender(Integer realEstateId, Integer investorId);

	List<AbsentModel> getHrsUserAbsents(Integer currentYear);

	List<Investor> loadInvestorsByRealEstateId(Integer realEstateId);

	public List<AnnoucementDetails> loadAnnouncementDetailsByStatus(Integer[] status);

	public List<Car> loadAllCars();

	public List<CarBrand> loadAllCarBrands();

	public List<CarModel> loadCarModelByBrandId(Integer carBrand);

	public List<VehicleType> loadAllVehicleTypes();

	public List<FuelType> loadAllFuelTypes();

	public List<FuelTransaction> loadAllFuelTransactions();

	public List<UserCars> loadUserCarsByUserId(Integer employerId);

	public List<UserCars> loadAllAllocatedUserCars();

	public List<Car> loadNotAllocatedCarsByModelId(Integer carModelId);

	public List<FuelTransaction> loadFuelTransactions(Date startDate, Date endDate);

	public UserCars findCarByNumDoor(Integer numDoor);

	public FuelTransaction findLastFuelTransaction(Integer userCarId);

	public List<ContractDirect> loadAllContractDirects(Integer contractTypeId);

	public List<ArcPeople> loadArcPeopleFields();

	public List<FuelSupply> loadAllFuelSupplies();

	public FuelSupply loadLastFuelSupply(Integer fuelTypeId);

	public List<IntroContract> loadAllIntroContracts();

	public List<AnnoucementDetails> loadAnnouncementDetailsHavingTenders(Integer[] status);

	public Tender loadAssignedTenderByAnnouncementDetailsId(Integer annoucementDetailsId);

	public List<ContractCancelReason> loadAllContractCancelReasons();

	public List<NotifFinesMaster> loadAllNotifPenalities(Integer status);

	public List<ContractDirectType> loadAllContractDirectTypes();

	public List<PayLicBills> loadContractBills(Integer contractId, String contractType);

	public List<LicTrdMasterFile> loadLicences();

	public List<ReqFinesSetup> loadFineSetupBySection(Integer sectionId);

	public List<WrkCommentType> findAllCommentTypes();

	public Integer getNewVacPeriod(int currentyear);

	List<CompensatoryVacStock> getAllUsersCompVacStock();

	/////////////////// Balabel /////////////////
	public List<HrsVacSold> getAllHrsVacSold();
	/////////////////////////////////////////////

	public boolean addUserRequest(UserRequest reuest, RequestStep reqSteps);

	Integer getInitUserVacSold(int findCurrentYear, int empno);

	List<DocumentsType> getAllDocumentsType();

	String createIncomeNo();

	public List<WrkFinesEntity> getAllWrkFinesEntity();

	ArcRecords findRecordByOutComeNo(int outcomeNumber);

	public List<Project> getAllProjects();

	public AutorizationSettings getAutorizations();

	public List<ProjectContract> getallProjectContract();

	public List<FinEntity> getAllfinEntitys();

	public HrsVacSold getHrsVacSoldById(Integer id);

	//// To calculate number of authorization hours taken par month
	public List<HrsUserAbsent> getAuthorizationsHoursNumber(int userId, String month, String year);

	public List<HrsUserAbsent> getAllEmpApsent(int userId);

	public Article getArticleById(Integer id);

	public List<CompensatoryVacStock> getUserCompensatoryVacStockByEmpNo(Integer empNO, Integer compVacType);

	// get all emps in Selected Depts (ARC_USER)
	public List<User> getAllEmployeesInSelectedDepts(List<String> deptIds);

	// delete FngUserTempShift by id and work date
	public void deleteFngUserTempShiftById(Integer userId, Date workDate);

	// to get time shift by timeshiftId
	public FngTimeTable getTimeShiftById(Integer timeShiftId);

	// to get time shifts for emplyee by userId and workDate
	public List<FngUserTempShift> getEmployeeShiftsById(Integer userId, Date workDate);

	public List<Article> getAllArticles();

	// to load all invenroties in WHS_GARD_MASTER table( inventory entity)
	public List<InventoryMaster> getInventoriesByStrNo(Integer strNo);

	public HrsJobCreation loadJobCreation(Integer jobNumber, String jobCode);

	public void addOperation(Object dep_tr);

	public List<String> loadDepTrain(Integer emp_no, Integer month, Integer year);

	List<DeputationTraining> loadStatus(Integer emp_number);

	List<DeputationTraining> loadStatus(Integer emp_number, Integer month, Integer year);

	void saveReward(RewardInfo rewardinfo);

	List<RewardInfo> loadRewards(Integer emp_number);
	public List<RewardInfo> loadRewards(Integer emp_number,Integer month,Integer year);
	public List<String> loadReward(Integer emp_no,Integer month,Integer year);
	public Integer getIdFromWorkAppByAppId(Integer appId);

	List<WrkLetterFrom> loadAllWrkLetterFrom();

	List<VacationsType> loadAllVacationTypes();

	List<FngStatusAbsence> loadAllAbsenceStatus();

	List<FngTypeAbsence> loadAllAbsenceTypes();

	List<SysCategoryEmployer> loadAllCategoryEmployers();

	List<WrkCommentType> loadAllCommentTypes();

	List<WrkPurpose> loadAllPurposes();

	List<WrkLetterTo> loadAllWrkLetterTo();
	public List<WrkDept> findDepartmentById(Integer deptId);
	public List<LoanModel> loadUsersLoan(Integer year,Integer Month,Integer type);
	public List<RetirementModel> loadRetirement();
	
}
