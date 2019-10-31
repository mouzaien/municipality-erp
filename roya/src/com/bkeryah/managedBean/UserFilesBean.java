/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;

import com.bkeryah.bean.AttachmentFileClass;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

/**
 *
 * @author IbrahimDarwiesh
 */

@ManagedBean
@ViewScoped
public class UserFilesBean implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
    List<AttachmentFileClass> files = new ArrayList<AttachmentFileClass>();
    AttachmentFileClass selectedFile = new AttachmentFileClass();
//    private DataAccess dataAccess = new DataAccessImpl();
    private String SearchKey;

    /**
     * Creates a new instance of userFilesBean
     */
    public UserFilesBean() {
//        if (dataAccess == null) {
//            DataAccess dataAccess = new DataAccessImpl();
//        }
    }

    public List<AttachmentFileClass> getFiles() {
        return this.dataAccessService.getAllFilesbyUser(Utils.findCurrentUser().getUserId());
    }

    public void setFiles(List<AttachmentFileClass> files) {
        this.files = files;
    }

    public AttachmentFileClass getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(AttachmentFileClass selectedFile) {
        this.selectedFile = selectedFile;
    }

    public void saveBtnAction(ActionEvent actionEvent) {
        if (this.selectedFile.getAttachmentFileAddedBy() == Utils.findCurrentUser().getUserId()) {
            this.dataAccessService.deleteAttachmentFile(this.selectedFile.getAttachmentFileId());
            files = this.dataAccessService.getAllFilesbyUser(Utils.findCurrentUser().getUserId());

        } else {
            MsgEntry.addInfoMessage("Ù„Ø§ ÙŠÙ…ÙƒÙ†Ùƒ Ø­Ø°Ù� Ù…Ù„Ù� Ù„Ø§ ØªÙ…Ù„ÙƒÙ‡");
        }

    }

    public String AttachIdEncrypt(String AttId) {
        return this.dataAccessService.encrypt(AttId);
    }

    public String getSearchKey() {
        return SearchKey;
    }

    public void setSearchKey(String SearchKey) {
        this.SearchKey = SearchKey;
    }

    List<AttachmentFileClass> findSearchResult(int userid, String srchky) {
        return this.dataAccessService.SearchAttachmentFiles(userid, SearchKey);
    }

    public void uploadFiles(FileUploadEvent fileUploadEvent) {
        try {
            this.dataAccessService.addNewAttachment(new String(fileUploadEvent.getFile().getFileName().getBytes(Charset.defaultCharset()),
                    "UTF-8"),
                    fileUploadEvent.getFile().getInputstream(),
                    Utils.findCurrentUser().getUserId(),
                    8);
            //copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SearchFiles(ActionEvent actionEvent) {
        this.files = findSearchResult(Utils.findCurrentUser().getUserId(), this.SearchKey);
    }

    public void refreshFilesList(ActionEvent actionEvent) {
        files = this.dataAccessService.getAllFilesbyUser(Utils.findCurrentUser().getUserId());
    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
	
	String xDate;
	
	public void printDate(ActionEvent actionEvent){
		String data = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("startDate");


	}

	public String getxDate() {
		return xDate;
	}

	public void setxDate(String xDate) {
		this.xDate = xDate;
	}
	
	
}
