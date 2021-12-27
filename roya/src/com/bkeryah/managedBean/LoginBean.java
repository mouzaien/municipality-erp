package com.bkeryah.managedBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

/**
 *
 * This class is for authentication and store it in the session of context
 *
 * @author Ghoyouth
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	// private static final Logger logger = LogManager.getLogger(LoginBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	@ManagedProperty(value = "#{authenticationManager}")
	private AuthenticationManager authenticationManager;
	private String userName;
	private String passWord;
	private String empUserName;
	private ArcUsers currentUser;
	private boolean technicalUser;

	/**
	 * Login user
	 * 
	 * @return
	 */
	public String login() {
		String outcome = "";
		try {
			Authentication result = null;
			if (this.userName.length() == 0 || this.passWord.length() == 0) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("required.fields"));
				return "";
			}
			ArcUsers uc = dataAccessService.loadUser(this.userName.toUpperCase(), this.passWord);
			Authentication request = new UsernamePasswordAuthenticationToken(uc.getLoginName().toUpperCase(),
					uc.getPassword());
			result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			if (result.isAuthenticated()) {
				if (uc.getUserId().equals(MyConstants.SUPPORT_USER_ID)) {// support
					RequestContext rc = RequestContext.getCurrentInstance();
					rc.execute("PF('dlg2').show();");
				} else {
					FacesContext context = FacesContext.getCurrentInstance();
					setCurrentUser(uc);
					context.getExternalContext().getSessionMap().put("user", uc);
					outcome = "success";
				}
				String val = dataAccessService.getPropertiesValueById(MyConstants.WAREHOUSE_MANAGER);
				Integer warehouseMgrId = (val == null) ? null : Integer.parseInt(val);
				if ((warehouseMgrId == uc.getUserId()) || (MyConstants.SUPPORT_USER_ID == uc.getUserId()))
					technicalUser = true;
			}
		} catch (BadCredentialsException e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.login"));
		}
		return outcome;
	}

	/**
	 * Login for support user
	 * 
	 * @return
	 */
	public String supportLogin() {
		try {
			Authentication result = null;
			if (empUserName == "") {
				setEmpUserName("AMER");
			}
			currentUser = dataAccessService.getUserByLoginName(empUserName);
			ArcUsers uc = dataAccessService.loadUser(this.userName.toUpperCase(), this.passWord);
			Authentication request2 = new UsernamePasswordAuthenticationToken(uc.getLoginName().toUpperCase(),
					uc.getPassword());
			result = authenticationManager.authenticate(request2);
			SecurityContextHolder.getContext().setAuthentication(result);

			Authentication request = new UsernamePasswordAuthenticationToken(currentUser.getLoginName().toUpperCase(),
					currentUser.getPassword());
			result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			HttpServletRequest request3 = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			HttpSession session = request3.getSession();
			session.setAttribute("user", currentUser);
			return "success";
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.username"));
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Logout
	 * 
	 * @return
	 */
	public String logout() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		SecurityContextHolder.clearContext();
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
		return "logout";
	}

	/**
	 * Getters and setters
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public String getEmpUserName() {
		return empUserName;
	}

	public void setEmpUserName(String empUserName) {
		this.empUserName = empUserName;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public boolean isTechnicalUser() {
		return technicalUser;
	}

	public void setTechnicalUser(boolean technicalUser) {
		this.technicalUser = technicalUser;
	}

}
