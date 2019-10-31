/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

/**
 *
 * @author ibrahim darwiesh
 */
public class AttachmentFileClass {

    private String AttachmentFileId;
    private String AttachmentFileSize;
    private String AttachmentFileRealName;
    private String AttachmentFile;
    private int AttachmentFileAddedBy;
    private String AttachmentFileAddedByName;
    private String AttachmentFileAddedIn;
    private String AttachmentFileExtinsion;
    private String AttachmentFiletype;

    public String getAttachmentFileId() {
        return AttachmentFileId;
    }

    public void setAttachmentFileId(String AttachmentFileId) {
        this.AttachmentFileId = AttachmentFileId;
    }

    public String getAttachmentFileSize() {
        return AttachmentFileSize;
    }

    public void setAttachmentFileSize(String AttachmentFileSize) {
        this.AttachmentFileSize = AttachmentFileSize;
    }

    public String getAttachmentFileRealName() {
        return AttachmentFileRealName;
    }

    public void setAttachmentFileRealName(String AttachmentFileRealName) {
        this.AttachmentFileRealName = AttachmentFileRealName;
    }

    public String getAttachmentFile() {
        return AttachmentFile;
    }

    public void setAttachmentFile(String AttachmentFile) {
        this.AttachmentFile = AttachmentFile;
    }

    public int getAttachmentFileAddedBy() {
        return AttachmentFileAddedBy;
    }

    public void setAttachmentFileAddedBy(int AttachmentFileAddedBy) {
        this.AttachmentFileAddedBy = AttachmentFileAddedBy;
    }

    public String getAttachmentFileAddedByName() {
        return AttachmentFileAddedByName;
    }

    public void setAttachmentFileAddedByName(String AttachmentFileAddedByName) {
        this.AttachmentFileAddedByName = AttachmentFileAddedByName;
    }

    public String getAttachmentFileAddedIn() {
        return AttachmentFileAddedIn;
    }

    public void setAttachmentFileAddedIn(String AttachmentFileAddedIn) {
        this.AttachmentFileAddedIn = AttachmentFileAddedIn;
    }

    public String getAttachmentFileExtinsion() {
        return AttachmentFileExtinsion;
    }

    public void setAttachmentFileExtinsion(String AttachmentFileExtinsion) {
        this.AttachmentFileExtinsion = AttachmentFileExtinsion;
    }

    public String getAttachmentFiletype() {
        return AttachmentFiletype;
    }

    public void setAttachmentFiletype(String AttachmentFiletype) {
        this.AttachmentFiletype = AttachmentFiletype;
    }

    public AttachmentFileClass(String AttachmentFileId, String AttachmentFileSize, String AttachmentFileRealName, int AttachmentFileAddedBy, String AttachmentFileAddedByName, String AttachmentFileAddedIn, String AttachmentFileExtinsion) {
        this.AttachmentFileId = AttachmentFileId;
        this.AttachmentFileSize = AttachmentFileSize;
        this.AttachmentFileRealName = AttachmentFileRealName;
        this.AttachmentFileAddedBy = AttachmentFileAddedBy;
        this.AttachmentFileAddedByName = AttachmentFileAddedByName;
        this.AttachmentFileAddedIn = AttachmentFileAddedIn;
        this.AttachmentFileExtinsion = AttachmentFileExtinsion;
    }

    public AttachmentFileClass() {
    }

}
