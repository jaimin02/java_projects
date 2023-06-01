package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOApplicantContactTypeMst {

	
                                      
	String applicantContactTypeCode;
	String applicantContactTypeDisplay;
	String remark;                           
	int modifyBy;                              
	Timestamp modifyOn;            
	char statusIndi;
	
	
	public String getApplicantContactTypeCode() {
		return applicantContactTypeCode;
	}
	public void setApplicantContactTypeCode(String applicantContactTypeCode) {
		this.applicantContactTypeCode = applicantContactTypeCode;
	}
	public String getApplicantContactTypeDisplay() {
		return applicantContactTypeDisplay;
	}
	public void setApplicantContactTypeDisplay(String applicantContactTypeDisplay) {
		this.applicantContactTypeDisplay = applicantContactTypeDisplay;
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
