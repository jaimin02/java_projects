/*
 * Created on Dec 23, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author riddhi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOWorkSpaceNodeVersionHistory implements Serializable{
	String workspaceId;
	String workspaceDesc;
	String nodeDisplayName;

	int nodeId;
	int tranNo;
	String fileVersionId;
	String fileName;
	String attrValue;
	String attrName;
	
	char Published;
	char lastClosed;
	char downloaded;
	String activityId;
	int modifyBy;
	int executedBy;
	Timestamp executedOn;
	Timestamp modifyOn;
	char statusIndi;	
	String userDefineVersionId;	
	String userName;
	
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
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
	public char getDownloaded() {
		return downloaded;
	}
	public void setDownloaded(char downloaded) {
		this.downloaded = downloaded;
	}
	public int getExecutedBy() {
		return executedBy;
	}
	public void setExecutedBy(int executedBy) {
		this.executedBy = executedBy;
	}
	public Timestamp getExecutedOn() {
		return executedOn;
	}
	public void setExecutedOn(Timestamp executedOn) {
		this.executedOn = executedOn;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileVersionId() {
		return fileVersionId;
	}
	public void setFileVersionId(String fileVersionId) {
		this.fileVersionId = fileVersionId;
	}
	public char getLastClosed() {
		return lastClosed;
	}
	public void setLastClosed(char lastClosed) {
		this.lastClosed = lastClosed;
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
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public char getPublished() {
		return Published;
	}
	public void setPublished(char published) {
		this.Published = published;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	public String getUserDefineVersionId() {
		return userDefineVersionId;
	}
	public void setUserDefineVersionId(String userDefineVersionId) {
		this.userDefineVersionId = userDefineVersionId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWorkspaceDesc() {
		return workspaceDesc;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
}
