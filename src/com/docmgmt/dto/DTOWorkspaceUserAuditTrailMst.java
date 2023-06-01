package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class DTOWorkspaceUserAuditTrailMst implements Serializable  
{
	
	private static final long serialVersionUID = -5245623400825730937L;
	
	int auditId;
	String workspaceId;
	int nodeId;
	int userCode;
	int stageId;
	char statusIndi;
	Timestamp modifyOn;
	int modifyBy;
	String workspaceDesc;
	String remark;
	String nodeName;
	String nodeDisplayName;
	String folderName;
	String stageDesc;
	String assignedTo;
	String assignedBy;
	String userGroupName;
	
	public int getAuditId() {
		return auditId;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public int getUserCode() {
		return userCode;
	}
	public int getStageId() {
		return stageId;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public int getModifyBy() {
		return modifyBy;
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
	public String getStageDesc() {
		return stageDesc;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public String getAssignedBy() {
		return assignedBy;
	}
	public String getUserGroupName() {
		return userGroupName;
	}
	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
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
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
	
}
