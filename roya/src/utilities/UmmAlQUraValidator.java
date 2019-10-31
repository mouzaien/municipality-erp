package utilities;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.context.ApplicationContext;

import com.bkeryah.dao.DataAccess;
import com.bkeryah.dao.DataAccessImpl;
import com.bkeryah.licences.SpringContext;
import com.bkeryah.service.IDataAccessService;

@FacesValidator("utilities.UmmAlQUraValidator")
public class UmmAlQUraValidator implements Validator {
	private static ApplicationContext applicationContext;
	private static DataAccess dataAccessService;

	static {
		applicationContext = SpringContext.getApplicationContext();
		dataAccessService = (DataAccess) applicationContext.getBean(DataAccess.class);

	}

	@Override
	public void validate(FacesContext arg0, UIComponent component, Object value) throws ValidatorException {
		isHigriDateValid(value);
	}

	public static boolean isHigriDateValid(Object value) {
		String result = "1";
		if (value != null) {
			result = dataAccessService.checkUmmAlQuraDate(value.toString());
		}
		if (result.equals("0")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MsgEntry.ERROR_INVALID_DATE, "");
			throw new ValidatorException(msg);
		}
		return true;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		UmmAlQUraValidator.applicationContext = applicationContext;
	}

	public static DataAccess getDataAccessService() {
		return dataAccessService;
	}

	public static void setDataAccessService(DataAccess dataAccessService) {
		UmmAlQUraValidator.dataAccessService = dataAccessService;
	}

}
