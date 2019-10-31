package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WRK_USER_INBOX_MAIL")
public class WrkUserFolderMail implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@EmbeddedId
	private wrkUSerFolderMailId folderMailId;


	public wrkUSerFolderMailId getFolderMailId() {
		return folderMailId;
	}


	public void setFolderMailId(wrkUSerFolderMailId folderMailId) {
		this.folderMailId = folderMailId;
	}
	
	

}
