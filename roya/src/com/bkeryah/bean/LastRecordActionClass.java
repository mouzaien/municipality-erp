/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

/**
 *
 * @author darwiesh
 */
public class LastRecordActionClass {
    
    /*
      pLast_action_Date OUT VARCHAR2,
      plast_action_from_days OUT NUMBER,
    */
    private String lastEmployeeName;
    private String lastFromEmployeeName;
    private String lastWrkComm;
    private String lastWrkDate;
    private int lastWrkdurationindays;
    private int stepsNumber;
    private String lastdirection;
    private String hasComment;
    private String commentText;
    private String wroteByName;
    private String commentWroteByName;
    private String commentMarkedByName;
    private String commentSignedByName;
    private String wrkApplicationId;

    public String getWrkApplicationId() {
        return wrkApplicationId;
    }

    public void setWrkApplicationId(String wrkApplicationId) {
        this.wrkApplicationId = wrkApplicationId;
    }

    
    
    public String getLastEmployeeName() {
        return lastEmployeeName;
    }

    public void setLastEmployeeName(String lastEmployeeName) {
        this.lastEmployeeName = lastEmployeeName;
    }

    public int getStepsNumber() {
        return stepsNumber;
    }

    public void setStepsNumber(int stepsNumber) {
        this.stepsNumber = stepsNumber;
    }

    public String getLastdirection() {
        return lastdirection;
    }

    public void setLastdirection(String lastdirection) {
        this.lastdirection = lastdirection;
    }

    public String isHasComment() {
        return hasComment;
    }

    public void setHasComment(String hasComment) {
        this.hasComment = hasComment;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getWroteByName() {
        return wroteByName;
    }

    public void setWroteByName(String wroteByName) {
        this.wroteByName = wroteByName;
    }

    public String getCommentWroteByName() {
        return commentWroteByName;
    }

    public void setCommentWroteByName(String commentWroteByName) {
        this.commentWroteByName = commentWroteByName;
    }

    public String getCommentMarkedByName() {
        return commentMarkedByName;
    }

    public void setCommentMarkedByName(String commentMarkedByName) {
        this.commentMarkedByName = commentMarkedByName;
    }

    public String getCommentSignedByName() {
        return commentSignedByName;
    }

    public void setCommentSignedByName(String commentSignedByName) {
        this.commentSignedByName = commentSignedByName;
    }

    public String getLastFromEmployeeName() {
        return lastFromEmployeeName;
    }

    public void setLastFromEmployeeName(String lastFromEmployeeName) {
        this.lastFromEmployeeName = lastFromEmployeeName;
    }

    public String getLastWrkComm() {
        return lastWrkComm;
    }

    public void setLastWrkComm(String lastWrkComm) {
        this.lastWrkComm = lastWrkComm;
    }

    public String getLastWrkDate() {
        return lastWrkDate;
    }

    public void setLastWrkDate(String lastWrkDate) {
        this.lastWrkDate = lastWrkDate;
    }

    public int getLastWrkdurationindays() {
        return lastWrkdurationindays;
    }

    public void setLastWrkdurationindays(int lastWrkdurationindays) {
        this.lastWrkdurationindays = lastWrkdurationindays;
    }
    
    
    
    
    
    
    
}
