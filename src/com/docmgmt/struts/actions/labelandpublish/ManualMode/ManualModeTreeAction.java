package com.docmgmt.struts.actions.labelandpublish.ManualMode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.ManualPublishTreeBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ManualModeTreeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	String subType;
	String workSpaceId;
	String currentSeqNumber;
	String lastPublishedVersion;
	String dos;
	String relatedSeqNo;
	String subMode;
	char isRMSSelected;
	int[] selectedCMS;
	public String[] trackCMS;
	String subDesc;
	String lastConfirmedSubmissionPath;
	String treeHtml;
	char projectPublishType;
	char addTT;
	String regionalDTDVersion;
	String subVariationMode;
	String applicationNumber;
	public String seqDesc_CA;
	public String regActType;
	public String htmlContent;
	public String number;
	public String description;
	public String singledate;
	public String fromdate;
	public String todate;
	public String[] MultipleARTG;
	public String seqTypes;
	public String seqDesc;
	public String seqTypes_th;
	public String seqDesc_th;
	public String[] datatype; // Submission/efficacy
	public String efficacyDescription;
	public String propriateryName;
	public String[] MultipleApplication;
	public String[] GanelicForm;
	public String[] MultiplesubmissionType;
	public String paragraph13;
	public String[] applicantContactDetails;
	public String subSubType;
	public String supplementEffectiveDateTypeCode;
	public String crossReferenceNumber;
	public String applicationTypeCode;
	public String submissionUnitType_eu_301;
	public String submissionUnitType;
	public String email;
	public String paragraph13Ch;
	int count=0;
	public char copyRelatedSeq;
	public String[] MultiplesubmissionTypeZa20;
	
	public String showTree(){	
		System.out.println("copyRelatedSeq--->"+copyRelatedSeq);
		String relatedSeqNoCSV = relatedSeqNo;
		Map<String, Object> manualModeInfo = new HashMap<String, Object>();
		manualModeInfo.put("subType", subType);
		manualModeInfo.put("workSpaceId", workSpaceId);
		manualModeInfo.put("currentSeqNumber", currentSeqNumber);
		manualModeInfo.put("lastPublishedVersion", lastPublishedVersion);
		manualModeInfo.put("dos", dos);
		manualModeInfo.put("relatedSeqNo", relatedSeqNoCSV);
		manualModeInfo.put("subMode", subMode);
		manualModeInfo.put("isRMSSelected", isRMSSelected);
		manualModeInfo.put("trackCMS", trackCMS);
		manualModeInfo.put("selectedCMS", selectedCMS);
		manualModeInfo.put("subDesc", subDesc);
		manualModeInfo.put("lastConfirmedSubmissionPath",
				lastConfirmedSubmissionPath);
		manualModeInfo.put("addTT", addTT);
		manualModeInfo.put("copyRelatedSeq", copyRelatedSeq);
		manualModeInfo.put("subVariationMode", subVariationMode);
		manualModeInfo.put("applicationNumber", applicationNumber);
		manualModeInfo.put("regActType", regActType);
		manualModeInfo.put("seqDesc_CA", seqDesc_CA);
		manualModeInfo.put("description", description);
		manualModeInfo.put("singledate", singledate);
		manualModeInfo.put("fromdate", fromdate);
		manualModeInfo.put("todate", todate);
		manualModeInfo.put("number", number);
		manualModeInfo.put("MultipleARTG", MultipleARTG);
		manualModeInfo.put("seqTypes", seqTypes);
		manualModeInfo.put("seqDesc", seqDesc);
		manualModeInfo.put("seqTypes_th", seqTypes_th);
		manualModeInfo.put("seqDesc_th", seqDesc_th);
		manualModeInfo.put("datatype", datatype);
		manualModeInfo.put("efficacyDescription", efficacyDescription);
		manualModeInfo.put("propriateryName", propriateryName);
		manualModeInfo.put("MultipleApplication", MultipleApplication);
		manualModeInfo.put("GanelicForm", GanelicForm);
		manualModeInfo.put("MultiplesubmissionType", MultiplesubmissionType);
		manualModeInfo.put("paragraph13", paragraph13);
		manualModeInfo.put("paragraph13Ch", paragraph13Ch);
		manualModeInfo.put("applicantContactDetails", applicantContactDetails);
		manualModeInfo.put("subSubType", subSubType);
		manualModeInfo.put("supplementEffectiveDateTypeCode",
				supplementEffectiveDateTypeCode);
		manualModeInfo.put("crossReferenceNumber", crossReferenceNumber);
		manualModeInfo.put("applicationTypeCode", applicationTypeCode);
		manualModeInfo.put("submissionUnitType_eu_301", submissionUnitType_eu_301);
		manualModeInfo.put("submissionUnitType", submissionUnitType);
		manualModeInfo.put("email", email);
		manualModeInfo.put("MultiplesubmissionTypeZa20", MultiplesubmissionTypeZa20);
		ActionContext.getContext().getSession().put("manualModeInfo",
				manualModeInfo);
		System.out.println("Inside Manual Mode...."+manualModeInfo.toString());
		int userCode= Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode= Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		/*String manualPrevSeqPath = PropertyInfo.getPropInfo()
		.getValue("ManualProjectsServerPath");
		String prevSequencePath = manualPrevSeqPath + "/" + workSpaceId;
		File seqPath = new File(prevSequencePath);
		String relSeqFromPreviousSeq="";
		if (seqPath.isDirectory()){
			File[] listOfFiles = seqPath.listFiles();
			if(listOfFiles.length==0){
				relSeqFromPreviousSeq="";
			}else{
				for(int i=0;i<listOfFiles.length;i++){
					relSeqFromPreviousSeq=relSeqFromPreviousSeq+","+listOfFiles[i].getName();
				}
				relSeqFromPreviousSeq=relSeqFromPreviousSeq.substring(1);
			}
		}
		System.out.println("relSeq->"+relSeqFromPreviousSeq);
		*/
		String refIdSuffix="";
		ManualPublishTreeBean manualModePublishBean = new ManualPublishTreeBean();
		/*if (relSeqFromPreviousSeq.equals("")) {
			manualModePublishBean.setAllRelatedSeqs(null);
		} else {
			manualModePublishBean.setAllRelatedSeqs(relSeqFromPreviousSeq.split(","));
		}*/
		
		int cSeqNo = Integer.parseInt(currentSeqNumber)-1;
		String relSeqNo="";
		for(int i=cSeqNo;i>-1;i--){
			String strSeqNo = "000" + i;
			relSeqNo = relSeqNo +","+strSeqNo.substring(strSeqNo.length() - 4);
		}
		System.out.println("relSeqNo:"+relSeqNo);
		relatedSeqNoCSV=relSeqNo.substring(1);
		if(relatedSeqNoCSV.equals("")){
			manualModePublishBean.setAllRelatedSeqs(null);}
		else{
			manualModePublishBean.setAllRelatedSeqs(relatedSeqNoCSV.split(","));
		}
		manualModePublishBean.setRefIdSuffix(refIdSuffix);
		treeHtml = manualModePublishBean.getWorkspaceTreeHtml(workSpaceId, userGroupCode, userCode);
		System.out.println("WId =="+workSpaceId);
		return SUCCESS;
	}
	public String checkIfAllPreviousDossiersUploaded(){
		String relSeqArr[];
		htmlContent = "";
		File[] listOfFiles=null;
		DTOWorkSpaceMst workSpaceMst = docMgmtImpl
				.getWorkSpaceDetail(workSpaceId);
		String manualPrevSeqPath = PropertyInfo.getPropInfo()
		.getValue("ManualProjectsServerPath");
		String FosunChanges=PropertyInfo.getPropInfo().getValue("FosunCustomization");
		if(FosunChanges.equalsIgnoreCase("yes")){
			manualPrevSeqPath=manualPrevSeqPath +"/"+workSpaceMst.getClientName();
		}
		String prevSequencePath = manualPrevSeqPath + "/" + workSpaceId;
		File seqPath = new File(prevSequencePath);
		//File[] listOfFiles = seqPath.listFiles();
		relSeqArr = relatedSeqNo.split(",");
		System.out.println("related seq:"+relatedSeqNo+":"+workSpaceId);
		if (seqPath.isDirectory()){
			listOfFiles = seqPath.listFiles();
			if(listOfFiles.length!=0){
				htmlContent += ",Exist";
			}
			else{
				if(listOfFiles.length==0)
				{
					for (int i = 0; i < relSeqArr.length; i++) {
						System.out.println("Not Exist!");
						htmlContent = htmlContent + "," + relSeqArr[i];
					}
					
				}
				else{
					if(!relatedSeqNo.equals(null) || !relatedSeqNo.equals("")){
						
						for (int i = 0; i < relSeqArr.length; i++) {
							for(int j=0;j<listOfFiles.length;j++){
								if (listOfFiles[j].getName().equalsIgnoreCase(relSeqArr[i])) {
									System.out.println("Exist!");
									htmlContent += ",Exist";
								}
							}
						}
						
					}
				}
			}
		}
		htmlContent=htmlContent.substring(1);
		System.out.println("data-->" + htmlContent);
		return "html";
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
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
	public String getDos() {
		return dos;
	}
	public void setDos(String dos) {
		this.dos = dos;
	}
	public String getRelatedSeqNo() {
		return relatedSeqNo;
	}
	public void setRelatedSeqNo(String relatedSeqNo) {
		this.relatedSeqNo = relatedSeqNo;
	}
	public String getSubMode() {
		return subMode;
	}
	public void setSubMode(String subMode) {
		this.subMode = subMode;
	}
	public char getIsRMSSelected() {
		return isRMSSelected;
	}
	public void setIsRMSSelected(char isRMSSelected) {
		this.isRMSSelected = isRMSSelected;
	}
	public int[] getSelectedCMS() {
		return selectedCMS;
	}
	public void setSelectedCMS(int[] selectedCMS) {
		this.selectedCMS = selectedCMS;
	}
	public String getSubDesc() {
		return subDesc;
	}
	public void setSubDesc(String subDesc) {
		this.subDesc = subDesc;
	}
	public String getLastConfirmedSubmissionPath() {
		return lastConfirmedSubmissionPath;
	}
	public void setLastConfirmedSubmissionPath(String lastConfirmedSubmissionPath) {
		this.lastConfirmedSubmissionPath = lastConfirmedSubmissionPath;
	}
	public String getTreeHtml() {
		return treeHtml;
	}
	public void setTreeHtml(String treeHtml) {
		this.treeHtml = treeHtml;
	}
	public char getProjectPublishType() {
		return projectPublishType;
	}
	public void setProjectPublishType(char projectPublishType) {
		this.projectPublishType = projectPublishType;
	}
	public char getAddTT() {
		return addTT;
	}
	public void setAddTT(char addTT) {
		this.addTT = addTT;
	}
	public String getRegionalDTDVersion() {
		return regionalDTDVersion;
	}
	public void setRegionalDTDVersion(String regionalDTDVersion) {
		
		this.regionalDTDVersion = regionalDTDVersion;
	}
	public String getSubVariationMode() {
		return subVariationMode;
	}
	public void setSubVariationMode(String subVariationMode) {
		this.subVariationMode = subVariationMode;
	}
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public String[] getTrackCMS() {
		return trackCMS;
	}
	public void setTrackCMS(String[] trackCMS) {
		this.trackCMS = trackCMS;
	}
	public String getSeqDesc_CA() {
		return seqDesc_CA;
	}
	public void setSeqDesc_CA(String seqDescCA) {
		seqDesc_CA = seqDescCA;
	}
	public String getRegActType() {
		return regActType;
	}
	public void setRegActType(String regActType) {
		this.regActType = regActType;
	}
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSingledate() {
		return singledate;
	}
	public void setSingledate(String singledate) {
		this.singledate = singledate;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
	}
	public String[] getMultipleARTG() {
		return MultipleARTG;
	}
	public void setMultipleARTG(String[] multipleARTG) {
		MultipleARTG = multipleARTG;
	}
	public String getSeqTypes() {
		return seqTypes;
	}
	public void setSeqTypes(String seqTypes) {
		this.seqTypes = seqTypes;
	}
	public String getSeqDesc() {
		return seqDesc;
	}
	public void setSeqDesc(String seqDesc) {
		this.seqDesc = seqDesc;
	}
	public String getSeqTypes_th() {
		return seqTypes_th;
	}
	public void setSeqTypes_th(String seqTypesTh) {
		seqTypes_th = seqTypesTh;
	}
	public String getSeqDesc_th() {
		return seqDesc_th;
	}
	public void setSeqDesc_th(String seqDescTh) {
		seqDesc_th = seqDescTh;
	}
	public String[] getDatatype() {
		return datatype;
	}
	public void setDatatype(String[] datatype) {
		this.datatype = datatype;
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
	public String[] getMultipleApplication() {
		return MultipleApplication;
	}
	public void setMultipleApplication(String[] multipleApplication) {
		MultipleApplication = multipleApplication;
	}
	public String[] getGanelicForm() {
		return GanelicForm;
	}
	public void setGanelicForm(String[] ganelicForm) {
		GanelicForm = ganelicForm;
	}
	public String getParagraph13() {
		return paragraph13;
	}
	public void setParagraph13(String paragraph13) {
		this.paragraph13 = paragraph13;
	}
	public String[] getApplicantContactDetails() {
		return applicantContactDetails;
	}
	public void setApplicantContactDetails(String[] applicantContactDetails) {
		this.applicantContactDetails = applicantContactDetails;
	}
	public String getSubSubType() {
		return subSubType;
	}
	public void setSubSubType(String subSubType) {
		this.subSubType = subSubType;
	}
	public String getSupplementEffectiveDateTypeCode() {
		return supplementEffectiveDateTypeCode;
	}
	public void setSupplementEffectiveDateTypeCode(
			String supplementEffectiveDateTypeCode) {
		this.supplementEffectiveDateTypeCode = supplementEffectiveDateTypeCode;
	}
	public String getCrossReferenceNumber() {
		return crossReferenceNumber;
	}
	public void setCrossReferenceNumber(String crossReferenceNumber) {
		this.crossReferenceNumber = crossReferenceNumber;
	}
	public String getApplicationTypeCode() {
		return applicationTypeCode;
	}
	public void setApplicationTypeCode(String applicationTypeCode) {
		this.applicationTypeCode = applicationTypeCode;
	}
	public String[] getMultiplesubmissionType() {
		return MultiplesubmissionType;
	}
	public void setMultiplesubmissionType(String[] multiplesubmissionType) {
		MultiplesubmissionType = multiplesubmissionType;
	}
}
