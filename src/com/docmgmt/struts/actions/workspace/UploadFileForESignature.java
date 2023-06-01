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

import com.docmgmt.dto.DTOCheckedoutFileDetail;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.dto.DTOWorkspaceNodeDocHistory;
import com.docmgmt.dto.DTOWorkspaceNodeReminderDoneStatus;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.services.pdf.PdfPropUtilities;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.docmgmt.struts.actions.reports.StageWiseMailReport;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UploadFileForESignature extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public String workspaceID;
	public String docId;
	public int usergrpcode;
	public int usercode;
	public String userType;
	public String UserName;

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
	public int stageId;
	public String autoMail;
	public String UplodFileMail;
	public String docType;
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
	public int currentStageId;
	public String baseWorkRefFolder="";
	public String RefrenceFileUpload;
	public double ReffileSizeInMB;
	public File uploadRefFile;
	public int ReffileUploloadingSize;
	public String RefFileName;
	public List<File> files1;
	
	@Override
	public String execute() throws SQLException {
		nodeId=recordId;
		workspaceID=docId;
		
		int maxTransNo = docMgmtImpl.getMaxTranNo(workspaceID,nodeId);
		stageId = docMgmtImpl.getStageDescription(workspaceID,nodeId,maxTransNo);
		usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		//useRightStage=docMgmtImpl.getStageIdsRightsWise(workspaceID, usercode,usergrpcode,nodeId).get(0);
		
		usrName = ActionContext.getContext().getSession().get("username").toString();
		
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.showFullNodeHistoryWithVoidFilesForESignature(workspaceID, nodeId);
		OpenFileAndSignOff = propertyInfo.getValue("OpenFileAndSignOff");
		
		//ManualSignatureConfig = propertyInfo.getValue("ManualSignatureConfig");
		ManualSignatureConfig = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, 1, "ManualSignature").getAttrValue();
		ApplicationUrl = propertyInfo.getValue("ApplicationUrl");
		RefrenceFileUpload = propertyInfo.getValue("RefrenceFileUpload");
		baseWorkRefFolder = propertyInfo.getValue("RefrenceFolder");
		
		Vector <DTOWorkSpaceNodeHistory> dtoSign;
		dtoSign =docMgmtImpl.getUserSignatureDetail(usercode);
		baseWorkFolder = propertyInfo.getValue("signatureFile");
		if(dtoSign.size()>0){
			signatureFlag = true;
			signPath = dtoSign.get(0).getFileName();
			fontStyle = dtoSign.get(0).getFileType();
		}

		userNameForPreview = usrName;
		String roleCode = docMgmtImpl.getRoleCodeFromWSUserRightsMst(workspaceID,nodeId,usercode);
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
		
		Vector<DTOWorkSpaceNodeDetail> getNodeDetail = new Vector<>();
		getNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, nodeId);
		//nodeFolderName = getNodeDetail.get(0).getFolderName(); 
		userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		DTOWorkSpaceMst dtowmst = docMgmtImpl.getWorkSpaceDetailWSList(workspaceID);
		appType=dtowmst.getProjectCode();
		
		
		for (int i = 0; i < fullNodeHistory.size(); i++) {
			DTOWorkSpaceNodeHistory dto = (DTOWorkSpaceNodeHistory) fullNodeHistory
					.get(i);
			dto.setHistoryDocumentSize(FileManager.getFileSize(dto.getBaseWorkFolder()+ dto.getFolderName() + "/" + dto.getFileName()));
		}
		
		DocURLForOffice365 = propertyInfo.getValue("DocURL");
		usergrpcode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("usergroupcode").toString());
		userWiseStageFlag = docMgmtImpl.getNextStageFlagByUser(workspaceID,nodeId,usercode);
		
		DTOWorkSpaceUserRightsMst obj = new DTOWorkSpaceUserRightsMst();
		obj.setNodeId(new Integer(nodeId).intValue());
		obj.setWorkSpaceId(workspaceID);
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
		
		ArrayList<Integer> stageIdsRightsWise = docMgmtImpl.getStageIdsRightsWise(workspaceID, usercode, usergrpcode, nodeId);
		isCreate = stageIdsRightsWise.contains(10);
		currentStageId=docMgmtImpl.getMaxNodeHistoryByTranNo(workspaceID, nodeId).get(0).getStageId();
		
		
		return SUCCESS;
	}
	
	public String SaveDocAction() throws MalformedURLException{

		nodeId=recordId;
		usergrpcode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("usergroupcode").toString());
		usercode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		userType = ActionContext.getContext().getSession().get("usertypecode")
				.toString();
		UserName = ActionContext.getContext().getSession().get("username")
				.toString();
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		UplodFileMail = propertyInfo.getValue("UplodFileMail");
		allowAutoCorrectionPdfProp = propertyInfo.getValue("PdfPropAutoCorrection");
		OpenFileAndSignOff = propertyInfo.getValue("OpenFileAndSignOff");
		passwordProtected = propertyInfo.getValue("PasswordProtected");
		fileSizeProperty = propertyInfo.getValue("FileUploadingSize");
		fileUploloadingSize = Integer.parseInt(fileSizeProperty);

		RefrenceFileUpload = propertyInfo.getValue("RefrenceFileUpload");
		fileUploloadingSize = Integer.parseInt(fileSizeProperty);
		ReffileUploloadingSize = Integer.parseInt(fileSizeProperty);
		baseWorkRefFolder = propertyInfo.getValue("RefrenceFolder");
		
		isReplaceFolderName='Y';
		
		if(remark.isEmpty() || remark.equals("")){
			remark="New";
		}
		
		Vector<DTOWorkSpaceNodeDetail> getNodeDetail = new Vector<>();
		getNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, nodeId);
		//nodeFolderName = getNodeDetail.get(0).getFolderName();
		
		String fileName = "";
		// Added By : Ashmak Shah
		int stageId = 10; // Created stage (By Default)

		// System.out.println(workspaceID +""+usergrpcode+" " +usercode +
		// ":"+UserName);

		DTOWorkSpaceMst workSpace = docMgmtImpl.getWorkSpaceDetailWSList(workspaceID);

		String baseWorkFolder = workSpace.getBaseWorkFolder();

		String tempBaseFolder = propertyInfo.getValue("BASE_TEMP_FOLDER");
		Date toDaydate= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(toDaydate);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH)+1;
		date = cal.get(Calendar.DAY_OF_MONTH);

		int TranNo = docMgmtImpl.getNewTranNo(workspaceID);
		File dirPath = new File(tempBaseFolder +"/"+year +"/"+month+"/"+date+"/"+ workspaceID + "/"+ nodeId + "/" + TranNo);
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
		if (useSourceFile == 'Y') {
			ArrayList<DTOWorkspaceNodeDocHistory> docHistory = docMgmtImpl
					.getLatestNodeDocHistory(workspaceID, nodeId);
			String baseSrcPath = propertyInfo.getValue("SOURCE_DOC_FOLDER");

			File srcDoc = new File(baseSrcPath + docHistory.get(0).getDocPath()
					+ "/" + docHistory.get(0).getDocName());
			uploadFile = FileManager.convertDocument(srcDoc, "pdf");
			uploadFileFileName = uploadFile.getName();
		}
		
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
		
		int fileSize1 = (fileUploloadingSize/10)+1;
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
		} 
		*/

		
		/*InputStream sourceInputStream = null;
		try {
			if (uploadFile != null) {
				fileData = getBytesFromFile(uploadFile);
				sourceInputStream = new FileInputStream(uploadFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
*/		
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
/*
InputStream RefsourceInputStream = null;
		
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
		wsnad.setWorkspaceId(workspaceID);
		wsnad.setNodeId(nodeId);
		wsnad.setAttrValue(ts.toString());
		wsnad.setAttrId(-999);
		wsnad.setStatusIndi('N');
		wsnad.setModifyBy(usercode);
		wsnad.setTranNo(TranNo);
		attrValueId.addElement(wsnad);

		wsnahdto = new DTOWorkSpaceNodeAttrHistory();
		wsnahdto.setWorkSpaceId(workspaceID);
		wsnahdto.setNodeId(nodeId);
		wsnahdto.setAttrValue(ts.toString());
		wsnahdto.setAttrId(-999);
		wsnahdto.setStatusIndi('N');
		wsnahdto.setModifyBy(usercode);
		wsnahdto.setTranNo(TranNo);
		attrValueIdForattrHistory.addElement(wsnahdto);
		// ////////////////////////////////////////////

		HttpServletRequest request = ServletActionContext.getRequest();
		ArrayList<DTOWorkspaceNodeReminderDoneStatus> dtoList = new ArrayList<DTOWorkspaceNodeReminderDoneStatus>();
		Vector<DTOWorkSpaceNodeHistory> nodeHistoryList = docMgmtImpl.getLastNodeHistory(workspaceID, nodeId);
		int tranNo = 0;
		if (nodeHistoryList.size() > 0) {
			for (int i = 0; i < nodeHistoryList.size(); i++) {
				DTOWorkSpaceNodeHistory dto = nodeHistoryList.get(i);
				tranNo = dto.getTranNo();
				// System.out.println("Last tran="+tranNo);

			}
		}
		Vector<DTOWorkSpaceNodeAttrHistory> attrHistory = docMgmtImpl.getWorkspaceNodeAttrHistorybyTranNo(workspaceID, nodeId,tranNo);
		
		 /* System.out.println("attrhistory" + attrHistory.size()); for(int
		 * i=0;i<attrHistory.size();i++) { System.out.println("i" + i);
		 * System.out.println(attrHistory.get(i).getAttrId());
		 * System.out.println(attrHistory.get(i).getAttrValue()); }
		 * System.out.println();*/
		 
		
		if(dndflag)
		{
			
			int index;
			System.out.println("File uploaded through dnd");
			
			//attrIdDrag=docMgmtImpl.getAttrMstForDnd();//to get all the attribute details(18 entries will be received)
			Vector<DTOWorkSpaceNodeAttrDetail> attrIdDrag = docMgmtImpl.getNodeAttrDetail(workspaceID, nodeId);
			System.out.println(attrIdDrag.size());
			for (index=0;index<attrIdDrag.size();index++) {
				
				DTOWorkSpaceNodeAttrDetail dtoam = attrIdDrag.get(index);
				
				wsnahdto=new DTOWorkSpaceNodeAttrHistory();
				wsnahdto.setWorkSpaceId(workspaceID);
				wsnahdto.setNodeId(nodeId);
			
				if(dtoam.getAttrName().equals("operation"))
					wsnahdto.setAttrValue(operation);
				else
					wsnahdto.setAttrValue(dtoam.getAttrValue());
				wsnahdto.setAttrId(dtoam.getAttrId());
				wsnahdto.setStatusIndi('N');    					
				wsnahdto.setModifyBy(usercode);
				wsnahdto.setTranNo(TranNo);
				attrValueIdForattrHistory.addElement(wsnahdto);
				
				wsnad=new DTOWorkSpaceNodeAttrDetail();
				wsnad.setWorkspaceId(workspaceID);
				wsnad.setNodeId(nodeId);
				if(dtoam.getAttrName().equals("operation"))
					wsnad.setAttrValue(operation);
				else
					wsnad.setAttrValue(dtoam.getAttrValue());
				wsnad.setAttrId(dtoam.getAttrId());
				wsnad.setStatusIndi('N');    					
				wsnad.setModifyBy(usercode);
				wsnad.setTranNo(TranNo);
				attrValueId.addElement(wsnad);
			}
		}else{

		for (int i = 1; i <= attrCount; i++) {

			String attributeValueId = "attrValueId" + i;
			String attrType = "attrType" + i;
			String attrId = "attrId" + i;
			String attrName = "attrName" + i;
			boolean doneFlag = true;

			for (int idxAttr = 0; idxAttr < attrHistory.size(); idxAttr++) {

				if (request.getParameter(attrType).equals("Date")
						&& attrHistory.get(idxAttr).getAttrId() == Integer
								.parseInt(request.getParameter(attrId))
						&& attrHistory.get(idxAttr).getAttrValue().equals(
								request.getParameter(attributeValueId))) {
					// System.out.println("attrid" +
					// attrHistory.get(idxAttr).getAttrId());
					// System.out.println("attrval" +
					// attrHistory.get(idxAttr).getAttrValue());
					doneFlag = false;
				}
			}

			if (request.getParameter(attrName).toString().equals(
					"Submission Type")) {
				// System.out.println("Paper Submission");
				if (request.getParameter(attributeValueId).toString().equals(
						"Paper Submission")) {
					docMgmtImpl.UpdateDisplayNameForPaperSubmission(
							workspaceID, nodeId);
				}

			} else if (request.getParameter(attrName).toString().equals(
					"operation")) {

				nodeOperationValue = request.getParameter(attributeValueId)
						.toString();

			}

			wsnad = new DTOWorkSpaceNodeAttrDetail();

			wsnad.setWorkspaceId(workspaceID);
			wsnad.setNodeId(nodeId);
			wsnad.setAttrValue(request.getParameter(attributeValueId));
			wsnad.setAttrId(Integer.parseInt(request.getParameter(attrId)));
			wsnad.setStatusIndi('N');
			wsnad.setModifyBy(usercode);
			wsnad.setTranNo(TranNo);
			attrValueId.addElement(wsnad);

			wsnahdto = new DTOWorkSpaceNodeAttrHistory();

			wsnahdto.setWorkSpaceId(workspaceID);
			wsnahdto.setNodeId(nodeId);
			wsnahdto.setAttrValue(request.getParameter(attributeValueId));
			wsnahdto.setAttrId(Integer.parseInt(request.getParameter(attrId)));
			wsnahdto.setStatusIndi('N');
			wsnahdto.setModifyBy(usercode);
			wsnahdto.setTranNo(TranNo);

			attrValueIdForattrHistory.addElement(wsnahdto);

			
			/* It will undone the user reminders displayed in welcome page */
			 
			if (request.getParameter(attrType).equals("Date") && doneFlag) {
				DTOWorkspaceNodeReminderDoneStatus dto = new DTOWorkspaceNodeReminderDoneStatus();
				dto.setvWorkspaceId(workspaceID);
				dto.setiNodeId(nodeId);
				dto.setiAttrId(Integer.parseInt(request.getParameter(attrId)));
				dto.setiUserCode(usercode);
				dtoList.add(dto);
			}
		}
		}
		docMgmtImpl.markReminderAsUnDone(dtoList);
		docMgmtImpl.updateNodeAttrDetail(attrValueId);

		DTOWorkSpaceNodeDetail wsnd = docMgmtImpl.getNodeDetail(workspaceID,
				nodeId).get(0);
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
		
		if (deleteFile == 'Y') {// delete file is checked or operation attribute
			// is 'delete'

			updateNodeAttrHistory(workspaceID, nodeId, TranNo, usercode,
					keyword, description, attrValueId);
			insertCheckedOutFileDetail(workspaceID, nodeId, TranNo, usercode,
					"No File");

			updateNodeHistory(workspaceID, nodeId, TranNo, "No File", usercode,
					stageId, remark);
			insertWorkspaceNodeVersionHistory(workspaceID, nodeId, TranNo,
					usercode, versionSuffix, versionPrefix);

		} else { // else uploading the file

			if (fileSize > 0) { // if new file is uploaded
				if(remark.isEmpty() || remark==""){
					remark="New";
				}
				
				/*if(RefrenceFileUpload.equalsIgnoreCase("yes")){
					uploadRefFileOnNode(workspaceID, nodeId, TranNo, baseWorkRefFolder, ReffileData,
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

					            int tranNoForAttachmenet = docMgmtImpl.getNewTranNoForAttachment(workspaceID,nodeId);
					            
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
									uploadRefFileOnNode(workspaceID, nodeId, TranNo,tranNoForAttachmenet, baseWorkRefFolder, ReffileData,
											RefFileName, ReffileSize, RefsourceInputStream);
									DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
									dto.setWorkSpaceId(workspaceID);
									dto.setNodeId(nodeId);
									dto.setTranNo(TranNo);
									dto.setRefFilePath( year + "/"+ month+"/"+ date+"/" + workspaceID + "/"+ nodeId + "/" + tranNoForAttachmenet);
									dto.setRefFileName(RefFileName);
									dto.setModifyBy(usercode);
									dto.setFileSize(ReffileSizeInMB);
									dto.setRemark("");
									docMgmtImpl.insertRefFileDetail(dto);
									}
					        	}
							}
						}
			
				uploadFileOnNode(workspaceID, nodeId, baseWorkFolder, fileData,
						fileName, usercode, keyword, description,
						tempBaseFolder, attrValueId, TranNo, fileSize,
						sourceInputStream, versionSuffix, versionPrefix,
						stageId, remark);

			} else { // else uploading the previous file with new tranNo

				int lastTranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
				DTOWorkSpaceNodeHistory previousHistory = docMgmtImpl
						.getWorkspaceNodeHistorybyTranNo(workspaceID, nodeId,
								lastTranNo);

				if (previousHistory.getFileName() == null
						|| previousHistory.getFileName().equalsIgnoreCase(
								"No File")) {

					updateNodeAttrHistory(workspaceID, nodeId, TranNo,
							usercode, keyword, description, attrValueId);
					insertCheckedOutFileDetail(workspaceID, nodeId, TranNo,
							usercode, "No File");

					updateNodeHistory(workspaceID, nodeId, TranNo, "No File",
							usercode, stageId, remark);
					insertWorkspaceNodeVersionHistory(workspaceID, nodeId,
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
						uploadFileOnNode(workspaceID, nodeId, baseWorkFolder,
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
		
		
		//if doc or docx file, converting to PDF at uploaded file location
		String srcFileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
		if(srcFileExt.equalsIgnoreCase("doc") || srcFileExt.equalsIgnoreCase("docx"))
		{
			tranNo=docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
			ArrayList<DTOWorkSpaceNodeHistory> dto = docMgmtImpl.showFullNodeHistoryForESignatureByTranNo(workspaceID, nodeId,tranNo);
			
	 		String srcPath=dto.get(0).getBaseWorkFolder()+ dto.get(0).getFolderName() + "/" + dto.get(0).getFileName();
	 		String fileNameWithoutExt=fileName.substring(0, fileName.lastIndexOf('.'));
	 		String pdfFileName=fileNameWithoutExt+".pdf";
	 		String destPath=dto.get(0).getBaseWorkFolder()+ dto.get(0).getFolderName() + "/" + pdfFileName;
	 		
			//String response=docToPdfURL+"inputPath="+srcPath+"&outputPath="+destPath;
			/*String response=docMgmtImpl.convertPdfToDoc(srcPath,destPath);
			
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
			
		docMgmtImpl.InsertUpdateNodeAttrHistory(attrValueIdForattrHistory);
		if(UplodFileMail.equalsIgnoreCase("Yes")){
		  StageWiseMailReport stageWiseMail = new StageWiseMailReport();
		  //stageWiseMail.UploadFileMail(workspaceID,nodeId,usercode);
		  stageWiseMail.UploadFileMailNewFormate(workspaceID,nodeId,usercode);
		  System.out.println("Mail Sent....");
	    }
		
		//BlockChain HashCode
		String hashCode=docMgmtImpl.generateHashCode(workspaceID,nodeId,stageId);
		//BlockChain HashCode Ends
		
		
		htmlContent="true";
	
		return "html";
	
	}
	
	public String SaveDocAction1() throws MalformedURLException{

		nodeId=recordId;
		usergrpcode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("usergroupcode").toString());
		usercode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		userType = ActionContext.getContext().getSession().get("usertypecode")
				.toString();
		UserName = ActionContext.getContext().getSession().get("username")
				.toString();
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		UplodFileMail = propertyInfo.getValue("UplodFileMail");
		allowAutoCorrectionPdfProp = propertyInfo.getValue("PdfPropAutoCorrection");
		OpenFileAndSignOff = propertyInfo.getValue("OpenFileAndSignOff");
		passwordProtected = propertyInfo.getValue("PasswordProtected");
		fileSizeProperty = propertyInfo.getValue("FileUploadingSize");
		RefrenceFileUpload = propertyInfo.getValue("RefrenceFileUpload");
		fileUploloadingSize = Integer.parseInt(fileSizeProperty);
		ReffileUploloadingSize = Integer.parseInt(fileSizeProperty);
		baseWorkRefFolder = propertyInfo.getValue("RefrenceFolder");
		
		
		isReplaceFolderName='Y';
		
		if(remark.isEmpty() || remark.equals("")){
			remark="New";
		}
		
		Vector<DTOWorkSpaceNodeDetail> getNodeDetail = new Vector<>();
		getNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, nodeId);
		//nodeFolderName = getNodeDetail.get(0).getFolderName();
		
		String fileName = "";
		// Added By : Ashmak Shah
		int stageId = 10; // Created stage (By Default)

		// System.out.println(workspaceID +""+usergrpcode+" " +usercode +
		// ":"+UserName);

		DTOWorkSpaceMst workSpace = docMgmtImpl.getWorkSpaceDetailWSList(workspaceID);

		String baseWorkFolder = workSpace.getBaseWorkFolder();

		String tempBaseFolder = propertyInfo.getValue("BASE_TEMP_FOLDER");
		Date toDaydate= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(toDaydate);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH)+1;
		date = cal.get(Calendar.DAY_OF_MONTH);

		int TranNo = docMgmtImpl.getNewTranNo(workspaceID);
		File dirPath = new File(tempBaseFolder +"/"+year +"/"+month+"/"+date+"/"+ workspaceID + "/"+ nodeId + "/" + TranNo);
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
		byte[] ReffileData = null;
		int ReffileSize = 0;
		if (uploadRefFile != null) {
			ReffileSize = new Long(uploadRefFile.length()).intValue();
		
		//int fileSize1 = (fileUploloadingSize/10)+1;
		/*if((fileSize/fileUploloadingSize) >fileSize1) {
			addActionMessage("FileUploadingSize"); 
			return INPUT;
			}*/
		
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
		} 
		
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
		
		InputStream RefsourceInputStream = null;
		
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
		}

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
		wsnad.setWorkspaceId(workspaceID);
		wsnad.setNodeId(nodeId);
		wsnad.setAttrValue(ts.toString());
		wsnad.setAttrId(-999);
		wsnad.setStatusIndi('N');
		wsnad.setModifyBy(usercode);
		wsnad.setTranNo(TranNo);
		attrValueId.addElement(wsnad);

		wsnahdto = new DTOWorkSpaceNodeAttrHistory();
		wsnahdto.setWorkSpaceId(workspaceID);
		wsnahdto.setNodeId(nodeId);
		wsnahdto.setAttrValue(ts.toString());
		wsnahdto.setAttrId(-999);
		wsnahdto.setStatusIndi('N');
		wsnahdto.setModifyBy(usercode);
		wsnahdto.setTranNo(TranNo);
		attrValueIdForattrHistory.addElement(wsnahdto);
		// ////////////////////////////////////////////

		HttpServletRequest request = ServletActionContext.getRequest();
		ArrayList<DTOWorkspaceNodeReminderDoneStatus> dtoList = new ArrayList<DTOWorkspaceNodeReminderDoneStatus>();
		Vector<DTOWorkSpaceNodeHistory> nodeHistoryList = docMgmtImpl.getLastNodeHistory(workspaceID, nodeId);
		int tranNo = 0;
		if (nodeHistoryList.size() > 0) {
			for (int i = 0; i < nodeHistoryList.size(); i++) {
				DTOWorkSpaceNodeHistory dto = nodeHistoryList.get(i);
				tranNo = dto.getTranNo();
				// System.out.println("Last tran="+tranNo);

			}
		}
		Vector<DTOWorkSpaceNodeAttrHistory> attrHistory = docMgmtImpl.getWorkspaceNodeAttrHistorybyTranNo(workspaceID, nodeId,tranNo);
		/*
		 * System.out.println("attrhistory" + attrHistory.size()); for(int
		 * i=0;i<attrHistory.size();i++) { System.out.println("i" + i);
		 * System.out.println(attrHistory.get(i).getAttrId());
		 * System.out.println(attrHistory.get(i).getAttrValue()); }
		 * System.out.println();
		 */
		
		if(dndflag)
		{
			
			int index;
			System.out.println("File uploaded through dnd");
			
			//attrIdDrag=docMgmtImpl.getAttrMstForDnd();//to get all the attribute details(18 entries will be received)
			Vector<DTOWorkSpaceNodeAttrDetail> attrIdDrag = docMgmtImpl.getNodeAttrDetail(workspaceID, nodeId);
			System.out.println(attrIdDrag.size());
			for (index=0;index<attrIdDrag.size();index++) {
				
				DTOWorkSpaceNodeAttrDetail dtoam = attrIdDrag.get(index);
				
				wsnahdto=new DTOWorkSpaceNodeAttrHistory();
				wsnahdto.setWorkSpaceId(workspaceID);
				wsnahdto.setNodeId(nodeId);
			
				if(dtoam.getAttrName().equals("operation"))
					wsnahdto.setAttrValue(operation);
				else
					wsnahdto.setAttrValue(dtoam.getAttrValue());
				wsnahdto.setAttrId(dtoam.getAttrId());
				wsnahdto.setStatusIndi('N');    					
				wsnahdto.setModifyBy(usercode);
				wsnahdto.setTranNo(TranNo);
				attrValueIdForattrHistory.addElement(wsnahdto);
				
				wsnad=new DTOWorkSpaceNodeAttrDetail();
				wsnad.setWorkspaceId(workspaceID);
				wsnad.setNodeId(nodeId);
				if(dtoam.getAttrName().equals("operation"))
					wsnad.setAttrValue(operation);
				else
					wsnad.setAttrValue(dtoam.getAttrValue());
				wsnad.setAttrId(dtoam.getAttrId());
				wsnad.setStatusIndi('N');    					
				wsnad.setModifyBy(usercode);
				wsnad.setTranNo(TranNo);
				attrValueId.addElement(wsnad);
			}
		}else{

		for (int i = 1; i <= attrCount; i++) {

			String attributeValueId = "attrValueId" + i;
			String attrType = "attrType" + i;
			String attrId = "attrId" + i;
			String attrName = "attrName" + i;
			boolean doneFlag = true;

			for (int idxAttr = 0; idxAttr < attrHistory.size(); idxAttr++) {

				if (request.getParameter(attrType).equals("Date")
						&& attrHistory.get(idxAttr).getAttrId() == Integer
								.parseInt(request.getParameter(attrId))
						&& attrHistory.get(idxAttr).getAttrValue().equals(
								request.getParameter(attributeValueId))) {
					// System.out.println("attrid" +
					// attrHistory.get(idxAttr).getAttrId());
					// System.out.println("attrval" +
					// attrHistory.get(idxAttr).getAttrValue());
					doneFlag = false;
				}
			}

			if (request.getParameter(attrName).toString().equals(
					"Submission Type")) {
				// System.out.println("Paper Submission");
				if (request.getParameter(attributeValueId).toString().equals(
						"Paper Submission")) {
					docMgmtImpl.UpdateDisplayNameForPaperSubmission(
							workspaceID, nodeId);
				}

			} else if (request.getParameter(attrName).toString().equals(
					"operation")) {

				nodeOperationValue = request.getParameter(attributeValueId)
						.toString();

			}

			wsnad = new DTOWorkSpaceNodeAttrDetail();

			wsnad.setWorkspaceId(workspaceID);
			wsnad.setNodeId(nodeId);
			wsnad.setAttrValue(request.getParameter(attributeValueId));
			wsnad.setAttrId(Integer.parseInt(request.getParameter(attrId)));
			wsnad.setStatusIndi('N');
			wsnad.setModifyBy(usercode);
			wsnad.setTranNo(TranNo);
			attrValueId.addElement(wsnad);

			wsnahdto = new DTOWorkSpaceNodeAttrHistory();

			wsnahdto.setWorkSpaceId(workspaceID);
			wsnahdto.setNodeId(nodeId);
			wsnahdto.setAttrValue(request.getParameter(attributeValueId));
			wsnahdto.setAttrId(Integer.parseInt(request.getParameter(attrId)));
			wsnahdto.setStatusIndi('N');
			wsnahdto.setModifyBy(usercode);
			wsnahdto.setTranNo(TranNo);

			attrValueIdForattrHistory.addElement(wsnahdto);

			/*
			 * It will undone the user reminders displayed in welcome page
			 */
			if (request.getParameter(attrType).equals("Date") && doneFlag) {
				DTOWorkspaceNodeReminderDoneStatus dto = new DTOWorkspaceNodeReminderDoneStatus();
				dto.setvWorkspaceId(workspaceID);
				dto.setiNodeId(nodeId);
				dto.setiAttrId(Integer.parseInt(request.getParameter(attrId)));
				dto.setiUserCode(usercode);
				dtoList.add(dto);
			}
		}
		}
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
		DTOWorkSpaceNodeDetail wsnd = docMgmtImpl.getNodeDetail(workspaceID,
				nodeId).get(0);
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

			updateNodeAttrHistory(workspaceID, nodeId, TranNo, usercode,
					keyword, description, attrValueId);
			insertCheckedOutFileDetail(workspaceID, nodeId, TranNo, usercode,
					"No File");

			updateNodeHistory(workspaceID, nodeId, TranNo, "No File", usercode,
					stageId, remark);
			insertWorkspaceNodeVersionHistory(workspaceID, nodeId, TranNo,
					usercode, versionSuffix, versionPrefix);

		} else { // else uploading the file

			if (fileSize > 0) { // if new file is uploaded
				if(remark.isEmpty() || remark==""){
					remark="New";
				}
				
				if(RefrenceFileUpload.equalsIgnoreCase("yes")){
						uploadRefFileOnNode(workspaceID, nodeId, TranNo, baseWorkRefFolder, ReffileData,
								RefFileName, ReffileSize, RefsourceInputStream);
				}
				
				
				uploadFileOnNode(workspaceID, nodeId, baseWorkFolder, fileData,
						fileName, usercode, keyword, description,
						tempBaseFolder, attrValueId, TranNo, fileSize,
						sourceInputStream, versionSuffix, versionPrefix,
						stageId, remark);

			} else { // else uploading the previous file with new tranNo

				int lastTranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
				DTOWorkSpaceNodeHistory previousHistory = docMgmtImpl
						.getWorkspaceNodeHistorybyTranNo(workspaceID, nodeId,
								lastTranNo);

				if (previousHistory.getFileName() == null
						|| previousHistory.getFileName().equalsIgnoreCase(
								"No File")) {

					updateNodeAttrHistory(workspaceID, nodeId, TranNo,
							usercode, keyword, description, attrValueId);
					insertCheckedOutFileDetail(workspaceID, nodeId, TranNo,
							usercode, "No File");

					updateNodeHistory(workspaceID, nodeId, TranNo, "No File",
							usercode, stageId, remark);
					insertWorkspaceNodeVersionHistory(workspaceID, nodeId,
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
						uploadFileOnNode(workspaceID, nodeId, baseWorkFolder,
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
		
		
		//if doc or docx file, converting to PDF at uploaded file location
		String srcFileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
		if(srcFileExt.equalsIgnoreCase("doc") || srcFileExt.equalsIgnoreCase("docx"))
		{
			tranNo=docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
			ArrayList<DTOWorkSpaceNodeHistory> dto = docMgmtImpl.showFullNodeHistoryForESignatureByTranNo(workspaceID, nodeId,tranNo);
			
	 		String srcPath=dto.get(0).getBaseWorkFolder()+ dto.get(0).getFolderName() + "/" + dto.get(0).getFileName();
	 		String fileNameWithoutExt=fileName.substring(0, fileName.lastIndexOf('.'));
	 		String pdfFileName=fileNameWithoutExt+".pdf";
	 		String destPath=dto.get(0).getBaseWorkFolder()+ dto.get(0).getFolderName() + "/" + pdfFileName;
	 		
			//String response=docToPdfURL+"inputPath="+srcPath+"&outputPath="+destPath;
			/*String response=docMgmtImpl.convertPdfToDoc(srcPath,destPath);
			
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
			
		docMgmtImpl.InsertUpdateNodeAttrHistory(attrValueIdForattrHistory);
		if(UplodFileMail.equalsIgnoreCase("Yes")){
		  StageWiseMailReport stageWiseMail = new StageWiseMailReport();
		  //stageWiseMail.UploadFileMail(workspaceID,nodeId,usercode);
		  stageWiseMail.UploadFileMailNewFormate(workspaceID,nodeId,usercode);
		  System.out.println("Mail Sent....");
	    }
		
		//BlockChain HashCode
		String hashCode=docMgmtImpl.generateHashCode(workspaceID,nodeId,stageId);
		//BlockChain HashCode Ends
		
		
		htmlContent="true";
	
		return "html";
	
	}
public String sendDocForReview() throws MalformedURLException{
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		autoMail = propertyInfo.getValue("AutoMail");
		//WorkspaceNodeDaviation = knetProperties.getValue("WorkspaceNodeDaviation");
		
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
				String roleCode = docMgmtImpl.getRoleCodeFromWSUserRightsMst(workspaceID,nodeId,usercode);
				docMgmtImpl.UpdateNodeHistoryForRoleCode(workspaceID,nodeId,roleCode);
				
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
		
		
		
		/*int parentNodeId;
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
	  	}*/
		  if(autoMail.equalsIgnoreCase("Yes")){
			 StageWiseMailReport stageWiseMail = new StageWiseMailReport();
			 //stageWiseMail.StageWiseMail(workspaceID,nodeId,"Created",20);
			 stageWiseMail.StageWiseMailNewFormate(workspaceID,nodeId,"Created",20);
			 System.out.println("Mail Sent....");
	      	}
		  
		//BlockChain HashCode
			String hashCode=docMgmtImpl.generateHashCode(workspaceID,nodeId,stageId);
		//BlockChain HashCode Ends
		  
		return SUCCESS;
	}
	
	private int parseInt(String fileSize2) {
		// TODO Auto-generated method stub
		return 0;
	}

	// //////////////////////////////////

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
			if (RefFileName == null) {
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

	private void createTempFolderStruc(String wsId, int nodeId, int tranNo,
			String baseWorkFolder, byte[] fileData, String fileName,
			java.io.InputStream sourceInputStream) {
		try {
			int newTranNo = tranNo /* + 1 */;
			File wsFolder = new File(baseWorkFolder + "/" + wsId);
			wsFolder.mkdirs();

			File nodeFolder = new File(baseWorkFolder + "/" + wsId + "/"
					+ nodeId);
			nodeFolder.mkdirs();

			if (newTranNo != -1) {
				File tranFolder = new File(baseWorkFolder + "/" + wsId + "/"
						+ nodeId + "/" + newTranNo);
				tranFolder.mkdirs();
			}

			File uploadedFile = new File(baseWorkFolder + "/" + wsId + "/"
					+ nodeId + "/" + newTranNo + "/" + fileName);

			FileOutputStream uploadedFileOutStream = new FileOutputStream(
					uploadedFile);
			uploadedFileOutStream.write(fileData);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

	
	
	public String uploadSourceDoc() {
		System.out.println("Upload");
		if (uploadDoc == null) {
			htmlContent="false";
		}

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String sourceDocPath = propertyInfo.getValue("SOURCE_DOC_FOLDER");
		String tempDir = propertyInfo.getValue("BASE_TEMP_FOLDER");
		autoMail = propertyInfo.getValue("AutoMail");
		uploadDocFileName = uploadDocFileName.replace(' ', '_');
		userName = ActionContext.getContext().getSession().get("username")
				.toString();

		// Check whether the last uploaded doc is being modified by some one
		// else
		// if yes then user cannot upload new doc.
		boolean deleted = true;
		ArrayList<DTOWorkspaceNodeDocHistory> docHis = docMgmtImpl
				.getLatestNodeDocHistory(workspaceID, nodeId);
		if (docHis.size() > 0) {
			String destFileName = "src_" + docHis.get(0).getDocName();

			File destFile = new File(tempDir + "/" + workspaceID + "/" + nodeId
					+ "/" + destFileName);

			if (destFile.exists()) {
				deleted = destFile.delete();
			}
		}
		
		fileSizeProperty = propertyInfo.getValue("FileUploadingSize");
		fileUploloadingSize = Integer.parseInt(fileSizeProperty);
		
		double fileSizeInBytes = uploadDoc.length();
		
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

		// if file is deleted from temp or there is no file then allow user to
		// upload new doc.
		if (deleted) {
			DTOWorkSpaceNodeDetail wsnd = docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0);
			String temp=uploadDocFileName;
			String srcFileExt = uploadDocFileName.substring(uploadDocFileName.lastIndexOf(".") + 1);
			String newFileName=temp.substring(0, temp.lastIndexOf('.'));
			System.out.println(temp);
			System.out.println(srcFileExt);
			System.out.println(newFileName);
			// Get new docTranNo and docPath
			int docTranNo = docMgmtImpl.getNewDocTranNo(workspaceID);
			String docPath = "/"+year +"/"+month+"/"+date+"/" + workspaceID + "/" + nodeId + "/" + docTranNo;
			ArrayList<DTOWorkspaceNodeDocHistory> WsNodeDocHistoryList = new ArrayList<DTOWorkspaceNodeDocHistory>();
			DTOWorkspaceNodeDocHistory dto = new DTOWorkspaceNodeDocHistory();
			dto.setWorkspaceId(workspaceID);
			dto.setNodeId(nodeId);
			dto.setDocTranNo(docTranNo);
			dto.setDocName(newFileName+"_"+workspaceID+"_"+nodeId+"."+srcFileExt);
			//dto.setDocName(wsnd.getFolderName());
			//dto.setDocName(uploadDocFileName);
			dto.setDocContentType(uploadDocContentType);
			dto.setDocPath(docPath);
			usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			dto.setUploadedBy(usercode);
			dto.setRemark(uploadDocFileName + " has been uploaded by "+ userName);
			dto.setModifyBy(usercode);
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
			//inserting data into sourceDocumentReminder table
			Vector<DTOWorkSpaceUserRightsMst> WorkspaceUserDetailList;
			int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			//WorkspaceUserDetailList=docMgmtImpl.getUserRightsDetailForsrcDocRmd(workspaceID, nodeId,userCode);
			WorkspaceUserDetailList=docMgmtImpl.getUserRightsDetail(workspaceID, nodeId);
			
			for(int i=0;i<WorkspaceUserDetailList.size();i++){
				DTOWorkSpaceNodeHistory wsndhs =new DTOWorkSpaceNodeHistory();
				wsndhs.setWorkSpaceId(WorkspaceUserDetailList.get(i).getWorkSpaceId());
				wsndhs.setNodeId(WorkspaceUserDetailList.get(i).getNodeId());
				wsndhs.setUserCode(WorkspaceUserDetailList.get(i).getUserCode());
				wsndhs.setFileName(newFileName+"_"+workspaceID+"_"+nodeId+"."+srcFileExt);
				String path=sourceDocPath+docPath;
				path=path.replace("/", "\\");
				wsndhs.setFilePath(path);
				wsndhs.setRemark("New");
				wsndhs.setModifyBy(userCode);
				wsndhs.setStatusIndi('N');
				
				docMgmtImpl.insertsrcDocReminder(wsndhs, 1);
			}
			if(autoMail.equalsIgnoreCase("Yes")){
				 StageWiseMailReport stageWiseMail = new StageWiseMailReport();
				 stageWiseMail.UploadFileMailFromSrcDocument(workspaceID,nodeId,usercode);
				  System.out.println("Mail Sent....");
			    }

		} else {
			addActionMessage("Cannot upload new document as it is being modified by some one else.");
			return "conflict";
		}
		htmlContent="true";
		return "html";
	}
	
	
	/*public String checkAdUserPass(){
		int usercd=checkAuthentic();
		
		DTOUserMst user=docMgmtImpl.getUserByCode(usercd);
		int isADUserAuthenticate=0;
		
		if(user.getIsAdUser()=='Y'){
    		if(user.getStatusIndi()=='N' || user.getStatusIndi()=='E'){
    			String adUserName=user.getAdUserName();
    			adUserName=adUserName.substring(adUserName.lastIndexOf("/") + 1);
    			isADUserAuthenticate=docMgmtImpl.authenticateADUser(adUserName,getPassword());
    		}
    	
        	if (isADUserAuthenticate != 200) {
        		addActionError("You have AD User rights. Please login with correct AD Username and Password");
        		return INPUT;
        	}
    	}
		htmlContent="true";
		return "html";
	}*/
	
	
	// //////////////////////////////////

	
	
	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
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

	/**
	 * @param ar
	 *            [0] = workspaceId ar[1] = file path to upload
	 */
	public static void main(String ar[]) {
		DocMgmtImpl docmgtImpl = new DocMgmtImpl();
		// SaveNodeAttrAction s = new SaveNodeAttrAction();
		ArrayList<DTOWorkSpaceNodeDetail> dtoWsNodeDtlList = new ArrayList<DTOWorkSpaceNodeDetail>();
		// String wsID = s.workspaceID;
		String wsID = ar[0];
		dtoWsNodeDtlList = docmgtImpl.getAllChildNodeForFileuploading(wsID);
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		// File baseImpDir = propertyInfo.getDir("ImportProjectPath");
		// System.out.println(dtoWsNodeDtlList.size());
		// File fileObj =new File("/demo.pdf");
		File fileObj = new File(ar[1]);
		FileManager fileManager = new FileManager();
		String workspaceFolder = propertyInfo.getValue("BaseWorkFolder");
		Vector<DTOWorkSpaceNodeAttrHistory> allwsnah = new Vector<DTOWorkSpaceNodeAttrHistory>();
		for (int i = 0; i < dtoWsNodeDtlList.size(); i++) {
			int newTranNo = docmgtImpl.getNewTranNo(wsID);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			int inodeid = dtoWsNodeDtlList.get(i).getNodeId();
			DTOWorkSpaceNodeHistory dtowsnh = new DTOWorkSpaceNodeHistory();
			dtowsnh.setWorkSpaceId(wsID);
			dtowsnh.setNodeId(inodeid);
			dtowsnh.setTranNo(newTranNo);
			dtowsnh.setFileName(fileObj.getName());
			dtowsnh.setFileType("");
			dtowsnh.setFolderName("/" + wsID + "/" + inodeid + "/" + newTranNo);
			dtowsnh.setRequiredFlag('Y');
			dtowsnh.setStageId(100);// Stage is by default approved...
			dtowsnh.setRemark("");
			dtowsnh.setModifyBy(1);
			dtowsnh.setStatusIndi('N');
			dtowsnh.setDefaultFileFormat("");
			docmgtImpl.insertNodeHistory(dtowsnh);

			fileManager.copyDirectory(fileObj, new File(workspaceFolder
					+ dtowsnh.getFolderName() + "/" + fileObj.getName()));
			Vector<DTOWorkSpaceNodeAttrDetail> dtoWsNodeAttrDtl = docmgtImpl
					.getNodeAttrDetail(wsID, inodeid);
			DTOWorkSpaceNodeAttrHistory dtowsnah = new DTOWorkSpaceNodeAttrHistory();
			dtowsnah.setWorkSpaceId(wsID);
			dtowsnah.setNodeId(inodeid);
			dtowsnah.setTranNo(newTranNo);
			dtowsnah.setAttrId(-999);
			dtowsnah.setAttrValue(ts.toString());
			dtowsnah.setModifyBy(1);

			for (int j = 0; j < dtoWsNodeAttrDtl.size(); j++) {
				dtowsnah = new DTOWorkSpaceNodeAttrHistory();
				dtowsnah.setWorkSpaceId(wsID);
				dtowsnah.setNodeId(inodeid);
				dtowsnah.setTranNo(newTranNo);
				dtowsnah.setAttrId(dtoWsNodeAttrDtl.get(j).getAttrId());
				dtowsnah.setAttrValue(dtoWsNodeAttrDtl.get(j).getAttrValue());
				dtowsnah.setModifyBy(1);
				allwsnah.add(dtowsnah);
			}

			DTOWorkSpaceNodeVersionHistory dtowsnvh = new DTOWorkSpaceNodeVersionHistory();
			dtowsnvh.setWorkspaceId(wsID);
			dtowsnvh.setNodeId(dtoWsNodeDtlList.get(i).getNodeId());
			dtowsnvh.setTranNo(newTranNo);
			dtowsnvh.setPublished('N');
			dtowsnvh.setDownloaded('N');
			dtowsnvh.setActivityId("");
			dtowsnvh.setModifyBy(1);
			dtowsnvh.setExecutedBy(1);
			dtowsnvh.setUserDefineVersionId("A-1");
			docmgtImpl.insertWorkspaceNodeVersionHistory(dtowsnvh);
		}
		docmgtImpl.InsertUpdateNodeAttrHistory(allwsnah);
	}
}
