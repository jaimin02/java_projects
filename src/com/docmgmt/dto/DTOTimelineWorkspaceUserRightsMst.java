package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOTimelineWorkspaceUserRightsMst {

	String workSpaceId;
	String workspaceDesc;
	String userName;
	String fileName;
	String modifyByName;
	int nodeId;
	int stageId;
	int userCode;
	int userGroupCode;
	int hours;
	Timestamp startDate;
	Timestamp endDate;
	Timestamp adjustDate;
	
	
	
	
	public String getWorkspaceDesc() {
		return workspaceDesc;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getModifyByName() {
		return modifyByName;
	}
	public void setModifyByName(String modifyByName) {
		this.modifyByName = modifyByName;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public int getUserGroupCode() {
		return userGroupCode;
	}
	public void setUserGroupCode(int userGroupCode) {
		this.userGroupCode = userGroupCode;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Timestamp getAdjustDate() {
		return adjustDate;
	}
	public void setAdjustDate(Timestamp adjustDate) {
		this.adjustDate = adjustDate;
	}
	
	
}
