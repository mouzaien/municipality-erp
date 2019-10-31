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
public class ArcApplicationTypeClass {
	private int arcAppliactionTypeId;
	private String arcApplicationType;

    
    
    public String getConvArcAppliactionTypeId() {
        return Utils.convertTextWithArNumric(""+arcAppliactionTypeId);
    }



	public int getArcAppliactionTypeId() {
		return arcAppliactionTypeId;
	}



	public void setArcAppliactionTypeId(int arcAppliactionTypeId) {
		this.arcAppliactionTypeId = arcAppliactionTypeId;
	}



	public String getArcApplicationType() {
		return arcApplicationType;
	}



	public void setArcApplicationType(String arcApplicationType) {
		this.arcApplicationType = arcApplicationType;
	}



	public ArcApplicationTypeClass(int arcAppliactionTypeId, String arcApplicationType) {
		super();
		this.arcAppliactionTypeId = arcAppliactionTypeId;
		this.arcApplicationType = arcApplicationType;
	}

    
    

//    public ArcApplicationTypeClass(int arcAppliactionTypeId, String arcApplicationType) {
//        this.arcAppliactionTypeId = arcAppliactionTypeId;
//        this.arcApplicationType = arcApplicationType;
//    }
    
    
    
    
}
