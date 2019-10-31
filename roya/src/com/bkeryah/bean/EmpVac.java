package com.bkeryah.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmpVac implements Serializable {

	public EmpVac() {

	}

	private String empno;
	private String naame;
	private String pjob;
	private String prank;
	private String pfappldat;
	private String pdept;
	private int pbascal;

	private int calc116;
	private int allvacprd;
	private int mxvac116;
	private int VACSUM360;
	private String lastVacDate;

	private String LSTVACWRK;
	private String LSTVACPRD;
	private int VACARESULT;
	private int vacprd;
	private String VACAEND;
	private int basicSalary;
	private int vac122;
	private int vacmx122;
	private int VCALC_new;
	private int PROCTYP;
	private int VACVAL;
	private String VACWRKFROM;
	private String VACWRKto;
	private String mesag;
	private String mesag1;
	private String mesag2;
	private String VACAPRD_new;
	private int employeeId;
	private String vacStartSDate;
	private int lastVacPeriod;
	private String lastVacWrk;

	public String getLastVacDate() {
		return lastVacDate;
	}

	public void setLastVacDate(String lastVacDate) {
		this.lastVacDate = lastVacDate;
	}

	public String getLastVacWrk() {
		return lastVacWrk;
	}

	public void setLastVacWrk(String lastVacWrk) {
		this.lastVacWrk = lastVacWrk;
	}

	public int getLastVacPeriod() {
		return lastVacPeriod;
	}

	public void setLastVacPeriod(int lastVacPeriod) {
		this.lastVacPeriod = lastVacPeriod;
	}

	public int getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(int basicSalary) {
		this.basicSalary = basicSalary;
	}

	public String getVacStartSDate() {
		return vacStartSDate;
	}

	public void setVacStartSDate(String vacStartSDate) {
		this.vacStartSDate = vacStartSDate;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getCalc116() {
		return calc116;
	}

	public void setCalc116(int calc116) {
		this.calc116 = calc116;
	}

	public int getAllvacprd() {
		return allvacprd;
	}

	public int getVacprd() {
		return vacprd;
	}

	public void setVacprd(int vacprd) {
		this.vacprd = vacprd;
	}

	public String getVACAEND() {
		return VACAEND;
	}

	public void setVACAEND(String vACAEND) {
		VACAEND = vACAEND;
	}

	public void setAllvacprd(int allvacprd) {
		this.allvacprd = allvacprd;
	}

	public int getMxvac116() {
		return mxvac116;
	}

	public void setMxvac116(int mxvac116) {
		this.mxvac116 = mxvac116;
	}

	public int getVACSUM360() {
		return VACSUM360;
	}

	public void setVACSUM360(int vACSUM360) {
		VACSUM360 = vACSUM360;
	}

	public String getLSTVACWRK() {
		return LSTVACWRK;
	}

	public void setLSTVACWRK(String lSTVACWRK) {
		LSTVACWRK = lSTVACWRK;
	}

	public String getLSTVACPRD() {
		return LSTVACPRD;
	}

	public void setLSTVACPRD(String lSTVACPRD) {
		LSTVACPRD = lSTVACPRD;
	}

	public int getVACARESULT() {
		return VACARESULT;
	}

	public void setVACARESULT(int vACARESULT) {
		VACARESULT = vACARESULT;
	}

	public String getNaame() {
		return naame;
	}

	public void setNaame(String naame) {
		this.naame = naame;
	}

	public String getPjob() {
		return pjob;
	}

	public void setPjob(String pjob) {
		this.pjob = pjob;
	}

	public String getPrank() {
		return prank;
	}

	public void setPrank(String prank) {
		this.prank = prank;
	}

	public String getPfappldat() {
		return pfappldat;
	}

	public void setPfappldat(String pfappldat) {
		this.pfappldat = pfappldat;
	}

	public String getPdept() {
		return pdept;
	}

	public void setPdept(String pdept) {
		this.pdept = pdept;
	}

	public int getPbascal() {
		return pbascal;
	}

	public void setPbascal(int pbascal) {
		this.pbascal = pbascal;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public int getVCALC_new() {
		return VCALC_new;
	}

	public void setVCALC_new(int vCALC_new) {
		VCALC_new = vCALC_new;
	}

	public int getPROCTYP() {
		return PROCTYP;
	}

	public void setPROCTYP(int pROCTYP) {
		PROCTYP = pROCTYP;
	}

	public int getVACVAL() {
		return VACVAL;
	}

	public void setVACVAL(int vACVAL) {
		VACVAL = vACVAL;
	}

	public String getVACWRKFROM() {
		return VACWRKFROM;
	}

	public void setVACWRKFROM(String vACWRKFROM) {
		VACWRKFROM = vACWRKFROM;
	}

	public String getVACWRKto() {
		return VACWRKto;
	}

	public void setVACWRKto(String vACWRKto) {
		VACWRKto = vACWRKto;
	}

	public String getMesag() {
		return mesag;
	}

	public void setMesag(String mesag) {
		this.mesag = mesag;
	}

	public String getMesag1() {
		return mesag1;
	}

	public void setMesag1(String mesag1) {
		this.mesag1 = mesag1;
	}

	public String getMesag2() {
		return mesag2;
	}

	public void setMesag2(String mesag2) {
		this.mesag2 = mesag2;
	}

	public String getVACAPRD_new() {
		return VACAPRD_new;
	}

	public void setVACAPRD_new(String vACAPRD_new) {
		VACAPRD_new = vACAPRD_new;
	}

	public int getVac122() {
		return vac122;
	}

	public void setVac122(int vac122) {
		this.vac122 = vac122;
	}

	public int getVacmx122() {
		return vacmx122;
	}

	public void setVacmx122(int vacmx122) {
		this.vacmx122 = vacmx122;
	}

}
