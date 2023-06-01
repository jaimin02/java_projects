package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOTelephoneNumberTypeMst {

	String telephoneNumberTypeCode;
	String telephoneNumberTypeDisplay;
	String remark;                           
	int modifyBy;                              
	Timestamp modifyOn;            
	char statusIndi;
	
	
	public String getTelephoneNumberTypeCode() {
		return telephoneNumberTypeCode;
	}
	public void setTelephoneNumberTypeCode(String telephoneNumberTypeCode) {
		this.telephoneNumberTypeCode = telephoneNumberTypeCode;
	}
	public String getTelephoneNumberTypeDisplay() {
		return telephoneNumberTypeDisplay;
	}
	public void setTelephoneNumberTypeDisplay(String telephoneNumberTypeDisplay) {
		this.telephoneNumberTypeDisplay = telephoneNumberTypeDisplay;
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
