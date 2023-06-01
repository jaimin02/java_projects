package com.docmgmt.dto;

import java.io.Serializable;

import java.util.Date;

public class DTOInternalLabelMst implements Serializable{
	
	String workspaceId;
	int labelNo;
	String labelId;
	String remark;
	int modifyBy;
	Date modifyOn;
	String userName;
	int nodeId;
	int parentNodeId;
	int tranNo;
	int attrTranNo;
	String nodeName;
	String nodeDisplayName;
	String baseWorkFolder;
	String basePublishFolder;
	String workspaceDesc;
	String fileName;
	String folderName;
	String fileVersion;
	
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
	public String getWorkspaceDesc() {
		return workspaceDesc;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	public int getAttrTranNo() {
		return attrTranNo;
	}
	public void setAttrTranNo(int attrTranNo) {
		this.attrTranNo = attrTranNo;
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
	public String getBaseWorkFolder() {
		return baseWorkFolder;
	}
	public void setBaseWorkFolder(String baseWorkFolder) {
		this.baseWorkFolder = baseWorkFolder;
	}
	public String getBasePublishFolder() {
		return basePublishFolder;
	}
	public void setBasePublishFolder(String basePublishFolder) {
		this.basePublishFolder = basePublishFolder;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public int getLabelNo() {
		return labelNo;
	}
	public void setLabelNo(int labelNo) {
		this.labelNo = labelNo;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getFileVersion() {
		return fileVersion;
	}
	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}
	
}
