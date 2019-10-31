package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="NEW_SYS_USER_SUBMENUS")
public class SubMenuUser implements Serializable {

	@EmbeddedId
	private SubMenuUserId subMenuUserId;

	@ManyToOne
	@JoinColumn(name="USER_ID",referencedColumnName="USER_ID",insertable=false,updatable=false)
	ArcUsers user;
	

	@ManyToOne
	@JoinColumn(name="NEW_SUB_MENU_ID",referencedColumnName="SUB_MENU_ID",insertable=false,updatable=false)
	SubMenu subMenu;


	public SubMenuUserId getSubMenuUserId() {
		return subMenuUserId;
	}


	public void setSubMenuUserId(SubMenuUserId subMenuUserId) {
		this.subMenuUserId = subMenuUserId;
	}


	public ArcUsers getUser() {
		return user;
	}


	public void setUser(ArcUsers user) {
		this.user = user;
	}


	public SubMenu getSubMenu() {
		return subMenu;
	}


	public void setSubMenu(SubMenu subMenu) {
		this.subMenu = subMenu;
	}
	
	
	
}
