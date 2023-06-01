package com.docmgmt.dto;

import java.io.File;
import java.sql.Timestamp;

public class DTOSubmissionInfoCADtl {
	
		String submissionInfoCADtlId;
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
	    String deleted;
	    String dossierIdentifier;
	    String sequenceDescriptionFlag;
	    String regulatoryActivityType; 
	    String sequenceDescription; 
	    String sequenceDescriptionValue; 
	   
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
	    
		public String getSubmissionInfoCADtlId() {
			return submissionInfoCADtlId;
		}
		public void setSubmissionInfoCADtlId(String submissionInfoCADtlId) {
			this.submissionInfoCADtlId = submissionInfoCADtlId;
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

		public String getDossierIdentifier() {
			return dossierIdentifier;
		}

		public void setDossierIdentifier(String dossierIdentifier) {
			this.dossierIdentifier = dossierIdentifier;
		}

		public String getRegulatoryActivityType() {
			return regulatoryActivityType;
		}

		public void setRegulatoryActivityType(String regulatoryActivityType) {
			this.regulatoryActivityType = regulatoryActivityType;
		}

		public String getSequenceDescriptionFlag() {
			return sequenceDescriptionFlag;
		}

		public void setSequenceDescriptionFlag(String sequenceDescriptionFlag) {
			this.sequenceDescriptionFlag = sequenceDescriptionFlag;
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
	
	 
}
