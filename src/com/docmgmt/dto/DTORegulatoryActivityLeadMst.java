package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTORegulatoryActivityLeadMst {
	String regActLeadCode;
	String regActLeadDescription;
	             
	int modifyBy;                              
	Timestamp modifyOn;            
	char statusIndi;
	public String getRegActLeadCode() {
		return regActLeadCode;
	}
	public void setRegActLeadCode(String regActLeadCode) {
		this.regActLeadCode = regActLeadCode;
	}
	public String getRegActLeadDescription() {
		return regActLeadDescription;
	}
	public void setRegActLeadDescription(String regActLeadDescription) {
		this.regActLeadDescription = regActLeadDescription;
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
