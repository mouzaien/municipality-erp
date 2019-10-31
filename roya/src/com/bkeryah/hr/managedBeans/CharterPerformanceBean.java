package com.bkeryah.hr.managedBeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsJobHistorical;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.hr.entities.HrsAppreciationScale;
import com.bkeryah.hr.entities.HrsCompactBaseFloor;
import com.bkeryah.hr.entities.HrsCompactCatFloor;
import com.bkeryah.hr.entities.HrsCompactFloors;
import com.bkeryah.hr.entities.HrsCompactGoals;
import com.bkeryah.hr.entities.HrsCompactPerformance;
import com.bkeryah.hr.entities.HrsFloors;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class CharterPerformanceBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<HrsCompactGoals> lstGoals = new ArrayList<>();
	private HrsCompactGoals goal;
	private List<HrsCompactCatFloor> lstCatFloors;
	private List<HrsFloors> lstHrsFlours;
	private List<HrsCompactFloors> hrsCommpactFloors;
	private HrsCompactPerformance compactPerformance = new HrsCompactPerformance();
	private List<ArcUsers> employersList;
	private boolean allowAdd = true;
	private HrsJobHistorical jobHistory;
	private Integer sumGoals = 0;
	private Integer sumFloors = 0;
	private Integer arcRcordId;
	private List<HrsCompactPerformance> compactPerformanceList;
	private List<HrsCompactPerformance> filteredPerformanceList;
	private List<WrkPurpose> wrkPurposes;
	private Integer performanceId;
	private Integer evaluation;
	private boolean signedAutorized = false;
	private boolean returnAutorized = true;
	private Integer currentUser = Utils.findCurrentUser().getUserId();
	private boolean updateMode = false;
	private Integer status = 0;
	private ArcUsers employer;
	private Integer countSigned = 0;
	private String applicationPurpose;
	private String applicationPurposeRefuse;
	private String wrkAppComment;
	private String wrkAppCommentRefuse;
	private boolean updateTable = false;
	private Integer modelTypeID;
	private boolean refuseAutorized = false;
	private String wrkAppCommentRef;
	private String applicationPurposeRef;
	private List<HrsAppreciationScale> appreciationScaleList;
	private String title = Utils.loadMessagesFromFile("charter.performance.title.mgr");
	private Integer selectedFloorId ;
	private boolean isMgr = true;
	private Integer countCatNumber;

	@PostConstruct
	public void init() {
		goal = new HrsCompactGoals();
		hrsCommpactFloors = dataAccessService.getHrsCompactFloors(-1, 1);
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		httpSession.removeAttribute("arcRecord");
		// test if in view mode or insert mode
		if (arcRecordId != null) {
			updateMode = true;
			arcRcordId = Integer.parseInt(arcRecordId.trim());
			ArcRecords arcRecordObject = (ArcRecords) dataAccessService.findEntityById(ArcRecords.class, arcRcordId);
			modelTypeID = arcRecordObject.getApplicationType();
			compactPerformance = (HrsCompactPerformance) dataAccessService.getHrsCompactPerformByArcID(arcRcordId);
			countSigned = dataAccessService.getWrkCountSigned(arcRcordId);
			WrkApplication app = dataAccessService.getWrkApplicationRecordById(arcRcordId);
			wrkPurposes = dataAccessService.getAllPurposes();
			Integer userTo = dataAccessService.getNextScenarioUserId(modelTypeID, app.getId().getApplicationId(),
					arcRcordId, 1);
			if (modelTypeID.equals(MailTypeEnum.COMPACT_PERFORMANCE.getValue()))
				initCharter(userTo);
			else
				initCharterPerform(userTo);
			calculateSumBaseFloors();
		} else {
			performanceId = (Integer) httpSession.getAttribute("performanceId");
			if (performanceId != null) {
				httpSession.removeAttribute("performanceId");
				compactPerformance = (HrsCompactPerformance) dataAccessService
						.findEntityById(HrsCompactPerformance.class, performanceId);
			}
		}
		if (compactPerformance.getEmpid() != null) {
			try {
				compactPerformance.setEmployer(dataAccessService.getUserNameById(compactPerformance.getEmpid()));
				status = compactPerformance.getStatus();
				lstGoals = dataAccessService.findHrsCompactPerformGoals(compactPerformance.getId());
				isMgr = (compactPerformance.getEmployer().getWrkRoleId() != 4);
				if (isMgr) {
					title = Utils.loadMessagesFromFile("charter.performance.title.mgr");
					hrsCommpactFloors = dataAccessService.getHrsCompactFloors(compactPerformance.getId(), 1);
					countCatNumber = 7;
				} else {
					title = Utils.loadMessagesFromFile("charter.performance.title");
					hrsCommpactFloors = dataAccessService.getHrsCompactFloors(compactPerformance.getId(), 2);
					countCatNumber = 6;
				}
				calculateSumFloors();
				calculateSumRGoals();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void initCharter(Integer userTo) {
		if (countSigned.equals(2)) {
			if (compactPerformance.getEmpid().equals(currentUser))
				signedAutorized = true;
			else if (userTo.equals(99999))
				updateTable = true;
		} else {
			if (userTo.equals(currentUser)) {
				refuseAutorized = true;
				signedAutorized = true;
			}
		}
		if (countSigned.equals(5))
			returnAutorized = false;
	}

	private void initCharterPerform(Integer userTo) {
		if (userTo.equals(currentUser)) {
			signedAutorized = true;
			refuseAutorized = true;
		}
		if (countSigned.equals(3))
			returnAutorized = false;
	}

	public void loadPerformances() {
		compactPerformanceList = dataAccessService.loadNotEvaluatedPerformances();
		for (HrsCompactPerformance hrsCompactPerformance : compactPerformanceList) {
			try {
				hrsCompactPerformance.setEmployer(dataAccessService.getUserNameById(hrsCompactPerformance.getEmpid()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String loadSelectedPerformance(HrsCompactPerformance selectedPerformance) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("performanceId", selectedPerformance.getId());
		return "charter_performance";
	}

	public String loadSelectedPerformancForGeneralAppreciation(HrsCompactPerformance selectedPerformance) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("performanceId", selectedPerformance.getId());
		return "general_appreciation";
	}
	
	
	public void fillAppScale( ) 
	{
		String  locale = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("language");
		appreciationScaleList = dataAccessService.findAppreciationScalesByFloorId(Integer.parseInt(locale));
	}

	public void loadData() {

	}

	public void addGoal(AjaxBehaviorEvent event) {

		try {

			if (sumGoals + goal.getRelativeImportance() > 100) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.relative.importance"));
				return;
			}
			if (goal.getRelativeImportance() < 15) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.relative.importance.goal.less.15"));
				return;
			}
			lstGoals.add(goal);
			sumGoals += goal.getRelativeImportance();
			goal = new HrsCompactGoals();
			if ((lstGoals.size() == 6) || (sumGoals == 100))
				allowAdd = false;

		} catch (Exception e) {
			MsgEntry.addErrorMessage("Ok");
		}

	}

	public void removeRecord(HrsCompactGoals goal) {
		lstGoals.remove(goal);
		sumGoals -= goal.getRelativeImportance();
		if (lstGoals.size() < 6)
			allowAdd = true;
	}

	public String savecharter() {
		try {
			if (!checkFields())
				return "";
			dataAccessService.saveMasterHrsCompactPerformance(compactPerformance, hrsCommpactFloors, lstGoals, title,
					countCatNumber);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return "mails";
	}

	public String savecharterEvaluation() {
		try {
			dataAccessService.saveHrsCompactPerformance(compactPerformance, hrsCommpactFloors, lstGoals, title);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return "mails";
	}

	public String accept() {
		try {
			updateTable = false;
			dataAccessService.acceptModel(compactPerformance, hrsCommpactFloors, lstGoals, modelTypeID, wrkAppComment,
					Integer.parseInt(applicationPurpose.trim()), arcRcordId);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return "mails";
	}

	public String refuse() {
		try {
			updateTable = false;
			dataAccessService.refuseCharterModel(compactPerformance, hrsCommpactFloors, lstGoals, modelTypeID,
					wrkAppCommentRef, Integer.parseInt(applicationPurposeRef.trim()), arcRcordId);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return "mails";
	}

	public String acceptPerform() {
		try {
			dataAccessService.acceptModel(compactPerformance, hrsCommpactFloors, lstGoals, modelTypeID, wrkAppComment,
					Integer.parseInt(applicationPurpose.trim()), arcRcordId);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return "mails";
	}

	public String sentToSender() {
		try {
			dataAccessService.returnWrk(compactPerformance, arcRcordId, hrsCommpactFloors, lstGoals,
					wrkAppCommentRefuse, Integer.parseInt(applicationPurposeRefuse.trim()));
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return "mails";
	}

	private boolean checkFields() {
		if ((sumGoals != 100) || (!checkSumFloors())) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.relative.importance"));
			return false;
		}
		return true;
	}

	private boolean checkSumFloors() {
		calculateSumFloors();
		return (sumFloors == 100);
	}

	public void calculateSumFloors() {
		sumFloors = 0;
		List<Integer> catFloorsList = new ArrayList<>();
		for (HrsCompactFloors floor : hrsCommpactFloors) {
			if ((floor.getRelativeImportance() == null) || (floor.getRelativeImportance() == 0)
					|| (catFloorsList.contains(floor.getCatFloorId())))
				continue;
			sumFloors += floor.getRelativeImportance();
			catFloorsList.add(floor.getCatFloorId());
		}
	}

	private void calculateSumBaseFloors() {
		sumFloors = 0;
		List<HrsCompactBaseFloor> baseFloors = dataAccessService.loadBaseFloors(compactPerformance.getId());
		for (HrsCompactBaseFloor baseFloor : baseFloors) {
			sumFloors += baseFloor.getRelativeImportance();
		}
	}

	public void calculateSumRGoals() {
		sumGoals = 0;
		for (HrsCompactGoals goal : lstGoals) {
			sumGoals += goal.getRelativeImportance();
		}
	}

	public void calculateEvaluation(HrsCompactGoals goal) {
		// for (HrsCompactGoals goal : lstGoals) {
		double res = Double.parseDouble(goal.getRealResult().toString())
				/ Double.parseDouble(goal.getTargetOutput().toString());
		if (res > 1)
			goal.setEvaluation(5);
		else if (res >= 0.9)
			goal.setEvaluation(4);
		else if (res >= 0.8)
			goal.setEvaluation(3);
		else if (res >= 0.6)
			goal.setEvaluation(2);
		else
			goal.setEvaluation(1);
		// }
	}

	public void loadEmployersByDept() {
		setEmployersList(dataAccessService.getAllActiveEmployeesInDept(compactPerformance.getDeptid()));
	}

	public void loadEmployerData() {
		dataAccessService.loadEmployerJobData(compactPerformance);
		if (compactPerformance.getEmployer().getWrkRoleId() == 4)
			isMgr = false;
		else
			isMgr = true;
		if (isMgr) {
			title = Utils.loadMessagesFromFile("charter.performance.title.mgr");
			hrsCommpactFloors = dataAccessService.getHrsCompactFloors(-1, 1);
			countCatNumber = 7;
		} else {
			title = Utils.loadMessagesFromFile("charter.performance.title");
			hrsCommpactFloors = dataAccessService.getHrsCompactFloors(-1, 2);
			countCatNumber = 6;
		}
	}

	public String printCharter() {
		String reportName = "/reports/compact_performance.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("performanceId", compactPerformance.getId());
		parameters.put("SUBREPORT_DIR1", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/sub_compact_performance_goals.jasper"));
		parameters.put("SUBREPORT_DIR2", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/sub_compact_performance_floors.jasper"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public List<HrsCompactGoals> getLstGoals() {
		return lstGoals;
	}

	public void setLstGoals(List<HrsCompactGoals> lstGoals) {
		this.lstGoals = lstGoals;
	}

	public HrsCompactGoals getGoal() {
		return goal;
	}

	public void setGoal(HrsCompactGoals goal) {
		this.goal = goal;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public boolean isAllowAdd() {
		return allowAdd;
	}

	public void setAllowAdd(boolean allowAdd) {
		this.allowAdd = allowAdd;
	}

	public List<HrsCompactCatFloor> getLstCatFloors() {
		return lstCatFloors;
	}

	public void setLstCatFloors(List<HrsCompactCatFloor> lstCatFloors) {
		this.lstCatFloors = lstCatFloors;
	}

	public List<HrsFloors> getLstHrsFlours() {
		return lstHrsFlours;
	}

	public void setLstHrsFlours(List<HrsFloors> lstHrsFlours) {
		this.lstHrsFlours = lstHrsFlours;
	}

	public List<HrsCompactFloors> getHrsCommpactFloors() {
		return hrsCommpactFloors;
	}

	public void setHrsCommpactFloors(List<HrsCompactFloors> hrsCommpactFloors) {
		this.hrsCommpactFloors = hrsCommpactFloors;
	}

	public HrsCompactPerformance getCompactPerformance() {
		return compactPerformance;
	}

	public void setCompactPerformance(HrsCompactPerformance compactPerformance) {
		this.compactPerformance = compactPerformance;
	}

	public List<ArcUsers> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<ArcUsers> employersList) {
		this.employersList = employersList;
	}

	public HrsJobHistorical getJobHistory() {
		return jobHistory;
	}

	public void setJobHistory(HrsJobHistorical jobHistory) {
		this.jobHistory = jobHistory;
	}

	public Integer getSumGoals() {
		return sumGoals;
	}

	public void setSumGoals(Integer sumGoals) {
		this.sumGoals = sumGoals;
	}

	public Integer getSumFloors() {
		return sumFloors;
	}

	public void setSumFloors(Integer sumFloors) {
		this.sumFloors = sumFloors;
	}

	public Integer getArcRcordId() {
		return arcRcordId;
	}

	public void setArcRcordId(Integer arcRcordId) {
		this.arcRcordId = arcRcordId;
	}

	public List<HrsCompactPerformance> getCompactPerformanceList() {
		return compactPerformanceList;
	}

	public void setCompactPerformanceList(List<HrsCompactPerformance> compactPerformanceList) {
		this.compactPerformanceList = compactPerformanceList;
	}

	public List<HrsCompactPerformance> getFilteredPerformanceList() {
		return filteredPerformanceList;
	}

	public void setFilteredPerformanceList(List<HrsCompactPerformance> filteredPerformanceList) {
		this.filteredPerformanceList = filteredPerformanceList;
	}

	public Integer getPerformanceId() {
		return performanceId;
	}

	public void setPerformanceId(Integer performanceId) {
		this.performanceId = performanceId;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	public boolean getSignedAutorized() {
		return signedAutorized;
	}

	public void setSignedAutorized(boolean isSignedAutorized) {
		this.signedAutorized = isSignedAutorized;
	}

	public boolean isUpdateMode() {
		return updateMode;
	}

	public void setUpdateMode(boolean updateMode) {
		this.updateMode = updateMode;
	}

	public boolean getReturnAutorized() {
		return returnAutorized;
	}

	public void setReturnAutorized(boolean returnAutorized) {
		this.returnAutorized = returnAutorized;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ArcUsers getEmployer() {
		return employer;
	}

	public void setEmployer(ArcUsers employer) {
		this.employer = employer;
	}

	public Integer getCountSigned() {
		return countSigned;
	}

	public void setCountSigned(Integer countSigned) {
		this.countSigned = countSigned;
	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public String getWrkAppComment() {
		return wrkAppComment;
	}

	public void setWrkAppComment(String wrkAppComment) {
		this.wrkAppComment = wrkAppComment;
	}

	public boolean isUpdateTable() {
		return updateTable;
	}

	public void setUpdateTable(boolean updateTable) {
		this.updateTable = updateTable;
	}

	public String getApplicationPurposeRefuse() {
		return applicationPurposeRefuse;
	}

	public void setApplicationPurposeRefuse(String applicationPurposeRefuse) {
		this.applicationPurposeRefuse = applicationPurposeRefuse;
	}

	public String getWrkAppCommentRefuse() {
		return wrkAppCommentRefuse;
	}

	public void setWrkAppCommentRefuse(String wrkAppCommentRefuse) {
		this.wrkAppCommentRefuse = wrkAppCommentRefuse;
	}

	public String getWrkAppCommentRef() {
		return wrkAppCommentRef;
	}

	public void setWrkAppCommentRef(String wrkAppCommentRef) {
		this.wrkAppCommentRef = wrkAppCommentRef;
	}

	public String getApplicationPurposeRef() {
		return applicationPurposeRef;
	}

	public void setApplicationPurposeRef(String applicationPurposeRef) {
		this.applicationPurposeRef = applicationPurposeRef;
	}

	public boolean isRefuseAutorized() {
		return refuseAutorized;
	}

	public void setRefuseAutorized(boolean refuseAutorized) {
		this.refuseAutorized = refuseAutorized;
	}

	public List<HrsAppreciationScale> getAppreciationScaleList() {
		return appreciationScaleList;
	}

	public void setAppreciationScaleList(List<HrsAppreciationScale> appreciationScaleList) {
		this.appreciationScaleList = appreciationScaleList;
	}

	public Integer getSelectedFloorId() {
		return selectedFloorId;
	}

	public void setSelectedFloorId(Integer selectedFloorId) {
		this.selectedFloorId = selectedFloorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isMgr() {
		return isMgr;
	}

	public void setMgr(boolean isMgr) {
		this.isMgr = isMgr;
	}
}
