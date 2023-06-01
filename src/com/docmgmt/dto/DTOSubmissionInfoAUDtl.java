package com.docmgmt.dto;

import java.io.File;
import java.sql.Timestamp;

public class DTOSubmissionInfoAUDtl {
		String submissionInfoAUDtlId;
		String workspaceId;
	    String countryCode;
	    String currentSeqNumber;
	    String lastPublishedVersion;
	    String submissionPath;
	    Timestamp submitedOn;
	    int submitedBy;
	    String relatedSeqNo;
	    char confirm;
	    Timestamp modifyOn;
	    int modifyBy;
	    char statusIndi;
	    String workspaceDesc;
	    String countryName;
	    String agencyName;
	    String labelId;
	    String submissionMode;  // single, grouping, Work_sharing etc
	    String eSubmissionId; 
	    char RMSSubmited;//'Y' or 'N'
	    String deleted;
	    String sequenceType; 
	    String sequenceDescription; 
	    String sequenceDescriptionValue; 
	    String ARTGNumber; 
	    
	    
	    public String getSubmissionInfoAUDtlId() {
			return submissionInfoAUDtlId;
		}
		public void setSubmissionInfoAUDtlId(String submissionInfoAUDtlId) {
			this.submissionInfoAUDtlId = submissionInfoAUDtlId;
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
		public String geteSubmissionId() {
			return eSubmissionId;
		}
		public void seteSubmissionId(String eSubmissionId) {
			this.eSubmissionId = eSubmissionId;
		}
		public char getRMSSubmited() {
			return RMSSubmited;
		}
		public void setRMSSubmited(char rMSSubmited) {
			RMSSubmited = rMSSubmited;
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
		public String getSequenceType() {
			return sequenceType;
		}
		public void setSequenceType(String sequenceType) {
			this.sequenceType = sequenceType;
		}
		public String getSequenceDescription() {
			return sequenceDescription;
		}
		public void setSequenceDescription(String sequenceDescription) {
			this.sequenceDescription = sequenceDescription;
		}
		public String getSequenceDescriptionValue() {
			return sequenceDescriptionValue;
		}
		public void setSequenceDescriptionValue(String sequenceDescriptionValue) {
			this.sequenceDescriptionValue = sequenceDescriptionValue;
		}
		public String getARTGNumber() {
			return ARTGNumber;
		}
		public void setARTGNumber(String aRTGNumber) {
			ARTGNumber = aRTGNumber;
		}
		
	    
	  
	    
	 
	
	 

}
