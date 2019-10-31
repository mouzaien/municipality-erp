/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.context.ApplicationContext;
import com.bkeryah.licences.SpringContext;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

@FacesConverter("userConverter")
public class UserConverter implements Converter {
	private static ApplicationContext applicationContext;
	private static IDataAccessService dataAccessService;
	static {
		applicationContext = SpringContext.getApplicationContext();
		dataAccessService = (IDataAccessService) applicationContext.getBean(IDataAccessService.class);

	}

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		User User = null;
		if (value != null && value.trim().length() > 0) {
			try {
				Integer userId = Integer.parseInt(value);
				User = dataAccessService.getUserById(userId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return User;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		String val = null;
		if (object != null) {
			val = String.valueOf(((User) object).getUserId());
		}
		return val;
	}

}
