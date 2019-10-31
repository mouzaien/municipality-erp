package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "NEW_SYS_SUB_MENU")
public class SubMenu implements Serializable{

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "SUB_MENU_ID")
	private Integer subMenuId;
	@Column(name = "SUB_MENU_TXT")
	private String subMenuName;
	@Column(name = "SUB_MENU_ICON")
	private String subMenuIcon;
	@Column(name = "SUB_MENU_COMMAND")
	private String subMenucommand;
	@Column(name = "SUB_MENU_URL")
	private String subMenuUrl;
	@Column(name = "MAIN_MENU_ID")
	private Integer mainMenuId;

	public Integer getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(Integer subMenuId) {
		this.subMenuId = subMenuId;
	}

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}

	public String getSubMenuIcon() {
		return subMenuIcon;
	}

	public void setSubMenuIcon(String subMenuIcon) {
		this.subMenuIcon = subMenuIcon;
	}

	public String getSubMenucommand() {
		return subMenucommand;
	}

	public void setSubMenucommand(String subMenucommand) {
		this.subMenucommand = subMenucommand;
	}

	public String getSubMenuUrl() {
		return subMenuUrl;
	}

	public void setSubMenuUrl(String subMenuUrl) {
		this.subMenuUrl = subMenuUrl;
	}

	public Integer getMainMenuId() {
		return mainMenuId;
	}

	public void setMainMenuId(Integer mainMenuId) {
		this.mainMenuId = mainMenuId;
	}

}
