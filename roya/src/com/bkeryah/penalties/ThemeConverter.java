package com.bkeryah.penalties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.springframework.context.ApplicationContext;

import com.bkeryah.licences.SpringContext;
import com.bkeryah.service.IDataAccessService;

@FacesConverter("themeConverter")
public class ThemeConverter implements Converter {
	private static ApplicationContext applicationContext;
	private IDataAccessService dataAccessService;
	{
		applicationContext = SpringContext.getApplicationContext();
		dataAccessService = (IDataAccessService) applicationContext.getBean(IDataAccessService.class);

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				List<LicTrdMasterFile> trdMasterFileList = dataAccessService.getTrdMasterFileList();
				Map<Integer, LicTrdMasterFile> licTrdMasterFileList2 = new HashMap<Integer, LicTrdMasterFile>();
				for (LicTrdMasterFile trdMasterFile : trdMasterFileList) {
					licTrdMasterFileList2.put(trdMasterFile.getId(), trdMasterFile);
				}
				LicTrdMasterFile licTrdMasterFile = licTrdMasterFileList2.get(Integer.parseInt(value));
				return licTrdMasterFile;
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
			return String.valueOf(((LicTrdMasterFile) object).getId());
		} else {
			return null;
		}
	}
}
