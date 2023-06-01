package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOReleaseDocMgmt {
	int autoID;
	String workspaceId;
	int nodeId;
	char statusIndi;
	int modifyBy;
	String comments;
	Timestamp modifyOn;
	String workspaceDesc;
	String remark;
	String nodeName;
	String nodeDisplayName;
	String folderName;
	int parentNodeId;
	String modifyByUser;
	String userGroupName;
	String docVersion;
	String stage;
	public int getAutoID() {
		return autoID;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public String getComments() {
		return comments;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public String getWorkspaceDesc() {
		return workspaceDesc;
	}
	public String getRemark() {
		return remark;
	}
	public String getNodeName() {
		return nodeName;
	}
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public String getFolderName() {
		return folderName;
	}
	public int getParentNodeId() {
		return parentNodeId;
	}
	public String getModifyByUser() {
		return modifyByUser;
	}
	public String getUserGroupName() {
		return userGroupName;
	}
	public void setAutoID(int autoID) {
		this.autoID = autoID;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public void setModifyByUser(String modifyByUser) {
		this.modifyByUser = modifyByUser;
	}
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
	public String getDocVersion() {
		return docVersion;
	}
	public String getStage() {
		return stage;
	}
	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
}