package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOSmartCaptureMst {

	int srNo;
	String smartCaptureVersion;
	String docStackVersion;
	String description;
	String fileName;
	String filePath;
	int modifyBy;                              
	Timestamp modifyOn;            
	char statusIndi;   	
	String ISTDateTime;
	String ESTDateTime;
	String remark;
	
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getISTDateTime() {
		return ISTDateTime;
	}
	public void setISTDateTime(String iSTDateTime) {
		ISTDateTime = iSTDateTime;
	}
	public String getESTDateTime() {
		return ESTDateTime;
	}
	public void setESTDateTime(String eSTDateTime) {
		ESTDateTime = eSTDateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSmartCaptureVersion() {
		return smartCaptureVersion;
	}
	public void setSmartCaptureVersion(String smartCaptureVersion) {
		this.smartCaptureVersion = smartCaptureVersion;
	}
	public String getDocStackVersion() {
		return docStackVersion;
	}
	public void setDocStackVersion(String docStackVersion) {
		this.docStackVersion = docStackVersion;
	}
	
	
}
