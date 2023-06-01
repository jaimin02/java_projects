package com.docmgmt.dto;

import java.io.Serializable;

public class DTOCreateManualProject implements Serializable
{
	String workspaceId;        
	String nodeName;  
	String parentNodeName; 
	int parentNodeId;
	String fileName;
	String displayName;
	String sequenceNumber;	
	String operation;  
	int UserId;
	int UserGroupCode;
	String locationName;
	String latestSequence;
	String submissionId;
	String indexId;
	
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getParentNodeName() {
		return parentNodeName;
	}
	public void setParentNodeName(String parentNodeName) {
		this.parentNodeName = parentNodeName;
	}
	public int getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int iUserId) {
		this.UserId = iUserId;
	}
	public int getUserGroupCode() {
		return UserGroupCode;
	}
	public void setUserGroupCode(int iUserGroupCode) {
		this.UserGroupCode = iUserGroupCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLatestSequence() {
		return latestSequence;
	}
	public void setLatestSequence(String latestSequence) {
		this.latestSequence = latestSequence;
	}
	public String getSubmissionId() {
		return submissionId;
	}
	public void setSubmissionId(String submissionId) {
		this.submissionId = submissionId;
	}
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

}
