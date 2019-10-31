/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.bkeryah.bean.Main_menu_class;
import com.bkeryah.bean.Sub_menu_class;
import com.bkeryah.bean.UserClass;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

/**
 *
 * @author idarwiesh
 */
@ManagedBean
@ViewScoped
public class UserMenuBean implements Serializable {

//    DataAccess dataAccess = new DataAccessImpl();
	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
    Map<String, String> allUsers = new LinkedHashMap<String, String>();
    private int SelectedUserId;
    private String SelectUserTxt;
    private List<Main_menu_class> usrMainmenus;
    private List<Main_menu_class> allMainmenus;
    private Main_menu_class selectedMainMenu;
    private List<Sub_menu_class> allSubmenusByMain;
    private List<Sub_menu_class> usrSubmenusByMain;
    Map<String, String> AvalMainMenus = new LinkedHashMap<String, String>();
    Map<String, String> AvalSubMenus = new LinkedHashMap<String, String>();
    private int selectedAvalMainMenu;
    private int selectedAvalSubMenu;
    private Sub_menu_class selectSubMenu;

    /**
     * Creates a new instance of UserMenuBean
     */
    public UserMenuBean() {
    }

    public Map<String, String> getAllUsers() {
        this.allUsers.clear();
        List<UserClass> list = this.dataAccessService.findAllUsers();
        Map<String, String> returnMap = new LinkedHashMap<String, String>();
        for (UserClass item : list) {
            returnMap.put(item.getVusers_real_name(), String.valueOf(item.getVuser_id()));
        }
        System.out.println("MapSize" + returnMap.size());
        return returnMap;
    }

    public void setAllUsers(Map<String, String> allUsers) {
        this.allUsers = allUsers;
    }

    public int getSelectedUserId() {
        return SelectedUserId;
    }

    public void setSelectedUserId(int SelectedUserId) {
        this.SelectedUserId = SelectedUserId;
    }

    public String getSelectUserTxt() {
        return SelectUserTxt;
    }

    public void setSelectUserTxt(String SelectUserTxt) {
        this.SelectUserTxt = SelectUserTxt;
    }

    public List<Main_menu_class> getUsrMainmenus() {
        return this.dataAccessService.getMenuByUser(this.SelectedUserId);
    }

    public void setUsrMainmenus(List<Main_menu_class> usrMainmenus) {
        this.usrMainmenus = usrMainmenus;
    }

    public List<Main_menu_class> getAllMainmenus() {
        return this.dataAccessService.findAllMainMenu();
    }

    public void setAllMainmenus(List<Main_menu_class> allMainmenus) {
        this.allMainmenus = allMainmenus;
    }

    public List<Sub_menu_class> getAllSubmenusByMain() {
        return allSubmenusByMain;
    }

    public void setAllSubmenusByMain(List<Sub_menu_class> allSubmenusByMain) {
        this.allSubmenusByMain = allSubmenusByMain;
    }

    public List<Sub_menu_class> getUsrSubmenusByMain() {
        return usrSubmenusByMain;
    }

    public void setUsrSubmenusByMain(List<Sub_menu_class> usrSubmenusByMain) {
        this.usrSubmenusByMain = usrSubmenusByMain;
    }

    public Main_menu_class getSelectedMainMenu() {
        return selectedMainMenu;
    }

    public void setSelectedMainMenu(Main_menu_class selectedMainMenu) {
        this.selectedMainMenu = selectedMainMenu;
    }

    public Map<String, String> getAvalMainMenus() {
        return AvalMainMenus;
    }

    public void setAvalMainMenus(Map<String, String> AvalMainMenus) {
        this.AvalMainMenus = AvalMainMenus;
    }

    public Map<String, String> getAvalSubMenus() {
        return AvalSubMenus;
    }

    public void setAvalSubMenus(Map<String, String> AvalSubMenus) {
        this.AvalSubMenus = AvalSubMenus;
    }

    public int getSelectedAvalMainMenu() {
        return selectedAvalMainMenu;
    }

    public void setSelectedAvalMainMenu(int selectedAvalMainMenu) {
        this.selectedAvalMainMenu = selectedAvalMainMenu;
    }

    public int getSelectedAvalSubMenu() {
        return selectedAvalSubMenu;
    }

    public void setSelectedAvalSubMenu(int selectedAvalSubMenu) {
        this.selectedAvalSubMenu = selectedAvalSubMenu;
    }

    public Sub_menu_class getSelectSubMenu() {
        return selectSubMenu;
    }

    public void setSelectSubMenu(Sub_menu_class selectSubMenu) {
        this.selectSubMenu = selectSubMenu;
    }

    /**
     * *******************FUNCTIONS _METHODSSS ****************************
     */
    public void LoadEmployeeDetails(ActionEvent actionEvent) {
        if (this.SelectedUserId == 0) {
            MsgEntry.addErrorMessage(MsgEntry.ERROR_CHOOSE_EMPLOYEE);
        } else {
            this.AvalMainMenus.clear();
            this.AvalMainMenus = LoadAvaliableMainMenus(this.SelectedUserId);
            this.usrMainmenus = this.dataAccessService.getMenuByUser(this.SelectedUserId);
            this.allSubmenusByMain = null;
            this.SelectUserTxt = " بيانات الموظف رقم " + this.SelectedUserId + "-" + this.dataAccessService.getEmployeeRealName(this.SelectedUserId);
        }
    }

    public void MainMenudtRowSelect(SelectEvent selectEvent) {
        Main_menu_class main_menu_class = (Main_menu_class) selectEvent.getObject();
        System.err.println(main_menu_class.getMenuTxt());

        this.allSubmenusByMain = this.dataAccessService.findExistedSubMenuByMainMenu(this.SelectedUserId, this.selectedMainMenu.getMenuId());
        this.AvalSubMenus = LoadAvaliableSubMenus(this.SelectedUserId, this.selectedMainMenu.getMenuId());
    }

    public void removeMainMenuBtnAction(ActionEvent actionEvent) {
        this.dataAccessService.removeMenufromUser(this.SelectedUserId, this.selectedMainMenu.getMenuId());
        this.selectedMainMenu = null;
        this.usrMainmenus = this.dataAccessService.getMenuByUser(this.SelectedUserId);
        this.allSubmenusByMain = null;
        MsgEntry.addInfoMessage(MsgEntry.SUCCESS_DELETE_MENU);

        //System.err.println(""+this.selectedMainMenu.getMenuTxt()+"---"+this.selectedMainMenu.getMenuId());
    }

    public void removesubMenuBtnAction(ActionEvent actionEvent) {
        this.dataAccessService.removeSubMenufromUser(this.SelectedUserId, this.selectSubMenu.getVsub_menu_id());
        this.selectSubMenu = null;
        this.allSubmenusByMain = this.dataAccessService.findExistedSubMenuByMainMenu(this.SelectedUserId, this.selectedMainMenu.getMenuId());
        this.AvalSubMenus = LoadAvaliableSubMenus(this.SelectedUserId, this.selectedMainMenu.getMenuId());
        MsgEntry.addInfoMessage(MsgEntry.SUCCESS_DELETE_MENU);

        //System.err.println(""+this.selectedMainMenu.getMenuTxt()+"---"+this.selectedMainMenu.getMenuId());
    }

    public Map<String, String> LoadAvaliableMainMenus(int UserId) {

        List<Main_menu_class> list = this.dataAccessService.findAllAvaliableMainMenu(UserId);
        Map<String, String> returnMap = new LinkedHashMap<String, String>();
        for (Main_menu_class item : list) {
            returnMap.put(item.getMenuTxt(), String.valueOf(item.getMenuId()));
        }
        return returnMap;
    }

    public Map<String, String> LoadAvaliableSubMenus(int UserId, int MainMenuId) {

        List<Sub_menu_class> list = this.dataAccessService.findAvaliableSubMenuByMainMenu(UserId, MainMenuId);
        Map<String, String> returnMap = new LinkedHashMap<String, String>();
        for (Sub_menu_class item : list) {
            returnMap.put(item.getVsub_menu_txt(), String.valueOf(item.getVsub_menu_id()));
        }
        return returnMap;
    }

    public void addMainMenuBtnAction(ActionEvent actionEvent) {
        if (this.selectedAvalMainMenu == 0 || this.SelectedUserId == 0) {
            MsgEntry.addErrorMessage(MsgEntry.ERROR_SELECT_MENU);
        } else {
            this.dataAccessService.addMenuToUser(this.SelectedUserId, this.selectedAvalMainMenu);
            LoadAvaliableMainMenus(this.SelectedUserId);
            this.usrMainmenus = this.dataAccessService.getMenuByUser(this.SelectedUserId);
            this.allSubmenusByMain = null;
        }

    }

    public void addsubMenuBtnAction(ActionEvent actionEvent) {

        if (this.selectedAvalSubMenu == 0 || this.SelectedUserId == 0) {
            MsgEntry.addErrorMessage(MsgEntry.ERROR_SELECT_MENU);
        } else {
            this.dataAccessService.addSubMenuToUser(this.SelectedUserId, this.selectedAvalSubMenu);
            this.allSubmenusByMain = this.dataAccessService.findExistedSubMenuByMainMenu(this.SelectedUserId, this.selectedMainMenu.getMenuId());
            this.AvalSubMenus = LoadAvaliableSubMenus(this.SelectedUserId, this.selectedMainMenu.getMenuId());
            MsgEntry.addInfoMessage(MsgEntry.SUCCESS_ADD_SUBMENU);
        }
    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
}
