package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTORegulatoryActivityType {
	String regActTypeCode;
	String regActTypeDescription;
	int modifyBy;                              
	Timestamp modifyOn;            
	char statusIndi;
	public String getRegActTypeCode() {
		return regActTypeCode;
	}
	public void setRegActTypeCode(String regActTypeCode) {
		this.regActTypeCode = regActTypeCode;
	}
	public String getRegActTypeDescription() {
		return regActTypeDescription;
	}
	public void setRegActTypeDescription(String regActTypeDescription) {
		this.regActTypeDescription = regActTypeDescription;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	
	

}
