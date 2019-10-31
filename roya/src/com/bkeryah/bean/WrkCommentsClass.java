/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import java.io.Serializable;

/**
 *
 * @author IbrahimDarwiesh this calss for COMMENT ONLY IN EXPLAINATION
 */
public class WrkCommentsClass implements Serializable {

    private int StepId;
    private String Comment;
    private String Purpose;
    private String FromName;
    private String ToName;
    private String CreatedIn;
    private String CreateDate;

    public int getStepId() {
        return StepId;
    }

    public void setStepId(int StepId) {
        this.StepId = StepId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String Purpose) {
        this.Purpose = Purpose;
    }

    public String getFromName() {
        return FromName;
    }

    public void setFromName(String FromName) {
        this.FromName = FromName;
    }

    public String getToName() {
        return ToName;
    }

    public void setToName(String ToName) {
        this.ToName = ToName;
    }

    public String getCreatedIn() {
        return CreatedIn;
    }

    public void setCreatedIn(String CreatedIn) {
        this.CreatedIn = CreatedIn;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

}
