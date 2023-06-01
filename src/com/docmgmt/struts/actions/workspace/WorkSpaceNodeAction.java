package com.docmgmt.struts.actions.workspace;

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
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOAttrReferenceTableMatrix;
import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.dto.DTOCheckedoutFileDetail;
import com.docmgmt.dto.DTODynamicTable;
import com.docmgmt.dto.DTOForumDtl;
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
import com.docmgmt.dto.DTOWorkspaceNodeReminderDoneStatus;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.DeleteAllTrees;
import com.docmgmt.server.webinterface.beans.FileCopy;
import com.docmgmt.server.webinterface.beans.GenerateTree;
import com.docmgmt.server.webinterface.entities.Workspace.FontTypes;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.docmgmt.struts.actions.reports.StageWiseMailReport;
import com.docmgmt.struts.actions.util.wordToPdf;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WorkSpaceNodeAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();

	public String buttonName;
	public String forumTextArea;
	public int nodeId = 1;
	Vector<DTOAttributeValueMatrix> attrDtl = new Vector();
	Vector users = new Vector();
	public String workspaceID;
	public int usergrpcode;
	public int usercode;
	public String userName;
	public String userType;
	public String takeVersion;
	public String statusIndi;
	HttpServletRequest request;
	public int historysize;
	public String alloweTMFCustomization;


	Vector lockedFileDetail = new Vector();
	Vector nodeHistory = new Vector();
	Vector checkedOutBy = new Vector();
	Vector attrHistory = new Vector();

	public String sopname;
	public boolean isNodeLocked;
	public int isLeafNode;
	public String tempBaseFolderReplaced;
	public String str ;
	public String result1;
	public String finalString;
	public String folderName;
	public String nodeDisplayName;
	public String nodeName;
	public int attrCount;
	public String project_type;
	public String fullPathName;
	public String lastPublishedVersion;
	public int stageCode;
	public boolean submittedNodeHistory;
	public String checkDelete;
	public boolean refresh;
	public String nodeFolderName;
	public String appType;
	public String remark;
	public String allowAutoCorrectionPDFProp;

	public ArrayList<Object[]> filterDynamicList;
	public int stageId;
	Vector<DTOWorkSpaceUserRightsMst> getSRFlagData=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> getAdjustDateData=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> hoursData=new Vector<>();

	/*
	 * 16-Nov-09 To display Full Node History
	 */
	ArrayList<DTOWorkSpaceNodeHistory> fullNodeHistory;
	int tranNoToRevert;

	/*
	 * 23-Nov-09 Source Document
	 */
	File uploadDoc;
	String uploadDocFileName;
	String uploadDocContentType;
	ArrayList<DTOWorkspaceNodeDocHistory> nodeDocHistory;

	/* Node Comments */
	public File uploadDocComment;
	public String uploadDocCommentFileName;
	public int[] userCodedtl;
	public String message;
	public String attrRemark;
	
	public File uploadFile;
	OutputStream out;
	public String DownloadFolderPath;
	public String DownloadFile;
	public InputStream inputStream;
	public String fileName;
    public long contentLength;
    public String htmlContent;
	public String baseSrcPath;
	public ArrayList<DTOWorkSpaceNodeHistory> previousHistory=new ArrayList<>();
	ArrayList<DTOWorkSpaceNodeHistory> Created=new ArrayList<>();
	ArrayList<DTOWorkSpaceNodeHistory> Reviewed=new ArrayList<>();
	ArrayList<DTOWorkSpaceNodeHistory> Authorized=new ArrayList<>();
	ArrayList<DTOWorkSpaceNodeHistory> Approved=new ArrayList<>();
	public File uploadedFile;
	public String autoMail;
	public boolean getCompletedNodeStageDetail;
	public String showAttributesInPublishPage;
	public String eSign="N";
	FontTypes ft =  new FontTypes();
	public String lbl_folderName;
	public String lbl_nodeName;
	int year;
	int month;
	int date;
	public File f;
	public double fileSizeInMB;
	public int fileUploloadingSize;
	public String fileSizeProperty;
	public String OpenFileAndSignOff;
	public int pageNo;
	public String uName;
	public String role;
	public String signId;
	public String signdate;
	public Vector <DTOWorkSpaceNodeHistory> dtoWsHistory;
	public String imgFilename="";
	public String fileExt;
	
	
	public String execute() throws MalformedURLException {
		request = ServletActionContext.getRequest();

		workspaceID = ActionContext.getContext().getSession().get("ws_id")
				.toString();
		usergrpcode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("usergroupcode").toString());
		usercode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		userType = ActionContext.getContext().getSession().get("usertypecode")
				.toString();
		userName = ActionContext.getContext().getSession().get("username")
				.toString();

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		allowAutoCorrectionPDFProp = propertyInfo
				.getValue("PdfPropAutoCorrection");
		autoMail = propertyInfo.getValue("AutoMail");
		alloweTMFCustomization = propertyInfo.getValue("ETMFCustomization");
		alloweTMFCustomization= alloweTMFCustomization.toLowerCase();
		lbl_folderName = propertyInfo.getValue("ForlderName");
		lbl_nodeName = propertyInfo.getValue("NodeName");	
		OpenFileAndSignOff = propertyInfo.getValue("OpenFileAndSignOff");	
		
		if (buttonName.equalsIgnoreCase("Lock Document")) {
			return editNodeAttribute();
		} else if (buttonName.equalsIgnoreCase("Unlock Document")) {
			System.out.println("Unlock File Inside");
			return unLockFiles();
		} else if (buttonName.equalsIgnoreCase("Unlock Without Saving")) {
			System.out.println("Unlock File without saving Inside");
			return unLockWithoutSaving();
		} else if (buttonName.equalsIgnoreCase("Save")) {
			System.out.println("Save Ectd Attributevalue");
			return SaveAttribute();
		}

		else if (buttonName.equalsIgnoreCase("Extend")) {
			System.out.println("Extend");
			return ExtendNode();
		}

		else if (buttonName.equalsIgnoreCase("Change") || buttonName.equalsIgnoreCase("Sign")) {
			System.out.println("Change");
			return changeStatus();
		} else if (buttonName.equalsIgnoreCase("View History")) {
			System.out.println("View History");
			return viewFullHistory();
		} else if (buttonName.equalsIgnoreCase("Upload")) {
			System.out.println("Upload");
			if (uploadDoc == null) {
				return SUCCESS;
			}
			//Checking Server access for file uploading 
			String tempdir = propertyInfo.getValue("BASE_TEMP_FOLDER");
			String tempBaseFolder = propertyInfo.getValue("BASE_TEMP_FOLDER");
			Date toDaydate= new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(toDaydate);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH)+1;
			date = cal.get(Calendar.DAY_OF_MONTH);
			int tranNo = docMgmtImpl.getNewDocTranNo(workspaceID);
			File dirpath = new File(tempdir + year+"/"+month+"/"+date+"/" + workspaceID + "/" + nodeId+ "/" + tranNo);
			if (!dirpath.exists() && !dirpath.mkdirs()) {
				addActionMessage("docUpload");
				return INPUT;
		    }
			//end of Checking Server access for file uploading 
			return uploadSourceDoc();
		} else if (buttonName.equalsIgnoreCase("Send Comments")) {
			System.out.println("Send Comments");
			return saveComments();
		} else if (buttonName.equalsIgnoreCase("Set Remark")) {
			updateRemark();
			System.out.println("Send remark" + remark);
			return SUCCESS;
		} else {
			return SUCCESS;
		}

	}

	/*
	 * Function added By Ashmak Shah (16-Nov-09) Will revert back node's history
	 */
	public String revertBackNodeHistory() {
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());

		/**
		 * Insert new node history
		 * */

		DTOWorkSpaceNodeHistory srcNodeHistory = docMgmtImpl
				.getWorkspaceNodeHistorybyTranNo(workspaceID, nodeId,
						tranNoToRevert);

		DTOWorkSpaceNodeHistory newNodeHistory = new DTOWorkSpaceNodeHistory();
		newNodeHistory.setWorkSpaceId(workspaceID);
		newNodeHistory.setNodeId(nodeId);

		int newTranNo = docMgmtImpl.getNewTranNo(workspaceID);
		newNodeHistory.setTranNo(newTranNo);
		newNodeHistory.setFileName(srcNodeHistory.getFileName());
		newNodeHistory.setFileType(srcNodeHistory.getFileType());
		newNodeHistory.setRequiredFlag(srcNodeHistory.getRequiredFlag());
		newNodeHistory.setRemark(srcNodeHistory.getRemark());
		newNodeHistory.setModifyBy(userId);
		newNodeHistory.setStatusIndi('N');
		newNodeHistory.setDefaultFileFormat("");
		newNodeHistory.setFolderName("/" + workspaceID + "/" + nodeId + "/"
				+ newTranNo);
		newNodeHistory.setStageId(srcNodeHistory.getStageId());

		docMgmtImpl.insertNodeHistory(newNodeHistory);

		/**
		 * Insert new node attribute history AND Update node attribute detail
		 * */

		Vector nodeAttrDtl = docMgmtImpl.getNodeAttrDetail(workspaceID, nodeId);

		Vector newWsnahVector = new Vector();
		Vector updateAttrDtl = new Vector();
		String lastSubmittedOpValue = "";

		/*
		 * Get operation value of the node/file when it was last submitted (Used
		 * only for eCTD)
		 */
		DTOWorkSpaceNodeHistory nodeSubHistory = docMgmtImpl
				.getLatestNodeSubHistory(workspaceID, nodeId);

		if (nodeSubHistory.getWorkSpaceId() != null
				&& !nodeSubHistory.getWorkSpaceId().equals("")) {
			Vector nodeSubAttrHistory = docMgmtImpl
					.getWorkspaceNodeAttrHistorybyTranNo(workspaceID, nodeId,
							nodeSubHistory.getTranNo());
			for (Object object : nodeSubAttrHistory) {
				DTOWorkSpaceNodeAttrHistory subAttrHis = (DTOWorkSpaceNodeAttrHistory) object;
				if (subAttrHis.getAttrName().equals("operation")) {
					lastSubmittedOpValue = subAttrHis.getAttrValue();
				}
				/*
				 * Vector subAttrDtl =
				 * docMgmtImpl.getAttrDetailById(subAttrHis.getAttrId());
				 * if(subAttrDtl.size() > 0){ DTOAttributeMst subAttr =
				 * (DTOAttributeMst)subAttrDtl.get(0);
				 * if(subAttr.getAttrName().equals("operation")){
				 * lastSubmittedOpValue = subAttrHis.getAttrValue(); } }
				 */

			}
		}

		for (int i = 0; i < nodeAttrDtl.size(); i++) {
			DTOWorkSpaceNodeAttrDetail objwsnad = (DTOWorkSpaceNodeAttrDetail) nodeAttrDtl
					.get(i);
			DTOWorkSpaceNodeAttrHistory objwsnah = new DTOWorkSpaceNodeAttrHistory();
			objwsnah.setWorkSpaceId(workspaceID);
			objwsnah.setNodeId(nodeId);
			// Set new TranNo.
			objwsnah.setTranNo(newTranNo);
			objwsnah.setAttrId(objwsnad.getAttrId());
			objwsnah.setAttrValue(objwsnad.getAttrValue());
			objwsnah.setModifyBy(userId);

			// Set new operation value.
			if (objwsnad.getAttrName().equals("operation")) {
				if (lastSubmittedOpValue.equals("new")
						|| lastSubmittedOpValue.equals("replace")
						|| lastSubmittedOpValue.equals("append")) {
					objwsnah.setAttrValue("replace");
				} else if (lastSubmittedOpValue.equals("delete")
						|| lastSubmittedOpValue.equals("")) {
					objwsnah.setAttrValue("new");
				}

				// Update Attribute Detail for 'operation'
				DTOWorkSpaceNodeAttrDetail dtoAttrDtl = new DTOWorkSpaceNodeAttrDetail();
				dtoAttrDtl.setWorkspaceId(workspaceID);
				dtoAttrDtl.setNodeId(nodeId);
				dtoAttrDtl.setAttrId(objwsnah.getAttrId());
				dtoAttrDtl.setAttrValue(objwsnah.getAttrValue());
				dtoAttrDtl.setModifyBy(userId);
				updateAttrDtl.add(dtoAttrDtl);
			}
			// Set new value for last modified
			else if (objwsnad.getAttrId() == -999) {

				Timestamp ts = new Timestamp(new Date().getTime());
				objwsnah.setAttrValue(ts.toString());

				// Update Attribute Detail for 'FileLastModified'
				DTOWorkSpaceNodeAttrDetail dtoAttrDtl = new DTOWorkSpaceNodeAttrDetail();
				dtoAttrDtl.setWorkspaceId(workspaceID);
				dtoAttrDtl.setNodeId(nodeId);
				dtoAttrDtl.setAttrId(objwsnah.getAttrId());
				dtoAttrDtl.setAttrValue(objwsnah.getAttrValue());
				dtoAttrDtl.setModifyBy(userId);
				updateAttrDtl.add(dtoAttrDtl);
			}
			newWsnahVector.add(objwsnah);

		}
		docMgmtImpl.updateNodeAttrDetail(updateAttrDtl);
		docMgmtImpl.insertNodeAttrHistory(newWsnahVector);

		/*
		 * Insert new node version history
		 */

		DTOWorkSpaceNodeVersionHistory nodeVersionHistory = new DTOWorkSpaceNodeVersionHistory();
		nodeVersionHistory.setWorkspaceId(workspaceID);
		nodeVersionHistory.setNodeId(nodeId);
		nodeVersionHistory.setTranNo(newTranNo);
		nodeVersionHistory.setPublished('N');
		nodeVersionHistory.setDownloaded('N');
		nodeVersionHistory.setModifyBy(userId);
		nodeVersionHistory.setExecutedBy(userId);
		nodeVersionHistory.setUserDefineVersionId("");
		docMgmtImpl.insertWorkspaceNodeVersionHistory(nodeVersionHistory);

		/* Copy Actual File */
		DTOWorkSpaceMst workspace = docMgmtImpl.getWorkSpaceDetail(workspaceID);
		File sourceLocation = new File(workspace.getBaseWorkFolder()
				+ srcNodeHistory.getFolderName() + "/"
				+ srcNodeHistory.getFileName());
		File targetLocation = new File(workspace.getBaseWorkFolder()
				+ newNodeHistory.getFolderName() + "/"
				+ newNodeHistory.getFileName());

		FileManager fileManager = new FileManager();
		fileManager.copyDirectory(sourceLocation, targetLocation);

		return SUCCESS;
	}

	/*
	 * Function added By Ashmak Shah (16-Nov-09) Will give node's history to
	 * display
	 */
	/*public String viewFullHistory() {
		ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
				.showFullNodeHistory(workspaceID, nodeId);*/
	public String viewFullHistory() {
		ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
				.showFullNodeHistoryWithVoidFiles(workspaceID, nodeId);

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

		for (int i = 0; i < fullNodeHistory.size(); i++) {
			DTOWorkSpaceNodeHistory dto = (DTOWorkSpaceNodeHistory) fullNodeHistory
					.get(i);
			dto.setHistoryDocumentSize(FileManager.getFileSize(dto
					.getBaseWorkFolder()
					+ dto.getFolderName() + "/" + dto.getFileName()));
		}
		return "ViewHistory";
	}

	public String uploadSourceDoc() {

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String sourceDocPath = propertyInfo.getValue("SOURCE_DOC_FOLDER");
		String tempDir = propertyInfo.getValue("BASE_TEMP_FOLDER");
		autoMail = propertyInfo.getValue("AutoMail");
		uploadDocFileName = uploadDocFileName.replace(' ', '_');

		if (uploadDoc == null) {
			return SUCCESS;
		}

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

		return SUCCESS;
	}

	public String changeStatus() throws MalformedURLException {
		
		int stageId = docMgmtImpl.getStageIdfromWSHistory(workspaceID,nodeId);
		System.out.println("stageId="+stageId);
		
		if(stageId==0){
			htmlContent="Document is Send back by other user.";
			System.out.println("******Document is Send back by other user.*****");
		}else{
		
		
		String currentSeq = docMgmtImpl.getCurrentSeq(workspaceID, nodeId);
		System.out.println("currentSeq="+currentSeq);
			
		
		if(stageCode==-1 || (stageId==100 && !currentSeq.isEmpty())){
			return SUCCESS;
		}
		
		int MaxtranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
		int tranNo = docMgmtImpl.getNewTranNo(workspaceID);
		DTOWorkSpaceNodeHistory dtownd = docMgmtImpl.getWorkspaceNodeHistorybyTranNo(workspaceID, nodeId, MaxtranNo);
		dtownd.setStageId(stageCode);
		dtownd.setModifyBy(usercode);
		dtownd.setTranNo(tranNo);
	if(!remark.isEmpty() && !remark.equals("")){
		dtownd.setRemark(remark);
	}
	else{
		dtownd.setRemark("");
	}
		dtownd.setDefaultFileFormat("");
		String roleCode = docMgmtImpl.getRoleCode(workspaceID, usercode);
		dtownd.setRoleCode(roleCode);
		dtownd.setFileSize(dtownd.getFileSize());
		//docMgmtImpl.insertNodeHistory(dtownd);
		
		docMgmtImpl.insertNodeHistoryWithRoleCode(dtownd);
		
		if(stageCode==0){
			DTOWorkSpaceNodeHistory wsndForOffice =new DTOWorkSpaceNodeHistory();
			wsndForOffice.setWorkSpaceId(workspaceID);
			wsndForOffice.setNodeId(nodeId);
			//wsndForOffice.setDocTranNo(nodeDocHistory.get(0).getDocTranNo());
			int mode=2;
			docMgmtImpl.insertworkspacenodehistoryToUpdate(wsndForOffice,mode);
			
			//soft deleting records if any doc goes to send back stage
			
			String Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();
			if(Automated_Doc_Type!=null)
				docMgmtImpl.deleteTraceblilityMatrixDoc(workspaceID,nodeId,Automated_Doc_Type);
			//deleting ends
			
		}
		
		if(stageCode!=0){
		if(eSign.equalsIgnoreCase("Y")){
			Vector <DTOWorkSpaceNodeHistory> dto;
			dto =docMgmtImpl.getUserSignatureDetail(usercode);
			if(dto.size()>0){
				String uuId = dto.get(0).getUuId();
				int signtrNo = dto.get(0).getSignTranNo();
				docMgmtImpl.UpdateNodeHistoryForESign(workspaceID,nodeId,uuId,signtrNo);
				 roleCode = docMgmtImpl.getRoleCode(workspaceID, usercode);
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
		dto1.setModifyBy(usercode);
		dto1.setStatusIndi('D');
		dto1.setMode(2);
		docMgmtImpl.insertIntofileopenforsign(dto1);
		}
		//(String wsId,int nodeId,int tranNo,int iStageId,int userCode,int Mode)
		String flag="";
		if(stageCode==0){
			docMgmtImpl.insertAssignedNodeRight(workspaceID,nodeId,tranNo,stageCode,usercode,2,flag);
		}
		else
		{
			docMgmtImpl.insertAssignedNodeRight(workspaceID,nodeId,tranNo,stageCode,usercode,1,flag);
			
			Timestamp signOffDate=docMgmtImpl.getAssignNodeRightsData(workspaceID,nodeId,stageCode).get(0).getModifyOn();
			String attrValues=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
			
			if(attrValues != null && !attrValues.equals("")){				
					int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, nodeId);
					getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(workspaceID,nodeId,parentNodeIdforAdjustDate,usercode,stageCode);
					
					//String s[]=attrVal.split("/");
					
					//LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
					LocalDateTime date=signOffDate.toLocalDateTime();
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
		}
		// update tran no in attribute history
		docMgmtImpl.UpdateTranNoForStageInAttrHistory(workspaceID, nodeId,
				tranNo);
		// /docMgmtImpl.updateStageStatus(dtownd);
		
		if(autoMail.equalsIgnoreCase("Yes") && stageCode==0){
			 StageWiseMailReport stageWiseMail = new StageWiseMailReport();
			 //stageWiseMail.StageWiseMail(workspaceID,nodeId,"Send back",10);
			 stageWiseMail.StageWiseMailNewFormate(workspaceID,nodeId,"Send back",10); 
			 System.out.println("Mail Sent....");
	      }
		
		if(autoMail.equalsIgnoreCase("Yes") && stageCode==20){
			
			getCompletedNodeStageDetail = docMgmtImpl.getCompletedNodeStageDetail(workspaceID,nodeId,stageCode);
			
			if(getCompletedNodeStageDetail==true){
				 StageWiseMailReport stageWiseMail = new StageWiseMailReport();
				 //stageWiseMail.StageWiseMail(workspaceID,nodeId,"Reviewed",100);
				 stageWiseMail.StageWiseMailNewFormate(workspaceID,nodeId,"Reviewed",100);
				 System.out.println("Mail Sent....");
			}
			
		}
		
		//Mail on cycle completion
		//get assigned users
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForSingleDoc(workspaceID, nodeId);
						
				//get users who signed
		Vector<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.getProjectSignOffHistory(workspaceID, nodeId);
				
		if(autoMail.equalsIgnoreCase("Yes") && getUserRightsDetail.size()==tempHistory.size()){
			StageWiseMailReport stageWiseMail = new StageWiseMailReport();
			//stageWiseMail.StageWiseMail(workspaceID,nodeId,"Reviewed",100);
			stageWiseMail.FinishDocMailNewFormate(workspaceID, nodeId,"Finished");
			stageWiseMail.completionMailNewFormate(workspaceID,nodeId,"Created",10);
			System.out.println("Mail Sent....");
		}
		
		//BlockChain HashCode
				String hashCode=docMgmtImpl.generateHashCode(workspaceID,nodeId,stageCode);
		//BlockChain HashCode Ends
		
		}
		return SUCCESS;
	}

	public String ExtendNode() {

		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		String userTypeCode = ActionContext.getContext().getSession().get(
				"usertypecode").toString();
		Vector wsDetail = docMgmtImpl.getWorkspaceDesc(workspaceID);
		if (wsDetail != null) {
			Object[] record = new Object[wsDetail.size()];
			record = (Object[]) wsDetail.elementAt(0);
			if (record != null) {
				project_type = record[5].toString();
			}

		}
		Timestamp ts = new Timestamp(new Date().getTime());
		DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
		dto.setWorkspaceId(workspaceID);
		dto.setNodeId(nodeId);
		dto.setNodeName(nodeName);
		dto.setNodeDisplayName(nodeDisplayName);
		dto.setFolderName(folderName);
		dto.setCloneFlag('N');
		dto.setNodeTypeIndi('N');
		dto.setRequiredFlag('N');
		dto.setCheckOutBy(0);
		dto.setPublishedFlag('Y');
		dto.setRemark("Remark");
		dto.setModifyBy(userId);
		dto.setModifyOn(ts);

		// add Leaf Node for extend
		docMgmtImpl.addChildNode(dto);

		// /update status indi and remark for selected Node
		dto = (DTOWorkSpaceNodeDetail) docMgmtImpl.getNodeDetail(workspaceID,
				nodeId).get(0);

		String folderName = dto.getFolderName();
		int lastIndex = folderName.lastIndexOf(".");
		if (lastIndex != -1) {
			folderName = folderName.substring(0, lastIndex);
		}

		dto.setFolderName(folderName);
		dto.setNodeTypeIndi('E');
		dto.setRemark(nodeDisplayName);
		docMgmtImpl.insertWorkspaceNodeDetail(dto, 2); // update remark as
		// nodedisplayname and
		// NodeTypeIndi as E

		GenerateTree tree = new GenerateTree();
		tree.generateTree(workspaceID, project_type, userId, userGroupCode,
				userTypeCode);
		tree = null;

		DeleteAllTrees objDeleteTrees = new DeleteAllTrees();
		objDeleteTrees.DeleteAllGeneratedTrees(workspaceID);

		return SUCCESS;

	}

	public String editNodeAttribute() {
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		fileExt = propertyInfo.getValue("FileExt");

		DTOWorkSpaceNodeDetail wsnodeAttr = new DTOWorkSpaceNodeDetail();
		wsnodeAttr.setNodeId(nodeId);
		wsnodeAttr.setWorkspaceId(workspaceID);
		wsnodeAttr.setUserGroupCode(usergrpcode);
		wsnodeAttr.setUserCode(usercode);

		/********** added by Jwalin Shah For validation ******************/

		Vector getFolderNames = new Vector();
		// int nodeId1= new Integer(nodeId).intValue();
		int nodeId1 = nodeId;
		boolean anyNonPub = false;
		while (nodeId1 > 1) {
			Vector parentNodeId = docMgmtImpl.getNodeDetail(workspaceID,
					nodeId1);
			
			DTOWorkSpaceNodeDetail dto = (DTOWorkSpaceNodeDetail) parentNodeId
					.get(0);
			int newParentNodeId = dto.getParentNodeId();

			if (nodeId1 != nodeId) {
				if (dto.getPublishedFlag() == 'N')
					anyNonPub = true;
			}

			/* With Attribute Value */
			Vector nodeAttribute = docMgmtImpl.getNodeAttributes(workspaceID,
					nodeId1);
			String MergeAttributeStr = "";
			if (nodeAttribute.size() > 0) {
				for (int k = 0; k < nodeAttribute.size(); k++) {
					DTOWorkSpaceNodeAttribute obj = (DTOWorkSpaceNodeAttribute) nodeAttribute
							.get(k);
					String attrvalue = obj.getAttrValue();
					if(attrvalue!=null && !attrvalue.equalsIgnoreCase("")){
					// for displaying attrvalue as "-" where there is a space
					attrvalue = attrvalue.trim().replaceAll(" ", "-");
					}
					if (k == 0) {
						//MergeAttributeStr = attrvalue.trim();
						MergeAttributeStr = attrvalue;
					} else {
						//MergeAttributeStr = MergeAttributeStr + "-"+ attrvalue.trim();
						MergeAttributeStr = MergeAttributeStr + "-"+ attrvalue;
					}
				}
				dto
						.setFolderName(dto.getFolderName() + "/"
								+ MergeAttributeStr);
			}

			getFolderNames.addElement(dto.getFolderName());
			nodeId1 = newParentNodeId;
		}
		// System.out.println("size of Vector:::::"+getFolderNames.size());
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < getFolderNames.size(); j++) {
			sb.append(getFolderNames.get(j)).toString();
			if (j < getFolderNames.size() - 1) {
				sb.append("/");
			}

		}

		fullPathName = sb.toString();

		/************************************** ended **********************************/

		attrDtl = docMgmtImpl.getAttributeValueByUserType(workspaceID, nodeId,
				"0003");

		filterDynamicList = filterAttributes(attrDtl, "Dynamic");

		//DTOWorkSpaceMst workSpace = docMgmtImpl.getWorkSpaceDetail(workspaceID);
		DTOWorkSpaceMst workSpace = docMgmtImpl.getWorkSpaceDetailWSList(workspaceID);

		tempBaseFolderReplaced = propertyInfo.getValue("BASE_TEMP_FOLDER");
		checkInOutNode(wsnodeAttr, workSpace.getBaseWorkFolder(),
				tempBaseFolderReplaced);

		// -------------------------get attribute
		// history-------------------------------

		Vector <DTOWorkSpaceNodeDetail>workSpaceNodeDtl = docMgmtImpl.getNodeDetail(workspaceID, nodeId);

		
		finalString = workSpaceNodeDtl.get(0).getNodeDisplayName();
		if(finalString.contains("{")){
		int startingIndex = finalString.indexOf("{");
		int closingIndex = finalString.indexOf("}");
		result1 = finalString.substring(startingIndex , closingIndex+1);
		
		//System.out.println(result1);
		finalString=finalString.replace(result1, "  ");
		System.out.println(finalString);
		}
		else{
			finalString=workSpaceNodeDtl.get(0).getNodeDisplayName();
		}
		
		
		
		isLeafNode = docMgmtImpl.isLeafNodes(wsnodeAttr.getWorkspaceId(),
				wsnodeAttr.getNodeId());

		// If selected Node is LeafNode then isLeafNode = 1
		// else isLeafNode = 0
		if (isLeafNode == 1) {
			lockedFileDetail = docMgmtImpl.getLockedFileDetail(wsnodeAttr
					.getWorkspaceId(), wsnodeAttr.getNodeId(), wsnodeAttr
					.getUserCode());
			nodeHistory = docMgmtImpl.getNodeHistory(workspaceID, nodeId);
			if(nodeHistory.size()>0){
			DTOWorkSpaceNodeHistory dto = (DTOWorkSpaceNodeHistory)nodeHistory.get(nodeHistory.size()-1);
			stageId =dto.getStageId();
			//System.out.println("**********" + stageId);
			}
			historysize = nodeHistory.size();
			System.out.println("NodeHistory Size:" + historysize);
			isNodeLocked = docMgmtImpl
					.isCheckOut(workspaceID, nodeId, usercode);
			checkedOutBy = docMgmtImpl.nodeCheckedOutBy(workspaceID, nodeId);
			submittedNodeHistory = docMgmtImpl.submittedNodeIdDetail(
					workspaceID, nodeId);
			// System.out.println("value of submittedNodehistory:::"+submittedNodeHistory);
			checkDelete = docMgmtImpl.checkDeleteOperation(workspaceID, nodeId);
			// System.out.println("value of checkdelete is :::"+checkDelete);
		}

		// -------------------------------------------------
		attrHistory = docMgmtImpl.getUserDefinedWorkspaceNodeAttrHistory(
				workspaceID, nodeId);

		DTOUserMst userMstFrom = new DTOUserMst();
		userMstFrom.setUserCode(usercode);
		userMstFrom.setUserGroupCode(usergrpcode);
		users = docMgmtImpl.getWorkspaceUserDetail(workspaceID, userMstFrom);

		// -------------------- For user define version
		// --------------------------------

		DTOWorkSpaceNodeVersionHistory dtownvh = new DTOWorkSpaceNodeVersionHistory();
		dtownvh.setWorkspaceId(workspaceID);
		dtownvh.setNodeId(nodeId);

		Vector wnvhDetail = docMgmtImpl.getMaxWsNodeVersionDetail(dtownvh);
		takeVersion = "false";
		statusIndi = "N";

		if (wnvhDetail.size() > 0) {
			DTOWorkSpaceNodeVersionHistory dtowvh = (DTOWorkSpaceNodeVersionHistory) wnvhDetail
					.elementAt(0);

			statusIndi = new Character(dtowvh.getStatusIndi()).toString();
			if (dtowvh.getUserDefineVersionId() == null)
				takeVersion = "true";
			else
				takeVersion = "false";
		} else
			takeVersion = "true";

		// Getting the values for the keyword and description set for the
		// previous file.

		Vector nodeHistory = new Vector();
		nodeHistory = docMgmtImpl.getNodeHistory(workspaceID, nodeId);

		if (nodeHistory.size() > 0) {
			for (int nodehistory = 0; nodehistory < nodeHistory.size(); nodehistory++) {
				DTOWorkSpaceNodeHistory DTO = (DTOWorkSpaceNodeHistory) nodeHistory
						.get(nodehistory);
				int attrId = DTO.getAttriId();
				String attrValue = DTO.getAttrValue();
				if (attrId == 2) {
					// workspaceNodeAttrForm.setKeyWord(attrValue);
				} else if (attrId == 3) {
					// workspaceNodeAttrForm.setDescription(attrValue);
				}
			}
		}

		// DTOWorkSpaceMst dto = docMgmtImpl.getWorkSpaceDetail(workspaceID);
		lastPublishedVersion = workSpace.getLastPublishedVersion();

		/**********************************************************************/
		// If Source Document Uploaded
		String enableSrcDoc = propertyInfo.getValue("UploadSrcDoc");
		if (enableSrcDoc != null && enableSrcDoc.equals("YES")) {
			nodeDocHistory = docMgmtImpl.getLatestNodeDocHistory(workspaceID,
					nodeId);
		} else {
			nodeDocHistory = new ArrayList<DTOWorkspaceNodeDocHistory>();
		}

		/**********************************************************************/
		// for project type.
		//appType = ProjectType.getApplicationType(workSpace.getProjectType());
		appType = workSpace.getProjectCode();
		if (anyNonPub)
			appType = "0002";

		// System.out.println(nodeDisplayName);

		return "EDITNODE";
	}

	public String unLockFiles() {
		String statusIndi = "N";
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String tempBaseFolder = propertyInfo.getValue("BASE_TEMP_FOLDER");
		unLockNode(workspaceID, nodeId, usercode, tempBaseFolder);
		return SUCCESS;
	}

	public String unLockWithoutSaving() {
		DTOWorkSpaceNodeVersionHistory dtownvh = new DTOWorkSpaceNodeVersionHistory();
		dtownvh.setWorkspaceId(workspaceID);
		dtownvh.setNodeId(nodeId);

		Vector wnvhDetail = docMgmtImpl.getMaxWsNodeVersionDetail(dtownvh);
		String statusIndi = "N";

		if (wnvhDetail.size() > 0) {
			DTOWorkSpaceNodeVersionHistory dtowvh = (DTOWorkSpaceNodeVersionHistory) wnvhDetail
					.elementAt(0);
			statusIndi = new Character(dtowvh.getStatusIndi()).toString();
		}

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String tempBaseFolder = propertyInfo.getValue("BASE_TEMP_FOLDER");
		if (unLockNodeWithoutSave(workspaceID, new Integer(nodeId), usercode,
				tempBaseFolder)) {

			// providing data for the input form
			// getDetailsForInputForward(nodeId);

			// return SUCCESS;
		}/*
		 * else {
		 * 
		 * return ERROR; }
		 */

		return SUCCESS;
	}

	public String SaveAttribute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		// ActionContext.getContext().getSession().put("refreshNodeId",new
		// Integer(nodeId));
		// refresh = true;
		Integer attributeId=0;
		String attrName="";
		String attrId="";
		String multiSelectionComboValue="";
		String attrValueId = "";
		String attrType="";
		String AttributeValueForNodeDisplayName = "";
		String tempAttrName="";
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String scriptCodeAutogenreate= propertyInfo.getValue("isScriptCodeAutogenerate");
		Vector<DTOWorkSpaceNodeAttrDetail> getAttrName = new Vector<DTOWorkSpaceNodeAttrDetail>();
		getAttrName = docMgmtImpl.getNodeAttrDetail(workspaceID, nodeId);
		DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0);
		ArrayList<DTOWorkspaceNodeReminderDoneStatus> dtoList = new ArrayList<DTOWorkspaceNodeReminderDoneStatus>();
		Vector<DTOWorkSpaceNodeAttrDetail> attrList = docMgmtImpl
				.getNodeAttrDetail(workspaceID, nodeId);

		for (int i = 1; i < attrCount; i++) {
			multiSelectionComboValue="";
			attrValueId = "attrValueId" + i;
			attrType = "attrType" + i;
			attrId = "attrId" + i;
			 attrName = "attrName" + i;
			if(request.getParameter(attrType)!=null && request.getParameter(attrType).equals("MultiSelectionCombo")){
				String[] outerArray=request.getParameterValues(attrValueId);
				if (outerArray != null) {
                    for (int j = 0; j < outerArray.length; j++) {
                    	multiSelectionComboValue = multiSelectionComboValue + "," + outerArray[j];
                    }
                }
			}
			attributeId = Integer.parseInt(request.getParameter(attrId));
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
							//if (request.getParameter(attrValueId).equals(dtoWorkSpaceNodeAttrDetail.getAttrValue())) {
							if (request.getParameter(attrValueId).equals(dtoWorkSpaceNodeAttrDetail.getAttrValue())
									&& !dtoWorkSpaceNodeAttrDetail.getAttrName().equalsIgnoreCase("Script Code")) {
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
			if(request.getParameter(attrName).equalsIgnoreCase("Project Start Date") && dupFound==false){
				System.out.println(request.getParameter(attrValueId));
				if(request.getParameter(attrValueId)!=""){
					hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
					
					String s[]=request.getParameter(attrValueId).split("/");
					
					LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));

					LocalDateTime startDate=null;
					LocalDateTime endDate = null;
					for(int k=0;k<hoursData.size();k++){
						//System.out.println("Hours : "+hoursData.get(k).getHours());
						DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData
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
						//System.out.println(dtotimeline.getNodeId());
						//System.out.println(dtotimeline.getHours());
						dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						//System.out.println(Timestamp.valueOf(startDate));
						dtotimeline.setEndDate(Timestamp.valueOf(endDate));
						 dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
						//System.out.println(Timestamp.valueOf(endDate));
						//String startDateAsString = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
						//String endDateAsString = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
						//System.out.println("Start Date : "+startDateAsString);
						//System.out.println("End Date : "+endDateAsString);
						
						docMgmtImpl.updateTimelineDatesValue(dtotimeline);
						
					}	
				}
				
				
			}

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
				else{
					if(dtoAttr.getAttrName().equalsIgnoreCase("URS Reference No.") || dtoAttr.getAttrName().equalsIgnoreCase("FS Reference No.")){
						DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();
						wsnadto.setWorkspaceId(workspaceID);
						wsnadto.setNodeId(nodeId);
						wsnadto.setAttrId(attributeId);
						wsnadto.setAttrName(request.getParameter(attrName));
						//wsnadto.setAttrValue(request.getParameter(attrValueId));
						if(request.getParameter(attrType).equals("MultiSelectionCombo")){
							wsnadto.setAttrValue("");
						}
						else{
							wsnadto.setAttrValue(request.getParameter(attrValueId));
						}
						wsnadto.setRemark(attrRemark);
						
						int ucd = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
						wsnadto.setModifyBy(ucd);
						// ///update workspacenode attribute value
						docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);
					}
				}
				continue;// TODO instead of individual attributes, values should
				// be changed together
			}
			DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();

			wsnadto.setWorkspaceId(workspaceID);
			wsnadto.setNodeId(nodeId);
			wsnadto.setAttrId(attributeId);
			wsnadto.setAttrName(request.getParameter(attrName));
			//wsnadto.setAttrValue(request.getParameter(attrValueId));
			if(request.getParameter(attrType).equals("MultiSelectionCombo")){
				wsnadto.setAttrValue(multiSelectionComboValue.substring(1));
			}
			else{
						
				if(wsnadto.getAttrName().equalsIgnoreCase("Script Code") && scriptCodeAutogenreate.equalsIgnoreCase("Yes")){
					 String temp;
		    		 int parentNodeId = docMgmtImpl.getParentNodeId(workspaceID, nodeId);
		    		 DTOWorkSpaceNodeDetail getNodeDetail =  new DTOWorkSpaceNodeDetail();
		    		 getNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, parentNodeId).get(0);
		    		 String nodeName = getNodeDetail.getNodeName();
		    		 int totalPQScripts = docMgmtImpl.getPQScriptsCount(workspaceID, parentNodeId,nodeName);
		 
		    		 DecimalFormat decimalFormat = new DecimalFormat("0000");                
		    		 System.out.println(decimalFormat.format(totalPQScripts));
		    		 if(nodeName.equalsIgnoreCase("PQ Scripts")){
		    			 temp = "PQSC-"+decimalFormat.format(totalPQScripts);
		    		 }
		    		 else if(nodeName.equalsIgnoreCase("OQ Scripts")){
		    			 temp = "OQSC-"+decimalFormat.format(totalPQScripts);
		    		 }
		    		 else{
		    			 temp = "IQSC-"+decimalFormat.format(totalPQScripts);
		    		 }
		    		 
		    		 wsnadto.setAttrValue(temp);
		    	 }
				 else{
					 wsnadto.setAttrValue(request.getParameter(attrValueId));
				 }
			}
			if(wsnadto.getAttrName().equalsIgnoreCase("Type of Signature")){
				if(wsnadto.getAttrValue().equalsIgnoreCase("Physical")){
					docMgmtImpl.UpdateNodeTypeIndi(workspaceID,nodeId,'K');
				}
				else{
					docMgmtImpl.UpdateNodeTypeIndi(workspaceID,nodeId,'N');
				}
			}
			wsnadto.setRemark(attrRemark);
			
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
				dto.setvWorkspaceId(workspaceID);
				dto.setiNodeId(nodeId);
				dto.setiAttrId(Integer.parseInt(request.getParameter(attrId)));
				dto.setiUserCode(usercode);
				dtoList.add(dto);
			}
		}
		docMgmtImpl.markReminderAsUnDone(dtoList);

		// update nodedisplayname of the node
		DTOWorkSpaceNodeDetail wsnd = (DTOWorkSpaceNodeDetail) docMgmtImpl
				.getNodeDetail(workspaceID, nodeId).get(0);
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
			
			for(int k=0;k<getAttrName.size();k++){
			tempAttrName = getAttrName.get(k).getAttrName();	
			if(tempAttrName.equalsIgnoreCase("Script Code") && getAttrName.get(k).getAttrValue().equals("")){
				DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();

				wsnadto.setWorkspaceId(workspaceID);
				wsnadto.setNodeId(nodeId);
				wsnadto.setAttrId(attributeId);
				wsnadto.setAttrName(request.getParameter(attrName));
				//wsnadto.setAttrValue(request.getParameter(attrValueId));
				if(request.getParameter(attrType).equals("MultiSelectionCombo")){
					wsnadto.setAttrValue(multiSelectionComboValue.substring(1));
				}
				else{
							
					 if(wsnadto.getAttrName().equalsIgnoreCase("Script Code") && scriptCodeAutogenreate.equalsIgnoreCase("Yes")){
			    		 String temp;
			    		 int parentNodeId = docMgmtImpl.getParentNodeId(workspaceID, nodeId);
			    		 DTOWorkSpaceNodeDetail getNodeDetail =  new DTOWorkSpaceNodeDetail();
			    		 getNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, parentNodeId).get(0);
			    		 String nodeName = getNodeDetail.getNodeName();
			    		 int totalPQScripts = docMgmtImpl.getPQScriptsCount(workspaceID, parentNodeId,nodeName);
			 
			    		 DecimalFormat decimalFormat = new DecimalFormat("0000");                
			    		 System.out.println(decimalFormat.format(totalPQScripts));
			    		 if(nodeName.equalsIgnoreCase("PQ Scripts")){
			    			 temp = "PQSC-"+decimalFormat.format(totalPQScripts);
			    		 }
			    		 else if(nodeName.equalsIgnoreCase("OQ Scripts")){
			    			 temp = "OQSC-"+decimalFormat.format(totalPQScripts);
			    		 }
			    		 else{
			    			 temp = "IQSC-"+decimalFormat.format(totalPQScripts);
			    		 }
			    		 wsnadto.setAttrValue(temp);
			    	 }
					 else{
						 wsnadto.setAttrValue(request.getParameter(attrValueId));
					 }
				}
				wsnadto.setRemark(attrRemark);
				
				int ucd = Integer.parseInt(ActionContext.getContext().getSession()
						.get("userid").toString());

				wsnadto.setModifyBy(ucd);

				// ///update workspacenode attribute value
				docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);
			}
			}
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
			for(int k=0;k<getAttrName.size();k++){
				tempAttrName = getAttrName.get(k).getAttrName();	
				if(tempAttrName.equalsIgnoreCase("Script Code") && getAttrName.get(k).getAttrValue().equals("")){
					DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();

					wsnadto.setWorkspaceId(workspaceID);
					wsnadto.setNodeId(nodeId);
					wsnadto.setAttrId(attributeId);
					wsnadto.setAttrName(request.getParameter(attrName));
					//wsnadto.setAttrValue(request.getParameter(attrValueId));
					if(request.getParameter(attrType).equals("MultiSelectionCombo")){
						wsnadto.setAttrValue(multiSelectionComboValue.substring(1));
					}
					else{
								
						 if(wsnadto.getAttrName().equalsIgnoreCase("Script Code") && scriptCodeAutogenreate.equalsIgnoreCase("Yes")){
				    		 String temp;
				    		 int parentNodeId = docMgmtImpl.getParentNodeId(workspaceID, nodeId);
				    		 DTOWorkSpaceNodeDetail getNodeDetail =  new DTOWorkSpaceNodeDetail();
				    		 getNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, parentNodeId).get(0);
				    		 String nodeName = getNodeDetail.getNodeName();
				    		 DTOWorkSpaceNodeDetail currentNodeDetail =  new DTOWorkSpaceNodeDetail();
				    		 currentNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0); 
				    		 int nodeNo = currentNodeDetail.getNodeNo();
				    		 int totalPQScripts = docMgmtImpl.getPQScriptsCount(workspaceID, parentNodeId,nodeName);
				 
				    		 DecimalFormat decimalFormat = new DecimalFormat("0000");                
				    		 System.out.println(decimalFormat.format(totalPQScripts));
				    		 if(nodeName.equalsIgnoreCase("PQ Scripts")){
				    			 if(nodeNo==1){
				    				 temp = "PQSC-"+decimalFormat.format(1); 
				    			 } 
				    			 else{
				    				 temp = "PQSC-"+decimalFormat.format(totalPQScripts);
				    			 }
				    		 }
				    		 else if(nodeName.equalsIgnoreCase("OQ Scripts")){
				    			 if(nodeNo==1){
				    				 temp = "OQSC-"+decimalFormat.format(1); 
				    			 } 
				    			 else{
				    				 temp = "OQSC-"+decimalFormat.format(totalPQScripts);
				    			 }
				    		 }
				    		 else{
				    			 if(nodeNo==1){
				    				 temp = "IQSC-"+decimalFormat.format(1); 
				    			 } 
				    			 else{
				    				 temp = "IQSC-"+decimalFormat.format(totalPQScripts);
				    			 }
				    		 }
				    		 wsnadto.setAttrValue(temp);
				    	 }
						 else{
							 wsnadto.setAttrValue(request.getParameter(attrValueId));
						 }
					}
					wsnadto.setRemark(attrRemark);
					
					int ucd = Integer.parseInt(ActionContext.getContext().getSession()
							.get("userid").toString());

					wsnadto.setModifyBy(ucd);

					// ///update workspacenode attribute value
					docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);
				}
				}
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
		
		
		/*
		 * DTOWorkSpaceNodeDetail wsnd = new DTOWorkSpaceNodeDetail();
		 * wsnd.setWorkspaceId(workspaceID); wsnd.setNodeId(nodeId);
		 * 
		 * docMgmtImpl.updateNodeDisplayNameAccToEctdAttribute(wsnd,
		 * AttributeValueForNodeDisplayName);
		 */

		/*
		 * ///Generate ECTD tree GenerateWorkspaceEctdAttrTree tree= new
		 * GenerateWorkspaceEctdAttrTree();
		 * 
		 * Integer
		 * usergrpcode=Integer.parseInt(ActionContext.getContext().getSession
		 * ().get("usergroupcode").toString()); Integer
		 * usercode=Integer.parseInt
		 * (ActionContext.getContext().getSession().get("userid").toString());
		 * 
		 * String
		 * userType=ActionContext.getContext().getSession().get("usertypecode"
		 * ).toString();
		 * 
		 * 
		 * 
		 * 
		 * 
		 * DTOWorkSpaceMst
		 * workSpaceMst=docMgmtImpl.getWorkSpaceDetail(workspaceID);
		 * 
		 * //generate tree text file
		 * tree.generateTree(workspaceID,workSpaceMst.getProjectCode
		 * (),usercode,usergrpcode,userType); tree=null;
		 */
				
		/*nodeDisplayName = wsnd.getNodeDisplayName();
		if(nodeDisplayName.contains("{"))
		{
			nodeDisplayName = nodeDisplayName.replace("{","(");
		}*/
		return SUCCESS;
	}

	public String saveComments() {
		String workspaceID = ActionContext.getContext().getSession().get(
				"ws_id").toString();
		// int
		// usergrpcode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int usercode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String sendEmail = propertyInfo.getValue("SEND_EMAIL");

		DTOWorkSpaceMst dtows = docMgmtImpl.getWorkSpaceDetail(workspaceID);
		DTOWorkSpaceNodeDetail dtowsnd = (DTOWorkSpaceNodeDetail) docMgmtImpl
				.getNodeDetail(workspaceID, nodeId).get(0);

		DTOUserMst dtoum = new DTOUserMst();
		String sender = ActionContext.getContext().getSession().get("username")
				.toString();
		String uuid =UUID.randomUUID().toString();
		System.out.println("Unique Id:"+ uuid);
		
		if (userCodedtl == null) {

			// System.out.println(userCodedtl + "************");
		}
		if (userCodedtl != null) {
			for (int i = 0; i < userCodedtl.length; i++) {

				// DTOWorkSpaceNodeDetail objWorkSpaceNodeDetail = new
				// DTOWorkSpaceNodeDetail();
				dtoum = docMgmtImpl.getUserByCode(userCodedtl[i]);

				/*
				 * objWorkSpaceNodeDetail.setWorkspaceId(workspaceID);
				 * objWorkSpaceNodeDetail.setNodeId(nodeId);
				 * objWorkSpaceNodeDetail
				 * .setUserGroupCode(dtoum.getUserGroupCode());
				 * objWorkSpaceNodeDetail.setUserCode(userCodedtl[i]); String
				 * subjectId
				 * =docMgmtImpl.insertForumComment(objWorkSpaceNodeDetail,
				 * forumTextArea ,usercode);
				 */

				DTOForumDtl comment = new DTOForumDtl();
				comment.setWorkspaceId(workspaceID);
				comment.setNodeId(nodeId);
				comment.setReceiverGroupCode(dtoum.getUserGroupCode());
				comment.setReceiverUserCode(userCodedtl[i]);
				comment.setSubjectDesc(forumTextArea);
				comment.setFileName(uploadDocCommentFileName);
				comment.setForumHdrModifyBy(usercode);
				comment.setUuid(uuid);
				String subjectId = docMgmtImpl.insertForumDetails(comment, 1);

				if (sendEmail != null && sendEmail.equalsIgnoreCase("YES")) {
					sendEmail(dtows, dtowsnd, sender);
				}
				if (uploadDocComment != null) {
					File commentDocDir = propertyInfo.getDir("CommentDoc");
					FileManager fileManager = new FileManager();

					/*
					 * Target path should be same as below while retrieving
					 * target document
					 */
					String targetFilePath = comment.getWorkspaceId()
							+ File.separator + comment.getNodeId()
							+ File.separator + subjectId + File.separator
							+ uploadDocCommentFileName;

					File target = new File(commentDocDir, targetFilePath);
					fileManager.copyDirectory(uploadDocComment, target);

				}
			}
		}
		return SUCCESS;
	}

	String updateRemark() {
		return SUCCESS;
	}

	// /service layer functions
	void sendEmail(DTOWorkSpaceMst dtows, DTOWorkSpaceNodeDetail dtowsnd,
			String sender) {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new MyAuth());

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("nirav.parmar@sarjen.com"));

			InternetAddress[] address = { new InternetAddress(
					"nirav.parmar@sarjen.com") };

			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject("KnowledgeNET [" + dtows.getWorkSpaceDesc() + "]");

			msg.setSentDate(new Date());
			msg.setText("Project: " + dtows.getWorkSpaceDesc()
					+ "\nNode\\Document: " + dtowsnd.getNodeDisplayName()
					+ "\nSender : " + sender + "\n\nComment: " + forumTextArea);

			// Adding a single attachment
			// fds=new FileDataSource("D:\\Documents\\FOP.ppt");
			// mimeAttach=new MimeBodyPart();
			// mimeAttach.setDataHandler(new DataHandler(fds));
			// setFileName(fds.getName());
			// mailBody.addBodyPart(mimeAttach);
			// msg.setContent(mailBody);

			Transport.send(msg);
			System.out.println("Message Sent Successfully ");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public void checkInOutNode(DTOWorkSpaceNodeDetail detail,
			String wsBaseFolder, String tempBaseFolder) {

		String workSpaceId = detail.getWorkspaceId();
		int nodeId = detail.getNodeId();

		Vector workSpaceNodeAttr = new Vector();
		workSpaceNodeAttr = docMgmtImpl.getAttrForNode(workspaceID, nodeId);

		if (workSpaceNodeAttr.size() > 0) {
			DTOWorkSpaceNodeAttrDetail attrDetail = (DTOWorkSpaceNodeAttrDetail) workSpaceNodeAttr
					.elementAt(0);

			String fileName = attrDetail.getFileName();
			int fileTranNo = attrDetail.getTranNo();

			DTOCheckedoutFileDetail dtocheck = new DTOCheckedoutFileDetail();

			dtocheck.setFileName(fileName);
			dtocheck.setPrevTranNo(fileTranNo - 1);
			dtocheck.setIsNodeLocked('Y');
			dtocheck.setStatusIndi('N');
			dtocheck.setWorkSpaceId(detail.getWorkspaceId());
			dtocheck.setNodeId(detail.getNodeId());
			dtocheck.setModifyBy(detail.getUserCode());

			int tranNo = docMgmtImpl.checkInOutNode(dtocheck);

			if (fileTranNo != 0) {
				createTempDirForLocking(tempBaseFolder,
						detail.getWorkspaceId(), detail.getNodeId(), tranNo,
						fileName, fileTranNo, wsBaseFolder);
			}
		}

	}

	public void createTempDirForLocking(String tempBaseFolder, String wsId,
			int nodeId, int tranNo, String fileName, int fileTranNo,
			String wsBaseFolder) {
		// System.out.println("tempBaseFolder: " + tempBaseFolder);
		File tempFolder = new File(tempBaseFolder + "//" + wsId + "//" + nodeId
				+ "//" + tranNo);

		if (!tempFolder.exists()) {
			if (tempFolder.mkdirs()) {

				File srcFile = new File(wsBaseFolder + "//" + wsId + "//"
						+ nodeId + "//" + fileTranNo + "//" + fileName);
				File destFile = new File(tempBaseFolder + "//" + wsId + "//"
						+ nodeId + "//" + tranNo + "//" + fileName);
				FileCopy fc = new FileCopy(srcFile, destFile);

			} else {
				System.out.println("Temp dirs cannot be created...Source"
						+ wsBaseFolder + "//" + wsId + "//" + nodeId + "//"
						+ fileTranNo + "//" + fileName + " ---------dest"
						+ tempBaseFolder + "//" + wsId + "//" + nodeId + "//"
						+ tranNo + "//" + fileName);

			}
		}
	}

	public boolean unLockNode(String wsId, int ndId, int uId,
			String tempBaseFolder) {

		// System.out.println("--------- UNLOCK NODE CALLED IN UnloackFileService -------- ");

		// System.out.println("INside unLockNode()w,n,u:"+workspaceID+","+nodeId+","+usercode);
		Vector lockedFileDetail = docMgmtImpl.getLockedFileDetail(workspaceID,
				nodeId, usercode);

		DTOCheckedoutFileDetail checkedoutFileDetail = new DTOCheckedoutFileDetail();
		if (lockedFileDetail.size() > 0) {
			checkedoutFileDetail = new DTOCheckedoutFileDetail();
			checkedoutFileDetail = (DTOCheckedoutFileDetail) lockedFileDetail
					.elementAt(0);
		}
		return unlockFilesData(checkedoutFileDetail, tempBaseFolder);
	}

	public boolean unlockFilesData(
			DTOCheckedoutFileDetail checkedoutFileDetail, String tempBaseFolder) {

		String baseWorkFolder = checkedoutFileDetail.getBaseWorkFolder();
		String wsId = checkedoutFileDetail.getWorkSpaceId();
		int nodeId = checkedoutFileDetail.getNodeId();
		int tranNo = checkedoutFileDetail.getTranNo();
		int fileTranNo = checkedoutFileDetail.getPrevTranNo();
		String fileName = checkedoutFileDetail.getFileName();

		File tempFolder = new File(tempBaseFolder + "//" + wsId + "//" + nodeId
				+ "//" + tranNo);
		File destFile = new File(baseWorkFolder + "//" + wsId + "//" + nodeId
				+ "//" + fileTranNo + "//" + fileName);
		File srcFile = new File(tempBaseFolder + "//" + wsId + "//" + nodeId
				+ "//" + tranNo + "//" + fileName);

		// System.out.println("-----source path of file-----" + srcFile);
		// System.out.println("-----Dest. file path-------" + destFile);

		//FileCopy fc = new FileCopy(srcFile, destFile);
		try {
			docMgmtImpl.unLockFiles(wsId, nodeId, tranNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("-----tempFolder-------" + tempFolder);
		//srcFile.delete();
		//tempFolder.delete();
		// System.out.println("-----Source File And Temp Folder deleted-------");
		return true;

	}

	private boolean unlockFilesWithoutSave(
			DTOCheckedoutFileDetail checkedoutFileDetail, String tempBaseFolder) {

		String wsId = checkedoutFileDetail.getWorkSpaceId();
		int nodeId = checkedoutFileDetail.getNodeId();
		int tranNo = checkedoutFileDetail.getTranNo();
		String fileName = checkedoutFileDetail.getFileName();

		File tempFolder = new File(tempBaseFolder + "//" + wsId + "//" + nodeId
				+ "//" + tranNo);
		File tempFile = new File(tempBaseFolder + "//" + wsId + "//" + nodeId
				+ "//" + tranNo + "//" + fileName);

		try {

			docMgmtImpl.unLockFiles(wsId, nodeId, tranNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tempFile.delete();
		tempFolder.delete();

		return true;
	}

	public boolean unLockNodeWithoutSave(String wsId, Integer nodeId,
			Integer userId, String tempBaseFolder) {

		Vector lockedFileDetail = docMgmtImpl.getLockedFileDetail(wsId, nodeId
				.intValue(), userId.intValue());
		DTOCheckedoutFileDetail checkedoutFileDetail = (DTOCheckedoutFileDetail) lockedFileDetail
				.elementAt(0);
		return unlockFilesWithoutSave(checkedoutFileDetail, tempBaseFolder);

	}

	private ArrayList<Object[]> filterAttributes(
			Vector<DTOAttributeValueMatrix> attrDtl, String attrType) {
		if (attrDtl == null || attrType == null) {
			return null;
		}

		ArrayList<Object[]> filterAttributeList = new ArrayList<Object[]>();
		for (int i = 0; i < attrDtl.size(); i++) {
			DTOAttributeValueMatrix dtoAttributeValueMatrix = attrDtl.get(i);

			if (attrType.equals("AutoCompleter")
					&& dtoAttributeValueMatrix.getAttrType().equals(attrType)) {

				ArrayList<String> attrValueList = new ArrayList<String>();
				for (int j = 0; j < attrDtl.size(); j++) {
					DTOAttributeValueMatrix dtoAttributeValueMatrix_ForValue = attrDtl
							.get(j);
					if (dtoAttributeValueMatrix.getAttrId() == dtoAttributeValueMatrix_ForValue
							.getAttrId()) {

						attrValueList.add(dtoAttributeValueMatrix_ForValue
								.getAttrMatrixValue());
						attrDtl.remove(j--);
					}
				}

				attrDtl.add(i, dtoAttributeValueMatrix);
				filterAttributeList.add(new Object[] {
						dtoAttributeValueMatrix.getAttrId(), attrValueList });
			}

			if (attrType.equals("Dynamic")
					&& dtoAttributeValueMatrix.getAttrType().equals(attrType)) {

				ArrayList<String> attrValueList = new ArrayList<String>();
				DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix = new DTOAttrReferenceTableMatrix();
				dtoAttrReferenceTableMatrix.setiAttrId(dtoAttributeValueMatrix
						.getAttrId());
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

						}
					}
				}

				filterAttributeList.add(new Object[] {
						dtoAttributeValueMatrix.getAttrId(), attrValueList });
			}
		}

		return filterAttributeList;
	}
public int DocToPdf(String sourceFile, String destFile){

	String flag="";
	int exitVal=0;
	
	try {
		
		String WordToPDFFile;
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		WordToPDFFile = propertyInfo.getValue("WordToPDFFile");
		String command = WordToPDFFile + " " + sourceFile + " "
				+ destFile + " ";
		
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
			
		} catch (Exception e) {
			System.out.println("Error...");
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	if(exitVal>0){
		exitVal=exitVal-1;
	}
	return exitVal;
	
}
public String DownloadZipFolder() throws SQLException, DocumentException, IOException{
		
		File fileToDownload = new File(DownloadFile);
		try {
			inputStream = new FileInputStream(fileToDownload);
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileName = fileToDownload.getName();
		contentLength = fileToDownload.length();
		System.out.println("Downloaded");
	    
		return "SUCCESS";

	}
  
public String convertAndDownload() throws SQLException, IOException, DocumentException, ParseException {
	wordToPdf wtp=new wordToPdf();
	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	baseSrcPath = propertyInfo.getValue("DoToPdfPath");
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	/*String flag="true";
	int exitFlag=0;*/
	showAttributesInPublishPage = propertyInfo.getValue("AttachAttributesInPublishPage");
	
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
	//String ManualSignatureConfig = propertyInfo.getValue("ManualSignatureConfig");
	String ManualSignatureConfig = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, 1, "ManualSignature").getAttrValue();
	DTOWorkSpaceMst wsDetail1 =docMgmtImpl.getWorkSpaceDetail(workspaceID);
	Vector<DTOWorkSpaceNodeDetail> getNodeDetail1 = new Vector<DTOWorkSpaceNodeDetail>();
	getNodeDetail1 = docMgmtImpl.getNodeDetail(workspaceID, nodeId);
	
	char nodeTypeIndi1 = getNodeDetail1.get(0).getNodeTypeIndi();
	if(!wsDetail1.getProjectCode().equals("0003") && nodeTypeIndi1!='K' && ManualSignatureConfig!=null && ManualSignatureConfig.equalsIgnoreCase("Yes")){
	//if(ManualSignatureConfig.equalsIgnoreCase("Yes")){
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
		String LTRSingleDocFooterFormat=propertyInfo.getValue("LTRSingleDocFooterFormat");
		userName = ActionContext.getContext().getSession().get("username").toString();
		if(LTRSingleDocFooterFormat.equals("Yes")){
		for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
		PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);
		 
			//if(LTRSingleDocFooterFormat.equals("Yes")){
               pdfContentByte.setFontAndSize(BaseFont.createFont
                       (BaseFont.TIMES_ROMAN, //Font name
                         BaseFont.CP1257, //Font encoding
                        BaseFont.EMBEDDED //Font embedded
                        ) 
						         , 8); // set font and size
						pdfContentByte.setTextMatrix(200,7);
						pdfContentByte.showText("This is system generated document, hence No Handwritten signature is required");	
			//}
		}

		}
		
		pdfStamper.close();
	      pdfReader.close();
		}
		catch (Exception e) {
		System.out.println(e.toString());
		htmlContent=e.toString();
		return "html";
		}

		System.out.println("Uploaded File Path: >> "+ inputFilePath);

		System.out.println("After Add Watermark File Path: >> "+ outFile);
		String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
		DownloadFolderPath=baseSrcPath + "/" + workspaceID + "/"
				+ nodeId + "/" + uuid+ "/" + fileNameWithOutExt+"_Sign.pdf";
		File original=new File(inputFilePath.getAbsolutePath());
		File fileToCopy=new File(outFile);
		//Files.copy(fileToCopy.toPath(),original.toPath() ,StandardCopyOption.REPLACE_EXISTING);
		File srcFile = new File(fileToCopy.toString());
		
		String fName = previousHistory.get(0).getFileName();
		File trnFolder = new File(nodeRec[9]+previousHistory.get(0).getFolderName());
		
		File destFile = new File(trnFolder+"\\"+fileNameWithOutExt+"_Sign.pdf");
		//FileUtils.copyFile(srcFile, destFile);
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
	    } finally {
	        is.close();
	        os.close();
	    }

		htmlContent=DownloadFolderPath;
		
		String LTRSingleDocFooterFormat=propertyInfo.getValue("LTRSingleDocFooterFormat");
		userName = ActionContext.getContext().getSession().get("username").toString();
		/*if(LTRSingleDocFooterFormat.equals("Yes")){
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		    //SimpleDateFormat  dtf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS z"); 
		    
		    Date date = new Date();
		    Timestamp ts=new Timestamp(date.getTime()); 
		    time = docMgmtImpl.TimeZoneConversion(ts,locationName,countryCode);
		    String dtf = "";
		    if(countryCode.equalsIgnoreCase("IND")){
		     dtf = time.get(0);
		    }else{
		     dtf = time.get(1);
		    }		      
	           OutputStream os = new FileOutputStream(new File(outputFilePath.toString()));
	 
	           PdfReader pdfReader = new PdfReader(inputFilePath.toString());
	           PdfStamper pdfStamper = new PdfStamper(pdfReader, os);
	           	
	           for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
					 PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);
	 
	                  // Add text in existing PDF
	                  pdfContentByte.beginText();
	                  pdfContentByte.setFontAndSize(BaseFont.createFont
	                                                    (BaseFont.TIMES_ROMAN, //Font name
	                                                      BaseFont.CP1257, //Font encoding
	                                                     BaseFont.EMBEDDED //Font embedded
	                                                     )
	                               , 8); // set font and size
	                  pdfContentByte.setTextMatrix(5,16);
	                  pdfContentByte.showText("Printed By : "+userName); // add the text
	                  
	                  pdfContentByte.setTextMatrix(5,7);
	                  pdfContentByte.showText("Print Date & Time :"+dtf);
	                  
	                  pdfContentByte.setTextMatrix(200,7);
	                  pdfContentByte.showText("This is system generated document, hence No Handwritten signature is required");
	                  pdfContentByte.endText();
	                 
	           }
	 
	           pdfStamper.close(); //close pdfStamper	
		}*/
		
		//Files.copy(fileToCopy.toPath(),original.toPath() ,StandardCopyOption.REPLACE_EXISTING);
		 //FileUtils.copyFile(fileToCopy, original);
		
		PdfReader reader = new PdfReader(fileToCopy.toString());
		 inputFilePath = new File(outFile); // Existing file
        outputFilePath = new File(outFile); // New file

       /*   OutputStream fos = new FileOutputStream(new File(outputFilePath));

          PdfStamper pdfStamper = new PdfStamper(reader, fos);
		 int n=reader.getNumberOfPages();
		 
		 
		 for(int i=0;i<n;i++){
			 
		 }*/
		 
        //Other publishing logic
        
        String srcFileExt = outFile.substring(outFile.lastIndexOf(".") +1);
        File pathForAttribute = null;
			File path=new File(baseSrcPath + "/" + workspaceID + "/"
					+ nodeId + "/" + uuid +"/Temp.pdf");
			OutputStream pathfos = new FileOutputStream(path);
			if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
			pathForAttribute=new File(baseSrcPath + "/" + workspaceID + "/"
					+ nodeId + "/" + uuid +"/attr.pdf");
			}
		 
		 //for attach attribute list in PDF
		
		 Vector<DTOWorkSpaceNodeAttribute> AttrDtl = null;
		 Vector<DTOWorkSpaceNodeAttribute> AttrDtlForPageSetting = null;
		 
		 if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
			AttrDtl=docMgmtImpl.getAttrDtl(workspaceID,nodeId);
	 if(AttrDtl.size()>0){
		  Document d2 = new Document();
		  OutputStream attrfos = new FileOutputStream(pathForAttribute);
			
	      PdfWriter pdfWriter1 = PdfWriter.getInstance(d2, attrfos);
	      Rectangle rectangle1 = new Rectangle(30, 30, 550, 800);
	      pdfWriter1.setBoxSize("rectangle", rectangle1);
	      
	      	d2.open();
			Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
			
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
		
			PdfPCell cell2 = new PdfPCell(new Phrase(Element.ALIGN_CENTER,"File Attributes"));
			cell2.setColspan(2);
			
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_CENTER);
			table1.addCell(cell2);
			
			
			PdfPCell An = new PdfPCell(new Phrase("Attribute Name"));
			PdfPCell Av = new PdfPCell(new Phrase("Attribute Value"));
			Av.setHorizontalAlignment(Element.ALIGN_CENTER);
			An.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(An);
			table1.addCell(Av);
			
       
			table1.completeRow();
			
			for(int i=0;i<AttrDtl.size();i++){
				//System.out.println("Name : "+AttrDtl.get(i).getAttrName()+"     Value : " + AttrDtl.get(i).getAttrValue());
				
				PdfPCell cell5 = new PdfPCell(new Phrase(AttrDtl.get(i).getAttrName()));
	            PdfPCell cell6 = new PdfPCell(new Phrase(AttrDtl.get(i).getAttrValue()));
	            table1.addCell(cell5);
	            table1.addCell(cell6);
	            table1.completeRow();
				
			}
         
			Rectangle rect1 = pdfWriter1.getBoxSize("rectangle");
         
			d2.add(table1);
			d2.close();
			pathfos.close();
	 	}
		 }
	
	    ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	    //SimpleDateFormat  dtf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS z");
	    
	    Date date = new Date();
	    Timestamp ts=new Timestamp(date.getTime()); 
	    time = docMgmtImpl.TimeZoneConversion(ts,locationName,countryCode);
	    String dtf = "";
	    if(countryCode.equalsIgnoreCase("IND")){
	     dtf = time.get(0);
	    }else{
	     dtf = time.get(1);
	    }		      
	    userName = ActionContext.getContext().getSession().get("username")
				.toString();
	    Font fontSize_12 =  FontFactory.getFont(FontFactory.TIMES, 10f);
	    boolean c=false;
	    
	    String file1=path.toString();
		String file2=fileToCopy.toString();
		String file3="";
		if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
			file3=pathForAttribute.toString();
		}
		String outs = null;
		if(srcFileExt.equalsIgnoreCase("pdf")){
			System.out.println("Aleready Pdf");
			outs = baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + "Final.pdf";
		}
		else{
			outs=baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + fileName +".pdf";
		}
		if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
		
		Document d1 = new Document();
		//Document document = new Document(PageSize.A4, 30, 30, 550, 800);
		OutputStream fos = new FileOutputStream(path);
		
	    PdfWriter pdfWriter = PdfWriter.getInstance(d1, fos);
	    Rectangle rectangle = new Rectangle(30, 30, 550, 800);
	    
	    pdfWriter.setBoxSize("rectangle", rectangle);
	   /* ColumnText.showTextAligned(pdfWriter.getDirectContent(),
	               Element.ALIGN_CENTER, new Phrase("Powered By Mark47"),
	               110, 30, 0);*/
	    d1.open();
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
		
		float[] widthsForTable = { 1.3f, 3 };
		PdfPTable table = new PdfPTable(widthsForTable);
		table.setWidthPercentage(100);
		//System.out.println(tempHistory.size()+"\n");
		//String Company="SSPL";
		float[] widths = { 1, 3 };
		PdfPTable Htable = new PdfPTable(widths);
		Htable.setWidthPercentage(100);
		/*Htable.setHorizontalAlignment(0);
		Htable.setTotalWidth(500f);
		Htable.setLockedWidth(true);
		/*float[] widths = new float[] { 20f, 50f, 30f};*/
		//Htable.setWidths(widths);
		/*PdfPCell cell;
		PdfPCell nodeName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Project Name : "+docMgmtImpl.getWorkSpaceDetail(workspaceID).getWorkSpaceDesc()));*/
		
		PdfPCell HPName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Project Name"));
		HPName.setPadding(8f);
		Htable.addCell(HPName);
       PdfPCell PName = new PdfPCell(new Phrase(docMgmtImpl.getWorkSpaceDetail(workspaceID).getWorkSpaceDesc()));
       PName.setPadding(8f);
       Htable.addCell(PName);
       
       PdfPCell HNname = new PdfPCell(new Phrase("Document Name  "));
       HNname.setPadding(8f);
       Htable.addCell(HNname);
       
       PdfPCell Nname = new PdfPCell(new Phrase(docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName()));
       Nname.setPadding(8f);
       Htable.addCell(Nname);
		
       Vector<DTOWorkSpaceNodeAttribute> AttrDtlForCoverPage = null;
       AttrDtlForCoverPage=docMgmtImpl.getAttrDtlForCoverPage(workspaceID,nodeId);
       if(AttrDtlForCoverPage.size()>0){
       for(int i=0;i<AttrDtlForCoverPage.size();i++){
			//System.out.println("Name : "+AttrDtl.get(i).getAttrName()+"     Value : " + AttrDtl.get(i).getAttrValue());
			
			PdfPCell cell5 = new PdfPCell(new Phrase(AttrDtlForCoverPage.get(i).getAttrName()));
           PdfPCell cell6 = new PdfPCell(new Phrase(AttrDtlForCoverPage.get(i).getAttrValue()));
           cell5.setPadding(8f);
           Htable.addCell(cell5);
           cell6.setPadding(8f);
           Htable.addCell(cell6);
           Htable.completeRow();
       	}
       }
       d1.add(Htable);
	    d1.add(table);
	    
       d1.close();
		 fos.close();	
	}
		PdfReader reader1 = null;
		if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
			reader1 = new PdfReader(path.toString());
		}
		
		PdfReader reader2 = new PdfReader(file2);
		PdfReader reader3=null;
		if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
			AttrDtl=docMgmtImpl.getAttrDtl(workspaceID,nodeId);
			if(AttrDtl.size()>0){
				reader3 = new PdfReader(file3);
			}
		}
		Document document = new Document();
		FileOutputStream fos1 = new FileOutputStream(outs);
		PdfCopy copy = new PdfCopy(document, fos1);
		document.open();
		PdfImportedPage page;
		PdfCopy.PageStamp stamp;
		Phrase phrase;
		BaseFont bf = BaseFont.createFont();
		Font font1 = new Font(bf, 9);
		int n=0;
		if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
		n = reader1.getNumberOfPages();
		}
		/*  AttrDtlForPageSetting=docMgmtImpl.getAttrDtlForPageSetting(WorkspaceId,nodeId);
		  String signOffPlacement="";
		  String printPageNo="";
		  if(AttrDtlForPageSetting.size()>0){
			  
			  for(int s=0;s<AttrDtlForPageSetting.size();s++){
					if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Sign Page Placement")){
						signOffPlacement=AttrDtlForPageSetting.get(s).getAttrValue();break;
					}
				}
		  }*/

		  //AttrDtlForPageSetting=docMgmtImpl.getAttrDtlForPageSetting(workspaceID,nodeId);
		 //else{
		if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
			 for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
				    page = copy.getImportedPage(reader1, i);
				    stamp = copy.createPageStamp(page);
				    
				    int n1=reader1.getNumberOfPages();
		     	   ColumnText.showTextAligned(stamp.getOverContent(),
			 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
			 	              300,5,0);
		     	  //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
				    		12,6,0);
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
				    		470, 6, 0);
		     	  //}
				    stamp.alterContents();
				    copy.addPage(page);
				}
			}
			 Rectangle pagesize;
				for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
				    page = copy.getImportedPage(reader2, i);
				    stamp = copy.createPageStamp(page);
				    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
				    pagesize = reader2.getPageSizeWithRotation(i);
				    //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
				    		12, 6, 0);
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
				    		 pagesize.getRight()-128, 6, 0); 
				    //}
				    stamp.alterContents();
				    copy.addPage(page);
				}
			  
		 // }
		  
		  
		
		/*for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
		    page = copy.getImportedPage(reader1, i);
		    stamp = copy.createPageStamp(page);
		    
		    int n1=reader1.getNumberOfPages();
    	   ColumnText.showTextAligned(stamp.getOverContent(),
	 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
	 	              300,5,0);
		    
		    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
		    		12,6,0);
		    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
		    		470, 6, 0);
		    stamp.alterContents();
		    copy.addPage(page);
		}*/
		/*if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
			AttrDtl=docMgmtImpl.getAttrDtl(WorkspaceId,nodeId);
			if(AttrDtl.size()>0){
				for (int i = 1; i <= reader3.getNumberOfPages(); i++) {
					  page = copy.getImportedPage(reader3, i);
					    stamp = copy.createPageStamp(page);
					    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
					    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
					    		12, 6, 0);
					    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
					    		470, 6, 0); 
					    stamp.alterContents();
					    copy.addPage(page);
					}
				}
		}*/
		//Rectangle pagesize;
		/*for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
		    page = copy.getImportedPage(reader2, i);
		    stamp = copy.createPageStamp(page);
		    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
		    pagesize = reader2.getPageSizeWithRotation(i);
		    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
		    		12, 6, 0);
		    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
		    		 pagesize.getRight()-128, 6, 0); 
		    stamp.alterContents();
		    copy.addPage(page);
		}*/
		
		document.close();
		if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
		reader1.close();}
		reader2.close();
		fos1.close();
	   
	    //out.flush();
	    //d1.close();
	  
	    //out.close();
        
        
        //Other publishing logic ends
		
		FileManager.deleteFile(path);
		File f=new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + fileName);
		FileManager.deleteFile(f);
		if(showAttributesInPublishPage.equalsIgnoreCase("Yes"))
			FileManager.deleteFile(pathForAttribute);
        
		
		File oldName = new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + "Final.pdf");
	    File newName = new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + fileName); 
	     
	   if (oldName.renameTo(newName))  
	       System.out.println("Renamed successfully");         
	   else 
	      System.out.println("Error");
		
		
		
	 
	return "html";
	}
	else{
	//uploadedFile = new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid + "/" + fileName);
		UUID uid=UUID.randomUUID();
		String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
		String srcFlExt = previousHistory.get(0).getFileName().substring(previousHistory.get(0).getFileName().lastIndexOf(".") + 1);
						
		uploadedFile = new File(baseSrcPath + "/" + workspaceID + "/" + nodeId + "/" + uuid + "/" + fileNameWithOutExt+"_"+uid+"."+srcFlExt);
	
	File srcDoc = new File(baseSrcPath + previousHistory.get(0).getFolderName()
			+ "/" + previousHistory.get(0).getFileName());
	if(uploadedFile.exists()){
		uploadedFile.delete();
	}
	Files.copy(fSrc.toPath(), uploadedFile.toPath());

	 String Name=previousHistory.get(0).getFileName();
	    int pos = Name.lastIndexOf(".");
	    if (pos > 0) {
	    	Name = Name.substring(0, pos)+"_Sign";
	    }
	String srcFileExt = previousHistory.get(0).getFileName().substring(previousHistory.get(0).getFileName().lastIndexOf(".") + 1);
	if(srcFileExt.equalsIgnoreCase("pdf")){
		System.out.println("Aleready Pdf");
		out = new FileOutputStream(new File(baseSrcPath + "/" + workspaceID + "/"
				+ nodeId + "/" + uuid+ "/" + "Final.pdf"));
		uploadFile=uploadedFile;
		}
	else{
		String existedName=previousHistory.get(0).getFileName();
	    int existedPos = existedName.lastIndexOf(".");
	    if (existedPos > 0) {
	    	existedName = existedName.substring(0, existedPos);
	    }
		String pdfFileName=existedName+".pdf";
		String baseWorkFolder=propertyInfo.getValue("BaseWorkFolder");
		File check =new File(baseWorkFolder + previousHistory.get(0).getFolderName()+ "/"+ pdfFileName);
		
		//String pdfFileNameSign = existedName+"_Sign.pdf";
		//File checkSign =new File(baseWorkFolder + previousHistory.get(0).getFolderName()+ "/"+ pdfFileNameSign);
		if(check.exists()){
			System.out.println("Aleready converted Pdf exists....");
			out = new FileOutputStream(new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + "Final.pdf"));
			uploadFile=check;
		}
		
		/*else if(checkSign.exists()){
			uploadFile=check;
			 pos = Name.lastIndexOf(".");
			    if (pos > 0) {
			    	Name = Name.substring(0, pos);
			    }
			out = new FileOutputStream(new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf"));
		}*/
		else{
			String response="";
			//String srcPath=src.replace("\\", "/");
			String srcPath=src;
			//String destPath=(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf").replace("\\", "/");
			String destPath=(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf");
						
			try{
				
				/*String srcDocPath= srcPath.replace('/', '\\');
				String destDocPath= destPath.replace('/', '\\');
						
				String nSrcPath = " \""+srcDocPath+"\" ";
				String nDestPath = " \""+destDocPath+"\" ";*/
				
				System.out.println("srcDocPath="+srcPath);
				System.out.println("destDocPath="+destPath);
				
				//exitFlag=DocToPdf(nSrcPath,nDestPath);
				//response=docMgmtImpl.convertDoc(srcPath,destPath);
				response=docMgmtImpl.convertDoc(uploadedFile.getAbsolutePath(),destPath);
				//response=docMgmtImpl.convertPdfToDoc(srcPath,destPath);
				//response=docMgmtImpl.convertPdfToDoc(srcPath,destPath);
				
				/*String[] parts = response.split(":");
				if(parts[0].equalsIgnoreCase("error")){
					htmlContent=parts[2];
					return "html";
				}*/
				String[] parts = response.split("#");
				if(parts[0].equalsIgnoreCase("False")){
					htmlContent=parts[2];
					return "html";
				}
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			String[] parts = response.split("#");
			if(parts[0].equalsIgnoreCase("False")){
				htmlContent=parts[2];
				return "html";
			}
						
			uploadFile = new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf");
			out = new FileOutputStream(new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf"));
			FileManager.deleteFile(uploadedFile);
		}}
	
	
	//Creating temp file to write data and later will be merged with new PDF
	
	/*ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
			.getPDFSignOffDocDetail(workspaceID, nodeId);
	*/
	
	Vector<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.getProjectSignOffHistory(workspaceID, nodeId);
	
	DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetail(workspaceID);
	Vector<DTOWorkSpaceNodeDetail> getNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	
	getNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, nodeId);
	char nodeTypeIndi = getNodeDetail.get(0).getNodeTypeIndi();
	File pathForAttribute = null;
	File path = null;
	
	if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K' ){
	path=new File(baseSrcPath + "/" + workspaceID + "/"
			+ nodeId + "/" + uuid +"/Temp.pdf");
	}
	if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
	pathForAttribute=new File(baseSrcPath + "/" + workspaceID + "/"
			+ nodeId + "/" + uuid +"/attr.pdf");
	}
	
	//Document document=new Document();
	//PdfCopy copy = new PdfCopy(document, new FileOutputStream(path));
	//PdfReader pdfReader;
	//PdfStamper pdfStamper;
	String signatureFilePath=propertyInfo.getValue("signatureFile");
	if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
		Document d1 = new Document();
		//Document document = new Document(PageSize.A4, 30, 30, 550, 800);
		OutputStream fos = new FileOutputStream(path);
		
	    PdfWriter pdfWriter = PdfWriter.getInstance(d1, fos);
	    Rectangle rectangle = new Rectangle(30, 30, 550, 800);
	    
	    pdfWriter.setBoxSize("rectangle", rectangle);
	   /* ColumnText.showTextAligned(pdfWriter.getDirectContent(),
	               Element.ALIGN_CENTER, new Phrase("Powered By Mark47"),
	               110, 30, 0);*/
	    d1.open();
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
		
		float[] widthsForTable = { 1.3f, 3 };
		PdfPTable table = new PdfPTable(widthsForTable);
		table.setWidthPercentage(100);
		//System.out.println(tempHistory.size()+"\n");
		//String Company="SSPL";
		if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
		float[] widths = { 1, 3 };
		PdfPTable Htable = new PdfPTable(widths);
		Htable.setWidthPercentage(100);
		/*Htable.setHorizontalAlignment(0);
		Htable.setTotalWidth(500f);
		Htable.setLockedWidth(true);
		/*float[] widths = new float[] { 20f, 50f, 30f};*/
		//Htable.setWidths(widths);
		/*PdfPCell cell;
		PdfPCell nodeName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Project Name : "+docMgmtImpl.getWorkSpaceDetail(workspaceID).getWorkSpaceDesc()));*/
		
		float[] LTRTablewidths = { 1, 5, 3 };
		PdfPTable LTRHtable = new PdfPTable(LTRTablewidths);
		LTRHtable.setWidthPercentage(100);
		
		
		String LTRSingleDocFooterFormat=propertyInfo.getValue("LTRSingleDocFooterFormat");
		if(LTRSingleDocFooterFormat.equals("Yes")){
			Image image = null;
				
			String imageFile = null;		
			String baseLogoDir=propertyInfo.getValue("LogoFilePath");
			imageFile =baseLogoDir+"/Lambda.png";
			File f=new File(imageFile);
			
			if(f.exists())
			{
			   image = Image.getInstance(imageFile);
	           
	    	   image.scaleToFit(50f, 80f); //Scale image's width and height
	    	   PdfPCell IMGCell = new PdfPCell();
	    	   IMGCell.addElement(image);
	    	   IMGCell.setFixedHeight(50f);
	    	   IMGCell.setPaddingTop(5f);
	    	   IMGCell.setPaddingLeft(7f);
	    	   IMGCell.setPaddingRight(1f);
	    	   //IMGCell.setBorder(Rectangle.NO_BORDER);
		       IMGCell.setBackgroundColor(new BaseColor(255,255,255));
		       LTRHtable.addCell(IMGCell);
		       
		       String PDFPublishParentHeader=propertyInfo.getValue("PDFPublishParentHeader");
		       int parentNodeId=docMgmtImpl.getParentNodeId(workspaceID, nodeId);
		       String nodeDisplayName=docMgmtImpl.getNodeDetail(workspaceID, parentNodeId).get(0).getNodeDisplayName();
		       
		       String[] headerValues=PDFPublishParentHeader.split(",");
		       for(int i=0;i<headerValues.length;i++){
		    	   	if(headerValues[i].equalsIgnoreCase("IQ Scripts")){
		    	   			if(nodeDisplayName.contains("{"))
		    	   				nodeDisplayName=nodeDisplayName.substring(nodeDisplayName.indexOf(0)+1, nodeDisplayName.indexOf("{"));
		    	   			else
		    	   				nodeDisplayName=nodeDisplayName;
		    	   			break;
		    	   	}
					if(headerValues[i].equalsIgnoreCase("OQ Scripts")){
						if(nodeDisplayName.contains("{"))
							nodeDisplayName=nodeDisplayName.substring(nodeDisplayName.indexOf(0)+1, nodeDisplayName.indexOf("{"));
			    		   else
			    			   nodeDisplayName=nodeDisplayName;
						break;
					}
					if(headerValues[i].equalsIgnoreCase("PQ Scripts")){
						if(nodeDisplayName.contains("{"))
							nodeDisplayName=nodeDisplayName.substring(nodeDisplayName.indexOf(0)+1, nodeDisplayName.indexOf("{"));
			    		   else
			    			   nodeDisplayName=nodeDisplayName;
						break;
						   
					}
		       }
		       PdfPCell DocNameCell = null;
		       if(PDFPublishParentHeader.contains(nodeDisplayName)){
		    	   String nDisplayNameOfLeaf = docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeDisplayName();
		    	   if(nDisplayNameOfLeaf.contains("{"))
		    		   nDisplayNameOfLeaf=nDisplayNameOfLeaf.substring(nDisplayNameOfLeaf.indexOf(0)+1, nDisplayNameOfLeaf.indexOf("{"));
		    	   DocNameCell = new PdfPCell(new Phrase(nodeDisplayName +" "+nDisplayNameOfLeaf));
		       }
		       else{
		    	   //DocNameCell = new PdfPCell(new Phrase(docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName()));
		    	   String nDisplayName = docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeDisplayName();
	  		 	if(nDisplayName.contains("{")){
	  			 nDisplayName=nDisplayName.substring(nDisplayName.indexOf(0)+1, nDisplayName.indexOf("{"));
	  			 //nDisplayName=nDisplayName.substring(nDisplayName.indexOf(0)+1, nDisplayName.indexOf("-"));
	  			 if(docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getCloneFlag()=='Y'){
	  				 if(nDisplayName.contains("-"))
	  					 nDisplayName=nDisplayName.substring(nDisplayName.indexOf(0)+1, nDisplayName.indexOf("-"));
	  			 }
	  			 DocNameCell = new PdfPCell(new Phrase(nDisplayName));
	  		 	}
	  		 	else{
	  		 		if(docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getCloneFlag()=='Y'){
		  				 if(nDisplayName.contains("-"))
		  					 nDisplayName=nDisplayName.substring(nDisplayName.indexOf(0)+1, nDisplayName.indexOf("-"));
	  		 		}
	  		 		DocNameCell = new PdfPCell(new Phrase(nDisplayName));
	  		 	}
		       }
		       DocNameCell.setPadding(8f);
		       DocNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		       DocNameCell.setVerticalAlignment(Element.ALIGN_CENTER);
		       LTRHtable.addCell(DocNameCell);
		        
		       PdfPCell ProjectNameCell = new PdfPCell(new Phrase("System Name :\n"+docMgmtImpl.getWorkSpaceDetail(workspaceID).getWorkSpaceDesc()));
		       //PdfPCell ProjectNameCell = new PdfPCell(new Phrase("System Name :  abcedfghhijklnopqrsatuvwxyzabcedfghhijklnopqrsatuvwxyz"));
		       ProjectNameCell.setPadding(8f);
		       ProjectNameCell.setNoWrap(false);
		       ProjectNameCell.setFixedHeight(55);
		       LTRHtable.addCell(ProjectNameCell);
		       
		       Font headingFont = null;
		       headingFont=ft.registerExternalFont("Ariel","No");
		       
		       String docName=null;
		       if(docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName().equalsIgnoreCase("Pre-approval"))
		       		docName=docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName().toString();
		       if(docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName().equalsIgnoreCase("Post-approval"))
		       		docName=docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName().toString();
		       if(docName==null)
		       		docName="Document Approval";
		       
		       Font heading = null;
		       heading=ft.registerExternalFont("ArielUnderline","No");
		       
		       PdfPCell HeadingCell = new PdfPCell(new Phrase(docName,heading));
		       HeadingCell.setColspan(3);
		       HeadingCell.setPaddingTop(10f);
		       HeadingCell.setFixedHeight(30f);
		       HeadingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		       HeadingCell.setVerticalAlignment(Element.ALIGN_CENTER);
		       HeadingCell.setBorder(rectangle.NO_BORDER);
			   LTRHtable.addCell(HeadingCell);
			   
			   PdfPCell headerTextCell = new PdfPCell(new Phrase("Indicates completion of the document review, approve and concurrence with the document content based on the individual team role and area of expertise",headingFont));
			   headerTextCell.setColspan(3);
		       headerTextCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		       headerTextCell.setVerticalAlignment(Element.ALIGN_CENTER);
		       headerTextCell.setBorder(rectangle.NO_BORDER);
			   headerTextCell.setPadding(8f);
		       LTRHtable.addCell(headerTextCell);
			}
		}
		else{
			Font headingFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE,BaseColor.BLACK);
		       headingFont=ft.registerExternalFont("Ariel","No");
		       PdfPCell HPName ;
			if(LTRSingleDocFooterFormat.equals("Yes"))
				HPName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Project Name",headingFont));
			else
				HPName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Project Name"));
		HPName.setPadding(8f);
		Htable.addCell(HPName);
		
		PdfPCell PName;
		if(LTRSingleDocFooterFormat.equals("Yes"))
			PName = new PdfPCell(new Phrase(docMgmtImpl.getWorkSpaceDetail(workspaceID).getWorkSpaceDesc(),headingFont));
		else
			PName = new PdfPCell(new Phrase(docMgmtImpl.getWorkSpaceDetail(workspaceID).getWorkSpaceDesc()));
        PName.setPadding(8f);
        Htable.addCell(PName);
        
        PdfPCell HNname;
        if(LTRSingleDocFooterFormat.equals("Yes"))
        	HNname = new PdfPCell(new Phrase("Document Name  ",headingFont));
        else
        HNname = new PdfPCell(new Phrase("Document Name  "));
        HNname.setPadding(8f);
        Htable.addCell(HNname);
        
        PdfPCell Nname ;
        if(LTRSingleDocFooterFormat.equals("Yes"))
        	Nname = new PdfPCell(new Phrase(docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName(),headingFont));
        else
        	Nname = new PdfPCell(new Phrase(docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName()));
        Nname.setPadding(8f);
        Htable.addCell(Nname);
        
        Vector<DTOWorkSpaceNodeAttribute> AttrDtlForCoverPage = null;
        AttrDtlForCoverPage=docMgmtImpl.getAttrDtlForCoverPage(workspaceID,nodeId);
        if(AttrDtlForCoverPage.size()>0){
        for(int i=0;i<AttrDtlForCoverPage.size();i++){
			//System.out.println("Name : "+AttrDtl.get(i).getAttrName()+"     Value : " + AttrDtl.get(i).getAttrValue());
        	PdfPCell cell5;
        	PdfPCell cell6;
        	if(LTRSingleDocFooterFormat.equals("Yes")){
        		cell5 = new PdfPCell(new Phrase(AttrDtlForCoverPage.get(i).getAttrName(),headingFont));
                cell6 = new PdfPCell(new Phrase(AttrDtlForCoverPage.get(i).getAttrValue(),headingFont));
        	}
        	else{
			cell5 = new PdfPCell(new Phrase(AttrDtlForCoverPage.get(i).getAttrName()));
            cell6 = new PdfPCell(new Phrase(AttrDtlForCoverPage.get(i).getAttrValue()));
        	}
            cell5.setPadding(8f);
            Htable.addCell(cell5);
            cell6.setPadding(8f);
            Htable.addCell(cell6);
            Htable.completeRow();
        	}
        }
		}
        
		
		/*PdfPCell nodeName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Project Name : "+docMgmtImpl.getWorkSpaceDetail(workspaceID).getWorkSpaceDesc()));
		nodeName.setColspan(2);
		nodeName.setPadding(5f);
		nodeName.setLeading(1f, 1.4f);
		nodeName.setBorder(Rectangle.BOX);
		nodeName.setBackgroundColor(new BaseColor(255,255,255));
		nodeName.setHorizontalAlignment(Element.ALIGN_CENTER);
		nodeName.setVerticalAlignment(Element.ALIGN_CENTER);
		Htable.addCell(nodeName);
		//Htable.completeRow();
		
		PdfPCell cell = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Document Name : "+docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName()));
		cell.setColspan(2);
		cell.setPadding(5f);
		cell.setLeading(1f, 1.4f);
		cell.setBorder(Rectangle.BOX);
		cell.setBackgroundColor(new BaseColor(255,255,255));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		Htable.addCell(cell);*/
		//Htable.completeRow();
        
        PdfPCell HSpace = new PdfPCell();
        HSpace.setColspan(2);
        HSpace.setPadding(8f);
        HSpace.setLeading(1f, 1.4f);
        HSpace.setBorder(rectangle.NO_BORDER);
        Htable.addCell(HSpace);
        
        
        Font headingFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE,BaseColor.BLACK);
	       headingFont=ft.registerExternalFont("Ariel","No");
        
        PdfPCell cell1=new PdfPCell();
      if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
			System.out.println("Project Type : "+wsDetail.getProjectCode());
			if(LTRSingleDocFooterFormat.equals("No")){
		cell1 = new PdfPCell(new Phrase(Element.ALIGN_CENTER,"Sign Off"));
		cell1.setColspan(4);
		cell1.setPadding(2f);
		cell1.setLeading(1f, 1.4f);
        

		cell1.setBorder(Rectangle.NO_BORDER);
		cell1.setBackgroundColor(new BaseColor(255,255,255));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell1);}
			else
				cell1 = new PdfPCell(new Phrase(Element.ALIGN_CENTER,"Sign Off",headingFont));
	 }
		PdfPCell Space = new PdfPCell();
		Space.setColspan(4);
		Space.setPadding(15f);
		Space.setLeading(1f, 1.4f);
		
	    Created.clear();
	    Reviewed.clear();
	    Authorized.clear();
	    Approved.clear();
	    
		for(int j=0;j<tempHistory.size();j++){
			
			if(tempHistory.get(j).getStageDesc().equalsIgnoreCase("Created")){
				Created.add(tempHistory.get(j));
			}
			else if(tempHistory.get(j).getStageDesc().equalsIgnoreCase("Reviewed")){
				Reviewed.add(tempHistory.get(j));
			}
			else if(tempHistory.get(j).getStageDesc().equalsIgnoreCase("Authorized")){
				Authorized.add(tempHistory.get(j));
			}
			else if(tempHistory.get(j).getStageDesc().equalsIgnoreCase("Approved")){
				Approved.add(tempHistory.get(j));
			}
		}
			/*System.out.println(Created.size());
			System.out.println(Reviewed.size());
			System.out.println(Authorized.size());
			System.out.println(Approved.size());*/

		Font LTRFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE,BaseColor.BLACK);
		LTRFont=ft.registerExternalFont("Ariel","No");
		
		for(int c=0;c<Created.size();c++){
			if(c==0){
				PdfPCell cell11;
				if(LTRSingleDocFooterFormat.equals("Yes")){
					String tcDisplayName=docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName();
					if(tcDisplayName.equalsIgnoreCase("TC"))
						cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Tested By: ",LTRFont));
					
					else if(tcDisplayName.equalsIgnoreCase("IR-Verification"))
						cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Executor: ",LTRFont));
					
					else if(tcDisplayName.equalsIgnoreCase("Service-Release"))
						cell11 = new PdfPCell(new Phrase("Application release by (AO):"));
					
					else if(tcDisplayName.equalsIgnoreCase("System-Release-Certificate"))
						cell11 = new PdfPCell(new Phrase("Application release by (AO):"));
					
					else
						cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Created By: ",LTRFont));
				}
				else{
				cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Created By: "));
				}
			cell11.setColspan(2);
			cell11.setBorder(0);
			cell11.setPadding(20f);
			cell11.setLeading(1f, 1.4f);
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.setBackgroundColor(new BaseColor(255,255,255));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell11.setVerticalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell11);
			
			//PdfPCell Dc = new PdfPCell(new Phrase("Name"));
			PdfPCell Mo;
			if(LTRSingleDocFooterFormat.equals("Yes"))
				Mo = new PdfPCell(new Phrase("Signature",LTRFont));
			else
				Mo = new PdfPCell(new Phrase("Signature"));
	       // Dc.setHorizontalAlignment(Element.ALIGN_CENTER);
	       // Dc.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        Mo.setHorizontalAlignment(Element.ALIGN_CENTER);
	        Mo.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        Mo.setFixedHeight(20);
	        //table.addCell(Dc);
	        Mo.setColspan(2);
	        table.addCell(Mo);
			
	        table.completeRow();
		}
			//PdfPCell title = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));
			PdfPCell title = new PdfPCell();
			PdfPCell mOn = new PdfPCell();
			//PdfPCell cell10 = new PdfPCell();      
	    	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
            Date date2 = sdf.parse(Created.get(c).getModifyOn().toString());
            String style = null;
            String imageFile;
            Image image = null;
            if(Created.get(c).getStyle()!=null && !Created.get(c).getStyle().isEmpty()){
            	style=Created.get(c).getStyle();
            }else{
            imageFile =signatureFilePath+"/"+Created.get(c).getFilePath()+"/"+Created.get(c).getFileName();
            //String imageFile = "//90.0.0.15//KnowledgeNET-CSV//userSignature//3//18//sign1.png";
            File f=new File(imageFile);
            
            if(f.exists())
  	        	{image = Image.getInstance(imageFile);}
            }
            if(date1.before(date2)){
            	//if(Created.get(c).getSignId() != null){
            	if(image!= null){
            		image.scaleToFit(150f, 50f); //Scale image's width and height
            	}
			/*mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));*/}
            else{
            	mOn = new PdfPCell(new Phrase(""));
            }
            title.setVerticalAlignment(Element.ALIGN_CENTER);
	        title.setHorizontalAlignment(Element.ALIGN_CENTER);
	        mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
	        
	       // table.addCell(title);
	       // PdfPCell imgCell = new PdfPCell(image, true);
	       // table.addCell(imgCell);   
	    	//if(Created.get(c).getSignId() != null){
	        if(image!= null){
		        Paragraph paragraph;
		        if(Created.get(c).getCountryCode().equalsIgnoreCase("IND")){
		        	paragraph = new Paragraph(Created.get(c).getISTDateTime()); 
			     }
			     else{
			    	 paragraph = new Paragraph(Created.get(c).getESTDateTime());
			    }
		        style=Created.get(c).getStyle();
		        Font font1 = null;
		        Paragraph p = null;
		        			      
		        font1=ft.registerExternalFont(style,"No");
		        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
		        p=new Paragraph(Created.get(c).getUserName(),font1);
		        image.setAlignment(image.ALIGN_CENTER);
		        p.setAlignment(p.ALIGN_CENTER);
		        paragraph.setAlignment(paragraph.ALIGN_CENTER);
		        mOn.addElement(image);
		    	mOn.setPaddingTop(10f);
		    	mOn.setPaddingLeft(1f);
		    	mOn.setPaddingRight(1f);
		    	
		      //  mOn.addElement(p);
		        //mOn.addElement(paragraph);
		        mOn.setFixedHeight(100);}
	    	else{
		    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));
	    		Font font1=null; 
	    		style=Created.get(c).getStyle();
	    		if(style!=null)
	    			font1 = ft.registerExternalFont(style,"No");
			    if(style!=null && !style.isEmpty())
		        	font1=ft.registerExternalFont(style,"No");
		        else
		        	font1= FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);
			    Paragraph p = new Paragraph(Created.get(c).getUserName(),font1);
				    
				    /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
		    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()));
		    		mOn.addElement(p);
		    		mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
		    		mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    		mOn.setPaddingTop(-10f);
				    mOn.setPaddingLeft(10f);
				    mOn.setPaddingRight(1f);
				    
				    /*mOn.setBorderWidthTop(2f);
				    mOn.setBorderWidthLeft(2f);
				    //mOn.setBorderWidthRight(2f);
			        mOn.setBorderWidthBottom(2f);
				    mOn.setBorderColorLeft(BaseColor.RED);
				    mOn.setBorderColorTop(BaseColor.RED);
				  mOn.setUseBorderPadding(true);
				    mOn.setBorderColorBottom(BaseColor.RED);*/
	    	}
	    	mOn.setBorderWidthRight(0f);
	    	mOn.setFixedHeight(50f);
	    	//mOn.setPaddingBottom(20);
	    	mOn.setHorizontalAlignment(Element.ALIGN_LEFT);
		    //mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    	
	        table.addCell(mOn);
	        
	        //Font font1 = null;
	        Paragraph p = null;
	        String style1 = "Brush Script MT";
	        //if(Created.get(c).getSignId() != null){
	        if(image!= null){
	        	//style=Created.get(c).getStyle();
	        	  image.setAlignment(image.ALIGN_CENTER);
	        } 
	        		      
	        //font1=ft.registerExternalFont(style1);
	        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
	        //p=new Paragraph(Created.get(c).getUserName(),font1);
	        Font font2 =null;
	        if(LTRSingleDocFooterFormat.equals("Yes"))
	        	p=new Paragraph("Name: "+Created.get(c).getUserName(),headingFont);
	        else
	        	font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f, BaseColor.BLACK);
	        p=new Paragraph("Name: "+Created.get(c).getUserName(),font2);
	      
	        p.setAlignment(p.ALIGN_LEFT);
	        Paragraph paragraph;
	        if(Created.get(c).getCountryCode().equalsIgnoreCase("IND")){
	        	if(LTRSingleDocFooterFormat.equals("Yes"))
	        		paragraph = new Paragraph("Date: "+Created.get(c).getISTDateTime(),headingFont);
	        	else
		    	  paragraph = new Paragraph("Date: "+Created.get(c).getISTDateTime(),font2);  
		     }
		     else{
		    	 if(LTRSingleDocFooterFormat.equals("Yes"))
		    		 paragraph = new Paragraph("Date: "+Created.get(c).getISTDateTime(),headingFont);
		    	 else
		    		 paragraph = new Paragraph("Date: "+Created.get(c).getESTDateTime(),font2);  
		    }
	      
	 	        paragraph.setAlignment(paragraph.ALIGN_LEFT);
	        title.setBorderWidthLeft(0f);
	      //  title.setHorizontalAlignment(Element.ALIGN_LEFT);
	        title.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        title.setFixedHeight(70f);
	        title.addElement(p);
	        
	        Paragraph role = null;
	        if(LTRSingleDocFooterFormat.equals("Yes"))
	        	role = new Paragraph("Role: "+Created.get(c).getRoleName(),headingFont);
	        else
	        	role = new Paragraph("Role: "+Created.get(c).getRoleName(),font2);
	        title.addElement(role);
	        
	        title.addElement(paragraph);
	        
	        Paragraph signId = null;
	        if(LTRSingleDocFooterFormat.equals("Yes"))
	        	signId = new Paragraph("Sign Id: "+Created.get(c).getSignId(),headingFont);
	        else
	        	signId = new Paragraph("Sign Id: "+Created.get(c).getSignId(),font2);
	        title.addElement(signId);
	        
	        /*float[] widthsForTbl = { 200f, 50 };
	        PdfPTable testTable = new PdfPTable(widthsForTbl);
	        PdfPCell c2;
	        //testTable.addCell(p+"\n"+paragraph+"\n"+signId);
	        testTable.addCell("Name: "+Created.get(c).getUserName()+"\n"+"Date: "+Created.get(c).getISTDateTime()+"\n"+"Sign Id: "+Created.get(c).getSignId());
	        testTable.addCell("bbbb");

	        c2 = new PdfPCell (testTable);//this line made the difference
	        c2.setPadding(0);
	        table.addCell(c2);*/
	        /*title.setBorderWidthTop(2f);
	        //title.setBorderWidthLeft(2f);
	        title.setBorderWidthRight(2f);
	        title.setBorderWidthBottom(2f);
	        title.setBorderColorRight(BaseColor.RED);
	        title.setBorderColorTop(BaseColor.RED);
	        title.setBorderColorBottom(BaseColor.RED);*/
	        title.setPaddingTop(-5f);
	        title.setPaddingRight(1f);
	        Phrase phrase = new Phrase();
	        String signdate;
	        
	        if(Created.get(c).getCountryCode().equalsIgnoreCase("IND")){
	        	//signdate = "Date: "+Created.get(c).getISTDateTime();
	        	signdate = Created.get(c).getISTDateTime();
		     }
		     else{
		    	 //signdate = "Date: "+Created.get(c).getESTDateTime();
		    	 signdate = Created.get(c).getESTDateTime();
		    }
	        Chunk chunk = null;
	        if(LTRSingleDocFooterFormat.equals("Yes")){
	        	chunk = new Chunk("\nName : "+Created.get(c).getUserName()+" \n"
					  	+ "Date : "+signdate+" \n"
					  	+ "Role : "+Created.get(c).getRoleName()+" \n"
			      		+ "Sign Id: "+Created.get(c).getSignId()+" \n"
			      		,headingFont);
	        }
	        else{
			  chunk = new Chunk("\nName : "+Created.get(c).getUserName()+" \n"
					  	+ "Date : "+signdate+" \n"
					  	+ "Role : "+Created.get(c).getRoleName()+" \n"
			      		+ "Sign Id: "+Created.get(c).getSignId()+" \n"
			      		,font2);}
			  String appUrl=propertyInfo.getValue("ApplicationUrl");
			  Vector<DTOWorkSpaceNodeHistory> data=docMgmtImpl.getWorkspacenodeHistoryByUserId(workspaceID, nodeId, Created.get(c).getUserCode());
			  String docId=docMgmtImpl.getDocIdForSign(workspaceID,nodeId,Created.get(c).getTranNo());
			  chunk.setAnchor(appUrl+"FileOpenFromPDF.do?wsId="+workspaceID+"&nodeId="+nodeId+"&tranNo="+data.get(0).getTranNo()
					  +"&userCode="+Created.get(c).getUserCode()+"&docId="+docId);
			  phrase.add(chunk);
			  
			  PdfPCell cellOne = new PdfPCell(new Phrase(phrase));
			  
			  //cellOne.setVerticalAlignment(Element.ALIGN_CENTER);
			  cellOne.setHorizontalAlignment(Element.ALIGN_LEFT);  
			  cellOne.setBorderWidthLeft(0f);
			  cellOne.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  cellOne.setFixedHeight(70f);
			  cellOne.setPaddingTop(-5f);
			  cellOne.setPaddingLeft(25f);
			  cellOne.setPaddingRight(1f);
			  
			  //cellOne.setBorder(Rectangle.NO_BORDER);
			  //cellOne.setBackgroundColor(new BaseColor(255,255,255));
		      //pdfPTable.addCell(pdfPCell2);
			  table.addCell(cellOne);
	        //table.addCell(title);
	        table.completeRow();
	        
	        PdfPCell Space1 = new PdfPCell();
	        Space1.setColspan(4);
	        Space1.setPadding(20f);
	        Space1.setLeading(1f, 1.4f);
	        System.out.println("Created");
		}
		for(int r=0;r<Reviewed.size();r++){
			if(r==0){
				PdfPCell cell11;
				if(LTRSingleDocFooterFormat.equals("Yes")){
					String tcDisplayName=docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName();
					if(tcDisplayName.equalsIgnoreCase("IR-Verification"))
						cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Reviewer (CSV): ",LTRFont));
					else if(tcDisplayName.equalsIgnoreCase("Service-Release"))
						cell11 = new PdfPCell(new Phrase(""));
					else if(tcDisplayName.equalsIgnoreCase("System-Release-Certificate"))
						cell11 = new PdfPCell(new Phrase(""));
					else
						cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Reviewed By: ",LTRFont));
				}
				else{
				cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Reviewed By: "));
				}
			cell11.setColspan(2);
			cell11.setBorder(0);
			cell11.setPadding(20f);
			cell11.setLeading(1f, 1.4f);
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.setBackgroundColor(new BaseColor(255,255,255));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell11.setVerticalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell11);
			
			//PdfPCell Dc = new PdfPCell(new Phrase("Name"));
			PdfPCell Mo;
			if(LTRSingleDocFooterFormat.equals("Yes"))
				Mo = new PdfPCell(new Phrase("Signature",LTRFont));
			else
				Mo = new PdfPCell(new Phrase("Signature"));
	       // Dc.setHorizontalAlignment(Element.ALIGN_CENTER);
	       // Dc.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        Mo.setHorizontalAlignment(Element.ALIGN_CENTER);
	        Mo.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        Mo.setFixedHeight(20);
	        //table.addCell(Dc);
	        Mo.setColspan(2);
	        table.addCell(Mo);
			
	        table.completeRow();
		}
			//PdfPCell title = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));
			PdfPCell title = new PdfPCell();
			PdfPCell mOn = new PdfPCell();
			//PdfPCell cell10 = new PdfPCell();      
	    	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
            Date date2 = sdf.parse(Reviewed.get(r).getModifyOn().toString());
            String style = null;
            String imageFile;
            Image image = null;
            if(Reviewed.get(r).getStyle()!=null && !Reviewed.get(r).getStyle().isEmpty()){
            	style=Reviewed.get(r).getStyle();
            }else{
            imageFile =signatureFilePath+"/"+Reviewed.get(r).getFilePath()+"/"+Reviewed.get(r).getFileName();
            //String imageFile = "//90.0.0.15//KnowledgeNET-CSV//userSignature//3//18//sign1.png";
            File f=new File(imageFile);
            
            if(f.exists())
  	        	{image = Image.getInstance(imageFile);}
            }
            if(date1.before(date2)){
            	//if(Created.get(c).getSignId() != null){
            	if(image!= null){
            		image.scaleToFit(150f, 50f); //Scale image's width and height
            	}
			/*mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));*/}
            else{
            	mOn = new PdfPCell(new Phrase(""));
            }
            title.setVerticalAlignment(Element.ALIGN_CENTER);
	        title.setHorizontalAlignment(Element.ALIGN_CENTER);
	        mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
	        
	       // table.addCell(title);
	       // PdfPCell imgCell = new PdfPCell(image, true);
	       // table.addCell(imgCell);   
	    	//if(Created.get(c).getSignId() != null){
	        if(image!= null){
		        Paragraph paragraph;
		        if(Reviewed.get(r).getCountryCode().equalsIgnoreCase("IND")){
		        	paragraph = new Paragraph(Reviewed.get(r).getISTDateTime()); 
			     }
			     else{
			    	 paragraph = new Paragraph(Reviewed.get(r).getESTDateTime());
			    }
		        style=Reviewed.get(r).getStyle();
		        Font font1 = null;
		        Paragraph p = null;
		        			      
		        font1=ft.registerExternalFont(style,"No");
		        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
		        p=new Paragraph(Reviewed.get(r).getUserName(),font1);
		        image.setAlignment(image.ALIGN_CENTER);
		        p.setAlignment(p.ALIGN_CENTER);
		        paragraph.setAlignment(paragraph.ALIGN_CENTER);
		        mOn.addElement(image);
		    	mOn.setPaddingTop(10f);
		    	mOn.setPaddingLeft(1f);
		    	mOn.setPaddingRight(1f);
		    	
		      //  mOn.addElement(p);
		        //mOn.addElement(paragraph);
		        mOn.setFixedHeight(100);}
	    	else{
		    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));
	    		Font font1=null; 
	    		style=Reviewed.get(r).getStyle();
	    		if(style!=null)
	    			font1 = ft.registerExternalFont(style,"No");
			    if(style!=null && !style.isEmpty())
		        	font1=ft.registerExternalFont(style,"No");
		        else
		        	font1= FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);
			    Paragraph p = new Paragraph(Reviewed.get(r).getUserName(),font1);
				    
				    /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
		    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()));
		    		mOn.addElement(p);
		    		mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
		    		mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    		mOn.setPaddingTop(-10f);
				    mOn.setPaddingLeft(10f);
				    mOn.setPaddingRight(1f);
				    
				    /*mOn.setBorderWidthTop(2f);
				    mOn.setBorderWidthLeft(2f);
				    //mOn.setBorderWidthRight(2f);
			        mOn.setBorderWidthBottom(2f);
				    mOn.setBorderColorLeft(BaseColor.RED);
				    mOn.setBorderColorTop(BaseColor.RED);
				  mOn.setUseBorderPadding(true);
				    mOn.setBorderColorBottom(BaseColor.RED);*/
	    	}
	    	mOn.setBorderWidthRight(0f);
	    	mOn.setFixedHeight(50f);
	    	//mOn.setPaddingBottom(20);
	    	mOn.setHorizontalAlignment(Element.ALIGN_LEFT);
		    //mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    	
	        table.addCell(mOn);
	        
	        //Font font1 = null;
	        Paragraph p = null;
	        String style1 = "Brush Script MT";
	        //if(Created.get(c).getSignId() != null){
	        if(image!= null){
	        	//style=Created.get(c).getStyle();
	        	  image.setAlignment(image.ALIGN_CENTER);
	        } 
	        		      
	        //font1=ft.registerExternalFont(style1);
	        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
	        //p=new Paragraph(Created.get(c).getUserName(),font1);
	        Font font2 =null;
	        if(LTRSingleDocFooterFormat.equals("Yes"))
	        	p=new Paragraph("Name: "+Reviewed.get(r).getUserName(),headingFont);
	        else
	        	font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f, BaseColor.BLACK);
	        p=new Paragraph("Name: "+Reviewed.get(r).getUserName(),font2);
	      
	        p.setAlignment(p.ALIGN_LEFT);
	        Paragraph paragraph;
	        if(Reviewed.get(r).getCountryCode().equalsIgnoreCase("IND")){
	        	if(LTRSingleDocFooterFormat.equals("Yes"))
	        		paragraph = new Paragraph("Date: "+Reviewed.get(r).getISTDateTime(),headingFont);
	        	else
		    	  paragraph = new Paragraph("Date: "+Reviewed.get(r).getISTDateTime(),font2);  
		     }
		     else{
		    	 if(LTRSingleDocFooterFormat.equals("Yes"))
		    		 paragraph = new Paragraph("Date: "+Reviewed.get(r).getISTDateTime(),headingFont);
		    	 else
		    		 paragraph = new Paragraph("Date: "+Reviewed.get(r).getESTDateTime(),font2);  
		    }
	      
	 	        paragraph.setAlignment(paragraph.ALIGN_LEFT);
	        title.setBorderWidthLeft(0f);
	      //  title.setHorizontalAlignment(Element.ALIGN_LEFT);
	        title.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        title.setFixedHeight(70f);
	        title.addElement(p);
	        
	        Paragraph role = null;
	        if(LTRSingleDocFooterFormat.equals("Yes"))
	        	role = new Paragraph("Role: "+Reviewed.get(r).getRoleName(),headingFont);
	        else
	        	role = new Paragraph("Role: "+Reviewed.get(r).getRoleName(),font2);
	        title.addElement(role);
	        
	        title.addElement(paragraph);
	        
	        Paragraph signId = null;
	        if(LTRSingleDocFooterFormat.equals("Yes"))
	        	signId = new Paragraph("Sign Id: "+Reviewed.get(r).getSignId(),headingFont);
	        else
	        	signId = new Paragraph("Sign Id: "+Reviewed.get(r).getSignId(),font2);
	        title.addElement(signId);
	        
	        /*float[] widthsForTbl = { 200f, 50 };
	        PdfPTable testTable = new PdfPTable(widthsForTbl);
	        PdfPCell c2;
	        //testTable.addCell(p+"\n"+paragraph+"\n"+signId);
	        testTable.addCell("Name: "+Created.get(c).getUserName()+"\n"+"Date: "+Created.get(c).getISTDateTime()+"\n"+"Sign Id: "+Created.get(c).getSignId());
	        testTable.addCell("bbbb");

	        c2 = new PdfPCell (testTable);//this line made the difference
	        c2.setPadding(0);
	        table.addCell(c2);*/
	        /*title.setBorderWidthTop(2f);
	        //title.setBorderWidthLeft(2f);
	        title.setBorderWidthRight(2f);
	        title.setBorderWidthBottom(2f);
	        title.setBorderColorRight(BaseColor.RED);
	        title.setBorderColorTop(BaseColor.RED);
	        title.setBorderColorBottom(BaseColor.RED);*/
	        title.setPaddingTop(-5f);
	        title.setPaddingRight(1f);
	        Phrase phrase = new Phrase();
	        String signdate;
	        
	        if(Reviewed.get(r).getCountryCode().equalsIgnoreCase("IND")){
	        	//signdate = "Date: "+Created.get(c).getISTDateTime();
	        	signdate = Reviewed.get(r).getISTDateTime();
		     }
		     else{
		    	 //signdate = "Date: "+Created.get(c).getESTDateTime();
		    	 signdate = Reviewed.get(r).getESTDateTime();
		    }
	        Chunk chunk = null;
	        if(LTRSingleDocFooterFormat.equals("Yes")){
	        	chunk = new Chunk("\nName : "+Reviewed.get(r).getUserName()+" \n"
					  	+ "Date : "+signdate+" \n"
					  	+ "Role : "+Reviewed.get(r).getRoleName()+" \n"
			      		+ "Sign Id: "+Reviewed.get(r).getSignId()+" \n"
			      		,headingFont);
	        }
	        else{
			  chunk = new Chunk("\nName : "+Reviewed.get(r).getUserName()+" \n"
					  	+ "Date : "+signdate+" \n"
					  	+ "Role : "+Reviewed.get(r).getRoleName()+" \n"
			      		+ "Sign Id: "+Reviewed.get(r).getSignId()+" \n"
			      		,font2);}
			  String appUrl=propertyInfo.getValue("ApplicationUrl");
			  Vector<DTOWorkSpaceNodeHistory> data=docMgmtImpl.getWorkspacenodeHistoryByUserId(workspaceID, nodeId, Reviewed.get(r).getUserCode());
			  String docId=docMgmtImpl.getDocIdForSign(workspaceID,nodeId,Reviewed.get(r).getTranNo());
			  chunk.setAnchor(appUrl+"FileOpenFromPDF.do?wsId="+workspaceID+"&nodeId="+nodeId+"&tranNo="+data.get(0).getTranNo()
					  +"&userCode="+Reviewed.get(r).getUserCode()+"&docId="+docId);
			  phrase.add(chunk);
			  
			  
			  
			  
			  PdfPCell cellOne = new PdfPCell(new Phrase(phrase));
			  
			  //cellOne.setVerticalAlignment(Element.ALIGN_CENTER);
			  cellOne.setHorizontalAlignment(Element.ALIGN_LEFT);  
			  cellOne.setBorderWidthLeft(0f);
			  cellOne.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  cellOne.setFixedHeight(70f);
			  cellOne.setPaddingTop(-5f);
			  cellOne.setPaddingLeft(25f);
			  cellOne.setPaddingRight(1f);
			  
			  //cellOne.setBorder(Rectangle.NO_BORDER);
			  //cellOne.setBackgroundColor(new BaseColor(255,255,255));
		      //pdfPTable.addCell(pdfPCell2);
			  table.addCell(cellOne);
	        //table.addCell(title);
	        table.completeRow();
	        
	        PdfPCell Space1 = new PdfPCell();
	        Space1.setColspan(4);
	        Space1.setPadding(20f);
	        Space1.setLeading(1f, 1.4f);
	        System.out.println("Reviewed");
		}
		
		for(int a=0;a<Approved.size();a++){
			if(a==0){
				PdfPCell cell11;
				if(LTRSingleDocFooterFormat.equals("Yes")){
					String tcDisplayName=docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName();
					if(tcDisplayName.equalsIgnoreCase("Service-Release"))
						cell11 = new PdfPCell(new Phrase(""));
					else if(tcDisplayName.equalsIgnoreCase("System-Release-Certificate"))
						cell11 = new PdfPCell(new Phrase(""));
					else
						cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Approved By: ",LTRFont));
				}
				else{
				cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Approved By: "));
				}
			cell11.setColspan(2);
			cell11.setBorder(0);
			cell11.setPadding(20f);
			cell11.setLeading(1f, 1.4f);
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.setBackgroundColor(new BaseColor(255,255,255));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell11.setVerticalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell11);
			
			//PdfPCell Dc = new PdfPCell(new Phrase("Name"));
			PdfPCell Mo;
			if(LTRSingleDocFooterFormat.equals("Yes"))
				Mo = new PdfPCell(new Phrase("Signature",LTRFont));
			else
				Mo = new PdfPCell(new Phrase("Signature"));
	       // Dc.setHorizontalAlignment(Element.ALIGN_CENTER);
	       // Dc.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        Mo.setHorizontalAlignment(Element.ALIGN_CENTER);
	        Mo.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        Mo.setFixedHeight(20);
	        //table.addCell(Dc);
	        Mo.setColspan(2);
	        table.addCell(Mo);
			
	        table.completeRow();
		}
			//PdfPCell title = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));
			PdfPCell title = new PdfPCell();
			PdfPCell mOn = new PdfPCell();
			//PdfPCell cell10 = new PdfPCell();      
	    	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
            Date date2 = sdf.parse(Approved.get(a).getModifyOn().toString());
            String style = null;
            String imageFile;
            Image image = null;
            if(Approved.get(a).getStyle()!=null && !Approved.get(a).getStyle().isEmpty()){
            	style=Approved.get(a).getStyle();
            }else{
            imageFile =signatureFilePath+"/"+Approved.get(a).getFilePath()+"/"+Approved.get(a).getFileName();
            //String imageFile = "//90.0.0.15//KnowledgeNET-CSV//userSignature//3//18//sign1.png";
            File f=new File(imageFile);
            
            if(f.exists())
  	        	{image = Image.getInstance(imageFile);}
            }
            if(date1.before(date2)){
            	//if(Created.get(c).getSignId() != null){
            	if(image!= null){
            		image.scaleToFit(150f, 50f); //Scale image's width and height
            	}
			/*mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));*/}
            else{
            	mOn = new PdfPCell(new Phrase(""));
            }
            title.setVerticalAlignment(Element.ALIGN_CENTER);
	        title.setHorizontalAlignment(Element.ALIGN_CENTER);
	        mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
	        
	       // table.addCell(title);
	       // PdfPCell imgCell = new PdfPCell(image, true);
	       // table.addCell(imgCell);   
	    	//if(Created.get(c).getSignId() != null){
	        if(image!= null){
		        Paragraph paragraph;
		        if(Approved.get(a).getCountryCode().equalsIgnoreCase("IND")){
		        	paragraph = new Paragraph(Approved.get(a).getISTDateTime()); 
			     }
			     else{
			    	 paragraph = new Paragraph(Approved.get(a).getESTDateTime());
			    }
		        style=Approved.get(a).getStyle();
		        Font font1 = null;
		        Paragraph p = null;
		        			      
		        font1=ft.registerExternalFont(style,"No");
		        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
		        p=new Paragraph(Approved.get(a).getUserName(),font1);
		        image.setAlignment(image.ALIGN_CENTER);
		        p.setAlignment(p.ALIGN_CENTER);
		        paragraph.setAlignment(paragraph.ALIGN_CENTER);
		        mOn.addElement(image);
		    	mOn.setPaddingTop(10f);
		    	mOn.setPaddingLeft(1f);
		    	mOn.setPaddingRight(1f);
		    	
		      //  mOn.addElement(p);
		        //mOn.addElement(paragraph);
		        mOn.setFixedHeight(100);}
	    	else{
		    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));
	    		Font font1=null; 
	    		style=Approved.get(a).getStyle();
	    		if(style!=null)
	    			font1 = ft.registerExternalFont(style,"No");
			    if(style!=null && !style.isEmpty())
		        	font1=ft.registerExternalFont(style,"No");
		        else
		        	font1= FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);
			    Paragraph p = new Paragraph(Approved.get(a).getUserName(),font1);
				    
				    /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
		    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()));
		    		mOn.addElement(p);
		    		mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
		    		mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    		mOn.setPaddingTop(-10f);
				    mOn.setPaddingLeft(10f);
				    mOn.setPaddingRight(1f);
				    
				    /*mOn.setBorderWidthTop(2f);
				    mOn.setBorderWidthLeft(2f);
				    //mOn.setBorderWidthRight(2f);
			        mOn.setBorderWidthBottom(2f);
				    mOn.setBorderColorLeft(BaseColor.RED);
				    mOn.setBorderColorTop(BaseColor.RED);
				  mOn.setUseBorderPadding(true);
				    mOn.setBorderColorBottom(BaseColor.RED);*/
	    	}
	    	mOn.setBorderWidthRight(0f);
	    	mOn.setFixedHeight(50f);
	    	//mOn.setPaddingBottom(20);
	    	mOn.setHorizontalAlignment(Element.ALIGN_LEFT);
		    //mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    	
	        table.addCell(mOn);
	        
	        //Font font1 = null;
	        Paragraph p = null;
	        String style1 = "Brush Script MT";
	        //if(Created.get(c).getSignId() != null){
	        if(image!= null){
	        	//style=Created.get(c).getStyle();
	        	  image.setAlignment(image.ALIGN_CENTER);
	        } 
	        		      
	        //font1=ft.registerExternalFont(style1);
	        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
	        //p=new Paragraph(Created.get(c).getUserName(),font1);
	        Font font2 =null;
	        if(LTRSingleDocFooterFormat.equals("Yes"))
	        	p=new Paragraph("Name: "+Approved.get(a).getUserName(),headingFont);
	        else
	        	font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f, BaseColor.BLACK);
	        p=new Paragraph("Name: "+Approved.get(a).getUserName(),font2);
	      
	        p.setAlignment(p.ALIGN_LEFT);
	        Paragraph paragraph;
	        if(Approved.get(a).getCountryCode().equalsIgnoreCase("IND")){
	        	if(LTRSingleDocFooterFormat.equals("Yes"))
	        		paragraph = new Paragraph("Date: "+Approved.get(a).getISTDateTime(),headingFont);
	        	else
		    	  paragraph = new Paragraph("Date: "+Approved.get(a).getISTDateTime(),font2);  
		     }
		     else{
		    	 if(LTRSingleDocFooterFormat.equals("Yes"))
		    		 paragraph = new Paragraph("Date: "+Approved.get(a).getISTDateTime(),headingFont);
		    	 else
		    		 paragraph = new Paragraph("Date: "+Approved.get(a).getESTDateTime(),font2);  
		    }
	      
	 	        paragraph.setAlignment(paragraph.ALIGN_LEFT);
	        title.setBorderWidthLeft(0f);
	      //  title.setHorizontalAlignment(Element.ALIGN_LEFT);
	        title.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        title.setFixedHeight(70f);
	        title.addElement(p);
	        
	        Paragraph role = null;
	        if(LTRSingleDocFooterFormat.equals("Yes"))
	        	role = new Paragraph("Role: "+Approved.get(a).getRoleName(),headingFont);
	        else
	        	role = new Paragraph("Role: "+Approved.get(a).getRoleName(),font2);
	        title.addElement(role);
	        
	        title.addElement(paragraph);
	        
	        Paragraph signId = null;
	        if(LTRSingleDocFooterFormat.equals("Yes"))
	        	signId = new Paragraph("Sign Id: "+Approved.get(a).getSignId(),headingFont);
	        else
	        	signId = new Paragraph("Sign Id: "+Approved.get(a).getSignId(),font2);
	        title.addElement(signId);
	        
	        /*float[] widthsForTbl = { 200f, 50 };
	        PdfPTable testTable = new PdfPTable(widthsForTbl);
	        PdfPCell c2;
	        //testTable.addCell(p+"\n"+paragraph+"\n"+signId);
	        testTable.addCell("Name: "+Created.get(c).getUserName()+"\n"+"Date: "+Created.get(c).getISTDateTime()+"\n"+"Sign Id: "+Created.get(c).getSignId());
	        testTable.addCell("bbbb");

	        c2 = new PdfPCell (testTable);//this line made the difference
	        c2.setPadding(0);
	        table.addCell(c2);*/
	        /*title.setBorderWidthTop(2f);
	        //title.setBorderWidthLeft(2f);
	        title.setBorderWidthRight(2f);
	        title.setBorderWidthBottom(2f);
	        title.setBorderColorRight(BaseColor.RED);
	        title.setBorderColorTop(BaseColor.RED);
	        title.setBorderColorBottom(BaseColor.RED);*/
	        title.setPaddingTop(-5f);
	        title.setPaddingRight(1f);
	        Phrase phrase = new Phrase();
	        String signdate;
	        
	        if(Approved.get(a).getCountryCode().equalsIgnoreCase("IND")){
	        	//signdate = "Date: "+Created.get(c).getISTDateTime();
	        	signdate = Approved.get(a).getISTDateTime();
		     }
		     else{
		    	 //signdate = "Date: "+Created.get(c).getESTDateTime();
		    	 signdate = Approved.get(a).getESTDateTime();
		    }
	        Chunk chunk = null;
	        if(LTRSingleDocFooterFormat.equals("Yes")){
	        	chunk = new Chunk("\nName : "+Approved.get(a).getUserName()+" \n"
					  	+ "Date : "+signdate+" \n"
					  	+ "Role : "+Approved.get(a).getRoleName()+" \n"
			      		+ "Sign Id: "+Approved.get(a).getSignId()+" \n"
			      		,headingFont);
	        }
	        else{
			  chunk = new Chunk("\nName : "+Approved.get(a).getUserName()+" \n"
					  	+ "Date : "+signdate+" \n"
					  	+ "Role : "+Approved.get(a).getRoleName()+" \n"
			      		+ "Sign Id: "+Approved.get(a).getSignId()+" \n"
			      		,font2);}
			  String appUrl=propertyInfo.getValue("ApplicationUrl");
			  Vector<DTOWorkSpaceNodeHistory> data=docMgmtImpl.getWorkspacenodeHistoryByUserId(workspaceID, nodeId, Approved.get(a).getUserCode());
			  String docId=docMgmtImpl.getDocIdForSign(workspaceID,nodeId,Approved.get(a).getTranNo());
			  chunk.setAnchor(appUrl+"FileOpenFromPDF.do?wsId="+workspaceID+"&nodeId="+nodeId+"&tranNo="+data.get(0).getTranNo()
					  +"&userCode="+Approved.get(a).getUserCode()+"&docId="+docId);
			  phrase.add(chunk);
			  
			  PdfPCell cellOne = new PdfPCell(new Phrase(phrase));
			  
			  //cellOne.setVerticalAlignment(Element.ALIGN_CENTER);
			  cellOne.setHorizontalAlignment(Element.ALIGN_LEFT);  
			  cellOne.setBorderWidthLeft(0f);
			  cellOne.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  cellOne.setFixedHeight(70f);
			  cellOne.setPaddingTop(-5f);
			  cellOne.setPaddingLeft(25f);
			  cellOne.setPaddingRight(1f);
			  
			  //cellOne.setBorder(Rectangle.NO_BORDER);
			  //cellOne.setBackgroundColor(new BaseColor(255,255,255));
		      //pdfPTable.addCell(pdfPCell2);
			  table.addCell(cellOne);
	        //table.addCell(title);
	        table.completeRow();
	        
	        PdfPCell Space1 = new PdfPCell();
	        Space1.setColspan(4);
	        Space1.setPadding(20f);
	        Space1.setLeading(1f, 1.4f);
	        System.out.println("Approved");
		}
		System.out.println("After finishing loop ");
	    Rectangle rect = pdfWriter.getBoxSize("rectangle");
	    System.out.println("LTRSingleDocFooterFormat "+ LTRSingleDocFooterFormat);
	    if(LTRSingleDocFooterFormat.equals("Yes"))d1.add(LTRHtable);
	    d1.add(Htable);
	    System.out.println("H Table added ");
	    d1.add(table);
	    System.out.println("Table added ");
	    
		 d1.close();
		 fos.close();
	}

	}
	
	Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailDraft(workspaceID, nodeId);
	
	//get users who signed
	Vector<DTOWorkSpaceNodeHistory> getSignedUsers = docMgmtImpl.getProjectSignOffHistory(workspaceID, nodeId);

	if(getUserRightsDetail.size()!=getSignedUsers.size()){
		System.out.println("Printing draft statement");
	
		String originFile = path.getAbsolutePath();
		String waterMarkFile = baseSrcPath + "/" + workspaceID + "/" + nodeId + "/" + uuid + "/"+ "Temp_watermark.pdf";
		
		PdfReader reader = new PdfReader(originFile);
	    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(waterMarkFile));
	    PdfContentByte under = stamper.getUnderContent(1);
	    Font f = new Font(FontFamily.HELVETICA, 35);
	    Phrase p = new Phrase("This watermark is added UNDER the existing content", f);
	    //ColumnText.showTextAligned(under, Element.ALIGN_CENTER, p, 297, 550, 0);
	    PdfContentByte over = stamper.getOverContent(1);
	    //p = new Phrase("This watermark is added ON TOP OF the existing content", f);
	    //ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 500, 0);
	    p = new Phrase("DRAFT", f);
	    over.saveState();
	    PdfGState gs1 = new PdfGState();
	    gs1.setFillOpacity(0.5f);
	    over.setGState(gs1);
	    ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 450, 20);
	    over.restoreState();
	    stamper.close();
	    reader.close();
		
	    //File oldFName = new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + "Temp_watermark.pdf"); 
	    //File newFName = new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + "Temp.pdf");
	    
	    Path f1 = Paths.get(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + "Temp_watermark.pdf");
	    Path rF = Paths.get(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + "Temp.pdf");
	    Files.move(f1, rF, StandardCopyOption.REPLACE_EXISTING);
    
	}
  
	 //for attach attribute list in PDF
	
	 Vector<DTOWorkSpaceNodeAttribute> AttrDtl = null;
	 Vector<DTOWorkSpaceNodeAttribute> AttrDtlForPageSetting = null;
	 
	 
	 if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
		AttrDtl=docMgmtImpl.getAttrDtl(workspaceID,nodeId);
 if(AttrDtl.size()>0){
	  Document d2 = new Document();
	  OutputStream attrfos = new FileOutputStream(pathForAttribute);
		
      PdfWriter pdfWriter1 = PdfWriter.getInstance(d2, attrfos);
      Rectangle rectangle1 = new Rectangle(30, 30, 550, 800);
      pdfWriter1.setBoxSize("rectangle", rectangle1);
      
      	d2.open();
		Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
		
		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
	
		PdfPCell cell2 = new PdfPCell(new Phrase(Element.ALIGN_CENTER,"File Attributes"));
		cell2.setColspan(2);
		
		cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell2.setVerticalAlignment(Element.ALIGN_CENTER);
		table1.addCell(cell2);
		
		
		PdfPCell An = new PdfPCell(new Phrase("Attribute Name"));
		PdfPCell Av = new PdfPCell(new Phrase("Attribute Value"));
		Av.setHorizontalAlignment(Element.ALIGN_CENTER);
		An.setHorizontalAlignment(Element.ALIGN_CENTER);
		table1.addCell(An);
		table1.addCell(Av);
		
    
		table1.completeRow();
		
		for(int i=0;i<AttrDtl.size();i++){
			//System.out.println("Name : "+AttrDtl.get(i).getAttrName()+"     Value : " + AttrDtl.get(i).getAttrValue());
			
			PdfPCell cell5 = new PdfPCell(new Phrase(AttrDtl.get(i).getAttrName()));
            PdfPCell cell6 = new PdfPCell(new Phrase(AttrDtl.get(i).getAttrValue()));
            table1.addCell(cell5);
            table1.addCell(cell6);
            table1.completeRow();
			
		}
      
		Rectangle rect1 = pdfWriter1.getBoxSize("rectangle");
      
		d2.add(table1);
		d2.close();
 	}
	 }

    ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();

    //SimpleDateFormat  dtf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS z");
    
    Date date = new Date();
    Timestamp ts=new Timestamp(date.getTime()); 
    time = docMgmtImpl.TimeZoneConversion(ts,locationName,countryCode);
    String dtf = "";
    if(countryCode.equalsIgnoreCase("IND")){
     dtf = time.get(0);
    }else{
     dtf = time.get(1);
    }
    
      
    userName = ActionContext.getContext().getSession().get("username")
			.toString();
    Font fontSize_12 =  FontFactory.getFont(FontFactory.TIMES, 10f);
    boolean c=false;
    String file1="";
    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
    file1=path.toString();}
	String file2=uploadFile.toString();
	String file3="";
	if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
		file3=pathForAttribute.toString();
	}
	String outs = null;
	if(srcFileExt.equalsIgnoreCase("pdf")){
		System.out.println("Aleready Pdf");
		outs = baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + "Final.pdf";
	}
	else{
		outs=baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf";
	}
	PdfReader reader1=null;
	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
		  reader1 = new PdfReader(file1);}
	PdfReader reader2 = new PdfReader(file2);
	PdfReader reader3=null;
	if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
		AttrDtl=docMgmtImpl.getAttrDtl(workspaceID,nodeId);
		if(AttrDtl.size()>0){
			reader3 = new PdfReader(file3);
		}
	}
	Document document = new Document();
	FileOutputStream fos1 = new FileOutputStream(outs);
	PdfCopy copy = new PdfCopy(document, fos1);
	document.open();
	PdfImportedPage page;
	PdfCopy.PageStamp stamp;
	Phrase phrase;
	BaseFont bf = BaseFont.createFont();
	Font font1 = new Font(bf, 9);
	int n ;
	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
	n = reader1.getNumberOfPages();}
	  
	  
	  AttrDtlForPageSetting=docMgmtImpl.getAttrDtlForPageSetting(workspaceID,nodeId);
	  String signOffPlacement="";
	  String printPageNo="";
	  if(AttrDtlForPageSetting.size()>0){
		  
		  for(int s=0;s<AttrDtlForPageSetting.size();s++){
				if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Sign Page Placement")){
					signOffPlacement=AttrDtlForPageSetting.get(s).getAttrValue();break;
				}
			}
	  }
	  
	  String LTRSingleDocFooterFormat=propertyInfo.getValue("LTRSingleDocFooterFormat");
	  Font headingFont = ft.registerExternalFont("ArielFooter","No");
	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
		  

		  if(AttrDtlForPageSetting.size()>0){
			  if(signOffPlacement.equalsIgnoreCase("first")){
				  if(AttrDtlForPageSetting.size()>0){
					  for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
						    page = copy.getImportedPage(reader1, i);
						    stamp = copy.createPageStamp(page);
						    
						    int n1=reader1.getNumberOfPages();
							String pageNoPosition="";
					    	String pageNoFormat="";
						    for(int s=0;s<AttrDtlForPageSetting.size();s++){
						    
						    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page No Position")){
						    		pageNoPosition=AttrDtlForPageSetting.get(s).getAttrValue();
						    	}
						    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page Number Format")){
						    		pageNoFormat=AttrDtlForPageSetting.get(s).getAttrValue();
						    	}
						    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Print Page No")){
						    		printPageNo=AttrDtlForPageSetting.get(s).getAttrValue();
						    	}
						    }
						    //	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page No Position")){
						    		if(pageNoPosition!=null && pageNoPosition!=""){
						    			if(pageNoPosition.equalsIgnoreCase("center") && pageNoFormat.equalsIgnoreCase("1 of n")){
						    				System.out.println("Center and 1 of N");
						    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
						    					if(LTRSingleDocFooterFormat.equals("No")){
						    						ColumnText.showTextAligned(stamp.getOverContent(),
											 	               Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
											 	              300,5,0);
							    				}
							    										    				}
						    				/*ColumnText.showTextAligned(stamp.getOverContent(),
									 	               Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
									 	              300,5,0);*/
						    				if(LTRSingleDocFooterFormat.equals("Yes")){
						    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
						    							155,7,0);
						    				}
						    				else{
						    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
										    		12,6,0);
										    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
										    		470, 6, 0);
						    				}
						    			}
						    			else if(pageNoPosition.equalsIgnoreCase("left") && pageNoFormat.equalsIgnoreCase("1 of n")){
						    				System.out.println("Left and 1 of N");
						    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
						    					if(LTRSingleDocFooterFormat.equals("No")){
						    				ColumnText.showTextAligned(stamp.getOverContent(),
						    						Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
									 	              33,5,0);}
						    				}
						    				if(LTRSingleDocFooterFormat.equals("Yes")){
						    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
						    							155,7,0);
						    				}
						    				else{
						    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
											    		464,18,0);
						    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
											    		590, 7, 0);
						    				}
						    			}
						    			else if(pageNoPosition.equalsIgnoreCase("right") && pageNoFormat.equalsIgnoreCase("1 of n")){
						    				System.out.println("Right and 1 of N");
						    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
						    					if(LTRSingleDocFooterFormat.equals("No")){
						    				ColumnText.showTextAligned(stamp.getOverContent(),
						    						Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
									 	              565,7,0);}}
						    				if(LTRSingleDocFooterFormat.equals("Yes")){
						    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
						    							155,7,0);
						    				}
						    				else{
						    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
											    		120,18,0);
						    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
											    		246, 7, 0);
						    				}
						    			}
						    			else if(pageNoPosition.equalsIgnoreCase("center") && pageNoFormat.equalsIgnoreCase("1")){
						    				System.out.println("Center and 1");
						    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
						    				ColumnText.showTextAligned(stamp.getOverContent(),
									 	               Element.ALIGN_CENTER, new Phrase(String.format(" %s ", i),fontSize_12),
									 	              300,5,0);}
						    				if(LTRSingleDocFooterFormat.equals("Yes")){
						    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
											    		155,7,0);
						    				}
						    				else{
						    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
										    		12,6,0);
										    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
										    		470, 6, 0);
						    				}
						    			}
						    			else if(pageNoPosition.equalsIgnoreCase("left") && pageNoFormat.equalsIgnoreCase("1")){
						    				System.out.println("left and 1");
						    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
						    				ColumnText.showTextAligned(stamp.getOverContent(),
									 	               Element.ALIGN_RIGHT, new Phrase(String.format(" %s ", i),fontSize_12),
									 	              33,5,0);}
						    				if(LTRSingleDocFooterFormat.equals("Yes")){
						    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
						    							155,7,0);
						    				}
						    				else{ 
						    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
											    		464,18,0);
						    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
											    		590, 7, 0);
						    				}
						    			}
						    			else if(pageNoPosition.equalsIgnoreCase("right") && pageNoFormat.equalsIgnoreCase("1")){
						    				System.out.println("right and 1");
						    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
						    				ColumnText.showTextAligned(stamp.getOverContent(),
									 	               Element.ALIGN_RIGHT, new Phrase(String.format(" %s ", i),fontSize_12),
									 	              565,7,0);}
						    				if(LTRSingleDocFooterFormat.equals("Yes")){
						    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
						    							155,7,0);
						    				}
						    				else{
						    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
											    		120,18,0);
						    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
											    		246, 7, 0);
						    				}
						    			}
						    		}else{
						    			if(LTRSingleDocFooterFormat.equals("Yes")){
					    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
					    							155,7,0);
					    				}
					    				else{
						    			  ColumnText.showTextAligned(stamp.getOverContent(),
								 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s ", i, n1),fontSize_12),
								 	              300,5,0);
					    				}
						    		}
						    //	}
						    	/*s*/
						  //  }
				     	 
				     	  /*if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
						    		12,6,0);
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
						    		470, 6, 0);
				     	  }*/
						    stamp.alterContents();
						    copy.addPage(page);
						}
					  Rectangle pagesize;
						for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
						    page = copy.getImportedPage(reader2, i);
						    stamp = copy.createPageStamp(page);
						    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
						    pagesize = reader2.getPageSizeWithRotation(i);
						    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
						    	if(LTRSingleDocFooterFormat.equals("Yes")){
			    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
			    							155,7,0);
			    				}
			    				else{
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
						    		12, 6, 0);
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
						    		 pagesize.getRight()-128, 6, 0);
			    				}
						    }
						    stamp.alterContents();
						    copy.addPage(page);
						}
					  
				  }
				  else{
						for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
						    page = copy.getImportedPage(reader1, i);
						    stamp = copy.createPageStamp(page);
						    
						    int n1=reader1.getNumberOfPages();
						    if(LTRSingleDocFooterFormat.equals("No")){
				     	   ColumnText.showTextAligned(stamp.getOverContent(),
					 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
					 	              300,5,0);}
				     	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
				     		 if(LTRSingleDocFooterFormat.equals("Yes")){
			    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
			    							155,7,0);
			    				}
			    				else{
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
						    		12,6,0);
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
						    		470, 6, 0);
			    				}
				     	  }
						    stamp.alterContents();
						    copy.addPage(page);
						}
				  }
			  }
			  else if(signOffPlacement.equalsIgnoreCase("last")){
				  Rectangle pagesize;
					for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
					    page = copy.getImportedPage(reader2, i);
					    stamp = copy.createPageStamp(page);
					    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
					    pagesize = reader2.getPageSizeWithRotation(i);
					    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
					    	if(LTRSingleDocFooterFormat.equals("Yes")){
		    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
		    							155,7,0);
		    				}
		    				else{
					    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
					    		12, 6, 0);
					    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
					    		 pagesize.getRight()-128, 6, 0);
		    				}
					    }
					    stamp.alterContents();
					    copy.addPage(page);
					}
					 if(AttrDtlForPageSetting.size()>0){
						  for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
							    page = copy.getImportedPage(reader1, i);
							    stamp = copy.createPageStamp(page);
							    
							    int n1=reader1.getNumberOfPages();
								String pageNoPosition="";
						    	String pageNoFormat="";
							    for(int s=0;s<AttrDtlForPageSetting.size();s++){
							    
							    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page No Position")){
							    		pageNoPosition=AttrDtlForPageSetting.get(s).getAttrValue();
							    	}
							    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page Number Format")){
							    		pageNoFormat=AttrDtlForPageSetting.get(s).getAttrValue();
							    	}
							    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Print Page No")){
							    		printPageNo=AttrDtlForPageSetting.get(s).getAttrValue();
							    	}
							    }
							    //	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page No Position")){
							    		if(pageNoPosition!=null && pageNoPosition!=""){
							    			if(pageNoPosition.equalsIgnoreCase("center") && pageNoFormat.equalsIgnoreCase("1 of n")){
							    				System.out.println("Center and 1 of N");
							    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
							    					if(LTRSingleDocFooterFormat.equals("No")){
							    				ColumnText.showTextAligned(stamp.getOverContent(),
										 	               Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
										 	              300,5,0);}}
							    				if(LTRSingleDocFooterFormat.equals("Yes")){
							    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
							    							155,7,0);
							    				}
							    				else{
							    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
											    		12,6,0);
											    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
											    		470, 6, 0);
							    				}
							    			}
							    			else if(pageNoPosition.equalsIgnoreCase("left") && pageNoFormat.equalsIgnoreCase("1 of n")){
							    				System.out.println("Left and 1 of N");
							    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
							    					if(LTRSingleDocFooterFormat.equals("No")){
							    				ColumnText.showTextAligned(stamp.getOverContent(),
							    						Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
										 	              33,5,0);}}
							    				if(LTRSingleDocFooterFormat.equals("Yes")){
							    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
							    							155,7,0);
							    				}
							    				else{
							    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
												    		464,18,0);
							    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
												    		590, 7, 0);
							    				}
							    			}
							    			else if(pageNoPosition.equalsIgnoreCase("right") && pageNoFormat.equalsIgnoreCase("1 of n")){
							    				System.out.println("Right and 1 of N");
							    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
							    					if(LTRSingleDocFooterFormat.equals("No")){
							    				ColumnText.showTextAligned(stamp.getOverContent(),
							    						Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
										 	              565,7,0);}}
							    				if(LTRSingleDocFooterFormat.equals("Yes")){
							    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
							    							155,7,0);
							    				}
							    				else{
							    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
												    		120,18,0);
							    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
												    		246, 7, 0);
							    				}
							    			}
							    			else if(pageNoPosition.equalsIgnoreCase("center") && pageNoFormat.equalsIgnoreCase("1")){
							    				System.out.println("Center and 1");
							    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
							    				ColumnText.showTextAligned(stamp.getOverContent(),
										 	               Element.ALIGN_CENTER, new Phrase(String.format(" %s ", i),fontSize_12),
										 	              300,5,0);}
							    				if(LTRSingleDocFooterFormat.equals("Yes")){
							    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
							    							155,7,0);
							    				}
							    				else{
							    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
											    		12,6,0);
											    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
											    		470, 6, 0);
							    				}
							    			}
							    			else if(pageNoPosition.equalsIgnoreCase("left") && pageNoFormat.equalsIgnoreCase("1")){
							    				System.out.println("left and 1");
							    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
							    				ColumnText.showTextAligned(stamp.getOverContent(),
										 	               Element.ALIGN_RIGHT, new Phrase(String.format(" %s ", i),fontSize_12),
										 	              33,5,0);}
							    				if(LTRSingleDocFooterFormat.equals("Yes")){
							    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
							    							155,7,0);
							    				}
							    				else{
							    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
												    		464,18,0);
							    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
												    		590, 7, 0);
							    				}
							    			}
							    			else if(pageNoPosition.equalsIgnoreCase("right") && pageNoFormat.equalsIgnoreCase("1")){
							    				System.out.println("right and 1");
							    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
							    				ColumnText.showTextAligned(stamp.getOverContent(),
										 	               Element.ALIGN_RIGHT, new Phrase(String.format(" %s ", i),fontSize_12),
										 	              565,7,0);}
							    				if(LTRSingleDocFooterFormat.equals("Yes")){
							    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
							    							155,7,0);
							    				}
							    				else{
							    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
												    		120,18,0);
							    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
												    		246, 7, 0);
							    				}
							    			}
							    		}else{
							    			if(LTRSingleDocFooterFormat.equals("Yes")){
						    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
						    							165,7,0);
						    				}else{
							    			  ColumnText.showTextAligned(stamp.getOverContent(),
									 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s", i, n1),fontSize_12),
									 	              300,5,0);
						    					}
							    		}
							    //	}
							    	/*s*/
							  //  }
					     	 
					     	  /*if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
							    		12,6,0);
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
							    		470, 6, 0);
					     	  }*/
							    stamp.alterContents();
							    copy.addPage(page);
							}
						  /*Rectangle pagesize;
							for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
							    page = copy.getImportedPage(reader2, i);
							    stamp = copy.createPageStamp(page);
							    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
							    pagesize = reader2.getPageSizeWithRotation(i);
							    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
							    		12, 6, 0);
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
							    		 pagesize.getRight()-128, 6, 0); 
							    }
							    stamp.alterContents();
							    copy.addPage(page);
							}*/
						  
					  }
					  else{
							for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
							    page = copy.getImportedPage(reader1, i);
							    stamp = copy.createPageStamp(page);
							    
							    int n1=reader1.getNumberOfPages();
							    if(LTRSingleDocFooterFormat.equals("No")){
					     	   ColumnText.showTextAligned(stamp.getOverContent(),
						 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
						 	              300,5,0);}
					     	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
					     		 if(LTRSingleDocFooterFormat.equals("Yes")){
				    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
				    							165,7,0);
				    				}
				    				else{
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
							    		12,6,0);
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
							    		470, 6, 0);
				    				}
					     	  }
							    stamp.alterContents();
							    copy.addPage(page);
							}
					  }
			  }
			  else{
				  Rectangle pagesize;
					for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
					    page = copy.getImportedPage(reader2, i);
					    stamp = copy.createPageStamp(page);
					    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
					    pagesize = reader2.getPageSizeWithRotation(i);
					    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
					    	if(LTRSingleDocFooterFormat.equals("Yes")){
		    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
		    							155,7,0);
		    				}
		    				else{
					    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
					    		12, 6, 0);
					    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
					    		 pagesize.getRight()-128, 6, 0); 
		    				}
					    }
					    stamp.alterContents();
					    copy.addPage(page);
					}
					 for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
						    page = copy.getImportedPage(reader1, i);
						    stamp = copy.createPageStamp(page);
						    
						    int n1=reader1.getNumberOfPages();
						    if(LTRSingleDocFooterFormat.equals("No")){
				     	   ColumnText.showTextAligned(stamp.getOverContent(),
					 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
					 	              300,5,0);}
				     	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
				     		 if(LTRSingleDocFooterFormat.equals("Yes")){
			    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
			    							155,7,0);
			    				}
			    				else{
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
						    		12,6,0);
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
						    		470, 6, 0);
			    				}
				     	  }
						    stamp.alterContents();
						    copy.addPage(page);
						}
				  
			  }
		  }
		  //AttrDtlForPageSetting=docMgmtImpl.getAttrDtlForPageSetting(workspaceID,nodeId);
		 else{
			 for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
				    page = copy.getImportedPage(reader1, i);
				    stamp = copy.createPageStamp(page);
				    
				    int n1=reader1.getNumberOfPages();
				    if(LTRSingleDocFooterFormat.equals("No")){
		     	   ColumnText.showTextAligned(stamp.getOverContent(),
			 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
			 	              300,5,0);}
		     	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
		     		 if(LTRSingleDocFooterFormat.equals("Yes")){
	    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
	    							155,7,0);
	    				}
	    				else{
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
				    		12,6,0);
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
				    		470, 6, 0);
	    				}
		     	  }
				    stamp.alterContents();
				    copy.addPage(page);
				}
			 Rectangle pagesize;
				for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
				    page = copy.getImportedPage(reader2, i);
				    stamp = copy.createPageStamp(page);
				    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
				    pagesize = reader2.getPageSizeWithRotation(i);
				    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
				    	if(LTRSingleDocFooterFormat.equals("Yes")){
	    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
	    							155,7,0);
	    				}
	    				else{
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
				    		12, 6, 0);
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
				    		 pagesize.getRight()-128, 6, 0);
	    				}
				    }
				    stamp.alterContents();
				    copy.addPage(page);
				}
			  
		  }
		  
		  /*
	for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
	    page = copy.getImportedPage(reader1, i);
	    stamp = copy.createPageStamp(page);
	    
	    int n1=reader1.getNumberOfPages();
 	   ColumnText.showTextAligned(stamp.getOverContent(),
 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
 	              300,5,0);
 	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
	    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
	    		12,6,0);
	    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
	    		470, 6, 0);
 	  }
	    stamp.alterContents();
	    copy.addPage(page);
	}
	  */}
	if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
		AttrDtl=docMgmtImpl.getAttrDtl(workspaceID,nodeId);
		if(AttrDtl.size()>0){
			for (int i = 1; i <= reader3.getNumberOfPages(); i++) {
				  page = copy.getImportedPage(reader3, i);
				    stamp = copy.createPageStamp(page);
				    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
				    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
				    	if(LTRSingleDocFooterFormat.equals("Yes")){
	    					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
	    							155,7,0);
	    				}
	    				else{
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
				    		12, 6, 0);
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
				    		470, 6, 0);
	    				}
				    }
				    stamp.alterContents();
				    copy.addPage(page);
				}
			}
	}
	if(wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
		Rectangle pagesize;
		for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
		    page = copy.getImportedPage(reader2, i);
		    stamp = copy.createPageStamp(page);
		    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
		    pagesize = reader2.getPageSizeWithRotation(i);
		    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
		    	if(LTRSingleDocFooterFormat.equals("Yes")){
					ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence No Handwritten signature is required",headingFont),
							155,7,0);
				}
				else{
		    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
		    		12, 6, 0);
		    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
		    		 pagesize.getRight()-128, 6, 0);
				}
		    }
		    stamp.alterContents();
		    copy.addPage(page);
		}
	}
	
	
	document.close();
	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
	reader1.close();}
	reader2.close();
	fos1.close();
   
    out.flush();
    //d1.close();
  
    out.close();
    
	
    
    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
    Files.delete(Paths.get(path.toString()));}
    if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
    	if(AttrDtl.size()>0 ){
    		Files.delete(Paths.get(pathForAttribute.toString()));
    	}
    }
    if(uploadedFile.exists()){
    	Files.delete(Paths.get(uploadedFile.toString()));
    }
        	    
    File oldName = new File(baseSrcPath + "/" + workspaceID + "/"
			+ nodeId + "/" + uuid+ "/" + "Final.pdf"); 
    File newName = new File(baseSrcPath + "/" + workspaceID + "/"
			+ nodeId + "/" + uuid+ "/" + Name +".pdf"); 
     
   if (oldName.renameTo(newName))  
       System.out.println("Renamed successfully");         
   else 
      System.out.println("Error"); 

   
   src=(String) nodeRec[9];
   System.out.println("src=(String) nodeRec[9]---------"+src);
   fileName=previousHistory.get(0).getFileName();
   System.out.println("fileName---------"+fileName);
	src+="/"+previousHistory.get(0).getFolderName()+"/"+ Name +".pdf";
	System.out.println("src+=---------"+src);
   
   File signOffFile = new File(src);
   System.out.println("signOffFile---------"+signOffFile);
   System.out.println("newName---------"+newName);
   System.out.println("signOffFile---------"+signOffFile);
   try{
   FileUtils.copyFile(newName, signOffFile);
   }
   catch(Exception e){
	   System.out.println(e.toString());
   }
	 //DownloadFolderPath=baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name+".pdf";
   System.out.println("DownloadFolderPath---------"+DownloadFolderPath);
   DownloadFolderPath = signOffFile.toString().replace('\\', '/');
   System.out.println("after DownloadFolderPath---------"+DownloadFolderPath);
	htmlContent=DownloadFolderPath;
	System.out.println("htmlContent---------"+htmlContent);
	 
	return "html";
	}
}

public String certifiedAndDownload() throws SQLException, IOException, DocumentException, ParseException {

	

	wordToPdf wtp=new wordToPdf();
	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	baseSrcPath = propertyInfo.getValue("DoToPdfPath");
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
	int lastTranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
	previousHistory = docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId,lastTranNo);
	UUID uuid=UUID.randomUUID();
	

	Vector<Object[]> WsDetail= docMgmtImpl.getWorkspaceDescList(workspaceID);
	Object[] nodeRec = (Object[]) WsDetail.get(0);
	String src=(String) nodeRec[9];
	
	String fileName=previousHistory.get(0).getFileName();
	src+="/"+previousHistory.get(0).getFolderName()+"/"+ previousHistory.get(0).getFileName();
	File fSrc=new File(src);
	
	String existedName=previousHistory.get(0).getFileName();
    int existedPos = existedName.lastIndexOf(".");
    if (existedPos > 0) {
    	existedName = existedName.substring(0, existedPos);
    }
	String pdfFileName=existedName+".pdf";
	String baseWorkFolder=propertyInfo.getValue("BaseWorkFolder");
	File check =new File(baseWorkFolder + previousHistory.get(0).getFolderName()+ "/"+ pdfFileName);
	if((fileName.endsWith("DOCX") || fileName.endsWith("DOC") || fileName.endsWith("docx") || fileName.endsWith("doc")) && !check.exists()){
		File wsFolder = new File(baseSrcPath + "/" + workspaceID);
		wsFolder.mkdirs();

		File nodeFolder = new File(baseSrcPath + "/" + workspaceID + "/"
				+ nodeId);
		nodeFolder.mkdirs();

		//if (lastTranNo != -1) {
			File tranFolder = new File(baseSrcPath + "/" + workspaceID + "/"
					+ nodeId + "/" + uuid);
			tranFolder.mkdirs();
			File wsDocFile = new File(tranFolder+"/"+fileName);
			try {
				Files.copy(fSrc.toPath(), wsDocFile.toPath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//}

	}
	
		Vector<DTOWorkSpaceNodeDetail> getNodeDetail1 = new Vector<DTOWorkSpaceNodeDetail>();
	getNodeDetail1 = docMgmtImpl.getNodeDetail(workspaceID, nodeId);
		UUID uid=UUID.randomUUID();
		String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
		String srcFlExt = previousHistory.get(0).getFileName().substring(previousHistory.get(0).getFileName().lastIndexOf(".") + 1);
						
		uploadedFile = new File(baseSrcPath + "/" + workspaceID + "/" + nodeId + "/" + uuid + "/" + fileNameWithOutExt+"_"+uid+"."+srcFlExt);
	
	File srcDoc = new File(baseSrcPath + previousHistory.get(0).getFolderName()
			+ "/" + previousHistory.get(0).getFileName());
	if(uploadedFile.exists()){
		uploadedFile.delete();
	}
	

	 String Name=previousHistory.get(0).getFileName();
	    int pos = Name.lastIndexOf(".");
	    if (pos > 0) {
	    	Name = Name.substring(0, pos);
	    }
	String srcFileExt = previousHistory.get(0).getFileName().substring(previousHistory.get(0).getFileName().lastIndexOf(".") + 1);
	if(srcFileExt.equalsIgnoreCase("pdf")){
		System.out.println("Aleready Pdf");
		File oldName = new File(baseSrcPath + "/" + workspaceID + "/" + nodeId + "/" + uuid + "/" + fileNameWithOutExt+"_"+uid+"."+srcFlExt); 
	    File newName = new File(baseSrcPath + "/" + workspaceID + "/" + nodeId + "/" + uuid+ "/" + Name +".pdf");
	    if (oldName.renameTo(newName))  
	        System.out.println("Renamed successfully");         
	    else 
	       System.out.println("Error");
	    //DownloadFolderPath=baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name+".pdf";
	    //htmlContent=DownloadFolderPath;	 
	    //return "html";
	    uploadFile=check;
		}
	else{
		
		if(check.exists()){
			System.out.println("Aleready converted Pdf exists....");
			//out = new FileOutputStream(new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf"));
			uploadFile=check;
		}
		else{
			String response="";
			//String srcPath=src.replace("\\", "/");
			String srcPath=src;
			//String destPath=(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf").replace("\\", "/");
			String destPath=(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf");
						
			try{
				
				
				System.out.println("srcDocPath="+srcPath);
				System.out.println("destDocPath="+destPath);
				response=docMgmtImpl.convertDoc(uploadedFile.getAbsolutePath(),destPath);
				//response=docMgmtImpl.convertPdfToDoc(srcPath,destPath);
				
				/*String[] parts = response.split(":");
				if(parts[0].equalsIgnoreCase("error") || parts[0].contains("error")){
					htmlContent=parts[2];
					return "html";
					response=docMgmtImpl.convertDoc(uploadedFile.getAbsolutePath(),destPath);
					//String[] parts = response.split(":");
					if(parts[0].equalsIgnoreCase("error")){
						htmlContent=parts[2];
						return "html";
					}
					parts = response.split("#");
					if(parts[0].equalsIgnoreCase("False")){
						htmlContent=parts[2];
						return "html";
					}
				}*/
				
				String[] parts = response.split("#");
				if(parts[0].equalsIgnoreCase("False")){
					htmlContent=parts[2];
					return "html";
				}
				//if(parts[0].equalsIgnoreCase("error")){
					//response=docMgmtImpl.convertDoc(uploadedFile.getAbsolutePath(),destPath);
					//String[] parts = response.split(":");
					//parts = response.split("#");
					//if(parts[0].equalsIgnoreCase("False")){
//						htmlContent=parts[2];
						//return "html";
					//}
				//}
			
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			String[] parts = response.split("#");
			if(parts[0].equalsIgnoreCase("False")){
				htmlContent=parts[2];
				return "html";
			}
			Path sourcePath = Paths.get(destPath);
		     Path destinationPath = Paths.get(baseWorkFolder + previousHistory.get(0).getFolderName()+ "/"+ pdfFileName);
			Files.copy(sourcePath,destinationPath, StandardCopyOption.REPLACE_EXISTING);
		}
		
		FileManager.deleteFile(uploadedFile);
		
	}        
	if(!check.exists()){
	 DownloadFolderPath=baseSrcPath + "/" + workspaceID + "/"
				+ nodeId + "/" + uuid+ "/" + Name+".pdf";
	}
	else{
		DownloadFolderPath=check.getAbsolutePath().toString();
		DownloadFolderPath=DownloadFolderPath.replace("\\", "/");
	}
	 
	 
	 boolean certifiedWithAttr = false;
	    Vector<DTOWorkSpaceNodeAttribute> attributeDetail = docMgmtImpl.getNodeAttributes(workspaceID,nodeId);
	 	for(int i=0;i<attributeDetail.size();i++){
	 		if(attributeDetail.get(i).getAttrName().equalsIgnoreCase("Type of Signature")
	 				  && attributeDetail.get(i).getAttrValue().equalsIgnoreCase("Physical")){
	 			  certifiedWithAttr =true;
	 		  }
	 	  }
	   if(certifiedWithAttr==true){
	   int tranNo = docMgmtImpl.getMaxTranNoByStageId(workspaceID,nodeId);
	   
	   ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
				.showFullNodeHistoryByStageId(workspaceID, nodeId,tranNo);
	   
	   usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	   
	   ArrayList<String> time = new ArrayList<String>();
	   int uploadedUserCode = tempHistory.get(0).getUserCode();
	   String locationName="";	   
	   //String uploadedusername = tempHistory.get(0).getUserName();
	   String uploadedusername = tempHistory.get(0).getCertifiedByName();
	   
	   Vector<DTOUserMst> useDtl = new Vector<DTOUserMst>();
		useDtl = docMgmtImpl.getUserLoaction(uploadedUserCode);
		if(useDtl.size()>0){
			locationName = useDtl.get(0).getLocationName();
			countryCode = useDtl.get(0).getCountyCode();
		}
	   
	   String cretifiedFilePath = propertyInfo.getValue("CertifiedFilePath");
	         
       time = docMgmtImpl.TimeZoneConversion(tempHistory.get(0).getCertifiedOn(),locationName,countryCode);
       
		String modify ="";
		if(countryCode.equalsIgnoreCase("IND")){
			modify= time.get(0);
		}
		else{
			modify= time.get(1);
		}
	   
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
		String destFile ;
		OutputStream fos = null;
		File fileForPdfReader; 
		//checking if certified file is docx 
		if(fileName.endsWith("docx") || fileName.endsWith("doc") || fileName.endsWith("DOCX") || fileName.endsWith("DOC")){
			//checking if certified file doc then first check if converted pdf is available or not
			if(check.exists()){
				// if converted pdf is available then take it from workspace folder
				File wsFolder = new File(baseSrcPath + "/" + workspaceID);
				wsFolder.mkdirs();

				File nodeFolder = new File(baseSrcPath + "/" + workspaceID + "/"
						+ nodeId);
				nodeFolder.mkdirs();

				//if (lastTranNo != -1) {
					File tranFolder = new File(baseSrcPath + "/" + workspaceID + "/"
							+ nodeId + "/" + uuid);
				tranFolder.mkdirs();
				File destFilePath = new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf");
				 Path sourcePath = Paths.get(check.getAbsolutePath());
			     Path destinationPath = Paths.get(destFilePath.getAbsolutePath());
				//Files.copy(sourcePath,destinationPath, StandardCopyOption.REPLACE_EXISTING);
				fos = new FileOutputStream(destFilePath);
				fileForPdfReader = new File(check.toString());
			}
			else{
				//converting doc to pdf for certified file

				String response="";
				//String srcPath=src.replace("\\", "/");
				String srcPath=src;
				//String destPath=(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf").replace("\\", "/");
				String destPath=(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf");
							
				try{
					
					
					System.out.println("srcDocPath="+srcPath);
					System.out.println("destDocPath="+destPath);
					response=docMgmtImpl.convertDoc(uploadedFile.getAbsolutePath(),destPath);
					//response=docMgmtImpl.convertPdfToDoc(srcPath,destPath);
					
					/*String[] parts = response.split(":");
					if(parts[0].equalsIgnoreCase("error") || parts[0].contains("error")){
						htmlContent=parts[2];
						return "html";
						response=docMgmtImpl.convertDoc(uploadedFile.getAbsolutePath(),destPath);
						//String[] parts = response.split(":");
						if(parts[0].equalsIgnoreCase("error")){
							htmlContent=parts[2];
							return "html";
						}
						parts = response.split("#");
						if(parts[0].equalsIgnoreCase("False")){
							htmlContent=parts[2];
							return "html";
						}
					}*/
					
					String[] parts = response.split("#");
					if(parts[0].equalsIgnoreCase("False")){
						htmlContent=parts[2];
						return "html";
					}
					//if(parts[0].equalsIgnoreCase("error")){
						//response=docMgmtImpl.convertDoc(uploadedFile.getAbsolutePath(),destPath);
						//String[] parts = response.split(":");
						//parts = response.split("#");
						//if(parts[0].equalsIgnoreCase("False")){
//							htmlContent=parts[2];
							//return "html";
						//}
					//}
				
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				}
				String[] parts = response.split("#");
				if(parts[0].equalsIgnoreCase("False")){
					htmlContent=parts[2];
					return "html";
				}
				//fos = new FileOutputStream(destPath);
				fos =  new FileOutputStream(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf");
				fileForPdfReader = new File(check.toString());
			}
		}
		else{
			File wsFolder = new File(baseSrcPath + "/" + workspaceID);
			wsFolder.mkdirs();

			File nodeFolder = new File(baseSrcPath + "/" + workspaceID + "/"
					+ nodeId);
			nodeFolder.mkdirs();

			//if (lastTranNo != -1) {
				File tranFolder = new File(baseSrcPath + "/" + workspaceID + "/"
						+ nodeId + "/" + uuid);
			tranFolder.mkdirs();
			File destFilePath = new File(wsIdpath+"/"+nodeId+"/"+tranNo+"/"+fileName);
			 Path sourcePath = Paths.get(check.getAbsolutePath());
		     Path destinationPath = Paths.get(destFilePath.getAbsolutePath());
		     System.out.println("sourcePath ----------"+sourcePath );
		     System.out.println("destinationPath ----------"+destinationPath );
		     try{
		    	 Files.copy(sourcePath,destinationPath, StandardCopyOption.REPLACE_EXISTING);
		     }
		     catch(Exception e){
		    	 System.out.println("error----------\\\\\\\\\\\\\\\\"+e.toString());
		     }
			//fos = new FileOutputStream(destFilePath);
		     System.out.println("fos to be made-----------"+baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf");
			fos =  new FileOutputStream(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf");
			System.out.println("fileForPdfReader------------"+check.toString());
			fileForPdfReader = new File(check.toString());
		}
		
	/*   File srcFilePath = new File(DownloadFolderPath); // Existing file
	   System.out.println("srcFilePath----------"+srcFilePath);
		//File outputFilePath = new File(tempFile.getAbsolutePath()); // New file
		
		File destFilePath = new File(destFile); // New file
		System.out.println("destFilePath----------"+destFilePath);
		
		try{
			fos = new FileOutputStream(destFilePath);
		}
		catch(Exception e){
			System.out.println("ErrorStackTrace=======----------=========="+e.toString());
		}
		System.out.println("check----------"+check.getAbsolutePath());*/
		
		PdfReader pdfReader = new PdfReader(fileForPdfReader.getAbsolutePath());

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
			pdfContentByte.showText("*This is an electronically certified true copy. Certified By: "+uploadedusername + " On: "+modify); // set Stamp
   
			System.out.println("Text added in "+fileForPdfReader);
   
			pdfContentByte.endText();
   
		}

		pdfStamper.close(); //close pdfStamper
		fos.close();
		 /*Path sourcePath = Paths.get(srcFilePath.getAbsolutePath());
	     Path destinationPath = Paths.get(destFilePath.getAbsolutePath());
		 System.out.println("sourcePath -------------"+sourcePath);
		 System.out.println("destinationPath -------------"+destinationPath);
		 try{
	     Files.copy(destinationPath,sourcePath, StandardCopyOption.REPLACE_EXISTING);
		 }
		 catch(Exception e){
			 System.out.println("error--------------"+e.toString());		
		 }*/
   	
		/*File outFile = null;
		outFile = new File(outputFilePath.getAbsolutePath());

		new File(inputFilePath.getAbsolutePath()).delete();

		outFile.renameTo(new File(inputFilePath.getAbsolutePath()));*/
	
		//System.out.println("Uploaded File Path: >> "+ srcFilePath);

		//System.out.println("After Add Watermark File Path: >> "+ destFile);
	   }
	 
	   File oldName = new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf");
	   File newName = new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Certified.pdf"); 
	     
	   if (oldName.renameTo(newName))  
	       System.out.println("Renamed successfully");         
	   else 
	      System.out.println("Error");
	   
	   
	 htmlContent=baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Certified.pdf";//DownloadFolderPath;
	 
	return "html";
	

	
	/*
	

	wordToPdf wtp=new wordToPdf();
	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	baseSrcPath = propertyInfo.getValue("DoToPdfPath");
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
	int lastTranNo = docMgmtImpl.getMaxTranNo(workspaceID, nodeId);
	previousHistory = docMgmtImpl.getWorkspaceNodeHistory(workspaceID, nodeId,lastTranNo);
	UUID uuid=UUID.randomUUID();
	

	Vector<Object[]> WsDetail= docMgmtImpl.getWorkspaceDescList(workspaceID);
	Object[] nodeRec = (Object[]) WsDetail.get(0);
	String src=(String) nodeRec[9];
	
	String fileName=previousHistory.get(0).getFileName();
	src+="/"+previousHistory.get(0).getFolderName()+"/"+ previousHistory.get(0).getFileName();
	File fSrc=new File(src);
	
	String existedName=previousHistory.get(0).getFileName();
    int existedPos = existedName.lastIndexOf(".");
    if (existedPos > 0) {
    	existedName = existedName.substring(0, existedPos);
    }
	String pdfFileName=existedName+".pdf";
	String baseWorkFolder=propertyInfo.getValue("BaseWorkFolder");
	File check =new File(baseWorkFolder + previousHistory.get(0).getFolderName()+ "/"+ pdfFileName);
	
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
	Vector<DTOWorkSpaceNodeDetail> getNodeDetail1 = new Vector<DTOWorkSpaceNodeDetail>();
	getNodeDetail1 = docMgmtImpl.getNodeDetail(workspaceID, nodeId);
		UUID uid=UUID.randomUUID();
		String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
		String srcFlExt = previousHistory.get(0).getFileName().substring(previousHistory.get(0).getFileName().lastIndexOf(".") + 1);
						
		uploadedFile = new File(baseSrcPath + "/" + workspaceID + "/" + nodeId + "/" + uuid + "/" + fileNameWithOutExt+"_"+uid+"."+srcFlExt);
	
	File srcDoc = new File(baseSrcPath + previousHistory.get(0).getFolderName()
			+ "/" + previousHistory.get(0).getFileName());
	if(uploadedFile.exists()){
		uploadedFile.delete();
	}
	try {
		Files.copy(fSrc.toPath(), uploadedFile.toPath());
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	 String Name=previousHistory.get(0).getFileName();
	    int pos = Name.lastIndexOf(".");
	    if (pos > 0) {
	    	Name = Name.substring(0, pos);
	    }
	String srcFileExt = previousHistory.get(0).getFileName().substring(previousHistory.get(0).getFileName().lastIndexOf(".") + 1);
	if(srcFileExt.equalsIgnoreCase("pdf")){
		System.out.println("Aleready Pdf");
		File oldName = new File(baseSrcPath + "/" + workspaceID + "/" + nodeId + "/" + uuid + "/" + fileNameWithOutExt+"_"+uid+"."+srcFlExt); 
	    File newName = new File(baseSrcPath + "/" + workspaceID + "/" + nodeId + "/" + uuid+ "/" + Name +".pdf");
	    if (oldName.renameTo(newName))  
	        System.out.println("Renamed successfully");         
	    else 
	       System.out.println("Error");
	    DownloadFolderPath=baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name+".pdf";
	    htmlContent=DownloadFolderPath;	 
	    return "html";
		}
	else{
		
		if(check.exists()){
			System.out.println("Aleready converted Pdf exists....");
			//out = new FileOutputStream(new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf"));
			uploadFile=check;
		}
		else{
			String response="";
			//String srcPath=src.replace("\\", "/");
			String srcPath=src;
			//String destPath=(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf").replace("\\", "/");
			String destPath=(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf");
						
			try{
				
				
				System.out.println("srcDocPath="+srcPath);
				System.out.println("destDocPath="+destPath);
				//response=docMgmtImpl.convertDoc(uploadedFile.getAbsolutePath(),destPath);
				response=docMgmtImpl.convertDoc(srcPath,destPath);
				
				String[] parts = response.split(":");
				if(parts[0].equalsIgnoreCase("error")){
					htmlContent=parts[2];
					return "html";
				}
			
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			String[] parts = response.split("#");
			if(parts[0].equalsIgnoreCase("False")){
				htmlContent=parts[2];
				return "html";
			}
		}
		
		FileManager.deleteFile(uploadedFile);
		
	}        
   
	 DownloadFolderPath=baseSrcPath + "/" + workspaceID + "/"
				+ nodeId + "/" + uuid+ "/" + Name+".pdf";
	 
	 
	 
	 boolean certifiedWithAttr = false;
	    Vector<DTOWorkSpaceNodeAttribute> attributeDetail = docMgmtImpl.getNodeAttributes(workspaceID,nodeId);
	 	for(int i=0;i<attributeDetail.size();i++){
	 		if(attributeDetail.get(i).getAttrName().equalsIgnoreCase("Type of Signature")
	 				  && attributeDetail.get(i).getAttrValue().equalsIgnoreCase("Physical")){
	 			  certifiedWithAttr =true;
	 		  }
	 	  }
	   if(certifiedWithAttr==true){
	   int tranNo = docMgmtImpl.getMaxTranNoByStageId(workspaceID,nodeId);
	   
	   ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
				.showFullNodeHistoryByStageId(workspaceID, nodeId,tranNo);
	   
	   usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	   
	   ArrayList<String> time = new ArrayList<String>();
	   int uploadedUserCode = tempHistory.get(0).getUserCode();
	   String locationName="";	   
	   //String uploadedusername = tempHistory.get(0).getUserName();
	   String uploadedusername = tempHistory.get(0).getCertifiedByName();
	   
	   Vector<DTOUserMst> useDtl = new Vector<DTOUserMst>();
		useDtl = docMgmtImpl.getUserLoaction(uploadedUserCode);
		if(useDtl.size()>0){
			locationName = useDtl.get(0).getLocationName();
			countryCode = useDtl.get(0).getCountyCode();
		}
	   
	   String cretifiedFilePath = propertyInfo.getValue("CertifiedFilePath");
	         
       time = docMgmtImpl.TimeZoneConversion(tempHistory.get(0).getCertifiedOn(),locationName,countryCode);
       
		String modify ="";
		if(countryCode.equalsIgnoreCase("IND")){
			modify= time.get(0);
		}
		else{
			modify= time.get(1);
		}
	   
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
		
		String destFile = wsIdpath+"/"+nodeId+"/"+tranNo+"/"+fileName;
	   
	   File srcFilePath = new File(DownloadFolderPath); // Existing file
		//File outputFilePath = new File(tempFile.getAbsolutePath()); // New file
		
		File destFilePath = new File(destFile); // New file

		OutputStream fos = new FileOutputStream(destFilePath);

		PdfReader pdfReader = new PdfReader(check.getAbsolutePath());

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
			pdfContentByte.showText("*This is an electronically certified true copy. Certified By: "+uploadedusername + " On: "+modify); // set Stamp
   
			System.out.println("Text added in "+destFilePath);
   
			pdfContentByte.endText();
   
		}

		pdfStamper.close(); //close pdfStamper

		 Path sourcePath = Paths.get(srcFilePath.getAbsolutePath());
	     Path destinationPath = Paths.get(destFilePath.getAbsolutePath());
		 
	     Files.copy(destinationPath,sourcePath, StandardCopyOption.REPLACE_EXISTING);
   	
		File outFile = null;
		outFile = new File(outputFilePath.getAbsolutePath());

		new File(inputFilePath.getAbsolutePath()).delete();

		outFile.renameTo(new File(inputFilePath.getAbsolutePath()));
	
		System.out.println("Uploaded File Path: >> "+ srcFilePath);

		System.out.println("After Add Watermark File Path: >> "+ destFile);
	   }
	 
	 
	 htmlContent=DownloadFolderPath;
	 
	return "html";
	
*/}


	// getters and setters

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getForumTextArea() {
		return forumTextArea;
	}

	public void setForumTextArea(String forumTextArea) {
		this.forumTextArea = forumTextArea;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public Vector getAttrDtl() {
		return attrDtl;
	}

	public void setAttrDtl(Vector attrDtl) {
		this.attrDtl = attrDtl;
	}

	public Vector getUsers() {
		return users;
	}

	public String getTakeVersion() {
		return takeVersion;
	}

	public void setTakeVersion(String takeVersion) {
		this.takeVersion = takeVersion;
	}

	public String getStatusIndi() {
		return statusIndi;
	}

	public Vector getLockedFileDetail() {
		return lockedFileDetail;
	}

	public void setLockedFileDetail(Vector lockedFileDetail) {
		this.lockedFileDetail = lockedFileDetail;
	}

	public Vector getNodeHistory() {
		return nodeHistory;
	}

	public void setNodeHistory(Vector nodeHistory) {
		this.nodeHistory = nodeHistory;
	}

	public Vector getCheckedOutBy() {
		return checkedOutBy;
	}

	public void setCheckedOutBy(Vector checkedOutBy) {
		this.checkedOutBy = checkedOutBy;
	}

	public Vector getAttrHistory() {
		return attrHistory;
	}

	public void setAttrHistory(Vector attrHistory) {
		this.attrHistory = attrHistory;
	}

	public boolean isNodeLocked() {
		return isNodeLocked;
	}

	public void setNodeLocked(boolean isNodeLocked) {
		this.isNodeLocked = isNodeLocked;
	}

	public int getIsLeafNode() {
		return isLeafNode;
	}

	public void setIsLeafNode(int isLeafNode) {
		this.isLeafNode = isLeafNode;
	}

	public String getTempBaseFolderReplaced() {
		return tempBaseFolderReplaced;
	}

	public void setTempBaseFolderReplaced(String tempBaseFolderReplaced) {
		this.tempBaseFolderReplaced = tempBaseFolderReplaced;
	}

	public int getAttrCount() {
		return attrCount;
	}

	public void setAttrCount(int attrCount) {
		this.attrCount = attrCount;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getNodeDisplayName() {
		return nodeDisplayName;
	}

	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getSopname() {
		return sopname;
	}

	public void setSopname(String sopname) {
		this.sopname = sopname;
	}

	public int getStageCode() {
		return stageCode;
	}

	public void setStageCode(int stageCode) {
		this.stageCode = stageCode;
	}

	public String getFullPathName() {
		return fullPathName;
	}

	public void setFullPathName(String fullPathName) {
		this.fullPathName = fullPathName;
	}

	public String getLastPublishedVersion() {
		return lastPublishedVersion;
	}

	public void setLastPublishedVersion(String lastPublishedVersion) {
		this.lastPublishedVersion = lastPublishedVersion;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getFullNodeHistory() {
		return fullNodeHistory;
	}

	public void setFullNodeHistory(
			ArrayList<DTOWorkSpaceNodeHistory> fullNodeHistory) {
		this.fullNodeHistory = fullNodeHistory;
	}

	public int getTranNoToRevert() {
		return tranNoToRevert;
	}

	public void setTranNoToRevert(int tranNoToRevert) {
		this.tranNoToRevert = tranNoToRevert;
	}

	public File getUploadDoc() {
		return uploadDoc;
	}

	public void setUploadDoc(File uploadDoc) {
		this.uploadDoc = uploadDoc;
	}

	public String getUploadDocFileName() {
		return uploadDocFileName;
	}

	public void setUploadDocFileName(String uploadDocFileName) {
		this.uploadDocFileName = uploadDocFileName;
	}

	public String getUploadDocContentType() {
		return uploadDocContentType;
	}

	public void setUploadDocContentType(String uploadDocContentType) {
		this.uploadDocContentType = uploadDocContentType;
	}

	public ArrayList<DTOWorkspaceNodeDocHistory> getNodeDocHistory() {
		return nodeDocHistory;
	}

	public void setNodeDocHistory(
			ArrayList<DTOWorkspaceNodeDocHistory> nodeDocHistory) {
		this.nodeDocHistory = nodeDocHistory;
	}

	public String getAllowAutoCorrectionPDFProp() {
		return allowAutoCorrectionPDFProp;
	}

	public void setAllowAutoCorrectionPDFProp(String allowAutoCorrectionPDFProp) {
		this.allowAutoCorrectionPDFProp = allowAutoCorrectionPDFProp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getHistorysize() {
		return historysize;
	}

	public void setHistorysize(int historysize) {
		this.historysize = historysize;
	}
	public String getNodeFolderName() {
		return nodeFolderName;
	}

	public void setNodeFolderName(String nodeFolderName) {
		this.nodeFolderName = nodeFolderName;
	}

	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public boolean isSubmittedNodeHistory() {
		return submittedNodeHistory;
	}

	public void setSubmittedNodeHistory(boolean submittedNodeHistory) {
		this.submittedNodeHistory = submittedNodeHistory;
	}

	public String getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(String checkDelete) {
		this.checkDelete = checkDelete;
	}

	public String getProject_type() {
		return project_type;
	}

	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	
	public String geteSign() {
		return eSign;
	}

	public void seteSign(String eSign) {
		this.eSign = eSign;
	}



	class MyAuth extends Authenticator {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("nirav.parmar@sarjen.com", "");
		}

	}
}