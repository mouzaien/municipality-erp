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
public class UserClass implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Vuser_id;
    private String Vusers_login_name;
    private String Vusers_real_name;
    private String Vusers_national_id;
    private String Vusers_mobile;
    private String Vusers_password;
    private String Vusers_date_of_birth;
    private String Vuser_email;
    private int Vusers_login_priv;
    private int Vusers_mgr_id;
    private int Vusers_dept_id;
    private String Vusers_job_identifier;
    private String Vusers_created_in;
    private int VUserRoleId;
    private String DepartmentName;
    private String ManagerName;
    private String empNo;

    public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public int getVuser_id() {
        return Vuser_id;
    }

    public void setVuser_id(int Vuser_id) {
        this.Vuser_id = Vuser_id;
    }

    public String getVusers_login_name() {
        return Vusers_login_name;
    }

    public void setVusers_login_name(String Vusers_login_name) {
        this.Vusers_login_name = Vusers_login_name;
    }

    public String getVusers_real_name() {
        return Vusers_real_name;
    }

    public void setVusers_real_name(String Vusers_real_name) {
        this.Vusers_real_name = Vusers_real_name;
    }

    public String getVusers_national_id() {
        return Vusers_national_id;
    }

    public void setVusers_national_id(String Vusers_national_id) {
        this.Vusers_national_id = Vusers_national_id;
    }

    public String getVusers_mobile() {
        return Vusers_mobile;
    }

    public void setVusers_mobile(String Vusers_mobile) {
        this.Vusers_mobile = Vusers_mobile;
    }

    public String getVusers_password() {
        return Vusers_password;
    }

    public void setVusers_password(String Vusers_password) {
        this.Vusers_password = Vusers_password;
    }

    public String getVusers_date_of_birth() {
        return Vusers_date_of_birth;
    }

    public void setVusers_date_of_birth(String Vusers_date_of_birth) {
        this.Vusers_date_of_birth = Vusers_date_of_birth;
    }

    public String getVuser_email() {
        return Vuser_email;
    }

    public void setVuser_email(String Vuser_email) {
        this.Vuser_email = Vuser_email;
    }

    public int getVusers_login_priv() {
        return Vusers_login_priv;
    }

    public void setVusers_login_priv(int Vusers_login_priv) {
        this.Vusers_login_priv = Vusers_login_priv;
    }

    public int getVusers_mgr_id() {
        return Vusers_mgr_id;
    }

    public void setVusers_mgr_id(int Vusers_mgr_id) {
        this.Vusers_mgr_id = Vusers_mgr_id;
    }

    public int getVusers_dept_id() {
        return Vusers_dept_id;
    }

    public void setVusers_dept_id(int Vusers_dept_id) {
        this.Vusers_dept_id = Vusers_dept_id;
    }

    public String getVusers_job_identifier() {
        return Vusers_job_identifier;
    }

    public void setVusers_job_identifier(String Vusers_job_identifier) {
        this.Vusers_job_identifier = Vusers_job_identifier;
    }

    public String getVusers_created_in() {
        return Vusers_created_in;
    }

    public void setVusers_created_in(String Vusers_created_in) {
        this.Vusers_created_in = Vusers_created_in;
    }

    public UserClass(int Vuser_id, String Vusers_login_name,
            String Vusers_real_name, String Vusers_password) {
        this.Vuser_id = Vuser_id;
        this.Vusers_login_name = Vusers_login_name;
        this.Vusers_real_name = Vusers_real_name;
        this.Vusers_password = Vusers_password;
    }
    
    public UserClass(int Vuser_id, String Vusers_login_name,
            String Vusers_real_name, String Vusers_password, int Vusers_mgr_id, String empNo) {
        this.Vuser_id = Vuser_id;
        this.Vusers_login_name = Vusers_login_name;
        this.Vusers_real_name = Vusers_real_name;
        this.Vusers_password = Vusers_password;
        this.Vusers_mgr_id = Vusers_mgr_id;
        this.empNo = empNo;
    }

    public int getVUserRoleId() {
        return VUserRoleId;
    }

    public void setVUserRoleId(int VUserRoleId) {
        this.VUserRoleId = VUserRoleId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String ManagerName) {
        this.ManagerName = ManagerName;
    }

    
    public UserClass() {
    }

    public UserClass(int Vuser_id, String Vusers_real_name) {
        this.Vuser_id = Vuser_id;
        this.Vusers_real_name = Vusers_real_name;
    }

    public UserClass(int Vuser_id, String Vusers_real_name, int VUserRoleId) {
        this.Vuser_id = Vuser_id;
        this.Vusers_real_name = Vusers_real_name;
        this.VUserRoleId = VUserRoleId;
    }
    
    

}
