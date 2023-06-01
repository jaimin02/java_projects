package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

public class DTOSubmissionMst implements Serializable {

	String licensee;
	String licenseeName;
	String licenseeType;
	String email;
	String eSubmissionId;
	String AAN;
	String RegActLead;
	String artgNumner;
	
	String dossierIdentifier;
	String dossierType;
	
	String sequenceType;
	String sequenceDescription;
	String sequenceDescriptionValue;
	
	String WorkspaceId;
	String applicationNo;
	String submissionId;
	int labelNo;
	String submissionpath;
	Timestamp submittedOn;
	int submittedBy;
	String countrycode;
	String applicant;
	
	String regActType;
	String sequenceDescriptionFlag;
	String uuid;
	String EUDtdVersion;
	public String getEUDtdVersion() {
		return EUDtdVersion;
	}

	public void setEUDtdVersion(String eUDtdVersion) {
		EUDtdVersion = eUDtdVersion;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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

	public String getArtgNumner() {
		return artgNumner;
	}

	public void setArtgNumner(String artgNumner) {
		this.artgNumner = artgNumner;
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

	public int getTotalLeadNode() {
		return totalLeadNode;
	}

	public void setTotalLeadNode(int totalLeadNode) {
		this.totalLeadNode = totalLeadNode;
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

	public String getRegActType() {
		return regActType;
	}

	public void setRegActType(String regActType) {
		this.regActType = regActType;
	}

	public String getSequenceDescriptionFlag() {
		return sequenceDescriptionFlag;
	}

	public void setSequenceDescriptionFlag(String sequenceDescriptionFlag) {
		this.sequenceDescriptionFlag = sequenceDescriptionFlag;
	}

	String agencyName;
	String atc;
	String procedureType;
	String submissionType;
	String inventedName;
	String inn;
	String submissionDescription;
	String companyName;
	Date dateOfSubmission;
	String productName;
	String productType;
	String applicationType;
	String countryName;
	String workspaceDesc;
	String labelId;
	String lastPublishedVersion;
	String reletedSeqNo;
	char confirm;
	String countryRegion;
	char statusIndi;
	String currentSequenceNumber;
	String submissionMode;
	String countryCodeName;
	String highLvlNo;
	String regionalDTDVersion;
	String trackingNo;
	String submissionUnitType;
	String dosageForm;
	String propriateryName;

	String dmfNumber;
	String dmfHolder;
	String pmfNumber;
	String pmfHolder;
	String paragraph13;
	
	String applicationId;
	
	

	String efficacy;
	String efficacyDescription;
	String ganelicForm;
	DTOSubmissionInfoUSDtl submissionInfoUSDtl;
	DTOSubmissionInfoEUDtl submissionInfoEUDtl;
	DTOSubmissionInfoCADtl submissionInfoCADtl;


	DTOSubmissionInfoEU14Dtl submissionInfoEU14Dtl;
	DTOSubmissionInfoZADtl submissionInfoZADtl;
	DTOSubmissionInfoCHDtl submissionInfoCHDtl;
	DTOSubmissionInfoAUDtl submissionInfoAUDtl;
	DTOSubmissionInfoTHDtl submissionInfoTHDtl;
	DTOSubmissionInfoGCCDtl submissionInfoGCCDtl;
	

	Vector<DTOSubmissionInfoEUDtl> submissionInfoEUDtls;
	Vector<DTOSubmissionInfoUSDtl> submissionInfoUSDtls;
	Vector<DTOSubmissionInfoEU14Dtl> submissionInfoEU14Dtls;
	Vector<DTOSubmissionInfoCADtl> submissionInfoCADtls;
	Vector<DTOSubmissionInfoZADtl> submissionInfoZADtls;
	Vector<DTOSubmissionInfoCHDtl> submissionInfoCHDtls;
	Vector<DTOSubmissionInfoEU20Dtl> submissionInfoEU20Dtls;
	Vector<DTOSubmissionInfoAUDtl> submissionInfoAUDtls;
	Vector<DTOSubmissionInfoTHDtl> submissionInfoTHDtls;
	Vector<DTOSubmissionInfoGCCDtl> submissionInfoGCCDtls;
	
	String isGroupSubmission;
	
	

	public DTOSubmissionInfoAUDtl getSubmissionInfoAUDtl() {
		return submissionInfoAUDtl;
	}

	public void setSubmissionInfoAUDtl(DTOSubmissionInfoAUDtl submissionInfoAUDtl) {
		this.submissionInfoAUDtl = submissionInfoAUDtl;
	}

	public Vector<DTOSubmissionInfoAUDtl> getSubmissionInfoAUDtls() {
		return submissionInfoAUDtls;
	}

	public void setSubmissionInfoAUDtls(
			Vector<DTOSubmissionInfoAUDtl> submissionInfoAUDtls) {
		this.submissionInfoAUDtls = submissionInfoAUDtls;
	}

	
	public DTOSubmissionInfoTHDtl getSubmissionInfoTHDtl() {
		return submissionInfoTHDtl;
	}

	public void setSubmissionInfoTHDtl(DTOSubmissionInfoTHDtl submissionInfoTHDtl) {
		this.submissionInfoTHDtl = submissionInfoTHDtl;
	}

	public Vector<DTOSubmissionInfoTHDtl> getSubmissionInfoTHDtls() {
		return submissionInfoTHDtls;
	}

	public void setSubmissionInfoTHDtls(
			Vector<DTOSubmissionInfoTHDtl> submissionInfoTHDtls) {
		this.submissionInfoTHDtls = submissionInfoTHDtls;
	}

	public String getIsGroupSubmission() {
		return isGroupSubmission;
	}

	public void setIsGroupSubmission(String isGroupSubmission) {
		this.isGroupSubmission = isGroupSubmission;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getGanelicForm() {
		return ganelicForm;
	}

	public void setGanelicForm(String ganelicForm) {
		this.ganelicForm = ganelicForm;
	}
	public String getParagraph13() {
		return paragraph13;
	}

	public void setParagraph13(String paragraph13) {
		this.paragraph13 = paragraph13;
	}
	public Vector<DTOSubmissionInfoEU20Dtl> getSubmissionInfoEU20Dtls() {
		return submissionInfoEU20Dtls;
	}

	public void setSubmissionInfoEU20Dtls(
			Vector<DTOSubmissionInfoEU20Dtl> submissionInfoEU20Dtls) {
		this.submissionInfoEU20Dtls = submissionInfoEU20Dtls;
	}

	ArrayList<DTOSubmissionInfoDMSDtl> submissionInfoDMSDtlsList;

	int totalLeadNode;

	
	public String getDmfNumber() {
		return dmfNumber;
	}

	public void setDmfNumber(String dmfNumber) {
		this.dmfNumber = dmfNumber;
	}

	public String getDmfHolder() {
		return dmfHolder;
	}

	public void setDmfHolder(String dmfHolder) {
		this.dmfHolder = dmfHolder;
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

	
	public DTOSubmissionInfoCHDtl getSubmissionInfoCHDtl() {
		return submissionInfoCHDtl;
	}

	public void setSubmissionInfoCHDtl(
			DTOSubmissionInfoCHDtl submissionInfoCHDtl) {
		this.submissionInfoCHDtl = submissionInfoCHDtl;
	}

	public Vector<DTOSubmissionInfoCHDtl> getSubmissionInfoCHDtls() {
		return submissionInfoCHDtls;
	}

	public void setSubmissionInfoCHDtls(
			Vector<DTOSubmissionInfoCHDtl> submissionInfoCHDtls) {
		this.submissionInfoCHDtls = submissionInfoCHDtls;
	}

	public String getWorkspaceId() {
		return WorkspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		WorkspaceId = workspaceId;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(String submissionId) {
		this.submissionId = submissionId;
	}

	public int getLabelNo() {
		return labelNo;
	}

	public void setLabelNo(int labelNo) {
		this.labelNo = labelNo;
	}

	public String getSubmissionpath() {
		return submissionpath;
	}

	public void setSubmissionpath(String submissionpath) {
		this.submissionpath = submissionpath;
	}

	public Timestamp getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(Timestamp submittedOn) {
		this.submittedOn = submittedOn;
	}

	public int getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(int submittedBy) {
		this.submittedBy = submittedBy;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAtc() {
		return atc;
	}

	public void setAtc(String atc) {
		this.atc = atc;
	}

	public String getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}

	public String getSubmissionType() {
		return submissionType;
	}

	public void setSubmissionType(String submissionType) {
		this.submissionType = submissionType;
	}

	public String getInventedName() {
		return inventedName;
	}

	public void setInventedName(String inventedName) {
		this.inventedName = inventedName;
	}

	public String getInn() {
		return inn;
	}

	public void setInn(String inn) {
		this.inn = inn;
	}

	public String getSubmissionDescription() {
		return submissionDescription;
	}

	public void setSubmissionDescription(String submissionDescription) {
		this.submissionDescription = submissionDescription;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getDateOfSubmission() {
		return dateOfSubmission;
	}

	public void setDateOfSubmission(Date dateOfSubmission) {
		this.dateOfSubmission = dateOfSubmission;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getWorkspaceDesc() {
		return workspaceDesc;
	}

	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLastPublishedVersion() {
		return lastPublishedVersion;
	}

	public void setLastPublishedVersion(String lastPublishedVersion) {
		this.lastPublishedVersion = lastPublishedVersion;
	}

	public String getReletedSeqNo() {
		return reletedSeqNo;
	}

	public void setReletedSeqNo(String reletedSeqNo) {
		this.reletedSeqNo = reletedSeqNo;
	}

	public char getConfirm() {
		return confirm;
	}

	public void setConfirm(char confirm) {
		this.confirm = confirm;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	public char getStatusIndi() {
		return statusIndi;
	}

	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}

	public String getCurrentSequenceNumber() {
		return currentSequenceNumber;
	}

	public void setCurrentSequenceNumber(String currentSequenceNumber) {
		this.currentSequenceNumber = currentSequenceNumber;
	}

	public String getSubmissionMode() {
		return submissionMode;
	}

	public void setSubmissionMode(String submissionMode) {
		this.submissionMode = submissionMode;
	}

	public String getCountryCodeName() {
		return countryCodeName;
	}

	public void setCountryCodeName(String countryCodeName) {
		this.countryCodeName = countryCodeName;
	}

	public String getHighLvlNo() {
		return highLvlNo;
	}

	public void setHighLvlNo(String highLvlNo) {
		this.highLvlNo = highLvlNo;
	}

	public String getRegionalDTDVersion() {
		return regionalDTDVersion;
	}

	public void setRegionalDTDVersion(String regionalDTDVersion) {
		this.regionalDTDVersion = regionalDTDVersion;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getSubmissionUnitType() {
		return submissionUnitType;
	}

	public void setSubmissionUnitType(String submissionUnitType) {
		this.submissionUnitType = submissionUnitType;
	}

	public DTOSubmissionInfoUSDtl getSubmissionInfoUSDtl() {
		return submissionInfoUSDtl;
	}

	public void setSubmissionInfoUSDtl(
			DTOSubmissionInfoUSDtl submissionInfoUSDtl) {
		this.submissionInfoUSDtl = submissionInfoUSDtl;
	}

	public DTOSubmissionInfoZADtl getSubmissionInfoZADtl() {
		return submissionInfoZADtl;
	}

	public void setSubmissionInfoZADtl(
			DTOSubmissionInfoZADtl submissionInfoZADtl) {
		this.submissionInfoZADtl = submissionInfoZADtl;
	}

	public Vector<DTOSubmissionInfoZADtl> getSubmissionInfoZADtls() {
		return submissionInfoZADtls;
	}

	public void setSubmissionInfoZADtls(
			Vector<DTOSubmissionInfoZADtl> submissionInfoZADtls) {
		this.submissionInfoZADtls = submissionInfoZADtls;
	}

	public DTOSubmissionInfoEUDtl getSubmissionInfoEUDtl() {
		return submissionInfoEUDtl;
	}

	public void setSubmissionInfoEUDtl(
			DTOSubmissionInfoEUDtl submissionInfoEUDtl) {
		this.submissionInfoEUDtl = submissionInfoEUDtl;
	}

	public DTOSubmissionInfoCADtl getSubmissionInfoCADtl() {
		return submissionInfoCADtl;
	}

	public void setSubmissionInfoCADtl(
			DTOSubmissionInfoCADtl submissionInfoCADtl) {
		this.submissionInfoCADtl = submissionInfoCADtl;
	}

	public DTOSubmissionInfoEU14Dtl getSubmissionInfoEU14Dtl() {
		return submissionInfoEU14Dtl;
	}

	public void setSubmissionInfoEU14Dtl(
			DTOSubmissionInfoEU14Dtl submissionInfoEU14Dtl) {
		this.submissionInfoEU14Dtl = submissionInfoEU14Dtl;
	}

	public Vector<DTOSubmissionInfoEUDtl> getSubmissionInfoEUDtls() {
		return submissionInfoEUDtls;
	}

	public void setSubmissionInfoEUDtls(
			Vector<DTOSubmissionInfoEUDtl> submissionInfoEUDtls) {
		this.submissionInfoEUDtls = submissionInfoEUDtls;
	}

	public Vector<DTOSubmissionInfoUSDtl> getSubmissionInfoUSDtls() {
		return submissionInfoUSDtls;
	}

	public void setSubmissionInfoUSDtls(
			Vector<DTOSubmissionInfoUSDtl> submissionInfoUSDtls) {
		this.submissionInfoUSDtls = submissionInfoUSDtls;
	}

	public Vector<DTOSubmissionInfoEU14Dtl> getSubmissionInfoEU14Dtls() {
		return submissionInfoEU14Dtls;
	}

	public void setSubmissionInfoEU14Dtls(
			Vector<DTOSubmissionInfoEU14Dtl> submissionInfoEU14Dtls) {
		this.submissionInfoEU14Dtls = submissionInfoEU14Dtls;
	}

	public Vector<DTOSubmissionInfoCADtl> getSubmissionInfoCADtls() {
		return submissionInfoCADtls;
	}

	public void setSubmissionInfoCADtls(
			Vector<DTOSubmissionInfoCADtl> submissionInfoCADtls) {
		this.submissionInfoCADtls = submissionInfoCADtls;
	}

	public ArrayList<DTOSubmissionInfoDMSDtl> getSubmissionInfoDMSDtlsList() {
		return submissionInfoDMSDtlsList;
	}

	public void setSubmissionInfoDMSDtlsList(
			ArrayList<DTOSubmissionInfoDMSDtl> submissionInfoDMSDtlsList) {
		this.submissionInfoDMSDtlsList = submissionInfoDMSDtlsList;
	}

	public DTOSubmissionInfoGCCDtl getSubmissionInfoGCCDtl() {
		return submissionInfoGCCDtl;
	}

	public void setSubmissionInfoGCCDtl(DTOSubmissionInfoGCCDtl submissionInfoGCCDtl) {
		this.submissionInfoGCCDtl = submissionInfoGCCDtl;
	}

	public Vector<DTOSubmissionInfoGCCDtl> getSubmissionInfoGCCDtls() {
		return submissionInfoGCCDtls;
	}

	public void setSubmissionInfoGCCDtls(
			Vector<DTOSubmissionInfoGCCDtl> submissionInfoGCCDtls) {
		this.submissionInfoGCCDtls = submissionInfoGCCDtls;
	}

	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	public String getPropriateryName() {
		return propriateryName;
	}

	public void setPropriateryName(String propriateryName) {
		this.propriateryName = propriateryName;
	}
}
