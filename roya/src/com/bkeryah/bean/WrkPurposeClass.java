/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import java.io.Serializable;

import utilities.Utils;

/**
 *
 * @author IbrahimDarwiesh
 */
public class WrkPurposeClass implements Serializable {

    private int purpId;
    private String purpName;

    
    public String getConvPurpId() {
        return Utils.convertTextWithArNumric(""+purpId);
    }


	public int getPurpId() {
		return purpId;
	}


	public void setPurpId(int purpId) {
		this.purpId = purpId;
	}


	public String getPurpName() {
		return purpName;
	}


	public void setPurpName(String purpName) {
		this.purpName = purpName;
	}


}
