package com.bkeryah.penalties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.springframework.context.ApplicationContext;

import com.bkeryah.entities.CodesFines;
import com.bkeryah.licences.SpringContext;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

@FacesConverter("fineTypeConverter")
public class FineTypeConverter implements Converter {
	private Map<Integer, FineFilterType>  fineFilterTypes=new HashMap<>();

	@PostConstruct
	public void init() {
		fineFilterTypes.put(1,new FineFilterType(1, Utils.loadMessagesFromFile("mahali")));// L
		fineFilterTypes.put(2,new FineFilterType(2, Utils.loadMessagesFromFile("chakssi")));// P
		fineFilterTypes.put(3,new FineFilterType(3, Utils.loadMessagesFromFile("charika")));// C
	}

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				FineFilterType fineType = fineFilterTypes.get(value);
				return fineType;
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return ((Integer) object).toString();
		} else {
			return null;
		}
	}
}
