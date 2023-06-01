package com.docmgmt.dto;

import java.io.Serializable;

public class DTOStageWiseMailReport implements Serializable{
	
  String workspaceId;
  String workspaceDesc;
  int nodeId;
  int stageId;
  int userCode;
  String userName;
  String loginName;
  String stageDesc;
  String fileName;
  String nextStageDesc;
  int nextStageId;
  String userGroupName;
  int completedStageId;
  
  
  
public String getUserGroupName() {
	return userGroupName;
}
public void setUserGroupName(String userGroupName) {
	this.userGroupName = userGroupName;
}
public int getCompletedStageId() {
	return completedStageId;
}
public void setCompletedStageId(int completedStageId) {
	this.completedStageId = completedStageId;
}
public String getNextStageDesc() {
	return nextStageDesc;
}
public void setNextStageDesc(String nextStageDesc) {
	this.nextStageDesc = nextStageDesc;
}
public int getNextStageId() {
	return nextStageId;
}
public void setNextStageId(int nextStageId) {
	this.nextStageId = nextStageId;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getWorkspaceId() {
	return workspaceId;
}
public void setWorkspaceId(String workspaceId) {
	this.workspaceId = workspaceId;
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
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getLoginName() {
	return loginName;
}
public void setLoginName(String loginName) {
	this.loginName = loginName;
}
public String getStageDesc() {
	return stageDesc;
}
public void setStageDesc(String stageDesc) {
	this.stageDesc = stageDesc;
}
}
