package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOSubmissionDtl implements Serializable {
	String WorkspaceDesc;
	String WorkspaceId;
	String CurrentSequenceNumber;
	Timestamp SubmitedOn;
	char confirm;
	public String getWorkspaceDesc() {
		return WorkspaceDesc;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		WorkspaceDesc = workspaceDesc;
	}
	public String getWorkspaceId() {
		return WorkspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		WorkspaceId = workspaceId;
	}
	public String getCurrentSequenceNumber() {
		return CurrentSequenceNumber;
	}
	public void setCurrentSequenceNumber(String currentSequenceNumber) {
		CurrentSequenceNumber = currentSequenceNumber;
	}
	
	public Timestamp getSubmitedOn() {
		return SubmitedOn;
	}
	public void setSubmitedOn(Timestamp timestamp) {
		SubmitedOn = timestamp;
	}
	public char getConfirm() {
		return confirm;
	}
	public void setConfirm(char confirm) {
		this.confirm = confirm;
	}
}
