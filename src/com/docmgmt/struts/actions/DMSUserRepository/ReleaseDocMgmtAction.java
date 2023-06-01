package com.docmgmt.struts.actions.DMSUserRepository;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Vector;

import javax.print.PrintService;

import com.docmgmt.dto.DTOReleaseDocMgmt;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.docmgmt.server.webinterface.services.pdf.PDFUtilities;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ReleaseDocMgmtAction extends ActionSupport 
{
	private static final long serialVersionUID = -8988778000966507449L;
	
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	
	public String workspaceId;
	public String nodeId;
	public String statusIndi;
	public String comments;
	public ArrayList<DTOReleaseDocMgmt> releaseDocMgmtList = new ArrayList<DTOReleaseDocMgmt>();
	public String htmlContent = "";
	public String filePath;
	public String docId;
	public ArrayList<DTOWorkSpaceMst> workspaceMstList= new ArrayList<DTOWorkSpaceMst>();
	public String folderName;
	public String fromId;
	public String toId;
	public String stage;
	public String docVersion;
	@Override
	public String execute() throws Exception 
	{
		return SUCCESS;
	}
	
	public String saveDtl()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		ArrayList<DTOReleaseDocMgmt> relDocMgmtList = new ArrayList<DTOReleaseDocMgmt>();
		DTOReleaseDocMgmt dtoReleaseDocMgmt = new DTOReleaseDocMgmt();
		dtoReleaseDocMgmt.setWorkspaceId(workspaceId);
		dtoReleaseDocMgmt.setNodeId(Integer.parseInt(nodeId));
		dtoReleaseDocMgmt.setStatusIndi(statusIndi.charAt(0));
		dtoReleaseDocMgmt.setModifyBy(userId);
		dtoReleaseDocMgmt.setComments(comments);
		dtoReleaseDocMgmt.setStage(stage);
		dtoReleaseDocMgmt.setDocVersion(docVersion);
		relDocMgmtList.add(dtoReleaseDocMgmt);
		docMgmtImpl.insertReleaseDocMgmt(relDocMgmtList);
		if (statusIndi.trim().equalsIgnoreCase("P"))
		{
			PrinterJob printJob = PrinterJob.getPrinterJob();
	        PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
	        PrintService[] printServices = PrinterJob.lookupPrintServices();
	        if (printServices.length > 0) 
	        {
				PDFUtilities p = new PDFUtilities();
				try 
				{
					p.printPDF(filePath, "", "KnowledgeNET:"+docId);
					htmlContent = "Document Sent To Printer.";
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					htmlContent = "Error While Printing Document.";
				}
	        }
	        else
	        	htmlContent = "Printer Not Found.";
		}
		else
			htmlContent = "Document Returned.";
		return SUCCESS;
	}
	
	public String getWorkspaceDetail()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		workspaceMstList = docMgmtImpl.getWorkspaceDtByProjPublishType(userId, userGroupCode, ProjectType.DMS_DOC_CENTRIC);
		return SUCCESS;
	}
	
	public String  getCatDtl()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		Vector<DTOWorkSpaceUserRightsMst> wsUserRightsMstList = new Vector<DTOWorkSpaceUserRightsMst>();
		DTOWorkSpaceUserRightsMst dtoUserRightsMst = new DTOWorkSpaceUserRightsMst();
		dtoUserRightsMst.setWorkSpaceId(workspaceId);
		dtoUserRightsMst.setParentNodeId(1);
		dtoUserRightsMst.setNodeId(0);
		dtoUserRightsMst.setUserCode(userId);
		wsUserRightsMstList  = docMgmtImpl.getUserRightsReport(dtoUserRightsMst, false);
		DTOWorkSpaceUserRightsMst dtoWsNodeDtl = new DTOWorkSpaceUserRightsMst();  
		htmlContent = "<option id=\"All##All\" value=\"All##All\" style=\"display: block;\"></option>";	
		for (int itrOption = 0; itrOption < wsUserRightsMstList.size(); itrOption++) 
		{
			dtoWsNodeDtl= wsUserRightsMstList.get(itrOption);
			htmlContent += "<option id=\""+dtoWsNodeDtl.getNodeId()+"##"+dtoWsNodeDtl.getWorkSpaceId()+"\" value=\""+dtoWsNodeDtl.getNodeId()+"##"+dtoWsNodeDtl.getWorkSpaceId()+"\" style=\"display: block;\">";
			htmlContent += dtoWsNodeDtl.getNodeName();
			htmlContent +="</option>";
		}
		return SUCCESS;
	}
	
	public String getDocId()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		Vector<DTOWorkSpaceUserRightsMst> wsUserRightsMstList = new Vector<DTOWorkSpaceUserRightsMst>();
		DTOWorkSpaceUserRightsMst dtoUserRightsMst = new DTOWorkSpaceUserRightsMst();
		dtoUserRightsMst.setWorkSpaceId(workspaceId);
		dtoUserRightsMst.setParentNodeId(Integer.parseInt(nodeId));
		dtoUserRightsMst.setNodeId(0);
		dtoUserRightsMst.setUserCode(userId);
		wsUserRightsMstList  = docMgmtImpl.getUserRightsReport(dtoUserRightsMst, false);
		DTOWorkSpaceUserRightsMst dtoWsNodeDtl = new DTOWorkSpaceUserRightsMst();  
		htmlContent = "<option id=\"All##All\" value=\"All##All\" style=\"display: block;\"></option>";	
		for (int itrOption = 0; itrOption < wsUserRightsMstList.size(); itrOption++) 
		{
			dtoWsNodeDtl= wsUserRightsMstList.get(itrOption);
			htmlContent += "<option id=\""+dtoWsNodeDtl.getNodeId()+"##"+dtoWsNodeDtl.getWorkSpaceId()+"\" value=\""+dtoWsNodeDtl.getNodeId()+"##"+dtoWsNodeDtl.getWorkSpaceId()+"\" style=\"display: block;\">";
			htmlContent += dtoWsNodeDtl.getFolderName();
			htmlContent +="</option>";
		}
		return SUCCESS;
	}
	
	public String  getDtl()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		Vector<DTOWorkSpaceUserRightsMst> wsUserRightsMstList = new Vector<DTOWorkSpaceUserRightsMst>();
		DTOWorkSpaceUserRightsMst dtoWsUserRightMst = new DTOWorkSpaceUserRightsMst();
		dtoWsUserRightMst.setWorkSpaceId(workspaceId);
		dtoWsUserRightMst.setNodeId(0);
		dtoWsUserRightMst.setUserCode(userId);
		dtoWsUserRightMst.setParentNodeId(Integer.parseInt(nodeId));
		dtoWsUserRightMst.setFolderName(folderName);
		dtoWsUserRightMst.setFrom(fromId);
		dtoWsUserRightMst.setTo(toId);
		wsUserRightsMstList  = docMgmtImpl.getUserRightsReport(dtoWsUserRightMst, false);
		ArrayList<Integer> nodeIdList = new ArrayList<Integer>();
		for (int itrwsNdDtlList = 0; itrwsNdDtlList < wsUserRightsMstList.size(); itrwsNdDtlList++) 
			nodeIdList.add(wsUserRightsMstList.get(itrwsNdDtlList).getNodeId()); 
		releaseDocMgmtList = docMgmtImpl.getReleaseDocDtl(workspaceId,nodeIdList);
		return SUCCESS;
	}
}