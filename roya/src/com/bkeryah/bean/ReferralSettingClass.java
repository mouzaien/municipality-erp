/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import utilities.Utils;

/**
 *
 * @author darwiesh
 */
public class ReferralSettingClass {
    private int ReerralId;
    private String ReferralName;
    private String ReferralTitle;
    private int ReferralManagerId;
    private String ReferralRealName;

    public int getReerralId() {
        return ReerralId;
    }

    public void setReerralId(int ReerralId) {
        this.ReerralId = ReerralId;
    }

    public String getReferralName() {
        return ReferralName;
    }

    public void setReferralName(String ReferralName) {
        this.ReferralName = ReferralName;
    }

    public String getReferralTitle() {
        return ReferralTitle;
    }

    public void setReferralTitle(String ReferralTitle) {
        this.ReferralTitle = ReferralTitle;
    }

    public int getReferralManagerId() {
        return ReferralManagerId;
    }
    
    public String getConvReferralManagerId() {
        return Utils.convertTextWithArNumric(""+ReferralManagerId);
    }

    public void setReferralManagerId(int ReferralManagerId) {
        this.ReferralManagerId = ReferralManagerId;
    }

    public String getReferralRealName() {
        return ReferralRealName;
    }

    public void setReferralRealName(String ReferralRealName) {
        this.ReferralRealName = ReferralRealName;
    }

    public ReferralSettingClass(int ReerralId, String ReferralName, String ReferralTitle, int ReferralManagerId, String ReferralRealName) {
        this.ReerralId = ReerralId;
        this.ReferralName = ReferralName;
        this.ReferralTitle = ReferralTitle;
        this.ReferralManagerId = ReferralManagerId;
        this.ReferralRealName = ReferralRealName;
    }

    public ReferralSettingClass() {
    }
    
    
}
