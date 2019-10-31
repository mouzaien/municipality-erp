package com.bkeryah.licences;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.bkeryah.entities.licences.BldLicNew;
import com.bkeryah.licences.models.BldLicNewModel;
import com.bkeryah.licences.models.ResultModel;
import validators.NationalIdValidator;

public class BldLicListExecutor extends AbstractExecutor {
	private String licNewAplOwnerId;

	public BldLicListExecutor(HttpServletRequest request) {
		super(request);
		try {
			if (error == null) {
				try {
					licNewAplOwnerId = getStringParam(request, "LicNewAplOwnerId", true);
					NationalIdValidator.isValidNat(licNewAplOwnerId);
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
		try {
			natioId = Long.parseLong(licNewAplOwnerId);
			List<BldLicNewModel> bldLicList = dataAccessService.loadLicNewList(natioId);
			return bldLicList;
		} catch (Exception e) {
			logger.info(" ArcPeopleExecutor nationalId : " + natioId);
		}
		return new ArrayList<BldLicNew>();
	}

	public String getLicNewAplOwnerId() {
		return licNewAplOwnerId;
	}

	public void setLicNewAplOwnerId(String licNewAplOwnerId) {
		this.licNewAplOwnerId = licNewAplOwnerId;
	}

}
