/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author idarwiesh
 */
@ManagedBean
@ViewScoped
public class HomeBean implements Serializable{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Creates a new instance of HomeBean
     */
    public HomeBean() {
    	System.out.println("in home bean **********************************************");
    }

//    public void goInbox(ActionEvent actionEvent) {
//        new DataAccessImpl().NavigateToInbox();
//    }
    
    public String goInbox() {
    	try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

            ec.redirect(ec.getRequestContextPath() + "/pages/userMail.xhtml");
    		
//    		ConfigurableNavigationHandler handler=(ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();      
//            handler.performNavigation("userMail");
            
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR!! 019 : " + e.getMessage());
            //showErrMessage("ERROR!! 017 : " + e.getMessage());
        }
        return "userMail";
    }
    
    public String goEmpServe() {
//         try {
//            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//
//            ec.redirect(ec.getRequestContextPath() + "/pages/EmpServ.xhtml");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    	return "EmpServ";
    }
    
    public String goEmpSettings() {
//         try {
//            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//
//            ec.redirect(ec.getRequestContextPath() + "/faces/pages/EmployeeSettings.xhtml?faces-redirect=false");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    	return "EmployeeSettings";
    }
    
    //
}
