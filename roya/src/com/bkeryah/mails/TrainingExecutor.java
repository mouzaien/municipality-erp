package com.bkeryah.mails;

import java.text.MessageFormat;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.util.StringUtils;
import org.springframework.webflow.action.SetAction;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsEmployeeTraining;
import com.bkeryah.entities.HrsTrainingMandate;
import com.bkeryah.entities.HrsTrainingPlace;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import net.sf.jasperreports.engine.util.LocalizedMessageProvider;
import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

/**
 * This class is used for training's model
 * @author habib
 *
 */

@ViewScoped
public class TrainingExecutor extends MailExecutor<HrsEmployeeTraining> {

	private HrsEmployeeTraining employeeTraining = null;
	private boolean acceptVisible;
	private List<HrsTrainingPlace> placeTrainingList;
	private HrsTrainingMandate trainingMandate;
	private String trainingPlace;
	
	public TrainingExecutor(MailTypeEnum mailType) {
		super(mailType);
	}

	@Override
	public void execute(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
		acceptVisible = false;
		modelContentHtml = "training_model.xhtml";
		contentModel = this;
		
	}
	
	/**
	 *	Calculate the return date
	 */
	public void checkdate() {
		employeeTraining.setStartDate(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("includeform:startDate"));
		if ((employeeTraining.getStartDate() != null) && (!employeeTraining.getStartDate().trim().equals(""))
				&& (employeeTraining.getPeriod() != null) && (employeeTraining.getPeriod() > 0)) {
			employeeTraining.setEndDate(HijriCalendarUtil.addDaysToHijriDate(employeeTraining.getStartDate(),
					employeeTraining.getPeriod() - 1));

			employeeTraining.setRewardMoney((double) ((employeeTraining.getPeriod() * employer.getBasicSalary()) / 30));

		}
	}

	/**
	 * Listener to load ticket price and mandate of the vacation
	 */
	public void loadVacationMoney() {
		if (placeTrainingList != null) {
			for (HrsTrainingPlace place : placeTrainingList) {
				if (place.getId() == employeeTraining.getPlace()) {
					employeeTraining.setTicketMoney(place.getTicketMoney());
					employeeTraining.setMandateMoney((double) (trainingMandate.getAmount() * place.getMandatePeriod()));
					return;
				}
			}
		}
	}

	@Override
	public String saveAction(IDataAccessService dataAccessService) {
		if (!checkFields())
			return "";
		try {
			String title = MessageFormat.format(Utils.loadMessagesFromFile("training.title"), employer.getName(),
					HijriCalendarUtil.findCurrentHijriDate());
			String date = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("includeform:startDate");
			employeeTraining.setStartDate(date);
			dataAccessService.saveTraining(employeeTraining, title, MyConstants.TRAINING_TYPE, false);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("request.send"));
			resetFields();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	/**
	 * Reset the screen's data
	 */
	private void resetFields() {
		employeeTraining = new HrsEmployeeTraining();
	}

	/**
	 * Validate screen's fields
	 * 
	 * @return
	 */
	private boolean checkFields() {
		boolean valid = true;
		// Check required fields
		if (!checkRequiredFields())
			valid = false;
		
		return valid;
	}

	/**
	 * Validate screen's fields
	 * 
	 * @return
	 */
	private boolean checkRequiredFields() {
		boolean valid = true;
		String date = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("includeform:startDate");
		if (!StringUtils.isEmpty(date)) {
			employeeTraining.setStartDate(date);
		} else {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("enter.date"));
			return false;
		}
		if ((employeeTraining.getPeriod() == 0) || ((employeeTraining.getPeriod() + "").trim().equals(""))) {
			MsgEntry.addErrorAspectToComponent("trainingPeriod");
			valid = false;
		}
		if ((employeeTraining.getStartDate() == null) || (employeeTraining.getStartDate().trim().equals(""))) {
			MsgEntry.addErrorAspectToComponent("startDate");
			valid = false;
		}
		if (!valid)
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("required.fields"));
		return valid;
	}
	
	@Override
	public HrsEmployeeTraining loadData(UserMailObj userMailClass) {
		isCurrUserSignAutorized(userMailClass);
		wrkApplicationId = new WrkApplicationId(Integer.parseInt(userMailClass.getWrkId()), userMailClass.getStepId());
		int appId = Integer.parseInt(userMailClass.getAppId());
		employeeTraining = this.dataAccessService.findTrainingByArcRecordId(appId);
		trainingPlace = dataAccessService.loadTrainingPlaceName(employeeTraining.getPlace());
		return employeeTraining;
	}

	@Override
	public String acceptAction() {
		try {
			dataAccessService.acceptTraining(wrkApplicationId, employeeTraining);
			MsgEntry.addAcceptFlashInfoMessage();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	@Override
	public String refuseAction() {
		try {
			employeeTraining.setTrainingStatus(MyConstants.NO);

			/**** begin create new application **/
			WrkApplication newApplication = createNewWrkAppForRefuse();
			/*** end **/
			refuseModel(newApplication, employeeTraining);
			MsgEntry.addRefuseFlashInfoMessage();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	@Override
	public Employer loadEmployerDataByRecordId(int arcRecordId) {
		ArcUsers user = dataAccessService.findUserFromHrsTrain(arcRecordId);
		Employer employer = dataAccessService.loadEmployerByUser(user);
		return employer;
	}

	@Override
	public void prepareData() {
		placeTrainingList = dataAccessService.loadTrainingPlaces();
		employeeTraining = new HrsEmployeeTraining();
		employeeTraining.setEmployeeNumber(employer.getEmpNB());
		trainingMandate = dataAccessService.loadTrainingMandate(10);//TODO employer.getRank());
	}

	public boolean isAcceptRendered() {
		return true;
	}

	public boolean isAcceptVisible() {
		return acceptVisible;
	}

	public void setAcceptVisible(boolean acceptVisible) {
		this.acceptVisible = acceptVisible;
	}

	public HrsEmployeeTraining getEmployeeTraining() {
		return employeeTraining;
	}

	public void setEmployeeTraining(HrsEmployeeTraining employeeTraining) {
		this.employeeTraining = employeeTraining;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<HrsTrainingPlace> getPlaceTrainingList() {
		return placeTrainingList;
	}

	public void setPlaceTrainingList(List<HrsTrainingPlace> placeTrainingList) {
		this.placeTrainingList = placeTrainingList;
	}

	public HrsTrainingMandate getTrainingMandate() {
		return trainingMandate;
	}

	public void setTrainingMandate(HrsTrainingMandate trainingMandate) {
		this.trainingMandate = trainingMandate;
	}

	public WrkApplicationId getWrkApplicationId() {
		return wrkApplicationId;
	}

	public void setWrkApplicationId(WrkApplicationId wrkApplicationId) {
		this.wrkApplicationId = wrkApplicationId;
	}

	public String getTrainingPlace() {
		return trainingPlace;
	}

	public void setTrainingPlace(String trainingPlace) {
		this.trainingPlace = trainingPlace;
	}

}
