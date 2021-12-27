package com.bkeryah.fng.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.fileupload.RequestContext;
import org.apache.logging.log4j.*;
import org.primefaces.event.SelectEvent;

import com.bkeryah.fng.entities.FngTimeTable;
import com.bkeryah.fng.entities.FngUserShiftId;
import com.bkeryah.fng.entities.FngUserTempShift;
import com.bkeryah.model.User;
import com.bkeryah.penalties.PenaltyBean;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class EmpPlanificationBean {
	protected static final Logger logger = LogManager.getLogger(PenaltyBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<User> employersList;
	private List<User> employersListSelected;
	private String deptId = "-1";
	private List<String> deptIds = new ArrayList<String>();
	private String beginDate = HijriCalendarUtil.findCurrentHijriDate();
	private String endDate = HijriCalendarUtil.findCurrentHijriDate();
	private Integer empId;
	private Integer beginDay;
	private Integer endDay;
	private FngTimeTable shift;
	private FngTimeTable secondShift;
	private List<FngUserTempShift> fngUserTempShiftLst = new ArrayList<>();
	private List<FngUserTempShift> fngShiftLst = new ArrayList<>();
	private boolean allchecked;
	private boolean higriMode;
	private String selecteDateFrom;
	private Date selecteDateFrom_G;
	private String selecteDateTo;
	private Date selecteDateTo_G;

	public FngTimeTable getSecondShift() {
		return secondShift;
	}

	public void setSecondShift(FngTimeTable secondShift) {
		this.secondShift = secondShift;
	}

	public boolean isAllchecked() {
		return allchecked;
	}

	public void setAllchecked(boolean allchecked) {
		this.allchecked = allchecked;
	}

	public FngTimeTable getShift() {
		return shift;
	}

	public List<FngUserTempShift> getFngUserTempShiftLst() {
		return fngUserTempShiftLst;
	}

	public void setFngUserTempShiftLst(List<FngUserTempShift> fngUserTempShiftLst) {
		this.fngUserTempShiftLst = fngUserTempShiftLst;
	}

	public void setShift(FngTimeTable shift) {
		this.shift = shift;
	}

	@PostConstruct
	public void init() {
		// employersList = dataAccessService.getAllEmployeesInDept(-1);

		// deptIds.add(-1);
		// employersList =
		// dataAccessService.getAllEmployeesInSelectedDepts(deptIds);

	}

	public void loadEmployersByDept(AjaxBehaviorEvent event) {
		// employersList =
		// dataAccessService.getAllEmployeesInDept(Integer.parseInt(deptId));

		// -----------------
		// employersList =
		// dataAccessService.getAllEmployeesInSelectedDepts(deptIds);
		employersList = new ArrayList<User>();
		List<User> empList = new ArrayList<User>();
		for (String id : deptIds) {

			empList = new ArrayList<User>();
			empList = dataAccessService.getAllEmployeesInDept(Integer.parseInt(id));
			employersList.addAll(empList);
		}

	}

	public void reLoadEmployers(AjaxBehaviorEvent event) {
		if (allchecked)
			employersList = dataAccessService.getAllEmployeesInDept(-1);
	}

	public void save() {

		for (FngUserTempShift shift : fngShiftLst) {
			FngUserTempShift userShift = new FngUserTempShift();
			// shift.getSecondTimeid() >
			for (User arcUsers : employersListSelected) {
				FngUserShiftId shiftId = new FngUserShiftId();
				shiftId.setUserid(arcUsers.getUserId());
				shiftId.setWorkdate(shift.getId().getWorkdate());
				shiftId.setTimeid(shift.getId().getTimeid());

				userShift.setId(shiftId);
				dataAccessService.saveObject(userShift);

				// FngUserTempShift();
			}

		}
		MsgEntry.addInfoMessage("تم الحفظ");
		// }
		fngUserTempShiftLst.clear();
	}

	public void loadUserPeriodShift(AjaxBehaviorEvent event) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		List<FngUserTempShift> shifts = new ArrayList<FngUserTempShift>();
		String fristOutTime = null;
		String secondInTime = null;
		try {

			if (higriMode) {
				selecteDateFrom_G = Utils.convertHDateToGDate(selecteDateFrom);
				selecteDateTo_G = Utils.convertHDateToGDate(selecteDateTo);
			}
			fngShiftLst.clear();
			for (Date currDate = selecteDateFrom_G; currDate.getTime() <= selecteDateTo_G
					.getTime(); currDate = new Date(currDate.getYear(), currDate.getMonth(), currDate.getDate() + 1)) {

				FngUserTempShift tempShift = new FngUserTempShift();
				FngUserShiftId tempShiftId = new FngUserShiftId();
				tempShiftId.setWorkdate(format.format(currDate));
				if (shift != null)
					tempShiftId.setTimeid(shift.getTimeShiftId());
				tempShiftId.setUserid(empId);
				tempShift.setId(tempShiftId);
				fngShiftLst.add(tempShift);

				for (User user : employersListSelected) {
					if (user.getUserId() != null && currDate != null) {

						shifts = dataAccessService.getEmployeeShiftsById(user.getUserId(), currDate);

						if (shifts.size() >= 1) {

							fristOutTime = dataAccessService.getTimeShiftById(shifts.get(0).getId().getTimeid())
									.getEndWork();
							secondInTime = shift.getStartWork();
							if (Utils.convertStringHourToTimeDate(secondInTime)
									.compareTo(Utils.convertStringHourToTimeDate(fristOutTime)) < 0) {
								dataAccessService.deleteFngUserTempShiftById(user.getUserId(), currDate);
								MsgEntry.addInfoMessage("تم تغير الدوام");
							}
						}
					}

					FngUserTempShift userShift = new FngUserTempShift();
					FngUserShiftId shiftId = new FngUserShiftId();
					shiftId.setWorkdate(format.format(currDate));
					shiftId.setTimeid(shift.getTimeShiftId());
					shiftId.setUserid(empId);
					userShift.setTimeName(shift.getTimeName());
					userShift.setId(shiftId);
					userShift.setUserDeptName(user.getDeptName());
					userShift.setUserName(user.getEmployeeName());
					fngUserTempShiftLst.add(userShift);
				}

				// RequestContext context = RequestContext.getCurrentInstance();
				// context.execute("PF('showUserShifts').show();");
			}
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			shift = null;
			secondShift = null;

		}

	}

	public void loadUserShifts() {
		fngShiftLst = new ArrayList<>();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		List<FngUserTempShift> shifts = new ArrayList<FngUserTempShift>();
		try {

			if (higriMode) {
				selecteDateFrom_G = Utils.convertHDateToGDate(selecteDateFrom);
				selecteDateTo_G = Utils.convertHDateToGDate(selecteDateTo);
			}
			fngShiftLst.clear();
			for (Date currDate = selecteDateFrom_G; currDate.getTime() <= selecteDateTo_G
					.getTime(); currDate = new Date(currDate.getYear(), currDate.getMonth(), currDate.getDate() + 1)) {
				//
				// FngUserTempShift tempShift = new FngUserTempShift();
				// FngUserShiftId tempShiftId = new FngUserShiftId();
				// tempShiftId.setWorkdate(format.format(currDate));
				// if(shift != null)
				// tempShiftId.setTimeid(shift.getTimeShiftId());
				// tempShiftId.setUserid(empId);
				// tempShift.setId(tempShiftId);
				// //fngShiftLst.add(tempShift);

				for (User user : employersListSelected) {
					if (user.getUserId() != null && currDate != null) {
						shifts = dataAccessService.getEmployeeShiftsById(user.getUserId(), currDate);
					}
					if (shifts.size() > 0) {
						FngTimeTable time = dataAccessService.getTimeShiftById(shifts.get(0).getId().getTimeid());
						time.setTimeShiftId(shifts.get(0).getId().getTimeid());
						FngUserTempShift userShift = new FngUserTempShift();
						FngUserShiftId shiftId = new FngUserShiftId();
						shiftId.setWorkdate(format.format(currDate));
						shiftId.setTimeid(shifts.get(0).getId().getTimeid());
						userShift.setTimeName(time.getTimeName());
						shiftId.setUserid(user.getUserId());
						userShift.setId(shiftId);
						userShift.setId(shifts.get(0).getId());
						userShift.setUserDeptName(user.getDeptName());
						userShift.setUserName(user.getEmployeeName());
						fngShiftLst.add(userShift);
					}
				}
		
			}
			Utils.openDialog("timetable1");
		} catch (

		Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			logger.error(e.toString());
		} finally {
			shift = null;
			secondShift = null;
		}

	}

	public List<User> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<User> employersList) {
		this.employersList = employersList;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getEndDay() {
		return endDay;
	}

	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	public Integer getBeginDay() {
		return beginDay;
	}

	public void setBeginDay(Integer beginDay) {
		this.beginDay = beginDay;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public void onRowSelect(SelectEvent event) {
		shift = (FngTimeTable) event.getObject();
	}

	public void onSecondRowSelect(SelectEvent event) {
		secondShift = (FngTimeTable) event.getObject();
	}

	public List<User> getEmployersListSelected() {
		return employersListSelected;
	}

	public void setEmployersListSelected(List<User> employersListSelected) {
		this.employersListSelected = employersListSelected;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public String getSelecteDateFrom() {
		return selecteDateFrom;
	}

	public void setSelecteDateFrom(String selecteDateFrom) {
		this.selecteDateFrom = selecteDateFrom;
	}

	public Date getSelecteDateFrom_G() {
		return selecteDateFrom_G;
	}

	public void setSelecteDateFrom_G(Date selecteDateFrom_G) {
		this.selecteDateFrom_G = selecteDateFrom_G;
	}

	public String getSelecteDateTo() {
		return selecteDateTo;
	}

	public void setSelecteDateTo(String selecteDateTo) {
		this.selecteDateTo = selecteDateTo;
	}

	public Date getSelecteDateTo_G() {
		return selecteDateTo_G;
	}

	public void setSelecteDateTo_G(Date selecteDateTo_G) {
		this.selecteDateTo_G = selecteDateTo_G;
	}

	public List<String> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<String> deptIds) {
		this.deptIds = deptIds;
	}

	public List<FngUserTempShift> getFngShiftLst() {
		return fngShiftLst;
	}

	public void setFngShiftLst(List<FngUserTempShift> fngShiftLst) {
		this.fngShiftLst = fngShiftLst;
	}

}