package com.bkeryah.licences;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bkeryah.entities.licences.BldLicNew;
import com.bkeryah.licences.models.ErrorModel;
import com.bkeryah.licences.models.LicModelResult;
import com.bkeryah.licences.models.ResultModel;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.PhoneNumberValidator;
import utilities.UmmAlQUraValidator;
import utilities.Utils;
import validators.NationalIdValidator;

public class BldLicNewExecutor extends AbstractExecutor {
	String LicNewHDate;
	Date licNewGDate;
	String licNewAplOwnerId;
	String licNewAplMobile;
	String licNewSakNo;
	String licNewSakDate;
	String licNewSakSource;

	public BldLicNewExecutor(HttpServletRequest request) {
		super(request);
		try {
			if (error == null) {
				try {
					licNewAplOwnerId = getStringParam(request, "LicNewAplOwnerId", true);
					NationalIdValidator.isValidNat(licNewAplOwnerId);
					licNewAplMobile = getStringParam(request, "LicNewAplMobile", true);
					PhoneNumberValidator.isPhoneValid(licNewAplMobile);
					licNewSakDate = getStringParam(request, "LicNewSakDate", true);
					UmmAlQUraValidator.isHigriDateValid(licNewSakDate);
				} catch (Exception e) {
					throw new ParamRequestException(e.getMessage());
				}

				licNewSakNo = getStringParam(request, "LicNewSakNo", true);
				licNewSakSource = getStringParam(request, "LicNewSakSource", true);
			}
		} catch (ParamRequestException e) {
			error = e.getMessage();
		} catch (Exception e) {
			error = "error parsing parameters";
		}

	}

	public Object execute() {
		try {

			if (error != null)
				return new String(error);
			Integer licId = saveAction();
			if (licId > 0) {
				LicModelResult success = new LicModelResult(1, "success");
				success.setLicNumber(licId);
				logger.info(success + " :" + reqType);
				return success;
			} else {
				ResultModel errorModel = new ResultModel(2, "error");
				logger.info(errorModel + " :" + reqType);
				return errorModel;
			}
		} catch (Exception e) {
			logger.info("saveAction	" + e.getMessage());

		}
		return new ResultModel(2, "error");
	}

	public Integer saveAction() {
		try {
			List<AttachmentModel> attachList = new ArrayList<>();
			String title = MessageFormat.format(Utils.loadMessagesFromFile("new.building.title"),
					HijriCalendarUtil.findCurrentHijriDate());
			BldLicNew newBuilding = new BldLicNew();
			newBuilding.setLicNewAplOwnerId(licNewAplOwnerId);
			newBuilding.setLicNewAplMobile(licNewAplMobile);
			newBuilding.setLicNewSakNo(licNewSakNo);
			newBuilding.setLicNewSakSource(licNewSakSource);
			newBuilding.setLicNewSakDate(LicNewHDate);
			Integer licId = dataAccessService.saveNewBuilding(newBuilding, title, attachList);
			return licId;
		} catch (Exception e) {
			logger.info("saveAction	" + e.getMessage());
			return 0;
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public String getLicNewHDate() {
		return LicNewHDate;
	}

	public void setLicNewHDate(String licNewHDate) {
		LicNewHDate = licNewHDate;
	}

	public Date getLicNewGDate() {
		return licNewGDate;
	}

	public void setLicNewGDate(Date licNewGDate) {
		licNewGDate = licNewGDate;
	}

	public String getLicNewAplOwnerId() {
		return licNewAplOwnerId;
	}

	public void setLicNewAplOwnerId(String licNewAplOwnerId) {
		licNewAplOwnerId = licNewAplOwnerId;
	}

	public String getLicNewAplMobile() {
		return licNewAplMobile;
	}

	public void setLicNewAplMobile(String licNewAplMobile) {
		licNewAplMobile = licNewAplMobile;
	}

	public String getLicNewSakNo() {
		return licNewSakNo;
	}

	public void setLicNewSakNo(String licNewSakNo) {
		licNewSakNo = licNewSakNo;
	}

	public String getLicNewSakDate() {
		return licNewSakDate;
	}

	public void setLicNewSakDate(String licNewSakDate) {
		licNewSakDate = licNewSakDate;
	}

	public String getLicNewSakSource() {
		return licNewSakSource;
	}

	public void setLicNewSakSource(String licNewSakSource) {
		licNewSakSource = licNewSakSource;
	}

}
