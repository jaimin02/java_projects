package com.docmgmt.struts.actions.labelandpublish.NeeS;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import com.docmgmt.dto.DTOInternalLabelMst;
import com.docmgmt.dto.DTOSubmissionInfoEU20SubDtl;
import com.docmgmt.dto.DTOSubmissionInfoNeeSDtl;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOSubmittedWorkspaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkspaceCMSMst;
import com.docmgmt.dto.PublishAttrForm;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class NeeSPublishAndSubmitAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();

	public String workSpaceId;
	public String dos;
	public String client_name;
	public String project_type;
	public String project_name;
	

	public String extraHtmlCode;

	public Vector<DTOSubmissionMst> workspacesWithSubmissionInfo;
	// public Vector<DTOWorkSpaceMst> workspacesWithSubmissionInfo;

	public Vector<?> getSubmissionTypes;
	public Vector<DTOSubmissionInfoNeeSDtl> getWorkspacePublishInfo;
	public Vector<DTOSubmissionInfoNeeSDtl> getWorkspaceConfirmDtl;
	public Vector<String> getAllWorkspaceSequences;
	public String lastPublishedVersion;
	public String currentSeqNumber;
	public String subType;
	public String relatedSeqNo = "";
	public String countryRegion;
	public String submissionPath;
	public String subMode;
	public String submissionInfo__DtlId;
	public String lastConfirmedSubmissionPath;
	public DTOSubmissionMst submissionDetail;
	public ArrayList<DTOWorkspaceCMSMst> lstWorkspaceCMS;
	public String workspaceRMS;
	public char isRMSSelected;
	public int[] selectedCMS;
	public String subDesc;
	 

	public String elecSig;
	public String[] trackCMS;

	public String editSequence;

	

	public String execute() throws Exception {
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		String defaultWorkSpaceId = ActionContext.getContext().getSession()
				.get("defaultWorkspace").toString();
		int userGroupCode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());

		if (defaultWorkSpaceId != null && !defaultWorkSpaceId.equals("")) {
			workSpaceId = defaultWorkSpaceId;

		}
		Vector<DTOWorkSpaceMst> getAllWorkspace = docMgmtImpl.getUserWorkspace(
				userGroupCode, userId);
		Vector<DTOSubmissionMst> wsWithSubInfo = docMgmtImpl
				.getAllWorkspaceSubmissionInfo(userId);

		workspacesWithSubmissionInfo = new Vector<DTOSubmissionMst>();
		for (int i = 0; i < getAllWorkspace.size(); i++) {
			DTOWorkSpaceMst dtoAllWorkSpaceMst = (DTOWorkSpaceMst) getAllWorkspace
					.get(i);

			System.out.println(dtoAllWorkSpaceMst.getProjectType());

			if (dtoAllWorkSpaceMst.getProjectType() == ProjectType.NEES_STANDARD) {

				DTOSubmissionMst neesProject = new DTOSubmissionMst();

				neesProject.setWorkspaceId(dtoAllWorkSpaceMst.getWorkSpaceId());
				neesProject.setWorkspaceDesc(dtoAllWorkSpaceMst
						.getWorkSpaceDesc());
				workspacesWithSubmissionInfo.add(neesProject);

				System.out.println("Nees");

			}
			for (int k = 0; k < wsWithSubInfo.size(); k++) {
				DTOSubmissionMst dtoSubWorkspaceMst = (DTOSubmissionMst) wsWithSubInfo
						.get(k);
				if (dtoAllWorkSpaceMst.getWorkSpaceId().equals(
						dtoSubWorkspaceMst.getWorkspaceId()) && dtoAllWorkSpaceMst.getProjectType() == ProjectType.NEES_STANDARD) {

					workspacesWithSubmissionInfo.add(dtoSubWorkspaceMst);
				}
			}
		}
		return SUCCESS;
	}

	public String getSubmissionDtl() throws Exception {
		// System.out.println(workSpaceId);
		if (workSpaceId == null || workSpaceId.trim().equals("")
				|| workSpaceId.equals("-1")) {
			return SUCCESS;
		}
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		elecSig = knetProperties.getValue("ElectronicSignature_ConfirmSeq");
		DTOWorkSpaceMst wsdto = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		client_name = wsdto.getClientName();
		project_type = wsdto.getProjectName();
		project_name = wsdto.getWorkSpaceDesc();
		lastPublishedVersion = wsdto.getLastPublishedVersion();

		DTOSubmissionMst dtoSubmissionMst = docMgmtImpl
				.getSubmissionInfo(workSpaceId);
		

		// totalLeafNode=docMgmtImpl.getTotalLeafNode(workSpaceId);
		// System.out.println("TotalLeafNode->"+totalLeafNode);

		// int nodehistory[]=new int[4];

		// nodehistory=docMgmtImpl.getAllNodeStatusCount(workSpaceId);
		// System.out.println("On Node 0="+nodehistory[3]);

		/*
		 * If project's Type is 'eCTD' (wsdto.getProjectType() == 'P') then
		 * publish the project sequence automatically.
		 */
		/*
		 * If project's Type is 'eCTD (Manual Mode)' (wsdto.getProjectType() ==
		 * 'M') then it will allow the user to publish new sequence manually.
		 */
		// 'N' =NeeS

		if (wsdto.getProjectType() == 'N') {

			System.out.println("currentSeqNumber::"+currentSeqNumber);
			if (lastPublishedVersion.equals("-999")) {
				currentSeqNumber = "0000";
			} else {
				int iSeqNo = Integer.parseInt(lastPublishedVersion);
				iSeqNo++;
				String strSeqNo = "000" + iSeqNo;
				currentSeqNumber = strSeqNo.substring(strSeqNo.length() - 4);
				
			}

			// getSubmissionTypes = docMgmtImpl
			// .getRegionWiseSubmissionTypes(dtoSubmissionMst
			// .getCountryRegion());

			getAllWorkspaceSequences = new Vector<String>();

			getWorkspacePublishInfo = docMgmtImpl
					.getWorkspaceSubmissionInfoNeeSDtl(workSpaceId);

			getWorkspaceConfirmDtl = docMgmtImpl
			.getWorkspaceSubmissionInfoNeeSDtlForConfirmSeq(workSpaceId);
			
			if(getWorkspaceConfirmDtl.size()>0){
				
				editSequence="No";
			}
			else{
				editSequence="Yes";
			}
			
			System.out.println("editSequence-->"+editSequence);

			
			for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
				DTOSubmissionInfoNeeSDtl dtoNeeS0Dtl = (DTOSubmissionInfoNeeSDtl) getWorkspacePublishInfo
						.get(i);

				// get all Previous Sequences
				if (!getAllWorkspaceSequences.contains(dtoNeeS0Dtl
						.getLastPublishedVersion())
						&& !dtoNeeS0Dtl.getLastPublishedVersion()
								.equals("-999"))
					getAllWorkspaceSequences.addElement(dtoNeeS0Dtl
							.getLastPublishedVersion());

				// get latest Confirmed Submission Path for copying
				// previous sequences' directories
				if (dtoNeeS0Dtl.getConfirm() == 'Y') {
					lastConfirmedSubmissionPath = dtoNeeS0Dtl
							.getSubmissionPath();
					
				}
				
				
			}
			
			System.out.println("editable sequece::"+editSequence);

			/** code for RMS/CMS /**EU specific code **/
			/*
			 * if (dtoSubmissionMst.getProcedureType().equalsIgnoreCase(
			 * "decentralised") ||
			 * dtoSubmissionMst.getProcedureType().equalsIgnoreCase(
			 * "mutual-recognition") ||
			 * dtoSubmissionMst.getProcedureType().equalsIgnoreCase(
			 * "national")) {
			 * 
			 * workspaceRMS = dtoSubmissionMst.getCountryName();
			 * 
			 * lstWorkspaceCMS = docMgmtImpl
			 * .getWorkspaceCMSInfo(dtoSubmissionMst.getWorkspaceId()); }
			 */
		} else {
			getWorkspacePublishInfo = new Vector<DTOSubmissionInfoNeeSDtl>();
		}
		Calendar cal = GregorianCalendar.getInstance();
		java.util.Date now = cal.getTime();
		DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
		dos = formater.format(now);

		countryRegion = dtoSubmissionMst.getCountryRegion();
		
		System.out.println("countryRegion:::"+countryRegion);

		System.out.println(wsdto.getProjectType());
		return SUCCESS;
	}

	public String saveSubmissionDtl() throws Exception {
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());

		// System.out.println("currentSeqNumber:::"+currentSeqNumber);
		DTOSubmissionMst dtoSubmissionMst = docMgmtImpl
				.getSubmissionInfo(workSpaceId);

		DTOWorkSpaceMst dtoWorkspace = docMgmtImpl
				.getWorkSpaceDetail(workSpaceId);

		String submissionId = "";
		// For current publishing sequence

		// converting 'dos'(DateOfSubmission) from String to Timestamp..
		java.sql.Timestamp sqltDate = null;
		try {
			DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
			java.util.Date parsedUtilDate = formater.parse(dos);

			sqltDate = new java.sql.Timestamp(parsedUtilDate.getTime());

		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		// Creating label for workspace with PublishType 'P' only
		DTOInternalLabelMst newLabel = null;
		String newLabelId = null;

		newLabel = createWorkspaceLabel(workSpaceId, userId);
		newLabelId = newLabel.getLabelId();

		String relatedSeqs = relatedSeqNo.replaceAll(" ", "");
		
		if (currentSeqNumber.equals("0000")) {
			lastPublishedVersion = "-999";
		} else {
			int iSeqNo = Integer.parseInt(currentSeqNumber);
			iSeqNo--;
			String strSeqNo = "000" + iSeqNo;
			lastPublishedVersion = strSeqNo.substring(strSeqNo
					.length() - 4);
		}


		DTOSubmissionInfoNeeSDtl submissionDtl = new DTOSubmissionInfoNeeSDtl();
		submissionDtl.setWorkspaceId(workSpaceId);
		submissionDtl.setCountryCode("0001");
		submissionDtl.setCurrentSeqNumber(currentSeqNumber);
		submissionDtl.setLastPublishedVersion(lastPublishedVersion);

		submissionDtl.setSubmissionPath(dtoWorkspace.getWorkSpaceDesc());// Setting

		submissionDtl.setSubmitedBy(userId);
		submissionDtl.setSubmissionType("");
		submissionDtl.setDateOfSubmission(sqltDate);
		submissionDtl.setRelatedSeqNo(relatedSeqs);
		submissionDtl.setConfirm('N');
		submissionDtl.setModifyBy(userId);
		submissionDtl.setLabelId(newLabelId);
		submissionDtl.setSubmissionMode("");

		submissionDtl.setSubVariationMode("");// New in EU

		submissionDtl.setTrackingNo("");

		submissionDtl.setRMSSubmited('N');

		submissionId = docMgmtImpl
				.insertSubmissionInfoNeeSDtl(submissionDtl, 1);// Insert MODE
		
		docMgmtImpl.updatePublishedVersionForNeeS(workSpaceId,
				lastPublishedVersion);

		// Inserting CMS/RMS info into SubmissionInfoEUSubDtl
		if (selectedCMS != null) {
			for (int i = 0; i < selectedCMS.length; i++) {

				DTOSubmissionInfoEU20SubDtl dto = new DTOSubmissionInfoEU20SubDtl();
				dto.setWorkspaceCMSId(selectedCMS[i]);
				dto.setDateOfSubmission(sqltDate);
				dto.setSubmissionDescription(subDesc);
				dto.setModifyBy(userId);
				dto.setSubmissionInfoEU20DtlId(submissionId);
				dto.setPublishCMSTrackingNo(trackCMS[i].trim());
				docMgmtImpl.insertSubmissionInfoEU20SubDtl(dto, 1);// 'insert'
				// mode

			}
		}
		dtoSubmissionMst.setSubmissionDescription("desciption");// for 'EU'
		// submission
		// only
		dtoSubmissionMst.setSubmissionId(submissionId);

		DTOSubmissionInfoNeeSDtl dtoNeeS = docMgmtImpl
				.getWorkspaceSubmissionInfoNeeSDtlBySubmissionId(submissionId);
		submissionPath = dtoNeeS.getSubmissionPath();

		publishWorkspaceSubmission(dtoSubmissionMst, newLabel,
				currentSeqNumber, relatedSeqs, submissionPath,
				lastConfirmedSubmissionPath);

		addActionMessage("Project Published Successfully...");
		
		

		extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\">";

		return "save";
	}

	public String confirmSubmission() {
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		int labelNo = 0;
		String rtnVal = null;
		String currentSubmissionLabelId = "";
		Vector<DTOWorkSpaceNodeDetail> nodesToSubmit = null;
		String result = "";
		
		if (result.equalsIgnoreCase("pathNotFound")) {
			addActionError("Cannot confirm submission...\n \n Submission path not found.");
			extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\">";
			return SUCCESS;
		} else {
			/* If no dead/broken links found then confirm the submission */
			DTOWorkSpaceMst dtoWorkspaceMst = null;

			DTOSubmissionInfoNeeSDtl dtoNeeSDtl = docMgmtImpl
					.getWorkspaceSubmissionInfoNeeSDtlBySubmissionId(submissionInfo__DtlId);

			currentSubmissionLabelId = dtoNeeSDtl.getLabelId();
			// Get workspace Label No.
			Vector<DTOInternalLabelMst> allWorkspaceLabels = docMgmtImpl
					.viewLabelUsingWorkspaceId(workSpaceId);
			for (int i = 0; i < allWorkspaceLabels.size(); i++) {
				DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
						.get(i);
				if (dtoLabel.getLabelId().equalsIgnoreCase(
						currentSubmissionLabelId)) {
					labelNo = dtoLabel.getLabelNo();
				}
			}
			nodesToSubmit = docMgmtImpl.getNodeForRevisedSubmission(
					workSpaceId, labelNo);

			/*
			 * Update lastPublishedVersion in workspaceMst IMP NOTE :
			 * 'lastPublishedVersion' should be updated after
			 * 'getNodeForRevisedSubmission' is called
			 */
			//docMgmtImpl.updatePublishedVersion(workSpaceId);
			docMgmtImpl.updatePublishedVersionForNeeS(workSpaceId,
					currentSeqNumber);
			dtoWorkspaceMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
			dtoNeeSDtl.setSubmissionInfoNeeSDtlId(submissionInfo__DtlId);
			dtoNeeSDtl.setConfirm('Y');// Confirm Selected Submission
			dtoNeeSDtl.setLastPublishedVersion(dtoWorkspaceMst
					.getLastPublishedVersion());
			dtoNeeSDtl.setSubmitedBy(userId);
			dtoNeeSDtl.setModifyBy(userId);
			docMgmtImpl.insertSubmissionInfoNeeSDtl(dtoNeeSDtl, 2); // Update
			// Published
			// Version

			// Insert into SubmittedWorkspaceNodeDetail
			ArrayList<DTOSubmittedWorkspaceNodeDetail> subNodeDtlLst = new ArrayList<DTOSubmittedWorkspaceNodeDetail>();

			
			if (nodesToSubmit.size() > 0 && nodesToSubmit != null) {

				for (Iterator<DTOWorkSpaceNodeDetail> iterator = nodesToSubmit.iterator(); iterator
						.hasNext();) {
					DTOWorkSpaceNodeDetail nDtl = (DTOWorkSpaceNodeDetail) iterator
							.next();
					DTOSubmittedWorkspaceNodeDetail dtoSubNodeDtl = new DTOSubmittedWorkspaceNodeDetail();
					dtoSubNodeDtl.setNodeId(nDtl.getNodeId());
					dtoSubNodeDtl.setIndexId("node-" + nDtl.getNodeId());// Setting

					dtoSubNodeDtl.setWorkspaceId(workSpaceId);
					dtoSubNodeDtl.setSubmissionId(submissionInfo__DtlId);
					dtoSubNodeDtl.setLastPublishVersion(dtoWorkspaceMst
							.getLastPublishedVersion());
					subNodeDtlLst.add(dtoSubNodeDtl);
				}
				docMgmtImpl
						.insertIntoSubmittedWorkspaceNodeDetail(subNodeDtlLst);
			}
		}
		if (rtnVal != null) {
			return rtnVal;
		}
		addActionMessage("Your Submission Has Been Confirmed...");
		extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\">";
		return SUCCESS;
	}

	public DTOInternalLabelMst createWorkspaceLabel(String workspaceId,
			int userId) {

		DTOInternalLabelMst maxLabel = docMgmtImpl
				.getMaxWorkspaceLabel(workspaceId);
		String maxLabelId = maxLabel.getLabelId();
		// Increment labelId by 1, e.g. L0001 to L0002
		String newLabelId = maxLabelId.substring(maxLabelId.length() - 4);
		int newLabelIdInt = Integer.parseInt(newLabelId);
		newLabelIdInt = newLabelIdInt + 1;
		newLabelId = "000" + newLabelIdInt;

		newLabelId = maxLabelId.substring(0, maxLabelId.length() - 4)
				+ newLabelId.substring(newLabelId.length() - 4);
		// System.out.println(newLabelId);
		DTOInternalLabelMst dto = new DTOInternalLabelMst();
		dto.setWorkspaceId(workspaceId);
		dto.setLabelId(newLabelId);// labelId
		dto.setRemark("");// labelRemark
		dto.setModifyBy(userId);
		docMgmtImpl.createInternalLabel(dto);

		maxLabel = docMgmtImpl.getMaxWorkspaceLabel(workspaceId);

		return maxLabel;

	}

	public void publishWorkspaceSubmission(DTOSubmissionMst dtoSubmissionMst,
			DTOInternalLabelMst dtoLabel, String currentSeqNumber,
			String relatedSeqNo, String publishDestinationPath,
			String sourcePath) {

		PublishAttrForm publishForm = new PublishAttrForm();

		publishForm.setSubmissionId(dtoSubmissionMst.getSubmissionId());// for
		// 'EU'
		// submission
		publishForm.setWsId(workSpaceId);
		publishForm.setLabelNo(dtoLabel.getLabelNo());
		publishForm.setLabelId(dtoLabel.getLabelId());

		publishForm.setSubmissionFlag(dtoSubmissionMst.getCountryRegion());
		publishForm.setSeqNumber(currentSeqNumber);
		publishForm.setPublishDestinationPath(publishDestinationPath);// Changed

		
		publishForm.setRelatedSeqNumber(relatedSeqNo);

		

		DTOWorkSpaceMst workSpaceMst = docMgmtImpl
				.getWorkSpaceDetail(workSpaceId);

		char projectType = workSpaceMst.getProjectType();
		String wsDesc = workSpaceMst.getWorkSpaceDesc();


		NeeSPublishService neesPublishService = new NeeSPublishService();
		neesPublishService.setProjectPublishType(projectType);

		neesPublishService.workspacePublish(workSpaceId, publishForm,
				wsDesc);

		// Copy Previous Submission Sequence Folders

		/*
		 * if (sourcePath != null && !sourcePath.equals("")) {
		 * 
		 * FileManager fileManager = new FileManager();
		 * fileManager.copyDirectory(new File(sourcePath), new File(
		 * publishDestinationPath));
		 * 
		 * }
		 */
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public String getDos() {
		return dos;
	}

	public void setDos(String dos) {
		this.dos = dos;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getProject_type() {
		return project_type;
	}

	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getExtraHtmlCode() {
		return extraHtmlCode;
	}

	public void setExtraHtmlCode(String extraHtmlCode) {
		this.extraHtmlCode = extraHtmlCode;
	}

	public Vector<DTOSubmissionMst> getWorkspacesWithSubmissionInfo() {
		return workspacesWithSubmissionInfo;
	}

	public void setWorkspacesWithSubmissionInfo(
			Vector<DTOSubmissionMst> workspacesWithSubmissionInfo) {
		this.workspacesWithSubmissionInfo = workspacesWithSubmissionInfo;
	}

	public Vector<?> getGetSubmissionTypes() {
		return getSubmissionTypes;
	}

	public void setGetSubmissionTypes(Vector<?> getSubmissionTypes) {
		this.getSubmissionTypes = getSubmissionTypes;
	}

	public Vector<DTOSubmissionInfoNeeSDtl> getGetWorkspacePublishInfo() {
		return getWorkspacePublishInfo;
	}

	public void setGetWorkspacePublishInfo(Vector<DTOSubmissionInfoNeeSDtl> getWorkspacePublishInfo) {
		this.getWorkspacePublishInfo = getWorkspacePublishInfo;
	}

	public Vector<String> getGetAllWorkspaceSequences() {
		return getAllWorkspaceSequences;
	}

	public void setGetAllWorkspaceSequences(Vector<String> getAllWorkspaceSequences) {
		this.getAllWorkspaceSequences = getAllWorkspaceSequences;
	}

	public String getLastPublishedVersion() {
		return lastPublishedVersion;
	}

	public void setLastPublishedVersion(String lastPublishedVersion) {
		this.lastPublishedVersion = lastPublishedVersion;
	}

	public String getCurrentSeqNumber() {
		return currentSeqNumber;
	}

	public void setCurrentSeqNumber(String currentSeqNumber) {
		this.currentSeqNumber = currentSeqNumber;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getRelatedSeqNo() {
		return relatedSeqNo;
	}

	public void setRelatedSeqNo(String relatedSeqNo) {
		this.relatedSeqNo = relatedSeqNo;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	public String getSubmissionPath() {
		return submissionPath;
	}

	public void setSubmissionPath(String submissionPath) {
		this.submissionPath = submissionPath;
	}

	public String getSubMode() {
		return subMode;
	}

	public void setSubMode(String subMode) {
		this.subMode = subMode;
	}

	public String getSubmissionInfo__DtlId() {
		return submissionInfo__DtlId;
	}

	public void setSubmissionInfo__DtlId(String submissionInfo__DtlId) {
		this.submissionInfo__DtlId = submissionInfo__DtlId;
	}

	public String getLastConfirmedSubmissionPath() {
		return lastConfirmedSubmissionPath;
	}

	public void setLastConfirmedSubmissionPath(
			String lastConfirmedSubmissionPath) {
		this.lastConfirmedSubmissionPath = lastConfirmedSubmissionPath;
	}

	InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getEditSequence() {
		return editSequence;
	}

	public void setEditSequence(String editSequence) {
		this.editSequence = editSequence;
	}

}// class end
