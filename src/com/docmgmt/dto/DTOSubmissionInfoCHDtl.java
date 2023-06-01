
package com.docmgmt.dto;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;

public class DTOSubmissionInfoCHDtl implements Serializable{

    String submissionInfoCHDtlId;
	String workspaceId;
    String countryCode;
    String currentSeqNumber;
    String lastPublishedVersion;
    String submissionPath;
    Timestamp submitedOn;
    int submitedBy;
    String submissionType;
    Timestamp dateOfSubmission;
    String relatedSeqNo;
    char confirm;
    Timestamp modifyOn;
    int modifyBy;
    char statusIndi;
    String workspaceDesc;
    String countryName;
    String agencyName;
    String labelId;
    String submissionMode;
    String applicationNo;
    char RMSSubmited;      		//'Y' or 'N'
    
    String ganelicForm;
    String paragraph13;
    String subVariationMode;
    String deleted;

	public String getParagraph13() {
		return paragraph13;
	}

	public void setParagraph13(String paragraph13) {
		this.paragraph13 = paragraph13;
	}

	
	
    public String getGanelicForm() {
		return ganelicForm;
	}

	public void setGanelicForm(String ganelicForm) {
		this.ganelicForm = ganelicForm;
	}

    
	public String getDeleted() {		
		File folder=new File(submissionPath);
		if (folder!=null)
			if (folder.exists())
				deleted="false";
			else
				deleted="true";
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}	

	public String getSubVariationMode() {
		return subVariationMode;
	}

	public void setSubVariationMode(String subVariationMode) {
		this.subVariationMode = subVariationMode;
	}

	public String getSubmissionInfoCHDtlId() {
		return submissionInfoCHDtlId;
	}

	public void setSubmissionInfoCHDtlId(String submissionInfoCHDtlId) {
		this.submissionInfoCHDtlId = submissionInfoCHDtlId;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
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

	public String getRelatedSeqNo() {
		return relatedSeqNo;
	}

	public void setRelatedSeqNo(String relatedSeqNo) {
		this.relatedSeqNo = relatedSeqNo;
	}

	public char getConfirm() {
		return confirm;
	}

	public void setConfirm(char confirm) {
		this.confirm = confirm;
	}

	public Timestamp getModifyOn() {
		return modifyOn;
	}

	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}

	public int getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}

	public char getStatusIndi() {
		return statusIndi;
	}

	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}

	public String getWorkspaceDesc() {
		return workspaceDesc;
	}

	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getSubmissionMode() {
		return submissionMode;
	}

	public void setSubmissionMode(String submissionMode) {
		this.submissionMode = submissionMode;
	}


	public char getRMSSubmited() {
		return RMSSubmited;
	}

	public void setRMSSubmited(char submited) {
		RMSSubmited = submited;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicatioNo) {
		this.applicationNo = applicatioNo;
	}

	
	

    
}
