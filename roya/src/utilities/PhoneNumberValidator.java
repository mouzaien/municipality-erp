package utilities;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("utilities.PhoneNumberValidator")
public class PhoneNumberValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent component, Object value) throws ValidatorException {
		isPhoneValid(value);

	}

	public static boolean  isPhoneValid(Object value) {
		Long phoneNumber;
		try {
			phoneNumber = Long.parseLong(value.toString());
		} catch (NumberFormatException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("error.phone.number"), "");
			throw new ValidatorException(msg);
		}
		if ((("" + phoneNumber).length() != 12) || (!("" + phoneNumber).startsWith("966"))) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("error.phone.number"), "");
			throw new ValidatorException(msg);
		}
		return true;
	}

}
