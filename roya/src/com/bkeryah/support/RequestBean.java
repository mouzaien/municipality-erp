package com.bkeryah.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.fng.beans.FngMonitoringBean;
import com.bkeryah.managedBean.LoadingDataBean;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.support.entities.RequestStep;
import com.bkeryah.support.entities.UserRequest;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class RequestBean {
	private enum requestStatus {
		all, open, progress, resolved
	}

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	protected static final Logger logger = Logger.getLogger(FngMonitoringBean.class);
	private String subject;
	private String description;
	private List<UserRequest> userRequests = new ArrayList<>();
	private List<UserRequest> selectedUserRequests;
	private UserRequest selectedUserRequest;
	private List<UserRequest> filtredUserRequests;
	private String selectStatus;
	@ManagedProperty(value = "#{loadingDataBean}")
	private LoadingDataBean loadingDataBean;
	private Map<Integer, User> usersMap = new HashMap<>();
	private List<User> usersDeptMap = new ArrayList<>();
	private ArcUsers currentUser = Utils.findCurrentUser();
	private Integer currUserId = currentUser.getUserId();
	private Integer toUserId = currUserId;
	private boolean roleIsAdmin;
	private boolean roleIsTecSupport;
	private Map<Integer, User> employers;
	private Integer searchStatus = requestStatus.open.ordinal();
	private List<Integer> devSections = Arrays.asList(1);

	{
		if (devSections.contains(currentUser.getWrkSectionId()))
			roleIsTecSupport = true;
	}

	public Integer getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(Integer searchStatus) {
		this.searchStatus = searchStatus;
	}

	public String getSelectStatus() {
		return selectStatus;
	}

	public boolean isRoleIsTecSupport() {
		return roleIsTecSupport;
	}

	public void setRoleIsTecSupport(boolean roleIsTecSupport) {
		this.roleIsTecSupport = roleIsTecSupport;
	}

	public List<User> getUsersDeptMap() {
		return usersDeptMap;
	}

	public void setUsersDeptMap(List<User> usersDeptMap) {
		this.usersDeptMap = usersDeptMap;
	}

	public void setSelectStatus(String selectStatus) {
		this.selectStatus = selectStatus;
	}

	public boolean isRoleIsAdmin() {
		return roleIsAdmin;
	}

	public void setRoleIsAdmin(boolean roleIsAdmin) {
		this.roleIsAdmin = roleIsAdmin;
	}

	public Map<Integer, User> getUsersMap() {
		return usersMap;
	}

	public void setUsersMap(Map<Integer, User> usersMap) {
		this.usersMap = usersMap;
	}

	public UserRequest getSelectedUserRequest() {
		return selectedUserRequest;
	}

	public void setSelectedUserRequest(UserRequest selectedUserRequest) {
		this.selectedUserRequest = selectedUserRequest;
	}

	public List<UserRequest> getSelectedUserRequests() {
		return selectedUserRequests;
	}

	public void setSelectedUserRequests(List<UserRequest> selectedUserRequests) {
		this.selectedUserRequests = selectedUserRequests;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		if (toUserId.equals(-1))
			this.toUserId = MyConstants.DEV_SEC_BOSS;
		else if (toUserId.equals(-2))
			this.toUserId = MyConstants.NETWORK_SEC_BOSS;
		else if (toUserId.equals(-3))
			this.toUserId = MyConstants.IT_SEC_BOSS;
		else
			this.toUserId = toUserId;
	}

	public LoadingDataBean getLoadingDataBean() {
		return loadingDataBean;
	}

	public void setLoadingDataBean(LoadingDataBean loadingDataBean) {
		this.loadingDataBean = loadingDataBean;
	}

	public List<UserRequest> getFiltredUserRequests() {
		return filtredUserRequests;
	}

	public void setFiltredUserRequests(List<UserRequest> filtredUserRequests) {
		this.filtredUserRequests = filtredUserRequests;
	}

	@PostConstruct
	public void loadRequests() {
		getUserRole();
		employers = loadingDataBean.getUsersMap();
		usersDeptMap = loadingDataBean.getUsersSectionMap(currentUser.getWrkSectionId());
		for (User user : usersDeptMap) {
			usersMap.put(user.getUserId(), user);
		}
		loadUserReqs();

	}

	private void loadUserReqs() {
		userRequests.clear();
		if (roleIsAdmin)
			userRequests = dataAccessService.loadUserRequests(searchStatus, -1, roleIsTecSupport);
		else
			userRequests = dataAccessService.loadUserRequests(searchStatus, currUserId, roleIsTecSupport);
		for (UserRequest userRequest : userRequests) {
			List<RequestStep> requestSteps = userRequest.getRequestSteps();
			if ((requestSteps != null) && (!requestSteps.isEmpty()))
				userRequest.setNote(requestSteps.get(requestSteps.size() - 1).getDescription());
			User fromUser = employers.get(userRequest.getUserId());
			if (fromUser != null)
				userRequest.setUserName(fromUser.getEmployeeName());
			User currUser = employers.get(userRequest.getCurrUserId());
			if (currUser != null)
				userRequest.setCurrUserName(currUser.getEmployeeName());
		}
	}

	private void getUserRole() {
		switch (currUserId) {
		case MyConstants.DEV_SEC_BOSS:
		case MyConstants.NETWORK_SEC_BOSS:
		case MyConstants.NETWORK_SEC_BOSS2:
		case MyConstants.IT_SEC_BOSS:
		case MyConstants.IT_USERREQUEST_SUPP:
		case MyConstants.IT_USERREQUEST_SUPP2:
			roleIsAdmin = true;
			break;
		}
	}

	public List<UserRequest> getUserRequests() {
		return userRequests;
	}

	public void setUserRequests(List<UserRequest> userRequests) {
		this.userRequests = userRequests;
	}

	public String add() {
		try {
			Date createDate = new Date();
			UserRequest userReq = addNewUserRequest(createDate);
			RequestStep requestStep = new RequestStep();
			requestStep.setRequestId(userReq.getId());
			requestStep.setStepId(1);
			requestStep.setDescription(userReq.getDescription());
			requestStep.setStatus(userReq.getStatus());
			requestStep.setCreateDate(userReq.getCreateDate());
			requestStep.setUserId(userReq.getUserId());
			// dataAccessService.saveObject(requestStep);
			dataAccessService.addUserRequest(userReq, requestStep);
			reset();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			return "";
		} catch (Exception e) {
			logger.error("add( UserRequest " + e.getCause());
		}
		return null;
	}

	public void save(AjaxBehaviorEvent event) {
		try {
			if (selectedUserRequest != null) {
				Date createDate = new Date();
				RequestStep requestStep = new RequestStep();
				requestStep.setRequestId(selectedUserRequest.getId());
				requestStep.setUserId(toUserId);
				requestStep.setCreateDate(createDate);
				requestStep.setStatus(selectedUserRequest.getStatus());
				int currStepId = selectedUserRequest.getCurrStepId() + 1;
				requestStep.setStepId(currStepId);
				requestStep.setDescription(description);
				selectedUserRequest.setCurrUserId(toUserId);
				selectedUserRequest.setCurrStepId(currStepId);
				dataAccessService.save(requestStep);
				dataAccessService.updateObject(selectedUserRequest);
				reset();
				loadUserReqs();
			} else
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("user.requests.select"));
			// return "";
		} catch (Exception e) {
			logger.error("save UserRequest " + selectedUserRequest.getId() + " " + e.getCause());
		}
		// return null;
	}

	private RequestStep copy(RequestStep oldReqUserStep) {
		try {
			RequestStep reqUserStep = new RequestStep();
			reqUserStep.setRequestId(oldReqUserStep.getRequestId());
			reqUserStep.setStatus(oldReqUserStep.getStatus());
			reqUserStep.setUserId(toUserId);
			return reqUserStep;
		} catch (Exception e) {
			logger.error("copy UserRequest " + oldReqUserStep.getId() + " " + e.getCause());
		}
		return oldReqUserStep;

	}

	private UserRequest addNewUserRequest(Date createDate) {
		UserRequest userRequest = new UserRequest();
		userRequest.setSubject(subject);
		userRequest.setDescription(description);
		userRequest.setCreateDate(createDate);
		userRequest.setStatus(requestStatus.open.ordinal());
		userRequest.setUserDept(currentUser.getDeptId());
		userRequest.setUserId(currentUser.getUserId());
		userRequest.setCurrUserId(MyConstants.SUPPORT_SEC_BOSS);
		userRequest.setCurrStepId(1);
		// Integer userReqId = (Integer)
		// dataAccessService.saveObject(userRequest);
		// userRequest.setId(userReqId);
		return userRequest;
	}

	public void onRowSelect(SelectEvent event) {
		selectedUserRequest = (UserRequest) (event.getObject());
	}

	public void reloadReqs(AjaxBehaviorEvent event) {
		loadUserReqs();
	}

	public void onRowUnselect(UnselectEvent event) {
		selectedUserRequest = null;
	}

	public void updateReqStatus() {

	}

	private void reset() {
		subject = null;
		description = null;
		selectedUserRequest = null;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

}
