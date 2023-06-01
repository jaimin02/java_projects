package com.docmgmt.dto;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class DTOSubmissionInfoZADtl implements Serializable{

    String submissionInfoZADtlId;
	String workspaceId;
    String countryCode;
    String currentSeqNumber;
    String lastPublishedVersion;
    String submissionPath;
    Timestamp submitedOn;
    int submitedBy;
    String submissionType;
    Timestamp dateOfSubmission; // = Date of Application
    String relatedSeqNo;
    char confirm;
    Timestamp modifyOn;
    int modifyBy;
    char statusIndi;
    String workspaceDesc;
    String countryName;
    String agencyName;
    String labelId;
    String submissionMode; // ESG, Email,Post etc  ( Transmission Media
    String applicationNo;  // Application reference Number  or tracking Number
    char RMSSubmited;//'Y' or 'N'
    
    List<DTOSubmissionInfoGCCSubDtl> cmsDtls;
    String subVariationMode;  // single, grouping, Work_sharing etc
    String deleted;
    
    String efficacy; // i.e. : Clinical,non Clinical,bs,other,Not Applicable
    String efficacyDescription; // if efficacy is other then Description should not be blank.
    String propriateryName; // for multiple Application
    
    String propriateryNameDtl;
    
    public String getPropriateryNameDtl() {
		return propriateryNameDtl;
	}

	public void setPropriateryNameDtl(String propriateryNameDtl) {
		this.propriateryNameDtl = propriateryNameDtl;
	}

	String dosageForm;
    
	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	public String getEfficacy() {
		return efficacy;
	}

	public void setEfficacy(String efficacy) {
		this.efficacy = efficacy;
	}

	public String getEfficacyDescription() {
		return efficacyDescription;
	}

	public void setEfficacyDescription(String efficacyDescription) {
		this.efficacyDescription = efficacyDescription;
	}

	public String getPropriateryName() {
		return propriateryName;
	}

	public void setPropriateryName(String propriateryName) {
		this.propriateryName = propriateryName;
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

	public String getSubmissionInfoZADtlId() {
		return submissionInfoZADtlId;
	}

	public void setSubmissionInfoZADtlId(String submissionInfoGCCDtlId) {
		this.submissionInfoZADtlId = submissionInfoGCCDtlId;
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

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

    
}
