package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import utilities.Utils;

@FacesValidator("nationalIdValidator")
public class NationalIdValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent component, Object value) throws ValidatorException {
		isValidNat(value);
	}

	public static boolean isValidNat(Object value) {
		boolean valid = true;
		if (value != null) {
			if (value.toString().trim().length() != 10) {
				valid = false;
			} else {
				String paddedVal = "";
				int resultVal = 0;
				for (int count = 1; count < 10; count++) {

					if (count % 2 != 0) {
						paddedVal = Utils.padLeft(
								"" + Integer.parseInt(value.toString().trim().substring(count - 1, count)) * 2, 2, '0');
						resultVal = resultVal + Integer.parseInt(("" + paddedVal).substring(0, 1))
								+ Integer.parseInt(("" + paddedVal).substring(1, 2));
					} else {
						resultVal = resultVal + Integer.parseInt(value.toString().trim().substring(count - 1, count));
					}
				}

				if (!((((resultVal + "").substring((resultVal + "").length() - 1, (resultVal + "").length())
						.equals("0")) && (value.toString().trim().substring(9, 10).equals("0")))
						|| ((10 - Integer.parseInt((resultVal + "").substring((resultVal + "").length() - 1,
								(resultVal + "").length()))) == Integer
										.parseInt(value.toString().trim().substring(9, 10)))))
					valid = false;
			}
		}
		if (!valid) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("error.NationalId"), "");
			throw new ValidatorException(msg);
		}
		return valid;
	}

}