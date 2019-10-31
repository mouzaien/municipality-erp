package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = " WRK_INBOX_FOLDER")
public class WrkInboxFolder implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "F_ID")
	private int folderId;
	@Column(name = "USER_ID")
	private int folderUserId;
	@Column(name = "FNAME")
	private String folderName;
	@Column(name = "F_VISIBLE_Y_N")
	private char visible;
	
	public int getFolderId() {
		return folderId;
	}
	public int getFolderUserId() {
		return folderUserId;
	}
	public String getFolderName() {
		return folderName;
	}
	public char getVisible() {
		return visible;
	}
	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}
	public void setFolderUserId(int folderUserId) {
		this.folderUserId = folderUserId;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public void setVisible(char visible) {
		this.visible = visible;
	}

}
