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
public class WrkLetterToClass {
	private int wrkLetterToId;
	private String wrkLetterToName;

    public String getConvWrkLetterToId() {
        return Utils.convertTextWithArNumric(""+wrkLetterToId);
    }

	public WrkLetterToClass(int wrkLetterToId, String wrkLetterToName) {
		super();
		this.wrkLetterToId = wrkLetterToId;
		this.wrkLetterToName = wrkLetterToName;
	}

	public int getWrkLetterToId() {
		return wrkLetterToId;
	}

	public void setWrkLetterToId(int wrkLetterToId) {
		this.wrkLetterToId = wrkLetterToId;
	}

	public String getWrkLetterToName() {
		return wrkLetterToName;
	}

	public void setWrkLetterToName(String wrkLetterToName) {
		this.wrkLetterToName = wrkLetterToName;
	}



    
}
