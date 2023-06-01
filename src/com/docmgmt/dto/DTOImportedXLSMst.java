package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOImportedXLSMst
{
	String vTableName;
	String vFileName;
	String vRemark;
	int iModifyBy;
	Timestamp dModifyOn;
	String cStatusIndi;
	
	public String getvTableName() {
		return vTableName;
	}
	public void setvTableName(String vTableName) {
		this.vTableName = vTableName;
	}
	public String getvFileName() {
		return vFileName;
	}
	public void setvFileName(String vFileName) {
		this.vFileName = vFileName;
	}
	public String getvRemark() {
		return vRemark;
	}
	public void setvRemark(String vRemark) {
		this.vRemark = vRemark;
	}
	public int getiModifyBy() {
		return iModifyBy;
	}
	public void setiModifyBy(int iModifyBy) {
		this.iModifyBy = iModifyBy;
	}
	public Timestamp getdModifyOn() {
		return dModifyOn;
	}
	public void setdModifyOn(Timestamp dModifyOn) {
		this.dModifyOn = dModifyOn;
	}
	public String getcStatusIndi() {
		return cStatusIndi;
	}
	public void setcStatusIndi(String cStatusIndi) {
		this.cStatusIndi = cStatusIndi;
	}
}