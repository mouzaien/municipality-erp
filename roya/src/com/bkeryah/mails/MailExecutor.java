package com.bkeryah.mails;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

public abstract class MailExecutor<T>{
	protected IDataAccessService dataAccessService;
	public static String page = "contentModel.xhtml";
	/**
	 * 
	 */
	protected static final Logger logger = Logger.getLogger(MailExecutor.class);
	protected WrkApplicationId wrkApplicationId;

	protected String contentHtml = "./models/edit_form.xhtml";
	protected String modelContentHtml = "./error.xhtml";
	protected Employer employer;
	private boolean readOnly = false;
	protected boolean isModel = true;
	protected boolean hasComment = false;
	protected boolean rendred = false;
	protected MailTypeEnum typeId;
	protected boolean isSignedAutorized = false;
	protected String urlFile;
	protected String reportName;
	protected int documentId;
	protected String reason = Utils.loadMessagesFromFile("cancel.opeartion");
	private boolean canPrint;
	private boolean modelStatus = false;
	private String wrkAppComment;
	private String applicationPurpose;
	private int modeScreen;

	public boolean isModelStatus() {
		return modelStatus;
	}

	public void setModelStatus(boolean modelStatus) {
		this.modelStatus = modelStatus;
	}

	public boolean isSignedAutorized() {
		return isSignedAutorized;
	}

	public void setSignedAutorized(boolean isSignedAutorized) {
		this.isSignedAutorized = isSignedAutorized;
	}

	public MailExecutor(MailTypeEnum mailType) {
		this.typeId = mailType;
		logger.info("constract executor  " + mailType);
	}

	public MailTypeEnum getTypeId() {
		return typeId;
	}

	public void setTypeId(MailTypeEnum typeId) {
		this.typeId = typeId;
	}

	public boolean isRendred() {
		return rendred;
	}

	public void setRendred(boolean rendred) {
		this.rendred = rendred;
	}

	public boolean getHasComment() {
		return hasComment;
	}

	public void setHasComment(boolean hasComment) {
		this.hasComment = hasComment;
	}

	public boolean getIsModel() {
		return isModel;
	}

	public void setIsModel(boolean isModel) {
		this.isModel = isModel;
	}

	public String getUrlFile() {

		return urlFile;
	}

	public void setUrlFile(String urlFile) {
		this.urlFile = urlFile;
	}

	/**
	 * Redirection
	 */
	public void execute() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the dataAccessService of the factory class
	 * 
	 * @param dataAccessService
	 */
	public abstract void execute(IDataAccessService dataAccessService);

	/**
	 * Prepare the data to show in screen
	 * 
	 */
	public abstract void prepareData();

	/**
	 * Save the model
	 * 
	 * @param dataAccessService
	 * @return
	 */
	public abstract String saveAction(IDataAccessService dataAccessService);

	/**
	 * Accept decision of model
	 * 
	 * @param dataAccessService
	 */
	public abstract String acceptAction();

	/**
	 * Refuse decision of model
	 * 
	 * @param dataAccessService
	 * @return
	 */
	public abstract String refuseAction();

	/**
	 * Load the employer having the model
	 * 
	 * @param arcRecordId
	 */
	public void loadEmployerByRecordId(int arcRecordId) {
		employer = loadEmployerDataByRecordId(arcRecordId);

	}

	/**
	 * Load employer by arcRecordId
	 * 
	 * @param args
	 * @return
	 */
	public abstract Employer loadEmployerDataByRecordId(int arcRecordId);

	/**
	 * Load model
	 * 
	 * @param idT
	 * @return
	 */
	public abstract T loadData(UserMailObj userMailClass);

	public String getModelContentHtml() {
		return modelContentHtml;
	}

	public void setModelContentHtml(String modelContentHtml) {
		this.modelContentHtml = modelContentHtml;
	}

	protected Object contentModel;

	public Object getContentModel() {
		return contentModel;
	}

	public void setContentModel(Object contentModel) {
		this.contentModel = contentModel;
	}

	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	/**
	 * @param userMailClass
	 */
	public boolean isCurrUserSignAutorized(UserMailObj userMailClass) {
		setReadOnly(true);
		try {
			final int FIRST_STEP = 1;
			int fromOfModelStep = 0;
			if ((typeId.getValue() == MailTypeEnum.VACATION.getValue()) && (dataAccessService
					.getWrkCountSigned(Integer.parseInt(userMailClass.getAppId())) == FIRST_STEP)) {
				fromOfModelStep = dataAccessService.findUserFromHrsSigns(Integer.parseInt(userMailClass.getWrkId()))
						.getUserId();
			} else {
				fromOfModelStep = this.dataAccessService.getNextScenarioUserId(typeId.getValue(),
						Integer.parseInt(userMailClass.getWrkId()), Integer.parseInt(userMailClass.getAppId()), 1);
			}
			if (fromOfModelStep == 0) {
				canPrint = true;
				return false;
			}
			Integer currentUserId = Utils.findCurrentUser().getUserId();
			if (currentUserId == fromOfModelStep)
				isSignedAutorized = true;
			else {
				isSignedAutorized = false;
				System.out.println("current user is :" + currentUserId
						+ " and autorized user id that must have signed before is " + fromOfModelStep);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSignedAutorized;
	}

	/**
	 * Refuse the model
	 * 
	 * @param userTo
	 * @param wrkApplicationId
	 * @param t
	 * @return
	 */
	public String refuseModel(WrkApplication newApplication, T t) {
		try {

			applicationPurpose = "0";
			if (dataAccessService.refuseModel(newApplication, wrkApplicationId, t,
					Utils.loadMessagesFromFile("reject.request.for") + " " + wrkAppComment,
					Integer.parseInt(applicationPurpose.trim())))

				return "mails";
		} catch (Exception e) {
			logger.error("drb fe el catch bta3 el  refuse" + e.getMessage());
			return "";
		}
		return "";
	}

	public void printDocument(String reportName, int documentId) {

		urlFile = dataAccessService.printDocument(reportName, documentId, "PVACSEQ");

	}

	public WrkApplication createNewWrkAppForRefuse() {
		try {
			WrkApplication oldWrkApp = (WrkApplication) dataAccessService.findWrkApplicationById(wrkApplicationId);
			int userTo = dataAccessService.getUserIdFromWorkAppByIdAndStepId(oldWrkApp.getArcRecordId(), 1);
			/**** begin create new application **/
			WrkApplication newApplication = new WrkApplication(oldWrkApp);
			// newApplication.getId().setStepId(newApplication.getId().getStepId()
			// + 1);
			newApplication.setToUserId(userTo);
			newApplication.setApplicationUsercomment(reason);
			return newApplication;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean isCanPrint() {
		return canPrint;
	}

	public void setCanPrint(boolean canPrint) {
		this.canPrint = canPrint;
	}

	public String getWrkAppComment() {
		return wrkAppComment;
	}

	public void setWrkAppComment(String wrkAppComment) {
		this.wrkAppComment = wrkAppComment;
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public int getModeScreen() {
		return modeScreen;
	}

	public void setModeScreen(int modeScreen) {
		this.modeScreen = modeScreen;
	}
}
