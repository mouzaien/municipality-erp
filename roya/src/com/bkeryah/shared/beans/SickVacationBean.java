package com.bkeryah.shared.beans;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrEmployeeVacation;
import com.bkeryah.entities.HrsEmpHistorical;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.HrsSalaryScale;
import com.bkeryah.entities.HrsScenarioDocument;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class SickVacationBean extends Scanner {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	// private List<HrsMasterFile> employers;
	private HrEmployeeVacation selectVacation;
	private Employer employer = new Employer();
	private Integer employerId;
	private Integer scoreVacation;
	private Integer scoreTotalSalary = 0;
	private Integer scoreHalfSalary = 0;
	private Integer scoreQuarterSalary = 0;
	private Integer scoreWithoutSalary = 0;
	private Integer departmentId;
	private List<ArcUsers> employersList;
	private UserMailObj selectedInbox;
	private WrkApplicationId wrkId;
	private boolean disableAccept;
	private List<ArcAttach> attachs = new ArrayList<ArcAttach>();
	protected static final Logger logger = Logger.getLogger(SickVacationBean.class);

	private boolean higriMode;
	private String higriDate;
	private Date grigDate;

	@PostConstruct
	public void init() {
		// employers = dataAccessService.loadHrsMasterEmployers();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");

		// test if in view mode or insert mode

		if (arcRecordId != null) {
			selectedInbox = (UserMailObj) httpSession.getAttribute("selectedMail");
			setWrkId(new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId), selectedInbox.StepId));
			selectVacation = dataAccessService.loadVacationByArcRecord(Integer.parseInt(arcRecordId));
			employerId = selectVacation.getEmployeeNumber();
			loadEmployerData();
			Integer acceptCount = dataAccessService.getHrsSignNextStep(Integer.parseInt(arcRecordId));
			HrsScenarioDocument scenario = dataAccessService.getDestinationModel(MailTypeEnum.SICK_VACATION.getValue(),
					acceptCount);
			disableAccept = false;
			if ((scenario == null) || (scenario.getStepId() > 2))
				disableAccept = true;
			httpSession.removeAttribute("arcRecord");
		} else {
			selectVacation = new HrEmployeeVacation();
			selectVacation.setVacationType(MyConstants.SICK_VACATION);
		}

	}

	public void loadEmployerData() {
		ArcUsers user = dataAccessService.loadUserByEmpNO(employerId);
	  //ArcUsers user =dataAccessService.loadUserById(employerId);
		employer.setDepartment(user.getUserDept().getDeptName());
		employerId=user.getEmployeeNumber();
		HrsEmpHistorical empl = dataAccessService.getHRsEmpHistoricalByEmpNo(employerId);
		if ((empl != null) && (empl.getGovJob4() != null))
			employer.setJob(empl.getGovJob4().getJobName());
		employer.setRank(empl.getRankNumber());
		employer.setBasicSalary(empl.getBasicSalary());
		employer.setEmpNB(user.getEmployeeNumber());
		employer.setJobNumber((empl.getJobNumber()));
		HrsSalaryScale hrsSalaryScale = dataAccessService.findHrsSalaryScaleById(empl.getRankNumber());
		employer.setRankName(hrsSalaryScale.getRankName());
		HrsMasterFile hrsMasterFile = (HrsMasterFile) dataAccessService.findEntityById(HrsMasterFile.class,
				user.getEmployeeNumber());
		// employer.setBirthDateGerige(hrsMasterFile.getBirthDateGrig());
		employer.setFirstApplicationDate(hrsMasterFile.getFirstApplicationDate());
		employer.setName(user.getEmployeeName());
		employer.setId(employerId);
		scoreVacation = dataAccessService.loadScoreSickVacation(user.getEmployeeNumber());
		if (scoreVacation <= MyConstants.RANGE_SICK_VACATION)
			scoreTotalSalary = scoreVacation;
		else {
			scoreTotalSalary = MyConstants.RANGE_SICK_VACATION;
			if (scoreVacation <= 2 * MyConstants.RANGE_SICK_VACATION)
				scoreHalfSalary = scoreVacation - MyConstants.RANGE_SICK_VACATION;
			else {
				scoreHalfSalary = MyConstants.RANGE_SICK_VACATION;
				if (scoreVacation <= 3 * MyConstants.RANGE_SICK_VACATION)
					scoreQuarterSalary = scoreVacation - 2 * MyConstants.RANGE_SICK_VACATION;
				else {
					scoreQuarterSalary = MyConstants.RANGE_SICK_VACATION;
					if (scoreVacation <= 4 * MyConstants.RANGE_SICK_VACATION)
						scoreWithoutSalary = scoreVacation - 3 * MyConstants.RANGE_SICK_VACATION;
					else
						scoreWithoutSalary = MyConstants.RANGE_SICK_VACATION;
				}
			}
		}
		HrEmployeeVacation lastVacation = dataAccessService.loadLastVacation(employer.getEmpNB(),
				MyConstants.SICK_VACATION);
		if (lastVacation != null) {
			selectVacation.setLastVacationDate(lastVacation.getHigriVacationStart());
			selectVacation.setLastVacationPeriod(lastVacation.getVacationPeriod());
		}
	}

	public void loadEmployersByDept() {
		// setEmployersList(dataAccessService.getAllActiveEmployeesInDept(departmentId));
		employersList = dataAccessService.getAllActiveEmployeesInDept(departmentId);

	}

	public String saveAction() {
		try {
			String title = MessageFormat.format(Utils.loadMessagesFromFile("sick.vacation.title"), employer.getName(),
					HijriCalendarUtil.findCurrentHijriDate());

			attachs = Utils.SaveAttachementsToFtpServer(attachList);
			for (ArcAttach xx : attachs) {
				xx.setAttachOwner(Utils.findCurrentUser().getUserId());
			}
			List<Integer> attachIds = dataAccessService.addAttachments(attachs);
			dataAccessService.saveVacation(employer, selectVacation, title, MailTypeEnum.SICK_VACATION.getValue(),
					false, attachIds);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			// resetFields();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("sendNormalVacation	" + e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	public String acceptAction() {
		try {
			dataAccessService.acceptVacation(wrkId, selectVacation, MyConstants.SICK_VACATION);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	/**
	 * Calculate return date
	 */
	public void calculateEndDate() {
		try {
			if (higriMode) {
				grigDate = Utils.convertHDateToGDate(higriDate);
			} else {
				higriDate = Utils.grigDatesConvert(grigDate);
			}

			selectVacation.setHigriVacationStart(higriDate);
			if ((selectVacation.getVacationPeriod() != null) && (selectVacation.getVacationPeriod() != 0) && (selectVacation.getHigriVacationStart() != null)
					&& (checkDays()) && (!selectVacation.getHigriVacationStart().trim().equals(""))) {
				selectVacation.setHigriVacationEnd(HijriCalendarUtil.addDaysToHijriDate(
						selectVacation.getHigriVacationStart(), selectVacation.getVacationPeriod() - 1));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			MsgEntry.addErrorMessage("يجب اختيار المدة قبل التاريخ");
			e.printStackTrace();

		}
	}

	/**
	 * Validate and load vacation data
	 * 
	 * @return
	 */
	public boolean checkDays() {
		boolean valid = true;// validateNormalVacationDays();
		if(employer.getBasicSalary()!=null)
		{
			selectVacation.setCostVacation(
					Utils.formatDouble((double) (employer.getBasicSalary() * selectVacation.getVacationPeriod() / 30)));
			return valid;
		}
		// uncomment this code
		// selectVacation.setHDatevacationFrom(dataAccessService.getLastServiceDate(employer.getEmpNB()));
		// if ((selectVacation.getHDatevacationFrom() != null)
		// && (!selectVacation.getHDatevacationFrom().trim().equals("")))
		// selectVacation.setHDateVacationTo(Utils.calculateEndServiceDate(selectVacation.getHDatevacationFrom(),
		// selectVacation.getVacationPeriod()));
		//
		return false;
	}

	public void uploadFile(FileUploadEvent event) {

		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(event.getFile().getFileName());
			attach.setRealName(event.getFile().getFileName());
			attach.setAttachSize(event.getFile().getSize());

			attach.setAttachStream(event.getFile().getInputstream());
			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
			attach.setRealName(Utils.generateRandomName() + "." + attach.getAttachExt());
			attachList.add(attach);

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("lpcDlgMessage",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("no.upload"), ""));
		}

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public HrEmployeeVacation getSelectVacation() {
		return selectVacation;
	}

	public void setSelectVacation(HrEmployeeVacation selectVacation) {
		this.selectVacation = selectVacation;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public Integer getScoreVacation() {
		return scoreVacation;
	}

	public void setScoreVacation(Integer scoreVacation) {
		this.scoreVacation = scoreVacation;
	}

	public Integer getScoreHalfSalary() {
		return scoreHalfSalary;
	}

	public void setScoreHalfSalary(Integer scoreHalfSalary) {
		this.scoreHalfSalary = scoreHalfSalary;
	}

	public Integer getScoreQuarterSalary() {
		return scoreQuarterSalary;
	}

	public void setScoreQuarterSalary(Integer scoreQuarterSalary) {
		this.scoreQuarterSalary = scoreQuarterSalary;
	}

	public Integer getScoreWithoutSalary() {
		return scoreWithoutSalary;
	}

	public void setScoreWithoutSalary(Integer scoreWithoutSalary) {
		this.scoreWithoutSalary = scoreWithoutSalary;
	}

	public Integer getScoreTotalSalary() {
		return scoreTotalSalary;
	}

	public void setScoreTotalSalary(Integer scoreTotalSalary) {
		this.scoreTotalSalary = scoreTotalSalary;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public List<ArcUsers> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<ArcUsers> employersList) {
		this.employersList = employersList;
	}

	public WrkApplicationId getWrkId() {
		return wrkId;
	}

	public void setWrkId(WrkApplicationId wrkId) {
		this.wrkId = wrkId;
	}

	public UserMailObj getSelectedInbox() {
		return selectedInbox;
	}

	public void setSelectedInbox(UserMailObj selectedInbox) {
		this.selectedInbox = selectedInbox;
	}

	public boolean isDisableAccept() {
		return disableAccept;
	}

	public void setDisableAccept(boolean disableAccept) {
		this.disableAccept = disableAccept;
	}

	public List<AttachmentModel> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachmentModel> attachList) {
		this.attachList = attachList;
	}

	public List<ArcAttach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<ArcAttach> attachs) {
		this.attachs = attachs;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public String getHigriDate() {
		return higriDate;
	}

	public void setHigriDate(String higriDate) {
		this.higriDate = higriDate;
	}

	public Date getGrigDate() {
		return grigDate;
	}

	public void setGrigDate(Date grigDate) {
		this.grigDate = grigDate;
	}

}
