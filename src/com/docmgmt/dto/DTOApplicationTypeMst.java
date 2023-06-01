package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOApplicationTypeMst {


    
	String applicationTypeCode;
	String applicationTypeDisplay;
	String remark;                           
	int modifyBy;                              
	Timestamp modifyOn;            
	char statusIndi;
	
	
	public String getApplicationTypeCode() {
		return applicationTypeCode;
	}
	public void setApplicationTypeCode(String applicationTypeCode) {
		this.applicationTypeCode = applicationTypeCode;
	}
	public String getApplicationTypeDisplay() {
		return applicationTypeDisplay;
	}
	public void setApplicationTypeDisplay(String applicationTypeDisplay) {
		this.applicationTypeDisplay = applicationTypeDisplay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
