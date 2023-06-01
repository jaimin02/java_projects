package com.docmgmt.struts.actions.workspace;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.docmgmt.struts.actions.reports.StageWiseMailReport;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ChangeStatusForDocSignForESignature extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public String workspaceID;
	public int usergrpcode;
	public int userCode;
	public String userType;
	public String UserName;
	public String docId;
	public int recordId;
	public int nodeId;
	public File uploadFile;
	public String uploadFileContentType; // The content type of the file
	public String uploadFileFileName; // The uploaded file name

	public String keyword;

	public String description;
	public String htmlContent = "";

	public int[] userIdDtl;

	public String versionPrefix="A";

	public String VersionSeperator="-";

	public String versionSuffix="1";

	public int attrCount;

	public char isReplaceFileName;
	
	public char isReplaceFolderName;

	public String nodeFolderName;

	public char deleteFile;
	public char useSourceFile;

	public String remark;
	public String Drag;
	public String operation;
	public boolean dndflag;

	public String allowAutoCorrectionPdfProp;
	public char autoCorrectPdfProp;
	String nodeOperationValue = "";
	public String passwordProtected;
	public String fileSizeProperty;
	public int fileUploloadingSize;
	ArrayList<DTOWorkSpaceNodeHistory> fullNodeHistory;
	public String nodeHistoryForUser;
	public Vector<DTOStageMst> stageuserdtl;
	public int stageCode;
	public String autoMail;
	public boolean getCompletedNodeStageDetail;
	public String docType;
	public boolean signatureFlag=false;
	public String signPath;
	public String fontStyle;
	public String usrName;
	public String eSign="N";
	public String OpenFileAndSignOff;
	public Vector<DTOWorkSpaceNodeHistory> getPreviewDetail;
	public String userNameForPreview;
	public String signId;
	public String signImg;
	public String signStyle;
	public String roleName;
	ArrayList<String> time = new ArrayList<String>();
	public String dateForPreview;
	public String ManualSignatureConfig;
	public String ApplicationUrl;
	public int tranNo;
	public String RefrenceFileUpload;
	public String baseWorkRefFolder="";
	public String otsuka="";
	
	
	@Override
	public String execute() {
		workspaceID= docId;
		nodeId=recordId;
		
		usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		usrName = ActionContext.getContext().getSession().get("username").toString();
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		OpenFileAndSignOff = propertyInfo.getValue("OpenFileAndSignOff");
		RefrenceFileUpload = propertyInfo.getValue("RefrenceFileUpload");
		baseWorkRefFolder = propertyInfo.getValue("RefrenceFolder");
		otsuka = propertyInfo.getValue("OtsukaCustomization");
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		
		//stageuserdtl = docMgmtImpl.geteSignatureStageDetail();
		/*if(stageuserdtl.size()>0){
			//int dtoSize = stageuserdtl.size();
			DTOWorkSpaceUserRightsMst dto  = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(workspaceID);
			dto.setNodeId(nodeId);
			dto.setUserCode(userCode);
			dto.setStageId(0);
			dto.setStageDesc("Send Back");
			stageuserdtl.add(dto);
		}*/
		
		userNameForPreview = usrName;
		String roleCode = docMgmtImpl.getRoleCode(workspaceID, userCode);
		roleName= docMgmtImpl.getRoleName(roleCode);
		Vector <DTOWorkSpaceNodeHistory> getSignDetail = docMgmtImpl.getUserSignatureDetail(userCode);
		if(getSignDetail.size()>0){
		signId = getSignDetail.get(0).getUuId();
		//signImg = singaturePath+getSignDetail.get(0).getFolderName()+"/"+getSignDetail.get(0).getFileName();
		signImg =getSignDetail.get(0).getFileName();
		}
		if((signImg==null || signImg.isEmpty()) && getSignDetail.size()>0){
			signStyle =getSignDetail.get(0).getFileType();
		}
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(currentTime,locationName,countryCode);
			dateForPreview = time.get(0);
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(currentTime,locationName,countryCode);
			dateForPreview = time.get(1);
		}
		
		
		Vector <DTOWorkSpaceNodeHistory> detSignHistory;
		detSignHistory =docMgmtImpl.getUserSignatureDetail(userCode);
		if(detSignHistory.size()>0){
			signatureFlag = true;
			signPath = detSignHistory.get(0).getFileName();
			fontStyle = detSignHistory.get(0).getFileType();
		}
		
		ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.showFullNodeHistoryWithVoidFilesForESignature(workspaceID, nodeId);

		tranNo=docMgmtImpl.getMaxNodeHistoryByTranNo(workspaceID, nodeId).get(0).getTranNo();
		stageCode=docMgmtImpl.getMaxNodeHistoryByTranNo(workspaceID, nodeId, tranNo).get(0).getStageId();
		/*if(stageCode==30 || stageCode==50)
			nodeHistoryForUser="true";
		else
			nodeHistoryForUser=docMgmtImpl.getNodeHistoryForUserCode(workspaceID, nodeId,userCode);*/
		nodeHistoryForUser=docMgmtImpl.getNodeHistoryForUserCodeForSendBack(workspaceID, nodeId,userCode);
		
		/**********************************************************************************/
		/*
		 * Logic to display file versions for all records as we get version only
		 * for max stage id
		 */
		
		String tempFileVersionId = "", tempUserDefineVer = "";
		char tempLastClosed = 'N';

		Collections.reverse(tempHistory);
		for (Iterator<DTOWorkSpaceNodeHistory> iterator = tempHistory
				.iterator(); iterator.hasNext();) {
			DTOWorkSpaceNodeHistory dtoHistory = iterator.next();

			// Fetch node's Attribute History (TranNo Wise)
			Vector attrHistory = docMgmtImpl
					.getWorkspaceNodeAttrHistorybyTranNo(dtoHistory
							.getWorkSpaceId(), dtoHistory.getNodeId(),
							dtoHistory.getTranNo());
			dtoHistory.setAttrHistory(attrHistory);
		}
		Collections.reverse(tempHistory);
		/*********************************************************************************/

		fullNodeHistory = tempHistory;

		for (int i = 0; i < fullNodeHistory.size(); i++) {
			DTOWorkSpaceNodeHistory dto = (DTOWorkSpaceNodeHistory) fullNodeHistory
					.get(i);
			dto.setHistoryDocumentSize(FileManager.getFileSize(dto
					.getBaseWorkFolder()
					+ dto.getFolderName() + "/" + dto.getFileName()));
		}
		ManualSignatureConfig = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, 1, "ManualSignature").getAttrValue();
		ApplicationUrl = propertyInfo.getValue("ApplicationUrl");
		//tranNo=docMgmtImpl.getMaxNodeHistoryByTranNo(workspaceID, nodeId).get(0).getTranNo();
		//stageCode=docMgmtImpl.getMaxNodeHistoryByTranNo(workspaceID, nodeId, tranNo).get(0).getStageId();
		return SUCCESS;
	}
	
	public String getOtsuka() {
		return otsuka;
	}

	public void setOtsuka(String otsuka) {
		this.otsuka = otsuka;
	}

	public String changeStatus() throws MalformedURLException, SQLException {
		workspaceID= docId;
		userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	
		int stageId = docMgmtImpl.getStageIdfromWSHistory(workspaceID,nodeId);
		
		if(stageId==0){
			htmlContent="sendback";
			System.out.println("******Document is Send back by other user.*****");
			return "html";
		}else{
		
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		autoMail = propertyInfo.getValue("AutoMail");
		OpenFileAndSignOff = propertyInfo.getValue("OpenFileAndSignOff");
		System.out.println("stageId="+stageId);
		String currentSeq = docMgmtImpl.getCurrentSeq(workspaceID, nodeId);
		System.out.println("currentSeq="+currentSeq);
				
		if(stageCode==-1 || (stageId==100 && !currentSeq.isEmpty())){
			return SUCCESS;
		}
		
		int MaxtranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
		int tranNo = docMgmtImpl.getNewTranNo(workspaceID);
		DTOWorkSpaceNodeHistory dtownd = docMgmtImpl.getWorkspaceNodeHistorybyTranNo(workspaceID, nodeId, MaxtranNo);
		dtownd.setStageId(stageCode);
		dtownd.setModifyBy(userCode);
		dtownd.setTranNo(tranNo);
	if(remark!=null && !remark.isEmpty() && !remark.equals("")){
		dtownd.setRemark(remark);
	}
	else{
		dtownd.setRemark("");
	}
		dtownd.setDefaultFileFormat("");
		String roleCode = docMgmtImpl.getRoleCodeFromWSUserRightsMst(workspaceID, nodeId,userCode);
		dtownd.setRoleCode(roleCode);
		dtownd.setFileSize(dtownd.getFileSize());
		docMgmtImpl.insertNodeHistoryWithRoleCode(dtownd);
		//docMgmtImpl.insertNodeHistory(dtownd);
		if(stageCode!=0){
			if(eSign.equalsIgnoreCase("Y")){
				Vector <DTOWorkSpaceNodeHistory> dto;
				dto =docMgmtImpl.getUserSignatureDetail(userCode);
				if(dto.size()>0){
					String uuId = dto.get(0).getUuId();
					int signtrNo = dto.get(0).getSignTranNo();
					docMgmtImpl.UpdateNodeHistoryForESign(workspaceID,nodeId,uuId,signtrNo);
					 roleCode = docMgmtImpl.getRoleCodeFromWSUserRightsMst(workspaceID, nodeId,userCode);
					docMgmtImpl.UpdateNodeHistoryForRoleCode(workspaceID,nodeId,roleCode);				
				}			
			}
			}
		if(OpenFileAndSignOff.equalsIgnoreCase("Yes")){
		DTOWorkSpaceNodeHistory dto1 = new DTOWorkSpaceNodeHistory();
		dto1 = docMgmtImpl.getNodeHistoryForSignOff(workspaceID,nodeId);
		dto1.setWorkSpaceId(workspaceID);
		dto1.setNodeId(nodeId);
		dto1.setTranNo(0);
		dto1.setFileName(dto1.getFileName());
		dto1.setFolderName(dto1.getFolderName());
		dto1.setRemark("");
		dto1.setModifyBy(userCode);
		dto1.setStatusIndi('D');
		dto1.setMode(2);
		docMgmtImpl.insertIntofileopenforsign(dto1);
		}
		//(String wsId,int nodeId,int tranNo,int iStageId,int userCode,int Mode)
		String flag="";
		if(stageCode==0){
			DTOWorkSpaceNodeHistory wsndForOffice =new DTOWorkSpaceNodeHistory();
			wsndForOffice.setWorkSpaceId(workspaceID);
			wsndForOffice.setNodeId(nodeId);
			//wsndForOffice.setDocTranNo(nodeDocHistory.get(0).getDocTranNo());
			int mode=2;
			docMgmtImpl.insertworkspacenodehistoryToUpdate(wsndForOffice,mode);
		}
		
		
		if(stageCode==0){
			docMgmtImpl.insertAssignedNodeRight(workspaceID,nodeId,tranNo,stageCode,userCode,2,flag);
		}
		else
		{
			docMgmtImpl.insertAssignedNodeRight(workspaceID,nodeId,tranNo,stageCode,userCode,1,flag);
		}

		// update tran no in attribute history
		docMgmtImpl.UpdateTranNoForStageInAttrHistory(workspaceID, nodeId,
				tranNo);
		// /docMgmtImpl.updateStageStatus(dtownd);
		
		if(autoMail.equalsIgnoreCase("Yes") && (stageCode==0 || stageCode==30) ){
			String otsukaCustomization = propertyInfo.getValue("OtsukaCustomization");
			String stageDesc="";
			if(stageCode==0)
				stageDesc="Send Back";
			if(stageCode==30)
				stageDesc="Reject";
				
				 Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForEsignature=
							docMgmtImpl.getUserRightsDetailForEsignatureByUsercode(workspaceID,nodeId,userCode);
				 if(getUserRightsDetailForEsignature.size()>0){
					 System.out.println("getUserRightsDetailForEsignature.size()>0");
						if(getUserRightsDetailForEsignature.get(0).getSeqNo()==0){
							System.out.println("if getUserRightsDetailForEsignature.get(0).getSeqNo()==0");
							StageWiseMailReport stageWiseMail = new StageWiseMailReport();
							if(otsukaCustomization.equalsIgnoreCase("Yes"))
								stageWiseMail.StageWiseMailForESignatureOtsuka(workspaceID,nodeId,userCode,"Send Back");
							else
								stageWiseMail.StageWiseMailForESignature(workspaceID,nodeId,userCode,"Send Back"); 
						}else{
							System.out.println("else getUserRightsDetailForEsignature.get(0).getSeqNo()!=0");
							int seqNo=getUserRightsDetailForEsignature.get(0).getSeqNo();
							String locationName=docMgmtImpl.getUserByCode(userCode).getUserLocationName();
							String countryCode=docMgmtImpl.getUserByCode(userCode).getCountyCode();
							Vector<DTOWorkSpaceNodeHistory> getNodeHistory=docMgmtImpl.getFirstNodeHistoryByTranNoAndCountryCode(workspaceID, nodeId,locationName,countryCode);
							if(seqNo==1){
								System.out.println("if seqNo===== "+ seqNo);
								System.out.println("getNodeHistory.get(0).getModifyBy() "+ getNodeHistory.get(0).getModifyBy());
								StageWiseMailReport stageWiseMail = new StageWiseMailReport();
								if(otsukaCustomization.equalsIgnoreCase("Yes"))
									stageWiseMail.StageWiseMailForESignatureForSendBackUserOtsuka(workspaceID,nodeId,getNodeHistory.get(0).getModifyBy(),stageDesc);
								else
									stageWiseMail.StageWiseMailForESignatureForSendBackUser(workspaceID,nodeId,getNodeHistory.get(0).getModifyBy(),stageDesc);
							}
							else if(seqNo!=1){
								System.out.println("else seqNo===== "+ seqNo);
								seqNo=seqNo-1;
							//if(seqNo!=1){
								//Vector<DTOWorkSpaceUserRightsMst>  getSeqSigned=docMgmtImpl.getUserFromSeqNo(workspaceID,nodeId,seqNo);
								if(getNodeHistory.size()>0){
									System.out.println("getNodeHistory.size()>0");
									System.out.println("getNodeHistory.get(0).getModifyBy() "+ getNodeHistory.get(0).getModifyBy());
									StageWiseMailReport stageWiseMail = new StageWiseMailReport();
									if(otsukaCustomization.equalsIgnoreCase("Yes"))
										stageWiseMail.StageWiseMailForESignatureForSendBackUserOtsuka(workspaceID,nodeId,getNodeHistory.get(0).getModifyBy(),stageDesc);
									else
									stageWiseMail.StageWiseMailForESignatureForSendBackUser(workspaceID,nodeId,getNodeHistory.get(0).getModifyBy(),stageDesc);
								}/*else{
									StageWiseMailReport stageWiseMail = new StageWiseMailReport();
									stageWiseMail.StageWiseMailForESignature(workSpaceId,Integer.parseInt(nodeId),getSeqSigned.get(0).getUserCode(),actionDesc);
								}*/
							}
							
						}
					} 
				 System.out.println("Mail Sent....");
		    
		}
		
		
		//BlockChain HashCode
			String hashCode=docMgmtImpl.generateHashCode(workspaceID,nodeId,stageCode);
		//BlockChain HashCode Ends
	}
		return SUCCESS;
	}

	// //////////////////////////////////
	
	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	
	public int getStageCode() {
		return stageCode;
	}

	public void setStageCode(int stageCode) {
		this.stageCode = stageCode;
	}
	
	public Vector<DTOStageMst> getStageuserdtl() {
		return stageuserdtl;
	}
	public void setStageuserdtl(Vector<DTOStageMst> stageuserdtl) {
		this.stageuserdtl = stageuserdtl;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int[] getUserIdDtl() {
		return userIdDtl;
	}

	public void setUserIdDtl(int[] userIdDtl) {
		this.userIdDtl = userIdDtl;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public int getAttrCount() {
		return attrCount;
	}

	public void setAttrCount(int attrCount) {
		this.attrCount = attrCount;
	}

	public String getVersionPrefix() {
		return versionPrefix;
	}

	public void setVersionPrefix(String versionPrefix) {
		this.versionPrefix = versionPrefix;
	}

	public String getVersionSeperator() {
		return VersionSeperator;
	}

	public void setVersionSeperator(String versionSeperator) {
		VersionSeperator = versionSeperator;
	}

	public String getVersionSuffix() {
		return versionSuffix;
	}

	public void setVersionSuffix(String versionSuffix) {
		this.versionSuffix = versionSuffix;
	}

	public char getIsReplaceFolderName() {
		return isReplaceFolderName;
	}

	public void setIsReplaceFolderName(char isReplaceFolderName) {
		this.isReplaceFolderName = isReplaceFolderName;
	}

	public char getIsReplaceFileName() {
		return isReplaceFileName;
	}

	public void setIsReplaceFileName(char isReplaceFileName) {
		this.isReplaceFileName = isReplaceFileName;
	}

	public String getNodeFolderName() {
		return nodeFolderName;
	}

	public void setNodeFolderName(String nodeFolderName) {
		this.nodeFolderName = nodeFolderName;
	}

	public char getDeleteFile() {
		return deleteFile;
	}

	public void setDeleteFile(char deleteFile) {
		this.deleteFile = deleteFile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDrag() {
		return Drag;
	}

	public void setDrag(String drag) {
		Drag = drag;
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> getFullNodeHistory() {
		return fullNodeHistory;
	}

	public void setFullNodeHistory(
			ArrayList<DTOWorkSpaceNodeHistory> fullNodeHistory) {
		this.fullNodeHistory = fullNodeHistory;
	}
}

