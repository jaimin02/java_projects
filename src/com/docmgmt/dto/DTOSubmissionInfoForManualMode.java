
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOSubmissionInfoForManualMode implements Serializable{
	
	long subInfoManualModeId;
	String workspaceId;
	String region;
	String submissionId;
	int nodeId;	
	int tranNo;
	String refID;
	String operation;
	String relSeqNo;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	
	String workSpaceDesc;
	String countryCode;
	String currentSeqNumber;
	String lastPublishedVersion;
	String submissionPath;
	Timestamp submitedOn;
	int submitedBy;
	String submissionType;
	Timestamp dateOfSubmission;
	String subRelatedSeqNo;
	char confirm;
	String submissionMode;
	int nodeNo;
	String nodeName;
	String nodeDisplayName;
	String folderName;
	int parentNodeId;
	
	
	public long getSubInfoManualModeId() {
		return subInfoManualModeId;
	}
	public void setSubInfoManualModeId(long subInfoManualModeId) {
		this.subInfoManualModeId = subInfoManualModeId;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSubmissionId() {
		return submissionId;
	}
	public void setSubmissionId(String submissionId) {
		this.submissionId = submissionId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getRefID() {
		return refID;
	}
	public void setRefID(String refID) {
		this.refID = refID;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getRelSeqNo() {
		return relSeqNo;
	}
	public void setRelSeqNo(String relSeqNo) {
		this.relSeqNo = relSeqNo;
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
	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}
	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCurrentSeqNumber() {
		return currentSeqNumber;
	}
	public void setCurrentSeqNumber(String currentSeqNumber) {
		this.currentSeqNumber = currentSeqNumber;
	}
	public String getLastPublishedVersion() {
		return lastPublishedVersion;
	}
	public void setLastPublishedVersion(String lastPublishedVersion) {
		this.lastPublishedVersion = lastPublishedVersion;
	}
	public String getSubmissionPath() {
		return submissionPath;
	}
	public void setSubmissionPath(String submissionPath) {
		this.submissionPath = submissionPath;
	}
	public Timestamp getSubmitedOn() {
		return submitedOn;
	}
	public void setSubmitedOn(Timestamp submitedOn) {
		this.submitedOn = submitedOn;
	}
	public int getSubmitedBy() {
		return submitedBy;
	}
	public void setSubmitedBy(int submitedBy) {
		this.submitedBy = submitedBy;
	}
	public String getSubmissionType() {
		return submissionType;
	}
	public void setSubmissionType(String submissionType) {
		this.submissionType = submissionType;
	}
	public Timestamp getDateOfSubmission() {
		return dateOfSubmission;
	}
	public void setDateOfSubmission(Timestamp dateOfSubmission) {
		this.dateOfSubmission = dateOfSubmission;
	}
	public char getConfirm() {
		return confirm;
	}
	public void setConfirm(char confirm) {
		this.confirm = confirm;
	}
	public String getSubmissionMode() {
		return submissionMode;
	}
	public void setSubmissionMode(String submissionMode) {
		this.submissionMode = submissionMode;
	}
	public int getNodeNo() {
		return nodeNo;
	}
	public void setNodeNo(int nodeNo) {
		this.nodeNo = nodeNo;
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
	public int getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public String getSubRelatedSeqNo() {
		return subRelatedSeqNo;
	}
	public void setSubRelatedSeqNo(String subRelatedSeqNo) {
		this.subRelatedSeqNo = subRelatedSeqNo;
	}
	
}
