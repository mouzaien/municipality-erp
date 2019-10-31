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
public class Main_menu_class implements Serializable {

    private int menuId;
    private String menuTxt;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuTxt() {
        return menuTxt;
    }

    public void setMenuTxt(String menuTxt) {
        this.menuTxt = menuTxt;
    }

    public Main_menu_class(int menuId, String menuTxt) {
        this.menuId = menuId;
        this.menuTxt = menuTxt;
    }

    public Main_menu_class() {
    }

}
