
package com.docmgmt.dto;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;

public class DTOWorkspaceNodeDocHistory implements Serializable{
	
	long docHistoryId;
	String workspaceId;
	int nodeId;
	int docTranNo;
	String docName;
	String docContentType;
	String docPath;
	int uploadedBy;
	char converted;
	Timestamp uploadedOn;
	Timestamp convertedOn;
	String remark;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi;
	
	String uploadedByUser;
	String modifyByUser;
	
	File srcDoc;
	String baseSrcDir;
	String ISTDateTime;
	String ESTDateTime;
	double fileSize;
	
	
	public double getFileSize() {
		return fileSize;
	}
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
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
	
	
	public long getDocHistoryId() {
		return docHistoryId;
	}
	public void setDocHistoryId(long docHistoryId) {
		this.docHistoryId = docHistoryId;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getDocTranNo() {
		return docTranNo;
	}
	public void setDocTranNo(int docTranNo) {
		this.docTranNo = docTranNo;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	public String getDocContentType() {
		return docContentType;
	}
	public void setDocContentType(String docContentType) {
		this.docContentType = docContentType;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public char getConverted() {
		return converted;
	}
	public void setConverted(char converted) {
		this.converted = converted;
	}
	public Timestamp getUploadedOn() {
		return uploadedOn;
	}
	public void setUploadedOn(Timestamp uploadedOn) {
		this.uploadedOn = uploadedOn;
	}
	public Timestamp getConvertedOn() {
		return convertedOn;
	}
	public void setConvertedOn(Timestamp convertedOn) {
		this.convertedOn = convertedOn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public int getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(int uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getUploadedByUser() {
		return uploadedByUser;
	}
	public void setUploadedByUser(String uploadedByUser) {
		this.uploadedByUser = uploadedByUser;
	}
	public String getModifyByUser() {
		return modifyByUser;
	}
	public void setModifyByUser(String modifyByUser) {
		this.modifyByUser = modifyByUser;
	}
	public File getSrcDoc() {
		return srcDoc;
	}
	public void setSrcDoc(File srcDoc) {
		this.srcDoc = srcDoc;
	}
	public String getBaseSrcDir() {
		return baseSrcDir;
	}
	public void setBaseSrcDir(String baseSrcDir) {
		this.baseSrcDir = baseSrcDir;
	}
	
}