package com.bkeryah.managedBean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.context.ApplicationContext;

import com.bkeryah.entities.CityID;
import com.bkeryah.entities.EstablishmentId;
import com.bkeryah.licences.SpringContext;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

@FacesConverter("establishmentConverter")
public class EstablishIDConverter implements Converter {
	private static ApplicationContext applicationContext;
	private static IDataAccessService dataAccessService;
	static {
		applicationContext = SpringContext.getApplicationContext();
		dataAccessService = (IDataAccessService) applicationContext.getBean(IDataAccessService.class);

	}
		
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		EstablishmentId establishmentId = null;
		if (value != null && value.trim().length() > 0) {
			try {
				Integer userId = Integer.parseInt(value);
				establishmentId = dataAccessService.getEstablishIdBySchool(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return establishmentId;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		String val = null;
		if (object != null) {
			val = String.valueOf(((EstablishmentId) object).getSchool());
		}
		return val;
	}
}
