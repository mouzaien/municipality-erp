package com.bkeryah.licences;

import javax.servlet.http.HttpServletRequest;
import com.bkeryah.entities.ArcPeopleModel;
import com.bkeryah.licences.models.ResultModel;
import utilities.PhoneNumberValidator;
import utilities.Utils;
import validators.NationalIdValidator;

public class ArcPeopleExecutor extends AbstractExecutor {
	private String licNewAplOwnerId;
	private String licNewAplMobile;
	private String firstName;
	private String seconedName;
	private String thirdName;
	private String fourthName;
	private String idDate;
	private String address;
	private String email;
	private String idSource;

	public ArcPeopleExecutor(HttpServletRequest request) {
		super(request);
		try {
			if (error == null) {
				try {
					licNewAplOwnerId = getStringParam(request, "LicNewAplOwnerId", true);
					NationalIdValidator.isValidNat(licNewAplOwnerId);
					licNewAplMobile = getStringParam(request, "LicNewAplMobile", true);
					PhoneNumberValidator.isPhoneValid(licNewAplMobile);
					firstName = getStringParam(request, "firstName", true);
					seconedName = getStringParam(request, "seconedName", false);
					thirdName = getStringParam(request, "thirdName", false);
					fourthName = getStringParam(request, "fourthName", false);
					address = getStringParam(request, "address", true);
					idSource = getStringParam(request, "idSource", true);
					idDate = getStringParam(request, "idDate", true);
					email = getStringParam(request, "email", true);
				} catch (Exception e) {
					throw new ParamRequestException(e.getMessage());
				}

			}
		} catch (ParamRequestException e) {
			error = e.getMessage();
		} catch (Exception e) {
			error = "error parsing parameters";
		}

	}

	@Override
	public Object execute() {
		if (error != null)
			return new ResultModel(2, error);
		long natioId = 0l;
		if (licNewAplOwnerId.length() > 0)
			natioId = Long.parseLong(licNewAplOwnerId);
		ArcPeopleModel arcPeople = dataAccessService.loadArcPeopleModel(natioId);
		try {
			if (arcPeople == null) {
				arcPeople = new ArcPeopleModel();
				arcPeople.setNationalId(natioId);
				arcPeople.setFirstName(firstName);
				arcPeople.setSeconedName(seconedName);
				arcPeople.setThirdName(thirdName);
				arcPeople.setFourthName(fourthName);
				arcPeople.setEmail(email);
				arcPeople.setAddress(address);
				arcPeople.setIdDate(idDate);
				arcPeople.setIdSource(idSource);
				arcPeople.setMobileNumber(licNewAplMobile);
				if (dataAccessService.addArcPeople(arcPeople) > 0)
					return new ResultModel(1, "success");
			} else
				dataAccessService.updateObject(arcPeople);
			return new ResultModel(1, "success");
		} catch (Exception e) {
			logger.info(" ArcPeopleExecutor nationalId : " + natioId);
		}
		return new ResultModel(2, "error");
	}

	public String getLicNewAplOwnerId() {
		return licNewAplOwnerId;
	}

	public void setLicNewAplOwnerId(String licNewAplOwnerId) {
		this.licNewAplOwnerId = licNewAplOwnerId;
	}

	public String getLicNewAplMobile() {
		return licNewAplMobile;
	}

	public void setLicNewAplMobile(String licNewAplMobile) {
		this.licNewAplMobile = licNewAplMobile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSeconedName() {
		return seconedName;
	}

	public void setSeconedName(String seconedName) {
		this.seconedName = seconedName;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getFourthName() {
		return fourthName;
	}

	public void setFourthName(String fourthName) {
		this.fourthName = fourthName;
	}

	public String getIdDate() {
		return idDate;
	}

	public void setIdDate(String idDate) {
		this.idDate = idDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdSource() {
		return idSource;
	}

	public void setIdSource(String idSource) {
		this.idSource = idSource;
	}

}
