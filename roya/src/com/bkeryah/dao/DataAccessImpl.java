/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.dao;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;
import org.springframework.transaction.annotation.Transactional;

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
import com.bkeryah.bean.RecordMapClass;
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
import com.bkeryah.entities.HrsEmpHistoricalId;
import com.bkeryah.entities.HrsSalaryScale;
import com.bkeryah.entities.HrsSalaryScaleId;
import com.bkeryah.entities.HrsUserAbsent;
import com.bkeryah.entities.StockEntryMaster;
import com.bkeryah.entities.StoreTemporeryReceiptMaster;
import com.bkeryah.fng.entities.TstFinger;
import com.bkeryah.fng.entities.TstFingerId;
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

import oracle.jdbc.OracleTypes;
import utilities.HijriCalendarUtil;
import utilities.Utils;

/**
 *
 * @author IbrahimDarwiesh
 */

public class DataAccessImpl implements DataAccess, Serializable {
	private SessionFactory sessionFactory;
	protected static final Logger logger = Logger.getLogger(DataAccessImpl.class);

	@Override
	public UserClass login(String User_name, String Password) {
		UserClass uc = new UserClass();
		Connection connection = null;
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {

			int xx = 0;

			List<UserClass> x = new ArrayList<UserClass>();
			String sql = "{call NEW_PKG_WEBKIT.prc_get_userdatabytype(0,?,?,1,?,?)}";
			connection = DataBaseConnectionClass.getConnection();

			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, User_name);
			callableStatement.setString(2, Password);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.setInt(4, xx);
			callableStatement.executeUpdate();

			rs = (ResultSet) callableStatement.getObject(3);

			while (rs.next()) {

				uc = new UserClass(rs.getInt("USER_ID"), rs.getString("LOGIN_NAME"), rs.getString("FNAME"),
						rs.getString("PASSWORD"), rs.getInt("MGR_ID"), rs.getString("EMPNO"));
				uc.setVUserRoleId(rs.getInt("WRK_ROLE_ID"));
				uc.setVusers_dept_id(rs.getInt("DEPT_ID"));

			}
		} catch (Exception e) {
			logger.error("login" + e.getMessage());
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
		return uc;
	}

	@Override
	public List<ArcRecordClass> getRecords(String IncomeNoPar) {
		Connection connection = DataBaseConnectionClass.getConnection();
		List<ArcRecordClass> ARCS = new ArrayList<ArcRecordClass>();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			// Statement stmt = connection.createStatement();
			String sql = "{call NEW_PKG_WEBKIT.GET_ARC_RECORD_BY_NUMBER(?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, IncomeNoPar);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				ArcRecordClass Arc = new ArcRecordClass();
				Arc.setRecTitle(rs.getString("REC_TITLE"));
				Arc.setLetterTo(rs.getString("Letter_to_name"));
				Arc.setRecHDate(rs.getString("REC_H_DATE"));
				Arc.setIncomeYear(rs.getString("INCOME_YEAR"));
				Arc.setLetterFrom(rs.getString("Letter_from_name"));
				Arc.setIncomeHDate(rs.getString("REC_H_DATE"));
				Arc.setIncomeNo(rs.getString("INCOME_NO"));
				Arc.setCreatedBy(rs.getString("creator"));
				Arc.setArcId(rs.getInt("ID"));
				Arc.setArcRecId(rs.getInt("rec_id"));
				Arc.setArcRecType(rs.getString("Application_type"));
				Arc.setLastEmpName(rs.getString("LAST_EMP_NAME"));
				Arc.setLastEmpNumber(rs.getInt("LAST_EMP"));
				ARCS.add(Arc);

			}
		} catch (Exception e) {
			logger.error("getRecords" + e.getMessage());
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
		return ARCS;
	}

	@Override
	public List<TstFinger> getVacationFng(String lstUser, String startDate, String endDate, Boolean allEmp,
			boolean higriMode) {
		Connection connection = DataBaseConnectionClass.getConnection();
		List<TstFinger> fngVacationList = new ArrayList<>();
		ResultSet rs = null;
		CallableStatement callableStatement = null;

		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_users_vcton_permmision(?,?,?,?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, lstUser);
			callableStatement.setString(2, startDate);
			callableStatement.setString(3, endDate);
			callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callableStatement.setBoolean(5, allEmp);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(4);

			while (rs.next()) {
				TstFinger tstFinger = new TstFinger();
				TstFingerId id = new TstFingerId();
				id.setUserid(rs.getInt("USERID"));
				id.setVdate(rs.getDate("vdate"));
				tstFinger.setUserName(rs.getString("empname"));
				tstFinger.setHigriMode(higriMode);
				tstFinger.setId(id);
				// tstFinger.setHigriDate(rs.getString("higriDate"));
				tstFinger.setHigriDate(Utils.grigDatesConvert(rs.getDate("vdate")));
				tstFinger.setClockin(rs.getString("clockin"));
				tstFinger.setClockout(rs.getString("clockout"));
				tstFinger.setOnduty(rs.getString("onduty"));
				tstFinger.setOffduty(rs.getString("offduty"));
				tstFinger.setAbsType(rs.getString("absType"));
				tstFinger.setTypeDoc(rs.getInt("doctype"));
				tstFinger.setAbsFrom(rs.getString("absFrom"));
				tstFinger.setAbsTo(rs.getString("absTo"));
				tstFinger.setVacaStart(rs.getDate("vacaStrt"));
				tstFinger.setVacaEnd(rs.getDate("vacaEnd"));
				tstFinger.setAbstName(rs.getString("abstName"));
				tstFinger.setInTime(rs.getString("inTime"));
				tstFinger.setOutTime(rs.getString("outTime"));
				tstFinger.setBintime(rs.getString("bintime"));
				tstFinger.setEintime(rs.getString("eintime"));
				tstFinger.setBouttime(rs.getString("bouttime"));
				tstFinger.setEouttime(rs.getString("eouttime"));
				tstFinger.setLatetime(rs.getString("latetime"));
				tstFinger.setDeptName(rs.getString("dept_name"));
				fngVacationList.add(tstFinger);

			}
		} catch (Exception e) {
			logger.error("getVacationFng" + e.getMessage());
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
		return fngVacationList;
	}

	@Override
	public List<HrsUserAbsent> getAllAbsent(String lstUser, String startDate, String endDate) {
		Connection connection = DataBaseConnectionClass.getConnection();
		// java.sql.Date dateS= new java.sql.Date(startDate.getTime());
		// java.sql.Date dateF= new java.sql.Date(endDate.getTime());
		List<HrsUserAbsent> hrsUserAbsentsList = new ArrayList<>();
		ResultSet rs = null;
		CallableStatement callableStatement = null;

		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_users_autors(?,?,?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, lstUser);
			callableStatement.setString(2, startDate);
			callableStatement.setString(3, endDate);
			callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(4);

			while (rs.next()) {
				HrsUserAbsent hrsUserAbsent = new HrsUserAbsent();

				hrsUserAbsent.setUserName(rs.getString("empname"));
				hrsUserAbsent.setEmpNo(rs.getInt("empno"));
				hrsUserAbsent.setId(rs.getInt("id"));
				hrsUserAbsent.setUserId(rs.getInt("user_id"));
				hrsUserAbsent.setAbseDate(rs.getString("absdate"));
				hrsUserAbsent.setAbsForm(rs.getString("absfrom"));
				hrsUserAbsent.setAbsTo(rs.getString("absto"));
				hrsUserAbsent.setAbsType(rs.getString("abstype"));
				hrsUserAbsent.setJabsDate(rs.getString("jabsdate"));
				hrsUserAbsent.setAccept(rs.getString("accept_y_n"));
				hrsUserAbsent.setReason(rs.getString("reason"));
				hrsUserAbsentsList.add(hrsUserAbsent);
			}
		} catch (Exception e) {
			logger.error("getAllAbsent" + e.getMessage());
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
		return hrsUserAbsentsList;
	}

	@Override
	public List<TstFinger> getFngDetail(String lstUser, String startDate, String endDate, boolean higriMode) {
		Connection connection = DataBaseConnectionClass.getConnection();
		List<TstFinger> fngVacationList = new ArrayList<>();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_user_fngs(?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, lstUser);
			callableStatement.setString(2, startDate);
			callableStatement.setString(3, endDate);
			callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(4);
			while (rs.next()) {
				TstFinger tstFinger = new TstFinger();
				TstFingerId id = new TstFingerId();
				id.setUserid(rs.getInt("USERID"));
				tstFinger.setUserName(rs.getString("empname"));
				tstFinger.setTimeFinger(rs.getString("CHECKTIME24"));
				tstFinger.setPic1(rs.getString("D1"));
				tstFinger.setHigriMode(higriMode);
				fngVacationList.add(tstFinger);
			}
		} catch (Exception e) {
			logger.error("getFngDetail" + e.getMessage());
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
		return fngVacationList;
	}

	@Override
	public List<Main_menu_class> getMenuByUser(int userId) {
		List<Main_menu_class> x = new ArrayList<Main_menu_class>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ ?=call New_PKG_WEBKIT.GET_MENUS_BY_USER(?)}"; // PRC_GET_ALL_MAIN_MENUS
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.setInt(2, userId);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				x.add(new Main_menu_class(rs.getInt("MENU_ID"), rs.getString("MAIN_MENU_TXT")));
			}
		} catch (Exception e) {
			logger.error("getMenuByUser" + e.getMessage());
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
		return x;
	}

	@Override
	public List<Sub_menu_class> findSubMenuByMain(int mainMenuId) {
		List<Sub_menu_class> x = new ArrayList<Sub_menu_class>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ ?=call New_PKG_WEBKIT.Get_sub_menus_by_main(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.setInt(2, mainMenuId);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				Sub_menu_class tmp = new Sub_menu_class(rs.getString("SUB_MENU_TXT"), rs.getString("SUB_MENU_ICON"),
						rs.getString("SUB_MENU_URL"));
				tmp.setVsub_menu_id(rs.getInt("SUB_MENU_ID"));

				x.add(tmp);
			}

		} catch (Exception e) {
			logger.error("findSubMenuByMain" + e.getMessage());
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
		return x;
	}

	@Override
	public String getHashedPassword(String userName, String password) {
		String x = "-1";
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		String sql = "{ ?=call WRK_PASSWORD(?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.setString(2, userName);
			callableStatement.setString(3, password);
			callableStatement.executeUpdate();
			x = (String) callableStatement.getObject(1);
		} catch (Exception e) {
			logger.error("getHashedPassword" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return x;
	}

	@Override
	public void chargingEmp(int chargingEmpIn) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.CHARGING_EMPLOYEE(?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, chargingEmpIn);
			callableStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("chargingEmp" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override

	public void finishChargingEmp(int chargingEmpIn) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.DECHARGING_EMPLOYEE(?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, chargingEmpIn);
			callableStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("finishChargingEmp" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<UserMailObj> findEmployeeInbox(Integer start, Integer size, int EmployeeUserId) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		int ErrNumber = 0;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_EMPLOYEE_INBOX(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeUserId);
			callableStatement.setInt(3, ErrNumber);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			// callableStatement.setMaxRows(size);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			// rs.getStatement().setMaxRows(size);
			while (rs.next()) {
				UserMailObj mail = new UserMailObj();
				mail.setWrkSender(rs.getString("usender"));
				mail.setAppTitle(rs.getString("apptitle"));
				mail.setAppId(rs.getString("APP_ID"));
				mail.setAppType(rs.getInt("wrk_type"));
				mail.setWrkType(rs.getInt("wrk_type"));
				mail.setWrkIncomeNo(rs.getString("incmno"));
				mail.setWrkId(rs.getString("ID"));
				mail.setStepId(rs.getInt("step_id"));
				mail.setHasComment(rs.getInt("hasCommentx"));
				mail.setWrkReciever(rs.getString("ureciever"));
				mail.setWrkTotalSteps(rs.getInt("stpes"));
				mail.setWrkAttachCount(rs.getInt("attach"));
				mail.setIsRead(rs.getInt("f_read"));
				mail.setWrkRoleId(rs.getInt("wrk_role_id"));
				mail.setFirstComment(rs.getString("firstComment"));
				mail.setAppTypeName(rs.getString("type"));
				mail.setWrkColor(rs.getString("COLOUR"));
				mail.setWrkCreateTime(rs.getString("create_time"));
				mail.setWrkGDate(rs.getString("m_date"));
				mail.setWrkHdate(rs.getString("h_date"));
				mail.setWrkOutcomeNo(rs.getInt("OUTCOMING_NO"));
				outList.add(mail);
			}
		} catch (Exception e) {
			logger.error("findEmployeeInbox" + e.getMessage());
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
		System.out.println("size**********************:" + outList.size());
		return outList;
	}

	@Override
	public Employer getReturnVacData(int userId) {
		Employer employer = new Employer();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBHR.prc_getdata_for_dirvac(?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, userId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				employer.setEmpNB(rs.getInt("EMPNO"));
				employer.setName(rs.getString("NAME"));
				employer.setVacationPeriod(rs.getInt("VACAPRD"));
				employer.setVacationStart(rs.getString("VACASTRT"));
				employer.setVacationEnd(rs.getString("VACAEND"));
				employer.setVacationExNB(rs.getInt("EXCNO"));
				employer.setVacationExDate(rs.getString("EXCDT"));
				employer.setRank(rs.getInt("RANK"));
				employer.setJob(rs.getString("JOB"));
				employer.setVacationType(rs.getString("VACNAME"));
				employer.setDepartment(rs.getString("department"));

			}
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			logger.error("getReturnVacData" + e.getMessage());
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
		return employer;
	}

	@Override
	public List<UserMailObj> findEmployeeInboxRead(int EmployeeUserId) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		int ErrNumber = 0;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_EMPLOYEE_INBOX_READ(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeUserId);
			callableStatement.setInt(3, ErrNumber);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				UserMailObj mail = new UserMailObj();
				mail.setWrkSender(rs.getString("usender"));
				mail.setAppTitle(rs.getString("apptitle"));
				mail.setAppId(rs.getString("APP_ID"));
				mail.setAppType(rs.getInt("wrk_type"));
				mail.setWrkType(rs.getInt("wrk_type"));
				mail.setWrkIncomeNo(rs.getString("incmno"));
				mail.setWrkId(rs.getString("ID"));
				mail.setStepId(rs.getInt("step_id"));
				mail.setHasComment(rs.getInt("hasCommentx"));
				mail.setWrkReciever(rs.getString("ureciever"));
				mail.setWrkAttachCount(rs.getInt("attach"));
				mail.setIsRead(rs.getInt("f_read"));
				mail.setWrkRoleId(rs.getInt("wrk_role_id"));
				mail.setFirstComment(rs.getString("firstComment"));
				mail.setAppTypeName(rs.getString("type"));
				mail.setWrkColor(rs.getString("COLOUR"));
				mail.setWrkCreateTime(rs.getString("create_time"));
				mail.setWrkGDate(rs.getString("m_date"));
				mail.setWrkHdate(rs.getString("h_date"));
				outList.add(mail);
			}
		} catch (Exception e) {
			logger.error("findEmployeeInboxRead" + e.getMessage());
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
		return outList;
	}

	@Override
	public List<UserMailObj> findEmployeeInboxUnread(int EmployeeUserId) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		int ErrNumber = 0;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_EMPLOYEE_INBOX_UNREAD(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeUserId);
			callableStatement.setInt(3, ErrNumber);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				UserMailObj mail = new UserMailObj();
				mail.setWrkSender(rs.getString("usender"));
				mail.setAppTitle(rs.getString("apptitle"));
				mail.setAppId(rs.getString("APP_ID"));
				mail.setAppType(rs.getInt("wrk_type"));
				mail.setWrkType(rs.getInt("wrk_type"));
				mail.setWrkIncomeNo(rs.getString("incmno"));
				mail.setWrkId(rs.getString("ID"));
				mail.setStepId(rs.getInt("step_id"));
				mail.setHasComment(rs.getInt("hasCommentx"));
				mail.setWrkReciever(rs.getString("ureciever"));
				mail.setIsRead(rs.getInt("f_read"));
				mail.setWrkAttachCount(rs.getInt("attach"));
				mail.setWrkTotalSteps(rs.getInt("f_read"));
				mail.setWrkRoleId(rs.getInt("wrk_role_id"));
				mail.setFirstComment(rs.getString("firstComment"));
				mail.setAppTypeName(rs.getString("type"));
				mail.setWrkColor(rs.getString("COLOUR"));
				mail.setWrkCreateTime(rs.getString("create_time"));
				mail.setWrkGDate(rs.getString("m_date"));
				mail.setWrkHdate(rs.getString("h_date"));
				outList.add(mail);
			}
		} catch (Exception e) {
			logger.error("findEmployeeInboxUnread" + e.getMessage());
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
		return outList;
	}

	@Override
	public List<UserMailObj> findEmployeeOutbox(int EmployeeUserId, String inDate, int partOfYear) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_EMPLOYEE_OUTBOX(?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeUserId);
			callableStatement.setString(2, inDate);
			callableStatement.setInt(5, partOfYear);
			callableStatement.setInt(4, 0);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);
			while (rs.next()) {
				UserMailObj mail = new UserMailObj();
				mail.setWrkSender(rs.getString("usender"));
				mail.setAppTitle(rs.getString("apptitle"));
				mail.setAppId(rs.getString("APP_ID"));
				mail.setWrkIncomeNo(rs.getString("incmno"));
				mail.setWrkId(rs.getString("ID"));
				mail.setStepId(rs.getInt("step_id"));
				mail.setWrkReciever(rs.getString("ureciever"));
				mail.setIsRead(rs.getInt("f_read"));
				mail.setWrkAttachCount(rs.getInt("attach001"));
				mail.setWrkTotalSteps(rs.getInt("f_read"));
				mail.setWrkRoleId(rs.getInt("wrk_role_id"));
				mail.setWrkCreateTime(rs.getString("create_time"));
				mail.setWrkGDate(rs.getString("m_date"));
				mail.setWrkHdate(rs.getString("h_date"));
				mail.setHasComment(rs.getInt("hasCommentx"));
				mail.setHasComment(rs.getInt("hasCommentx"));
				mail.setFromId(rs.getInt("from_id"));
				mail.setWrkOutcomeNo(rs.getInt("OUTCOMING_NO"));
				outList.add(mail);
			}
		} catch (Exception e) {
			logger.error("findEmployeeOutbox" + e.getMessage());
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
		return outList;
	}

	@Override
	public List<UserMailObj> findEmployeeOutbox(int EmployeeUserId, int RecordsRang) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_EMPLOYEE_OUTBOX(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeUserId);
			callableStatement.setInt(3, RecordsRang);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				UserMailObj mail = new UserMailObj();
				mail.setWrkSender(rs.getString("usender"));
				mail.setAppTitle(rs.getString("apptitle"));
				mail.setAppId(rs.getString("APP_ID"));
				mail.setAppType(rs.getInt("wrk_type"));
				mail.setWrkType(rs.getInt("wrk_type"));
				mail.setWrkIncomeNo(rs.getString("incmno"));
				mail.setWrkId(rs.getString("ID"));
				mail.setStepId(rs.getInt("step_id"));
				mail.setHasComment(rs.getInt("hasCommentx"));
				mail.setWrkReciever(rs.getString("ureciever"));
				mail.setIsRead(rs.getInt("f_read"));
				mail.setWrkAttachCount(rs.getInt("attach"));
				mail.setWrkTotalSteps(rs.getInt("f_read"));
				mail.setWrkRoleId(rs.getInt("wrk_role_id"));
				mail.setFirstComment(rs.getString("firstComment"));
				mail.setAppTypeName(rs.getString("type"));
				mail.setWrkColor(rs.getString("COLOUR"));
				mail.setWrkGDate(rs.getString("m_date"));
				mail.setWrkHdate(rs.getString("h_date"));
				outList.add(mail);
			}
		} catch (Exception e) {
			logger.error("findEmployeeOutbox" + e.getMessage());
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
		return outList;

	}

	@Override
	public List<WrkCommentsClass> findCommentsByWrkId(String WrkId) {
		List<WrkCommentsClass> comments = new ArrayList<WrkCommentsClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call New_PKG_WEBKIT.GET_ALL_COMMENTS_BY_WRKID(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, WrkId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.setInt(3, Utils.findCurrentUser().getUserId());
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				WrkCommentsClass wcc = new WrkCommentsClass();
				wcc.setStepId(rs.getInt("STEP_ID"));
				wcc.setComment(rs.getString("COMM"));
				wcc.setPurpose(rs.getString("PURP"));
				wcc.setFromName(rs.getString("FR"));
				wcc.setToName(rs.getString("TT"));
				wcc.setCreatedIn(rs.getString("CREATE_TIME"));
				wcc.setCreateDate(rs.getString("CTIME"));
				comments.add(wcc);

			}
		} catch (Exception e) {
			logger.error("findEmployeeOutbox" + e.getMessage());
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
		return comments;

	}

	@Override
	public List<UserContactClass> findAllEmployeesInDept(int Userid) {
		List<UserContactClass> list = new ArrayList<UserContactClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.GET_ALL_EMP_department_users(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, Userid);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				UserContactClass ucc = new UserContactClass();
				ucc.setUserId(rs.getInt("USER_ID"));
				ucc.setUserName(rs.getString("NAME"));
				ucc.setUserOrder(rs.getInt("SS"));
				ucc.setUserRole(rs.getInt("WRK_ROLE_ID"));
				list.add(ucc);

			}
		} catch (Exception e) {
			logger.error("findAllEmployeesInDept" + e.getMessage());
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
	public List<UserContactClass> findAllManagers(int Userid) {
		List<UserContactClass> list = new ArrayList<UserContactClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.GET_ALL_EMP_MANAGMENT(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, Userid);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				UserContactClass ucc = new UserContactClass();
				ucc.setUserId(rs.getInt("USER_ID"));
				ucc.setUserName(rs.getString("NAME"));
				ucc.setUserOrder(rs.getInt("SS"));
				list.add(ucc);

			}
		} catch (Exception e) {
			logger.error("findAllManagers" + e.getMessage());
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
	public List<ArcAttachmentClass> findAttachmentFilesByArcId(String ArcId) {
		List<ArcAttachmentClass> list = new ArrayList<ArcAttachmentClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.GET_ALL_ATTACHMENT_BY_REC(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, ArcId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				ArcAttachmentClass ucc = new ArcAttachmentClass();
				ucc.setAttId(rs.getString("ID"));
				ucc.setAttName(rs.getString("Att_Name"));
				ucc.setAttOwner(rs.getString("ATT_OWNER"));
				ucc.setAttSize(rs.getDouble("ATT_SIZE"));
				ucc.setOwnerName(rs.getString("OWNER_NAME"));
				ucc.setText1(rs.getString("TEXT_1"));
				ucc.setFileType(rs.getString("FILE_TYPE"));

				// if (rs.getString("FILE_TYPE").equalsIgnoreCase("pdf")
				// || rs.getString("Att_Name").equalsIgnoreCase("309")) {
				ucc.setIsPdf(true);
				// } else {
				// ucc.setIsPdf(false);
				// }
				if (rs.getString("Att_Name").equalsIgnoreCase("309")) {
					ucc.setAttLink(rs.getString("TEXT_1"));

				} else {
					ucc.setAttLink("http://localhost:8080/BKERY/1/" + rs.getString("Att_Name"));
				}

				list.add(ucc);

			}
		} catch (Exception e) {
			logger.error("findAttachmentFilesByArcId" + e.getMessage());
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
	public List<WrkPurposeClass> findAllWrkPurpose(Integer puserid) {
		List<WrkPurposeClass> list = new ArrayList<WrkPurposeClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.GET_ALL_ARC_PURPOSE(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, puserid);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				WrkPurposeClass wrp = new WrkPurposeClass();
				wrp.setPurpId(rs.getInt("id"));
				wrp.setPurpName(rs.getString("p_name"));
				list.add(wrp);
			}
		} catch (Exception e) {
			logger.error("findAllWrkPurpose" + e.getMessage());
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

	// @Override
	// public List<WrkCommentTypeClass> findAllCommenttTypes() {
	// List<WrkCommentTypeClass> list = new ArrayList<WrkCommentTypeClass>();
	// Connection connection = DataBaseConnectionClass.getConnection();
	// ResultSet rs = null;
	// CallableStatement callableStatement = null;
	// try {
	// String sql = "{call NEW_PKG_WEBKIT.GET_ALL_COMMENT_TYPE(?)}";
	// callableStatement = connection.prepareCall(sql);
	// callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
	// callableStatement.executeUpdate();
	// rs = (ResultSet) callableStatement.getObject(1);
	// while (rs.next()) {
	// WrkCommentTypeClass wrp = new WrkCommentTypeClass();
	// wrp.setWrkCommenttypeId(rs.getInt("id"));
	// wrp.setWrkCommenttypeName(rs.getString("name"));
	// list.add(wrp);
	//
	// }
	// } catch (Exception e) {
	// logger.error("findAllCommenttTypes" + e.getMessage());
	// } finally {
	// try {
	// if (rs != null)
	// rs.close();
	// if (callableStatement != null)
	// callableStatement.close();
	// if (connection != null)
	// connection.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// return list;
	// }

	@Override
	public String transferAttachment(InputStream inputStream, String v_att_name, int UserId, String ArcRecordId) {
		String CompleteName = "";
		String AttachmentId = "";
		String FileName = "";// System.err.println("1");
		String FileExtension = v_att_name.substring(v_att_name.lastIndexOf('.') + 1);
		Connection connection = DataBaseConnectionClass.getConnection();
		String destination = "C:\\temp\\";
		File temp = new File(destination);
		if (!temp.exists())
			temp.mkdirs();
		try {
			String name = getAttachmentRandomName() + v_att_name.substring(v_att_name.lastIndexOf('.'));

			BigDecimal ATT_ID_NUMBER = AddNewAttachment(UserId, ArcRecordId, name, 0.0);
			AttachmentId = ATT_ID_NUMBER.toString();
			OutputStream out = new FileOutputStream(new File(destination + name));
			CompleteName = destination + name;
			FileName = name;
			int read = 0;

			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			// System.err.println("2");
			InputStream fis = null;
			inputStream.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println("ERROR!! 009 : " + e.getMessage());
		}

		/**
		 * ***************UPLOADING TO REMOTE HTTP SERVER *************
		 */
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// http://localhost:8080/uploadservletproject/upload
			HttpPost httpPost = new HttpPost(
					"http://" + findSystemProperty("FILE_SERVER_IP") + ":8080/serverUpload/upload");
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
		if (AttachmentId == null) {
			AttachmentId = "";
		}
		return AttachmentId;
	}

	@Override
	public String getAttachmentRandomName() {
		String fileName = "UnKown";
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{ ? = call NEW_PKG_WEBKIT.getRandomName }";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			fileName = (String) callableStatement.getObject(1);
		} catch (Exception e) {
			logger.error("getAttachmentRandomName" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fileName;

	}

	@Override
	public void wrkTransferApplication(String WrkId, String arcRecordId, int fromEmp, int toEmp, String comment,
			int purp) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call PKG_WRK_ETC.PRC_WRK_SEND_TO(?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, WrkId);
			callableStatement.setString(2, arcRecordId);
			callableStatement.setInt(3, fromEmp);
			callableStatement.setInt(4, toEmp);
			callableStatement.setString(5, comment);
			callableStatement.setInt(6, purp);
			callableStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("wrkTransferApplication" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void wrkSaveComment(int UserId, String WrkAppId, String AppNo, String AppDate, int AppPapers, String AppTo,
			String AppSubject, String AppAttach, String AppLongComment, String FirstCopy, String SecondCopy,
			int FontSize, int CommentType, int CommentWroteBy, String commentWroteIn) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call New_PKG_WEBKIT.prc_save_comment(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, WrkAppId);
			callableStatement.setString(3, AppNo);
			callableStatement.setString(4, AppDate);
			callableStatement.setInt(5, AppPapers);
			callableStatement.setString(6, AppTo);
			callableStatement.setString(7, AppSubject);
			callableStatement.setString(8, AppAttach);
			callableStatement.setString(9, AppLongComment);
			callableStatement.setString(10, FirstCopy);
			callableStatement.setString(11, SecondCopy);
			callableStatement.setInt(12, 12);
			callableStatement.setInt(13, CommentType);
			callableStatement.setInt(14, CommentWroteBy);
			callableStatement.setString(15, commentWroteIn);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("wrkSaveComment" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public CommentClass getWrkCommentInfoByWrkId(String WrkApllicationId) {
		CommentClass commentClass = new CommentClass();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call New_PKG_WEBKIT.PRC_GET_COMMENT_BY_WRK_ID(?,?)}";
			callableStatement = connection.prepareCall(sql);

			callableStatement.setString(1, WrkApllicationId);

			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();

			rs = (ResultSet) callableStatement.getObject(2);
			if (rs != null)
				while (rs.next()) {

					CommentClass commclas = new CommentClass();
					commclas.setAppId(rs.getString("APP_ID"));
					commclas.setAppNo(rs.getString("APP_NO"));
					commclas.setAppDate(rs.getString("APP_DATE"));
					commclas.setAppPapers(rs.getInt("APP_PAPERS"));
					commclas.setAppTo(rs.getString("APP_TO"));
					commclas.setAppSubject(rs.getString("APP_SUBJECT"));
					commclas.setAppAttach(rs.getString("APP_ATTACH"));
					commclas.setLongComment(rs.getString("LONG_COMMENT"));
					commclas.setFirstCopy(rs.getString("FIRST_COPY"));
					commclas.setSecondCopy(rs.getString("SECOND_COPY"));
					commclas.setFontSize(rs.getInt("FONT_SIZE"));
					commclas.setWroteBy(rs.getInt("WROTE_BY"));
					commclas.setWroteDate(rs.getString("WROTE_IN"));
					commclas.setMarkedBy(rs.getInt("MARKED_BY"));
					commclas.setMarkdeIn(rs.getString("MARKED_IN"));
					commclas.setSignedBy(rs.getInt("SIGNED_BY"));
					commclas.setSignedIn(rs.getString("SIGNED_IN"));
					commclas.setCommentType(rs.getInt("COMMENT_TYPE"));
					commclas.setPrintedBy(rs.getInt("PRINTED_BY"));
					commclas.setPrintedIn(rs.getString("PRINTED_IN"));
					commclas.setSignTypeSN(rs.getString("SIGN_TYPE_S_N"));
					commentClass = commclas;
				}
		} catch (Exception e) {
			logger.error("getWrkCommentInfoByWrkId" + e.getMessage());
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
		return commentClass;
	}

	@Override
	public void showMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, ""));
	}

	@Override
	public String getEmployeeManagerId(String EmployeeId) {
		String ManagerId = "";
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call New_PKG_WEBKIT.PRC_GET_EMP_MANAGE_ID(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, EmployeeId);
			callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			ManagerId = (String) callableStatement.getObject(2);
		} catch (Exception e) {
			logger.error("getEmployeeManagerId" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ManagerId;
	}

	@Override
	public String CreateNewWrkComment(int UserId, String PAppSubject, int ToId, int AppPurpose, String AppTo,
			String AppLongComment, String AppSecondCopy, int AppCommentType, String AppFirstCopy, int AppPapers) {
		String outArcRecordId = "";
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call New_PKG_WEBKIT.prc_create_document(?,?,?,?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, PAppSubject);
			callableStatement.setInt(3, ToId);
			callableStatement.setInt(4, AppPurpose);
			callableStatement.setString(5, AppTo);
			callableStatement.setString(6, AppLongComment);
			callableStatement.setString(7, AppSecondCopy);
			callableStatement.setInt(8, AppCommentType);
			callableStatement.setString(9, AppFirstCopy);
			callableStatement.setInt(10, AppPapers);
			callableStatement.registerOutParameter(11, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			outArcRecordId = (String) callableStatement.getObject(11);
		} catch (Exception e) {
			logger.error("CreateNewWrkComment" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return outArcRecordId;
	}

	@Override
	public void MakeItRead(String vStepId, String vWrkId) {
		/*
		 * procedure PRC_MAKE_IT_READ(stepid number , wrkid number) is begin
		 * update wrk_application set f_read=1 where id=wrkid and step_id =
		 * stepid; commit; end;
		 */
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call New_PKG_WEBKIT.PRC_MAKE_IT_READ(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, vStepId);
			callableStatement.setString(2, vWrkId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("MakeItRead" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void NavigateToInbox() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

			ec.redirect(ec.getRequestContextPath() + "/faces/pages/userMail.xhtml?faces-redirect=false");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR!! 019 : " + e.getMessage());
			showErrMessage("ERROR!! 017 : " + e.getMessage());
		}

	}

	@Override
	public List<UserClass> findAllUsers() {
		List<UserClass> allusers = new ArrayList<UserClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call new_pkg_webkit.PRC_GET_ALL_USERS(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				UserClass uc = new UserClass(rs.getInt("USER_ID"), rs.getString("LOGIN_NAME"), rs.getString("REALNAME"),
						rs.getString("PASSWORD"));
				uc.setVUserRoleId(rs.getInt("WRK_ROLE_ID"));
				uc.setDepartmentName(rs.getString("DEPARTMENT_NAME"));
				uc.setManagerName(rs.getString("MANAGER_NAME"));
				uc.setEmpNo(rs.getString("empno"));
				allusers.add(uc);
			}
		} catch (Exception e) {
			logger.error("findAllUsers" + e.getMessage());
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
		return allusers;
	}

	@Override
	public void showErrMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
	}

	@Override
	public List<Main_menu_class> findAllMainMenu() {
		List<Main_menu_class> returnmainMenuList = new ArrayList<Main_menu_class>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_ALL_MAIN_MENUS(?)}"; // PRC_GET_ALL_MAIN_MENUS
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				returnmainMenuList.add(new Main_menu_class(rs.getInt("MENU_ID"), rs.getString("MAIN_MENU_TXT")));
			}
		} catch (Exception e) {
			logger.error("findAllMainMenu" + e.getMessage());
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
		return returnmainMenuList;
	}

	@Override
	public String getEmployeeRealName(int EmployeeId) {
		String empName = "";
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{?= call get_real_empname(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.setInt(2, EmployeeId);
			callableStatement.executeUpdate();
			empName = (String) callableStatement.getObject(1);
		} catch (Exception e) {
			logger.error("getEmployeeRealName" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empName;
	}

	@Override
	public void removeMenufromUser(int Userid, int Menuid) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call New_PKG_WEBKIT.PRC_DEL_MAIN_MENU_FRM_USER(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, Userid);
			callableStatement.setInt(2, Menuid);
			callableStatement.executeUpdate();
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			logger.error("removeMenufromUser" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addMenuToUser(int Userid, int Menuid) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call New_PKG_WEBKIT.PRC_ADD_MAIN_MENU_TO_USER(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, Userid);
			callableStatement.setInt(2, Menuid);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("addMenuToUser" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Main_menu_class> findAllAvaliableMainMenu(int Userid) {
		List<Main_menu_class> returnmainMenuList = new ArrayList<Main_menu_class>();
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_AVALIABLE_MAIN_MENUS(?,?)}";
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, Userid);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				returnmainMenuList.add(new Main_menu_class(rs.getInt("MENU_ID"), rs.getString("MAIN_MENU_TXT")));
			}
		} catch (Exception e) {
			logger.error("findAllAvaliableMainMenu" + e.getMessage());
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
		return returnmainMenuList;
	}

	@Override
	public List<Sub_menu_class> findAvaliableSubMenuByMainMenu(int userId, int MainMenuId) {
		List<Sub_menu_class> AvalSubMenu = new ArrayList<Sub_menu_class>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_AVALIABLE_SUB_MENUS(?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.setInt(2, MainMenuId);
			callableStatement.setInt(1, userId);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);
			while (rs.next()) {
				Sub_menu_class tmp = new Sub_menu_class(rs.getString("SUB_MENU_TXT"), rs.getString("SUB_MENU_ICON"),
						rs.getString("SUB_MENU_URL"));
				tmp.setVsub_menu_id(rs.getInt("SUB_MENU_ID"));

				AvalSubMenu.add(tmp);
			}
		} catch (Exception e) {
			logger.error("findAvaliableSubMenuByMainMenu" + e.getMessage());
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

		return AvalSubMenu;
	}

	@Override
	public void removeSubMenufromUser(int Userid, int SubMenuid) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call New_PKG_WEBKIT.PRC_DEL_SUB_MENU_FRM_USER(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, Userid);
			callableStatement.setInt(2, SubMenuid);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("removeSubMenufromUser" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addSubMenuToUser(int Userid, int SubMenuid) {

		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call New_PKG_WEBKIT.PRC_ADD_SUB_MENU_TO_USER(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, Userid);
			callableStatement.setInt(2, SubMenuid);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("addSubMenuToUser" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Sub_menu_class> findExistedSubMenuByMainMenu(int userId, int MainMenuId) {

		List<Sub_menu_class> ExistedSubMenu = new ArrayList<Sub_menu_class>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_existed_SUB_MENUS(?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.setInt(2, MainMenuId);
			callableStatement.setInt(1, userId);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);
			while (rs.next()) {
				Sub_menu_class tmp = new Sub_menu_class(rs.getString("SUB_MENU_TXT"), rs.getString("SUB_MENU_ICON"),
						rs.getString("SUB_MENU_URL"));
				tmp.setVsub_menu_id(rs.getInt("SUB_MENU_ID"));

				ExistedSubMenu.add(tmp);
			}
		} catch (Exception e) {
			logger.error("findExistedSubMenuByMainMenu" + e.getMessage());
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
		return ExistedSubMenu;
	}

	@Override
	public List<Sub_menu_class> findSubMenus(int UserId, int mainMenuId) {
		List<Sub_menu_class> x = new ArrayList<Sub_menu_class>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_SUBMENUS(?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setInt(2, mainMenuId);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);
			while (rs.next()) {
				Sub_menu_class tmp = new Sub_menu_class(rs.getString("SUB_MENU_TXT"), rs.getString("SUB_MENU_ICON"),
						rs.getString("SUB_MENU_URL"));
				tmp.setVsub_menu_id(rs.getInt("SUB_MENU_ID"));

				x.add(tmp);
			}
		} catch (Exception e) {
			logger.error("findSubMenus" + e.getMessage());
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
		return x;
	}

	@Override
	public List<UserMailObj> SearchEmployeeInbox(int EmployeeUserId, int searchType, String searchKey,
			String IncludeComment) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;

		try {
			String sql = "{call new_pkg_webkit.PRC_SEARCH_INBOX(?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeUserId);
			callableStatement.setString(2, "0");
			callableStatement.setString(3, searchKey);
			callableStatement.setString(4, IncludeComment);
			callableStatement.registerOutParameter(5, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(5);

			while (rs.next()) {
				// UserMailObj umc = new UserMailObj();
				UserMailObj umc = new UserMailObj();
				// rs.getString("datenumber"), rs.getString("ID"),
				// rs.getInt("step_id"), rs.getInt("from_id"),
				// rs.getInt("to_id"), rs.getString("usender"),
				// rs.getString("ureciever"), rs.getString("APP_ID"),
				// rs.getString("apptitle"),
				// rs.getString("h_date"), rs.getString("pname"),
				// rs.getInt("wrk_role_id"),
				// rs.getString("create_time"), rs.getString("incmno"),
				// rs.getString("incmdate"),
				// rs.getInt("stpes"), rs.getInt("attach"),
				// rs.getInt("hascomment"), rs.getString("m_date"),
				// rs.getInt("f_read"), rs.getString("COLOUR"),
				// rs.getInt("wrk_type"));
				outList.add(umc);

			}
		} catch (Exception e) {
			logger.error("SearchEmployeeInbox" + e.getMessage());
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
		return outList;
	}

	@Override
	public void addRecordToFavourit(String WrkApplicationId, int StepId, int UserId) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.Add_to_Favourit(?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, WrkApplicationId);
			callableStatement.setInt(2, StepId);
			callableStatement.setInt(3, UserId);
			callableStatement.executeUpdate();

		} catch (Exception e) {
			logger.error("addRecordToFavourit" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<UserMailObj> findFavouritInbox(int UserId) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call new_pkg_webkit.PRC_GET_FAVOURIT_INBOX(?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				// UserMailObj umc = new UserMailObj();
				UserMailObj umc = new UserMailObj();
				// rs.getString("datenumber"), rs.getString("ID"),
				// rs.getInt("step_id"), rs.getInt("from_id"),
				// rs.getInt("to_id"), rs.getString("usender"),
				// rs.getString("ureciever"), rs.getString("APP_ID"),
				// rs.getString("apptitle"),
				// rs.getString("h_date"), rs.getString("pname"),
				// rs.getInt("wrk_role_id"),
				// rs.getString("create_time"), rs.getString("incmno"),
				// rs.getString("incmdate"),
				// rs.getInt("stpes"), rs.getInt("attach"),
				// rs.getInt("hascomment"), rs.getString("m_date"),
				// rs.getInt("f_read"), rs.getString("COLOUR"),
				// rs.getInt("wrk_type"));
				outList.add(umc);

			}
		} catch (Exception e) {
			logger.error("findFavouritInbox" + e.getMessage());
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
		return outList;

	}

	@Override
	public void markComment(String WrkId, int UserId, String AppId) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.prc_marked_comment(?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, WrkId);
			callableStatement.setInt(2, UserId);
			callableStatement.setString(3, AppId);
			callableStatement.executeUpdate();
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			logger.error("markComment" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addNewAttachment(String FileName, InputStream in, int AddedBy, int FolderId) {
		CallableStatement stmt = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String destination = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
			String name = getAttachmentRandomName() + FileName.substring(FileName.lastIndexOf('.'));
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(destination + name));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			InputStream fis = null;
			try {
				String sql = " call New_PKG_WEBKIT.PRC_ADD_NEW_ATTACHMENT(?,?,?,?,?)";
				stmt = connection.prepareCall(sql);
				File image = new File(destination + name);
				fis = new FileInputStream(image);
				int ilen = (int) image.length();
				stmt.setBinaryStream(1, fis, ilen);
				stmt.setString(2, FileName);
				stmt.setInt(3, AddedBy);
				stmt.setInt(4, FolderId);
				stmt.setString(5, name.replaceAll("^.*\\.([^.]+)$", "$1"));
				stmt.executeUpdate();
				// System.err.println("Added Successfully");
			} catch (Exception e) {
				logger.error("addNewAttachment" + e.getMessage());
			}
			in.close();
			out.flush();
			out.close();

		} catch (IOException e) {

		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String encrypt(String string) {
		String encryptedString = "";
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		String sql = "{ ? =call ENCRYPT(?)}"; // PRC_GET_ALL_MAIN_MENUS
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.setString(2, string);
			callableStatement.executeUpdate();
			encryptedString = (String) callableStatement.getObject(1);
		} catch (Exception e) {
			logger.error("encrypt" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return encryptedString;
	}

	@Override
	public String decrypt(String string) {
		String decryptedString = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ ? =call DECRYPT(?)}"; // PRC_GET_ALL_MAIN_MENUS
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.setString(2, string);
			callableStatement.executeUpdate();
			decryptedString = (String) callableStatement.getObject(1);
		} catch (Exception e) {
			logger.error("decrypt" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return decryptedString;
	}

	@Override
	public List<AttachmentFileClass> getAllFilesbyUser(int UserId) {
		List<AttachmentFileClass> fileList = new ArrayList<AttachmentFileClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call new_pkg_webkit.PRC_GET_FILES_BY_USER(?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				AttachmentFileClass file = new AttachmentFileClass(rs.getString("ATTACHMENT_FILE_ID"),
						rs.getString("ATTACHMENT_FILE_SIZE"), rs.getString("ATTACHMENT_FILE_REAL_NAME"),
						rs.getInt("ATTACHMENT_FILE_ADDBY"), rs.getString(8), rs.getString(9),
						rs.getString("ATTACHMENT_EXT"));
				file.setAttachmentFiletype(rs.getString(10));
				fileList.add(file);

			}
		} catch (Exception e) {
			logger.error("getAllFilesbyUser" + e.getMessage());
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
		return fileList;
	}

	@Override
	public AttachmentFileClass getFilesbyId(String FileId) {
		AttachmentFileClass file = new AttachmentFileClass();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call new_pkg_webkit.PRC_GET_FILE_BY_fileid(?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, FileId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				file = new AttachmentFileClass(rs.getString("ATTACHMENT_FILE_ID"), rs.getString("ATTACHMENT_FILE_SIZE"),
						rs.getString("ATTACHMENT_FILE_REAL_NAME"), rs.getInt("ATTACHMENT_FILE_ADDBY"), rs.getString(8),
						rs.getString("ATTACHMENT_FILE_ADDIN"), rs.getString("ATTACHMENT_EXT"));
				file.setAttachmentFiletype(rs.getString(10));

			}

		} catch (Exception e) {
			logger.error("getFilesbyId" + e.getMessage());
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
		return file;
	}

	@Override
	public List<AttachmentFileClass> SearchAttachmentFiles(int UserId, String Search_key) {
		List<AttachmentFileClass> fileList = new ArrayList<AttachmentFileClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call new_pkg_webkit.PRC_SEARCH_FILES(?,?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, Search_key);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);

			while (rs.next()) {
				AttachmentFileClass file = new AttachmentFileClass(rs.getString("ATTACHMENT_FILE_ID"),
						rs.getString("ATTACHMENT_FILE_SIZE"), rs.getString("ATTACHMENT_FILE_REAL_NAME"),
						rs.getInt("ATTACHMENT_FILE_ADDBY"), rs.getString(8), rs.getString(9),
						rs.getString("ATTACHMENT_EXT"));
				file.setAttachmentFiletype(rs.getString(10));
				fileList.add(file);

			}
		} catch (Exception e) {
			logger.error("SearchAttachmentFiles" + e.getMessage());
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
		return fileList;
	}

	@Override
	public void deleteAttachmentFile(String fileAttachId) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_DELTE_ATTACHMENT_FILE(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, fileAttachId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("deleteAttachmentFile" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void sendRecordSingleCopy(String ArcRecordId, String WrkApplicationId, int FromUserId, int ToUserId,
			String LongComment, int PurposeId, String AttachedExplain, String Title, int StepId) {
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.PRC_SEND_Single_COPY(?,?,?,?,?,?,?,?,?)}";
		CallableStatement callableStatement = null;
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, ArcRecordId);
			callableStatement.setString(2, WrkApplicationId);
			callableStatement.setInt(3, FromUserId);
			callableStatement.setInt(4, ToUserId);
			callableStatement.setString(5, LongComment);
			callableStatement.setInt(6, PurposeId);
			callableStatement.setString(7, AttachedExplain);
			callableStatement.setString(8, Title);
			callableStatement.setInt(9, StepId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("sendRecordSingleCopy" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean WrkAppHasComment(String WrkApplicaionId) {
		boolean hasComment = false;
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		String sql = "{ ? =call NEW_PKG_WEBKIT.is_Wrk_app_has_Comment(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.NUMBER);
			callableStatement.setString(2, WrkApplicaionId);
			callableStatement.executeUpdate();
			BigDecimal returnvalue = (BigDecimal) callableStatement.getObject(1);
			if (returnvalue.compareTo(BigDecimal.ZERO) > 0) {
				hasComment = true;
			}
		} catch (Exception e) {
			logger.error("WrkAppHasComment" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hasComment;
	}

	@Override
	public List<UserContactClass> findCommentSignEmployeesList(int UserId, String WrkApplicationId) {
		List<UserContactClass> list = new ArrayList<UserContactClass>();
		Connection conn = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_SIGN_EMPLOYEE_LIST(?,?,?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, WrkApplicationId);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);
			while (rs.next()) {
				UserContactClass ucc = new UserContactClass();
				ucc.setUserId(rs.getInt("USER_ID"));
				ucc.setUserName(rs.getString("NAME"));
				// ucc.setUserOrder(rs.getInt("SS"));
				list.add(ucc);

			}
			conn.close();
		} catch (Exception e) {
			logger.error("findCommentSignEmployeesList" + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}

	@Override
	public void SignWrkComment(int UserId, String WrkApplicationId, int WrkApplicationStepId, String WrkCommentAppId,
			String ConfirmPassword, String Signtype, int SentToUserId) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_Sign_WRK_Comment(?,?,?,?,?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, WrkApplicationId);
			callableStatement.setInt(3, WrkApplicationStepId);
			callableStatement.setString(4, WrkCommentAppId);
			callableStatement.setString(5, ConfirmPassword);
			callableStatement.setString(6, Signtype);
			callableStatement.setInt(7, SentToUserId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("SignWrkComment" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean EmployeeHasSign(int EmployeeId) {
		boolean hasSign = false;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.PRC_IS_USER_HAS_SIGNTURE(?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeId);
			callableStatement.registerOutParameter(2, OracleTypes.NUMBER);
			callableStatement.executeUpdate();
			BigDecimal returnvalue = (BigDecimal) callableStatement.getObject(2);
			if (returnvalue.compareTo(BigDecimal.ZERO) > 0) {
				hasSign = true;
			}
		} catch (Exception e) {
			logger.error("EmployeeHasSign" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return hasSign;
	}

	@Override
	public void navigateToWebPage(String webpage) {

		FacesContext context = FacesContext.getCurrentInstance();
		NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(context, null, "/faces/pages/" + webpage + ".xhtml?faces-redirect=true");

	}

	@Override
	public void navigateToAccessDenied() {
		navigateToWebPage("accessdeied");
	}

	@Override
	public String retrieveRecord(int UserId, String WrkApplicationId, String WrkApplicationAppId) {
		String retString = "";
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.retrieve_Record_to_Inbox(?,?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(3, WrkApplicationAppId);
			callableStatement.setString(2, WrkApplicationId);
			callableStatement.registerOutParameter(4, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			retString = (String) callableStatement.getObject(4);
			if (retString == null) {
				retString = "";
			}
		} catch (Exception e) {
			logger.error("retrieveRecord" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return retString;

	}

	// NOt Tested ,, May this function not working
	@Override
	public void CallJasperReport(String CallingUrl) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
			navigationHandler.handleNavigation(context, null,
					"/b/jasperservlet?rpt=" + new DataAccessImpl().encrypt(CallingUrl));

		} catch (Exception e) {
			System.err.println("ERROR!! 048 : " + e.getMessage());
			showErrMessage("ERROR!! 048 : " + e.getMessage());
		}

	}

	@Override
	public void sendCommentCopy(int UserId, int ToId, String WrkCommentAppId, String ArcRecordId) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_send_comment_copy(?,?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setInt(2, ToId);
			callableStatement.setString(3, WrkCommentAppId);
			callableStatement.setString(4, ArcRecordId);
			callableStatement.executeUpdate();

		} catch (Exception e) {
			logger.error("sendCommentCopy" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * send_New_Internal_Record( ARC_REC_TITLE varchar2, ARC_APP_TYP number,
	 * ARC_INCOME_NO varchar2, ARC_IS_IMPORTANT number, -- 1 ,0 curr_user_id
	 * number, toid NUMBER, tocomm VARCHAR2,-- Ø§Ù„Ø´Ø±Ø­ topurp NUMBER,
	 * WRK_COMM_IS_SECRET number, -- 1,0 CREATE_NEW_INCOME VARCHAR2, ARC_REC_ID
	 * OUT varchar2 --to decide if procedure create new income or NO .. ('Y'
	 * ,'N') );
	 */
	@Override
	public String SendNewRecord(String ArcRecTitle, int ArcIsImportant, int UserId, int ToId, String ArcComment,
			int ArcPurp, int WrkCommIsSecret, String CreateNewIncomeNo) {
		String ArcRecordId = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.send_New_Internal_Record(?,?,?,?,?,?,?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, ArcRecTitle);
			callableStatement.setInt(2, ArcIsImportant);
			callableStatement.setInt(3, UserId);
			callableStatement.setInt(4, ToId);
			callableStatement.setString(5, ArcComment);
			callableStatement.setInt(6, ArcPurp);
			callableStatement.setInt(7, WrkCommIsSecret);
			callableStatement.setString(8, CreateNewIncomeNo);
			callableStatement.registerOutParameter(9, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			ArcRecordId = (String) callableStatement.getObject(9);

		} catch (Exception e) {
			logger.error("SendNewRecord" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ArcRecordId;
	}

	@Override
	public String SendNewRecordcopy(String ArcRecTitle, int ArcIsImportant, int UserId, int ToId, String ArcComment,
			int ArcPurp, int WrkCommIsSecret, String CreateNewIncomeNo, String OriginalArcRecordId) {
		String ArcRecordId = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.send_New_Internal_Record_copy(?,?,?,?,?,?,?,?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, ArcRecTitle);
			callableStatement.setInt(2, ArcIsImportant);
			callableStatement.setInt(3, UserId);
			callableStatement.setInt(4, ToId);
			callableStatement.setString(5, ArcComment);
			callableStatement.setInt(6, ArcPurp);
			callableStatement.setInt(7, WrkCommIsSecret);
			callableStatement.setString(8, CreateNewIncomeNo);
			callableStatement.registerOutParameter(9, OracleTypes.VARCHAR);
			callableStatement.setString(10, OriginalArcRecordId);
			callableStatement.executeUpdate();
			ArcRecordId = (String) callableStatement.getObject(9);

		} catch (Exception e) {
			logger.error("SendNewRecordcopy" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ArcRecordId;
	}

	@Override
	public BigDecimal AddNewAttachment(int UserId, String ArcRecordId, String ArcAttName, double ArcAttSize) {
		BigDecimal ATT_ID = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.PRC_ADD_ATTACHMENT(?,?,?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, ArcRecordId);
			callableStatement.setString(3, ArcAttName);
			callableStatement.setDouble(4, ArcAttSize);
			callableStatement.registerOutParameter(5, OracleTypes.NUMBER);
			callableStatement.executeUpdate();
			ATT_ID = (BigDecimal) callableStatement.getObject(5);
		} catch (Exception e) {
			logger.error("AddNewAttachment" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ATT_ID;

	}

	@Override
	public String findAllowedFilesTypes(String fileTypes) {
		String x = "/(\\.|\\/)(gif|jpe?g|png|pdf|doc|docx|exe|mp3|dwg|mp4)$/";
		return x;

	}

	/*
	 * Add_New_income_Record (curr_user_id NUMBER, ARC_REC_TITLE VARCHAR2,
	 * Arc_Purp NUMBER, --when sending to recpient contact Arc_Type NUMBER,
	 * Letter_To NUMBER, -- department that will recieve this record Letter_From
	 * NUMBER, Arc_Send_To NUMBER, -- when sending to One contant this is his
	 * USER_ID Letter_From_No VARCHAR2, Letter_From_Date VARCHAR2,
	 * Refrenced_Letter_No VARCHAR2, Mobile_Number VARCHAR2, tocomm VARCHAR2,
	 * ARC_RECORD_IS_IMPORTANT NUMBER, New_ARC_RECORD_ID OUT Varchar2);
	 */
	@Override
	public String CreateNewIncomeRecord(int UserId, String ArcTitle, int ArcPurp, int ArcTyp, int LetterTo,
			int LetterFrom, int ArcSendTo, String LetterFromNo, String LetterFromDate, String RefrencedLetterNo,
			String MobileNumber, String LongComment, int IsArcRecordImportant) {
		String NewArcRecordId = "";
		List<ArcApplicationTypeClass> RetList = new ArrayList<ArcApplicationTypeClass>();
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.Add_New_income_Record(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, ArcTitle);
			callableStatement.setInt(3, ArcPurp);
			callableStatement.setInt(4, ArcTyp);
			callableStatement.setInt(5, LetterTo);
			callableStatement.setInt(6, LetterFrom);
			callableStatement.setInt(7, ArcSendTo);
			callableStatement.setString(8, LetterFromNo);
			callableStatement.setString(9, LetterFromDate);
			callableStatement.setString(10, RefrencedLetterNo);
			callableStatement.setString(11, MobileNumber);
			callableStatement.setString(12, LongComment);
			callableStatement.setInt(13, IsArcRecordImportant);
			callableStatement.registerOutParameter(14, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			NewArcRecordId = (String) callableStatement.getObject(14);

		} catch (Exception e) {
			logger.error("CreateNewIncomeRecord" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return NewArcRecordId;
	}

	@Override
	public List<ArcApplicationTypeClass> findAllApplicationTypes() {
		List<ArcApplicationTypeClass> RetList = new ArrayList<ArcApplicationTypeClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_ALL_ARC_APP_TYPES(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				ArcApplicationTypeClass apptype = new ArcApplicationTypeClass(rs.getInt(1), rs.getString(2));
				RetList.add(apptype);
			}
		} catch (Exception e) {
			logger.error("findAllApplicationTypes" + e.getMessage());
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
		return RetList;
	}

	@Override
	public List<WrkLetterFromClass> findAllWrkLetterFroms() {
		List<WrkLetterFromClass> RetList = new ArrayList<WrkLetterFromClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_ALL_WRK_LETTER_FROM(?)}";
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				WrkLetterFromClass Wrkfrom = new WrkLetterFromClass(rs.getInt(1), rs.getString(2));
				RetList.add(Wrkfrom);
			}
		} catch (Exception e) {
			logger.error("findAllWrkLetterFroms" + e.getMessage());
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
		return RetList;
	}

	@Override
	public List<WrkLetterToClass> findAllAWrkLetterTos() {
		List<WrkLetterToClass> RetList = new ArrayList<WrkLetterToClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_ALL_WRK_LETTER_to(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				WrkLetterToClass Wrkto = new WrkLetterToClass(rs.getInt(1), rs.getString(2));
				RetList.add(Wrkto);
			}
		} catch (Exception e) {
			logger.error("findAllAWrkLetterTos" + e.getMessage());
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
		return RetList;
	}

	@Override
	public List<WrkArchiveRecipentClass> findAllWrkArchiveRecipents() {
		List<WrkArchiveRecipentClass> RetList = new ArrayList<WrkArchiveRecipentClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_ALL_WRK_ARC_Recipents(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				WrkArchiveRecipentClass recp = new WrkArchiveRecipentClass(rs.getInt(1), rs.getString(2));
				RetList.add(recp);
			}
		} catch (Exception e) {
			logger.error("findAllWrkArchiveRecipents" + e.getMessage());
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
		return RetList;
	}

	@Override
	public List<String> findAllWrkCommentAppTo(String SearchKey) {
		// PRC_GET_WRK_COMMENT_APP_TO
		List<String> RetList = new ArrayList<String>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_WRK_COMMENT_APP_TO(?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, SearchKey);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				String x = rs.getString(1);
				RetList.add(x);
			}
		} catch (Exception e) {
			logger.error("findAllWrkCommentAppTo" + e.getMessage());
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
		return RetList;
	}
	/*
	 * PROCEDURE PRC_CREATE_NEW_USER( pLOGIN_NAME VARCHAR2, pPASSWORD VARCHAR2,
	 * pFNAME VARCHAR2, pLNAME VARCHAR2, pTitLE_Id NUMBER, pJOB_ID NUMBER,
	 * pDEPT_ID NUMBER, pMGR_ID NUMBER, pWRK_ROLE_ID NUMBER, pSEC_ID NUMBER,
	 * PMOBILE VARCHAR2, pUSER_ID OUT NUMBER )
	 */

	@Override
	public int createNewUser(String LoginName, String Password, String FirstName, String LastName, int titleId,
			int JobId, int DepartmentId, int ManagerId, int WrkRoleId, int SectionId, String MobileNumber) {
		int NewUserId = 0;
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_CREATE_NEW_USER(?,?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, LoginName.toUpperCase());
			callableStatement.setString(2, Password);
			callableStatement.setString(3, FirstName);
			callableStatement.setString(4, LastName);
			callableStatement.setInt(5, titleId);
			callableStatement.setInt(6, JobId);
			callableStatement.setInt(7, DepartmentId);
			callableStatement.setInt(8, ManagerId);
			callableStatement.setInt(9, WrkRoleId);
			callableStatement.setInt(10, SectionId);
			callableStatement.setString(11, MobileNumber);
			callableStatement.registerOutParameter(12, OracleTypes.NUMBER);
			callableStatement.executeUpdate();
			BigDecimal x = (BigDecimal) callableStatement.getObject(12);
			NewUserId = x.intValue();
			// System.err.println("User Created" + NewUserId);
		} catch (Exception e) {
			logger.error("createNewUser" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return NewUserId;
	}

	@Override
	public List<WrkDeptClass> findAllDepartment() {
		List<WrkDeptClass> RetList = new ArrayList<WrkDeptClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_ALL_WRK_DEPT(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				WrkDeptClass x = new WrkDeptClass();
				x.setDeptId(rs.getInt(1));
				x.setDeptName(rs.getString(2));
				RetList.add(x);
			}
		} catch (Exception e) {
			logger.error("findAllDepartment" + e.getMessage());
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
		return RetList;
	}

	@Override
	public List<SysTitleClass> findAllSysTitles() {
		List<SysTitleClass> RetList = new ArrayList<SysTitleClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_ALL_SYS_TITLES(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				SysTitleClass x = new SysTitleClass();
				x.setTitleId(rs.getInt(1));
				x.setTitle(rs.getString(2));
				RetList.add(x);
			}
		} catch (Exception e) {
			logger.error("findAllSysTitles" + e.getMessage());
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
		return RetList;
	}

	@Override
	public List<WrkRolesClass> findAllRoles() {
		List<WrkRolesClass> RetList = new ArrayList<WrkRolesClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_ALL_WRK_ROLES(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				WrkRolesClass x = new WrkRolesClass();
				x.setRoleId(rs.getInt(1));
				x.setRoleName(rs.getString(2));
				RetList.add(x);
			}
		} catch (Exception e) {
			logger.error("findAllRoles" + e.getMessage());
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
		return RetList;
	}

	@Override
	public List<WrkSectionClass> findAllSectionsByDept(int deptId) {
		List<WrkSectionClass> RetList = new ArrayList<WrkSectionClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_WRK_SECTION_by_dept(?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, deptId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				WrkSectionClass x = new WrkSectionClass();
				x.setWrkSectionId(rs.getInt(1));
				x.setWrkSectionName(rs.getString(2));
				RetList.add(x);
			}
		} catch (Exception e) {
			logger.error("findAllSectionsByDept" + e.getMessage());
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
		return RetList;
	}

	@Override
	public List<WrkJobClass> findAllJobsBySection(int SectionId) {
		List<WrkJobClass> RetList = new ArrayList<WrkJobClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_WRK_JOB_BY_SECTION(?,?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, SectionId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				WrkJobClass x = new WrkJobClass();
				x.setJobId(rs.getInt(1));
				x.setJobName(rs.getString(2));
				RetList.add(x);
			}
		} catch (Exception e) {
			logger.error("findAllJobsBySection" + e.getMessage());
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
		return RetList;
	}

	@Override
	public void showModalDialog(String Msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ø±Ø³Ø§Ù„Ø©", Msg);
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	@Override
	public int addNewsection(String sectionName, int DeptId) {
		int returnNewSectionId = 0;
		Connection conn = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_NEW_SECTION(?,?,?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.setString(1, sectionName);
			callableStatement.setInt(2, DeptId);
			callableStatement.registerOutParameter(3, OracleTypes.NUMBER);
			callableStatement.executeUpdate();
			returnNewSectionId = ((BigDecimal) callableStatement.getObject(3)).intValue();
		} catch (Exception e) {
			logger.error("addNewsection" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return returnNewSectionId;
	}

	@Override
	public int addNewDept(String DepartmentNameAR, String DepartmentNameEN) {
		int returnNewDeptId = 0;
		Connection conn = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_NEW_DEPT(?,?,?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.setString(1, DepartmentNameAR);
			callableStatement.setString(2, DepartmentNameEN);
			callableStatement.registerOutParameter(3, OracleTypes.NUMBER);
			callableStatement.executeUpdate();
			returnNewDeptId = ((BigDecimal) callableStatement.getObject(3)).intValue();
		} catch (Exception e) {
			logger.error("addNewDept" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnNewDeptId;
	}

	@Override
	public int removeSectionById(int Section_id) {
		int returnerrorCode = 0;
		Connection conn = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_DEL_SECTION_BY_ID(?,?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.setInt(1, Section_id);
			callableStatement.registerOutParameter(2, OracleTypes.NUMBER);
			callableStatement.executeUpdate();
			returnerrorCode = ((BigDecimal) callableStatement.getObject(2)).intValue();
		} catch (Exception e) {
			logger.error("removeSectionById" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnerrorCode;

	}

	@Override
	public String findCurrentHijriDate() {
		String CurrentHijriDate = "";
		Connection conn = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_CURRENT_HIJRI_DATE(?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			CurrentHijriDate = (String) callableStatement.getObject(1);
		} catch (Exception e) {
			logger.error("findCurrentHijriDate" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return CurrentHijriDate;

	}

	@Override
	public String findCurrentHijriYear() {
		String CurrentHijriYear = "";
		Connection conn = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_CURRENT_HIJRI_YEAR(?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			CurrentHijriYear = (String) callableStatement.getObject(1);
		} catch (Exception e) {
			logger.error("findCurrentHijriYear" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return CurrentHijriYear;
	}

	@Override
	public int findDayOrderInWeek(String pDate) {
		int CurrentHijriDayOrder = 0;
		Connection conn = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_DAY_ORDER_IN_WEEK(?,?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.setString(1, pDate);
			callableStatement.registerOutParameter(2, OracleTypes.NUMBER);
			callableStatement.executeUpdate();
			CurrentHijriDayOrder = ((BigDecimal) callableStatement.getObject(2)).intValue();
		} catch (Exception e) {
			logger.error("findDayOrderInWeek" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return CurrentHijriDayOrder;
	}

	@Override
	public int findCurrentMonth() {
		int CurrentHijrimonth = 0;
		Connection conn = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_CURRENT_HIJRI_MONTH(?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.NUMBER);
			callableStatement.executeUpdate();
			CurrentHijrimonth = ((BigDecimal) callableStatement.getObject(1)).intValue();
		} catch (Exception e) {
			logger.error("findCurrentMonth" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return CurrentHijrimonth;
	}

	@Override
	public String findCurrentMonthName() {
		String CurrentHijriMonthName = "";
		Connection conn = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_CURR_HIJRI_MONTH_TXT(?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			CurrentHijriMonthName = (String) callableStatement.getObject(1);
		} catch (Exception e) {
			logger.error("findCurrentMonthName" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return CurrentHijriMonthName;
	}

	@Override
	public Map<String, String> findAllHijriYears() {
		Map<String, String> Years = new HashMap<String, String>();
		Connection conn = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_ALL_YEARS(?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				String x = rs.getString(1);
				Years.put(x, x);
			}
		} catch (Exception e) {
			logger.error("findAllHijriYears" + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Years;
	}

	@Override
	public Map<String, String> findAllHijriMonths() {
		Map<String, String> Months = new HashMap<String, String>();
		Months.put("1-Ù…Ø­Ø±Ù…", "1");
		Months.put("2-ØµÙ�Ø±", "2");
		Months.put("3-Ø±Ø¨ÙŠØ¹ Ø§Ù„Ø£ÙˆÙ„", "3");
		Months.put("4-Ø±Ø¨ÙŠØ¹ Ø§Ù„Ø«Ø§Ù†ÙŠ", "4");
		Months.put("5- Ø¬Ù…Ø§Ø¯ÙŠ Ø§Ù„Ø£ÙˆÙ„", "5");
		Months.put("6-Ø¬Ù…Ø§Ø¯ÙŠ Ø§Ù„Ø«Ø§Ù†ÙŠ", "6");
		Months.put("7-Ø±Ø¬Ø¨", "7");
		Months.put("8-Ø´Ø¹Ø¨Ø§Ù†", "8");
		Months.put("9-Ø±Ù…Ø¶Ø§Ù†", "9");
		Months.put("10-Ø´ÙˆØ§Ù„", "10");
		Months.put("11-Ø°Ùˆ Ø§Ù„Ù‚Ø¹Ø¯Ø©", "11");
		Months.put("12-Ø°Ùˆ Ø§Ù„Ø­Ø¬Ø©", "12");

		return Months;
	}

	@Override
	public Map<String, String> findAllHijriDays(String YearNbr, int MonthNbr) {
		Map<String, String> Dayes = new HashMap<String, String>();
		Connection conn = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_MONTH_DAYS_COUNT(?,?,?)}";

			callableStatement = conn.prepareCall(sql);
			callableStatement.setString(1, YearNbr);
			callableStatement.setInt(2, MonthNbr);
			callableStatement.registerOutParameter(3, OracleTypes.NUMBER);
			callableStatement.executeUpdate();
			int MonthDays = ((BigDecimal) callableStatement.getObject(3)).intValue();
			for (int i = 1; i <= MonthDays; i++) {
				Dayes.put(i + "", i + "");
			}
		} catch (Exception e) {
			logger.error("findAllHijriDays" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Dayes;

	}

	@Override
	public UserClass findEmployeeById(int EmployeeId) {
		UserClass User = new UserClass();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call new_pkg_webkit.PRC_GET_Employee_by_id(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				UserClass uc = new UserClass(rs.getInt("USER_ID"), rs.getString("LOGIN_NAME"), rs.getString("REALNAME"),
						rs.getString("PASSWORD"));
				uc.setVUserRoleId(rs.getInt("WRK_ROLE_ID"));
				uc.setDepartmentName(rs.getString("DEPARTMENT_NAME"));
				uc.setManagerName(rs.getString("MANAGER_NAME"));
				User = uc;
			}
		} catch (Exception e) {
			logger.error("findEmployeeById" + e.getMessage());
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

		return User;

	}

	@Override
	public List<UserClass> convertContactListToUserList(List<UserContactClass> contactList) {
		List<UserClass> userList = new ArrayList<UserClass>();
		userList.clear();
		for (UserContactClass contact : contactList) {
			userList.add(new UserClass(contact.getUserId(), contact.getUserName(), contact.getUserRole()));
		}
		return userList;
	}

	@Override
	public List<UserContactClass> findInboxSenderList(int pUserId) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		List<UserContactClass> list = new ArrayList<UserContactClass>();
		Connection conn = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_SENDERS_LIST(?,?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.setInt(1, pUserId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				UserContactClass ucc = new UserContactClass();
				ucc.setUserId(rs.getInt("USER_ID"));
				ucc.setUserName(rs.getString("NAME"));
				list.add(ucc);

			}
		} catch (Exception e) {
			logger.error("findInboxSenderList" + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (callableStatement != null)
					callableStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;

	}

	@Override
	public List<UserMailObj> findEmployeeInboxBySenderId(int EmployeeUserId, int EmployeeSenderId) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		int ErrNumber = 0;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_EMPLOYEE_INBOX_BY_USER(?,?,?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeUserId);
			callableStatement.setInt(3, ErrNumber);
			callableStatement.setInt(4, EmployeeSenderId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				// UserMailObj umc = new UserMailObj();
				UserMailObj umc = new UserMailObj();
				// rs.getString("datenumber"), rs.getString("ID"),
				// rs.getInt("step_id"), rs.getInt("from_id"),
				// rs.getInt("to_id"), rs.getString("usender"),
				// rs.getString("ureciever"), rs.getString("APP_ID"),
				// rs.getString("apptitle"),
				// rs.getString("h_date"), rs.getString("pname"),
				// rs.getInt("wrk_role_id"),
				// rs.getString("create_time"), rs.getString("incmno"),
				// rs.getString("incmdate"),
				// rs.getInt("stpes"), rs.getInt("attach"),
				// rs.getInt("hascomment"), rs.getString("m_date"),
				// rs.getInt("f_read"), rs.getString("COLOUR"),
				// rs.getInt("wrk_type"));
				umc.setHasComment(rs.getInt("hasCommentx"));
				umc.setFirstComment(rs.getString("firstComment"));
				outList.add(umc);

			}
		} catch (Exception e) {
			logger.error("findEmployeeInboxBySenderId" + e.getMessage());
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
		return outList;

	}

	@Override
	public List<UserMailObj> findEmployeeInboxDaily(int EmployeeUserId) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		int ErrNumber = 0;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_EMPLOYEE_INBOX_TODAY(?,?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeUserId);
			callableStatement.setInt(3, ErrNumber);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				// UserMailObj umc = new UserMailObj();
				UserMailObj umc = new UserMailObj();
				// rs.getString("datenumber"), rs.getString("ID"),
				// rs.getInt("step_id"), rs.getInt("from_id"),
				// rs.getInt("to_id"), rs.getString("usender"),
				// rs.getString("ureciever"), rs.getString("APP_ID"),
				// rs.getString("apptitle"),
				// rs.getString("h_date"), rs.getString("pname"),
				// rs.getInt("wrk_role_id"),
				// rs.getString("create_time"), rs.getString("incmno"),
				// rs.getString("incmdate"),
				// rs.getInt("stpes"), rs.getInt("attach"),
				// rs.getInt("hascomment"), rs.getString("m_date"),
				// rs.getInt("f_read"), rs.getString("COLOUR"),
				// rs.getInt("wrk_type"));
				umc.setHasComment(rs.getInt("hasCommentx"));
				umc.setFirstComment(rs.getString("firstComment"));
				outList.add(umc);

			}
		} catch (Exception e) {
			logger.error("findEmployeeInboxDaily" + e.getMessage());
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
		return outList;
	}

	@Override
	public List<UserMailObj> findEmployeeOutboxArchive(int EmployeeUserId) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_EMPLOYEE_OUTBOX_ARCH(?,?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeUserId);
			callableStatement.setInt(3, 0);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				UserMailObj umc = new UserMailObj();
				// rs.getString("datenumber"), rs.getString("ID"),
				// rs.getInt("step_id"), rs.getInt("from_id"),
				// rs.getInt("to_id"), rs.getString("usender"),
				// rs.getString("ureciever"), rs.getString("APP_ID"),
				// rs.getString("apptitle"),
				// rs.getString("h_date"), rs.getString("pname"),
				// rs.getInt("wrk_role_id"),
				// rs.getString("create_time"), rs.getString("incmno"),
				// rs.getString("incmdate"),
				// rs.getInt("stpes"), rs.getInt("attach001"),
				// rs.getInt("hascomment"), rs.getString("m_date"),
				// rs.getInt("f_read"), rs.getString("COLOUR"),
				// rs.getInt("wrk_type"));
				outList.add(umc);

			}
		} catch (Exception e) {
			logger.error("findEmployeeOutboxArchive" + e.getMessage());
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
		return outList;
	}

	@Override
	public List<UserMailObj> searchEmployeeOutbox(int EmployeeUserId, String SearchKey, String IncludeArchiveYN) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_SEARCH_EMPLOYEE_OUTBOX(?,?,?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeUserId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.setString(3, SearchKey);
			callableStatement.setString(4, IncludeArchiveYN);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				UserMailObj umc = new UserMailObj();
				// rs.getString("datenumber"), rs.getString("ID"),
				// rs.getInt("step_id"), rs.getInt("from_id"),
				// rs.getInt("to_id"), rs.getString("usender"),
				// rs.getString("ureciever"), rs.getString("APP_ID"),
				// rs.getString("apptitle"),
				// rs.getString("h_date"), rs.getString("pname"),
				// rs.getInt("wrk_role_id"),
				// rs.getString("create_time"), rs.getString("incmno"),
				// rs.getString("incmdate"),
				// rs.getInt("stpes"), rs.getInt("attach"),
				// rs.getInt("hascomment"), rs.getString("m_date"),
				// rs.getInt("f_read"), rs.getString("COLOUR"),
				// rs.getInt("wrk_type"));
				outList.add(umc);

			}
		} catch (Exception e) {
			logger.error("searchEmployeeOutbox" + e.getMessage());
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
		return outList;
	}

	@Override
	public Map findAllReportParameter(String paramsUrl) {
		// String t = "test&dpt=156799&x=121221&yddd=wqeqwewqe";
		String t = paramsUrl;
		Map params = new HashMap();
		int i = t.indexOf("&");
		List<Integer> Indexs_i = new ArrayList<>();
		List<String> paramsString = new ArrayList<>();
		while (i >= 0) {
			Indexs_i.add(i);
			i = t.indexOf("&", i + 1);
		}
		for (int x = 0; x < Indexs_i.size(); x++) {
			try {
				paramsString.add(t.substring(Indexs_i.get(x), Indexs_i.get(x + 1)).substring(1));
			} catch (IndexOutOfBoundsException e) {
				paramsString.add(t.substring(t.lastIndexOf("&")).substring(1));
			}
		}

		for (String paramsString1 : paramsString) {
			String pName = paramsString1.substring(0, paramsString1.indexOf("="));
			String pvalue = paramsString1.substring(paramsString1.indexOf("=") + 1);
			params.put(pName, pvalue);
		}

		return params;
	}

	@Override
	public List<UserFolderClass> findAllUserFolders(int Userid) {
		List<UserFolderClass> outFolderList = new ArrayList<>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_USER_FOLDERS(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, Userid);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				UserFolderClass ufc = new UserFolderClass(rs.getInt(1), rs.getString(2), rs.getInt(3));
				outFolderList.add(ufc);
			}
		} catch (Exception e) {
			logger.error("findAllUserFolders" + e.getMessage());
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
		return outFolderList;
	}

	@Override
	public int EmployeeArchivedArch(int UserId) {
		CallableStatement callableStatement = null;
		BigDecimal returnCount = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ ?=call New_PKG_WEBKIT.PRC_GET_EMP_OUTBOX_ARCH_CNT(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.NUMBER);
			callableStatement.setInt(2, UserId);
			callableStatement.executeUpdate();
			returnCount = (BigDecimal) callableStatement.getObject(1);

			return returnCount.intValue();
		} catch (Exception e) {
			logger.error("EmployeeArchivedArch" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;

	}

	@Override
	public String convertTextWithArNumric(String txt) {
		String returnText = txt;
		returnText = returnText.replaceAll("0", "\\\u0660").replaceAll("1", "\\\u0661").replaceAll("2", "\\\u0662")
				.replaceAll("3", "\\\u0663").replaceAll("4", "\\\u0664").replaceAll("5", "\\\u0665")
				.replaceAll("6", "\\\u0666").replaceAll("7", "\\\u0667").replaceAll("8", "\\\u0668")
				.replaceAll("9", "\\\u0669");
		return returnText;
	}

	@Override
	public List<UserMailObj> findEmployeeFolderRecords(int UserId, int FolderId) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_RECORDS_IN_FOLDER(?,?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setInt(2, FolderId);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);

			while (rs.next()) {
				// UserMailObj umc = new UserMailObj();
				UserMailObj umc = new UserMailObj();
				// rs.getString("datenumber"), rs.getString("ID"),
				// rs.getInt("step_id"), rs.getInt("from_id"),
				// rs.getInt("to_id"), rs.getString("usender"),
				// rs.getString("ureciever"), rs.getString("APP_ID"),
				// rs.getString("apptitle"),
				// rs.getString("h_date"), rs.getString("pname"),
				// rs.getInt("wrk_role_id"),
				// rs.getString("create_time"), rs.getString("incmno"),
				// rs.getString("incmdate"),
				// rs.getInt("stpes"), rs.getInt("attach"),
				// rs.getInt("hascomment"), rs.getString("m_date"),
				// rs.getInt("f_read"), rs.getString("COLOUR"),
				// rs.getInt("wrk_type"));
				umc.setHasComment(rs.getInt("hasCommentx"));
				umc.setFirstComment(rs.getString("firstComment"));
				outList.add(umc);

			}
		} catch (Exception e) {
			logger.error("findEmployeeFolderRecords" + e.getMessage());
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
		return outList;

	}

	@Override
	public List<UserMailObj> SearchEmployeeInboxByDate(int EmployeeUserId, int searchType, String searchKey,
			String StartDate, String EndDate) {
		List<UserMailObj> outList = new ArrayList<UserMailObj>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call new_pkg_webkit.PRC_SEARCH_INBOX_BY_DATE(?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeUserId);
			callableStatement.setInt(2, searchType);
			callableStatement.setString(3, searchKey);
			callableStatement.setString(4, StartDate);
			callableStatement.setString(5, EndDate);
			callableStatement.registerOutParameter(6, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(6);

			while (rs.next()) {
				UserMailObj umc = new UserMailObj();
				// rs.getString("datenumber"), rs.getString("ID"),
				// rs.getInt("step_id"), rs.getInt("from_id"),
				// rs.getInt("to_id"), rs.getString("usender"),
				// rs.getString("ureciever"), rs.getString("APP_ID"),
				// rs.getString("apptitle"),
				// rs.getString("h_date"), rs.getString("pname"),
				// rs.getInt("wrk_role_id"),
				// rs.getString("create_time"), rs.getString("incmno"),
				// rs.getString("incmdate"),
				// rs.getInt("stpes"), rs.getInt("attach"),
				// rs.getInt("hascomment"), rs.getString("m_date"),
				// rs.getInt("f_read"), rs.getString("COLOUR"),
				// rs.getInt("wrk_type"));
				outList.add(umc);

			}
		} catch (Exception e) {
			logger.error("SearchEmployeeInboxByDate" + e.getMessage());
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
		return outList;
	}

	@Override
	public boolean CheckSignturePassword(int UserId, String Password) {
		CallableStatement callableStatement = null;
		boolean returnValue = false;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_EMP_SIGNTURE_CODE(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, Password);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String rs = (String) callableStatement.getObject(3);
			// System.err.println("" + rs);
			if ("1".equals(rs)) {
				returnValue = true;
			}

		} catch (Exception e) {
			logger.error("CheckSignturePassword" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	@Override
	public String findDepartmentNameById(int departmentId) {
		String deptname = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_DEPT_NAME(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, departmentId);
			callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			deptname = (String) callableStatement.getObject(2);
		} catch (Exception e) {
			logger.error("findDepartmentNameById" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return deptname;
	}

	@Override
	public void addNewWrkLetterfrom(String NewLetterFromName) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_WRK_LETTER_FROM(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, NewLetterFromName);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("addNewWrkLetterfrom" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteWrkLetterfrom(int LetterFormId) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_DEL_WRK_LETTER_FROM(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, LetterFormId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("deleteWrkLetterfrom" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addNewWrkLetterTo(String NewLetterToName) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_WRK_LETTER_TO(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, NewLetterToName);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("addNewWrkLetterTo" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteWrkLetterTo(int LetterToId) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_DEL_WRK_LETTER_to(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, LetterToId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("deleteWrkLetterTo" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addNewPurpose(String PurposeName) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_WRK_PURPOSE(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, PurposeName);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("addNewPurpose" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deletePurpose(int PurposeId) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_DEL_WRK_PURPOSE(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, PurposeId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("deletePurpose" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addNewApplicationType(String NewApplicationTypeName) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_ARC_APP_TYPE(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, NewApplicationTypeName);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("addNewApplicationType" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteApplicationType(int ApplicationTypeId) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_DEL_Arc_APP_TYPE(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, ApplicationTypeId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("deleteApplicationType" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<ReferralSettingClass> findAllReferralSetting() {
		List<ReferralSettingClass> ReferraloutList = new ArrayList<ReferralSettingClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_ALL_REFERRAL_SETTING(?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);

			while (rs.next()) {
				ReferralSettingClass umc = new ReferralSettingClass(rs.getInt("ID"), rs.getString("NAME"),
						rs.getString("TITLE"), rs.getInt("MGR_ID"), rs.getString("REAL_NAME"));
				ReferraloutList.add(umc);

			}
		} catch (Exception e) {
			logger.error("findAllReferralSetting" + e.getMessage());
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
		return ReferraloutList;

	}

	@Override
	public void deleteReferralById(int ReferralId) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_DEL_REFERRAL_SETTING_BY_ID(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, ReferralId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("deleteReferralById" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteReferralByManagerId(int ManagerId) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_DEL_REFERRAL_SETTING_BYMGR(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, ManagerId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("deleteReferralByManagerId" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addNewReferralSetting(int ManagerId) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_REFERRAL_SETTING(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, ManagerId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("addNewReferralSetting" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addExistedAttachToRecord(String AttachmentId, String ArcRecordId) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_arc_rec_attach_i(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(2, AttachmentId);
			callableStatement.setString(1, ArcRecordId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("addExistedAttachToRecord" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void addNewUserFolder(String FolderName, int UserId) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_NEW_USER_FOLDER(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, FolderName);
			callableStatement.setInt(2, UserId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("addNewUserFolder" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String MoveRecordToFolder(String WrkId, int StepId, int UserId, int FolderId) {
		String txt = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_MOVE_RECORD_TO_FOLDER(?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, WrkId);
			callableStatement.setInt(2, StepId);
			callableStatement.setInt(3, UserId);
			callableStatement.setInt(4, FolderId);
			callableStatement.registerOutParameter(5, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String msg = (String) callableStatement.getObject(5);
			txt = msg;
		} catch (Exception e) {
			logger.error("MoveRecordToFolder" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return txt;
	}

	@Override
	public String findSystemProperty(String propertyName) {
		String x = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ ?=call GET_SYS_PROP_VAL(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.setString(2, propertyName.toUpperCase());
			callableStatement.executeUpdate();
			x = (String) callableStatement.getObject(1);
		} catch (Exception e) {
			logger.error("findSystemProperty" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return x;
	}

	@Override
	public List<ArcRecordClass> SearchRecordsByCommentdata(String SrchKey) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<ArcRecordClass> ARCS = new ArrayList<ArcRecordClass>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.get_arc_record_by_COMM_DETAIL(?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, SrchKey);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				ArcRecordClass Arc = new ArcRecordClass();
				Arc.setRecTitle(rs.getString("REC_TITLE"));
				Arc.setLetterTo(rs.getString("Letter_to_name"));
				Arc.setRecHDate(rs.getString("REC_H_DATE"));
				Arc.setIncomeYear(rs.getString("INCOME_YEAR"));
				Arc.setLetterFrom(rs.getString("Letter_from_name"));
				Arc.setIncomeHDate(rs.getString("REC_H_DATE"));
				Arc.setIncomeNo(rs.getString("INCOME_NO"));
				Arc.setCreatedBy(rs.getString("creator"));
				Arc.setArcId(rs.getInt("ID"));
				Arc.setArcRecId(rs.getInt("rec_id"));
				Arc.setArcRecType(rs.getString("Application_type"));
				Arc.setLastEmpName(rs.getString("LAST_EMP_NAME"));
				ARCS.add(Arc);

			}
		} catch (Exception e) {
			logger.error("SearchRecordsByCommentdata" + e.getMessage());
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
		return ARCS;
	}

	@Override
	public List<ArcRecordClass> SearchRecordsByLetterTo(int SrchKey) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<ArcRecordClass> ARCS = new ArrayList<ArcRecordClass>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.get_arc_record_by_LETTER_TO(?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, SrchKey);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				ArcRecordClass Arc = new ArcRecordClass();
				Arc.setRecTitle(rs.getString("REC_TITLE"));
				Arc.setLetterTo(rs.getString("Letter_to_name"));
				Arc.setRecHDate(rs.getString("REC_H_DATE"));
				Arc.setIncomeYear(rs.getString("INCOME_YEAR"));
				Arc.setLetterFrom(rs.getString("Letter_from_name"));
				Arc.setIncomeHDate(rs.getString("REC_H_DATE"));
				Arc.setIncomeNo(rs.getString("INCOME_NO"));
				Arc.setCreatedBy(rs.getString("creator"));
				Arc.setArcId(rs.getInt("ID"));
				Arc.setArcRecId(rs.getInt("rec_id"));
				Arc.setArcRecType(rs.getString("Application_type"));
				Arc.setLastEmpName(rs.getString("LAST_EMP_NAME"));
				ARCS.add(Arc);

			}
		} catch (Exception e) {
			logger.error("SearchRecordsByLetterTo" + e.getMessage());
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
		return ARCS;
	}

	@Override
	public List<ArcRecordClass> SearchRecordsByLetterFrom(int SrchKey) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<ArcRecordClass> ARCS = new ArrayList<ArcRecordClass>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.get_arc_record_by_LETTER_FROM(?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, SrchKey);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				ArcRecordClass Arc = new ArcRecordClass();
				Arc.setRecTitle(rs.getString("REC_TITLE"));
				Arc.setLetterTo(rs.getString("Letter_to_name"));
				Arc.setRecHDate(rs.getString("REC_H_DATE"));
				Arc.setIncomeYear(rs.getString("INCOME_YEAR"));
				Arc.setLetterFrom(rs.getString("Letter_from_name"));
				Arc.setIncomeHDate(rs.getString("REC_H_DATE"));
				Arc.setIncomeNo(rs.getString("INCOME_NO"));
				Arc.setCreatedBy(rs.getString("creator"));
				Arc.setArcId(rs.getInt("ID"));
				Arc.setArcRecId(rs.getInt("rec_id"));
				Arc.setArcRecType(rs.getString("Application_type"));
				Arc.setLastEmpName(rs.getString("LAST_EMP_NAME"));
				Arc.setLastEmpNumber(rs.getInt("LAST_EMP"));
				ARCS.add(Arc);

			}
		} catch (Exception e) {
			logger.error("SearchRecordsByLetterFrom" + e.getMessage());
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
		return ARCS;
	}

	@Override
	public LastRecordActionClass findLastWrkApplicationDetails(String ArcId) {
		Connection connection = DataBaseConnectionClass.getConnection();
		LastRecordActionClass lra = new LastRecordActionClass();
		CallableStatement callableStatement = null;
		try {

			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_LAST_ACTION_DATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, ArcId);
			callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(4, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(5, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(6, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(7, OracleTypes.NUMBER);
			callableStatement.registerOutParameter(8, OracleTypes.NUMBER);
			callableStatement.registerOutParameter(9, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(10, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(11, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(12, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(13, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(14, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			lra.setLastEmployeeName((String) callableStatement.getObject(2));
			lra.setLastFromEmployeeName((String) callableStatement.getObject(3));
			lra.setLastWrkComm((String) callableStatement.getObject(4));
			lra.setLastWrkDate((String) callableStatement.getObject(6));
			lra.setLastWrkdurationindays((callableStatement.getObject(7) == null) ? 0
					: ((BigDecimal) callableStatement.getObject(7)).intValue());
			lra.setStepsNumber((callableStatement.getObject(8) == null) ? 0
					: ((BigDecimal) callableStatement.getObject(8)).intValue());
			lra.setLastdirection((String) callableStatement.getObject(5));
			lra.setHasComment((String) callableStatement.getObject(9));
			lra.setCommentText((String) callableStatement.getObject(10));
			lra.setWroteByName("");
			lra.setCommentWroteByName((String) callableStatement.getObject(11));
			lra.setCommentMarkedByName((String) callableStatement.getObject(12));
			lra.setWrkApplicationId((String) callableStatement.getObject(14));
			lra.setCommentSignedByName((String) callableStatement.getObject(13));

		} catch (Exception e) {
			logger.error("findLastWrkApplicationDetails" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lra;
	}

	@Override
	public int findArcRecordParam(String ArcId) {
		int ret = 0;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.GET_REPORT_PARAMETER(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, ArcId);
			callableStatement.registerOutParameter(2, OracleTypes.NUMBER);
			callableStatement.executeUpdate();
			ret = ((BigDecimal) callableStatement.getObject(2)).intValue();
		} catch (Exception e) {
			logger.error("findArcRecordParam" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public void SendRecordFromArchieve(String RecordId, int EmployeeId, String SendingType) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_SEND_RECORD_FROM_SRCH(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, SendingType);
			callableStatement.setInt(2, EmployeeId);
			callableStatement.setString(3, RecordId);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("SendRecordFromArchieve" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String deleteUserFolder(int FolderId) {
		String returnMsg = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_DELETE_INBOX_FOLDER(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, FolderId);
			callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String x = (String) callableStatement.getObject(2);
			returnMsg = x;
		} catch (Exception e) {
			logger.error("deleteUserFolder" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return returnMsg;
	}

	@Override
	public String updateUserPassword(int UserId, String NewPassword) {
		String returnMsg = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_UPDATE_USER_PASSWORD(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, NewPassword);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String x = (String) callableStatement.getObject(3);
			returnMsg = x;
		} catch (Exception e) {
			logger.error("updateUserPassword" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnMsg;
	}

	@Override
	public String updateUserSignPassword(int UserId, String NewPassword) {
		String returnMsg = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_UPDATE_USER_SIGN_PASSWORD(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, NewPassword);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String x = (String) callableStatement.getObject(3);
			returnMsg = x;
		} catch (Exception e) {
			logger.error("updateUserSignPassword" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnMsg;
	}

	@Override
	public String convertDateToArabic(String hijriDate) {
		String ret = hijriDate;
		if (hijriDate.length() != 10) {
			ret = Utils.convertTextWithArNumric(hijriDate) + "*";
		} else {
			String dd = hijriDate.substring(0, 2);
			String mm = hijriDate.substring(3, 5);
			String yy = hijriDate.substring(6, 10);
			ret = Utils.convertTextWithArNumric(yy + "/" + mm + "/" + dd);
		}

		return ret;
	}

	@Override
	public List<SpecialAddressClass> findSpeicalAddressByUser(int pUserId) {
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		List<SpecialAddressClass> outList = new ArrayList<SpecialAddressClass>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_favourit_contact(?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, pUserId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				SpecialAddressClass spc = new SpecialAddressClass();
				spc.setUserId(rs.getInt("USER_ID"));
				spc.setSpecialAddressId(rs.getInt("IN_LIST"));
				spc.setSpecialAddressName(rs.getString("FAV_NAME"));
				outList.add(spc);

			}
		} catch (Exception e) {
			logger.error("findSpeicalAddressByUser" + e.getMessage());
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
		return outList;
	}

	@Override
	public String addNewSpecialAddress(int pUserId, int pSpecialAddressId) {
		String ret = "";
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_add_new_special_addrs(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, pUserId);
			callableStatement.setInt(2, pSpecialAddressId);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String rett = (String) callableStatement.getObject(3);
			ret = rett;
		} catch (Exception e) {
			logger.error("addNewSpecialAddress" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;

	}

	@Override
	public String deleteSpecialAddress(int pUserId, int pSpecialAddressId) {
		String ret = "";
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_delete_new_special_addrs(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, pUserId);
			callableStatement.setInt(2, pSpecialAddressId);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String rett = (String) callableStatement.getObject(3);
			ret = rett;
		} catch (Exception e) {
			logger.error("deleteSpecialAddress" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public List<ReferralSettingClass> findReferralSettings() {
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		List<ReferralSettingClass> outList = new ArrayList<ReferralSettingClass>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_REFERRALS(?)}";

			callableStatement = connection.prepareCall(sql);

			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);

			while (rs.next()) {
				ReferralSettingClass rfc = new ReferralSettingClass();
				rfc.setReerralId(rs.getInt("ID"));
				rfc.setReferralManagerId(rs.getInt("MGR_ID"));
				rfc.setReferralName(rs.getString("NAME"));
				rfc.setReferralRealName(rs.getString("REAL_NAME"));
				rfc.setReferralTitle(rs.getString("TITLE"));
				outList.add(rfc);

			}
		} catch (Exception e) {
			logger.error("findReferralSettings" + e.getMessage());
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
		return outList;

	}

	@Override
	public String addNewReferralSetting(int pManagerId, String ManagerTitle) {
		String ret = "";
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_REFERRAL_setting(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, pManagerId);
			callableStatement.setString(2, ManagerTitle);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String rett = (String) callableStatement.getObject(3);
			ret = rett;
		} catch (Exception e) {
			logger.error("addNewReferralSetting" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;

	}

	@Override
	public String deleteReferralSetting(int pId) {
		String ret = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_DELETE_REFERRAL_setting(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, pId);
			callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String rett = (String) callableStatement.getObject(2);
			ret = rett;
		} catch (Exception e) {
			logger.error("deleteReferralSetting" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public String addNewRecipent(int pId, String pRecipenetTitle) {
		String ret = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_recipents(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, pId);
			callableStatement.setString(2, pRecipenetTitle);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String rett = (String) callableStatement.getObject(3);
			ret = rett;

		} catch (Exception e) {
			logger.error("addNewRecipent" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;

	}

	@Override
	public String deleteRecipent(int pId) {
		String ret = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_DELETE_recipents(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, pId);
			callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String rett = (String) callableStatement.getObject(2);
			ret = rett;
		} catch (Exception e) {
			logger.error("deleteRecipent" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public List<WrkChargingClass> findAllChargingByStatus(int Status) {
		Connection connection = DataBaseConnectionClass.getConnection();
		List<WrkChargingClass> outList = new ArrayList<WrkChargingClass>();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_CHARGING(?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, Status);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				WrkChargingClass wcc = new WrkChargingClass();
				wcc.setWhoIsCharged(rs.getInt("WHO_IS_CHARGED"));
				wcc.setWhoIsChargedName(rs.getString("WHO_IS_CHARGED_name"));
				wcc.setWhoIsNotHere(rs.getInt("WHO_IS_NOT_HERE"));
				wcc.setWhoIsNotHereName(rs.getString("WHO_IS_NOT_HERE_NAME"));
				wcc.setUserNameBefore(rs.getString("USER_NAME_BEFORE"));
				wcc.setUserNameAfter(rs.getString("USER_NAME_AFTER"));
				wcc.setStartDate(rs.getString("START_TAKLEEF"));
				wcc.setEndDate(rs.getString("END_TAKLEEF"));
				wcc.setChargingflag(rs.getInt("FLAG"));
				wcc.setPrivilageBefore(rs.getInt("PRIV_BEFORE"));
				wcc.setPrivilageBeforeName(rs.getString("PRIV_BEFORE_NAME"));
				wcc.setPrivilageAfter(rs.getInt("PRIV_AFTER"));
				wcc.setPrivilageAfterName(rs.getString("PRIV_AFTER_NAME"));
				wcc.setManagerIdBefore(rs.getInt("MGR_BEFORE"));
				wcc.setManagerNameBefore(rs.getString("MGR_BEFORE_name"));
				wcc.setManagerIdAfter(rs.getInt("MGR_AFTER"));
				wcc.setManagerNameAfter(rs.getString("MGR_AFTER_name"));

				outList.add(wcc);

			}
		} catch (Exception e) {
			logger.error("findAllChargingByStatus" + e.getMessage());
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
		return outList;

	}

	@Override
	public String addNewCharging(int WhoIsCharged, int WhoIsNotHere, String UserNameBefore, String UserNameAfter,
			String StartDate, String EndDate, int Flag, int privilageBefore, int privilageAfter, int ManagerBefore,
			int ManagerAfter) {
		String returnMsg = "";
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_CHARGING(?,?,?,?,?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, WhoIsCharged);
			callableStatement.setInt(2, WhoIsNotHere);
			callableStatement.setString(3, UserNameBefore);
			callableStatement.setString(4, UserNameAfter);
			callableStatement.setString(5, StartDate);
			callableStatement.setString(6, EndDate);
			callableStatement.setInt(7, Flag);
			callableStatement.setInt(8, privilageBefore);
			callableStatement.setInt(9, privilageAfter);
			callableStatement.setInt(10, ManagerBefore);
			callableStatement.setInt(11, ManagerAfter);
			callableStatement.registerOutParameter(12, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String x = (String) callableStatement.getObject(12);
			returnMsg = x;

		} catch (Exception e) {
			logger.error("addNewCharging" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return returnMsg;

	}

	@Override
	public String updateUserSign(int pUserId, InputStream pSign) {
		CallableStatement stmt = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String destination = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		String ret = "";
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
			String name = getAttachmentRandomName();
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(destination + name));
			int read = 0;
			System.err.println("destination : " + destination);
			byte[] bytes = new byte[1024];
			while ((read = pSign.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			InputStream fis = null;
			try {
				String sql = " call New_PKG_WEBKIT.PRC_UPDATE_USER_SIGN(?,?,?)";
				stmt = connection.prepareCall(sql);
				File sign = new File(destination + name);
				fis = new FileInputStream(sign);
				int ilen = (int) sign.length();
				stmt.setInt(1, pUserId);
				stmt.setBinaryStream(2, fis, ilen);
				stmt.registerOutParameter(3, OracleTypes.VARCHAR);
				stmt.executeUpdate();
				ret = (String) stmt.getObject(3);
			} catch (Exception e) {
				logger.error("updateUserSign" + e.getMessage());
			}
			pSign.close();
			out.flush();
			out.close();

		} catch (IOException e) {
			logger.error("updateUserSign" + e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public String updateUserMark(int pUserId, InputStream pMark) {
		CallableStatement stmt = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String destination = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		String ret = "";
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
			String name = getAttachmentRandomName();
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(destination + name));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = pMark.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			InputStream fis = null;
			try {
				String sql = " call New_PKG_WEBKIT.PRC_UPDATE_USER_mark(?,?,?)";
				stmt = connection.prepareCall(sql);
				File sign = new File(destination + name);
				fis = new FileInputStream(sign);
				int ilen = (int) sign.length();
				stmt.setInt(1, pUserId);
				stmt.setBinaryStream(2, fis, ilen);
				stmt.registerOutParameter(3, OracleTypes.VARCHAR);
				stmt.executeUpdate();
				ret = (String) stmt.getObject(3);
			} catch (Exception e) {
				logger.error("updateUserMark" + e.getMessage());
			}
			pMark.close();
			out.flush();
			out.close();

		} catch (IOException e) {
			logger.error("updateUserMark" + e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public String updateUserRefSign(int pUserId, InputStream pRefSign) {
		CallableStatement stmt = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String destination = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		String ret = "";
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
			String name = getAttachmentRandomName();
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(destination + name));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = pRefSign.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			InputStream fis = null;
			try {
				String sql = " call New_PKG_WEBKIT.PRC_UPDATE_USER_REF_SIGN(?,?,?)";
				stmt = connection.prepareCall(sql);
				File sign = new File(destination + name);
				fis = new FileInputStream(sign);
				int ilen = (int) sign.length();
				stmt.setInt(1, pUserId);
				stmt.setBinaryStream(2, fis, ilen);
				stmt.registerOutParameter(3, OracleTypes.VARCHAR);
				stmt.executeUpdate();
				ret = (String) stmt.getObject(3);
			} catch (Exception e) {
				logger.error("updateUserRefSign" + e.getMessage());
			}
			pRefSign.close();
			out.flush();
			out.close();

		} catch (IOException e) {
			logger.error("updateUserRefSign" + e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public Map<String, String> findUsersMap() {
		List<UserClass> list = findAllUsers();
		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		for (UserClass item : list) {
			returnMap.put(item.getVusers_real_name(), String.valueOf(item.getVuser_id()));
		}
		return returnMap;

	}

	@Override
	public Map<String, String> findUsersRolesMap() {
		List<WrkRolesClass> list = findAllRoles();
		Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
		for (WrkRolesClass item : list) {
			ReturnMap.put(item.getRoleName(), String.valueOf(item.getRoleId()));
		}
		return ReturnMap;

	}

	@Override
	public String addNewCharging(int WhoIsCharged, int WhoIsNotHere, String UserNameAfter, String StartDate,
			String EndDate, int privilageAfter) {
		CallableStatement callableStatement = null;
		String returnMsg = "";
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_CHARGING(?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, WhoIsCharged);
			callableStatement.setInt(2, WhoIsNotHere);
			callableStatement.setString(3, UserNameAfter);
			callableStatement.setString(4, StartDate);
			callableStatement.setString(5, EndDate);
			callableStatement.setInt(6, privilageAfter);
			callableStatement.registerOutParameter(7, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String x = (String) callableStatement.getObject(7);
			returnMsg = x;
		} catch (Exception e) {
			logger.error("addNewCharging" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return returnMsg;
	}

	@Override
	public String findEmployeeName(int EmployeeId) {
		String ret = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_EMPLOYEE_NAME(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, EmployeeId);
			callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String rett = (String) callableStatement.getObject(2);
			ret = rett;
		} catch (Exception e) {
			logger.error("findEmployeeName" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public String updateUserInfo(int UserId, int ManagerId, int RoleId, int DeptId, int SectionId, int JobId) {
		String ret = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_UPDATE_USER_WRK_INFO(?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setInt(2, ManagerId);
			callableStatement.setInt(3, RoleId);
			callableStatement.setInt(4, DeptId);
			callableStatement.setInt(5, SectionId);
			callableStatement.setInt(6, JobId);
			callableStatement.registerOutParameter(7, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String rett = (String) callableStatement.getObject(7);
			ret = rett;
		} catch (Exception e) {
			logger.error("updateUserInfo" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public String updateUserNameInfo(int UserId, String FirstName, String LastName, String NameAr, String NameEn) {
		String ret = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.update_user_NAME_INFO(?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, FirstName);
			callableStatement.setString(3, LastName);
			callableStatement.setString(4, NameAr);
			callableStatement.setString(5, NameEn);
			callableStatement.registerOutParameter(6, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String rett = (String) callableStatement.getObject(6);
			ret = rett;
		} catch (Exception e) {
			logger.error("updateUserNameInfo" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;

	}

	@Override
	public Map<String, String> findrecordsMap() {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<RecordMapClass> outList = new ArrayList<RecordMapClass>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_RECORD_MAP(?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				RecordMapClass wcc = new RecordMapClass();
				wcc.setRecordIncomeNo(rs.getString("income_no"));
				wcc.setRecordTitle(rs.getString("REC_TITLE"));
				outList.add(wcc);
			}
		} catch (Exception e) {
			logger.error("findrecordsMap" + e.getMessage());
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
		Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
		for (RecordMapClass item : outList) {
			ReturnMap.put("[" + item.getRecordIncomeNo() + "]" + item.getRecordTitle(),
					String.valueOf(item.getRecordIncomeNo()));
		}
		return ReturnMap;
	}

	@Override
	public ArcRecordClass getRecordbyIcomeNUmber(String IncomeNumber) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		ArcRecordClass arcRecord = new ArcRecordClass();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_RECORD_DET_BY_INCOM_NO(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, IncomeNumber);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				ArcRecordClass Arc = new ArcRecordClass();
				Arc.setRecTitle(rs.getString("REC_TITLE"));
				Arc.setLetterTo(rs.getString("Letter_to_name"));
				Arc.setRecHDate(rs.getString("REC_H_DATE"));
				Arc.setIncomeYear(rs.getString("INCOME_YEAR"));
				Arc.setLetterFrom(rs.getString("Letter_from_name"));
				Arc.setIncomeHDate(rs.getString("REC_H_DATE"));
				Arc.setIncomeNo(rs.getString("INCOME_NO"));
				Arc.setCreatedBy(rs.getString("creator"));
				Arc.setArcId(rs.getInt("ID"));
				Arc.setArcRecId(rs.getInt("rec_id"));
				Arc.setArcRecType(rs.getString("Application_type"));
				Arc.setLastEmpName(rs.getString("LAST_EMP_NAME"));
				Arc.setLastEmpNumber(rs.getInt("LAST_EMP"));
				arcRecord = Arc;
			}
		} catch (Exception e) {
			logger.error("getRecordbyIcomeNUmber" + e.getMessage());
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
		return arcRecord;
	}

	@Override
	public String addNewExportedRecord(String IncomeNumber, int UserId) {
		String ret = "";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_ADD_NEW_EXPORTED_COMMENT(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(2, UserId);
			callableStatement.setString(1, IncomeNumber);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String rett = (String) callableStatement.getObject(3);
			ret = rett;
		} catch (Exception e) {
			logger.error("addNewExportedRecord" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public List<Employer> loadAllEmployers() {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		List<Employer> employerList = new ArrayList<Employer>();
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.PRC_GET_HIRARCAL_EMPS(?) }";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				Employer employer = new Employer();
				employer.setId(Integer.parseInt(rs.getString("TO_CHAR(USER_ID)")));
				employer.setName("GET_NAME(USER_ID)");
				employer.setLevel(rs.getInt("LEVEL"));
				employer.setParentId(rs.getInt("MGR"));
				employerList.add(employer);
			}
		} catch (Exception e) {
			logger.error("loadAllEmployers" + e.getMessage());
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
		return employerList;
	}

	@Override
	public Map<String, String> loadDirections() {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Map<String, String> directionMap = new HashMap<String, String>();
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.get_all_arc_purpose(?) }";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				directionMap.put(rs.getString("p_name"), rs.getString("ID"));
			}
		} catch (Exception e) {
			logger.error("loadDirections" + e.getMessage());
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
		return directionMap;
	}

	@Override
	public String checkUmmAlQuraDate(String value) {
		String returnValue = "0";
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{? = call PKG_ALL_COM.FUN_check_hdate(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(2, value);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			returnValue = callableStatement.getString(1);
		} catch (SQLException e) {
			logger.error("checkUmmAlQuraDate" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	@Override
	public void storeAction(String Action, String Detail) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call new_pkg_webkit.PRC_ADD_ACTION(?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, Utils.findCurrentUser().getUserId());
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			callableStatement.setString(2,
					request.getRemoteAddr() + "**" + request.getRemoteHost() + "**" + request.getPathInfo());
			callableStatement.setString(3, Action);
			callableStatement.setString(4, Detail);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("storeAction" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public UserStatisticsClass findUserStatistics(int UserId, String StartDate, String EndDate) {
		UserStatisticsClass usc = new UserStatisticsClass();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call new_pkg_webkit.find_user_Statistics(?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.setString(2, StartDate);
			callableStatement.setString(3, EndDate);
			callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(4);

			while (rs.next()) {
				usc = new UserStatisticsClass();
				usc.setCreatedLettersCount(rs.getInt("Created_Letters_Count"));
				usc.setCreatedRecordsCount(rs.getInt("Created_Records_Count"));
				usc.setRecievedCommentsCount(rs.getInt("Recieved_Comments_Count"));
				usc.setSentCommentsCount(rs.getInt("Sent_Comments_Count"));
				usc.setUserId(UserId);
				usc.setUserName(findEmployeeName(UserId));

			}
		} catch (Exception e) {
			logger.error("findUserStatistics" + e.getMessage());
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
		return usc;
	}

	@Override
	public boolean isAuthonticatedtoChangePassword(String loginName, String systemPassword) {
		boolean ret = false;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{ ?=call authontacteUserPASSWORD(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(2, loginName.toUpperCase());
			callableStatement.setString(3, systemPassword);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String retChar = (String) callableStatement.getObject(1);
			if (retChar.equalsIgnoreCase("y")) {
				ret = true;
			}
		} catch (Exception e) {
			logger.error("isAuthonticatedtoChangePassword" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public boolean isAuthonticatedtoChangeSignPassword(String loginName, String signPassword) {
		boolean ret = false;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{ ?=call authontacteUserSignPASSWORD(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(2, loginName.toUpperCase());
			callableStatement.setString(3, signPassword);
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			String retChar = (String) callableStatement.getObject(1);
			if (retChar.equalsIgnoreCase("y")) {
				ret = true;
			}

		} catch (Exception e) {
			logger.error("isAuthonticatedtoChangeSignPassword" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public UserFolderClass findInboxRecordFolder(int UserId, String WrkId, int StepId) {
		UserFolderClass ret = new UserFolderClass();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.PRC_GET_INBOX_RECORD_FLDR(?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(2, StepId);
			callableStatement.setString(1, WrkId);
			callableStatement.setInt(3, UserId);
			callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(4);

			while (rs.next()) {
				UserFolderClass ufc = new UserFolderClass();
				ufc.setFolderId(rs.getInt("F_ID"));
				ufc.setFolderName(rs.getString("FNAME"));
				ret = ufc;
			}
			if (ret.getFolderId() == 0) {
				UserFolderClass ufc = new UserFolderClass();
				ufc.setFolderId(0);
				ufc.setFolderName("Ø§Ù„Ù…Ø¬Ù„Ø¯ Ø§Ù„Ø±Ø¦ÙŠØ³ÙŠ Ù„Ù„Ù…Ø¹Ø§Ù…Ù„Ø§Øª - ØºÙŠØ± Ù…ØµÙ†Ù�Ø©");
				ret = ufc;
			}
		} catch (Exception e) {
			logger.error("findInboxRecordFolder" + e.getMessage());
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
		return ret;

	}

	@Override
	public List<ActionClass> findUserAction(int UserId) {
		List<ActionClass> outList = new ArrayList<ActionClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call new_pkg_webkit.GET_ALL_USER_ACTION(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, UserId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				outList.add(new ActionClass(rs.getInt("ID"), rs.getString("DATE_TIMe"), rs.getInt("USER_ID"),
						rs.getString("PC_ID"), rs.getString("ACTION"), rs.getString("ACTION_DETAILS"),
						rs.getString("EMP_NAME"), rs.getString("ACTION_TIME")));
			}
		} catch (Exception e) {
			logger.error("findUserAction" + e.getMessage());
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
		return outList;
	}

	@Override

	public EmpVac infoVacEmp(int puserid) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		EmpVac empvacObj = null;
		try {
			String sql = "{call NEW_PKG_WEBHR.prc_get_inf_vac(?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, puserid);
			callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(4, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(5, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(6, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(7, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(8, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			empvacObj = new EmpVac();
			empvacObj.setNaame(callableStatement.getString(2));
			empvacObj.setPjob(callableStatement.getString(3));
			empvacObj.setPrank(callableStatement.getString(4));
			empvacObj.setPfappldat(callableStatement.getString(5));
			empvacObj.setPdept(callableStatement.getString(6));
			empvacObj.setPbascal(callableStatement.getInt(7));
			empvacObj.setEmpno(callableStatement.getString(8));

		} catch (SQLException e) {
			logger.error("infoVacEmp" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empvacObj;

	}

	public EmpVac loadVacEmp(int app_id) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		EmpVac empvacObj = null;
		try {
			String sql = "{call NEW_PKG_WEBHR.prc_get_datavacationn(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, app_id);
			callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(4, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(5, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(6, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(7, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(8, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			empvacObj = new EmpVac();
			empvacObj.setNaame(callableStatement.getString(2));
			empvacObj.setPjob(callableStatement.getString(3));
			empvacObj.setPrank(callableStatement.getString(4));
			empvacObj.setPfappldat(callableStatement.getString(5));
			empvacObj.setPdept(callableStatement.getString(6));
			empvacObj.setPbascal(callableStatement.getInt(7));
			empvacObj.setEmpno(callableStatement.getString(8));

		} catch (SQLException e) {
			logger.error("loadVacEmp" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empvacObj;

	}

	public EmpVac infVacEmp1(int vuser_id, int vacprd) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();

		EmpVac empvacobject1 = null;
		try {
			String sql = "{call NEW_PKG_WEBHR.prc_hrs_vac_116(?,?,?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, vuser_id);
			callableStatement.setInt(2, vacprd);
			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(4, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(5, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(6, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(7, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(8, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(9, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(10, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			empvacobject1 = new EmpVac();
			empvacobject1.setVACSUM360(callableStatement.getInt(6));
			empvacobject1.setLastVacDate(callableStatement.getString(7));
			empvacobject1.setLSTVACWRK(callableStatement.getString(8));
			empvacobject1.setLSTVACPRD(callableStatement.getString(9));
			empvacobject1.setVACARESULT(callableStatement.getInt(10));

		} catch (Exception e) {
			logger.error("infVacEmp1" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empvacobject1;
	}

	@Override
	public EmpVac infVacEmp2(int vuser_id, int vacsum360, int vacprd, int calc, int pbascal, int vacaresult) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		EmpVac empvacobj2 = null;
		try {
			String sql = "{call NEW_PKG_WEBHR.validate_period_vac116(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, vuser_id);
			callableStatement.setInt(2, vacsum360);
			callableStatement.setInt(3, vacprd);
			callableStatement.setInt(4, calc);
			callableStatement.setInt(5, pbascal);
			callableStatement.setInt(6, vacaresult);
			callableStatement.registerOutParameter(7, OracleTypes.NUMBER);
			callableStatement.registerOutParameter(8, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(9, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(10, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(11, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(12, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(13, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(14, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(15, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			empvacobj2 = new EmpVac();
			empvacobj2.setVCALC_new(callableStatement.getInt(7));
			empvacobj2.setPROCTYP(callableStatement.getInt(8));
			empvacobj2.setVACVAL(callableStatement.getInt(9));
			empvacobj2.setVACWRKFROM(callableStatement.getString(10));
			empvacobj2.setVACWRKto(callableStatement.getString(11));
			empvacobj2.setMesag(callableStatement.getString(12));
			empvacobj2.setMesag1(callableStatement.getString(13));
			empvacobj2.setMesag2(callableStatement.getString(14));
			empvacobj2.setVACAPRD_new(callableStatement.getString(15));

		} catch (Exception e) {
			logger.error("infVacEmp2" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empvacobj2;

	}

	@Override
	public EmpVac validate_dates_vac116(int vuser_id, String vACASTRT, int vacprd) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		EmpVac empVacobject3 = null;
		try {
			String sql = "{call NEW_PKG_WEBHR.validate_dates_vac116(?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);

			callableStatement.setInt(1, vuser_id);
			callableStatement.setString(2, vACASTRT);
			callableStatement.setInt(3, vacprd);

			callableStatement.registerOutParameter(4, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(5, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(6, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(7, OracleTypes.VARCHAR);

			callableStatement.executeUpdate();

			empVacobject3 = new EmpVac();

			empVacobject3.setVACAEND(callableStatement.getString(4));
			empVacobject3.setMesag(callableStatement.getString(5));
			empVacobject3.setMesag1(callableStatement.getString(6));
			empVacobject3.setMesag2(callableStatement.getString(7));

		} catch (Exception e) {
			logger.error("validate_dates_vac116" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empVacobject3;

	}

	@Override
	public boolean instrVac1166(int vuser_id, int vactype, int vacprd, String vacastrt, String vacaend, int proctyp,
			int salary, int vacval, String vacwrkfrom, String vacwrKto) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBHR.prc_insrt_vac116(?,?,?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, vuser_id);
			callableStatement.setInt(2, vactype);
			callableStatement.setInt(3, vacprd);
			callableStatement.setString(4, vacastrt);
			callableStatement.setString(5, vacaend);
			callableStatement.setInt(6, proctyp);
			callableStatement.setInt(7, salary);
			callableStatement.setInt(8, vacval);
			callableStatement.setString(9, vacwrkfrom);
			callableStatement.setString(10, vacwrKto);
			callableStatement.executeUpdate();
			return true;

		} catch (Exception e) {
			logger.error("instrVac1166" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	@Override
	public EmpVac CALC_VAC122_emp122(int vuser_id) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		EmpVac empVacobject5 = null;
		try {
			String sql = "{call NEW_PKG_WEBHR.CALC_VAC122_emp(?,?,?)}";
			callableStatement = connection.prepareCall(sql);

			callableStatement.setInt(1, vuser_id);

			callableStatement.registerOutParameter(2, OracleTypes.NUMBER);
			callableStatement.registerOutParameter(3, OracleTypes.NUMBER);

			callableStatement.executeUpdate();

			empVacobject5 = new EmpVac();

			empVacobject5.setVac122(callableStatement.getInt(2));
			empVacobject5.setVacmx122(callableStatement.getInt(3));

		} catch (Exception e) {
			logger.error("CALC_VAC122_emp122" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empVacobject5;
	}

	@Override
	public EmpVac checkdate122(String vacastrt, int vacprd) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		EmpVac empVacobject6 = null;
		try {
			String sql = "{call NEW_PKG_WEBHR.validate_dates_vac122(?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);

			callableStatement.setString(1, vacastrt);
			callableStatement.setInt(2, vacprd);

			callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(4, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(5, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(6, OracleTypes.VARCHAR);

			callableStatement.executeUpdate();

			empVacobject6 = new EmpVac();

			empVacobject6.setVACAEND(callableStatement.getString(3));
			empVacobject6.setMesag(callableStatement.getString(4));
			empVacobject6.setMesag1(callableStatement.getString(5));
			empVacobject6.setMesag2(callableStatement.getString(6));

		} catch (Exception e) {
			logger.error("prc_accept_vac" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empVacobject6;

	}

	@Override
	public boolean prc_insrt_vacEtrari(int vuser_id, int vactype, int vacprd, String vacastrt, String vacaend) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		EmpVac empVacobject3 = null;
		try {

			String sql = "{call NEW_PKG_WEBHR.prc_insrt_vacEtrari(?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);

			callableStatement.setInt(1, vuser_id);
			callableStatement.setInt(2, vactype);
			callableStatement.setInt(3, vacprd);
			callableStatement.setString(4, vacastrt);
			callableStatement.setString(5, vacaend);
			callableStatement.executeUpdate();
			return true;
		} catch (Exception e) {
			logger.error("prc_accept_vac" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	@Override
	public EmpVac prc_accept_vac(int pWRKID, int vuser_id, String d_MGRtxt, String d_PRStxt) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		EmpVac empVac = null;
		try {
			String sql = "{call NEW_PKG_WEBHR.prc_accept_vac(?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, pWRKID);
			callableStatement.setInt(2, vuser_id);
			callableStatement.setString(3, d_MGRtxt);
			callableStatement.setString(4, d_PRStxt);
			callableStatement.registerOutParameter(5, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			empVac = new EmpVac();
			empVac.setMesag(callableStatement.getString(5));
		} catch (Exception e) {
			logger.error("prc_accept_vac" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empVac;

	}

	@Override
	public EmpVac prc_accept_vacEtrati(int pWRKID, int vuser_id, String d_MGRtxt, String d_PRStxt) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		EmpVac empVac = null;
		try {

			String sql = "{call NEW_PKG_WEBHR.prc_accp_vac_etrari(?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, pWRKID);
			callableStatement.setInt(2, vuser_id);
			callableStatement.setString(3, d_MGRtxt);
			callableStatement.setString(4, d_PRStxt);
			callableStatement.registerOutParameter(5, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			empVac = new EmpVac();
			empVac.setMesag(callableStatement.getString(5));
		} catch (Exception e) {
			logger.error("prc_accept_vacEtrati" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empVac;
	}

	@Override
	public TrdModelClass findTrdApplicationData(int trdApplicationId) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		TrdModelClass trdModelClass = new TrdModelClass();
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call PKG_LIC_ETC.PRC_GET_TRD_APP_DATA(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, trdApplicationId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				trdModelClass = new TrdModelClass();
				trdModelClass.setLicApplicationId(rs.getInt("ID"));
				trdModelClass.setLicNumber(rs.getString("LIC_NO"));
				trdModelClass.setLicStartDate(rs.getString("LIC_DT_BGN_H"));
				trdModelClass.setLicEndDate(rs.getString("LIC_DT_END_H"));
				trdModelClass.setLicExtractionType(rs.getString("PROC_TYPE"));
				trdModelClass.setLicOwnerId(rs.getString("APL_OWNER"));
				trdModelClass.setLicOwnerName(rs.getString("APL_NAME"));
				trdModelClass.setLicMainActivity(rs.getString("MAIN_ACTV"));
				trdModelClass.setLicSubActivities(rs.getString("ACTV"));
				trdModelClass.setLicTrdName(rs.getString("TRD_NAME"));
				trdModelClass.setLicBuildingOwner(rs.getString("AQR_OWNER_NAME"));
				trdModelClass.setLicTrdAddress(rs.getString("MHL_ADDRS"));
				trdModelClass.setLicTrdArea(rs.getInt("AREA"));
				trdModelClass.setLicTotalAdvArea(rs.getInt("ADV"));
				trdModelClass.setLicSpecialNumber(rs.getString("SPNO"));
				trdModelClass.setLicValidityYears(rs.getInt("LIC_YRS_NUM"));
				trdModelClass.setLicBillstatus(rs.getString("Bill_details"));
				trdModelClass.setLicApplicationCreatedBy(rs.getInt("WROTE_BY"));
				trdModelClass.setLicApplicationCreatedByName(rs.getString("WROTE_BY_Emp_name"));
				trdModelClass.setLicApplicationCreatedIn(rs.getString("WROTE_IN"));
				if (rs.getInt("MARKED_BY") > 0) {
					trdModelClass.setMarked(true);
				}
				trdModelClass.setLicApplicationMarkedBy(rs.getInt("MARKED_BY"));
				trdModelClass.setLicApplicationMarkedByName(rs.getString("MARKED_BYEmp_name"));
				trdModelClass.setLicApplicationMarkedIn(rs.getString("MARKED_IN"));
				if (rs.getInt("SIGNED_BY") > 0) {
					trdModelClass.setSigned(true);
				}
				trdModelClass.setLicApplicationSignedBy(rs.getInt("SIGNED_BY"));
				trdModelClass.setLicApplicationSignedByName(rs.getString("SigndByName"));
				trdModelClass.setLicApplicationSignedIn(rs.getString("signed_in"));
				trdModelClass.setLicTrdPic(rs.getBlob("KAROKI").getBinaryStream());
			}

		} catch (Exception e) {
			logger.error("findTrdApplicationData" + e.getMessage());
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
		return trdModelClass;
	}

	@Override
	public EmpVac loadEtrariVac(int app_id) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		EmpVac empvacObj = null;
		try {

			String sql = "{call NEW_PKG_WEBHR.prc_load_vac_normal (?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, app_id);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			empvacObj = new EmpVac();
			empvacObj.setNaame(callableStatement.getString(2));
			empvacObj.setPjob(callableStatement.getString(3));
			empvacObj.setPrank(callableStatement.getString(4));
			empvacObj.setPfappldat(callableStatement.getString(5));
			empvacObj.setPdept(callableStatement.getString(6));
			empvacObj.setPbascal(callableStatement.getInt(7));
			empvacObj.setEmpno(callableStatement.getString(8));

		} catch (SQLException e) {
			logger.error("loadEtrariVac" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empvacObj;
	}

	@Override
	public EmpVac loadNormalVac(int app_id) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		EmpVac empvacObj = null;
		try {
			String sql = "{call NEW_PKG_WEBHR.prc_load_vac_normal(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, app_id);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				empvacObj = new EmpVac();
				empvacObj.setNaame(rs.getString("namee"));
				empvacObj.setCalc116(rs.getInt("calc116"));
				empvacObj.setAllvacprd(rs.getInt("allvacprd"));
				empvacObj.setMxvac116(rs.getInt("mxvac116"));
				empvacObj.setVACSUM360(rs.getInt("VACSUM360"));
				empvacObj.setVACARESULT(rs.getInt("VACARESULT"));
				empvacObj.setVacprd(rs.getInt("vacaprd"));
				empvacObj.setVACVAL(rs.getInt("vacval"));
				empvacObj.setVCALC_new(rs.getInt("vcalc"));
				empvacObj.setVACWRKFROM(rs.getString("vacwrkfrom"));
				empvacObj.setVACWRKto(rs.getString("vacwrkto"));
				empvacObj.setVACAEND(rs.getString("vacaend"));
				empvacObj.setPfappldat(rs.getString("fbbdat"));
				empvacObj.setPjob(rs.getString("jobb"));
				empvacObj.setBasicSalary(rs.getInt("BSCSAL"));
				empvacObj.setEmployeeId(rs.getInt("empno"));
				empvacObj.setVacStartSDate(rs.getString("vacastrt"));
				empvacObj.setLastVacWrk(rs.getString("LSTVACWRK"));
				empvacObj.setLastVacPeriod(rs.getInt("LSTVACPRD"));
				empvacObj.setLastVacDate(rs.getString("LSTVACDT"));
				empvacObj.setPrank(rs.getString("rankk"));
				empvacObj.setPdept(rs.getString("dept"));
			}

		} catch (SQLException e) {
			logger.error("loadNormalVac" + e.getMessage());
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
		return empvacObj;
	}

	@Override
	public boolean signedIsAutorized(int userId, int privlegeId) throws SQLException {
		ResultSet rs = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		Statement callableStatement = connection.createStatement();
		try {

			String sql = "Select * from ARC_USER_PRIVS where user_id = " + userId + "and priv_id = " + privlegeId;

			rs = callableStatement.executeQuery(sql);

			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("signedIsAutorized" + e.getMessage());
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
		return false;

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<WrkCommentsClass> findCommentsByArcId(Integer arcId) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		List<WrkCommentsClass> comments = new ArrayList<WrkCommentsClass>();
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call New_PKG_WEBKIT.GET_ALL_COMMENTS_BY_ARCID(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, arcId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.setInt(3, Utils.findCurrentUser().getUserId());
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				WrkCommentsClass wcc = new WrkCommentsClass();
				wcc.setStepId(rs.getInt("STEP_ID"));
				wcc.setComment(rs.getString("COMM"));
				wcc.setPurpose(rs.getString("PURP"));
				wcc.setFromName(rs.getString("FR"));
				wcc.setToName(rs.getString("TT"));
				wcc.setCreatedIn(rs.getString("CREATE_TIME"));
				wcc.setCreateDate(rs.getString("CTIME"));
				comments.add(wcc);

			}
		} catch (Exception e) {
			logger.error("findCommentsByArcId" + e.getMessage());
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
		return comments;

	}

	@Override
	public List<ExchangeRequest> searchExchangeRequests(String beginDate, String finishDate, Integer strNo,
			Integer artType, Integer employerId) {
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		List<ExchangeRequest> exchangesList = new ArrayList<ExchangeRequest>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_exchReqs(?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callableStatement.setString(1, beginDate);
			callableStatement.setString(2, finishDate);
			callableStatement.setInt(3, strNo);
			callableStatement.setInt(5, artType);
			callableStatement.setInt(6, employerId);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(4);
			int i = 0;
			while (rs.next()) {
				ExchangeRequest exchange = new ExchangeRequest();
				exchange.setGeneralrequestNumber(rs.getInt("inv_g_r_no"));
				exchange.setSerialNumber(rs.getInt("SERIALNUMBER"));
				exchange.setGeneralrequestDate(rs.getString("inv_g_r_date"));
				exchange.setNotes(rs.getString("notes"));
				exchange.setEmpName(rs.getString("empname"));
				exchange.setStatus(rs.getString("status"));
				exchangesList.add(exchange);
				i++;
			}
			System.out.println(" count " + i);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("searchExchangeRequests" + e.getMessage());
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
		return exchangesList;
	}

	@Transactional
	@Override
	public List<StoreRequestModel> getTransactionsQty(int articleId, int strNo) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<StoreRequestModel> storeRequestModelLst = new ArrayList<StoreRequestModel>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_artQtys(?,?,?)}";
			callableStatement = connection.prepareCall(sql);

			callableStatement.setInt(1, articleId);
			callableStatement.setInt(3, strNo);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			storeRequestModelLst.clear();
			while (rs.next()) {
				StoreRequestModel store = new StoreRequestModel();
				store.setQtyAvailable(rs.getInt("available"));
				store.setQtyReserved(rs.getInt("reserved"));
				store.setQtyInput(rs.getInt("input"));
				store.setQtyOutput(rs.getInt("output"));
				store.setArticleId(rs.getInt("id"));
				store.setArticleName(rs.getString("name"));
				store.setArticleCode(rs.getString("code"));
				store.setArticleUnite(rs.getString("ITEMUNITNAME"));
				storeRequestModelLst.add(store);
			}
		} catch (Exception e) {
			logger.error("getTransactionsQty" + e.getMessage());
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
		// for (StoreRequestModel storeRequestModelList : storeRequestModelLst)
		// {
		// System.out.println("Ø§Ø³Ù… Ø§Ù„ØµÙ†Ù� " +
		// storeRequestModelList.getArticleName());
		// }

		return storeRequestModelLst;
	}

	@Override
	public List<InventoryModel> ListInventories(int strNo, Integer inventoryId, String inventoryDate) {
		Connection connection = DataBaseConnectionClass.getConnection();
		List<InventoryModel> inventoryList = new ArrayList<InventoryModel>();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_artsGarden(?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.setInt(2, strNo);
			callableStatement.setInt(3, inventoryId);
			callableStatement.setString(4, inventoryDate);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);

			while (rs.next()) {
				InventoryModel inv = new InventoryModel();
				inv.setInventoryRecordId(rs.getInt("GARDDETID"));
				inv.setInventoryMasterId(rs.getInt("GARDID"));
				inv.setArticleId(rs.getInt("id"));
				inv.setArticleName(rs.getString("name"));
				inv.setArticleCode(rs.getString("code"));
				inv.setQteActuel(rs.getInt("GARD_ACTUAL"));
				inv.setLastGardQty(rs.getInt("lstGqty"));
				inv.setStock(rs.getInt("stock"));
				inv.setArtUnitName(rs.getString("ITEMUNITNAME"));
				inventoryList.add(inv);
			}
		} catch (Exception e) {
			logger.error("ListInventories" + e.getMessage());
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
		return inventoryList;
	}

	@Override
	public List<StoreRequestModel> getArticleHistory(int articleId, Integer strNo) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<StoreRequestModel> storeRequestModelLst = new ArrayList<StoreRequestModel>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_artHistory(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.setInt(1, articleId);
			callableStatement.setInt(2, strNo);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);
			while (rs.next()) {
				StoreRequestModel store = new StoreRequestModel();
				store.setTransactionCode(rs.getInt("tr"));
				store.setTransactionName(rs.getString("trType"));
				store.setArticleId(rs.getInt("id"));
				store.setArticleName(rs.getString("name"));
				store.setArticleCode(rs.getString("code"));
				store.setSerialNumber(rs.getString("SPECIAL_NO"));
				store.setQtyOutput(rs.getInt("qty"));
				store.setArticleUnite(rs.getString("ITEMUNITNAME"));
				store.setTransactionDate(rs.getString("datetransaction"));
				store.setRequesterName(rs.getString("requester_name"));
				store.setSupplierName(rs.getString("supplier"));
				store.setStoreNo(rs.getInt("strno"));
				storeRequestModelLst.add(store);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getArticleHistory has " + e.getMessage());
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
		return storeRequestModelLst;
	}

	@Override
	public List<LicTrdMasterFile> getTrdMasterFileList() {
		ResultSet rs = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<LicTrdMasterFile> listTrdMasterFiles = new ArrayList<LicTrdMasterFile>();
		Statement callableStatement = null;
		try {
			callableStatement = connection.createStatement();
			String sql = "select lic_trd_master_file.ID,TRD_NAME, req_get_sub_actv(lic_trd_master_file.ID) as ACTIVITY,"
					+ "LIC_NO,LIC_DT_BGN_H,LIC_DT_END_H, MHL_ADDRS, AQR_OWNER_NAME       from lic_trd_master_file  "
					+ " where is_active_y_n = 'Y'";

			rs = callableStatement.executeQuery(sql);
			while (rs.next()) {
				LicTrdMasterFile licTrdMFile = new LicTrdMasterFile(rs.getInt("id"), rs.getString("LIC_NO"),
						rs.getString("TRD_NAME"));

				licTrdMFile.setActivity(rs.getString("ACTIVITY"));
				licTrdMFile.setLicAdress(rs.getString("MHL_ADDRS"));
				licTrdMFile.setLicOwnerName(rs.getString("AQR_OWNER_NAME"));

				licTrdMFile.setLicBeginDate(rs.getString("LIC_DT_BGN_H"));
				licTrdMFile.setLicEndDate(rs.getString("LIC_DT_END_H"));
				listTrdMasterFiles.add(licTrdMFile);
			}
			return listTrdMasterFiles;
		} catch (Exception e) {
			logger.error("getTrdMasterFileList" + e.getMessage());
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
		return listTrdMasterFiles;

	}

	@Override
	public ReqFinesMaster checkLicLst(String licNo) {
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		Statement callableStatement = null;
		ReqFinesMaster reqFinesMaster = null;
		try {
			callableStatement = connection.createStatement();
			String sql = "select (trd_name || ' - ' || req_get_sub_actv(lic_trd_master_file.ID))trd_name,MHL_ADDRS,LIC_NO,APL_OWNER,get_people_name(APL_OWNER)fName,LIC_DT_BGN_H,LIC_DT_END_H ,trd_get_SPNO(mhl_id)mhl_id,trd_get_PLANENAME(mhl_id)fMapNo,trd_get_pcno(mhl_id)pcNo"
					+ " from lic_trd_master_file where lic_no='" + licNo + "' and is_active_y_n = 'Y'";

			rs = callableStatement.executeQuery(sql);
			while (rs.next()) {
				reqFinesMaster = new ReqFinesMaster();
				reqFinesMaster.setfTradeMarkName(rs.getString("trd_name"));
				reqFinesMaster.setfIdNo(rs.getString("APL_OWNER"));
				reqFinesMaster.setfName(rs.getString("fName"));
				reqFinesMaster.setfAddress(rs.getString("MHL_ADDRS"));
				reqFinesMaster.setfBlockNo(rs.getString("pcNo"));
				reqFinesMaster.setfMapNo(rs.getString("fMapNo"));
				reqFinesMaster.setfLicEndDate(rs.getString("LIC_DT_END_H"));
				reqFinesMaster.setfLicStartDate(rs.getString("LIC_DT_BGN_H"));
				reqFinesMaster.setfLicenceNo(rs.getString("LIC_NO"));
				reqFinesMaster.setfKeycode(rs.getString("mhl_id"));
			}
			return reqFinesMaster;
		} catch (Exception e) {
			logger.error("checkLicLst" + e.getMessage());
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
		return reqFinesMaster;

	}

	@Override
	public List<LicTrdMasterFile> getTrdMasterFileListByPeriodType(Integer typeLicence, Integer period) {
		ResultSet rs = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<LicTrdMasterFile> listTrdMasterFiles = new ArrayList<LicTrdMasterFile>();
		String condition = "";
		Statement callableStatement = null;
		int days = (period == null) ? 90 : period;
		try {
			switch (typeLicence) {
			case 1:
				condition = "AND SUBSTR (LIC_DT_BGN_H, 7, 4) || SUBSTR (LIC_DT_BGN_H, 4, 2) || SUBSTR (LIC_DT_BGN_H, 1, 2) > SUBSTR ('"
						+ HijriCalendarUtil.addDaysToHijriDate(HijriCalendarUtil.findCurrentHijriDate(), -days)
						+ "', 7, 4) || SUBSTR ('"
						+ HijriCalendarUtil.addDaysToHijriDate(HijriCalendarUtil.findCurrentHijriDate(), -days)
						+ "', 4, 2) || SUBSTR ('"
						+ HijriCalendarUtil.addDaysToHijriDate(HijriCalendarUtil.findCurrentHijriDate(), -days)
						+ "', 1, 2)";
				break;
			case 2:
				condition = "AND SUBSTR (LIC_DT_END_H, 7, 4) || SUBSTR (LIC_DT_END_H, 4, 2) || SUBSTR (LIC_DT_END_H, 1, 2) < SUBSTR ('"
						+ HijriCalendarUtil.findCurrentHijriDate() + "', 7, 4) || SUBSTR ('"
						+ HijriCalendarUtil.findCurrentHijriDate() + "', 4, 2) || SUBSTR ('"
						+ HijriCalendarUtil.findCurrentHijriDate() + "', 1, 2)";
				break;
			case 3:
				condition = "AND SUBSTR (LIC_DT_END_H, 7, 4) || SUBSTR (LIC_DT_END_H, 4, 2) || SUBSTR (LIC_DT_END_H, 1, 2) < SUBSTR ('"
						+ HijriCalendarUtil.addDaysToHijriDate(HijriCalendarUtil.findCurrentHijriDate(), days)
						+ "', 7, 4) || SUBSTR ('"
						+ HijriCalendarUtil.addDaysToHijriDate(HijriCalendarUtil.findCurrentHijriDate(), days)
						+ "', 4, 2) || SUBSTR ('"
						+ HijriCalendarUtil.addDaysToHijriDate(HijriCalendarUtil.findCurrentHijriDate(), days)
						+ "', 1, 2)";// +Utils.reverseDateToNumber(HijriCalendarUtil.findCurrentHijriDate());
				break;
			default:
				break;
			}
			callableStatement = connection.createStatement();
			String sql = "select lic_trd_master_file.ID,TRD_NAME, req_get_sub_actv(lic_trd_master_file.ID) as ACTIVITY,"
					+ "LIC_NO,LIC_DT_BGN_H,LIC_DT_END_H, MHL_ADDRS, AQR_OWNER_NAME from lic_trd_master_file  "
					+ " where is_active_y_n = 'Y' " + condition;

			rs = callableStatement.executeQuery(sql);
			while (rs.next()) {
				LicTrdMasterFile licTrdMFile = new LicTrdMasterFile(rs.getInt("id"), rs.getString("LIC_NO"),
						rs.getString("TRD_NAME"));

				licTrdMFile.setActivity(rs.getString("ACTIVITY"));
				licTrdMFile.setLicAdress(rs.getString("MHL_ADDRS"));
				licTrdMFile.setLicOwnerName(rs.getString("AQR_OWNER_NAME"));

				licTrdMFile.setLicBeginDate(rs.getString("LIC_DT_BGN_H"));
				licTrdMFile.setLicEndDate(rs.getString("LIC_DT_END_H"));
				listTrdMasterFiles.add(licTrdMFile);
			}
			return listTrdMasterFiles;
		} catch (Exception e) {
			logger.error("getTrdMasterFileListByPeriodType" + e.getMessage());
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
		return listTrdMasterFiles;
	}

	@Override
	public Integer billstatus(Integer newBill) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		Integer x = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.BILL_IS_PAID(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(2, OracleTypes.INTEGER);
			callableStatement.setInt(1, newBill);
			callableStatement.executeUpdate();
			x = callableStatement.getInt(2);
		} catch (Exception e) {
			logger.error("loadLicNewList" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return x;
	}

	@Override
	public List<BldLicNewModel> loadLicNewList(Long nationalId) {
		ResultSet rs = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<BldLicNewModel> licList = new ArrayList<BldLicNewModel>();
		Statement callableStatement = null;
		try {
			callableStatement = connection.createStatement();
			String sql = "select c.lic_New_Apl_Owner_Id,c.lic_New_Number,c.lic_New_Bill_Number, b.bill_Status as billStatus "
					+ "from Bld_Lic_New c,Pay_Lic_Bills b  where c.lic_New_Bill_Number = b.bill_no (+) and c.lic_New_Apl_Owner_Id="
					+ nationalId + " order by c.lic_New_Id DESC";

			rs = callableStatement.executeQuery(sql);
			while (rs.next()) {
				BldLicNewModel bldLicNewModel = new BldLicNewModel(rs.getString("lic_New_Apl_Owner_Id"),
						rs.getString("lic_New_Number"), rs.getString("lic_New_Bill_Number"), rs.getInt("billStatus"));
				licList.add(bldLicNewModel);
			}
			return licList;
		} catch (Exception e) {
			logger.error("loadLicNewList" + e.getMessage());
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
		return licList;

	}

	@Override
	public List<HrsSalaryScale> loadAllJobRanks() {
		ResultSet rs = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<HrsSalaryScale> scalesList = new ArrayList<HrsSalaryScale>();
		Statement callableStatement = null;
		try {
			callableStatement = connection.createStatement();
			String sql = "select rankcode,ranktitle from (select rankcode,ranktitle from HRS_SALARY_SCALE where orderid = (select max(orderid) from HRS_SALARY_SCALE) ) ORDER BY 1";

			rs = callableStatement.executeQuery(sql);
			while (rs.next()) {
				HrsSalaryScale scale = new HrsSalaryScale();
				scale.setId(new HrsSalaryScaleId());
				scale.getId().setRankCode(rs.getInt("rankcode"));
				scale.setRankName(rs.getString("ranktitle"));
				scalesList.add(scale);
			}
			return scalesList;
		} catch (Exception e) {
			logger.error("loadAllJobRanks" + e.getMessage());
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
		return scalesList;
	}

	@Override
	public List<HrsEmpHistorical> calculatePrimes() {
		ResultSet rs = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<HrsEmpHistorical> primesList = new ArrayList<HrsEmpHistorical>();
		Statement callableStatement = null;
		try {
			callableStatement = connection.createStatement();
			String sql = "select empno,trans,MANDIN,MANDOUT,natno,fun_get_inf001_ar(empno) as name,RANKCOD,CLSSCOD,fun_get_sys002(natcod) nat,natcod,fun_get_stp006(rankcod) rank, "
					+ "fun_get_stp007(clsscod,rankcod)clss, bscsal,catcod,empsts,fun_get_newClss(clsscod,rankcod) newclss, fun_get_Clssbscsal(fun_get_newClss(clsscod,rankcod) ,"
					+ "rankcod) newSal from HRSV$INF001 where empsts=1 and catcod in (1,2,3,4) and natcod=1 and fun_get_newClss(clsscod,rankcod) <> clsscod";

			rs = callableStatement.executeQuery(sql);
			while (rs.next()) {
				HrsEmpHistorical prime = new HrsEmpHistorical();
				prime.setId(new HrsEmpHistoricalId(rs.getInt("empno"), null));
				prime.setTransferSalary(rs.getInt("trans"));
				prime.setCATegoryId(rs.getInt("catcod"));
				prime.setNationalId(rs.getString("natno"));
				prime.setEmpName(rs.getString("name"));
				prime.setNationality(rs.getString("nat"));
				prime.setRankNumber(rs.getInt("RANKCOD"));
				prime.setRankName(rs.getString("rank"));
				prime.setOldClassnumber(rs.getInt("CLSSCOD"));
				prime.setOldBasicSalary(rs.getInt("bscsal"));
				prime.setClassNumber(rs.getInt("newclss"));
				prime.setBasicSalary(rs.getInt("newSal"));
				prime.setMandateInner(rs.getInt("MANDIN"));
				prime.setMandateOuter(rs.getInt("MANDOUT"));
				primesList.add(prime);
			}
			return primesList;
		} catch (Exception e) {
			logger.error("calculatePrimes" + e.getMessage());
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
		return primesList;
	}

	@Override
	public void calcSalary(int catid, int pmonth, int pyear) {
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_calc_salary(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, catid);
			callableStatement.setInt(2, pmonth);
			callableStatement.setInt(3, pyear);
			callableStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("getHrsCompactFloors" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<HrsCompactFloors> getHrsCompactFloors(Integer hrsPerfomId, Integer jobType) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<HrsCompactFloors> hrsCompactFloors = new ArrayList<HrsCompactFloors>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.getHrsCompactFloors(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.setInt(1, hrsPerfomId);
			callableStatement.setInt(2, jobType);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);

			while (rs.next()) {
				HrsCompactFloors floor = new HrsCompactFloors();
				floor.setId(rs.getInt("ID"));
				floor.setHrsCompactPerformanceid(rs.getInt("hrs_compact_performance_id"));
				floor.setHrsCompactBasefloorid(rs.getInt("HRS_COMPACT_BASEFLOORID"));
				floor.setFloorsid(rs.getInt("floorId"));
				floor.setFloorDescription(rs.getString("fDesc"));
				floor.setCatFloorId(rs.getInt("CATERIE_FLOURID"));
				floor.setCatFloorDesc(rs.getString("cat_name"));
				floor.setEvaluation(rs.getInt("EVALUATION"));
				if ((floor.getEvaluation() == 0))
					floor.setEvaluation(4);
				floor.setRelativeImportance(rs.getInt("RELATIVE_IMPORTANCE"));
				hrsCompactFloors.add(floor);
			}
		} catch (Exception e) {
			logger.error("getHrsCompactFloors" + e.getMessage());
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
		return hrsCompactFloors;
	}

	@Override
	public List<ArcUsers> employeersTree() {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		List<ArcUsers> x = new ArrayList<ArcUsers>();
		Connection connection = DataBaseConnectionClass.getConnection();
		String sql = "{ call New_PKG_WEBKIT.prc_employers_tree(?)}";
		try {
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);
			while (rs.next()) {
				ArcUsers arcuser = new ArcUsers();
				arcuser.setEmployeeName(rs.getString("nameuser"));
				arcuser.setUserId(Integer.parseInt(rs.getString("usersid")));
				x.add(arcuser);
			}
		} catch (Exception e) {
			logger.error("employeersTree" + e.getMessage());
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
		return x;
	}

	@Override
	public void loadLetterReportParameters(Map<String, Object> parameters, String wrkId) {
		ResultSet rs = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "SELECT GET_SEC_NAME(WRK_COMMENT.MARKED_BY) sec_name, WRK_COMMENT_TYPE.NAME tname, "
					+ "WRK_COMMENT.WROTE_BY||' - '||GET_SEC_NAME(WRK_COMMENT.WROTE_BY) wname, wps.ddd NSIGN ,GET_NAME(wps.USER_ID) POSE, "
					+ "GET_REAL_EMPNAME(wps.USER_ID) REAL,PR.SIGN3,"
					+ "(SELECT PR.SIGN2 FROM WRK_PROFILE PR,WRK_COMMENT C WHERE PR.USER_ID = C.MARKED_BY AND C.APP_ID= "
					+ wrkId + ") sign2 , "
					+ "(SELECT PR.SIGN2 PRSIGN FROM WRK_PROFILE PR WHERE PR.USER_ID = GET_SYS_PROP_VAL('PRESIDENT_ID'))  PRSIGN ,"
					+ "GET_REAL_EMPNAME(GET_SYS_PROP_VAL('PRESIDENT_ID')) PRNAME "
					+ "FROM WRK_COMMENT, WRK_COMMENT_TYPE ,WRK_PROFILE PR ,WRK_PROFILE_SIGN WPS "
					+ "WHERE WRK_COMMENT.APP_ID = " + wrkId + " AND (WRK_COMMENT.COMMENT_TYPE = WRK_COMMENT_TYPE.ID) "
					+ "and (WPS.USER_ID = nvl(WRK_COMMENT.SIGNED_BY,1)) and  PR.user_id  = WPS.user_id";
			callableStatement = connection.prepareCall(sql);
			rs = callableStatement.executeQuery(sql);
			while (rs.next()) {
				parameters.put("pose", rs.getString("POSE"));
				parameters.put("prName", rs.getString("PRNAME"));
				parameters.put("real", rs.getString("REAL"));
				parameters.put("secName", rs.getString("sec_name"));
				parameters.put("wName", rs.getString("wname"));
				parameters.put("nSign",
						(rs.getBytes("NSIGN") == null) ? null : new ByteArrayInputStream(rs.getBytes("NSIGN")));
				parameters.put("sign3",
						(rs.getBytes("SIGN3") == null) ? null : new ByteArrayInputStream(rs.getBytes("SIGN3")));
				parameters.put("prSign",
						(rs.getBytes("PRSIGN") == null) ? null : new ByteArrayInputStream(rs.getBytes("PRSIGN")));
				parameters.put("sign2",
						(rs.getBytes("sign2") == null) ? null : new ByteArrayInputStream(rs.getBytes("sign2")));
				parameters.put("tName", rs.getString("tname"));
			}

		} catch (Exception e) {
			logger.error("loadLetterReportParameters" + e.getMessage());
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
	}

	@Override
	public DashbordModel loadDasbordParams(Integer userId) {
		ResultSet rs = null;
		// Connection connection = DataBaseConnectionClass.getConnection();
		DashbordModel dashbordModel = new DashbordModel();
		// CallableStatement callableStatement = null;
		// String sql = "{ call
		// New_PKG_WEBKIT.get_inbox_out_archive_unread(?,?,?,?,?,?,?)}";
		// try {
		// callableStatement = connection.prepareCall(sql);
		// callableStatement.setInt(1, userId);
		// callableStatement.registerOutParameter(2, OracleTypes.NUMBER);
		// callableStatement.registerOutParameter(3, OracleTypes.NUMBER);
		// callableStatement.registerOutParameter(4, OracleTypes.NUMBER);
		// callableStatement.registerOutParameter(5, OracleTypes.NUMBER);
		// callableStatement.registerOutParameter(6, OracleTypes.NUMBER);
		// callableStatement.registerOutParameter(7, OracleTypes.NUMBER);
		// callableStatement.execute();
		// // if(StringUtils.isEmpty(callableStatement.getInt(2)) ||
		// // StringUtils.isEmpty(callableStatement.getInt(5)) ||
		// // StringUtils.isEmpty(callableStatement.getInt(4))
		// // ||StringUtils.isEmpty(callableStatement.getInt(3)))
		// // return dashbordModel;
		// dashbordModel.setInboxNb(callableStatement.getInt(2));
		// dashbordModel.setArchNb(callableStatement.getInt(5));
		// dashbordModel.setOutboxNb(callableStatement.getInt(4));
		// dashbordModel.setUnreadNb(callableStatement.getInt(3));
		// dashbordModel.setAuthorizationNb(callableStatement.getInt(6));
		// dashbordModel.setVacationNb(callableStatement.getInt(7));
		// return dashbordModel;
		// } catch (Exception e) {
		// logger.error("loadDasbordParams" + e.getMessage());
		// } finally {
		// try {
		// if (rs != null)
		// rs.close();
		// if (callableStatement != null)
		// callableStatement.close();
		// if (connection != null)
		// connection.close();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }
		return dashbordModel;
	}

	private void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} // end finally try
	}

	@Override
	public List<VacationModel> getUsersVacations(String lstUser, String startDate, Boolean allEmp) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<VacationModel> usersVacs = new ArrayList<>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_users_vctions(?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, lstUser);
			callableStatement.setString(2, startDate);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.setBoolean(4, allEmp);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);

			while (rs.next()) {
				VacationModel vacationModel = new VacationModel();
				vacationModel.setUserId(rs.getInt("userid"));
				vacationModel.setDateBegin(rs.getDate("vacastrt"));
				vacationModel.setDateEnd(rs.getDate("vacaend"));
				vacationModel.setType(rs.getInt("vacatyp"));
				vacationModel.setVacName(rs.getString("vacName"));
				usersVacs.add(vacationModel);
			}
		} catch (Exception e) {
			logger.error("getUsersVacations" + e.getMessage());
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
		return usersVacs;
	}

	@Override
	public List<DeptArcRecords> loadDeptRecords(Integer currDeptId, Integer status) {
		Connection connection = DataBaseConnectionClass.getConnection();
		List<DeptArcRecords> arcsDept = new ArrayList<>();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_dept_arcs(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, currDeptId);
			callableStatement.setInt(2, status);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);
			while (rs.next()) {
				DeptArcRecords arcDept = new DeptArcRecords();
				arcDept.setDonner(rs.getInt("donner"));
				arcDept.setArcId(rs.getInt("arc_id"));
				arcDept.setDonnerDeptId(rs.getInt("DONNER_DEPT_ID"));
				arcDept.setDonnerDeptName(rs.getString("DONNER_DEPT_NAME"));
				arcDept.setSubject(rs.getString("subject"));
				arcDept.setSignDate(rs.getDate("SIGN_DATE"));
				arcDept.setIncomeNo(rs.getInt("income_no"));
				// arcDept.set(rs.getInt("income_no"));
				arcDept.setAttachNb(rs.getInt("attach_nb"));
				arcDept.setReceiverDeptId(rs.getInt("receiver_dept_id"));
				arcDept.setWrkId(rs.getInt("wrk_id"));
				arcDept.setStepId(rs.getInt("step_id"));
				arcDept.setLetterFromNo(rs.getString("LETTER_FROM_NO"));
				arcDept.setLetterFromDate(rs.getString("LETTER_FROM_DATE"));
				arcDept.setReceiverDepName(rs.getString("receiver_dep_name"));
				if (status.equals(0)) {
					arcDept.setDonner(Utils.findCurrentUser().getUserId());
					arcDept.setDonnerDeptId(currDeptId);
					arcDept.setDonnerDeptName(Utils.findCurrentUser().getUserDept().getDeptName());
				}
				arcsDept.add(arcDept);
			}
		} catch (Exception e) {
			logger.error("loadDeptRecords" + e.getMessage());
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
		return arcsDept;
	}

	@Override
	public void correctUserFngs(Integer userId, String startDate, String endDate) {
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_update_user_fng(?,?,?)}";

			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, userId);
			callableStatement.setString(2, startDate);
			callableStatement.setString(3, endDate);
			callableStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("correctUserFngs" + e.getMessage());
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Map<Object, Number> loadArcRecordsNBForCurrentYear(Integer userId, Integer deptId) {
		ResultSet rs = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		Map<Object, Number> itemsEmpMap = new LinkedHashMap<Object, Number>();
		// Map<Integer, Integer> itemsDeptMap = new LinkedHashMap<Integer,
		// Integer>();

		Statement callableStatement = null;
		try {
			callableStatement = connection.createStatement();
			// if(deptId != null){
			// String sql1 = generateRequest(userId, deptId);
			// rs = callableStatement.executeQuery(sql1);
			// while (rs.next()) {
			// itemsDeptMap.put(rs.getInt("month"), rs.getInt("nbr"));
			// }
			// }

			String sql = generateRequest(userId, deptId);
			rs = callableStatement.executeQuery(sql);
			while (rs.next()) {
				// if(deptId == null)
				itemsEmpMap.put(rs.getInt("month"), NumberFormat.getInstance().parse(rs.getString("nbr")));
				// else
				// itemsEmpMap.put(Month.of(rs.getInt("month")).getDisplayName(TextStyle.FULL,
				// new Locale("ar")),
				// (itemsDeptMap.get(rs.getInt("month"))==null)?0:NumberFormat.getInstance().parse(""+(rs.getInt("nbr")/itemsDeptMap.get(rs.getInt("month")))*100));
			}

			return itemsEmpMap;
		} catch (Exception e) {
			logger.error("getTrdMasterFileListByPeriodType" + e.getMessage());
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
		return itemsEmpMap;
	}

	private String generateRequest(Integer userId, Integer deptId) {
		return "SELECT TO_NUMBER(SUBSTR(REC_H_DATE, 6, 2)) AS month, count(rec.ID) AS nbr  "
				+ "FROM ARC_RECORDS rec, WRK_APPLICATION app, ARC_USERS us"
				+ " WHERE rec.ID = app.APP_ID AND SUBSTR(REC_H_DATE, 1, 4) = '1439' AND (app.FROM_ID=us.USER_ID OR app.TO_ID=us.USER_ID) "
				+ ((deptId == null) ? (" AND " + userId + " = us.USER_ID") : " AND us.DEPT_ID=" + deptId)
				+ " GROUP BY TO_NUMBER(SUBSTR(REC_H_DATE, 6, 2)) ORDER BY TO_NUMBER(SUBSTR(REC_H_DATE, 6, 2))";
	}

	@Override
	public List<String> getEmpSalariesFile(Integer p_month, Integer p_year) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<String> rowsData = new ArrayList<String>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_salaries(?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.setInt(1, p_month);
			callableStatement.setInt(2, p_year);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);

			while (rs.next()) {
				rowsData.add(rs.getString("SALARY"));
			}
		} catch (Exception e) {
			logger.error("getEmpSalariesFile" + e.getMessage());
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
		return rowsData;
	}

	@Override
	public StoreRequestModel getArticleHistoryById(int articleId) {
		// TODO Auto-generated method stub
		return null;
	}

	/// balable
	@Override
	public List<StoreRequestModel> ListStoreRequestModel(int strNo) {

		Connection connection = DataBaseConnectionClass.getConnection();
		List<StoreRequestModel> storeRequestModelList = new ArrayList<StoreRequestModel>();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_artsGarden(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.setInt(2, strNo);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(1);

			while (rs.next()) {
				StoreRequestModel store = new StoreRequestModel();
				store.setQtyAvailable(rs.getInt("available"));
				store.setQtyReserved(rs.getInt("reserved"));
				store.setQtyInput(rs.getInt("input"));
				store.setQtyOutput(rs.getInt("output"));
				store.setArticleId(rs.getInt("id"));
				store.setArticleName(rs.getString("name"));
				store.setArticleCode(rs.getString("code"));
				store.setArticleUnite(rs.getString("ITEMUNITNAME"));
				storeRequestModelList.add(store);
			}
		} catch (Exception e) {
			logger.error("ListstoreRequestModels" + e.getMessage());
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
		return storeRequestModelList;
	}

	@Override
	public MemoReceiptModel getMemoReceiptDetails(Integer memo_receipt_id) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		MemoReceiptModel memoReceiptModel = new MemoReceiptModel();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_memoReceipt_details(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.setInt(1, memo_receipt_id);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				memoReceiptModel = new MemoReceiptModel();
				memoReceiptModel.setSuppName(rs.getString("SUPPNAME"));
				memoReceiptModel.setStrName(rs.getString("STRNAME"));
				memoReceiptModel.setEntryByNo(rs.getInt("STOCK_BUY_NO"));
				memoReceiptModel.setEntryByDAte(rs.getString("STOCK_BUY_DATE"));
				memoReceiptModel.setEntryNoticeNo(rs.getInt("STOCK_NOTICE_NO"));
				memoReceiptModel.setEntryNoticeDate(rs.getString("STOCK_NOTICE_DATE"));
				memoReceiptModel.setReqName(rs.getString("REQ_NAME"));
				memoReceiptModel.setReqSignDate(rs.getString("REQ_sign_DATE"));
				memoReceiptModel.setStoreDeanName(rs.getString("STORE_DEAN_NAME"));
				memoReceiptModel.setStoreDeanSignDate(rs.getString("STORE_DEAN_SIGN_DATE"));
				memoReceiptModel.setStoreMgr(rs.getString("STORE_MGR"));
				memoReceiptModel.setStoreMgrSignDate(rs.getString("STORE_MGR_SIGN_DATE"));

			}
		} catch (Exception e) {
			logger.error("getMemoReceiptDetails" + e.getMessage());
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
		return memoReceiptModel;
	}

	@Override
	public String convertNumberToLiteralWords(Float number) {
		String words = new String();
		Connection connection = DataBaseConnectionClass.getConnection();
		CallableStatement callableStatement = null;

		try {
			String sql = "call procedures.Num_ar(?,?)";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setFloat(1, number);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			ResultSet rs = (ResultSet) callableStatement.getObject(2);
			words = rs.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return words;
	}

	@Override
	public List<StockEntryMaster> searchMemoReceipts(String beginDate, String finishDate, Integer strNo) {
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		List<StockEntryMaster> memoReceiptList = new ArrayList<StockEntryMaster>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_memoReqs(?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callableStatement.setString(1, beginDate);
			callableStatement.setString(2, finishDate);
			callableStatement.setInt(3, strNo);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(4);
			while (rs.next()) {
				StockEntryMaster memoReceipt = new StockEntryMaster();
				memoReceipt.setStockEntryMasterId(rs.getInt("id"));
				memoReceipt.setStockFinEntryNo(rs.getInt("stock_fin_entry_no"));
				memoReceipt.setStockFinEntryHdate(rs.getString("stock_fin_entry_hdate"));
				memoReceipt.setStockBuyDate(rs.getString("stock_buy_date"));
				memoReceipt.setSupplierName(rs.getString("RELATEDENTITYNAME"));
				memoReceipt.setRecordId(rs.getInt("recordId"));

				memoReceiptList.add(memoReceipt);
			}
		} catch (Exception e) {
			logger.error("searchMemoReceipts" + e.getMessage());
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
		return memoReceiptList;
	}

	@Override
	public List<Article> getArticlesByUserId(Integer userId) {
		System.out.println("user id " + userId);
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<Article> articlesList = new ArrayList<Article>();
		Article article = new Article();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_user_articles(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, userId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				article = new Article();
				article.setId(rs.getInt("art_id"));
				article.setName(rs.getString("art_name"));
				article.setCode(rs.getString("art_code"));
				article.setUnitName(rs.getString("unit_name"));
				article.setExchMasterId(rs.getInt("exmaster_id"));
				article.setStrNo(rs.getInt("STRNO"));

				// article.setExchMasterDate(rs.getString("exmaster_date"));
				articlesList.add(article);
			}
		} catch (Exception e) {
			logger.error("getArticlesByUserId" + e.getMessage());
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
		return articlesList;
	}

	@Override
	public List<Article> getArticlesByUserIdWithoutCars(Integer userId) {
		System.out.println("user id " + userId);
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<Article> articlesList = new ArrayList<Article>();
		Article article = new Article();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_user_articles_withoutCars(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, userId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				article = new Article();
				article.setId(rs.getInt("art_id"));
				article.setName(rs.getString("art_name"));
				article.setCode(rs.getString("art_code"));
				article.setUnitName(rs.getString("unit_name"));
				article.setExchMasterId(rs.getInt("exmaster_id"));
				article.setStrNo(rs.getInt("STRNO"));

				// article.setExchMasterDate(rs.getString("exmaster_date"));
				articlesList.add(article);
			}
		} catch (Exception e) {
			logger.error("getArticlesByUserId" + e.getMessage());
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
		return articlesList;
	}

	@Override
	public List<Article> getAllReturnStoreArticles(Integer strNo) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<Article> articlesList = new ArrayList<Article>();
		Article article = new Article();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_items_qty(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, strNo);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				article = new Article();
				article.setId(rs.getInt("ID"));
				article.setName(rs.getString("NAME"));
				// article.setCode(rs.getString("CODE"));
				article.setUnitName(rs.getString("ITEMUNITNAME"));
				article.setQty(rs.getInt("sumQTy"));
				article.setStrNo(strNo);
				articlesList.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getAllReturnStoreArticles" + e.getMessage());
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
		return articlesList;
	}

	@Override
	public List<Car> getCarsDetailsBySubGroupId(Integer subGroupId) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<Car> carsList = new ArrayList<>();
		Car car = new Car();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_carDetails_BySubGrupId(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, subGroupId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				car = new Car();
				car.setId(rs.getInt("ID"));
				car.setArtId(rs.getInt("ART_ID"));
				// System.out.println("art id"+car.getArtId());
				car.setArtName(rs.getString("name"));
				car.setCarCode(rs.getString("CAR_SERIAL"));
				car.setCarColor(rs.getString("CAR_COLOR"));
				car.setChassisNumber(rs.getString("CHASSIS_NUMBER"));
				car.setMatricule(rs.getString("MATRICULE"));
				car.setCarModel(rs.getInt("CAR_MODEL"));
				car.setFuelTypeId(rs.getInt("FUEL_TYPE"));
				car.setVehicleTypeId(rs.getInt("VEHICLE_TYPE_ID"));
				car.setYearModel(rs.getInt("YEAR_MODEL"));
				car.setNumDoor(rs.getInt("NUM_DOOR"));
				carsList.add(car);
				// c.ID , c.ART_ID ,c.FUEL_TYPE ,c.CAR_MODEL ,
				// c.YEAR_MODEL ,c.VEHICLE_TYPE_ID , c.NUM_DOOR ,
				// c.CAR_COLOR , c.CHASSIS_NUMBER , c.CAR_SERIAL ,c.MATRICULE
			}
		} catch (Exception e) {
			logger.error("getCarsDetailsBySubGroupId" + e.getMessage());
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
		return carsList;
	}

	@Override
	public List<Article> get3ohadByUserId(Integer userId) {
		System.out.println("user id " + userId);
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		List<Article> articlesList = new ArrayList<Article>();
		Article article = new Article();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_user_alouhad(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, userId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
			while (rs.next()) {
				article = new Article();
				article.setReqType(rs.getInt("reqtYPE"));
				article.setId(rs.getInt("art_id"));
				article.setName(rs.getString("art_name"));
				article.setCode(rs.getString("art_code"));
				article.setUnitName(rs.getString("unit_name"));
				article.setExchMasterId(rs.getInt("exmaster_id"));
				article.setStrNo(rs.getInt("STRNO"));

				// article.setExchMasterDate(rs.getString("exmaster_date"));
				articlesList.add(article);
			}
		} catch (Exception e) {
			logger.error("get3ohadByUserId" + e.getMessage());
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
		return articlesList;
	}

	@Override
	public List<StoreTemporeryReceiptMaster> searchTempReceipts(String beginDate, String finishDate, Integer strNo) {
		Connection connection = DataBaseConnectionClass.getConnection();
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		List<StoreTemporeryReceiptMaster> tempReceiptList = new ArrayList<StoreTemporeryReceiptMaster>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_tempReceipts(?,?,?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callableStatement.setString(1, beginDate);
			callableStatement.setString(2, finishDate);
			callableStatement.setInt(3, strNo);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(4);
			while (rs.next()) {
				StoreTemporeryReceiptMaster tempoReceipt = new StoreTemporeryReceiptMaster();
				tempoReceipt.setId(rs.getInt("id"));
				tempoReceipt.setSpecialNumber(rs.getInt("SPECIAL_NUM"));
				tempoReceipt.setDocumentNumber(rs.getInt("DOCUMENT_NUMBER"));
				tempoReceipt.setReceiptHDate(rs.getString("RECEIPT_H_DATE"));
				tempoReceipt.setDocumentHDate(rs.getString("DOCUMENT_H_DATE"));
				tempoReceipt.setDocument(rs.getInt("DOCUMENT_TYPE"));
				tempoReceipt.setSupplierName(rs.getString("RELATEDENTITYNAME"));
				tempoReceipt.setRecordId(rs.getInt("recordId"));

				tempReceiptList.add(tempoReceipt);
			}
		} catch (Exception e) {
			logger.error("searchTempReceipts" + e.getMessage());
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
		return tempReceiptList;
	}

	@Override
	public List<StoreTemporeryReceiptDetailsModel> getTempReceiptDetailsList(Integer temp_receipt_id) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection connection = DataBaseConnectionClass.getConnection();
		StoreTemporeryReceiptDetailsModel tempReceiptModel = new StoreTemporeryReceiptDetailsModel();
		List<StoreTemporeryReceiptDetailsModel> tempReceiptModelList = new ArrayList<StoreTemporeryReceiptDetailsModel>();
		try {
			String sql = "{call NEW_PKG_WEBKIT.prc_get_tempReceipt_details(?,?)}";
			callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.setInt(1, temp_receipt_id);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(2);
		 	while (rs.next()) {
				tempReceiptModel = new StoreTemporeryReceiptDetailsModel();
				tempReceiptModel.setArticleId(rs.getInt("ARTICLE_ID"));
				tempReceiptModel.setQty(rs.getInt("QTY"));
				tempReceiptModel.setNotes(rs.getString("NOTES"));
				tempReceiptModel.setUnit(rs.getString("ARTICLE_UNIT"));
				tempReceiptModel.setArticleStatus(rs.getInt("ARTICLE_STATUS"));
				tempReceiptModel.setArticleName(rs.getString("ARTICLE_NAME"));
				tempReceiptModel.setArticleUnit(rs.getString("ARTICLE_UNIT"));
				tempReceiptModel.setSupplierName(rs.getString("SUPPLIER_NAME"));
				tempReceiptModelList.add(tempReceiptModel);

			} 
		} catch (Exception e) {
			logger.error("getTempReceiptDetailsList " + e.getMessage());
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
		return tempReceiptModelList;
	}
}
