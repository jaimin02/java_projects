package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class DTOContentSearch implements Serializable
{
	private static final long serialVersionUID = 1L;
	String workspaceid;
	String workspaceDesc;
	String baseWorkFolder;
	int nodeId;
	String nodeName;
	String nodeDisplayName;
	String folderName;
	String fileName;
	String fileFolderName;
	String modifyBy;
	Timestamp modifyOn;
	String subjectDesc;
	String subjectId;
	String senderUserName;
	String receiverUserName;
	String attrName;
	String attrValue;
	int attrid;
	String attrtype;
	String pdfFileName;
	String pdfFilePath;
	
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getWorkspaceid() {
		return workspaceid;
	}
	public void setWorkspaceid(String workspaceid) {
		this.workspaceid = workspaceid;
	}
	public String getWorkspaceDesc() {
		return workspaceDesc;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}
	public String getBaseWorkFolder() {
		return baseWorkFolder;
	}
	public void setBaseWorkFolder(String baseWorkFolder) {
		this.baseWorkFolder = baseWorkFolder;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileFolderName() {
		return fileFolderName;
	}
	public void setFileFolderName(String fileFolderName) {
		this.fileFolderName = fileFolderName;
	}
	public String getSubjectDesc() {
		return subjectDesc;
	}
	public void setSubjectDesc(String subjectDesc) {
		this.subjectDesc = subjectDesc;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSenderUserName() {
		return senderUserName;
	}
	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}
	public String getReceiverUserName() {
		return receiverUserName;
	}
	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}			
	public int getAttrid() {
		return attrid;
	}
	public void setAttrid(int attrid) {
		this.attrid = attrid;
	}
	public String getAttrtype() {
		return attrtype;
	}
	public void setAttrtype(String attrtype) {
		this.attrtype = attrtype;
	}
	public String getPdfFileName() {
		return pdfFileName;
	}
	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}
	public String getPdfFilePath() {
		return pdfFilePath;
	}
	public void setPdfFilePath(String pdfFilePath) {
		this.pdfFilePath = pdfFilePath;
	}
}