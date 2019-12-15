package com.bkeryah.mails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrLetterRequest;
import com.bkeryah.entities.HrsEmpHistorical;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.PayBank;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ViewScoped
public class HrLetterExcuter extends MailExecutor<HrLetterRequest> {
	public HrLetterExcuter(MailTypeEnum mailType) {
		super(mailType);

		if (mailType.equals(MailTypeEnum.SALARY))
			setUpdateViewFlage(232);
		else
			setUpdateViewFlage(233);

	}

	private boolean transferSalary;
	private boolean hrLetter;
	private Integer priviledge;
	private Integer updateViewFlage;
	private HrsMasterFile employeeInfo = new HrsMasterFile();
	private PayBank bank;
	private List<PayBank> banks = new ArrayList<PayBank>();
	private HrsMasterFile updatedEmployeeInfo = new HrsMasterFile();
	private UploadedFile file;
	private List<ArcAttach> attachs = new ArrayList<ArcAttach>();
	private List<AttachmentModel> attachModelList = new ArrayList<>();
	private ArcUsers arcUser = new ArcUsers();
	private Integer currentUserId;
	private ArcUsers fromUser;
	private Integer bankId;
	private String iban;
	private Integer viewerId;
	private HrsEmpHistorical empHistoricalInfo = new HrsEmpHistorical();
	private boolean viewer;
	private String purposForHrLetter;
	private String hrLetterDestenation;
	private boolean updateBankInfoPanelFlag;
	private Integer recordId;
	private Integer docType;
	private boolean authorized;
	private HrLetterRequest hrLetterRequest = new HrLetterRequest();

	public void updateView() {

		if (updateViewFlage == 233) {
			setHrLetter(true);
			setTransferSalary(false);

		} else {
			setHrLetter(false);
			setTransferSalary(true);

		}
	}

	public void uploadFile(FileUploadEvent event) {
		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(event.getFile().getFileName());
			attach.setAttachSize(event.getFile().getSize());
			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
			attach.setAttachStream(event.getFile().getInputstream());
			attachModelList.add(attach);
		} catch (IOException e) {

		}

	}

	/**
	 * send Hr Letter Request
	 * 
	 * @return
	 */
	public String sendRequestForHrletter() {

		if (purposForHrLetter != "" && hrLetterDestenation != "") {
			hrLetterRequest.setRequestType(MailTypeEnum.SALARY.getValue());
			// hrLetterRequest.setAcceptStatus("N");
			hrLetterRequest.setDestination(hrLetterDestenation);
			hrLetterRequest.setPurpose(purposForHrLetter);
			hrLetterRequest.setRank(empHistoricalInfo.getRankNumber());
			hrLetterRequest.setJOB(empHistoricalInfo.getGovJob4().getJobName());
			hrLetterRequest.setBasicSalary(empHistoricalInfo.getBasicSalary());
			hrLetterRequest.setCommision(empHistoricalInfo.getTransferSalary());
			hrLetterRequest.setEmpNo(Utils.findCurrentUser().getEmployeeNumber());
			try {

				dataAccessService.requsetHrLetter(purposForHrLetter, hrLetterDestenation, updateViewFlage,
						typeId.getValue(), hrLetterRequest);

				hrLetterDestenation = "";
				purposForHrLetter = "";
				hrLetterRequest = new HrLetterRequest();
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("request.send"));
				return "mails";
			} catch (Exception e) {
				logger.error("Error in send Hr Letter Request" + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null,		 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("error"), Utils.loadMessagesFromFile("error")));
				e.printStackTrace();
				return "";
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("required.fields"), Utils.loadMessagesFromFile("required.fields")));
			return "";

		}

	}

	// update user bank information
	public void updateUserInfo() {

		hrLetterRequest.setIbanNumber(iban);
		hrLetterRequest.setBankId(bankId);
		try {

			dataAccessService.updateObject(hrLetterRequest);

			iban = "";
			bankId = null;
			setUpdateBankInfoPanelFlag(true);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					Utils.loadMessagesFromFile("employee.data.update"), Utils.loadMessagesFromFile("employee.data.update")));
		} catch (Exception e) {
			logger.error("Error in update Hr Letter Request" + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("error"), Utils.loadMessagesFromFile("error")));
		}
	}

	// public void updateUserdetails() {
	//
	// HrsMasterFile currentInfoForRequest = (HrsMasterFile)
	// dataAccessService.findEntityById(HrsMasterFile.class,
	// fromUser.getEmployeeNumber());
	// // hrLetterRequest.setIbanNumber(iban);
	// // hrLetterRequest.setBankId(bankId);
	// currentInfoForRequest.setBankId(bankId);
	// currentInfoForRequest.setAccountNumber(iban);
	// try {
	// dataAccessService.updateObject(currentInfoForRequest);
	//
	// iban = "";
	// bankId = null;
	// setUpdateBankInfoPanelFlag(true);
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage(FacesMessage.SEVERITY_INFO,
	// "#{msg['employee.data.update']} ", "#{msg['employee.data.update']}"));
	// } catch (Exception e) {
	// logger.error("Error in update Hr Letter Request" + e.getMessage());
	// e.printStackTrace();
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage(FacesMessage.SEVERITY_ERROR, "#{msg['error']} ",
	// "#{msg['error']} "));
	// }
	// }

	/**
	 * save attachment
	 * 
	 * @param attachList
	 * @return
	 */
	public List<ArcAttach> SaveAttachementsInServer(List<AttachmentModel> attachList) {
		List<ArcAttach> myAttachs = new ArrayList<ArcAttach>();
		for (AttachmentModel att : attachList) {
			ArcAttach attach = new ArcAttach();
			String name = Utils.generateRandomName();
			attach.setAttachName(name + att.getAttachExt());
			try {

				Utils.saveAttachments(att.getAttachStream(), name);

				attach.setAttachOwner(Utils.findCurrentUser().getUserId());
				attach.setAttachDate(new Date());

				attach.setAttachSize((double) att.getAttachSize());
				attach.setAttachType(1);

				attach.setAttachCategory("FILE");
				myAttachs.add(attach);
			} catch (Exception e) {

				e.printStackTrace();
				myAttachs.clear();
			}
		}
		return myAttachs;
	}
	@Override
	public void execute() {
		contentHtml = "./model3.xhtml";
		page = "hrLetter.xhtml";
		super.execute();
	}

	@Override
	public void execute(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
		// setUpdateViewFlage(this.type );
		setHrLetter(updateViewFlage == 232);
		modelContentHtml = "hrLetter.xhtml";

		setBanks(dataAccessService.getAllBanks());
		if (Utils.findCurrentUser().getEmployeeNumber() != null && Utils.findCurrentUser().getEmployeeNumber() != 0) {
			setEmployeeInfo((HrsMasterFile) dataAccessService.findEntityById(HrsMasterFile.class,
					Utils.findCurrentUser().getEmployeeNumber()));
			if (employeeInfo != null) {
				// setBank((PayBank)
				// dataAccessService.findEntityById(PayBank.class,
				// employeeInfo.getBankId()));
				setEmpHistoricalInfo(dataAccessService.getEmployeeLastHistoryRecord(employeeInfo.getEmployeNumber()));

			}
		}

	}

	@Override
	public String saveAction(IDataAccessService dataAccessService) {
		List<ArcAttach> attachments = SaveAttachementsInServer(attachModelList);
		hrLetterRequest.setIbanNumber(iban);
		hrLetterRequest.setBankId(bankId);
		// hrLetterRequest.setAcceptStatus("N");
		hrLetterRequest.setRequestType(MailTypeEnum.SALAR.getValue());

		if (employeeInfo.getBankId() > 0 && employeeInfo.getBankId() != null) {
			if (attachModelList.size() >= 2) {
				try {
					dataAccessService.addBankAccountRequest(attachments, updateViewFlage, hrLetterRequest);
					hrLetterRequest = new HrLetterRequest();
					iban = "";
					bankId = null;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							Utils.loadMessagesFromFile("request.succes"), Utils.loadMessagesFromFile("request.succes")));
					MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("request.send"));
					return "mails";
				} catch (Exception e) {
					logger.error("Error in send convert salary Hr Letter Request" + e.getMessage());
					e.printStackTrace();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("error"), Utils.loadMessagesFromFile("error")));
					return " ";
				}
			}

			else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						Utils.loadMessagesFromFile("attach.iban.finish"), Utils.loadMessagesFromFile("attach.iban.finish")));
				return " ";
			}
		}
		else {
			if (attachModelList.size() >= 1) {
				try {
					dataAccessService.addBankAccountRequest(attachments, updateViewFlage, hrLetterRequest);
					hrLetterRequest = new HrLetterRequest();
					iban = "";
					bankId = null;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, Utils.loadMessagesFromFile("done"), Utils.loadMessagesFromFile("done")));

				} catch (Exception e) {
					logger.error("Error in send convert salary Hr Letter Request" + e.getMessage());
					e.printStackTrace();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("error"), Utils.loadMessagesFromFile("error")));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						Utils.loadMessagesFromFile("attach.iban.finish"), Utils.loadMessagesFromFile("attach.iban.finish")));
				return "";
			}
		}
		return null;
	}

	/**
	 * Print report
	 */
	public String printHrLetterReportAction() {
		String reportName = "/reports/hr_letter.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("record_id", recordId);
		parameters.put("compName", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	@Override
	public HrLetterRequest loadData(UserMailObj userMailClass) {
		isCurrUserSignAutorized(userMailClass);

		docType = userMailClass.getWrkType();
		authorized = isSignedAutorized;
		recordId = Integer.parseInt(userMailClass.getAppId());
		setHrLetterRequest(dataAccessService.getHrRequsetByArchrecorId(recordId));
		setBanks(dataAccessService.getAllBanks());
		wrkApplicationId = new WrkApplicationId(Integer.parseInt(userMailClass.getWrkId()), userMailClass.getStepId());
		setCurrentUserId(Utils.findCurrentUser().getUserId());
		setPriviledge(dataAccessService.getResponsibleId(MyConstants.HR_SALARY_RESPONSIBLE));
		if (currentUserId != dataAccessService.getResponsibleId(MyConstants.HR_SALARY_RESPONSIBLE))
			setUpdateBankInfoPanelFlag(false);

		if (userMailClass.getWrkType() == 232) {
			setHrLetter(true);
			setTransferSalary(false);
			reportName = MyConstants.REPORT_SALARY;
		} else {
			setTransferSalary(true);
			setHrLetter(false);
			reportName = MyConstants.REPORT_SALAR;
		}
		viewer = true;
		Integer fromUserId = dataAccessService.getUserIdFromWorkAppByIdAndStepId(recordId, 1);
		fromUser = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class, fromUserId);
		setViewerId(fromUserId);

		setEmployeeInfo(
				(HrsMasterFile) dataAccessService.findEntityById(HrsMasterFile.class, fromUser.getEmployeeNumber()));
		if (employeeInfo != null) {
			setBank((PayBank) dataAccessService.findEntityById(PayBank.class, employeeInfo.getBankId()));
			if (Utils.findCurrentUser().getEmployeeNumber() != null
					&& Utils.findCurrentUser().getEmployeeNumber() != 0) {
				setEmployeeInfo((HrsMasterFile) dataAccessService.findEntityById(HrsMasterFile.class,
						fromUser.getEmployeeNumber()));

				setEmpHistoricalInfo(dataAccessService.getEmployeeLastHistoryRecord(employeeInfo.getEmployeNumber()));
				setEmployeeInfo((HrsMasterFile) dataAccessService.findEntityById(HrsMasterFile.class,
						fromUser.getEmployeeNumber()));
			}

		}
		documentId = hrLetterRequest.getId();
		printDocument(reportName, documentId);
		return hrLetterRequest;
	}

	@Override
	public String acceptAction() {
		if (authorized) {

			try {

				if (currentUserId.equals(dataAccessService.getResponsibleId(MyConstants.HR_SALARY_RESPONSIBLE))
						&& wrkApplicationId.getStepId() == 1) {
					updateUserInfo();
				}
				dataAccessService.acceptActionFortransferSalary(recordId, docType, hrLetterRequest);

				MsgEntry.addAcceptFlashInfoMessage();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("error in accept Hr Transfer salary" + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, Utils.loadMessagesFromFile("error"), Utils.loadMessagesFromFile("error")));

			}
		} else {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,Utils.loadMessagesFromFile("error"), Utils.loadMessagesFromFile("error")));
		}

		return "mails";

	}

	@Override
	public String refuseAction() {
		try {
			hrLetterRequest.setAcceptStatus("N");
			WrkApplication newApplication = createNewWrkAppForRefuse();
			refuseModel(newApplication, hrLetterRequest);
			MsgEntry.addRefuseFlashInfoMessage();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in refuse Hr letter");
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	public String acceptActionForHr() {
		if (authorized) {

			try {
				dataAccessService.acceptActionforHrLetter(recordId, docType, hrLetterRequest);
				MsgEntry.addAcceptFlashInfoMessage();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("error in accept hr letter " + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("error"), Utils.loadMessagesFromFile("error")));

			}
		} else {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("error.privileges"), Utils.loadMessagesFromFile("error.privileges")));
		}

		return "mails";

	}
//
//	@Override
//	public Employer loadEmployerDataByRecordId(int selectedId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public void prepareData() {
		setBanks(dataAccessService.getAllBanks());
		setCurrentUserId(Utils.findCurrentUser().getUserId());
		setViewer(false);
		setEmployeeInfo((HrsMasterFile) dataAccessService.findEntityById(HrsMasterFile.class,
				Utils.findCurrentUser().getEmployeeNumber()));
		if (employeeInfo != null) {
			setBank((PayBank) dataAccessService.findEntityById(PayBank.class, employeeInfo.getBankId()));
			setEmpHistoricalInfo(dataAccessService.getEmployeeLastHistoryRecord(employeeInfo.getEmployeNumber()));

		}
	}

	// public void acceptActionFortransfeSalaryHrSpecialist() {
	// try {
	//
	// WrkApplication application =
	// dataAccessService.getWrkApplicationRecordById(recordId);
	// WrkApplicationId applicationId = new WrkApplicationId();
	// WrkApplication app = new WrkApplication(application);
	// applicationId.setApplicationId(recordId);
	// applicationId.setStepId(application.getId().getStepId() + 1);
	// app.setId(applicationId);
	// app.setToUserId(dataAccessService.getResponsibleId(MyConstants.HR_USER));
	// dataAccessService.updateWrkApplicationVisible(application.getId());
	// dataAccessService.createNewWrkApplication(recordId, app, "", false,
	// null);
	// dataAccessService.saveHrsSigns(recordId, updateViewFlage, false, "",
	// Utils.findCurrentUser().getUserId(), docType);
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage(FacesMessage.SEVERITY_INFO,
	// "#{msg['request.send']}",
	// "#{msg['request.send']}"));
	// } catch (Exception e) {
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage(FacesMessage.SEVERITY_ERROR, " #{msg['error']}",
	// "#{msg['error']}"));
	// }
	// }
	public boolean isTransferSalary() {
		return transferSalary;
	}

	public void setTransferSalary(boolean transferSalary) {
		this.transferSalary = transferSalary;
	}

	public boolean isHrLetter() {
		return hrLetter;
	}

	public void setHrLetter(boolean hrLetter) {
		this.hrLetter = hrLetter;
	}

	public Integer getUpdateViewFlage() {
		return updateViewFlage;
	}

	public void setUpdateViewFlage(Integer updateViewFlage) {
		this.updateViewFlage = updateViewFlage;
	}

	public HrsMasterFile getEmployeeInfo() {
		return employeeInfo;
	}

	public void setEmployeeInfo(HrsMasterFile employeeInfo) {
		this.employeeInfo = employeeInfo;
	}

	public PayBank getBank() {
		return bank;
	}

	public void setBank(PayBank bank) {
		this.bank = bank;
	}

	public List<PayBank> getBanks() {
		return banks;
	}

	public void setBanks(List<PayBank> banks) {
		this.banks = banks;
	}

	public WrkApplicationId getWrkApplicationId() {
		return wrkApplicationId;
	}

	public void setWrkApplicationId(WrkApplicationId wrkApplicationId) {
		this.wrkApplicationId = wrkApplicationId;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public HrLetterRequest getHrLetterRequest() {
		return hrLetterRequest;
	}

	public void setHrLetterRequest(HrLetterRequest hrLetterRequest) {
		this.hrLetterRequest = hrLetterRequest;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getDocType() {
		return docType;
	}

	public void setDocType(Integer docType) {
		this.docType = docType;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public HrsMasterFile getUpdatedEmployeeInfo() {
		return updatedEmployeeInfo;
	}

	public void setUpdatedEmployeeInfo(HrsMasterFile updatedEmployeeInfo) {
		this.updatedEmployeeInfo = updatedEmployeeInfo;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getPurposForHrLetter() {
		return purposForHrLetter;
	}

	public void setPurposForHrLetter(String purposForHrLetter) {
		this.purposForHrLetter = purposForHrLetter;
	}

	public String getHrLetterDestenation() {
		return hrLetterDestenation;
	}

	public void setHrLetterDestenation(String hrLetterDestenation) {
		this.hrLetterDestenation = hrLetterDestenation;
	}

	public List<ArcAttach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<ArcAttach> attachs) {
		this.attachs = attachs;
	}

	public List<AttachmentModel> getAttachModelList() {
		return attachModelList;
	}

	public void setAttachModelList(List<AttachmentModel> attachModelList) {
		this.attachModelList = attachModelList;
	}

	public ArcUsers getArcUser() {
		return arcUser;
	}

	public void setArcUser(ArcUsers arcUser) {
		this.arcUser = arcUser;
	}

	public Integer getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(Integer currentUserId) {
		this.currentUserId = currentUserId;
	}

	public ArcUsers getFromUser() {
		return fromUser;
	}

	public void setFromUser(ArcUsers fromUser) {
		this.fromUser = fromUser;
	}

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public boolean isViewer() {
		return viewer;
	}

	public void setViewer(boolean viewer) {
		this.viewer = viewer;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public HrsEmpHistorical getEmpHistoricalInfo() {
		return empHistoricalInfo;
	}

	public void setEmpHistoricalInfo(HrsEmpHistorical empHistoricalInfo) {
		this.empHistoricalInfo = empHistoricalInfo;
	}

	public boolean isUpdateBankInfoPanelFlag() {
		return updateBankInfoPanelFlag;
	}

	public void setUpdateBankInfoPanelFlag(boolean updateBankInfoPanelFlag) {
		this.updateBankInfoPanelFlag = updateBankInfoPanelFlag;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Integer getPriviledge() {
		return priviledge;
	}

	public void setPriviledge(Integer priviledge) {
		this.priviledge = priviledge;
	}

	@Override
	public Employer loadEmployerDataByRecordId(int arcRecordId) {
		// TODO Auto-generated method stub
		return null;
	}

	// accept for Transfer salary Hr specialist

	// public void acceptActionFortransfeSalaryPresident() {
	// try {
	//
	// WrkApplication application =
	// dataAccessService.getWrkApplicationRecordById(recordId);
	// WrkApplicationId applicationId = new WrkApplicationId();
	// WrkApplication app = new WrkApplication(application);
	// applicationId.setApplicationId(application.getId().getApplicationId());
	// applicationId.setStepId(application.getId().getStepId() + 1);
	// app.setId(applicationId);
	// app.setToUserId(dataAccessService.getResponsibleId(MyConstants.ARCHIVE_USER));
	// dataAccessService.updateWrkApplicationVisible(app.getId());
	// dataAccessService.createNewWrkApplication(recordId, app, "", false,
	// null);
	// dataAccessService.saveHrsSigns(recordId, updateViewFlage, true, "",
	// Utils.findCurrentUser().getUserId(), docType);
	// FacesContext.getCurrentInstance().addMessage(null, new
	// FacesMessage(FacesMessage.SEVERITY_INFO,
	// "#{msg['request.send']}", "#{msg['request.send']}"));
	// } catch (Exception e) {
	//
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage(FacesMessage.SEVERITY_ERROR, " #{msg['error']}",
	// "#{msg['error']}"));
	// }
	// }

	// public void acceptActionForHrLetterHrManager() {
	// try {
	//
	// WrkApplication application =
	// dataAccessService.getWrkApplicationRecordById(recordId);
	// WrkApplicationId applicationId = new WrkApplicationId();
	// WrkApplication app = new WrkApplication(application);
	// applicationId.setApplicationId(application.getId().getApplicationId());
	// applicationId.setStepId(application.getId().getStepId() + 1);
	// app.setId(applicationId);
	// app.setToUserId(dataAccessService.getResponsibleId(MyConstants.ARCHIVE_USER));
	// //
	// app.setToUserId(dataAccessServices.getResponsibleId(MyConstants.PRESIDENT));
	// // send copy
	// dataAccessService.updateWrkApplicationVisible(application.getId());
	// dataAccessService.createNewWrkApplication(recordId, app, "", false,
	// null);
	// dataAccessService.saveHrsSigns(recordId, updateViewFlage, true, "",
	// Utils.findCurrentUser().getUserId(), docType);
	//
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage(FacesMessage.SEVERITY_INFO, "#{msg['request.succes']} ",
	// "#{msg['request.succes']} "));
	// } catch (Exception e) {
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage(FacesMessage.SEVERITY_ERROR, " #{msg['error']}",
	// "#{msg['error']}"));
	// }
	//
	// }
	//
	//
}