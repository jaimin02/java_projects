package com.docmgmt.struts.actions.DMSUserRepository;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.print.PrintService;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTODocReleaseTrack;
import com.docmgmt.dto.DTOUserDocStageComments;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.services.pdf.PDFUtilities;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveDocAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workSpaceId;
	public String nodeId;
	public String nodeName;
	public String folderName;
	public String userComments;
	public String mode;
	public int stageId;
	public File attachment;
	public String attachmentFileName;
	public String sourceFileContentType;
	public String documentSrc;
	public String stageDesc;
	public String htmlContent;
	public String printDocId;
	public String fileUploading;
	public String attachmentContentType;
	public String mtype="";
	public int attrCount;
	public String repeatNo="1";
	public int parentNodeId;
	public String printing;
	@Override
	public String execute()
	{
		if(mode.equals("add"))
			addNewData();
		else if(mode.equals("edit"))
			editData();
		return SUCCESS;
	}
	
	private void addNewData()
	{
		
		if (mtype.trim().equalsIgnoreCase("n"))
		{
			saveData();
			htmlContent="Document Attached To Category";
		}
		else
		{
			String targetFilePath=""; 
			if (repeatNo == null || repeatNo.trim().equals("")) 
				repeatNo = "1";
			int repet = Integer.parseInt(repeatNo);
			String startId = folderName;
			int printError = 0;
			for (int i = 0; i < repet; i++) 
			{
				targetFilePath = saveData();
				//For Printing Released File
				if (printing != null && printing.trim().equalsIgnoreCase("yes"))
					
				{
					int x=0;
					assert (x>0) :"dasd";
					PrinterJob printJob = PrinterJob.getPrinterJob();
			        PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
			        PrintService[] printServices = PrinterJob.lookupPrintServices();
			        if (printServices.length > 0) 
			        {
			        	PDFUtilities p = new PDFUtilities();
						try 
						{
							
						
							
							p.printPDF(targetFilePath, "", "KnowledgeNET:"+folderName);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
							printError=1; 
						}
					}
			        else
			        	printError = 2;
				}
				
				String inc = folderName.substring(folderName.lastIndexOf("-")+1);
				int incLen = inc.length();
				int newInt = Integer.parseInt(inc);
				newInt++;	
				String finalValueStr=Integer.toString(newInt);
				int incdLen=finalValueStr.length();				
				for(int k=0; k<(incLen-incdLen); k++)
					finalValueStr = "0" + finalValueStr;
				if (i < repet-1)			
					folderName = folderName.substring(0, folderName.lastIndexOf("-")+1)+finalValueStr;
			}
			
			ArrayList<DTODocReleaseTrack> docReleaseTrackList = new ArrayList<DTODocReleaseTrack>();
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			DTODocReleaseTrack dtoDocReleaseTrack = new DTODocReleaseTrack();
			dtoDocReleaseTrack.setWorkspaceId(workSpaceId);
			dtoDocReleaseTrack.setParentNodeId(parentNodeId);
			dtoDocReleaseTrack.setQty(repet);
			dtoDocReleaseTrack.setStartId(startId);
			dtoDocReleaseTrack.setEndId(folderName);
			dtoDocReleaseTrack.setReleasedBy(userId);
			dtoDocReleaseTrack.setComments(userComments);
			docReleaseTrackList.add(dtoDocReleaseTrack);
			docMgmtImpl.insertDocReleaseTrack(docReleaseTrackList);
			
			if (repet == 1) 
				targetFilePath="#"+targetFilePath;
			else
				targetFilePath="";
			if (printError == 1) 
				htmlContent = "Document Released Successfully. Error While Printing Document.";
			else if (printError == 2) 
				htmlContent = "Document Released Successfully. Printer Not Found.";	
			else
				htmlContent = "Document Released Successfully."+targetFilePath;
		}
	}
	
	private boolean editData()
	{
		int []nodeIds = {Integer.parseInt(nodeId)};
		ArrayList<DTOWorkSpaceNodeHistory> WsNodeHistoryList = docMgmtImpl.getAllNodesLastHistory(workSpaceId, nodeIds);
		DTOWorkSpaceNodeHistory dtoWsNdhis = new DTOWorkSpaceNodeHistory();
		if (WsNodeHistoryList.size()>0) 
			dtoWsNdhis = WsNodeHistoryList.get(WsNodeHistoryList.size()-1);
		
		if (dtoWsNdhis.getStageId() > 0 && (attachment == null && fileUploading.trim().equalsIgnoreCase("Current"))) 
		{
			htmlContent = "Document Stage Changed.";
			return true;
		}
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		//Insert node History with file
		//-----------------------------------------------------------------------------------------------------------
		//Get new TranNo
		int newTranNo = 0;
		boolean insertAll = false;
		if ((stageDesc != null && stageDesc.trim().equals("Created")) ||
				(attachment != null && fileUploading.trim().equalsIgnoreCase("Send back"))) 
		{
			newTranNo=docMgmtImpl.getNewTranNo(workSpaceId);
			insertAll = true;
		}
		else
			newTranNo= docMgmtImpl.getMaxTranNo(workSpaceId, Integer.parseInt(nodeId));

		File sourceFile,targetFile;
		String folderNm="";
		String fileNm="";
		String fileTp="";
		DTOWorkSpaceMst workSpaceMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		
		
		if (attachment != null && fileUploading.trim().equalsIgnoreCase("Send back")) 
		{
			sourceFile= attachment;
			fileNm=attachmentFileName;
			fileTp=attachmentContentType;
			folderNm="/"+workSpaceId+"/"+nodeId+"/"+newTranNo;
			targetFile = new File(workSpaceMst.getBaseWorkFolder()+folderNm+"/"+fileNm);
			FileManager fileManager = new FileManager();
			fileManager.copyDirectory(sourceFile, targetFile);
		}
		else
		{
			folderNm=dtoWsNdhis.getFolderName();
			fileNm=dtoWsNdhis.getFileName();
			fileTp=dtoWsNdhis.getFileType();
		}

		stageId = 0;
		if (stageDesc != null && stageDesc.trim().equals("Created")) 
			stageId = 10;
		
		if (insertAll) 
		{
			DTOWorkSpaceNodeHistory dtoWsNodeHistory = new DTOWorkSpaceNodeHistory();
			dtoWsNodeHistory.setWorkSpaceId(workSpaceId);
			dtoWsNodeHistory.setNodeId(Integer.parseInt(nodeId));
			dtoWsNodeHistory.setFolderName(folderNm);
			dtoWsNodeHistory.setTranNo(newTranNo);
			dtoWsNodeHistory.setStageId(stageId);
			dtoWsNodeHistory.setFileName(fileNm);
			dtoWsNodeHistory.setFileType(fileTp);
			dtoWsNodeHistory.setRequiredFlag('Y');
			dtoWsNodeHistory.setRemark("");
			dtoWsNodeHistory.setModifyBy(userId);
			dtoWsNodeHistory.setStatusIndi('N');
			dtoWsNodeHistory.setDefaultFileFormat("");
			docMgmtImpl.insertNodeHistory(dtoWsNodeHistory);
	
			DTOWorkSpaceNodeVersionHistory dtoWsNodeVersionHistory = new DTOWorkSpaceNodeVersionHistory();
			dtoWsNodeVersionHistory.setWorkspaceId(workSpaceId);
			dtoWsNodeVersionHistory.setNodeId(Integer.parseInt(nodeId));
			dtoWsNodeVersionHistory.setTranNo(newTranNo);
			dtoWsNodeVersionHistory.setPublished('N');
			dtoWsNodeVersionHistory.setDownloaded('N');
			dtoWsNodeVersionHistory.setModifyBy(userId);
			dtoWsNodeVersionHistory.setExecutedBy(userId);
			DTOWorkSpaceNodeVersionHistory dtoWsNdVerHis = new DTOWorkSpaceNodeVersionHistory();
			dtoWsNdVerHis.setWorkspaceId(workSpaceId);
			dtoWsNdVerHis.setNodeId(Integer.parseInt(nodeId));
			Vector<DTOWorkSpaceNodeVersionHistory> wsNdVerHistoryList = docMgmtImpl.getWorkSpaceNodeVersionDetail(dtoWsNdVerHis);
			DTOWorkSpaceNodeVersionHistory dtoWsHistory = wsNdVerHistoryList.get(wsNdVerHistoryList.size()-1); 
			String version = dtoWsHistory.getUserDefineVersionId();
			if (attachment != null && fileUploading.trim().equalsIgnoreCase("Send back"))
			{
				float t=1.0f;
				if(version != null)
					t  = Float.parseFloat(version);
				if(dtoWsNdhis.getStageId() == 100)
				{
					t = (float)Math.floor(t);
					t+=1.000f;
				}
				else
					t=t+0.001f;
				version= String.format("%5.3f", t);
			}
			dtoWsNodeVersionHistory.setUserDefineVersionId(version);
			docMgmtImpl.insertWorkspaceNodeVersionHistory(dtoWsNodeVersionHistory, false);
			
			Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDtl = docMgmtImpl.getNodeAttrDetail(workSpaceId, Integer.parseInt(nodeId));
			if(wsNodeAttrDtl.size() > 0)
			{
				Vector<DTOWorkSpaceNodeAttrHistory> wsNodeAttrHistory = new Vector<DTOWorkSpaceNodeAttrHistory>();
				for (int i = 0; i < wsNodeAttrDtl.size(); i++) 
				{
					DTOWorkSpaceNodeAttrHistory dtoWsNodeAttrHistory = new DTOWorkSpaceNodeAttrHistory();
					dtoWsNodeAttrHistory.setWorkSpaceId(workSpaceId);
					dtoWsNodeAttrHistory.setAttrId(wsNodeAttrDtl.get(i).getAttrId());
					dtoWsNodeAttrHistory.setAttrValue(wsNodeAttrDtl.get(i).getAttrValue()== null?"":wsNodeAttrDtl.get(i).getAttrValue());
					dtoWsNodeAttrHistory.setNodeId(Integer.parseInt(nodeId));
					dtoWsNodeAttrHistory.setTranNo(newTranNo);
					dtoWsNodeAttrHistory.setRemark("");
					dtoWsNodeAttrHistory.setModifyBy(userId);
					dtoWsNodeAttrHistory.setStatusIndi('N');
					wsNodeAttrHistory.add(dtoWsNodeAttrHistory);
				}
				docMgmtImpl.insertNodeAttrHistory(wsNodeAttrHistory);
			}
		}
		
		DTOUserDocStageComments dtoDocStageComments = new DTOUserDocStageComments();
		dtoDocStageComments.setWorkspaceId(workSpaceId);
		dtoDocStageComments.setNodeId(Integer.parseInt(nodeId));
		dtoDocStageComments.setStageId(0);// 0 = document editing comments
		dtoDocStageComments.setTranNo(newTranNo);
		dtoDocStageComments.setUserCode(userId);
		dtoDocStageComments.setUserComments(userComments);
		dtoDocStageComments.setUserRefDocPath("");
		dtoDocStageComments.setUserRefDocName("");
		dtoDocStageComments.setRemarks("");
		dtoDocStageComments.setModifyBy(userId);
		dtoDocStageComments.setStatusIndi('N');
		docMgmtImpl.insertUserDocStageComments(dtoDocStageComments, 1);

		if (insertAll && stageId != 10) 
			htmlContent = "New Document Released Successfully";
		else		
			htmlContent = "Comments Added on Released Document";
		return true;
	}
	
	public String saveData() //This is for Add data....
	{
		String targetFilePath = "";
		int stageId=0;
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int usergrpcode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		ArrayList<DTOWorkSpaceNodeDetail> wsNodeDtlList = new ArrayList<DTOWorkSpaceNodeDetail>();
		int maxNodeId = docMgmtImpl.getmaxNodeId(workSpaceId);
		int newNodeId = maxNodeId+1;
		parentNodeId = Integer.parseInt(nodeId);
		int maxNodeNo = docMgmtImpl.getmaxNodeNo(workSpaceId, parentNodeId);
		
		DTOWorkSpaceNodeDetail dtoWsNodeDtl = new DTOWorkSpaceNodeDetail();
		dtoWsNodeDtl.setWorkspaceId(workSpaceId);
		dtoWsNodeDtl.setNodeId(newNodeId);
		dtoWsNodeDtl.setNodeNo(maxNodeNo+1);
		dtoWsNodeDtl.setParentNodeId(Integer.parseInt(nodeId));
		dtoWsNodeDtl.setNodeName(nodeName);
		dtoWsNodeDtl.setNodeDisplayName(nodeName);
		dtoWsNodeDtl.setNodeTypeIndi('N');
		dtoWsNodeDtl.setFolderName(folderName);
		dtoWsNodeDtl.setCloneFlag('N');
		dtoWsNodeDtl.setRequiredFlag('N');
		dtoWsNodeDtl.setCheckOutBy(0);
		dtoWsNodeDtl.setPublishedFlag('Y');
		dtoWsNodeDtl.setRemark(userComments);
		dtoWsNodeDtl.setModifyBy(userId);
		dtoWsNodeDtl.setDefaultFileFormat(null);
		wsNodeDtlList.add(dtoWsNodeDtl);
		docMgmtImpl.insertWorkspaceNodeDetail(wsNodeDtlList, 1);
		
		ArrayList<DTOWorkSpaceUserRightsMst> wsUserRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		DTOWorkSpaceUserRightsMst dtoWsUserRightsMst = new DTOWorkSpaceUserRightsMst();
		dtoWsUserRightsMst.setWorkSpaceId(workSpaceId);
		dtoWsUserRightsMst.setUserGroupCode(usergrpcode);
		dtoWsUserRightsMst.setUserCode(userId);
		dtoWsUserRightsMst.setCanReadFlag('Y');
		dtoWsUserRightsMst.setCanEditFlag('Y');
		dtoWsUserRightsMst.setCanAddFlag('Y');
		dtoWsUserRightsMst.setCanDeleteFlag('Y');
		dtoWsUserRightsMst.setAdvancedRights("Y");
		dtoWsUserRightsMst.setNodeId(newNodeId);
		dtoWsUserRightsMst.setStageId(10);
		dtoWsUserRightsMst.setRemark("");
		dtoWsUserRightsMst.setModifyBy(userId);
		dtoWsUserRightsMst.setStatusIndi('N');
		wsUserRightsList.add(dtoWsUserRightsMst);
		docMgmtImpl.insertUpdateMultipleUserRights(wsUserRightsList, 1);		
		
		/*Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDtl = new Vector<DTOWorkSpaceNodeAttrDetail>();
		wsNodeAttrDtl = docMgmtImpl.getNodeAttrDetail(workSpaceId, Integer.parseInt(nodeId));
		ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
		for (int i = 0; i < wsNodeAttrDtl.size(); i++) 
		{
			DTOWorkSpaceNodeAttrDetail dtoWsNodeAttrDtl = new DTOWorkSpaceNodeAttrDetail();
			dtoWsNodeAttrDtl.setWorkspaceId(workSpaceId);
			dtoWsNodeAttrDtl.setNodeId(newNodeId);
			dtoWsNodeAttrDtl.setAttrId(wsNodeAttrDtl.get(i).getAttrId());
			dtoWsNodeAttrDtl.setAttrName(wsNodeAttrDtl.get(i).getAttrName());
			dtoWsNodeAttrDtl.setAttrValue(wsNodeAttrDtl.get(i).getAttrValue());
			dtoWsNodeAttrDtl.setAttrForIndi(wsNodeAttrDtl.get(i).getAttrForIndi());
			dtoWsNodeAttrDtl.setModifyBy(userId);
			dtoWsNodeAttrDtl.setStatusIndi('N');
			nodeAttrList.add(dtoWsNodeAttrDtl);
			//docMgmtImpl.insertWorkspaceNodeAttrDetail(dtoWsNodeAttrDtl);
		}
		docMgmtImpl.insertWorkspaceNodeAttrDetail(nodeAttrList);
		*/
		
		HttpServletRequest request = ServletActionContext.getRequest();
	    ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
		for(int i=1;i<attrCount;i++)
		{
			DTOWorkSpaceNodeAttrDetail  wsNodeAttrDtl = new DTOWorkSpaceNodeAttrDetail();
			String attrValueIds = "attrValueId"+i;
	    	String attrTypes = "attrType"+i;
	    	String attrIds= "attrId"+i;
	    	String attrNames = "attrName"+i;
	    	String attrForInIds ="attrForIndiId"+i;
	    	Integer attributeId = Integer.parseInt(request.getParameter(attrIds));
	    	wsNodeAttrDtl.setWorkspaceId(workSpaceId);
			wsNodeAttrDtl.setNodeId(newNodeId);
			wsNodeAttrDtl.setAttrId(attributeId);
			wsNodeAttrDtl.setAttrName(request.getParameter(attrNames));
	    	wsNodeAttrDtl.setAttrValue(request.getParameter(attrValueIds));
	    	wsNodeAttrDtl.setAttrForIndi(request.getParameter(attrForInIds));
	    	wsNodeAttrDtl.setModifyBy(userId);
	    	nodeAttrList.add(wsNodeAttrDtl);
		}	
		docMgmtImpl.insertWorkspaceNodeAttrDetail(nodeAttrList);
		
		
		//Insert node History with file
		//-------------------------------------------------------------------------------------------------
		//Get new TranNo
		int newTranNo=docMgmtImpl.getNewTranNo(workSpaceId);
		//Copy file
		DTOWorkSpaceMst workSpaceMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		//TODO if condition for new and inherited doc
		File sourceFile,targetFile;
		String sourceFileName;
		File tempTargetFile = null;
		if(documentSrc != null && documentSrc.equalsIgnoreCase("Send back"))
		{
			sourceFile = attachment;
			sourceFileName = attachmentFileName;
		}
		else
		{
			int[] nodeIds =  {Integer.parseInt(nodeId)};
			//ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistoryList = docMgmtImpl.getAllNodesLastHistory(workSpaceId,nodeIds);
			ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistoryList = docMgmtImpl.getRefWorkspaceNodes(workSpaceId,Integer.parseInt(nodeId));
			DTOWorkSpaceNodeHistory dtoWsNodeHis = new DTOWorkSpaceNodeHistory();
			dtoWsNodeHis = wsNodeHistoryList.get(wsNodeHistoryList.size()-1);
			sourceFileName = dtoWsNodeHis.getFileName();
			sourceFile = new File(dtoWsNodeHis.getBaseWorkFolder()+dtoWsNodeHis.getFolderName()+"/"+sourceFileName);
		}
		
		if(printDocId!=null && printDocId.equalsIgnoreCase("Y"))
		{
			tempTargetFile = new File(sourceFile.getParent(),"__"+sourceFile.getName());
			try {
				//PDFUtilities.addStringToPdf(sourceFile.getPath(), tempTargetFile.getPath(), folderName, 05.0f, null, PDFUtilities.POSITION_TOPRIGHT, 0.0f, 0.0f, 0.0f);
				PDFUtilities.addStringToPdf(sourceFile.getPath(), tempTargetFile.getPath(), folderName,60.0f, 12.0f, null, PDFUtilities.POSITION_BOTTOMLEFT, 0.0f, 0.0f, 0.0f);
			} catch (Exception e) {
				e.printStackTrace();
			}
			sourceFile = tempTargetFile;
		}
		targetFile = new File(workSpaceMst.getBaseWorkFolder()+"/"+workSpaceId+"/"+newNodeId+"/"+newTranNo+"/"+sourceFileName);
		FileManager fileManager = new FileManager();
		fileManager.copyDirectory(sourceFile, targetFile);
	        
		if(printDocId!=null && printDocId.equalsIgnoreCase("Y") && sourceFile == tempTargetFile)
			sourceFile.delete();
		//-------------------------------------------------------------------------------------------------
		Vector<DTOWorkSpaceNodeAttrHistory> wsNodeAttrHistory = new Vector<DTOWorkSpaceNodeAttrHistory>();
		for (int i = 0; i < nodeAttrList.size(); i++) 
		{
			DTOWorkSpaceNodeAttrHistory dtoWsNodeAttrHistory = new DTOWorkSpaceNodeAttrHistory();
			dtoWsNodeAttrHistory.setWorkSpaceId(workSpaceId);
			dtoWsNodeAttrHistory.setNodeId(newNodeId);
			dtoWsNodeAttrHistory.setTranNo(newTranNo);
			dtoWsNodeAttrHistory.setAttrId(nodeAttrList.get(i).getAttrId());
			dtoWsNodeAttrHistory.setAttrValue(nodeAttrList.get(i).getAttrValue());
			dtoWsNodeAttrHistory.setRemark("");
			dtoWsNodeAttrHistory.setModifyBy(userId);
			dtoWsNodeAttrHistory.setStatusIndi('N');
			wsNodeAttrHistory.add(dtoWsNodeAttrHistory);
		}
		docMgmtImpl.insertNodeAttrHistory(wsNodeAttrHistory);
		
		DTOWorkSpaceNodeHistory dtoWsNodeHistory = new DTOWorkSpaceNodeHistory();
		dtoWsNodeHistory.setWorkSpaceId(workSpaceId);
		dtoWsNodeHistory.setNodeId(newNodeId);
		dtoWsNodeHistory.setTranNo(newTranNo);
		dtoWsNodeHistory.setStageId(stageId);
		dtoWsNodeHistory.setFolderName("/"+workSpaceId+"/"+newNodeId+"/"+newTranNo);
		dtoWsNodeHistory.setFileName(sourceFileName);
		dtoWsNodeHistory.setFileType(sourceFileContentType);
		dtoWsNodeHistory.setRequiredFlag('Y');
		dtoWsNodeHistory.setRemark("");
		dtoWsNodeHistory.setModifyBy(userId);
		dtoWsNodeHistory.setStatusIndi('N');
		dtoWsNodeHistory.setDefaultFileFormat("");
		docMgmtImpl.insertNodeHistory(dtoWsNodeHistory);
		
		DTOWorkSpaceNodeVersionHistory dtoWsNodeVersionHistory = new DTOWorkSpaceNodeVersionHistory();
		dtoWsNodeVersionHistory.setWorkspaceId(workSpaceId);
		dtoWsNodeVersionHistory.setNodeId(newNodeId);
		dtoWsNodeVersionHistory.setTranNo(newTranNo);	
		dtoWsNodeVersionHistory.setPublished('N');
		dtoWsNodeVersionHistory.setDownloaded('N');
		dtoWsNodeVersionHistory.setModifyBy(userId);
		dtoWsNodeVersionHistory.setExecutedBy(userId);
		dtoWsNodeVersionHistory.setUserDefineVersionId("1.000");
		docMgmtImpl.insertWorkspaceNodeVersionHistory(dtoWsNodeVersionHistory,false);
		
		//int tranNo = docMgmtImpl.getNewTranNo(workSpaceId);
		DTOUserDocStageComments dtoDocStageComments = new DTOUserDocStageComments();
		dtoDocStageComments.setWorkspaceId(workSpaceId);
		dtoDocStageComments.setNodeId(newNodeId);
		dtoDocStageComments.setStageId(stageId);
		dtoDocStageComments.setTranNo(newTranNo);
		dtoDocStageComments.setUserCode(userId);
		dtoDocStageComments.setUserComments(userComments);
		dtoDocStageComments.setUserRefDocPath("");
		dtoDocStageComments.setUserRefDocName("");
		dtoDocStageComments.setRemarks("");
		dtoDocStageComments.setModifyBy(userId);
		dtoDocStageComments.setStatusIndi('N');
		docMgmtImpl.insertUserDocStageComments(dtoDocStageComments, 1);
		targetFilePath =targetFile.toString();
		return targetFilePath;
	}
}