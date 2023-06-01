package com.docmgmt.dto;

public class PublishAttrForm 
{
	 	String clientName;
	    String submissionName;
	    String submissioneCTDId;
	    String nodeId;
	    String wsId;
	    boolean isRecompile;
	    String baseWorkspaceFolder;
	    String indexFileName;
	    String submissionFlag;
	    
	    // Eu-Envelope Detail
	    String country;
	    String applicationNumber;
	    String applicant;
	    String agencyName;
	    String atc;
	    String procedureType;
	    String submissionType;
	    String inventedName;
	    String inn;
	    String submissionDescription;
	       
	    String companyName;
	    
	    String prodName;
	    String productType;
	    String applicationType;
	    String seqNumber;
	    String relatedSeqNumber;
	    String submissionType_eu;
		String submissionType_us;
		String submissionType_ca;
		String labelName;
		String labelId;
		int labelNo;
		String publishDestinationPath;
		
		char RMSSelected;
		
		String submissionUnitType;
		String regionalDtdVersion;
		String submissionId;
		
		/*Added For EU v1.4*/
		char addTT;
		String submissionMode;
		String highLvlNo;
		
		
		String dosageForm;
		String propriteryNames;
		String efficacy;
		String ganelicForm;
		
		String dmfHolder;
		String dmfNumber;
		String pmfNumber;
		String pmfHolder;
		String paragraph13;
		
		String submissionSubType;
		String[] applicantContactList;
		String applicationId;
		
		public String suppEffectiveDateType;
		public String crossReferenceNumber;
		public String crossRefAppType;
		
		public String uuid;
		public String EUDTDVersion;
		public String subTypes_za20;
		
		public boolean isRecompile() {
			return isRecompile;
		}
		public void setRecompile(boolean isRecompile) {
			this.isRecompile = isRecompile;
		}
		public String getEUDTDVersion() {
			return EUDTDVersion;
		}
		public void setEUDTDVersion(String eUDTDVersion) {
			EUDTDVersion = eUDTDVersion;
		}
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		String AAN;

		public String getAAN() {
			return AAN;
		}
		public void setAAN(String aAN) {
			AAN = aAN;
		}
		public String getRegActLead() {
			return RegActLead;
		}
		public void setRegActLead(String regActLead) {
			RegActLead = regActLead;
		}
		String fieldTypes;
		public String getFieldTypes() {
			return fieldTypes;
		}
		public void setFieldTypes(String fieldTypes) {
			this.fieldTypes = fieldTypes;
		}
		String RegActLead;
		String eSubmissionId;
		String sequenceType;
		String sequenceDescription;
		public String getSequenceDescriptionValue() {
			return sequenceDescriptionValue;
		}
		public void setSequenceDescriptionValue(String sequenceDescriptionValue) {
			this.sequenceDescriptionValue = sequenceDescriptionValue;
		}
		public String getArtgNumber() {
			return artgNumber;
		}
		public void setArtgNumber(String artgNumber) {
			this.artgNumber = artgNumber;
		}
		String sequenceDescriptionValue;
		String artgNumber;
		String licensee;
		String licenseeName;
		String licenseeType;
		String email;
		String dossierIdentifier;
		String dossierType;
		String regActType;
		String seqDescFlag;
		
		public String getDossierIdentifier() {
			return dossierIdentifier;
		}
		public void setDossierIdentifier(String dossierIdentifier) {
			this.dossierIdentifier = dossierIdentifier;
		}
		public String getDossierType() {
			return dossierType;
		}
		public void setDossierType(String dossierType) {
			this.dossierType = dossierType;
		}
		public String getRegActType() {
			return regActType;
		}
		public void setRegActType(String regActType) {
			this.regActType = regActType;
		}
		public String getSeqDescFlag() {
			return seqDescFlag;
		}
		public void setSeqDescFlag(String seqDescFlag) {
			this.seqDescFlag = seqDescFlag;
		}
		public String getLicensee() {
			return licensee;
		}
		public void setLicensee(String licensee) {
			this.licensee = licensee;
		}
		
		public String getLicenseeName() {
			return licenseeName;
		}
		public void setLicenseeName(String licenseeName) {
			this.licenseeName = licenseeName;
		}
		public String getLicenseeType() {
			return licenseeType;
		}
		public void setLicenseeType(String licenseeType) {
			this.licenseeType = licenseeType;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String geteSubmissionId() {
			return eSubmissionId;
		}
		public void seteSubmissionId(String eSubmissionId) {
			this.eSubmissionId = eSubmissionId;
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
		
		
		public String getSuppEffectiveDateType() {
			return suppEffectiveDateType;
		}
		public void setSuppEffectiveDateType(String suppEffectiveDateType) {
			this.suppEffectiveDateType = suppEffectiveDateType;
		}
		public String getCrossReferenceNumber() {
			return crossReferenceNumber;
		}
		public void setCrossReferenceNumber(String crossReferenceNumber) {
			this.crossReferenceNumber = crossReferenceNumber;
		}
		public String getCrossRefAppType() {
			return crossRefAppType;
		}
		public void setCrossRefAppType(String crossRefAppType) {
			this.crossRefAppType = crossRefAppType;
		}
		public String getApplicationId() {
			return applicationId;
		}
		public void setApplicationId(String applicationId) {
			this.applicationId = applicationId;
		}
		public String getSubmissionSubType() {
			return submissionSubType;
		}
		public void setSubmissionSubType(String submissionSubType) {
			this.submissionSubType = submissionSubType;
		}
		public String[] getApplicantContactList() {
			return applicantContactList;
		}
		public void setApplicantContactList(String[] applicantContactDetails) {
			this.applicantContactList = applicantContactDetails;
		}
		public String getParagraph13() {
			return paragraph13;
		}
		public void setParagraph13(String paragraph13) {
			this.paragraph13 = paragraph13;
		}
		public String getDmfHolder() {
			return dmfHolder;
		}
		public void setDmfHolder(String dmfHolder) {
			this.dmfHolder = dmfHolder;
		}
		public String getDmfNumber() {
			return dmfNumber;
		}
		public void setDmfNumber(String dmfNumber) {
			this.dmfNumber = dmfNumber;
		}
		public String getPmfNumber() {
			return pmfNumber;
		}
		public void setPmfNumber(String pmfNumber) {
			this.pmfNumber = pmfNumber;
		}
		public String getPmfHolder() {
			return pmfHolder;
		}
		public void setPmfHolder(String pmfHolder) {
			this.pmfHolder = pmfHolder;
		}
		public String getGanelicForm() {
			return ganelicForm;
		}
		public void setGanelicForm(String ganelicForm) {
			this.ganelicForm = ganelicForm;
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
		String efficacyDescription;
		
		public String getPropriteryNames() {
			return propriteryNames;
		}
		public void setPropriteryNames(String propriteryNames) {
			this.propriteryNames = propriteryNames;
		}
		public String getDosageForm() {
			return dosageForm;
		}
		public void setDosageForm(String dosageForm) {
			this.dosageForm = dosageForm;
		}
		public String getSubmissionMode() {
			return submissionMode;
		}
		public void setSubmissionMode(String submissionMode) {
			this.submissionMode = submissionMode;
		}
		public String getHighLvlNo() {
			return highLvlNo;
		}
		public void setHighLvlNo(String highLvlNo) {
			this.highLvlNo = highLvlNo;
		}
		public char getAddTT() {
			return addTT;
		}
		public void setAddTT(char addTT) {
			this.addTT = addTT;
		}
		public String getSubmissionId() {
			return submissionId;
		}
		public void setSubmissionId(String submissionId) {
			this.submissionId = submissionId;
		}
		public String getPublishDestinationPath() {
			return publishDestinationPath;
		}
		public void setPublishDestinationPath(String publishDestinationPath) {
			this.publishDestinationPath = publishDestinationPath;
		}
		public int getLabelNo() {
			return labelNo;
		}
		public void setLabelNo(int labelNo) {
			this.labelNo = labelNo;
		}
		public String getSubmissionType_eu() {
			return submissionType_eu;
		}
		public void setSubmissionType_eu(String submissionType_eu) {
			this.submissionType_eu = submissionType_eu;
		}
		public String getSubmissionType_us() {
			return submissionType_us;
		}
		public void setSubmissionType_us(String submissionType_us) {
			this.submissionType_us = submissionType_us;
		}
		public String getApplicationType() {
			return applicationType;
		}
		public void setApplicationType(String applicationType) {
			this.applicationType = applicationType;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getProdName() {
			return prodName;
		}
		public void setProdName(String prodName) {
			this.prodName = prodName;
		}
		public String getProductType() {
			return productType;
		}
		public void setProductType(String productType) {
			this.productType = productType;
		}
		public String getRelatedSeqNumber() {
			return relatedSeqNumber;
		}
		public void setRelatedSeqNumber(String relatedSeqNumber) {
			this.relatedSeqNumber = relatedSeqNumber;
		}
		public String getSeqNumber() {
			return seqNumber;
		}
		public void setSeqNumber(String seqNumber) {
			this.seqNumber = seqNumber;
		}
		/**
		 * @return Returns the agencyName.
		 */
		public String getAgencyName() {
			return agencyName;
		}
		/**
		 * @param agencyName The agencyName to set.
		 */
		public void setAgencyName(String agencyName) {
			this.agencyName = agencyName;
		}
		/**
		 * @return Returns the applicant.
		 */
		public String getApplicant() {
			return applicant;
		}
		/**
		 * @param applicant The applicant to set.
		 */
		public void setApplicant(String applicant) {
			this.applicant = applicant;
		}
		/**
		 * @return Returns the applicationNumber.
		 */
		public String getApplicationNumber() {
			return applicationNumber;
		}
		/**
		 * @param applicationNumber The applicationNumber to set.
		 */
		public void setApplicationNumber(String applicationNumber) {
			this.applicationNumber = applicationNumber;
		}
		/**
		 * @return Returns the atc.
		 */
		public String getAtc() {
			return atc;
		}
		/**
		 * @param atc The atc to set.
		 */
		public void setAtc(String atc) {
			this.atc = atc;
		}
		/**
		 * @return Returns the baseWorkspaceFolder.
		 */
		public String getBaseWorkspaceFolder() {
			return baseWorkspaceFolder;
		}
		/**
		 * @param baseWorkspaceFolder The baseWorkspaceFolder to set.
		 */
		public void setBaseWorkspaceFolder(String baseWorkspaceFolder) {
			this.baseWorkspaceFolder = baseWorkspaceFolder;
		}
		/**
		 * @return Returns the country.
		 */
		public String getCountry() {
			return country;
		}
		/**
		 * @param country The country to set.
		 */
		public void setCountry(String country) {
			this.country = country;
		}
		/**
		 * @return Returns the indexFileName.
		 */
		public String getIndexFileName() {
			return indexFileName;
		}
		/**
		 * @param indexFileName The indexFileName to set.
		 */
		public void setIndexFileName(String indexFileName) {
			this.indexFileName = indexFileName;
		}
		/**
		 * @return Returns the inn.
		 */
		public String getInn() {
			return inn;
		}
		/**
		 * @param inn The inn to set.
		 */
		public void setInn(String inn) {
			this.inn = inn;
		}
		/**
		 * @return Returns the inventedName.
		 */
		public String getInventedName() {
			return inventedName;
		}
		/**
		 * @param inventedName The inventedName to set.
		 */
		public void setInventedName(String inventedName) {
			this.inventedName = inventedName;
		}
		/**
		 * @return Returns the procedureType.
		 */
		public String getProcedureType() {
			return procedureType;
		}
		/**
		 * @param procedureType The procedureType to set.
		 */
		public void setProcedureType(String procedureType) {
			this.procedureType = procedureType;
		}
		/**
		 * @return Returns the submissionDescription.
		 */
		public String getSubmissionDescription() {
			return submissionDescription;
		}
		/**
		 * @param submissionDescription The submissionDescription to set.
		 */
		public void setSubmissionDescription(String submissionDescription) {
			this.submissionDescription = submissionDescription;
		}
		/**
		 * @return Returns the submissionFlag.
		 */
		public String getSubmissionFlag() {
			return submissionFlag;
		}
		/**
		 * @param submissionFlag The submissionFlag to set.
		 */
		public void setSubmissionFlag(String submissionFlag) {
			this.submissionFlag = submissionFlag;
		}
		/**
		 * @return Returns the submissionType.
		 */
		public String getSubmissionType() {
			return submissionType;
		}
		/**
		 * @param submissionType The submissionType to set.
		 */
		public void setSubmissionType(String submissionType) {
			this.submissionType = submissionType;
		}
	    public String getClientName() {
	        return clientName;
	    }
	    public void setClientName(String clientName) {
	        this.clientName = clientName;
	    }
	    public String getSubmissionName() {
	        return submissionName;
	    }
	    public void setSubmissionName(String submissionName) {
	        this.submissionName = submissionName;
	    }
	    public String getSubmissioneCTDId() {
	        return submissioneCTDId;
	    }
	    public void setSubmissioneCTDId(String submissioneCTDId) {
	        this.submissioneCTDId = submissioneCTDId;
	    }
	    public String getNodeId() {
	        return nodeId;
	    }
	    public void setNodeId(String nodeId) {
	        this.nodeId = nodeId;
	    }
		/**
		 * @return Returns the wsId.
		 */
		public String getWsId() {
			return wsId;
		}
		/**
		 * @param wsId The wsId to set.
		 */
		public void setWsId(String wsId) {
			this.wsId = wsId;
		}
		/**
		 * @return Returns the labelId.
		 */
		public String getLabelId() {
			return labelId;
		}
		/**
		 * @param labelId The labelId to set.
		 */
		public void setLabelId(String labelId) {
			this.labelId = labelId;
		}
		/**
		 * @return Returns the labelName.
		 */
		public String getLabelName() {
			return labelName;
		}
		/**
		 * @param labelName The labelName to set.
		 */
		public void setLabelName(String labelName) {
			this.labelName = labelName;
		}
		public char getRMSSelected() {
			return RMSSelected;
		}
		public void setRMSSelected(char selected) {
			RMSSelected = selected;
		}
		
		public String getSubmissionUnitType() {
			return submissionUnitType;
		}
		public void setSubmissionUnitType(String submissionUnitType) {
			this.submissionUnitType = submissionUnitType;
		}
		
		public String getRegionalDtdVersion() {
			return regionalDtdVersion;
		}
		public void setRegionalDtdVersion(String regionalDtdVersion) {
			this.regionalDtdVersion = regionalDtdVersion;
		}
		public String getSubmissionType_ca() {
			return submissionType_ca;
		}
		public void setSubmissionType_ca(String submissionType_ca) {
			this.submissionType_ca = submissionType_ca;
		}
		

		public String getSubTypes_za20() {
			return subTypes_za20;
		}
		public void setSubTypes_za20(String subTypesZa20) {
			subTypes_za20 = subTypesZa20;
		}
}
