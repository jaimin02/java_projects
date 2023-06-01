package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTODocReleaseTrack;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DocReleaseTrackAction extends ActionSupport 
{
	private static final long serialVersionUID = -2709310654973549183L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public ArrayList<DTOWorkSpaceMst> workspaceMstList= new ArrayList<DTOWorkSpaceMst>();
	public String htmlContent="";
	public String workspaceId="";
	public String nodeId="";
	public ArrayList<DTODocReleaseTrack> docReleaseTrackList = new ArrayList<DTODocReleaseTrack>();
	
	
	@Override
	public String execute() throws Exception 
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		workspaceMstList = docMgmtImpl.getWorkspaceDtByProjPublishType(userId, userGroupCode, ProjectType.DMS_DOC_CENTRIC);
		return SUCCESS;
	}
	
	public String getReleasedNodes()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		Vector<DTOWorkSpaceUserRightsMst> wsUserRightsMstList = new Vector<DTOWorkSpaceUserRightsMst>();
		DTOWorkSpaceUserRightsMst dtoUserRightsMst = new DTOWorkSpaceUserRightsMst();
		dtoUserRightsMst.setWorkSpaceId(workspaceId);
		dtoUserRightsMst.setNodeId(0);
		dtoUserRightsMst.setParentNodeId(1);
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

	public String getReleasedDocuments()
	{
		docReleaseTrackList = docMgmtImpl.getDocReleaseTrack(workspaceId,Integer.parseInt(nodeId));
		return SUCCESS;
	}
}
