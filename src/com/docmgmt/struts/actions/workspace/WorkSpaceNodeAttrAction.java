package com.docmgmt.struts.actions.workspace;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.docmgmt.dto.DTOAssignNodeRights;
import com.docmgmt.dto.DTOAttrReferenceTableMatrix;
import com.docmgmt.dto.DTOCheckedoutFileDetail;
import com.docmgmt.dto.DTODynamicTable;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.dto.DTOWorkspaceNodeDocHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.master.WorkspaceNodeDetail;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.entities.Node.NodeTypeIndi;
import com.docmgmt.server.webinterface.entities.Node.RequiredFlag;
import com.docmgmt.server.webinterface.entities.Workspace.FontTypes;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.docmgmt.struts.actions.reports.StageWiseMailReport;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WorkSpaceNodeAttrAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();

	public int nodeId;

	public int selectedStage;
	public String displayName;
	// parameters use in jsp pages

	DTOWorkSpaceNodeDetail workSpaceNodeDtl;
	public Vector <DTOWorkSpaceNodeDetail> URSTracebelityMatrixDtl=new Vector();
	public Vector <DTOWorkSpaceNodeDetail> FSTracebelityMatrixDtl=new Vector();
	

	public String TemplateFile;
	public String str ;
	public String result1;
	public String finalString;
	public String subjectId;

	public char canEditFlag;

	public String canApprovedReviewed;

	// public char cloneFlag;

	// public char cloneDisplay;

	public char projectStatus;

	Vector<DTOCheckedoutFileDetail> lockedFileDetail;

	public Vector<DTOWorkSpaceNodeHistory> nodeHistory;
	
	public int nodeHistorysize=0;

	public boolean isNodeLocked;

	Vector<DTOCheckedoutFileDetail> checkedOutBy;

	Vector<DTOWorkSpaceNodeAttrHistory> attrHistory;

	Vector<DTOWorkSpaceUserRightsMst> stageuserdtl;
	Vector users = new Vector();

	public String tempBaseFolderReplaced;

	public String userDefineId;

	public String statusIndi = "N";

	public int isLeafNode;

	public String workspacedesc;
	
	public Vector<DTOWorkSpaceNodeAttribute> attrDtl;

	public boolean isNodeExtendable;

	public boolean iscreatedRights;

	public String appType;
	public String txtmsg;

	public List<String> nodeActivities;

	public ArrayList<DTOWorkspaceNodeDocHistory> nodeDocHistory;
	public String srcDocPath;

	public ArrayList<Object[]> filterAutoCompleterList;

	public ArrayList<Object[]> filterDynamicList;

	public String elecSig;
	public String showEditStructure;
	public DTOWorkSpaceNodeHistory dtoWsNodeHistory;
	public String workspaceID;
	public String alloweTMFCustomization;
	public String usertypecode;
	
	public String userTypeName;
	//public String eTMF_Customization;
	public int stageId;
	public String stageDesc;
	public String currentSeqNo;
	public boolean lockSeqFlag;
	public boolean isReviewRights=false;
	public String htmlContent = "";
	public boolean stampFlag;
	public String userName;
	public String cretifiedFilePath;
	public int usercode;
	public int ndId;
	public boolean isCreate;
	public String isFileHistoy;
	public boolean attrFlag =false;
	public String attrValue="";
	public boolean nextStageFlag;
	public boolean userWiseStageFlag;
	public String autoMail;
	public boolean createandApprovedRights=false;
	public boolean isVoidFlag=true;
	public Vector<DTOWorkSpaceNodeHistory> checkVoidFile=null;
	public Vector<DTOWorkSpaceNodeDetail> checkDeviationFile=null;
	public String WorkspaceNodeDaviation;
	public String remark;
	public int isLeafParent;
	public List<List<String>> getAttributeDetails;
	public List<List<String>> getAttributeDetailsForDisplay;
	public Vector<DTOWorkSpaceNodeDetail> WsNodeDetail;
	public Vector<DTOWorkSpaceNodeAttrDetail> getProjectLevelSearchResult;
	public Vector<DTOWorkSpaceNodeAttrDetail> WsNodeAttrDetail;
	public String ProjectName;
	public String NodeName;
	public Vector<DTOWorkSpaceNodeDetail> getWSDeviationDetail;
	public String WorkspaceNodeDaviationPopUp;
	
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	
	Vector<DTOWorkSpaceUserRightsMst> hoursData=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> getSRFlagData=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> getAdjustDateData=new Vector<>();
	public boolean isTimeLineNodeCount=false;
	public boolean isSendForReview=false;
	public String projectTimeLine;
	public Vector<DTOWorkSpaceNodeAttribute> getAttrDtl=null;
	public boolean isStartDate=false;
	public String wsid;
	
	public Vector<DTOWorkSpaceNodeDetail> originalNodeWithAllclones;
	public DTOWorkSpaceNodeDetail sourceNodeDetail;
	public String nodeNames;
	public Vector<DTOWorkSpaceNodeDetail> wsNodeDtlForParent;
	public Vector<DTOWorkSpaceNodeDetail> wsNodeDtlToShow;
	public List<List<String>> AttrDetilas;
	public HashMap<Integer,List<List<String>>> mapForSection=new HashMap<Integer,List<List<String>>>();
	public TreeMap<Integer,List<List<String>>> sortedMap = new TreeMap<Integer,List<List<String>>>();
	
	public int nodeIdForFirstChild;
	public int nodeIdForFlags;
	public boolean flag=true;
	public boolean signatureFlag=false;
	public String eSign;
	public String signPath;
	public String baseWorkFolder="";
	public String fontStyle="";
	public String usrName;
	public boolean IsUpload=false;
	public boolean IsDownload=false;
	public boolean IsComment=false;
	public boolean confirmAndUpload=false;
	public boolean confirmAndUploadOfHistory=false;
	
	public String path;
	public String OfficeWsId;
	public int OfficeNodeId;
	public int OfficeUserCode;
	public int userCodeForOffice;
	File uploadDoc;
	String uploadDocFileName;
	String uploadDocContentType;
	public Vector<DTOWorkSpaceNodeHistory> wSOfficeHistory;
	public Vector<DTOWorkSpaceNodeHistory> wSOfficeHistoryForFlagCheck;
	public Vector<DTOWorkSpaceNodeHistory> wsNodeHistory;
	public Vector assignWorkspaceRightsDetails;
	public String UplodFileMail;
	public String reviewDoc="false";
	String srcFileExt ;
	public String newFileName;
	public String fileNameToShow;
	public String lbl_folderName;
	public String lbl_nodeName;
	public String DocURLForOffice365;
	int year;
	int month;
	int officeDate;
	public boolean scriptFlag=false;
	
	
	public File f;
	public double fileSizeInMB;
	public int fileUploloadingSize;
	public String fileSizeProperty;
	public boolean nodeType=true;
	public String OpenFileAndSignOff;
	public boolean IsValidate=false;
	public String clientCode;
	public String clientCodeToCheck;
	
	public String Path;
	public String PQPreApprovalPopup;
	public boolean IsConfirmBtn=true;
	public boolean showValidateBtn=false;
	public Vector<DTOWorkSpaceNodeDetail> showPQSTableHeaderMst;
	ArrayList<DTOWorkSpaceNodeDetail> getClientDetail = new  ArrayList<DTOWorkSpaceNodeDetail>();
	public ArrayList<DTOWorkSpaceNodeDetail> getTracebilityDetail = new  ArrayList<DTOWorkSpaceNodeDetail>();
	public ArrayList<DTOWorkSpaceNodeDetail> getTracebilityMatrixHeader = new  ArrayList<DTOWorkSpaceNodeDetail>();
	public Vector<DTOWorkSpaceNodeDetail> getTracebilityMatrixDetail = new Vector<>();
	public Vector<DTOWorkSpaceNodeDetail> getTracebilityMatrixDetailTemp = new Vector<>();
	public Vector<DTOWorkSpaceNodeDetail> getTracebilityMatrixDetailToDisplay = new Vector<>();
	public HashMap<String,String> map=new HashMap<String,String>();//Creating HashMap
	public TreeMap<String, String> sorted = new TreeMap<>();
	public String Confirmflag;
	public int totalScript=0;
	public int successScript=0;
	public int successSection=0;
	public String confidence;
	public boolean scriptApproval=false;
	public boolean isRequiredValidate=false;
	public String ScriptExtraction;
	public String PreApprovalURL;
	public String PreApprovalDocPath;
	public String wsDesc;
	
	public String singleDocFlag;
	public Vector<DTOWorkSpaceNodeHistory> getPreviewDetail;
	public String userNameForPreview;
	public String signId;
	public String signImg;
	public String signStyle;
	public String roleName;
	ArrayList<String> time = new ArrayList<String>();
	public String dateForPreview;
	public String preApproValError;
	public String ManualSignatureConfig;
	public String ApplicationUrl;
	public float xCordinates;
	public float yCordinates;
	public int pageNo;
	
	public String baseSrcPath;
	public ArrayList<DTOWorkSpaceNodeHistory> previousHistory=new ArrayList<>();
	public String uName;
	public String role;
	public String signdate;
	public Vector <DTOWorkSpaceNodeHistory> dtoWsHistory;
	public String imgFilename="";
	FontTypes ft =  new FontTypes();
	public String actionDesc;
	public boolean isFailedScriptBtn=false;
	public String uuId;
	public boolean signatureReqCrtifiedBtn=false;
	public boolean isCertified=true;
	public String Automated_TM_Required;
	public String Automated_Doc_Type;
	public boolean showAutomateButton=false;
	public int incompleteData;	
	public boolean traceblityButton=false;
	public boolean traceblityAlert=true;
	public String[] ScriptCodeVal; 
	public String isScriptCodeAutoGenrate;
	public String ursFsFileName;
	public boolean showSendForReviewBtn=false;
	public String globalStatus="";
	
	public String nextstageuserdtl;
	public String baseWorkFolderForPublishing;
	public int tranNoForPublishing;
	
	@Override
	public String execute() throws SQLException {

		if (nodeId > 0) {
			System.out.println("Workspace ID IS :-"+wsid);
			if(wsid != null){
			workspaceID = wsid;
			ActionContext.getContext().getSession().put("ws_id",workspaceID);
			}
			workspaceID = ActionContext.getContext().getSession().get("ws_id").toString();
			int usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			usertypecode=ActionContext.getContext().getSession().get("usertypecode").toString();
			userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
			usrName = ActionContext.getContext().getSession().get("username").toString();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			
			try{
			Automated_TM_Required = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, 1, "Automated TM Required").getAttrValue();
			System.out.println("Automated_TM_Required---"+Automated_TM_Required);
			
			//Automated_Doc_Type=docMgmtImpl.getAttrDtlForPageSetting(workspaceID, nodeId);
			Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();
			System.out.println("Automated_Doc_Type---"+Automated_Doc_Type);
			
			int tranNo = docMgmtImpl.getMaxTranNoByStageId(workspaceID,nodeId);
			if(Automated_Doc_Type!=null){
				if(docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId, tranNo).size()>0){
					if(docMgmtImpl.getTracebelityMatrixDtlForDocTypeHistory(workspaceID,nodeId,tranNo,Automated_Doc_Type).size()<=0){
						showAutomateButton=true;
					}
				}
			}	
			if(Automated_TM_Required!=null && Automated_TM_Required.equalsIgnoreCase("Yes")){
				String tracebilityVal = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "Tracebility Matrix").getAttrValue();
				if(tracebilityVal!=null && tracebilityVal.equalsIgnoreCase("Yes")){ 
					/*if(docMgmtImpl.getURSTracebelityMatrixDtl(workspaceID,nodeId,tranNo).size()>=0 &&
							docMgmtImpl.getFSTracebelityMatrixDtl(workspaceID,nodeId,tranNo).size()>=0){*/
					traceblityButton=true;
					if(docMgmtImpl.getFSTracebelityMatrixDtlToCheck(workspaceID,"URS").size()>0 &&
					docMgmtImpl.getFSTracebelityMatrixDtlToCheck(workspaceID,"FS").size()>0){
						traceblityAlert=false;
					}
				}
			}
				
			}
			catch(Exception e){
				Automated_TM_Required="";
				Automated_Doc_Type="";
			}
			/*else
				showValidateButton=true;*/
			DTOUserMst userMstFrom = new DTOUserMst();
			userMstFrom.setUserCode(usercode);
			userMstFrom.setUserGroupCode(usergrpcode);
						
			/*users = docMgmtImpl
					.getWorkspaceUserDetail(workspaceID, userMstFrom);*/
			int countForArchival = docMgmtImpl.getCountForArchivleSequence(workspaceID);
			if(countForArchival>0)
				lockSeqFlag=true;
			
			userTypeName=docMgmtImpl.getUserTypeName(usercode);
			
			users = docMgmtImpl.getWorkspaceuserdetailByNodeId(workspaceID,nodeId,userMstFrom);
			
			String fileext = docMgmtImpl.getFileName(workspaceID,nodeId);
			if((fileext.endsWith("pdf")||fileext.endsWith("PDF") || fileext.endsWith("doc")||fileext.endsWith("docx"))){							
				showSendForReviewBtn=true;
			}
		
			Vector<DTOWorkSpaceNodeDetail> wsNodeDtl = docMgmtImpl.getNodeDetail(workspaceID, nodeId);
			if(wsNodeDtl.get(0).getNodeTypeIndi()=='K'){
				nodeType=false;
			}
			elecSig = knetProperties.getValue("ElectronicSignature");
			lbl_folderName = knetProperties.getValue("ForlderName");
			lbl_nodeName = knetProperties.getValue("NodeName");	
			WorkspaceNodeDaviation = knetProperties.getValue("WorkspaceNodeDaviation");
			WorkspaceNodeDaviationPopUp = knetProperties.getValue("WorkspaceNodeDaviationPopUp");
			DocURLForOffice365 = knetProperties.getValue("DocURL");
			OpenFileAndSignOff = knetProperties.getValue("OpenFileAndSignOff");
			PQPreApprovalPopup = knetProperties.getValue("PQPreApprovalPopup");
			// by default electronic signature will not be asked from user, if
			// not specified in property file
			alloweTMFCustomization = knetProperties.getValue("ETMFCustomization");
			String singaturePath = knetProperties.getValue("signatureFile");
			isScriptCodeAutoGenrate =knetProperties.getValue("isScriptCodeAutogenerate");
			baseWorkFolderForPublishing = knetProperties.getValue("BaseWorkFolder");
			
			if (elecSig == null)
				elecSig = "";
			// System.out.println("digiSig"+digiSig);
			
			userNameForPreview = usrName;
			String roleCode = docMgmtImpl.getRoleCode(workspaceID, usercode);
			roleName= docMgmtImpl.getRoleName(roleCode);
			Vector <DTOWorkSpaceNodeHistory> getSignDetail = docMgmtImpl.getUserSignatureDetail(usercode);
			if(getSignDetail.size()>0){
			//signId = getSignDetail.get(0).getUuId();
			signId = getSignDetail.get(0).getFolderName().split("/")[2];
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
			
			if (wsNodeDtl.size() > 0) {

				workSpaceNodeDtl = wsNodeDtl.get(0);
				
				WorkspaceNodeDetail wsndtl=new WorkspaceNodeDetail();
				 Vector<DTOWorkSpaceNodeDetail> getChildsForNextChilds=new Vector<DTOWorkSpaceNodeDetail>();
				 getChildsForNextChilds=wsndtl.getChildNodesSections(workspaceID, nodeId);
				 
				 //for(int i=0;i<getChildsForNextChilds.size();i++){
				 if(getChildsForNextChilds.size()>0){
					 if(getChildsForNextChilds.get(0).getRequiredFlag()=='A'){
						 nodeIdForFlags=1;
						 }
				 }
				 //}
				 if(getChildsForNextChilds.size()>0)
					 nodeIdForFirstChild=getChildsForNextChilds.get(0).getNodeId();
				
				//String s=
				finalString = workSpaceNodeDtl.getNodeDisplayName();
				if(finalString.contains("{")){
				int startingIndex = finalString.indexOf("{");
				int closingIndex = finalString.indexOf("}");
				result1 = finalString.substring(startingIndex , closingIndex+1);
				
				//System.out.println(result1);
				finalString=finalString.replace(result1, "  ");
				//System.out.println(finalString);
				}
				else{
					finalString=workSpaceNodeDtl.getNodeDisplayName();
				}
				attrDtl = docMgmtImpl.getAttributeDetailForDisplay(workspaceID,
						nodeId);
			  for(int i=0;i<attrDtl.size();i++){
				
				  if(attrDtl.get(i).getAttrName().equalsIgnoreCase("Script Code") && isScriptCodeAutoGenrate.equalsIgnoreCase("Yes"))  
					  scriptFlag=true;
				  
				  attrValue = attrDtl.get(i).getAttrValue();
				  if(!attrValue.isEmpty() || !attrValue.equalsIgnoreCase("")){
					  attrFlag =true;
			  	}
				  if(attrDtl.get(i).getAttrName().equalsIgnoreCase("Type of Signature")
						  && attrDtl.get(i).getAttrValue().equalsIgnoreCase("Physical")){
					  signatureReqCrtifiedBtn =true;
				  }
			  }
			  
			  int tranNo = docMgmtImpl.getMaxTranNoByStageId(workspaceID,nodeId);
			  ArrayList<DTOWorkSpaceNodeHistory> checkCertified = docMgmtImpl
						.showFullNodeHistoryByStageId(workspaceID, nodeId,tranNo);
			  
			  if(checkCertified.size()>0){
				  String crtified = checkCertified.get(0).getDefaultFileFormat();
				  if(crtified.equalsIgnoreCase("Certified") && signatureReqCrtifiedBtn == true){
					  isCertified = false;
				  }
			  }
			  
			 //System.out.println(attrFlag);
				filterAutoCompleterList = filterAttributes(attrDtl,
						"AutoCompleter");

				filterDynamicList = filterAttributes(attrDtl, "Dynamic");

				stageuserdtl = docMgmtImpl.getStageUserDetail(workspaceID,
						nodeId, usercode, usergrpcode);
				selectedStage = docMgmtImpl.getMaxStageId(workspaceID, nodeId,
						usercode);
				DTOWorkSpaceNodeDetail wsnodeAttr = new DTOWorkSpaceNodeDetail();

				PropertyInfo propInfo = PropertyInfo.getPropInfo();
				showEditStructure = propInfo.getValue("ShowEditStructure");

				alloweTMFCustomization = propInfo.getValue("ETMFCustomization");
				alloweTMFCustomization= alloweTMFCustomization.toLowerCase();
				
				wsnodeAttr.setNodeId(nodeId);
				wsnodeAttr.setWorkspaceId(workspaceID);
				wsnodeAttr.setUserGroupCode(usergrpcode);
				wsnodeAttr.setUserCode(usercode);

				DTOWorkSpaceUserRightsMst obj = new DTOWorkSpaceUserRightsMst();
				obj.setNodeId(new Integer(nodeId).intValue());
				obj.setWorkSpaceId(workspaceID);
				obj.setUserCode(usercode);
				obj.setUserGroupCode(usergrpcode);

				Vector<DTOWorkSpaceUserRightsMst> workspaceuserrightsmst = docMgmtImpl
						.getWorkspaceNodeRightsDetail(obj);

				//DTOWorkSpaceMst dtowmst = docMgmtImpl.getWorkSpaceDetail(workspaceID);
				DTOWorkSpaceMst dtowmst = docMgmtImpl.getWorkSpaceDetailWSList(workspaceID);
			
				if(PQPreApprovalPopup.equalsIgnoreCase("Yes")){
				clientCode = knetProperties.getValue("ClientCode");
				ScriptExtraction = knetProperties.getValue("ScriptExtraction");
				
				String check[] = ScriptExtraction.split(",");
				for(int k=0;k<check.length;k++){
					if(workSpaceNodeDtl.getNodeName().equalsIgnoreCase(check[k])){
						isRequiredValidate =true;
					}
				}
				
				int trnNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
				ArrayList<DTOWorkSpaceNodeHistory> geNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
				geNodeHistory = docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId, trnNo);
				if(geNodeHistory.size()>0){
				String fldrName= geNodeHistory.get(0).getFolderName();
				showValidateBtn = true;	    
				Vector<DTOWorkSpaceNodeDetail> PQSTableHeaderMst = new Vector<DTOWorkSpaceNodeDetail>();
			    PQSTableHeaderMst = docMgmtImpl.getPQSTableHeaderMst(workspaceID,nodeId,clientCode,fldrName);
			    if(PQSTableHeaderMst.size()>0){
			    	IsConfirmBtn=false;
			    }
				}
				}
				
				if(dtowmst.getStatusIndi()=='L'){
					lockSeqFlag=true;
				}

				DTOWorkSpaceUserRightsMst wsuserrightsmst = new DTOWorkSpaceUserRightsMst();
				if (workspaceuserrightsmst.size() != 0) {
					wsuserrightsmst = workspaceuserrightsmst
							.get(0);
				}

				canEditFlag = wsuserrightsmst.getCanEditFlag();

				canApprovedReviewed = wsuserrightsmst.getAdvancedRights();

				projectStatus = new Character(dtowmst.getStatusIndi());
				workspacedesc = dtowmst.getWorkSpaceDesc();

				isLeafNode = docMgmtImpl.isLeafNodes(workspaceID, nodeId);
						
				//isLeafParent = docMgmtImpl.isLeafParent(workspaceID, nodeId);
				if(wsNodeDtl.get(0).getRequiredFlag()=='A')
					isLeafParent = 0;
				else
					isLeafParent = 1;

				iscreatedRights = docMgmtImpl.iscreatedRights(workspaceID,nodeId, usercode, usergrpcode);

				if (isLeafNode == 1) {
					Vector<DTOWorkSpaceNodeDetail> getDtl = docMgmtImpl.getNodeDetail(workspaceID, nodeId);

					if (getDtl.size() > 0) {
						DTOWorkSpaceNodeDetail dto = getDtl
								.get(0);
						TemplateFile = dto.getDocTemplatePath();

						if (TemplateFile == null) {
							TemplateFile = "Not Found";
						}

					}

					lockedFileDetail = docMgmtImpl.getLockedFileDetail(
							workspaceID, nodeId, usercode);
					nodeHistory = docMgmtImpl.getNodeHistory(workspaceID,nodeId);
					if(nodeHistory.size()>0)
						tranNoForPublishing = nodeHistory.get(nodeHistory.size()-1).getTranNo();
					//int tranNo = docMgmtImpl.getMaxTranNoByStageId(workspaceID,nodeId);
					
					ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
							.showFullNodeHistoryByStageId(workspaceID, nodeId,tranNo);
					
					nodeHistorysize = nodeHistory.size();
					for (int i = 0; i < nodeHistory.size(); i++) {
						DTOWorkSpaceNodeHistory dto = nodeHistory
								.get(i);
						dto.setHistoryDocumentSize(FileManager
								.getFileSize(dto.getBaseWorkFolder()
										+ dto.getFolderName() + "/"
										+ dto.getFileName()));
						
						if(tempHistory.size()>0){
							String DefaultFileFormat = tempHistory.get(0).getDefaultFileFormat();
							if(DefaultFileFormat.equalsIgnoreCase("Certified")){
								dto.setDefaultFileFormat("Certified");
							}else{
								dto.setDefaultFileFormat("-");
							}
						}else {
							dto.setDefaultFileFormat("-");
						}
					}
					if(nodeHistory.size()>0){
					int tmp = nodeHistory.size()-1;
					PreApprovalDocPath = nodeHistory.get(tmp).getBaseWorkFolder()+"/"+nodeHistory.get(tmp).getFolderName()+
							"/"+nodeHistory.get(tmp).getFileName();
					}
					isNodeLocked = docMgmtImpl.isCheckOut(workspaceID, nodeId,
							usercode);
					checkedOutBy = docMgmtImpl.nodeCheckedOutBy(workspaceID,
							nodeId);

					/*attrHistory = docMgmtImpl
							.getUserDefinedWorkspaceNodeAttrHistory(
									workspaceID, nodeId);*/
					attrHistory = docMgmtImpl
							.getUserDefinedNodeAttrHistory(
									workspaceID, nodeId);
					//Show isFailedScriptBtn
					String nodeName = wsNodeDtl.get(0).getNodeName();
					/*String attrName,attrVal = "";
					if(attrHistory.size()>0){
					for(int j=0;j<attrHistory.size();j++){
						attrName = attrHistory.get(j).getAttrName();
						if(attrName.equalsIgnoreCase("Pass/Fail")){
							attrVal = attrHistory.get(j).getAttrValue();
						}
					}
					}*/
					int nodeNo  = wsNodeDtl.get(0).getNodeNo();
					if (nodeNo != 0){
						DTOWorkSpaceNodeDetail getFailedScriptDetail =new DTOWorkSpaceNodeDetail();
						
						getFailedScriptDetail = docMgmtImpl.getFailedScriptNodeDetail(workspaceID,wsNodeDtl.get(0).getParentNodeId(),nodeNo+1);
									
						//if(nodeName.equalsIgnoreCase("TC") && attrVal.equalsIgnoreCase("Fail") && !getFailedScriptDetail.getNodeName().equalsIgnoreCase("FailedScript")){
						/*if(nodeName.equalsIgnoreCase("TC") && !getFailedScriptDetail.getNodeName().equalsIgnoreCase("FailedScript")){
							isFailedScriptBtn = true;
						}
						else if(nodeName.equalsIgnoreCase("TC") && getFailedScriptDetail.getNodeName().equalsIgnoreCase("FailedScript") && getFailedScriptDetail.getStatusIndi()=='D'){
							isFailedScriptBtn = true;
						}
						else{
							isFailedScriptBtn = false;
						}*/
						if(nodeName.equalsIgnoreCase("TC")){
							isFailedScriptBtn = true;
						}
						else{
							isFailedScriptBtn = false;
						}
					}
					ArrayList<DTOWorkSpaceNodeHistory> publishedNodeHistory = docMgmtImpl
							.getNodeHistoryStageWiseUserWise(workspaceID,
									nodeId, 100, 0, true);
					/*
					 * FUNCTION DESCRIPTION
					 * getNodeHistoryStageWiseUserWise(workspaceID, nodeId,
					 * StageId, ModifyUserId, maxTranData or alldata);
					 */
					if (publishedNodeHistory.size() > 0) {
						for (int itrPubNodeHis = 0; itrPubNodeHis < publishedNodeHistory
								.size(); itrPubNodeHis++) {
							dtoWsNodeHistory = publishedNodeHistory
									.get(itrPubNodeHis);
							tranNoForPublishing = dtoWsNodeHistory.getTranNo(); 
						}
						dtoWsNodeHistory
								.setHistoryDocumentSize(FileManager
										.getFileSize(dtoWsNodeHistory
												.getBaseWorkFolder()
												+ dtoWsNodeHistory
														.getFolderName()
												+ "/"
												+ dtoWsNodeHistory
														.getFileName()));
					}

				}
				// Getting User defined version id.
				DTOWorkSpaceNodeVersionHistory dtoversionhistory = new DTOWorkSpaceNodeVersionHistory();

				dtoversionhistory.setWorkspaceId(workspaceID);
				dtoversionhistory.setNodeId(nodeId);

				Vector<DTOWorkSpaceNodeVersionHistory> getMaxUserDefineId = docMgmtImpl
						.getMaxWsNodeVersionDetail(dtoversionhistory);

				userDefineId = "";

				statusIndi = "N";

				if (getMaxUserDefineId.size() > 0) {
					DTOWorkSpaceNodeVersionHistory dtowvh = getMaxUserDefineId
							.elementAt(0);
					userDefineId = dtowvh.getUserDefineVersionId();
					statusIndi = new Character(dtowvh.getStatusIndi())
							.toString();
					if (userDefineId == null)
						userDefineId = "";

				}

				isNodeExtendable = docMgmtImpl.isNodeExtendable(workspaceID,
						nodeId);
				PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
				tempBaseFolderReplaced = propertyInfo
						.getValue("BASE_TEMP_FOLDER");
				projectTimeLine = propertyInfo
						.getValue("ProjectTimeLine");

				// get application type
				
				/*if(dtowmst.getProjectCode().equalsIgnoreCase("0003")){
					appType = "Archive";
				}else{
					appType = ProjectType.getApplicationType(dtowmst.getProjectType());
				}*/
				appType=dtowmst.getProjectCode();
				getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
				if(getSRFlagData.size()>0){
					isSendForReview=true;
				}
				int wsnddtltoatlLeafCount = docMgmtImpl.getTotalLeafNodeCount(workspaceID);
				int tileLinetotalLeafcount = docMgmtImpl.gettimeLineTotalLeafCount(workspaceID);
				if(wsnddtltoatlLeafCount==tileLinetotalLeafcount){
					isTimeLineNodeCount =true;
					System.out.println("Match found...");
					
				}
				checkVoidFile = docMgmtImpl.getVoidFileHistory(workspaceID,nodeId);
				if(checkVoidFile.size()>0){
					isVoidFlag=false;
				}
				getAttrDtl = docMgmtImpl.getTimelineAttrDtlForTree(workspaceID);
				
				if(getAttrDtl.size()>0){
					isStartDate=true;
				}
				Vector <DTOWorkSpaceNodeHistory> dtoHistory;
				dtoHistory =docMgmtImpl.getUserSignatureDetail(usercode);
				baseWorkFolder = propertyInfo.getValue("signatureFile");
				if(dtoHistory.size()>0){
					signatureFlag = true;
					signPath = dtoHistory.get(0).getFileName();
					fontStyle = dtoHistory.get(0).getFileType();
				}
				
				nextStageFlag = docMgmtImpl.getNextStageFlag(workspaceID,nodeId,usercode);
				userWiseStageFlag = docMgmtImpl.getNextStageFlagByUser(workspaceID,nodeId,usercode);
				ArrayList<Integer> assignedStageIds = docMgmtImpl.getStageIdsRightsWise(workspaceID,nodeId);
				if(alloweTMFCustomization.equalsIgnoreCase("yes") && (appType.equalsIgnoreCase("0002") || appType.equalsIgnoreCase("0003")))
				{

					int maxTransNo = docMgmtImpl.getMaxTranNo(workspaceID,nodeId);					
					stageId = docMgmtImpl.getStageDescription(workspaceID,nodeId,maxTransNo);
					if(maxTransNo !=0){
						String stageDescNCurrentSeq = docMgmtImpl.getStageDescNCurrentSeq(workspaceID, nodeId, maxTransNo);					
						if(stageDescNCurrentSeq.endsWith("&")){
							stageDesc = stageDescNCurrentSeq.split("&")[0];
							currentSeqNo = "";
						}
						else{
							stageDesc = stageDescNCurrentSeq.split("&")[0];
							currentSeqNo = stageDescNCurrentSeq.split("&")[1];	
						}										
						 System.out.println("*****stageDesc******"+stageDesc+"&&"+currentSeqNo);
					}
					if(userTypeName.equals("WA")){
						stageuserdtl.clear();
						stageuserdtl.addAll(docMgmtImpl.getAllStageIdsForAdmin(workspaceID,nodeId, usercode, usergrpcode));						
					}	
					if(!userTypeName.equals("WA") && stageId>0)
					 {
						Vector<DTOAssignNodeRights> getAssignNodeRightsData = docMgmtImpl.getAssignNodeRightsData(workspaceID,nodeId,stageId);											
						Vector<DTOAssignNodeRights> getAllDataForNodeId = docMgmtImpl.getAllDataForNodeId(workspaceID,nodeId);																	
						Vector<DTOAssignNodeRights> getAllDataForNodeIdByUserId = docMgmtImpl.getAllDataForNodeIdByUserId(workspaceID,nodeId,usercode);																	
						
						String sendForReviewFlag = "";
						Vector<DTOWorkSpaceUserRightsMst> attachedUsersForCurrentStage = docMgmtImpl.
									getTotalUsersByStage(workspaceID,nodeId,stageId);							
						Vector<DTOWorkSpaceUserRightsMst> getAllUsersWithMultiRights = docMgmtImpl.
								getAllRightsUserWise(workspaceID,nodeId,usercode,usergrpcode);	
						Vector<DTOWorkSpaceUserRightsMst> getNextStageRightsWise = docMgmtImpl.
								getNextStageRightsWise(workspaceID,nodeId,usercode,usergrpcode,stageId,1);	
						ArrayList<Integer> listUsersCode = new ArrayList<>();
						for(int k=0;k<attachedUsersForCurrentStage.size();k++)
							{
								listUsersCode.add(attachedUsersForCurrentStage.get(k).getUserCode());
							}
						ArrayList<Integer> usersCode = new ArrayList<>();
						for(int k=0;k<getAssignNodeRightsData.size();k++)
							{
								usersCode.add(getAssignNodeRightsData.get(k).getModifyBy());
							}
						ArrayList<Integer> stageIdsForCurrentUser = new ArrayList<>();
						for(int k=0;k<getAllUsersWithMultiRights.size();k++){
							stageIdsForCurrentUser.add(getAllUsersWithMultiRights.get(k).getStageId());
						}
						System.out.println("stageIdsForCurrentUser :: "+stageIdsForCurrentUser);
						if(stageIdsForCurrentUser.contains(10) && stageIdsForCurrentUser.contains(100)){
							createandApprovedRights=true;
						}
						//New 
						if(stageId == 10 && listUsersCode.contains(usercode))
						{
							stageuserdtl.clear();
						}
						int nextStageId = docMgmtImpl.getNextStageInfo(stageId).getStageId();
						boolean flag = false;
						if(getAllDataForNodeId.size()>1)
						{
							System.out.println("TURE");	
							flag=true;
						}
						if(stageId == 10){
							System.out.println("CREATED");
							if(getAssignNodeRightsData.size() == 1)
							{
								sendForReviewFlag = getAssignNodeRightsData.get(0).getFlag();
							}
							System.out.println("getAssignNodeRightsData flag "+ sendForReviewFlag);
							DTOAssignNodeRights dtoAssignNodeRights = docMgmtImpl.checkDataExistsForUser(workspaceID,nodeId,stageId,usercode);							
							if(dtoAssignNodeRights == null){
							if(stageIdsForCurrentUser.contains(stageId))
							{
								System.out.println("If case");
//								int tmpStageId = stageIdsForCurrentUser.get((stageIdsForCurrentUser.indexOf(stageId)+1));
//								if(nextStageId == tmpStageId)
//								{
//									stageuserdtl.addAll(docMgmtImpl.getNextStageIds(workspaceID,nodeId, usercode, usergrpcode));
//								}
								stageuserdtl.clear();
								if(getAllUsersWithMultiRights.size()>0)
								{
									//stageuserdtl.addAll(docMgmtImpl.getNewStageId(workspaceID,nodeId, usercode, usergrpcode));
									//stageuserdtl.addAll(docMgmtImpl.getNextStageIds(workspaceID,nodeId, usercode, usergrpcode));
								}
							}						
							else
							{
								System.out.println("Else case");
								if(stageIdsForCurrentUser.indexOf(nextStageId)>-1)
								{
									int tmpStageId =  stageIdsForCurrentUser.get(stageIdsForCurrentUser.indexOf(nextStageId));
									if(nextStageId == tmpStageId)
									{
										stageuserdtl.clear();
										stageuserdtl.addAll(docMgmtImpl.getNewStageId(workspaceID,nodeId, usercode, usergrpcode));
										stageuserdtl.addAll(docMgmtImpl.getNextStageIds(workspaceID,nodeId, usercode, usergrpcode));
									}
								}
								else
								{
									stageuserdtl.clear();
								}
							}	
						 }
						//specific for when user has create as well as nxt stage rights	
						else if(getAllUsersWithMultiRights.size() != getAllDataForNodeIdByUserId.size() && 
								getAllUsersWithMultiRights.size() > getAllDataForNodeIdByUserId.size() && 
								stageIdsForCurrentUser.size()>1 && dtoAssignNodeRights!=null)
						{
							System.out.println("complex else if");
							stageuserdtl.clear();
							stageuserdtl.addAll(docMgmtImpl.getNewStageId(workspaceID,nodeId, usercode, usergrpcode));
							stageuserdtl.addAll(docMgmtImpl.getNextStageIds(workspaceID,nodeId, usercode, usergrpcode));
						}
						}
						if(sendForReviewFlag.equalsIgnoreCase("SR") || flag)
						{
						if(stageId == 20){
							System.out.println("REVIEW");
							stageuserdtl.clear();
							DTOAssignNodeRights dtoAssignNodeRights = docMgmtImpl.checkDataExistsForUser(workspaceID,nodeId,stageId,usercode);
							int stageIndex = stageIdsForCurrentUser.indexOf(stageId);
							int tmpStageId = 0;
							if(stageIdsForCurrentUser.indexOf(nextStageId)>-1)
								tmpStageId =  stageIdsForCurrentUser.get(stageIdsForCurrentUser.indexOf(nextStageId));
							
							if(dtoAssignNodeRights == null){
							if(stageId == stageIdsForCurrentUser.get(stageIdsForCurrentUser.size()-1))
							{
								System.out.println("RR IF");								
								stageuserdtl.clear();
								stageuserdtl.addAll(docMgmtImpl.getNewStageId(workspaceID,nodeId, usercode, usergrpcode));
								stageuserdtl.addAll(docMgmtImpl.getNextStageRightsWise(workspaceID,nodeId, usercode, usergrpcode,stageId,2));
							}
							else if(stageId == stageIdsForCurrentUser.get(0))
							{
								System.out.println("RR ELSE IF");
								stageuserdtl.addAll(docMgmtImpl.getNewStageId(workspaceID,nodeId, usercode, usergrpcode));
								stageuserdtl.addAll(docMgmtImpl.getStageIds(workspaceID,nodeId, usercode, usergrpcode,stageId));
							}
							else if(getAssignNodeRightsData.size() < attachedUsersForCurrentStage.size()){
								System.out.println("RR ELSE IF 2");
								stageuserdtl.clear();
							}
							else
							{
								System.out.println("RR ELSE");
								if(stageIdsForCurrentUser.indexOf(nextStageId)<0)
								{
									stageuserdtl.clear();
								}
								else
								{
									stageuserdtl.addAll(docMgmtImpl.getNewStageId(workspaceID,nodeId, usercode, usergrpcode));
									stageuserdtl.addAll(docMgmtImpl.getStageIds(workspaceID,nodeId, usercode, usergrpcode,stageId));
								}
								
							}
							}
							
							/*if(dtoAssignNodeRights!=null && (nextStageId == tmpStageId))							
							{
								System.out.println("review main 2 if");
								stageuserdtl.clear();
								stageuserdtl.addAll(docMgmtImpl.getNewStageId(workspaceID,nodeId, usercode, usergrpcode));
								stageuserdtl.addAll(docMgmtImpl.getNextStageIds(workspaceID,nodeId, usercode, usergrpcode));
							
							}*/
							if(getAllUsersWithMultiRights.size() != getAllDataForNodeIdByUserId.size() && 
									getAllUsersWithMultiRights.size() > getAllDataForNodeIdByUserId.size() && 
									stageIdsForCurrentUser.size()>1 && dtoAssignNodeRights!=null &&
									getAssignNodeRightsData.size()<attachedUsersForCurrentStage.size())
							{
								System.out.println("review complex if");
								stageuserdtl.clear();
								//stageuserdtl.addAll(docMgmtImpl.getNewStageId(workspaceID,nodeId, usercode, usergrpcode));
								//stageuserdtl.addAll(docMgmtImpl.getStageIds(workspaceID,nodeId, usercode, usergrpcode,stageId));
							}
							if(getAssignNodeRightsData.size() == attachedUsersForCurrentStage.size())
							{
								
							}
							if(getAssignNodeRightsData.size() == attachedUsersForCurrentStage.size()
									&& getAllUsersWithMultiRights.size() != getAllDataForNodeIdByUserId.size() && 
									getAllUsersWithMultiRights.size() > getAllDataForNodeIdByUserId.size() && 
									stageIdsForCurrentUser.size()>1 && dtoAssignNodeRights!=null)
							{
								//System.out.println("AHHHHHHHHHH");
								stageuserdtl.clear();
								stageuserdtl.addAll(docMgmtImpl.getNewStageId(workspaceID,nodeId, usercode, usergrpcode));
								stageuserdtl.addAll(docMgmtImpl.getNextStageIds(workspaceID,nodeId, usercode, usergrpcode));								
							}
							
													
						}
						
						if(stageId == 100){
							System.out.println("Approved");
							stageuserdtl.clear();							
							//stageuserdtl.addAll(docMgmtImpl.getNewStageId(workspaceID,nodeId, usercode, usergrpcode));							
							DTOAssignNodeRights dtoAssignNodeRights = docMgmtImpl.checkDataExistsForUser(workspaceID,nodeId,stageId,usercode);
							
							if(dtoAssignNodeRights == null){
							if(stageId == stageIdsForCurrentUser.get(stageIdsForCurrentUser.size()-1))
							{
								System.out.println("RR IF");
								stageuserdtl.addAll(docMgmtImpl.getNewStageId(workspaceID,nodeId, usercode, usergrpcode));
								stageuserdtl.addAll(docMgmtImpl.getNextStageRightsWise(workspaceID,nodeId, usercode, usergrpcode,stageId,2));
							}
							else if(stageId == stageIdsForCurrentUser.get(0))
							{
								System.out.println("RR ELSE IF");
								stageuserdtl.addAll(docMgmtImpl.getStageIds(workspaceID,nodeId, usercode, usergrpcode,stageId));
							}
							else
							{
								System.out.println("RR ELSE");
								//if(stageIdsForCurrentUser.indexOf(nextStageId)<0)
								//{
									stageuserdtl.clear();
								//}
								//else
								//{
								//	stageuserdtl.addAll(docMgmtImpl.getStageIds(workspaceID,nodeId, usercode, usergrpcode,stageId));
								//}
							}
							}	
						}
						}
						else{
							stageuserdtl.clear();
						}
						
						//Finished
						}
					else{
						stageuserdtl.clear();
					}
						String fileName = docMgmtImpl.getFileName(workspaceID,nodeId);
						if((fileName.endsWith("pdf")||fileName.endsWith("PDF")) && iscreatedRights==true){							
							stampFlag=true;
						}
				
				}
				// Add Node Activities By Node's 'RequiredFlag'
				nodeActivities = new ArrayList<String>();
				if (workSpaceNodeDtl != null) {

					if (workSpaceNodeDtl.getRequiredFlag() == RequiredFlag.ECTD_STF_NODE) {

						if (workSpaceNodeDtl.getNodeTypeIndi() != NodeTypeIndi.ECTD_STF_PARENT_NODE) {

							nodeActivities.add("Add STF");
						} else {
							nodeActivities.add("Edit/Remove STF");
						}
					} else if (workSpaceNodeDtl.getRequiredFlag() == RequiredFlag.ECTD_REPEATABLE_NODE) {

						if (workSpaceNodeDtl.getCloneFlag() == 'N') {
							nodeActivities.add("Repeat Section");
						} else {
							nodeActivities.add("Repeat/Delete Section");
						}
					} else if (workSpaceNodeDtl.getRequiredFlag() == RequiredFlag.SAMPLE_NODE
							&& workSpaceNodeDtl.getCloneFlag() == 'N') {

						// If parent is not 'S' then node is repeatable(i.e.
						// only root node of the section is repeatable).
						DTOWorkSpaceNodeDetail parentNodeDtl = docMgmtImpl
								.getNodeDetail(workspaceID,
										workSpaceNodeDtl.getParentNodeId())
								.get(0);
						if (parentNodeDtl.getRequiredFlag() != RequiredFlag.SAMPLE_NODE) {
							nodeActivities.add("Repeat Section");
							
						}

					} else if (isLeafNode != 0
							&& !(workSpaceNodeDtl.getRequiredFlag() == RequiredFlag.SAMPLE_NODE && workSpaceNodeDtl
									.getCloneFlag() == 'N')) {

						if (workSpaceNodeDtl.getNodeTypeIndi() != NodeTypeIndi.ECTD_STF_NODE) {

							if (workSpaceNodeDtl.getCloneFlag() == 'N') {
								nodeActivities.add("Repeat Section");
							} else {
								nodeActivities.add("Repeat/Delete Section");
							}
						}
					}
				}

				// Get nodeDocHistory for source document
				String enableSrcDoc = propertyInfo.getValue("UploadSrcDoc");
				if (enableSrcDoc != null && enableSrcDoc.equals("YES")) {

					srcDocPath = propertyInfo.getValue("SOURCE_DOC_FOLDER");
					nodeDocHistory = docMgmtImpl.getLatestNodeDocHistory(
							workspaceID, nodeId);
				} else {
					srcDocPath = "No_Path_Found";
				}

			} else {
				nodeId = 0;
			}
			wSOfficeHistory=new Vector<DTOWorkSpaceNodeHistory>();
			wSOfficeHistoryForFlagCheck=new Vector<DTOWorkSpaceNodeHistory>();
			
			wSOfficeHistory=docMgmtImpl.getWorkspaceNodeHistoryForOffice(workspaceID, nodeId);
			//wSOfficeHistorySize=wSOfficeHistory.size();
			wSOfficeHistoryForFlagCheck=docMgmtImpl.getWorkspaceNodeHistoryForOfficeForFlagCheck(workspaceID, nodeId,usercode);
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
			
			assignWorkspaceRightsDetails = docMgmtImpl.getWorkspaceUserDetailForNode(workspaceID, nodeId);
			wsNodeHistory = docMgmtImpl.getNodeHistoryForSectinDeletion(workspaceID, nodeId);
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
			ManualSignatureConfig = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, 1, "ManualSignature").getAttrValue();
			ApplicationUrl = knetProperties.getValue("ApplicationUrl");
			if(nodeDocHistory!=null && nodeDocHistory.size()>0) {
				String temp=workSpaceNodeDtl.getFolderName();
				srcFileExt = "docx";
				newFileName=temp.substring(0, temp.lastIndexOf('.'));
				newFileName=newFileName+"."+srcFileExt ;
				if(wSOfficeHistoryForFlagCheck.size()>0)
					fileNameToShow=wSOfficeHistoryForFlagCheck.get(0).getFileName();
				else
					fileNameToShow=temp.substring(0, temp.lastIndexOf('.'))+"_"+workspaceID+"_"+nodeId+"."+srcFileExt;
			}
			//if(confirmAndUpload=true)
			if(nodeDocHistory.size()>0 && wsNodeHistory.size()>0){
				if(nodeDocHistory.get(0).getDocTranNo()==wsNodeHistory.get(0).getDocTranNo())
				IsUpload=false;
			}
			if(docMgmtImpl.getNodeAttrDetail(workspaceID, 1).size()>0)
				Automated_TM_Required = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, 1, "Automated TM Required").getAttrValue();
			//Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();

			//if(docMgmtImpl.getURSTracebelityMatrixDtlForDocType(workspaceID,Automated_Doc_Type).size()<=0)
//				showAutomateButton=true;
			
			//if(Automated_Doc_Type!=null){
				/*if(nodeId==99 && Automated_TM_Required.equalsIgnoreCase("Yes") && (Automated_Doc_Type.equalsIgnoreCase("URS") || 
						Automated_Doc_Type.equalsIgnoreCase("FS") && showAutomateButton==false)  ){*/
			Vector<DTOWorkSpaceNodeDetail> getNodeDetail = docMgmtImpl.getNodeDetailByNodeDisplayName(workspaceID,"PQ Scripts");
			if(getNodeDetail.size()>0 ){
			if(Automated_TM_Required!=null && Automated_TM_Required.equalsIgnoreCase("Yes") ){
					//URSTracebelityMatrixDtl=docMgmtImpl.getURSTracebelityMatrixDtl(workspaceID,3,4);
					URSTracebelityMatrixDtl=docMgmtImpl.getURSTracebelityMatrixDtl(workspaceID);
					}
				}
			//}
			System.out.println(stageuserdtl.size());
			boolean checkFlag=false;
			for(int i=0;i<stageuserdtl.size();i++){
				if(stageuserdtl.get(i).getStageId() == 100)
					checkFlag = true;
			}
			if(checkFlag == false){
				if(userWiseStageFlag==true)
					nextstageuserdtl = docMgmtImpl.getUserRightsDetailForNextStageUser(workspaceID, nodeId,100);
				
				if(nextstageuserdtl!=null && !nextstageuserdtl.isEmpty())
					nextstageuserdtl= nextstageuserdtl.substring(0, nextstageuserdtl.length() - 1);
				
				if(userWiseStageFlag==false){
					if(stageId==10)
					nextstageuserdtl = docMgmtImpl.getUserRightsDetailForNextStageUser(workspaceID, nodeId,20);
					
					if(stageId==20)
						nextstageuserdtl = docMgmtImpl.getUserRightsDetailForNextStageUser(workspaceID, nodeId,100);
					
					if(nextstageuserdtl!=null && !nextstageuserdtl.isEmpty())
						nextstageuserdtl= nextstageuserdtl.substring(0, nextstageuserdtl.length() - 1);
				}
			}
			
			
		} // nodeid condition if end

		return SUCCESS;
	}
	
	public String sendForReview() throws IOException, SQLException{
		
		autoMail = knetProperties.getValue("AutoMail");
		OpenFileAndSignOff = knetProperties.getValue("OpenFileAndSignOff");
		WorkspaceNodeDaviation = knetProperties.getValue("WorkspaceNodeDaviation");
			
		System.out.println("******************sendForReview");
		System.out.println(workspaceID+" "+nodeId+" "+usercode);		
				
		docMgmtImpl.insertAssignedNodeRight(workspaceID,nodeId,0,10,usercode,3,"SR");	
		
		if(eSign.equalsIgnoreCase("Y")){
			Vector <DTOWorkSpaceNodeHistory> dto;
			dto =docMgmtImpl.getUserSignatureDetail(usercode);
			if(dto.size()>0){
				String uuId = dto.get(0).getUuId();
				int signtrNo = dto.get(0).getSignTranNo();
				docMgmtImpl.UpdateNodeHistoryForESign(workspaceID,nodeId,uuId,signtrNo);
				String roleCode = docMgmtImpl.getRoleCode(workspaceID, usercode);
				docMgmtImpl.UpdateNodeHistoryForRoleCode(workspaceID,nodeId,roleCode);
				
				if(OpenFileAndSignOff.equalsIgnoreCase("Yes")){
					
					DTOWorkSpaceNodeHistory dto1 = new DTOWorkSpaceNodeHistory();
					dto1 = docMgmtImpl.getNodeHistoryForSignOff(workspaceID,nodeId);
					dto1.setWorkSpaceId(workspaceID);
					dto1.setNodeId(nodeId);
					dto1.setTranNo(0);
					dto1.setFileName(dto1.getFileName());
					dto1.setFolderName(dto1.getFolderName());
					dto1.setRemark("");
					dto1.setModifyBy(usercode);
					dto1.setStatusIndi('D');
					dto1.setMode(2);
					docMgmtImpl.insertIntofileopenforsign(dto1);
				}
			}			
		}
		
		//Code to update Adjust Date
		Timestamp attrValue=docMgmtImpl.getAssignNodeRightsData(workspaceID,nodeId,stageId).get(0).getModifyOn();
		  String attrVal=attrValue.toLocalDateTime().toLocalDate().toString();
		  attrVal=attrVal.replace("-", "/");
		  if(attrVal != null && !attrVal.equals("")){
				//hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
				//getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
				
				int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, nodeId);
				//getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustDateInfo(workspaceID,nodeId,parentNodeIdforAdjustDate,usercode,stageId);
				getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(workspaceID,nodeId,parentNodeIdforAdjustDate,usercode,stageId);
				
				//String s[]=attrVal.split("/");
				
				//LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
				LocalDateTime date=attrValue.toLocalDateTime();
				LocalDateTime startDate=null;
				LocalDateTime endDate = null;
				for(int k=0;k<getAdjustDateData.size();k++){
					//System.out.println("Hours : "+hoursData.get(k).getHours());
					DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getAdjustDateData
							.get(k);
					int noofhours=dtotimeline.getHours();
					if(startDate==null){
						startDate=date;
						endDate=startDate.plusHours(noofhours*3);
						DayOfWeek dayOfWeek = endDate.getDayOfWeek();
						//System.out.println(dayOfWeek);
						if(dayOfWeek==DayOfWeek.SUNDAY){
							endDate=endDate.plusHours(24);
						}
					}
					else{
						startDate=endDate;
						endDate=startDate.plusHours(noofhours*3);
						DayOfWeek dayOfWeek = endDate.getDayOfWeek();
						//System.out.println(dayOfWeek);
						if(dayOfWeek==DayOfWeek.SUNDAY){
							endDate=endDate.plusHours(24);
						}
					}	
		            dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
					docMgmtImpl.updateTimelineAdjustDate(dtotimeline);
				}	
			}

		int parentNodeId;
	    String nodeIds = "";
		Vector<DTOWorkSpaceNodeDetail> getParenNodeId;
	    getParenNodeId = docMgmtImpl.getParentNode(workspaceID, nodeId);
	 
	  if(getParenNodeId.size()>0){
	     
		  parentNodeId = getParenNodeId.get(0).getParentNodeId();
		  Vector<DTOWorkSpaceNodeDetail> getDeviationFileDtl = null;
		  if(WorkspaceNodeDaviation.equalsIgnoreCase("Single")){
			  getDeviationFileDtl =docMgmtImpl.getFileUploadSeqForSingle(workspaceID,nodeId,parentNodeId);
			}
		  if(WorkspaceNodeDaviation.equalsIgnoreCase("All")){
			  getDeviationFileDtl =docMgmtImpl.getFileUploadSeqForAll(workspaceID,nodeId,parentNodeId);
			}
		 
		  if(getDeviationFileDtl.size()>0){
			  for(int i=0;i<getDeviationFileDtl.size();i++){
				  nodeIds += String.valueOf(getDeviationFileDtl.get(i).getNodeId())+",";
			  }
			  nodeIds= nodeIds.substring(0, nodeIds.length() - 1);
			  DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			  dto.setWorkspaceId(workspaceID);
			  dto.setNodeId(nodeId);
			  dto.setRefNodeId(nodeIds);
			  dto.setRemark(remark);
			  dto.setModifyBy(usercode);
			  dto.setStatusIndi('N');
	
	   		  docMgmtImpl.insertWSNodeDeviation(dto);
		  	}
	  	}
	  DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		//int tranNo = docMgmtImpl.getMaxTranNoByStageId(workSpaceId,Integer.parseInt(nodeId));
		 Vector<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.getMaxNodeHistoryByTranNo(workspaceID, nodeId);

		 DTOWorkSpaceNodeHistory workSpaceNodeHistoryDTO = new DTOWorkSpaceNodeHistory();
			workSpaceNodeHistoryDTO.setWorkSpaceId(tempHistory.get(0).getWorkSpaceId());
			workSpaceNodeHistoryDTO.setNodeId(nodeId);
			workSpaceNodeHistoryDTO.setTranNo(tempHistory.get(0).getTranNo());
			if (tempHistory.get(0).getFileName() == null) {
				workSpaceNodeHistoryDTO.setFileName("No File");
			} else {
				workSpaceNodeHistoryDTO.setFileName(tempHistory.get(0).getFileName());
			}

			workSpaceNodeHistoryDTO.setFileType("");
			workSpaceNodeHistoryDTO.setFolderName(tempHistory.get(0).getBaseWorkFolder());
			workSpaceNodeHistoryDTO.setRequiredFlag('Y');
			workSpaceNodeHistoryDTO.setStageId(tempHistory.get(0).getStageId());
			if (tempHistory.get(0).getRemark() == null)
				workSpaceNodeHistoryDTO.setRemark("");
			else
				workSpaceNodeHistoryDTO.setRemark(tempHistory.get(0).getRemark());
			workSpaceNodeHistoryDTO.setModifyBy(usercode);
			workSpaceNodeHistoryDTO.setStatusIndi('N');
			workSpaceNodeHistoryDTO.setDefaultFileFormat("");
			String roleCode = docMgmtImpl.getRoleCode(workspaceID, usercode);
			workSpaceNodeHistoryDTO.setRoleCode(roleCode);
			workSpaceNodeHistoryDTO.setFileSize(tempHistory.get(0).getFileSize());
			workSpaceNodeHistoryDTO.setCoOrdinates(pageNo+":"+xCordinates+":"+yCordinates);
			docMgmtImpl.insertNodeHistoryWithCoordinates(workSpaceNodeHistoryDTO);
			
		  if(autoMail.equalsIgnoreCase("Yes")){
			 StageWiseMailReport stageWiseMail = new StageWiseMailReport();
			 //stageWiseMail.StageWiseMail(workspaceID,nodeId,"Created",20);
			 stageWiseMail.StageWiseMailNewFormate(workspaceID,nodeId,"Created",20);
			 System.out.println("Mail Sent....");
	      	}
		  
		  if(!docMgmtImpl.getWorkSpaceDetail(workspaceID).getProjectCode().equalsIgnoreCase("0004")){//checks for CR projects only
		  	//BlockChain HashCode
			String hashCode=docMgmtImpl.generateHashCode(workspaceID,nodeId,stageId);
			//BlockChain HashCode Ends	
		  }
		  //Reloading page after manual signature starts
		  //if(docMgmtImpl.getWorkSpaceDetail(workspaceID).getProjectCode().equalsIgnoreCase("0004")){//checks for CR projects only

			  PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
				baseSrcPath = propertyInfo.getValue("DoToPdfPath");
				String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
				String flag="true";
				int exitFlag=0;
				
				int lastTranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
				previousHistory = docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId,lastTranNo);
				UUID uuid=UUID.randomUUID();
				

				Vector<Object[]> WsDetail= docMgmtImpl.getWorkspaceDescList(workspaceID);
				Object[] nodeRec = (Object[]) WsDetail.get(0);
				String src=(String) nodeRec[9];
				
				String fileName=previousHistory.get(0).getFileName();
				//sString src=previousHistory.get(0).getFolderName();
				//src+="/"+previousHistory.get(0).getWorkSpaceId()+"/"+previousHistory.get(0).getNodeId()+"/"+previousHistory.get(0).getTranNo()+"/"+ previousHistory.get(0).getFileName();
				src+="/"+previousHistory.get(0).getFolderName()+"/"+ previousHistory.get(0).getFileName();
				File fSrc=new File(src);
				File wsFolder = new File(baseSrcPath + "/" + workspaceID);
				wsFolder.mkdirs();

				File nodeFolder = new File(baseSrcPath + "/" + workspaceID + "/"
						+ nodeId);
				nodeFolder.mkdirs();

				//if (lastTranNo != -1) {
					File tranFolder = new File(baseSrcPath + "/" + workspaceID + "/"
							+ nodeId + "/" + uuid);
					tranFolder.mkdirs();
				//}
				
				String ManualSignatureConfig = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, 1, "ManualSignature").getAttrValue();
				DTOWorkSpaceMst wsDetail1 =docMgmtImpl.getWorkSpaceDetail(workspaceID);
				Vector<DTOWorkSpaceNodeDetail> getNodeDetail1 = new Vector<DTOWorkSpaceNodeDetail>();
				getNodeDetail1 = docMgmtImpl.getNodeDetail(workspaceID, nodeId);
				
				char nodeTypeIndi1 = getNodeDetail1.get(0).getNodeTypeIndi();
				
				
				//BlockChain HashCode
				String hashCode=docMgmtImpl.generateHashCode(workspaceID,nodeId,stageId);
				//BlockChain HashCode Ends		
				
				
				
				if(!wsDetail1.getProjectCode().equals("0003") && nodeTypeIndi1!='K' && ManualSignatureConfig != null && ManualSignatureConfig.equalsIgnoreCase("Yes")){
						File inputFilePath = null;
						File outputFilePath;
						String outFile = null;
						try{
						String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
						outFile = tranFolder+"/"+fileNameWithOutExt+"_Sign.pdf";
						String fName = previousHistory.get(0).getFileName();

						File trnFolder = new File(nodeRec[9]+previousHistory.get(0).getFolderName());

						File uploadedFile = new File(trnFolder, fName);

						inputFilePath = new File(uploadedFile.getAbsolutePath()); // Existing file
						outputFilePath = new File(outFile); // New file
						OutputStream fos = new FileOutputStream(outputFilePath);

						
						inputFilePath=new File(trnFolder+"\\"+fileNameWithOutExt+".pdf");
						
						PdfReader pdfReader = new PdfReader(inputFilePath.getAbsolutePath());
						PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
						String signatureFilePath=propertyInfo.getValue("signatureFile");
						
						/*String style = null;
						String imageFile = null;
						Image image = null;
						float xCord = 0;
						float yCord = 0;
						int pageNo = 0;*/
						
						usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
						docMgmtImpl.getUserSignatureDetail(usercode);
						

						Vector<DTOWorkSpaceNodeHistory> userHistory = docMgmtImpl.getProjectSignOffHistory(workspaceID, nodeId);
						Vector<DTOWorkSpaceNodeHistory> nodeHistoryByTranNo = docMgmtImpl.getMaxNodeHistoryByTranNo(workspaceID, nodeId);
						String upcomingAction="";
						if(nodeHistoryByTranNo.size()>0){
							if(nodeHistoryByTranNo.get(0).getStageId()==10 && nodeHistoryByTranNo.get(0).getFileType()==""){
								upcomingAction="Created";
							}
							else if(nodeHistoryByTranNo.get(0).getStageId()==10 && nodeHistoryByTranNo.get(0).getFileType().equalsIgnoreCase("SR")){
								upcomingAction="Created";
								/*if(stageId==20)
									upcomingAction="Reviewed";
								if(stageId==0)
									upcomingAction="Send Back";*/
							}
							else if(nodeHistoryByTranNo.get(0).getStageId()==20 && nodeHistoryByTranNo.get(0).getFileType().equalsIgnoreCase("SR")){
								if(stageId==100)
									upcomingAction="Approve";
								if(stageId==0)
									upcomingAction="Send Back";
							}
							else if(nodeHistoryByTranNo.get(0).getStageId()==100 && nodeHistoryByTranNo.get(0).getFileType().equalsIgnoreCase("SR")){
								if(stageId==100)
									upcomingAction="Approve";
								if(stageId==0)
									upcomingAction="Send Back";
							}
						}
						
						
						Vector<DTOWorkSpaceNodeHistory> data=new Vector<>();
						Vector<DTOWorkSpaceNodeHistory> tempHistory1 = new Vector<>();
						for(int i=0;i<userHistory.size();i++){
							String style = null;
							String imageFile = null;
							Image image = null;
							float xCord = 0;
							float yCord = 0;
							int pageNo = 0;
							data=docMgmtImpl.getWorkspacenodeHistoryByUserId(workspaceID, nodeId, userHistory.get(i).getUserCode());
							tempHistory1 = docMgmtImpl.getUserSignatureDetail(userHistory.get(i).getUserCode());
							
							String arr[]=data.get(0).getCoOrdinates().split(":");
							pageNo=Integer.parseInt(arr[0]);
							xCord=Float.parseFloat(arr[1]);
							yCord=Float.parseFloat(arr[2]);
							

							if(tempHistory1.get(0).getFileType()!=null && !tempHistory1.get(0).getFileType().isEmpty()){
								style=tempHistory1.get(0).getStyle();
							}else{
								imageFile =signatureFilePath+"/"+tempHistory1.get(0).getFolderName()+"/"+tempHistory1.get(0).getFileName();
								File f=new File(imageFile);
								if(f.exists())
								{
								image = Image.getInstance(imageFile);
								PdfImage stream = new PdfImage(image, "", null);	
								stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
								PdfIndirectObject ref = pdfStamper.getWriter().addToBody(stream);
								image.setDirectReference(ref.getIndirectReference());
								image.scaleToFit(70f, 80f); //Scale image's width and height
								image.setAbsolutePosition(xCord, yCord-20);
								}
							}
							PdfContentByte pdfContentByte = pdfStamper.getOverContent(pageNo);
							
							DTOUserMst dtouser = docMgmtImpl.getUserByCode(userHistory.get(i).getUserCode());
							dtoWsHistory =docMgmtImpl.getUserSignatureDetail(userHistory.get(i).getUserCode());
							String baseWorkFolder = propertyInfo.getValue("signatureFile");
							if(dtoWsHistory.size()>0){
								imgFilename = dtoWsHistory.get(0).getFileName();
								uName = userHistory.get(i).getUserName();
								role = dtouser.getRoleName();
								//signdate = userHistory.get(i).getModifyOn();
								if(userHistory.get(i).getCountryCode().equalsIgnoreCase("IND")){
									signdate =  userHistory.get(i).getISTDateTime(); 
							     }
							     else{
							    	 signdate =  userHistory.get(i).getESTDateTime();
							    }
								signId = dtoWsHistory.get(0).getUuId(); 
							}
							//float[] widthsForTable = { 2.5f, 3 };
							float[] widthsForTable = { 7.5f, 10 };
							PdfPTable pdfPTable = new PdfPTable(widthsForTable);
							//pdfPTable.setTotalWidth(82);
							pdfPTable.setTotalWidth(93);
					       //Create cells
					       PdfPCell pdfPCell1 = new PdfPCell();
					       PdfPCell pdfPCell2 = new PdfPCell();
					       //Add cells to table
					       //image = Image.getInstance(imageFile);
					       if(image!= null){   
					    	   image.scaleToFit(38f, 80f); //Scale image's width and height
						       pdfPCell1.addElement(image);
						       pdfPCell1.setFixedHeight(20f);
						       pdfPCell1.setPaddingTop(8f);
						       pdfPCell1.setPaddingLeft(1f);
						       pdfPCell1.setPaddingRight(1f);
						       pdfPCell1.setBorder(Rectangle.NO_BORDER);
						       pdfPCell1.setBackgroundColor(new BaseColor(255,255,255));
						       pdfPTable.addCell(pdfPCell1);
						    }else{
						    	style=tempHistory1.get(0).getFileType();
						    	Font font1 = ft.registerExternalFont(style,ManualSignatureConfig);
						    	if(!style.isEmpty())
						    	font1=ft.registerExternalFont(style,ManualSignatureConfig);
						    	else
						    	font1= FontFactory.getFont("Calibri", 7f, Font.BOLD, BaseColor.BLACK);
						    	
						    	Paragraph p = new Paragraph(tempHistory1.get(0).getUserName(),font1);
						    	pdfPCell1.addElement(p);
						    	pdfPCell1.setFixedHeight(20f);
							    pdfPCell1.setPaddingTop(8f);
							    pdfPCell1.setPaddingLeft(1f);
							    pdfPCell1.setPaddingRight(1f);
							    pdfPCell1.setBorder(Rectangle.NO_BORDER);
							    pdfPCell1.setBackgroundColor(new BaseColor(255,255,255));
						    	pdfPTable.addCell(pdfPCell1);
						    }
					       Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN,  4.4f, BaseColor.BLACK);
						      Paragraph p=new Paragraph("Name : "+uName,font2);
						      Paragraph paragraph = new Paragraph("Date :"+signdate,font2);
						      pdfPCell2.setVerticalAlignment(Element.ALIGN_TOP);
						      pdfPCell2.addElement(p);
						      Paragraph roll = new Paragraph("Role : "+role,font2);
						      pdfPCell2.addElement(roll);
						      pdfPCell2.addElement(paragraph);
						      pdfPCell2.setFixedHeight(20f);
						      pdfPCell2.setBorder(Rectangle.NO_BORDER);
						      pdfPCell2.setBackgroundColor(new BaseColor(255,255,255));
						  
						      Paragraph SignId = new Paragraph("Sign Id:"+signId,font2);
						      pdfPCell2.addElement(SignId);
						      
						      Paragraph action = new Paragraph("Action : "+upcomingAction,font2);
						      pdfPCell2.addElement(action);
						      
						      Phrase phrase = new Phrase();
							  Chunk chunk = new Chunk("\nName : "+uName+" \n"
									  	+ "Date : "+signdate+" \n"
									  	+ "Role : "+role+" \n"/*
							      		+ "Sign Id: "+signId+" \n"
							      		+ "Action : "+upcomingAction+" \n"*/
							      		,font2);
							  String appUrl=propertyInfo.getValue("ApplicationUrl");
							  String docId=docMgmtImpl.getDocIdForSign(workspaceID,nodeId,userHistory.get(i).getTranNo());
							  chunk.setAnchor(appUrl+"FileOpenFromPDF.do?wsId="+workspaceID+"&nodeId="+nodeId+"&tranNo="+data.get(0).getTranNo()
									  +"&userCode="+userHistory.get(i).getUserCode()+"&docId="+docId);
							  //chunk.setAnchor(appUrl+"FileOpenFromPDF.do?wsId="+workspaceID+"&nodeId="+nodeId+"&tranNo="+nodeHistoryByTranNo.get(0).getTranNo()+"&userCode="+userHistory.get(i).getUserCode());
							  phrase.add(chunk);
							  PdfPCell cellOne = new PdfPCell(new Phrase(phrase));
							  cellOne.setBorder(Rectangle.NO_BORDER);
							  cellOne.setBackgroundColor(new BaseColor(255,255,255));
						      //pdfPTable.addCell(pdfPCell2);
							  pdfPTable.addCell(cellOne);
						      //pdfPTable.writeSelectedRows(0, -1, xCord-42, yCord+20, pdfContentByte);
							  pdfPTable.writeSelectedRows(0, -1, xCord-45.9f, yCord+15, pdfContentByte);
						}
						  pdfStamper.close();
					      pdfReader.close();
						//Vector<DTOWorkSpaceNodeHistory> tempHistory1 = docMgmtImpl.getUserSignatureDetail(usercode);
						/*
						String arr[]=previousHistory.get(0).getCoOrdinates().split(":");
						pageNo=Integer.parseInt(arr[0]);
						xCord=Float.parseFloat(arr[1]);
						yCord=Float.parseFloat(arr[2]);
						
						if(tempHistory1.get(0).getFileType()!=null && !tempHistory1.get(0).getFileType().isEmpty()){
							style=tempHistory1.get(0).getStyle();
						}else{
							imageFile =signatureFilePath+"/"+tempHistory1.get(0).getFolderName()+"/"+tempHistory1.get(0).getFileName();
							File f=new File(imageFile);
							if(f.exists())
							{
							image = Image.getInstance(imageFile);
							PdfImage stream = new PdfImage(image, "", null);	
							stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
							PdfIndirectObject ref = pdfStamper.getWriter().addToBody(stream);
							image.setDirectReference(ref.getIndirectReference());
							image.scaleToFit(70f, 80f); //Scale image's width and height
							image.setAbsolutePosition(xCord, yCord-20);
							}
						}*/
						
						/*PdfContentByte pdfContentByte = pdfStamper.getOverContent(pageNo);
						
						DTOUserMst dtouser = docMgmtImpl.getUserByCode(usercode);
						dtoWsHistory =docMgmtImpl.getUserSignatureDetail(usercode);
						String baseWorkFolder = propertyInfo.getValue("signatureFile");
						if(dtoWsHistory.size()>0){
							imgFilename = dtoWsHistory.get(0).getFileName();
							uName = dtoWsHistory.get(0).getUserName();
							role = dtouser.getRoleName();
							signdate = dtoWsHistory.get(0).getModifyOn();
							signId = dtoWsHistory.get(0).getUuId();
						}*/
						  /*float[] widthsForTable = { 2.5f, 3 };
							PdfPTable pdfPTable = new PdfPTable(widthsForTable);
					       pdfPTable.setTotalWidth(106);
					       //Create cells
					       PdfPCell pdfPCell1 = new PdfPCell();
					       PdfPCell pdfPCell2 = new PdfPCell();
					       //Add cells to table
					       //image = Image.getInstance(imageFile);
					      */ 
					       /*if(image!= null){   
					    	   image.scaleToFit(45f, 80f); //Scale image's width and height
						       pdfPCell1.addElement(image);
						       pdfPCell1.setFixedHeight(28f);
						       pdfPCell1.setPaddingTop(5f);
						       pdfPCell1.setPaddingLeft(1f);
						       pdfPCell1.setPaddingRight(1f);
						       pdfPCell1.setBorder(Rectangle.NO_BORDER);
						       pdfPCell1.setBackgroundColor(new BaseColor(255,255,45));
						       pdfPCell1.setBorder(Rectangle.NO_BORDER);
						       pdfPCell1.setBackgroundColor(new BaseColor(255,255,255));
						       pdfPTable.addCell(pdfPCell1);
						    }else{
						    	style=tempHistory1.get(0).getFileType();
						    	Font font1 = ft.registerExternalFont(style);
						    	if(!style.isEmpty())
						    	font1 = FontFactory.getFont(style, 7f,Font.BOLD, BaseColor.BLACK);
						    	else
						    	font1= FontFactory.getFont("Calibri", 3f, Font.BOLD, BaseColor.BLACK);
						    	
						    	Paragraph p = new Paragraph(tempHistory1.get(0).getUserName(),font1);
						    	pdfPCell1.addElement(p);
						    	pdfPCell1.setFixedHeight(28f);
							    //pdfPCell1.setPaddingTop(5f);
							    pdfPCell1.setPaddingLeft(1f);
							    pdfPCell1.setPaddingRight(1f);
							    pdfPCell1.setBorder(Rectangle.NO_BORDER);
							    pdfPCell1.setBackgroundColor(new BaseColor(255,255,45));
							    pdfPCell1.setBorder(Rectangle.NO_BORDER);
							    pdfPCell1.setBackgroundColor(new BaseColor(255,255,255));
						    	pdfPTable.addCell(pdfPCell1);
						    }*/
					      
					      /*Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 3, BaseColor.BLACK);
					      Paragraph p=new Paragraph("Name : "+uName,font2);
					      Paragraph paragraph = new Paragraph("Date :"+signdate,font2);
					      pdfPCell2.setVerticalAlignment(Element.ALIGN_TOP);
					      pdfPCell2.addElement(p);
					      Paragraph roll = new Paragraph("Role : "+role,font2);
					      pdfPCell2.addElement(roll);
					      pdfPCell2.addElement(paragraph);
					      pdfPCell2.setFixedHeight(28f);
					      pdfPCell2.setBorder(Rectangle.NO_BORDER);
					      pdfPCell2.setBackgroundColor(new BaseColor(255,255,255));
					  
					      Paragraph SignId = new Paragraph("Sign Id:"+signId,font2);
					      pdfPCell2.addElement(SignId);
					      pdfPTable.addCell(pdfPCell2);
					      pdfPTable.writeSelectedRows(0, -1, xCord-40, yCord+15, pdfContentByte);*/
					      
					    /*  pdfStamper.close();
					      pdfReader.close();*/

						
						}
						catch (Exception e) {
						System.out.println(e.toString());
						htmlContent=e.toString();
						return SUCCESS;
						}

						System.out.println("Uploaded File Path: >> "+ inputFilePath);

						System.out.println("After Add Watermark File Path: >> "+ outFile);
						String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
						/*DownloadFolderPath=baseSrcPath + "/" + WorkspaceId + "/"
								+ nodeId + "/" + uuid+ "/" + fileNameWithOutExt+".pdf";*/
						//File original=new File(inputFilePath.getAbsolutePath());
						File fileToCopy=new File(outFile);
						File srcFile = new File(fileToCopy.toString());
						File destFile = new File(inputFilePath.getParent()+"\\"+fileNameWithOutExt+"_Sign.pdf");
						//Files.copy(fileToCopy.toPath(),original.toPath() ,StandardCopyOption.REPLACE_EXISTING);
						InputStream is = null;
					    OutputStream os = null;
					    try {
					        is = new FileInputStream(srcFile);
					        os = new FileOutputStream(destFile); 
					        byte[] buffer = new byte[1024];
					        int length;
					        while ((length = is.read(buffer)) > 0) {
					            os.write(buffer, 0, length);
					        }
					    } catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
					        is.close();
					        os.close();
					    }
		  }
		  //Reloading page after manual signature ends
				
				
				
		 // }
		return SUCCESS;
	}
public String checkFileUploadSeq(){
		
		workspaceID = ActionContext.getContext().getSession().get("ws_id")
				.toString();
		//WorkspaceNodeDaviation = knetProperties.getValue("WorkspaceNodeDaviation");
		System.out.println("***Check Earlier All File Approve Or Not.. ***");
		System.out.println(workspaceID+" "+nodeId);		
		checkDeviationFile = docMgmtImpl.getDeviationFile(workspaceID,nodeId);
		if(checkDeviationFile.size()>0){
			htmlContent="true";
		}
		else{
			int parentNodeId;
	    
			Vector<DTOWorkSpaceNodeDetail> getParenNodeId;
		
			getParenNodeId = docMgmtImpl.getParentNode(workspaceID, nodeId);
	 
			if(getParenNodeId.size()>0){
				parentNodeId = getParenNodeId.get(0).getParentNodeId();
				boolean getfileUploadSeqDetail;
				getfileUploadSeqDetail =docMgmtImpl.getfileUploadSeqDetail(workspaceID,nodeId,parentNodeId);
		  	 
				if(getfileUploadSeqDetail==false){
					htmlContent="false";
				}
				else{
					htmlContent="true";
				}
			 }
		  }
		return SUCCESS;
}
	
  public String fileUploadSeq(){
		
		workspaceID = ActionContext.getContext().getSession().get("ws_id").toString();
		usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		WorkspaceNodeDaviation = knetProperties.getValue("WorkspaceNodeDaviation");
		System.out.println(workspaceID+" "+nodeId+" "+remark);		
	    int parentNodeId;
	    String nodeIds = "";
		Vector<DTOWorkSpaceNodeDetail> getParenNodeId;
	    getParenNodeId = docMgmtImpl.getParentNode(workspaceID, nodeId);
	 
	  /*if(getParenNodeId.size()>0){
	     
		  parentNodeId = getParenNodeId.get(0).getParentNodeId();
		  Vector<DTOWorkSpaceNodeDetail> getDeviationFileDtl;
		  getDeviationFileDtl =docMgmtImpl.getfileUploadSeq(workspaceID,nodeId,parentNodeId);
		 
		  if(getDeviationFileDtl.size()>0){
			  for(int i=0;i<getDeviationFileDtl.size();i++){
				  nodeIds += String.valueOf(getDeviationFileDtl.get(i).getNodeId())+",";
			  }
			  nodeIds= nodeIds.substring(0, nodeIds.length() - 1);
			  DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			  dto.setWorkspaceId(workspaceID);
			  dto.setNodeId(nodeId);
			  dto.setRefNodeId(nodeIds);
			  dto.setRemark(remark);
			  dto.setModifyBy(usercode);
			  dto.setStatusIndi('N');
	
	   		  docMgmtImpl.insertWSNodeDeviation(dto);
	   		htmlContent="false";
		  }
		  else{
			  htmlContent="true";
		  }
	  }*/
	 return SUCCESS;
	}
  
  public String showFileUploadSeq(){
	  			
	  	workspaceID = ActionContext.getContext().getSession().get("ws_id").toString();
		usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		WorkspaceNodeDaviation = knetProperties.getValue("WorkspaceNodeDaviation");
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");	

		StringBuffer tableHtml=new StringBuffer();
		String html="";
		System.out.println(workspaceID+" "+nodeId+" "+remark);		
	    int parentNodeId;
	    String nodeIds = "";
		Vector<DTOWorkSpaceNodeDetail> getParenNodeId;
	    getParenNodeId = docMgmtImpl.getParentNode(workspaceID, nodeId);
	 
	  if(getParenNodeId.size()>0){
	     
		  parentNodeId = getParenNodeId.get(0).getParentNodeId();
		  Vector<DTOWorkSpaceNodeDetail> getDeviationFileDtl = null;
		  if(WorkspaceNodeDaviation.equalsIgnoreCase("Single")){
			 // getDeviationFileDtl =docMgmtImpl.getFileUploadSeqForSingle(workspaceID,nodeId,parentNodeId);
			  getDeviationFileDtl =docMgmtImpl.getFileUploadSeqForSingleUpdated(workspaceID,nodeId,parentNodeId);
			}
		  if(WorkspaceNodeDaviation.equalsIgnoreCase("All")){
			  //getDeviationFileDtl =docMgmtImpl.getFileUploadSeqForAll(workspaceID,nodeId,parentNodeId);
			  getDeviationFileDtl =docMgmtImpl.getFileUploadSeqForAllUpdated(workspaceID,nodeId,parentNodeId);
			}
		  boolean isOddRow = true;
		  if(getDeviationFileDtl.size()>0){
			  for(int i=0;i<getDeviationFileDtl.size();i++){
				  nodeIds += String.valueOf(getDeviationFileDtl.get(i).getNodeId())+",";
			  }
			  nodeIds= nodeIds.substring(0, nodeIds.length() - 1);
			  getWSDeviationDetail = docMgmtImpl.getWSDeviationDetail(workspaceID,nodeIds);
			  
			  	tableHtml.append("<TABLE class=\"datatable\"style=\"table-layout:fixed; margin-left: 20px; margin-top: 5px;"+
			  	                " margin-bottom: 5px;font-family: Calibri;border-collapse: collapse; width: 95%;\">");
			  	tableHtml.append("<TR style=\"color: white; border: 1px solid black;\">");
				tableHtml.append("<TH style=\"border: 1px solid; background: #b1b1b1; border-color: black; padding-left: 5px;\">"
									+"<b style=\"font-size: 17px;\">"+lbl_nodeName+"/"+lbl_folderName+"</b></TH>");
				/*tableHtml.append("<TH style=\"border: 1px solid; background: #b1b1b1; border-color: black; padding-left: 5px;\">"
									+ "<b style=\"font-size: 17px;\">Document Name</b></TH>");*/
				tableHtml.append("<TH style=\"border: 1px solid; background: #b1b1b1; border-color: black; padding-left: 5px;\">"
									+ "<b style=\"font-size: 17px;\">Current Stage</b></TH></TR>");
			
			  if(getDeviationFileDtl.size()>0){
				  for(int i=0;i<getDeviationFileDtl.size();i++){
				  	//tableHtml.append("<TR style=\"border: 1px solid black;border-collapse: collapse;\">");
				  	tableHtml.append("<tr class=\""+((isOddRow)?"odd":"even")+"\">");
					tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getDeviationFileDtl.get(i).getNodeName()+" ["+getDeviationFileDtl.get(i).getFolderName()+"]</TD>");
					//tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getDeviationFileDtl.get(i).getFolderName()+"</TD>");
					tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getDeviationFileDtl.get(i).getStageDesc()+"</TD></TR>");
					if(isOddRow)
						isOddRow=false;
					else
						isOddRow=true;
				  }
			  }
			  tableHtml.append("</TABLE>");
			  html= tableHtml.toString();
	   		htmlContent=html;
		  }
		  else{
			  htmlContent="true";
		  }
	  }
	 return SUCCESS;
	}
	public String sendForVoid() throws MalformedURLException{
		System.out.println("******************sendForVoid");
		System.out.println(workspaceID+" "+nodeId+" "+usercode);		
		
		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		Vector <DTOWorkSpaceNodeHistory> getLastNodeHistory = docMgmtImpl.getWorkspaceNodeHistoryForVoid(workspaceID,nodeId);
		
		if(getLastNodeHistory.size()>0){
			
			dto.setWorkSpaceId(workspaceID);
			dto.setNodeId(nodeId);
			dto.setFileName(getLastNodeHistory.get(0).getFileName());
			dto.setFolderName(getLastNodeHistory.get(0).getFileName());
			dto.setStageId(getLastNodeHistory.get(0).getStageId());
			dto.setRemark(remark);
			dto.setTranNo(getLastNodeHistory.get(0).getTranNo());
			dto.setModifyBy(usercode);
			dto.setStatusIndi('N');
			
			docMgmtImpl.insertWSNodeVoidDetail(dto);
			String roleCode = docMgmtImpl.getRoleCode(workspaceID, usercode);
			docMgmtImpl.UpdateNodeHistoryForRoleCode(workspaceID,nodeId,roleCode);
			
			//BlockChain HashCode
			String hashCode=docMgmtImpl.generateHashCodeForVoid(workspaceID,nodeId,getLastNodeHistory.get(0).getStageId());
			//BlockChain HashCode Ends
			
			
		}
		return SUCCESS;
	}

	private ArrayList<Object[]> filterAttributes(
			Vector<DTOWorkSpaceNodeAttribute> attrDtl, String attrType) {
		if (attrDtl == null || attrType == null) {
			return null;
		}

		ArrayList<Object[]> filterAttributeList = new ArrayList<Object[]>();
		for (int i = 0; i < attrDtl.size(); i++) {
			DTOWorkSpaceNodeAttribute dtoWorkSpaceNodeAttribute = attrDtl
					.get(i);

			System.out.println(i + " - "
					+ dtoWorkSpaceNodeAttribute.getAttrName());

			if (attrType.equals("AutoCompleter")
					&& dtoWorkSpaceNodeAttribute.getAttrType().equals(attrType)) {
				ArrayList<String> attrValueList = new ArrayList<String>();
				for (int j = 0; j < attrDtl.size(); j++) {
					DTOWorkSpaceNodeAttribute dtoWorkSpaceNodeAttribute_ForValue = attrDtl
							.get(j);
					if (dtoWorkSpaceNodeAttribute.getAttrId() == dtoWorkSpaceNodeAttribute_ForValue
							.getAttrId()) {
						attrValueList.add(dtoWorkSpaceNodeAttribute_ForValue
								.getAttrMatrixValue());
						attrDtl.remove(j--);
					}
				}
				attrDtl.add(i, dtoWorkSpaceNodeAttribute);
				filterAttributeList.add(new Object[] {
						dtoWorkSpaceNodeAttribute.getAttrId(), attrValueList });
			}

			if (attrType.equals("Dynamic")
					&& dtoWorkSpaceNodeAttribute.getAttrType().equals(attrType)) {
				ArrayList<String> attrValueList = new ArrayList<String>();
				DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix = new DTOAttrReferenceTableMatrix();
				dtoAttrReferenceTableMatrix
						.setiAttrId(dtoWorkSpaceNodeAttribute.getAttrId());
				ArrayList<DTOAttrReferenceTableMatrix> attrReferenceTableMatrixs = docMgmtImpl
						.getFromAttrReferenceTableMatrix(dtoAttrReferenceTableMatrix);
				if (attrReferenceTableMatrixs != null
						&& attrReferenceTableMatrixs.size() > 0
						&& attrReferenceTableMatrixs.size() <= 1) {
					DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix2 = attrReferenceTableMatrixs
							.get(0);
					String tableName = dtoAttrReferenceTableMatrix2
							.getvTableName();
					String tableColumn = dtoAttrReferenceTableMatrix2
							.getvTableColumn();
					DTODynamicTable dtoDynamicTable = docMgmtImpl
							.getCompleteTableDetails(tableName);
					int idxColumn = dtoDynamicTable.getColumnNames().indexOf(
							tableColumn);
					if (idxColumn >= 0
							&& idxColumn < dtoDynamicTable.getColumnNames()
									.size()) {
						for (int indTableRecord = 0; indTableRecord < dtoDynamicTable
								.getTableData().size(); indTableRecord++) {
							attrValueList.add(dtoDynamicTable.getTableData()
									.get(indTableRecord).get(idxColumn));
							// System.out.println(indTableRecord + " - " +
							// dtoDynamicTable.getTableData().get(indTableRecord).get(idxColumn));
						}
					}
				}
				filterAttributeList.add(new Object[] {
						dtoWorkSpaceNodeAttribute.getAttrId(), attrValueList });
			}
		}

		return filterAttributeList;
	}

public String chekFileCertified(){
		
		workspaceID = ActionContext.getContext().getSession().get("ws_id").toString();
		
		int tranNo = docMgmtImpl.getMaxTranNoByStageId(workspaceID,nodeId);
		
		ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
				.showFullNodeHistoryByStageId(workspaceID, nodeId,tranNo);
		
		String DefaultFileFormat = tempHistory.get(0).getDefaultFileFormat();
		
		if(DefaultFileFormat.equalsIgnoreCase("Certified")){
		    
			System.out.println("File Already Certified..");
			htmlContent="true";
			
		}
		else{
			System.out.println("File Not Certified..");
			
			
		}
		
		 return SUCCESS;
	}
	
	public String CertifiedFile() throws DocumentException, IOException{
		
		//System.out.println("Test");
		boolean certifiedWithAttr=false;
		ArrayList<String> time = new ArrayList<String>();
		String locationName="";
		String countryCode="";
		workspaceID = ActionContext.getContext().getSession().get("ws_id").toString();
		
	
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		cretifiedFilePath = knetProperties.getValue("CertifiedFilePath");
		OpenFileAndSignOff = knetProperties.getValue("OpenFileAndSignOff");
		//DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		
		//Date date = new Date();

		int tranNo = docMgmtImpl.getMaxTranNoByStageId(workspaceID,nodeId);
			
		ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
				.showFullNodeHistoryByStageId(workspaceID, nodeId,tranNo);
		
		attrDtl = docMgmtImpl.getNodeAttributes(workspaceID,nodeId);
	  for(int i=0;i<attrDtl.size();i++){
		  if(attrDtl.get(i).getAttrName().equalsIgnoreCase("Type of Signature")
				  && attrDtl.get(i).getAttrValue().equalsIgnoreCase("Physical")){
			  certifiedWithAttr =true;
		  }
	  }
		
		
		//time = docMgmtImpl.TimeZoneConversion(tempHistory.get(0).getModifyOn(),locationName,countryCode);
		int uploadedUserCode = tempHistory.get(0).getUserCode();
		
		String uploadedusername = tempHistory.get(0).getUserName();
		String certifiedBy = tempHistory.get(0).getCertifiedByName();
		
		Vector<DTOUserMst> useDtl = new Vector<DTOUserMst>();
		useDtl = docMgmtImpl.getUserLoaction(uploadedUserCode);
		if(useDtl.size()>0){
			locationName = useDtl.get(0).getLocationName();
			countryCode = useDtl.get(0).getCountyCode();
		}
		//Timestamp timestamp = new Timestamp(date.getTime());
        
       // time = docMgmtImpl.TimeZoneConversion(timestamp,locationName,countryCode);
		 time = docMgmtImpl.TimeZoneConversion(tempHistory.get(0).getCertifiedOn(),locationName,countryCode);
		String modify ="";
		if(countryCode.equalsIgnoreCase("IND")){
			modify= time.get(0);
		}
		else{
			modify= time.get(1);
		}
				
		usercode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("userid").toString());
				
		String DefaultFileFormat = tempHistory.get(0).getDefaultFileFormat();

			if((DefaultFileFormat==null || DefaultFileFormat.equals("")) && !DefaultFileFormat.equalsIgnoreCase("Certified")){
			
				if(usercode==uploadedUserCode){
					
					if(certifiedWithAttr==true){
						
						DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
						dto.setWorkSpaceId(workspaceID);
						dto.setNodeId(nodeId);
						dto.setTranNo(tranNo);
						dto.setDefaultFileFormat("Certified");
						dto.setCertifiedBy(usercode);
						docMgmtImpl.UpdateWorkspaceNodeHistory(dto);
						
						dto = null;
						
					}else{
		
				System.out.println(tempHistory.size());
		
				String fileName = tempHistory.get(0).getFileName();
		
				String tranFolder = tempHistory.get(0).getBaseWorkFolder() + tempHistory.get(0).getFolderName();
		
				File uploadedFile = new File(tranFolder, fileName);
				//File tempFile = new File(tranFolder, "temp.pdf");
				
				File wsIdpath = new File(cretifiedFilePath + "/" + workspaceID );
				File ndIdPath;
				File tranfldrPath;
				
				if(!wsIdpath.isDirectory()){
					wsIdpath.mkdir();
					
					ndIdPath = new File(wsIdpath+"/"+nodeId);
					if(!ndIdPath.isDirectory()){
						ndIdPath.mkdir();
						
						tranfldrPath = new File(ndIdPath+"/"+tranNo);
						 if(!tranfldrPath.isDirectory()){
							 tranfldrPath.mkdir();
						 }	
					}
					else{
						tranfldrPath = new File(ndIdPath+"/"+tranNo);
						if(!tranfldrPath.isDirectory()){
							tranfldrPath.mkdir();
						}	
					}
					
				}else{
					ndIdPath = new File(wsIdpath+"/"+nodeId);
					if(!ndIdPath.isDirectory()){
						ndIdPath.mkdir();
						
						tranfldrPath = new File(ndIdPath+"/"+tranNo);
						 if(!tranfldrPath.isDirectory()){
							 tranfldrPath.mkdir();
						 }	
					}
					else{
						tranfldrPath = new File(ndIdPath+"/"+tranNo);
						if(!tranfldrPath.isDirectory()){
							tranfldrPath.mkdir();
						}	
					}
				}
				
				String outFile = wsIdpath+"/"+nodeId+"/"+tranNo+"/"+fileName;
		
				File inputFilePath = new File(uploadedFile.getAbsolutePath()); // Existing file
				//File outputFilePath = new File(tempFile.getAbsolutePath()); // New file
				
				File outputFilePath = new File(outFile); // New file

				OutputStream fos = new FileOutputStream(outputFilePath);

				PdfReader pdfReader = new PdfReader(inputFilePath.getAbsolutePath());
        
				PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
        
				int lastPageOfPdf = pdfReader.getNumberOfPages();
        
				for (int i = 1; i <= lastPageOfPdf; i++) {
                 
					PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);
					pdfContentByte.beginText();
					pdfContentByte.setFontAndSize(BaseFont.createFont
                                                 (BaseFont.TIMES_ROMAN, //Font name
                                                   BaseFont.CP1257, //Font encoding
                                                  BaseFont.EMBEDDED //Font embedded
                                                  ), 10); // set font and size
              
					pdfContentByte.setColorFill(BaseColor.BLACK);// set font Color
					//pdfContentByte.setTextMatrix(48,28); // 
					//pdfContentByte.showText("*This is an electronically certified true copy."); // set Stamp
               
					//pdfContentByte.setTextMatrix(50,17); // 
					//pdfContentByte.showText("Certified by : "+uploadedusername); // set Stamp
               
					pdfContentByte.setTextMatrix(50,05); // 
					pdfContentByte.showText("*This is an electronically certified true copy. Certified By: "+certifiedBy + " On: "+modify); // set Stamp
               
					System.out.println("Text added in "+outputFilePath);
               
					pdfContentByte.endText();
               
				}
        
				pdfStamper.close(); //close pdfStamper
     
				 Path sourcePath = Paths.get(inputFilePath.getAbsolutePath());
			     Path destinationPath = Paths.get(outputFilePath.getAbsolutePath());
				 
		       	Files.copy(destinationPath,sourcePath, StandardCopyOption.REPLACE_EXISTING);
		       	
				/*File outFile = null;
				outFile = new File(outputFilePath.getAbsolutePath());
        
				new File(inputFilePath.getAbsolutePath()).delete();
    	
				outFile.renameTo(new File(inputFilePath.getAbsolutePath()));*/
       	
				System.out.println("Uploaded File Path: >> "+ inputFilePath);
      
				System.out.println("After Add Watermark File Path: >> "+ outFile);
				
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(workspaceID);
				dto.setNodeId(nodeId);
				dto.setTranNo(tranNo);
				dto.setDefaultFileFormat("Certified");
			    dto.setCertifiedBy(usercode);
				docMgmtImpl.UpdateWorkspaceNodeHistory(dto);
				
				dto = null;
				
				docMgmtImpl.insertAssignedNodeRight(workspaceID,nodeId,0,10,usercode,3,"SR");
				
				int MaxtranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
				int newTranNo = docMgmtImpl.getNewTranNo(workspaceID);
				DTOWorkSpaceNodeHistory dtownd = docMgmtImpl
						.getWorkspaceNodeHistorybyTranNo(workspaceID, nodeId, MaxtranNo);
				dtownd.setStageId(100);
				dtownd.setModifyBy(usercode);
				dtownd.setTranNo(newTranNo);
				dtownd.setRemark("");
				dtownd.setDefaultFileFormat("Certified");
				String roleCode = docMgmtImpl.getRoleCode(workspaceID, usercode);
				dtownd.setRoleCode(roleCode);
				dtownd.setCertifiedBy(usercode);
				//docMgmtImpl.insertNodeHistory(dtownd);
				docMgmtImpl.insertNodeHistoryWithRoleCode(dtownd);
				
				String flag="";
				docMgmtImpl.insertAssignedNodeRight(workspaceID,nodeId,newTranNo,100,usercode,1,flag);
				docMgmtImpl.UpdateTranNoForStageInAttrHistory(workspaceID, nodeId,newTranNo);
				
				if(OpenFileAndSignOff.equalsIgnoreCase("Yes")){
					DTOWorkSpaceNodeHistory dto1 = new DTOWorkSpaceNodeHistory();
					dto1 = docMgmtImpl.getNodeHistoryForSignOff(workspaceID,nodeId);
					dto1.setWorkSpaceId(workspaceID);
					dto1.setNodeId(nodeId);
					dto1.setTranNo(0);
					dto1.setFileName(dto1.getFileName());
					dto1.setFolderName(dto1.getFolderName());
					dto1.setRemark("");
					dto1.setModifyBy(usercode);
					dto1.setStatusIndi('D');
					dto1.setMode(2);
					docMgmtImpl.insertIntofileopenforsign(dto1);
					}
					}
				htmlContent = "Document certified successfully.";
				}
			else{
				htmlContent="You don't have permission to certified this document because it is uploaded by other user.";
				
				}
		}
		else{
			htmlContent="Document already certified.";
		}
       
        return SUCCESS;
        
	}

	public void saveRemark() {
		System.out.println("Remark Saved Successfully=" + getNodeId());
	}
	
public String checkNodeRights(){
		
		workspaceID = ActionContext.getContext().getSession().get("ws_id")
				.toString();
		int usergrpcode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		usercode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("userid").toString());
		
		ArrayList<Integer> stageIdsRightsWise = docMgmtImpl.getStageIdsRightsWise(workspaceID, usercode, usergrpcode, ndId);
		isCreate = stageIdsRightsWise.contains(10);
		if(isCreate){
			htmlContent = "true";
		}
		else{
			htmlContent = "false";
		}
		 
		return SUCCESS;
	}

	public String checkNodeHistory(){
		
		workspaceID = ActionContext.getContext().getSession().get("ws_id")
				.toString();
		
		ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
				.showFullNodeHistory(workspaceID, ndId);
		
		int dto = tempHistory.size();
	
		if(dto != 0){
			htmlContent = "true";
		}
		else{
			htmlContent = "false";
		}
		 
		return SUCCESS;
	}
	public String checkStageDetail(){
		
		workspaceID = ActionContext.getContext().getSession().get("ws_id")
				.toString();
		
		ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
				.getLatestNodeHistory(workspaceID, ndId);
		
		int dto = tempHistory.size();
		if(dto != 0){
		    htmlContent = tempHistory.get(0).getStageId()+":"+tempHistory.get(0).getCurrentSeqNumber();
		}
		else{
			htmlContent = 0+":"+"";
		}
		return SUCCESS;
	}
	
	
	public String getSectionAttribute(){
		htmlContent="";
		
		int usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		
		
		try{
			Automated_TM_Required = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, 1, "Automated TM Required").getAttrValue();
			System.out.println("Automated_TM_Required---"+Automated_TM_Required);
			
			//Automated_Doc_Type=docMgmtImpl.getAttrDtlForPageSetting(workspaceID, nodeId);
			Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();
			System.out.println("Automated_Doc_Type---"+Automated_Doc_Type);
			
			int tranNo = docMgmtImpl.getMaxTranNoByStageId(workspaceID,nodeId);
			if(Automated_Doc_Type!=null){
				if(docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId, tranNo).size()>0){
					if(docMgmtImpl.getTracebelityMatrixDtlForDocTypeHistory(workspaceID,nodeId,tranNo,Automated_Doc_Type).size()<=0){
						showAutomateButton=true;
					}
				}
			}
			}
			catch(Exception e){
				Automated_TM_Required="";
				Automated_Doc_Type="";
			}
		
		WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>(); 	
   		WsNodeDetail=docMgmtImpl.isLeafParentForAttribute(workspaceID,nodeId,usercode,usergrpcode);
   		
   		lbl_folderName = knetProperties.getValue("ForlderName");
   		lbl_nodeName = knetProperties.getValue("NodeName");
   		DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetail(workspaceID);
   		ProjectName=wsDetail.getWorkSpaceDesc();
   		NodeName=docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeDisplayName();
   		
   		//getAttributeDetails=new ArrayList<List<String>>();
   		getProjectLevelSearchResult=new Vector<>();
   		WsNodeAttrDetail=new Vector<>();
   		getAttributeDetailsForDisplay=new ArrayList<List<String>>();
   		AttrDetilas=new ArrayList<List<String>>();
   		
   		List<String> tableHeader = new ArrayList<String>();
   		if(WsNodeDetail.size()>0){}
   		sourceNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0);
   		originalNodeWithAllclones = new Vector<DTOWorkSpaceNodeDetail>();
		int parentNodeId= docMgmtImpl.getParentNodeId(workspaceID, nodeId);
		
		Vector<DTOWorkSpaceNodeDetail> repeatNodeAndSiblingsDtl = docMgmtImpl.getChildNodeByParent(parentNodeId, workspaceID);
		
		for(int i = 0; i < repeatNodeAndSiblingsDtl.size(); i++) {
			DTOWorkSpaceNodeDetail currentSibling = repeatNodeAndSiblingsDtl.get(i);
			//System.out.println(currentSibling.getNodeName());
			if(currentSibling.getNodeName().equalsIgnoreCase(sourceNodeDetail.getNodeName())) {
				
				DTOWorkSpaceNodeDetail repetnodesibling = (DTOWorkSpaceNodeDetail) repeatNodeAndSiblingsDtl
						.get(i);
				/*nodeNames=repetnodesibling.getNodeDisplayName();
				if(nodeNames.contains("{")){
					int startingIndex = nodeNames.indexOf("{");
					int closingIndex = nodeNames.indexOf("}");
					result1 = nodeNames.substring(startingIndex , closingIndex+1);
										
					nodeNames=nodeNames.replace(result1, "  ");
					
					repetnodesibling.setNodeDisplayName(nodeNames);
					}*/
				
				originalNodeWithAllclones.addElement(repetnodesibling);
			}
		}

		wsNodeDtlForParent = new Vector<DTOWorkSpaceNodeDetail>();
		wsNodeDtlToShow = new Vector<DTOWorkSpaceNodeDetail>();
		
		WsNodeAttrDetail=docMgmtImpl.getNodeAttrDetailForCSV(workspaceID, nodeId);
		wsNodeDtlForParent.clear();
	
		
 		for(int j=0;j<originalNodeWithAllclones.size();j++){
 			//wsNodeDtlForParent.size()
 			getAttributeDetails=new ArrayList<List<String>>();
 			
 			DTOWorkSpaceNodeDetail dto1=new DTOWorkSpaceNodeDetail();
 			dto1.setNodeId(originalNodeWithAllclones.get(j).getNodeId());
 			
 			String[] nDisplayName = null;
 			String nodeDisplayName=originalNodeWithAllclones.get(j).getNodeDisplayName();
 			if(nodeDisplayName.contains("{")){
 				nDisplayName=nodeDisplayName.substring(nodeDisplayName.indexOf("{") + 1, nodeDisplayName.indexOf("}")).split(",");
 			}
 			else{
 				nDisplayName=nodeDisplayName.split(" ");
 			}
 			
 			dto1.setNodeDisplayName(originalNodeWithAllclones.get(j).getNodeDisplayName());
 			dto1.setParentFlag('Y');
 			
 			dto1.setCloneFlag(originalNodeWithAllclones.get(j).getCloneFlag());
 			dto1.setRequiredFlag('D');
 			Vector<DTOWorkSpaceNodeDetail> getChildNodes;
 			Vector<DTOWorkSpaceNodeHistory> getNodeHistoryForSection;
 			getChildNodes=docMgmtImpl.getChildNodesForSectionDeletion(workspaceID, originalNodeWithAllclones.get(j).getNodeId());
 			for(int x=0;x<getChildNodes.size();x++){
 				getNodeHistoryForSection=docMgmtImpl.getNodeHistoryForSectinDeletion(workspaceID, getChildNodes.get(x).getNodeId());
 				if(getNodeHistoryForSection.size()>0){
 					if(!getNodeHistoryForSection.get(0).getFileName().equalsIgnoreCase("No File") && !getNodeHistoryForSection.get(0).getFileName().equalsIgnoreCase("")){
 						//docMgmtImpl.deleteNodeDetail(workspaceID, nodeIdForSectionDelete,remark);
 						dto1.setRequiredFlag('E');
 					}
 					/*else{
 						dto1.setRequiredFlag('D');
 						//docMgmtImpl.deleteNodeDetail(workspaceID, nodeIdForSectionDelete,remark);
 					}*/
 				}
 				
 			}
 			
 			
 			if(j==0){
 				dto1.setSortOrder("LastElmt");
 			}
 			dto1.setFolderName(originalNodeWithAllclones.get(j).getFolderName());
 			wsNodeDtlToShow.add(dto1);
			//docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeDisplayName();
			wsNodeDtlForParent=docMgmtImpl.getChildNodeByParent(originalNodeWithAllclones.get(j).getNodeId(), workspaceID);
			
			String attr1="";
    		if(tableHeader.size()<=0){
    		for(int k=0;k<WsNodeAttrDetail.size();k++){
    			if(Automated_TM_Required.equalsIgnoreCase("Yes")){
	    				if(WsNodeAttrDetail.get(k).getAttrName().equalsIgnoreCase("URS Reference Number") || 
	    						WsNodeAttrDetail.get(k).getAttrName().equalsIgnoreCase("FS Reference Number")){
	    					continue;
	    				}else{
	    					tableHeader.add(WsNodeAttrDetail.get(k).getAttrName());
	    	    			DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
	    	    			attr1= WsNodeAttrDetail.get(k).getAttrName();
	    	    			//System.out.println(attr1);
	    	    			dto.setAttrName(attr1);
	    	    			getProjectLevelSearchResult.add(dto);
	    				}
	    			}
	    			else{
		    			tableHeader.add(WsNodeAttrDetail.get(k).getAttrName());
		    			DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
		    			attr1= WsNodeAttrDetail.get(k).getAttrName();
		    			//System.out.println(attr1);
		    			dto.setAttrName(attr1);
		    			getProjectLevelSearchResult.add(dto);
		    			//System.out.println(attr1);
	    			}
    			}//loop
    		} //size
    		if(Automated_TM_Required.equalsIgnoreCase("Yes")){
    			for(int i =0;i<tableHeader.size();i++){
    				if(tableHeader.get(i).equalsIgnoreCase("URS Reference Number") || tableHeader.get(i).equalsIgnoreCase("FS Reference Number")){
    					tableHeader.remove(i);
    					i--;
    				}
    			}
    			getAttributeDetails=docMgmtImpl.getAttributeDetailsForSection(workspaceID, originalNodeWithAllclones.get(j).getNodeId(),tableHeader);
    			
    		}
    		else
    			getAttributeDetails=docMgmtImpl.getAttributeDetailsForSection(workspaceID, originalNodeWithAllclones.get(j).getNodeId(),tableHeader);
			//map.put(j, getAttributeDetails);
			
			for(int k =0;k < wsNodeDtlForParent.size();k++){
				Vector<DTOWorkSpaceNodeHistory> getNodeHistory;
				DTOWorkSpaceNodeDetail dto=new DTOWorkSpaceNodeDetail();
				dto.setNodeId(wsNodeDtlForParent.get(k).getNodeId());
				dto.setParentNodeId(wsNodeDtlForParent.get(k).getParentNodeId());
				dto.setNodeDisplayName(wsNodeDtlForParent.get(k).getNodeDisplayName());
				dto.setFolderName(wsNodeDtlForParent.get(k).getFolderName());
				getNodeHistory=docMgmtImpl.getNodeHistoryForSectinDeletion(workspaceID, wsNodeDtlForParent.get(k).getNodeId());
				if(getNodeHistory.size()>0){
					if(!getNodeHistory.get(0).getFileName().equalsIgnoreCase("No File") && !getNodeHistory.get(0).getFileName().equalsIgnoreCase("")){
						dto.setUploadFlag("Yes");
						dto.setRequiredFlag('N');
						
					}else{
						dto.setRequiredFlag('B');
					}
				}
				
				String attrVal=wsNodeDtlForParent.get(k).getNodeDisplayName();
				if(attrVal.contains("{")){
					/*attrVal=attrVal.substring(attrVal.lastIndexOf("{") + 1);
					attrVal= attrVal.substring(0, attrVal.length() - 1);*/
					int startingIndex = attrVal.indexOf("{");
					int closingIndex = attrVal.indexOf("}");
					result1 = attrVal.substring(startingIndex , closingIndex+1);
					attrVal=result1.substring(1);
					attrVal= attrVal.substring(0, attrVal.length() - 1);
					dto.setAttrValue(attrVal);
				}
				                                                                                                             
				dto.setCloneFlag(wsNodeDtlForParent.get(k).getCloneFlag());
				//if(k==wsNodeDtlForParent.size()-1){
				if(wsNodeDtlForParent.get(k).getRequiredFlag()=='F'){
					dto.setSortOrder("true");
					dto.setParentNodeId(wsNodeDtlForParent.get(k).getParentNodeId());
				}
				if(nDisplayName!=null){
					for(int x=0;x<getAttributeDetails.size();x++){
						/*if(x==0)
							AttrDetilas.add(String.valueOf(wsNodeDtlForParent.get(k).getParentNodeId()));*/
						//AttrDetilas.add(getAttributeDetails.get(x));
						if(Automated_TM_Required.equalsIgnoreCase("Yes")){
							if(k==0){
								String ursFsNo = "";
								for(int i=0;i<getAttributeDetails.get(0).size();i++){
									String sample = getAttributeDetails.get(0).get(i);
								      char[] chars = sample.toCharArray();
								      StringBuilder sb = new StringBuilder();
								      for (int i1 = 0; i1 < sample.length(); i1++) {
								            // Check if character is not a digit between 0-9 then return false
								            if (sample.charAt(i1) < '0' || sample.charAt(i1) > '9') {
								            	if(sample.charAt(i1)!=',')
								            	sb.append(sample.charAt(i1));break;
								            }
								        }
								      if(sb.length()<=0){
										ursFsNo = docMgmtImpl.getTracebelityMatrixDtlForAttributes(workspaceID,getAttributeDetails.get(0).get(i));
										if(!ursFsNo.contains("null")){
											if(!ursFsNo.contains("null") && ursFsNo.contains(",")){
													List<String> s=getAttributeDetails.get(0);
													s.set(i, getAttributeDetails.get(0).get(i).replace(getAttributeDetails.get(0).get(i), ursFsNo.subSequence(0, ursFsNo.length()-1)));
													System.out.println(getAttributeDetails.get(0));
												}
											else{
													if(!ursFsNo.contains("")){
														ursFsNo=ursFsNo.split(",")[1];
														List<String> s=getAttributeDetails.get(0);
														s.set(i, getAttributeDetails.get(0).get(i).replace(getAttributeDetails.get(0).get(i), ursFsNo));
														System.out.println(getAttributeDetails.get(0));
													}
												}
											}
								      }
								}
								mapForSection.put(wsNodeDtlForParent.get(k).getParentNodeId(), getAttributeDetails);
								mapForSection.keySet().toArray()[0].toString().equals(String.valueOf(nodeId));
							}
						}
						else{
							mapForSection.put(wsNodeDtlForParent.get(k).getParentNodeId(), getAttributeDetails);
						mapForSection.keySet().toArray()[0].toString().equals(String.valueOf(nodeId));
						}
					}
					
					//dto.setAttrValue1(nDisplayName[0]);
					//dto.setAttrValue2(nDisplayName[1]);
				}
				if(k==0)
					dto.setPublishFlag(true);
					wsNodeDtlToShow.add(dto);
				
			}	
 
		}
         // For sorting purpose, copy all data from hashMap into TreeMap
         sortedMap.putAll(mapForSection);
 		 getAttributeDetails=docMgmtImpl.getAttributeDetailsForSection(workspaceID, nodeId,tableHeader);
		 getAttributeDetailsForDisplay.addAll(getAttributeDetails);
		 
		 

			
		 
		 
		return SUCCESS;
	}

	
	
	
	

	/*public String getSectionAttribute(){
		htmlContent="";
		
		int usergrpcode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		int usercode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("userid").toString());
		String userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		
		WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>(); 	
   		WsNodeDetail=docMgmtImpl.isLeafParentForAttribute(workspaceID,nodeId,usercode,usergrpcode);
   		
   		DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetail(workspaceID);
   		ProjectName=wsDetail.getWorkSpaceDesc();
   		NodeName=docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeDisplayName();
   		
   		getAttributeDetails=new ArrayList<List<String>>();
   		getProjectLevelSearchResult=new Vector<>();
   		WsNodeAttrDetail=new Vector<>();
   		getAttributeDetailsForDisplay=new ArrayList<List<String>>();
   		
   		List<String> tableHeader = new ArrayList<String>();
   		if(WsNodeDetail.size()>0){
    		for(int j=0;j<WsNodeDetail.size();j++){
    			
    			WsNodeAttrDetail=docMgmtImpl.getNodeAttrDetailForCSV(workspaceID, WsNodeDetail.get(j).getNodeId());
	    		
        		String attr1="";
        		if(tableHeader.size()<=0){
        		for(int k=0;k<WsNodeAttrDetail.size();k++){
        			tableHeader.add(WsNodeAttrDetail.get(k).getAttrName());
        			DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
        			attr1= WsNodeAttrDetail.get(k).getAttrName();
        			//System.out.println(attr1);
        			dto.setAttrName(attr1);
        			getProjectLevelSearchResult.add(dto);
        			//System.out.println(attr1);
        			}
        		}	
    			
        			
			 getAttributeDetails=docMgmtImpl.getAttributeDetailsForCSV(workspaceID, WsNodeDetail.get(j).getNodeId(),tableHeader);
			 getAttributeDetailsForDisplay.addAll(getAttributeDetails);
    		}
    	}
		return SUCCESS;
	}*/
	public String uploadSourceDoc() {

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String sourceDocPath = propertyInfo.getValue("SOURCE_DOC_FOLDER");
		String tempDir = propertyInfo.getValue("BASE_TEMP_FOLDER");

		if (uploadDoc == null) {
			return SUCCESS;
		}

		// Check whether the last uploaded doc is being modified by some one
		// else
		// if yes then user cannot upload new doc.
		boolean deleted = true;
		ArrayList<DTOWorkspaceNodeDocHistory> docHis = docMgmtImpl
				.getLatestNodeDocHistory(OfficeWsId, OfficeNodeId);
		if (docHis.size() > 0) {
			String destFileName = "src_" + docHis.get(0).getDocName();

			File destFile = new File(tempDir + "/" + OfficeWsId + "/" + OfficeNodeId
					+ "/" + destFileName);

			if (destFile.exists()) {
				deleted = destFile.delete();
			}
		}

		// if file is deleted from temp or there is no file then allow user to
		// upload new doc.
		if (deleted) {
			userName = ActionContext.getContext().getSession().get("username")
					.toString();
			DTOWorkSpaceNodeDetail wsnd = docMgmtImpl.getNodeDetail(OfficeWsId, OfficeNodeId).get(0);
			//String temp=wsnd.getFolderName();
			String temp=uploadDocFileName;
			//String srcFileExt = uploadDocFileName.substring(uploadDocFileName.lastIndexOf(".") + 1);
			//String srcFileExt = wsnd.getFolderName().substring(wsnd.getFolderName().lastIndexOf(".") + 1);
			String srcFileExt = "docx";
			String newFileName=temp.substring(0, temp.lastIndexOf('.'));
			System.out.println(temp);
			System.out.println(srcFileExt);
			System.out.println(newFileName);
			// Get new docTranNo and docPath
			int docTranNo = docMgmtImpl.getNewDocTranNo(OfficeWsId);
			String docPath =  "/"+year +"/"+month+"/"+officeDate+"/"+ OfficeWsId + "/" + OfficeNodeId + "/" + docTranNo;
			ArrayList<DTOWorkspaceNodeDocHistory> WsNodeDocHistoryList = new ArrayList<DTOWorkspaceNodeDocHistory>();
			DTOWorkspaceNodeDocHistory dto = new DTOWorkspaceNodeDocHistory();
			dto.setWorkspaceId(OfficeWsId);
			dto.setNodeId(OfficeNodeId);
			dto.setDocTranNo(docTranNo);
			dto.setDocName(newFileName+"_"+OfficeWsId+"_"+OfficeNodeId+"."+srcFileExt);
			//dto.setDocName(wsnd.getFolderName());
			//dto.setDocName(newFileName+"."+srcFileExt);
			dto.setDocContentType(uploadDocContentType);
			dto.setDocPath(docPath);
			dto.setUploadedBy(userCodeForOffice);
			dto.setRemark(uploadDocFileName + " has been uploaded by "+ userName);
			dto.setModifyBy(userCodeForOffice);
			// To copy file in master function, set the below two variables.
			dto.setSrcDoc(uploadDoc);
			dto.setBaseSrcDir(sourceDocPath);
			dto.setFileSize(fileSizeInMB);

			WsNodeDocHistoryList.add(dto);
			docMgmtImpl.insertNodeDocHistory(WsNodeDocHistoryList, true);
			/*
			 * File sourceLocation = uploadDoc; File targetLocation = new
			 * File(sourceDocPath+docPath+"/"+uploadDocFileName); FileManager
			 * fileManager = new FileManager();
			 * fileManager.copyDirectory(sourceLocation, targetLocation);
			 */

		} else {
			addActionMessage("Cannot upload new document as it is being modified by some one else.");
			return "conflict";
		}

		return SUCCESS;
	}

public String CreateBlankFileInSharePoint() throws IOException, InterruptedException{
		
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String tempdir = propertyInfo.getValue("BASE_TEMP_FOLDER");
		UplodFileMail = propertyInfo.getValue("UplodFileMail");
		autoMail = knetProperties.getValue("AutoMail");
		
		Date toDaydate= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(toDaydate);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH)+1;
		officeDate = cal.get(Calendar.DAY_OF_MONTH);
		
		int tranNo = docMgmtImpl.getNewDocTranNo(OfficeWsId);
		File dirpath = new File(tempdir+ "/"+year +"/"+month+"/"+officeDate+"/"+ OfficeWsId + "/" + OfficeNodeId+ "/" + tranNo);
		if (!dirpath.exists() && !dirpath.mkdirs()) {
			addActionMessage("docUpload");
			return INPUT;
	    }
		//end of Checking Server access for file uploading
		DTOWorkSpaceNodeDetail wsndtl = docMgmtImpl.getNodeDetail(OfficeWsId, OfficeNodeId).get(0);
		String fPath= wsndtl.getDefaultFileFormat();
		if(fPath != null && !fPath.isEmpty()){
		File templateFile=new File(fPath);
		if(templateFile.exists() && !templateFile.toString().equalsIgnoreCase("null")){
			path=wsndtl.getDefaultFileFormat().replace("\\", "/");
			uploadDocFileName =path.substring(path.lastIndexOf("/") + 1);
		}
		else{
			uploadDocFileName="BlankDoc.docx";
			path=propertyInfo.getValue("BlankSrcDoc");
			path=path+"//"+uploadDocFileName.replace("\\", "/");
		}
		}
		else{
			uploadDocFileName="BlankDoc.docx";
			path=propertyInfo.getValue("BlankSrcDoc");
			path=path+"//"+uploadDocFileName.replace("\\", "/");
		}
		uploadDoc=new File(path);
		if(!uploadDoc.exists() || uploadDoc.toString().equalsIgnoreCase("null")){
			htmlContent="File Not Found"+ ","+path;
			return "html";
		}
		f=new File(path);
		fileSizeProperty = propertyInfo.getValue("FileUploadingSize");
		fileUploloadingSize = Integer.parseInt(fileSizeProperty);
		
		double fileSizeInBytes = f.length();
		
		double fileSizeInKB = fileSizeInBytes / 1024;

         fileSizeInMB = (fileSizeInKB / 1024);
         DecimalFormat df2 = new DecimalFormat("#.##");
         df2.setRoundingMode(RoundingMode.UP);
         System.out.println("String : " + df2.format(fileSizeInMB)); 
         fileSizeInMB= Double.parseDouble(df2.format(fileSizeInMB));  
         
        
      	if (fileSizeInMB > fileUploloadingSize) {
      		htmlContent="You can't upload file size greate than "+fileUploloadingSize+" MB.";
    		return "html";
		}
      	uploadDocFileName = uploadDocFileName.replace(' ', '_');
		uploadSourceDoc();
		
		int exitVal=0;
		String Office365OnlineDocPath;
		//Office365OnlineDocPath = propertyInfo.getValue("officedoconline")+"//Office365OnlineDoc.exe";
		Office365OnlineDocPath=propertyInfo.getValue("officedoconline")+"Office365OnlineDoc.exe";
		Office365OnlineDocPath.replace("\\", "/");
		
		nodeDocHistory = docMgmtImpl.getLatestNodeDocHistory(OfficeWsId, OfficeNodeId);
		srcDocPath = propertyInfo.getValue("SOURCE_DOC_FOLDER");
		
		Vector<DTOWorkSpaceNodeDetail> wsNodeDtl = docMgmtImpl.getNodeDetail(OfficeWsId, OfficeNodeId);
		String temp=wsNodeDtl.get(0).getFolderName();
		if(temp.contains(" "))
			temp=temp.replace(' ', '_');
		srcFileExt = "docx";
		//if(singleDocFlag!=null && !singleDocFlag.equalsIgnoreCase("true")){
		if(singleDocFlag!=null && !singleDocFlag.equalsIgnoreCase("true")){
			newFileName=temp.substring(0, temp.lastIndexOf('.'));
			newFileName=newFileName+"_"+OfficeWsId+"_"+OfficeNodeId+"."+srcFileExt;
			}
		else{
		newFileName=temp;
		newFileName=newFileName+"_"+OfficeWsId+"_"+OfficeNodeId+"."+srcFileExt;}
		
		File fName=new File(srcDocPath+"/"+nodeDocHistory.get(0).getDocPath()+"/"+nodeDocHistory.get(0).getDocName());
		File renameName=new File(srcDocPath+"/"+nodeDocHistory.get(0).getDocPath()+"/"+newFileName);
		
		fName.renameTo(renameName);
		
		
		path=srcDocPath +"/"+nodeDocHistory.get(0).getDocPath()+"/"+newFileName;
				
		String S1= "Upload";
		//String S2= "//90.0.0.15/KnowledgeNET-CSV/srcDoc/0104/89/2/";
		//90.0.0.15/KnowledgeNET-CSV/srcDoc/0104/44/1
	    //String S3 = "SSPL-SW-FR-07-DS_0104_89.docx";
	    System.out.println("Path is : "+path);
	    String S2 =  path.substring(0,path.lastIndexOf("/")+1);
	    String S3 =  path.substring(path.lastIndexOf("/")+1,path.length());
	   
	    String command = Office365OnlineDocPath + " " + S1 + " "+ S2 + " "+ S3+ " ";
	    System.out.println("Command : "+command);
		try {
			Runtime rt = Runtime.getRuntime();
			Process pCD = rt.exec(command);
			InputStream stderr = pCD.getErrorStream();
			InputStreamReader isr = new InputStreamReader(pCD.getInputStream());
			String line = null;

			BufferedReader input = new BufferedReader(new InputStreamReader(pCD
					.getInputStream()));
			while ((line = input.readLine()) != null)
				System.out.println(line);

			exitVal = pCD.waitFor();
			int len;
			if ((len = pCD.getErrorStream().available()) > 0) {
				byte[] buf = new byte[len];
				pCD.getErrorStream().read(buf);
				System.err.println("Command error:\t\"" + new String(buf)
						+ "\"");
			}

			stderr.close();
			isr.close();
			System.gc();
			
		    
			System.out.println("Doc to Pdf Process exitValue: " + exitVal);	
			if(exitVal>0){
				String baseSrcPath = propertyInfo.getValue("OfficeTempfilePath");
				UUID uuid=UUID.randomUUID();
				File wsFolder = new File(baseSrcPath + "/"+year +"/"+month+"/"+officeDate+"/" + OfficeWsId);
				wsFolder.mkdirs();

				File nodeFolder = new File(baseSrcPath + "/"+year +"/"+month+"/"+officeDate+"/" + OfficeWsId + "/"	+ OfficeNodeId);
				nodeFolder.mkdirs();
				
				File userFolder = new File(baseSrcPath + "/"+year +"/"+month+"/"+officeDate+"/" + OfficeWsId + "/"	+ OfficeNodeId + "/" + userCodeForOffice);
				userFolder.mkdirs();

				//if (lastTranNo != -1) {
					File tranFolder = new File(baseSrcPath +  "/"+year +"/"+month+"/"+officeDate+"/" + OfficeWsId + "/"
							+ OfficeNodeId + "/" + userCodeForOffice + "/" + uuid);
					tranFolder.mkdirs();
				//}
				
				DTOWorkSpaceNodeHistory wsnd =new DTOWorkSpaceNodeHistory();
				wsnd.setTranNo(1);
				wsnd.setWorkSpaceId(OfficeWsId);
				wsnd.setNodeId(OfficeNodeId);
				wsnd.setFileName(S3);
				wsnd.setFileType(S3.substring(S3.lastIndexOf(".") + 1));
				wsnd.setFilePath(tranFolder.getPath());
				wsnd.setClsUpload('Y');
				wsnd.setClsDownload('N');
				wsnd.setModifyBy(OfficeUserCode);
				wsnd.setFileSize(fileSizeInMB);
				docMgmtImpl.insertworkspacenodeofficehistory(wsnd, 1); 
				path=srcDocPath +"/"+nodeDocHistory.get(0).getDocPath()+"/"+newFileName;
			}
			
		} catch (Exception e) {
			System.out.println("Error...");
			e.printStackTrace();
			// TODO: handle exception
		}
		
		/*if(UplodFileMail.equalsIgnoreCase("Yes") && autoMail.equalsIgnoreCase("Yes")){
			  StageWiseMailReport stageWiseMail = new StageWiseMailReport();
			  //stageWiseMail.UploadFileMail(workspaceID,nodeId,usercode);
			  stageWiseMail.UploadFileMailNewFormateForSrcUpload(OfficeWsId,OfficeNodeId,OfficeUserCode);
			  System.out.println("Mail Sent....");
		    }*/
		
		if(exitVal>0)
			htmlContent="Document Uploaded Successfully. "+path;
		else
			htmlContent="false";
		return "html";
	}
	
	
	
	
	public String UploadFileInSharepoint() throws IOException, InterruptedException{
		
		int exitVal=0;
		String Office365OnlineDocPath;
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		//Office365OnlineDocPath = propertyInfo.getValue("officedoconline")+"//Office365OnlineDoc.exe";
		Office365OnlineDocPath=propertyInfo.getValue("officedoconline")+"Office365OnlineDoc.exe";
		Office365OnlineDocPath.replace("\\", "/");
		String S1= "Upload";
		//String S2= "//90.0.0.15/KnowledgeNET-CSV/srcDoc/0104/89/2/";
		//90.0.0.15/KnowledgeNET-CSV/srcDoc/0104/44/1
	    //String S3 = "SSPL-SW-FR-07-DS_0104_89.docx";
	    System.out.println("Path is : "+path);
	    String S2 =  path.substring(0,path.lastIndexOf("/")+1);
	    String S3 =  path.substring(path.lastIndexOf("/")+1,path.length());
	    S3 = S3.replace(' ', '_');
	   
	    String command = Office365OnlineDocPath + " " + S1 + " "+ S2 + " "+ S3+ " ";
	    System.out.println("Command : "+command);
		try {
			Runtime rt = Runtime.getRuntime();
			Process pCD = rt.exec(command);
			InputStream stderr = pCD.getErrorStream();
			InputStreamReader isr = new InputStreamReader(pCD.getInputStream());
			String line = null;

			BufferedReader input = new BufferedReader(new InputStreamReader(pCD
					.getInputStream()));
			while ((line = input.readLine()) != null)
				System.out.println(line);

			exitVal = pCD.waitFor();
			int len;
			if ((len = pCD.getErrorStream().available()) > 0) {
				byte[] buf = new byte[len];
				pCD.getErrorStream().read(buf);
				System.err.println("Command error:\t\"" + new String(buf)
						+ "\"");
			}

			stderr.close();
			isr.close();
			System.gc();
			
		    
			System.out.println("Doc to Pdf Process exitValue: " + exitVal);	
			if(exitVal>0){

				Date toDaydate= new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(toDaydate);
				year = cal.get(Calendar.YEAR);
				month = cal.get(Calendar.MONTH)+1;
				officeDate = cal.get(Calendar.DAY_OF_MONTH);
				
				String baseSrcPath = propertyInfo.getValue("OfficeTempfilePath");
				UUID uuid=UUID.randomUUID();
				File wsFolder = new File(baseSrcPath + "/"+year +"/"+month+"/"+officeDate+"/" + OfficeWsId);
				wsFolder.mkdirs();

				File nodeFolder = new File(baseSrcPath + "/"+year +"/"+month+"/"+officeDate+"/" + OfficeWsId + "/"	+ OfficeNodeId);
				nodeFolder.mkdirs();
				
				File userFolder = new File(baseSrcPath + "/"+year +"/"+month+"/"+officeDate+"/" + OfficeWsId + "/"	+ OfficeNodeId + "/" + userCodeForOffice);
				userFolder.mkdirs();

				//if (lastTranNo != -1) {
				File tranFolder = new File(baseSrcPath +  "/"+year +"/"+month+"/"+officeDate+"/" + OfficeWsId + "/"
						+ OfficeNodeId + "/" + userCodeForOffice + "/" + uuid);
					tranFolder.mkdirs();
				//}
				
					f=new File(path);
					fileSizeProperty = propertyInfo.getValue("FileUploadingSize");
					fileUploloadingSize = Integer.parseInt(fileSizeProperty);
					
					double fileSizeInBytes = f.length();
					
					double fileSizeInKB = fileSizeInBytes / 1024;

			         fileSizeInMB = (fileSizeInKB / 1024);
			         DecimalFormat df2 = new DecimalFormat("#.##");
			         df2.setRoundingMode(RoundingMode.UP);
			         System.out.println("String : " + df2.format(fileSizeInMB)); 
			         fileSizeInMB= Double.parseDouble(df2.format(fileSizeInMB));  
			         
			        
			      	if (fileSizeInMB > fileUploloadingSize) {
			      		htmlContent="You can't upload file size greate than "+fileUploloadingSize+" MB.";
			    		return "html";
					}
					
					
					
				DTOWorkSpaceNodeHistory wsnd =new DTOWorkSpaceNodeHistory();
				wsnd.setTranNo(1);
				wsnd.setWorkSpaceId(OfficeWsId);
				wsnd.setNodeId(OfficeNodeId);
				wsnd.setFileName(S3);
				wsnd.setFileType(S3.substring(S3.lastIndexOf(".") + 1));
				wsnd.setFilePath(tranFolder.getPath());
				wsnd.setClsUpload('Y');
				wsnd.setClsDownload('N');
				wsnd.setModifyBy(OfficeUserCode);
				wsnd.setFileSize(fileSizeInMB);
				docMgmtImpl.insertworkspacenodeofficehistory(wsnd, 1);// edit mode
				

				//inserting data into sourceDocumentReminder table
				//Vector<DTOWorkSpaceUserRightsMst> WorkspaceUserDetailList;
				int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
				//WorkspaceUserDetailList=docMgmtImpl.getUserRightsDetailForsrcDocRmd(OfficeWsId, OfficeNodeId,userCode);
				
				//for(int i=0;i<WorkspaceUserDetailList.size();i++){
					
					wsnd.setWorkSpaceId(OfficeWsId);
					wsnd.setNodeId(OfficeNodeId);
					wsnd.setUserCode(userCode);
					wsnd.setFileName(S3);
					wsnd.setFilePath(tranFolder.getPath());
					wsnd.setRemark("test");
					wsnd.setModifyBy(userCode);
					wsnd.setStatusIndi('E');
					
					docMgmtImpl.insertsrcDocReminder(wsnd, 2);
			}
			
		} catch (Exception e) {
			System.out.println("Error...");
			e.printStackTrace();
			// TODO: handle exception
		}
		
		if(exitVal>0)
			htmlContent="true";
		else
			htmlContent="false";
		return "html";
	}
	
	
public String DownloadFileFromSharePoint() throws IOException, InterruptedException{
		
		int exitVal=0;
		String Office365OnlineDocPath;
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		autoMail = propertyInfo.getValue("AutoMail");
		//Office365OnlineDocPath = propertyInfo.getValue("officedoconline")+"/Office365OnlineDoc.exe";
		Office365OnlineDocPath=propertyInfo.getValue("officedoconline")+"Office365OnlineDoc.exe";
		Office365OnlineDocPath.replace("\\", "/");
		String S1= "Download";
		//String S2= "//90.0.0.15/KnowledgeNET-CSV/srcDoc/0104/89/2/";
		//90.0.0.15/KnowledgeNET-CSV/srcDoc/0104/44/1
	    //String S3 = "SSPL-SW-FR-07-DS_0104_89.docx";
	    System.out.println("Path is : "+path);
	    String S2 =  path.substring(0,path.lastIndexOf("/")+1);
	    String S3 =  path.substring(path.lastIndexOf("/")+1,path.length());
	    File tranFolder = null;
	    String command = Office365OnlineDocPath + " " + S1 + " "+ S2 + " "+ S3+ " ";
	    System.out.println("Command : "+command);
		try {
			Runtime rt = Runtime.getRuntime();
			Process pCD = rt.exec(command);
			InputStream stderr = pCD.getErrorStream();
			InputStreamReader isr = new InputStreamReader(pCD.getInputStream());
			String line = null;

			BufferedReader input = new BufferedReader(new InputStreamReader(pCD
					.getInputStream()));
			while ((line = input.readLine()) != null)
				System.out.println(line);

			exitVal = pCD.waitFor();
			int len;
			if ((len = pCD.getErrorStream().available()) > 0) {
				byte[] buf = new byte[len];
				pCD.getErrorStream().read(buf);
				System.err.println("Command error:\t\"" + new String(buf)
						+ "\"");
			}

			stderr.close();
			isr.close();
			System.gc();
			
		    
			System.out.println("Doc to Pdf Process exitValue: " + exitVal);	
			if(exitVal>0){
				wSOfficeHistoryForFlagCheck=new Vector<DTOWorkSpaceNodeHistory>();
				wSOfficeHistoryForFlagCheck=docMgmtImpl.getWorkspaceNodeHistoryForOfficeForFlagCheck(OfficeWsId, OfficeNodeId,usercode);
				/*String baseSrcPath = propertyInfo.getValue("OfficeTempfilePath");
				UUID uuid=UUID.randomUUID();
				File wsFolder = new File(baseSrcPath + "/" + OfficeWsId);
				if(!wsFolder.exists())
					wsFolder.mkdirs();

				File nodeFolder = new File(baseSrcPath + "/" + OfficeWsId + "/"	+ OfficeNodeId);
				if(!nodeFolder.exists())
					nodeFolder.mkdirs();

				//if (lastTranNo != -1) {
				tranFolder = new File(baseSrcPath + "/" + OfficeWsId + "/"	+ OfficeNodeId + "/" + uuid);
					if(!tranFolder.exists())
						tranFolder.mkdirs();
				//}
*/				
				File f=new File(path.toString());
				File destFileCopy=new File(wSOfficeHistoryForFlagCheck.get(0).getFilePath()+ "/" + S3);
				Files.copy(f.toPath(), destFileCopy.toPath());
				/*new Thread(new Runnable() {
					public void run() {

						try {
							Files.copy(f.toPath(), destFileCopy.toPath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}).start();
				*/
				f=new File(path);
				fileSizeProperty = propertyInfo.getValue("FileUploadingSize");
				fileUploloadingSize = Integer.parseInt(fileSizeProperty);
				
				double fileSizeInBytes = f.length();
				
				double fileSizeInKB = fileSizeInBytes / 1024;

		         fileSizeInMB = (fileSizeInKB / 1024);
		         DecimalFormat df2 = new DecimalFormat("#.##");
		         df2.setRoundingMode(RoundingMode.UP);
		         System.out.println("String : " + df2.format(fileSizeInMB)); 
		         fileSizeInMB= Double.parseDouble(df2.format(fileSizeInMB));  
		         
		        
		      	if (fileSizeInMB > fileUploloadingSize) {
		      		htmlContent="You can't upload file size greate than "+fileUploloadingSize+" MB.";
		    		return "html";
				}
				DTOWorkSpaceNodeHistory wsnd =new DTOWorkSpaceNodeHistory();
				//wsnd.setTranNo(1);
				wsnd.setWorkSpaceId(OfficeWsId);
				wsnd.setNodeId(OfficeNodeId);
				wsnd.setFileName(S3);
				wsnd.setFileType(S3.substring(S3.lastIndexOf(".") + 1));
				wsnd.setFilePath(wSOfficeHistoryForFlagCheck.get(0).getFilePath());
				//wsnd.setClsUpload('Y');
				wsnd.setClsDownload('Y');
				wsnd.setModifyBy(OfficeUserCode);
				wsnd.setFileSize(fileSizeInMB);
				docMgmtImpl.insertworkspacenodeofficehistory(wsnd, 2);// edit mode
				
				nodeDocHistory = docMgmtImpl.getLatestNodeDocHistory(OfficeWsId, OfficeNodeId);
				ArrayList<DTOWorkspaceNodeDocHistory> WsNodeDocHistoryList = new ArrayList<DTOWorkspaceNodeDocHistory>();
				DTOWorkspaceNodeDocHistory dto = new DTOWorkspaceNodeDocHistory();
				dto.setWorkspaceId(OfficeWsId);
				dto.setNodeId(OfficeNodeId);
				dto.setDocTranNo(nodeDocHistory.get(0).getDocTranNo());
				dto.setDocName("");
				dto.setDocContentType(uploadDocContentType);
				dto.setDocPath("");
				dto.setUploadedBy(OfficeUserCode);
				dto.setRemark("");
				dto.setModifyBy(OfficeUserCode);
				// To copy file in master function, set the below two variables.
				dto.setSrcDoc(null);
				dto.setBaseSrcDir("");
				
				
				
				dto.setFileSize(fileSizeInMB);
				WsNodeDocHistoryList.add(dto);
				docMgmtImpl.insertNodeDocHistoryForSaveAndSendUpload(WsNodeDocHistoryList, 3);
				//inserting data into sourceDocumentReminder table
				Vector<DTOWorkSpaceUserRightsMst> WorkspaceUserDetailList;
				int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
				WorkspaceUserDetailList=docMgmtImpl.getUserRightsDetailForsrcDocRmd(OfficeWsId, OfficeNodeId,userCode);
				
				for(int i=0;i<WorkspaceUserDetailList.size();i++){
					
					wsnd.setWorkSpaceId(WorkspaceUserDetailList.get(i).getWorkSpaceId());
					wsnd.setNodeId(WorkspaceUserDetailList.get(i).getNodeId());
					wsnd.setUserCode(WorkspaceUserDetailList.get(i).getUserCode());
					wsnd.setFileName(S3);
					wsnd.setFilePath(wSOfficeHistoryForFlagCheck.get(0).getFilePath());
					wsnd.setRemark("test");
					wsnd.setModifyBy(userCode);
					wsnd.setStatusIndi('N');
					
					docMgmtImpl.insertsrcDocReminder(wsnd, 1);
				}
				if(autoMail.equalsIgnoreCase("Yes")){
					 StageWiseMailReport stageWiseMail = new StageWiseMailReport();
					 stageWiseMail.UploadFileMailFromSrcDocument(OfficeWsId,OfficeNodeId,OfficeUserCode);
					  System.out.println("Mail Sent....");
				    }
			}
			
		} catch (Exception e) {
			//System.out.println("Error...");
			htmlContent="Fail to Download.";
			return "html";
			// TODO: handle exception
		}
		
		//String paths=tranFolder.getPath();
		if(exitVal>0)
			htmlContent="Document saved successfully.";
		else
			htmlContent="Fail to download.";
		return "html";
	}

public String checkfileopenforsig(){
	
	DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
	int uCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	dto = docMgmtImpl.getNodeHistoryForSignOff(workspaceID,nodeId);
	dto.setModifyBy(uCode);
	int checkFileOpen = docMgmtImpl.getfileopenforsignHistoryForSignOff(dto);				
	if(checkFileOpen==0){
		htmlContent="false";
	}
	else{
		htmlContent="true";
	}
	return "html";
}

public String checkReviewFile() throws IOException, InterruptedException{
	wSOfficeHistoryForFlagCheck=docMgmtImpl.getWorkspaceNodeHistoryForOfficeForFlagCheck(OfficeWsId, OfficeNodeId,OfficeUserCode);
	
	if(wSOfficeHistoryForFlagCheck.size()>0){
		if(wSOfficeHistoryForFlagCheck.get(0).getClsDownload()!='Y' /*&& wSOfficeHistoryForFlagCheck.get(0).getModifyBy()!=usercode*/){
			/*saveAndSendFlagForOffice=true;
			saveAndSenduCodeForOffice=wSOfficeHistoryForFlagCheck.get(0).getModifyBy();*/
			reviewDoc="true"+":"+wSOfficeHistoryForFlagCheck.get(0).getUserName();
		}
	}
	htmlContent=reviewDoc;
	return "html";	
}

public String checkValidateApproval() throws SQLException{
	
	
	int tranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
    ArrayList<DTOWorkSpaceNodeHistory> geNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
	geNodeHistory = docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId, tranNo);
	String fldrName= geNodeHistory.get(0).getFolderName();
	Vector<DTOWorkSpaceNodeDetail> PQSTableHeaderMst = new Vector<DTOWorkSpaceNodeDetail>();
	PQSTableHeaderMst = docMgmtImpl.getPQSTableHeaderMst(workspaceID,nodeId,clientCode,fldrName);
    if(PQSTableHeaderMst.size()>0){
		htmlContent="true";
	}
	else{
		htmlContent="false";
	}
	return "html";
}
public String RepeatFailedScript() throws ClassNotFoundException{
	
	Vector<DTOWorkSpaceNodeDetail> wsNodeDtl = docMgmtImpl.getNodeDetail(workspaceID, nodeId);
	usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	int startIndex=1;
	
	int parentNodeId = wsNodeDtl.get(0).getParentNodeId();
	DTOWorkSpaceNodeDetail dto =new DTOWorkSpaceNodeDetail();
	dto = wsNodeDtl.get(0);
	
	int nodeNo  = docMgmtImpl.getFailedScriptData(workspaceID,parentNodeId,"TC");
	DTOWorkSpaceNodeDetail sourceNodeDtl1 =new DTOWorkSpaceNodeDetail();
	if (nodeNo != 0){
		DTOWorkSpaceNodeDetail getFailedScriptDetail =new DTOWorkSpaceNodeDetail();
		
		getFailedScriptDetail = docMgmtImpl.getFailedScriptNodeDetail(workspaceID,parentNodeId,nodeNo+1);
		Vector<DTOWorkSpaceNodeDetail> FailScriptNodeCount = new Vector<DTOWorkSpaceNodeDetail>();
		
		if(!getFailedScriptDetail.getNodeName().equalsIgnoreCase("FailedScript")){
				
			FailScriptNodeCount = docMgmtImpl.getNodeDetailForNodeCountForFailedSctipt(workspaceID, parentNodeId,"FailedScript");
			
			if(FailScriptNodeCount.size()>0){
				sourceNodeDtl1 = docMgmtImpl.getNodeDetail(workspaceID, FailScriptNodeCount.lastElement().getNodeId()).get(0);
			}
			//else{
				
				//startIndex = Integer.parseInt(suffixStartForParent);
				int  maxSourceNodeDtl = FailScriptNodeCount.size();
				if(maxSourceNodeDtl==0){
					startIndex = maxSourceNodeDtl;
					startIndex=startIndex+1;
				}
				else{
					//startIndex = maxSourceNodeDtl;
					startIndex=maxSourceNodeDtl+1;
				}
				
				DTOWorkSpaceNodeDetail dto1 = new DTOWorkSpaceNodeDetail();
				dto1.setWorkspaceId(workspaceID);
				dto1.setNodeId(nodeId);
				dto1.setNodeName("FailedScript");
				dto1.setNodeDisplayName("FailedScript-"+startIndex);
				dto1.setFolderName("FailedScript-"+startIndex+".pdf");
				dto1.setCloneFlag('Y');// Its a clone node
				dto1.setNodeTypeIndi('K');
				dto1.setRequiredFlag('F');
				dto1.setCheckOutBy(0);
				dto1.setPublishedFlag(dto.getPublishedFlag());
				dto1.setRemark(dto.getRemark());
				dto1.setModifyBy(usercode);
				
				docMgmtImpl.addChildNodeAfter(dto1);
			//}
		}
	}
	
	return SUCCESS;
	
}
public String getPreAppvalDetails() throws FileNotFoundException, IOException, ParseException, SQLException{
	
	
	//http://90.0.0.201:7979/pqsdetail?Wsid=0001&NodeId=12&
	//ClientId=0001&Path=E:\Mallika\Pycharm Projects 2.0\TableTestCase\Documents\Centrifuge Master.docx
	int usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
	usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	iscreatedRights = docMgmtImpl.iscreatedRights(workspaceID,nodeId, usercode, usergrpcode);
	PQPreApprovalPopup = knetProperties.getValue("PQPreApprovalPopup");
	//PreApprovalDocPath = "";
	PreApprovalURL = knetProperties.getValue("PreApprovalURL");
	StringBuilder result = new StringBuilder();
	//Path="E:/Mallika/PQS/TestFile/BizNET_LTR_EDC-SW-FR-24-PQS-01-092019-Sample_Inventory-PQ001-Freezer_Copy.docx";
	//Path=PreApprovalDocPath;
	Path=Path.replace(" ", "_");
	URL url = new URL(PreApprovalURL+"?wsid="+workspaceID+"&nodeid="+nodeId+"&clientid="+clientCode+"&path="+Path);
	getClientDetail = new  ArrayList<DTOWorkSpaceNodeDetail>();
	ArrayList<String> PQHeaders = new ArrayList<String>();
	PQHeaders = docMgmtImpl.getPQSHeader(clientCode);
	for(int x=0;x<PQHeaders.size();x++){
		 DTOWorkSpaceNodeDetail dto1 = new DTOWorkSpaceNodeDetail();
		 dto1.setStepNo(PQHeaders.get(x));
		 getClientDetail.add(dto1);
	}
	//getClientDetail =  docMgmtImpl.getPQSHeader(clientCode);
   
    int tranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
    ArrayList<DTOWorkSpaceNodeHistory> geNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
	geNodeHistory = docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId, tranNo);
	Vector<DTOWorkSpaceNodeDetail> PQSTableHeaderMst = null;
	showPQSTableHeaderMst=null;
	showPQSTableHeaderMst = new Vector<DTOWorkSpaceNodeDetail>();
	if(geNodeHistory.size()>0){
	String fldrName= geNodeHistory.get(0).getFolderName();
	showValidateBtn = true;	 
	PQSTableHeaderMst = docMgmtImpl.getPQSTableHeaderMst(workspaceID,nodeId,clientCode,fldrName);
    if(PQSTableHeaderMst.size()>0){
    	IsConfirmBtn=false;
    	showPQSTableHeaderMst.addAll(PQSTableHeaderMst);
    	totalScript = PQSTableHeaderMst.size();
    	
    	for(int z=0; z<PQSTableHeaderMst.size();z++){
    		if(!PQSTableHeaderMst.get(z).getStepNo().isEmpty() && !PQSTableHeaderMst.get(z).getExpectedResult().isEmpty()){
    			successSection = successSection+1;
    		}
    		if(!PQSTableHeaderMst.get(z).getStepNo().isEmpty() && PQSTableHeaderMst.get(z).getExpectedResult().isEmpty()){
    			successScript = successScript+1;
    		}
    	}
    }
    else{
    	BufferedReader reader = null ;
    	//URL url = new URL("http://90.0.0.201:7999/pqsdetail?wsid="+workspaceID+"&nodeid="+nodeId+"&clientid="+clientCode);
    		try{
    			HttpURLConnection con = (HttpURLConnection) url.openConnection();

    			con.setRequestMethod("GET");

    			InputStream in = new BufferedInputStream(con.getInputStream());

    			reader = new BufferedReader(new InputStreamReader(in));

    			System.out.println(con.getResponseMessage());	
    			
    			String line;

    			 while ((line = reader.readLine()) != null)
    		     {
    				 result.append(line + '\n');
    		     }

    	Object object = new JSONParser().parse(result.toString());

    	JSONObject jo = (JSONObject)object;
    	JSONArray jsonArray = null;
    	List<String> mapKeys  = new java.util.ArrayList<>(jo.keySet());
    	jo.get(mapKeys.get(0));
    	if(mapKeys.contains("error")){
//   		tableHtml.append("<b text-align="\center\">");
   		htmlContent = jo.get("error").toString();
   		preApproValError = htmlContent;
    	}else{
    	for(int i=0;i<mapKeys.size();i++){
    		jsonArray = (JSONArray) jo.get(mapKeys.get(i));
    	}
//        jo.get(mapKeys);   
       	
       	for(int i=0;i<jsonArray.size();i++){
    		 JSONObject mJsonObjectProperty = (JSONObject) jsonArray.get(i);
    		 DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail(); 
    		
    		 for (Object rsltkey: mJsonObjectProperty.keySet()){

    			 if(rsltkey.toString().equalsIgnoreCase("vStepNo")){
    				dto.setStepNo(mJsonObjectProperty.get(rsltkey).toString());
    				if(!dto.getStepNo().isEmpty() && dto.getStepNo().matches("[0-9.]*")){
    					dto.setIsActive('Y');
    				}else{
    					dto.setIsActive('N');
    				}
    			}
    			 if(rsltkey.toString().equalsIgnoreCase("vStepName")){
    				dto.setStepName(mJsonObjectProperty.get(rsltkey).toString());
    			}
    			 if(rsltkey.toString().equalsIgnoreCase("vExpectedResult")){
    				dto.setExpectedResult(mJsonObjectProperty.get(rsltkey).toString());
    			}
    			 	 
    			 }
    		if(!dto.getStepNo().isEmpty() && dto.getStepNo().matches("[0-9.]*") && !dto.getExpectedResult().isEmpty()){
     			
     			successSection = successSection+1;
     		}
     		if(!dto.getStepNo().isEmpty() && dto.getStepNo().matches("[0-9.]*") && dto.getExpectedResult().isEmpty()){
     			successScript = successScript+1;
     		}
    		showPQSTableHeaderMst.addElement(dto);	
    	}
       		totalScript = showPQSTableHeaderMst.size();
       	}
    	}
    	catch(Exception e){
    	 e.printStackTrace();
    	 }
    }
    float total = successSection+successScript;
    float per = (float)((total / totalScript) * 100);
    confidence = String.format("%.2f",per);
    wsDesc=docMgmtImpl.getWorkspaceName(workspaceID);
	} 
 return SUCCESS;
}
public String getPreAppvalDetailsForSrcDoc() throws FileNotFoundException, IOException, ParseException, SQLException{
	
	
	//http://90.0.0.201:7979/pqsdetail?Wsid=0001&NodeId=12&
	//ClientId=0001&Path=E:\Mallika\Pycharm Projects 2.0\TableTestCase\Documents\Centrifuge Master.docx
	int usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
	usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	iscreatedRights = docMgmtImpl.iscreatedRights(workspaceID,nodeId, usercode, usergrpcode);
	PQPreApprovalPopup = knetProperties.getValue("PQPreApprovalPopup");
	//PreApprovalDocPath = "";
	PreApprovalURL = knetProperties.getValue("PreApprovalURL");
	StringBuilder result = new StringBuilder();
	//Path="E:/Mallika/PQS/TestFile/BizNET_LTR_EDC-SW-FR-24-PQS-01-092019-Sample_Inventory-PQ001-Freezer_Copy.docx";
	//Path=PreApprovalDocPath;
	//Path=Path.replace(" ", "_");
	URL url = new URL(PreApprovalURL+"?wsid="+workspaceID+"&nodeid="+nodeId+"&clientid="+clientCode+"&path="+Path);
	getClientDetail = new  ArrayList<DTOWorkSpaceNodeDetail>();
	ArrayList<String> PQHeaders = new ArrayList<String>();
	PQHeaders = docMgmtImpl.getPQSHeader(clientCode);
	for(int x=0;x<PQHeaders.size();x++){
		 DTOWorkSpaceNodeDetail dto1 = new DTOWorkSpaceNodeDetail();
		 dto1.setStepNo(PQHeaders.get(x));
		 getClientDetail.add(dto1);
	}
	//getClientDetail =  docMgmtImpl.getPQSHeader(clientCode);
   
    /*int tranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
    ArrayList<DTOWorkSpaceNodeHistory> geNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
	geNodeHistory = docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId, tranNo);
	Vector<DTOWorkSpaceNodeDetail> PQSTableHeaderMst = null;
	showPQSTableHeaderMst=null;
	showPQSTableHeaderMst = new Vector<DTOWorkSpaceNodeDetail>();
	if(geNodeHistory.size()>0){
	String fldrName= geNodeHistory.get(0).getFolderName();
	showValidateBtn = true;	 
	PQSTableHeaderMst = docMgmtImpl.getPQSTableHeaderMst(workspaceID,nodeId,clientCode,fldrName);*/
    /*if(PQSTableHeaderMst.size()>0){
    	IsConfirmBtn=false;
    	showPQSTableHeaderMst.addAll(PQSTableHeaderMst);
    	totalScript = PQSTableHeaderMst.size();
    	
    	for(int z=0; z<PQSTableHeaderMst.size();z++){
    		if(!PQSTableHeaderMst.get(z).getStepNo().isEmpty() && !PQSTableHeaderMst.get(z).getExpectedResult().isEmpty()){
    			successSection = successSection+1;
    		}
    		if(!PQSTableHeaderMst.get(z).getStepNo().isEmpty() && PQSTableHeaderMst.get(z).getExpectedResult().isEmpty()){
    			successScript = successScript+1;
    		}
    	}
    }
    else{*/
		showPQSTableHeaderMst=null;
		showPQSTableHeaderMst = new Vector<DTOWorkSpaceNodeDetail>();
    	BufferedReader reader = null ;
    	//URL url = new URL("http://90.0.0.201:7999/pqsdetail?wsid="+workspaceID+"&nodeid="+nodeId+"&clientid="+clientCode);
    		try{
    			HttpURLConnection con = (HttpURLConnection) url.openConnection();

    			con.setRequestMethod("GET");

    			InputStream in = new BufferedInputStream(con.getInputStream());

    			reader = new BufferedReader(new InputStreamReader(in));

    			System.out.println(con.getResponseMessage());	
    			
    			String line;

    			 while ((line = reader.readLine()) != null)
    		     {
    				 result.append(line + '\n');
    		     }

    	Object object = new JSONParser().parse(result.toString());

    	JSONObject jo = (JSONObject)object;
    	JSONArray jsonArray = null;
    	List<String> mapKeys  = new java.util.ArrayList<>(jo.keySet());
    	jo.get(mapKeys.get(0));
    	if(mapKeys.contains("error")){
//   		tableHtml.append("<b text-align="\center\">");
   		htmlContent = jo.get("error").toString();
    	}else{
    	for(int i=0;i<mapKeys.size();i++){
    		jsonArray = (JSONArray) jo.get(mapKeys.get(i));
    	}
//        jo.get(mapKeys);   
       	
       	for(int i=0;i<jsonArray.size();i++){
    		 JSONObject mJsonObjectProperty = (JSONObject) jsonArray.get(i);
    		 DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail(); 
    		
    		 for (Object rsltkey: mJsonObjectProperty.keySet()){

    			 if(rsltkey.toString().equalsIgnoreCase("vStepNo")){
    				dto.setStepNo(mJsonObjectProperty.get(rsltkey).toString());
    				if(!dto.getStepNo().isEmpty() && dto.getStepNo().matches("[0-9.]*")){
    					dto.setIsActive('Y');
    				}else{
    					dto.setIsActive('N');
    				}
    			}
    			 if(rsltkey.toString().equalsIgnoreCase("vStepName")){
    				dto.setStepName(mJsonObjectProperty.get(rsltkey).toString());
    			}
    			 if(rsltkey.toString().equalsIgnoreCase("vExpectedResult")){
    				dto.setExpectedResult(mJsonObjectProperty.get(rsltkey).toString());
    			}
    			 	 
    			 }
    		if(!dto.getStepNo().isEmpty() && dto.getStepNo().matches("[0-9.]*") && !dto.getExpectedResult().isEmpty()){
     			 
     			successSection = successSection+1;
     		}
     		if(!dto.getStepNo().isEmpty() && dto.getStepNo().matches("[0-9.]*") && dto.getExpectedResult().isEmpty()){
     			successScript = successScript+1;
     		}
    		showPQSTableHeaderMst.addElement(dto);	
    	}
       		totalScript = showPQSTableHeaderMst.size();
       	}
    	}
    	catch(Exception e){
    	 e.printStackTrace();
    	 }
   // }
    float total = successSection+successScript;
    float per = (float)((total / totalScript) * 100);
    confidence = String.format("%.2f",per);
    wsDesc=docMgmtImpl.getWorkspaceName(workspaceID);
    if(docMgmtImpl.getNodeHistoryForDocSign(workspaceID, nodeId).size()<0)
		showAutomateButton=true;
	//} 
 return SUCCESS;
}
public String insertPQApprovalNodeDetail() throws FileNotFoundException, IOException, ParseException{
	
	
	//http://90.0.0.201:7979/pqsdetail?Wsid=0001&NodeId=12&
	//ClientId=0001&Path=E:\Mallika\Pycharm Projects 2.0\TableTestCase\Documents\Centrifuge Master.docx
	usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	///PreApprovalDocPath = knetProperties.getValue("PreApprovalDocPath");
	PreApprovalURL = knetProperties.getValue("PreApprovalURL");
	StringBuilder result = new StringBuilder();
	//Path=PreApprovalDocPath;
	//Path=Path.replace(" ", "_");
	URL url = new URL(PreApprovalURL+"?wsid="+workspaceID+"&nodeid="+nodeId+"&clientid="+clientCode+"&path="+Path);
	BufferedReader reader = null ;
	//URL url = new URL("http://90.0.0.201:7999/pqsdetail?wsid="+workspaceID+"&nodeid="+nodeId+"&clientid="+clientCode);
		try{
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");

			InputStream in = new BufferedInputStream(con.getInputStream());

			reader = new BufferedReader(new InputStreamReader(in));

			System.out.println(con.getResponseMessage());	
			
			String line;

			 while ((line = reader.readLine()) != null)
		     {
				 result.append(line + '\n');
		     }
		
	 
	Object object = new JSONParser().parse(result.toString());

	JSONObject jo = (JSONObject)object;
	JSONArray jsonArray = null;
	String html="true";
	List<String> mapKeys  = new java.util.ArrayList<>(jo.keySet());
	jo.get(mapKeys.get(0));
	for(int i=0;i<mapKeys.size();i++){
		jsonArray = (JSONArray) jo.get(mapKeys.get(i));
	}
	getClientDetail = new  ArrayList<DTOWorkSpaceNodeDetail>(); 
    DTOWorkSpaceNodeDetail dtoForInsertPQApproval = new  DTOWorkSpaceNodeDetail(); 
    ArrayList<String> PQHeaders = new ArrayList<String>();
	PQHeaders = docMgmtImpl.getPQSHeader(clientCode);
    
    for(int x=0;x<PQHeaders.size();x++){
		 DTOWorkSpaceNodeDetail dto1 = new DTOWorkSpaceNodeDetail();
		 dto1.setStepNo(PQHeaders.get(x));
		 getClientDetail.add(dto1);
	}
   
   	if(mapKeys.contains("error")){
   		htmlContent = jo.get("error").toString();
   	}
   	
	for(int i=0;i<jsonArray.size();i++){
		 JSONObject mJsonObjectProperty = (JSONObject) jsonArray.get(i);
				
			 for (Object rsltkey: mJsonObjectProperty.keySet()){

				 if(rsltkey.toString().equalsIgnoreCase("vStepNo")){
					 dtoForInsertPQApproval.setStepNo(mJsonObjectProperty.get(rsltkey).toString());
				}
				 if(rsltkey.toString().equalsIgnoreCase("vStepName")){
					 dtoForInsertPQApproval.setStepName(mJsonObjectProperty.get(rsltkey).toString());
				}
				 if(rsltkey.toString().equalsIgnoreCase("vExpectedResult")){
					 dtoForInsertPQApproval.setExpectedResult(mJsonObjectProperty.get(rsltkey).toString());
				}
					 
				 }	
			 	dtoForInsertPQApproval.setWorkspaceId(workspaceID);
				dtoForInsertPQApproval.setNodeId(nodeId);
				int tranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
				dtoForInsertPQApproval.setTranNo(tranNo);
				String fldrName= docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId, tranNo).get(0).getFolderName();
				dtoForInsertPQApproval.setFolderName(fldrName);
				dtoForInsertPQApproval.setClientCode(clientCode);
				dtoForInsertPQApproval.setModifyBy(usercode);
				dtoForInsertPQApproval.setIsActive('Y');
				dtoForInsertPQApproval.setRemark("");
				
				if(!dtoForInsertPQApproval.getStepNo().isEmpty() && dtoForInsertPQApproval.getStepNo().matches("[0-9.]*") && dtoForInsertPQApproval.getStepNo().matches("[0-9.]*")){
					docMgmtImpl.insertIntoPQApproval(dtoForInsertPQApproval);
				}
	}
	}
	catch(Exception e){
		e.printStackTrace();
	} 
 return SUCCESS;
}

public String getAutomateDocumentDetails() throws FileNotFoundException, IOException, ParseException, SQLException{
	
	
	int usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
	usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	iscreatedRights = docMgmtImpl.iscreatedRights(workspaceID,nodeId, usercode, usergrpcode);
	ursFsFileName = docMgmtImpl.getNodeDetail(workspaceID, nodeId ).get(0).getNodeDisplayName();
	if(ursFsFileName.equalsIgnoreCase("User Requirement Specification"))
		ursFsFileName = "URS-Document.xls";
	if(ursFsFileName.equalsIgnoreCase("Functional Specification"))
		ursFsFileName = "FS-Document.xls";
	
	String DocAutomaateURL = knetProperties.getValue("DocAutomaateURL");
	StringBuilder result = new StringBuilder();
	
	clientCode = knetProperties.getValue("ClientCode");
	Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();
	//Path=Path.substring(Path.lastIndexOf('/') + 1);
	URL url = new URL(DocAutomaateURL+"?clientId="+clientCode+"&docType="+Automated_Doc_Type+"&filePath="+Path);
	
	System.out.println("URL\n"+url );
	
	getClientDetail = new  ArrayList<DTOWorkSpaceNodeDetail>();
	showPQSTableHeaderMst = new Vector<DTOWorkSpaceNodeDetail>();
	
	getTracebilityDetail = new  ArrayList<DTOWorkSpaceNodeDetail>();
	ArrayList<String> TracebilityMatrixHeaders = new ArrayList<String>();
	TracebilityMatrixHeaders = docMgmtImpl.getTracebilityMatrixHeaders(clientCode,Automated_Doc_Type);
	for(int x=0;x<TracebilityMatrixHeaders.size();x++){
		 DTOWorkSpaceNodeDetail dto1 = new DTOWorkSpaceNodeDetail();
		 dto1.setStepNo(TracebilityMatrixHeaders.get(x));
		 getTracebilityDetail.add(dto1);
	}
	
	
    	BufferedReader reader = null ;
    
    		try{
    			HttpURLConnection con = (HttpURLConnection) url.openConnection();

    			con.setRequestMethod("GET");

    			InputStream in = new BufferedInputStream(con.getInputStream());

    			reader = new BufferedReader(new InputStreamReader(in));

    			System.out.println(con.getResponseMessage());	
    			
    			String line;

    			 while ((line = reader.readLine()) != null)
    		     {
    				 result.append(line + '\n');
    		     }

    	Object object = new JSONParser().parse(result.toString());

    	JSONObject jo = (JSONObject)object;
    	JSONArray jsonArray = null;
    	List<String> mapKeys  = new java.util.ArrayList<>(jo.keySet());
    	jo.get(mapKeys.get(0));
    	if(mapKeys.contains("error")){
   		htmlContent = jo.get("error").toString();
   		preApproValError = htmlContent;
    	}else{
    	for(int i=0;i<mapKeys.size();i++){
    		jsonArray = (JSONArray) jo.get(mapKeys.get(i));
    	}

    	clientCodeToCheck=clientCode;
       	for(int i=0;i<jsonArray.size();i++){
    		 JSONObject mJsonObjectProperty = (JSONObject) jsonArray.get(i);
    		 DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail(); 
    		if(Automated_Doc_Type.equalsIgnoreCase("URS")){
    			for (Object rsltkey: mJsonObjectProperty.keySet()){

       			 if(rsltkey.toString().equalsIgnoreCase("URSNo")){
       				dto.setURSNo(mJsonObjectProperty.get(rsltkey).toString());
       				if(!dto.getURSNo().isEmpty()){
    					dto.setIsActive('Y');
    				}else{
    					dto.setIsActive('N');
    				}
       			}
       			 if(rsltkey.toString().equalsIgnoreCase("Description")){
       				 dto.setURSDescription(mJsonObjectProperty.get(rsltkey).toString());
       			 }
    			}
    			if(!dto.getURSNo().isEmpty() && !dto.getURSDescription().isEmpty()){
       				successSection = successSection+1;
       				}
    		}
    		else{
    			
    			if(clientCode.equalsIgnoreCase("0002")){
    				for (Object rsltkey: mJsonObjectProperty.keySet()){

    	       			 if(rsltkey.toString().equalsIgnoreCase("FRSNo")){
    	       				dto.setFRSNo(mJsonObjectProperty.get(rsltkey).toString());
    	       				if(!dto.getFRSNo().isEmpty() ){
    	    					dto.setIsActive('Y');
    	    				}else{
    	    					dto.setIsActive('N');
    	    				}
    	       			}
    	       			 if(rsltkey.toString().equalsIgnoreCase("Description")){
    	       				 dto.setURSDescription(mJsonObjectProperty.get(rsltkey).toString());
    	       			 		}
    	       			 	 }
    				if(!dto.getFRSNo().isEmpty() && !dto.getURSDescription().isEmpty()){
	       				successSection = successSection+1;
	       				}
    			}
    			else{
    				for (Object rsltkey: mJsonObjectProperty.keySet()){
    				if(rsltkey.toString().equalsIgnoreCase("URSNo")){
    	       			dto.setURSNo(mJsonObjectProperty.get(rsltkey).toString());
    	       			if(!dto.getURSNo().isEmpty()){
        					dto.setIsActive('Y');
        				}else{
        					dto.setIsActive('N');
        				}
    	       		}
          			 if(rsltkey.toString().equalsIgnoreCase("FRSNo")){
          				dto.setFRSNo(mJsonObjectProperty.get(rsltkey).toString());
          				if(!dto.getFRSNo().isEmpty()){
	    					dto.setIsActive('Y');
	    				}else{
	    					dto.setIsActive('N');
	    				}
          			}
          			 if(rsltkey.toString().equalsIgnoreCase("Description")){
          				 dto.setURSDescription(mJsonObjectProperty.get(rsltkey).toString());
          			 		}
          			 	 }
    				if(!dto.getURSNo().isEmpty() && !dto.getURSDescription().isEmpty()){
	       				successSection = successSection+1;
	       				}
          			/*if(!dto.getFRSNo().isEmpty() && !dto.getURSDescription().isEmpty()){
	       				successSection = successSection+1;
	       				}*/
    				}
    			}
    		showPQSTableHeaderMst.addElement(dto);
    	}
       		totalScript = showPQSTableHeaderMst.size();
       	}
    	}
    	catch(Exception e){
    	 e.printStackTrace();
    	 }
    float total = successSection+successScript;
    float per = (float)((total / totalScript) * 100);
    confidence = String.format("%.2f",per);
    wsDesc=docMgmtImpl.getWorkspaceName(workspaceID);
    incompleteData=totalScript-successSection;
    /*if(docMgmtImpl.getURSTracebelityMatrixDtlForDocType(workspaceID,Automated_Doc_Type).size()<=0)
		showAutomateButton=true;*/
    int tranNo = docMgmtImpl.getMaxTranNoByStageId(workspaceID,nodeId);
	if(Automated_Doc_Type!=null){
		if(docMgmtImpl.getTracebelityMatrixDtlForDocTypeHistory(workspaceID,nodeId,tranNo,Automated_Doc_Type).size()<=0){
			showAutomateButton=true;}
		}
    
 return SUCCESS;
}


public String insertAutomatedDocumentDetail() throws FileNotFoundException, IOException, ParseException{
	
	int usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
	usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	iscreatedRights = docMgmtImpl.iscreatedRights(workspaceID,nodeId, usercode, usergrpcode);
	
	String DocAutomaateURL = knetProperties.getValue("DocAutomaateURL");
	StringBuilder result = new StringBuilder();
	
	clientCode = knetProperties.getValue("ClientCode");
	Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();
	//Path=Path.substring(Path.lastIndexOf('/') + 1);
	URL url = new URL(DocAutomaateURL+"?clientId="+clientCode+"&docType="+Automated_Doc_Type+"&filePath="+Path);
	 
	System.out.println("URL\n"+url );
	BufferedReader reader = null ;
		try{
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");

			InputStream in = new BufferedInputStream(con.getInputStream());

			reader = new BufferedReader(new InputStreamReader(in));

			System.out.println(con.getResponseMessage());	
			
			String line;

			 while ((line = reader.readLine()) != null)
		     {
				 result.append(line + '\n');
		     }
		
	 
	Object object = new JSONParser().parse(result.toString());

	JSONObject jo = (JSONObject)object;
	JSONArray jsonArray = null;
	String html="true";
	List<String> mapKeys  = new java.util.ArrayList<>(jo.keySet());
	jo.get(mapKeys.get(0));
	for(int i=0;i<mapKeys.size();i++){
		jsonArray = (JSONArray) jo.get(mapKeys.get(i));
	}
	getClientDetail = new  ArrayList<DTOWorkSpaceNodeDetail>(); 
    DTOWorkSpaceNodeDetail dtoForDocument = new  DTOWorkSpaceNodeDetail(); 
    ArrayList<String> PQHeaders = new ArrayList<String>();
	PQHeaders = docMgmtImpl.getPQSHeader(clientCode);
    
    for(int x=0;x<PQHeaders.size();x++){
		 DTOWorkSpaceNodeDetail dto1 = new DTOWorkSpaceNodeDetail();
		 dto1.setStepNo(PQHeaders.get(x));
		 getClientDetail.add(dto1);
	}
   
   	if(mapKeys.contains("error")){
   		htmlContent = jo.get("error").toString();
   	}
   	Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();
   	if(Automated_Doc_Type.equalsIgnoreCase("URS")){
   		for(int i=0;i<jsonArray.size();i++){
   			

   		 JSONObject mJsonObjectProperty = (JSONObject) jsonArray.get(i);
   		 DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail(); 
   		
   			 for (Object rsltkey: mJsonObjectProperty.keySet()){

   				 if(rsltkey.toString().equalsIgnoreCase("URSNo")){
   					dto.setURSNo(mJsonObjectProperty.get(rsltkey).toString());
   				}
   				 if(rsltkey.toString().equalsIgnoreCase("Description")){
   					 dto.setURSDescription(mJsonObjectProperty.get(rsltkey).toString());
   				}
   				 	 
   				 }	
   			 dtoForDocument.setWorkspaceId(workspaceID);
   			 dtoForDocument.setNodeId(nodeId);
   				int tranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
   				dtoForDocument.setTranNo(tranNo);
   				Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();
   				dtoForDocument.setUploadFlag(Automated_Doc_Type);
   				//String fldrName= docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId, tranNo).get(0).getFolderName();
   				//dtoForInsertPQApproval.setFolderName(fldrName);
   				dtoForDocument.setURSNo(dto.getURSNo());
   				dtoForDocument.setURSDescription(dto.getURSDescription());
   				dtoForDocument.setClientCode(clientCode);
   				dtoForDocument.setModifyBy(usercode);
   				dtoForDocument.setIsActive('Y');
   				dtoForDocument.setRemark("");
   				docMgmtImpl.insertIntoTracebelityMatrixDtl(dtoForDocument,1);
   				}
   		}
   		else{
   			for(int i=0;i<jsonArray.size();i++){
   				

   			 JSONObject mJsonObjectProperty = (JSONObject) jsonArray.get(i);
   			 DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail(); 
   			if(clientCode.equalsIgnoreCase("0002")){
   				 for (Object rsltkey: mJsonObjectProperty.keySet()){

   					 if(rsltkey.toString().equalsIgnoreCase("FRSNo")){
   						dto.setFRSNo(mJsonObjectProperty.get(rsltkey).toString());
   					}
   					 if(rsltkey.toString().equalsIgnoreCase("Description")){
   						 dto.setURSDescription(mJsonObjectProperty.get(rsltkey).toString());
   					}
   					 	 
   					 }	
   				 dtoForDocument.setWorkspaceId(workspaceID);
   				 dtoForDocument.setNodeId(nodeId);
   				int tranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
   				dtoForDocument.setTranNo(tranNo);
   				Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();
   				dtoForDocument.setUploadFlag(Automated_Doc_Type);
   				//String fldrName= docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId, tranNo).get(0).getFolderName();
   				//dtoForInsertPQApproval.setFolderName(fldrName);
   				dtoForDocument.setFRSNo(dto.getFRSNo());
   				dtoForDocument.setURSDescription(dto.getURSDescription());
   				dtoForDocument.setClientCode(clientCode);
   				dtoForDocument.setModifyBy(usercode);
   				dtoForDocument.setIsActive('Y');
   				dtoForDocument.setRemark("");
   				docMgmtImpl.insertIntoTracebelityMatrixDtl(dtoForDocument,2);
   			}
   				
   			else{
   				for (Object rsltkey: mJsonObjectProperty.keySet()){
    				if(rsltkey.toString().equalsIgnoreCase("URSNo")){
    	       			dto.setURSNo(mJsonObjectProperty.get(rsltkey).toString());
    	       		}
          			 if(rsltkey.toString().equalsIgnoreCase("FRSNo")){
          				dto.setFRSNo(mJsonObjectProperty.get(rsltkey).toString());
          			}
          			 if(rsltkey.toString().equalsIgnoreCase("Description")){
          				 dto.setURSDescription(mJsonObjectProperty.get(rsltkey).toString());
          			 		}
          			 	 }
	   				dtoForDocument.setWorkspaceId(workspaceID);
	   				dtoForDocument.setNodeId(nodeId);
	   				int tranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
	   				dtoForDocument.setTranNo(tranNo);
	   				Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();
	   				dtoForDocument.setUploadFlag(Automated_Doc_Type);
	   				//String fldrName= docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId, tranNo).get(0).getFolderName();
	   				//dtoForInsertPQApproval.setFolderName(fldrName);
	   				dtoForDocument.setURSNo(dto.getURSNo());
	   				dtoForDocument.setFRSNo(dto.getFRSNo());
	   				dtoForDocument.setURSDescription(dto.getURSDescription());
	   				dtoForDocument.setClientCode(clientCode);
	   				dtoForDocument.setModifyBy(usercode);
	   				dtoForDocument.setIsActive('Y');
	   				dtoForDocument.setRemark("");
	   				docMgmtImpl.insertIntoTracebelityMatrixDtl(dtoForDocument,3);
   				}
   					
   				}
   			}
   	
	
		}	
	catch(Exception e){
		e.printStackTrace();
	} 
 return SUCCESS;
}


public String getURSFSInfo(){
	
	htmlContent = "";
	if(nodeId==99){
		int trnNo = docMgmtImpl.getMaxTranNo(workspaceID, 3);
		URSTracebelityMatrixDtl= docMgmtImpl.getURSTracebelityMatrixDtl(workspaceID,3,4);
		FSTracebelityMatrixDtl = docMgmtImpl.getFSTracebelityMatrixDtl(workspaceID,3,4);
		
		if(URSTracebelityMatrixDtl.size()>0){
			ArrayList<DTOWorkSpaceNodeDetail> arraylist = new ArrayList<DTOWorkSpaceNodeDetail>(URSTracebelityMatrixDtl);
			
			for (DTOWorkSpaceNodeDetail userList : arraylist) {
				if(!htmlContent.equals("")){
					htmlContent += ","; 
				}
				htmlContent += userList.getURSNo()+"::"+userList.getURSDescription();
			}
		}
		else{
			htmlContent="No data found.";
		}
	}
	return "html";
}


public String getTracebilityMatrixData(){
	ArrayList<String> TracebilityMatrixHeaders = new ArrayList<String>();
	wsDesc=docMgmtImpl.getWorkspaceName(workspaceID);
	clientCode = knetProperties.getValue("ClientCode");
	//Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();
	TracebilityMatrixHeaders = docMgmtImpl.getTracebilityMatrixHeaders(clientCode);
	clientCodeToCheck = clientCode ; 
	for(int x=0;x<TracebilityMatrixHeaders.size();x++){
		 DTOWorkSpaceNodeDetail dto1 = new DTOWorkSpaceNodeDetail();
		 dto1.setStepNo(TracebilityMatrixHeaders.get(x));
		 getTracebilityMatrixHeader.add(dto1);
		}
	if(docMgmtImpl.getFSTracebelityMatrixDtlToCheck(workspaceID,"URS").size()>=0 &&
			docMgmtImpl.getFSTracebelityMatrixDtlToCheck(workspaceID,"FS").size()>0){
				//traceblityAlert=false;
		Vector<DTOWorkSpaceNodeDetail> getNodeDetail = docMgmtImpl.getNodeDetailByNodeDisplayName(workspaceID,"PQ Scripts");
		String scCode="";
		//String status="";
		for(int i=0;i<getNodeDetail.size();i++){
			//ScriptCodeVal[i] = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, getNodeDetail.get(i).getNodeId(), "Script Code").getAttrValue();
			String attrVal="";
			ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrDtl = docMgmtImpl.getAttributeDetailfromWorkspaceIdAndNodeId(workspaceID, String.valueOf(getNodeDetail.get(i).getNodeId()));
			/*for(int nd=0;nd<nodeAttrDtl.size();nd++){
				if(nodeAttrDtl.get(nd).getAttrName().equalsIgnoreCase("Script Code")){
					
				}
			}*/
			for(int i1=0;i1<nodeAttrDtl.size();i1++){
				if(nodeAttrDtl.get(i1).getAttrName().equalsIgnoreCase("Script Code")){
					scCode=nodeAttrDtl.get(i1).getAttrValue();
				}
				if(nodeAttrDtl.get(i1).getAttrName().equalsIgnoreCase("Pass/Fail")){
					globalStatus=nodeAttrDtl.get(i1).getAttrValue();
				}
				
				/*if(nodeAttrDtl.get(i1).getAttrName().equalsIgnoreCase("URS Reference No.")){
					getTracebilityMatrixDetailToDisplay=new Vector<>();
					getTracebilityMatrixDetailToDisplay=docMgmtImpl.getTracebelityMatrixDtlForAttributesToShow(workspaceID,nodeAttrDtl.get(i1).getAttrValue());
					//getTracebilityMatrixDetail=docMgmtImpl.getTracebelityMatrixDtlForAttributesToShow(workspaceID,nodeAttrDtl.get(i1).getAttrValue());
					System.out.println(scCode);
					for(int i11=0;i11<getTracebilityMatrixDetailToDisplay.size();i11++){
						getTracebilityMatrixDetailToDisplay.get(i11).setAttrValue(scCode);
					}
					getTracebilityMatrixDetail.addAll(getTracebilityMatrixDetailToDisplay);
					 //getTracebilityMatrixDetail=docMgmtImpl.getTracebelityMatrixDtlByID(workspaceID,attrVal);
				}*/
				if(nodeAttrDtl.get(i1).getAttrName().equalsIgnoreCase("FS Reference No.")){
					getTracebilityMatrixDetailToDisplay=new Vector<>();
					if(nodeAttrDtl.get(i1).getAttrValue()!=null && nodeAttrDtl.get(i1).getAttrValue().length()!=0){
					getTracebilityMatrixDetailToDisplay=docMgmtImpl.getTracebelityMatrixDtlForAttributesToShow(workspaceID,nodeAttrDtl.get(i1).getAttrValue());
					//getTracebilityMatrixDetail=docMgmtImpl.getTracebelityMatrixDtlForAttributesToShow(workspaceID,nodeAttrDtl.get(i1).getAttrValue());
					//getTracebilityMatrixDetailToDisplay.get(i).setAttrValue(globalStatus);
					}
					System.out.println(scCode);
					String scriptCodes="";
					scCode="";
					
					for(int x=0;x<getTracebilityMatrixDetailToDisplay.size();x++){
					//getTracebilityMatrixDetailToDisplay.get(x).setAttrValue(globalStatus);
					scriptCodes=docMgmtImpl.getScriptCodesForTM(workspaceID,getTracebilityMatrixDetailToDisplay.get(x).getIDNo());
					String arr[]=scriptCodes.split(",");
					scCode="";
					for(int arrlngth=0;arrlngth<arr.length;arrlngth++){ 
							if(scCode==""){
								scCode = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, Integer.parseInt(arr[arrlngth]), "Script Code").getAttrValue();
								scCode += "["+docMgmtImpl.getAttributeDetailByAttrName(workspaceID, Integer.parseInt(arr[arrlngth]), "Pass/Fail").getAttrValue()+"]";
								}
							else{
								if(!scCode.contains(docMgmtImpl.getAttributeDetailByAttrName(workspaceID, Integer.parseInt(arr[arrlngth]), "Script Code").getAttrValue())){
									scCode += ","+docMgmtImpl.getAttributeDetailByAttrName(workspaceID, Integer.parseInt(arr[arrlngth]), "Script Code").getAttrValue();
									scCode += "["+docMgmtImpl.getAttributeDetailByAttrName(workspaceID, Integer.parseInt(arr[arrlngth]), "Pass/Fail").getAttrValue()+"]";
								}
							}
					// += docMgmtImpl.getAttributeDetailByAttrName(workspaceID, Integer.parseInt(arr[arrlngth]), "Script Code").getAttrValue();
					map.put(getTracebilityMatrixDetailToDisplay.get(x).getIDNo(), scCode);
			        // Copy all data from hashMap into TreeMap
			        sorted.putAll(map);
					//break;
					}
					 //getTracebilityMatrixDetail=docMgmtImpl.getTracebelityMatrixDtlByID(workspaceID,attrVal);
				}getTracebilityMatrixDetail.addAll(getTracebilityMatrixDetailToDisplay);
					}
				}
			}	
		}
	
	for(int i=0;i<getTracebilityMatrixDetail.size();i++)
    {
      for(int j=0;j<getTracebilityMatrixDetail.size();j++)
        {
          if(i!=j)
           {
        	  if(getTracebilityMatrixDetail.elementAt(i).getIDNo().equals(getTracebilityMatrixDetail.elementAt(j).getIDNo()))
                 {
        		   getTracebilityMatrixDetail.removeElementAt(j);
        		 }
           }
        }
    }
	return SUCCESS;
}



	public String getDocURLForOffice365() {
	return DocURLForOffice365;
	}

	public void setDocURLForOffice365(String docURLForOffice365) {
	DocURLForOffice365 = docURLForOffice365;
	}

	public Vector getUsers() {
		return users;
	}

	public void setUsers(Vector users) {
		this.users = users;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	// parameters setter -getter to use in page

	public DTOWorkSpaceNodeDetail getWorkSpaceNodeDtl() {
		return workSpaceNodeDtl;
	}

	public void setWorkSpaceNodeDtl(DTOWorkSpaceNodeDetail workSpaceNodeDtl) {
		this.workSpaceNodeDtl = workSpaceNodeDtl;
	}

	public boolean isNodeExtendable() {
		return isNodeExtendable;
	}

	public void setNodeExtendable(boolean isNodeExtendable) {
		this.isNodeExtendable = isNodeExtendable;
	}

	public char getCanEditFlag() {
		return canEditFlag;
	}

	public void setCanEditFlag(char canEditFlag) {
		this.canEditFlag = canEditFlag;
	}

	public String getCanApprovedReviewed() {
		return canApprovedReviewed;
	}

	public void setCanApprovedReviewed(String canApprovedReviewed) {
		this.canApprovedReviewed = canApprovedReviewed;
	}

	public char getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(char projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Vector<DTOCheckedoutFileDetail> getLockedFileDetail() {
		return lockedFileDetail;
	}

	public void setLockedFileDetail(
			Vector<DTOCheckedoutFileDetail> lockedFileDetail) {
		this.lockedFileDetail = lockedFileDetail;
	}

	public Vector<DTOWorkSpaceNodeHistory> getNodeHistory() {
		return nodeHistory;
	}

	public void setNodeHistory(Vector<DTOWorkSpaceNodeHistory> nodeHistory) {
		this.nodeHistory = nodeHistory;
	}

	public boolean isNodeLocked() {
		return isNodeLocked;
	}

	public void setNodeLocked(boolean isNodeLocked) {
		this.isNodeLocked = isNodeLocked;
	}

	public Vector<DTOCheckedoutFileDetail> getCheckedOutBy() {
		return checkedOutBy;
	}

	public void setCheckedOutBy(Vector<DTOCheckedoutFileDetail> checkedOutBy) {
		this.checkedOutBy = checkedOutBy;
	}

	public String getTempBaseFolderReplaced() {
		return tempBaseFolderReplaced;
	}

	public void setTempBaseFolderReplaced(String tempBaseFolderReplaced) {
		this.tempBaseFolderReplaced = tempBaseFolderReplaced;
	}

	public String getUserDefineId() {
		return userDefineId;
	}

	public void setUserDefineId(String userDefineId) {
		this.userDefineId = userDefineId;
	}

	public int getIsLeafNode() {
		return isLeafNode;
	}

	public void setIsLeafNode(int isLeafNode) {
		this.isLeafNode = isLeafNode;
	}

	public String getWorkspacedesc() {
		return workspacedesc;
	}

	public void setWorkspacedesc(String workspacedesc) {
		this.workspacedesc = workspacedesc;
	}

	public Vector<DTOWorkSpaceNodeAttrHistory> getAttrHistory() {
		return attrHistory;
	}

	public void setAttrHistory(Vector<DTOWorkSpaceNodeAttrHistory> attrHistory) {
		this.attrHistory = attrHistory;
	}

	public Vector<DTOWorkSpaceNodeAttribute> getAttrDtl() {
		return attrDtl;
	}

	public void setAttrDtl(Vector<DTOWorkSpaceNodeAttribute> attrDtl) {
		this.attrDtl = attrDtl;
	}

	public Vector<DTOWorkSpaceUserRightsMst> getStageuserdtl() {
		return stageuserdtl;
	}

	public void setStageuserdtl(Vector<DTOWorkSpaceUserRightsMst> stageuserdtl) {
		this.stageuserdtl = stageuserdtl;
	}

	public boolean isIscreatedRights() {
		return iscreatedRights;
	}

	public void setIscreatedRights(boolean iscreatedRights) {
		this.iscreatedRights = iscreatedRights;
	}

	public int getSelectedStage() {
		return selectedStage;
	}

	public void setSelectedStage(int selectedStage) {
		this.selectedStage = selectedStage;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getStatusIndi() {
		return statusIndi;
	}

	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}

	public List<String> getNodeActivities() {
		return nodeActivities;
	}

	public void setNodeActivities(List<String> nodeActivities) {
		this.nodeActivities = nodeActivities;
	}

	public ArrayList<DTOWorkspaceNodeDocHistory> getNodeDocHistory() {
		return nodeDocHistory;
	}

	public void setNodeDocHistory(
			ArrayList<DTOWorkspaceNodeDocHistory> nodeDocHistory) {
		this.nodeDocHistory = nodeDocHistory;
	}

	public String getSrcDocPath() {
		return srcDocPath;
	}

	public void setSrcDocPath(String srcDocPath) {
		this.srcDocPath = srcDocPath;
	}

	public String getTxtmsg() {
		return txtmsg;
	}

	public void setTxtmsg(String txtmsg) {
		this.txtmsg = txtmsg;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public Vector<DTOWorkSpaceNodeDetail> getShowPQSTableHeaderMst() {
		return showPQSTableHeaderMst;
	}

	public void setShowPQSTableHeaderMst(Vector<DTOWorkSpaceNodeDetail> showPQSTableHeaderMst) {
		this.showPQSTableHeaderMst = showPQSTableHeaderMst;
	}

	public ArrayList<DTOWorkSpaceNodeDetail> getGetClientDetail() {
		return getClientDetail;
	}

	public void setGetClientDetail(ArrayList<DTOWorkSpaceNodeDetail> getClientDetail) {
		this.getClientDetail = getClientDetail;
	}

	public String getSignImg() {
		return signImg;
	}

	public void setSignImg(String signImg) {
		this.signImg = signImg;
	}

	public String getSignStyle() {
		return signStyle;
	}

	public void setSignStyle(String signStyle) {
		this.signStyle = signStyle;
	}	
	
	public String getIsScriptCodeAutoGenrate() {
		return isScriptCodeAutoGenrate;
	}

	public void setIsScriptCodeAutoGenrate(String isScriptCodeAutoGenrate) {
		this.isScriptCodeAutoGenrate = isScriptCodeAutoGenrate;
	}
	

}
