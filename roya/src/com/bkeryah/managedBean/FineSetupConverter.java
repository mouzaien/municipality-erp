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
import com.bkeryah.penalties.ReqFinesSetup;
import com.bkeryah.service.IDataAccessService;

@FacesConverter("fineSetupConverter")
public class FineSetupConverter implements Converter {
	private static ApplicationContext applicationContext;
	private static IDataAccessService dataAccessService;
	static {
		applicationContext = SpringContext.getApplicationContext();
		dataAccessService = (IDataAccessService) applicationContext.getBean(IDataAccessService.class);

	}

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		ReqFinesSetup fine = null;
		if (value != null && value.trim().length() > 0) {
			try {
				Integer fineId = Integer.parseInt(value);
				fine = (ReqFinesSetup) dataAccessService.findEntityById(ReqFinesSetup.class, fineId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return fine;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		String val = null;
		if (object != null) {
			val = String.valueOf(((ReqFinesSetup) object).getId());
		}
		return val;
	}

}
