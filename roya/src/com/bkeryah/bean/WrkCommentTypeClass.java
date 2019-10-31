/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import java.io.Serializable;

/**
 *
 * @author IbrahimDarwiesh
 */
public class WrkCommentTypeClass implements Serializable {

    private int WrkCommenttypeId;
    private String WrkCommenttypeName;

    public int getWrkCommenttypeId() {
        return WrkCommenttypeId;
    }

    public void setWrkCommenttypeId(int WrkCommenttypeId) {
        this.WrkCommenttypeId = WrkCommenttypeId;
    }

    public String getWrkCommenttypeName() {
        return WrkCommenttypeName;
    }

    public void setWrkCommenttypeName(String WrkCommenttypeName) {
        this.WrkCommenttypeName = WrkCommenttypeName;
    }

}
