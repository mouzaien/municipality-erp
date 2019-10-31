package com.bkeryah.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "NEW_SYS_USER_MENUS")
public class MainMenuUser implements Serializable {

	@EmbeddedId
	private MainMenuUserId mainMenuUserId;
	
	@ManyToOne
	@JoinColumn(name="USER_ID",referencedColumnName="USER_ID",insertable=false,updatable=false)
	ArcUsers user;
	
	@ManyToOne
	@JoinColumn(name="MAIN_MENU_ID",referencedColumnName="MENU_ID",insertable=false,updatable=false)
	MainMenu mainMenu;

	@Transient
	private List<SubMenu> subMenus;	
	
	public MainMenuUserId getMainMenuUserId() {
		return mainMenuUserId;
	}

	public void setMainMenuUserId(MainMenuUserId mainMenuUserId) {
		this.mainMenuUserId = mainMenuUserId;
	}

	public ArcUsers getUser() {
		return user;
	}

	public void setUser(ArcUsers user) {
		this.user = user;
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public List<SubMenu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<SubMenu> subMenus) {
		this.subMenus = subMenus;
	}
	
	

}
