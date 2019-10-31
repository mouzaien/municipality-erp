/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import com.bkeryah.bean.UserClass;
import com.bkeryah.dao.DataAccessImpl;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author IbrahimDarwiesh
 */
@FacesConverter("userClassConverter")
public class UserClassConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        UserClass userClass = null;
        if (value != null && value.trim().length() > 0) {
            try {
                userClass =  new DataAccessImpl().findEmployeeById(Integer.parseInt(value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return userClass;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        String val =null;
        if (object != null){
            val = String.valueOf(((UserClass) object).getVuser_id());
        }
        
        
        return val;
    }

}
