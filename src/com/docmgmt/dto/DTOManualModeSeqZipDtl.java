
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class DTOManualModeSeqZipDtl implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	long manualModeSeqZipId;
	String workspaceId;
	String seqNo;
	String zipFileName;
	String zipFilePath;
	int uploadedBy;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	
	String workspaceDesc;
	String uploadedByName;
	String modifyByName;
	
	
	public String getWorkspaceDesc() {
		return workspaceDesc;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}
	public String getUploadedByName() {
		return uploadedByName;
	}
	public void setUploadedByName(String uploadedByName) {
		this.uploadedByName = uploadedByName;
	}
	public String getModifyByName() {
		return modifyByName;
	}
	public void setModifyByName(String modifyByName) {
		this.modifyByName = modifyByName;
	}
	public long getManualModeSeqZipId() {
		return manualModeSeqZipId;
	}
	public void setManualModeSeqZipId(long manualModeSeqZipId) {
		this.manualModeSeqZipId = manualModeSeqZipId;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getZipFileName() {
		return zipFileName;
	}
	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}
	public String getZipFilePath() {
		return zipFilePath;
	}
	public void setZipFilePath(String zipFilePath) {
		this.zipFilePath = zipFilePath;
	}
	public int getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(int uploadedBy) {
		this.uploadedBy = uploadedBy;
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
