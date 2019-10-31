/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import java.io.Serializable;

/**
 *
 * @author Ibrahim Darwiesh
 */
public class Sub_menu_class implements Serializable {

    private int Vsub_menu_id;
    private String Vsub_menu_txt;
    private String Vsub_menu_icon;
    private String Vsub_menu_command;
    private String Vsub_menu_url;
    private int Vmain_menu_id;

    public int getVsub_menu_id() {
        return Vsub_menu_id;
    }

    public void setVsub_menu_id(int Vsub_menu_id) {
        this.Vsub_menu_id = Vsub_menu_id;
    }

    public String getVsub_menu_txt() {
        return Vsub_menu_txt;
    }

    public void setVsub_menu_txt(String Vsub_menu_txt) {
        this.Vsub_menu_txt = Vsub_menu_txt;
    }

    public String getVsub_menu_icon() {
        return Vsub_menu_icon;
    }

    public void setVsub_menu_icon(String Vsub_menu_icon) {
        this.Vsub_menu_icon = Vsub_menu_icon;
    }

    public String getVsub_menu_command() {
        return Vsub_menu_command;
    }

    public void setVsub_menu_command(String Vsub_menu_command) {
        this.Vsub_menu_command = Vsub_menu_command;
    }

    public String getVsub_menu_url() {
        return Vsub_menu_url;
    }

    public void setVsub_menu_url(String Vsub_menu_url) {
        this.Vsub_menu_url = Vsub_menu_url;
    }

    public int getVmain_menu_id() {
        return Vmain_menu_id;
    }

    public void setVmain_menu_id(int Vmain_menu_id) {
        this.Vmain_menu_id = Vmain_menu_id;
    }

    public Sub_menu_class(String Vsub_menu_txt, String Vsub_menu_icon, String Vsub_menu_url) {
        this.Vsub_menu_txt = Vsub_menu_txt;
        this.Vsub_menu_icon = Vsub_menu_icon;
        this.Vsub_menu_url = Vsub_menu_url;
    }

    public Sub_menu_class() {
    }

    public Sub_menu_class(int Vsub_menu_id, String Vsub_menu_txt, int Vmain_menu_id) {
        this.Vsub_menu_id = Vsub_menu_id;
        this.Vsub_menu_txt = Vsub_menu_txt;
        this.Vmain_menu_id = Vmain_menu_id;
    }

}
