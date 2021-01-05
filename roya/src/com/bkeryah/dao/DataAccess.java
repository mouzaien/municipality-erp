/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.dao;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.bkeryah.bean.ActionClass;
import com.bkeryah.bean.ArcApplicationTypeClass;
import com.bkeryah.bean.ArcAttachmentClass;
import com.bkeryah.bean.ArcRecordClass;
import com.bkeryah.bean.AttachmentFileClass;
import com.bkeryah.bean.CommentClass;
import com.bkeryah.bean.EmpVac;
import com.bkeryah.bean.InventoryModel;
import com.bkeryah.bean.LastRecordActionClass;
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
import com.bkeryah.bean.UserStatisticsClass;
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
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.DeptArcRecords;
import com.bkeryah.entities.ExchangeRequest;
import com.bkeryah.entities.HrsEmpHistorical;
import com.bkeryah.entities.HrsSalaryScale;
import com.bkeryah.entities.HrsUserAbsent;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.StockEntryMaster;
import com.bkeryah.entities.StoreTemporeryReceiptMaster;
import com.bkeryah.entities.investment.ContractDirect;
import com.bkeryah.fng.entities.TstFinger;
import com.bkeryah.fuel.entities.Car;
import com.bkeryah.hr.entities.HrsCompactFloors;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.licences.models.BldLicNewModel;
import com.bkeryah.model.DashbordModel;
import com.bkeryah.model.MemoReceiptModel;
import com.bkeryah.model.VacationModel;
import com.bkeryah.penalties.LicTrdMasterFile;
import com.bkeryah.penalties.ReqFinesMaster;
import com.bkeryah.stock.beans.StoreTemporeryReceiptDetailsModel;

/**
 *
 * @author IbrahimDarwiesh
 */
public interface DataAccess {

	// User Login function that return of object of UserClass
	public UserClass login(String User_name, String Password);

	// Get All Records الحصول علي كل المعاملات داخل البرنامج
	public List<ArcRecordClass> getRecords(String IncomeNoPar);

	public List<ArcRecordClass> SearchRecordsByCommentdata(String SrchKey);

	public List<ArcRecordClass> SearchRecordsByLetterTo(int SrchKey);

	public List<ArcRecordClass> SearchRecordsByLetterFrom(int SrchKey);

	// get all Menus related to specific User تحميل القوائم الرئيسية المتعلقه
	// بالمستخدم الحالي
	public List<Main_menu_class> getMenuByUser(int userId);

	// load all sub menues that related to main menu تحميل جميع القوائم الفرعيى
	// لكل قائمة رئيسية تم تحميلها
	public List<Sub_menu_class> findSubMenuByMain(int mainMenuId);

	// load all sub menues
	public List<Sub_menu_class> findSubMenus(int UserId, int mainMenuId);

	// load all records in Employee INBOX تحميل صندوق البريد الخاص بالموظف او
	// المستخدم
	public List<UserMailObj> findEmployeeInbox(Integer start, Integer size, int EmployeeUserId);

	public List<UserMailObj> findEmployeeInboxRead(int EmployeeUserId);

	public List<UserMailObj> findEmployeeInboxUnread(int EmployeeUserId);

	public List<UserMailObj> findEmployeeInboxDaily(int EmployeeUserId);

	public List<UserMailObj> findEmployeeInboxBySenderId(int EmployeeUserId, int EmployeeSenderId);

	// search in user inbox البحث داخل معاملات البريد الخاصة بالموظف
	public List<UserMailObj> SearchEmployeeInbox(int EmployeeUserId, int searchType, String searchKey,
			String IncludeComment);

	// load all outbox of user تحميل البريد الصادر للمستخدم
	public List<UserMailObj> findEmployeeOutbox(int EmployeeUserId, String inDate, int partOfYear);

	public List<UserMailObj> findEmployeeOutboxArchive(int EmployeeUserId);

	public List<UserMailObj> searchEmployeeOutbox(int EmployeeUserId, String SearchKey, String IncludeArchiveYN);

	public List<UserMailObj> findEmployeeOutbox(int EmployeeUserId, int RecordsRang);

	// find Current Logged in user ايجاد المستخدم الحالي للنظام
	// public UserClass findCurrentUser();

	// lading All comments by Wrk_application ID تحميل جميع الشروحات الخاصة
	// بالمعاملة
	public List<WrkCommentsClass> findCommentsByWrkId(String WrkId);

	// load list of departments Manager تحميل قائمة بمدراء الادارات
	public List<UserContactClass> findAllManagers(int Userid);

	// load all employees in the same department تحميل قائمة بجميع موظفي القسم
	// المتواجيدن مع الموظف الحالي
	public List<UserContactClass> findAllEmployeesInDept(int Userid);

	// load all attachments related to record تحميل قائمة المرفقات
	public List<ArcAttachmentClass> findAttachmentFilesByArcId(String ArcId);

	/**
	 * load wrk purpose list
	 * 
	 * @param puserid
	 * @return
	 */
	public List<WrkPurposeClass> findAllWrkPurpose(Integer puserid);

	public void addNewPurpose(String PurposeName);

	public void deletePurpose(int PurposeId);

	// LAOD ALL WRK_COMMENTS TYPES تحميل قائمة انواع الخطابات
	// public List<WrkCommentTypeClass> findAllCommenttTypes();

	// LOADING ATTACHMENT AT THE REMOTE SERVER ارسال الملف من الالخادم الحالي
	// الي الخادم البعيد الخاص بالملفات
	public String transferAttachment(InputStream inputStream, String v_att_name, int UserId, String ArcRecordId);

	// GENERATE FILE RANDOME NAME TO RENAME EVERY ATTACHED FILE
	public String getAttachmentRandomName();

	// TRANSFER RECORD FROM USER TO OTHER عمليه احالة المعاملة من مستخدم الي آخر
	public void wrkTransferApplication(String WrkId, String arcRecordId, int fromEmp, int toEmp, String comment,
			int purp);

	// save comment tin database حفظ الخطاب
	public void wrkSaveComment(int UserId, String WrkAppId, String AppNo, String AppDate, int AppPapers, String AppTo,
			String AppSubject, String AppAttach, String AppLongComment, String FirstCopy, String SecondCopy,
			int FontSize, int CommentType, int CommentWroteBy, String commentWroteIn);

	// load Comment information using Wrk_application ID تحميل بيانات الخطاب
	// المتعلق بالمعاملة
	public CommentClass getWrkCommentInfoByWrkId(String WrkApllicationId);

	// show updated growl عرض رسالة في التنبيه
	public void showMessage(String msg);

	// عرض رسالة خطا
	public void showErrMessage(String msg);

	// get Manager USER_ID for specific user الحصول علي رقم المدير الخاص بالموظف
	public String getEmployeeManagerId(String EmployeeId);

	// create New internal COMMENT انشاء خطاب جديد
	public String CreateNewWrkComment(int UserId, String PAppSubject, int ToId, int AppPurpose, String AppTo,
			String AppLongComment, String AppSecondCopy, int AppCommentType, String AppFirstCopy, int AppPapers);

	// convert record from UNREAD to READ after opening IT تحويل المعاملة الي
	// مقروءه
	public void MakeItRead(String vStepId, String vWrkId);

	// navigation that go to user inbox
	public void NavigateToInbox();

	// get list of all system users الحصول علي جميع المستخدمين النظام الفعالين
	// حاليا
	public List<UserClass> findAllUsers();

	public UserClass findEmployeeById(int EmployeeId);

	// get all MainMenus in the system الحصول علي قائمة بجميع القوائم الرئيسية
	// الموجوده بالنظام
	public List<Main_menu_class> findAllMainMenu();

	// get employee Real Name الحصول علي الاسم الحقيقي للموظف
	public String getEmployeeRealName(int EmployeeId);

	// remove Main Menu from specific user حذف قائمة من المستخد
	public void removeMenufromUser(int Userid, int Menuid);

	// add Main Menu from specific user اضافة قائمة جديده للمستخدم
	public void addMenuToUser(int Userid, int Menuid);

	// LOAD All Avaliable Main Menu that is not assigned to specific User قائمة
	// القوائمة التي لا يمتلكها المستخدم المحدد
	public List<Main_menu_class> findAllAvaliableMainMenu(int Userid);

	public List<Sub_menu_class> findAvaliableSubMenuByMainMenu(int userId, int MainMenuId);

	public List<Sub_menu_class> findExistedSubMenuByMainMenu(int userId, int MainMenuId);

	public void removeSubMenufromUser(int Userid, int SubMenuid);

	public void addSubMenuToUser(int Userid, int SubMenuid);

	public void addRecordToFavourit(String WrkApplicationId, int StepId, int UserId);

	public List<UserMailObj> findFavouritInbox(int UserId);

	public void markComment(String WrkId, int UserId, String AppId);

	public void addNewAttachment(String FileName, InputStream in, int AddedBy, int FolderId);

	public String encrypt(String string);

	public String decrypt(String string);

	public List<AttachmentFileClass> getAllFilesbyUser(int UserId);

	public AttachmentFileClass getFilesbyId(String FileId);

	public List<AttachmentFileClass> SearchAttachmentFiles(int UserId, String Search_key);

	public void deleteAttachmentFile(String fileAttachId);

	// ارسال صورة من المعاملة عند الاحالة
	public void sendRecordSingleCopy(String ArcRecordId, String WrkApplicationId, int FromUserId, int ToUserId,
			String LongComment, int PurposeId, String AttachedExplain, String Title, int StepId);

	// التحقق من وجود خطاب متعلق بالمعاملة أم لا
	public boolean WrkAppHasComment(String WrkApplicaionId);

	public List<UserContactClass> findCommentSignEmployeesList(int UserId, String WrkApplicationId);

	public void SignWrkComment(int UserId, String WrkApplicationId, int WrkApplicationStepId, String WrkCommentAppId,
			String ConfirmPassword, String Signtype, int SentToUserId);

	// public void openDialog(String DialogName);
	//
	// public void closeDialog(String DialogName);

	// check if specific user has sign in database or not
	public boolean EmployeeHasSign(int EmployeeId);

	//
	public void navigateToWebPage(String webpage);

	public void navigateToAccessDenied();

	// عملية استرجاع المعاملة retrieving record after sending It
	public String retrieveRecord(int UserId, String WrkApplicationId, String WrkApplicationAppId);

	// استدعاء تقارير الجاسبر ريبورت calling jasper report
	public void CallJasperReport(String CallingUrl);

	// send WRK_Comment copy after signing ارسال نسخ من الخطاب الالكتروني بعد
	// التوقيع
	public void sendCommentCopy(int UserId, int ToId, String WrkCommentAppId, String ArcRecordId);

	public String SendNewRecord(String ArcRecTitle, int ArcIsImportant, int UserId, int ToId, String ArcComment,
			int ArcPurp, int WrkCommIsSecret, String CreateNewIncomeNo);

	public String SendNewRecordcopy(String ArcRecTitle, int ArcIsImportant, int UserId, int ToId, String ArcComment,
			int ArcPurp, int WrkCommIsSecret, String CreateNewIncomeNo, String OriginalArcRecordId);

	public BigDecimal AddNewAttachment(int UserId, String ArcRecordId, String ArcAttName, double ArcAttSize);

	public String findAllowedFilesTypes(String fileTypes);

	public String CreateNewIncomeRecord(int UserId, String ArcTitle, int ArcPurp, int ArcTyp, int LetterTo,
			int LetterFrom, int ArcSendTo, String LetterFromNo, String LetterFromDate, String RefrencedLetterNo,
			String MobileNumber, String LongComment, int IsArcRecordImportant);

	public List<ArcApplicationTypeClass> findAllApplicationTypes();

	public void addNewApplicationType(String NewApplicationTypeName);

	public void deleteApplicationType(int ApplicationTypeId);

	/**
	 * load list of letter form
	 * 
	 * @return
	 */
	public List<WrkLetterFromClass> findAllWrkLetterFroms();

	public void addNewWrkLetterfrom(String NewLetterFromName);

	public void deleteWrkLetterfrom(int LetterFormId);

	/**
	 * load list letter to
	 * 
	 * @return
	 */
	public List<WrkLetterToClass> findAllAWrkLetterTos();

	public void addNewWrkLetterTo(String NewLetterToName);

	public void deleteWrkLetterTo(int LetterToId);

	/**
	 * load list of wrk archive reciept
	 * 
	 * @return
	 */
	public List<WrkArchiveRecipentClass> findAllWrkArchiveRecipents();

	public List<String> findAllWrkCommentAppTo(String SearchKey);

	// create New User in the System ARC_USERS
	public int createNewUser(String LoginName, String Password, String FirstName, String LastName, int titleId,
			int JobId, int DepartmentId, int ManagerId, int WrkRoleId, int SectionId, String MobileNumber);

	// الحصول علي جميع الإدارات داخل النظام
	List<WrkDeptClass> findAllDepartment();

	List<SysTitleClass> findAllSysTitles();

	List<WrkRolesClass> findAllRoles();

	List<WrkSectionClass> findAllSectionsByDept(int deptId);

	// find All jobs existen in specific SECTION
	List<WrkJobClass> findAllJobsBySection(int SectionId);

	// show Modal Dialog
	public void showModalDialog(String Msg);

	// اضافة قسم جديد لإدارة موجوده بالفعل
	public int addNewsection(String sectionName, int deptId);

	// اضافة إدارة جديده
	public int addNewDept(String DepartmentNameAR, String DepartmentNameEN);

	// حذف قسم موجود 1 نجاح 0 فشل
	public int removeSectionById(int Section_id);

	// الحصول علي التاريخ الهجري لليوم الحالي
	public String findCurrentHijriDate();

	// الحصول علي السنة الهجرية الحالية
	public String findCurrentHijriYear();

	// الحصول علي ترتيب يوم معين خلال الأسبوع 1-7 من السبت إلي الجمعة // pDate
	// is Georgian Date البارامتير تاريخ ميلادي
	public int findDayOrderInWeek(String pDate);

	// الحصول علي الشهر الحالي بالارقام
	public int findCurrentMonth();

	// الحصول علي الشهر الحالي بالحروف
	public String findCurrentMonthName();

	// الحصول علي جميع السنوات الهجرية المتاحة داخل النظام //PRC_GET_ALL_YEARS
	public Map<String, String> findAllHijriYears();

	// الحصول علي جميع الشهور الهجرية المتاحة داخل النظام
	public Map<String, String> findAllHijriMonths();

	// الحصول علي جميع الايام المتاحة داخل الشهر
	// NEW_PKG_WEBKIT.PRC_GET_MONTH_DAYS_COUNT(1435 , 12 , n)
	public Map<String, String> findAllHijriDays(String YearNbr, int MonthNbr);

	public List<UserClass> convertContactListToUserList(List<UserContactClass> contactList);

	public List<UserContactClass> findInboxSenderList(int pUserId);

	public Map findAllReportParameter(String paramsUrl);

	public List<UserFolderClass> findAllUserFolders(int Userid);

	public int EmployeeArchivedArch(int UserId);

	public String convertTextWithArNumric(String txt);

	public List<UserMailObj> findEmployeeFolderRecords(int UserId, int FolderId);

	// public Map<String, String> findDaysMap();
	//
	// public Map<String, String> findMinutesMap();
	//
	// public Map<String, String> findHoursMap();

	public boolean CheckSignturePassword(int UserId, String Password);

	public String findDepartmentNameById(int departmentId);

	public List<ReferralSettingClass> findAllReferralSetting();

	public void deleteReferralById(int ReferralId);

	public void deleteReferralByManagerId(int ManagerId);

	public void addNewReferralSetting(int ManagerId);

	public void addExistedAttachToRecord(String AttachmentId, String ArcRecordId);

	public void addNewUserFolder(String FolderName, int UserId);

	public String MoveRecordToFolder(String WrkId, int StepId, int UserId, int FolderId);

	public String findSystemProperty(String propertyName);

	public LastRecordActionClass findLastWrkApplicationDetails(String ArcId);

	public int findArcRecordParam(String ArcId);

	public void SendRecordFromArchieve(String RecordId, int EmployeeId, String SendingType);

	public String deleteUserFolder(int FolderId);

	public String updateUserPassword(int UserId, String NewPassword);

	public String updateUserSignPassword(int UserId, String NewPassword);

	public String convertDateToArabic(String hijriDate);

	public List<SpecialAddressClass> findSpeicalAddressByUser(int pUserId);

	public String addNewSpecialAddress(int pUserId, int pSpecialAddressId);

	public String deleteSpecialAddress(int pUserId, int pSpecialAddressId);

	public List<ReferralSettingClass> findReferralSettings();

	public String addNewReferralSetting(int pManagerId, String ManagerTitle);

	public String deleteReferralSetting(int pId);

	public String addNewRecipent(int pId, String pRecipenetTitle);

	public String deleteRecipent(int pId);

	public List<WrkChargingClass> findAllChargingByStatus(int Status);

	public String addNewCharging(int WhoIsCharged, int WhoIsNotHere, String UserNameBefore, String UserNameAfter,
			String StartDate, String EndDate, int Flag, int privilageBefore, int privilageAfter, int ManagerBefore,
			int ManagerAfter);

	public String addNewCharging(int WhoIsCharged, int WhoIsNotHere, String UserNameAfter, String StartDate,
			String EndDate, int privilageAfter);

	public String updateUserSign(int pUserId, InputStream pSign);

	public String updateUserMark(int pUserId, InputStream pMark);

	public String updateUserRefSign(int pUserId, InputStream pRefSign);

	public Map<String, String> findUsersMap();

	public Map<String, String> findUsersRolesMap();

	public String findEmployeeName(int EmployeeId);

	public String updateUserInfo(int UserId, int ManagerId, int RoleId, int DeptId, int SectionId, int JobId);

	public String updateUserNameInfo(int UserId, String FirstName, String LastName, String NameAr, String NameEn);

	public Map<String, String> findrecordsMap();

	public ArcRecordClass getRecordbyIcomeNUmber(String IncomeNumber);

	public String addNewExportedRecord(String IncomeNumber, int UserId);

	public List<Employer> loadAllEmployers();

	public Map<String, String> loadDirections();

	public void storeAction(String Action, String Detail);

	public String checkUmmAlQuraDate(String value);

	public UserStatisticsClass findUserStatistics(int UserId, String StartDate, String EndDate);

	public boolean isAuthonticatedtoChangePassword(String loginName, String systemPassword);

	public boolean isAuthonticatedtoChangeSignPassword(String loginName, String signPassword);

	public UserFolderClass findInboxRecordFolder(int UserId, String WrkId, int StepId);

	public List<UserMailObj> SearchEmployeeInboxByDate(int EmployeeUserId, int searchType, String searchKey,
			String StartDate, String EndDate);

	public List<ActionClass> findUserAction(int UserId);

	public EmpVac infoVacEmp(int empNo);

	public EmpVac loadEtrariVac(int app_id);

	public EmpVac loadNormalVac(int app_id);

	public EmpVac infVacEmp1(int vuser_id, int vacprd);

	public EmpVac infVacEmp2(int vuser_id, int vacsum360, int vacprd, int calc, int pbascal, int vacaresult);

	public EmpVac validate_dates_vac116(int vuser_id, String vACASTRT, int vacprd);

	public boolean instrVac1166(int vuser_id, int vactype, int vacprd, String vacastrt, String vacaend, int proctyp,
			int salary, int vacval, String vacwrkfrom, String vacwrKto);

	public EmpVac CALC_VAC122_emp122(int vuser_id);

	public EmpVac checkdate122(String vacastrt, int vacprd);

	public boolean prc_insrt_vacEtrari(int vuser_id, int vactype, int vacprd, String vacastrt, String vacaend);

	public EmpVac prc_accept_vac(int pWRKID, int vuser_id, String d_MGRtxt, String d_PRStxt);

	public EmpVac prc_accept_vacEtrati(int pWRKID, int vuser_id, String d_MGRtxt, String d_PRStxt);

	public TrdModelClass findTrdApplicationData(int trdApplicationId);

	public Employer getReturnVacData(int EmployeeUserId);

	public String getHashedPassword(String userName, String password);

	boolean signedIsAutorized(int userId, int privlegeId) throws SQLException;

	void chargingEmp(int chargingEmpIn);

	public void finishChargingEmp(int chargingEmpIn);

	public List<WrkCommentsClass> findCommentsByArcId(Integer arcId);

	public List<ExchangeRequest> searchExchangeRequests(String beginDate, String finishDate, Integer strNo,
			Integer artType, Integer employerId);

	List<StoreRequestModel> getTransactionsQty(int articleId, int strNo);

	List<InventoryModel> ListInventories(int strNo, Integer inventoryId, String inventoryDate);

	List<StoreRequestModel> getArticleHistory(int articleId, Integer strNO);

	List<LicTrdMasterFile> getTrdMasterFileList();

	ReqFinesMaster checkLicLst(String licNo);

	public List<LicTrdMasterFile> getTrdMasterFileListByPeriodType(Integer typeLicence, Integer period);

	public Integer billstatus(Integer newBill);

	List<BldLicNewModel> loadLicNewList(Long nationalId);

	public List<HrsSalaryScale> loadAllJobRanks();

	public List<HrsEmpHistorical> calculatePrimes();

	void calcSalary(int catid, int pmonth, int pyear);

	List<HrsCompactFloors> getHrsCompactFloors(Integer hrsPerfomId, Integer jobType);

	public List<TstFinger> getVacationFng(String lstUser, String startDate, String endDate, Boolean allEmp,
			boolean higriMode);

	public List<TstFinger> getFngDetail(String lstUser, String startDate, String endDate, boolean higriMode);

	public List<ArcUsers> employeersTree();

	public List<HrsUserAbsent> getAllAbsent(String lstUser, String startDate, String endDate);

	public void loadLetterReportParameters(Map<String, Object> parameters, String wrkId);

	public DashbordModel loadDasbordParams(Integer userId);

	public List<VacationModel> getUsersVacations(String lstUser, String startDate, Boolean allEmp);

	public List<DeptArcRecords> loadDeptRecords(Integer currDeptId, Integer status);

	public void correctUserFngs(Integer userId, String startDate, String endDate);

	public Map<Object, Number> loadArcRecordsNBForCurrentYear(Integer userId, Integer deptId);

	List<String> getEmpSalariesFile(Integer p_month, Integer p_year);

	StoreRequestModel getArticleHistoryById(int articleId);

	public List<StoreRequestModel> ListStoreRequestModel(int strNo);

	public MemoReceiptModel getMemoReceiptDetails(Integer memo_receipt_id);

	public String convertNumberToLiteralWords(Float number);

	public List<StockEntryMaster> searchMemoReceipts(String beginDate, String finishDate, Integer strNo);

	public List<Article> getArticlesByUserId(Integer userId);

	public List<Article> getAllReturnStoreArticles(Integer strNo);

	List<Car> getCarsDetailsBySubGroupId(Integer subGroupId);

	List<Article> getArticlesByUserIdWithoutCars(Integer userId);

	List<Article> get3ohadByUserId(Integer userId);

	public List<StoreTemporeryReceiptMaster> searchTempReceipts(String beginDate, String finishDate, Integer strNo);

	public List<StoreTemporeryReceiptDetailsModel> getTempReceiptDetailsList(Integer temp_receipt_id);

	void deleteVisitsForDisactiveSupervisors(Integer licId, String startHDate, String endHDate);

	public List<ContractDirect> loadContractDirectListByAllFilters(Integer contNum, Integer investorId, Integer status,
			String fromStartDate, String toStartDate, String fromEndDate, String toEndDate, Integer sectionId,
			Integer contractMaincatgId, Integer contractSubcatgId, Integer actvityId, String component, Integer contractStatusFilter);

	public List<PayLicBills> loadBillsListByAllFilters(String fromStartDate, String toStartDate, String aplnumber,
			Long phoneNumber, Integer billStatus,Integer bandId);
}
