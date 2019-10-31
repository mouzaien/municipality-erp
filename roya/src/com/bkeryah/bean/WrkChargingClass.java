/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import utilities.Utils;

/**
 *
 * @author IbrahimDarwiesh
 */
public class WrkChargingClass {
    private int WhoIsCharged;
    private String WhoIsChargedName;
    private int WhoIsNotHere;
    private String WhoIsNotHereName;
    private String UserNameBefore;
    private String UserNameAfter;
    private String StartDate;
    private String EndDate;
    private int Chargingflag;
    private int privilageBefore;
    private String privilageBeforeName;
    private int privilageAfter;
    private String privilageAfterName;
    private int ManagerIdBefore;
    private String ManagerNameBefore;
    private int ManagerIdAfter;
    private String ManagerNameAfter;

    public int getWhoIsCharged() {
        return WhoIsCharged;
    }

    public void setWhoIsCharged(int WhoIsCharged) {
        this.WhoIsCharged = WhoIsCharged;
    }

    public String getWhoIsChargedName() {
        return WhoIsChargedName;
    }

    public void setWhoIsChargedName(String WhoIsChargedName) {
        this.WhoIsChargedName = WhoIsChargedName;
    }

    public int getWhoIsNotHere() {
        return WhoIsNotHere;
    }

    public void setWhoIsNotHere(int WhoIsNotHere) {
        this.WhoIsNotHere = WhoIsNotHere;
    }

    public String getWhoIsNotHereName() {
        return WhoIsNotHereName;
    }

    public void setWhoIsNotHereName(String WhoIsNotHereName) {
        this.WhoIsNotHereName = WhoIsNotHereName;
    }

    public String getUserNameBefore() {
        return UserNameBefore;
    }

    public void setUserNameBefore(String UserNameBefore) {
        this.UserNameBefore = UserNameBefore;
    }

    public String getUserNameAfter() {
        return UserNameAfter;
    }

    public void setUserNameAfter(String UserNameAfter) {
        this.UserNameAfter = UserNameAfter;
    }

    public String getStartDate() {
        return StartDate;
    }
    
    public String getConvStartDate() {
        return Utils.convertDateToArabic(StartDate);
    }
    
    public String getConvEndDate() {
        return Utils.convertDateToArabic(EndDate);
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public int getChargingflag() {
        return Chargingflag;
    }

    public void setChargingflag(int Chargingflag) {
        this.Chargingflag = Chargingflag;
    }

    public int getPrivilageBefore() {
        return privilageBefore;
    }

    public void setPrivilageBefore(int privilageBefore) {
        this.privilageBefore = privilageBefore;
    }

    public String getPrivilageBeforeName() {
        return privilageBeforeName;
    }

    public void setPrivilageBeforeName(String privilageBeforeName) {
        this.privilageBeforeName = privilageBeforeName;
    }

    public int getPrivilageAfter() {
        return privilageAfter;
    }

    public void setPrivilageAfter(int privilageAfter) {
        this.privilageAfter = privilageAfter;
    }

    public String getPrivilageAfterName() {
        return privilageAfterName;
    }

    public void setPrivilageAfterName(String privilageAfterName) {
        this.privilageAfterName = privilageAfterName;
    }

    public int getManagerIdBefore() {
        return ManagerIdBefore;
    }

    public void setManagerIdBefore(int ManagerIdBefore) {
        this.ManagerIdBefore = ManagerIdBefore;
    }

    public String getManagerNameBefore() {
        return ManagerNameBefore;
    }

    public void setManagerNameBefore(String ManagerNameBefore) {
        this.ManagerNameBefore = ManagerNameBefore;
    }

    public int getManagerIdAfter() {
        return ManagerIdAfter;
    }

    public void setManagerIdAfter(int ManagerIdAfter) {
        this.ManagerIdAfter = ManagerIdAfter;
    }

    public String getManagerNameAfter() {
        return ManagerNameAfter;
    }

    public void setManagerNameAfter(String ManagerNameAfter) {
        this.ManagerNameAfter = ManagerNameAfter;
    }
    
    
    
}
