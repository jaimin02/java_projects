package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOSubmissionSubTypeMst {
	

	String submissionSubTypeCode;
	String submissionSubTypeDisplay;
	String remark;                           
	int modifyBy;                              
	Timestamp modifyOn;            
	char statusIndi;
	
	
	public String getSubmissionSubTypeCode() {
		return submissionSubTypeCode;
	}
	public void setSubmissionSubTypeCode(String submissionSubTypeCode) {
		this.submissionSubTypeCode = submissionSubTypeCode;
	}
	public String getSubmissionSubTypeDisplay() {
		return submissionSubTypeDisplay;
	}
	public void setSubmissionSubTypeDisplay(String submissionSubTypeDisplay) {
		this.submissionSubTypeDisplay = submissionSubTypeDisplay;
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
