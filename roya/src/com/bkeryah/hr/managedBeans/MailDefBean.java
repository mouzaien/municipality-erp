package com.bkeryah.hr.managedBeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.bkeryah.bean.UserMailObj;

@ManagedBean
@SessionScoped
public class MailDefBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserMailObj selectedItem;
	private UserMailObj xx;

	public UserMailObj getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(UserMailObj selectedItem) {
		xx = selectedItem;
		this.selectedItem = selectedItem;
	}

	public UserMailObj getXx() {
		return xx;
	}

	public void setXx(UserMailObj xx) {
		this.xx = xx;
	}
}
