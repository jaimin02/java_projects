package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOSubmissionInfoDMSDtl implements Serializable {
	private static final long serialVersionUID = -2371372606501083571L;
	long SubInfoDMSDtlId;
	String Workspaceid; 
	String CurrentSeqNumber;
	String RelatedSeqNumber;
	String LabelId;
	String SubmissionMode;
	String SubmissionType;
	String SubmissionDesc;
	Timestamp DateOfSubmission;
	Timestamp SubmittedOn;
	char Confirm;
	int ConfirmBy;
	String Remark;
	int ModifyBy;
	Timestamp ModifyOn;
	char StatusIndi;
	//Form view
	int LabelNo;
	String ConfirmedBy;
	String ModifiedBy;
	
	String SubmissionPath; 
	String ISTDateTime;
	String ESTDateTime;
	String workspaceDesc;
	
	public String getWorkspaceDesc() {
		return workspaceDesc;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}
	
	public String getISTDateTime() {
		return ISTDateTime;
	}
	public void setISTDateTime(String iSTDateTime) {
		ISTDateTime = iSTDateTime;
	}
	public String getESTDateTime() {
		return ESTDateTime;
	}
	public void setESTDateTime(String eSTDateTime) {
		ESTDateTime = eSTDateTime;
	}
	
	public String getSubmissionPath() {
		return SubmissionPath;
	}
	public void setSubmissionPath(String submissionPath) {
		SubmissionPath = submissionPath;
	}
	public long getSubInfoDMSDtlId() {
		return SubInfoDMSDtlId;
	}
	public void setSubInfoDMSDtlId(long subInfoDMSDtlId) {
		SubInfoDMSDtlId = subInfoDMSDtlId;
	}
	public String getWorkspaceid() {
		return Workspaceid;
	}
	public void setWorkspaceid(String workspaceid) {
		Workspaceid = workspaceid;
	}
	public String getCurrentSeqNumber() {
		return CurrentSeqNumber;
	}
	public void setCurrentSeqNumber(String currentSeqNumber) {
		CurrentSeqNumber = currentSeqNumber;
	}
	public String getRelatedSeqNumber() {
		return RelatedSeqNumber;
	}
	public void setRelatedSeqNumber(String relatedSeqNumber) {
		RelatedSeqNumber = relatedSeqNumber;
	}
	public String getLabelId() {
		return LabelId;
	}
	public void setLabelId(String labelId) {
		LabelId = labelId;
	}
	public String getSubmissionMode() {
		return SubmissionMode;
	}
	public void setSubmissionMode(String submissionMode) {
		SubmissionMode = submissionMode;
	}
	public String getSubmissionType() {
		return SubmissionType;
	}
	public void setSubmissionType(String submissionType) {
		SubmissionType = submissionType;
	}
	public String getSubmissionDesc() {
		return SubmissionDesc;
	}
	public void setSubmissionDesc(String submissionDesc) {
		SubmissionDesc = submissionDesc;
	}
	public Timestamp getDateOfSubmission() {
		return DateOfSubmission;
	}
	public void setDateOfSubmission(Timestamp ts) {
		DateOfSubmission = ts;
	}
	public Timestamp getSubmittedOn() {
		return SubmittedOn;
	}
	public void setSubmittedOn(Timestamp submittedOn) {
		SubmittedOn = submittedOn;
	}
	public char getConfirm() {
		return Confirm;
	}
	public void setConfirm(char confirm) {
		Confirm = confirm;
	}
	public int getConfirmBy() {
		return ConfirmBy;
	}
	public void setConfirmBy(int confirmBy) {
		ConfirmBy = confirmBy;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public int getModifyBy() {
		return ModifyBy;
	}
	public void setModifyBy(int modifyBy) {
		ModifyBy = modifyBy;
	}
	public Timestamp getModifyOn() {
		return ModifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		ModifyOn = modifyOn;
	}
	public char getStatusIndi() {
		return StatusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		StatusIndi = statusIndi;
	}
	public int getLabelNo() {
		return LabelNo;
	}
	public void setLabelNo(int labelNo) {
		LabelNo = labelNo;
	}
	public String getConfirmedBy() {
		return ConfirmedBy;
	}
	public void setConfirmedBy(String confirmedBy) {
		ConfirmedBy = confirmedBy;
	}
	public String getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	
}