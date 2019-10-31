/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author IbrahimDarwiesh
 */
@ManagedBean
@RequestScoped
public class TimeOutBean {

    /**
     * Creates a new instance of TimeOutBean
     */
    public TimeOutBean() {
    }

    public void redirect(ActionEvent actionEvent) {

        FacesContext context = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(context, null, "/faces/login.xhtml?faces-redirect=true");
    }

}
