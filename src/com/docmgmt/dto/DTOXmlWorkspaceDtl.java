
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOXmlWorkspaceDtl implements Serializable{

    
	long xmlWorkspaceId;
	String xmlWorkspaceName;
	String xmlFileName;
	String filePath;
	String xmlHeader;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi;
	
	
	public long getXmlWorkspaceId() {
		return xmlWorkspaceId;
	}
	public void setXmlWorkspaceId(long xmlWorkspaceId) {
		this.xmlWorkspaceId = xmlWorkspaceId;
	}
	public String getXmlWorkspaceName() {
		return xmlWorkspaceName;
	}
	public void setXmlWorkspaceName(String xmlWorkspaceName) {
		this.xmlWorkspaceName = xmlWorkspaceName;
	}
	public String getXmlFileName() {
		return xmlFileName;
	}
	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getXmlHeader() {
		return xmlHeader;
	}
	public void setXmlHeader(String xmlHeader) {
		this.xmlHeader = xmlHeader;
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
	
	

}