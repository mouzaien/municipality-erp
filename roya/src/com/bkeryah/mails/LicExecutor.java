package com.bkeryah.mails;

import java.io.InputStream;
import java.sql.Blob;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;

import org.codehaus.plexus.util.IOUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bkeryah.bean.TrdModelClass;
import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

/**
 * This class is used for authorization's model
 *
 */
public class LicExecutor extends MailExecutor<TrdModelClass> {

	public LicExecutor(MailTypeEnum mailType) {
		super(mailType);
	}

	private TrdModelClass selectedTrdApplication;
	private StreamedContent streamedcontent;

	@Override
	public void execute(IDataAccessService dataAccessService) {
		// page = "TradeLicModel.xhtml";
		modelContentHtml = "TradeLicModel.xhtml";
		this.dataAccessService = dataAccessService;
		// super.execute();

	}

	public StreamedContent getImage() {
//		selectedTrdApplication = dataAccessService.findTrdApplicationData(8521);
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String pId = req.getParameter("pid");
		try {
			byte[] pBlob = IOUtil.toByteArray(selectedTrdApplication.getLicTrdPic());
			Blob bild;
			bild = new SerialBlob(pBlob);
			InputStream stream = bild.getBinaryStream();
			return new DefaultStreamedContent(stream, "image/jpg");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return streamedcontent;
	}

	@Override
	public String saveAction(IDataAccessService dataAccessService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String acceptAction() {
		try {
			dataAccessService.acceptLicTrdModel(wrkApplicationId, selectedTrdApplication);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	@Override
	public String refuseAction() {
		try {
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	@Override
	public Employer loadEmployerDataByRecordId(int arcRecordId) {
		return new Employer();
	}

	//todo
	@Override
	public TrdModelClass loadData(UserMailObj userMailClass) {
		isCurrUserSignAutorized(userMailClass);
		wrkApplicationId = new WrkApplicationId(Integer.parseInt(userMailClass.getWrkId()), userMailClass.getStepId());
		int currRequestId = dataAccessService.findLicApplicationIdByRecord(Integer.parseInt(userMailClass.getAppId()));
		selectedTrdApplication = dataAccessService.findTrdApplicationData(currRequestId);
		streamedcontent = getImage();
		return selectedTrdApplication;
	}

	public TrdModelClass getSelectedTrdApplication() {
		return selectedTrdApplication;
	}

	public void setSelectedTrdApplication(TrdModelClass selectedTrdApplication) {
		this.selectedTrdApplication = selectedTrdApplication;
	}

	public StreamedContent getStreamedcontent() {
		return streamedcontent;
	}

	public void setStreamedcontent(StreamedContent streamedcontent) {
		this.streamedcontent = streamedcontent;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	@Override
	public void prepareData() {
		// TODO Auto-generated method stub
	}

}
