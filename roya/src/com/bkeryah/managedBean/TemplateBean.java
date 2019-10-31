/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import com.bkeryah.bean.Main_menu_class;
import com.bkeryah.bean.Sub_menu_class;
import com.bkeryah.bean.UserClass;
import com.bkeryah.dao.DataAccess;
import com.bkeryah.dao.DataAccessImpl;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author IbrahimDarwiesh
 */
@ManagedBean
@SessionScoped
public class TemplateBean implements Serializable {

	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private MenuModel model;
	private List<SelectItem> menus;
	private String ApplicationHeader;
	private ArcUsers currentUser;
	private String currentUserName;

	/**
	 * Creates a new instance of TemplateBean
	 */
	@PostConstruct
	public void init() {
		// if (Utils.findCurrentUser().getVuser_id() == 118) {
		// // da.navigateToAccessDenied();
		// }

		// if (this.dataAccess == null) {
		// dataAccess = new DataAccessImpl();
		// }

		if (Utils.findCurrentUser() == null)
			return;

	}

	public void loadMenu() {
		model = new DefaultMenuModel();
		List<Main_menu_class> x = dataAccessService.getMenuByUser(findCurrentUser().getUserId());
		for (Main_menu_class main_menu_class : x) {
			DefaultSubMenu firstSubmenu = new DefaultSubMenu(main_menu_class.getMenuTxt());

			List<Sub_menu_class> xx = dataAccessService.findSubMenus(findCurrentUser().getUserId(),
					main_menu_class.getMenuId());
			for (Sub_menu_class sub_menu_class : xx) {
				DefaultMenuItem item = new DefaultMenuItem(sub_menu_class.getVsub_menu_txt());
				item.setCommand(sub_menu_class.getVsub_menu_url());
				item.setIcon(sub_menu_class.getVsub_menu_icon());
				firstSubmenu.addElement(item);
			}
			model.addElement(firstSubmenu);
		}
	}

	public TemplateBean() {
		// if (this.dataAccess == null) {
		// dataAccess = new DataAccessImpl();
		// }
		//
		// model = new DefaultMenuModel();
		// List<Main_menu_class> x =
		// dataAccess.getMenuByUser(findCurrentUser().getVuser_id());
		// for (Main_menu_class main_menu_class : x) {
		// DefaultSubMenu firstSubmenu = new
		// DefaultSubMenu(main_menu_class.getMenuTxt());
		//
		// List<Sub_menu_class> xx =
		// dataAccess.findSubMenus(findCurrentUser().getVuser_id(),
		// main_menu_class.getMenuId());
		// for (Sub_menu_class sub_menu_class : xx) {
		// DefaultMenuItem item = new
		// DefaultMenuItem(sub_menu_class.getVsub_menu_txt());
		// item.setUrl(sub_menu_class.getVsub_menu_url());
		// item.setIcon(sub_menu_class.getVsub_menu_icon());
		// firstSubmenu.addElement(item);
		// }
		// model.addElement(firstSubmenu);
		//
		// }

	}

	public MenuModel getModel() {
		return model;
	}

	public String getApplicationHeader() {
		ApplicationHeader = this.dataAccessService.findSystemProperty("APPLICATION_HEADER_TXT");
		return ApplicationHeader;
	}

	public void setApplicationHeader(String ApplicationHeader) {
		this.ApplicationHeader = ApplicationHeader;
	}

	public ArcUsers findCurrentUser() {

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession appsession = request.getSession(true);
		currentUser = (ArcUsers) appsession.getAttribute("user");
		return currentUser;
	}

    public String logOutbtn(ActionEvent action) {
    	FacesContext facesContext = FacesContext.getCurrentInstance();
    	HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
    	System.out.println("session logOutbtn " +session.getId());
    	session.invalidate();
		

		
		return "logout";
	}

	public String goToHome() {
		return "Home";
		// try {
		// ExternalContext ec =
		// FacesContext.getCurrentInstance().getExternalContext();
		// ec.redirect(ec.getRequestContextPath() +
		// "/faces/pages/Home.xhtml?faces-redirect=false");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public String goHome(ActionEvent actionEvent) {
		return "success2";
	}

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

}
