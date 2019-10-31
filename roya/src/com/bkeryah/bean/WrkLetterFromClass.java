/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import utilities.Utils;

/**
 *
 * @author Ibrahimdarwiesh
 */
public class WrkLetterFromClass {
  private int wrkLetterfromId ;
  private String wrkLetterFromName;

    
    public String getConvWrkLetterfromId() {
        return Utils.convertTextWithArNumric(""+wrkLetterfromId);
    }


	public int getWrkLetterfromId() {
		return wrkLetterfromId;
	}


	public void setWrkLetterfromId(int wrkLetterfromId) {
		this.wrkLetterfromId = wrkLetterfromId;
	}


	public String getWrkLetterFromName() {
		return wrkLetterFromName;
	}


	public void setWrkLetterFromName(String wrkLetterFromName) {
		this.wrkLetterFromName = wrkLetterFromName;
	}


	public WrkLetterFromClass(int wrkLetterfromId, String wrkLetterFromName) {
		super();
		this.wrkLetterfromId = wrkLetterfromId;
		this.wrkLetterFromName = wrkLetterFromName;
	}

    
    
}
