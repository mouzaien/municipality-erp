package com.bkeryah.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PreDestroy;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bkeryah.bean.UserContactClass;
import com.bkeryah.entities.*;
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
import com.bkeryah.entities.licences.BldLicMasterTbl;
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
import com.bkeryah.hr.entities.HrsCompactFloors;
import com.bkeryah.hr.entities.HrsCompactGoals;
import com.bkeryah.hr.entities.HrsCompactPerformance;
import com.bkeryah.hr.entities.HrsCompactRating;
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
import com.bkeryah.hr.entities.HrsSalaryScaleOrder;
import com.bkeryah.hr.entities.HrsSumVacation;
import com.bkeryah.hr.entities.HrsVacSold;
import com.bkeryah.hr.entities.HrsVacationUpdate;
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
import com.bkeryah.penalties.FineSection;
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
import oracle.jdbc.OracleTypes;
import utilities.HijriCalendarUtil;
import utilities.MyConstants;
import utilities.Utils;

public class CommonDao extends HibernateTemplate implements ICommonDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SessionFactory sessionFactory; // comments removed
	private Integer wrkAppSequence = 0;
	private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ArcUsers> findAllUsers() {
		List<ArcUsers> userList = new ArrayList<>();
		String hql = Utils.readQuery("findAllUsers");
		Query query1 = sessionFactory.getCurrentSession().createQuery(hql);
		userList = query1.list();
		return userList;
	}

	@Override
	public Properties createPopertiesFile(String filePath) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(filePath);
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return prop;
	}

	@Override
	@Transactional
	public ArcUsers findUserById(int userId) {
		ArcUsers au = new ArcUsers();
		au = (ArcUsers) sessionFactory.getCurrentSession().get(ArcUsers.class, userId);
		return au;
	}

	@Override
	@Transactional
	// NOT USED
	public String addArcRecordAttachment(InputStream inputstream, String fileName, String arcRecordId, int userId) {
		try {
			String randomFileName = new BigInteger(130, new SecureRandom()).toString(32)
					+ new SimpleDateFormat("ssddMMmmSSS").format(new Date());
			Properties prop = createPopertiesFile("configure/config.properties");
			String fileServerIp = prop.getProperty("FILE.SERVER.IP");
			String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
			String fileDistnation = prop.getProperty("APP.SERVER.LOCAL.PATH");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public ArcAttach findattachById(int attachId) {
		ArcAttach arcAttach = new ArcAttach();
		arcAttach = (ArcAttach) sessionFactory.getCurrentSession().get(ArcAttach.class, attachId);
		return arcAttach;
	}

	@Override
	@Transactional
	public ArcRecords findRecordById(int recordId) {
		ArcRecords arcAttach = new ArcRecords();
		arcAttach = (ArcRecords) sessionFactory.getCurrentSession().get(ArcRecords.class, recordId);
		return arcAttach;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional
	public List<Object> findAll(Class typeClass) {
		try {
			return loadAll(typeClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Object findEntityById(Class entityClass, int EntityId) {
		return get(entityClass, EntityId);
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Object getEstablishIdBySchool(Class entityClass, String school) {
		return get(entityClass, school);
	}

	@Override
	@Transactional
	public WrkApplication findWrkApplicationById(WrkApplicationId wrkApplicationId) {
		return get(WrkApplication.class, wrkApplicationId);
	}

	@Override
	@Transactional
	public HrsSalaryScale findHrsSalaryScaleById(Class entityClass, HrsSalaryScaleId EntityId) {
		return get(HrsSalaryScale.class, EntityId);
	}

	@Override
	@Transactional
	public ArcPeoplePic findArcPeoplePicById(Long nationalId) {
		return get(ArcPeoplePic.class, nationalId);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ArcAttach> findAttachmentList(int arcRecordId) {
		List<ArcAttach> userList = new ArrayList<>();
		String sql = Utils.readQuery("findAttachmentList");
		Query query1 = sessionFactory.getCurrentSession().createQuery(sql).setParameter("recId", arcRecordId);
		userList = query1.list();
		return userList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<WrkApplication> findAllWrkApplicationById(Integer wrkApplicationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkApplication.class);
		criteria.add(Restrictions.eq("id.applicationId", wrkApplicationId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ReqFinesMaster> findLstFinesMasterByLicId(String fLicenceNo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReqFinesMaster.class);
		criteria.add(Restrictions.eq("fLicenceNo", fLicenceNo));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ReqFinesMaster> findLstFinesMasterByLicIdWithState(String fLicenceNo, Integer fFineCase) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReqFinesMaster.class);
		criteria.add(Restrictions.eq("fLicenceNo", fLicenceNo));
		criteria.add(Restrictions.eq("fFineCase", fFineCase));
		return criteria.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ReqFinesDetails> findLstFinesDetailsByFineNO(Integer fNo) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReqFinesDetails.class);
		criteria.add(Restrictions.eq("fineNo", fNo));
		return criteria.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public LicTrdMasterFile findLicByLicId(String fLicenceNo) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LicTrdMasterFile.class);
		criteria.add(Restrictions.eq("licNo", fLicenceNo));
		return (LicTrdMasterFile) criteria.list().get(0);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ReqFinesDetails findFinesDetailsByFno(Integer fNo) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReqFinesDetails.class);
		criteria.add(Restrictions.eq("fineNo", fNo));
		return (ReqFinesDetails) criteria.list().get(0);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ReqFinesMaster findFinesMasterByLicId(String fLicenceNo) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReqFinesMaster.class);
		criteria.add(Restrictions.eq("fLicenceNo", fLicenceNo));
		return (ReqFinesMaster) criteria.list().get(0);

	}

	@Override
	@Transactional
	public Integer save(Object myObject) {

		Integer save = (Integer) sessionFactory.getCurrentSession().save(myObject);
		System.out.println("SAVE---> " + save);
		return save;

	}

	@Override
	@Transactional
	public Object saveObject(Object myObject) {
		return sessionFactory.getCurrentSession().save(myObject);
	}

	@Override
	@Transactional
	public Object addMailToFolder(WrkUserFolderMail folderMail) {
		return sessionFactory.getCurrentSession().merge(folderMail);
	}

	@Override
	@Transactional
	public Long saveObjectReturnLong(Object myObject) throws Exception {

		return (Long) sessionFactory.getCurrentSession().save(myObject);
	}

	@Override
	@Transactional
	public synchronized WrkApplicationId saveWrkApp(WrkApplication app, Integer recordId) {
		int stepId = 0;
		WrkApplicationId wrkId = null;
		if (recordId > 0) {
			stepId = loadApplicationStepId(recordId);
		} else
			stepId = 1;
		if (app.getId() == null) {
			int maxWrkId = createWrkId();
			if (wrkAppSequence < maxWrkId)
				wrkAppSequence = maxWrkId;
			else
				wrkAppSequence++;
			wrkId = new WrkApplicationId(wrkAppSequence, stepId);
			app.setId(wrkId);
		}
		WrkApplicationId applicationId = (WrkApplicationId) sessionFactory.getCurrentSession().save(app);
		return applicationId;
	}

	@Override
	@Transactional
	public void update(Object myObject) {
		try {
			sessionFactory.getCurrentSession().update(myObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void update(Object myObject1, Object myObject2) {
		try {
			sessionFactory.getCurrentSession().update(myObject1);
			sessionFactory.getCurrentSession().update(myObject2);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Object myObject1, Object myObject2, Object myObject3) {
		try {
			sessionFactory.getCurrentSession().update(myObject1);
			sessionFactory.getCurrentSession().update(myObject2);
			sessionFactory.getCurrentSession().update(myObject3);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public synchronized Integer createOutcomeNo() {
		String sql = Utils.readQuery("createOutcomeNumber");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		BigDecimal result = (BigDecimal) query.uniqueResult();
		return result.intValue();
	}

	@Override
	@Transactional
	public synchronized String createIncomeNo() {
		String sql = Utils.readQuery("createIncomeNo");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		Object result = query.uniqueResult();
		return result.toString();
	}

	@Override
	@Transactional
	public synchronized int createWrkId() {
		String sql = Utils.readQuery("createWrkId");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		Object result = query.uniqueResult();
		return Integer.parseInt(result.toString());
	}

	@Override
	@Transactional
	public Integer findUserSection(int userId) {
		String sql = Utils.readQuery("findUserSection");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("puserId", userId);
		Object result = query.uniqueResult();
		return (result == null) ? 0 : (Integer.parseInt(result.toString()) + 1);
	}

	@Override
	@Transactional
	public synchronized int loadApplicationStepId(int recordId) {
		String sql = Utils.readQuery("loadApplicationStepId");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("precordId", recordId);
		Object result = query.uniqueResult();
		return (result == null) ? 1 : (Integer.parseInt(result.toString()) + 1);
	}

	@Override
	@Transactional
	public int markLetter(int wrkAppId, int userId) {
		if (userId == 0)
			return 0;

		String hql = Utils.readQuery("markLetter");
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("hijriDate", HijriCalendarUtil.findCurrentHijriDate().toString());
		query.setParameter("puserId", userId);
		query.setParameter("pwrkAppId", wrkAppId);
		int queryStatus = query.executeUpdate();
		return queryStatus;
	}

	@Override
	@Transactional
	public int signLetter(int pwrkAppId, int puserId, String psignType) {
		if (puserId == 0)
			return 0;
		String CurrHDate = HijriCalendarUtil.findCurrentHijriDate();
		String HqlQuery = Utils.readQuery("signLetter");
		Query query = sessionFactory.getCurrentSession().createQuery(HqlQuery);
		query.setParameter("psignedIn", CurrHDate);
		query.setParameter("puserId", puserId);
		query.setParameter("psignType", psignType);
		query.setParameter("pappDate", CurrHDate);
		query.setParameter("pwrkAppId", pwrkAppId);
		int queryStatus = query.executeUpdate();
		return queryStatus;
	}

	@Override
	public void saveAttachment(InputStream inputStream, String att_name) {
		String destination = "C:\\temp\\";
		File temp = new File(destination);
		if (!temp.exists())
			temp.mkdirs();
		String CompleteName = "";
		try {
			OutputStream out = new FileOutputStream(new File(destination + att_name));
			CompleteName = destination + att_name;
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			inputStream.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println("ERROR!! 009 : " + e.getMessage());
		}

		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://localhost:8080/serverUpload/upload");
			FileBody fileBody = new FileBody(new File(CompleteName));
			HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("upfile", fileBody).build();
			httpPost.setEntity(httpEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				HttpEntity responseEntity = response.getEntity();
				if (responseEntity != null) {
					BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					StringBuilder result = new StringBuilder();
					String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				}
				EntityUtils.consume(responseEntity);
			} catch (Exception responseEx) {
				responseEx.printStackTrace();
				System.err.println("ERROR!! 010 : " + responseEx.getMessage());
			} finally {
				response.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("ERROR!! 011 : " + ex.getMessage());
		}

	}

	@Override
	public List<WrkPurpose> getAllPurposes() {
		return loadAll(WrkPurpose.class);

	}

	@Override
	public List<Sys037> getAllContractorsCategories() {
		return loadAll(Sys037.class);

	}

	@Override
	public List<WrkCommentType> getCommentTypes() {

		return loadAll(WrkCommentType.class);

	}

	@Transactional
	public Integer getUserDeptJob(int managerId) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcUsersExtension.class);
		criteria.add(Restrictions.eq("userId", managerId));
		ArcUsersExtension user = (ArcUsersExtension) criteria.uniqueResult();
		if (user == null)
			return null;
		return user.getUserDeptJob();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> getAllEmployeesInDept(Integer deptId) {
		String hql = "from  User user where (user.deptId=:deptId and :deptId >0 or :deptId = -1) and user.enabled= true ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("deptId", deptId);
		if (!query.list().isEmpty() && query.list() != null)
			return query.list();
		else
			return new ArrayList<User>();
	}

	@Override
	@Transactional
	public List<HrsMasterFile> getAllEmployeesByDeptId(Integer deptId) {
		// String hql = "from HrsMasterFile emp where emp.dptCode=:deptId";
		// // String sql = "SELECT
		// FST_NAME_AR,SND_NAME_AR,TRD_NAME_AR,FTH_NAME_AR
		// // as FULL_NAME FROM HRS_MASTER_FILE wher ";
		//
		// Query query = sessionFactory.getCurrentSession().createQuery(hql);
		// query.setParameter("dptCode", deptId);
		// if (!query.list().isEmpty() && query.list() != null)
		// return query.list();
		// else
		return new ArrayList<HrsMasterFile>();
	}

	@Override
	public List<ArcUsers> getAllEmployees() {

		return loadAll(ArcUsers.class);
	}

	@Override
	@Transactional
	public List<ArcUsers> getAllManagers() {

		return null;
	}

	@Override
	@Transactional
	public List<Integer> addAttachments(List<ArcAttach> attachs) {
		List<Integer> attachmentIds = new ArrayList<Integer>();
		for (ArcAttach arcAttach : attachs) {

			attachmentIds.add((Integer) sessionFactory.getCurrentSession().save(arcAttach));

		}

		return attachmentIds;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ArcUsers loadUser(final String username, final String password) throws AuthenticationException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcUsers.class);
		criteria.add(Restrictions.sqlRestriction("UPPER(LOGIN_NAME) = UPPER(?)", username, StringType.INSTANCE));
		if (password != null) {
			Object[] params = { username, password };
			Type[] types = { StringType.INSTANCE, StringType.INSTANCE };
			criteria.add(Restrictions.sqlRestriction("PASSWORD = wrk_password(?, ?)", params, types));
		}
		List<ArcUsers> result = criteria.list();
		if (CollectionUtils.isEmpty(result))
			throw new BadCredentialsException("bad credentials");
		Hibernate.initialize(result.get(0).getRoles());
		return result.get(0);
	}

	// @SuppressWarnings("unchecked")
	// @Override
	// @Transactional
	// public ArcUsers loadUser(String username) {
	// ArcUsers user = null;
	// String hql = "FROM ArcUsers US where US.loginName=:username ";
	// Query query = sessionFactory.getCurrentSession().createQuery(hql);
	// query.setParameter("username", username);
	// List<ArcUsers> results = query.list();
	// if (results != null && !results.isEmpty())
	// user = results.get(0);
	// return user;
	// }

	/*
	 * (non-Javadoc) get next step of scenario
	 * 
	 * @see com.bkeryah.dao.ICommonDao#getHrsSignNextStep(int)
	 */
	@Override
	@Transactional
	public int getHrsSignNextStep(int arcRecord) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSigns.class);
		criteria.setProjection(Projections.max("stepId"));
		criteria.add(Restrictions.eq("arcRecordId", arcRecord));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id + 1);
	}

	@Override
	@Transactional
	public int getWrkAppFromID(int arcRecord) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSigns.class);
		criteria.add(Restrictions.eq("arcRecordId", arcRecord));
		criteria.add(Restrictions.eq("stepId", 1));
		criteria.setProjection(Projections.property("userId"));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HrsUserAbsent loadHrsUserAbsentByRecordId(int arcRecordId) {
		HrsUserAbsent result = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSigns.class);
		Projection hrsUserAbsent = Projections.property("hrsUserAbsent");
		criteria.setProjection(Projections.distinct(hrsUserAbsent));
		criteria.add(Restrictions.eq("arcRecordId", arcRecordId));
		List<HrsUserAbsent> resultList = criteria.list();
		if (resultList != null && !resultList.isEmpty())
			result = resultList.get(0);
		return result;
	}

	@Override
	@Transactional
	public String getPropertiesValue(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SysProperties.class);
		criteria.setProjection(Projections.property("value"));
		criteria.add(Restrictions.eq("name", name));
		String value = (String) criteria.uniqueResult();
		return value;
	}

	@Override
	public List<WrkRefrentionalSetting> getAllManagersFromWFS() {
		// TODO Auto-generated method stub
		return loadAll(WrkRefrentionalSetting.class);
	}

	@Override
	public ArcRecords getrecordById(Integer recordId) {

		return load(ArcRecords.class, recordId);
	}

	@Override
	@Transactional
	public WrkApplication getWrkApplicationRecordById(int arcRecId) {
		String hql = "from WrkApplication C where C.arcRecordId=:arcRecId order by C.id.stepId DESC";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("arcRecId", arcRecId);
		List<WrkApplication> applicationsList = query.list();
		return applicationsList.get(0);
	}

	@Override
	@Transactional
	public ArcRecords getArchRecordById(int archRecordId) {
		return load(ArcRecords.class, archRecordId);
	}

	@Override
	@Transactional
	public WrkComment getWrkCommentByAppId(int appId) {
		WrkComment comm = null;
		String hql = "from WrkComment  c  where c.appId=:WrkCommentId ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("WrkCommentId", appId);
		List<WrkComment> applicationsList = query.list();
		if ((applicationsList != null) && (!applicationsList.isEmpty()))
			comm = applicationsList.get(0);
		return comm;

	}

	@Override

	public List<ArcAttach> getAttachmentByArchRecordId(int archRecordId) {
		List<ArcAttach> attachments = new ArrayList<ArcAttach>();

		String hql = "from ArcRecAtt  R  where R.id.recordId=:archRecordId ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("archRecordId", archRecordId);
		List<ArcRecAtt> archRecorsdAttList = query.list();
		if (!archRecorsdAttList.isEmpty() && archRecorsdAttList != null) {
			for (ArcRecAtt arcRecAtt : archRecorsdAttList) {
				attachments.add(arcRecAtt.getAttachment());
			}
		}

		return attachments;
	}

	@Transactional
	public int getDocIdHrsSignByRecId(int arcRecordId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSigns.class);
		criteria.setProjection(Projections.max("docId"));
		criteria.add(Restrictions.eq("arcRecordId", arcRecordId));
		int docId = (int) criteria.uniqueResult();
		return docId;
	}

	@Transactional
	public int getDocRecordIdFromHrsSignByRecId(int arcRecordId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSigns.class);
		criteria.setProjection(Projections.property("docId"));
		criteria.add(Restrictions.eq("arcRecordId", arcRecordId));
		int docId = (int) criteria.uniqueResult();
		return docId;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Integer getEmployerNumberHrsAbsent(int autorizationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsUserAbsent.class);
		criteria.setProjection(Projections.property("empNo"));
		criteria.add(Restrictions.eq("id", autorizationId));
		List<Integer> results = criteria.list();
		Integer employerNumber = null;
		if (results != null)
			employerNumber = results.get(0);
		return employerNumber;
	}

	@Override
	@Transactional
	public HrsEmpHistorical getHRsEmpHistoricalByEmpNo(int employerNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsEmpHistorical.class);
		criteria.add(Restrictions.and(Restrictions.eq("id.empno", employerNumber), Restrictions.eq("flag", 1)));
		// criteria.add(Restrictions.eq("id.empno", employerNumber));
		// criteria.add(Restrictions.eq("flag", 1));
		return (HrsEmpHistorical) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public ArcUsers loadUserByEmpNO(int employerNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcUsers.class);
		criteria.add(Restrictions.eq("employeeNumber", employerNumber));
		return (ArcUsers) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<MainMenu> findUserMenus(int puserId) {
		return sessionFactory.getCurrentSession().createQuery("from MainMenuUser where mainMenuUserId.userId = :usr")
				.setParameter("usr", puserId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SubMenu> _findSubMenus(int puserId, int mainMenuId) {
		return sessionFactory.getCurrentSession()
				.createQuery("from SubMenuUser where user.userId = :usr and subMenu.subMenuId = :pmainMenuId")
				.setParameter("usr", puserId).setParameter("pmainMenuId", mainMenuId).list();
	}

	@Override
	@Transactional
	public EmployeeInitiation getInitiationById(Integer initiationId) {
		// TODO Auto-generated method stub
		return (EmployeeInitiation) sessionFactory.getCurrentSession().get(EmployeeInitiation.class, initiationId);
	}

	@Override
	@Transactional
	public HrEmployeeVacation getVacationById(Integer vacationId) {
		// TODO Auto-generated method stub
		return (HrEmployeeVacation) sessionFactory.getCurrentSession().get(HrEmployeeVacation.class, vacationId);
	}

	@Override
	@Transactional
	public Integer getLastVacationForCurrentId(Integer employeeNum) {
		String hql = " Select id from HrEmployeeVacation  V  where  V.employeeNumber=:employeeNum  and V.haveInitiation=0  and V.vacationStatus='Y' and V.vacationPeriod >4   order by V.id DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("employeeNum", employeeNum);
		//
		List<Integer> vacationList = query.list();
		if (!vacationList.isEmpty() && vacationList != null) {
			Integer x = vacationList.get(0);
			return x;
		} else {
			vacationList.size();
			return null;
		}
	}

	@Override
	@Transactional
	public int findMaxOrderIdRank() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSalaryScale.class);
		criteria.setProjection(Projections.max("id.orderId"));
		return (int) criteria.uniqueResult();
	}

	// for testing onlyyyyyyyyyyyyyyyyy
	@Override
	@Transactional
	public Integer saveInitaionAftrWorl(EmployeeInitiation employeeInitiation) throws VacationAndInitException {
		return (Integer) sessionFactory.getCurrentSession().save(employeeInitiation);
	}

	@Override
	@Transactional
	public int getEmpNoHrsVction(int vacationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrEmployeeVacation.class);
		criteria.setProjection(Projections.property("employeeNumber"));
		criteria.add(Restrictions.eq("id", vacationId));
		int empNumber = (int) criteria.uniqueResult();
		return empNumber;

	}

	@Override
	@Transactional
	public int findForcedVacationScore(int empNB) {

		String hql = "SELECT NVL (SUM (vacaprd), 0) FROM HRS_EMP_VCTON WHERE VACATYP = :forcedType AND empno = :empNB AND VAC_ACCPT_Y_N = 'Y' AND TO_DATE (date_converter2 (vacastrt), 'DD/MM/YYYY') >= TO_DATE ('31/12/' || (:CYY - 1), 'DD/MM/YYYY') "
				+ "AND TO_DATE (date_converter2 (vacastrt), 'DD/MM/YYYY') <= TO_DATE ('30/12/' || (:CYY), 'DD/MM/YYYY')";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		query.setParameter("forcedType", MyConstants.FORCED_VACATION);
		query.setParameter("empNB", empNB);
		query.setParameter("CYY", Utils.year);
		return ((BigDecimal) query.uniqueResult()).intValueExact();

	}

	@Override
	@Transactional
	public void updateWrkApplicationVisible(WrkApplicationId wrkApplicationId) {
		// wrkApplicationId.getStepId());
		// String hqll = "from WrkApplication WHERE id.applicationId =
		// :applicationId and id.stepId = :stepId";
		// Query queryy = sessionFactory.getCurrentSession().createQuery(hqll);
		// queryy.setParameter("applicationId",
		// wrkApplicationId.getApplicationId());
		// queryy.setParameter("stepId", wrkApplicationId.getStepId());
		// WrkApplication application = (WrkApplication) queryy.list().get(0);
		// application.setApplicationIsVisible(0);
		// application.setApplicationIsReply(1);
		// sessionFactory.getCurrentSession().merge(application);
		String hql = "UPDATE WrkApplication set applicationIsVisible =:visible, applicationIsReply = :reply WHERE id.applicationId =:applicationId and id.stepId = :stepId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("visible", 0);
		query.setParameter("reply", 1);
		query.setParameter("applicationId", wrkApplicationId.getApplicationId());
		query.setParameter("stepId", wrkApplicationId.getStepId());
		query.executeUpdate();
	}

	@Override
	public Integer getCountAccept(Integer arcRecordId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSigns.class);
		criteria.setProjection(Projections.count("stepId"));
		criteria.add(Restrictions.eq("arcRecordId", arcRecordId));
		Long count = (Long) criteria.uniqueResult();
		return count.intValue();
	}

	// @Override
	// public Integer checkEmployerIsTerminated(Integer employerNumber) {
	// Criteria criteria =
	// sessionFactory.getCurrentSession().createCriteria(HrsEmpTrmn.class);
	// criteria.setProjection(Projections.count("EMPNO"));
	// criteria.add(Restrictions.eq("EMPNO", employerNumber));
	// Long count = (Long) criteria.uniqueResult();
	// return count.intValue();
	// }

	@Override
	public String getExecutionDateTermnate(Integer employerNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsEmpTerminate.class);
		criteria.setProjection(Projections.property("executedDate"));
		criteria.add(Restrictions.eq("employeNumber", employerNumber));
		String executedDateTerminate = (String) criteria.uniqueResult();
//		return executedDateTerminate;
		if (executedDateTerminate != null) {
			Integer status = getActifEmployer(employerNumber);
			if (status > 0)
				return null;
			else
				return executedDateTerminate;
		}
		return null;
	}

	@Override
	public void updateVacationAfterInit(Integer employeeVacationId) {
		HrEmployeeVacation employeeVacation = (HrEmployeeVacation) sessionFactory.getCurrentSession()
				.get(HrEmployeeVacation.class, employeeVacationId);
		employeeVacation.setHaveInitiation(1);
		sessionFactory.getCurrentSession().merge(employeeVacation);

	}

	@Override
	public Map<String, Integer> getEmployeeInfo() {
		String hql = " Select userId from ArcUsers  V ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Map<String, Integer> employeesMap = new HashMap<>();

		List<Integer> usersIds = query.list();
		// usersIds.size());
		for (Integer id : usersIds) {

			String hql2 = " Select firstName from ArcUsers V Where  V.userId=:id ";
			Query query2 = sessionFactory.getCurrentSession().createQuery(hql2);
			query2.setParameter("id", id);

			List<String> usersNames = query2.list();
			if (id == 59) {
			}
			employeesMap.put(usersNames.get(0), id);
		}

		return employeesMap;
	}

	@Override
	@Transactional
	public Map<String, Integer> getEmployeeInDeptInfo() {

		String hql = " Select userId from ArcUsers  V where V.deptId=:currentUserIdDept ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("currentUserIdDept", Utils.findCurrentUser().getDeptId());
		List<Integer> usersIds = query.list();
		Map<String, Integer> employeesMap = new HashMap<>();

		for (Integer id : usersIds) {
			String hql2 = " Select concat(V.firstName , ' ', V.lastName)   from ArcUsers V Where  V.userId=:id ";
			Query query2 = sessionFactory.getCurrentSession().createQuery(hql2);
			query2.setParameter("id", id);
			List<String> usersNames = query2.list();

			employeesMap.put(usersNames.get(0), id);
		}

		return employeesMap;
	}

	@Override
	public List<PayBank> getAllBanks() {

		return loadAll(PayBank.class);
	}

	@Override
	public int getUsedTotalVacationPeriod(int empNB) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrEmployeeVacation.class);
		criteria.setProjection(Projections.sum("vacationPeriod"));
		criteria.add(Restrictions.eq("employeeNumber", empNB));
		criteria.add(Restrictions.eq("vacationType", MyConstants.NORMAL_VACATION));
		criteria.add(Restrictions.eq("vacationStatus", MyConstants.YES));
		criteria.add(Restrictions.sqlRestriction("substr(VACAEND,7,4)||substr(VACAEND,4,2)||substr(VACAEND,1,2) <= ?",
				HijriCalendarUtil.findCurrentHijriDate().substring(6, 10)
						+ HijriCalendarUtil.findCurrentHijriDate().substring(3, 5)
						+ HijriCalendarUtil.findCurrentHijriDate().substring(0, 2),
				StringType.INSTANCE));

		Long count = (Long) criteria.uniqueResult();
		int result = 0;
		if (count != null)
			result = count.intValue();
		return result;
	}

	// @Override
	// public HrsEmpHistorical getEmployeeLastHistoryRecord(Integer id) {
	//
	// String hql = "from HrsEmpHistorical h where h.id.EMPNO=:wrkApplicationId
	// order by app.id.stepId DESC";
	//
	// Query query = sessionFactory.getCurrentSession().createQuery(hql);
	// query.setParameter("wrkApplicationId", id);
	// List<HrsEmpHistorical> List = query.list();
	// return List.get(0);
	//
	// }
	@Override
	public int getUsedNormalYearlyPeriod(int empNB) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String myDate = sdf.format(cal.getTime());
		// String dd = now.substring(0, 2);
		// String mm = now.substring(3, 5);
		// int yy = Integer.parseInt(now.substring(6, 10)) - 1;

		// try {
		// Date beforeYearDate = sdf.parse(dd + "/" + mm + "/" + yy);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// String myDate = dd + "/" + mm + "/" + yy;
		// int [] typeVac = { MyConstants.NORMAL_VACATION,
		// MyConstants.EXTENSION_NORMAL_VACATION };
		// String hql = "SELECT NVL (SUM (vacaprd), 0) FROM HRS_EMP_VCTON WHERE
		// VACATYP IN (:normalVac, :normalVacExt) AND empno = :empNB AND
		// VAC_ACCPT_Y_N = 'Y' AND TO_DATE (date_converter2 (VACASTRT),
		// 'DD/MM/YYYY') >= TO_DATE (:myDate, 'DD/MM/YYYY')";
		// Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		// List<Integer> normalTypes = new ArrayList<>();
		// normalTypes.add(MyConstants.NORMAL_VACATION);
		// normalTypes.add(MyConstants.EXTENSION_NORMAL_VACATION);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrEmployeeVacation.class);
		criteria.setProjection(Projections.sum("vacationPeriod"));
		// criteria.add(Restrictions.sqlRestriction(
		// "substr(VACASTRT,7,4)||substr(VACASTRT,4,2)||substr(VACASTRT,1,2) <=
		// ?",
		// HijriCalendarUtil.findCurrentHijriDate().substring(6, 10)
		// + HijriCalendarUtil.findCurrentHijriDate().substring(3, 5)
		// + HijriCalendarUtil.findCurrentHijriDate().substring(0, 2),
		// StringType.INSTANCE));
		criteria.add(
				Restrictions.sqlRestriction("substr(VACASTRT,7,4)||substr(VACASTRT,4,2)||substr(VACASTRT,1,2) >= ?",
						HijriCalendarUtil.findCurrentHijriDate().substring(6, 10) + "01" + "01", StringType.INSTANCE));
		// criteria.add(Restrictions.eq("normalVac",
		// MyConstants.NORMAL_VACATION));
		Object[] values = { MyConstants.NORMAL_VACATION, MyConstants.EXTENSION_NORMAL_VACATION };
		criteria.add(Restrictions.in("vacationType", values));
		criteria.add(Restrictions.eq("employeeNumber", empNB));
		criteria.add(Restrictions.eq("vacationStatus", MyConstants.YES));
		// query.setParameter("normalVac", MyConstants.NORMAL_VACATION);
		// query.setParameter("normalVacExt",
		// MyConstants.EXTENSION_NORMAL_VACATION);
		// query.setParameter("empNB", empNB);
		// query.setParameter("myDate", myDate);
		Long count = (Long) criteria.uniqueResult();
		int result = 0;
		if (count != null)
			result = count.intValue();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HrEmployeeVacation loadLastNormalVacation(int empNB) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrEmployeeVacation.class);
		criteria.add(Restrictions.eq("employeeNumber", empNB));
		// sort desc criteria.add(Restrictions.)
		Criterion crit1 = Restrictions.eq("vacationType", MyConstants.NORMAL_VACATION);
		Criterion crit2 = Restrictions.eq("vacationType", MyConstants.EXTENSION_NORMAL_VACATION);
		Disjunction or = Restrictions.disjunction();
		or.add(crit1);
		or.add(crit2);
		criteria.add(or);
		criteria.add(Restrictions.eq("vacationStatus", MyConstants.YES));
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(2);
		List<HrEmployeeVacation> vacationsList = criteria.list();
		// HrEmployeeVacation result = null;
		int finalPeriod = 0;
		HrEmployeeVacation myVacation = null;
		if ((vacationsList.size() > 0) && (vacationsList.get(0).getVacationPeriod() != null)) {

			finalPeriod = vacationsList.get(0).getVacationPeriod();
			// if(vacationsList == null)
			// result = new HrEmployeeVacation();
			if ((vacationsList.get(0).getVacationType() == MyConstants.EXTENSION_NORMAL_VACATION)
					&& (vacationsList.size() == 2)) {
				finalPeriod = vacationsList.get(0).getVacationPeriod() + vacationsList.get(1).getVacationPeriod();
			}
			myVacation = new HrEmployeeVacation(vacationsList.get(0));
			myVacation.setVacationPeriod(finalPeriod);
		}

		return myVacation;
	}

	@Override
	public String getLastServiceDate(Integer empNB) {

		String hql = "select max(substr(VACWRKto,7,4)||substr(VACWRKto,4,2)||substr(VACWRKto,1,2)) from HRS_EMP_VCTON where empno = :empNB and vac_accpt_y_n = 'Y' and VACATYP in (:normalVac,:normalVacExt)";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		query.setParameter("normalVac", MyConstants.NORMAL_VACATION);
		query.setParameter("normalVacExt", MyConstants.EXTENSION_NORMAL_VACATION);
		query.setParameter("empNB", empNB);

		String result = (String) query.uniqueResult();
		if (result == null)
			return "";
		String myDate = Utils.reverseNumberToDate(result);
		return HijriCalendarUtil.addDaysToHijriDate(myDate, 1);

	}

	@Override
	public boolean checKIsManager(int userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkRefrentionalSetting.class);
		criteria.setProjection(Projections.count("managerId"));
		criteria.add(Restrictions.eq("managerId", userId));
		Long id = (Long) criteria.uniqueResult();
		return (id > 0);
	}

	@Override
	@Transactional
	public Integer getUserIdFromWorkAppByIdAndStepId(Integer recordId, Integer stepId) {
		String hql = "select fromUserId  from WrkApplication  app  where app.arcRecordId=:recordId and app.id.stepId=:stepId ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("recordId", recordId);
		query.setParameter("stepId", stepId);
		List<Integer> list = query.list();
		return list.get(0);

	}

	@Override
	public List<HrsTrainingPlace> loadTrainingPlaces() {
		return loadAll(HrsTrainingPlace.class);
	}

	@Override
	public HrsTrainingMandate loadTrainingMandate(int rank) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsTrainingMandate.class);
		criteria.add(Restrictions.eq("rankCode", rank));
		return (HrsTrainingMandate) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public HrsScenarioDocument getDestinationModel(int modelId, int stepId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsScenarioDocument.class);
		criteria.add(Restrictions.eq("modelId", modelId));
		criteria.add(Restrictions.eq("stepId", stepId));
		return (HrsScenarioDocument) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public int getEmpNoTraining(int trainId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsEmployeeTraining.class);
		criteria.setProjection(Projections.property("employeeNumber"));
		criteria.add(Restrictions.eq("id", trainId));
		int empNumber = (int) criteria.uniqueResult();
		return empNumber;
	}

	@Override
	@Transactional
	public String getPropertiesValueById(int id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SysProperties.class);
		criteria.setProjection(Projections.property("value"));
		criteria.add(Restrictions.eq("id", id));
		String value = (String) criteria.uniqueResult();
		return value;
	}

	public List<Integer> getAuthorizedEmployeesForModel(int modelId) {
		List<Integer> authorizedIDs = new ArrayList<Integer>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsScenarioDocument.class);
		criteria.add(Restrictions.eq("modelId", modelId));
		List<HrsScenarioDocument> scenarioDocuments = criteria.list();
		if (scenarioDocuments != null && !scenarioDocuments.isEmpty())
			for (HrsScenarioDocument hrsScenarioDocument : scenarioDocuments) {
				Integer id = Integer
						.parseInt(((SysProperties) findEntityById(SysProperties.class, hrsScenarioDocument.getToId()))
								.getValue());
				authorizedIDs.add(id);
			}
		return authorizedIDs;
	}

	@Override
	public void injectFactory(Object sessionFactory) {
		this.sessionFactory = (SessionFactory) sessionFactory;
	}

	@Override
	public EmployeeInitiation getLastEmployeeInitiation() {
		String hql = "  from EmployeeInitiation  E  orderBy DESC ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		List<EmployeeInitiation> list = query.list();
		if (list != null && !list.isEmpty())
			return list.get(0);
		else {
			return null;
		}

	}

	@PreDestroy
	public void test() {
	}

	@Override
	public Long getOutBoxmailsNumbers(Integer userId) {
		String hql = "SELECT count(*) FROM WrkApplication W where  W.fromUserId=:userId ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		query.setParameter("userId", userId);
		return (Long) query.list().get(0);
	}

	@Override
	public int getAuthorizationsNumber(int userId, String month, String year) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsUserAbsent.class);
		criteria.setProjection(Projections.count("id"));
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(
				Restrictions.sqlRestriction("substr(ABSDATE,7,4)||substr(ABSDATE,4,2)||substr(ABSDATE,1,2) <= ?",
						HijriCalendarUtil.findCurrentHijriDate().substring(6, 10)
								+ HijriCalendarUtil.findCurrentHijriDate().substring(3, 5) + "30",
						StringType.INSTANCE));
		criteria.add(
				Restrictions.sqlRestriction("substr(ABSDATE,7,4)||substr(ABSDATE,4,2)||substr(ABSDATE,1,2) >= ?",
						HijriCalendarUtil.findCurrentHijriDate().substring(6, 10)
								+ HijriCalendarUtil.findCurrentHijriDate().substring(3, 5) + "01",
						StringType.INSTANCE));
		Criterion permission = Restrictions.eq("absType", MyConstants.PERMISSION);
		Criterion perException = Restrictions.eq("absType", MyConstants.SPECIAL);
		LogicalExpression perOrExcep = Restrictions.or(permission, perException);
		criteria.add(perOrExcep);
		// criteria.add(Restrictions.eq("absType", MyConstants.PERMISSION));
		criteria.add(Restrictions.eq("accept", MyConstants.YES));
		Long count = (Long) criteria.uniqueResult();
		return count.intValue();
	}

	@Override
	public Long getInboxNumbers(Integer userId) {
		String hql = "SELECT count(*) FROM WrkApplication W where  W.toUserId=:userId ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		query.setParameter("userId", userId);

		return (Long) query.list().get(0);

	}

	@Override
	public Long getUnReadMails(Integer userId) {
		String hql = "SELECT count(*) FROM WrkApplication W where  W.toUserId=:userId and W.applicationIsRead=0 and W.applicationIsVisible=1 ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		query.setParameter("userId", userId);

		return (Long) query.list().get(0);

	}

	@Override
	public Long getUnReadMailsForToday(Integer userId) {
		String hql = "SELECT count(*) FROM WrkApplication W where  W.toUserId=:userId and W.applicationIsRead=0 and W.hijriDate=:HDate ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		// HijriCalendarUtil hijriCalendarUtil = new HijriCalendarUtil();
		query.setParameter("userId", userId);
		query.setParameter("HDate", HijriCalendarUtil.findCurrentHijriDate());

		return (Long) query.list().get(0);

	}

	@Override
	public Long getExcuseVacationCount(Integer empNumber) {
		String hql = "SELECT count(*) FROM HrEmployeeVacation  V where  V.employeeNumber=:empNumber and V.vacationPeriod<5  ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		// HijriCalendarUtil hijriCalendarUtil = new HijriCalendarUtil();
		query.setParameter("empNumber", empNumber);

		return (Long) query.list().get(0);
	}

	@Override
	public Long getNormalVacationCount(Integer empNumber) {
		String hql = "SELECT count(*) FROM HrEmployeeVacation  V where  V.employeeNumber=:empNumber and V.vacationPeriod>4  ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		// HijriCalendarUtil hijriCalendarUtil = new HijriCalendarUtil();
		query.setParameter("empNumber", empNumber);
		return (Long) query.list().get(0);
	}

	@Override
	public String getHashedPassword(String userName, String password) {
		String x = "-1";
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ ?=call WRK_PASSWORD(?,?)}";
		try {
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.setString(2, userName);
			callableStatement.setString(3, password);
			callableStatement.executeUpdate();
			x = (String) callableStatement.getObject(1);
			connection.close();
		} catch (Exception e) {
		}

		return x;
	}

	// @Override
	// public String updatePassword(String userName, String password, String
	// newPassword) throws Exception {
	// String x = "-1";
	// Connection connection = new DataBaseConnectionClass().getConnection();
	// String sql = "{ ?=call WRK_PASSWORD(?,?)}";
	//
	// CallableStatement callableStatement = connection.prepareCall(sql);
	// callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
	// callableStatement.setString(2, userName);
	// callableStatement.setString(3, password);
	// callableStatement.executeUpdate();
	// x = (String) callableStatement.getObject(1);
	// connection.close();
	// if (!x.equals("-1"))
	// return x;
	// else
	// throw new Exception();
	// }

	@Override
	public void updateUserPasswordForSystem(Integer userId, String hashedNewPassword) throws Exception {

		String hql = "UPDATE ArcUsers x set x.password=:hashedNewPassword   WHERE x.userId=:userId ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("hashedNewPassword", hashedNewPassword);
		query.setParameter("userId", userId);

		query.executeUpdate();
	}

	@Override
	public void updateUserPasswordForSign(Integer userId, String hashedNewPassword) throws Exception {
		ArcUsers user = (ArcUsers) findEntityById(ArcUsers.class, userId);
		WrkProfile wrkProfile = (WrkProfile) findEntityById(WrkProfile.class, userId);
		wrkProfile.setSignPassword(hashedNewPassword);
		update(wrkProfile);
		user.setWrkProfile(wrkProfile);
		update(user);

		// String hql = "UPDATE WrkProfile x set
		// x.signPassword=:hashedNewPassword WHERE x.userId=:userId ";
		// Query query = sessionFactory.getCurrentSession().createQuery(hql);
		// query.setParameter("hashedNewPassword", hashedNewPassword);
		// query.setParameter("userId", userId);
		//
		// query.executeUpdate();
	}

	@Override
	public ArcUsers getUserNameById(Integer userId) throws Exception {
		String hql = " FROM ArcUsers  V where  V.userId=:userId ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		query.setParameter("userId", userId);
		if (query.list() != null && !query.list().isEmpty())
			return (ArcUsers) query.list().get(0);
		else
			throw new Exception();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayMaster> loadAllPayMasters() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayMaster.class);
		criteria.addOrder(Order.asc("accountNumber"));
		return criteria.list();
	}

	@Override
	public List<WrkRoles> getallRoles() throws Exception {
		String hql = " FROM WrkRoles  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<WrkRoles> roles = query.list();
		if (roles != null && !roles.isEmpty())
			return roles;
		else
			throw new Exception();
	}

	@Override
	public List<Charging> getallCharging() {

		String hql = " FROM Charging x Where  x.chargingStatus=1 or  x.chargingStatus=2";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		List<Charging> charges = query.list();
		if (charges != null && !charges.isEmpty())
			return charges;
		else
			return new ArrayList<>();
	}

	@Override
	public void deletePayBillDetails(PayBillDetails selectedItem) {
		delete(selectedItem);
	}

	@Override
	@Transactional
	public int findMaxBillId() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayLicBills.class);
		criteria.setProjection(Projections.max("billNumber"));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id + 1);
	}

	@Override
	@Transactional
	public int findMaxHrsCompactPerformance() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsCompactPerformance.class);
		criteria.setProjection(Projections.max("id"));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id + 1);
	}

	@Override
	@Transactional
	public int findMaxHrsFloorsBase() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsCompactBaseFloor.class);
		criteria.setProjection(Projections.max("id"));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id + 1);
	}

	@Override
	@Transactional
	public int findMaxHrsFloors() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsCompactFloors.class);
		criteria.setProjection(Projections.max("id"));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id + 1);
	}

	@Override
	public List<NationalIdPlaces> getallNationalIdPlaces() {
		String hql = " FROM NationalIdPlaces  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<NationalIdPlaces> nationalIdPlaces = query.list();

		return nationalIdPlaces;

	}

	@Override
	public List<NationalIdType> getAllNationalIdTypes() {
		String hql = " FROM NationalIdType  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<NationalIdType> nationalIdType = query.list();

		return nationalIdType;

	}

	@Override
	public List<Nationality> getallNationalities() {
		String hql = " FROM Nationality  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Nationality> nationalities = query.list();

		return nationalities;

	}

	@Override
	public List<Project> getallProjects() {
		String hql = " FROM Project  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Project> Project = query.list();

		return Project;

	}

	@Override
	public List<ProjectContract> getallProjectContract(Integer projectId) {

		String hql = " FROM ProjectContract p where p.projectId=:projectId  ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("projectId", projectId);

		List<ProjectContract> contracts = query.list();
		return contracts;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PayLicBills> loadAllBills() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayLicBills.class);
		criteria.addOrder(Order.desc("billNumber"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayBillDetails> loadBillDetails(Integer billNumber) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayBillDetails.class);
		criteria.add(Restrictions.eq("billNumber", billNumber));
		return criteria.list();
	}

	@Override
	public List<DocumentScenario> findScenariosByModelId(int pmodelId) {
		String hql = " FROM DocumentScenario p where p.modelId=:pmodelId  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("pmodelId", pmodelId);
		List<DocumentScenario> contracts = query.list();
		return contracts;
	}

	@Override
	public ArcPeople findArcPeopleById(long nationalId) {
		return get(ArcPeople.class, nationalId);
	}

	@Override
	public int findLicApplicationIdByRecord(int arcRecordId) {
		int resultId = -1;
		String sql = "select NVL(LIC_ID,0) licId from LIC_TRD_ARCHIVE where ARC_ID = :pArcRecord";
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
		sqlQuery.setParameter("pArcRecord", arcRecordId);
		List result = sqlQuery.list();
		if (result == null || result.isEmpty()) {
		} else {
			resultId = ((BigDecimal) result.get(0)).intValue();
		}

		return resultId;
	}

	@Override
	public List<TenderItems> getAllTenderItems() {
		String hql = " FROM TenderItems   ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		List<TenderItems> items = query.list();
		return items;

	}

	@Override
	@Transactional
	public int getNextArcId() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcRecords.class);
		criteria.setProjection(Projections.max("ID"));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id + 1);
	}

	@Override
	@Transactional
	public void refresh(Object myObject) {

		sessionFactory.getCurrentSession().setCacheMode(CacheMode.REFRESH);

	}

	@Override
	@Transactional
	public Integer getWrkIdByArc(int arcRecordId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkApplication.class);
		criteria.add(Restrictions.eq("arcRecordId", arcRecordId));
		criteria.add(Restrictions.eq("id.stepId", 1));
		criteria.setProjection(Projections.property("id.applicationId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<WrkApplication> getWrkApplicationListByRecordId(Integer archRecordId) {
		String hql = " FROM  WrkApplication Where arcRecordId=:archRecordId order by id.stepId asc";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		query.setParameter("archRecordId", archRecordId);
		List<WrkApplication> items = new ArrayList<>();
		items = query.list();
		return items;

	}

	@Transactional
	@Override
	public Integer getArchRecParentFromLinkByRecordId(Integer childArchRecordId) {
		String hql = "Select A.arcRrecordChildId FROM  ArcRecordsLink A Where A.arcRecordParentId=:childArchRecordId";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("childArchRecordId", childArchRecordId);
		Integer arcRecordId = (Integer) query.list().get(0);
		return arcRecordId;
	}

	@Override
	public List<ArcRecordsLink> getChildsObjectsByParentRecordId(Integer parentArchRecordId) {
		String hql = " FROM  ArcRecordsLink A Where A.arcRecordParentId=:parentArchRecordId   ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		query.setParameter("parentArchRecordId", parentArchRecordId);
		List<ArcRecordsLink> items = new ArrayList<>();
		items = query.list();
		return items;
	}

	@Override
	public boolean isIncomeExist(Integer incomeNumber) {
		boolean result = false;
		String incomeNo = incomeNumber.toString();
		String hql = " FROM  ArcRecords Where incomeNo = :incomeNo";
		List l = sessionFactory.getCurrentSession().createQuery(hql).setParameter("incomeNo", incomeNo).list();
		if (!l.isEmpty()) {
			result = true;
		}
		return result;
	}

	@Override
	@Transactional
	public List<WhsWarehouses> getAllStores() {
		return loadAll(WhsWarehouses.class);

	}

	@Override
	@Transactional
	public List<FinFinancialYear> getAllFinYears() {
		return loadAll(FinFinancialYear.class);

	}

	@Override
	@Transactional
	public Integer getArcRecordParent(int arcRecordId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcRecordsLink.class);
		criteria.add(Restrictions.eq("arcRrecordChildId", arcRecordId));
		criteria.setProjection(Projections.property("arcRecordParentId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public int findEmployeeInboxCount(Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkApplication.class);
		criteria.setProjection(Projections.count("toUserId"));
		// criteria.add(Restrictions.eq("toUserId", userId));
		// criteria.add(Restrictions.eq("fromUserId", userId));
		Criterion toId = Restrictions.eq("toUserId", userId);
		Criterion fromId = Restrictions.eq("fromUserId", userId);
		LogicalExpression toOrfrom = Restrictions.or(toId, fromId);
		criteria.add(toOrfrom);
		Long count = (Long) criteria.uniqueResult();
		return count.intValue();
	}

	@Override
	public void addFolderForUser(WrkInboxFolder folderInfo) {
		save(folderInfo);

	}

	@Override
	public void addMailsToFolder(List<WrkUserFolderMail> mails) {
		for (WrkUserFolderMail wrkUserFolderMail : mails) {
			addMailToFolder(wrkUserFolderMail);
		}

	}

	@Override
	@Transactional
	public List<WrkInboxFolder> getAllfoldersForUser(int userId) {
		String hql = "  FROM  WrkInboxFolder A Where A.folderUserId=:userId   ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userId", userId);
		List<WrkInboxFolder> folders = new ArrayList<>();
		folders = query.list();

		return folders;
	}

	@Override
	@Transactional
	public List<WrkUserFolderMail> getAllaMailsInFolder(int folderId) {
		String hql = "  FROM  WrkUserFolderMail A Where A.folderMailId.folderId=:folderId   ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("folderId", folderId);
		List<WrkUserFolderMail> folderMails = new ArrayList<>();
		folderMails = query.list();

		return folderMails;

	}

	@Override
	@Transactional
	public int getFolderNameForWrkApp(int stepId, int wrkId) {
		String hql = " select folderMailId.folderId FROM  WrkUserFolderMail A Where A.folderMailId.wrkAppId=:wrkId   and  A.folderMailId.wrkAppStepId=:stepId  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("stepId", stepId);
		query.setParameter("wrkId", wrkId);
		if (!query.list().isEmpty()) {
			int folderId = (int) query.list().get(0);

			return folderId;
		} else {
			return 0;
		}
	}

	@Override
	@Transactional
	public WrkUserFolderMail getFolderMailInfoByMailIdStepNum(int stepId, int wrkId) {
		String hql = "  FROM  WrkUserFolderMail A Where A.folderMailId.wrkAppId=:wrkId   and  A.folderMailId.wrkAppStepId=:stepId  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("stepId", stepId);
		query.setParameter("wrkId", wrkId);
		if (!query.list().isEmpty()) {

			return (WrkUserFolderMail) query.list().get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void deleteMailFomFolder(WrkUserFolderMail folderMail) {
		WrkUserFolderMail deletedMail = load(WrkUserFolderMail.class, folderMail.getFolderMailId());
		delete(deletedMail);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArcRecords> searchArcRecords(String subject, String letter, Integer recordSender, Integer incomeNB,
			String letterFromNo, List<Integer> selectedStructIdList, boolean employer) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcRecords.class, "arcRec");
		if ((subject != null) && (!subject.trim().equals("")))
			criteria.add(Restrictions.like("recTitle", "%" + subject + "%"));
		// if ((letter != null) && (!letter.trim().equals(""))) {
		// criteria.createAlias("arcRec.wrkComment", "wrkComm");
		// criteria.add(Restrictions.like("wrkComm.longComment", "%" + letter +
		// "%"));
		// }
		if ((recordSender != null) && (recordSender != 0))
			criteria.add(Restrictions.eq("letterFrom", recordSender));
		if ((incomeNB != null) && (incomeNB != 0))
			criteria.add(Restrictions.like("incomeNo", "%" + incomeNB + "%"));
		// if ((selectedStructIdList != null) &&
		// (!selectedStructIdList.isEmpty()))
		// criteria.add(Restrictions.in("structId", selectedStructIdList));
		if ((letterFromNo != null) && (!letterFromNo.trim().equals("")))
			criteria.add(Restrictions.like("letterFromNo", "%" + letterFromNo + "%"));

		DetachedCriteria subCriteria = DetachedCriteria.forClass(WrkApplication.class);
		subCriteria.setProjection(Projections.property("arcRecordId"));
		// subCriteria.add(Restrictions.eq("fromUserId",
		// Utils.findCurrentUser().getUserId()));
		subCriteria.add(Restrictions.eq("toUserId", MyConstants.ARCHIVE_USER_ID));
		subCriteria.add(Restrictions.eq("applicationIsVisible", 1));

		DetachedCriteria subCriteriaUser = DetachedCriteria.forClass(ArcUsers.class);
		subCriteriaUser.setProjection(Projections.property("userId"));
		subCriteriaUser.add(Restrictions.in("wrkSectionId", selectedStructIdList));
		// subCriteria.add(Subqueries.propertiesIn(new String[] { "fromUserId"
		// }, subCriteriaUser));

		criteria.add(Subqueries.propertiesIn(new String[] { "id" }, subCriteria));
		subCriteria.add(Subqueries.propertiesIn(new String[] { "fromUserId" }, subCriteriaUser));
		criteria.add(Subqueries.propertiesIn(new String[] { "id" }, subCriteria));

		if (!employer) {
			subCriteria.add(Restrictions.eq("fromUserId", Utils.findCurrentUser().getUserId()));
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WrkSection> loadArcDocumentStructList() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkSection.class);
		// criteria.addOrder(Order.desc("structId"));
		return criteria.list();
	}

	private Connection conn;

	@Override
	public Connection findConnection() {

		sessionFactory.openSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				conn = connection;
			}
		});
		return conn;
	}

	// @Override
	// @Transactional
	// public synchronized int createArcRecordsId() {
	// String sql = readQuery("createArcRecId");
	// SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
	// Object result = query.uniqueResult();
	// return Integer.parseInt(result.toString());
	// }
	@Transactional
	public synchronized int createArcRecordsId() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcRecords.class);
		criteria.setProjection(Projections.max("id"));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id + 1);
	}

	@Override
	@Transactional
	public List<EmployeeForDropDown> getSpecialEmployeeList() {

		String hql = "  from WrkSpecialAddress  V where V.id.userId=:userId ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userId", Utils.findCurrentUser().getUserId());
		List<WrkSpecialAddress> usersIds = query.list();
		Map<String, Integer> employeesMap = new HashMap<>();
		List<EmployeeForDropDown> specialEmpList = new ArrayList<>();

		for (WrkSpecialAddress id : usersIds) {
			if (id.getId().getUserInListId() != MyConstants.ARCHIVE_USER_ID) {
				String hql2 = " Select concat(V.firstName , ' ', V.lastName)   from ArcUsers V Where  V.userId=:id ";
				Query query2 = sessionFactory.getCurrentSession().createQuery(hql2);
				query2.setParameter("id", id.getId().getUserInListId());
				List<String> usersNames = query2.list();

				EmployeeForDropDown employeeForDropDown = new EmployeeForDropDown();
				employeeForDropDown.setId(id.getId().getUserInListId());
				employeeForDropDown.setName(usersNames.get(0));
				specialEmpList.add(employeeForDropDown);
				// employeesMap.put(usersNames.get(0), id);
			} else {
				continue;
			}
		}

		return specialEmpList;
	}

	@Override
	public ArcUsers getUserByLoginName(String loginName) {
		String hql2 = "    from ArcUsers V Where  V.loginName=:loginName ";
		Query query2 = sessionFactory.getCurrentSession().createQuery(hql2);
		query2.setParameter("loginName", loginName);
		List<ArcUsers> users = query2.list();

		return users.get(0);
	}

	@Override
	@Transactional
	public void addNewArticleGroup(ArticleGroup articleGroup) {
		sessionFactory.getCurrentSession().merge(articleGroup);

	}

	@Override
	@Transactional
	public List<ArticleGroup> getAllArticleGroups() {
		// TODO Auto-generated method stub
		return loadAll(ArticleGroup.class);
	}

	@Override
	@Transactional
	public void addNewArticleSubGroup(ArticleSubGroup articleSubGroup) {
		sessionFactory.getCurrentSession().merge(articleSubGroup);
	}

	@Override
	@Transactional
	public List<ArticleSubGroup> getAllArticleSubGroups() {
		// TODO Auto-generated method stub
		return loadAll(ArticleSubGroup.class);
	}

	@Override
	@Transactional
	public void addNewArticle(Article article) {
		sessionFactory.getCurrentSession().merge(article);

	}

	@Override
	@Transactional
	public void addNewPromotion(HrsEmpHistorical oldEmpHistorical, HrsEmpHistorical newEmpHistorical2) {
		sessionFactory.getCurrentSession().update(oldEmpHistorical);
		sessionFactory.getCurrentSession().save(newEmpHistorical2);

	}

	@Override
	@Transactional
	public List<Article> getAllArticles(Integer strNo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Article.class);
		criteria.add(Restrictions.eq("strNo", strNo));
		return criteria.list();
	}

	@Override
	@Transactional
	public List<ItemUnite> getAllUnites() {
		// TODO Auto-generated method stub
		return loadAll(ItemUnite.class);
	}

	@Override
	public List<Integer> getAttachmentIdsByRecordId(int arcRecordID) {
		String hql2 = "select  V.arcRecAttId.attachId   from arcRecAtt V Where  V.arcRecAttId.recordId=:arcRecordID ";
		Query query2 = sessionFactory.getCurrentSession().createQuery(hql2);
		query2.setParameter("arcRecordID", arcRecordID);
		List<Integer> ids = query2.list();

		return ids;

	}

	@Override
	public List<WrkDept> findAllDepartments() {
		return loadAll(WrkDept.class);
	}

	@Override
	public WrkApplication findRecordLastStep(int recordId) {
		WrkApplication result = new WrkApplication();

		DetachedCriteria subSubCriteria = DetachedCriteria.forClass(WrkApplication.class);
		subSubCriteria.setProjection(Projections.min("id.applicationId"));
		subSubCriteria.add(Restrictions.eq("arcRecordId", recordId));

		DetachedCriteria subCriteria = DetachedCriteria.forClass(WrkApplication.class);
		subCriteria.setProjection(Projections.max("id.stepId"));
		subCriteria.add(Restrictions.eq("arcRecordId", recordId));
		// subCriteria.add(Restrictions.eq("id.applicationId", subSubCriteria));
		subCriteria.add(Subqueries.propertiesIn(new String[] { "id.applicationId" }, subSubCriteria));

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkApplication.class);
		criteria.add(Restrictions.eq("arcRecordId", recordId));
		criteria.add(Subqueries.propertiesIn(new String[] { "id.stepId" }, subCriteria));
		// criteria.add(Restrictions.eq("id.stepId", subCriteria));
		List<WrkApplication> lstReply = criteria.list();

		// String sqlQuery = "SELECT * FROM wrk_application WHERE app_id =
		// :recId "
		// + "AND step_id = (SELECT MAX (step_id) FROM wrk_application WHERE
		// app_id = :recId "
		// + "AND ID = (SELECT MIN(ID) FROM wrk_application WHERE app_id =
		// :recId)) ";
		// @SuppressWarnings({ "unchecked", "unchecked" })
		// List<WrkApplication> lstReply =
		// sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
		// .addEntity(WrkApplication.class).setParameter("recId",
		// recordId).list();
		if (lstReply.size() > 0 && lstReply != null) {
			result = lstReply.get(0);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArcUsers> loadTechnicalUsers() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcUsers.class);
		criteria.add(Restrictions.eq("deptId", 11));
		return criteria.list();
	}

	@Override
	@Transactional
	public List<ArticleSubGroup> getAllArticleSubGroupsByGroupId(int groupId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArticleSubGroup.class);
		criteria.add(Restrictions.eq("groupId", groupId));
		return criteria.list();
	}

	@Override
	public List<InventoryRecord> getAllInventoryByArticleId(int articleId, Integer inventoryId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InventoryRecord.class);
		criteria.add(Restrictions.eq("articleId", articleId));
		criteria.add(Restrictions.eq("inventoryMasterId", inventoryId));
		return criteria.list();
	}

	@Override
	public void deleteObject(Object object) {
		delete(object);
	}

	@Override
	public List<HrsMasterFile> loadHrsMasterEmployers() {
		return loadAll(HrsMasterFile.class);
	}

	@Override
	public List<HrEmployeeVacation> loadVacationInfo(Integer employerId, Integer vacationid) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrEmployeeVacation.class);
		criteria.add(Restrictions.eq("employeeNumber", employerId));
		criteria.add(Restrictions.eq("vacationType", vacationid));
		criteria.add(Restrictions.eq("vacationStatus", "Y"));

		List<HrEmployeeVacation> emps = criteria.list();
		return emps;

	}

	@Override
	public List<VacationsType> loadVacationsType() {
		return loadAll(VacationsType.class);
	}

	@Transactional
	public ArcRecords findRecordByIncomeNumber(int incomeNumber) {
		String hql = "  FROM  ArcRecords  Where incomeNo = :inNo  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("inNo", incomeNumber + "");
		if (!query.list().isEmpty()) {

			return (ArcRecords) query.list().get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<ArcPeople> loadnameEmployerbynationid(Long nationid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcPeople.class);
		criteria.add(Restrictions.eq("nationalId", nationid));
		List<ArcPeople> emps = criteria.list();
		return emps;

		// return loadAll(ArcPeople.class);

	}

	@Override
	public List<HrsJobCreation> loadHrJob(Integer catid, Integer status, Integer rank) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsJobCreation.class);
		criteria.add(Restrictions.eq("categoryId", catid));
	//	criteria.add(Restrictions.eq("jobstatus", status));
		if (rank != null)
			criteria.add(Restrictions.eq("rankCode", rank));
		List<HrsJobCreation> jobs = criteria.list();
		System.out.println("jobs size ----> " + jobs.size());
		return jobs;
	}

	@Override
	public List<Integer> findAllOldIncomes(int incomeNumber) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				"select distinct * from ( SELECT REC2 FROM ARC_RECORD_LINKING  start with rec2 = :incomeNum CONNECT BY PRIOR rec2 = rec1 union select :incomeNum from dual)");
		List<BigDecimal> l = query.setParameter("incomeNum", incomeNumber).list();
		List<Integer> list = new ArrayList<Integer>();
		for (BigDecimal object : l) {
			list.add(object.intValue());
		}

		return list;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<TechnicalUsers> loadNewTechnicalUsers() {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TechnicalUsers.class);
		return criteria.list();

	}

	@Transactional
	@Override
	public List<TechnicalResponse> getTechnicalResponsesByRecordId(Integer archRecordId) {
		String hql = " FROM  TechnicalResponse Where ddeId=:archRecordId ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		query.setParameter("archRecordId", archRecordId);
		List<TechnicalResponse> items = new ArrayList<>();
		items = query.list();
		return items;
	}

	@Override
	@Transactional
	public WrkComment findWrkCommentByAppNo(String appNum) {
		String hql = "  FROM  WrkComment  Where appNo = :app_no  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("app_no", appNum);
		if (!query.list().isEmpty()) {

			return (WrkComment) query.list().get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public boolean recordIslinked(int currentIncome) {
		String hql = "  FROM  ArcRecordLinking  Where id.newIncomeNumber = :app_no  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("app_no", currentIncome);
		if (!query.list().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public HrsJobCreation loadSelecredJob(Integer creatId, Integer status) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsJobCreation.class);
		criteria.add(Restrictions.eq("createId", creatId));
		if (status != null)
			criteria.add(Restrictions.eq("jobstatus", status));
		HrsJobCreation jobs = (HrsJobCreation) criteria.uniqueResult();
		return jobs;
	}

	@Transactional
	@Override
	public ArcRecordsLink getArcLinkByRecordId(Integer archRecordId) {
		String hql = " FROM  ArcRecordsLink Where arcRecordParentId=:archRecordId ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		query.setParameter("archRecordId", archRecordId);
		List<ArcRecordsLink> items = new ArrayList<>();
		items = query.list();
		if (!items.isEmpty()) {
			return items.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public Integer loadScoreSickVacation(Integer empNo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrEmployeeVacation.class);
		criteria.setProjection(Projections.sum("vacationPeriod"));
		criteria.add(Restrictions.eq("vacationType", MyConstants.SICK_VACATION));
		criteria.add(Restrictions.eq("vacationStatus", MyConstants.YES));
		criteria.add(Restrictions.eq("employeeNumber", empNo));
		Long count = (Long) criteria.uniqueResult();
		if (count == null)
			return 0;
		return count.intValue();
	}

	@Override
	public HrEmployeeVacation loadLastVacation(Integer empNB, int vacationType) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrEmployeeVacation.class);
		criteria.add(Restrictions.eq("vacationType", vacationType));
		criteria.add(Restrictions.eq("vacationStatus", MyConstants.YES));
		criteria.add(Restrictions.eq("employeeNumber", empNB));
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1);
		HrEmployeeVacation resut = (HrEmployeeVacation) criteria.uniqueResult();
		return resut;
	}

	@Override
	public Integer findLicTrdByArcrecordId(Integer arcRecordId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LicTrdArchive.class);
		criteria.setProjection(Projections.property("licId"));
		criteria.add(Restrictions.eq("arcId", arcRecordId));
		return (Integer) criteria.uniqueResult();
	}

	@Transactional
	@Override
	public List<HrsSalaryScaleDgrs> loadScaleDgree(Integer rankCode) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSalaryScaleDgrs.class);
		// criteria.setProjection(Projections.max("orderId"));
		criteria.add(Restrictions.eq("orderId", 3));
		criteria.add(Restrictions.eq("rankCode", rankCode));
		List<HrsSalaryScaleDgrs> dgree = criteria.list();
		return dgree;
	}

	@Transactional
	@Override
	public HrsSalaryScaleDgrs loadSelectedDegree(Integer dgreeId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSalaryScaleDgrs.class);
		criteria.add(Restrictions.eq("id", dgreeId));
		HrsSalaryScaleDgrs degree = (HrsSalaryScaleDgrs) criteria.uniqueResult();
		return degree;
	}

	@Override
	public HrsSalaryScaleDgrs loadSelectedDegree(Integer classCode, Integer rankCode) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSalaryScaleDgrs.class);
		criteria.add(Restrictions.eq("orderId", 3));
		criteria.add(Restrictions.eq("classCode", classCode));
		criteria.add(Restrictions.eq("rankCode", rankCode));
		HrsSalaryScaleDgrs degree = (HrsSalaryScaleDgrs) criteria.uniqueResult();
		return degree;

	}

	@Transactional
	@Override
	public Integer foundCountEmp(String firstApplicationDate, Integer cactegoryId) {
		// TODO Auto-generated method stub
		// select count(*) into isFound from HRS_MASTER_FILE
		// where substr(empno,1,4)=substr(:FAPPLDAT,8) || :catcod;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsMasterFile.class);
		criteria.setProjection(Projections.count("nationalNumber"));
		// criteria.add(Restrictions.eq("employeInChargingId",
		// firstApplicationDate));
		criteria.add(Restrictions.eq("cactegoryId", cactegoryId));
		criteria.add(Restrictions.sqlRestriction("substr(empno,1,4) = ?",
				firstApplicationDate.substring(7, 10) + cactegoryId, StringType.INSTANCE));
		Long count = (Long) criteria.uniqueResult();
		return count.intValue();
		// String sql = readQuery("findCountEmp");
		// Query query = sessionFactory.getCurrentSession().createQuery(sql);
		// query.setParameter("FAPPLDAT", firstApplicationDate);
		// query.setParameter("catcod", cactegoryId);
		// Long x = (Long) query.uniqueResult();
		// return x.intValue();
		//
		// Criteria criteria =
		// sessionFactory.getCurrentSession().createCriteria(HrsMasterFile.class);
		// criteria.add(Restrictions.sqlRestriction("substr(empno,1,4)=",
		// "substr("+firstApplicationDate+",8)"+cactegoryId, In))
	}

	@Transactional
	@Override
	public Integer getMaxEmployerNumber(String firstApplicationDate, Integer cactegoryId) {
		// TODO Auto-generated method stub
		// select (max(empno)+1) into :HRS_INF_001.empno from HRS_MASTER_FILE
		// where substr(empno,1,4)=substr(:FAPPLDAT,8) || :catcod;

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsMasterFile.class);
		criteria.setProjection(Projections.max("employeNumber"));
		criteria.add(Restrictions.eq("cactegoryId", cactegoryId));
		criteria.add(Restrictions.sqlRestriction("substr(empno,1,4) = ?",
				firstApplicationDate.substring(7, 10) + cactegoryId, StringType.INSTANCE));
		int x = (int) criteria.uniqueResult();
		return x + 1;
		// String sql = readQuery("maxempno");
		// Query query = sessionFactory.getCurrentSession().createQuery(sql);
		// query.setParameter("FAPPLDAT", firstApplicationDate);
		// query.setParameter("catcod", cactegoryId);
		// Integer x = (Integer) query.uniqueResult();
		// return x.intValue();
	}

	@Transactional
	@Override
	public Charging getChargingemployer(Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Charging.class);
		criteria.add(Restrictions.eq("employeInChargingId", userId));
		criteria.add(Restrictions.eq("chargingStatus", 1));
		criteria.add(Restrictions.sqlRestriction(
				"substr(START_TAKLEEF,7,4)+substr(START_TAKLEEF,4,2)+substr(START_TAKLEEF,1,2) <= ?",
				HijriCalendarUtil.findCurrentHijriDate().substring(6, 10)
						+ HijriCalendarUtil.findCurrentHijriDate().substring(3, 5)
						+ HijriCalendarUtil.findCurrentHijriDate().substring(0, 2),
				StringType.INSTANCE));
		criteria.add(Restrictions.sqlRestriction(
				"substr(end_takleef,7,4)+substr(end_takleef,4,2)+substr(end_takleef,1,2) >= ?",
				HijriCalendarUtil.findCurrentHijriDate().substring(6, 10)
						+ HijriCalendarUtil.findCurrentHijriDate().substring(3, 5)
						+ HijriCalendarUtil.findCurrentHijriDate().substring(0, 2),
				StringType.INSTANCE));
		Charging charge = (Charging) criteria.uniqueResult();

		// List<Charging> ChargingList = new ArrayList<>();
		// String sql = readQuery("charging");
		// Query query = sessionFactory.getCurrentSession().createQuery(sql);
		// query.setParameter("puserId", userId);
		// query.setParameter("datcharg",
		// HijriCalendarUtil.findCurrentHijriDate());
		// ChargingList = query.list();
		return charge;
	}

	@Transactional
	@Override
	public int getMaxSerialJobHistorical(String jobcode, Integer jobNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsJobHistorical.class);
		criteria.setProjection(Projections.max("serial"));
		criteria.add(Restrictions.eq("jobNumber", jobNumber));
		criteria.add(Restrictions.eq("jobCode", jobcode));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id + 1);
	}

	@Transactional
	@Override
	public int nationalIsFound(Long nationalNumber, Integer cat) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsMasterFile.class);
		criteria.setProjection(Projections.count("nationalNumber"));
		criteria.add(Restrictions.eq("nationalNumber", nationalNumber));
		criteria.add(Restrictions.eq("cactegoryId", cat));

		String id = criteria.uniqueResult().toString();
		return Integer.parseInt(id);
	}

	@Override
	@Transactional
	public HrsMasterFile loadEmployerData(Integer empno) {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsMasterFile.class);
		criteria.add(Restrictions.eq("employeNumber", empno));
		HrsMasterFile HrsMasterFile = (HrsMasterFile) criteria.uniqueResult();
		return HrsMasterFile;
	}

	@Override
	public HrsEmpHistorical loadEmployerDataHistorcial(Integer empno) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsEmpHistorical.class);
		criteria.add(Restrictions.eq("id.empno", empno));
		criteria.add(Restrictions.eq("flag", 1));
		HrsEmpHistorical HrsEmpHistorical = (HrsEmpHistorical) criteria.uniqueResult();
		return HrsEmpHistorical;
	}

	@Override
	public List<UserRoles> loadAllRoles() {
		return loadAll(UserRoles.class);

	}

	public List<FinEntity> loadSupliers() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FinEntity.class);
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.like("finEntityTypeUser", "%" + Utils.loadMessagesFromFile("importer") + "%"));
		or.add(Restrictions.like("finEntityTypeUser", "%" + Utils.loadMessagesFromFile("contractor") + "%"));
		criteria.add(or);
		List supliers = criteria.list();
		return supliers;

	}

	@Override
	public int deleteUserRole(Integer userId, Integer roleId) {
		String hql = "  delete FROM  RolePriv  Where userId = :userId and roleId = :roleId  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameter("roleId", roleId);
		return query.executeUpdate();
	}

	@Override
	public List<HrScenario> loadDistinctScenarios() {
		List<HrScenario> scenarioList = new ArrayList<>();
		String hql = Utils.readQuery("findScenarios");
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		for (Object myList : query.list()) {
			Object[] result = (Object[]) myList;
			HrScenario scenario = new HrScenario(Integer.parseInt("" + result[0]), "" + result[1],
					Integer.parseInt("" + result[2]));
			scenarioList.add(scenario);
		}
		return scenarioList;
	}

	@Override
	public Integer getCreatedId(String jobcode, Integer jobNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsJobCreation.class);
		criteria.add(Restrictions.eq("jobCode", jobcode));
		criteria.add(Restrictions.eq("jobNumber", jobNumber));
		// criteria.add(Restrictions.eq("",creatId));
		criteria.setProjection(Projections.property("createId"));
		criteria.add(Restrictions.eq("jobstatus", 3));
		Integer id = (Integer) criteria.uniqueResult();
		return id;
	}

	@Transactional
	public List<UserRoles> loadUserRoles(Integer userId) {
		String hql = "select new UserRoles(rd.id, rd.roleName,rd.roleDesc) FROM  RolePriv as rp, UserRoles as rd  Where rp.roleId = rd.id and rp.userId = :userId  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userId", userId);
		List<UserRoles> roles = (List<UserRoles>) query.list();
		return roles;

	}

	@Override
	public List<SysProperties> loadAllSysProperties() {
		return loadAll(SysProperties.class);
	}

	@Override
	public List<ArcApplicationType> loadNewScenarioModels() {
		DetachedCriteria subCriteria = DetachedCriteria.forClass(HrScenario.class);
		subCriteria.setProjection(Projections.property("modelId"));
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcApplicationType.class);
		criteria.add(Subqueries.propertiesNotIn(new String[] { "id" }, subCriteria));
		return criteria.list();
	}

	@Override
	public List<SysProperties> loadScenarioSysProperties() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SysProperties.class);
		criteria.add(Restrictions.eq("typeProperty", MyConstants.PROPERTIES_FOR_SCENARIO));
		return criteria.list();
	}

	@Override
	public List<BldPaperTypes> loadAllPaperTypes() {
		return loadAll(BldPaperTypes.class);
	}

	@Override
	public int saveNewBuilding(BldLicNew newBuilding) {
		int maxLicId = getMaxLicenceId();
		// if (wrkAppSequence < maxLicId)
		// wrkAppSequence = maxLicId;
		// else
		// wrkAppSequence++;
		newBuilding.setLicNewId(maxLicId);
		return save(newBuilding);
	}

	@Override
	public int saveAttBuilding(BldLicAttch attchBuilding) {
		int maxLicId = getMaxLicenceId();
		attchBuilding.setLicAttId(maxLicId);
		return save(attchBuilding);
	}

	// private int getMaxLicNewId() {
	// Criteria criteria =
	// sessionFactory.getCurrentSession().createCriteria(BldLicNew.class);
	// criteria.setProjection(Projections.max("licNewId"));
	// Integer id = (Integer) criteria.uniqueResult();
	// return (id == null) ? 1 : (id + 1);
	// }
	// private int getMaxLicAttId() {
	// Criteria criteria =
	// sessionFactory.getCurrentSession().createCriteria(BldLicAttch.class);
	// criteria.setProjection(Projections.max("licAttId"));
	// Integer id = (Integer) criteria.uniqueResult();
	// return (id == null) ? 1 : (id + 1);
	// }

	@Override
	public List<ArcUsers> getAllActiveEmployeesInDept(Integer departmentId) {
		List<ArcUsers> users = new ArrayList<ArcUsers>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcUsers.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (departmentId != null)
			criteria.add(Restrictions.eq("deptId", departmentId));
		criteria.add(Restrictions.eq("isActive", 1));
		users = criteria.list();
		return users;
	}

	@Override
	public List<BldLicBuildingUsage> loadAllBuildingUsages() {
		return loadAll(BldLicBuildingUsage.class);
	}

	@Override
	public List<LicAgents> loadAllEngineeringOffices() {
		return loadAll(LicAgents.class);
	}

	@Override
	public int saveNewBuildingWall(BldLicWall newBuilding) {
		int maxLicId = getMaxLicWallNewId();
		newBuilding.setLicWallId(maxLicId);
		return save(newBuilding);
	}

	private int getMaxLicWallNewId() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BldLicWall.class);
		criteria.setProjection(Projections.max("licWallId"));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id + 1);
	}

	@Override
	public void deleteNewBuildingPieces(Integer licNewId) {
		String hql = "delete FROM  BldLicPcs  Where id.licId = :licId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("licId", licNewId);
		query.executeUpdate();
	}

	@Override
	public PayLicBills loadBillByLicNo(Integer licNewId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayLicBills.class);
		criteria.add(Restrictions.eq("licenceNumber", licNewId));
		return (PayLicBills) criteria.uniqueResult();
	}

	private synchronized int getMaxLicenceId() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BldLicMasterTbl.class);
		criteria.setProjection(Projections.max("bldReqId"));
		Integer max = (Integer) criteria.uniqueResult();
		return (max == null) ? 1 : max + 1;
	}

	@Override
	public LicTrdMasterFile loadlicenceByNumber(String licenceNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LicTrdMasterFile.class);
		criteria.add(Restrictions.eq("licNo", licenceNumber));
		LicTrdMasterFile licence = (LicTrdMasterFile) criteria.uniqueResult();
		return licence;
	}

	@Override
	public List<User> getAllUsers() {
		return loadAll(User.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReqFinesMaster> loadAllPenalities(boolean notification) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReqFinesMaster.class);
		if (notification)
			criteria.add(Restrictions.eq("type", 1));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("fineNo"));
		return criteria.list();
	}

	@Override
	public List<ReqFinesSetup> getCodesFines() {
		return loadAll(ReqFinesSetup.class);
	}

	@Override
	public Integer getNumberLicencePenality(Integer fineCode, Integer fineNo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReqFinesDetails.class);
		criteria.setProjection(Projections.count("fineNo"));
		criteria.add(Restrictions.eq("fineNo", fineNo));
		criteria.add(Restrictions.eq("fineCode", fineCode));
		Long count = (Long) criteria.uniqueResult();
		return count.intValue();
	}

	public int nationalIsFoundarcpeople(Long identifierNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcPeople.class);
		criteria.setProjection(Projections.count("nationalId"));
		criteria.add(Restrictions.eq("nationalId", identifierNumber));
		String id = criteria.uniqueResult().toString();
		return Integer.parseInt(id);
	}

	@Override
	public ArcPeople loadnamebynationid(Long identifierNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcPeople.class);
		criteria.add(Restrictions.eq("nationalId", identifierNumber));
		ArcPeople emps = (ArcPeople) criteria.uniqueResult();
		return emps;
	}

	@Override
	public Integer getCountBill(Integer newBill) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayLicBills.class);
		criteria.setProjection(Projections.count("billNumber"));
		criteria.add(Restrictions.eq("billNumber", newBill));
		Long x = (Long) criteria.uniqueResult();

		return x.intValue();
	}

	@Override
	public PayLicBills getBillbyBillNumber(Integer newBill) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayLicBills.class);
		criteria.add(Restrictions.eq("billNumber", newBill));
		// TODO remove this line after finish link bill
		criteria.add(Restrictions.isNull("licenceNumber"));
		PayLicBills bill = (PayLicBills) criteria.uniqueResult();
		return bill;
	}

	@Override
	public List<PayBillDetails> getBillDetail(Integer newBill) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayBillDetails.class);
		criteria.add(Restrictions.eq("billNumber", newBill));
		List<PayBillDetails> bill = criteria.list();
		return bill;

	}

	@Override
	public Integer getMaxBillNumber() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayLicBills.class);
		criteria.setProjection(Projections.max("billNumber"));
		Integer x = (Integer) criteria.uniqueResult();
		return x;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getRecordIdFromHrsSignByDocId(Integer docId, Integer modelId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSigns.class);
		criteria.setProjection(Projections.property("arcRecordId"));
		criteria.add(Restrictions.eq("docId", docId));
		criteria.add(Restrictions.eq("modelId", modelId));
		List<Integer> docIds = criteria.list();
		if ((docIds == null) || (docIds.isEmpty()))
			return null;
		return docIds.get(0);
	}

	@Override
	public Integer getCountBillByFineNo(Integer fineNo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayLicBills.class);
		criteria.setProjection(Projections.count("billNumber"));
		criteria.add(Restrictions.eq("licenceNumber", fineNo));
		criteria.add(Restrictions.eq("licenceType", "F"));
		Long count = (Long) criteria.uniqueResult();
		return count.intValue();
	}

	@Override
	public List<ArcPeopleModel> loadAllPeoples() {
		return loadAll(ArcPeopleModel.class);

	}

	@Override
	@Transactional
	public Integer getMaxFineMAsterId() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReqFinesMaster.class);
		criteria.setProjection(Projections.max("fineNo"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<ReqFinesMaster> findListFinesMasterByNationalId(String nationalId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReqFinesMaster.class);
		criteria.add(Restrictions.eq("fIdNo", nationalId));
		return criteria.list();
	}

	@Override
	public List<BldLicHangover> loadAllRubbishCertificate() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BldLicHangover.class);
		criteria.addOrder(Order.desc("licHangoverId"));
		return criteria.list();
	}

	@Override
	public int saveRubbishCertificate(BldLicHangover bldLicHangover) {
		int maxLicId = getMaxLicenceId();
		bldLicHangover.setLicHangoverId(maxLicId);
		return save(bldLicHangover);
	}

	@Override
	public Integer getRecIdMasterTBLByLicenceId(Integer licId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BldLicMasterTbl.class);
		criteria.setProjection(Projections.property("bldReqRecId"));
		criteria.add(Restrictions.eq("bldReqId", licId));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public ArcPeopleModel loadArcPeopleModel(long nationalId) {
		return get(ArcPeopleModel.class, nationalId);
	}

	@Override
	public Integer loadOldIncomeNoFromLinking(String wrkIncomeNo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcRecordLinking.class);
		criteria.setProjection(Projections.property("id.oldIncomeNumber"));
		criteria.add(Restrictions.eq("id.newIncomeNumber", Integer.parseInt(wrkIncomeNo)));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<HrsMasterFile> loadEmpsActive() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsMasterFile.class);
		Object[] values = { 1, 2, 3, 4, 9 };
		criteria.add(Restrictions.eq("employerStatus", 1));
		criteria.add(Restrictions.in("cactegoryId", values));
		criteria.add(Restrictions.eq("salaryStop", 0));
		return criteria.list();
	}

	public List<HrsGovJobSeries> getHrsJobs() {
		String hql = " FROM HrsGovJobSeries order by job_g, job_spec, job_catseries ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<HrsGovJobSeries> hrsJobs = query.list();
		if (hrsJobs != null && !hrsJobs.isEmpty())
			return hrsJobs;
		return hrsJobs;

	}

	@Override
	public List<HrsCompactCatFloor> getfCatFloors() {
		return loadAll(HrsCompactCatFloor.class);

	}

	@Override
	public List<HrsFloors> getFloors() {
		return loadAll(HrsFloors.class);

	}

	@Override
	public List<HrsGovJobType> getAllHrsGovJobTypes() {
		return loadAll(HrsGovJobType.class);

	}

	@Override
	public List<HrsCompactCatFloor> getAllCatFloors() {
		return loadAll(HrsCompactCatFloor.class);

	}

	public List<Sys037> loadAllJobCategories() {
		return loadAll(Sys037.class);
	}

	@Override
	public List<HrsGovJob4> loadJobsByCategory(Integer categoryId, Integer rank) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsGovJob4.class);
		criteria.add(Restrictions.eq("CATegoryId", categoryId));
		if (rank != null) {
			criteria.add(Restrictions.like("jobCode", "%" + Utils.formatTwoDigits(rank)));
		}

		return criteria.list();
	}

	@Override
	public List<Sys112> loadAllDepartments() {
		return loadAll(Sys112.class);
	}

	@Override
	public List<HrsJobCreation> loadAllJobs() {
		return loadAll(HrsJobCreation.class);
	}

	@Override
	public List<Sys038> loadAllJobStatus() {
		return loadAll(Sys038.class);
	}

	@Override
	public HrsJobHistorical loadLastHistoricalJob(Integer jobNumber, String jobCode) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsJobHistorical.class);
		criteria.add(Restrictions.eq("jobNumber", jobNumber));
		// criteria.add(Restrictions.eq("jobYear", jobYear));
		criteria.add(Restrictions.eq("jobCode", jobCode));
		criteria.addOrder(Order.desc("serial"));
		criteria.setMaxResults(1);
		return (HrsJobHistorical) criteria.uniqueResult();
	}

	@Override
	public HrsYearsPrime findYearPrime(Integer year) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsYearsPrime.class);
		criteria.add(Restrictions.eq("primeYear", "" + year));
		return (HrsYearsPrime) criteria.uniqueResult();
	}

	@Override
	public void disableLastFlags() {
		String hql = "UPDATE HrsEmpHistorical set flag =0";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public Integer loadSerialEmpHistorical(Integer empNo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsEmpHistorical.class);
		criteria.setProjection(Projections.max("id.stepId"));
		criteria.add(Restrictions.eq("id.empno", empNo));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id + 1);
	}

	@Override
	public HrsJobHistorical loadEmployerLastJob(Integer empNo) {
		HrsJobHistorical jobHistory = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsJobHistorical.class);
		criteria.add(Restrictions.eq("employerNumber", empNo));
		criteria.addOrder(Order.desc("serial"));
		criteria.setMaxResults(1);
		List<HrsJobHistorical> jobsList = criteria.list();
		if ((jobsList != null) && (jobsList.size() > 0)) {
			jobHistory = jobsList.get(0);
		}
		return jobHistory;
	}

	@Override
	public List<HrsMasterFile> loadEmpSalary() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsMasterFile.class);
		// criteria.setProjection(Projections.max("id.stepId"));
		// criteria.add(Restrictions.eq("cactegoryId", catId));
		criteria.add(Restrictions.eq("employerStatus", 1));
		criteria.add(Restrictions.eq("salaryStop", 0));
		List<HrsMasterFile> emp = criteria.list();
		return emp;

	}

	@Override
	public List<Sys012> loadMonthsHijri() {
		// TODO Auto-generated method stub
		return loadAll(Sys012.class);
	}

	@Transactional
	@Override
	public List<HrsCompactGoals> findHrsCompactPerformGoals(int performId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsCompactGoals.class);
		criteria.add(Restrictions.eq("hrsCompactPerformid", performId));
		List<HrsCompactGoals> goals = criteria.list();
		return goals;
	}

	@Transactional
	@Override
	public List<HrsCompactCatFloor> findHrsCompactPerformFloors(int performId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsCompactCatFloor.class);
		criteria.add(Restrictions.eq("hrsCompactPerformid", performId));
		List<HrsCompactCatFloor> floors = criteria.list();
		return floors;
	}

	@Transactional
	@Override
	public List<HrsCompactEmpCaracter> findAllHrsCompactEmpCaracByPerformId(int performId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsCompactEmpCaracter.class);
		criteria.add(Restrictions.eq("hrsCompactPerformid", performId));
		List<HrsCompactEmpCaracter> caracters = criteria.list();
		return caracters;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HrsCompactPerformance> loadNotEvaluatedPerformances() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsCompactPerformance.class);
		Criterion stat1 = Restrictions.eq("status", 3);
		Criterion stat2 = Restrictions.eq("status", 5);
		LogicalExpression statusthreeOrFive = Restrictions.or(stat1, stat2);
		criteria.add(statusthreeOrFive);
		return criteria.list();
	}

	@Transactional
	@Override
	public HrsGeneralAppreciation findHrsGeneralAppreciationByPerformId(int performId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsGeneralAppreciation.class);
		criteria.add(Restrictions.eq("hrsCompactPerformanceid", performId));
		return (HrsGeneralAppreciation) criteria.uniqueResult();
	}

	@Override
	public Integer getRatingByValue(int result) {
		Integer rate = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsCompactRating.class);
		criteria.setProjection(Projections.property("id"));
		criteria.add(Restrictions.eq("value", result));
		rate = (Integer) criteria.uniqueResult();
		if (rate == 0)
			rate = 1;
		return rate;
	}

	@Override
	public List<HrsCompactBaseFloor> loadBaseFloors(Integer performanceId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsCompactBaseFloor.class);
		criteria.add(Restrictions.eq("hrsCompactPerformanceId", performanceId));
		return criteria.list();
	}

	@Override
	public List<HrsLoan> loadLoans(Integer empNB) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsLoan.class);
		if (empNB != null)
			criteria.add(Restrictions.eq("loanEmpno", empNB));
		return criteria.list();

	}

	@Override
	public List<HrsSalary> loadListSalary(int catid, Integer pmonth, int pyear) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSalary.class);
		criteria.add(Restrictions.eq("categoryCode", catid));
		criteria.add(Restrictions.eq("id.salMonth", pmonth));
		criteria.add(Restrictions.eq("id.salYear", pyear));
		return criteria.list();
	}

	@Override
	public List<ArcUsers> getemployersinusrs() {
		String hql = "FROM  ArcUsers US where US.isActive=1 and US.employeeNumber is not null ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<ArcUsers> results = query.list();
		return results;

	}

	@Override
	public List<HrsMasterFile> getAllLoanEmployees() {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(HrsMasterFile.class, "e");
		DetachedCriteria dc = DetachedCriteria.forClass(HrsLoan.class, "d");
		dc.add(Restrictions.isNotNull("d.loanEmpno"));
		dc.setProjection(Projections.property("d.loanEmpno"));
		c.add(Subqueries.propertyIn("e.employeNumber", dc));
		return c.list();
	}

	@Override
	public List<HrsLoanDetails> loadLoanDetails(Integer loanId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsLoanDetails.class);
		criteria.add(Restrictions.eq("loanId", loanId));
		criteria.addOrder(Order.asc("loanDetYear"));
		criteria.addOrder(Order.asc("loanDetMonth"));
		return criteria.list();
	}

	@Override
	@Transactional
	public WrkApplication getWrkApplication(Integer wrkId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkApplication.class);
		criteria.add(Restrictions.eq("id.applicationId", wrkId));
		criteria.add(Restrictions.eq("id.stepId", 1));
		return (WrkApplication) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HrsSalaryScale> loadAllSalayScales(Integer orderId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSalaryScale.class);
		criteria.add(Restrictions.eq("id.orderId", orderId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HrsSalaryScaleDgrs> loadAllSalayScaleDgrs(Integer orderId, Integer classCode) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSalaryScaleDgrs.class);
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.add(Restrictions.eq("classCode", classCode));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HrsAppreciationScale> findAppreciationScalesByFloorId(Integer floorId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsAppreciationScale.class);
		criteria.add(Restrictions.eq("floorId", floorId));
		return criteria.list();
	}

	@Override
	public List<HrsLoanType> loadAllLoanTypes() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsLoanType.class);
		// criteria.add(Restrictions.eq("isActiveYN", MyConstants.YES));
		return criteria.list();
	}

	@Override
	public List<Sys012> loadAllMonths() {
		return loadAll(Sys012.class);
	}

	@Override
	public List<SysTitle> loadAllTitles() {
		return loadAll(SysTitle.class);
	}

	@Override
	public List<WrkSection> loadSectionsByDeptId(Integer deptId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkSection.class);
		criteria.add(Restrictions.eq("deptId", deptId));
		return criteria.list();
	}

	@Override
	@Transactional
	public List<WrkJobs> loadJobsBySectionId(Integer wrkSectionId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkJobs.class);
		criteria.add(Restrictions.eq("sectionId", wrkSectionId));
		return criteria.list();
	}

	// @Override
	// public List<TstFinger> loadAllFingerByusersIdAndDate(List<Integer>
	// usersIdList, String DateStart, String DateFinish) {
	// Criteria criteria =
	// sessionFactory.getCurrentSession().createCriteria(TstFinger.class);
	// Criterion stat1 = Restrictions.in("userid", usersIdList);
	//
	//// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	//// Date dateStartx =null;
	//// Date dateFinishx=null;
	//// try {
	//// dateStartx = dateFormat.parse(DateStart);
	//// dateFinishx = dateFormat.parse(DateFinish);
	//// } catch (ParseException e) {
	//// // TODO Auto-generated catch block
	//// e.printStackTrace();
	//// }
	////
	//// if(DateStart!=null){
	////
	//// criteria.add( Restrictions.ge("vdate",dateStartx));
	//// }
	//// if(DateFinish!=null)
	//// {
	//// criteria.add(Restrictions.le("vdate", dateFinishx));
	//// }
	// criteria.add(stat1);
	// return criteria.list();
	// }

	@Override
	public List<FngTimeTable> loadTimeShift() {
		// TODO Auto-generated method stub
		return loadAll(FngTimeTable.class);
	}

	@Override
	public ArcRecords getArcRecord(Integer arcRecordId) {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcRecords.class);
		criteria.add(Restrictions.eq("id", arcRecordId));
		return (ArcRecords) criteria.uniqueResult();
	}

	@Override
	public WrkProfile loadProfileUser(Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkProfile.class);
		criteria.add(Restrictions.eq("userId", userId));
		return (WrkProfile) criteria.uniqueResult();
	}

	@Override
	public List<ArcPeople> loadAllArcPeoples() {
		return loadAll(ArcPeople.class);
	}

	@Override
	public List<HrsEmpOvertime> loadEmployerOvertimes(Integer empNB) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsEmpOvertime.class);
		criteria.add(Restrictions.eq("employeeNumber", empNB));
		return criteria.list();
	}

	@Override
	public List<HrsEmpOvertime> retriveOverTime(Integer catid, Integer frommonth, Integer tomonth, Integer pyear) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsEmpOvertime.class);
		criteria.add(Restrictions.eq("yearOverTime", pyear));
		criteria.add(Restrictions.eq("category", catid));
		criteria.addOrder(Order.desc("basicSalry"));
		criteria.add(Restrictions.between("monthOvertime", frommonth, tomonth));
		return criteria.list();
	}

	@Override
	public List<HrsEmpHistorical> loadPromotionJobs(Integer employerNB) {
		List<HrsEmpHistorical> list = new ArrayList<HrsEmpHistorical>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsEmpHistorical.class);
		criteria.add(Restrictions.eq("id.empno", employerNB));
		Object[] values = { 109, 106, 161, 166, 200, 201, 217, 219, 226, 235 };
		criteria.add(Restrictions.in("RecordType", values));
		criteria.addOrder(Order.desc("id.stepId"));
		list = criteria.list();

		System.out.println("list Size ---> " + list.size());
		return list;
	}

	@Override
	public List<EmpMoveType> loadEmpMoveType() {
		// List<EmpMoveType> moveList = new ArrayList<EmpMoveType>();
		//
		//
		// moveList = loadAll(EmpMoveType.class);
		// System.out.println("list Size ---> " + moveList.size());
		return loadAll(EmpMoveType.class);

	}

	@Override
	public List<InvestorType> loadInvestorType() {
		// TODO Auto-generated method stub
		return loadAll(InvestorType.class);
	}

	@Override
	public List<InvestorIdentityType> loadInvestorIdentityType() {
		// TODO Auto-generated method stub
		return loadAll(InvestorIdentityType.class);
	}

	@Override
	public List<InvestorStatus> loadInvestorStatus() {
		// TODO Auto-generated method stub
		return loadAll(InvestorStatus.class);
	}

	@Override
	public List<BuildingType> loadBuildingType() {
		// TODO Auto-generated method stub
		return loadAll(BuildingType.class);

	}

	@Override
	public List<ContractMainCategory> loadContractMainCategory() {
		return loadAll(ContractMainCategory.class);
	}

	@Override
	public List<ContractSubcategory> loadContractSubcategory() {
		return loadAll(ContractSubcategory.class);
	}

	@Override
	public List<ContractStatus> loadContractStatus() {
		return loadAll(ContractStatus.class);
	}

	@Override
	public HrsSalaryScaleDgrs loadSalaryScale(Integer rankCode, Integer basicSalary, int id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSalaryScaleDgrs.class);
		criteria.add(Restrictions.eq("rankCode", rankCode));
		criteria.add(Restrictions.eq("orderId", id));
		criteria.add(Restrictions.gt("firstSalary", basicSalary));
		criteria.addOrder(Order.asc("firstSalary"));
		criteria.setMaxResults(1);
		HrsSalaryScaleDgrs salary = null;
		List<HrsSalaryScaleDgrs> salariesList = criteria.list();
		if ((salariesList != null) && (salariesList.size() > 0))
			salary = salariesList.get(0);
		return salary;
	}

	@Override
	public HrsEmpHistorical loadEmpHistorical(Integer employerNumber, String jobCode, Integer jobNumber,
			Integer rankNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsEmpHistorical.class);
		criteria.add(Restrictions.eq("id.empno", employerNumber));
		criteria.add(Restrictions.eq("jobcode", jobCode));
		criteria.add(Restrictions.eq("jobNumber", jobNumber));
		criteria.add(Restrictions.eq("rankNumber", rankNumber));
		return (HrsEmpHistorical) criteria.uniqueResult();
	}

	@Override
	public int checkPeriodVacation(Integer empNB, Integer userid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrEmployeeVacation.class);
		criteria.setProjection(Projections.sum("vacationPeriod"));
		criteria.add(Restrictions.eq("employeeNumber", empNB));
		criteria.add(Restrictions.eq("vacationType", MyConstants.NORMAL_VACATION));
		criteria.add(Restrictions.eq("vacationStatus", MyConstants.YES));
		criteria.add(Restrictions.eq("createdById", userid));
		criteria.add(Restrictions.sqlRestriction("substr(VACASTRT,7,4) = ?",
				HijriCalendarUtil.findCurrentHijriDate().substring(6, 10), StringType.INSTANCE));
		criteria.add(Restrictions.lt("vacationPeriod", 4));
		Long count = (Long) criteria.uniqueResult();
		int result = 0;
		if (count != null)
			result = count.intValue();
		return result;
	}

	@Override
	public HrsGovJob4 loadHrsGovJob(String jobCode) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsGovJob4.class);
		criteria.add(Restrictions.eq("jobCode", jobCode));
		return (HrsGovJob4) criteria.uniqueResult();
	}

	@Override
	public List<HrsEmpTerminate> loadAllEmpTerminates() {
		return loadAll(HrsEmpTerminate.class);
	}

	@Override
	public List<Sys059> loadAllTerminateReasons() {
		return loadAll(Sys059.class);
	}

	@Override
	public List<Sys018> loadAllPayStatus() {
		return loadAll(Sys018.class);
	}

	@Override
	public List<Sys051> loadAllPayTypes() {
		return loadAll(Sys051.class);
	}

	@Override
	@Transactional
	public Integer getMaxEmpNoWorkers() {
		Object[] values = { 1, 2, 4 };
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsMasterFile.class);
		criteria.setProjection(Projections.max("employeNumber"));
		criteria.add(Restrictions.not(Restrictions.in("cactegoryId", values)));
		Integer id = (Integer) criteria.uniqueResult();
		return (id == null) ? 1 : (id + 1);
	}

	@Override
	public int loadMaxID() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSalaryScaleOrder.class);
		criteria.setProjection(Projections.max("id"));
		// criteria.add(Restrictions.eq("arcRecordId", arcRecord));
		Integer id = (Integer) criteria.uniqueResult();
		// return (id == null) ? 1 : (id + 1);
		return id;
	}

	@Override
	public User getNameFromUserById(Integer userId) {
		String hql = " FROM User  V where  V.userId=:userId ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		query.setParameter("userId", userId);
		if (query.list() != null && !query.list().isEmpty())
			return (User) query.list().get(0);
		else
			return null;
	}

	@Override
	public List<ArcRecords> loadRecordsInDepartment(Integer deptId, Integer mode) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcRecords.class);
		criteria.setProjection(Projections.projectionList().add(Projections.property("this.id"), "id")
				.add(Projections.property("this.incomeNo"), "incomeNo")
				.add(Projections.property("this.recTitle"), "recTitle"));
		// criteria.add(Restrictions.eq("letterTo", deptId));
		criteria.add(Restrictions.eq("letterTo", deptId));

		DetachedCriteria subCriteria2 = DetachedCriteria.forClass(WrkApplication.class);
		subCriteria2.setProjection(Projections.property("arcRecordId"));
		// subCriteria2.add(Restrictions.eq("toUserId",
		// Utils.findCurrentUser().getUserId()));
		subCriteria2.add(Restrictions.eq("applicationIsVisible", 1));

		DetachedCriteria subCriteria3 = DetachedCriteria.forClass(User.class);
		subCriteria3.setProjection(Projections.property("userId"));
		subCriteria3.add(Restrictions.eq("deptId", Utils.findCurrentUser().getDeptId()));
		subCriteria2.add(Subqueries.propertiesIn(new String[] { "toUserId" }, subCriteria3));

		criteria.add(Subqueries.propertiesIn(new String[] { "id" }, subCriteria2));

		DetachedCriteria subCriteria = DetachedCriteria.forClass(DepartmentArcRecords.class);
		subCriteria.setProjection(Projections.property("arcrecId"));
		subCriteria.add(Restrictions.eq("deptId", deptId));
		if (mode == 0)
			criteria.add(Subqueries.propertiesNotIn(new String[] { "id" }, subCriteria));
		else
			criteria.add(Subqueries.propertiesIn(new String[] { "id" }, subCriteria));

		criteria.setResultTransformer(Transformers.aliasToBean(ArcRecords.class));
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}

	@Override
	public List<TradIssueType> loadTrdType() {
		return loadAll(TradIssueType.class);
	}

	@Override
	public List<BillIssueDetail> loadBillIssue() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillIssueDetail.class);
		criteria.add(Restrictions.eq("idIssue", 2));
		return criteria.list();
	}

	@Override
	public List<BillIssueCash> loadBillCash(int issueCategory, Integer activityType, Double advArea) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillIssueCash.class);
		// criteria.setProjection(Projections.projectionList().add(Projections.property("this.id"),
		// "id").add(Projections.property("this.incomeNo"),
		// "incomeNo").add(Projections.property("this.recTitle"), "recTitle"));
		criteria.add(Restrictions.eq("category", issueCategory));
		criteria.add(Restrictions.eq("idBillDetail", activityType));
		criteria.add(Restrictions.gt("maximumCash", advArea));
		criteria.add(Restrictions.lt("minimumCash", advArea));
		return criteria.list();
	}

	@Override
	public List<BillIssueRubish> loadBillCashRubish(int issueCategory, Integer activityType) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillIssueRubish.class);
		// criteria.setProjection(Projections.projectionList().add(Projections.property("this.id"),
		// "id").add(Projections.property("this.incomeNo"),
		// "incomeNo").add(Projections.property("this.recTitle"), "recTitle"));
		criteria.add(Restrictions.eq("category", issueCategory));
		criteria.add(Restrictions.eq("idBillDetail", activityType));
		return criteria.list();
	}

	@Override
	public List<BillIssueDig> loadStreetDig() {
		// TODO Auto-generated method stub
		return loadAll(BillIssueDig.class);
	}

	@Override
	public List<BillIssueDigDetail> getTypeDigByCat(Integer streetGeneral) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillIssueDigDetail.class);
		// criteria.setProjection(Projections.projectionList().add(Projections.property("this.id"),
		// "id").add(Projections.property("this.incomeNo"),
		// "incomeNo").add(Projections.property("this.recTitle"), "recTitle"));
		criteria.add(Restrictions.eq("idDigMaster", streetGeneral));
		return criteria.list();
	}

	@Override
	public List<BillIssueDigCash> loadBillCashStreetDig(int issueCategory, Integer streetType) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillIssueDigCash.class);
		// criteria.setProjection(Projections.projectionList().add(Projections.property("this.id"),
		// "id").add(Projections.property("this.incomeNo"),
		// "incomeNo").add(Projections.property("this.recTitle"), "recTitle"));
		// criteria.add(Restrictions.eq("category3", issueCategory));
		criteria.add(Restrictions.eq("idDigType", streetType));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> loadSalaryData(Integer year, Integer month, Integer type) {
		List<String> salaries = new ArrayList<String>();
		String sql;
		try {
			if (type.equals(1))
				sql = Utils.readSqlRequest("salaryRequestrsd");
			else
				sql = Utils.readSqlRequest("overTimeRequest");
			Session currentSession = sessionFactory.getCurrentSession();
			Query salaryQuery = currentSession.createSQLQuery(sql);
			salaryQuery.setParameter("month", month);
			salaryQuery.setParameter("year", year);
			salaries = salaryQuery.list();

			return salaries;

		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
		}
		return salaries;
	}

	@Override
	public List<MasterFile> getAllMasterFiles() {
		return loadAll(MasterFile.class);
	}

	@Override
	public TstFinger getFingerDigByUserId(TstFingerId userId) {

		return get(TstFinger.class, userId);
	}

	@Override
	public int loadVacationSumLastYear(int empNB) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSumVacation.class);
		criteria.setProjection(Projections.sum("days"));
		criteria.add(Restrictions.eq("employeeNumber", empNB));
		Long count = (Long) criteria.uniqueResult();
		int result = 0;
		if (count != null)
			result = count.intValue();
		return result;

	}

	@Override
	public HrsSumVacation listsumbyEmp(int empno) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsSumVacation.class);
		// criteria.setProjection(Projections.projectionList().add(Projections.property("this.id"),
		// "id").add(Projections.property("this.incomeNo"),
		// "incomeNo").add(Projections.property("this.recTitle"), "recTitle"));
		// criteria.add(Restrictions.eq("category3", issueCategory));
		criteria.add(Restrictions.eq("employeeNumber", empno));
		return (HrsSumVacation) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public List<RecDepts> getAllRecDepts() {

		return loadAll(RecDepts.class);

	}

	@Override
	@Transactional
	public DepartmentArcRecords getAttachNBByRecId(int recId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DepartmentArcRecords.class);
		criteria.add(Restrictions.eq("arcrecId", recId));
		return (DepartmentArcRecords) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public String findReciverNameById(int reciverId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecDepts.class);
		criteria.add(Restrictions.eq("id", reciverId));
		RecDepts dept = (RecDepts) criteria.uniqueResult();
		return dept.getDeptName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeptArcRecords> loadDeptRecords(Integer currDeptId, Integer status) {
		List<DeptArcRecords> arcs = new ArrayList<DeptArcRecords>();
		String sql;
		try {
			if (status.equals(0))
				sql = Utils.readSqlRequest("dept_records_not_received");
			else
				sql = Utils.readSqlRequest("dept_records_received");
			Session currentSession = sessionFactory.getCurrentSession();
			Query deptArcsQuery = currentSession.createSQLQuery(sql);
			deptArcsQuery.setParameter("dept_id", currDeptId);
			List<Object> arcsss = deptArcsQuery.list();
			arcs = deptArcsQuery.list();
			return arcs;
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
		}
		return arcs;
	}

	@Override
	@Transactional
	public User getUserByEmpNO(int employerNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("employeeNumber", employerNumber));
		return (User) criteria.uniqueResult();
	}

	@Override
	public List<InvNewspaper> loadAllNewspapers() {
		return loadAll(InvNewspaper.class);
	}

	@Override
	public List<Investor> loadAllInvestors() {
		return loadAll(Investor.class);
	}

	@Override
	public List<RequestStatus> getUserReqStatus() {
		return loadAll(RequestStatus.class);
	}

	@Override
	public List<UserRequest> loadUserRequests(Integer status, Integer userId, boolean isTecSupportRole) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserRequest.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (status > 0)
			criteria.add(Restrictions.eq("status", status));
		if (userId > 0 && isTecSupportRole)
			criteria.add(Restrictions.eq("currUserId", userId));
		else if (userId > 0 && !isTecSupportRole)
			criteria.add(Restrictions.eq("userId", userId));
		return criteria.list();
	}

	@Override
	public List<RealEstate> loadAllRealEstates() {
		return loadAll(RealEstate.class);
	}

	@Override
	public List<Contract> loadAllContracts() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Contract.class);
		criteria.addOrder(Order.desc("contractNum"));
		return criteria.list();
	}

	@Override
	public List<SiteType> loadAllSiteTypes() {
		return loadAll(SiteType.class);
	}

	public List<AnnoucementDetails> loadAnnoucementDetails(Integer AnnoucementId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AnnoucementDetails.class);
		criteria.add(Restrictions.eq("annoucementId", AnnoucementId));
		return criteria.list();
	}

	@Override
	public List<ContractType> loadAllContractTypes() {
		return loadAll(ContractType.class);
	}

	@Override
	public List<Tender> loadTendersByAnnouncementDetailsId(Integer annDetailsId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tender.class);
		criteria.add(Restrictions.eq("announcementDetailsId", annDetailsId));
		return criteria.list();
	}

	@Override
	public List<HrsMasterFile> getAllEmployeesActive() {
		List<Integer> listCat = new ArrayList<Integer>();
		listCat.add(1);
		listCat.add(2);
		listCat.add(4);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsMasterFile.class);
		criteria.add(Restrictions.eq("employerStatus", 1));
		criteria.add(Restrictions.in("cactegoryId", listCat));
		return criteria.list();
	}

	@Override
	public List<TechnicalUsers> loadAllTechnicalUsers() {
		return loadAll(TechnicalUsers.class);
	}

	@Override
	public List<Clause> loadAllClauses() {
		return loadAll(Clause.class);
	}

	@Override
	public FngTimeTable loadTimeShiftByUserId(Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FngTimeTable.class);
		DetachedCriteria subCriteria = DetachedCriteria.forClass(FngUserTempShift.class);
		subCriteria.setProjection(Projections.property("id.timeid"));
		subCriteria.add(Restrictions.eq("id.userid", userId));
		String currentDate = formatDate.format(new Date());
		subCriteria.add(Restrictions.eq("id.workdate", currentDate));
		criteria.add(Subqueries.propertiesIn(new String[] { "timeShiftId" }, subCriteria));
		return (FngTimeTable) criteria.list().get(0);
	}

	@Override
	public String getAmountInLetters(String amount) {
		String sql = "select NUM_AR(" + amount + ") as aa from dual";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return (String) query.uniqueResult();
	}

	@Override
	public void deleteAnncementNews(Integer announcementId) {
		String hql = "delete FROM  AnncementNews  Where annncementId = :announcementId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("announcementId", announcementId);
		query.executeUpdate();
	}

	@Override
	public List<AbsentModel> getHrsUserAbsents(Integer currentYear) {
		List<AbsentModel> absents = new ArrayList<AbsentModel>();
		String sql;
		Connection conn = DataBaseConnectionClass.getConnection();
		PreparedStatement stmt = null;
		try {
			sql = "select user_id as empno,sum(days)as days,sum(hours)as hours,sum(mins)as minutes "
					+ "from  HRS_EMP_ABSNT abs, arc_users us " + " where    " + " abs.empno = us.empno and"
					+ "   ABSTYPE   = 204  and" + " absyear   =?" + " group by us.user_id;";

			// Utils.readSqlRequest("user_hrs_absents");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, currentYear);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				AbsentModel absentModel = new AbsentModel();
				absentModel.setEmpno(rs.getInt("empno"));
				absentModel.setDays(rs.getInt("days"));
				absentModel.setHours(rs.getInt("hours"));
				absentModel.setMinutes(rs.getInt("minutes"));
				absents.add(absentModel);
			}
			return absents;
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
		}
		return absents;
	}

	@Override
	public Tender loadSelectedTender(Integer realEstateId, Integer investorId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tender.class);
		criteria.add(Restrictions.eq("investorId", investorId));
		criteria.add(Restrictions.eq("realEstateId", realEstateId));
		// criteria.add(Restrictions.eq("status", 1));
		return (Tender) criteria.uniqueResult();
	}

	@Override
	public List<Investor> loadInvestorsByRealEstateId(Integer realEstateId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tender.class);
		criteria.setProjection(Projections.property("investor"));
		criteria.add(Restrictions.eq("realEstateId", realEstateId));
		criteria.add(Restrictions.eq("closed", 0));
		return criteria.list();
	}

	@Override
	public List<AnnoucementDetails> loadAnnouncementDetailsByStatus(Integer[] status) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AnnoucementDetails.class);
		criteria.add(Restrictions.in("status", status));
		return criteria.list();
	}

	@Override
	public List<Car> loadAllCars() {
		return loadAll(Car.class);
	}

	@Override
	public List<CarBrand> loadAllCarBrands() {
		return loadAll(CarBrand.class);
	}

	@Override
	public List<CarModel> loadCarModelByBrandId(Integer carBrand) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CarModel.class);
		criteria.add(Restrictions.eq("brandId", carBrand));
		return criteria.list();
	}

	@Override
	public List<VehicleType> loadAllVehicleTypes() {
		return loadAll(VehicleType.class);
	}

	@Override
	public List<FuelType> loadAllFuelTypes() {
		return loadAll(FuelType.class);
	}

	@Override
	public List<FuelTransaction> loadAllFuelTransactions() {
		return loadAll(FuelTransaction.class);
	}

	@Override
	public List<UserCars> loadUserCarsByUserId(Integer employerId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserCars.class);
		criteria.add(Restrictions.eq("userId", employerId));
		return criteria.list();
	}

	@Override
	public List<UserCars> loadAllAllocatedUserCars() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserCars.class);
		criteria.add(Restrictions.eq("ownerStatus", 1));
		return criteria.list();
	}

	@Override
	public List<Car> loadNotAllocatedCarsByModelId(Integer carModelId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Car.class);
		criteria.add(Restrictions.eq("carModel", carModelId));
		DetachedCriteria subCriteria = DetachedCriteria.forClass(UserCars.class);
		subCriteria.setProjection(Projections.property("carId"));
		subCriteria.add(Restrictions.eq("ownerStatus", 1));
		criteria.add(Subqueries.propertiesNotIn(new String[] { "id" }, subCriteria));
		return criteria.list();
	}

	@Override
	public List<FuelTransaction> loadFuelTransactions(Date startDate, Date endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FuelTransaction.class);
		criteria.add(Restrictions.between("startDate", startDate, endDate));
		return criteria.list();
	}

	@Override
	public UserCars findCarByNumDoor(Integer numDoor) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserCars.class).createAlias("car", "car");
		criteria.add(Restrictions.eq("car.numDoor", numDoor));
		return (UserCars) criteria.uniqueResult();
	}

	@Override
	public FuelTransaction findLastFuelTransaction(Integer userCarId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FuelTransaction.class);
		criteria.add(Restrictions.eq("userCarId", userCarId));
		criteria.addOrder(Order.desc("id"));
		List<FuelTransaction> result = criteria.list();
		return ((result != null) && (!result.isEmpty())) ? result.get(0) : new FuelTransaction();
	}

	@Override
	public List<ContractDirect> loadAllContractDirects(Integer contractTypeId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ContractDirect.class);
		if ((contractTypeId != null) && (contractTypeId != 0))
			criteria.add(Restrictions.eq("contractTypeId", contractTypeId));
		criteria.addOrder(Order.desc("contractNum"));
		return criteria.list();
	}

	@Override
	public List<FuelSupply> loadAllFuelSupplies() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FuelSupply.class);
		criteria.addOrder(Order.desc("supplyDate"));
		return criteria.list();
	}

	@Override
	public FuelSupply loadLastFuelSupply(Integer fuelTypeId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FuelSupply.class);
		criteria.add(Restrictions.eq("fuelTypeId", fuelTypeId));
		criteria.addOrder(Order.desc("supplyDate"));
		List<FuelSupply> result = criteria.list();
		return ((result != null) && (!result.isEmpty())) ? result.get(0) : new FuelSupply();
	}

	@Override
	public List<IntroContract> loadAllIntroContracts() {
		return loadAll(IntroContract.class);
	}

	@Override
	public List<AnnoucementDetails> loadAnnouncementDetailsHavingTenders(Integer[] status) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tender.class);
		Projection annoucementDetails = Projections.property("annoucementDetails");
		criteria.setProjection(Projections.distinct(annoucementDetails));
		criteria.add(Restrictions.in("status", status));

		DetachedCriteria subCriteria = DetachedCriteria.forClass(AnnoucementDetails.class);
		subCriteria.setProjection(Projections.property("id"));
		subCriteria.add(Restrictions.eq("status", 0));
		criteria.add(Subqueries.propertiesIn(new String[] { "announcementDetailsId" }, subCriteria));
		return criteria.list();
	}

	@Override
	public Tender loadAssignedTenderByAnnouncementDetailsId(Integer annoucementDetailsId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tender.class);
		criteria.add(Restrictions.eq("announcementDetailsId", annoucementDetailsId));
		criteria.add(Restrictions.eq("status", 1));
		return (Tender) criteria.uniqueResult();
	}

	@Override
	public List<ContractCancelReason> loadAllContractCancelReasons() {
		return loadAll(ContractCancelReason.class);
	}

	// @Override
	// public ContractBills loadContractBills(Integer contractId) {
	// Criteria criteria =
	// sessionFactory.getCurrentSession().createCriteria(ContractBills.class);
	// criteria.add(Restrictions.eq("contractId", contractId));
	// criteria.add(Restrictions.eq("status", 0));
	// return (ContractBills) criteria.uniqueResult();
	// }
	@SuppressWarnings("unchecked")
	@Override
	public List<ArcPeople> loadArcPeopleFields() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcPeople.class)
				.setProjection(Projections.projectionList().add(Projections.property("nationalId"), "nationalId")
						.add(Projections.property("firstName"), "firstName")
						.add(Projections.property("seconedName"), "seconedName")
						.add(Projections.property("thirdName"), "thirdName")
						.add(Projections.property("fourthName"), "fourthName")
						.add(Projections.property("dateOfBirth"), "dateOfBirth"))
				.setResultTransformer(Transformers.aliasToBean(ArcPeople.class));
		return criteria.list();
	}

	@Override
	public List<NotifFinesMaster> loadAllNotifPenalities(Integer status) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(NotifFinesMaster.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (status != null)
			criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.desc("notifId"));
		List<NotifFinesMaster> resultList = criteria.list();
		return resultList;
	}

	@Override
	public List<ContractDirectType> loadAllContractDirectTypes() {
		return loadAll(ContractDirectType.class);
	}

	@Override
	public List<PayLicBills> loadContractBills(Integer contractId, String contractType) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayLicBills.class);
		criteria.add(Restrictions.eq("licenceNumber", contractId));
		criteria.add(Restrictions.eq("licenceType", contractType));
		return criteria.list();
	}

	@Override
	public List<LicTrdMasterFile> loadLicences() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LicTrdMasterFile.class)
				.setProjection(Projections.projectionList().add(Projections.property("licNo"), "licNo")
						.add(Projections.property("trdName"), "trdName")
						.add(Projections.property("aplOwner"), "aplOwner"))
				.setResultTransformer(Transformers.aliasToBean(LicTrdMasterFile.class));
		return criteria.list();
	}

	@Override
	public List<ReqFinesSetup> loadFineSetupBySection(Integer sectionId) {
		// Criteria criteria =
		// sessionFactory.getCurrentSession().createCriteria(FineSection.class);
		// criteria.setProjection(Projections.property("finesList"));
		// criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		// criteria.setFetchMode("finesList", FetchMode.JOIN);
		// criteria.add(Restrictions.idEq(sectionId));
		// return criteria.list();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FineSection.class);
		// .setProjection(Projections.projectionList()
		// .add(Projections.property("finesList"), "finesList"))
		// .setResultTransformer(Transformers.aliasToBean(FineSection.class));
		criteria.add(Restrictions.eq("id", sectionId));
		FineSection section = (FineSection) criteria.uniqueResult();
		Hibernate.initialize(section.getFinesList());
		return section.getFinesList();

	}

	@Override
	public List<WrkCommentType> findAllCommentTypes() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkCommentType.class);
		criteria.add(Restrictions.isNotNull("commentTypeName"));
		criteria.addOrder(Order.asc("commentTypeName"));
		return criteria.list();
	}

	@Override
	public Integer getNewVacPeriod(int findCurrentYear) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsVacationUpdate.class);
		criteria.setProjection(Projections.sum("days"));
		// criteria.add(Restrictions.eq("year", findCurrentYear));

		Integer days = Integer.parseInt(criteria.uniqueResult().toString());
		return days;
	}

	@Override
	public List<CompensatoryVacStock> getAllUsersCompVacStock() {
		return loadAll(CompensatoryVacStock.class);
	}

	@Override
	public List<HrsVacSold> getAllHrsVacSold() {
		return loadAll(HrsVacSold.class);
	}

	@Override
	public boolean addUserRequest(UserRequest reuest, RequestStep reqSteps) {
		try {
			Session currentSession = getSessionFactory().getCurrentSession();
			Integer requestId = (Integer) currentSession.save(reuest);
			reqSteps.setRequestId(requestId);
			currentSession.save(reqSteps);

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Transactional
	@Override
	public Integer getInitUserVacSold(int findCurrentYear, int empno) {
		try {

			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsVacSold.class);
			criteria.setProjection(Projections.sqlProjection("(VAC_OLD_SOLD + VAC_CURR_YEAR_SOLD) as total",
					new String[] { "total" }, new Type[] { IntegerType.INSTANCE }));
			criteria.add(Restrictions.eq("currentYear", findCurrentYear));
			criteria.add(Restrictions.eq("employeeNumber", empno));
			/*
			 * String hql =
			 * " select w.oldSold + w.currentYearSold from HrsVacSold w where w.employeeNumber=:empno AND w.currentYear=:curryear "
			 * ; Query query = sessionFactory.getCurrentSession().createQuery(hql);
			 * query.setParameter("empno", empno); query.setParameter("curryear",
			 * findCurrentYear);
			 */
			return (Integer) criteria.uniqueResult();
//			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsVacSold.class);
//			criteria.setProjection(Projections.property("VAC_CURR_YEAR_SOLD"));
//			criteria.setProjection(Projections.property("VAC_OLD_SOLD"));
//			criteria.add(Restrictions.eq("VAC_CURR_YEAR", findCurrentYear));
//			criteria.add(Restrictions.eq("empno", empno));
			// return (Integer) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("getInitUserVacSold" + e.getMessage());
		}
		return 0;
	}

	@Override
	public List<DocumentsType> getAllDocumentsType() {
		return loadAll(DocumentsType.class);
	}

	@Transactional
	@Override
	public List<WrkFinesEntity> getAllWrkFinesEntity() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkFinesEntity.class);
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}

	@Override
	@Transactional
	public ArcRecords findRecordByOutComeNo(int outcomeNumber) {
		String hql = "  FROM  ArcRecords  Where OUTCOMING_NO = :outNo  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("outNo", outcomeNumber);
		if (!query.list().isEmpty()) {
			return (ArcRecords) query.list().get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public List<Project> getAllProjects() {
		return loadAll(Project.class);

	}

	@Override
	@Transactional
	public List<ProjectContract> getallProjectContract() {
		return loadAll(ProjectContract.class);

	}

	@Override
	@Transactional
	public List<FinEntity> getAllfinEntitys() {
		return loadAll(FinEntity.class);
	}

	@Override
	public AutorizationSettings getAutorizations() {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AutorizationSettings.class);
		AutorizationSettings autorizationSettings = new AutorizationSettings();
		autorizationSettings = (AutorizationSettings) criteria.uniqueResult();
		return autorizationSettings;
	}

	@Override
	public HrsVacSold getHrsVacSoldById(Integer id) {
		Integer year = null;
		try {
			year = Utils.getCurrentHijriYear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HrsVacSold vacSold = new HrsVacSold();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsVacSold.class);
		criteria.add(Restrictions.eq("employeeNumber", id));
		criteria.add(Restrictions.eq("currentYear", year));
		Object result = criteria.uniqueResult();
		if (result != null) {
			vacSold = (HrsVacSold) result;
		}
		return vacSold;
	}

	@Override
	public List<HrsUserAbsent> getAuthorizationsHoursNumber(int userId, String month, String year) {
		List<HrsUserAbsent> userAbsents = new ArrayList<HrsUserAbsent>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsUserAbsent.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.sqlRestriction("substr(ABSDATE,7,4)||substr(ABSDATE,4,2)||substr(ABSDATE,1,2)<= ?",
				year + month + "30", StringType.INSTANCE));
		criteria.add(Restrictions.sqlRestriction("substr(ABSDATE,7,4)||substr(ABSDATE,4,2)||substr(ABSDATE,1,2)>= ?",
				year + month + "01", StringType.INSTANCE));
		criteria.add(Restrictions.eq("absType", MyConstants.PERMISSION));
		criteria.add(Restrictions.eq("accept", MyConstants.YES));
		userAbsents = criteria.list();
		return userAbsents;

	}

	@Override
	@Transactional
	public List<HrsUserAbsent> getAllEmpApsent(int userId) {
		List<HrsUserAbsent> userAbsents = new ArrayList<HrsUserAbsent>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsUserAbsent.class);
		criteria.add(Restrictions.eq("userId", userId));

		// criteria.add(
		// Restrictions.sqlRestriction("substr(ABSDATE,7,4)||substr(ABSDATE,4,2)||substr(ABSDATE,1,2)
		// <= ?",
		// HijriCalendarUtil.findCurrentHijriDate().substring(6, 10)
		// + HijriCalendarUtil.findCurrentHijriDate().substring(3, 5) + "30",
		// StringType.INSTANCE));
		// criteria.add(
		// Restrictions.sqlRestriction("substr(ABSDATE,7,4)||substr(ABSDATE,4,2)||substr(ABSDATE,1,2)
		// >= ?",
		// HijriCalendarUtil.findCurrentHijriDate().substring(6, 10)
		// + HijriCalendarUtil.findCurrentHijriDate().substring(3, 5) + "01",
		// StringType.INSTANCE));
		criteria.add(Restrictions.eq("absType", MyConstants.PERMISSION));
		criteria.add(Restrictions.eq("accept", MyConstants.YES));
		userAbsents = criteria.list();
		return userAbsents;

	}

	@Override
	@Transactional
	public Article getArticleById(Integer id) {
		Article article = new Article();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Article.class);
		criteria.add(Restrictions.eq("id", id));
		Object result = criteria.uniqueResult();
		if (result != null) {
			article = (Article) result;
		}
		return article;
	}

	@Override
	public List<CompensatoryVacStock> getUserCompensatoryVacStockByEmpNo(Integer empNO, Integer compVacType) {
		Integer year = null;
		List<CompensatoryVacStock> comp = null;
		try {
			year = Utils.getCurrentHijriYear();
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CompensatoryVacStock.class);
			criteria.add(Restrictions.eq("empno", empNO));
			criteria.add(Restrictions.eq("year", year));
			if (compVacType != null && compVacType != 0) {
				criteria.add(Restrictions.eq("comp_type", compVacType));
			}
			comp = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comp;
	}

	@Override
	public List<User> getAllEmployeesInSelectedDepts(List<String> ids) {
		List<User> deptsEmps = new ArrayList<User>();
		Integer deptId = 0;
		for (String deptIds : ids) {
			deptId = Integer.parseInt(deptIds);
			String hql = "from  User user where (user.deptId=:deptId and :deptId >0 or :deptId = -1) and user.enabled= true ";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setParameter("deptId", deptId);

			if (!query.list().isEmpty() && query.list() != null)
				deptsEmps.add((User) query.list());
			else
				System.out.println("there is no dept with this id");
		}

		// if (!query.list().isEmpty() && query.list() != null)
		// return query.list();
		// else
		// return new ArrayList<User>();

		return deptsEmps;
	}

	@Override
	public void deleteFngUserTempShiftById(Integer userId, Date workDate) {
		String selectedDate = Utils.convertDateToString(workDate);
		String hql = " delete from FngUserTempShift where  userId = :userId and workDate = :workDate  ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameter("workDate", selectedDate);
		query.executeUpdate();
		System.out.println("User Temp Shift has been Deleted for " + selectedDate + " _ _ _ .");
	}

	@Override
	public FngTimeTable getTimeShiftById(Integer timeShiftId) {
		FngTimeTable shift = new FngTimeTable();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FngTimeTable.class);
		criteria.add(Restrictions.eq("timeShiftId", timeShiftId));
		Object result = criteria.uniqueResult();
		if (result != null) {
			shift = (FngTimeTable) result;
		}
		return shift;
	}

	@Override
	public List<FngUserTempShift> getEmployeeShiftsById(Integer userId, Date workDate) {
		List<FngUserTempShift> empShiftList = new ArrayList<FngUserTempShift>();
		String selectedDate = Utils.convertDateToString(workDate);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FngUserTempShift.class);
		criteria.add(Restrictions.eq("id.userid", userId));
		criteria.add(Restrictions.eq("id.workdate", selectedDate));
		// query.executeUpdate();

		if (!criteria.list().isEmpty() && criteria.list() != null)
			empShiftList.addAll(criteria.list());
		else
			System.out.println("Employee dosen't have shift in " + workDate + " ---.");
		return empShiftList;
	}

	@Override
	public List<InventoryMaster> getInventoriesByStrNo(Integer strNo) {
		List<InventoryMaster> inventories = new ArrayList<InventoryMaster>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InventoryMaster.class);
		criteria.add(Restrictions.eq("strno", strNo));
		inventories = criteria.list();
		return inventories;
	}

	@Transactional
	@Override
	public List<Article> getAllArticles() {
		return loadAll(Article.class);
	}

	@Transactional
	@Override
	public HrsJobCreation loadJobCreation(Integer jobNumber, String jobCode) {
		HrsJobCreation job = new HrsJobCreation();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsJobCreation.class);
		criteria.add(Restrictions.eq("jobCode", jobCode));
		criteria.add(Restrictions.eq("jobNumber", jobNumber));

		Object result = criteria.uniqueResult();
		if (result != null) {
			job = (HrsJobCreation) result;
		}
		return job;
	}

	@Override
	@Transactional
	public void addOperation(Object dep_tr) {

		sessionFactory.getCurrentSession().save(dep_tr);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> loadDepTrain(Integer emp_no, Integer month, Integer year) {

		List<String> list = new ArrayList<String>();
		String sql;
		try {

			sql = Utils.readSqlRequest("DeptTrainRequest");

			System.out.println(sql);
			Session currentSession = sessionFactory.getCurrentSession();

			Query salaryQuery = currentSession.createSQLQuery(sql);

			salaryQuery.setParameter("year", year);

			salaryQuery.setParameter("month", month);

			list = salaryQuery.list();

			return list;
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DeputationTraining> loadStatus(Integer emp_number) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DeputationTraining.class);

		if (emp_number != 0) {
			criteria.add(Restrictions.eq("emp_no", emp_number));
			return criteria.list();
		} else {

			return criteria.list();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DeputationTraining> loadStatus(Integer emp_number, Integer month, Integer year) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DeputationTraining.class);

		if (emp_number != 0) {
			criteria.add(Restrictions.eq("emp_no", emp_number));

			if (month != null)
				criteria.add(Restrictions.eq("month", month));
			if (year != null)
				criteria.add(Restrictions.eq("year", year));

			return criteria.list();

		} else {
			if (month != null)
				criteria.add(Restrictions.eq("month", month));
			if (year != null)
				criteria.add(Restrictions.eq("year", year));
			return criteria.list();
		}
	}

	@Override
	@Transactional
	public void saveReward(RewardInfo rewardinfo) {

		sessionFactory.getCurrentSession().save(rewardinfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<RewardInfo> loadRewards(Integer emp_number) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RewardInfo.class);
		if (emp_number != 0)
			criteria.add(Restrictions.eq("emp_no", emp_number));

		return criteria.list();
	}

	public List<RewardInfo> loadRewards(Integer emp_number, Integer month, Integer year) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RewardInfo.class);

		if (emp_number != 0) {
			criteria.add(Restrictions.eq("emp_no", emp_number));

			if (month != null)
				criteria.add(Restrictions.eq("month", month));
			if (year != null)
				criteria.add(Restrictions.eq("year", year));

			return criteria.list();

		} else {
			if (month != null)
				criteria.add(Restrictions.eq("month", month));
			if (year != null)
				criteria.add(Restrictions.eq("year", year));
			return criteria.list();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> loadReward(Integer emp_no, Integer month, Integer year) {
		List<String> list = new ArrayList<String>();
		String sql;
		try {

			sql = Utils.readSqlRequest("RewardRequest");

			Session currentSession = sessionFactory.getCurrentSession();

			Query salaryQuery = currentSession.createSQLQuery(sql);

			salaryQuery.setParameter("year", year);

			salaryQuery.setParameter("month", month);

			list = salaryQuery.list();

			return list;
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
		}
		return list;
	}

	public Integer getActifEmployer(Integer employerNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsEmpHistorical.class);
		criteria.setProjection(Projections.property("flag"));
		criteria.add(Restrictions.eq("id.empno", employerNumber));
		criteria.add(Restrictions.eq("flag", 1));
		Integer status = (Integer) criteria.uniqueResult();
		return status;
	}

	@Override
	@Transactional
	public Integer getIdFromWorkAppByAppId(Integer appId) {
		String hql = "select app.id.applicationId  from WrkApplication  app  where  app.arcRecordId=:appId ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("appId", appId);
		List<Integer> list = query.list();
		return list.get(0);
	}

	@Override
	public List<WrkLetterFrom> loadAllWrkLetterFrom() {
		return loadAll(WrkLetterFrom.class);

	}

	@Override
	public List<WrkLetterTo> loadAllWrkLetterTo() {
		return loadAll(WrkLetterTo.class);

	}

	@Override
	public List<WrkPurpose> loadAllPurposes() {
		return loadAll(WrkPurpose.class);

	}

	@Override
	public List<WrkCommentType> loadAllCommentTypes() {
		return loadAll(WrkCommentType.class);

	}

	@Override
	public List<VacationsType> loadAllVacationTypes() {
		return loadAll(VacationsType.class);

	}

	@Override
	public List<SysCategoryEmployer> loadAllCategoryEmployers() {
		return loadAll(SysCategoryEmployer.class);

	}

	@Override
	public List<FngStatusAbsence> loadAllAbsenceStatus() {
		return loadAll(FngStatusAbsence.class);

	}

	@Override
	public List<FngTypeAbsence> loadAllAbsenceTypes() {
		return loadAll(FngTypeAbsence.class);

	}

	@Override
	@Transactional
	public List<WrkDept> findDepartmentById(Integer deptId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WrkDept.class);
		criteria.add(Restrictions.eq("id", deptId));
		return criteria.list();

	}

	public List<LoanModel> loadUsersLoan(Integer year, Integer Month, Integer type) {
		List<LoanModel> list = new ArrayList<LoanModel>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.get_users_loan(?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, year);
			callableStatement.setInt(2, Month);
			callableStatement.setInt(3, type);

			callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(4);
			while (rs.next()) {
				LoanModel loanModel = new LoanModel();
				loanModel.setEmpNo(rs.getInt("LOAN_EMPNO"));
				loanModel.setEmpName(rs.getString("NAME"));
				loanModel.setRowId(rs.getInt("ROW_ID"));
				loanModel.setPaidMonthly(rs.getInt("LOAN_PAID_MONTLY"));
				loanModel.setBankName(rs.getString("BANK_NAME"));
				loanModel.setLoanName(rs.getString("LOAN_NAME"));
				loanModel.setNatNo(rs.getInt("NATNO"));

				list.add(loanModel);

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public List<RetirementModel> loadRetirement() {
		List<RetirementModel> list = new ArrayList<RetirementModel>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.get_users_retirement(?)}";
			callableStatement = connection.prepareCall(sql);

			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				RetirementModel retirementModel = new RetirementModel();
				retirementModel.setEmpName(rs.getString("name"));
				retirementModel.setAppDate(rs.getString("appdate"));
				retirementModel.setBirthDay(rs.getString("bday"));
				retirementModel.setClassCode(rs.getInt("CLSSCOD"));
				retirementModel.setGender(rs.getString("gender"));
				retirementModel.setNatNo(rs.getInt("natno"));
				retirementModel.setRankCode(rs.getInt("RANKCOD"));
				retirementModel.setText(rs.getString("text"));
				retirementModel.setBasicSalary(rs.getInt("BSCSAL"));

				list.add(retirementModel);

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}

	@Override
	@Transactional
	public List<HRCountry> getAllCountry() {
		return loadAll(HRCountry.class);

	}

	@Override
	@Transactional
	public List<HRAbsence> getAllAbsence() {
		return loadAll(HRAbsence.class);

	}

	@Override
	@Transactional
	public List<HRStudyType> getAllStudyType() {
		return loadAll(HRStudyType.class);

	}

	@Override
	@Transactional
	public List<HRPhoneType> getAllPhoneType() {
		return loadAll(HRPhoneType.class);

	}

	@Override
	@Transactional
	public List<HREmpRanks> getAllEmpRanks() {
		return loadAll(HREmpRanks.class);

	}

	@Override
	@Transactional
	public List<HREmpCat> getAllEmpCat() {
		return loadAll(HREmpCat.class);

	}

	@Override
	@Transactional
	public List<HRLawSentence> getAllLawSentence() {
		return loadAll(HRLawSentence.class);

	}

	@Override
	@Transactional
	public List<HRCourse> getAllCourseTypes() {
		return loadAll(HRCourse.class);

	}

	@Override
	@Transactional
	public List<HRSubjectStatus> getAllSubjectStatus() {
		return loadAll(HRSubjectStatus.class);

	}

	@Override
	@Transactional
	public List<HRBlood> getAllBloodTypes() {
		return loadAll(HRBlood.class);

	}

	@Override
	@Transactional
	public List<HRNationality> getAllNationalities() {
		return loadAll(HRNationality.class);

	}

	@Override
	@Transactional
	public List<HRMarStatus> getAllMarStatus() {
		return loadAll(HRMarStatus.class);

	}

	@Override
	@Transactional
	public List<HRPositionAction> getAllPositionAction() {
		return loadAll(HRPositionAction.class);

	}

	@Override
	@Transactional
	public List<HRTitles> getAllTitles() {
		return loadAll(HRTitles.class);

	}

	@Override
	@Transactional
	public List<HRQlfSpeciality> getAllQlfSpeciality() {
		return loadAll(HRQlfSpeciality.class);

	}

	@Override
	@Transactional
	public List<HRQlfMajors> getAllQlfMajors() {
		return loadAll(HRQlfMajors.class);

	}

	@Override
	@Transactional
	public List<HRQlfTypes> getAllQlfTypes() {
		return loadAll(HRQlfTypes.class);

	}

	@Override
	@Transactional
	public List<HROrgTypes> getAllOrgTypes() {
		return loadAll(HROrgTypes.class);

	}

	@Override
	@Transactional
	public List<HRCity> getAllCities() {
		return loadAll(HRCity.class);

	}

	@Override
	@Transactional
	public List<HRArea> getAllAreas() {
		return loadAll(HRArea.class);

	}

	@Override
	@Transactional
	public List<HRContacts> getAllContacts() {
		return loadAll(HRContacts.class);

	}

	@Override
	@Transactional
	public List<HRReligion> getAllReligions() {
		return loadAll(HRReligion.class);

	}

	@Override
	@Transactional
	public List<HrQualification> getAllQualification() {
		return loadAll(HrQualification.class);
	}

	@Override
	@Transactional
	public List<Establishment> getAllEstablishment() {
		return loadAll(Establishment.class);
	}

	@Override
	@Transactional
	public Establishment getEstablishmentById(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Establishment.class);
		criteria.add(Restrictions.eq("id", id));

		return (Establishment) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public List<HrsEmpHistorical> getEmpHistoricalByEmpNo(Integer employerNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HrsEmpHistorical.class);
		criteria.add(Restrictions.eq("id.empno", employerNumber));
		return criteria.list();
	}
}