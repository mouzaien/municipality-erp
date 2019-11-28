package com.bkeryah.hr.managedBeans;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.mails.Factory;
import com.bkeryah.mails.FactoryOfFactories;
import com.bkeryah.mails.MailExecutor;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.mails.MailsFactory;
import com.bkeryah.mails.TrainingExecutor;
import com.bkeryah.mails.VacationExecutor;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@SessionScoped
public class MainManagedBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String content = "mails_view.xhtml";
	public String modelContent;
	private List<WrkPurpose> wrkPurposes;

	private boolean higriMode;
	private String missionDate;
	private Date missionDate_G;

	// private Boolean haveManagerId;
	public String getModelContent() {
		return modelContent;
	}

	public void setModelContent(String modelContent) {
		this.modelContent = modelContent;
	}

	// public Boolean getHaveManagerId() {
	// haveManagerId = !(Utils.findCurrentUser().getMgrId() == null);
	// return haveManagerId;
	// }
	//
	// public void setHaveManagerId(Boolean haveManagerId) {
	// this.haveManagerId = haveManagerId;
	// }

	public void initMails() {

		MailTypeEnum wrkType = MailTypeEnum.MAILS;
		MailsFactory factory = MailsFactory.getInstance();
		mailExecutor = factory.getExecutor(wrkType);

		mailExecutor.execute(dataAccessService);
		content = mailExecutor.getContentHtml();
	}

	public String goToCreatComment() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		session.setAttribute("p", "personalComment");

		return "PersonalComment";

	}

	public void goToCreatInternalMemo() {

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("internalMemo.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	public Employer employer;

	public VacationExecutor employeeServiceBean;
	private MailExecutor mailExecutor;

	public VacationExecutor getEmployeeServiceBean() {
		return employeeServiceBean;
	}

	public void setEmployeeServiceBean(VacationExecutor employeeServiceBean) {
		this.employeeServiceBean = employeeServiceBean;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String updateContent(String model) {

		MailTypeEnum wrkType = MailTypeEnum.getValue(model);
		MailsFactory factory = MailsFactory.getInstance();
		mailExecutor = factory.getExecutor(wrkType);
		if ((mailExecutor.getEmployer() == null) || (mailExecutor.getEmployer().getId() == 0))
			mailExecutor.setEmployer(dataAccessService.loadEmployerByUser(Utils.findCurrentUser()));
		mailExecutor.setModeScreen(MyConstants.INSERT_MODE);
		mailExecutor.execute(dataAccessService);
		mailExecutor.prepareData();
		content = mailExecutor.getModelContentHtml();
		return mailExecutor.page;
		// redirect(mailExecutor.page);
	}

	public void redirect(String page) {
		try {
			String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
			if (viewId.equals("/pages/mails.xhtml") || viewId.equals("/pages/dashboard.xhtml")
					|| viewId.equals("/pages/profilePage.xhtml"))
				FacesContext.getCurrentInstance().getExternalContext().redirect("models/contentModel.xhtml");
			else if (!viewId.equals("/pages/models/contentModel.xhtml"))
				FacesContext.getCurrentInstance().getExternalContext().redirect(page);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String returnTomails() {
		return "mails";
	}

	public String returnToDashboard() {
		return "dashboard";
	}

	public String exchangeRequestNavi() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("arcRecord", null);
		return "exchangeRequest";

	}

	public String projectExtractNavi() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("arcRecord", null);
		return "projectExtract";

	}

	public String editMail(final UserMailObj mailObject) {

		int model = mailObject.getWrkType();

		MailTypeEnum wrkType = MailTypeEnum.getValue(model);

		FactoryOfFactories mainFactory = FactoryOfFactories.getInstance();
		Factory factory = mainFactory.getFactory(wrkType);
		if (wrkPurposes == null)
			setWrkPurposes(dataAccessService.getAllPurposes());
		if (factory instanceof MailsFactory) {

			mailExecutor = ((MailsFactory) factory).getExecutor(wrkType);
			if (mailExecutor != null) {
				mailExecutor.setModeScreen(MyConstants.EDIT_MODE);
				mailExecutor.execute(dataAccessService);
				modelContent = mailExecutor.getModelContentHtml();

				mailExecutor.setHasComment(false);

				loadData(mailObject);
				return "models/mail_definition.xhtml";
			}

		} else {
			return "/royaTest/modelTemplates/services.xhtml";
		}
		return null;
	}

	public String saveAction() {
		return mailExecutor.saveAction(dataAccessService);
	}

	public void sortDates() {
		try {
			if (!higriMode) {

				missionDate = Utils.grigDatesConvert(missionDate_G);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// mailExecutor.set
	}

	public void loadData(UserMailObj userMailClass) {
		mailExecutor.loadEmployerByRecordId(Integer.parseInt(userMailClass.getAppId()));
		mailExecutor.loadData(userMailClass);
	}

	public MailExecutor getMailExecutor() {
		return mailExecutor;
	}

	public void setMailExecutor(MailExecutor mailExecutor) {
		this.mailExecutor = mailExecutor;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public String getMissionDate() {
		return missionDate;
	}

	public void setMissionDate(String missionDate) {
		this.missionDate = missionDate;
	}

	public Date getMissionDate_G() {
		return missionDate_G;
	}

	public void setMissionDate_G(Date missionDate_G) {
		this.missionDate_G = missionDate_G;
	}

}
