/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import java.io.Serializable;
import java.sql.Date;

import com.bkeryah.dao.DataAccessImpl;

/**
 *
 * @author IbrahimDarwiesh
 */
public class ArcRecordClass implements Serializable {

    int ArcId;
    int ArcRecId;
    String RecHDate;
    String RecTitle;
    Date RecDate;
    String CreatedBy;
    String ArcRecType;
    String IncomeNo;
    String IncomeHDate;
    String LetterFrom;
    String LetterTo;
    String LetterFromDate;
    String IncomeYear;
    int LastEmpNumber;
    String LastEmpName;
    LastRecordActionClass lastRecord;

    public int getArcId() {
        return ArcId;
    }

    public void setArcId(int ArcId) {
        this.ArcId = ArcId;
    }

    public int getArcRecId() {
        return ArcRecId;
    }

    public void setArcRecId(int ArcRecId) {
        this.ArcRecId = ArcRecId;
    }

    public String getRecHDate() {
        return RecHDate;
    }

    public void setRecHDate(String RecHDate) {
        this.RecHDate = RecHDate;
    }

    public String getRecTitle() {
        return RecTitle;
    }

    public void setRecTitle(String RecTitle) {
        this.RecTitle = RecTitle;
    }

    public Date getRecDate() {
        return RecDate;
    }

    public void setRecDate(Date RecDate) {
        this.RecDate = RecDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getArcRecType() {
        return ArcRecType;
    }

    public void setArcRecType(String ArcRecType) {
        this.ArcRecType = ArcRecType;
    }

    public String getIncomeNo() {
        return IncomeNo;
    }

    public void setIncomeNo(String IncomeNo) {
        this.IncomeNo = IncomeNo;
    }

    public String getIncomeHDate() {
        return IncomeHDate;
    }

    public void setIncomeHDate(String IncomeHDate) {
        this.IncomeHDate = IncomeHDate;
    }

    public String getLetterFrom() {
        return LetterFrom;
    }

    public void setLetterFrom(String LetterFrom) {
        this.LetterFrom = LetterFrom;
    }

    public String getLetterTo() {
        return LetterTo;
    }

    public void setLetterTo(String LetterTo) {
        this.LetterTo = LetterTo;
    }

    public String getLetterFromDate() {
        return LetterFromDate;
    }

    public void setLetterFromDate(String LetterFromDate) {
        this.LetterFromDate = LetterFromDate;
    }

    public String getIncomeYear() {
        return IncomeYear;
    }

    public void setIncomeYear(String IncomeYear) {
        this.IncomeYear = IncomeYear;
    }

    public int getLastEmpNumber() {
        return LastEmpNumber;
    }

    public void setLastEmpNumber(int LastEmpNumber) {
        this.LastEmpNumber = LastEmpNumber;
    }

    public String getLastEmpName() {
        return LastEmpName;
    }

    public void setLastEmpName(String LastEmpName) {
        this.LastEmpName = LastEmpName;
    }

    public LastRecordActionClass getLastRecord() {
        lastRecord = new DataAccessImpl().findLastWrkApplicationDetails(ArcId+"");
        return lastRecord;
    }

    public void setLastRecord(LastRecordActionClass lastRecord) {
        this.lastRecord = lastRecord;
    }
    
    

}
