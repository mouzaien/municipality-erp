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
public class SpecialAddressClass {
    private int UserId;
    private int SpecialAddressId;
    private String SpecialAddressName;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public int getSpecialAddressId() {
        return SpecialAddressId;
    }
    
    public String getConvSpecialAddressId() {
        return Utils.convertTextWithArNumric(""+SpecialAddressId);
    }

    public void setSpecialAddressId(int SpecialAddressId) {
        this.SpecialAddressId = SpecialAddressId;
    }

    public String getSpecialAddressName() {
        return SpecialAddressName;
    }

    public void setSpecialAddressName(String SpecialAddressName) {
        this.SpecialAddressName = SpecialAddressName;
    }
    
    
    
}
