/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

/**
 *
 * @author IbrahimDarwiesh
 */
public class UserFolderClass {
    
    private int FolderId;
    private String FolderName;
    private int UserId;

    public int getFolderId() {
        return FolderId;
    }

    public void setFolderId(int FolderId) {
        this.FolderId = FolderId;
    }

    public String getFolderName() {
        return FolderName;
    }

    public void setFolderName(String FolderName) {
        this.FolderName = FolderName;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public UserFolderClass(int FolderId, String FolderName, int UserId) {
        this.FolderId = FolderId;
        this.FolderName = FolderName;
        this.UserId = UserId;
    }

    public UserFolderClass() {
    }
    
    
    
}
