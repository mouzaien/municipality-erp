/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean.param;

import java.time.LocalTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.bkeryah.bean.SysTitleClass;
import com.bkeryah.entities.WrkJobs;
//import com.bkeryah.entities.WrkProfileSign;
import com.bkeryah.entities.WrkSection;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class UsersParamBean {
	private static Logger logger = Logger.getLogger(UsersParamBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<User> usersList;
	private List<User> filteredUsers;
	private User currUser = new User();

	private List<WrkSection> sectionsList;
	private List<WrkJobs> jobsList;
	private List<SysTitleClass> titlesList;
	private SysTitleClass title = new SysTitleClass();;

	private boolean addMode;

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	@PostConstruct
	public void init() {
		usersList = dataAccessService.getAllUsers();
	}

	public String getTime() {
		return LocalTime.now().toString();
	}

	public void handleClick(ActionEvent actionEvent) {
		RequestContext.getCurrentInstance().execute("handleMsg('invoked from bean listener');");
	}
	// ============================= Ajax Listeners =============================

	public void loadSectionListChangeEvent(AjaxBehaviorEvent ajaxBehaviorEvent) {
		sectionsList = this.dataAccessService.loadSectionsByDeptId(currUser.getDeptId());
	}

	public void loadJobsListChangeEvent(AjaxBehaviorEvent ajaxBehaviorEvent) {
		jobsList = this.dataAccessService.loadJobsBySectionId(currUser.getWrkSectionId());

	}

	// ========================== CRUD Operations =============================
	public void addNewUser() {
		addMode = true;
		currUser = new User();
		titlesList = this.dataAccessService.findAllSysTitles();

	}

	public void loadUsers(User userItem) {
		currUser = (User) dataAccessService.findEntityById(User.class, userItem.getUserId());
		logger.info("Username is?= " + currUser);
	}

	public void loadSelectedUser() {
		addMode = false;
		sectionsList = this.dataAccessService.loadSectionsByDeptId(currUser.getDeptId());
		jobsList = this.dataAccessService.loadJobsBySectionId(currUser.getWrkSectionId());
		titlesList = this.dataAccessService.findAllSysTitles();
	}

	public void saveUser() {
		try {
			currUser.setEmployeeName(currUser.getFirstName());
			if (currUser == null || currUser.getFirstName().isEmpty() || currUser.getEmployeeName().isEmpty()) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

			} else {
				currUser.setEnabled(true);
				currUser.setPassword(dataAccessService.getHashedPassword(currUser.getUsername(), currUser.getPassword()));
				dataAccessService.saveObject(currUser);
				usersList.add(currUser);
				currUser = new User();

				logger.info("add User: id: " + currUser.getUserId());
				System.out
						.println("User Added With Id: " + currUser.getUserId() + " Is Fetched Successfully From Database");
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}

		} catch (Exception e) {

		}
	}

	// return "";

	public void updateUser() {
		try {
			if (currUser != null) {
				currUser.setPassword(dataAccessService.getHashedPassword(currUser.getUsername(), currUser.getPassword()));
				dataAccessService.updateObject(currUser);
				currUser = new User();
				logger.info("Update User: id: " + currUser.getUserId());
			}
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void deleteUser() {
		try {
			dataAccessService.deleteObject(currUser);
			usersList.remove(currUser);
			System.out.println("User Deleted With Id: " + currUser.getUserId() + "  Successfully From Database");

			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("delete User: id: " + currUser.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("delete User: id: " + currUser.getUserId());
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public List<SysTitleClass> getTitlesList() {
		return titlesList;
	}

	public void setTitlesList(List<SysTitleClass> titlesList) {
		this.titlesList = titlesList;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}

	public List<WrkSection> getSectionsList() {
		return sectionsList;
	}

	public void setSectionsList(List<WrkSection> sectionsList) {
		this.sectionsList = sectionsList;
	}

	public List<WrkJobs> getJobsList() {
		return jobsList;
	}

	public void setJobsList(List<WrkJobs> jobsList) {
		this.jobsList = jobsList;
	}

//	public User getUser() {
//		return currUser;
//	}
//
//	public void setUser(User user) {
//		this.currUser = user;
//	}

	public SysTitleClass getTitle() {
		return title;
	}

	public User getCurrUser() {
		return currUser;
	}

	public void setCurrUser(User currUser) {
		this.currUser = currUser;
	}

	public void setTitle(SysTitleClass title) {
		this.title = title;
	}

	// ==========Old=====================================================
//		public void saveUser() {
//			try {
//				if (user != null) {
//					
//					usersList.add((User) dataAccessService.findEntityById(User.class, user.getUserId()));
//					user = new User();
//					logger.info("Save User: id: " + user.getUserId());
	//
////					dataAccessService.saveObject(mSelectedUser);
////					MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
//				logger.error("add car: id: " + user.getUserId());
//			}
//			// return "";
//		}
//	public void updateUserPassword() {
//		try {
//			dataAccessService.updateUserPasswordForSystem(user.getUsername(), user.getUserId(), user.getPassword());
//			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
//		}
//	}
//
//	public void updateProfile() {
//		try {
////			dataAccessService.updateSignatures(user.getWrkProfile(), user.getWrkProfileSign());
//			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
//		}
//	}
//
////    public String getNewPwdString() {
////        return newPwdString;
////    }

//	public void uploadSignature(FileUploadEvent e) {
//		try {
//			removeOldFile(Utils.loadMessagesFromFile("signature"));
//			addUploadedFile(e, Utils.loadMessagesFromFile("signature"));
//			UploadedFile file = e.getFile();
//			InputStream inputStream = file.getInputstream();
//			byte[] pBlob = IOUtil.toByteArray(inputStream);
////			Blob bild;
////			bild = new SerialBlob(pBlob);
////			InputStream stream = bild.getBinaryStream();
////			image = new DefaultStreamedContent(stream, "image/jpg");
////			user.getWrkProfileSign().setSign(pBlob);
//		} catch (Exception ex) {
//
//		}
//	}

//	private void removeOldFile(String loadMessagesFromFile) {
//		if (attachList == null || attachList.isEmpty()) {
//			for (AttachmentModel att : attachList) {
////				if(att.getLabel().equals(loadMessagesFromFile)){
////					attachList.remove(att);
////				}
//			}
//		}
//	}

//	public void uploadMarque(FileUploadEvent e) {
//		try {
//			addUploadedFile(e, Utils.loadMessagesFromFile("marque"));
//			UploadedFile file = e.getFile();
//			InputStream inputStream = file.getInputstream();
//			byte[] pBlob = IOUtil.toByteArray(inputStream);
////			Blob bild;
////			bild = new SerialBlob(pBlob);
////			InputStream stream = bild.getBinaryStream();
////			image = new DefaultStreamedContent(stream, "image/jpg");
//			// user.getWrkProfile().setSign2(pBlob);
//		} catch (Exception ex) {
//
//		}
//	}

//	public void uploadPresidentSignature(FileUploadEvent e) {
//		try {
//			addUploadedFile(e, Utils.loadMessagesFromFile("president.signature"));
//			UploadedFile file = e.getFile();
//			InputStream inputStream = file.getInputstream();
//			byte[] pBlob = IOUtil.toByteArray(inputStream);
////			Blob bild;
////			bild = new SerialBlob(pBlob);
////			InputStream stream = bild.getBinaryStream();
////			image = new DefaultStreamedContent(stream, "image/jpg");
//			// user.getWrkProfile().setSign3(pBlob);
//		} catch (Exception ex) {
//
//		}
//	}

//	private void addUploadedFile(FileUploadEvent event, String label) {
//
//		try {
//			AttachmentModel attach = new AttachmentModel();
//			if (event != null) {
//				attach.setAttachRealName(event.getFile().getFileName());
//				attach.setRealName(event.getFile().getFileName());
//				attach.setAttachSize(event.getFile().getSize());
//
//				attach.setAttachStream(event.getFile().getInputstream());
//				attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
//				attach.setRealName(Utils.generateRandomName() + "." + attach.getAttachExt());
//			}
////			attach.setLabel(label);
//			attachList.add(attach);
//
//		} catch (Exception e) {
//			FacesContext.getCurrentInstance().addMessage("lpcDlgMessage",
//					new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("no.upload"), ""));
//		}
//
//	}

}
