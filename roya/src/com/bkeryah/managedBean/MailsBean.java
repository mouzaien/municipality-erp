package com.bkeryah.managedBean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.dao.ICommonDao;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.hr.managedBeans.MainManagedBean;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;
import utilities.primefacesUtils.LazyMailDataModel;

/**
 * Load mails for inbox and outbox
 * @author Ghoyouth
 *
 */
@ManagedBean
@ViewScoped
public class MailsBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	@ManagedProperty("#{flash}")
	private Flash flash;
	@ManagedProperty(value = "#{mainManagedBean}")
	private MainManagedBean mainManagedBean;
	private List<UserMailObj> inboxMails;
	private List<UserMailObj> outBoxList;
	private List<UserMailObj> filteredOutBoxList;
	private UserMailObj selectedInbox;
	private String comingFrom;
	private Integer selectedTypeMail;
	private int inboxCount;
	private ArcUsers currentUser;
	private List<UserMailObj> allInboxList = new ArrayList<>();
	private List<UserMailObj> checkedMailsList = new ArrayList<>();
	private String yearOfSearch;
	private int yearMonths;
	private Integer usertoId;
	private List<User> employeesList = new ArrayList<>();
	private LazyDataModel<UserMailObj> mailsModel;
	private LazyDataModel<UserMailObj> outBoxModel;

	@PostConstruct
	public void init() {
		comingFrom = "0";
		setYearMonths(1);
		setYearOfSearch(HijriCalendarUtil.findCurrentYear());
		currentUser = Utils.findCurrentUser();
		if (currentUser == null)
			return;
		loadOutBox();
		getViewEmployers();
	}

	/**
	 * Load inbox
	 */
	@Async
	public void loaMails() {
		allInboxList = dataAccessService.findEmployeeInboxNew(null, null, currentUser.getUserId());
		inboxMails = allInboxList;
		mailsModel = new LazyMailDataModel(inboxMails);
		if (!CollectionUtils.isEmpty(inboxMails)) {
			inboxCount = inboxMails.size();
		}
		filteredOutBoxList = null;
	}

	/**
	 * Search in outbox
	 * @return
	 */
	public void outBoxMailsSearch() {
		if(StringUtils.isEmpty(yearOfSearch))
			return;
		outBoxList = dataAccessService.findEmployeeOutbox(Utils.findCurrentUser().getUserId(), yearOfSearch,
				yearMonths);
		outBoxModel = new LazyMailDataModel(outBoxList);
		filteredOutBoxList = null;
	}

	/**
	 * 
	 * @param mailClass
	 */
	public void checkRecord(UserMailObj mailClass) {
		if (mailClass.isChecked()) {
			checkedMailsList.add(mailClass);
			mailClass.setWrkColor("3");// for select background color
		} else {
			checkedMailsList.remove(mailClass);
		}
	}

	/**
	 * Load outbox mail
	 */
	private void loadOutBox() {
		setYearMonths(getCurrentYearPart());
		setYearOfSearch(HijriCalendarUtil.findCurrentYear());
		if (outBoxList == null || outBoxList.size() == 0) {
			outBoxList = dataAccessService.findEmployeeOutbox(Utils.findCurrentUser().getUserId(), yearOfSearch,
					yearMonths);
		}
		outBoxModel = new LazyMailDataModel(outBoxList);
	}

	/**
	 * Load new mails
	 * @param e
	 */
	public void refreshMails(AjaxBehaviorEvent e) {
		inboxMails = dataAccessService.findEmployeeInboxNew(null, null, currentUser.getUserId());
	}

	/**
	 * Archive the selected records
	 * @return
	 */
	public void sendRecordListToArchive(AjaxBehaviorEvent event) {
		if ((checkedMailsList == null) || (checkedMailsList.isEmpty())) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.select.record"));
			return;
		}
		try {
			dataAccessService.sendRecordListToArchive(checkedMailsList);
			for (UserMailObj cheakedMail : checkedMailsList) {
				allInboxList.remove(cheakedMail);
//				setRemoveRecordFlag(true);
			}
			outBoxList = dataAccessService.findEmployeeOutbox(Utils.findCurrentUser().getUserId(), yearOfSearch,
					yearMonths);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.archive.record"));
			checkedMailsList = new ArrayList<>();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Transfert transaction
	 * @param event
	 */
	public void sendRecordListTo(AjaxBehaviorEvent event) {
		if ((usertoId == null) || (checkedMailsList.isEmpty())) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.select.record"));
			return;
		}
		try {
			dataAccessService.sendListRecordTo(checkedMailsList, usertoId);
			for (UserMailObj cheakedMail : checkedMailsList) {
				allInboxList.remove(cheakedMail);
			}
			outBoxList = dataAccessService.findEmployeeOutbox(Utils.findCurrentUser().getUserId(), yearOfSearch,
					yearMonths);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.sent.records"));
			checkedMailsList = new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Filter list
	 */
	public void sHowMails() {
		switch (selectedTypeMail) {
		case 1:
			showAllMails();
			break;
		case 2:
			showUnReadMails();
			break;
		case 3:
			showReadMails();
			break;
		case 4:
			showTodayMails();
			break;
		case 5:
			showImportantMails();
			break;
		default:
			showAllMails();
			break;
		}
	}

	/**
	 * Get all mails
	 */
	public void showAllMails() {
		inboxMails = allInboxList;
	}

	/**
	 * Filter unread mail
	 */
	public void showUnReadMails() {
		inboxMails = allInboxList.stream().filter(mail -> mail.IsRead == 0).collect(Collectors.toList());
	}

	/**
	 * Filter read mail
	 */
	public void showReadMails() {
		inboxMails = allInboxList.stream().filter(mail -> mail.IsRead == 1).collect(Collectors.toList());
	}

	/**
	 * Filter today mails
	 */
	public void showTodayMails() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		inboxMails = allInboxList.stream().filter(mail -> sdf.format(new Date()).equals(mail.WrkGDate)).collect(Collectors.toList());

	}

	/**
	 * Filter important mails
	 */
	public void showImportantMails() {
		inboxMails = allInboxList.stream().filter(mail -> (mail.WrkColor != null) && (mail.getWrkColor().trim().equals("1"))).collect(Collectors.toList());
	}

	/**
	 * Consult details of selected items
	 * @param selectedMail
	 */
	public void viewInboxRow(SelectEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("arcRecord", selectedInbox.AppId);
		System.out.println("appId "+selectedInbox.AppId);
		session.setAttribute("type", selectedInbox.getWrkType());
		session.setAttribute("selectedMail", selectedInbox);
		String page = mainManagedBean.editMail(selectedInbox);
		dataAccessService.MakeItRead(String.valueOf(selectedInbox.StepId), selectedInbox.WrkId);
		flash.put("selectedMail", selectedInbox);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get selected year quarter
	 * @return
	 */
	private int getCurrentYearPart() {
		int currentMonth = Integer.parseInt(HijriCalendarUtil.findCurrentMonth());
		switch (currentMonth) {
		case 1:
		case 2:
		case 3:
			return 1;
		case 4:
		case 5:
		case 6:
			return 2;
		case 7:
		case 8:
		case 9:
			return 3;
		case 10:
		case 11:
		case 12:
			return 4;
		default:
			return 1;
		}
	}

	/**
	 * Load correspondent Users
	 */
	private void getViewEmployers() {
		if (currentUser.getWrkRoleId() == 1 || currentUser.getUserId() == MyConstants.SUPPORT_USER_ID
				|| currentUser.getUserId() == MyConstants.TT_DIRECTOR_ID)
			employeesList = dataAccessService.getAllUsers();
		else if (currentUser.getWrkRoleId() == 2) {
			employeesList = dataAccessService.getAllEmployeesInDept(currentUser.getDeptId());
		}
	}

	/**
	 * Consult selected mail
	 * @param selectedOutBoxMail
	 */
	public void viewOutBoxRow(SelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedOutBoxMail", selectedInbox);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("models/outBox_definition.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Getters and setters
	 */

	public int getYearMonths() {
		return yearMonths;
	}

	public void setYearMonths(int yearMonths) {
		this.yearMonths = yearMonths;
	}

	public String getYearOfSearch() {
		return yearOfSearch;
	}

	public void setYearOfSearch(String yearOfSearch) {
		this.yearOfSearch = yearOfSearch;
	}

	public List<UserMailObj> getOutBoxList() {
		return outBoxList;
	}

	public void setOutBoxList(List<UserMailObj> outBoxList) {
		this.outBoxList = outBoxList;
	}

	public UserMailObj getSelectedInbox() {
		return selectedInbox;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setSelectedInbox(UserMailObj selectedInbox) {
		this.selectedInbox = selectedInbox;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public MainManagedBean getMainManagedBean() {
		return mainManagedBean;
	}

	public void setMainManagedBean(MainManagedBean mainManagedBean) {
		this.mainManagedBean = mainManagedBean;
	}

	public int getInboxCount() {
		return inboxCount;
	}

	public List<UserMailObj> getCheckedMailsList() {
		return checkedMailsList;
	}

	public void setCheckedMailsList(List<UserMailObj> checkedMailsList) {
		this.checkedMailsList = checkedMailsList;
	}

	public List<UserMailObj> getAllInboxList() {
		return allInboxList;
	}

	public void setAllInboxList(List<UserMailObj> allInboxList) {
		this.allInboxList = allInboxList;
	}

	public void setInboxCount(int inboxCount) {
		this.inboxCount = inboxCount;
	}

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public String archiveView() {
		return "archive";
	}

	public List<UserMailObj> getInboxMails() {
		return inboxMails;
	}

	public void setInboxMails(List<UserMailObj> inboxMails) {
		this.inboxMails = inboxMails;
	}

	private List<UserMailObj> filteredInboxList;

	public List<UserMailObj> getFilteredInboxList() {
		return filteredInboxList;
	}

	public void setFilteredInboxList(List<UserMailObj> filteredInboxList) {
		this.filteredInboxList = filteredInboxList;
	}

	public List<UserMailObj> getFilteredOutBoxList() {
		return filteredOutBoxList;
	}

	public void setFilteredOutBoxList(List<UserMailObj> filteredOutBoxList) {
		this.filteredOutBoxList = filteredOutBoxList;
	}

	public Integer getUsertoId() {
		return usertoId;
	}

	public void setUsertoId(Integer usertoId) {
		this.usertoId = usertoId;
	}

	public List<User> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<User> employeesList) {
		this.employeesList = employeesList;
	}

	public String getComingFrom() {
		return comingFrom;
	}

	public void setComingFrom(String comingFrom) {
		this.comingFrom = comingFrom;
	}

	public Integer getSelectedTypeMail() {
		return selectedTypeMail;
	}

	public void setSelectedTypeMail(Integer selectedTypeMail) {
		this.selectedTypeMail = selectedTypeMail;
	}

	public Flash getFlash() {
		return flash;
	}

	public void setFlash(Flash flash) {
		this.flash = flash;
	}

	public LazyDataModel<UserMailObj> getMailsModel() {
		mailsModel = new LazyMailDataModel(inboxMails);
		return mailsModel;
	}

	public void setMailsModel(LazyDataModel<UserMailObj> mailsModel) {
		this.mailsModel = mailsModel;
	}

	public LazyDataModel<UserMailObj> getOutBoxModel() {
		return outBoxModel;
	}

	public void setOutBoxModel(LazyDataModel<UserMailObj> outBoxModel) {
		this.outBoxModel = outBoxModel;
	}
}
