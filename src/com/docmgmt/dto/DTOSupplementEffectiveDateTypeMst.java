package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOSupplementEffectiveDateTypeMst {


	String supplementEffectiveDateTypeCode;
	String supplementEffectiveDateTypeDisplay;
	String remark;                           
	int modifyBy;                              
	Timestamp modifyOn;            
	char statusIndi;
	
	
	public String getSupplementEffectiveDateTypeCode() {
		return supplementEffectiveDateTypeCode;
	}
	public void setSupplementEffectiveDateTypeCode(
			String supplementEffectiveDateTypeCode) {
		this.supplementEffectiveDateTypeCode = supplementEffectiveDateTypeCode;
	}
	public String getSupplementEffectiveDateTypeDisplay() {
		return supplementEffectiveDateTypeDisplay;
	}
	public void setSupplementEffectiveDateTypeDisplay(
			String supplementEffectiveDateTypeDisplay) {
		this.supplementEffectiveDateTypeDisplay = supplementEffectiveDateTypeDisplay;
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
