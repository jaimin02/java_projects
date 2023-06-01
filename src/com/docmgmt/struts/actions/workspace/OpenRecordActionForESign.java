package com.docmgmt.struts.actions.workspace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOCheckedoutFileDetail;
import com.docmgmt.dto.DTORoleMst;
import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.dto.DTOWorkspaceNodeDocHistory;
import com.docmgmt.dto.DTOWorkspaceNodeReminderDoneStatus;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.services.pdf.PdfPropUtilities;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.docmgmt.struts.actions.reports.StageWiseMailReport;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OpenRecordActionForESign extends ActionSupport{

	public Vector<DTOWorkSpaceNodeAttribute> attrDtl;
	public Vector workspaceUserGroupsDetails;
	public Vector<DTOStageMst> getStageDetail;
	public int attrCount;
	public String nodeName;
	
	public String userTypeCode;
	public boolean isVoidFlag=true;
	public Vector<DTOWorkSpaceNodeHistory> checkVoidFile=null;
	public String projectCode="";
	
	public int nodeId;
	public Vector nodeDetail;
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail;
	public String RightsType="modulewiserights";
	
	public int duration;
	public String[] userCode;
	//public int uCode=2;
	//public int useCode=2;
	public int [] stageIds;
	public int userGroupId;
	
	//public int userGroupId;
	public int userWiseGroupCode;
	public String htmlContent;
	Vector workspaceuserdtl=new Vector();
	DTOUserMst dto = new DTOUserMst();
	public Vector getUserRightsDetailToShow;
	
	public String remark;
	public String userGroupYN;
	public int userGroupCode;
	public int[] userCodeForGrp;
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public String WorkspaceId;
	public String numberOfRepetitions="1";
	public String suffixStart;
	String errorMsg;
	public int repeatNodeId;
	public String docName;
	
	public String docId;
	public int recordId;
	
	public int[] stageIdForChange;
	public String[] userCodeForChange;
	public int nodeIdForChange;
	public String remarkForChange;
	public int uCodeByForChange;
	public Vector moduleUserDetailHistory;
	public String wsId;
	public String docType;
	public String docTypeName;
	public Vector getDeletedNodeDetail;
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	public String lbl_folderName;
	public String lbl_nodeName;
	public int usrCode;
	public Vector<DTORoleMst> roleDtl=new Vector<DTORoleMst>();
	public String roleCode;
	
	
	public String workspaceID;
	public int usergrpcode;
	public int usercode;
	public String userType;
	public String UserName;

	public File uploadFile;
	public String uploadFileContentType; // The content type of the file
	public String uploadFileFileName; // The uploaded file name

	public String keyword;

	public String description;

	public int[] userIdDtl;

	public String versionPrefix="A";

	public String VersionSeperator="-";

	public String versionSuffix="1";

	public char isReplaceFileName;
	
	public char isReplaceFolderName;

	public String nodeFolderName;

	public char deleteFile;
	public char useSourceFile;

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
	public int stageId;
	public String autoMail;
	public String UplodFileMail;
	public String eSign="N";
	public String signPath;
	public String baseWorkFolder="";
	public String fontStyle="";
	public boolean signatureFlag=false;
	public String usrName;
	public double fileSizeInMB;
	int year;
	int month;
	int date;
	public String OpenFileAndSignOff;
	
	public boolean isCreate;
	public String srcDocPath;
	public ArrayList<DTOWorkspaceNodeDocHistory> nodeDocHistory;
	public boolean IsUpload=false;
	public boolean IsDownload=false;
	public boolean IsComment=false;
	public boolean confirmAndUpload=false;
	public boolean confirmAndUploadOfHistory=false;
	public Vector<DTOWorkSpaceNodeHistory> wSOfficeHistory;
	public Vector<DTOWorkSpaceNodeHistory> wSOfficeHistoryForFlagCheck;
	public Vector<DTOWorkSpaceNodeHistory> wsNodeHistory;
	public Vector assignWorkspaceRightsDetails;
	DTOWorkSpaceNodeDetail workSpaceNodeDtl;
	String srcFileExt ;
	public String newFileName;
	public String fileNameToShow;
	public boolean IsValidate=false;
	public char canEditFlag;
	public String DocURLForOffice365;
	
	public File uploadDoc;
	public String uploadDocFileName;
	public String uploadDocContentType;
	public String userName;
	public boolean userWiseStageFlag;
	public String userTypeName;
	public String appType;
	public Vector<DTOWorkSpaceNodeHistory> getPreviewDetail;
	public String userNameForPreview;
	public String signId;
	public String signImg;
	public String signStyle;
	public String roleName;
	ArrayList<String> time = new ArrayList<String>();
	public String dateForPreview;
	public boolean sendForReviewFlag=true;
	public String ManualSignatureConfig;
	public String ApplicationUrl;
	public int TranNo;
	public int useRightStage;
	public String maintainSeq;
	public String baseWorkRefFolder="";
	public String RefrenceFileUpload;
	public double ReffileSizeInMB;
	public File uploadRefFile;
	public int ReffileUploloadingSize;
	public String RefFileName;
	public List<File> files1;
	
	public String execute() throws SQLException {
		WorkspaceId =docId;
		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		int firstLeafNode =  docMgmtImpl.getFirstLeafNodeForDocCR(WorkspaceId);
		//nodeName=docMgmtImpl.getNodeDetail(WorkspaceId,firstLeafNode).get(0).getFolderName();
		attrDtl = docMgmtImpl.getAttributeDetailForDisplay(WorkspaceId,firstLeafNode);
		//attrDtl = docMgmtImpl.getAttributeDetailForDisplayForOpenSign(WorkspaceId,firstLeafNode);
		workspaceUserGroupsDetails=docMgmtImpl.getAllUserGroups();
		getStageDetail = docMgmtImpl.getStageDetailCSV();
		docTypeName = docMgmtImpl.getDocTypeName(docType);
		
		//File uploading data
		
		recordId=docMgmtImpl.getmaxNodeId(WorkspaceId);
		nodeId=recordId;
		//WorkspaceId=docId;
		
		int maxTransNo = docMgmtImpl.getMaxTranNo(WorkspaceId,nodeId);
		stageId = docMgmtImpl.getStageDescription(WorkspaceId,nodeId,maxTransNo);
		usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		//useRightStage=docMgmtImpl.getStageIdsRightsWise(WorkspaceId, usercode,usergrpcode,nodeId).get(0);
		
		usrName = ActionContext.getContext().getSession().get("username").toString();
		
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.showFullNodeHistoryWithVoidFiles(WorkspaceId, nodeId);
		OpenFileAndSignOff = propertyInfo.getValue("OpenFileAndSignOff");
		
		//ManualSignatureConfig = propertyInfo.getValue("ManualSignatureConfig");
		ManualSignatureConfig = docMgmtImpl.getAttributeDetailByAttrName(WorkspaceId, 1, "ManualSignature").getAttrValue();
		ApplicationUrl = propertyInfo.getValue("ApplicationUrl");
		RefrenceFileUpload = propertyInfo.getValue("RefrenceFileUpload");
		
		Vector <DTOWorkSpaceNodeHistory> dtoSign;
		dtoSign =docMgmtImpl.getUserSignatureDetail(usercode);
		baseWorkFolder = propertyInfo.getValue("signatureFile");
		if(dtoSign.size()>0){
			signatureFlag = true;
			signPath = dtoSign.get(0).getFileName();
			fontStyle = dtoSign.get(0).getFileType();
		}

		userNameForPreview = usrName;
		String roleCode = docMgmtImpl.getRoleCodeFromWSUserRightsMst(WorkspaceId,nodeId,usercode);
		roleName= docMgmtImpl.getRoleName(roleCode);
		Vector <DTOWorkSpaceNodeHistory> getSignDetail = docMgmtImpl.getUserSignatureDetail(usercode);
		if(getSignDetail.size()>0){
		//signId = getSignDetail.get(0).getUuId();
		 signId=getSignDetail.get(0).getFolderName().split("/")[2];
		//signImg = baseWorkFolder+getSignDetail.get(0).getFolderName()+"/"+getSignDetail.get(0).getFileName();
		
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

			if (dtoHistory.getFileVersionId().equals("")) {
				dtoHistory.setFileVersionId(tempFileVersionId);
				dtoHistory.setUserDefineVersionId(tempUserDefineVer);
				dtoHistory.setLastClosed(tempLastClosed);
			} else {
				tempFileVersionId = dtoHistory.getFileVersionId();
				tempUserDefineVer = dtoHistory.getUserDefineVersionId();
				tempLastClosed = dtoHistory.getLastClosed();
			}

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
		if(fullNodeHistory.size()>0){
			int tmp = fullNodeHistory.size()-1;
			String SRFlag = fullNodeHistory.get(tmp).getFileType();
			if(SRFlag.equalsIgnoreCase("SR")){
				sendForReviewFlag = false;
			}
		}
		Vector<DTOWorkSpaceNodeDetail> getNodeDetail = new Vector<>();
		getNodeDetail = docMgmtImpl.getNodeDetail(WorkspaceId, nodeId);
		nodeFolderName = getNodeDetail.get(0).getFolderName();
		userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		DTOWorkSpaceMst dtowmst = docMgmtImpl.getWorkSpaceDetailWSList(WorkspaceId);
		appType=dtowmst.getProjectCode();
		
		
		for (int i = 0; i < fullNodeHistory.size(); i++) {
			DTOWorkSpaceNodeHistory dto = (DTOWorkSpaceNodeHistory) fullNodeHistory
					.get(i);
			dto.setHistoryDocumentSize(FileManager.getFileSize(dto
					.getBaseWorkFolder()
					+ dto.getFolderName() + "/" + dto.getFileName()));
		}
		
		DocURLForOffice365 = propertyInfo.getValue("DocURL");
		usergrpcode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("usergroupcode").toString());
		userWiseStageFlag = docMgmtImpl.getNextStageFlagByUser(WorkspaceId,nodeId,usercode);
		
		DTOWorkSpaceUserRightsMst obj = new DTOWorkSpaceUserRightsMst();
		obj.setNodeId(new Integer(nodeId).intValue());
		obj.setWorkSpaceId(WorkspaceId);
		obj.setUserCode(usercode);
		obj.setUserGroupCode(usergrpcode);

		Vector<DTOWorkSpaceUserRightsMst> workspaceuserrightsmst = docMgmtImpl
				.getWorkspaceNodeRightsDetail(obj);

		
		DTOWorkSpaceUserRightsMst wsuserrightsmst = new DTOWorkSpaceUserRightsMst();
		if (workspaceuserrightsmst.size() != 0) {
			wsuserrightsmst = workspaceuserrightsmst
					.get(0);
		}
		canEditFlag = wsuserrightsmst.getCanEditFlag();
		
		ArrayList<Integer> stageIdsRightsWise = docMgmtImpl.getStageIdsRightsWise(WorkspaceId, usercode, usergrpcode, nodeId);
		isCreate = stageIdsRightsWise.contains(10);
		
		String enableSrcDoc = propertyInfo.getValue("UploadSrcDoc");
		if (enableSrcDoc != null && enableSrcDoc.equals("YES")) {

			srcDocPath = propertyInfo.getValue("SOURCE_DOC_FOLDER");
			nodeDocHistory = docMgmtImpl.getLatestNodeDocHistory(
					WorkspaceId, nodeId);
		} else {
			srcDocPath = "No_Path_Found";
		}
		
		
		wSOfficeHistory=new Vector<DTOWorkSpaceNodeHistory>();
		wSOfficeHistoryForFlagCheck=new Vector<DTOWorkSpaceNodeHistory>();
		
		wSOfficeHistory=docMgmtImpl.getWorkspaceNodeHistoryForOffice(WorkspaceId, nodeId);
		//wSOfficeHistorySize=wSOfficeHistory.size();
		wSOfficeHistoryForFlagCheck=docMgmtImpl.getWorkspaceNodeHistoryForOfficeForFlagCheck(WorkspaceId, nodeId,usercode);
		if(wSOfficeHistoryForFlagCheck.size()==0){
			IsUpload=true;
			IsDownload=false;
		}
		if(wSOfficeHistoryForFlagCheck.size()>0){
			if(wSOfficeHistoryForFlagCheck.get(0).getClsDownload()=='Y' /*&& wSOfficeHistoryForFlagCheck.get(0).getModifyBy()!=usercode*/){
				/*saveAndSendFlagForOffice=true;
				saveAndSenduCodeForOffice=wSOfficeHistoryForFlagCheck.get(0).getModifyBy();*/
				IsUpload=true;
				IsDownload=false;
			}
			else if(wSOfficeHistoryForFlagCheck.get(0).getClsUpload()=='Y' && wSOfficeHistoryForFlagCheck.get(0).getModifyBy()==usercode){
				IsUpload=false;
				IsDownload=true;
				IsComment=true;
				IsValidate=true;
			}
			else{
				IsUpload=false;
				IsDownload=false;
			}
		}
		
		assignWorkspaceRightsDetails = docMgmtImpl.getWorkspaceUserDetailForNode(WorkspaceId, nodeId);
		wsNodeHistory = docMgmtImpl.getNodeHistoryForSectinDeletion(WorkspaceId, nodeId);
		if(wSOfficeHistoryForFlagCheck.size()>0){
			if(wSOfficeHistoryForFlagCheck.get(0).getClsUpload()=='Y' && wSOfficeHistoryForFlagCheck.get(0).getClsDownload()=='Y'){
				if(wsNodeHistory.size()>0){
					if(nodeDocHistory.get(0).getDocTranNo()!=wsNodeHistory.get(0).getDocTranNo() && 
							(wsNodeHistory.get(0).getStageId()==0 ||wsNodeHistory.get(0).getStageId()==10)){
						confirmAndUpload=true;
					}
					
					if(nodeDocHistory.get(0).getDocTranNo()==wsNodeHistory.get(0).getDocTranNo() && 
							(wsNodeHistory.get(0).getStageId()==10 && wsNodeHistory.get(0).getFileType().isEmpty())){
						confirmAndUpload=true;
					}
				}
				else
				confirmAndUpload=true;
			}
			/*else{
				confirmAndUpload=true;
			}*/
			}
		Vector<DTOWorkSpaceNodeDetail> wsNodeDtl = docMgmtImpl.getNodeDetail(WorkspaceId, nodeId);
		workSpaceNodeDtl = wsNodeDtl.get(0);
		if(wSOfficeHistoryForFlagCheck.size()>0){
			if(nodeDocHistory!=null && nodeDocHistory.size()>0) {
				String temp=workSpaceNodeDtl.getFolderName();
				srcFileExt = "docx";
				//newFileName=temp.substring(0, temp.lastIndexOf('.'));
				newFileName=temp+"."+srcFileExt ;
				//newFileName=newFileName+"."+srcFileExt ;
				if(wSOfficeHistoryForFlagCheck.size()>0)
					fileNameToShow=wSOfficeHistoryForFlagCheck.get(0).getFileName();
				else
					fileNameToShow=temp.substring(0, temp.lastIndexOf('.'))+"_"+WorkspaceId+"_"+nodeId+"."+srcFileExt;
			}
		}else{
			srcFileExt = "docx";
			String temp=workSpaceNodeDtl.getFolderName();
			newFileName=temp+"."+srcFileExt ;
			//newFileName=newFileName+"."+srcFileExt; 
			fileNameToShow=temp+"_"+WorkspaceId+"_"+nodeId+"."+srcFileExt;
		}
		if(nodeDocHistory.size()>0 && wsNodeHistory.size()>0){
			if(nodeDocHistory.get(0).getDocTranNo()==wsNodeHistory.get(0).getDocTranNo())
			IsUpload=false;
		}
		int lastTranNo = docMgmtImpl.getMaxTranNo(WorkspaceId,nodeId);
		ArrayList<DTOWorkSpaceNodeHistory> previousHistory = docMgmtImpl.getWorkspaceNodeHistory(WorkspaceId, nodeId,lastTranNo);
		if(previousHistory.size()>0)
			TranNo=previousHistory.get(0).getTranNo();

		// Preparation ends
		return SUCCESS;
		
	}
	
	public String SaveRecord() throws MalformedURLException{
		//WorkspaceId =docId;
		// Repeat Node Code 
		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		
		DTOWorkSpaceNodeDetail sourceNodeDtl = docMgmtImpl.getNodeDetailForEDocSign(WorkspaceId);
		int userId=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		
		
		Vector<DTOWorkSpaceUserMst> wsUserDtl = new Vector<>();
		
		wsUserDtl = docMgmtImpl.getWorkspaceUserDetailForDocSign(WorkspaceId,userId,usergrpcode);
		if(wsUserDtl.size()==0 && userTypeName.equalsIgnoreCase("WA")){
			
			DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
			dto.setWorkSpaceId(WorkspaceId);
			dto.setUserCode(userId);
			dto.setUserGroupCode(usergrpcode);
			dto.setAdminFlag('N');
			
	        Date date = new Date();
	        Timestamp currentDate=new Timestamp(date.getTime());  
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            System.out.println(formatter.format(currentDate));   
	        
            // Convert Date to Calendar
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.add(Calendar.YEAR, 50);
	        
	        Date afterDate = c.getTime();
	        Timestamp toDate=new Timestamp(afterDate.getTime()); 		
			
			dto.setLastAccessedOn(currentDate);
			dto.setRemark("");
			dto.setModifyBy(userId);
			dto.setModifyOn(currentDate);
			dto.setFromDt(currentDate);
			dto.setToDt(toDate);
		  if(userTypeName.equalsIgnoreCase("WA")){
			dto.setRightsType("projectwise");
		  }else{
			dto.setRightsType("modulewise");
		  }
		  //docMgmtImpl.insertUsertoWorkspaceUserMstForDocSign(dto);
		  docMgmtImpl.insertUsertoWorkspaceUserMstForDocSignWithRoleCode(dto);
		}
		
		repeatNodeId = sourceNodeDtl.getNodeId();
		int parentNdId = docMgmtImpl.getParentNodeId(WorkspaceId, repeatNodeId);
		int WsNodeDetail=docMgmtImpl.getLeafNodeCount(WorkspaceId,parentNdId);
		suffixStart= String.valueOf(WsNodeDetail);
		int numOfRepetition;
		int startIndex;
		try {
			numOfRepetition = Integer.parseInt(numberOfRepetitions);
			startIndex = Integer.parseInt(suffixStart);
			
			if(numOfRepetition < 1 || startIndex < 1) {
				//System.out.println(numOfRepetition);
				throw new NumberFormatException();
			}
		}
		catch (NumberFormatException e) {
			return SUCCESS;
		}
				
		String nodeDisplayName = sourceNodeDtl.getNodeDisplayName();
		int ind = sourceNodeDtl.getFolderName().lastIndexOf(".");
		String folderName="";
		if(ind != -1){
			folderName = sourceNodeDtl.getFolderName().substring(0, ind);
		}
		else{
			folderName = sourceNodeDtl.getFolderName();
		}
		
		
		int sourceNodeId = repeatNodeId;
		String fileExtension = "";
		if(sourceNodeDtl.getFolderName().lastIndexOf(".") != -1)
			fileExtension = sourceNodeDtl.getFolderName().substring(sourceNodeDtl.getFolderName().lastIndexOf("."));
		
		
		//Fetch All siblings in 'childNodes'
		int parentNodeId = docMgmtImpl.getParentNodeId(WorkspaceId, sourceNodeDtl.getNodeId());
		Vector childNodes = docMgmtImpl.getChildNodeByParent(parentNodeId, WorkspaceId);
		
		 //if selected node is clone node then find its original node
		if(sourceNodeDtl.getCloneFlag() == 'Y') {
			for(int i = 0; i < childNodes.size(); i++) {
				DTOWorkSpaceNodeDetail currentChild = (DTOWorkSpaceNodeDetail)childNodes.get(i);
				if(currentChild.getNodeName().equalsIgnoreCase(sourceNodeDtl.getNodeName())){
					if(currentChild.getCloneFlag() == 'N') {//Original Node of clone found
						nodeDisplayName = currentChild.getNodeDisplayName();
						
						ind = currentChild.getFolderName().lastIndexOf(".");
						if(ind != -1){
							folderName = currentChild.getFolderName().substring(0, ind);
						}
						else{
							folderName = currentChild.getFolderName();
						}
						
						//System.out.println("folderName::"+folderName);
					}
				}
			}
			
		}
		folderName = folderName.replaceAll("-\\d$", "");
		//Search if Node(fileName) already exists...
		for(int i = 0; i < childNodes.size(); i++) {
			DTOWorkSpaceNodeDetail currentChild = (DTOWorkSpaceNodeDetail)childNodes.get(i);					
			
			for(int j = startIndex ; j < startIndex + numOfRepetition ; j++)
			{
				if (currentChild.getFolderName().equals(folderName + "-" + j + fileExtension))
				{
					errorMsg="The FileName (" + currentChild.getFolderName() + ") exists!";
					addActionError(errorMsg);
					return "show";
				}
			}
		}
		//source node has attributes with attrForIndiId='0002'
		String attriVals = "";
		if(nodeDisplayName.contains("(") && nodeDisplayName.endsWith(")")){
			attriVals = nodeDisplayName.substring(nodeDisplayName.indexOf("("));
			nodeDisplayName = nodeDisplayName.substring(0, nodeDisplayName.indexOf("("));
		}
		nodeDisplayName = nodeDisplayName.replaceAll("-\\d$", "");
		for(int i = startIndex ; i < startIndex + numOfRepetition ; i++){
		
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(WorkspaceId);
			dto.setNodeId(sourceNodeId);
			dto.setNodeName(sourceNodeDtl.getNodeName());
			dto.setNodeDisplayName(nodeDisplayName+"-"+i+attriVals);
			dto.setFolderName(folderName+"-"+i+fileExtension);
			dto.setCloneFlag('Y');// Its a clone node
			dto.setNodeTypeIndi(sourceNodeDtl.getNodeTypeIndi());
			dto.setRequiredFlag(sourceNodeDtl.getRequiredFlag());
			dto.setCheckOutBy(0);
			dto.setPublishedFlag(sourceNodeDtl.getPublishedFlag());
			dto.setRemark(sourceNodeDtl.getRemark());
			dto.setModifyBy(userId);
			//dto.setModifyOn();
			
			docMgmtImpl.addChildNodeAfterForEDocSign(dto);
			
			//sourceNodeId = docMgmtImpl.getmaxNodeId(workspaceID);
		}		
		int newRepetedNodeId = docMgmtImpl.getmaxNodeId(WorkspaceId);
		// delete rights from wsuserrightsmst after repeat node except admin 
		docMgmtImpl.RemoveRightsFromWorkspaceUserRightsMst(WorkspaceId,newRepetedNodeId);
		//Save Attribute Code
		HttpServletRequest request = ServletActionContext.getRequest();

		String AttributeValueForNodeDisplayName = "";
		DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) docMgmtImpl
				.getNodeDetail(WorkspaceId, newRepetedNodeId).get(0);
		ArrayList<DTOWorkspaceNodeReminderDoneStatus> dtoList = new ArrayList<DTOWorkspaceNodeReminderDoneStatus>();
		Vector<DTOWorkSpaceNodeAttrDetail> attrList = docMgmtImpl
				.getNodeAttrDetail(WorkspaceId, newRepetedNodeId);
		attrDtl = docMgmtImpl.getAttributeDetailForDisplayForESignature(WorkspaceId,newRepetedNodeId);
		attrCount=attrDtl.size();
		int attrIdToMatch;
		
		for (int i = 1; i <= attrCount; i++) {
			String multiSelectionComboValue="";
			String attrValueId = "attrValueId" + i;
			String attrType = "attrType" + i;
			String attrId = "attrId" + i;
			String attrName = "attrName" + i;
			if(request.getParameter(attrType)!=null && request.getParameter(attrType).equals("MultiSelectionCombo")){
				String[] outerArray=request.getParameterValues(attrValueId);
				if (outerArray != null) {
                    for (int j = 0; j < outerArray.length; j++) {
                    	multiSelectionComboValue = multiSelectionComboValue + "," + outerArray[j];
                    }
                }
			}
			try{
				attrIdToMatch=Integer.parseInt(request.getParameter(attrId));
				}
				catch(Exception e){
					System.out.println(e.toString());
					break;
				}
			Integer attributeId = Integer.parseInt(request.getParameter(attrId));
			boolean dupFound = false;
			for (int idxAttr = 0; idxAttr < attrList.size(); idxAttr++) {
				DTOWorkSpaceNodeAttrDetail dtoWorkSpaceNodeAttrDetail = attrList
						.get(idxAttr);
				if (dtoWorkSpaceNodeAttrDetail.getAttrId() == attributeId) {
					if(request.getParameter(attrValueId)!=null){
						if((multiSelectionComboValue != null && multiSelectionComboValue != "" ) && multiSelectionComboValue.substring(1).equalsIgnoreCase(dtoWorkSpaceNodeAttrDetail.getAttrValue()) ){
							dupFound = true;
							break;	
						}else{
							if (request.getParameter(attrValueId).equals(dtoWorkSpaceNodeAttrDetail.getAttrValue())) {
								dupFound = true;
								break;
							}
						}
					}
					else{
						dupFound = true;
						break;
					}
				}
			}

			DTOAttributeMst dtoAttr = docMgmtImpl.getAttribute(attributeId);

			// if the new values of attributes are same as the old ones, don't
			// update in database
			if (dupFound) {
				// for getting the values in display
				if (dtoAttr.getAttrForIndiId().equals("0002")
						&& request.getParameter(attrValueId)!=null && !request.getParameter(attrValueId).equals("")) {
					if (AttributeValueForNodeDisplayName == "")
						AttributeValueForNodeDisplayName = request
								.getParameter(attrValueId);
					else
						AttributeValueForNodeDisplayName += ","
								+ request.getParameter(attrValueId);
				}
				continue;// TODO instead of individual attributes, values should
				// be changed together
			}
			DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();

			wsnadto.setWorkspaceId(WorkspaceId);
			wsnadto.setNodeId(newRepetedNodeId);
			wsnadto.setAttrId(attributeId);
			wsnadto.setAttrName(request.getParameter(attrName));
			//wsnadto.setAttrValue(request.getParameter(attrValueId));
			if(request.getParameter(attrType).equals("MultiSelectionCombo")){
				wsnadto.setAttrValue(multiSelectionComboValue.substring(1));
			}
			else{
				wsnadto.setAttrValue(request.getParameter(attrValueId));		
			}
			wsnadto.setRemark("new");
			
			int ucd = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userid").toString());

			wsnadto.setModifyBy(ucd);

			// ///update workspacenode attribute value
			docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);

			/**
			 * Only attr value of type '0002' will be appended to node display
			 * name. Changed By Ashmak Shah
			 */

			if (dtoAttr.getAttrForIndiId().equals("0002")) {

				if (AttributeValueForNodeDisplayName == "") {
					AttributeValueForNodeDisplayName = request
							.getParameter(attrValueId);
				} else if (!request.getParameter(attrValueId).equals("")) {
					AttributeValueForNodeDisplayName += ","
							+ request.getParameter(attrValueId);
				}

			}
			/*
			 * It will undone the user reminders displayed in welcome page
			 */
			if (request.getParameter(attrType).equals("Date")) {
				DTOWorkspaceNodeReminderDoneStatus dto = new DTOWorkspaceNodeReminderDoneStatus();
				dto.setvWorkspaceId(WorkspaceId);
				dto.setiNodeId(newRepetedNodeId);
				dto.setiAttrId(Integer.parseInt(request.getParameter(attrId)));
				dto.setiUserCode(2);
				dtoList.add(dto);
			}
		}
		docMgmtImpl.markReminderAsUnDone(dtoList);

		// update nodedisplayname of the node
		DTOWorkSpaceNodeDetail wsnd = (DTOWorkSpaceNodeDetail) docMgmtImpl
				.getNodeDetail(WorkspaceId, newRepetedNodeId).get(0);
		/*if (AttributeValueForNodeDisplayName.equals("")) {

			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll(
					"\\(.+\\)$", ""));
		} else {
			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll(
					"\\(.+\\)$", "")
					+ "(" + AttributeValueForNodeDisplayName + ")");
		}*/
		if (AttributeValueForNodeDisplayName.equals("")) {

			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			if(wsnd.getNodeDisplayName().contains("{"))
			{
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{')));
			}
			else
			{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName());
			}
		} else {
			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			if(wsnd.getNodeDisplayName().contains("{"))
			{
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{'))
					+ "{" + AttributeValueForNodeDisplayName + "}");
			}
			else
			{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName()+"{" + AttributeValueForNodeDisplayName + "}");
			}
		}

		docMgmtImpl.insertWorkspaceNodeDetail(wsnd, 2);// edit mode
		System.out.println(newRepetedNodeId);
		
		//Check Rights in WorkspaceUserRightsMst
		Vector<DTOWorkSpaceUserRightsMst> getRightsDetial;
		getRightsDetial= docMgmtImpl.checkRights(WorkspaceId,newRepetedNodeId,userId,usergrpcode);
		if(userTypeName.equalsIgnoreCase("WA") && getRightsDetial.size()==0){
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(WorkspaceId);
			dto.setUserGroupCode(usergrpcode);
			dto.setUserCode(userId);
			dto.setNodeId(newRepetedNodeId);
			dto.setModifyBy(userId);
			dto.setRemark("New");
			
			docMgmtImpl.insertintoWSUserRightsMst(dto);
		}
		
		//Update File Name in WSNodeDetail & WSNodeDetailHistory
		//String fileName = docName+".pdf";
		docMgmtImpl.UpdateNodeDetail(WorkspaceId,newRepetedNodeId,docName);
		
		docMgmtImpl.updateNodeAttrDetailForModifiedUser(WorkspaceId,newRepetedNodeId,userId);


		//nodeId=recordId;
		nodeId=docMgmtImpl.getmaxNodeId(WorkspaceId);
		usergrpcode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("usergroupcode").toString());
		usercode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		userType = ActionContext.getContext().getSession().get("usertypecode")
				.toString();
		UserName = ActionContext.getContext().getSession().get("username")
				.toString();
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		baseWorkRefFolder = propertyInfo.getValue("RefrenceFolder");
		UplodFileMail = propertyInfo.getValue("UplodFileMail");
		allowAutoCorrectionPdfProp = propertyInfo.getValue("PdfPropAutoCorrection");
		OpenFileAndSignOff = propertyInfo.getValue("OpenFileAndSignOff");
		passwordProtected = propertyInfo.getValue("PasswordProtected");
		fileSizeProperty = propertyInfo.getValue("FileUploadingSize");
		fileUploloadingSize = Integer.parseInt(fileSizeProperty);
		RefrenceFileUpload = propertyInfo.getValue("RefrenceFileUpload");
		baseWorkRefFolder = propertyInfo.getValue("RefrenceFolder");
		
		isReplaceFolderName='Y';
		remark="New";
		
		Vector<DTOWorkSpaceNodeDetail> getNodeDetail = new Vector<>();
		getNodeDetail = docMgmtImpl.getNodeDetail(WorkspaceId, nodeId);
		//nodeFolderName = getNodeDetail.get(0).getFolderName();
		
		String fileName = "";
		// Added By : Ashmak Shah
		int stageId = 10; // Created stage (By Default)

		// System.out.println(workspaceID +""+usergrpcode+" " +usercode +
		// ":"+UserName);

		DTOWorkSpaceMst workSpace = docMgmtImpl.getWorkSpaceDetailWSList(WorkspaceId);

		String baseWorkFolder = workSpace.getBaseWorkFolder();

		String tempBaseFolder = propertyInfo.getValue("BASE_TEMP_FOLDER");
		Date toDaydate= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(toDaydate);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH)+1;
		date = cal.get(Calendar.DAY_OF_MONTH);

		int TranNo = docMgmtImpl.getNewTranNo(WorkspaceId);
		File dirPath = new File(tempBaseFolder +"/"+year +"/"+month+"/"+date+"/"+ WorkspaceId + "/"+ nodeId + "/" + TranNo);
		//checking server is accessible or not
		if (!dirPath.exists() && !dirPath.mkdirs()) {
			//addActionMessage("pdfUpload");
			htmlContent="server not accessible";
			
			return "html";
	    }
		/**
		 * If user wants to use the source file then first convert it to PDF
		 * first and use it
		 * */
		/*if (useSourceFile == 'Y') {
			ArrayList<DTOWorkspaceNodeDocHistory> docHistory = docMgmtImpl
					.getLatestNodeDocHistory(workspaceID, nodeId);
			String baseSrcPath = propertyInfo.getValue("SOURCE_DOC_FOLDER");

			File srcDoc = new File(baseSrcPath + docHistory.get(0).getDocPath()
					+ "/" + docHistory.get(0).getDocName());
			uploadFile = FileManager.convertDocument(srcDoc, "pdf");
			uploadFileFileName = uploadFile.getName();
		}*/
		
		uploadFileFileName = uploadFileFileName.replace(' ', '_');
		// isReplaceFileName='Y';

		// if isReplaceFileName check box is checked
		if (isReplaceFileName == 'Y' && uploadFileFileName != null) {
			int dotIndex = nodeFolderName.lastIndexOf(".");
			String fileNameExt = uploadFileFileName
					.substring(uploadFileFileName.lastIndexOf(".") + 1);
			fileNameExt = fileNameExt.toLowerCase();
			String name = "";

			// System.out.println("====>"+nodeFolderName.substring(0,dotIndex));

			if (dotIndex != -1) {
				name = nodeFolderName.substring(0, dotIndex);
			} else {
				name = nodeFolderName;
			}

			// System.out.println("Name="+name);
			fileName = name + "." + fileNameExt;
			// System.out.println("fileNameExt:::"+fileNameExt);
			// System.out.println("fileName::"+fileName);

		} else {// if isReplaceFileName check box is unchecked
			fileName = uploadFileFileName;
			nodeFolderName =uploadFileFileName;
		}
		

		byte[] fileData = null;
		int fileSize = 0;
		if (uploadFile != null) {
			fileSize = new Long(uploadFile.length()).intValue();
		
		//int fileSize1 = (fileUploloadingSize/10)+1;
		/*if((fileSize/fileUploloadingSize) >fileSize1) {
			addActionMessage("FileUploadingSize"); 
			return INPUT;
			}*/
		
			double fileSizeInBytes = uploadFile.length();
			
			double fileSizeInKB = fileSizeInBytes / 1024;

	         fileSizeInMB = (fileSizeInKB / 1024);
	         DecimalFormat df2 = new DecimalFormat("#.##");
	         df2.setRoundingMode(RoundingMode.UP);
	         System.out.println("String : " + df2.format(fileSizeInMB)); 
	         fileSizeInMB= Double.parseDouble(df2.format(fileSizeInMB));  
	         
	        
	      	if (fileSizeInMB > fileUploloadingSize) {
	      		addActionMessage("FileUploadingSize"); 
				htmlContent = "You can't upload file size greate than "+fileUploloadingSize+" MB.";
				return "html";
			}
		} 
		
		//FileSize For Reference File
				/*byte[] ReffileData = null;
				int ReffileSize = 0;
				if (uploadRefFile != null) {
					ReffileSize = new Long(uploadRefFile.length()).intValue();
				
				//int fileSize1 = (fileUploloadingSize/10)+1;
				if((fileSize/fileUploloadingSize) >fileSize1) {
					addActionMessage("FileUploadingSize"); 
					return INPUT;
					}
				
					double fileSizeInBytes = uploadRefFile.length();
					
					double fileSizeInKB = fileSizeInBytes / 1024;
			         ReffileSizeInMB = (fileSizeInKB / 1024);
			         DecimalFormat df2 = new DecimalFormat("#.##");
			         df2.setRoundingMode(RoundingMode.UP);
			         System.out.println("String : " + df2.format(ReffileSizeInMB)); 
			         ReffileSizeInMB= Double.parseDouble(df2.format(ReffileSizeInMB));  
			         
			        
			      	if (ReffileSizeInMB > fileUploloadingSize) {
			      		addActionMessage("FileUploadingSize"); 
						htmlContent = "You can't upload Reference file size greater than "+fileUploloadingSize+" MB.";
						return "html";
					}
			      	
			}*/
		
		/*InputStream sourceInputStream = null;
		try {
			if (uploadFile != null) {
				fileData = getBytesFromFile(uploadFile);
				sourceInputStream = new FileInputStream(uploadFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		InputStream sourceInputStream = null;
		
		boolean flagtemp=false;
		try {
			if (uploadFile != null) {
				
					fileData = getBytesFromFile(uploadFile);
					sourceInputStream = new FileInputStream(uploadFile);
					if(passwordProtected.equalsIgnoreCase("yes")  && (fileName.contains(".pdf") || fileName.contains(".PDF")))
					{
						//PDDocument document = PDDocument.load(uploadFile);
						PDDocument document = PDDocument.load(uploadFile);
	
					    if(document.isEncrypted())
					    {
					    	flagtemp=true;
					    }
					    document.close();
					}
				
			}
			 if(flagtemp==true && passwordProtected.equalsIgnoreCase("yes")){
				  flagtemp = false;
				  //addActionMessage("EncryptedFile");
			    	htmlContent ="You are try to upload password protected file. \nPlease remove password protection security and re-upload the same.";
			    	return "html";
			 }  
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*InputStream RefsourceInputStream = null;
		
		boolean Refflagtemp=false;
		try {
			if (uploadRefFile != null) {
				
					ReffileData = getBytesFromFile(uploadRefFile);
					RefsourceInputStream = new FileInputStream(uploadRefFile);
					if(passwordProtected.equalsIgnoreCase("yes")  && (fileName.contains(".pdf") || fileName.contains(".PDF")))
					{
						//PDDocument document = PDDocument.load(uploadFile);
						PDDocument document = PDDocument.load(uploadRefFile);
	
					    if(document.isEncrypted())
					    {
					    	flagtemp=true;
					    }
					    document.close();
					}
				
			}
			 if(Refflagtemp==true && passwordProtected.equalsIgnoreCase("yes")){
				 Refflagtemp = false;
				  //addActionMessage("EncryptedFile");
			    	htmlContent ="You are try to upload password protected Reference file. \nPlease remove password protection security and re-upload the same.";
			    	return "html";
			 }  
			  
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		/**
		 * If Source file is used at the node then deleting the converted PDF
		 * file.
		 * 
		 * */
		
		/*if (useSourceFile == 'Y') {
			FileManager.deleteFile(uploadFile);
		}*/

		Vector<DTOWorkSpaceNodeAttrDetail> attrValueId = new Vector<DTOWorkSpaceNodeAttrDetail>();
		Vector<DTOWorkSpaceNodeAttrHistory> attrValueIdForattrHistory = new Vector<DTOWorkSpaceNodeAttrHistory>();

		DTOWorkSpaceNodeAttrHistory wsnahdto = new DTOWorkSpaceNodeAttrHistory();
		DTOWorkSpaceNodeAttrDetail wsnad = new DTOWorkSpaceNodeAttrDetail();

		// FileLastModified attribute value
		Timestamp ts = new Timestamp(new Date().getTime());

		wsnad = new DTOWorkSpaceNodeAttrDetail();
		wsnad.setWorkspaceId(WorkspaceId);
		wsnad.setNodeId(nodeId);
		wsnad.setAttrValue(ts.toString());
		wsnad.setAttrId(-999);
		wsnad.setStatusIndi('N');
		wsnad.setModifyBy(usercode);
		wsnad.setTranNo(TranNo);
		attrValueId.addElement(wsnad);

		wsnahdto = new DTOWorkSpaceNodeAttrHistory();
		wsnahdto.setWorkSpaceId(WorkspaceId);
		wsnahdto.setNodeId(nodeId);
		wsnahdto.setAttrValue(ts.toString());
		wsnahdto.setAttrId(-999);
		wsnahdto.setStatusIndi('N');
		wsnahdto.setModifyBy(usercode);
		wsnahdto.setTranNo(TranNo);
		attrValueIdForattrHistory.addElement(wsnahdto);
		// ////////////////////////////////////////////

		request = ServletActionContext.getRequest();
		dtoList = new ArrayList<DTOWorkspaceNodeReminderDoneStatus>();
		Vector<DTOWorkSpaceNodeHistory> nodeHistoryList = docMgmtImpl.getLastNodeHistory(WorkspaceId, nodeId);
		int tranNo = 0;
		if (nodeHistoryList.size() > 0) {
			for (int i = 0; i < nodeHistoryList.size(); i++) {
				DTOWorkSpaceNodeHistory dto = nodeHistoryList.get(i);
				tranNo = dto.getTranNo();
				// System.out.println("Last tran="+tranNo);

			}
		}
		Vector<DTOWorkSpaceNodeAttrHistory> attrHistory = docMgmtImpl.getWorkspaceNodeAttrHistorybyTranNo(WorkspaceId, nodeId,tranNo);
		/*
		 * System.out.println("attrhistory" + attrHistory.size()); for(int
		 * i=0;i<attrHistory.size();i++) { System.out.println("i" + i);
		 * System.out.println(attrHistory.get(i).getAttrId());
		 * System.out.println(attrHistory.get(i).getAttrValue()); }
		 * System.out.println();
		 */
		
		docMgmtImpl.markReminderAsUnDone(dtoList);
		docMgmtImpl.updateNodeAttrDetail(attrValueId);

		/**
		 * Added By : Ashmak Shah Reason : Add new history data for STF XML node
		 * in case of 'Save and Send' of any STF child node whose NodeTypeIndi
		 * is 'S'
		 * 
		 * if current node is an STF child node and its operation is not
		 * 'delete' then it is required that its STF XML Node should also be
		 * changed and added in next submission sequence so we have to update
		 * XML Node's history to publish it.
		 * 
		 */
		wsnd = docMgmtImpl.getNodeDetail(WorkspaceId,nodeId).get(0);
		if (isReplaceFolderName == 'Y' && uploadFileFileName != null) {
			
			if(nodeFolderName.contains(".")){
				fileName = uploadFileFileName;
			}else{
				fileName = uploadFileFileName.substring(0, uploadFileFileName.indexOf("."));
			}
				//wsnd.setFolderName(fileName);
				//docMgmtImpl.insertWorkspaceNodeDetail(wsnd, 2);
		}
		//Added by Harsh Shah for changing the extension only
		else if(uploadFileFileName != null){
			if(nodeFolderName.contains(".")){
				String filename=nodeFolderName.substring(0, nodeFolderName.lastIndexOf("."));
				String ext = uploadFileFileName.substring(uploadFileFileName.lastIndexOf("."), uploadFileFileName.length());
				fileName = filename+ext;
			}else{
				fileName = uploadFileFileName.substring(0, uploadFileFileName.indexOf("."));
			}
			//wsnd.setFolderName(fileName);
			//docMgmtImpl.insertWorkspaceNodeDetail(wsnd, 2);
		}		
		if (wsnd.getNodeTypeIndi() == 'S') {

			if (!nodeOperationValue.equalsIgnoreCase("delete")) {
				addSTFXMLNodeHistory(wsnd);
			} else {
				deleteFile = 'Y';
			}

			stageId = 100;// STF child will be in approved stage by default

		}
		/***********************************************************************************************************/

		/**
		 * Code Added By : Ashmak Shah Code Added On : 19-Aug-09 To delete a
		 * file from node or To carry forward the previous file with new tranNo
		 */
		if (deleteFile == 'Y') {// delete file is checked or operation attribute
			// is 'delete'

			updateNodeAttrHistory(WorkspaceId, nodeId, TranNo, usercode,
					keyword, description, attrValueId);
			insertCheckedOutFileDetail(WorkspaceId, nodeId, TranNo, usercode,
					"No File");

			updateNodeHistory(WorkspaceId, nodeId, TranNo, "No File", usercode,
					stageId, remark);
			insertWorkspaceNodeVersionHistory(WorkspaceId, nodeId, TranNo,
					usercode, versionSuffix, versionPrefix);

		} else { // else uploading the file

			if (fileSize > 0) { // if new file is uploaded
				if(remark.isEmpty() || remark==""){
					remark="New";
				}
				/*if(RefrenceFileUpload.equalsIgnoreCase("yes") && ReffileSize>0){
					RefFileName = RefFileName.trim();
					RefFileName = RefFileName.replace(' ', '_');
					uploadRefFileOnNode(WorkspaceId, nodeId, TranNo, baseWorkRefFolder, ReffileData,
							RefFileName, ReffileSize, RefsourceInputStream);
					}*/
				
				if(RefrenceFileUpload.equalsIgnoreCase("yes")){
					if(files1!=null){
					 for(int a=0;a<files1.size();a++){
						 	String refFileNames[] =RefFileName.split(",");
						 	uploadRefFile = new File(files1.get(a).getAbsolutePath());
				            System.out.print("\nFile ["+a+"] ");
				            System.out.print("; name:"         + refFileNames[a]);
				            System.out.print("; length: "      + uploadRefFile.length());
				            
				            int tranNoForAttachmenet = docMgmtImpl.getNewTranNoForAttachment(WorkspaceId,nodeId);
							byte[] ReffileData = null;
							int ReffileSize = 0;
							if (uploadRefFile != null) {
								ReffileSize = new Long(uploadRefFile.length()).intValue();
							
								double fileSizeInBytes = uploadRefFile.length();
								
								double fileSizeInKB = fileSizeInBytes / 1024;
						         ReffileSizeInMB = (fileSizeInKB / 1024);
						         DecimalFormat df2 = new DecimalFormat("#.##");
						         df2.setRoundingMode(RoundingMode.UP);
						         System.out.println("String : " + df2.format(ReffileSizeInMB)); 
						         ReffileSizeInMB= Double.parseDouble(df2.format(ReffileSizeInMB));  		      	
						}
							InputStream RefsourceInputStream = null;
							
							try {
								ReffileData = getBytesFromFile(uploadRefFile);
								RefsourceInputStream = new FileInputStream(uploadRefFile);
														
							} catch (IOException e) {
								e.printStackTrace();
							}
				            if(ReffileSize>0){
				            	/*RefFileName = uploadRefFile.getName();
								RefFileName = RefFileName.trim();
								RefFileName = RefFileName.replace(' ', '_');*/
				            	String RefFileName = refFileNames[a];
								RefFileName = RefFileName.trim();
								RefFileName = RefFileName.replace(' ', '_');
								uploadRefFileOnNode(WorkspaceId, nodeId, TranNo,tranNoForAttachmenet, baseWorkRefFolder, ReffileData,
										RefFileName, ReffileSize, RefsourceInputStream);
								DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
								dto.setWorkSpaceId(WorkspaceId);
								dto.setNodeId(nodeId);
								dto.setTranNo(TranNo);
								dto.setRefFilePath(year + "/"+ month+"/"+ date+"/" + WorkspaceId + "/"+ nodeId + "/" + tranNoForAttachmenet);
								dto.setRefFileName(RefFileName);
								dto.setModifyBy(usercode);
								dto.setFileSize(ReffileSizeInMB);
								dto.setRemark("");
								docMgmtImpl.insertRefFileDetail(dto);
								}
				        	}
						}
					}
				uploadFileOnNode(WorkspaceId, nodeId, baseWorkFolder, fileData,
						fileName, usercode, keyword, description,
						tempBaseFolder, attrValueId, TranNo, fileSize,
						sourceInputStream, versionSuffix, versionPrefix,
						stageId, remark);

			} else { // else uploading the previous file with new tranNo

				int lastTranNo = docMgmtImpl.getMaxTranNo(WorkspaceId, nodeId);
				DTOWorkSpaceNodeHistory previousHistory = docMgmtImpl
						.getWorkspaceNodeHistorybyTranNo(WorkspaceId, nodeId,
								lastTranNo);

				if (previousHistory.getFileName() == null
						|| previousHistory.getFileName().equalsIgnoreCase(
								"No File")) {

					updateNodeAttrHistory(WorkspaceId, nodeId, TranNo,
							usercode, keyword, description, attrValueId);
					insertCheckedOutFileDetail(WorkspaceId, nodeId, TranNo,
							usercode, "No File");

					updateNodeHistory(WorkspaceId, nodeId, TranNo, "No File",
							usercode, stageId, remark);
					insertWorkspaceNodeVersionHistory(WorkspaceId, nodeId,
							TranNo, usercode, versionSuffix, versionPrefix);

				} else {

					File file = new File(baseWorkFolder
							+ previousHistory.getFolderName() + "/"
							+ previousHistory.getFileName());

					try {
						sourceInputStream = new FileInputStream(file);
						fileSize = new Long(file.length()).intValue();
						fileName = file.getName();
						fileData = getBytesFromFile(file);
						uploadFileOnNode(WorkspaceId, nodeId, baseWorkFolder,
								fileData, fileName, usercode, keyword,
								description, tempBaseFolder, attrValueId,
								TranNo, fileSize, sourceInputStream,
								versionSuffix, versionPrefix, stageId, remark);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
			
		docMgmtImpl.InsertUpdateNodeAttrHistory(attrValueIdForattrHistory);
		if(UplodFileMail.equalsIgnoreCase("Yes")){
		  StageWiseMailReport stageWiseMail = new StageWiseMailReport();
		  //stageWiseMail.UploadFileMail(workspaceID,nodeId,usercode);
		  stageWiseMail.UploadFileMailNewFormate(WorkspaceId,nodeId,usercode);
		  System.out.println("Mail Sent....");
	    }
		
		
			//if doc or docx file, converting to PDF at uploaded file location
			String srcFileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
			if(srcFileExt.equalsIgnoreCase("doc") || srcFileExt.equalsIgnoreCase("docx"))
			{
				
				tranNo=docMgmtImpl.getMaxTranNo(WorkspaceId, nodeId);
				ArrayList<DTOWorkSpaceNodeHistory> dto = docMgmtImpl.showFullNodeHistoryForESignatureByTranNo(WorkspaceId, nodeId,tranNo);
				
		 		String srcPath=dto.get(0).getBaseWorkFolder()+ dto.get(0).getFolderName() + "/" + dto.get(0).getFileName();
		 		String fileNameWithoutExt=fileName.substring(0, fileName.lastIndexOf('.'));
		 		String pdfFileName=fileNameWithoutExt+".pdf";
		 		String destPath=dto.get(0).getBaseWorkFolder()+ dto.get(0).getFolderName() + "/" + pdfFileName;
		 		
				/*//String response=docToPdfURL+"inputPath="+srcPath+"&outputPath="+destPath;
				String response=docMgmtImpl.convertPdfToDoc(srcPath,destPath);
				
				String[] parts = response.split(":");
				if(parts[0].equalsIgnoreCase("error")){
					htmlContent=parts[2];
					return "html";*/
		 		String response=docMgmtImpl.convertDoc(srcPath,destPath);
				
				 String[] parts = response.split("#");
				if(parts[0].equalsIgnoreCase("False")){
					htmlContent=parts[2];
					return "html";
				}
			}
			//conversion ends
		
		//BlockChain HashCode
		String hashCode=docMgmtImpl.generateHashCode(WorkspaceId,nodeId,stageId);
		//BlockChain HashCode Ends
		
		
		return SUCCESS;
	}
	
	public String GetDocSignRecordFOrESignature(){
		WorkspaceId = docId;
		nodeId = recordId;
		docTypeName = docMgmtImpl.getDocTypeName(docType);
		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		//DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetail(WorkspaceId);
		DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetailWSList(WorkspaceId);
		workspaceUserGroupsDetails=docMgmtImpl.getAllUserGroupsForDocSign();
		projectCode = wsDetail.getProjectCode();
		nodeDetail = docMgmtImpl.getNodeDetail(WorkspaceId, nodeId);
		DTOWorkSpaceNodeDetail dto =(DTOWorkSpaceNodeDetail)nodeDetail.elementAt(0);		
		nodeName = dto.getNodeDisplayName();
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(userCode);
		userMst.setUserGroupCode(userGroupCode);

		assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForESignature(WorkspaceId, nodeId,"modulewise");
		getStageDetail = docMgmtImpl.getStageDetailCSV();
		
		checkVoidFile = docMgmtImpl.getVoidFileHistory(WorkspaceId,nodeId);
		if(checkVoidFile.size()>0){
			isVoidFlag=false;
		}
		
		ArrayList<Integer> assignedStageIds = docMgmtImpl.getStageIdsRightsWise(WorkspaceId,nodeId);
		
		if(!assignedStageIds.contains(30)){
			for(int i = 0;i<getStageDetail.size();i++){
				if(getStageDetail.get(i).getStageId() == 30){
					getStageDetail.remove(i);
					break;
				}
			}
			
		}
		getUserRightsDetail = docMgmtImpl.getUserRightsDetailForESignautre(WorkspaceId, nodeId);
		
		
		/*if(!userTypeName.equalsIgnoreCase("WA")){
			int userId;
		for(int j=0;j<getUserRightsDetail.size();j++){
			userId=getUserRightsDetail.get(j).getUserCode();
			if(userCode!=userId){
				getUserRightsDetail.remove(j);
				j--;
			}
		}
			}*/
		ArrayList<DTOWorkSpaceNodeHistory> WorkspaceUserHistory;
		Vector<DTOWorkSpaceUserRightsMst> WorkspaceUserDetailList;
		
		WorkspaceUserDetailList=docMgmtImpl.getUserRightsDetail(WorkspaceId, nodeId);
		
		for(int i=0;i<getUserRightsDetail.size();i++){
			
		//WorkspaceUserHistory=docMgmtImpl.showNodeHistory(WorkspaceId,getUserRightsDetail.get(i).getUserCode(),nodeId,getUserRightsDetail.get(i).getStageId());
		WorkspaceUserHistory=docMgmtImpl.showNodeHistoryForESignature(WorkspaceId,getUserRightsDetail.get(i).getUserCode(),nodeId);
		if(WorkspaceUserHistory.size()>0){
			getUserRightsDetail.get(i).setUserFlag('Y');
			//WorkspaceUserDetailList.set(i, WorkspaceUserDetailList.get(i));
			}
			else{
				getUserRightsDetail.get(i).setUserFlag('N');
				//WorkspaceUserDetailList.set(i, WorkspaceUserDetailList.get(i));
				//countForUser="false";
			}
		}
		
		userTypeCode=docMgmtImpl.getUserType(userCode);
		
		return SUCCESS;
	}
	
	public String AssignRecordRights(){
		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		
	/*	if(userGroupYN.equals("usergroup"))
		{
			ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
			
			
			DTOWorkSpaceUserMst dtoWorkSpaceUserMst = new DTOWorkSpaceUserMst();
			DTOWorkSpaceUserMst deletewsurmdata = new DTOWorkSpaceUserMst();
			DTOWorkSpaceUserRightsMst dtowsurm = new DTOWorkSpaceUserRightsMst();
			
			dtoWorkSpaceUserMst.setWorkSpaceId(WorkspaceId);
			dtowsurm.setWorkSpaceId(WorkspaceId);
			deletewsurmdata.setWorkSpaceId(WorkspaceId);
			deletewsurmdata.setUserGroupCode(userGroupCode);
			
			
			if(userGroupYN.equals("usergroup"))
			{
				Vector<DTOUserMst> workspaceUserDetails=docMgmtImpl.getuserDetailsByUserGrp(userGroupCode);
				
				userCodeForGrp=new int[workspaceUserDetails.size()];
				for(int i =0;i<workspaceUserDetails.size();i++)
				{
					DTOUserMst userMstDTO = workspaceUserDetails.get(i);
					userCodeForGrp[i] = userMstDTO.getUserCode();
				}
				dtoWorkSpaceUserMst.setUserGroupCode(userGroupCode);
				dtowsurm.setUserGroupCode(userGroupCode);
			}
			
			dtoWorkSpaceUserMst.setAdminFlag('N');
			Timestamp ts = new Timestamp(new Date().getTime());
			dtoWorkSpaceUserMst.setLastAccessedOn(ts);
			dtoWorkSpaceUserMst.setRemark(remark);
			int ucd=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			dtoWorkSpaceUserMst.setModifyBy(ucd);
			dtoWorkSpaceUserMst.setModifyOn(ts);			
			Calendar cal = Calendar.getInstance();
			Date today = cal.getTime();
			dtoWorkSpaceUserMst.setFromDt(today);
			cal.add(Calendar.YEAR, 50); // to get previous year add -1
			Date nextYear = cal.getTime();
			dtoWorkSpaceUserMst.setToDt(nextYear);
				dtoWorkSpaceUserMst.setRightsType("modulewise");
				deletewsurmdata.setRightsType("modulewise");
				
				for(int userId=0;userId<userCodeForGrp.length; userId++)
				{
					deletewsurmdata.setUserCode(userCodeForGrp[userId]);
					docMgmtImpl.DeleteProjectlevelRights(deletewsurmdata);
				}
			docMgmtImpl.insertUpdateUsertoWorkspace(dtoWorkSpaceUserMst, userCodeForGrp);
			String stageDesc="";
			
				dtoWorkSpaceUserMst.setStages("-");
			dtoWorkSpaceUserMst.setMode(1);
			docMgmtImpl.insertUpdateUsertoWorkspaceHistory(dtoWorkSpaceUserMst, userCodeForGrp);
			addActionMessage(" Selected User Group is added to the project successfully.");
		}*/
		
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String roleCode="";
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();

		WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(WorkspaceId,nodeId);
		String nodeIdsCSV = "";
		String selectedUsers [] = userCode;
		for(int i=0;i<WsNodeDetail.size();i++){	
			DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
			nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
			
		}
		Vector<DTOWorkSpaceUserMst> getUserRightsForWorksapce=new Vector<>();
		nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
		
		for(int i =0;i<userCode.length;i++){
		getUserRightsForWorksapce=docMgmtImpl.getUserRightsForESignature(WorkspaceId, Integer.parseInt(userCode[i]),userWiseGroupCode);
		if(getUserRightsForWorksapce.size()==0){
			
			DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
			dto.setWorkSpaceId(WorkspaceId);
			dto.setUserCode(Integer.parseInt(userCode[i]));
			dto.setUserGroupCode(userWiseGroupCode);
			dto.setAdminFlag('N');
			
	        Date date = new Date();
	        Timestamp currentDate=new Timestamp(date.getTime());  
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            System.out.println(formatter.format(currentDate));   
	        
            // Convert Date to Calendar
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.add(Calendar.YEAR, 50);
	        
	        Date afterDate = c.getTime();
	        Timestamp toDate=new Timestamp(afterDate.getTime()); 		
			
			dto.setLastAccessedOn(currentDate);
			dto.setRemark("");
			dto.setModifyBy(userId);
			dto.setModifyOn(currentDate);
			dto.setFromDt(currentDate);
			dto.setToDt(toDate);
			dto.setRightsType("modulewise");
			roleCode = docMgmtImpl.getRoleCodeFromUserMst(Integer.parseInt(userCode[i]), userWiseGroupCode);
			dto.setRoleCode(roleCode);

			//docMgmtImpl.insertUsertoWorkspaceUserMstForDocSign(dto);
			
			docMgmtImpl.insertUsertoWorkspaceUserMstForDocSignWithRoleCode(dto);
		}
		
			
		//Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForCSV(WorkspaceId, nodeId,Integer.parseInt(userCode[i]));
		//Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForTimeLine = docMgmtImpl.getUserRightsDetailForCSVforTimeLine(WorkspaceId, nodeId,Integer.parseInt(userCode[i]));
		
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			stageIds = new int[1];
			stageIds[0]=100;
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForESignature(WorkspaceId, Integer.parseInt(userCode[i]), stageIds,nodeIdsCSV);
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(WorkspaceId, Integer.parseInt(userCode[i]), stageIds,nodeIdsCSV);
			
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeId);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			String stageDesc="";
			String stage;
			int stageid;
				for(int j=0;j<stageIds.length;j++){
					stageid=stageIds[j];
					stage= docMgmtImpl.getStageDescForESignature(stageid);
					stageDesc+=stage+",";
				}
			System.out.println("StageId="+stageDesc);
			  if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
			  objWSUserRightsMstforModuleHistory.setMode(2);
			  
			  DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCode[i]));
			  System.out.println(objUserMst.getUserGroupCode());
			  Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
			  objWSUserRightsMstforModuleHistory.setUserCode(Integer.parseInt(userCode[i]));
			  objWSUserRightsMstforModuleHistory.setRemark("test");
			  objWSUserRightsMstforModuleHistory.setMode(1);
			  roleCode = docMgmtImpl.getRoleCodeFromUserMst(Integer.parseInt(userCode[i]), userGroupCode.intValue());
			  objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
			  //docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
			  docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	
		System.out.println("-----------------------Updating Rights------------------------");
		
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
		
			WsNodeDetail.clear();
			WsNodeDetail=docMgmtImpl.getChildNodesModulewise(WorkspaceId,nodeId);
			if(nodeId==1)
			{
				WsNodeDetail.removeElementAt(1);
			}
			nodeIdsCSV="";
			for(int k=0;k<WsNodeDetail.size();k++){	
				DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(k);
				nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
			}
			nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
			
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForESignature(WorkspaceId, Integer.parseInt(userCode[i]), stageIds,nodeIdsCSV);
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(WorkspaceId, Integer.parseInt(userCode[i]), stageIds,nodeIdsCSV);
			
			for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				
					objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCode[i]));
					userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(WorkspaceId);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(Integer.parseInt(userCode[i]));
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					roleCode = docMgmtImpl.getRoleCodeFromUserMst(Integer.parseInt(userCode[i]), userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setRoleCode(roleCode);
					objWorkSpaceUserRightsMst.setMode(1);
					maintainSeq = docMgmtImpl.getAttributeDetailByAttrName(WorkspaceId, nodeId, "SignOff In Sequence").getAttrValue();
					System.out.println("maintainSeq--- "+maintainSeq);
					if(maintainSeq!=null && maintainSeq.equalsIgnoreCase("Yes")){
						getUserRightsDetail = docMgmtImpl.getUserRightsDetailForESignautre(WorkspaceId, nodeId);
						int size=getUserRightsDetail.size();
						objWorkSpaceUserRightsMst.setSeqNo(size+1);
					}
					else{
						objWorkSpaceUserRightsMst.setSeqNo(0);
					}
					
					for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIds[istageIds]);
						userRightsList.add(dtoClone);
					}
				}
			//docMgmtImpl.insertFolderSpecificMultipleUserRightsForESignature(userRightsList);
			
			
			
			docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCodeForESignature(userRightsList);
			
			//sending mail to first attached user if seq yes
			if(maintainSeq!=null && maintainSeq.equalsIgnoreCase("Yes")){
				System.out.println("maintainSeq---->>>"+maintainSeq);
				Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForEsignature=
						docMgmtImpl.getUserRightsDetailForEsignatureByUsercode(WorkspaceId,nodeId,Integer.parseInt(userCode[i]));
				System.out.println("getUserRightsDetailForEsignature size---->>>"+getUserRightsDetailForEsignature.size());
				int seqNo=getUserRightsDetailForEsignature.get(0).getSeqNo();
				System.out.println("seqNo---->>>"+seqNo);
				if(seqNo==1){
					System.out.println("if seqNo 1 mail sent to ---->>>"+Integer.parseInt(userCode[i]));
					StageWiseMailReport stageWiseMail = new StageWiseMailReport();
					stageWiseMail.StageWiseMailForESignatureForAttachedUser(WorkspaceId,nodeId,Integer.parseInt(userCode[i]),"Send Back");
				}
				if(seqNo!=1){
					System.out.println("else seqNo---->>>"+seqNo);
					//seqNo = seqNo - 1;
					Vector<DTOWorkSpaceNodeHistory> tempDocHistory = 
							docMgmtImpl.getProjectSignOffHistoryForESignature(WorkspaceId,nodeId);
					System.out.println("tempDocHistory---->>>"+tempDocHistory.size());
					if(tempDocHistory.size()>0){
						boolean isUserSigned=false;
						seqNo = seqNo - 1;
						System.out.println("seqNo now---->>>"+seqNo);
						Vector<DTOWorkSpaceUserRightsMst>  getSeqSigned=docMgmtImpl.getUserFromSeqNo(WorkspaceId,nodeId,seqNo);
						System.out.println("getSeqSigned---->>>"+getSeqSigned.size());
						for(int j=0;j<tempDocHistory.size();j++){
							if(tempDocHistory.get(j).getUserCode()==getSeqSigned.get(0).getUserCode()){
								isUserSigned=true;
							}
						}
						
						if(getSeqSigned.size()>0 && isUserSigned==true) {
							StageWiseMailReport stageWiseMail = new StageWiseMailReport();
							stageWiseMail.StageWiseMailForESignatureForAttachedUser(WorkspaceId,nodeId,Integer.parseInt(userCode[i]),"Send Back");
						}
					}
					
					
				}
				
			}
			if(maintainSeq!=null && maintainSeq.equalsIgnoreCase("No")){
				System.out.println("maintainSeq---->>>"+maintainSeq);
				String locationName=docMgmtImpl.getUserByCode(Integer.parseInt(userCode[i])).getUserLocationName();
				String countryCode=docMgmtImpl.getUserByCode(Integer.parseInt(userCode[i])).getCountyCode();
				Vector<DTOWorkSpaceNodeHistory> getNodeHistory=docMgmtImpl.getFirstNodeHistoryByTranNoAndCountryCode(WorkspaceId, nodeId,locationName,countryCode);
				StageWiseMailReport stageWiseMail = new StageWiseMailReport();
				stageWiseMail.StageWiseMailForESignatureForAttachedUser(WorkspaceId,nodeId,Integer.parseInt(userCode[i]),"Send Back");
				System.out.println("mail sent to ---->>>"+Integer.parseInt(userCode[i]));
			}
			//sending mail to attached user ends
			
			
		/*	for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				
					objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCode[i]));
					userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(5);
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					objWorkSpaceUserRightsMst.setDuration(duration);
					
					for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIds[istageIds]);
						userRightsListForTimeLine.add(dtoClone);
					}
			}
			docMgmtImpl.insertFolderSpecificMultipleUserRightsForTimeLine(userRightsListForTimeLine);*/
		}
		return SUCCESS;
		
	}
	
	public String getUserDtl(){

		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		
		htmlContent = "";
		workspaceuserdtl=docMgmtImpl.getuserDetailsByUserGrpForDocSign(userGroupId);
		//getUserRightsDetailToShow = docMgmtImpl.getUserRightsDetail("0068", nodeId);
		
		assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWorkspaceUserDetailForESignature(WorkspaceId, nodeId,userGroupId);
		ArrayList<DTOUserMst> arraylist = new ArrayList<DTOUserMst>(assignWorkspaceRightsDetails);
		
		for (DTOUserMst userList : arraylist) {
			if(!htmlContent.equals("")){
				htmlContent += ","; 
			}
			htmlContent += userList.getUserCode()+"::"+userList.getUserName();
		}		
		return "html";
	}
	
	
	public void DeleteUserRights()
	{
		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();
		WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(WorkspaceId,nodeId);
		String nodeIdsCSV = "";
		String selectedUsers [] = userCode;
		
		for(int i=0;i<WsNodeDetail.size();i++){	
			DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
			nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
		}
		nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
		
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForCSV(WorkspaceId, nodeId,Integer.parseInt(userCode[0]));
		String roleCode = docMgmtImpl.getRoleCodeFromWSUserRightsMst(WorkspaceId,nodeId,Integer.parseInt(userCode[0]));
		docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForESignature(WorkspaceId, Integer.parseInt(userCode[0]), stageIds,nodeIdsCSV);
		docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(WorkspaceId, Integer.parseInt(userCode[0]), stageIds,nodeIdsCSV);
		
		//Rearranging sequences
		
		getUserRightsDetail = docMgmtImpl.getUserRightsDetailForESignautre(WorkspaceId, nodeId);
		int size=0;
		for(int i=0;i<getUserRightsDetail.size();i++){
		size=size+1;
			//objWorkSpaceUserRightsMst.setSeqNo(size+1);
			docMgmtImpl.updateMaintainedSeq(WorkspaceId, getUserRightsDetail.get(i).getNodeId(),
					getUserRightsDetail.get(i).getUserCode(),getUserRightsDetail.get(i).getUserGroupCode(),size);
		}
		//ends
		
		
		objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
		objWSUserRightsMstforModuleHistory.setNodeId(nodeId);
		objWSUserRightsMstforModuleHistory.setModifyBy(userId);
		
		String stageDesc="";
		String stage;
		int stageid;
		for(int i=0;i<stageIds.length;i++){
		 	stageid=stageIds[i];
			stage= docMgmtImpl.getStageDesc(stageid);
			stageDesc+=stage+",";
		}
		System.out.println("StageId="+stageDesc);
		if (stageDesc != null && stageDesc.length() > 0){
		    stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
		 }
		objWSUserRightsMstforModuleHistory.setStages(stageDesc);
		objWSUserRightsMstforModuleHistory.setMode(2);
		 
		DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCode[0]));
		System.out.println(objUserMst.getUserGroupCode());
		Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
		objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
		objWSUserRightsMstforModuleHistory.setUserCode(Integer.parseInt(userCode[0]));
		objWSUserRightsMstforModuleHistory.setRemark(remark);
		roleCode = docMgmtImpl.getRoleCodeFromUserMst(Integer.parseInt(userCode[0]), userGroupCode.intValue());
		 objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
		objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				
		docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	}
	
	public String getUserRights(){		
	
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(Integer.parseInt(userCode[0]));
		userMst.setUserGroupCode(userGroupCode);
		lbl_folderName = knetProperties.getValue("ForlderName");
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetail(workspaceID, userMst,"modulewise");
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(WorkspaceId, nodeId,"modulewise");
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForDocSign(WorkspaceId, nodeId,userGroupId);
		assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForESignatureWithRoleCode(WorkspaceId, nodeId,userGroupId,roleCode);
		
		return SUCCESS;
	}
	
	public String getUserRole(){		
		
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(Integer.parseInt(userCode[0]));
		userMst.setUserGroupCode(userGroupCode);
		lbl_folderName = knetProperties.getValue("ForlderName");
		usrCode = userMst.getUserCode();
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetail(workspaceID, userMst,"modulewise");
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(WorkspaceId, nodeId,"modulewise");		
		roleDtl = docMgmtImpl.getRoleDtl(roleCode);
		
		return SUCCESS;
	}
	
	public void updateAndDeleteRights(){
		
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String rolCode="";
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();

		WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(WorkspaceId,nodeIdForChange);
		String nodeIdsCSV = "";
		String selectedUsers [] = userCodeForChange;
		for(int i=0;i<WsNodeDetail.size();i++){	
			DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
			nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
		}
		Vector<DTOWorkSpaceUserMst> getUserRightsForWorksapce=new Vector<>();
		nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
		
//		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForCSV(WorkspaceId, nodeIdForChange,Integer.parseInt(userCodeForChange[0]));
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForESignature(WorkspaceId, nodeIdForChange,Integer.parseInt(userCodeForChange[0]));
		if(getUserRightsDetail.size()>0)
			rolCode=getUserRightsDetail.get(0).getRoleCode();
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForTimeLine = docMgmtImpl.getUserRightsDetailForCSVforTimeLine(WorkspaceId, nodeIdForChange,Integer.parseInt(userCodeForChange[0]));
		DTOUserMst getUserDtl=docMgmtImpl.getUserInfo(Integer.parseInt(userCodeForChange[0]));
		
		getUserRightsForWorksapce=docMgmtImpl.getUserRightsForWorkspace(WorkspaceId, uCodeByForChange,getUserDtl.getUserGroupCode());
		if(getUserRightsForWorksapce.size()==0){
			
			DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
			dto.setWorkSpaceId(WorkspaceId);
			dto.setUserCode(uCodeByForChange);
			dto.setUserGroupCode(getUserDtl.getUserGroupCode());
			dto.setAdminFlag('N');
			
	        Date date = new Date();
	        Timestamp currentDate=new Timestamp(date.getTime());  
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            System.out.println(formatter.format(currentDate));   
	        
            // Convert Date to Calendar
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.add(Calendar.YEAR, 50);
	        
	        Date afterDate = c.getTime();
	        Timestamp toDate=new Timestamp(afterDate.getTime()); 		
			
			dto.setLastAccessedOn(currentDate);
			dto.setRemark("");
			dto.setModifyBy(userId);
			dto.setModifyOn(currentDate);
			dto.setFromDt(currentDate);
			dto.setToDt(toDate);
			dto.setRightsType("modulewise");
			dto.setRoleCode(rolCode);

		  //docMgmtImpl.insertUsertoWorkspaceUserMstForDocSign(dto);
		  docMgmtImpl.insertUsertoWorkspaceUserMstForDocSignWithRoleCode(dto);
		}
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForESignature(WorkspaceId, Integer.parseInt(userCodeForChange[0]), stageIdForChange,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(WorkspaceId, Integer.parseInt(userCodeForChange[0]), stageIdForChange,nodeIdsCSV);
			
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeIdForChange);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			String stageDesc="";
			String stage;
			int stageid;
				for(int j=0;j<stageIdForChange.length;j++){
					stageid=stageIdForChange[j];
					stage= docMgmtImpl.getStageDescForESignature(stageid);
					stageDesc+=stage+",";
				}
			System.out.println("StageId="+stageDesc);
			  if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
						  
			  DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCodeForChange[0]));
			  System.out.println(objUserMst.getUserGroupCode());
			  Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
			  objWSUserRightsMstforModuleHistory.setUserCode(Integer.parseInt(userCodeForChange[0]));
			  objWSUserRightsMstforModuleHistory.setRemark(remarkForChange);
			  objWSUserRightsMstforModuleHistory.setRoleCode(rolCode);
			  objWSUserRightsMstforModuleHistory.setMode(2);
				
				//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
				//docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	
		System.out.println("-----------------------Updating Rights------------------------");
		
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
		
			WsNodeDetail.clear();
			WsNodeDetail=docMgmtImpl.getChildNodesModulewise(WorkspaceId,nodeIdForChange);
			if(nodeIdForChange==1)
			{
				WsNodeDetail.removeElementAt(1);
			}
			nodeIdsCSV="";
			for(int k=0;k<WsNodeDetail.size();k++){	
				DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(k);
				nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
			}
			nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForESignature(WorkspaceId, uCodeByForChange, stageIdForChange,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(WorkspaceId, uCodeByForChange, stageIdForChange,nodeIdsCSV);
			
			for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				
					objUserMst = docMgmtImpl.getUserInfo(uCodeByForChange);
					userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(WorkspaceId);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(uCodeByForChange);
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					objWorkSpaceUserRightsMst.setRoleCode(rolCode);
					objWorkSpaceUserRightsMst.setMode(1);
					maintainSeq = docMgmtImpl.getAttributeDetailByAttrName(WorkspaceId, nodeDetail.getNodeId(), "SignOff In Sequence").getAttrValue();
					System.out.println("maintainSeq--- "+maintainSeq);
					if(maintainSeq!=null && maintainSeq.equalsIgnoreCase("Yes")){
						getUserRightsDetail = docMgmtImpl.getUserRightsDetailForESignautre(WorkspaceId, nodeDetail.getNodeId());
						int size=getUserRightsDetail.size();
						objWorkSpaceUserRightsMst.setSeqNo(size+1);
					}
					else{
						objWorkSpaceUserRightsMst.setSeqNo(0);
					}
					
					for (int istageIds = 0; istageIds < stageIdForChange.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIdForChange[istageIds]);
						userRightsList.add(dtoClone);
					}
			}
			//docMgmtImpl.insertFolderSpecificMultipleUserRights(userRightsList);
			docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCodeForESignature(userRightsList);
			
			
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeIdForChange);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			System.out.println("StageId="+stageDesc);
			  /*if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }*/
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
			  		
				objUserMst = docMgmtImpl.getUserInfo(uCodeByForChange);
				System.out.println(objUserMst.getUserGroupCode());
				userGroupCode = new Integer(objUserMst.getUserGroupCode());
				objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
				
				objWSUserRightsMstforModuleHistory.setUserCode(uCodeByForChange);
				objWSUserRightsMstforModuleHistory.setRoleCode(rolCode);
				objWSUserRightsMstforModuleHistory.setMode(1);
				
				//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
				docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	} 
	
public void updateRole(){
		
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();
		 ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		  DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCodeForChange[0]));
		  objUserMst = docMgmtImpl.getUserInfo(uCodeByForChange);
		  System.out.println(objUserMst.getUserGroupCode());
		  userGroupCode = new Integer(objUserMst.getUserGroupCode());
		  WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(WorkspaceId,nodeIdForChange);
			
		objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
		objWSUserRightsMstforModuleHistory.setNodeId(nodeIdForChange);
		objWSUserRightsMstforModuleHistory.setUserCode(uCodeByForChange);
		objWSUserRightsMstforModuleHistory.setUserGroupCode(objUserMst.getUserGroupCode());
		objWSUserRightsMstforModuleHistory.setModifyBy(userId);
		objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
			
			String stageDesc="";
			String stage;
			int stageid = 0;
				for(int j=0;j<stageIdForChange.length;j++){
					stageid=stageIdForChange[j];
					stage= docMgmtImpl.getStageDesc(stageid);
					stageDesc+=stage+",";
				}
			  System.out.println("StageId="+stageDesc);
			  if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }
			  objWSUserRightsMstforModuleHistory.setStageId(stageid);					 
			  objWSUserRightsMstforModuleHistory.setMode(2);
			  userRightsList.add(objWSUserRightsMstforModuleHistory);
			  					
			  //docMgmtImpl.insertFolderSpecificMultipleUserRights(userRightsList);
			  docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);
			  
			  System.out.println("-----------------------Updating Role------------------------");
			  
			  objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
			  objWSUserRightsMstforModuleHistory.setNodeId(nodeIdForChange);
			  objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			  System.out.println("StageId="+stageDesc);
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
			  System.out.println(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserGroupCode(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserCode(uCodeByForChange);
			  objWSUserRightsMstforModuleHistory.setRemark(remarkForChange);
			  objWSUserRightsMstforModuleHistory.setMode(1);
			  //docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
			  docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	}
	
	public String DocNameExists() 
	{	
		boolean docNameExist = docMgmtImpl.folderNameExist(WorkspaceId,docName);
		if (docNameExist== true || docName=="")
		{
			htmlContent = docName+" already exists.";
		}
		else
		{
			htmlContent = "<font color=\"green\">"+docName+" is available. </font>";			
		}
		return SUCCESS;
	}	

	
public String AssignModuleUserDetailHistory(){
		WorkspaceId = docId;
		nodeId = recordId;
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");
		moduleUserDetailHistory = docMgmtImpl.getmoduleUserDetailHistory(WorkspaceId,nodeId);
		return SUCCESS;
		
	}
public String DeletedNodeDetail(){
	
	getDeletedNodeDetail = docMgmtImpl.getDeletedNodeDetail(WorkspaceId);
	
	lbl_folderName = knetProperties.getValue("ForlderName");
	lbl_nodeName = knetProperties.getValue("NodeName");
	
	return SUCCESS;
}

public static byte[] getBytesFromFile(File file) throws IOException {
	InputStream is = new FileInputStream(file);

	// Get the size of the file
	long length = file.length();

	if (length > Integer.MAX_VALUE) {
		// File is too large
	}

	// Create the byte array to hold the data
	byte[] bytes = new byte[(int) length];

	// Read in the bytes
	int offset = 0;
	int numRead = 0;
	while (offset < bytes.length
			&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
		offset += numRead;
	}

	// Ensure all the bytes have been read in
	if (offset < bytes.length) {
		throw new IOException("Could not completely read file "
				+ file.getName());
	}

	// Close the input stream and return bytes
	is.close();
	return bytes;
}

private void addSTFXMLNodeHistory(DTOWorkSpaceNodeDetail wsnd) {

	// Find the STF parent node with NodeTypeIndi = 'T' and then get STF XML
	// Node(where NodeNo = 1).
	int parentId = wsnd.getParentNodeId();
	DTOWorkSpaceNodeDetail stfwsnd;
	while (true) {

		stfwsnd = docMgmtImpl.getNodeDetail(workspaceID, parentId).get(0);

		if (stfwsnd.getNodeTypeIndi() == 'T')
			break;
		else
			parentId = stfwsnd.getParentNodeId();
	}

	Vector<DTOWorkSpaceNodeDetail> allChildren = docMgmtImpl
			.getChildNodeByParent(stfwsnd.getNodeId(), workspaceID);
	DTOWorkSpaceNodeDetail STFXMLNode = allChildren.get(0);// STF XML Node
	// (First child
	// of the STF)

	// Get attr value of operation attr.
	Vector<DTOWorkSpaceNodeAttrDetail> STFXMLAttrDetail = docMgmtImpl
			.getNodeAttrDetail(workspaceID, STFXMLNode.getNodeId());

	// int operationAttrId;
	// String operationAttrValue = "";
	DTOWorkSpaceNodeAttrDetail operationAttrDto = new DTOWorkSpaceNodeAttrDetail();
	;

	for (int i = 0; i < STFXMLAttrDetail.size(); i++) {

		DTOWorkSpaceNodeAttrDetail attrDto = STFXMLAttrDetail.get(i);
		if (attrDto.getAttrName().equalsIgnoreCase("operation")) {
			operationAttrDto = attrDto;
			break;
		}

	}

	if (operationAttrDto.getAttrValue().equalsIgnoreCase("new")) {

		// If STF XML Node is submitted but if its operation is still 'new'
		// then update operation value to 'append'
		if (docMgmtImpl.submittedNodeIdDetail(workspaceID, STFXMLNode
				.getNodeId())) {

			operationAttrDto.setAttrValue("append");
			Vector<DTOWorkSpaceNodeAttrDetail> attrDtl = new Vector<DTOWorkSpaceNodeAttrDetail>();
			attrDtl.addElement(operationAttrDto);
			docMgmtImpl.updateNodeAttrDetail(attrDtl);
		}

	}

	DTOWorkSpaceNodeHistory nodeHistory = new DTOWorkSpaceNodeHistory();
	nodeHistory.setWorkSpaceId(workspaceID);
	nodeHistory.setNodeId(STFXMLNode.getNodeId());
	int tranNo = docMgmtImpl.getNewTranNo(workspaceID);

	nodeHistory.setTranNo(tranNo);
	nodeHistory.setFileName(STFXMLNode.getFolderName());
	nodeHistory.setFileType("xml");
	nodeHistory.setRequiredFlag('Y');
	nodeHistory.setRemark("");
	nodeHistory.setModifyBy(usercode);
	nodeHistory.setStatusIndi('N');
	nodeHistory.setDefaultFileFormat("");
	nodeHistory.setFolderName("");
	nodeHistory.setStageId(100); // Approved stage

	docMgmtImpl.insertNodeHistory(nodeHistory);

	STFXMLAttrDetail = docMgmtImpl.getNodeAttrDetail(workspaceID,
			STFXMLNode.getNodeId());
	Vector<DTOWorkSpaceNodeAttrHistory> STFXMLAttrHistory = new Vector<DTOWorkSpaceNodeAttrHistory>();

	for (int i = 0; i < STFXMLAttrDetail.size(); i++) {

		DTOWorkSpaceNodeAttrDetail stfAttr = STFXMLAttrDetail.get(i);

		// Adding values into vector for workspacenodeattrhistory
		DTOWorkSpaceNodeAttrHistory dtoAttrHistory = new DTOWorkSpaceNodeAttrHistory();
		dtoAttrHistory.setWorkSpaceId(workspaceID);
		dtoAttrHistory.setNodeId(STFXMLNode.getNodeId());
		dtoAttrHistory.setTranNo(tranNo);
		dtoAttrHistory.setAttrId(stfAttr.getAttrId());
		dtoAttrHistory.setAttrValue(stfAttr.getAttrValue());
		dtoAttrHistory.setModifyBy(usercode);
		STFXMLAttrHistory.add(dtoAttrHistory);
		dtoAttrHistory = null;

	}// for end

	// inserting workspacenodeattrhistory
	docMgmtImpl.InsertUpdateNodeAttrHistory(STFXMLAttrHistory);

	// System.out.println("Inserting node version history...");
	DTOWorkSpaceNodeVersionHistory nodeVersionHistory = new DTOWorkSpaceNodeVersionHistory();
	nodeVersionHistory.setWorkspaceId(workspaceID);
	nodeVersionHistory.setNodeId(STFXMLNode.getNodeId());
	nodeVersionHistory.setTranNo(tranNo);
	nodeVersionHistory.setFileVersionId("");
	nodeVersionHistory.setPublished('N');
	nodeVersionHistory.setDownloaded('N');
	nodeVersionHistory.setModifyBy(usercode);
	nodeVersionHistory.setExecutedBy(usercode);
	nodeVersionHistory.setExecutedOn(new Timestamp(new Date().getTime()));
	nodeVersionHistory.setModifyOn(new Timestamp(new Date().getTime()));
	nodeVersionHistory.setUserDefineVersionId("");
	docMgmtImpl.insertWorkspaceNodeVersionHistory(nodeVersionHistory);

}

private void updateNodeAttrHistory(String wsId, int nodeId, int tranNo,
		int userCode, String keyword, String description,
		Vector<DTOWorkSpaceNodeAttrDetail> attrValueIdDtl) {
	Vector<DTOWorkSpaceNodeAttrHistory> saveFileAttr = new Vector<DTOWorkSpaceNodeAttrHistory>();
	Vector<DTOWorkSpaceNodeAttrDetail> fileAttr = docMgmtImpl
			.getNodeAttrDetail(wsId, nodeId);
	for (int i = 0; i < fileAttr.size(); i++) {

		DTOWorkSpaceNodeAttrDetail workSpaceNodeAttrDetail = fileAttr
				.get(i);
		DTOWorkSpaceNodeAttrHistory workSpaceNodeAttrHistoryDTO = new DTOWorkSpaceNodeAttrHistory();
		workSpaceNodeAttrHistoryDTO.setWorkSpaceId(wsId);
		workSpaceNodeAttrHistoryDTO.setNodeId(nodeId);
		workSpaceNodeAttrHistoryDTO.setTranNo(tranNo);
		workSpaceNodeAttrHistoryDTO.setAttrId(workSpaceNodeAttrDetail
				.getAttrId());
		workSpaceNodeAttrHistoryDTO.setAttrValue(workSpaceNodeAttrDetail
				.getAttrValue());
		workSpaceNodeAttrHistoryDTO.setRemark(workSpaceNodeAttrDetail
				.getRemark());
		workSpaceNodeAttrHistoryDTO.setStatusIndi('N');
		workSpaceNodeAttrHistoryDTO.setModifyBy(userCode);
		saveFileAttr.add(workSpaceNodeAttrHistoryDTO);
		workSpaceNodeAttrHistoryDTO = null;
	}

	docMgmtImpl.InsertUpdateNodeAttrHistory(saveFileAttr);

	saveFileAttr = null;
}


private void insertCheckedOutFileDetail(String wsId, int nodeId,
		int tranNo, int userCode, String fileName) {
	DTOCheckedoutFileDetail dtocheck = new DTOCheckedoutFileDetail();

	dtocheck.setWorkSpaceId(wsId);
	dtocheck.setNodeId(nodeId);
	dtocheck.setPrevTranNo(tranNo - 1);
	dtocheck.setModifyBy(userCode);
	dtocheck.setFileName(fileName);

	docMgmtImpl.insertLockedFileDetail(dtocheck);
}

private void updateNodeHistory(String wsId, int nodeId, int tranNo,
		String fileName, int userCode, int stageId, String remark) {

	/*String folderPath = "/" + wsId + "/" + nodeId + "/" + tranNo;*/
	String folderPath = "/"+ year + "/"+ month+"/"+date+"/" + wsId + "/" + nodeId + "/" + tranNo;

	DTOWorkSpaceNodeHistory workSpaceNodeHistoryDTO = new DTOWorkSpaceNodeHistory();
	workSpaceNodeHistoryDTO.setWorkSpaceId(wsId);
	workSpaceNodeHistoryDTO.setNodeId(nodeId);
	workSpaceNodeHistoryDTO.setTranNo(tranNo);
	// System.out.println("FileName:-----"+fileName);
	if (fileName == null) {
		workSpaceNodeHistoryDTO.setFileName("No File");
	} else {
		// System.out.println("File Name Set="+fileName);
		workSpaceNodeHistoryDTO.setFileName(fileName);
	}

	workSpaceNodeHistoryDTO.setFileType("");
	workSpaceNodeHistoryDTO.setFolderName(folderPath);
	workSpaceNodeHistoryDTO.setRequiredFlag('Y');
	// System.out.println("FileName:-----"+fileName);

	// stageId
	/*
	 * int stageId = docMgmtImpl.getMaxStageId(workspaceID, nodeId,
	 * userCode); workSpaceNodeHistoryDTO.setStageId(stageId);
	 */
	// workSpaceNodeHistoryDTO.setStageId(10);
	workSpaceNodeHistoryDTO.setStageId(stageId);
	if (remark == null)
		workSpaceNodeHistoryDTO.setRemark("");
	else
		workSpaceNodeHistoryDTO.setRemark(remark);
	workSpaceNodeHistoryDTO.setModifyBy(userCode);
	workSpaceNodeHistoryDTO.setStatusIndi('N');
	workSpaceNodeHistoryDTO.setDefaultFileFormat("");
	//String roleCode = docMgmtImpl.getRoleCodeFromWSUserRightsMst(wsId, nodeId,userCode);
	String roleCode = docMgmtImpl.getUserByCode(userCode).getRoleCode();
	if(roleCode != null){
		workSpaceNodeHistoryDTO.setRoleCode(roleCode);
	}
	else{
		workSpaceNodeHistoryDTO.setRoleCode("");
	}
	workSpaceNodeHistoryDTO.setFileSize(fileSizeInMB);
	if(RefrenceFileUpload.equalsIgnoreCase("yes")){
		if (RefFileName == null || RefFileName.isEmpty() || RefFileName == "") {
			workSpaceNodeHistoryDTO.setRefFileName("No File");
			workSpaceNodeHistoryDTO.setRefFilePath("");
		} else {
			// System.out.println("File Name Set="+fileName);
			//workSpaceNodeHistoryDTO.setRefFileName(RefFileName);
			workSpaceNodeHistoryDTO.setRefFileName("Y");
			workSpaceNodeHistoryDTO.setRefFilePath(folderPath);
		}
	}

	//docMgmtImpl.insertNodeHistory(workSpaceNodeHistoryDTO);
	if(RefrenceFileUpload.equalsIgnoreCase("yes") && workSpaceNodeHistoryDTO.getRefFileName().equalsIgnoreCase("Y")){
		docMgmtImpl.insertNodeHistoryWithRefFile(workSpaceNodeHistoryDTO);
	}
	else{
		//docMgmtImpl.insertNodeHistory(workSpaceNodeHistoryDTO);
		docMgmtImpl.insertNodeHistoryWithRoleCode(workSpaceNodeHistoryDTO);
	}
	workSpaceNodeHistoryDTO = null;
}


private void insertWorkspaceNodeVersionHistory(String wsId, int nodeId,
		int tranNo, int userCode, String versionSuffix, String versionPrefix) {

	DTOWorkSpaceNodeVersionHistory objWSNodeVersionHistory = new DTOWorkSpaceNodeVersionHistory();

	objWSNodeVersionHistory.setWorkspaceId(wsId);
	objWSNodeVersionHistory.setNodeId(nodeId);
	objWSNodeVersionHistory.setTranNo(tranNo);
	objWSNodeVersionHistory.setPublished('N');
	objWSNodeVersionHistory.setDownloaded('N');
	objWSNodeVersionHistory.setActivityId("");
	objWSNodeVersionHistory.setModifyBy(userCode);
	objWSNodeVersionHistory.setExecutedBy(userCode);

	Timestamp ts = new Timestamp(new Date().getTime());
	objWSNodeVersionHistory.setExecutedOn(ts);

	objWSNodeVersionHistory.setUserDefineVersionId(versionPrefix + "-"
			+ versionSuffix);

	docMgmtImpl.insertWorkspaceNodeVersionHistory(objWSNodeVersionHistory);
}

public boolean uploadFileOnNode(String wsId, int nodeId,
		String baseWorkFolder, byte[] fileData, String fileName,
		int userCode, String keyword, String description,
		String tempBaseFolder,
		Vector<DTOWorkSpaceNodeAttrDetail> attrValueId, int tranNo,
		int fileSize, java.io.InputStream sourceInputStream,
		String versionSuffix, String versionPrefix, int stageId,
		String remark) {
	// System.out.println("Node History ok");
	createFolderStruc(wsId, nodeId, tranNo, baseWorkFolder, fileData,
			fileName, fileSize, sourceInputStream);
	updateNodeHistory(wsId, nodeId, tranNo, fileName, userCode, stageId,
			remark);
	insertWorkspaceNodeVersionHistory(wsId, nodeId, tranNo, userCode,
			versionSuffix, versionPrefix);
	updateNodeAttrHistory(wsId, nodeId, tranNo, userCode, keyword,
			description, attrValueId);
	insertCheckedOutFileDetail(wsId, nodeId, tranNo, userCode, fileName);
	docMgmtImpl.insertAssignedNodeRight(workspaceID,nodeId,tranNo,stageId,usercode,1,"");
	//createTempFolderStruc(wsId, nodeId, tranNo, tempBaseFolder, fileData,fileName, sourceInputStream);

	return true;
}

public boolean uploadRefFileOnNode(String wsId, int nodeId, int tranNo,
		String baseWorkFolder, byte[] fileData, String fileName,
		int fileSize, InputStream stream) {
	

		try {
	
			if (tranNo != -1) {
				// System.out.println("NODE ID="+nodeId);
	
				/*File tranFolder = new File(baseWorkFolder + "/" + wsId + "/"
						+ nodeId + "/" + tranNo);*/
	
				File tranFolder = new File(baseWorkFolder + "/" + year + "/"+ month+"/"+ date+"/" + wsId + "/"
						+ nodeId + "/" + tranNo);
				tranFolder.mkdirs();
	
				File uploadedFile = new File(tranFolder, fileName);
				// System.out.println("uploadfile path::"+uploadedFile);
				OutputStream bos = new FileOutputStream(uploadedFile);
				int temp = 0;
				while ((temp = stream.read(fileData, 0, fileSize)) != -1) {
					bos.write(fileData, 0, temp);
				}
				bos.close();
				stream.close();
				if (autoCorrectPdfProp == 'Y' && uploadedFile.exists()
						&& (uploadedFile.getAbsolutePath().endsWith(".pdf") || uploadedFile.getAbsolutePath().endsWith(".PDF"))
						&& allowAutoCorrectionPdfProp.equalsIgnoreCase("yes")
						&& !nodeOperationValue.equalsIgnoreCase("delete")) {
					// Auto Correcting Pdf Properties
	
					final String FileToCorrect = uploadedFile.getAbsolutePath();
					new Thread(new Runnable() {
						public void run() {
	
							PdfPropUtilities pdfUtil = new PdfPropUtilities();
							pdfUtil.autoCorrectPdfProp(FileToCorrect);
							
						}
					}).start();
					
				}
	
			}
		} catch (Exception ex) {
			ex.printStackTrace();
	}


	return true;
}

public boolean uploadRefFileOnNode(String wsId, int nodeId, int tranNo,int tranNoForAttachmenet,
		String baseWorkFolder, byte[] fileData, String fileName,
		int fileSize, InputStream stream) {
	

		try {
	
			if (tranNo != -1) {
				// System.out.println("NODE ID="+nodeId);
	
				/*File tranFolder = new File(baseWorkFolder + "/" + wsId + "/"
						+ nodeId + "/" + tranNo);*/
	
				File tranFolder = new File(baseWorkFolder + "/" + year + "/"+ month+"/"+ date+"/" + wsId + "/"
						+ nodeId + "/" + tranNoForAttachmenet);
				tranFolder.mkdirs();
	
				File uploadedFile = new File(tranFolder, fileName);
				// System.out.println("uploadfile path::"+uploadedFile);
				OutputStream bos = new FileOutputStream(uploadedFile);
				int temp = 0;
				while ((temp = stream.read(fileData, 0, fileSize)) != -1) {
					bos.write(fileData, 0, temp);
				}
				bos.close();
				stream.close();
				if (autoCorrectPdfProp == 'Y' && uploadedFile.exists()
						&& (uploadedFile.getAbsolutePath().endsWith(".pdf") || uploadedFile.getAbsolutePath().endsWith(".PDF"))
						&& allowAutoCorrectionPdfProp.equalsIgnoreCase("yes")
						&& !nodeOperationValue.equalsIgnoreCase("delete")) {
					// Auto Correcting Pdf Properties
	
					final String FileToCorrect = uploadedFile.getAbsolutePath();
					new Thread(new Runnable() {
						public void run() {
	
							PdfPropUtilities pdfUtil = new PdfPropUtilities();
							pdfUtil.autoCorrectPdfProp(FileToCorrect);
							
						}
					}).start();
					
				}
	
			}
		} catch (Exception ex) {
			ex.printStackTrace();
	}


	return true;
}


private void createFolderStruc(String wsId, int nodeId, int tranNo,
		String baseWorkFolder, byte[] fileData, String fileName,
		int fileSize, InputStream stream) {
	try {

		if (tranNo != -1) {
			// System.out.println("NODE ID="+nodeId);

			/*File tranFolder = new File(baseWorkFolder + "/" + wsId + "/"
					+ nodeId + "/" + tranNo);*/

			File tranFolder = new File(baseWorkFolder + "/" + year + "/"+ month+"/"+ date+"/" + wsId + "/"
					+ nodeId + "/" + tranNo);
			tranFolder.mkdirs();

			File uploadedFile = new File(tranFolder, fileName);
			// System.out.println("uploadfile path::"+uploadedFile);
			OutputStream bos = new FileOutputStream(uploadedFile);
			int temp = 0;
			while ((temp = stream.read(fileData, 0, fileSize)) != -1) {
				bos.write(fileData, 0, temp);
			}
			bos.close();
			stream.close();
			if (autoCorrectPdfProp == 'Y' && uploadedFile.exists()
					&& (uploadedFile.getAbsolutePath().endsWith(".pdf") || uploadedFile.getAbsolutePath().endsWith(".PDF"))
					&& allowAutoCorrectionPdfProp.equalsIgnoreCase("yes")
					&& !nodeOperationValue.equalsIgnoreCase("delete")) {
				// Auto Correcting Pdf Properties

				final String FileToCorrect = uploadedFile.getAbsolutePath();
				new Thread(new Runnable() {
					public void run() {

						PdfPropUtilities pdfUtil = new PdfPropUtilities();
						pdfUtil.autoCorrectPdfProp(FileToCorrect);
						
					}
				}).start();
				
			}

		}
	} catch (Exception ex) {
		ex.printStackTrace();
	}
}

	public String updateMaintainedSeq() {
		htmlContent="";
		DocMgmtImpl doc=new DocMgmtImpl();
		
		//String workspaceId = ActionContext.getContext().getSession().get("ws_id").toString();
		DTOUserMst dto=doc.getUserByCode(Integer.parseInt(userCode[0]));
		if(doc.updateMaintainedSeq(WorkspaceId, nodeId,Integer.parseInt(userCode[0]),dto.getUserGroupCode(),TranNo))
				htmlContent = "<b>Re-arrange Successfully</b>";
		else
			htmlContent = "<b>Problem in Re-arrange</b>";
		
		return SUCCESS;
	
	}


}
