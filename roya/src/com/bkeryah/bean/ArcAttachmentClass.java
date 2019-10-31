/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import java.io.Serializable;

import utilities.MyConstants;

/**
 *
 * @author idarwiesh
 */
public class ArcAttachmentClass implements Serializable {

    private String AttId;
    private String AttName;
    private String AttOwner;
    private double AttSize;
    private String AttLink;
    private String OwnerName;
    private String text1;
    private String FileType;
    private boolean isPdf;
    private boolean ireportFile;

    public String getAttId() {
        return AttId;
    }

    public void setAttId(String AttId) {
        this.AttId = AttId;
    }

    public String getAttName() {
        return AttName;
    }

    public void setAttName(String AttName) {
        this.AttName = AttName;
    }

    public String getAttOwner() {
        return AttOwner;
    }

    public void setAttOwner(String AttOwner) {
        this.AttOwner = AttOwner;
    }

    public double getAttSize() {
        return AttSize;
    }

    public void setAttSize(double AttSize) {
        this.AttSize = AttSize;
    }

    public String getAttLink() {
        return AttLink;
    }

    public void setAttLink(String AttLink) {
        this.AttLink = AttLink;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String OwnerName) {
        this.OwnerName = OwnerName;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String FileType) {
        this.FileType = FileType;
    }

    public boolean getIsPdf() {
        return isPdf;
    }

    public void setIsPdf(boolean isPdf) {
        this.isPdf = isPdf;
    }

	public boolean isIreportFile() {
		if((AttLink != null) && ((AttLink.contains(MyConstants.REPORT_NORMAL_VACATION)) || (AttLink.contains(MyConstants.REPORT_MEDICAL)) || (AttLink.contains(MyConstants.REPORT_SALAR))))
			ireportFile = true;
		return ireportFile;
	}

	public void setIreportFile(boolean ireportFile) {
		this.ireportFile = ireportFile;
	}

    
}
