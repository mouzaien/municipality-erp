package com.bkeryah.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "NEW_SYS_MAIN_MENU")
public class MainMenu implements Serializable{

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "MENU_ID")
	private Integer menuId;
	@Column(name = "MAIN_MENU_TXT")
	private String menuName;

	@Transient
	List<SubMenu> submenuList;
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public List<SubMenu> getSubmenuList() {
		return submenuList;
	}

	public void setSubmenuList(List<SubMenu> submenuList) {
		this.submenuList = submenuList;
	}
	
	

}
