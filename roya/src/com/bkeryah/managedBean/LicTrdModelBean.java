package com.bkeryah.managedBean;

import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.codehaus.plexus.util.IOUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bkeryah.bean.TrdModelClass;
import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ExchangeRequest;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped

public class LicTrdModelBean {

	private TrdModelClass selectedTrdApplication;
	private StreamedContent streamedcontent;
	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private WrkApplicationId wrkId;
	private UserMailObj selectedInbox;
	private List<WrkPurpose> wrkPurposes;
	private String applicationPurpose;
	
	public LicTrdModelBean() {

	}
	
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		// test if in view mode or insert mode
		if (arcRecordId != null) {
			selectedInbox = (UserMailObj) httpSession.getAttribute("selectedMail");
			setWrkId(new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId), selectedInbox.StepId));
			setWrkPurposes(dataAccessService.getAllPurposes());
			selectedTrdApplication = dataAccessService.findTrdApplicationDataByArcrecord(Integer.parseInt(arcRecordId));
		}
	}

	public String acceptAction() {
		try {
			dataAccessService.acceptLicTrdModel(wrkId, selectedTrdApplication);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
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

		// return new
		// DefaultStreamedContent(selectedTrdApplication.getLicTrdPic(),
		// "image/jpeg");
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public WrkApplicationId getWrkId() {
		return wrkId;
	}

	public void setWrkId(WrkApplicationId wrkId) {
		this.wrkId = wrkId;
	}

	public UserMailObj getSelectedInbox() {
		return selectedInbox;
	}

	public void setSelectedInbox(UserMailObj selectedInbox) {
		this.selectedInbox = selectedInbox;
	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

}
