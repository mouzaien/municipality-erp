/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.dao;

/**
 *
 * @author darwiesh
 */
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import utilities.Utils;

public class LoggedInCheck{// implements PhaseListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public void beforePhase(PhaseEvent event) {
    }

    public void afterPhase(PhaseEvent event) {
        FacesContext fc = event.getFacesContext();

        // Check to see if they are on the login page.
        boolean loginPage= fc.getViewRoot().getViewId().lastIndexOf("login") > -1 ? true : false;
        if (!loginPage && !loggedIn()) {
            NavigationHandler nh = fc.getApplication().getNavigationHandler();
            nh.handleNavigation(fc, null, "logout");
        }
    }

    private boolean loggedIn() {
        boolean islogged = false;
        try {
            if (Utils.findCurrentUser() != null) {
                islogged = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return islogged;
    }
}
